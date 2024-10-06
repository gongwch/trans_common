package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �A�����ђn��͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TMlitCountryReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TMlitCountryReference ctrlCountryReferenceFrom;

	/** �I���t�B�[���h */
	public TMlitCountryReference ctrlCountryReferenceTo;

	@Override
	public void initComponents() {
		ctrlCountryReferenceFrom = new TMlitCountryReference();
		ctrlCountryReferenceTo = new TMlitCountryReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlCountryReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlCountryReferenceTo.getSearchCondition().setCountryCodeFrom(ctrlCountryReferenceFrom.getCode());
			}
		});

		ctrlCountryReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlCountryReferenceFrom.getSearchCondition().setCountryCodeTo(ctrlCountryReferenceTo.getCode());
			}
		});
	}

	@Override
	public TReference getFieldFrom() {
		return ctrlCountryReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlCountryReferenceTo;
	}

}
