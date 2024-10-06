package jp.co.ais.trans.common.util;

import java.io.*;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import jp.co.ais.trans.common.except.*;

/**
 * 暗号化するユーティリティ
 */
public final class EncryptUtil {

	/** 暗号化方式 */
	private static final String METHOD = "Blowfish";

	/** 標準キー */
	private static final String KEY = "ais.trans";

	/**
	 * ファイルを暗号化する
	 * 
	 * @param inputPath 対象ファイル
	 * @param outputPath 暗号化後ファイル
	 * @param key 暗号化キー
	 * @throws TException
	 */
	public static void encryptProperty(String inputPath, String outputPath, String key) throws TException {

		FileInputStream fi = null;
		CipherInputStream co = null;
		FileOutputStream fo = null;

		try {

			// 入力ストリームの設定
			fi = new FileInputStream(inputPath);

			// 共通鍵の設定
			SecretKeySpec ks = new SecretKeySpec(key.getBytes(), METHOD);

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, ks);

			// ファイルの暗号化ストリーム設定
			co = new CipherInputStream(fi, cipher);

			// 出力ストリームの設定
			fo = new FileOutputStream(outputPath);

			byte[] m = new byte[8];
			int i = co.read(m);

			while (i != -1) {
				// ファイルの読み込み
				fo.write(m, 0, i);

				// ファイルの出力
				i = co.read(m);
			}

		} catch (Exception ex) {
			throw new TException(ex);

		} finally {

			// ストリームの終了
			ResourceUtil.closeInputStream(co);
			ResourceUtil.closeInputStream(fi);
			ResourceUtil.closeOutputStream(fo);
		}
	}

	/**
	 * ファイルを復号し、Propertiesに挿入
	 * 
	 * @param fis 対象ストリーム
	 * @return Properties properties
	 * @throws TException
	 */
	public static Properties decryptProperty(FileInputStream fis) throws TException {
		CipherInputStream cis = null;

		try {
			Properties properties = new Properties();

			// 共通鍵の設定
			SecretKeySpec ks = new SecretKeySpec(new byte[] { 116, 114, 97, 110, 115, 67, 111, 100, 101 }, METHOD);

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.DECRYPT_MODE, ks);

			// ファイルの復号ストリーム設定
			cis = new CipherInputStream(fis, cipher);

			// 復号したファイルをpropertiesで読み込む
			properties.load(cis);

			return properties;

		} catch (Exception ex) {
			throw new TException(ex);

		} finally {
			// ストリームを閉じる
			ResourceUtil.closeInputStream(cis);
			ResourceUtil.closeInputStream(fis);
		}
	}

	/**
	 * 標準暗号化(単純)
	 * 
	 * @param str 暗号化対象文字列
	 * @return 暗号化文字列
	 */
	public static String encrypt(String str) {
		return encrypt(KEY, str);
	}

	/**
	 * 標準複合化(単純)
	 * 
	 * @param str 複合化対象文字列
	 * @return 複合化文字列
	 */
	public static String decrypt(String str) {
		return decrypt(KEY, str);
	}

	/**
	 * 標準暗号化(単純)
	 * 
	 * @param key キー
	 * @param str 暗号化対象文字列
	 * @return 暗号化文字列
	 */
	public static String encrypt(String key, String str) {
		try {
			// 共通鍵の設定
			SecretKeySpec ks = new SecretKeySpec(key.getBytes(), METHOD);
			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, ks);

			byte encrypted[] = cipher.doFinal(str.getBytes()); // ここで暗号化

			return new String(encrypted);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 標準複合化(単純)
	 * 
	 * @param key キー
	 * @param str 複合化対象文字列
	 * @return 複合化文字列
	 */
	public static String decrypt(String key, String str) {
		try {
			// 共通鍵の設定
			SecretKeySpec ks = new SecretKeySpec(key.getBytes(), METHOD);

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.DECRYPT_MODE, ks);

			byte decrypted[] = cipher.doFinal(str.getBytes()); // ここで複合化

			return new String(decrypted);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

}
