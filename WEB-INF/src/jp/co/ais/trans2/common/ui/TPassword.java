package jp.co.ais.trans2.common.ui;

import java.awt.Frame;
import java.awt.event.KeyEvent;

import jp.co.ais.trans.common.gui.TButton;
import jp.co.ais.trans.common.gui.TLabelField;
import jp.co.ais.trans.common.gui.TLabelPasswordField;
import jp.co.ais.trans.common.gui.TTextField;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * パスワード変更画面
 * 
 * @author AIS
 */
public class TPassword extends TDialog {

	public TPassword(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	/** serialVersionUID */
	private static final long serialVersionUID = 2048709526982899509L;

	/** 確定ボタン */
	protected TButton btnSettle;

	/** 戻るボタン */
	protected TButton btnBack;

	/** ユーザーコード */
	protected TLabelField txUserCode;

	/** ユーザー略称 */
	protected TTextField txUserNames;

	/** 新パスワード */
	protected TLabelPasswordField txNewPassword;

	/** 新パスワード(確認) */
	protected TLabelPasswordField txNewPasswordRe;

	@Override
	public void initComponents() {
		btnSettle = new TButton();
		btnBack = new TButton();
		txUserCode = new TLabelField();
		txUserNames = new TTextField();
		txNewPassword = new TLabelPasswordField();
		txNewPasswordRe = new TLabelPasswordField();
	}

	@Override
	public void allocateComponents() {

		setTitle(getWord("C02318"));// パスワード変更

		setSize(500, 200);

		// 確定ボタン
		btnSettle.setSize(25, 110);
		btnSettle.setLangMessageID("C01019");// 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setLocation(265 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnBack.setSize(25, 110);
		btnBack.setLangMessageID("C01747");// 戻る
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		btnBack.setLocation(375, HEADER_Y);
		pnlHeader.add(btnBack);

		pnlBody.setLayout(null);

		// ユーザーコード
		txUserCode.setLabelSize(110);
		txUserCode.setFieldSize(75);
		txUserCode.setSize(190, 20);
		txUserCode.setLocation(10, 15);
		txUserCode.setEditable(false);
		txUserCode.setLangMessageID("C00993");// ログインユーザー
		txUserCode.setMaxLength(TransUtil.USER_CODE_LENGTH);
		pnlBody.add(txUserCode);

		// ユーザー略称
		txUserNames.setSize(230, 20);
		txUserNames.setLocation(200, 15);
		txUserNames.setEditable(false);
		pnlBody.add(txUserNames);

		// 新パスワード
		txNewPassword.setLabelSize(110);
		txNewPassword.setFieldSize(75);
		txNewPassword.setSize(190, 20);
		txNewPassword.setLocation(10, 40);
		txNewPassword.setLangMessageID("C00742");// 新パスワード
		txNewPassword.setMaxLength(TransUtil.PASSWORD_LENGTH);
		pnlBody.add(txNewPassword);

		// 新パスワード(確認)
		txNewPasswordRe.setLabelSize(110);
		txNewPasswordRe.setFieldSize(75);
		txNewPasswordRe.setSize(190, 20);
		txNewPasswordRe.setLocation(10, 65);
		txNewPasswordRe.setLangMessageID("C00305");// 新パスワード(確認)
		txNewPasswordRe.setMaxLength(TransUtil.PASSWORD_LENGTH);
		pnlBody.add(txNewPasswordRe);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		txNewPassword.setTabControlNo(i++);
		txNewPasswordRe.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
	}

}
