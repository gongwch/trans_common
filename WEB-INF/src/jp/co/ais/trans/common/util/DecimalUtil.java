package jp.co.ais.trans.common.util;

import java.math.*;
import java.text.*;

/**
 * ���l�n���[�e�B���e�B
 */
public final class DecimalUtil {

	/** 100 */
	public static final BigDecimal HUNDRED = new BigDecimal("100");

	/** 60�� */
	public static final BigDecimal NUM_60 = new BigDecimal("0.6");

	/**
	 * �w��̒[�����������ŏ����������l��Ԃ��܂��B<br>
	 * �[�������������w�肳��Ă��Ȃ��ꍇ�A���̒l��Ԃ��܂��B
	 * 
	 * @param number ���l
	 * @param digits �����_�ȉ�����
	 * @param mode �[����������(RoundingMode.XX)
	 * @return �w��̒[�����������ŏ����������l
	 */
	public static BigDecimal getEvenNum(BigDecimal number, int digits, RoundingMode mode) {

		switch (mode) {
			case CEILING: // �؂�グ
				return ceilingNum(number, digits);

			case FLOOR: // �؎̂�
				return truncateNum(number, digits);

			case HALF_UP: // �l�̌ܓ�
				return roundNum(number, digits);

			default:
				return number;
		}
	}

	/**
	 * �w�蕶����BigDecimal�ϊ������ꍇ�̏����_������Ԃ�
	 * 
	 * @param value
	 * @return �����_����
	 */
	public static int getScale(String value) {
		if (Util.isNullOrEmpty(value)) {
			return 0;
		}
		return DecimalUtil.toBigDecimal(value).scale();
	}

	/**
	 * �؂�グ
	 * 
	 * @param number �Ώې��l
	 * @param digits �؂�グ���錅��
	 * @return �؂�グ���ꂽ���l
	 */
	public static BigDecimal ceilingNum(BigDecimal number, int digits) {
		if (number == null) {
			return null;
		}

		BigDecimal curCalculate = new BigDecimal(0);
		BigDecimal curAbsKin = new BigDecimal(0);

		// ��Βl���擾
		curAbsKin = number.abs();

		if (digits >= 0) {
			curCalculate = curAbsKin.setScale(digits, RoundingMode.CEILING);
		} else {
			// �����ɂ��킹�ăV�t�g���Z
			int idigits = Math.abs(digits);
			curCalculate = curAbsKin.movePointLeft(idigits);

			// �؂�グ
			curCalculate = curCalculate.setScale(0, RoundingMode.CEILING);

			// ������߂�
			curCalculate = curCalculate.movePointRight(idigits);
		}

		// �����̏ꍇ
		if (number.signum() == -1) {
			BigDecimal bd2 = new BigDecimal(0);
			curCalculate = bd2.subtract(curCalculate);
		}

		return curCalculate;
	}

	/**
	 * �l�̌ܓ����s��
	 * 
	 * @param number �Ώې��l
	 * @param digits �l�̌ܓ����錅��
	 * @return �l�̌ܓ����ꂽ���l
	 */
	public static BigDecimal roundNum(BigDecimal number, int digits) {
		if (number == null) {
			return null;
		}

		BigDecimal curCalculate = new BigDecimal(0);
		BigDecimal curAbsKin = new BigDecimal(0);

		// ��Βl���擾
		curAbsKin = number.abs();

		if (digits >= 0) {
			curCalculate = curAbsKin.setScale(digits, RoundingMode.HALF_UP);
		} else {
			// �����ɂ��킹�ăV�t�g���Z
			int idigits = Math.abs(digits);
			curCalculate = curAbsKin.movePointLeft(idigits);

			// �؎̂�
			curCalculate = curCalculate.setScale(0, RoundingMode.HALF_UP);

			// ������߂�
			curCalculate = curCalculate.movePointRight(idigits);
		}

		// �����̏ꍇ
		if (number.signum() == -1) {
			BigDecimal bd2 = new BigDecimal(0);
			curCalculate = bd2.subtract(curCalculate);
		}

		return curCalculate;
	}

	/**
	 * �؂�̂�
	 * 
	 * @param number �Ώې��l
	 * @param digits �؎̂Ă��錅��
	 * @return �؎̂Ă��ꂽ���l
	 */
	public static BigDecimal truncateNum(BigDecimal number, int digits) {
		if (number == null) {
			return null;
		}

		BigDecimal curCalculate = new BigDecimal(0);
		BigDecimal curAbsKin = new BigDecimal(0);

		// ��Βl���擾
		curAbsKin = number.abs();

		if (digits >= 0) {
			curCalculate = curAbsKin.setScale(digits, RoundingMode.FLOOR);
		} else {
			// �����ɂ��킹�ăV�t�g���Z
			int idigits = Math.abs(digits);
			curCalculate = curAbsKin.movePointLeft(idigits);

			// �؎̂�
			curCalculate = curCalculate.setScale(0, RoundingMode.FLOOR);

			// ������߂�
			curCalculate = curCalculate.movePointRight(idigits);
		}

		// �����̏ꍇ
		if (number.signum() == -1) {
			BigDecimal bd2 = new BigDecimal(0);
			curCalculate = bd2.subtract(curCalculate);
		}

		return curCalculate;
	}

	/**
	 * �؂�グ���Z
	 * 
	 * @param number �Ώې��l
	 * @param denomi ���鐔�l
	 * @param digits �؂�グ���錅��
	 * @return �؂�グ���Z���ꂽ���l
	 */
	public static BigDecimal divideCeiling(BigDecimal number, BigDecimal denomi, int digits) {

		int signum = number.signum(); // ����
		BigDecimal absnum = number.abs(); // �܂���Βl��

		absnum = absnum.divide(denomi, digits, RoundingMode.CEILING);

		return (signum == -1) ? absnum.negate() : absnum;
	}

	/**
	 * �l�̌ܓ����Z
	 * 
	 * @param number �Ώې��l
	 * @param denomi ���鐔�l
	 * @param digits �l�̌ܓ����錅��
	 * @return �l�̌ܓ����Z���ꂽ���l
	 */
	public static BigDecimal divideRound(BigDecimal number, BigDecimal denomi, int digits) {
		int signum = number.signum(); // ����
		BigDecimal absnum = number.abs(); // �܂���Βl��

		absnum = absnum.divide(denomi, digits, RoundingMode.HALF_UP);

		return (signum == -1) ? absnum.negate() : absnum;
	}

	/**
	 * �؂�̂Ċ��Z
	 * 
	 * @param number �Ώې��l
	 * @param denomi ���鐔�l
	 * @param digits �؎̂Ă��錅��
	 * @return �؎̂Ċ��Z���ꂽ���l
	 */
	public static BigDecimal divideTruncate(BigDecimal number, BigDecimal denomi, int digits) {
		int signum = number.signum(); // ����
		BigDecimal absnum = number.abs(); // �܂���Βl��

		absnum = absnum.divide(denomi, digits, RoundingMode.FLOOR);

		return (signum == -1) ? absnum.negate() : absnum;
	}

	/**
	 * �ݏ�v�Z
	 * 
	 * @param n ��
	 * @param m �w��
	 * @return �v�Z����
	 */
	public static double calculatePower(double n, int m) {
		return Math.pow(n, m);
	}

	/**
	 * Double�^��String�ɕϊ�����
	 * 
	 * @param amount
	 * @return String
	 */
	public static String doubleToString(double amount) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(6);
		numberFormat.setGroupingUsed(false);

		return numberFormat.format(amount);
	}

	/**
	 * �w�萔�l�̐������Ə������𕪗����ĕԂ�.
	 * 
	 * @param num �Ώےl
	 * @return �����z��[0:������][1:������]
	 */
	public static BigDecimal[] separateDecimal(BigDecimal num) {
		BigDecimal positive = num.setScale(0, RoundingMode.DOWN);
		BigDecimal decimal = num.subtract(positive);

		return new BigDecimal[] { positive, decimal };
	}

	/**
	 * �I�u�W�F�N�g��BigDecimal�ɕϊ�.<br>
	 * NULL�̏ꍇ�AZERO��߂�
	 * 
	 * @param obj �ΏۃI�u�W�F�N�g
	 * @return �ϊ���BigDecimal
	 */
	public static BigDecimal toBigDecimalNVL(Object obj) {
		return avoidNull(toBigDecimal(obj));
	}

	/**
	 * �I�u�W�F�N�g��BigDecimal�ɕϊ�.<br>
	 * NULL�̏ꍇ�ANULL��߂�
	 * 
	 * @param obj �ΏۃI�u�W�F�N�g
	 * @return �ϊ���BigDecimal
	 */
	public static BigDecimal toBigDecimalNULL(Object obj) {
		if (Util.isNullOrEmpty(obj)) {
			return null;
		}
		return avoidNull(toBigDecimal(obj));
	}

	/**
	 * �I�u�W�F�N�g��BigDecimal�ɕϊ�.
	 * 
	 * @param obj �ΏۃI�u�W�F�N�g
	 * @return �ϊ���BigDecimal
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		if (obj == null) {
			return null;
		}

		if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}

		if (obj instanceof String) {
			return toBigDecimal((String) obj);
		}

		return toBigDecimal(obj.toString());
	}

	/**
	 * ������̐��l��BigDecimal�ɕϊ�.<br>
	 * �J���}�͊O��
	 * 
	 * @param numStr �Ώە����l
	 * @return �ϊ���BigDecimal
	 */
	public static BigDecimal toBigDecimal(String numStr) {
		if (Util.isNullOrEmpty(numStr) || numStr.trim().equals("-")) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(numStr.replaceAll(",", "").trim());
	}

	/**
	 * ������̐��l��int�ɕϊ�.<br>
	 * �J���}�͊O��
	 * 
	 * @param numStr �Ώە����l
	 * @return �ϊ���int
	 */
	public static int toInt(String numStr) {
		if (Util.isNullOrEmpty(numStr)) {
			return 0;
		}

		return Integer.parseInt(numStr.replaceAll(",", "").trim());
	}

	/**
	 * Null���.Null�̏ꍇ�̓[����Ԃ�.
	 * 
	 * @param num �Ώې��l
	 * @return Null�̏ꍇ�ABigDecimal.ZERO
	 */
	public static BigDecimal avoidNull(BigDecimal num) {
		return num == null ? BigDecimal.ZERO : num;
	}

	/**
	 * �w�萔�l��0���ǂ����𔻒肷��. NULL�̏ꍇ��false���Ԃ�.
	 * 
	 * @param num ���l
	 * @return true:0�l
	 */
	public static boolean isZero(BigDecimal num) {
		if (num == null) {
			return false;
		}

		return BigDecimal.ZERO.compareTo(num) == 0;
	}

	/**
	 * �w�萔�l��0���ǂ����𔻒肷��. NULL�̏ꍇ��true���Ԃ�.
	 * 
	 * @param num ���l
	 * @return true:0�l
	 */
	public static boolean isNullOrZero(BigDecimal num) {
		if (num == null) {
			return true;
		}

		return isZero(num);
	}

	/**
	 * �w�蕶����0���ǂ����𔻒肷��. NULL�̏ꍇ��true���Ԃ�.
	 * 
	 * @param num ���l����
	 * @return true:0�l
	 */
	public static boolean isNullOrZero(String num) {
		if (Util.isNullOrEmpty(num)) {
			return true;
		}

		return isZero(toBigDecimal(num));
	}

	/**
	 * ���l��r(compare)���s���A�������l�Ȃ�true��Ԃ�
	 * 
	 * @param obj1 �l1
	 * @param obj2 �l2
	 * @return true:���ꐔ�l
	 */
	public static boolean equals(Object obj1, Object obj2) {

		if (obj1 == null && obj2 == null) {
			return true;
		}

		if ((obj1 == null && obj2 != null) || (obj1 != null && obj2 == null)) {
			return false;
		}

		BigDecimal num1 = toBigDecimal(obj1);
		BigDecimal num2 = toBigDecimal(obj2);

		return num1.compareTo(num2) == 0;
	}

	/**
	 * ���l���w�菬���_���܂łŊ���؂�邩�ǂ����̔���
	 * 
	 * @param num ���l
	 * @param denomi ���l
	 * @param postion �����_�ȉ�����
	 * @return true:����؂��
	 */
	public static boolean isDivisible(BigDecimal num, BigDecimal denomi, int postion) {
		BigDecimal pow = BigDecimal.TEN.pow(postion);

		return isZero(num.remainder(denomi.divide(pow)));
	}

	/**
	 * �����Ȑ��l�̎擾
	 * 
	 * @param num1
	 * @param num2
	 * @return �����Ȑ��l
	 */
	public static BigDecimal min(BigDecimal num1, BigDecimal num2) {
		if (num1 == null) {
			return num2;
		}
		if (num2 == null) {
			return num1;
		}

		if (num1.compareTo(num2) <= 0) {
			return num1;
		} else {
			return num2;
		}
	}

	/**
	 * �傫�Ȑ��l�̎擾
	 * 
	 * @param num1
	 * @param num2
	 * @return �傫�Ȑ��l
	 */
	public static BigDecimal max(BigDecimal num1, BigDecimal num2) {
		if (num1 == null) {
			return num2;
		}
		if (num2 == null) {
			return num1;
		}

		if (num1.compareTo(num2) >= 0) {
			return num1;
		} else {
			return num2;
		}
	}

	/**
	 * �������̌������擾����B
	 * 
	 * @param val
	 * @return �������̌���
	 */
	public static int getIntLength(BigDecimal val) {
		int i = val.precision() - val.scale();
		return i;
	}

	/**
	 * 60�i�@�֕ϊ�<br>
	 * �����_�ȉ�����<br>
	 * ��j1.5��1.30, 1.75��1.45
	 * 
	 * @param n10 10�i�@
	 * @return 60�i�@
	 */
	public static BigDecimal convert10to60(Number n10) {
		if (n10 == null) {
			return null;
		}

		BigDecimal value = new BigDecimal(n10.toString());

		BigDecimal nums[] = DecimalUtil.separateDecimal(value);
		BigDecimal n60 = nums[0].add(nums[1].multiply(NUM_60));
		return n60;
	}

	/**
	 * 10�i�@�֕ϊ�<br>
	 * �����_�ȉ�����<br>
	 * ��j1.30��1.5, 1.45��1.75
	 * 
	 * @param n60 60�i�@
	 * @return 10�i�@
	 */
	public static BigDecimal convert60to10(Number n60) {
		if (n60 == null) {
			return null;
		}

		BigDecimal value = new BigDecimal(n60.toString());

		BigDecimal nums[] = DecimalUtil.separateDecimal(value);
		BigDecimal n10 = nums[0].add(nums[1].divide(NUM_60, RoundingMode.HALF_EVEN));
		return n10;
	}

	/**
	 * @param num
	 * @return �������]
	 */
	public static BigDecimal negate(BigDecimal num) {
		return DecimalUtil.avoidNull(num).negate();
	}
	/**
	 * �v�Z(a + b)
	 * 
	 * @param a
	 * @param b
	 * @return a + b
	 */
	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).add(DecimalUtil.avoidNull(b));
	}

	/**
	 * ���l���Z
	 * 
	 * @param nums ���l
	 * @return ���v
	 */
	public static BigDecimal add(BigDecimal... nums) {
		BigDecimal total = BigDecimal.ZERO;
		if (nums.length > 0) {
			for (BigDecimal num : nums) {
				total = add(total, num);
			}
		}
		return total;
	}

	/**
	 * �v�Z(a - b)
	 * 
	 * @param a
	 * @param b
	 * @return a - b
	 */
	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).subtract(DecimalUtil.avoidNull(b));
	}

}
