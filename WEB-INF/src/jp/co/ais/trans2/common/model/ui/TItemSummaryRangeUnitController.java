package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 科目体系&科目範囲検索コントローラー
 * 
 * @author AIS
 */
public class TItemSummaryRangeUnitController extends TController {

	/** メインパネル */
	public TItemSummaryRangeUnit refItemSummary;

	/**
	 * @param refItemSummary
	 */
	public TItemSummaryRangeUnitController(TItemSummaryRangeUnit refItemSummary) {
		super();
		this.refItemSummary = refItemSummary;
		getEvent();
	}

	/**
	 * 制御定義
	 */
	public void getEvent() {

		refItemSummary.ctrlItemOrgRef.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				// 画面初期化
				refItemSummary.ctrlItemRefFrom.clear();
				refItemSummary.ctrlItemRefTo.clear();

				// 検索条件見直し
				refItemSummary.ctrlItemRefFrom.getSearchCondition().setCodeFrom(null);
				refItemSummary.ctrlItemRefFrom.getSearchCondition().setCodeTo(null);
				refItemSummary.ctrlItemRefFrom.getSearchCondition().setKmtCode(refItemSummary.ctrlItemOrgRef.getCode());

				refItemSummary.ctrlItemRefTo.getSearchCondition().setCodeFrom(null);
				refItemSummary.ctrlItemRefTo.getSearchCondition().setCodeTo(null);
				refItemSummary.ctrlItemRefTo.getSearchCondition().setKmtCode(refItemSummary.ctrlItemOrgRef.getCode());

			}
		});

		refItemSummary.ctrlItemRefFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				refItemSummary.ctrlItemRefTo.getSearchCondition().setCodeFrom(refItemSummary.ctrlItemRefFrom.getCode());
			}
		});

		refItemSummary.ctrlItemRefTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				refItemSummary.ctrlItemRefFrom.getSearchCondition().setCodeTo(refItemSummary.ctrlItemRefTo.getCode());
			}
		});
	}
}