package jp.co.ais.trans.common.client.http;

import java.io.UnsupportedEncodingException;

/**
 * 文字エンコーディングを表すクラス.
 * <p>
 * 例1:<br>
 * エンコード名を指定してEncodingオブジェクトを生成する。<br>
 * 
 * <pre>
 * Encoding sjis = Encoding.getEncoding(&quot;Shift_JIS&quot;);
 * 
 * String unicodeString = sjis.getString(shift_jis_string);
 * </pre>
 * 
 * </p>
 * <p>
 * 例2:<br>
 * 定義済みEncodingオブジェクトを利用する。<br>
 * 
 * <pre>
 * String unicodeString = Encoding.sjis.getString(shift_jis_string);
 * </pre>
 * 
 * 上記コードは次と等価になる．<br>
 * 
 * <pre>
 * String unicodeString;
 * try {
 * 	unicodeString = new String(shift_jis_string.getByte(&quot;iso-8859-1&quot;), &quot;Shift_JIS&quot;);
 * } catch (UnsupportedEncodingException e) {
 * 	throw new IllegalArgumentException(e.getMessage());
 * }
 * </pre>
 * 
 * </p>
 */
public class Encoding {

	// /** Shift_JISを表す定義済みオブジェクト */
	// public static final Encoding sjis = getEncoding("Shift_JIS");
	// /** EUC_JPを表す定義済みオブジェクト */
	// public static final Encoding eucjp = getEncoding("EUC_JP");

	/** 文字エンコーディングの文字表現 */
	private final String encoding;

	/**
	 * privateコンストラクタ
	 * 
	 * @param enc エンコーディングの文字表現
	 */
	private Encoding(String enc) {
		this.encoding = enc;
	}

	/**
	 * Encodingオブジェクトを取得する。
	 * 
	 * @param enc
	 * @return Encodingオブジェクト
	 * @exception IllegalArgumentException <code>enc</code>がサポートされない文字コードの時
	 */
	public static Encoding getEncoding(String enc) {
		try {
			// encがサポートされない場合はUnsupportedEncodingExceptionが発生する。
			new String(new byte[0], enc);
			return new Encoding(enc);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Unicode文字列を取得する。
	 * 
	 * @param s
	 * @return Unicode文字列
	 */
	public String getString(String s) {
		if (s == null) return null;
		try {
			return new String(getRawBytes(s), this.encoding);
		} catch (UnsupportedEncodingException e) {
			// 絶対に起こらないはずの例外
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}

	/**
	 * <code>s</code>をiso-8859-1 文字列とみなして、byte配列に変換する。
	 * <p>
	 * 誤ってASCIIとして文字列して構築されたStringを元のバイト列に戻す時に利用する。
	 * </p>
	 * 
	 * @param s 変換する文字列
	 * @return 変換後バイナリ
	 */
	public static byte[] getRawBytes(String s) {
		if (s == null) {
			return null;
		}
		try {
			return s.getBytes("MS932");
			// return s.getBytes("iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			// 絶対に起こらないはずの例外
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
}
