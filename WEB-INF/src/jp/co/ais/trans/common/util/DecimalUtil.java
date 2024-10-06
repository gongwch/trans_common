package jp.co.ais.trans.common.util;

import java.math.*;
import java.text.*;

/**
 * 数値系ユーティリティ
 */
public final class DecimalUtil {

	/** 100 */
	public static final BigDecimal HUNDRED = new BigDecimal("100");

	/** 60分 */
	public static final BigDecimal NUM_60 = new BigDecimal("0.6");

	/**
	 * 指定の端数処理方式で処理した数値を返します。<br>
	 * 端数処理方式が指定されていない場合、元の値を返します。
	 * 
	 * @param number 数値
	 * @param digits 少数点以下桁数
	 * @param mode 端数処理方式(RoundingMode.XX)
	 * @return 指定の端数処理方式で処理した数値
	 */
	public static BigDecimal getEvenNum(BigDecimal number, int digits, RoundingMode mode) {

		switch (mode) {
			case CEILING: // 切り上げ
				return ceilingNum(number, digits);

			case FLOOR: // 切捨て
				return truncateNum(number, digits);

			case HALF_UP: // 四捨五入
				return roundNum(number, digits);

			default:
				return number;
		}
	}

	/**
	 * 指定文字をBigDecimal変換した場合の小数点桁数を返す
	 * 
	 * @param value
	 * @return 小数点桁数
	 */
	public static int getScale(String value) {
		if (Util.isNullOrEmpty(value)) {
			return 0;
		}
		return DecimalUtil.toBigDecimal(value).scale();
	}

	/**
	 * 切り上げ
	 * 
	 * @param number 対象数値
	 * @param digits 切り上げする桁数
	 * @return 切り上げされた数値
	 */
	public static BigDecimal ceilingNum(BigDecimal number, int digits) {
		if (number == null) {
			return null;
		}

		BigDecimal curCalculate = new BigDecimal(0);
		BigDecimal curAbsKin = new BigDecimal(0);

		// 絶対値を取得
		curAbsKin = number.abs();

		if (digits >= 0) {
			curCalculate = curAbsKin.setScale(digits, RoundingMode.CEILING);
		} else {
			// 桁数にあわせてシフト演算
			int idigits = Math.abs(digits);
			curCalculate = curAbsKin.movePointLeft(idigits);

			// 切り上げ
			curCalculate = curCalculate.setScale(0, RoundingMode.CEILING);

			// 桁数を戻す
			curCalculate = curCalculate.movePointRight(idigits);
		}

		// 負数の場合
		if (number.signum() == -1) {
			BigDecimal bd2 = new BigDecimal(0);
			curCalculate = bd2.subtract(curCalculate);
		}

		return curCalculate;
	}

	/**
	 * 四捨五入を行う
	 * 
	 * @param number 対象数値
	 * @param digits 四捨五入する桁数
	 * @return 四捨五入された数値
	 */
	public static BigDecimal roundNum(BigDecimal number, int digits) {
		if (number == null) {
			return null;
		}

		BigDecimal curCalculate = new BigDecimal(0);
		BigDecimal curAbsKin = new BigDecimal(0);

		// 絶対値を取得
		curAbsKin = number.abs();

		if (digits >= 0) {
			curCalculate = curAbsKin.setScale(digits, RoundingMode.HALF_UP);
		} else {
			// 桁数にあわせてシフト演算
			int idigits = Math.abs(digits);
			curCalculate = curAbsKin.movePointLeft(idigits);

			// 切捨て
			curCalculate = curCalculate.setScale(0, RoundingMode.HALF_UP);

			// 桁数を戻す
			curCalculate = curCalculate.movePointRight(idigits);
		}

		// 負数の場合
		if (number.signum() == -1) {
			BigDecimal bd2 = new BigDecimal(0);
			curCalculate = bd2.subtract(curCalculate);
		}

		return curCalculate;
	}

	/**
	 * 切り捨て
	 * 
	 * @param number 対象数値
	 * @param digits 切捨てする桁数
	 * @return 切捨てされた数値
	 */
	public static BigDecimal truncateNum(BigDecimal number, int digits) {
		if (number == null) {
			return null;
		}

		BigDecimal curCalculate = new BigDecimal(0);
		BigDecimal curAbsKin = new BigDecimal(0);

		// 絶対値を取得
		curAbsKin = number.abs();

		if (digits >= 0) {
			curCalculate = curAbsKin.setScale(digits, RoundingMode.FLOOR);
		} else {
			// 桁数にあわせてシフト演算
			int idigits = Math.abs(digits);
			curCalculate = curAbsKin.movePointLeft(idigits);

			// 切捨て
			curCalculate = curCalculate.setScale(0, RoundingMode.FLOOR);

			// 桁数を戻す
			curCalculate = curCalculate.movePointRight(idigits);
		}

		// 負数の場合
		if (number.signum() == -1) {
			BigDecimal bd2 = new BigDecimal(0);
			curCalculate = bd2.subtract(curCalculate);
		}

		return curCalculate;
	}

	/**
	 * 切り上げ割算
	 * 
	 * @param number 対象数値
	 * @param denomi 割る数値
	 * @param digits 切り上げする桁数
	 * @return 切り上げ割算された数値
	 */
	public static BigDecimal divideCeiling(BigDecimal number, BigDecimal denomi, int digits) {

		int signum = number.signum(); // 符号
		BigDecimal absnum = number.abs(); // まず絶対値に

		absnum = absnum.divide(denomi, digits, RoundingMode.CEILING);

		return (signum == -1) ? absnum.negate() : absnum;
	}

	/**
	 * 四捨五入割算
	 * 
	 * @param number 対象数値
	 * @param denomi 割る数値
	 * @param digits 四捨五入する桁数
	 * @return 四捨五入割算された数値
	 */
	public static BigDecimal divideRound(BigDecimal number, BigDecimal denomi, int digits) {
		int signum = number.signum(); // 符号
		BigDecimal absnum = number.abs(); // まず絶対値に

		absnum = absnum.divide(denomi, digits, RoundingMode.HALF_UP);

		return (signum == -1) ? absnum.negate() : absnum;
	}

	/**
	 * 切り捨て割算
	 * 
	 * @param number 対象数値
	 * @param denomi 割る数値
	 * @param digits 切捨てする桁数
	 * @return 切捨て割算された数値
	 */
	public static BigDecimal divideTruncate(BigDecimal number, BigDecimal denomi, int digits) {
		int signum = number.signum(); // 符号
		BigDecimal absnum = number.abs(); // まず絶対値に

		absnum = absnum.divide(denomi, digits, RoundingMode.FLOOR);

		return (signum == -1) ? absnum.negate() : absnum;
	}

	/**
	 * 累乗計算
	 * 
	 * @param n 底
	 * @param m 指数
	 * @return 計算結果
	 */
	public static double calculatePower(double n, int m) {
		return Math.pow(n, m);
	}

	/**
	 * Double型をStringに変換する
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
	 * 指定数値の整数部と小数部を分離して返す.
	 * 
	 * @param num 対象値
	 * @return 分離配列[0:整数部][1:小数部]
	 */
	public static BigDecimal[] separateDecimal(BigDecimal num) {
		BigDecimal positive = num.setScale(0, RoundingMode.DOWN);
		BigDecimal decimal = num.subtract(positive);

		return new BigDecimal[] { positive, decimal };
	}

	/**
	 * オブジェクトをBigDecimalに変換.<br>
	 * NULLの場合、ZEROを戻す
	 * 
	 * @param obj 対象オブジェクト
	 * @return 変換後BigDecimal
	 */
	public static BigDecimal toBigDecimalNVL(Object obj) {
		return avoidNull(toBigDecimal(obj));
	}

	/**
	 * オブジェクトをBigDecimalに変換.<br>
	 * NULLの場合、NULLを戻す
	 * 
	 * @param obj 対象オブジェクト
	 * @return 変換後BigDecimal
	 */
	public static BigDecimal toBigDecimalNULL(Object obj) {
		if (Util.isNullOrEmpty(obj)) {
			return null;
		}
		return avoidNull(toBigDecimal(obj));
	}

	/**
	 * オブジェクトをBigDecimalに変換.
	 * 
	 * @param obj 対象オブジェクト
	 * @return 変換後BigDecimal
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
	 * 文字列の数値をBigDecimalに変換.<br>
	 * カンマは外す
	 * 
	 * @param numStr 対象文字値
	 * @return 変換後BigDecimal
	 */
	public static BigDecimal toBigDecimal(String numStr) {
		if (Util.isNullOrEmpty(numStr) || numStr.trim().equals("-")) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(numStr.replaceAll(",", "").trim());
	}

	/**
	 * 文字列の数値をintに変換.<br>
	 * カンマは外す
	 * 
	 * @param numStr 対象文字値
	 * @return 変換後int
	 */
	public static int toInt(String numStr) {
		if (Util.isNullOrEmpty(numStr)) {
			return 0;
		}

		return Integer.parseInt(numStr.replaceAll(",", "").trim());
	}

	/**
	 * Null回避.Nullの場合はゼロを返す.
	 * 
	 * @param num 対象数値
	 * @return Nullの場合、BigDecimal.ZERO
	 */
	public static BigDecimal avoidNull(BigDecimal num) {
		return num == null ? BigDecimal.ZERO : num;
	}

	/**
	 * 指定数値が0かどうかを判定する. NULLの場合はfalseが返る.
	 * 
	 * @param num 数値
	 * @return true:0値
	 */
	public static boolean isZero(BigDecimal num) {
		if (num == null) {
			return false;
		}

		return BigDecimal.ZERO.compareTo(num) == 0;
	}

	/**
	 * 指定数値が0かどうかを判定する. NULLの場合はtrueが返る.
	 * 
	 * @param num 数値
	 * @return true:0値
	 */
	public static boolean isNullOrZero(BigDecimal num) {
		if (num == null) {
			return true;
		}

		return isZero(num);
	}

	/**
	 * 指定文字が0かどうかを判定する. NULLの場合はtrueが返る.
	 * 
	 * @param num 数値文字
	 * @return true:0値
	 */
	public static boolean isNullOrZero(String num) {
		if (Util.isNullOrEmpty(num)) {
			return true;
		}

		return isZero(toBigDecimal(num));
	}

	/**
	 * 数値比較(compare)を行い、同じ数値ならtrueを返す
	 * 
	 * @param obj1 値1
	 * @param obj2 値2
	 * @return true:同一数値
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
	 * 数値が指定小数点桁までで割り切れるかどうかの判定
	 * 
	 * @param num 数値
	 * @param denomi 割値
	 * @param postion 小数点以下桁目
	 * @return true:割り切れる
	 */
	public static boolean isDivisible(BigDecimal num, BigDecimal denomi, int postion) {
		BigDecimal pow = BigDecimal.TEN.pow(postion);

		return isZero(num.remainder(denomi.divide(pow)));
	}

	/**
	 * 小さな数値の取得
	 * 
	 * @param num1
	 * @param num2
	 * @return 小さな数値
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
	 * 大きな数値の取得
	 * 
	 * @param num1
	 * @param num2
	 * @return 大きな数値
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
	 * 整数部の桁数を取得する。
	 * 
	 * @param val
	 * @return 整数部の桁数
	 */
	public static int getIntLength(BigDecimal val) {
		int i = val.precision() - val.scale();
		return i;
	}

	/**
	 * 60進法へ変換<br>
	 * 小数点以下部分<br>
	 * 例）1.5→1.30, 1.75→1.45
	 * 
	 * @param n10 10進法
	 * @return 60進法
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
	 * 10進法へ変換<br>
	 * 小数点以下部分<br>
	 * 例）1.30→1.5, 1.45→1.75
	 * 
	 * @param n60 60進法
	 * @return 10進法
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
	 * @return 符号反転
	 */
	public static BigDecimal negate(BigDecimal num) {
		return DecimalUtil.avoidNull(num).negate();
	}
	/**
	 * 計算(a + b)
	 * 
	 * @param a
	 * @param b
	 * @return a + b
	 */
	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).add(DecimalUtil.avoidNull(b));
	}

	/**
	 * 数値加算
	 * 
	 * @param nums 数値
	 * @return 合計
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
	 * 計算(a - b)
	 * 
	 * @param a
	 * @param b
	 * @return a - b
	 */
	public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).subtract(DecimalUtil.avoidNull(b));
	}

}
