package jp.co.ais.trans2.common.file;

import java.io.*;
import java.nio.charset.*;

import org.mozilla.universalchardet.*;

/**
 * 
 */
public class FileCharsetDetector {

	/**
	 * キャラクタセットを取得する
	 * @param file
	 * @return キャラクタセット
	 * @throws IOException
	 */
	public static Charset getCharset(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return getCharset(is);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

	/**
	 * キャラクタセットを取得する
	 * @param is
	 * @return キャラクタセット
	 * @throws IOException
	 */
	public static Charset getCharset(InputStream is) throws IOException {
		//4kbのメモリバッファを確保する
		byte[] buf = new byte[4096];
		UniversalDetector detector = new UniversalDetector(null);

		//文字コードの推測結果が得られるまでInputStreamを読み進める
		int nread;
		while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}

		//推測結果を取得する
		detector.dataEnd();
		final String detectedCharset = detector.getDetectedCharset();

		detector.reset();

		if (detectedCharset != null) {
			return Charset.forName(detectedCharset);
		}
		//文字コードを取得できなかった場合、環境のデフォルトを使用する
		return Charset.forName(System.getProperty("file.encoding"));
	}
}
