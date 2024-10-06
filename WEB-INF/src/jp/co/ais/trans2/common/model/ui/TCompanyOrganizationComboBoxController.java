package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��Бg�D�R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TCompanyOrganizationComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TCompanyOrganizationComboBox comboBox;

	/** �������� */
	protected CompanyOrganizationSearchCondition condition;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TCompanyOrganizationComboBoxController(TCompanyOrganizationComboBox comboBox) {

		this.comboBox = comboBox;

		init();

	}

	/**
	 * ������
	 */
	protected void init() {

		// ��������������
		initSearchCondition();

		// �R���{�{�b�N�X����
		getList(condition);

	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected CompanyOrganizationSearchCondition createSearchCondition() {
		return new CompanyOrganizationSearchCondition();
	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 */
	public void initList() {
		getList(condition);
	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 * 
	 * @param condition1
	 */
	public void getList(CompanyOrganizationSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<CompanyOrganization> list = getDataList(condition1);
			String[] code = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				switch (TCompanyOrganizationUnit.DISPLAY_NAME_FLG) {
					case 1:
						comboBox.getComboBox().addTextValueItem(list.get(i).getCode(), list.get(i).getName());
						break;
					case 2:
						comboBox.getComboBox().addTextValueItem(list.get(i).getCode(),
							list.get(i).getCode() + " " + list.get(i).getName());
						break;
					default:
						code[i] = list.get(i).getCode();
						break;
				}
			}
			if (0 < list.size()) {
				switch (TCompanyOrganizationUnit.DISPLAY_NAME_FLG) {
					case 0:
					case 3:
						comboBox.getComboBox().setModel(code);
						break;
					default:
						break;
				}
			}

		} catch (TException ex) {
			errorHandler(ex);
		}

	}

	/**
	 * @param condition1
	 * @return ���X�g��Ԃ�
	 * @throws TException
	 */
	protected List<CompanyOrganization> getDataList(CompanyOrganizationSearchCondition condition1) throws TException {
		List<CompanyOrganization> list = (List<CompanyOrganization>) request(getModelClass(), "getCompanyOrganization",
			condition1);

		if (comboBox.hasBlank) {
			CompanyOrganization blank = new CompanyOrganization();
			list.add(0, blank);
		}

		return list;
	}

	/**
	 * @return Servlet��Class��Ԃ�
	 */
	protected Class getModelClass() {
		return CompanyOrganizationManager.class;
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
	@Override
	public void start() {
		//
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �I�����ꂽ�g�D�R�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�g�D�R�[�h
	 */
	public String getSelectedOrganizationCode() {

		switch (TCompanyOrganizationUnit.DISPLAY_NAME_FLG) {
			case 1:
			case 2:
				return (String) comboBox.getComboBox().getSelectedItemValue();
			default:
				return (String) comboBox.getComboBox().getSelectedItem();
		}
	}

	/**
	 * �g�D�R�[�h��ݒ肷��
	 * 
	 * @param code �g�D�R�[�h
	 */
	public void setSelectedOrganizationCode(String code) {
		DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getComboBox().getModel();
		int index = model.getIndexOf(code);
		if (index != -1) {
			comboBox.setSelectedIndex(index);
		}

	}

}
