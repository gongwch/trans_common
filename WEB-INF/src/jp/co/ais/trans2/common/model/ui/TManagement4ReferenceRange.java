package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * �Ǘ��S�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TManagement4ReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TManagement4Reference ctrlManagement4ReferenceFrom;

	/** �I���t�B�[���h */
	public TManagement4Reference ctrlManagement4ReferenceTo;

	@Override
	public void initComponents() {
		ctrlManagement4ReferenceFrom = new TManagement4Reference();
		ctrlManagement4ReferenceTo = new TManagement4Reference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlManagement4ReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement4ReferenceTo.getSearchCondition().setCodeFrom(ctrlManagement4ReferenceFrom.getCode());
			}
		});

		ctrlManagement4ReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement4ReferenceFrom.getSearchCondition().setCodeTo(ctrlManagement4ReferenceTo.getCode());
			}
		});

	}

	@Override
	public TManagement4Reference getFieldFrom() {
		return ctrlManagement4ReferenceFrom;
	}

	@Override
	public TManagement4Reference getFieldTo() {
		return ctrlManagement4ReferenceTo;
	}

}
