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
 * 摘要マスタの指示画面
 * 
 * @author AIS
 */
public class MG0120RemarkMasterPanel extends TMainPanel {

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

	/** 摘要範囲検索開始終了 */
	public TRemarkReferenceRange ctrlRemRefRan;

	/** 有効期限切れチェックボックス */
	public TCheckBox chkOutputTermEnd;

	/** 摘要区分パネル */
	public TPanel pnlTek;

	/** 摘要区分 ButtonGroup */
	public ButtonGroup bgBmn;

	/** 伝票摘要 */
	public TRadioButton rdoDnp;

	/** 行摘要 */
	public TRadioButton rdoGyo;

	/** 全件 */
	public TRadioButton rdoZen;

	/** 下部 */
	public TPanel pnlBodyBottom;

	/** 摘要マスタ一覧 */
	public TTable tbList;

	/** 一覧のカラム定義 */
	public enum SC {

		/** 摘要区分 */
		TEK_KBN,

		/** データ区分 */
		DATA_KBN,

		/** 摘要コード */
		TEK_CODE,

		/** 摘要名称 */
		TEK_NAME,

		/** 摘要検索名称 */
		TEK_NAME_K,

		/** 開始年月日 */
		STR_DATE,

		/** 終了年月日 */
		END_DATE,

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
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();
		ctrlRemRefRan = new TRemarkReferenceRange();
		pnlTek = new TPanel();
		rdoDnp = new TRadioButton();
		rdoGyo = new TRadioButton();
		rdoZen = new TRadioButton();
		bgBmn = new ButtonGroup();
		pnlBodyBottom = new TPanel();

		tbList = new TTable();
		tbList.addColumn(SC.TEK_KBN, getWord("C00568"), 100, SwingConstants.CENTER);
		tbList.addColumn(SC.DATA_KBN, getWord("C00567"), 100, SwingConstants.CENTER);
		tbList.addColumn(SC.TEK_CODE, getWord("C00564"), 100);
		tbList.addColumn(SC.TEK_NAME, getWord("C00565"), 200);
		tbList.addColumn(SC.TEK_NAME_K, getWord("C00566"), 200);
		tbList.addColumn(SC.STR_DATE, getWord("COP706"), 100, 0);
		tbList.addColumn(SC.END_DATE, getWord("COP707"), 100, 0);
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
		pnlBodyTop.setMaximumSize(new Dimension(0, 115));
		pnlBodyTop.setMinimumSize(new Dimension(0, 115));
		pnlBodyTop.setPreferredSize(new Dimension(0, 115));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(700, 105);
		pnlBodyTop.add(pnlSearchCondition);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089"); // 有効期間切れ表示
		chkOutputTermEnd.setLocation(560, 70);
		chkOutputTermEnd.setSize(180, 20);
		pnlSearchCondition.add(chkOutputTermEnd);

		// 摘要（範囲）（開始、終了）
		ctrlRemRefRan.setLocation(20, 20);
		pnlSearchCondition.add(ctrlRemRefRan);

		// 摘要区分パネル
		pnlTek.setLayout(null);
		pnlTek.setLangMessageID("C00568");
		pnlTek.setLocation(750, 10);
		pnlTek.setSize(170, 105);
		pnlBodyTop.add(pnlTek);

		x = 30;
		// 伝票摘要
		rdoDnp.setLangMessageID("C00569");
		rdoDnp.setSize(100, 20);
		rdoDnp.setLocation(20, 20);
		rdoDnp.setIndex(0);
		pnlTek.add(rdoDnp);

		x += rdoDnp.getWidth();
		// 行摘要
		rdoGyo.setLangMessageID("C00119");
		rdoGyo.setSize(100, 20);
		rdoGyo.setLocation(20, 45);
		rdoGyo.setIndex(1);
		pnlTek.add(rdoGyo);

		// 全件
		rdoZen.setLangMessageID("C01212");
		rdoZen.setSize(100, 20);
		rdoZen.setLocation(20, 70);
		rdoZen.setIndex(2);
		rdoZen.setSelected(true);
		pnlTek.add(rdoZen);

		bgBmn = new ButtonGroup();
		bgBmn.add(rdoDnp);
		bgBmn.add(rdoGyo);
		bgBmn.add(rdoZen);

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
		ctrlRemRefRan.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		rdoDnp.setTabControlNo(i++);
		rdoGyo.setTabControlNo(i++);
		rdoZen.setTabControlNo(i++);
		tbList.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}