package jp.co.ais.trans2.common.file;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * ファイルのユーティリティ
 * 
 * @author AIS
 */
public class FileUtil {

	/**
	 * オブジェクトの保存<br>
	 * AIS設定フォルダに変更した。(Windows10以降にTEMPフォルダは不安定のため)
	 * 
	 * @param obj オブジェクト
	 * @param name ファイル名称(ファイル名のみ)
	 */
	public static void saveTemporaryObject(Object obj, String name) {
		String path = SystemUtil.getAisSettingDir();
		saveObject(obj, path + name);
	}

	/**
	 * オブジェクトの保存
	 * 
	 * @param obj オブジェクト
	 * @param fileName ファイル名称(フルパス)
	 */
	public static void saveObject(Object obj, String fileName) {

		try {

			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 8192);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			bos.close();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * オブジェクトの復元<br>
	 * AIS設定フォルダに変更した。(Windows10以降にTEMPフォルダは不安定のため)
	 * 
	 * @param name ファイル名称(ファイル名のみ)
	 * @return オブジェクト
	 */
	public static Object getTemporaryObject(String name) {
		String path = SystemUtil.getAisSettingDir();
		return getObject(path + name);
	}

	/**
	 * オブジェクトの復元
	 * 
	 * @param fileName ファイル名
	 * @return オブジェクト
	 */
	public static Object getObject(String fileName) {

		try {
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis, 8192);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object obj = ois.readObject();

			ois.close();
			bis.close();
			fis.close();

			return obj;

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * PDFの保存
	 * 
	 * @param obj
	 * @param ledgerName
	 */
	public static void savePdf(Object obj, String ledgerName) {

		try {

			File file = new File(ledgerName + ".pdf");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 8192);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);

			oos.flush();
			oos.close();
			bos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * PDFを開く
	 * 
	 * @param obj
	 * @param ledgerName
	 */
	public static void openPdf(Object obj, String ledgerName) {

		try {
			savePdf(obj, ledgerName);
			File f = new File(ledgerName + ".pdf");
			Desktop desktop = Desktop.getDesktop();
			desktop.open(f);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ファイルを削除する
	 * 
	 * @param filePath 削除ファイルパス(フルパス)
	 */
	public static void removeFile(String filePath) {

		try {
			File file = new File(filePath);

			if (file.exists()) {
				file.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ファイルを削除する<br>
	 * AIS設定フォルダに変更した。(Windows10以降にTEMPフォルダは不安定のため)
	 * 
	 * @param fileName 削除ファイル名
	 */
	public static void removeTempolaryFile(String fileName) {
		String path = SystemUtil.getAisSettingDir();
		removeFile(path + fileName);
	}

	/**
	 * ファイルをコピーする。<br>
	 * コピー先フォルタが存在しない場合作成する。
	 * 
	 * @param oldFilePath コピー元
	 * @param newFilePath コピー先
	 * @throws TException
	 */
	public static void copyFile(String oldFilePath, String newFilePath) throws TException {

		FileInputStream fis = null;
		FileOutputStream fos = null;

		FileChannel oldChannel = null;
		FileChannel newChannel = null;

		try {
			// 初期化
			File oldFile = new File(oldFilePath);
			File newFile = new File(newFilePath);

			// コピー元ファイルの存在チェック
			if (!oldFile.exists() || !oldFile.isFile()) {
				// 処理対象ファイルが取得元フォルダに存在しません。設定ファイルを確認してください。\n{0}
				throw new TException("I00253", oldFilePath);
			}

			// コピー先パスの存在チェック
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}

			// 更新日時
			long lastModified = oldFile.lastModified();

			fis = new FileInputStream(oldFilePath);
			fos = new FileOutputStream(newFilePath);

			oldChannel = fis.getChannel();
			newChannel = fos.getChannel();

			// コピー処理
			oldChannel.transferTo(0, oldChannel.size(), newChannel);

			close(oldChannel);
			close(newChannel);

			// 更新日時
			newFile.setLastModified(lastModified);

		} catch (IOException ex) {
			throw new TException(ex);

		} finally {
			close(oldChannel);
			close(newChannel);

			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				//
			}
		}
	}

	/**
	 * FileChannelのclose
	 * 
	 * @param channel
	 */
	public static void close(FileChannel channel) {
		try {
			if (channel != null) {
				channel.close();
			}
		} catch (IOException ex) {
			//
		}
	}

	/**
	 * ファイルを移動する<br>
	 * 移動先フォルタが存在しない場合、自動作成する。
	 * 
	 * @param oldFilePath 移動元
	 * @param newFilePath 移動先
	 * @throws TException
	 */
	public static void moveFile(String oldFilePath, String newFilePath) throws TException {

		// ファイルをコピーする。
		copyFile(oldFilePath, newFilePath);

		// 移動先ファイルの存在確認を行なってから元を削除
		if (new File(newFilePath).exists()) {
			removeFile(oldFilePath);
		}
	}

	/**
	 * パスへアクセスチェック
	 * 
	 * @param path パス
	 * @return メッセージ
	 */
	public static boolean canAccess(String path) {

		return canAccess(new File(path));
	}

	/**
	 * パスへアクセスチェック
	 * 
	 * @param file 対象ファイル
	 * @return メッセージ
	 */
	public static boolean canAccess(File file) {

		// パスへアクセス確認
		if (!file.exists() || !file.canRead() || file.listFiles() == null) {
			return false;
		}

		return true;
	}

	/**
	 * ファイル名の禁止文字変換
	 * 
	 * @param fileName ファイル名
	 * @return 禁止文字をブランクに置換した名称
	 */
	public static String toNonProhibitedName(String fileName) {

		// TODO パターンを解析して全て網羅
		return fileName.replaceAll("/", "");
	}

	/**
	 * ファイル名に文字を追加する
	 * 
	 * @param fileName ファイル名
	 * @param add 追加文字
	 * @return 追加後文字
	 */
	public static String addName(String fileName, String add) {
		int point = fileName.lastIndexOf('.');

		if (point == -1) {
			return fileName + add;
		}

		return fileName.substring(0, point) + add + fileName.substring(point, fileName.length());
	}

	/**
	 * 拡張子を除外
	 * 
	 * @param fileName ファイル名
	 * @return 拡張子抜きの名称
	 */
	public static String removeExtension(String fileName) {
		int point = fileName.lastIndexOf('.');

		if (point <= 0) {
			return fileName;
		}

		return fileName.substring(0, point);
	}

	/**
	 * URLからファイルに保存する
	 * 
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	public static void copyURLToFile(URL source, File destination) throws IOException {
		if ((destination.getParentFile() != null) && (!(destination.getParentFile().exists()))) {
			destination.getParentFile().mkdirs();
		}

		if ((destination.exists()) && (!(destination.canWrite()))) {
			String message = "Unable to open file " + destination + " for writing.";

			throw new IOException(message);
		}

		InputStream input = source.openStream();
		try {
			FileOutputStream output = new FileOutputStream(destination);
			try {
				copy(input, output);
			} finally {
				ResourceUtil.closeOutputStream(output);
			}
		} finally {
			ResourceUtil.closeInputStream(input);
		}
	}

	/**
	 * インプットからアウトプットへ変換
	 * 
	 * @param input
	 * @param output
	 * @return カウント
	 * @throws IOException
	 */
	public static int copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[4096];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
