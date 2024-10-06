package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 消費税の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TTaxOptionalSelectorDialog extends TOptionalSelectorDialog {

	/**
	 * テーブルのカラム
	 * 
	 * @author AIS
	 */
	public enum SC {
		code, names, entity
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TTaxOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
