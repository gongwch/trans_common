package jp.co.ais.trans.common.util;

/**
 * Boolean�ϊ����[�e�B���e�B
 */
public final class BooleanUtil {

	/**
	 * true�Ȃ��1�Afalse�Ȃ��0�𕶎���ŕԂ�
	 * 
	 * @param boo
	 * @return 0 or 1
	 */
	public static String toString(boolean boo) {
		return String.valueOf(BooleanUtil.toInt(boo));
	}

	/**
	 * Boolean�̐��l�ϊ�
	 * 
	 * @param flag true or false
	 * @return true�Ȃ�1�Afalse�Ȃ�0���Ԃ�.
	 */
	public static int toInt(boolean flag) {
		return flag ? 1 : 0;
	}

	/**
	 * ���l��Boolean�ϊ�
	 * 
	 * @param number ���l(0 or 1)
	 * @return 0�Ȃ�false�A1�Ȃ�true���Ԃ�.
	 */
	public static boolean toBoolean(int number) {
		return (number == 1);
	}

	/**
	 * Boolean�ϊ�
	 * 
	 * @param str ������
	 * @return 0�Ȃ�false�A1�Ȃ�true���Ԃ�.�����Ŗ����ꍇ�́A�����ɏ]��.
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
