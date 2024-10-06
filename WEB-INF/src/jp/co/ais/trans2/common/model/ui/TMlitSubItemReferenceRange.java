package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �A�����уT�u�A�C�e���͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TMlitSubItemReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TMlitSubItemReference ctrlItemReferenceFrom;

	/** �I���t�B�[���h */
	public TMlitSubItemReference ctrlItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlItemReferenceFrom = new TMlitSubItemReference();
		ctrlItemReferenceTo = new TMlitSubItemReference();
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
