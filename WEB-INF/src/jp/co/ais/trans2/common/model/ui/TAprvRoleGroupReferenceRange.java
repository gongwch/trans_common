package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * ���F�������[���O���[�v�}�X�^�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TAprvRoleGroupReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TAprvRoleGroupReference ctrlFrom;

	/** �I���t�B�[���h */
	public TAprvRoleGroupReference ctrlTo;

	@Override
	public void initComponents() {
		ctrlFrom = new TAprvRoleGroupReference();
		ctrlTo = new TAprvRoleGroupReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlTo.getSearchCondition().setCodeFrom(ctrlFrom.getCode());
			}
		});

		ctrlTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlFrom.getSearchCondition().setCodeTo(ctrlTo.getCode());
			}
		});

	}

	@Override
	public TAprvRoleGroupReference getFieldFrom() {
		return ctrlFrom;
	}

	@Override
	public TAprvRoleGroupReference getFieldTo() {
		return ctrlTo;
	}

}
