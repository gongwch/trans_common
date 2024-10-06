package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 銀行マスタの指示画面
 */
public class MG0140BankMasterPanel extends TMainPanel {

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 編集ボタン */
	public TImageButton btnModify;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 銀行検索フィールド */
	public TBankReference ctrlBank;

	/** 支店検索フィールド */
	public TBranchReferenceRange ctrlBranch;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 一覧 */
	public TTable tbl;

	/** 有効期間切れを表示するか */
	public TCheckBox chkOutputTermEnd;

	/**
	 * 一覧のカラム定義
	 */
	public enum SC {

		/** 銀行コード */
		BnkCode,

		/** 銀行支店コード */
		BranchCode,

		/** 銀行名称 */
		BnkName,

		/** 銀行カナ名称 */
		BnkKana,

		/** 銀行検索名称 */
		BnkNamek,

		/** 銀行支店名称 */
		BranchName,

		/** 銀行支店カナ名称 */
		BranchKana,

		/** 銀行支店検索名称 */
		BranchNamek,

		/** 開始年月日 */
		DateFrom,

		/** 終了年月日 */
		DateTo
	}

	/**
	 * コンストラクタ
	 */
	public MG0140BankMasterPanel() {
		super();
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		ctrlBank = new TBankReference();
		ctrlBranch = new TBranchReferenceRange();
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.BnkCode, getWord("C00779"), 100);
		tbl.addColumn(SC.BranchCode, getWord("C00780"), 100);
		tbl.addColumn(SC.BnkName, getWord("C00781"), 300);
		tbl.addColumn(SC.BnkKana, getWord("C00782"), 200);
		tbl.addColumn(SC.BnkNamek, getWord("C00829"), 300);
		tbl.addColumn(SC.BranchName, getWord("C00783"), 100);
		tbl.addColumn(SC.BranchKana, getWord("C00784"), 200);
		tbl.addColumn(SC.BranchNamek, getWord("C00785"), 300);
		tbl.addColumn(SC.DateFrom, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.DateTo, getWord("C00261"), 100, SwingConstants.CENTER);

		tbl.setRowColumnWidth(38);
	}

	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 銀行検索フィールド
		ctrlBank.setSize(320, 30);
		ctrlBank.setLocation(35, 30);
		pnlBodyTop.add(ctrlBank);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C00433")));
		pnlSearchCondition.setLocation(370, 10);
		pnlSearchCondition.setSize(500, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 支店検索フィールド
		ctrlBranch.setSize(420, 50);
		ctrlBranch.setLocation(30, 20);
		pnlSearchCondition.add(ctrlBranch);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(chkOutputTermEnd);

		// ダブルクリック処理
		tbl.addSpreadSheetSelectChange(btnModify);

		// 下部
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {

		int i = 0;
		ctrlBank.setTabControlNo(i++);
		ctrlBranch.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}