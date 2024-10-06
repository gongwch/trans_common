package jp.co.ais.trans2.common.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.gui.TFrame;
import jp.co.ais.trans.common.gui.TPanelBusiness;
import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.client.TController;
import jp.co.ais.trans2.common.exception.TInformationException;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.security.PasswordManager;
import jp.co.ais.trans2.model.user.User;

/**
 * パスワード変更のコントローラ。<BR>
 * 
 * @author AIS
 */
public class TPasswordCtrl extends TController {

	/** 呼び出し元親フレーム */
	protected TFrame parentFrame = null;

	/**
	 * パスワード変更画面
	 */
	protected TPassword view = null;

	/**
	 * パスワード変更がなされたか
	 */
	protected boolean isPasswordChanged = false;

	/**
	 * 親フレームセット
	 * 
	 * @param parentFrame 親フレーム
	 */
	public TPasswordCtrl(TFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public void start() {

		try {

			// パスワード変更画面生成
			view = createView();

			// 画面初期化
			initView();

			// 表示
			view.setLocationRelativeTo(null);
			view.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * パスワード変更画面を初期化する
	 */
	protected void initView() {

		// ユーザー
		view.txUserCode.setValue(getUser().getCode());
		view.txUserNames.setValue(getUser().getNames());

		// イベント定義
		initViewEvent();

	}

	/**
	 * ログイン画面のイベント定義
	 */
	protected void initViewEvent() {

		// 確定ボタン押下
		view.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused")
			ActionEvent e) {
				btnSettle_Click();
			}
		});

		// 戻るボタン押下
		view.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused")
			ActionEvent e) {
				btnBack_Click();
			}
		});

	}

	/**
	 * パスワード変更画面のファクトリ
	 * 
	 * @return パスワード変更画面
	 */
	protected TPassword createView() {
		return new TPassword(parentFrame, true);
	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェック
			if (!isInputCorrect()) {
				return;
			}

			// パスワード変更処理
			try {
				updatePassword(getCompany(), getUser(), view.txNewPassword.getValue());
			} catch (TInformationException e) {
				showMessage(view, e.getMessageID(), e.getMessageArgs());
				view.txNewPassword.requestTextFocus();
				return;
			}

			setPasswordChanged(true);
			view.setVisible(false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [戻る]ボタン押下
	 */
	protected void btnBack_Click() {

		try {
			view.setVisible(false);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 入力が正しいかを返す
	 * 
	 * @return true(正常) / false(エラー)
	 */
	protected boolean isInputCorrect() {

		// 新パスワードが未入力の場合エラー
		if (Util.isNullOrEmpty(view.txNewPassword.getValue())) {
			showMessage(view, "I00139");// パスワードを入力してください。
			view.txNewPassword.requestFocus();
			return false;
		}

		// 新パスワード(確認)が未入力の場合エラー
		if (Util.isNullOrEmpty(view.txNewPasswordRe.getValue())) {
			showMessage(view, "I00140");// パスワード(確認)を入力してください。
			view.txNewPasswordRe.requestFocus();
			return false;
		}

		// 新パスワードとその確認が一致しない場合エラー
		if (!view.txNewPassword.getValue().equals(view.txNewPasswordRe.getValue())) {
			showMessage(view, "I00141");// パスワードが一致しません。
			view.txNewPassword.requestFocus();
			return false;
		}

		return true;

	}

	public boolean isPasswordChanged() {
		return isPasswordChanged;
	}

	public void setPasswordChanged(boolean isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * パスワードを更新する
	 * 
	 * @param company 会社
	 * @param user ユーザー
	 * @param password パスワード
	 */
	protected void updatePassword(Company company, User user, String password) throws TInformationException, TException {
		request(PasswordManager.class, "updatePassword", company, user, password);
	}

}
