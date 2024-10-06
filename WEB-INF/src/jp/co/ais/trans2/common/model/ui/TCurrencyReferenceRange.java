package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �ʉݔ͈͌����t�B�[���h
 * 
 * @author AIS
 *
 */
public class TCurrencyReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TCurrencyReference ctrlCurrencyReferenceFrom;

	/** �I���t�B�[���h */
	public TCurrencyReference ctrlCurrencyReferenceTo;

	@Override
	public void initComponents() {
		ctrlCurrencyReferenceFrom = new TCurrencyReference();
		ctrlCurrencyReferenceTo = new TCurrencyReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlCurrencyReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCurrencyReferenceTo.getSearchCondition().setCodeFrom(ctrlCurrencyReferenceFrom.getCode());
			}
		});

		ctrlCurrencyReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCurrencyReferenceFrom.getSearchCondition().setCodeTo(ctrlCurrencyReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlCurrencyReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlCurrencyReferenceTo;
	}

}
