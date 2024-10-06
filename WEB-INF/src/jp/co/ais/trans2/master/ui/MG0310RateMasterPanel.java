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
 * 通貨レートマスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0310RateMasterPanel extends TMainPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -6170021596968496269L;

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

	/** 通貨範囲検索パネル */
	public TPanel pnlCurrencyReferenceRange;

	/** 通貨範囲検索 */
	public TCurrencyReferenceRange ctrlCurrencyReferenceRange;

	/** レート区分パネル */
	public TPanel pnlRateType;

	/** 社定レート */
	public TCheckBox chkCompanyRate;

	/** 決算日レート */
	public TCheckBox chkSettlementRate;

	/** 社定レート（機能通貨） */
	public TCheckBox chkFuncCompanyRate;

	/** 決算日レート（機能通貨） */
	public TCheckBox chkFuncSettlementRate;

	/** 適用開始日付開始 */
	public TLabelPopupCalendar dateFrom;

	/** 適用開始日付終了 */
	public TLabelPopupCalendar dateTo;

	/** 一覧 */
	public TPanel pnlBodyBottom;

	/** レートマスタ一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** レート区分 */
		type,
		/** 通貨コード */
		currencyCode,
		/** 適用開始日付 */
		termFrom,
		/** レート */
		rate,
		/** Entity */
		entity
	}

	/**
	 * 
	 * 
	 */
	public MG0310RateMasterPanel() {
		//
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
		pnlSearchCondition = new TPanel();
		pnlCurrencyReferenceRange = new TPanel();
		ctrlCurrencyReferenceRange = new TCurrencyReferenceRange();
		pnlRateType = new TPanel();
		chkCompanyRate = new TCheckBox();
		chkSettlementRate = new TCheckBox();
		chkFuncCompanyRate = new TCheckBox();
		chkFuncSettlementRate = new TCheckBox();
		dateFrom = new TLabelPopupCalendar();
		dateTo = new TLabelPopupCalendar();
		pnlBodyBottom = new TPanel();
		tbl = new TTable();
		tbl.addColumn(SC.type, getWord("C00883"), 150);// レート区分
		tbl.addColumn(SC.currencyCode, getWord("C00665"), 100, SwingConstants.CENTER);// 通貨コード
		tbl.addColumn(SC.termFrom, getWord("C02046"), 100, SwingConstants.CENTER);// 適用開始日付
		tbl.addColumn(SC.rate, getWord("C00556"), 100, SwingConstants.RIGHT);// レート
		tbl.addColumn(SC.entity, "", -1, false);
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
		pnlBodyTop.setMinimumSize(new Dimension(0, 145));
		pnlBodyTop.setPreferredSize(new Dimension(0, 145));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));// 検索条件
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(720, 135);
		pnlBodyTop.add(pnlSearchCondition);

		// 通貨範囲パネル
		pnlCurrencyReferenceRange.setLayout(null);
		pnlCurrencyReferenceRange.setBorder(TBorderFactory.createTitledBorder(getWord("C00371")));// 通貨
		pnlCurrencyReferenceRange.setSize(350, 75);
		pnlCurrencyReferenceRange.setLocation(20, 20);
		pnlSearchCondition.add(pnlCurrencyReferenceRange);

		// 通貨範囲
		ctrlCurrencyReferenceRange.setLocation(20, 20);
		pnlCurrencyReferenceRange.add(ctrlCurrencyReferenceRange);

		// レート区分パネル
		pnlRateType.setLayout(null);
		pnlRateType.setBorder(TBorderFactory.createTitledBorder(getWord("C00883")));// レート区分
		pnlRateType.setSize(320, 75);
		pnlRateType.setLocation(375, 20);
		pnlSearchCondition.add(pnlRateType);

		// 社定レート
		chkCompanyRate.setSize(110, 20);
		chkCompanyRate.setLocation(20, 20);
		chkCompanyRate.setLangMessageID("C11167");
		pnlRateType.add(chkCompanyRate);

		// 決算日レート
		chkSettlementRate.setSize(110, 20);
		chkSettlementRate.setLocation(20, 45);
		chkSettlementRate.setLangMessageID("C02820");
		pnlRateType.add(chkSettlementRate);

		// 社定レート（機能通貨）
		chkFuncCompanyRate.setSize(180, 20);
		chkFuncCompanyRate.setLocation(130, 20);
		chkFuncCompanyRate.setLangMessageID("C11216");
		pnlRateType.add(chkFuncCompanyRate);

		// 決算日レート（機能通貨）
		chkFuncSettlementRate.setSize(180, 20);
		chkFuncSettlementRate.setLocation(130, 45);
		chkFuncSettlementRate.setLangMessageID("C11217");
		pnlRateType.add(chkFuncSettlementRate);

		// 適用開始日付開始
		dateFrom.setLabelSize(115);
		dateFrom.setLabelText(getWord("C02046"));// 適用開始日付
		dateFrom.setSize(new Dimension(dateFrom.getCalendarSize() + 120, 20));
		dateFrom.setLocation(5, 100);
		pnlSearchCondition.add(dateFrom);

		// 適用開始日付終了
		dateTo.setLabelSize(40);
		dateTo.setLabelText(getWord("C01333"));// ～
		dateTo.setSize(new Dimension(dateFrom.getCalendarSize() + 45, 20));
		dateTo.setLocation(180, 100);
		pnlSearchCondition.add(dateTo);

		// 下部
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlCurrencyReferenceRange.setTabControlNo(i++);
		chkCompanyRate.setTabControlNo(i++);
		chkSettlementRate.setTabControlNo(i++);
		chkFuncCompanyRate.setTabControlNo(i++);
		chkFuncSettlementRate.setTabControlNo(i++);
		dateFrom.setTabControlNo(i++);
		dateTo.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
