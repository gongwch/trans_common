package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �E�v�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TRemarkReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TRemarkReference ctrlRemReferenceFrom;

	/** �I���t�B�[���h */
	public TRemarkReference ctrlRemReferenceTo;

	@Override
	public void initComponents() {
		ctrlRemReferenceFrom = new TRemarkReference();
		ctrlRemReferenceTo = new TRemarkReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlRemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRemReferenceTo.getSearchCondition().setCodeFrom(ctrlRemReferenceFrom.getCode());
			}
		});

		ctrlRemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRemReferenceFrom.getSearchCondition().setCodeTo(ctrlRemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlRemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlRemReferenceTo;
	}

}
