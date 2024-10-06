package jp.co.ais.trans2.common.gui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * Enum�R���{�{�b�N�X(Label�Ȃ�)
 * 
 * @param <T> Enum
 */
public class TEnumCombo<T extends TEnumRadio> extends TComboBox {

	/**
	 * �R���X�g���N�^(��)
	 */
	public TEnumCombo() {
		super();
	}

	/**
	 * �l��S�ĕԂ�.
	 * 
	 * @return ���X�g
	 */
	public List<T> getList() {

		List<T> list = new ArrayList<T>();
		ComboBoxModel model = getModel();

		if (model == null || model.getSize() == 0) {
			return list;
		}

		// �w��̕�����Ɠ���ItemValue���s��I����Ԃɂ���B
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
	 * �l���w�肵�đI����ύX����
	 * 
	 * @param e Enum(null�\)
	 */
	public void setSelectedItemValue(T e) {
		setValue(e);
	}

	/**
	 * �I������Ă���l���擾
	 * 
	 * @return Enum(null�\)
	 */
	@Override
	public T getSelectedItemValue() {
		return getValue();
	}

	/**
	 * @param e Enum
	 */
	public void addItem(T e) {
		String lang = TLoginInfo.getUser() == null ? "ja" : TLoginInfo.getUser().getLanguage();
		String text = e == null ? "" : MessageUtil.getWord(lang, e.getName());
		this.addTextValueItem(e, text);
	}

	/**
	 * �w��Index�{�^���̑I����Ԃ�ON�ɂ���.
	 * 
	 * @param e �w��Enum(null�\)
	 */
	public void setValue(T e) {
		if (e == null) {
			setSelectedIndex(0);
			return;
		}

		List<T> list = getList();
		for (int i = 0; i < list.size(); i++) {
			if (e.equals(list.get(i))) {
				setSelectedIndex(i);
			}
		}
	}

	/**
	 * �I������Ă���l���擾
	 * 
	 * @return Enum(null�\)
	 */
	public T getValue() {
		return (T) super.getSelectedItemValue();
	}

	/**
	 * �I�����N���A
	 */
	public void clear() {
		removeAllItems();
	}

}
