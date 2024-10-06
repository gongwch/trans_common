package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.io.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * パスワード変更ダイアログコントロール
 */
public class PasswordUpdateDialogCtrl extends TAppletClientBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0025";

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "common/ChangePassword";

	/** ダイアログ */
	protected PasswordUpdateDialog dialog;

	/**
	 * コンストラクタ
	 * 
	 * @param parent パネル
	 */
	public PasswordUpdateDialogCtrl(Frame parent) {
		try {
			dialog = new PasswordUpdateDialog(parent, this);

			// ユーザーコードとユーザー名称の表示
			dialog.ctrlUserCode.getField().setText(getLoginUserID());
			dialog.txtUserName.setText(getLoginUserName());

		} catch (Exception e) {
			errorHandler(dialog, e, "E00010");
		}
	}

	/**
	 * 表示
	 */
	public void show() {
		dialog.setVisible(true);
	}

	/**
	 * 閉じる
	 */
	protected void disposeDialog() {
		try {
			// 確定ボタンかどうかチェック
			if (!dialog.isSettle) {
				dialog.setVisible(false);
				return;
			}

			// 確定ボタン押下
			if (!checkField()) {
				return;
			}

			addSendValues("newPassword", new String(dialog.ctrlNewPassword.getField().getPassword()));
			addSendValues("prgID", PROGRAM_ID);

			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return;
			}

			showMessage(dialog, "I00008");

			dialog.setVisible(false);

		} catch (IOException ex) {
			errorHandler(dialog, ex, "E00009");
		}
	}

	/**
	 * 入力チェック
	 * 
	 * @return true 入力に問題なし false 問題あり
	 */
	protected boolean checkField() {

		if (Util.isNullOrEmpty(dialog.ctrlNewPassword.getValue())) {
			showMessage(dialog, "I00002", "C00742");
			dialog.ctrlNewPassword.getField().requestFocus();
			return false;
		}

		if (Util.isNullOrEmpty(dialog.ctrlNewPasswordConfirmation.getValue())) {
			showMessage(dialog, "I00002", "C00305");
			dialog.ctrlNewPasswordConfirmation.getField().requestFocus();
			return false;
		}

		if (!dialog.ctrlNewPassword.getValue().equals(dialog.ctrlNewPasswordConfirmation.getValue())) {
			showMessage(dialog, "W00074", "");
			dialog.ctrlNewPasswordConfirmation.getField().requestFocus();
			return false;
		}

		return true;
	}
}
