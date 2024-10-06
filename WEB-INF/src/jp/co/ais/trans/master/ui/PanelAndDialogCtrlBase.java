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

	/** 日付コントロールの最小値 * */
	public static final Date minInputDate = DateUtil.getDate(1900, 1, 1);

	/** 日付コントロールの最大値 * */
	public static final Date maxInputDate = DateUtil.getDate(2099, 12, 31);

	/** EXCELの最大行数 * */
	public static final int maxExcelRows = 65535;

	// ***** 共通リストボックスに関する共通ファンクション *****

	/**
	 * Word.xmlによりMap中のデータ（MessageID）を変換する
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
	 * Mapデータをリストボックスにfillする
	 * 
	 * @param comboBox
	 * @param data
	 */
	public void fillItemsToComboBox(JComboBox comboBox, Map data) {
		fillItemsToComboBox(comboBox, data, true);
	}

	/**
	 * Mapデータをリストボックスにfillする
	 * 
	 * @param comboBox
	 * @param data
	 * @param addEmptyItem ブランク項目は必要ですか？
	 */
	public void fillItemsToComboBox(JComboBox comboBox, Map data, boolean addEmptyItem) {
		comboBox.removeAllItems();

		if (addEmptyItem) {
			// ブランク項目
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
	 * リストボックス中の指定項目を選択する
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
	 * リストボックスの選択された項目のＤＢ値を取得
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
