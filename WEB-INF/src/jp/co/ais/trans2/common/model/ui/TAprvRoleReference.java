package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * ���F�������[���}�X�^�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TAprvRoleReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** �R���g���[�� */
	protected TAprvRoleReferenceController controller;

	/**
	 * 
	 *
	 */
	public TAprvRoleReference() {

		// �R���g���[������
		controller = new TAprvRoleReferenceController(this);

	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public AprvRole getEntity() {
		return controller.getEntity();
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public AprvRoleSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entity���Z�b�g����
	 * @param aprvUser 
	 */
	public void setEntity(AprvRole aprvUser) {
		controller.setEntity(aprvUser);
	}

}
