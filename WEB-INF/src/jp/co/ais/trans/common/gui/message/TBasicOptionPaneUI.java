package jp.co.ais.trans.common.gui.message;

import javax.swing.plaf.basic.*;

/**
 * TBasicOptionPaneUI
 */
public class TBasicOptionPaneUI extends BasicOptionPaneUI {

	/**
	 * 確認メッセージで、初期フォーカスのインデックスを取得する
	 * 
	 * @see javax.swing.plaf.basic.BasicOptionPaneUI#getInitialValueIndex()
	 */
	@Override
	protected int getInitialValueIndex() {

		if (optionPane != null) {

			Object initialValue = optionPane.getInitialValue();

			if (initialValue != null) {

				if (initialValue instanceof Integer) {
					return ((Integer) initialValue).intValue();
				}
			}
		}
		return -1;
	}
}
