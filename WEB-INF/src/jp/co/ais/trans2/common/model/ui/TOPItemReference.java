package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * SA�A�C�e�������R���|�[�l���g
 * 
 * @author AIS
 */
public class TOPItemReference extends TReference {

	/** �R���g���[�� */
	protected TOPItemReferenceController controller;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TOPItemReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 */
	public TOPItemReference() {
		super();
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TOPItemReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public TOPItemReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	protected TOPItemReferenceController createController() {
		return new TOPItemReferenceController(this);
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
	public OPItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public OPItem getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param bean �G���e�B�e�B
	 */
	public void setEntity(OPItem bean) {
		controller.setEntity(bean);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}
