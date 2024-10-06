package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * ����g�D�R�[�h�݂̂̃R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TDepartmentOrganizationCodeComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TDepartmentOrganizationCodeComboBox comboBox;

	/** �������� */
	protected DepartmentOrganizationSearchCondition condition;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TDepartmentOrganizationCodeComboBoxController(TDepartmentOrganizationCodeComboBox comboBox) {

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
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected DepartmentOrganizationSearchCondition createSearchCondition() {
		return new DepartmentOrganizationSearchCondition();
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
	public void getList(DepartmentOrganizationSearchCondition condition1) {

		try {

			comboBox.getComboBox().removeAllItems();
			List<DepartmentOrganization> list = getDataList(condition1);
			String[] code = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				code[i] = list.get(i).getCode();
			}

			comboBox.getComboBox().setModel(code);
		} catch (TException ex) {
			errorHandler(ex);
		}

	}

	/**
	 * @param condition1
	 * @return ���X�g��Ԃ�
	 * @throws TException
	 */
	protected List<DepartmentOrganization> getDataList(DepartmentOrganizationSearchCondition condition1)
		throws TException {
		List<DepartmentOrganization> list = (List<DepartmentOrganization>) request(getModelClass(),
			"getDepartmentOrganization", condition1);

		if (comboBox.hasBlank) {
			DepartmentOrganization blank = new DepartmentOrganization();
			list.add(0, blank);
		}

		return list;
	}

	/**
	 * @return Servlet��Class��Ԃ�
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
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

		return (String) comboBox.getComboBox().getSelectedItem();
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
