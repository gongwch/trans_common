package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * Item reference.
 * 
 * @author Ngoc Nguyen
 */
public class OPItemReference extends TReference {

	/** The controller */
	protected OPItemReferenceController controller;

	/**
	 * �R���X�g���N�^
	 */
	public OPItemReference() {
		this(null);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPItemReference(String title) {
		super(title);

		this.controller = createController();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return OPItemReferenceController
	 */
	public OPItemReferenceController createController() {
		return new OPItemReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return this.controller;
	}

	/**
	 * OP�A�C�e�����������̎擾
	 * 
	 * @return the search condition
	 */
	public OPItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
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
