package jp.co.ais.trans.logic.util;

import java.math.*;

import jp.co.ais.trans.common.util.DecimalUtil;
import jp.co.ais.trans.master.entity.CMP_MST;
import jp.co.ais.trans.master.entity.CUR_MST;

/**
 * �ʉ݊��Z����
 * 
 * @author nagahashi
 */
public class CurrencyConvertImpl implements CurrencyConvert {

	/**
	 * ��ʉ݊��Z
	 * 
	 * @param money �O�݋��z
	 * @param rate �O�݃��[�g
	 * @param curBase ��ʉݏ��(�G���e�B�e�B)
	 * @param curFrn �O�ݏ��(�G���e�B�e�B)
	 * @param cmp ��Џ��(�G���e�B�e�B)
	 * @return ��ʉ݂Ɋ��Z�������z
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

				// ��Z����
				amount = money.multiply(rateBd.multiply(ratePow));

				// �[���������s���A�l��Ԃ�
				switch (cmp.getRATE_KBN()) {
					case 0:
						return DecimalUtil.truncateNum(amount, curBase.getDEC_KETA());
					default:
						return DecimalUtil.roundNum(amount, curBase.getDEC_KETA());
				}

			default:

				// ��Βl�擾
				amount = money.abs();

				// �[�������Ə��Z�������ꏏ�ɍs�Ȃ�
				switch (cmp.getRATE_KBN()) {
					case 0:
						amount = amount.divide(rateBd.multiply(ratePow), curBase.getDEC_KETA(), RoundingMode.FLOOR);
						break;
					default:
						amount = amount.divide(rateBd.multiply(ratePow), curBase.getDEC_KETA(), RoundingMode.HALF_UP);
						break;
				}

				// �����̏ꍇ
				if (money.signum() == -1) {
					return BigDecimal.ZERO.subtract(amount);
				} else {
					return amount;
				}
		}
	}

	/**
	 * �O�݊��Z
	 * 
	 * @param money �M�݋��z
	 * @param rate �O�݃��[�g
	 * @param curFrn �O�ݏ��(�G���e�B�e�B)
	 * @param cmp ��Џ��(�G���e�B�e�B)
	 * @return �O�ݒʉ݂Ɋ��Z�������z
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

				// ��Βl�擾
				amount = money.abs();

				// �[�������Ə��Z�������ꏏ�ɍs�Ȃ�
				switch (cmp.getRATE_KBN()) {
					case 0:
						amount = amount.divide(rateBd.multiply(ratePow), curFrn.getDEC_KETA(), RoundingMode.FLOOR);
						break;
					default:
						amount = amount.divide(rateBd.multiply(ratePow), curFrn.getDEC_KETA(), RoundingMode.HALF_UP);
						break;
				}

				// �����̏ꍇ
				if (money.signum() == -1) {
					return BigDecimal.ZERO.subtract(amount);
				} else {
					return amount;
				}

			default:

				// ��Z����
				amount = money.multiply(rateBd.multiply(ratePow));

				// �[���������s���A�l��Ԃ�
				switch (cmp.getRATE_KBN()) {
					case 0:
						return DecimalUtil.truncateNum(amount, curFrn.getDEC_KETA());
					default:
						return DecimalUtil.roundNum(amount, curFrn.getDEC_KETA());
				}

		}

	}

}
