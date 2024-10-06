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
 * MG0070ItemSummaryMaster - 科目集計マスタ - Main Panel Class
 * 
 * @author AIS
 */
public class MG0070ItemSummaryMasterPanel extends TMainPanel{

	/** 横幅固定値 */
	protected final int ITEM_WIDTH = 110;

	/** 縦幅固定値 */
	protected final int ITEM_HEIGHT = 25;

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

	/** 範囲指定検索パネル */
	public TPanel pnlRefRange;

	/** 科目体系検索部品 */
	public TItemSummaryRangeUnit ctrlAccSumRef; 

	/** 有効期間切れ */
	public TCheckBox chkOutputTermEnd;

	/** 一覧パネル */
	public TPanel pnlBodyBottom;

	/** 一覧 */
	public TTable tbl;

	/** 一覧のカラム定義 */
	public enum SC{

		/** 科目体系コード */
		KMT_CODE,

		/** 科目体系略称 */
		KMT_NAME,

		/** 科目コード */
		KMK_CODE,

		/** 科目略称 */
		KMK_NAME,

		/** 公表科目名称 */
		KOK_NAME,

		/** 集計相手先科目コード */
		SUM_CODE,

		/** 集計相手先科目略称 */
		SUM_NAME,

		/** 科目出力順序 */
		ODR,

		/** 表示区分 */
		HYJ_KBN,

		/** 開始年月日 */
		STR_DATE,

		/** 終了年月日 */
		END_DATE,

		/** Entity */
		ENTITY
	}

	@Override
	public void initComponents() {

		btnNew     = new TImageButton(IconType.NEW);
		btnSearch  = new TImageButton(IconType.SEARCH);
		btnModify  = new TImageButton(IconType.EDIT);
		btnCopy    = new TImageButton(IconType.COPY);
		btnDelete  = new TImageButton(IconType.DELETE);
		btnExcel   = new TImageButton(IconType.EXCEL);
		chkOutputTermEnd = new TCheckBox();
		pnlBodyBottom = new TPanel();
		pnlRefRange  = new TPanel();
		ctrlAccSumRef = new TItemSummaryRangeUnit();

		// 一覧（リスト）タイトル定義
		tbl = new TTable();
		tbl.addColumn(SC.KMT_CODE, getWord("C00617"), 120);
		tbl.addColumn(SC.KMT_NAME, getWord("C00619"), 240);
		tbl.addColumn(SC.KMK_CODE, getWord("C00572"), 120);
		tbl.addColumn(SC.KMK_NAME, getWord("C00730"), 180);
		tbl.addColumn(SC.KOK_NAME, getWord("C00624"), 280);
		tbl.addColumn(SC.SUM_CODE, getWord("C01609"), 150);
		tbl.addColumn(SC.SUM_NAME, getWord("C00625"), 240);
		tbl.addColumn(SC.ODR, getWord("C00626"), 120, SwingConstants.RIGHT);
		tbl.addColumn(SC.HYJ_KBN, getWord("C01300"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.STR_DATE, getWord("C00055"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.END_DATE, getWord("C00261"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.ENTITY, "", -1, false);
	}

	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(ITEM_HEIGHT, ITEM_WIDTH + 20);
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
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

		// 範囲パネル
		pnlRefRange.setLangMessageID("C01060");
		pnlRefRange.setLayout(null);
		pnlRefRange.setSize(650, 110);
		pnlRefRange.setLocation(20, 20);
		pnlBodyTop.add(pnlRefRange);

		// 科目体系
		ctrlAccSumRef.setSize(510, 105);
		ctrlAccSumRef.setLocation(20, 25);
		pnlRefRange.add(ctrlAccSumRef);

		// メッセージ追加必要 ##########
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(490, 77);
		pnlRefRange.add(chkOutputTermEnd);
		
		// 下部
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(-5, 20, 10, 20);
		pnlBody.add(tbl, gc);
	}

	@Override
	/**
	 * Tab順定義
	 */
	public void setTabIndex() {
		int i = 1;
		ctrlAccSumRef.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}