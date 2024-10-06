package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * �A�����э��R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TMlitRegionComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TMlitRegionComboBox comboBox;

	/** �������� */
	protected YJRegionSearchCondition condition;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TMlitRegionComboBoxController(TMlitRegionComboBox comboBox) {

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
					setCountryCondition();
				}
			}

		});
	}

	/**
	 * �I����e�ύX���C�x���g<br>
	 * �΂ɂȂ�CoutryCombo�ݒ莞�ACountry�R���{�̌���������ݒ肷��
	 */
	protected void setCountryCondition() {
		if (comboBox.couCombo == null) {
			return;
		}
		TMlitCountryComboBox combo = comboBox.couCombo;
		combo.getSearchCondition().setRegionCode(getSelectedRegionCode());
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
	protected YJRegionSearchCondition createSearchCondition() {
		return new YJRegionSearchCondition();
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
	public void getList(YJRegionSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<YJRegion> list = getDataList(condition1);

			comboBox.getComboBox().addTextValueItem(null, "");

			for (int i = 0; i < list.size(); i++) {
				comboBox.getComboBox().addTextValueItem(list.get(i).getREGION_CODE(), list.get(i).getREGION_NAME());
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
		List<YJRegion> list = (List<YJRegion>) request(getModelClass(), "getRegion", condition1);

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
	 * �I�����ꂽ���R�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ���R�[�h
	 */
	public String getSelectedRegionCode() {
		return (String) comboBox.getComboBox().getSelectedItem();
	}

	/**
	 * ���R�[�h��ݒ肷��
	 * 
	 * @param code ���R�[�h
	 */
	public void setSelectedRegionCode(String code) {
		DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getComboBox().getModel();
		int index = model.getIndexOf(code);
		if (index != -1) {
			comboBox.setSelectedIndex(index);
		}
	}
}
