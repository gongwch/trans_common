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
 * 	科目制御区分マスタの指示画面
 */
public class MG0431AutomaticJournalizingItemMasterPanel extends TMainPanel {

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

	/** 科目制御区分範囲検索 */
	public TAutomaticJournalizingItemReferenceRange AutoJnlItemRange;
	
	/** 科目制御区分マスタ一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 */
	public enum SC {
		/** 科目制御区分 */
		autojnlitemkbn, 
		/** 科目制御区分名称 */
		autojnlitemname, 
		/** 部門コード */
		depcode, 
		/** 部門名称 */
		depname, 
		/** 科目コード */
		kmkcode, 
		/** 科目名称 */
		kmkname, 
		/** 補助コード */
		hkmcode, 
		/** 補助名称 */
		hkmname,
		/** 科目コード */
		ukmcode, 
		/** 科目名称 */
		ukmname
	}
	
	/**
	 * コンストラクタ.
	 */
	public MG0431AutomaticJournalizingItemMasterPanel() {
		super();
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
		pnlSearchCondition.setLangMessageID("C01060"); // 検索条件
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(480, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 科目制御区分検索範囲
		AutoJnlItemRange.setLocation(20, 20);
		pnlSearchCondition.add(AutoJnlItemRange);

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
		pnlBodyButtom.add(tbl,gc);

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
		AutoJnlItemRange = new TAutomaticJournalizingItemReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.autojnlitemkbn, "C01008", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.autojnlitemname, "C11885", 250, SwingConstants.LEFT);
		tbl.addColumn(SC.depcode, "C00571", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.depname, "C10389", 250, SwingConstants.LEFT);
		tbl.addColumn(SC.kmkcode, "C00572", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.kmkname, "C00700", 150, SwingConstants.LEFT);
		tbl.addColumn(SC.hkmcode, "C00602", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.hkmname, "C00701", 150, SwingConstants.LEFT);
		tbl.addColumn(SC.ukmcode, "C00603", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.ukmname, "C00702", 150, SwingConstants.LEFT);
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		AutoJnlItemRange.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
