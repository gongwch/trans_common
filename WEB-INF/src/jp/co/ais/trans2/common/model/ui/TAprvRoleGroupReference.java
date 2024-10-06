package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * ���F�O���[�v�}�X�^�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TAprvRoleGroupReference extends TReference {

	/** �R���g���[�� */
	protected TAprvRoleGroupReferenceController controller;

	/**
	 * 
	 *
	 */
	public TAprvRoleGroupReference() {

		// �R���g���[������
		controller = new TAprvRoleGroupReferenceController(this);

	}

	@Override
	public TAprvRoleGroupReferenceController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public AprvRoleGroup getEntity() {
		return controller.getEntity();
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public AprvRoleGroupSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param entity
	 */
	public void setEntity(AprvRoleGroup entity) {
		controller.setEntity(entity);
	}

}
