package jp.co.ais.trans.logic.util;

import java.math.BigDecimal;

import jp.co.ais.trans.master.entity.CMP_MST;
import jp.co.ais.trans.master.entity.CUR_MST;

/**
 * �ʉ݊��Z
 * 
 * @author nagahashi
 */
public interface CurrencyConvert {

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
	public BigDecimal getAmountToBase(BigDecimal money, double rate, CUR_MST curBase, CUR_MST curFrn, CMP_MST cmp);

	/**
	 * �O�݊��Z
	 * 
	 * @param money �M�݋��z
	 * @param rate �O�݃��[�g
	 * @param curFrn �O�ݏ��(�G���e�B�e�B)
	 * @param cmp ��Џ��(�G���e�B�e�B)
	 * @return �O�ݒʉ݂Ɋ��Z�������z
	 */
	public BigDecimal getAmountToForeign(BigDecimal money, double rate, CUR_MST curFrn, CMP_MST cmp);
}
