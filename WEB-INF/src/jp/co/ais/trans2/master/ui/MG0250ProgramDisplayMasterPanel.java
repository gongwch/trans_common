package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * プログラム表示設定マスタの指示画面レイアウト
 * 
 * @author AIS TODO 多言語対応
 */
public class MG0250ProgramDisplayMasterPanel extends TMainPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 7022921575858055294L;

	/** タブ追加ボタン */
	public TButton btnAddTab;

	/** タブ名変更ボタン */
	public TButton btnModifyTab;

	/** タブ削除ボタン */
	public TButton btnDeleteTab;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** メニュータブとプログラム一覧の境界 */
	public JSplitPane splitPane;

	/** メニュータブ */
	public TTabbedPane menuTab;

	/** 右パネル */
	public TPanel pnlCenter;

	/** 右パネル */
	public TPanel pnlRight;

	/** プログラム追加ボタン */
	public TButton btnAddProgram;

	/** プログラム削除ボタン */
	public TButton btnDeleteProgram;

	/** プログラム一覧 */
	public TTable tbl;

	public enum SC {
		systemCode, programCode, programNames
	}

	@Override
	public void initComponents() {
		btnAddTab = new TButton();
		btnModifyTab = new TButton();
		btnDeleteTab = new TButton();
		btnSettle = new TImageButton(IconType.SETTLE);
		splitPane = new JSplitPane();
		menuTab = new TTabbedPane();
		pnlCenter = new TPanel();
		pnlRight = new TPanel();
		btnAddProgram = new TButton();
		btnDeleteProgram = new TButton();
		tbl = new TTable();
		tbl.addColumn(SC.systemCode, getWord("C00217"), 100);//システム区分
		tbl.addColumn(SC.programCode, getWord("C00818"), 100);//プログラムコード
		tbl.addColumn(SC.programNames, getWord("C00820"), 200);//プログラム略称
	}

	@Override
	public void allocateComponents() {

		// タブ追加ボタン
		int x = HEADER_LEFT_X;
		btnAddTab.setLangMessageID("C11176");
		btnAddTab.setShortcutKey(KeyEvent.VK_F1);
		btnAddTab.setSize(25, 110);
		btnAddTab.setLocation(x, HEADER_Y);
		pnlHeader.add(btnAddTab);

		// タブ修正ボタン
		x = x + btnAddTab.getWidth() + HEADER_MARGIN_X;
		btnModifyTab.setLangMessageID("C11177");
		btnModifyTab.setShortcutKey(KeyEvent.VK_F3);
		btnModifyTab.setSize(25, 110);
		btnModifyTab.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModifyTab);

		// タブ削除ボタン
		x = x + btnModifyTab.getWidth() + HEADER_MARGIN_X;
		btnDeleteTab.setLangMessageID("C11178");
		btnDeleteTab.setShortcutKey(KeyEvent.VK_F5);
		btnDeleteTab.setSize(25, 110);
		btnDeleteTab.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDeleteTab);

		// 確定ボタン
		x = x + btnDeleteTab.getWidth() + HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// splitPane
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(300);
		splitPane.setDividerSize(2);
		splitPane.setBorder(null);

		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlBody.add(splitPane, gc);

		// メニュー一覧
		JScrollPane sp = new JScrollPane(menuTab);
		splitPane.setLeftComponent(sp);

		// 右側
		pnlRight.setLayout(new GridBagLayout());
		splitPane.setRightComponent(pnlRight);

		pnlCenter.setLayout(new GridBagLayout());
		pnlCenter.setMaximumSize(new Dimension(60, 600));
		pnlCenter.setMinimumSize(new Dimension(60, 600));
		pnlCenter.setPreferredSize(new Dimension(60, 600));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.VERTICAL;
		pnlRight.add(pnlCenter, gc);

		// プログラム追加ボタン
		btnAddProgram.setLangMessageID("C10140");
		btnAddProgram.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 20, 0);
		pnlCenter.add(btnAddProgram, gc);

		// プログラム削除ボタン
		btnDeleteProgram.setLangMessageID("C10139");
		btnDeleteProgram.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(20, 0, 0, 0);
		pnlCenter.add(btnDeleteProgram, gc);

		// プログラム一覧
		JScrollPane spTable = new JScrollPane(tbl);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 10);
		gc.gridx = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlRight.add(spTable, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		btnAddTab.setTabControlNo(i++);

	}

	/**
	 * タブメニュー
	 * 
	 * @author AIS
	 */
	public class TProgramMenuPanel extends TPanel {

		/** serialVersionUID */
		private static final long serialVersionUID = -8692998405247935610L;

		/** メニューツリー */
		public TTree tree;

		/** タブのタイトル */
		protected String title;

		/** ルート */
		protected DefaultMutableTreeNode root;

		/**
		 * 
		 *
		 */
		public TProgramMenuPanel() {
			initComponents();
			allocateComponents();
		}

		public void initComponents() {
			root = new DefaultMutableTreeNode();
			tree = new TTree(root);
			tree.setRootVisible(false);
		}

		public void allocateComponents() {

			setLayout(new GridBagLayout());
			setBackground(Color.white);

			GridBagConstraints gc = new GridBagConstraints();
			gc.weightx = 1.0d;
			gc.weighty = 1.0d;
			gc.fill = GridBagConstraints.BOTH;
			add(tree, gc);

		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
			setName(getTabChar(title));
		}

		/**
		 * メニュータブにセットする文字列を返す
		 * 
		 * @param title タイトル
		 * @return メニュータブにセットする文字列
		 */
		protected String getTabChar(String title) {
			String rt = "<html>　";

			for (int i = 0; i < title.length(); i++) {
				rt = rt + "<br>" + title.charAt(i);
			}
			rt = rt + "<br>　";

			return rt;
		}

		public DefaultMutableTreeNode getRoot() {
			return root;
		}

		public void setRoot(DefaultMutableTreeNode root) {
			this.root = root;
		}

	}
}
