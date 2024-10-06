package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.model.history.*;

/**
 * 承認履歴ダイアログ
 */
public class TApproveHistoryDialog extends TDialog {

	/** 閉じるボタン */
	public TButton btnReturn;

	/** スプレッド */
	public TTable tbl;

	/** コントローラー */
	public TApproveHistoryDialogCtrl controller;

	/**
	 * @param parent
	 * @param mordal
	 */
	public TApproveHistoryDialog(Frame parent, boolean mordal) {
		super(parent, mordal);

		controller = createController();
	}

	/**
	 * @return コントローラー
	 */
	public TApproveHistoryDialogCtrl createController() {
		return new TApproveHistoryDialogCtrl(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {
		btnReturn = new TButton();
		tbl = new TTable();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setResizable(true);

		setPreferredSize(new Dimension(800, 520));

		setTitle(getWord("C11755")); // 承認履歴

		int x = getPreferredSize().width - 160;
		int y = 10;

		// 閉じるボタン
		btnReturn.setText(getWord("C02374")); // 閉じる
		btnReturn.setSize(25, 120);
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setLocation(x, y);
		pnlHeader.add(btnReturn);

		// 一覧
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlBody.add(tbl, gc);

		initSpread();

		pack();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		tbl.setTabControlNo(i++);
		btnReturn.setTabControlNo(i++);
	}

	/**
	 * スプレッド初期化
	 */
	public void initSpread() {
		tbl.addColumn(SC.companyCode, getWord("C00596"), 75); // 会社コード
		tbl.addColumn(SC.slipDate, getWord("C00599"), 75, SwingConstants.CENTER); // 伝票日付
		tbl.addColumn(SC.slipNo, getWord("C00605"), 110); // 伝票番号
		tbl.addColumn(SC.slipState, getWord("C01069"), 75, SwingConstants.CENTER); // 更新区分
		tbl.addColumn(SC.employeeCode, getWord("C00363"), 75); // 担当者
		tbl.addColumn(SC.employeeName, getWord("C11296"), 95); // 担当者名称
		tbl.addColumn(SC.employeeNameS, getWord("C11756"), 0); // 担当者略称
		tbl.addColumn(SC.approveFlag, getWord("C02829"), 75, SwingConstants.CENTER); // 処理区分
		tbl.addColumn(SC.approveDate, getWord("C11757"), 115, SwingConstants.CENTER); // 処理日時
	}

	/**
	 * SC
	 */
	public enum SC {
		/** 会社コード */
		companyCode,

		/** 伝票日付 */
		slipDate,

		/** 伝票番号 */
		slipNo,

		/** 更新区分 */
		slipState,

		/** 担当者 */
		employeeCode,

		/** 担当者名称 */
		employeeName,

		/** 担当者略称 */
		employeeNameS,

		/** 処理区分 */
		approveFlag,

		/** 処理日時 */
		approveDate
	}

	/**
	 * データ設定
	 * 
	 * @param list
	 */
	public void setData(List<ApproveHistory> list) {
		controller.setData(list);
	}

}
