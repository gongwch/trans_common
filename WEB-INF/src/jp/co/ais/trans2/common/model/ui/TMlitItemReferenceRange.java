package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �A�����уA�C�e���͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TMlitItemReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TMlitItemReference ctrlItemReferenceFrom;

	/** �I���t�B�[���h */
	public TMlitItemReference ctrlItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlItemReferenceFrom = new TMlitItemReference();
		ctrlItemReferenceTo = new TMlitItemReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemReferenceTo.getSearchCondition().setItemCodeFrom(ctrlItemReferenceFrom.getCode());
			}
		});

		ctrlItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemReferenceFrom.getSearchCondition().setItemCodeTo(ctrlItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlItemReferenceTo;
	}

}
