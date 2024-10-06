package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MG0160EmployeeMaster - 社員マスタ - Main Panel Class
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterPanel extends TMainPanel {

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

	/** 上部 */
	public TPanel pnlBodyTop;

	/** 社員範囲検索パネル */
	public TPanel pnlEmpRefRan;

	/** 社員範囲検索 */
	public TEmployeeReferenceRange ctrlEmpRefRan;

	/** 有効期間切れ */
	public TCheckBox chkTerm;

	/** 下部 */
	public TPanel pnlBodyBottom;

	/** 社員マスタ一覧 */
	public TTable tbList;

	/** 一覧のカラム定義 */
	public enum SC {

		/** 社員コード */
		CODE,

		/** 社員名称 */
		NAME,

		/** 社員略称 */
		NAME_S,

		/** 社員検索名称 */
		NAME_K,

		/** 振出銀行口座コード */
		CBK_CODE,

		/** 振出銀行口座略称 */
		CBK_NAME,

		/** 振込銀行コード */
		BNK_CODE,

		/** 振込銀行略称 */
		BNK_NAME,

		/** 振込支店コード */
		STN_CODE,

		/** 振込支店略称 */
		STN_NAME,

		/** 振込口座現預金種別 */
		KOZA_KBN,

		/** 振込口座番号 */
		YKN_NO,

		/** 口座名義カナ */
		YKN_KANA,

		/** 開始年月日 */
		DATE_FROM,

		/** 終了年月日 */
		DATE_TO,

		/** Entity */
		ENTITY
	}

	@SuppressWarnings("static-access")
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		pnlEmpRefRan = new TPanel();
		ctrlEmpRefRan = new TEmployeeReferenceRange();
		chkTerm = new TCheckBox();
		pnlBodyBottom = new TPanel();

		tbList = new TTable();
		tbList.addColumn(SC.CODE, getWord("C00697"), 120);
		tbList.addColumn(SC.NAME, getWord("C00807"), 200);
		tbList.addColumn(SC.NAME_S, getWord("C00808"), 200);
		tbList.addColumn(SC.NAME_K, getWord("C00809"), 250);
		tbList.addColumn(SC.CBK_CODE, getWord("C00810"), 120);
		tbList.addColumn(SC.CBK_NAME, getWord("C00475"), 200);
		tbList.addColumn(SC.BNK_CODE, getWord("C00811"), 120);
		tbList.addColumn(SC.BNK_NAME, getWord("C02470"), 200);
		tbList.addColumn(SC.STN_CODE, getWord("C00812"), 120);
		tbList.addColumn(SC.STN_NAME, getWord("C00473"), 200);
		tbList.addColumn(SC.KOZA_KBN, getWord("C00471"), 100, 0);
		tbList.addColumn(SC.YKN_NO, getWord("C00813"), 100);
		tbList.addColumn(SC.YKN_KANA, getWord("C01068"), 200);
		tbList.addColumn(SC.DATE_FROM, getWord("C00055"), 100, 0);
		tbList.addColumn(SC.DATE_TO, getWord("C00261"), 100, 0);
		tbList.addColumn(SC.ENTITY, "", -1, false);
	}

	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setSize(25, 110);
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setSize(25, 110);
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setSize(25, 110);
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbList.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setSize(25, 110);
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setSize(25, 110);
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setSize(25, 130);
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 上部
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 社員範囲パネル
		pnlEmpRefRan.setLayout(null);
		pnlEmpRefRan.setSize(600, 75);
		pnlEmpRefRan.setLangMessageID("C01060");
		pnlEmpRefRan.setLocation(30, 10);
		pnlBodyTop.add(pnlEmpRefRan);

		// 社員範囲
		ctrlEmpRefRan.setLocation(20, 20);
		pnlEmpRefRan.add(ctrlEmpRefRan);

		// メッセージ一覧にデータがない。追加必要 ##########
		// 有効期間切れ表示
		chkTerm.setLangMessageID("C11089");
		chkTerm.setSize(140, 20);
		chkTerm.setLocation(360, 40);
		pnlEmpRefRan.add(chkTerm);

		// 下部
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbList, gc);
	}

	@Override
	// Tab順定義
	public void setTabIndex() {
		int i = 1;
		ctrlEmpRefRan.setTabControlNo(i++);
		chkTerm.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}