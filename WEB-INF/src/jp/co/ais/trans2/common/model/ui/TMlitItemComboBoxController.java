package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����ё�i�ڃR���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TMlitItemComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TMlitItemComboBox comboBox;

	/** �������� */
	protected YJItemSearchCondition condition;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TMlitItemComboBoxController(TMlitItemComboBox comboBox) {

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

		// �C�x���g��`
		addEvent();
	}

	/**
	 * �C�x���g���`
	 */
	protected void addEvent() {
		comboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setItemCondition();
				}
			}

		});
	}

	/**
	 * �I����e�ύX���C�x���g<br>
	 * �΂ɂȂ�SubItemCombo�ݒ莞�ASub�R���{�̌���������ݒ肷��
	 */
	protected void setItemCondition() {
		if (comboBox.subCombo == null) {
			return;
		}
		TMlitSubItemComboBox combo = comboBox.subCombo;
		boolean enable = !Util.isNullOrEmpty(comboBox.getSelectedCode());
		combo.setEnabled(enable);
		combo.getSearchCondition().setItemCode(getSelectedCode());
		combo.reflesh();
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
	public void getList(YJItemSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<YJItem> list = getDataList(condition1);

			comboBox.getComboBox().addTextValueItem(null, "");

			for (int i = 0; i < list.size(); i++) {
				comboBox.getComboBox().addTextValueItem(list.get(i).getITEM_CODE(), list.get(i).getITEM_NAME());
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
		List<YJItem> list = (List<YJItem>) request(getModelClass(), "getItems", condition1);

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
	 * �I�����ꂽ�i�ڃR�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�i�ڃR�[�h
	 */
	public String getSelectedCode() {
		return (String) comboBox.getComboBox().getSelectedItemValue();
	}

	/**
	 * �i�ڃR�[�h��ݒ肷��
	 * 
	 * @param code �i�ڃR�[�h
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
