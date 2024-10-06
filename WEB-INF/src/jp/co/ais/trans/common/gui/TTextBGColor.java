package jp.co.ais.trans.common.gui;

import java.awt.*;

import jp.co.ais.plaf.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * テキストカラー
 */
public class TTextBGColor {

	/** フォーカスIN時カラー[明るい緑] */
	public static Color FOCUS_IN = new Color(166, 251, 179);

	/** 必須項目カラー[水色] */
	public static Color NECESSARY = new Color(210, 249, 253);

	/** エラー状態カラー[ローズ] */
	public static Color ERROR = new Color(252, 148, 164);

	/** 黄色(汎用)状態カラー[黄色] */
	public static Color WARNING = new Color(247, 247, 123);

	/** L/F Color Name */
	public static String currentInternalName = null;

	/**
	 * @return フォーカス当てる時のバックカラー
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

					// ラジオボタン同じ
					BaseRadioButtonUI.FOCUS_IN = FOCUS_IN;

				} catch (Exception ex) {
					//
				}
			}
		}

		return FOCUS_IN;
	}

}
