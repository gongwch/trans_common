package jp.co.ais.trans.master.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 
 */
public class PanelAndDialogCtrlBase extends TPanelCtrlBase {

	/** ���t�R���g���[���̍ŏ��l * */
	public static final Date minInputDate = DateUtil.getDate(1900, 1, 1);

	/** ���t�R���g���[���̍ő�l * */
	public static final Date maxInputDate = DateUtil.getDate(2099, 12, 31);

	/** EXCEL�̍ő�s�� * */
	public static final int maxExcelRows = 65535;

	// ***** ���ʃ��X�g�{�b�N�X�Ɋւ��鋤�ʃt�@���N�V���� *****

	/**
	 * Word.xml�ɂ��Map���̃f�[�^�iMessageID�j��ϊ�����
	 * 
	 * @param data
	 */
	public void translateMessageIDForMapData(Map data) {
		if (data == null) {
			return;
		}

		Iterator ite = data.entrySet().iterator();

		while (ite.hasNext()) {
			Map.Entry entry = (Map.Entry) ite.next();
			String text = (String) entry.getValue();
			text = getWord(text);

			entry.setValue(text);
		}
	}

	/**
	 * Map�f�[�^�����X�g�{�b�N�X��fill����
	 * 
	 * @param comboBox
	 * @param data
	 */
	public void fillItemsToComboBox(JComboBox comboBox, Map data) {
		fillItemsToComboBox(comboBox, data, true);
	}

	/**
	 * Map�f�[�^�����X�g�{�b�N�X��fill����
	 * 
	 * @param comboBox
	 * @param data
	 * @param addEmptyItem �u�����N���ڂ͕K�v�ł����H
	 */
	public void fillItemsToComboBox(JComboBox comboBox, Map data, boolean addEmptyItem) {
		comboBox.removeAllItems();

		if (addEmptyItem) {
			// �u�����N����
			TTextValue emptyTV = new TTextValue("");
			comboBox.addItem(emptyTV);
		}
		if (data == null) {
			return;
		}

		Iterator ite = data.entrySet().iterator();

		while (ite.hasNext()) {
			Map.Entry entry = (Map.Entry) ite.next();
			String key = (String) entry.getKey();
			String text = (String) entry.getValue();

			TTextValue tv = new TTextValue(text, key);
			comboBox.addItem(tv);
		}
	}

	/**
	 * ���X�g�{�b�N�X���̎w�荀�ڂ�I������
	 * 
	 * @param comboBox
	 * @param selectedValue
	 */
	public void setComboBoxSelectedItem(JComboBox comboBox, String selectedValue) {
		if (comboBox.getItemCount() > 0) {
			comboBox.setSelectedIndex(0);
		}
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			TTextValue tv = (TTextValue) comboBox.getItemAt(i);
			if (tv.getValue().equals(selectedValue)) {
				comboBox.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * ���X�g�{�b�N�X�̑I�����ꂽ���ڂ̂c�a�l���擾
	 * 
	 * @param comboBox
	 * @return String
	 */
	public String getComboBoxSelectedValue(JComboBox comboBox) {
		TTextValue tv = (TTextValue) comboBox.getSelectedItem();
		if (tv != null) {
			return tv.getValue();
		} else {
			return "";
		}
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}
}
