package jp.co.ais.trans2.common.model.ui;

import java.awt.Frame;
import jp.co.ais.trans2.common.gui.TOptionalSelectorDialog;

/**
 * 銀行口座の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TBankAccountOptionalSelectorDialog extends TOptionalSelectorDialog {

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
	public TBankAccountOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
