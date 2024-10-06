package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �Ǘ�1�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TManagement1ReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TManagement1Reference ctrlManagement1ReferenceFrom;

	/** �I���t�B�[���h */
	public TManagement1Reference ctrlManagement1ReferenceTo;

	@Override
	public void initComponents() {
		ctrlManagement1ReferenceFrom = new TManagement1Reference();
		ctrlManagement1ReferenceTo = new TManagement1Reference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlManagement1ReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement1ReferenceTo.getSearchCondition().setCodeFrom(ctrlManagement1ReferenceFrom.getCode());
			}
		});

		ctrlManagement1ReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement1ReferenceFrom.getSearchCondition().setCodeTo(ctrlManagement1ReferenceTo.getCode());
			}
		});

	}

	@Override
	public TManagement1Reference getFieldFrom() {
		return ctrlManagement1ReferenceFrom;
	}

	@Override
	public TManagement1Reference getFieldTo() {
		return ctrlManagement1ReferenceTo;
	}

}
