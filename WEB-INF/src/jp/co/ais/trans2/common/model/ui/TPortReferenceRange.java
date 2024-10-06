package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �`�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TPortReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TPortReference ctrlPortReferenceFrom;

	/** �I���t�B�[���h */
	public TPortReference ctrlPortReferenceTo;

	@Override
	public void initComponents() {
		ctrlPortReferenceFrom = new TPortReference();
		ctrlPortReferenceTo = new TPortReference();
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		ctrlPortReferenceFrom.setCompanyCode(companyCode);
		ctrlPortReferenceTo.setCompanyCode(companyCode);
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlPortReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPortReferenceTo.getSearchCondition().setCodeFrom(ctrlPortReferenceFrom.getCode());
			}
		});

		ctrlPortReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPortReferenceFrom.getSearchCondition().setCodeTo(ctrlPortReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlPortReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlPortReferenceTo;
	}

}
