package jp.co.ais.trans.common.util;

import java.io.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import org.apache.commons.codec.binary.*;

import jp.co.ais.trans.common.except.*;

/**
 * UTF8�ňÍ�Util
 */
public class UTF8EncryptUtil {

	/** �Í������� */
	protected static final String METHOD = "Blowfish";

	/** �W���L�[ */
	protected static final String KEY = "trans2.2";

	/** ���ʌ� */
	protected static SecretKeySpec KS = null;

	static {
		try {
			KS = new SecretKeySpec(KEY.getBytes(StringUtil.CHARSET_KIND_UTF8), METHOD);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����p�����[�^��TOKEN�ɕϊ�����
	 * 
	 * @param arr
	 * @return TOKEN
	 */
	public static String getToken(String[] arr) {

		// HTML�ɕϊ�����
		String token = StringUtil.toHTMLEscapeString(arr);

		// �Í�������
		String encryptToken = encrypt(token);

		return encryptToken;
	}

	/**
	 * TOKEN�𕡐��p�����[�^�ɕϊ�����
	 * 
	 * @param tokenString
	 * @return TOKEN
	 */
	public static String[] getArray(String tokenString) {

		// �Í�����������
		String decryptToken = decrypt(tokenString);

		// ���ʂɕϊ�����
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
	 * �W���Í���(�P��)
	 * 
	 * @param str �Í����Ώە�����
	 * @return �Í���������
	 */
	public static String encrypt(String str) {
		try {
			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, KS);

			byte arr[] = Base64.encodeBase64URLSafe(str.getBytes(StringUtil.CHARSET_KIND_UTF8));
			byte encrypted[] = cipher.doFinal(arr); // �����ňÍ���

			return Base64.encodeBase64URLSafeString(encrypted);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �W��������(�P��)
	 * 
	 * @param str �������Ώە�����
	 * @return ������������
	 */
	public static String decrypt(String str) {
		try {

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.DECRYPT_MODE, KS);

			byte arr[] = Base64.decodeBase64(str);
			byte decrypted[] = cipher.doFinal(arr); // �����ŕ�����

			return new String(Base64.decodeBase64(decrypted), StringUtil.CHARSET_KIND_UTF8);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}
}
