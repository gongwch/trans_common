package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �Ǘ�6�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TManagement6ReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TManagement6Reference ctrlManagement6ReferenceFrom;

	/** �I���t�B�[���h */
	public TManagement6Reference ctrlManagement6ReferenceTo;

	@Override
	public void initComponents() {
		ctrlManagement6ReferenceFrom = new TManagement6Reference();
		ctrlManagement6ReferenceTo = new TManagement6Reference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlManagement6ReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement6ReferenceTo.getSearchCondition().setCodeFrom(ctrlManagement6ReferenceFrom.getCode());
			}
		});

		ctrlManagement6ReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement6ReferenceFrom.getSearchCondition().setCodeTo(ctrlManagement6ReferenceTo.getCode());
			}
		});

	}

	@Override
	public TManagement6Reference getFieldFrom() {
		return ctrlManagement6ReferenceFrom;
	}

	@Override
	public TManagement6Reference getFieldTo() {
		return ctrlManagement6ReferenceTo;
	}

}
