package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ��I���F�Ǘ��I��+�͈͌���+�C�ӑI�� * 3�F���C��
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnit extends TTitlePanel {

	/** �Ǘ��I���t�B�[���h */
	public List<TManagementSelectUnit> ctrlManagementSelectUnits;

	/** �R���g���[�� */
	protected TManagementSelectMultiUnitController controller;

	/** �t�B�[���h���̏����l */
	protected static final int FIELD_COUNT = 3;

	/** �ێ��L�[ */
	protected String saveKey = null;

	/** ��Џo�͒P�� */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/**
	 * �R���X�g���N�^
	 */
	public TManagementSelectMultiUnit() {
		this(FIELD_COUNT);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param fieldCount
	 */
	public TManagementSelectMultiUnit(int fieldCount) {

		// �R���|�[�l���g������
		initComponents(fieldCount);

		// �R���|�[�l���g�z�u
		allocateComponents();

		// �R���g���[������
		controller = createController();
	}

	/**
	 * �R���|�[�l���g������
	 * 
	 * @param fieldCount
	 */
	public void initComponents(int fieldCount) {
		ctrlManagementSelectUnits = new ArrayList<TManagementSelectUnit>();
		for (int i = 0; i < fieldCount; i++) {
			ctrlManagementSelectUnits.add(new TManagementSelectUnit());
		}
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	public void allocateComponents() {

		setLangMessageID("C10834"); // �Ǘ��I��

		int y = 5;
		for (TManagementSelectUnit unit : ctrlManagementSelectUnits) {
			unit.setLocation(15, y);
			add(unit);
			y = y + unit.getHeight() + 5;
		}

		setSize(360, y + 25);
	}

	/**
	 * �R���g���[������
	 * 
	 * @return controller
	 */
	protected TManagementSelectMultiUnitController createController() {
		return new TManagementSelectMultiUnitController(this);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TManagementSelectUnit unit : ctrlManagementSelectUnits) {
			unit.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * �Ǘ�������Z�߂ĕԂ�
	 * 
	 * @return �Ǘ�������Z�߂ĕԂ�
	 */
	public List<ManagementAngleSearchCondition> getManagementAngleSearchConditions() {
		return controller.getManagementAngleSearchConditions();
	}

	/**
	 * �Ǘ�������ݒ肷��
	 * 
	 * @param list �Ǘ�����
	 */
	public void setManagementAngleSearchConditions(List<ManagementAngleSearchCondition> list) {
		controller.setManagementAngleSearchConditions(list);
	}

	/**
	 * �召�`�F�b�N�B�J�n�R�[�h > �I���R�[�h�̃t�B�[���h�������<br>
	 * ���̃C���f�b�N�X��Ԃ��B������Ε�����Ԃ��B
	 * 
	 * @return �J�n�R�[�h > �I���R�[�h�̃t�B�[���h�������<br>
	 *         ���̃C���f�b�N�X��Ԃ��B������Ε�����Ԃ��B
	 */
	public int isSmallerFrom() {
		return controller.isSmallerFrom();
	}

	/**
	 * �ォ�珇�ɓ��͂���Ă��邩��Ԃ��B
	 * 
	 * @return �ォ�珇�ɓ��͂���Ă��邩
	 */
	public boolean isEnteredAtTheTop() {
		return controller.isEnteredAtTheTop();
	}

	/**
	 * �Ǘ��w��ɏd��������΁A�d�����Ă���t�B�[���h�̃C���f�b�N�X��Ԃ��B<br>
	 * �����̏ꍇ�A�d���͂Ȃ�
	 * 
	 * @return �����B����ȊO�͏d�����������t�B�[���h�̔ԍ�
	 */
	public int getSameManagementAngleIndex() {
		return controller.getSameManagementAngleIndex();
	}

	/**
	 * �ێ��L�[�̎擾
	 * 
	 * @return saveKey �ێ��L�[
	 */
	public String getSaveKey() {
		return saveKey;
	}

	/**
	 * �ێ��L�[�̐ݒ�
	 * 
	 * @param saveKey �ێ��L�[
	 */
	public void setSaveKey(String saveKey) {
		this.saveKey = saveKey;
	}

	/**
	 * �Ǘ����ڒP�ʏ����ێ��̐ݒ�ɂ�蕜��
	 */
	public void restoreManagementSetting() {
		controller.restoreSetting();
	}

	/**
	 * �Ǘ����ڒP�ʏ��� �ݒ�ێ�
	 */
	public void saveManagementSetting() {
		// ��������鎞�ɁA�ێ��L�[������΁A���Y�������N���C�A���g�Ɏ���
		controller.saveSetting();
	}

	/**
	 * true:�SSPC���[�h�̐ݒ�
	 * 
	 * @param allCompanyMode true:�SSPC���[�h
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		controller.setAllCompanyMode(allCompanyMode);
	}

	/**
	 * ��Џo�͒P�ʂ̎擾
	 * 
	 * @return companyOrgUnit ��Џo�͒P��
	 */
	public TCompanyOrganizationUnit getCompanyOrgUnit() {
		return companyOrgUnit;
	}

	/**
	 * ��Џo�͒P�ʂ̐ݒ�
	 * 
	 * @param companyOrgUnit ��Џo�͒P��
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {
		this.companyOrgUnit = companyOrgUnit;

		controller.setCompanyOrgUnit(companyOrgUnit);
	}

}