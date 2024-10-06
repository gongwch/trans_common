package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 検索ダイアログの基底
 * 
 * @author AIS
 */
public class TReferenceDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = -7281962661789452420L;

	/** 一覧 */
	public TTable tbl;

	/** コード */
	public TTextField code;

	/** 略称 */
	public TTextField names;

	/** 検索名称 */
	public TTextField namek;

	/** 検索ボタン */
	public TButton btnSearch;

	/** 確定ボタン */
	public TButton btnSettle;

	/** 戻るボタン */
	public TButton btnBack;

	/** 会社情報 */
	public Company company;

	/** 検索パネル */
	public TPanel pnlSearch;

	/** ボタンパネル */
	public TPanel pnlButton;

	/** コントローラ */
	protected TReferenceController controller = null;

	/** 3カラムの表示 */
	protected boolean show3rdColumn = true;

	/** 検索ダイアログの列番号定義 */
	public enum SC {
		/** Bean */
		bean,
		/** コード */
		code,
		/** 略称 */
		names,
		/** 検索名称 */
		namek
	}

	/**
	 * 3カラムの表示
	 * 
	 * @return boolean
	 */
	public boolean isShow3rdColumn() {
		return show3rdColumn;
	}

	/**
	 * 3カラムの表示
	 * 
	 * @param show3rdColumn
	 */
	public void setShow3rdColumn(boolean show3rdColumn) {
		this.show3rdColumn = show3rdColumn;
	}

	/**
	 * @param controller コントローラ
	 */
	public TReferenceDialog(TReferenceController controller) {
		super(controller.field.getParentFrame(), true);
		this.controller = controller;

		setShow3rdColumn(controller.isShow3rdColumn());

		initComponents();

		try {
			// テーブルのみ先に復元
			Class c = this.getClass();
			tbl.restoreComponent(new TDefaultStorableKey(c, c.getField("tbl")));

		} catch (Exception ex) {
			ClientErrorHandler.handledException(ex);
		}

		allocateComponents();

		setTabIndex();
		initDialog();
	}

	/**
	 * コンポーネント初期化
	 */
	protected void initComponents() {
		tbl = new TTable();
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.code, getWord(controller.getCodeCaption()), controller.getCodeColumnSize());

		if (this.show3rdColumn) {
			tbl.addColumn(SC.names, getWord(controller.getNamesCaption()), controller.getNamesColumnSize(),
				controller.getNamesColumnAlignment());
			tbl.addColumn(SC.namek, getWord(controller.getNamekCaption()), controller.getNamekColumnSize(),
				controller.getNamekColumnAlignment());

		} else {
			tbl.addColumn(SC.names, controller.getNameCaption(), controller.getNamesColumnSize(),
				controller.getNamesColumnAlignment());
			tbl.addColumn(SC.namek, controller.getNamekCaption(), 0, controller.getNamekColumnAlignment());
		}

		code = new TTextField();
		names = new TTextField();
		namek = new TTextField();
		btnSearch = new TButton();
		btnSettle = new TButton();
		btnBack = new TButton();

		pnlSearch = new TPanel();
		pnlButton = new TPanel();
	}

	/**
	 * コンポーネント配置
	 */
	protected void allocateComponents() {

		setLayout(new GridBagLayout());
		setResizable(true);

		setPreferredSize(new Dimension(650, 520));

		setTitle(controller.getDialogTitle());

		// 一覧
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		add(tbl, gc);
		tbl.addSpreadSheetSelectChange(btnSettle);

		// 検索条件指定フィールド
		pnlSearch.setLayout(new GridBagLayout());
		pnlSearch.setMinimumSize(new Dimension(0, 20));
		pnlSearch.setPreferredSize(new Dimension(0, 20));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(pnlSearch, gc);

		// コード検索
		TTableInformation ti = tbl.getTableInformation();
		int codeLength = ti.getWidthList().get(SC.code.ordinal());
		code.setMinimumSize(new Dimension(codeLength, 20));
		code.setPreferredSize(new Dimension(codeLength, 20));
		code.setImeMode(false);
		int marginX = tbl.getRowColumnWidth() + 10;
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, marginX, 0, 0);

		pnlSearch.add(code, gc);

		// 略称検索
		int namesLength = ti.getWidthList().get(SC.names.ordinal());
		names.setMinimumSize(new Dimension(namesLength, 20));
		names.setPreferredSize(new Dimension(namesLength, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		pnlSearch.add(names, gc);

		// 検索名称
		int namekLength = ti.getWidthList().get(SC.namek.ordinal());
		namek.setMinimumSize(new Dimension(namekLength, 20));
		namek.setPreferredSize(new Dimension(namekLength, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;

		pnlSearch.add(namek, gc);
		if (!show3rdColumn) {
			namek.setVisible(true);
		}

		// ボタンフィールド
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMinimumSize(new Dimension(0, 40));
		pnlButton.setPreferredSize(new Dimension(0, 40));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 2;
		add(pnlButton, gc);

		// 検索ボタン
		btnSearch.setPreferredSize(new Dimension(120, 25));
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		pnlButton.add(btnSearch);
		gc = new GridBagConstraints();
		pnlButton.add(btnSearch, gc);

		// 確定ボタン
		btnSettle.setPreferredSize(new Dimension(120, 25));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		pnlButton.add(btnSettle, gc);

		// 戻るボタン
		btnBack.setPreferredSize(new Dimension(120, 25));
		btnBack.setLangMessageID("C01747");
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		gc = new GridBagConstraints();
		pnlButton.add(btnBack, gc);

		pack();
	}

	/**
	 * Tab順セット
	 */
	protected void setTabIndex() {
		int i = 0;
		code.setTabControlNo(i++);
		names.setTabControlNo(i++);
		namek.setTabControlNo(i++);
		if (ClientConfig.isFlagOn("trans.ref.table.focusable")) {
			tbl.setTabControlNo(i++);
			tbl.setEnterToButton(true);
		}
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
	}
}
