package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * ����Ȗڂ̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TDetailItemReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -2442214843620387829L;

	/** �R���g���[�� */
	protected TDetailItemReferenceController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TDetailItemReference() {

		// �R���g���[������
		controller = createController();

	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TDetailItemReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TDetailItemReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public TDetailItemReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return TDetailItemReferenceController
	 */
	public TDetailItemReferenceController createController() {
		return new TDetailItemReferenceController(this);
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
	public DetailItemSearchCondition getSearchCondition() {
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
