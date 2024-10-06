package jp.co.ais.trans2.common.model.ui.payment;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �x�����@�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TPaymentMethodReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TPaymentMethodReference ctrlPayReferenceFrom;

	/** �I���t�B�[���h */
	public TPaymentMethodReference ctrlPayReferenceTo;

	@Override
	public void initComponents() {
		ctrlPayReferenceFrom = new TPaymentMethodReference();
		ctrlPayReferenceTo = new TPaymentMethodReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlPayReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPayReferenceTo.getSearchCondition().setCodeFrom(ctrlPayReferenceFrom.getCode());
			}
		});

		ctrlPayReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPayReferenceFrom.getSearchCondition().setCodeTo(ctrlPayReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlPayReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlPayReferenceTo;
	}

}
