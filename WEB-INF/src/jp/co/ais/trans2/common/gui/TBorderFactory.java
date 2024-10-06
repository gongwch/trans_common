package jp.co.ais.trans2.common.gui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * ボーダーファクトリ
 * @author AIS
 *
 */
public class TBorderFactory {

	/**
	 * タイトル付きBorderを返します。
	 * @param title タイトル
	 * @return タイトル付きBorder
	 */
	public static Border createTitledBorder(String title) {
		return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
	}

}
