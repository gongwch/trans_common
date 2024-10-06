package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * TAB可能なパネル
 */
public class TTabbedPanel extends TPanelBusiness {

	/** TAB機能ダイアログ */
	protected TTabbedDialog dialog = null;

	/**
	 * TAB機能ダイアログの取得
	 * 
	 * @return dialog TAB機能ダイアログ
	 */
	public TTabbedDialog getDialog() {
		return dialog;
	}

	/**
	 * TAB機能ダイアログの設定
	 * 
	 * @param dialog TAB機能ダイアログ
	 */
	public void setDialog(TTabbedDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * パネルのアイテムのメッセージIDを変換する.
	 */
	@Override
	protected void translateLangMessageID() {
		TGuiUtil.recursiveTranslateLangMessageID(this, TClientLoginInfo.getInstance().getUserLanguage(), 0);
	}

	/**
	 * ACTIVE処理
	 */
	public void active() {
		if (dialog != null) {
			dialog.active();
		}
	}
}
