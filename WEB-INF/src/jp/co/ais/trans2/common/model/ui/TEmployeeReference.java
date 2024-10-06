package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * �Ј��̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TEmployeeReference extends TReference {

	/** �R���g���[�� */
	protected TEmployeeReferenceController controller;

	/** ���͕���R�[�h */
	protected String inputDepartmentCode;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public TEmployeeReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 */
	public TEmployeeReference() {
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TEmployeeReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public TEmployeeReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	public TEmployeeReferenceController createController() {
		return new TEmployeeReferenceController(this);
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
	public EmployeeSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Employee getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param employee �Ј�
	 */
	public void setEntity(Employee employee) {
		controller.setEntity(employee);
	}

	/**
	 * �`�[�̎Q�ƌ������Z�b�g����B<br>
	 * �����ɂ���ĎЈ��t�B�[���h���䂷��B
	 * 
	 * @param slipRole �`�[�Q�ƌ���
	 * @param employee �Ј�
	 */
	public void setSlipRole(SlipRole slipRole, Employee employee) {
		controller.setSlipRole(slipRole, employee);
	}

	/**
	 * ���͕�����Z�b�g����
	 * 
	 * @param inputDepartmentCode ���͕���
	 */
	public void setInputDepartmentCode(String inputDepartmentCode) {
		this.inputDepartmentCode = inputDepartmentCode;
	}

	/**
	 * ���͕����Ԃ�
	 * 
	 * @return inputDepartmentCode ���͕���
	 */
	public String getInputDepartmentCode() {
		return this.inputDepartmentCode;
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

	/**
	 * true:�SSPC���[�h�̎擾
	 * 
	 * @return allCompanyMode true:�SSPC���[�h
	 */
	public boolean isAllCompanyMode() {
		return allCompanyMode;
	}

	/**
	 * true:�SSPC���[�h�̐ݒ�
	 * 
	 * @param allCompanyMode true:�SSPC���[�h
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		this.allCompanyMode = allCompanyMode;

		if (allCompanyMode) {
			getSearchCondition().setCompanyCode(null);
		} else {
			getSearchCondition().setCompanyCode(TLoginInfo.getCompany().getCode());
		}
	}

}
