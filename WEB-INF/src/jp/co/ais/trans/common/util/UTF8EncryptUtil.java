package jp.co.ais.trans.common.util;

import java.io.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import org.apache.commons.codec.binary.*;

import jp.co.ais.trans.common.except.*;

/**
 * UTF8版暗号Util
 */
public class UTF8EncryptUtil {

	/** 暗号化方式 */
	protected static final String METHOD = "Blowfish";

	/** 標準キー */
	protected static final String KEY = "trans2.2";

	/** 共通鍵 */
	protected static SecretKeySpec KS = null;

	static {
		try {
			KS = new SecretKeySpec(KEY.getBytes(StringUtil.CHARSET_KIND_UTF8), METHOD);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 複数パラメータをTOKENに変換する
	 * 
	 * @param arr
	 * @return TOKEN
	 */
	public static String getToken(String[] arr) {

		// HTMLに変換する
		String token = StringUtil.toHTMLEscapeString(arr);

		// 暗号化する
		String encryptToken = encrypt(token);

		return encryptToken;
	}

	/**
	 * TOKENを複数パラメータに変換する
	 * 
	 * @param tokenString
	 * @return TOKEN
	 */
	public static String[] getArray(String tokenString) {

		// 暗号化復旧する
		String decryptToken = decrypt(tokenString);

		// 結果に変換する
		String[] result = StringUtil.toArrayFromHTMLEscapeString(decryptToken);

		return result;
	}

	// /**
	// * @param prefix
	// * @param msg
	// * @throws UnsupportedEncodingException
	// */
	// public static void debug(String prefix, String msg) throws UnsupportedEncodingException {
	// System.out.print(prefix);
	// System.out.print(" [");
	// System.out.print(msg);
	// System.out.print("]");
	// System.out.print(", UTF8.len=" + msg.getBytes(StringUtil.CHARSET_KIND_UTF8).length);
	// System.out.print(", SJIS.len=" + msg.getBytes(StringUtil.CHARSET_KIND_SJIS).length);
	// System.out.print(", ISO.len=" + msg.getBytes(StringUtil.CHARSET_KIND_ISO8859_1).length);
	// System.out.println();
	// }

	/**
	 * 標準暗号化(単純)
	 * 
	 * @param str 暗号化対象文字列
	 * @return 暗号化文字列
	 */
	public static String encrypt(String str) {
		try {
			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, KS);

			byte arr[] = Base64.encodeBase64URLSafe(str.getBytes(StringUtil.CHARSET_KIND_UTF8));
			byte encrypted[] = cipher.doFinal(arr); // ここで暗号化

			return Base64.encodeBase64URLSafeString(encrypted);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 標準複合化(単純)
	 * 
	 * @param str 複合化対象文字列
	 * @return 複合化文字列
	 */
	public static String decrypt(String str) {
		try {

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.DECRYPT_MODE, KS);

			byte arr[] = Base64.decodeBase64(str);
			byte decrypted[] = cipher.doFinal(arr); // ここで複合化

			return new String(Base64.decodeBase64(decrypted), StringUtil.CHARSET_KIND_UTF8);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}
}
