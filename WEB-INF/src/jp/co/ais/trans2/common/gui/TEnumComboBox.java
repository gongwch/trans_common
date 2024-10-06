package jp.co.ais.trans2.common.gui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.TComboBox.TTextValue;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * Enumコンボボックス(Labelあり)
 * 
 * @param <T> Enum
 */
public class TEnumComboBox<T extends TEnumRadio> extends TLabelComboBox {

	/**
	 * コンストラクタ(横)
	 */
	public TEnumComboBox() {
		super();
	}

	/**
	 * 値を全て返す.
	 * 
	 * @return リスト
	 */
	public List<T> getItemValueList() {

		List<T> list = new ArrayList<T>();
		ComboBoxModel model = combo.getModel();

		if (model == null || model.getSize() == 0) {
			return list;
		}

		// 指定の文字列と同じItemValue持つ行を選択状態にする。
		for (int i = 0; i < model.getSize(); i++) {
			Object item = model.getElementAt(i);

			if (item != null && item instanceof TTextValue) {
				T value = (T) ((TTextValue) item).getValue();
				list.add(value);
			} else {
				list.add(null);
			}
		}

		return list;
	}

	/**
	 * 値を指定して選択を変更する
	 * 
	 * @param e Enum(null可能)
	 */
	public void setSelectedItemValue(T e) {
		setValue(e);
	}

	/**
	 * 選択されている値を取得
	 * 
	 * @return Enum(null可能)
	 */
	@Override
	public T getSelectedItemValue() {
		return getValue();
	}

	/**
	 * 値(キー)・表示文字を追加
	 * 
	 * @param text テキスト
	 * @param value 値
	 */
	@Override
	@Deprecated
	public void addTextValueItem(Object value, String text) {
		// 廃止
	}

	/**
	 * @param e Enum
	 */
	public void addItem(T e) {
		String lang = TLoginInfo.getUser() == null ? "ja" : TLoginInfo.getUser().getLanguage();
		String text = e == null ? "" : MessageUtil.getWord(lang, e.getName());
		this.combo.addTextValueItem(e, text);
	}

	/**
	 * 指定Indexボタンの選択状態をONにする.
	 * 
	 * @param e 指定Enum(null可能)
	 */
	public void setValue(T e) {
		if (e == null) {
			setSelectedIndex(0);
			return;
		}

		List<T> list = getItemValueList();
		for (int i = 0; i < list.size(); i++) {
			if (e.equals(list.get(i))) {
				setSelectedIndex(i);
			}
		}
	}

	/**
	 * 選択されている値を取得
	 * 
	 * @return Enum(null可能)
	 */
	public T getValue() {
		return (T) this.combo.getSelectedItemValue();
	}

	/**
	 * 選択肢クリア
	 */
	public void clear() {
		combo.removeAllItems();
	}

}
