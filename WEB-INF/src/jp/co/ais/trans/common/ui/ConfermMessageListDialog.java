package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable.AutoSizeType;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 確認付きメッセージ一覧表示ダイアログ
 */
public class ConfermMessageListDialog extends TDialog {

	/** キャンセル選択 */
	public static int CANCEL_OPTION = 0;

	/** OK選択 */
	public static int OK_OPTION = 1;

	/** オプション */
	protected int option = CANCEL_OPTION;

	/** 表示メッセージ */
	protected List<String[]> messageList;

	/** 上部パネル */
	protected TPanel pnlTop;

	/** タイトルラベル */
	protected TLabel lblMessage;

	/** 中部パネル */
	protected TPanel pnlMiddle;

	/** メッセージ */
	protected TTable tblMessage;

	/** ボタンパネル */
	protected TPanel pnlBottom;

	/** OK */
	protected TButton btnOK;

	/** Cancel */
	protected TButton btnCancel;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 */
	public ConfermMessageListDialog(Frame parent) {
		super(parent, true);
		init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親ダイアログ
	 */
	public ConfermMessageListDialog(Dialog parent) {
		super(parent, true);
		init();
	}

	/**
	 * メッセージのセット
	 * 
	 * @param msgList
	 */
	public void setMessageList(List<String[]> msgList) {
		this.messageList = msgList;
	}

	/**
	 * 初期処理
	 */
	protected void init() {

		// 画面構築
		initComponents();

		// テーブルの設定
		initTable();

		// ダイアログ初期化
		super.initDialog();

		// フォーカス
		btnOK.requestFocusInWindow();

		// IME禁止
		btnOK.enableInputMethods(false);
		btnCancel.enableInputMethods(false);
		enableInputMethods(false);
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		// サイズ調整可能
		setResizable(true);

		// レイアウト定義
		setLayout(new GridBagLayout());
		setSize(680, 360);

		// 中央表示
		this.setLocationRelativeTo(null);

		// GridBagConstraints
		GridBagConstraints gc;

		// 初期化
		pnlTop = new TPanel();
		lblMessage = new TLabel();
		pnlMiddle = new TPanel();
		tblMessage = new TTable();
		pnlBottom = new TPanel();
		btnOK = new TButton();
		btnCancel = new TButton();

		// 上部
		pnlTop.setLayout(new GridBagLayout());
		pnlTop.setMinimumSize(new Dimension(0, 40));
		pnlTop.setMaximumSize(new Dimension(0, 40));
		pnlTop.setPreferredSize(new Dimension(0, 40));

		// 上部パネルをメインパネルへ配置
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1.0d;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(5, 0, 0, 0);
		add(pnlTop, gc);

		// タイトルラベルを上部パネルへ配置
		gc.weightx = 0;
		pnlTop.add(lblMessage, gc);

		// 中部
		pnlMiddle.setLayout(new GridBagLayout());
		pnlMiddle.setMinimumSize(new Dimension(0, 200));
		pnlMiddle.setMaximumSize(new Dimension(0, 200));
		pnlMiddle.setPreferredSize(new Dimension(0, 200));
		pnlMiddle.setFocusable(false);

		// 中部パネルをメインパネルへ配置
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, 40, 5, 5);
		add(pnlMiddle, gc);

		// スプレッドを中部パネルへ配置
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		pnlMiddle.add(tblMessage, gc);

		// 下部
		pnlBottom.setLayout(new GridBagLayout());
		pnlBottom.setMinimumSize(new Dimension(0, 40));
		pnlBottom.setMaximumSize(new Dimension(0, 40));
		pnlBottom.setPreferredSize(new Dimension(0, 40));

		// 下部パネルをメインパネルへ配置
		gc.gridy = 2;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlBottom, gc);

		// 了解ボタン
		btnOK.setText("<html>" + getWord("C10794") + "(<u>Y</u>)</html>"); // はい
		btnOK.setMinimumSize(new Dimension(100, 25));
		btnOK.setMaximumSize(new Dimension(100, 25));
		btnOK.setPreferredSize(new Dimension(100, 25));
		btnOK.setTabControlNo(1);
		btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				option = OK_OPTION;
				setVisible(false);
			}
		});

		// 了解ボタンを下部パネルへ配置
		gc.gridy = 0;
		gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		pnlBottom.add(btnOK, gc);

		// キャンセルボタン
		btnCancel.setText("<html>" + getWord("C10795") + "(<u>N</u>)</html>"); // いいえ
		btnCancel.setMinimumSize(new Dimension(100, 25));
		btnCancel.setMaximumSize(new Dimension(100, 25));
		btnCancel.setPreferredSize(new Dimension(100, 25));
		btnCancel.setTabControlNo(2);
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				option = CANCEL_OPTION;
				setVisible(false);
			}
		});

		// キャンセルボタンを下部パネルへ配置
		gc.gridx = 1;
		gc.insets = new Insets(0, 5, 0, 0);
		pnlBottom.add(btnCancel, gc);

		// ボタン任意変更
		String[] btnWords = ClientConfig.getYesNoButtonWords();

		if (btnWords != null) {
			btnOK.setLangMessageID(btnWords[0]);
			btnCancel.setLangMessageID(btnWords[1]);
		}

		// ショートカット
		btnOK.getActionMap().put("yes", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				option = OK_OPTION;
				setVisible(false);
			}
		});

		// ショートカット
		btnCancel.getActionMap().put("no", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				option = CANCEL_OPTION;
				setVisible(false);
			}
		});

		InputMap yesMap = btnOK.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		yesMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.ALT_MASK), "yes");

		InputMap noMap = btnCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		noMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.ALT_MASK), "no");
	}

	/**
	 * テーブルの初期化
	 */
	protected void initTable() {
		tblMessage.setFocusable(false);
		tblMessage.setRowColumnWidth(0);
		tblMessage.setHeaderRowHeight(0);

		tblMessage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * 表示
	 * 
	 * @param title タイトルメッセージ
	 * @return 選択値
	 */
	public int show(String title) {
		this.lblMessage.setText(title);

		// 一覧に設定するデータを作成
		boolean hasSub = false;

		for (String[] msg : messageList) {
			hasSub |= !Util.isNullOrEmpty(msg[0]);
		}

		// 幅のスタイル設定
		if (hasSub) {
			tblMessage.addColumn("", 75);
			tblMessage.addColumn("", 255);

		} else {
			tblMessage.addColumn("", 0);
			tblMessage.addColumn("", 310);
		}

		// データを一覧に設定
		for (String[] msg : messageList) {
			tblMessage.addRow(msg);
		}

		tblMessage.autosizeColumnWidth(AutoSizeType.HeaderAndContents);

		this.setVisible(true);

		return option;
	}
}