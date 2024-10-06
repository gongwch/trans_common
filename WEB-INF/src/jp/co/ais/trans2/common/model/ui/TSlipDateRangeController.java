package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.TPanelBusiness;
import jp.co.ais.trans2.common.client.TController;

/**
 * 伝票日付範囲フィールドのコントローラ
 * 
 * @author AIS
 */
public class TSlipDateRangeController extends TController {

	/** 伝票日付範囲フィールド */
	protected TSlipDateRange slipDateRange;

	/**
	 * @param slipDateRange 伝票日付範囲フィールド
	 */
	public TSlipDateRangeController(TSlipDateRange slipDateRange) {
		this.slipDateRange = slipDateRange;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		//
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 年度跨ぎをチェックする
	 * 
	 * @return true:年度を跨いでいる
	 */
	public boolean isOverFiscalYear() {

		// 年度跨ぎチェック
		int yearFrom = getCompany().getFiscalPeriod().getFiscalYear(slipDateRange.dateFrom.getValue());
		int yearTo = getCompany().getFiscalPeriod().getFiscalYear(slipDateRange.dateTo.getValue());
		if (yearFrom != yearTo) {
			return true;
		}

		return false;

	}

}
