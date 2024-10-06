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
 * システム区分マスタ 指示画面
 */
public class MG0320SystemDivisionMasterPanel extends TMainPanel {

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

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 開始ラベル */
	public TLabel lblStart;

	/** 終了ラベル */
	public TLabel lblEnd;

	/** システム区分範囲検索 */
	public TSystemDivisionReferenceRange TSystemDivisionReferenceRange;

	/** 一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {

		/** Entity **/
		ENTITY

		/** システム区分 */
		, KBN

		/** システム区分名称 */
		, KBN_NAME

		/** システム区分略称 */
		, KBN_NAME_S

		/** システム区分検索名称 */
		, KBN_NAME_K

		/** 外部システム区分 */
		, EX_KBN
	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		lblStart = new TLabel();
		lblEnd = new TLabel();
		TSystemDivisionReferenceRange = new TSystemDivisionReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.ENTITY, "", -1);
		tbl.addColumn(SC.KBN, getWord("C00980"), 100, SwingConstants.CENTER);// システム区分
		tbl.addColumn(SC.KBN_NAME, getWord("C00832"), 280);// システム区分名称
		tbl.addColumn(SC.KBN_NAME_S, getWord("C00833"), 180);// システム区分略称
		tbl.addColumn(SC.KBN_NAME_K, getWord("C00834"), 180);// システム区分検索名称
		tbl.addColumn(SC.EX_KBN, getWord("C01018"), 160, SwingConstants.CENTER);// 外部システム区分

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
		tbl.addSpreadSheetSelectChange(btnModify);

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

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(430, 75);
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

		// システム区分範囲検索
		TSystemDivisionReferenceRange.ctrlSysDivReferenceFrom.btn.setLangMessageID("C00980");// システム区分
		TSystemDivisionReferenceRange.ctrlSysDivReferenceTo.btn.setLangMessageID("C00980");
		TSystemDivisionReferenceRange.setLocation(70, 20);
		pnlSearchCondition.add(TSystemDivisionReferenceRange);

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
		int i = 1;
		TSystemDivisionReferenceRange.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
