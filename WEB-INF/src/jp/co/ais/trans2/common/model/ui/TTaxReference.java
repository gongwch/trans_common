package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * ����ł̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TTaxReference extends TReference {

	/** �R���g���[�� */
	protected TTaxReferenceController controller;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TTaxReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 */
	public TTaxReference() {
		super();
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TTaxReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public TTaxReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	protected TTaxReferenceController createController() {
		return new TTaxReferenceController(this);
	}

	@Override
	public TTaxReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public ConsumptionTaxSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public ConsumptionTax getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void setEntity(ConsumptionTax entity) {
		controller.setEntity(entity);
	}

	@Override
	protected int getCodeLength() {
		return TransUtil.TAX_CODE_LENGTH;
	}

	@Override
	protected void allocateComponents() {
		super.allocateComponents();
		code.setMaximumSize(new Dimension(30, 20));
		code.setMinimumSize(new Dimension(30, 20));
		code.setPreferredSize(new Dimension(30, 20));
		resize();
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

}
