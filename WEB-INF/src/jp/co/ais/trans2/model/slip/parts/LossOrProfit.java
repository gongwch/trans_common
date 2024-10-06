package jp.co.ais.trans2.model.slip.parts;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 差損益
 */
public class LossOrProfit extends TModel {

	/** 為替差損 情報 */
	protected AutoJornalAccount loss;

	/** 為替差益 情報 */
	protected AutoJornalAccount profit;

	/**
	 * @see jp.co.ais.trans2.model.TModel#setCompany(jp.co.ais.trans2.model.company.Company)
	 */
	@Override
	public void setCompany(Company company) {
		super.setCompany(company);

		if (company == null) {
			return;
		}

		loss = getAutoItem(AutoJornalAccountType.EXCHANGE_LOSE);
		profit = getAutoItem(AutoJornalAccountType.EXCHANGE_GAIN);
	}

	/**
	 * 為替差損益の仕訳追加(基軸通貨=調整区分:両方、機能通貨=調整区分:IFRS)
	 * 
	 * @param currencyType 通貨タイプ
	 * @param slip 追加予定の伝票
	 */
	public void addLossOrProfit(Slip slip, CurrencyType currencyType) {
		addLossOrProfit(slip, currencyType, currencyType == CurrencyType.KEY ? AccountBook.BOTH : AccountBook.IFRS);
	}

	/**
	 * 為替差損益の仕訳追加
	 * 
	 * @param slip 追加予定の伝票
	 * @param currencyType 通貨タイプ
	 * @param accountBook 調整区分
	 */
	public void addLossOrProfit(Slip slip, CurrencyType currencyType, AccountBook accountBook) {
		BigDecimal sa = BigDecimal.ZERO;

		for (SWK_DTL dtl : slip.getDetails()) {
			sa = sa.add(dtl.isDR() ? dtl.getSWK_KIN() : dtl.getSWK_KIN().negate());
		}

		if (sa.compareTo(BigDecimal.ZERO) == 0) {
			return; // 差なし
		}

		boolean isDR = sa.compareTo(BigDecimal.ZERO) < 0;

		SWK_DTL fdtl = slip.createDetail();
		fdtl.setCurrencyType(currencyType);
		fdtl.setAccountBook(accountBook);

		// 差益、差損科目をセット
		AutoJornalAccount aja = isDR ? loss : profit;
		fdtl.setSWK_KMK_CODE(aja.getItemCode());
		fdtl.setSWK_KMK_NAME(aja.getItemName());
		fdtl.setSWK_HKM_CODE(aja.getSubItemCode());
		fdtl.setSWK_HKM_NAME(aja.getSubItemName());
		fdtl.setSWK_UKM_CODE(aja.getDetailItemCode());
		fdtl.setSWK_UKM_NAME(aja.getDetailItemName());
		fdtl.setSWK_DEP_CODE(aja.getDepertmentCode());
		fdtl.setSWK_DEP_NAME(aja.getDepertmentName());
		fdtl.setAppropriateCompany(getCompany()); // 計上会社
		fdtl.setDC(isDR ? Dc.DEBIT : Dc.CREDIT); // 貸借

		// 通貨
		Currency currency;
		switch (currencyType) {
			case FUNCTIONAL:
				currency = getCompany().getAccountConfig().getFunctionalCurrency();
				break;

			default:
				currency = getCompany().getAccountConfig().getKeyCurrency();
				break;
		}

		fdtl.setSWK_CUR_CODE(currency.getCode()); // 通貨コード
		fdtl.setSWK_IN_KIN(sa.abs()); // 入力金額
		fdtl.setSWK_CUR_RATE(BigDecimal.ONE); // レート1
		fdtl.setSWK_KIN(sa.abs()); // 基軸金額
		fdtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // 非課税
		fdtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO); // 消費税額(外貨)
		fdtl.setSWK_ZEI_KIN(BigDecimal.ZERO); // 消費税額
		fdtl.setHAS_DATE(slip.getSlipDate()); // 発生日
		fdtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO); // 自動
		fdtl.setSWK_GYO_TEK(slip.getRemarks()); // 行摘要

		slip.addDetail(fdtl);
	}

	/**
	 * 指定仕訳が為替差損益仕訳かどうか判定
	 * 
	 * @param dtl 仕訳
	 * @return true:為替差損益仕訳
	 */
	public boolean isProfitOrLoss(SWK_DTL dtl) {
		boolean isProfit = profit.equalsItem(dtl.getSWK_KMK_CODE(), dtl.getSWK_HKM_CODE(), dtl.getSWK_UKM_CODE());
		boolean isLoss = loss.equalsItem(dtl.getSWK_KMK_CODE(), dtl.getSWK_HKM_CODE(), dtl.getSWK_UKM_CODE());

		return isProfit || isLoss;
	}

	/**
	 * 自動仕訳科目を取得する.
	 * 
	 * @param kmkCnt 科目制御区分
	 * @return 自動仕訳科目
	 */
	protected AutoJornalAccount getAutoItem(AutoJornalAccountType kmkCnt) {
		try {
			SlipManager manager = (SlipManager) getComponent(SlipManager.class);

			String companyCode = getCompanyCode();
			AutoJornalAccount bean = manager.getAutoJornalAccount(companyCode, kmkCnt);

			if (bean == null) {
				throw new TRuntimeException("I00160", companyCode);//会社[{0}]の自動仕訳科目:為替差損益が設定されていません。
			}

			return bean;

		} catch (TException ex) {
			throw new TRuntimeException(ex);
		}
	}
}
