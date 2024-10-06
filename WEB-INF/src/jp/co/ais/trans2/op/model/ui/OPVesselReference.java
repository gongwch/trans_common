package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vessel�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class OPVesselReference extends TVesselReference {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPVesselReference(String title) {
		super(title);
	}

	/**
	 * �R���X�g���N�^
	 */
	public OPVesselReference() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPVesselReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public OPVesselReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * �R���g���[������
	 */
	@Override
	protected void initController() {
		// �R���g���[������
		if (controller == null) {
			controller = new OPVesselReferenceController(this);
		}
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
	public OPVesselReferenceController getController() {
		return (OPVesselReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public VesselSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	@Override
	public Vessel getEntity() {
		return getController().getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param vessel �G���e�B�e�B
	 */
	@Override
	public void setEntity(Vessel vessel) {
		getController().setEntity(vessel);
	}
}
