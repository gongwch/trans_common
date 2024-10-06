package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;

/**
 * ログインのコントローラ。<BR>
 * アプリケーションの起動、ログイン画面の制御、ログインの成否判定を行う。
 * 
 * @author AIS
 */
public class TSingleLoginCtrl extends TLoginCtrl {

	/**
	 * プログラムエントリポイント<BR>
	 * アプリケーションを起動する。
	 */
	@Override
	public void start() {

		// UIの設定
		try {
			// L&F 初期化
			TUIManager.setLookAndFeel(LookAndFeelType.WINDOWS.value);

			// ログイン画面生成
			loginView = createLoginView();

			// ログイン画面初期化
			initLoginView();

			TUIManager.setUI(loginView.btnLogin, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClear, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClose, LookAndFeelType.MINT, LookAndFeelColor.White);

			// ログイン画面非表示
			loginView.setVisible(false);

			// デフォルト値設定
			setDefaultLoginInfo();

			// ログイン情報初期化
			initLoginInformation();

			// ログインボタン押下同様
			btnLogin_Click();

		} catch (Exception e) {
			e.printStackTrace();
			errorHandler(loginView, e);
		} finally {

			// ログイン失敗
			if (loginView != null && loginView.isVisible()) {

				// 入力項目制御
				controllItems(true);
			}
		}

	}

	/**
	 * ログイン情報初期化
	 */
	protected void initLoginInformation() {

		String token = Util.avoidNull(ClientConfig.getProperty("trans.single.login.token"));
		String[] arr = UTF8EncryptUtil.getArray(token);

		if (arr != null && arr.length > 2) {
			loginView.txCompanyCode.setValue(arr[0]);
			loginView.txUserCode.setValue(arr[1]);
			loginView.txPassword.setValue(arr[2]);
		}

		// 入力項目制御
		controllItems(false);
	}

	/**
	 * 入力項目制御
	 * 
	 * @param enable true:入力可能
	 */
	protected void controllItems(boolean enable) {
		loginView.txCompanyCode.setEditable(enable);
		loginView.txUserCode.setEditable(enable);
		loginView.txPassword.setEditable(enable);
		loginView.btnLogin.setEnabled(enable);
		loginView.btnClear.setEnabled(enable);
		loginView.btnClose.setEnabled(enable);
	}

}
