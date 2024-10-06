package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �g�D(��ЊK�w)�̏o�͒P�ʃ��j�b�g�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TCompanyOrganizationUnitController extends TController {

	/** �t�B�[���h */
	protected TCompanyOrganizationUnit field;

	/**
	 * @param field �t�B�[���h
	 */
	public TCompanyOrganizationUnitController(TCompanyOrganizationUnit field) {
		this.field = field;
		init();
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
		// �W�v��Ђ��܂�
		field.ctrlSuperiorCompany.setCheckExist(false);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.setCheckExist(false);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.setCheckExist(false);
		clear();
	}

	/**
	 * �C�x���g�ǉ�
	 */
	protected void addEvent() {

		// �g�D�ύX
		field.cboCompanyOrganization.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					cboCompanyOrganization_itemStateChanged();
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

		// ��ʉ�БI����
		field.ctrlSuperiorCompany.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSuperiorCompany_after();
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
	protected void cboCompanyOrganization_itemStateChanged() {
		clear();
	}

	/**
	 * [���x��]�ύX���̏���
	 */
	protected void cboLevel_itemStateChanged() {

		int level = field.cboLevel.getSelectedIndex();

		// ���x��0�̏ꍇ�A��ʉ�Ђ�I��s�\�ɂ���B
		field.ctrlSuperiorCompany.setEditable(level != 0);
		field.ctrlCompany.ctrlReferenceRange.setEditable(level == 0);

		// ��Џ��N���A
		field.ctrlSuperiorCompany.clear();
		field.ctrlCompany.getReferenceRange().clear();
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeFrom(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeTo(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeFrom(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeTo(null);

		// ��ʉ�Ђ̓��x��-1���w��
		field.ctrlSuperiorCompany.getSearchCondition().setLevel(level - 1);

		// ��Ђ̓��x�����w��
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setLevel(level);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setLevel(level);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setSuperiorCompanyCode(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setSuperiorCompanyCode(null);

		// �ʑI���̓��x�����w��
		field.ctrlCompany.ctrlOptionalSelector.getSearchCondition().setLevel(level);
		field.ctrlCompany.ctrlOptionalSelector.refresh();

	}

	/**
	 * �t�B�[���h���N���A����
	 */
	public void clear() {
		// �g�D�R�[�h�����Z�b�g
		field.ctrlSuperiorCompany.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		if (!Util.isNullOrEmpty(field.cboCompanyOrganization.getSelectedOrganizationCode())) {
			// ���x����0��I��
			field.cboLevel.setSelectedIndex(0);
		}
		field.ctrlCompany.ctrlOptionalSelector.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		if (TCompanyOrganizationUnit.DISPLAY_NAME_FLG != 0) {
			initLevelNameComboBox();
		}
		cboLevel_itemStateChanged();
	}

	/**
	 * ���x�����̃R���{�{�b�N�X
	 */
	protected void initLevelNameComboBox() {
		field.cboLevel.getComboBox().removeAllItems();
		CompanyOrganizationSearchCondition condition = new CompanyOrganizationSearchCondition();
		condition.setCode(field.cboCompanyOrganization.getSelectedOrganizationCode());
		try {
			CompanyOrganization bean = (CompanyOrganization) request(CompanyOrganizationManager.class,
				"getCompanyOrganizationName", condition);
			String[] codes = new String[10];

			if (bean == null) {
				bean = new CompanyOrganization();
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
	 * @return getLevelName
	 */
	protected String getLevelName(String name, String defaultName) {
		if (Util.isNullOrEmpty(name)) {
			return defaultName;
		}
		return name;
	}

	/**
	 * ��ʉ�БI����
	 */
	protected void ctrlSuperiorCompany_after() {

		if (field.ctrlSuperiorCompany.code.isValueChanged()) {
			field.ctrlCompany.getReferenceRange().clear();
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeFrom(null);
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeTo(null);
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeFrom(null);
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeTo(null);
		}

		Company company = field.ctrlSuperiorCompany.getEntity();

		if (company == null) {
			field.ctrlCompany.ctrlReferenceRange.setEditable(false);
		} else {
			field.ctrlCompany.ctrlReferenceRange.setEditable(true);
		}

		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setSuperiorCompanyCode(
			field.ctrlSuperiorCompany.getCode());
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setSuperiorCompanyCode(
			field.ctrlSuperiorCompany.getCode());

	}

	/**
	 * �o�͒P�ʂ�Ԃ�
	 * 
	 * @return �o�͒P��
	 */
	public CompanyOutputCondition getCompanyOutputCondition() {

		CompanyOutputCondition condition = createCondition();
		// ���
		condition.setCompanyCode(getCompany().getCode());
		// �g�D�R�[�h
		condition.setCompanyOrganizationCode(field.cboCompanyOrganization.getSelectedOrganizationCode());
		// �K�w���x��
		condition.setLevel(field.cboLevel.getSelectedIndex());
		// �z����Ђ��܂ނ�
		condition.setIncludeUnderCompany(field.rdoInclude.isSelected());
		// ��ʉ��
		condition.setSuperiorCompany(field.ctrlSuperiorCompany.getEntity());
		// �J�n���
		condition.setCompanyFrom(field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getEntity());
		// �I�����
		condition.setCompanyTo(field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getEntity());
		// �ʑI�����
		condition.setOptionalCompanys(field.ctrlCompany.ctrlOptionalSelector.getEntities());

		return condition;

	}

	/**
	 * �o�͒P�ʂ�ݒ肷��
	 * 
	 * @param condition �o�͒P��
	 */
	public void setCompanyOutputCondition(CompanyOutputCondition condition) {
		// �g�D�R�[�h
		field.cboCompanyOrganization.setSelectedOrganizationCode(condition.getCompanyOrganizationCode());
		// �K�w���x��
		field.cboLevel.setSelectedIndex(condition.getLevel());
		// �z����Ђ��܂ނ�
		field.rdoInclude.setSelected(condition.isIncludeUnderCompany());
		// ��ʉ��
		field.ctrlSuperiorCompany.setEntity(condition.getSuperiorCompany());
		// �J�n���
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.setEntity(condition.getCompanyFrom());
		// �I�����
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.setEntity(condition.getCompanyTo());
		// �ʑI�����
		field.ctrlCompany.ctrlOptionalSelector.setEntities(condition.getOptionalCompanys());
	}

	/**
	 * @return ���������쐬
	 */
	protected CompanyOutputCondition createCondition() {
		return new CompanyOutputCondition();
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isCompanySmallerFrom() {
		return field.ctrlCompany.ctrlReferenceRange.isSmallerFrom();
	}

}
