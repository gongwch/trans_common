package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �����d��Ȗڔ͈͌����t�B�[���h
 * @author AIS
 *
 */
public class TAutomaticJournalizingItemReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TAutomaticJournalizingItemReference ctrlReferenceFrom;

	/** �I���t�B�[���h */
	public TAutomaticJournalizingItemReference ctrlReferenceTo;

	@Override
	public void initComponents() {
		ctrlReferenceFrom = new TAutomaticJournalizingItemReference();
		ctrlReferenceTo = new TAutomaticJournalizingItemReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlReferenceTo.getSearchCondition().setCodeFrom(
						ctrlReferenceFrom.getCode());
			}
		});

		ctrlReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlReferenceFrom.getSearchCondition().setCodeTo(
						ctrlReferenceTo.getCode());
			}
		});

	}

	@Override
	public TAutomaticJournalizingItemReference getFieldFrom() {
		return ctrlReferenceFrom;
	}

	@Override
	public TAutomaticJournalizingItemReference getFieldTo() {
		return ctrlReferenceTo;
	}

}
