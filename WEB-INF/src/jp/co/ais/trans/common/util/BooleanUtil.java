package jp.co.ais.trans.common.util;

/**
 * Boolean変換ユーティリティ
 */
public final class BooleanUtil {

	/**
	 * trueならば1、falseならば0を文字列で返す
	 * 
	 * @param boo
	 * @return 0 or 1
	 */
	public static String toString(boolean boo) {
		return String.valueOf(BooleanUtil.toInt(boo));
	}

	/**
	 * Booleanの数値変換
	 * 
	 * @param flag true or false
	 * @return trueなら1、falseなら0が返る.
	 */
	public static int toInt(boolean flag) {
		return flag ? 1 : 0;
	}

	/**
	 * 数値のBoolean変換
	 * 
	 * @param number 数値(0 or 1)
	 * @return 0ならfalse、1ならtrueが返る.
	 */
	public static boolean toBoolean(int number) {
		return (number == 1);
	}

	/**
	 * Boolean変換
	 * 
	 * @param str 文字列
	 * @return 0ならfalse、1ならtrueが返る.数字で無い場合は、文字に従う.
	 */
	public static boolean toBoolean(String str) {

		if (Util.isNullOrEmpty(str)) {
			return false;
		}

		if (Util.isNumber(str)) {
			return toBoolean(Integer.valueOf(str).intValue());
		}

		return Boolean.valueOf(str);

	}
}
