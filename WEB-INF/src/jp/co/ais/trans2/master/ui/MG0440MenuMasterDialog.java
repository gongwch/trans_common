package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;

/**
 * メニュー表示マスタの編集画面
 * 
 * @author AIS
 */
public class MG0440MenuMasterDialog extends TDialog {

	/** タブ情報 */
	public SystemizedProgram systemizedProgram = null;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** ボタンパネル */
	public TPanel pnlInput;

	/** コード */
	public TLabelField ctrlTabCode;

	/** 名称 */
	public TLabelField ctrlTabName;

	/** 色選択 */
	public TColorChooser colorChooser;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0440MenuMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		pnlInput = new TPanel();
		ctrlTabCode = new TLabelField();
		ctrlTabName = new TLabelField();
		colorChooser = new TColorChooser();
	}

	@Override
	public void allocateComponents() {

		setSize(555, 500);
		setMinimumSize(getSize());

		// 確定ボタン
		int x = 325 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019"); // 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 325;
		btnClose.setLangMessageID("C01747"); // 戻る
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlInput.setLayout(null);
		TGuiUtil.setComponentSize(pnlInput, 0, 60);

		// グループ名称
		ctrlTabCode.setLabelSize(80);
		ctrlTabCode.setFieldSize(50);
		ctrlTabCode.setSize(135, 20);
		ctrlTabCode.setLocation(10, 10);
		ctrlTabCode.setLangMessageID("C11402"); // グループコード
		ctrlTabCode.setMaxLength(2);
		ctrlTabCode.setImeMode(false);
		pnlInput.add(ctrlTabCode);

		// グループ名称
		ctrlTabName.setLabelSize(80);
		ctrlTabName.setFieldSize(300);
		ctrlTabName.setSize(385, 20);
		ctrlTabName.setLocation(ctrlTabCode.getX(), ctrlTabCode.getY() + ctrlTabCode.getHeight() + 5);
		ctrlTabName.setLangMessageID("C11404"); // グループ名称
		ctrlTabName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlInput.add(ctrlTabName);

		gc = new GridBagConstraints();
		gc.gridy = 0;
		gc.weightx = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;

		pnlBody.add(pnlInput, gc);

		// 色選択
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlBody.add(colorChooser, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlTabCode.setTabControlNo(i++);
		ctrlTabName.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

	/**
	 * タブ情報の取得
	 * 
	 * @return systemizedProgram タブ情報
	 */
	public SystemizedProgram getSystemizedProgram() {
		return systemizedProgram;
	}

	/**
	 * タブ情報の設定
	 * 
	 * @param systemizedProgram タブ情報
	 */
	public void setSystemizedProgram(SystemizedProgram systemizedProgram) {
		this.systemizedProgram = systemizedProgram;
	}

}
