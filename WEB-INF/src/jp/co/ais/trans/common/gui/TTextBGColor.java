package jp.co.ais.trans.common.gui;

import java.awt.*;

import jp.co.ais.plaf.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * �e�L�X�g�J���[
 */
public class TTextBGColor {

	/** �t�H�[�J�XIN���J���[[���邢��] */
	public static Color FOCUS_IN = new Color(166, 251, 179);

	/** �K�{���ڃJ���[[���F] */
	public static Color NECESSARY = new Color(210, 249, 253);

	/** �G���[��ԃJ���[[���[�Y] */
	public static Color ERROR = new Color(252, 148, 164);

	/** ���F(�ėp)��ԃJ���[[���F] */
	public static Color WARNING = new Color(247, 247, 123);

	/** L/F Color Name */
	public static String currentInternalName = null;

	/**
	 * @return �t�H�[�J�X���Ă鎞�̃o�b�N�J���[
	 */
	public static Color getHighLightFocusInColor() {

		if (TLoginInfo.getUser() != null) {
			String internalName = TLoginInfo.getUser().getLfColorType();

			if (!Util.isNullOrEmpty(internalName) && !internalName.equals(currentInternalName)) {

				try {
					currentInternalName = internalName;

					LFColorConfig lfcolor = new LFColorConfig();
					lfcolor.setBundle(internalName);

					FOCUS_IN = lfcolor.getColorByName("inputHighLightBackgroundColor");

					// ���W�I�{�^������
					BaseRadioButtonUI.FOCUS_IN = FOCUS_IN;

				} catch (Exception ex) {
					//
				}
			}
		}

		return FOCUS_IN;
	}

}
