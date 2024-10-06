package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * �A�����ђn��R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TMlitCountryComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TMlitCountryComboBox comboBox;

	/** �������� */
	protected YJRegionSearchCondition condition;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TMlitCountryComboBoxController(TMlitCountryComboBox comboBox) {

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
	protected YJRegionSearchCondition createSearchCondition() {
		return new YJRegionSearchCondition();
	}

	/**
	 * @return ����������Ԃ�
	 */
	public YJRegionSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 */
	public void initList() {
		YJRegionSearchCondition searchCondition = condition.clone();
		getList(searchCondition);
	}

	/**
	 * �R���{�{�b�N�X���e�����t���b�V��
	 */
	public void refleshCombo() {
		String selected = getSelectedCountryCode();
		getList(condition);
		if (selected != null) {
			setSelectedCountryCode(selected);
		}
	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 * 
	 * @param condition1
	 */
	public void getList(YJRegionSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<YJRegion> list = getDataList(condition1);

			comboBox.getComboBox().addTextValueItem(null, "");

			for (int i = 0; i < list.size(); i++) {
				comboBox.getComboBox().addTextValueItem(list.get(i).getCOUNTRY_CODE(), list.get(i).getCOUNTRY_NAME());
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
	protected List<YJRegion> getDataList(YJRegionSearchCondition condition1) throws TException {
		List<YJRegion> list = (List<YJRegion>) request(getModelClass(), "getCountry", condition1);

		if (comboBox.hasBlank) {
			YJRegion blank = new YJRegion();
			list.add(0, blank);
		}
		return list;
	}

	/**
	 * @return Servlet��Class��Ԃ�
	 */
	protected Class getModelClass() {
		return YJRegionManager.class;
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
	 * �I�����ꂽ�n��R�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�n��R�[�h
	 */
	public String getSelectedCountryCode() {
		return (String) comboBox.getComboBox().getSelectedItem();
	}

	/**
	 * �n��R�[�h��ݒ肷��
	 * 
	 * @param code �n��R�[�h
	 */
	public void setSelectedCountryCode(String code) {
		DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getComboBox().getModel();
		int index = model.getIndexOf(code);
		if (index != -1) {
			comboBox.setSelectedIndex(index);
		}
	}
}
