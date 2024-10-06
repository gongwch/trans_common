package jp.co.ais.trans.logic.util;

import java.math.BigDecimal;

import jp.co.ais.trans.master.entity.CMP_MST;
import jp.co.ais.trans.master.entity.CUR_MST;

/**
 * 通貨換算
 * 
 * @author nagahashi
 */
public interface CurrencyConvert {

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
	public BigDecimal getAmountToBase(BigDecimal money, double rate, CUR_MST curBase, CUR_MST curFrn, CMP_MST cmp);

	/**
	 * 外貨換算
	 * 
	 * @param money 邦貨金額
	 * @param rate 外貨レート
	 * @param curFrn 外貨情報(エンティティ)
	 * @param cmp 会社情報(エンティティ)
	 * @return 外貨通貨に換算した金額
	 */
	public BigDecimal getAmountToForeign(BigDecimal money, double rate, CUR_MST curFrn, CMP_MST cmp);
}
