package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans2.common.client.*;

/**
 * ワークフロー承認<br>
 * 承認時オプション選択画面
 */
public class TApproveOptionDialogCtrl extends TController {

	/** ダイアログ */
	protected TApproveOptionDialog dialog;

	/**
	 * コンストラクタ
	 * 
	 * @param dialog
	 */
	public TApproveOptionDialogCtrl(TApproveOptionDialog dialog) {
		this.dialog = dialog;
		init();
	}

	/** 初期化処理 */
	protected void init() {
		addEvent();
	}

	/**
	 * 画面イベント定義
	 */
	protected void addEvent() {
		dialog.btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOK_Clicked();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		dialog.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCancel_Clicked();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * OKボタン押下時の処理
	 */
	protected void btnOK_Clicked() {
		dialog.option = JOptionPane.OK_OPTION;
		close();
	}

	/**
	 * キャンセルボタン押下時の処理
	 */
	protected void btnCancel_Clicked() {
		close();
	}

	/**
	 * 画面を閉じる
	 */
	protected void close() {
		dialog.setVisible(false);
	}

}
