package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �Ǘ�2�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TManagement2ReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TManagement2Reference ctrlManagement2ReferenceFrom;

	/** �I���t�B�[���h */
	public TManagement2Reference ctrlManagement2ReferenceTo;

	@Override
	public void initComponents() {
		ctrlManagement2ReferenceFrom = new TManagement2Reference();
		ctrlManagement2ReferenceTo = new TManagement2Reference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlManagement2ReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement2ReferenceTo.getSearchCondition().setCodeFrom(ctrlManagement2ReferenceFrom.getCode());
			}
		});

		ctrlManagement2ReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement2ReferenceFrom.getSearchCondition().setCodeTo(ctrlManagement2ReferenceTo.getCode());
			}
		});

	}

	@Override
	public TManagement2Reference getFieldFrom() {
		return ctrlManagement2ReferenceFrom;
	}

	@Override
	public TManagement2Reference getFieldTo() {
		return ctrlManagement2ReferenceTo;
	}

}
