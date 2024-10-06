package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.cargo.*;

/**
 * Cargo�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class OPCargoReference extends TCargoReference {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPCargoReference(String title) {
		super(title);
	}

	/**
	 * �R���X�g���N�^
	 */
	public OPCargoReference() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPCargoReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public OPCargoReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * �R���g���[������
	 */
	@Override
	protected TCargoReferenceController createController() {
		// �R���g���[������
		return new OPCargoReferenceController(this);
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
	public OPCargoReferenceController getController() {
		return (OPCargoReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public CargoSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	@Override
	public Cargo getEntity() {
		return getController().getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param vessel �G���e�B�e�B
	 */
	@Override
	public void setEntity(Cargo vessel) {
		getController().setEntity(vessel);
	}

	/**
	 * ����ݒ�
	 * 
	 * @param termDate
	 */
	public void setTermDate(Date termDate) {
		getController().setTermDate(termDate);
	}
}
