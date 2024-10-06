package jp.co.ais.trans2.model.calc;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * 計算ロジック
 */
public class TCalculator {

	/**
	 * 外貨を基軸通貨に換算
	 * 
	 * @param param 換算パラメータ
	 * @return 基軸通貨金額
	 */
	public BigDecimal exchangeKeyAmount(TExchangeAmount param) {

		BigDecimal rate = param.getRate(); // 外貨レート
		BigDecimal foreignMoney = param.getForeignAmount(); // 外貨金額

		if (DecimalUtil.isNullOrZero(rate) || DecimalUtil.isNullOrZero(foreignMoney)) {
			return BigDecimal.ZERO;
		}

		// レート換算端数処理
		ExchangeFraction exchangeFraction = param.getExchangeFraction();

		if (exchangeFraction == null) {
			// レート換算端数処理が指定されていません。
			throw new TRuntimeException("I00117");
		}

		BigDecimal ratePow = BigDecimal.ONE.scaleByPowerOfTen(param.getRatePow()); // レート係数
		int decimalPoint = param.getDigit(); // 小数点桁数

		// 戻り値
		BigDecimal amount = BigDecimal.ZERO;

		// 換算区分が掛算の場合
		if (param.getConvertType() == ConvertType.MULTIPLICATION) {

			// 乗算処理
			amount = foreignMoney.multiply(rate.multiply(ratePow));

			// 端数処理を行い、値を返す
			switch (exchangeFraction) {
				case TRUNCATE: // 切捨
					return DecimalUtil.truncateNum(amount, decimalPoint);

				case ROUND_UP: // 切上
					return DecimalUtil.ceilingNum(amount, decimalPoint);

				case ROUND_OFF: // 四捨五入
					return DecimalUtil.roundNum(amount, decimalPoint);

				default:
					return amount;
			}

		} else {
			// 絶対値取得
			amount = foreignMoney.abs();

			// 端数処理と除算処理を一緒に行なう
			switch (exchangeFraction) {
				case TRUNCATE:
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.FLOOR);
					break;

				case ROUND_UP: // 切上
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.CEILING);
					break;

				default: // 四捨五入
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.HALF_UP);
					break;
			}

			// 負数の場合、正数に
			return (foreignMoney.signum() == -1) ? amount.negate() : amount;
		}
	}

	/**
	 * 基軸通貨金額を外貨に換算
	 * 
	 * @param rate レート
	 * @param keyAmount 基軸通貨金額
	 * @param currency 通貨
	 * @param convertType 変換タイプ
	 * @param exchangeFraction 端数処理
	 * @return 外貨金額
	 */
	public BigDecimal exchangeForeignAmount(BigDecimal rate, BigDecimal keyAmount, Currency currency,
		ConvertType convertType, ExchangeFraction exchangeFraction) {

		TExchangeAmount param = new TExchangeAmount();
		param.setRate(rate);
		param.setKeyAmount(keyAmount);
		param.setDigit(currency.getDecimalPoint());
		param.setConvertType(convertType);
		param.setExchangeFraction(exchangeFraction);

		return exchangeForeignAmount(param);
	}

	/**
	 * 基軸通貨金額を外貨に換算
	 * 
	 * @param param 換算パラメータ
	 * @return 外貨金額
	 */
	public BigDecimal exchangeForeignAmount(TExchangeAmount param) {

		BigDecimal rate = param.getRate();
		BigDecimal keyAmount = param.getKeyAmount();

		if (DecimalUtil.isNullOrZero(rate) || DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		// レート換算端数処理
		ExchangeFraction exchangeFraction = param.getExchangeFraction();

		if (exchangeFraction == null) {
			// レート換算端数処理が指定されていません。
			throw new TRuntimeException("I00117");
		}

		BigDecimal ratePow = BigDecimal.ONE.scaleByPowerOfTen(param.getRatePow());
		int decimalPoint = param.getDigit(); // 小数点桁数
		ConvertType convertType = param.getConvertType();

		// 戻り値
		BigDecimal amount = BigDecimal.ZERO;

		// 換算区分が掛算の場合は逆なので割算
		if (convertType == ConvertType.MULTIPLICATION) {

			// 絶対値取得
			amount = keyAmount.abs();

			// 端数処理と除算処理を一緒に行なう
			switch (exchangeFraction) {
				case TRUNCATE:
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.FLOOR);
					break;

				case ROUND_UP: // 切上
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.CEILING);
					break;

				default: // 四捨五入
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.HALF_UP);
					break;
			}

			// 負数の場合、正数に
			return (keyAmount.signum() == -1) ? amount.negate() : amount;

			// 換算区分が割算の場合は逆なので掛算
		} else {

			// 乗算処理
			amount = keyAmount.multiply(rate.multiply(ratePow));

			// 端数処理を行い、値を返す
			switch (exchangeFraction) {
				case TRUNCATE: // 切捨
					return DecimalUtil.truncateNum(amount, decimalPoint);

				case ROUND_UP: // 切上
					return DecimalUtil.ceilingNum(amount, decimalPoint);

				case ROUND_OFF: // 四捨五入
					return DecimalUtil.roundNum(amount, decimalPoint);

				default:
					return amount;
			}
		}
	}

	/**
	 * 消費税計算<br>
	 * 税 = 金額 × 税率 ÷ 100(内税) or 100 + RATE(外税)
	 * 
	 * @param param パラメータ
	 * @return 内税
	 */
	public BigDecimal calculateTax(TTaxCalculation param) {

		BigDecimal amount = param.getAmount(); // 対象金額
		BigDecimal taxRate = param.getTax().getRate(); // 税レート

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		// 符号
		int signum = amount.signum();

		// まず絶対値に
		amount = amount.abs();

		// 掛算
		amount = amount.multiply(taxRate);

		// 割る数値
		BigDecimal divideNumber = param.isInside() ? DecimalUtil.HUNDRED.add(taxRate) : DecimalUtil.HUNDRED;

		// 端数計算の種類を特定
		ExchangeFraction fraction = null;
		switch (param.getTax().getTaxType()) {
			case SALES:
				fraction = param.getReceivingFunction(); // 売上課税(仮受消費税端数処理)
				break;

			case PURCHAESE:
				fraction = param.getPaymentFunction(); // 仕入課税(仮払消費税端数処理)
				break;
				// 対象外の場合はZEROを返す
			case NOT:
				return BigDecimal.ZERO;
		}

		switch (fraction) {
			case TRUNCATE: // 切捨て
				amount = amount.divide(divideNumber, param.getDigit(), RoundingMode.FLOOR);
				break;

			case ROUND_UP: // 切上
				amount = amount.divide(divideNumber, param.getDigit(), RoundingMode.CEILING);
				break;

			default: // 四捨五入
				amount = amount.divide(divideNumber, param.getDigit(), RoundingMode.HALF_UP);
				break;
		}

		// マイナスの場合は符号反転して返す
		return (signum == -1) ? amount.negate() : amount;
	}
}
