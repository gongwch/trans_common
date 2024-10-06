package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.department.Department;
import jp.co.ais.trans2.model.employee.Employee;

/**
 * ���͕���/���͎҃t�B�[���h�R���|�[�l���g
 * 
 * @author AIS
 */
public class TDepEmpReferenceUnit extends TPanel {

	/** ����t�B�[���h */
	public TDepartmentReference ctrlDepartmentReference;

	/** �Ј��t�B�[���h */
	public TEmployeeReference ctrlEmployeeReference;

	/** �R���g���[�� */
	protected TDepEmpReferenceUnitController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TDepEmpReferenceUnit() {

		initComponents();

		allocateComponents();

		controller = new TDepEmpReferenceUnitController(this);
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {
		ctrlDepartmentReference = new TDepartmentReference();
		ctrlEmployeeReference = new TEmployeeReference();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {

		setLayout(null);

		// ���͕���
		ctrlDepartmentReference.btn.setLangMessageID("C01280");
		ctrlDepartmentReference.setLocation(0, 0);
		add(ctrlDepartmentReference);

		// ���͎�
		ctrlEmployeeReference.btn.setLangMessageID("C01278");
		ctrlEmployeeReference.setLocation(0, ctrlDepartmentReference.getY() + ctrlDepartmentReference.getHeight() + 5);
		add(ctrlEmployeeReference);

		setSize(ctrlDepartmentReference.getWidth(), ctrlDepartmentReference.getHeight()
			+ ctrlEmployeeReference.getHeight() + 5);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabNo �^�u��
	 */
	public void setTabControlNo(int tabNo) {
		ctrlDepartmentReference.setTabControlNo(tabNo);
		ctrlEmployeeReference.setTabControlNo(tabNo);
	}

	/**
	 * ����R�[�h��Ԃ�
	 * 
	 * @return ����R�[�h
	 */
	public String getDepartmentCode() {
		return ctrlDepartmentReference.getCode();
	}

	/**
	 * �Ј��R�[�h��Ԃ�
	 * 
	 * @return �Ј��R�[�h
	 */
	public String getEmployeeCode() {
		return ctrlEmployeeReference.getCode();
	}

	/**
	 * �����Ԃ�
	 * 
	 * @return ����
	 */
	public Department getDepartment() {
		return ctrlDepartmentReference.getEntity();
	}

	/**
	 * �Ј���Ԃ�
	 * 
	 * @return �Ј�
	 */
	public Employee getEmployee() {
		return ctrlEmployeeReference.getEntity();
	}

}
