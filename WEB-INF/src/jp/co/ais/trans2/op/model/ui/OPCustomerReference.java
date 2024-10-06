package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * ����挟���R���|�[�l���g
 * 
 * @author AIS
 */
public class OPCustomerReference extends TCustomerReference {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPCustomerReference(String title) {
		super(title);
	}

	/**
	 * �R���X�g���N�^
	 */
	public OPCustomerReference() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPCustomerReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public OPCustomerReference(TYPE type, String title) {
		super(type, title);
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
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	@Override
	protected OPCustomerReferenceController createController() {
		return new OPCustomerReferenceController(this);
	}

	@Override
	public OPCustomerReferenceController getController() {
		return (OPCustomerReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public CustomerSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Customer getEntity() {
		return getController().getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param bean �G���e�B�e�B
	 */
	@Override
	public void setEntity(Customer bean) {
		getController().setEntity(bean);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	@Override
	public void refleshEntity() {
		getController().refleshEntity();
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
