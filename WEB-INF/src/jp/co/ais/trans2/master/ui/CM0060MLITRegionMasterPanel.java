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
 * 輸送実績国マスタ
 * 
 * @author AIS
 */
public class CM0060MLITRegionMasterPanel extends TMainPanel {

	/** 一覧のカラム定義 */
	public enum SC {
		/** コード */
		code,
		/** 名称 */
		name,
		/** Remark */
		remark;
	}

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

	/** リスト出力ボタン */
	public TImageButton btnListOutput;

	/** 範囲 */
	public TMlitRegionReferenceRange ctrlRange;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 一覧 */
	public TTable tbl;

	/**
	 * コンストラクタ.
	 */
	public CM0060MLITRegionMasterPanel() {
		super();
	}

	/**
	 * 画面コンポーネントの構築
	 */
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		ctrlRange = new TMlitRegionReferenceRange();

		pnlSearchCondition = new TPanel();

		tbl = new TTable();
		tbl.addColumn(SC.code, getWord("Region Code"), 80); // アイテムコード
		tbl.addColumn(SC.name, getWord("Region Name"), 180); // アイテム名称
		tbl.addColumn(SC.remark, getWord("CM0015"), 360); // Remark
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		int x = HEADER_LEFT_X;

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		x = x + btnNew.getWidth() + HEADER_MARGIN_X;

		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;

		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		tbl.addSpreadSheetSelectChange(btnModify);
		pnlHeader.add(btnModify);

		x = x + btnModify.getWidth() + HEADER_MARGIN_X;

		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;

		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setSize(25, 130);
		btnListOutput.setLocation(x, HEADER_Y);
		pnlHeader.add(btnListOutput);

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
		pnlSearchCondition.setLangMessageID("CBL364");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(400, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 範囲検索
		ctrlRange.setLocation(20, 20);
		pnlSearchCondition.add(ctrlRange);

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

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlRange.setTabControlNo(i++);

		tbl.setTabControlNo(i++);

		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnListOutput.setTabControlNo(i++);
	}
}
