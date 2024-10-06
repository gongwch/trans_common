package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.country.*;

/**
 * �������R���|�[�l���g
 */
public class OPCountryReference extends TCountryReference {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPCountryReference(String title) {
		super(title);
	}

	/**
	 * �R���X�g���N�^
	 */
	public OPCountryReference() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPCountryReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public OPCountryReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * �R���g���[������
	 */
	@Override
	protected TCountryReferenceController createController() {
		// �R���g���[������
		return new OPCountryReferenceController(this);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	@Override
	protected void initComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			// �����I�Ƀ��x���ɂ���
			this.type = TYPE.LABEL;
		}

		super.initComponents();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			OPGuiUtil.allocateComponents(this);
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * @return true:���x�����[�h
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	/**
	 * �R���g���[���̃t�@�N�g��
	 */
	@Override
	public OPCountryReferenceController getController() {
		return (OPCountryReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public CountrySearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	@Override
	public Country getEntity() {
		return getController().getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param vessel �G���e�B�e�B
	 */
	@Override
	public void setEntity(Country vessel) {
		getController().setEntity(vessel);
	}
}
