package jp.co.ais.trans2.common.gui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * �{�[�_�[�t�@�N�g��
 * @author AIS
 *
 */
public class TBorderFactory {

	/**
	 * �^�C�g���t��Border��Ԃ��܂��B
	 * @param title �^�C�g��
	 * @return �^�C�g���t��Border
	 */
	public static Border createTitledBorder(String title) {
		return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
	}

}
