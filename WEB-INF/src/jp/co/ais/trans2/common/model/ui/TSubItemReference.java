package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �⏕�Ȗڂ̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TSubItemReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -8638123416261093176L;

	/** �R���g���[�� */
	protected TSubItemReferenceController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TSubItemReference() {
		// �R���g���[������
		controller = createController();

	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TSubItemReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TSubItemReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public TSubItemReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return TSubItemReferenceController
	 */
	public TSubItemReferenceController createController() {
		return new TSubItemReferenceController(this);
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
	public SubItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Item getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g
	 * 
	 * @param item Entity
	 */
	public void setEntity(Item item) {
		controller.setEntity(item);
	}
}
