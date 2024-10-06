package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����уT�u�i�ڃR���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TMlitSubItemComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TMlitSubItemComboBox comboBox;

	/** �������� */
	protected YJItemSearchCondition condition;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TMlitSubItemComboBoxController(TMlitSubItemComboBox comboBox) {

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
	protected YJItemSearchCondition createSearchCondition() {
		return new YJItemSearchCondition();
	}

	/**
	 * @return ����������Ԃ�
	 */
	public YJItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 */
	public void initList() {
		YJItemSearchCondition searchCondition = condition.clone();
		getList(searchCondition);
	}

	/**
	 * �R���{�{�b�N�X���e�����t���b�V��
	 */
	public void refleshCombo() {
		String selected = getSelectedCode();
		getList(condition);
		comboBox.setSelectedIndex(0);
		if (selected != null) {
			setSelectedCode(selected);
		}
	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 * 
	 * @param condition1
	 */
	public void getList(YJItemSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<YJItem> list = getDataList(condition1);

			comboBox.getComboBox().addTextValueItem(null, "");

			for (int i = 0; i < list.size(); i++) {
				comboBox.getComboBox().addTextValueItem(list.get(i).getITEM_SUB_CODE(), list.get(i).getITEM_SUB_NAME());
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
	protected List<YJItem> getDataList(YJItemSearchCondition condition1) throws TException {
		List<YJItem> list = (List<YJItem>) request(getModelClass(), "getSubItems", condition1);

		if (comboBox.hasBlank) {
			YJItem blank = new YJItem();
			list.add(0, blank);
		}
		return list;
	}

	/**
	 * @return Servlet��Class��Ԃ�
	 */
	protected Class getModelClass() {
		return YJItemManager.class;
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
	 * �I�����ꂽ�T�u�i�ڃR�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�T�u�i�ڃR�[�h
	 */
	public String getSelectedCode() {
		return (String) comboBox.getComboBox().getSelectedItemValue();
	}

	/**
	 * �T�u�i�ڃR�[�h��ݒ肷��
	 * 
	 * @param code �T�u�i�ڃR�[�h
	 */
	public void setSelectedCode(String code) {
		comboBox.setSelectedItemValue(code);
	}

	/**
	 * �I�����ꂽ���̂��擾����
	 * 
	 * @return ����
	 */
	public String getSelectedName() {
		TTextValue val = comboBox.getComboBox().getSelectedTextValue();
		return val == null ? null : val.getText();
	}
}
