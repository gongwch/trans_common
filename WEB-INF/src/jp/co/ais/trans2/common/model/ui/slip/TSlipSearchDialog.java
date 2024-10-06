package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 伝票検索
 */
public class TSlipSearchDialog extends TDialog {

	/** テーブル列名列挙体 */
	public enum SC {
		/** 基データ */
		bean,

		/** 伝票番号 */
		slipNo,

		/** 伝票日付 */
		slipDate,

		/** 部門コード */
		depCode,

		/** 部門略称 */
		depNames,

		/** 摘要 */
		remarks,

		/** 更新区分 */
		updDivision;
	}

	/** ダイアログ サイズ */
	public int dialogWidth = getDialogWidth();

	/** 一覧 */
	public TTable tbl;

	/** 条件 伝票番号 */
	public TTextField txtSlipNo;

	/** 条件 伝票日付 */
	public THalfwayDateField txtSlipDate;

	/** 条件 部門コード */
	public TTextField txtDepCode;

	/** 条件 部門略称 */
	public TTextField txtDepNames;

	/** 条件 伝票摘要 */
	public TTextField txtSlipRemarks;

	/** 条件 更新区分 */
	public TComboBox cmbUpdDivision;

	/** 検索ボタン */
	public TButton btnSearch;

	/** 確定ボタン */
	public TButton btnEnter;

	/** 取消ボタン */
	public TButton btnCancel;

	/** 複写モード選択パネル */
	public TRadioPanel ctrlCopyMode;

	/** メインパネル */
	protected TPanel pnlMain;

	/** スプレッド領域 */
	protected TPanel pnlSS;

	/** フィールド領域 */
	protected TPanel pnlField;

	/** ボタン領域 */
	protected TPanel pnlButton;

	/** true時、検索条件に部門追加 */
	protected static boolean useDepartmentSearch = ClientConfig.isFlagOn("trans.slip.use.search.depcode");

	/**
	 * コンストラクタ.
	 * 
	 * @param panel パネル
	 */
	public TSlipSearchDialog(TPanel panel) {
		this(panel.getParentFrame());
	}

	/**
	 * ダイアログの大きさ
	 * 
	 * @return サイズ
	 */
	public int getDialogWidth() {
		return 850;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親フレーム
	 */
	public TSlipSearchDialog(Frame parent) {
		super(parent, true);

		initComponents();
		initSpreadSheet();
		allocateComponents();
		setTabIndex();

		initDialog();
	}

	/**
	 * コンポーネント作成
	 */
	public void initComponents() {
		this.setLangMessageID("C00391");// 伝票一覧
		this.setResizable(true);

		pnlMain = new TPanel();
		pnlSS = new TPanel();
		pnlField = new TPanel();
		pnlButton = new TPanel();

		tbl = new TTable();

		txtSlipNo = new TTextField();
		txtSlipDate = new THalfwayDateField();
		txtDepCode = new TTextField();
		txtDepNames = new TTextField();
		txtSlipRemarks = new TTextField();
		cmbUpdDivision = new TComboBox();

		btnSearch = new TButton();
		btnEnter = new TButton();
		btnCancel = new TButton();

		ctrlCopyMode = new TRadioPanel();
	}

	/**
	 * スプレッドシート設定
	 */
	public void initSpreadSheet() {
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.slipNo, "C00605", 150);// 伝票番号
		tbl.addColumn(SC.slipDate, "C00599", 80, SwingConstants.CENTER);// 伝票日付
		if (useDepartmentSearch) {
			tbl.addColumn(SC.depCode, "C00698", 80);// 部門コード
			tbl.addColumn(SC.depNames, "C00724", 100);// 部門略称
		} else {
			tbl.addColumn(SC.depCode, "C00698", 0, false);
			tbl.addColumn(SC.depNames, "C00724", 0, false);
		}
		tbl.addColumn(SC.remarks, "C00569", dialogWidth - 420);// 伝票摘要
		tbl.addColumn(SC.updDivision, "C01069", 100, SwingConstants.CENTER);// 更新区分

		tbl.setRowColumnWidth(38);
	}

	/**
	 * コンポーネント配置
	 */
	public void allocateComponents() {

		int depX = 0;

		this.setSize(dialogWidth, 600);
		this.setLayout(new BorderLayout());

		pnlMain.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1.0d;

		// 一覧
		pnlSS.setLayout(new BorderLayout());
		TGuiUtil.setComponentSize(pnlSS, 0, 0);

		tbl.addSpreadSheetSelectChange(btnEnter);
		tbl.setEnterToButton(true);
		tbl.getTableHeader().setReorderingAllowed(false);
		TGuiUtil.setComponentSize(tbl, new Dimension(0, 0));
		pnlSS.add(tbl, BorderLayout.CENTER);

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 10, 0, 10);
		pnlMain.add(pnlSS, gc);

		// 検索条件
		pnlField.setLayout(null);
		TGuiUtil.setComponentSize(pnlField, 0, 30);

		int x = tbl.getRowColumnWidth();

		// 伝票番号
		txtSlipNo.setImeMode(false);
		txtSlipNo.setMaxLength(20);
		TGuiUtil.setComponentSize(txtSlipNo, 150, 20);
		txtSlipNo.setLocation(x, 0);
		pnlField.add(txtSlipNo);

		// 伝票日付
		TGuiUtil.setComponentSize(txtSlipDate, 80, 20);
		txtSlipDate.setLocation(txtSlipNo.getX() + txtSlipNo.getWidth(), 0);
		pnlField.add(txtSlipDate);

		if (useDepartmentSearch) {
			depX = 180;
			this.setSize(dialogWidth + depX, 600);

			// 部門コード
			txtDepCode.setImeMode(false);
			txtDepCode.setMaxLength(10);
			TGuiUtil.setComponentSize(txtDepCode, 80, 20);
			txtDepCode.setLocation(txtSlipDate.getX() + txtSlipDate.getWidth(), 0);
			pnlField.add(txtDepCode);

			// 部門略称
			txtDepNames.setMaxLength(40);
			TGuiUtil.setComponentSize(txtDepNames, 100, 20);
			txtDepNames.setLocation(txtDepCode.getX() + txtDepCode.getWidth(), 0);
			pnlField.add(txtDepNames);
		}

		// 摘要
		TGuiUtil.setComponentSize(txtSlipRemarks, dialogWidth - 420, 20);
		txtSlipRemarks.setLocation(txtSlipDate.getX() + txtSlipDate.getWidth() + depX, 0);
		pnlField.add(txtSlipRemarks);

		// 更新区分
		TGuiUtil.setComponentSize(cmbUpdDivision, 100, 20);
		cmbUpdDivision.setLocation(txtSlipRemarks.getX() + txtSlipRemarks.getWidth(), 0);
		pnlField.add(cmbUpdDivision);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.weighty = 0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(5, 10, 0, 10);
		pnlMain.add(pnlField, gc);

		// ボタン
		pnlButton.setLayout(null);
		TGuiUtil.setComponentSize(pnlButton, 0, 45);

		// 検索ボタン
		btnSearch.setLangMessageID("C00155");// 検索
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		TGuiUtil.setComponentSize(btnSearch, 25, 100);
		btnSearch.setLocation(0, 10);
		pnlButton.add(btnSearch);

		// 確定ボタン
		btnEnter.setLangMessageID("C01019");// 確定
		btnEnter.setShortcutKey(KeyEvent.VK_F8);
		btnEnter.setEnabled(false);
		TGuiUtil.setComponentSize(btnEnter, 25, 100);
		btnEnter.setLocation(btnSearch.getX() + btnSearch.getWidth() + 2, 10);
		pnlButton.add(btnEnter);

		// キャンセルボタン
		btnCancel.setLangMessageID("C00405");// 取消
		btnCancel.setShortcutKey(KeyEvent.VK_F12);
		TGuiUtil.setComponentSize(btnCancel, 25, 100);
		btnCancel.setLocation(dialogWidth - btnCancel.getWidth() - 30, 10);
		pnlButton.add(btnCancel);

		// 複写モード選択パネル
		ctrlCopyMode.setSize(ctrlCopyMode.getWidth(), ctrlCopyMode.getHeight() + 1);
		ctrlCopyMode.setLangMessageID("C00459");// 複写
		ctrlCopyMode.addRadioButton(getWord("C10758"), 80);// 黒伝複写
		ctrlCopyMode.addRadioButton(getWord("C10759"), 80);// 赤伝複写
		ctrlCopyMode.addRadioButton(getWord("C10760"), 80);// 逆伝複写
		ctrlCopyMode.setSelectON(0);
		ctrlCopyMode.setVisible(false);
		ctrlCopyMode.setLocation(btnEnter.getX() + btnEnter.getWidth() + 30, -1);
		pnlButton.add(ctrlCopyMode);

		gc.gridx = 0;
		gc.gridy = 2;
		gc.weighty = 0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0, 10, 0, 10);
		pnlMain.add(pnlButton, gc);

		add(pnlMain);
	}

	/**
	 * タブ順設定
	 */
	public void setTabIndex() {
		int i = 1;

		txtSlipNo.setTabControlNo(i++);
		txtSlipDate.setTabControlNo(i++);
		txtDepCode.setTabControlNo(i++);
		txtDepNames.setTabControlNo(i++);
		txtSlipRemarks.setTabControlNo(i++);
		cmbUpdDivision.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnEnter.setTabControlNo(i++);
		ctrlCopyMode.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);

		tbl.setTabControlNo(i++);
	}
}
