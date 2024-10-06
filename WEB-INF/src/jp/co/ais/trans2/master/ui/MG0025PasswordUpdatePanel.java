package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * パスワード変更の画面レイアウト
 * 
 * @author AIS
 */
public class MG0025PasswordUpdatePanel extends TMainPanel {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** ログインユーザーコード */
	public TLabelField ctrlUserCode;

	/** ログインユーザー名称 */
	public TTextField ctrlUserName;

	/** 新しいパスワード */
	public TLabelPasswordField ctrlNewPassword;

	/** 新しいパスワード（確認） */
	public TLabelPasswordField ctrlConfNewPassword;

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		ctrlUserCode = new TLabelField();
		ctrlUserName = new TTextField();
		ctrlNewPassword = new TLabelPasswordField();
		ctrlConfNewPassword = new TLabelPasswordField();
	}

	@Override
	public void allocateComponents() {

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(1000, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 800));
		pnlBodyTop.setMinimumSize(new Dimension(0, 800));
		pnlBodyTop.setPreferredSize(new Dimension(0, 800));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ログインユーザーコード
		ctrlUserCode.setLangMessageID("C00993");
		ctrlUserCode.setLabelSize(110);
		ctrlUserCode.setEnabled(true);
		ctrlUserCode.setFieldSize(120);
		ctrlUserCode.setMaxLength(TransUtil.USER_CODE_LENGTH);
		ctrlUserCode.setLocation(80, 0);
		pnlBodyTop.add(ctrlUserCode);

		// ログインユーザー名称
		ctrlUserName.setEnabled(true);
		ctrlUserName.setSize(440, 20);
		ctrlUserName.setLocation(315, 0);
		ctrlUserName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBodyTop.add(ctrlUserName);

		// 新しいパスワード
		ctrlNewPassword.setLangMessageID("C00742");
		ctrlNewPassword.setLabelSize(110);
		ctrlNewPassword.setFieldSize(120);
		ctrlNewPassword.setMaxLength(TransUtil.PASSWORD_LENGTH);
		ctrlNewPassword.setLocation(ctrlUserCode.getX(), ctrlUserCode.getHeight() + 5);
		pnlBodyTop.add(ctrlNewPassword);

		// 新しいパスワード（確認用）
		ctrlConfNewPassword.setLangMessageID("C00305");
		ctrlConfNewPassword.setLabelSize(110);
		ctrlConfNewPassword.setFieldSize(120);
		ctrlConfNewPassword.setMaxLength(TransUtil.PASSWORD_LENGTH);
		ctrlConfNewPassword.setLocation(ctrlUserCode.getX(), ctrlUserCode.getHeight() + 30);
		pnlBodyTop.add(ctrlConfNewPassword);

		// 下部
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlNewPassword.setTabControlNo(i++);
		ctrlConfNewPassword.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
	}
}
