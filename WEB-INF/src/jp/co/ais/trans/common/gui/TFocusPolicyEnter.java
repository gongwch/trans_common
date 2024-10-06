package jp.co.ais.trans.common.gui;

import java.awt.*;

import javax.swing.*;

/**
 * タブ順フォーカス移動ポリシー(Enter keyのとき)
 */
public class TFocusPolicyEnter extends TFocusPolicy {

	/**
	 * constructor.
	 * 
	 * @param list コンポーネントのリスト
	 */
	public TFocusPolicyEnter(java.util.List<Component> list) {
		super(list);
	}

	/**
	 * タブが移動できる(focusできる)componentかチェックする。
	 * 
	 * @param comp コンポーネント
	 * @return true:有効
	 */
	@Override
	protected boolean isActiveComponent(Component comp) {

		if (comp == null || comp instanceof JTabbedPane || (comp instanceof JButton && !(comp instanceof TButton))) {
			return false;
		}

		if (comp instanceof TButton && !((TButton) comp).isEnterFocusable()) {
			return false;
		}

		if (comp instanceof TCheckBox && !((TCheckBox) comp).isEnterFocusable()) {
			return false;
		}

		if (comp instanceof TToggleButton && !((TToggleButton) comp).isEnterFocusable()) {
			return false;
		}

		return super.isActiveComponent(comp);
	}
}
