package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;

/**
 * ワークフロー承認時<br>
 * 挙動選択オプション
 */
public class TApproveOptionDialog extends TDialog {

	/** 上部パネル */
	protected TPanel pnlTop;

	/** 中部パネル */
	protected TPanel pnlMiddle;

	/** ボタンパネル */
	protected TPanel pnlBottom;

	/** OKボタン */
	protected TButton btnOK;

	/** キャンセルボタン */
	protected TButton btnCancel;

	/** スキップボタン */
	protected TCheckBox chkSkip;

	/** 画面コントローラ */
	protected TApproveOptionDialogCtrl controller;

	/** メッセージ */
	TLabel lblCaption;

	/** 結果 */
	public int option = JOptionPane.CANCEL_OPTION;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public TApproveOptionDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
		// コントローラ初期化
		initController();
	}

	@Override
	protected void init() {
		setLayout(new GridBagLayout());
		setResizable(true);
		setSize(450, 150);

	}

	/**
	 * コントローラ初期化
	 */
	protected void initController() {
		this.controller = new TApproveOptionDialogCtrl(this);
	}

	@Override
	public void initComponents() {
		pnlTop = new TPanel();
		pnlMiddle = new TPanel();
		pnlBottom = new TPanel();

		btnOK = new TButton();
		btnCancel = new TButton();

		lblCaption = new TLabel();
		chkSkip = new TCheckBox();

	}

	@Override
	public void allocateComponents() {
		// キャプション設定
		initCaption();
		// ボディ部
		allocateBody();
		// ボタン部
		allocateButtons();
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		chkSkip.setTabControlNo(i++);
		btnOK.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
	}

	/**
	 * 
	 */
	protected void allocateButtons() {
		btnOK.setMinimumSize(new Dimension(100, 25));
		btnOK.setMaximumSize(new Dimension(100, 25));
		btnOK.setPreferredSize(new Dimension(100, 25));
		// 了解ボタンを下部パネルへ配置
		gc.gridy = 0;
		gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		pnlBottom.add(btnOK, gc);

		btnCancel.setMinimumSize(new Dimension(100, 25));
		btnCancel.setMaximumSize(new Dimension(100, 25));
		btnCancel.setPreferredSize(new Dimension(100, 25));
		btnCancel.setForClose(true);
		// キャンセルボタンを下部パネルへ配置
		gc.gridx = 1;
		gc.insets = new Insets(0, 5, 0, 0);
		pnlBottom.add(btnCancel, gc);

	}

	/**
	 * 文字キャプション初期化
	 */
	protected void initCaption() {
		lblCaption.setLangMessageID(getMessage("Q00004")); // 確定します。よろしいですか？
		chkSkip.setLangMessageID("進行可能な箇所まで承認する");

		btnOK.setText("<html>" + getWord("C10794") + "(<u>Y</u>)</html>"); // はい
		btnCancel.setText("<html>" + getWord("C10795") + "(<u>N</u>)</html>"); // いいえ
	}

	/**
	 * ボディ部配置
	 */
	protected void allocateBody() {

		TGuiUtil.setComponentSize(pnlTop, 0, 30);
		pnlTop.setLayout(new GridBagLayout());

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
		pnlTop.add(lblCaption, gc);

		// 中部パネル
		gc.gridy += 1;
		TGuiUtil.setComponentSize(pnlMiddle, 0, 40);
		pnlMiddle.setLayout(null);
		add(pnlMiddle, gc);

		// スナップショット取得要否
		TGuiUtil.setComponentSize(chkSkip, 200, 20);
		chkSkip.setLocation(80, 0);
		pnlMiddle.add(chkSkip);

		// 下部
		pnlBottom.setLayout(new GridBagLayout());
		pnlBottom.setMinimumSize(new Dimension(0, 40));
		pnlBottom.setMaximumSize(new Dimension(0, 40));
		pnlBottom.setPreferredSize(new Dimension(0, 40));

		// 下部パネルをメインパネルへ配置
		gc.gridy += 1;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlBottom, gc);

	}

	/**
	 * OKボタン押下したか
	 * 
	 * @return true:OK
	 */
	public boolean isOK() {
		return option == JOptionPane.OK_OPTION;
	}

	/**
	 * 可能な限り承認を進めるか
	 * 
	 * @return true:進める
	 */
	public boolean isApproveAsMuchAsPossible() {
		return chkSkip.isSelected();
	}
}
