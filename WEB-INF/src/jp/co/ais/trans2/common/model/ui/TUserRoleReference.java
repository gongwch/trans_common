package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�[���[���}�X�^�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TUserRoleReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** �R���g���[�� */
	protected TUserRoleReferenceController controller;

	/**
	 * 
	 *
	 */
	public TUserRoleReference() {

		// �R���g���[������
		controller = new TUserRoleReferenceController(this);

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
	public UserRole getEntity() {
		return controller.getEntity();
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public UserSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param userRole ���[�U�[���[��
	 */
	public void setEntity(UserRole userRole) {
		controller.setEntity(userRole);
	}

}
