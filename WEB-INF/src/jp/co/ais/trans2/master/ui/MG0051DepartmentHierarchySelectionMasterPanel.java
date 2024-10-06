package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TReference.*;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 部門階層マスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0051DepartmentHierarchySelectionMasterPanel extends TMainPanel {

	/** コントロールクラス */
	public MG0051DepartmentHierarchySelectionMasterPanelCtrl ctrl;

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 確定 */
	public TImageButton btnSettle;

	/** 組織名称設定 */
	public TImageButton btnOrganizationName;

	/** 新規作成パネル */
	public TPanel pnlDetail1;

	/** 組織コード */
	public TDepartmentOrganizationCodeComboBox ctrlOrganizationCode;

	/** 組織名称 */
	public TTextField ctrlOrganizationName;

	/** レベル０ */
	public TDepartmentReference ctrlDepartment;

	/** 上位部門ボタン */
	public TButton btnUpperDepartment;

	/** 下位部門ボタン */
	public TButton btnLowerDepartment;

	/** 部門一覧パネル */
	public TPanel pnlDetail2;

	/** 部門リストパネル */
	public TPanel pnlDepartmentList;

	/** 部門一覧（左スプレッド） */
	public TTable ssDepartmentList;

	/** ラベルリストパネル */
	public TPanel pnlLabelList;

	/** 部門ボタンパネル */
	public TPanel pnlButtonDepartment;

	/** 部門追加ボタン */
	public TButton btnDepartmentAdd;

	/** 部門削除ボタン */
	public TButton btnDepartmentCancellation;

	/** 部門階層一覧（右スプレッド） */
	public TTable ssHierarchyList;

	/** リストタブ */
	public TTabbedPane listTab;

	/** BodyHeaderパネル */
	public TPanel pnlBodyHeader;

	/**
	 * コンストラクタ
	 * 
	 * @param panelCtrl コントロール
	 */
	public MG0051DepartmentHierarchySelectionMasterPanel(MG0051DepartmentHierarchySelectionMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * 部門一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum DS1 {
		/** 部門コード */
		code,
		/** 部門略称 */
		name,
		/** 部門エンティティ */
		bean,
	}

	/**
	 * 部門階層一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum DS2 {
		/** 部門コード */
		codeDep,
		/** レベル */
		level,
		/** レベル１ */
		level1,
		/** レベル2 */
		level2,
		/** レベル3 */
		level3,
		/** レベル4 */
		level4,
		/** レベル5 */
		level5,
		/** レベル6 */
		level6,
		/** レベル7 */
		level7,
		/** レベル8 */
		level8,
		/** レベル9 */
		level9,
		/** 部門エンティティ */
		bean2,
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnSettle = new TImageButton(IconType.SETTLE);
		btnOrganizationName = new TImageButton();
		pnlDetail1 = new TPanel();
		ctrlOrganizationCode = new TDepartmentOrganizationCodeComboBox(true);
		ctrlOrganizationName = new TTextField();
		ctrlDepartment = new TDepartmentReference(TYPE.LABEL, "C00722");
		btnUpperDepartment = new TButton();
		btnLowerDepartment = new TButton();
		pnlDetail2 = new TPanel();
		pnlDepartmentList = new TPanel();
		ssDepartmentList = new TTable();
		pnlLabelList = new TPanel();
		ssHierarchyList = new TTable();
		pnlButtonDepartment = new TPanel();
		btnDepartmentAdd = new TButton();
		btnDepartmentCancellation = new TButton();
		pnlBodyHeader = new TPanel();

		// 部門一覧（左スプレッド）
		ssDepartmentList = new TTable();
		ssDepartmentList.addColumn(DS1.code, "C00698", 100, SwingConstants.LEFT); // 部門コード
		ssDepartmentList.addColumn(DS1.name, "C00723", 250, SwingConstants.LEFT); // 部門名称
		ssDepartmentList.addColumn(DS1.bean, "", -1);

		// 部門階層一覧（右スプレッド）
		ssHierarchyList = new TTable();
		ssHierarchyList.addColumn(DS2.codeDep, "C00698", 120, SwingConstants.LEFT); // 部門コード
		ssHierarchyList.addColumn(DS2.level, "C01739", 50, SwingConstants.RIGHT); // レベル
		ssHierarchyList.addColumn(DS2.level1, "C02126", 90, SwingConstants.LEFT); // レベル1
		ssHierarchyList.addColumn(DS2.level2, "C02127", 90, SwingConstants.LEFT); // レベル2
		ssHierarchyList.addColumn(DS2.level3, "C02128", 90, SwingConstants.LEFT); // レベル3
		ssHierarchyList.addColumn(DS2.level4, "C02129", 90, SwingConstants.LEFT); // レベル4
		ssHierarchyList.addColumn(DS2.level5, "C02130", 90, SwingConstants.LEFT); // レベル5
		ssHierarchyList.addColumn(DS2.level6, "C02131", 90, SwingConstants.LEFT); // レベル6
		ssHierarchyList.addColumn(DS2.level7, "C02132", 90, SwingConstants.LEFT); // レベル7
		ssHierarchyList.addColumn(DS2.level8, "C02133", 90, SwingConstants.LEFT); // レベル8
		ssHierarchyList.addColumn(DS2.level9, "C02134", 90, SwingConstants.LEFT); // レベル9
		ssHierarchyList.addColumn(DS2.bean2, "", -1);
	}

	@Override
	public void allocateComponents() {

		int x = 5;

		// 新規ボタン
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 130);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 削除ボタン
		x = btnNew.getX() + btnNew.getWidth() + 5;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 130);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = btnDelete.getX() + btnDelete.getWidth() + 5;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 確定ボタン
		x = btnExcel.getX() + btnExcel.getWidth() + 5;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 130);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 組織名称設定ボタン
		x = btnSettle.getX() + btnSettle.getWidth() + 5;
		btnOrganizationName.setLangMessageID("C11968");
		btnOrganizationName.setSize(25, 130);
		btnOrganizationName.setLocation(x, HEADER_Y);
		pnlHeader.add(btnOrganizationName);

		// BodyHeaderパネル
		pnlBodyHeader.setLayout(null);
		TGuiUtil.setComponentSize(pnlBodyHeader, 0, 45);
		gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		gc.insets = new Insets(5, 10, 5, 0);
		pnlBody.add(pnlBodyHeader, gc);

		x = 0;
		int y = 0;

		gc = new GridBagConstraints();
		gc.weightx = 10d;
		gc.weighty = 5;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;

		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, 10, 5, 0);

		// 組織コード
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setSize(210, 20);
		ctrlOrganizationCode.setLocation(x, y);
		pnlBodyHeader.add(ctrlOrganizationCode);

		// 組織名称
		ctrlOrganizationName.setLocation(x + 187, y);
		ctrlOrganizationName.setSize(250, 20);
		ctrlOrganizationName.setEditable(false);
		pnlBodyHeader.add(ctrlOrganizationName);

		y += ctrlOrganizationCode.getHeight();

		// レベル０
		ctrlDepartment.setEditable(false);
		ctrlDepartment.setLocation(x + 2, y);
		ctrlDepartment.setNameSize(300);
		pnlBodyHeader.add(ctrlDepartment);

		// 上位部門ボタン
		btnUpperDepartment.setLangMessageID("C00719");
		btnUpperDepartment.setShortcutKey(KeyEvent.VK_F7);
		btnUpperDepartment.setSize(25, 130);
		btnUpperDepartment.setLocation(500, 10);
		pnlBodyHeader.add(btnUpperDepartment);

		// 下位部門ボタン
		btnLowerDepartment.setLangMessageID("C00720");
		btnLowerDepartment.setShortcutKey(KeyEvent.VK_F8);
		btnLowerDepartment.setSize(25, 130);
		btnLowerDepartment.setLocation(650, 10);
		pnlBodyHeader.add(btnLowerDepartment);

		// 部門一覧（左スプレッド）
		ssDepartmentList.setMaximumSize(new Dimension(260, 0));
		ssDepartmentList.setMinimumSize(new Dimension(260, 0));
		ssDepartmentList.setPreferredSize(new Dimension(260, 0));

		gc = new GridBagConstraints();
		gc.weightx = 0.2d;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 10, 45, 0);
		pnlBody.add(ssDepartmentList, gc);
		ssDepartmentList.addSpreadSheetSelectChange(btnDepartmentAdd);

		// 部門追加
		btnDepartmentAdd.setLangMessageID("C03827");
		btnDepartmentAdd.setShortcutKey(KeyEvent.VK_F2);
		btnDepartmentAdd.setMaximumSize(new Dimension(100, 25));
		btnDepartmentAdd.setMinimumSize(new Dimension(100, 25));
		btnDepartmentAdd.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 100, 10);
		pnlBody.add(btnDepartmentAdd, gc);

		// 部門削除
		btnDepartmentCancellation.setLangMessageID("C03828");
		btnDepartmentCancellation.setShortcutKey(KeyEvent.VK_F3);
		btnDepartmentCancellation.setMaximumSize(new Dimension(100, 25));
		btnDepartmentCancellation.setMinimumSize(new Dimension(100, 25));
		btnDepartmentCancellation.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 10, 10);
		pnlBody.add(btnDepartmentCancellation, gc);

		// 部門階層一覧（右スプレッド）
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 0, 45, 10);
		pnlBody.add(ssHierarchyList, gc);
		ssHierarchyList.addSpreadSheetSelectChange(btnDepartmentCancellation);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlOrganizationCode.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		ctrlOrganizationCode.setTabControlNo(i++);
		btnUpperDepartment.setTabControlNo(i++);
		btnLowerDepartment.setTabControlNo(i++);
		btnDepartmentAdd.setTabControlNo(i++);
		btnDepartmentCancellation.setTabControlNo(i++);
		ssDepartmentList.setTabControlNo(i++);
		ssHierarchyList.setTabControlNo(i++);

	}

}
