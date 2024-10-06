package jp.co.ais.trans.common.util;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

import javax.imageio.*;
import javax.swing.*;

import jp.co.ais.trans.common.except.*;

/**
 * リソース系ユーティリティ
 */
public final class ResourceUtil {

	/** バッファーサイズ（バイト数）4KB */
	public static final int BUFFER_SIZE = 4096;

	/**
	 * リソースパス
	 * 
	 * @param clazz クラス
	 * @return リソースパス
	 */
	public static String getResourcePath(Class clazz) {
		return org.seasar.framework.util.ResourceUtil.getResourcePath(clazz);
	}

	/**
	 * クラスの配置場所を基にファイルパスを取得.
	 * 
	 * @param cls 基準クラス
	 * @param path パス
	 * @return ファイルパス
	 */
	public static String convertPath(Class cls, String path) {
		if (org.seasar.framework.util.ResourceUtil.getResourceNoException(path) != null) {
			return path;
		}
		String prefix = cls.getName().replace('.', '/').replaceFirst("/[^/]+$", "");
		return prefix + "/" + path;
	}

	/**
	 * クラスの配置場所を基にリソースを取得.
	 * 
	 * @param cls 基準クラス
	 * @param path パス
	 * @return リソース
	 */
	public static File getResourceAsFile(Class cls, String path) {
		return org.seasar.framework.util.ResourceUtil.getResourceAsFile(convertPath(cls, path));
	}

	/**
	 * 指定されたパス上のリソースを取得.
	 * 
	 * @param path パス
	 * @return リソース
	 */
	public static File getResourceAsFile(String path) {
		return org.seasar.framework.util.ResourceUtil.getResourceAsFile(path);
	}

	/**
	 * Iconの取得
	 * 
	 * @param cls 基準クラス
	 * @param path パス
	 * @return Icon
	 */
	public static ImageIcon getImage(Class cls, String path) {

		return new ImageIcon(getResource(cls, path));
	}

	/**
	 * Iconの取得
	 * 
	 * @param path パス
	 * @return Icon
	 */
	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(getResource(path));

		} catch (IOException ex) {
			throw new TRuntimeException(ex, "E00008");
		}
	}

	/**
	 * Iconの取得
	 * 
	 * @param path パス
	 * @return Icon
	 */
	public static Icon getIcon(String path) {
		return new ImageIcon(getResource(path));
	}

	/**
	 * URLの取得
	 * 
	 * @param path パス
	 * @return URL
	 */
	public static URL getResource(String path) {
		try {
			return org.seasar.framework.util.ResourceUtil.getResource(path);

		} catch (RuntimeException ex) {
			throw new TRuntimeException(ex, "E00008");
		}
	}

	/**
	 * URLの取得
	 * 
	 * @param cls 基準クラス
	 * @param path パス
	 * @return URL
	 */
	public static URL getResource(Class cls, String path) {
		return getResource(convertPath(cls, path));
	}

	/**
	 * InputStreamの取得
	 * 
	 * @param path パス
	 * @return InputStream
	 */
	public static InputStream getResourceAsStream(String path) {

		return org.seasar.framework.util.ResourceUtil.getResourceAsStream(path, null);
	}

	/**
	 * InputStreamの取得
	 * 
	 * @param cls クラス
	 * @param path パス
	 * @return InputStream
	 */
	public static InputStream getResourceAsStream(Class cls, String path) {

		return org.seasar.framework.util.ResourceUtil.getResourceAsStream(convertPath(cls, path), null);
	}

	/**
	 * バイトから画像の取得
	 * 
	 * @param bytes バイナリ
	 * @return 画像
	 */
	public static BufferedImage toImage(byte[] bytes) {
		try {
			if (bytes == null) {
				return null;
			}

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			return ImageIO.read(bais);

		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * 指定されたファイルが書き込み可能状態かどうかを返す
	 * 
	 * @param fileName ファイル名
	 * @return 書き込み可能な場合true
	 */
	public static boolean canWrite(String fileName) {

		if (!new File(fileName).exists()) {
			return true;
		}

		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(fileName);

			return true;

		} catch (IOException ex) {
			return false;
		} finally {
			closeOutputStream(fo);
		}
	}

	/**
	 * バイナリデータをファイルに書き込む
	 * 
	 * @param fileName ファイル名
	 * @param bytes バイナリデータ
	 * @throws IOException 書き込み失敗
	 */
	public static void writeBinary(String fileName, byte[] bytes) throws IOException {
		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(fileName);

			fo.write(bytes);
			fo.flush();

		} finally {
			closeOutputStream(fo);
		}
	}

	/**
	 * InputStreamをclose
	 * 
	 * @param stream InputStream
	 */
	public static void closeInputStream(InputStream stream) {

		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}

	/**
	 * OutputStreamをclose
	 * 
	 * @param stream OutputStream
	 */
	public static void closeOutputStream(OutputStream stream) {

		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}

	/**
	 * PrintWriterをclose
	 * 
	 * @param bw PrintWriter
	 */
	public static void closePrintWriter(PrintWriter bw) {
		if (bw != null) {
			bw.close();
		}
	}

	/**
	 * Zip圧縮.指定ファイルをZIPに格納してバイナリ化.<br>
	 * 1ファイルのみを対象
	 * 
	 * @param fileName 格納ファイル名
	 * @param bytes 格納対象ファイルバイナリ
	 * @return ZIPバイナリ
	 * @throws IOException
	 */
	public static byte[] zipBinary(String fileName, byte[] bytes) throws IOException {

		ByteArrayOutputStream bos = null;
		ZipOutputStream zos = null;

		try {
			// ファイル圧縮
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);

			ZipEntry zent = new ZipEntry(fileName);
			zos.putNextEntry(zent);

			// ZIPに追加する[ファイル1]を読み取る入力ストリームを生成する
			BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(bytes));

			// 入力ストリームからZIPファイルに追加するファイルの内容を読み取る
			int count = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((count = in.read(buffer)) > 0) {

				if (count != BUFFER_SIZE) {
					buffer = Arrays.copyOf(buffer, count);
				}
				// ZIPファイルを出力するストリームに対してwrite()する。
				zos.write(buffer);
			}

			// 入力ストリームを閉じる
			in.close();
			in = null;

			// エントリを閉じる
			zos.closeEntry();

			return bos.toByteArray();

		} finally {
			ResourceUtil.closeOutputStream(bos);
			ResourceUtil.closeOutputStream(zos);
		}
	}

	/**
	 * Zip解凍.Zip形式ファイルからファイルを取り出して書き込み.<br>
	 * Zip内には1ファイルのみを想定
	 * 
	 * @param fileName 出力ファイル名
	 * @param bytes Zip形式ファイルバイナリ
	 * @throws IOException
	 */
	public static void writeBinaryInZip(String fileName, byte[] bytes) throws IOException {

		BufferedOutputStream out = null; // 解凍ファイル出力用ストリーム
		ZipInputStream in = null; // Zipファイル読込用ストリーム

		try {
			in = new ZipInputStream(new ByteArrayInputStream(bytes));

			@SuppressWarnings("unused")
			ZipEntry zipEntry = null;
			boolean hasData = false;

			// ZIPファイルの次のエントリの先頭にストリームを移動する
			while ((zipEntry = in.getNextEntry()) != null) {

				// ZIPファイル内のエントリをファイルに出力するストリームを生成する
				out = new BufferedOutputStream(new FileOutputStream(fileName));

				// 1バイトずつ、エントリから読み込んで、展開先ファイルに出力する
				int count = 0;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((count = in.read(buffer)) > 0) {

					if (count != BUFFER_SIZE) {
						buffer = Arrays.copyOf(buffer, count);
					}
					out.write(buffer);
					hasData = true;
				}

				// 現在のZIPエントリを閉じる
				in.closeEntry();

				// 出力ストリームを閉じる
				out.close();
				out = null;
			}

			if (!hasData) {
				// 空ファイルを作る
				writeBinary(fileName, new byte[0]);
			}

		} finally {
			ResourceUtil.closeInputStream(in);
			ResourceUtil.closeOutputStream(out);
		}
	}

	/**
	 * Zip解凍.Zip形式ファイルからファイルを取り出してバイナリに戻す.<br>
	 * Zip内には1ファイルのみを想定
	 * 
	 * @param bytes Zip形式ファイルバイナリ
	 * @return 解凍後ファイルバイナリ
	 * @throws IOException
	 */
	public static byte[] toBinaryInZip(byte[] bytes) throws IOException {

		BufferedOutputStream out = null; // 解凍ファイル出力用ストリーム
		ZipInputStream in = null; // Zipファイル読込用ストリーム
		ByteArrayOutputStream baos = null;

		try {
			in = new ZipInputStream(new ByteArrayInputStream(bytes));

			baos = new ByteArrayOutputStream();

			@SuppressWarnings("unused")
			ZipEntry zipEntry = null;

			// ZIPファイルの次のエントリの先頭にストリームを移動する
			while ((zipEntry = in.getNextEntry()) != null) {

				// ZIPファイル内のエントリをファイルに出力するストリームを生成する
				out = new BufferedOutputStream(baos);

				// 1バイトずつ、エントリから読み込んで、展開先ファイルに出力する
				int count = 0;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((count = in.read(buffer)) > 0) {

					if (count != BUFFER_SIZE) {
						buffer = Arrays.copyOf(buffer, count);
					}
					out.write(buffer);
				}

				// 現在のZIPエントリを閉じる
				in.closeEntry();

				// 出力ストリームを閉じる
				out.close();
				out = null;
			}

			return baos.toByteArray();

		} finally {
			ResourceUtil.closeOutputStream(baos);
			ResourceUtil.closeInputStream(in);
			ResourceUtil.closeOutputStream(out);
		}
	}

	/**
	 * 指定ファイルかフォルダが存在するかどうか判断する
	 * 
	 * @param filePath 指定ファイルかフォルダのパス
	 * @return 指定ファイルかフォルダが存在するかどうか
	 */
	public static boolean isFilePathExists(String filePath) {
		// 指定ファイルかフォルダ
		File file = new File(filePath);

		// 存在判断
		return file.exists();
	}

	/**
	 * ファイル名称から拡張子を取得
	 * 
	 * @param f ファイル
	 * @return 拡張子
	 */
	public static String getExtension(File f) {
		String ext = "";
		String filename = f.getName();
		int dotIndex = filename.lastIndexOf('.');

		if ((dotIndex > 0) && (dotIndex < filename.length() - 1)) {
			ext = filename.substring(dotIndex + 1).toLowerCase();
		}

		return ext;
	}

	/**
	 * 指定ファイルをバイナリ形式に変換する.
	 * 
	 * @param fileName ファイル名
	 * @return バイナリ
	 * @throws IOException
	 */
	public static byte[] readBinarry(String fileName) throws IOException {
		FileInputStream in = null;
		ByteArrayOutputStream bo = null;

		try {
			in = new FileInputStream(fileName);
			bo = new ByteArrayOutputStream();

			int count = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((count = in.read(buffer)) > 0) {

				if (count != BUFFER_SIZE) {
					buffer = Arrays.copyOf(buffer, count);
				}
				bo.write(buffer);
			}

			return bo.toByteArray();

		} finally {
			closeOutputStream(bo);
			closeInputStream(in);
		}
	}

	/**
	 * 指定文字でテキストファイルを作成する
	 * 
	 * @param fileName ファイル名
	 * @param txt テキスト
	 * @throws IOException
	 */
	public static void makeTextFile(String fileName, String txt) throws IOException {

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(txt);

			bw.close();
			osw.close();

		} finally {
			closeOutputStream(fos);
		}
	}

	/**
	 * バイナリのオブジェクト変換
	 * 
	 * @param bytes バイナリ
	 * @return オブジェクト
	 */
	public static Object toObject(byte[] bytes) {
		ByteArrayInputStream input = null;
		ObjectInputStream ois = null;

		try {
			input = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(input);

			return ois.readObject();

		} catch (Exception ex) {
			throw new TRuntimeException(ex);

		} finally {
			ResourceUtil.closeInputStream(input);
			ResourceUtil.closeInputStream(ois);
		}
	}

	/**
	 * オブジェクトのバイナリ変換
	 * 
	 * @param obj オブジェクト
	 * @return バイナリ
	 */
	public static byte[] toBinarry(Object obj) {
		ByteArrayOutputStream out = null;
		ObjectOutputStream oos = null;

		try {
			out = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			oos.flush();

			return out.toByteArray();

		} catch (Exception ex) {
			throw new TRuntimeException(ex);

		} finally {
			ResourceUtil.closeOutputStream(out);
			ResourceUtil.closeOutputStream(oos);
		}
	}

	/**
	 * 拡張子を取得
	 * 
	 * @param fileName ファイル名
	 * @return 拡張子
	 */
	public static String getExtension(String fileName) {
		if (Util.isNullOrEmpty(fileName)) {
			return "";
		}

		String[] names = StringUtil.split(fileName, ".");
		if (names.length == 0) {
			return "";
		}

		return names[names.length - 1];
	}
}
