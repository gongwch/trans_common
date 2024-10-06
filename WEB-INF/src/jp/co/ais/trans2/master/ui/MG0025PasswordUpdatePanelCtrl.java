package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.passwordupdate.*;

/**
 * パスワード変更のコントローラ
 * 
 * @author AIS
 */
public class MG0025PasswordUpdatePanelCtrl extends TController {

	/** 変更画面 */
	protected MG0025PasswordUpdatePanel mainView = null;

	@Override
	public void start() {

		try {

			// 指示画面生成
			createMainView();

			// 指示画面を初期化する
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 変更画面を初期化する
	 */
	protected void initMainView() {
		mainView.ctrlUserCode.getField().setText(getUserCode());
		mainView.ctrlUserCode.setEditable(false);
		mainView.ctrlUserName.setText(getUser().getName());
		mainView.ctrlUserName.setEditable(false);
		mainView.ctrlNewPassword.getField().requestFocus();
	}

	/**
	 * 変更画面のファクトリ。新規に画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0025PasswordUpdatePanel();
		addMainViewEvent();
	}

	/**
	 * 変更画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isInputCorrect()) {
				return;
			}
			// 登録しますか？
			if (!showConfirmMessage(mainView, FocusButton.YES, "Q00005")) {
				return;
			}

			// 入力された情報を取得
			PasswordUpdate passwordUpdate = getInputedPassword();

			// 更新
			request(getModelClass(), "modify", passwordUpdate);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return UserManager
	 */
	protected Class getModelClass() {
		return PasswordUpdateManager.class;
	}

	/**
	 * 変更画面で入力されたユーザーを返す
	 * 
	 * @return 変更画面で入力されたユーザー
	 */
	protected PasswordUpdate getInputedPassword() {

		PasswordUpdate PasswordUpdate = new PasswordUpdate();
		PasswordUpdate.setNewPassword(mainView.ctrlNewPassword.getValue());
		PasswordUpdate.setConfNewPassword(mainView.ctrlConfNewPassword.getValue());

		return PasswordUpdate;

	}

	/**
	 * 入力した値が妥当かをチェックする
	 * 
	 * @return 入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isInputCorrect() {
		// 新しいパスワードが未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlNewPassword.getValue())) {
			showMessage(mainView, "I00037", "C00742");
			mainView.ctrlNewPassword.requestFocus();
			return false;
		}
		// 新しいパスワード(確認)が未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlConfNewPassword.getValue())) {
			showMessage(mainView, "I00037", "C00305");
			mainView.ctrlConfNewPassword.requestFocus();
			return false;
		}

		// 新しいパスワードと確認用が異なる場合エラー
		if (!mainView.ctrlNewPassword.getValue().equals(mainView.ctrlConfNewPassword.getValue())) {
			showMessage(mainView, "W00074", "");
			mainView.ctrlConfNewPassword.getField().requestFocus();
			return false;
		}

		return true;

	}

}
