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
 * 会社階層マスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class OW0140CompanyHierarchicalMasterPanel extends TMainPanel {

	/** コントロールクラス */
	public OW0140CompanyHierarchicalMasterPanelCtrl ctrl;

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
	public TCompanyOrganizationCodeComboBox ctrlOrganizationCode;

	/** 組織名称 */
	public TTextField ctrlOrganizationName;

	/** レベル０ */
	public TCompanyReference ctrlCompany;

	/** 上位会社ボタン */
	public TButton btnUpperCompany;

	/** 下位会社ボタン */
	public TButton btnLowerCompany;

	/** 会社一覧パネル */
	public TPanel pnlDetail2;

	/** 会社リストパネル */
	public TPanel pnlCompanyList;

	/** 会社一覧（左スプレッド） */
	public TTable ssCompanyList;

	/** ラベルリストパネル */
	public TPanel pnlLabelList;

	/** 会社ボタンパネル */
	public TPanel pnlButtonCompany;

	/** 会社追加ボタン */
	public TButton btnCompanyAdd;

	/** 会社削除ボタン */
	public TButton btnCompanyCancellation;

	/** 会社階層一覧（右スプレッド） */
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
	public OW0140CompanyHierarchicalMasterPanel(OW0140CompanyHierarchicalMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * 会社一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum CS1 {
		/** 会社コード */
		code,
		/** 会社略称 */
		name,
		/** 会社エンティティ */
		bean,
	}

	/**
	 * 会社階層一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum CS2 {
		/** 会社コード */
		codeCmp,
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
		/** 会社エンティティ */
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
		ctrlOrganizationCode = new TCompanyOrganizationCodeComboBox(true);
		ctrlOrganizationName = new TTextField();
		ctrlCompany = new TCompanyReference(TYPE.LABEL);
		btnUpperCompany = new TButton();
		btnLowerCompany = new TButton();
		pnlDetail2 = new TPanel();
		pnlCompanyList = new TPanel();
		ssCompanyList = new TTable();
		pnlLabelList = new TPanel();
		ssHierarchyList = new TTable();
		pnlButtonCompany = new TPanel();
		btnCompanyAdd = new TButton();
		btnCompanyCancellation = new TButton();
		pnlBodyHeader = new TPanel();

		// 会社一覧（左スプレッド）
		ssCompanyList = new TTable();
		ssCompanyList.addColumn(CS1.code, "COW042", 100, SwingConstants.LEFT); // 会社コード
		ssCompanyList.addColumn(CS1.name, "C00685", 250, SwingConstants.LEFT); // 会社名称
		ssCompanyList.addColumn(CS1.bean, "", -1);

		// 会社階層一覧（右スプレッド）
		ssHierarchyList = new TTable();
		ssHierarchyList.addColumn(CS2.codeCmp, "COW042", 120, SwingConstants.LEFT); // 会社コード
		ssHierarchyList.addColumn(CS2.level, "C01739", 50, SwingConstants.RIGHT); // レベル
		ssHierarchyList.addColumn(CS2.level1, "C02126", 90, SwingConstants.LEFT); // レベル1
		ssHierarchyList.addColumn(CS2.level2, "C02127", 90, SwingConstants.LEFT); // レベル2
		ssHierarchyList.addColumn(CS2.level3, "C02128", 90, SwingConstants.LEFT); // レベル3
		ssHierarchyList.addColumn(CS2.level4, "C02129", 90, SwingConstants.LEFT); // レベル4
		ssHierarchyList.addColumn(CS2.level5, "C02130", 90, SwingConstants.LEFT); // レベル5
		ssHierarchyList.addColumn(CS2.level6, "C02131", 90, SwingConstants.LEFT); // レベル6
		ssHierarchyList.addColumn(CS2.level7, "C02132", 90, SwingConstants.LEFT); // レベル7
		ssHierarchyList.addColumn(CS2.level8, "C02133", 90, SwingConstants.LEFT); // レベル8
		ssHierarchyList.addColumn(CS2.level9, "C02134", 90, SwingConstants.LEFT); // レベル9
		ssHierarchyList.addColumn(CS2.bean2, "", -1);
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
		ctrlCompany.setEditable(false);
		ctrlCompany.setLocation(x + 2, y);
		ctrlCompany.setNameSize(300);
		pnlBodyHeader.add(ctrlCompany);

		// 上位会社ボタン
		btnUpperCompany.setLangMessageID("C01487");
		btnUpperCompany.setShortcutKey(KeyEvent.VK_F7);
		btnUpperCompany.setSize(25, 130);
		btnUpperCompany.setLocation(500, 10);
		pnlBodyHeader.add(btnUpperCompany);

		// 下位会社ボタン
		btnLowerCompany.setLangMessageID("C01488");
		btnLowerCompany.setShortcutKey(KeyEvent.VK_F8);
		btnLowerCompany.setSize(25, 130);
		btnLowerCompany.setLocation(650, 10);
		pnlBodyHeader.add(btnLowerCompany);

		// 会社一覧（左スプレッド）
		ssCompanyList.setMaximumSize(new Dimension(260, 0));
		ssCompanyList.setMinimumSize(new Dimension(260, 0));
		ssCompanyList.setPreferredSize(new Dimension(260, 0));

		gc = new GridBagConstraints();
		gc.weightx = 0.2d;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 10, 45, 0);
		pnlBody.add(ssCompanyList, gc);
		ssCompanyList.addSpreadSheetSelectChange(btnCompanyAdd);

		// 会社追加
		btnCompanyAdd.setLangMessageID("C10543");
		btnCompanyAdd.setShortcutKey(KeyEvent.VK_F2);
		btnCompanyAdd.setMaximumSize(new Dimension(100, 25));
		btnCompanyAdd.setMinimumSize(new Dimension(100, 25));
		btnCompanyAdd.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 100, 10);
		pnlBody.add(btnCompanyAdd, gc);

		// 会社削除
		btnCompanyCancellation.setLangMessageID("C10544");
		btnCompanyCancellation.setShortcutKey(KeyEvent.VK_F3);
		btnCompanyCancellation.setMaximumSize(new Dimension(100, 25));
		btnCompanyCancellation.setMinimumSize(new Dimension(100, 25));
		btnCompanyCancellation.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 10, 10);
		pnlBody.add(btnCompanyCancellation, gc);

		// 会社階層一覧（右スプレッド）
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
		ssHierarchyList.addSpreadSheetSelectChange(btnCompanyCancellation);

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
		btnUpperCompany.setTabControlNo(i++);
		btnLowerCompany.setTabControlNo(i++);
		btnCompanyAdd.setTabControlNo(i++);
		btnCompanyCancellation.setTabControlNo(i++);
		ssCompanyList.setTabControlNo(i++);
		ssHierarchyList.setTabControlNo(i++);

	}

}
