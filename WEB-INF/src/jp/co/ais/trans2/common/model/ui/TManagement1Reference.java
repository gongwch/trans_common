package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�1�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TManagement1Reference extends TReference {

	/** �R���g���[�� */
	protected TManagement1ReferenceController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TManagement1Reference() {
		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	protected TManagement1ReferenceController createController() {
		return new TManagement1ReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public Management1SearchCondition getSearchCondition() {
		return controller.getSearchCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Management1 getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param management �Ǘ�
	 */
	public void setEntity(Management1 management) {
		controller.setEntity(management);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.<br>
	 * �\�����X�V����
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}

	/**
	 * true:�SSPC���[�h�̎擾
	 * 
	 * @return allCompanyMode true:�SSPC���[�h
	 */
	public boolean isAllCompanyMode() {
		return allCompanyMode;
	}

	/**
	 * true:�SSPC���[�h�̐ݒ�
	 * 
	 * @param allCompanyMode true:�SSPC���[�h
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		this.allCompanyMode = allCompanyMode;

		if (allCompanyMode) {
			getSearchCondition().setCompanyCode(null);
		} else {
			getSearchCondition().setCompanyCode(TLoginInfo.getCompany().getCode());
		}
	}

}
