package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TReference.TYPE;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 部門階層マスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchyMasterPanel extends TMainPanel {

	/** コントロールクラス */
	public MG0050DepartmentHierarchyMasterPanelCtrl ctrl;

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

	/** 新規作成パネル */
	public TPanel pnlNew;

	/** 一覧パネル */
	public TPanel pnlTable;

	/** 一覧 */
	public TDnDTable dndTable;

	/** 組織コード */
	public TDepartmentOrganizationComboBox ctrlOrganizationCode;

	/** レベル０ */
	public TDepartmentReference ctrlDepartment;

	/** コメント用 */
	public TLabel ctrlComment;

	/** リストタブ */
	public TTabbedPane listTab;

	/** ツリー */
	public TDnDTree tree;

	/** ツリーパネル */
	public JScrollPane sp;

	/**
	 * コンストラクタ
	 * 
	 * @param panelCtrl コントロール
	 */
	public MG0050DepartmentHierarchyMasterPanel(MG0050DepartmentHierarchyMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** 部門コード */
		code,
		/** 部門略称 */
		name,
		/** 部門エンティティ */
		bean,
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlNew = new TPanel();
		ctrlOrganizationCode = new TDepartmentOrganizationComboBox(true);
		ctrlDepartment = new TDepartmentReference(TYPE.LABEL, "C00722");
		ctrlComment = new TLabel();
		listTab = new TTabbedPane();
		tree = new TDnDTree();
		sp = new JScrollPane();
		pnlTable = new TPanel();

		// TTableの機能拡張クラス
		dndTable = new TDnDTable();
		dndTable.addColumn(SC.code, "C00698", 120); // 部門コード
		dndTable.addColumn(SC.name, "C00724", 260); // 部門略称
		dndTable.addColumn(SC.bean, "", 0);
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

		// 複写ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 130);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = btnCopy.getX() + btnCopy.getWidth() + 5;
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
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 130);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyTop, gc);

		// 新規作成パネル
		pnlNew.setLayout(null);

		TGuiUtil.setComponentSize(pnlNew, 0, 80);
		gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;

		pnlBodyTop.add(pnlNew, gc);

		x = 0;
		int y = 10;

		// 組織コード
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setLabelSize(80);
		ctrlOrganizationCode.setLocation(x, y);
		pnlNew.add(ctrlOrganizationCode);

		y += ctrlOrganizationCode.getHeight();

		// レベル０
		ctrlDepartment.resize();
		TGuiUtil.setComponentSize(ctrlDepartment, 315, 20);
		ctrlDepartment.setEditable(false);
		ctrlDepartment.setLocation(x, y);
		pnlNew.add(ctrlDepartment);

		// コメント
		TGuiUtil.setComponentSize(ctrlComment, 475, 20);
		ctrlComment.setLangMessageID("C11607");
		ctrlComment.setForeground(Color.blue);
		ctrlComment.setLocation(180, 60);
		pnlNew.add(ctrlComment);

		// リストタブ
		listTab.switchMode();
		listTab.setOpaque(false);

		TGuiUtil.setComponentSize(sp, 270, 400);
		sp.getViewport().add(tree, null);
		listTab.addTab(getWord("C02473"), sp);
		listTab.setSelectedIndex(0);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 30, 10, 30);

		pnlBodyTop.add(listTab, gc);

		// 一覧
		dndTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		TGuiUtil.setComponentSize(pnlTable, 274, 400);
		pnlTable.setLayout(new BoxLayout(pnlTable, BoxLayout.X_AXIS));

		pnlTable.add(dndTable);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);

		pnlBodyTop.add(pnlTable, gc);
	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlOrganizationCode.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		dndTable.setTabControlNo(i++);
	}

}
