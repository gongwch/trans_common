package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * Port�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class OPPortReference extends TPortReference {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPPortReference(String title) {
		super(title);
	}

	/**
	 * �R���X�g���N�^
	 */
	public OPPortReference() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPPortReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public OPPortReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * �R���g���[������
	 */
	@Override
	protected void initController() {
		// �R���g���[������
		if (controller == null) {
			controller = new OPPortReferenceController(this);
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

	@Override
	public OPPortReferenceController getController() {
		return (OPPortReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public PortSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	@Override
	public Port getEntity() {
		return getController().getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param port �G���e�B�e�B
	 */
	@Override
	public void setEntity(Port port) {
		getController().setEntity(port);
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
