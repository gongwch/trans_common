package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;

/**
 * 取引先マスタの担当者設定画面
 * 
 * @author AIS
 */
public class MG0150CustomerUsrMasterDialog extends TDialog {

	/**
	 * 担当者設定画面 TriColumダイアログ一覧 カラム
	 */
	public enum TriColum {

		/** BEAN */
		BEAN,

		/** システム区分 */
		SYS_KBN,

		/** 担当者名 */
		USR_NAME,

		/** 部署名 */
		DEP_NAME,

		/** 役職 */
		POSITION

	}

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 取引先コード */
	public TLabelField ctrlTriCode;

	/** 取引先名称 */
	public TTextField ctrlTriName;

	/** 一覧 */
	public TTable tbl;

	/**
	 * @param parent
	 * @param mordal
	 */
	public MG0150CustomerUsrMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlTriCode = new TLabelField();
		ctrlTriName = new TTextField();
		tbl = new TTable();
		initTable();
	}

	/**
	 * 一覧初期化
	 */
	protected void initTable() {

		TTextField textField = new TTextField();
		textField.setMaxLength(40);

		tbl.addColumn(TriColum.BEAN, "", SwingConstants.LEFT, 0, false);
		tbl.addColumn(TriColum.SYS_KBN, getWord("C00980"), 80, SwingConstants.CENTER, true);
		tbl.addColumn(TriColum.USR_NAME, getWord("C11296"), 150, textField, true);
		tbl.addColumn(TriColum.DEP_NAME, getWord("NK0182") + getWord("C00518"), 150, textField, true);
		tbl.addColumn(TriColum.POSITION, getWord("C12094"), 100, textField, true);

	}

	@Override
	public void allocateComponents() {

		setSize(600, 400);
		int y = HEADER_Y;

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(new GridBagLayout());
		y = 10;

		// 上部
		TPanel pnlBodyUP = new TPanel();
		pnlBodyUP.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0d;
		gc.weighty = 0.1d;
		gc.fill = GridBagConstraints.WEST;
		gc.insets = new Insets(0, 0, 0, 140);
		pnlBody.add(pnlBodyUP, gc);

		// 取引先コード
		ctrlTriCode.setFieldSize(80);
		ctrlTriCode.setLocation(0, y);
		ctrlTriCode.setMaxLength(10);
		ctrlTriCode.setLangMessageID("C00408");
		ctrlTriCode.setEditable(false);
		ctrlTriCode.setMinimumSize(new Dimension(150, 20));
		ctrlTriCode.setMaximumSize(new Dimension(150, 20));
		ctrlTriCode.setPreferredSize(new Dimension(150, 20));
		pnlBodyUP.add(ctrlTriCode);

		// 取引先名称
		ctrlTriName.setLocation(205, y);
		ctrlTriName.setEditable(false);
		ctrlTriName.setMinimumSize(new Dimension(300, 20));
		ctrlTriName.setMaximumSize(new Dimension(300, 20));
		ctrlTriName.setPreferredSize(new Dimension(300, 20));
		pnlBodyUP.add(ctrlTriName);

		// 下部
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// 一覧
		tbl.setMinimumSize(new Dimension(0, 180));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 0.5d;
		gc.weighty = 0.5d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 20, 20, 20);
		pnlBodyButtom.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
