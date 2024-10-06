package jp.co.ais.trans2.model.financial;

import java.math.*;

import jp.co.ais.trans.common.util.*;

/**
 * 財務共通関数
 * 
 * @author AIS YF.CONG
 */
public class FinancialUtil {

	/**
	 * PMTの計算<br>
	 * 将来価値=0d、デフォルト:終了期
	 * 
	 * @param numRate 利率
	 * @param nPeriod 期数
	 * @param numNow 現在価値
	 * @return PMT
	 */
	public static double pmt(BigDecimal numRate, int nPeriod, BigDecimal numNow) {
		double rate = DecimalUtil.avoidNull(numRate).doubleValue();
		double now = DecimalUtil.avoidNull(numNow).negate().doubleValue();

		return pmt(rate, nPeriod, now, 0d, true);
	}

	/**
	 * PPMTの計算<br>
	 * 将来価値=0d、デフォルト:終了期
	 * 
	 * @param numRate 利率
	 * @param period 現在期間
	 * @param nPeriod 期数
	 * @param numNow 現在価値
	 * @return PPMT
	 */
	public static double ppmt(BigDecimal numRate, int period, int nPeriod, BigDecimal numNow) {
		double rate = DecimalUtil.avoidNull(numRate).doubleValue();
		double now = DecimalUtil.avoidNull(numNow).negate().doubleValue();

		return ppmt(rate, period, nPeriod, now, 0d, true);
	}

	/**
	 * IPMTの計算<br>
	 * 将来価値=0d、デフォルト:終了期
	 * 
	 * @param numRate 利率
	 * @param period 現在期間
	 * @param nPeriod 期数
	 * @param numNow 現在価値
	 * @return IPMT
	 */
	public static double ipmt(BigDecimal numRate, int period, int nPeriod, BigDecimal numNow) {
		double rate = DecimalUtil.avoidNull(numRate).doubleValue();
		double now = DecimalUtil.avoidNull(numNow).negate().doubleValue();

		return ipmt(rate, period, nPeriod, now, 0d, true);
	}

	/**
	 * PPMTの計算
	 * 
	 * @param rate 利率
	 * @param nPeriod 期数
	 * @param periodValue 現在価値
	 * @param futureValue 将来価値
	 * @param endPeriod true:終了期
	 * @return PPMT
	 */
	public static double pmt(double rate, double nPeriod, double periodValue, double futureValue, boolean endPeriod) {
		double num;
		if (nPeriod == 0.0) {
			// error
			return 0.0;
		}
		if (rate == 0.0) {
			return ((-futureValue - periodValue) / nPeriod);
		}
		if (!endPeriod) {
			num = 1.0 + rate;
		} else {
			num = 1.0;
		}
		double x = rate + 1.0;
		double num2 = Math.pow(x, nPeriod);
		return (((-futureValue - (periodValue * num2)) / (num * (num2 - 1.0))) * rate);
	}

	/**
	 * IPMTの計算
	 * 
	 * @param rate 利率
	 * @param period 現在期間
	 * @param nPeriod 期数
	 * @param periodValue 現在価値
	 * @param futureValue 将来価値
	 * @param endPeriod true:終了期
	 * @return IPMT
	 */
	public static double ipmt(double rate, double period, double nPeriod, double periodValue, double futureValue,
		boolean endPeriod) {
		double num;
		if (!endPeriod) {
			num = 2.0;
		} else {
			num = 1.0;
		}
		if ((period <= 0.0) || (period >= (nPeriod + 1.0))) {
			// error
			return 0.0;
		}
		if ((!endPeriod) && (period == 1.0)) {
			return 0.0;
		}
		double pmt = pmt(rate, nPeriod, periodValue, futureValue, endPeriod);
		if (!endPeriod) {
			periodValue += pmt;
		}
		return (fv(rate, period - num, pmt, periodValue, endPeriod) * rate);
	}

	/**
	 * FVの計算
	 * 
	 * @param rate 利率
	 * @param nPeriod 期数
	 * @param payment 支払金額
	 * @param periodValue 現在価値
	 * @param endPeriod
	 * @return FV
	 */
	public static double fv(double rate, double nPeriod, double payment, double periodValue, boolean endPeriod) {
		double num;
		if (rate == 0.0) {
			return (-periodValue - (payment * nPeriod));
		}
		if (!endPeriod) {
			num = 1.0 + rate;
		} else {
			num = 1.0;
		}
		double x = 1.0 + rate;
		double num2 = Math.pow(x, nPeriod);
		double result = ((-periodValue * num2) - (((payment / rate) * num) * (num2 - 1.0)));

		return result;
	}

	/**
	 * PPMTの計算
	 * 
	 * @param rate 利率
	 * @param period 現在期間
	 * @param nPeriod 期数
	 * @param periodValue 現在価値
	 * @param futureValue 将来価値
	 * @param endPeriod true:終了期
	 * @return PPMT
	 */
	public static double ppmt(double rate, double period, double nPeriod, double periodValue, double futureValue,
		boolean endPeriod) {
		if ((period <= 0.0) || (period >= (nPeriod + 1.0))) {
			// error
			return 0.0;
		}
		double pmt = pmt(rate, nPeriod, periodValue, futureValue, endPeriod);
		double ipmt = ipmt(rate, period, nPeriod, periodValue, futureValue, endPeriod);
		return (pmt - ipmt);
	}
}
