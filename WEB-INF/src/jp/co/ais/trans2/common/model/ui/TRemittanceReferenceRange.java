package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �����ړI�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TRemittanceReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TRemittanceReference ctrlReferenceFrom;

	/** �I���t�B�[���h */
	public TRemittanceReference ctrlReferenceTo;

	@Override
	public void initComponents() {
		ctrlReferenceFrom = new TRemittanceReference();
		ctrlReferenceTo = new TRemittanceReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlReferenceTo.getSearchCondition().setCodeFrom(ctrlReferenceFrom.getCode());
			}
		});

		ctrlReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlReferenceFrom.getSearchCondition().setCodeTo(ctrlReferenceTo.getCode());
			}
		});
	}

	@Override
	public TRemittanceReference getFieldFrom() {
		return ctrlReferenceFrom;
	}

	@Override
	public TRemittanceReference getFieldTo() {
		return ctrlReferenceTo;
	}
}
