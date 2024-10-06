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
 * MG0065ItemSystemMaster - 科目体系名称マスタ - Main Panel Class
 * 
 * @author AIS
 */
public class MG0065ItemSystemNameMasterPanel extends TMainPanel {

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

	/** 科目体系名称範囲検索パネル */
	public TPanel pnlSearchCondition;

	/** 科目体系名称範囲検索 */
	public TItemOrganizationReferenceRange ctrlItemOrgRan;

	/** 開始ラベル */
	public TLabel lblStart;

	/** 終了ラベル */
	public TLabel lblEnd;

	/** 下部 */
	public TPanel pnlBodyBottom;

	/** 科目体系名称マスタ一覧 */
	public TTable tbList;

	/** 一覧のカラム定義 */
	public enum SC {
		/** Entity */
		ENTITY

		/** 科目体系コード */
		, CODE

		/** 科目体系名称 */
		, NAME

		/** 科目体系略称 */
		, NAME_S

		/** 科目体系検索名称 */
		, NAME_K

	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		pnlSearchCondition = new TPanel();
		lblStart = new TLabel();
		lblEnd = new TLabel();
		ctrlItemOrgRan = new TItemOrganizationReferenceRange();
		pnlBodyBottom = new TPanel();

		tbList = new TTable();
		tbList.addColumn(SC.ENTITY, "", -1);
		tbList.addColumn(SC.CODE, getWord("C00617"), 120);
		tbList.addColumn(SC.NAME, getWord("C00618"), 200);
		tbList.addColumn(SC.NAME_S, getWord("C00619"), 200);
		tbList.addColumn(SC.NAME_K, getWord("C00620"), 250);

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

		// 科目体系名称範囲検索パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setSize(430, 75);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlBodyTop.add(pnlSearchCondition);

		// 開始ラベル
		lblStart.setLangMessageID("C01012");// 開始
		lblStart.setSize(60, 20);
		lblStart.setLocation(20, 20);
		pnlSearchCondition.add(lblStart);

		// 終了ラベル
		lblEnd.setLangMessageID("C01143");// 終了
		lblEnd.setSize(60, 20);
		lblEnd.setLocation(20, 40);
		pnlSearchCondition.add(lblEnd);

		// 科目体系名称範囲
		ctrlItemOrgRan.ctrlItemOrReferenceFrom.btn.setLangMessageID("C00609");// 科目体系
		ctrlItemOrgRan.ctrlItemOrgReferenceTo.btn.setLangMessageID("C00609");
		ctrlItemOrgRan.setLocation(70, 20);
		pnlSearchCondition.add(ctrlItemOrgRan);

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
		ctrlItemOrgRan.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}