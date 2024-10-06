package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * メニュー表示マスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0440MenuMasterPanel extends TMainPanel {

	/** コントロールクラス */
	public MG0440MenuMasterPanelCtrl ctrl;

	/** 検索 */
	public TImageButton btnSearch;

	/** 確定 */
	public TImageButton btnSettle;

	/** タブ新規作成パネル */
	public TPanel pnlTabNew;

	/** タブ新規作成 */
	public TButton btnNewTab;

	/** メニュータブ */
	public TLeftColorTabbedPane menuTab;

	/** 一覧パネル */
	public TPanel pnlTable;

	/** 一覧 */
	public TDnDTable dndTable;

	/**
	 * コンストラクタ
	 * 
	 * @param panelCtrl コントロール
	 */
	public MG0440MenuMasterPanel(MG0440MenuMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** プログラムコード */
		code,
		/** プログラム略称 */
		name,
		/** プログラムエンティティ */
		bean,
	}

	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlTabNew = new TPanel();
		btnNewTab = new TButton();
		menuTab = new TLeftColorTabbedPane();
		pnlTable = new TPanel();

		// TTableの機能拡張クラス
		dndTable = new TDnDTable();
		dndTable.addColumn(SC.code, "C11403", 120); // メニューコード
		dndTable.addColumn(SC.name, "C00820", 260); // プログラム略称
		dndTable.addColumn(SC.bean, "", 0);
	}

	@Override
	public void allocateComponents() {

		int x = HEADER_LEFT_X;

		// 検索ボタン
		btnSearch.setLangMessageID("C00155"); // 検索
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 130);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 確定ボタン
		btnSettle.setLangMessageID("C01019"); // 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 130);
		btnSettle.setLocation(x + btnSearch.getWidth() + 5, HEADER_Y);
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

		// タブ新規作成パネル
		pnlTabNew.setLayout(null);

		TGuiUtil.setComponentSize(pnlTabNew, 200, 40);
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;

		pnlBodyTop.add(pnlTabNew, gc);

		// タブ新規作成
		btnNewTab.setLangMessageID("C11176"); // タブ追加
		btnNewTab.setSize(25, 100);
		btnNewTab.setLocation(30, 10);
		pnlTabNew.add(btnNewTab);

		// メニュータブ
		menuTab.switchMode();
		menuTab.setOpaque(false);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);

		pnlBodyTop.add(menuTab, gc);

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
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnNewTab.setTabControlNo(i++);
		dndTable.setTabControlNo(i++);
	}

}
