package jp.co.ais.trans.logic.util;

import java.math.*;

import jp.co.ais.trans.common.util.DecimalUtil;
import jp.co.ais.trans.master.entity.CMP_MST;
import jp.co.ais.trans.master.entity.CUR_MST;

/**
 * 通貨換算実装
 * 
 * @author nagahashi
 */
public class CurrencyConvertImpl implements CurrencyConvert {

	/**
	 * 基軸通貨換算
	 * 
	 * @param money 外貨金額
	 * @param rate 外貨レート
	 * @param curBase 基軸通貨情報(エンティティ)
	 * @param curFrn 外貨情報(エンティティ)
	 * @param cmp 会社情報(エンティティ)
	 * @return 基軸通貨に換算した金額
	 */
	public BigDecimal getAmountToBase(BigDecimal money, double rate, CUR_MST curBase, CUR_MST curFrn, CMP_MST cmp) {

		if (rate == 0) {
			return BigDecimal.ZERO;
		}

		BigDecimal rateBd = new BigDecimal(String.valueOf(rate));
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal ratePow = BigDecimal.ONE.scaleByPowerOfTen(curBase.getRATE_POW());

		switch (cmp.getCONV_KBN()) {
			case 0:

				// 乗算処理
				amount = money.multiply(rateBd.multiply(ratePow));

				// 端数処理を行い、値を返す
				switch (cmp.getRATE_KBN()) {
					case 0:
						return DecimalUtil.truncateNum(amount, curBase.getDEC_KETA());
					default:
						return DecimalUtil.roundNum(amount, curBase.getDEC_KETA());
				}

			default:

				// 絶対値取得
				amount = money.abs();

				// 端数処理と除算処理を一緒に行なう
				switch (cmp.getRATE_KBN()) {
					case 0:
						amount = amount.divide(rateBd.multiply(ratePow), curBase.getDEC_KETA(), RoundingMode.FLOOR);
						break;
					default:
						amount = amount.divide(rateBd.multiply(ratePow), curBase.getDEC_KETA(), RoundingMode.HALF_UP);
						break;
				}

				// 負数の場合
				if (money.signum() == -1) {
					return BigDecimal.ZERO.subtract(amount);
				} else {
					return amount;
				}
		}
	}

	/**
	 * 外貨換算
	 * 
	 * @param money 邦貨金額
	 * @param rate 外貨レート
	 * @param curFrn 外貨情報(エンティティ)
	 * @param cmp 会社情報(エンティティ)
	 * @return 外貨通貨に換算した金額
	 */
	public BigDecimal getAmountToForeign(BigDecimal money, double rate, CUR_MST curFrn, CMP_MST cmp) {

		if (rate == 0) {
			return BigDecimal.ZERO;
		}

		BigDecimal rateBd = new BigDecimal(String.valueOf(rate));
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal ratePow = BigDecimal.ONE.scaleByPowerOfTen(curFrn.getRATE_POW());

		switch (cmp.getCONV_KBN()) {
			case 0:

				// 絶対値取得
				amount = money.abs();

				// 端数処理と除算処理を一緒に行なう
				switch (cmp.getRATE_KBN()) {
					case 0:
						amount = amount.divide(rateBd.multiply(ratePow), curFrn.getDEC_KETA(), RoundingMode.FLOOR);
						break;
					default:
						amount = amount.divide(rateBd.multiply(ratePow), curFrn.getDEC_KETA(), RoundingMode.HALF_UP);
						break;
				}

				// 負数の場合
				if (money.signum() == -1) {
					return BigDecimal.ZERO.subtract(amount);
				} else {
					return amount;
				}

			default:

				// 乗算処理
				amount = money.multiply(rateBd.multiply(ratePow));

				// 端数処理を行い、値を返す
				switch (cmp.getRATE_KBN()) {
					case 0:
						return DecimalUtil.truncateNum(amount, curFrn.getDEC_KETA());
					default:
						return DecimalUtil.roundNum(amount, curFrn.getDEC_KETA());
				}

		}

	}

}
