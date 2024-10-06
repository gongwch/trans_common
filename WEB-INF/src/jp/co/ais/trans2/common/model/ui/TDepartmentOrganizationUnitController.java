package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * �g�D(����K�w)�̏o�͒P�ʃ��j�b�g�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TDepartmentOrganizationUnitController extends TController {

	/** true:�L�� */
	protected static boolean useSaveSetting = ClientConfig.isFlagOn("trans.report.department.unit.save");

	/** �t�B�[���h */
	protected TDepartmentOrganizationUnit field;

	/**
	 * @param field �t�B�[���h
	 */
	public TDepartmentOrganizationUnitController(TDepartmentOrganizationUnit field) {
		this.field = field;
		init();
	}

	/**
	 * @return true:�L��
	 */
	protected boolean isUseSaveSetting() {
		return useSaveSetting;
	}

	/**
	 * �o�͒P�ʏ����ێ��̐ݒ�ɂ�蕜��
	 */
	protected void restoreSetting() {
		if (!isUseSaveSetting()) {
			// �L�����Ȃ��ꍇ�A�����s�v
			return;
		}

		// �ێ��L�[���g���ď���������
		if (!Util.isNullOrEmpty(getSaveKey())) {
			DepartmentOutputCondition condition = getSaveCondition();
			if (condition != null) {
				setDepartmentOutputCondition(getSaveCondition());
			}
		}
	}

	/**
	 * �ێ��L�[�̎擾
	 * 
	 * @return saveKey �ێ��L�[
	 */
	protected DepartmentOutputCondition getSaveCondition() {
		return (DepartmentOutputCondition) FileUtil.getTemporaryObject(getSaveKey());
	}

	/**
	 * �ێ��L�[�̎擾
	 * 
	 * @return saveKey �ێ��L�[
	 */
	protected String getSaveKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(TLoginCtrl.getClientSaveKey());
		sb.append("_");
		sb.append(field.getSaveKey());
		return sb.toString();
	}

	/**
	 * �o�͒P�ʏ��� �ݒ�ێ�
	 */
	public void saveSetting() {
		if (!isUseSaveSetting()) {
			// �L�����Ȃ��ꍇ�A�����s�v
			return;
		}

		// ��������鎞�ɁA�ێ��L�[������΁A���Y�������N���C�A���g�Ɏ���
		if (Util.isNullOrEmpty(getSaveKey())) {
			return;
		}

		FileUtil.saveTemporaryObject(getDepartmentOutputCondition(), getSaveKey());

	}

	/**
	 * ������
	 */
	protected void init() {
		// �K�w���x��������
		initLevelCombobox();
		// �u�܂ށv��I��
		field.rdoInclude.setSelected(true);
		// �C�x���g��`
		addEvent();
		// �W�v������܂�
		field.ctrlSuperiorDepartment.getSearchCondition().setSumDepartment(true);
		field.ctrlSuperiorDepartment.setCheckExist(false);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setSumDepartment(true);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setSumDepartment(true);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.setCheckExist(false);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.setCheckExist(false);
		field.ctrlDepartment.ctrlOptionalSelector.getSearchCondition().setSumDepartment(true);
		clear();
	}

	/**
	 * �C�x���g�ǉ�
	 */
	protected void addEvent() {

		// �g�D�ύX
		field.cboDepartmentOrganization.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					cboDepartmentOrganization_itemStateChanged();
				}
			}
		});

		// �K�w���x���ύX
		field.cboLevel.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					cboLevel_itemStateChanged();
				}
			}
		});

		// ��ʕ���I����
		field.ctrlSuperiorDepartment.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSuperiorDepartment_after();
			}
		});

	}

	/**
	 * �K�w���x���̏�����
	 */
	protected void initLevelCombobox() {
		for (int i = 0; i <= 9; i++) {
			field.cboLevel.getComboBox().addItem(getWord("C01739") + Integer.toString(i));
		}
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * [�g�D]���̏���
	 */
	protected void cboDepartmentOrganization_itemStateChanged() {
		clear();
	}

	/**
	 * [���x��]�ύX���̏���
	 */
	protected void cboLevel_itemStateChanged() {

		// ���x��0�̏ꍇ�A��ʕ����I��s�\�ɂ���B
		field.ctrlSuperiorDepartment.setEditable(field.cboLevel.getSelectedIndex() != 0);
		field.ctrlDepartment.ctrlReferenceRange.setEditable(field.cboLevel.getSelectedIndex() == 0);

		// ������N���A
		field.ctrlSuperiorDepartment.clear();
		field.ctrlDepartment.getReferenceRange().clear();
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeFrom(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeTo(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeFrom(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeTo(null);

		// ��ʕ���̓��x��-1���w��
		field.ctrlSuperiorDepartment.getSearchCondition().setLevel(field.cboLevel.getSelectedIndex() - 1);

		// ����̓��x�����w��
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setLevel(
			field.cboLevel.getSelectedIndex());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setLevel(
			field.cboLevel.getSelectedIndex());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition()
			.setSuperiorDepartmentCode(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition()
			.setSuperiorDepartmentCode(null);

		// �ʑI���̓��x�����w��
		field.ctrlDepartment.ctrlOptionalSelector.getSearchCondition().setLevel(field.cboLevel.getSelectedIndex());
		field.ctrlDepartment.ctrlOptionalSelector.refresh();

	}

	/**
	 * ���x�����̃R���{�{�b�N�X
	 */
	protected void initLevelNameComboBox() {
		field.cboLevel.getComboBox().removeAllItems();
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCode(field.cboDepartmentOrganization.getSelectedOrganizationCode());
		try {
			DepartmentOrganization bean = (DepartmentOrganization) request(DepartmentOrganizationManager.class,
				"getDepartmentOrganizationName", condition);
			String[] codes = new String[10];

			if (bean == null) {
				bean = new DepartmentOrganization();
			}

			codes[0] = getLevelName(bean.getDPK_LVL_0_NAME(), getWord("C00722"));
			codes[1] = getLevelName(bean.getDPK_LVL_1_NAME(), getWord("C02126"));
			codes[2] = getLevelName(bean.getDPK_LVL_2_NAME(), getWord("C02127"));
			codes[3] = getLevelName(bean.getDPK_LVL_3_NAME(), getWord("C02128"));
			codes[4] = getLevelName(bean.getDPK_LVL_4_NAME(), getWord("C02129"));
			codes[5] = getLevelName(bean.getDPK_LVL_5_NAME(), getWord("C02130"));
			codes[6] = getLevelName(bean.getDPK_LVL_6_NAME(), getWord("C02131"));
			codes[7] = getLevelName(bean.getDPK_LVL_7_NAME(), getWord("C02132"));
			codes[8] = getLevelName(bean.getDPK_LVL_8_NAME(), getWord("C02133"));
			codes[9] = getLevelName(bean.getDPK_LVL_9_NAME(), getWord("C02134"));
			field.cboLevel.getComboBox().setModel(codes);

		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * @param name
	 * @param defaultName
	 * @return getLevelNames
	 */
	protected String getLevelName(String name, String defaultName) {
		if (Util.isNullOrEmpty(name)) {
			return defaultName;
		}
		return name;
	}

	/**
	 * �t�B�[���h���N���A����
	 */
	public void clear() {
		// �g�D�R�[�h�����Z�b�g
		field.ctrlSuperiorDepartment.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		if (!Util.isNullOrEmpty(field.cboDepartmentOrganization.getSelectedOrganizationCode())) {
			// ���x����0��I��
			field.cboLevel.setSelectedIndex(0);
		}
		field.ctrlDepartment.ctrlOptionalSelector.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		if (TDepartmentOrganizationUnit.DISPLAY_NAME_FLG != 0) {
			initLevelNameComboBox();
		}
		cboLevel_itemStateChanged();
	}

	/**
	 * ��ʕ���I����
	 */
	protected void ctrlSuperiorDepartment_after() {

		if (field.ctrlSuperiorDepartment.code.isValueChanged()) {
			field.ctrlDepartment.getReferenceRange().clear();
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeFrom(null);
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeTo(null);
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeFrom(null);
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeTo(null);
		}

		Department department = field.ctrlSuperiorDepartment.getEntity();

		if (department == null) {
			field.ctrlDepartment.ctrlReferenceRange.setEditable(false);
		} else {
			field.ctrlDepartment.ctrlReferenceRange.setEditable(true);
		}

		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition()
			.setSuperiorDepartmentCode(field.ctrlSuperiorDepartment.getCode());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition()
			.setSuperiorDepartmentCode(field.ctrlSuperiorDepartment.getCode());

	}

	/**
	 * �o�͒P�ʂ�Ԃ�
	 * 
	 * @return �o�͒P��
	 */
	public DepartmentOutputCondition getDepartmentOutputCondition() {

		DepartmentOutputCondition condition = createCondition();
		// ���
		condition.setCompanyCode(getCompany().getCode());
		// �g�D�R�[�h
		condition.setDepartmentOrganizationCode(field.cboDepartmentOrganization.getSelectedOrganizationCode());
		// �K�w���x��
		condition.setLevel(field.cboLevel.getSelectedIndex());
		// �z��������܂ނ�
		condition.setIncludeUnderDepartment(field.rdoInclude.isSelected());
		// ��ʕ���
		condition.setSuperiorDepartment(field.ctrlSuperiorDepartment.getEntity());
		// �J�n����
		condition.setDepartmentFrom(field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getEntity());
		// �I������
		condition.setDepartmentTo(field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getEntity());
		// �ʑI�𕔖�
		condition.setOptionalDepartments(field.ctrlDepartment.ctrlOptionalSelector.getDepartmentEntities());

		return condition;

	}

	/**
	 * �o�͒P�ʂ�ݒ肷��
	 * 
	 * @param condition �o�͒P��
	 */
	public void setDepartmentOutputCondition(DepartmentOutputCondition condition) {
		// �g�D�R�[�h
		field.cboDepartmentOrganization.setSelectedOrganizationCode(condition.getDepartmentOrganizationCode());
		// �K�w���x��
		field.cboLevel.setSelectedIndex(condition.getLevel());
		// �z��������܂ނ�
		field.rdoInclude.setSelected(condition.isIncludeUnderDepartment());
		// ��ʕ���
		field.ctrlSuperiorDepartment.setEntity(condition.getSuperiorDepartment());
		// �J�n����
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.setEntity(condition.getDepartmentFrom());
		// �I������
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.setEntity(condition.getDepartmentTo());
		// �ʑI�𕔖�
		field.ctrlDepartment.ctrlOptionalSelector.setDepartmentEntities(condition.getOptionalDepartments());

		// ��ʐ���ǉ�
		field.ctrlSuperiorDepartment.setEditable(condition.getSuperiorDepartment() != null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.setEditable(condition
			.getSuperiorDepartment() != null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo
			.setEditable(condition.getSuperiorDepartment() != null);
	}

	/**
	 * @return ���������쐬
	 */
	protected DepartmentOutputCondition createCondition() {
		return new DepartmentOutputCondition();
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isDepartmentSmallerFrom() {
		return field.ctrlDepartment.ctrlReferenceRange.isSmallerFrom();
	}

}
