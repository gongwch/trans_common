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
 * 消費税マスタの指示画面
 * 
 * @author AIS
 */
public class MG0130ConsumptionTaxMasterPanel extends TMainPanel {

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

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 消費税範囲検索開始終了 */
	public TTaxReferenceRange ctrlTaxRefRan;

	/** 有効期間切れを表示するか */
	public TCheckBox chkOutputTermEnd;

	/** 下部 */
	public TPanel pnlBodyBottom;

	/** 消費税マスタ一覧 */
	public TTable tbList;

	/** 一覧のカラム定義 */
	public enum SC {

		/** 消費税コード */
		ZEI_CODE,

		/** 消費税名称 */
		ZEI_NAME,

		/** 消費税略称 */
		ZEI_NAME_S,

		/** 消費税検索名称 */
		ZEI_NAME_K,

		/** 売上仕入区分 */
		US_KBN,

		/** 消費税率 */
		ZEI_RATE,

		/** 消費税計算出力順序 */
		ZEI_KEI_KBN,

		/** 非適格請求書発行事業者 */
		noInvReg,

		/** 経過措置控除可能率 */
		KEKA_SOTI_RATE,

		/** 開始年月日 */
		STR_DATE,

		/** 終了年月日 */
		END_DATE,

		/** Entity */
		ENTITY
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
		ctrlTaxRefRan = new TTaxReferenceRange();
		chkOutputTermEnd = new TCheckBox();
		pnlBodyBottom = new TPanel();

		int invoiceWidth = -1;
		int transitMeasureWidth = -1;

		if (MG0130ConsumptionTaxMasterPanelCtrl.isInvoice) {
			invoiceWidth = 150;
			transitMeasureWidth = 115;
		}

		tbList = new TTable();
		tbList.addColumn(SC.ZEI_CODE, getWord("C00573"), 75);
		tbList.addColumn(SC.ZEI_NAME, getWord("C00774"), 155);
		tbList.addColumn(SC.ZEI_NAME_S, getWord("C00775"), 155);
		tbList.addColumn(SC.ZEI_NAME_K, getWord("C00828"), 155);
		tbList.addColumn(SC.US_KBN, getWord("C01283"), 85, SwingConstants.CENTER);
		tbList.addColumn(SC.ZEI_RATE, getWord("C00777"), 75, SwingConstants.RIGHT);
		tbList.addColumn(SC.ZEI_KEI_KBN, getWord("C02045"), 135, SwingConstants.RIGHT);
		tbList.addColumn(SC.noInvReg, getWord("C12197"), invoiceWidth, SwingConstants.CENTER); // 非適格請求書発行事業者
		tbList.addColumn(SC.KEKA_SOTI_RATE, "C12228", transitMeasureWidth, SwingConstants.RIGHT);
		tbList.addColumn(SC.STR_DATE, getWord("COP706"), 85, SwingConstants.CENTER);
		tbList.addColumn(SC.END_DATE, getWord("COP707"), 85, SwingConstants.CENTER);
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
		pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 消費税（範囲）（開始、終了）
		ctrlTaxRefRan.setLocation(20, 20);
		pnlSearchCondition.add(ctrlTaxRefRan);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(chkOutputTermEnd);

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
		int i = 0;
		ctrlTaxRefRan.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		tbList.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}