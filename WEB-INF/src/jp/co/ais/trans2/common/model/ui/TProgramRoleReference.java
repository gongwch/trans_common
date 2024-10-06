package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.security.*;

/**
 * �v���O�������[���}�X�^�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TProgramRoleReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** �R���g���[�� */
	protected TProgramRoleReferenceController controller;

	/**
	 * 
	 *
	 */
	public TProgramRoleReference() {
		controller = new TProgramRoleReferenceController(this);
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
	public ProgramRoleSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public ProgramRole getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param programRole �v���O�������[��
	 */
	public void setEntity(ProgramRole programRole) {
		controller.setEntity(programRole);
	}

}
