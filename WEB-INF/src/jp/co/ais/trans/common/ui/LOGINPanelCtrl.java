package jp.co.ais.trans.common.ui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ログイン用パネル コントローラ(未使用)
 * 
 * @author liuguozheng
 */
public class LOGINPanelCtrl extends TPanelCtrlBase {

	/** パネル */
	private LOGINPanel panel;

	/**
	 * コンストラクタ.
	 */
	public LOGINPanelCtrl() {
		try {
			panel = new LOGINPanel(this);

		} catch (Exception e) {
			errorHandler(panel, e, "E00010");
		}
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * 検索処理
	 */
	void search() {

		// 会社コードチェック
		if (Util.isNullOrEmpty(panel.ctrlCompanyCode.getValue())) {
			// 会社コードを入力してください
			super.showMessage(panel, "I00002", "C00684");
			panel.ctrlCompanyCode.requestTextFocus();
			return;
		}

		// ユーザーコードチェック
		if (Util.isNullOrEmpty(panel.ctrlUserCode.getValue())) {
			// ユーザーコードを入力してください
			super.showMessage(panel, "I00002", "C00589");
			panel.ctrlUserCode.requestTextFocus();
			return;
		}

		// パスワードチェック
		if (Util.isNullOrEmpty(panel.ctrlPassword.getValue())) {
			// パスワードを入力してください
			super.showMessage(panel, "I00002", "C00597");
			panel.ctrlPassword.requestTextFocus();
			return;
		}
	}
}
