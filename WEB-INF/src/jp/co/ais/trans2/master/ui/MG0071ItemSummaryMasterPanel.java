package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 科目集計マスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0071ItemSummaryMasterPanel extends TMainPanel {

	/** コントロールクラス */
	public MG0071ItemSummaryMasterPanelCtrl ctrl;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 複写ボタン */
	public TImageButton btnCopy;

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

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 科目体系 */
	public TItemOrganizationReference ctrlItemOrganizationReference;

	/** 有効期限切れチェックボックス */
	public TCheckBox chkOutputTermEnd;

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
	public MG0071ItemSummaryMasterPanel(MG0071ItemSummaryMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** 科目コード */
		code,
		/** 科目略称 */
		name,
		/** 集計区分 */
		sumkbn,
		/** 科目種別 */
		kmkshu,
		/** 貸借区分 */
		dckbn,
		/** 科目エンティティ */
		bean,
	}

	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnCopy = new TImageButton(IconType.COPY);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlNew = new TPanel();
		pnlSearchCondition = new TPanel();
		ctrlItemOrganizationReference = new TItemOrganizationReference();
		chkOutputTermEnd = new TCheckBox();
		ctrlComment = new TLabel();
		listTab = new TTabbedPane();
		tree = new TDnDTree();
		sp = new JScrollPane();
		pnlTable = new TPanel();

		// TTableの機能拡張クラス
		dndTable = new TDnDTable();
		dndTable.addColumn(SC.code, "C00572", 70); // 科目コード
		dndTable.addColumn(SC.name, "C00730", 155); // 科目略称
		dndTable.addColumn(SC.sumkbn, "C01148", 60, SwingConstants.CENTER); // 集計区分
		dndTable.addColumn(SC.kmkshu, "C01007", 60, SwingConstants.CENTER); // 科目種別
		dndTable.addColumn(SC.dckbn, "C01226", 60, SwingConstants.CENTER); // 貸借区分
		dndTable.addColumn(SC.bean, "", 0);
	}

	@Override
	public void allocateComponents() {

		int x = 5;

		// 検索ボタン
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 130);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 複写ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 130);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// エクセルボタン
		x = btnCopy.getX() + btnCopy.getWidth() + 5;
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

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(450, 55);
		pnlNew.add(pnlSearchCondition);

		// 科目体系
		ctrlItemOrganizationReference.setLocation(10, 5);
		TGuiUtil.setComponentSize(ctrlItemOrganizationReference, 300, 50);
		pnlSearchCondition.add(ctrlItemOrganizationReference);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setLocation(0, 0);
		chkOutputTermEnd.setSize(0, 0);
		pnlSearchCondition.add(chkOutputTermEnd);

		// コメント
		ctrlComment.setSize(450, 15);
		ctrlComment.setLangMessageID("C11607");
		ctrlComment.setForeground(Color.blue);
		ctrlComment.setLocation(180, 65);
		pnlNew.add(ctrlComment);

		// リストタブ
		listTab.switchMode();
		listTab.setOpaque(false);

		TGuiUtil.setComponentSize(sp, 270, 400);
		sp.getViewport().add(tree, null);
		listTab.addTab(getWord("C02421"), sp);
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
		ctrlItemOrganizationReference.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		dndTable.setTabControlNo(i++);
	}

}
