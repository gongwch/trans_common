package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �Ǘ�5�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TManagement5ReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TManagement5Reference ctrlManagement5ReferenceFrom;

	/** �I���t�B�[���h */
	public TManagement5Reference ctrlManagement5ReferenceTo;

	@Override
	public void initComponents() {
		ctrlManagement5ReferenceFrom = new TManagement5Reference();
		ctrlManagement5ReferenceTo = new TManagement5Reference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlManagement5ReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement5ReferenceTo.getSearchCondition().setCodeFrom(ctrlManagement5ReferenceFrom.getCode());
			}
		});

		ctrlManagement5ReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement5ReferenceFrom.getSearchCondition().setCodeTo(ctrlManagement5ReferenceTo.getCode());
			}
		});

	}

	@Override
	public TManagement5Reference getFieldFrom() {
		return ctrlManagement5ReferenceFrom;
	}

	@Override
	public TManagement5Reference getFieldTo() {
		return ctrlManagement5ReferenceTo;
	}

}
