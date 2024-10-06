package jp.co.ais.trans.common.gui.message;

import javax.swing.plaf.basic.*;

/**
 * TBasicOptionPaneUI
 */
public class TBasicOptionPaneUI extends BasicOptionPaneUI {

	/**
	 * �m�F���b�Z�[�W�ŁA�����t�H�[�J�X�̃C���f�b�N�X���擾����
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
