package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �Ȗڑ̌n&�Ȗڔ͈͌����R���g���[���[
 * 
 * @author AIS
 */
public class TItemSummaryRangeUnitController extends TController {

	/** ���C���p�l�� */
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
	 * �����`
	 */
	public void getEvent() {

		refItemSummary.ctrlItemOrgRef.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				// ��ʏ�����
				refItemSummary.ctrlItemRefFrom.clear();
				refItemSummary.ctrlItemRefTo.clear();

				// ��������������
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