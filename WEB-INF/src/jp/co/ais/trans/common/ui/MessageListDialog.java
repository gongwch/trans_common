package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable.AutoSizeType;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * メッセージ一覧表示ダイアログ
 */
public class MessageListDialog extends TDialog {

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

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 */
	public MessageListDialog(Frame parent) {
		super(parent, true);

		init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親ダイアログ
	 */
	public MessageListDialog(Dialog parent) {
		super(parent, true);

		init();
	}

	/**
	 * メッセージリスト
	 * 
	 * @param msgList メッセージリスト
	 */
	public void setMessageList(List<String> msgList) {

		List<String[]> list = new ArrayList<String[]>(msgList.size());

		for (String msg : msgList) {
			list.add(new String[] { "", msg });
		}

		this.messageList = list;
	}

	/**
	 * メッセージリスト
	 * 
	 * @param msgList メッセージリスト
	 */
	public void setMessagesList(List<String[]> msgList) {
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
		String btnWord = ClientConfig.getOkButtonText();
		if (Util.isNullOrEmpty(btnWord)) {
			btnWord = "C04284";
		}
		btnOK.setLangMessageID(btnWord);
		btnOK.setMinimumSize(new Dimension(100, 25));
		btnOK.setMaximumSize(new Dimension(100, 25));
		btnOK.setPreferredSize(new Dimension(100, 25));
		btnOK.setTabControlNo(1);
		btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// 画面を閉じる
				setVisible(false);
			}
		});

		// 了解ボタンを下部パネルへ配置
		gc.gridy = 0;
		gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		pnlBottom.add(btnOK, gc);
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
	 */
	public void show(String title) {
		if (Util.isNullOrEmpty(title)) {
			this.pnlTop.setVisible(false);
		} else {
			this.lblMessage.setText(title);
		}

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
	}
}