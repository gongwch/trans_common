package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�[�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TUserReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -8122503842627737947L;

	/** �R���g���[�� */
	protected TUserReferenceController controller;

	/**
	 * 
	 *
	 */
	public TUserReference() {

		// �R���g���[������
		controller = createController();

	}

	/**
	 * Controller�𐶐�
	 * 
	 * @return controller
	 */
	protected TUserReferenceController createController() {
		return new TUserReferenceController(this);
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
	public UserSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public void refleshAndShowEntity() {
		this.controller.refleshEntity();
		this.controller.setEntity(getEntity());
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public User getEntity() {
		return (User) getController().getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param user ���[�U�[
	 */
	public void setEntity(User user) {
		controller.setEntity(user);
	}
}
