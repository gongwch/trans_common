package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;

/**
 * 銀行統廃合
 */
public class MG0450BankMergePanel extends TMainPanel {

	/** エクセル取込 */
	public TButton btnImportExcel;

	/** 実行 */
	public TButton btnExecute;

	/** 更新対象銀行一覧 */
	public TTable tblImportList;

	/** エラー内容一覧 */
	public TTable tblErrList;

	/** 処理確認一覧 */
	public TTable tblCompList;

	/** 更新対象銀行ラベル */
	public TLabel labImportList;

	/** エラー内容一覧ラベル */
	public TLabel labErrList;

	/** 処理確認一覧 */
	public TLabel labCompList;

	/**
	 * 更新対象銀行一覧
	 */
	public enum IN {
		/** チェック */
		CHK_BOX,
		/** 新銀行名 */
		NEW_BANK_NAME,
		/** 旧銀行名 */
		OLD_BANK_NAME,
		/** 支店数 */
		COUNT_BANK_OFFICE,
		/** Entity */
		ENTITY
	}

	/**
	 * エラー内容一覧
	 */
	public enum ERR {
		/** 行 */
		LINE,
		/** エラー内容 */
		ERROR_DETAIL,
		/** Entity */
		ENTITY
	}

	/**
	 * 処理確認一覧
	 */
	public enum COMP {
		/** マスタ名 */
		MASTER_NAME,
		/** 追加件数 */
		COUNT_ADD,
		/** 更新件数 */
		COUNT_UPDATE,
		/** Entity */
		ENTITY
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {

		btnImportExcel = new TButton();
		btnExecute = new TButton();
		labImportList = new TLabel("C11788"); // 更新対象銀行
		labErrList = new TLabel("C11789"); // エラー内容一覧
		labCompList = new TLabel("C11790"); // 処理確認一覧

		tblImportList = new TTable();
		tblImportList.addColumn(IN.CHK_BOX, "", 30, TCheckBox.class);
		tblImportList.addColumn(IN.NEW_BANK_NAME, "C11791", 200); // 新銀行名
		tblImportList.addColumn(IN.OLD_BANK_NAME, "C11792", 200); // 旧銀行名
		tblImportList.addColumn(IN.COUNT_BANK_OFFICE, "C11793", 200, SwingConstants.RIGHT); // 支店数
		tblImportList.addColumn(IN.ENTITY, "", -1, false);

		tblErrList = new TTable();
		tblErrList.addColumn(ERR.LINE, "C00118", 230, SwingConstants.RIGHT); // 行
		tblErrList.addColumn(ERR.ERROR_DETAIL, "C01589", 400); // エラー内容
		tblErrList.addColumn(ERR.ENTITY, "", -1, false);

		tblCompList = new TTable();
		tblCompList.addColumn(COMP.MASTER_NAME, "C11794", 230); // マスタ名
		tblCompList.addColumn(COMP.COUNT_ADD, "C11795", 200, SwingConstants.RIGHT); // 追加件数
		tblCompList.addColumn(COMP.COUNT_UPDATE, "C11796", 200, SwingConstants.RIGHT); // 更新件数
		tblCompList.addColumn(COMP.ENTITY, "", -1, false);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// エクセル取込ボタン
		int x;
		x = HEADER_LEFT_X;
		btnImportExcel.setLangMessageID("C03259"); // エクセル取込
		btnImportExcel.setShortcutKey(KeyEvent.VK_F1);
		btnImportExcel.setSize(25, 120);
		btnImportExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnImportExcel);

		// 実行ボタン
		x = x + btnImportExcel.getWidth() + HEADER_MARGIN_X;
		btnExecute.setLangMessageID("C00218"); // 実行
		btnExecute.setShortcutKey(KeyEvent.VK_F8);
		btnExecute.setSize(25, 120);
		btnExecute.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExecute);

		// 更新対象銀行
		gc = new GridBagConstraints();
		gc.gridy = 0;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, HEADER_LEFT_X, 0, 10);
		pnlBody.add(labImportList, gc);

		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, HEADER_LEFT_X, 2, 10);
		pnlBody.add(tblImportList, gc);

		// エラー内容一覧
		gc = new GridBagConstraints();
		gc.gridy = 2;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, HEADER_LEFT_X, 0, 10);
		pnlBody.add(labErrList, gc);

		gc.gridy = 3;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, HEADER_LEFT_X, 2, 10);
		pnlBody.add(tblErrList, gc);

		// 処理確認一覧
		gc = new GridBagConstraints();
		gc.gridy = 4;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, HEADER_LEFT_X, 0, 10);
		pnlBody.add(labCompList, gc);

		gc.gridy = 5;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, HEADER_LEFT_X, 0, 10);
		pnlBody.add(tblCompList, gc);

	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {

		int i = 1;

		btnImportExcel.setTabControlNo(i++);
		btnExecute.setTabControlNo(i++);
		tblImportList.setTabControlNo(i++);
		tblErrList.setTabControlNo(i++);
		tblCompList.setTabControlNo(i++);

	}

}
