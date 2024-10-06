package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * プログラムロールマスタの編集画面
 * 
 * @author AIS
 */
public class MG0260ProgramRoleMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090392L;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** コード */
	public TLabelField ctrlProgramCode;

	/** 名称 */
	public TLabelField ctrlProgramName;

	/** 略称 */
	public TLabelField ctrlProgramNames;

	/** 検索名称 */
	public TLabelField ctrlProgramNamek;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/** メニュータブとプログラム一覧の境界 */
	public JSplitPane splitPane;

	/** メニュータブ */
	public TProgramPanel prgPnl;

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

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** システムコード */
		systemCode,
		/** プログラムコード */
		programCode,
		/** プログラム名称 */
		programNames
	}

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0260ProgramRoleMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlProgramCode = new TLabelField();
		ctrlProgramName = new TLabelField();
		ctrlProgramNames = new TLabelField();
		ctrlProgramNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		splitPane = new JSplitPane();
		prgPnl = new TProgramPanel();
		pnlCenter = new TPanel();
		pnlRight = new TPanel();
		btnAddProgram = new TButton();
		btnDeleteProgram = new TButton();
		tbl = new TTable();
		tbl.addColumn(SC.systemCode, getWord("C00217"), 100);// システム区分
		tbl.addColumn(SC.programCode, getWord("C00818"), 100);// プログラムコード
		tbl.addColumn(SC.programNames, getWord("C00820"), 200);// プログラム略称
	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {

		setSize(850, 600);

		// 確定ボタン
		int x = 620 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 620;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(480, 155));
		pnlBodyTop.setMinimumSize(new Dimension(480, 155));
		pnlBodyTop.setPreferredSize(new Dimension(480, 155));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// システム区分
		x = 10;
		int y = 10;

		// コード
		ctrlProgramCode.setLabelSize(120);
		ctrlProgramCode.setFieldSize(75);
		ctrlProgramCode.setSize(200, 20);
		ctrlProgramCode.setLocation(x, y);
		ctrlProgramCode.setLabelText(getWord("C11154"));
		ctrlProgramCode.setMaxLength(TransUtil.PROGRAM_CODE_LENGTH);
		ctrlProgramCode.setImeMode(false);
		ctrlProgramCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlProgramCode);

		// 名称
		ctrlProgramName.setLabelSize(120);
		ctrlProgramName.setFieldSize(300);
		ctrlProgramName.setSize(425, 20);
		ctrlProgramName.setLocation(x, y += 25);
		ctrlProgramName.setLabelText(getWord("C11155"));
		ctrlProgramName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBodyTop.add(ctrlProgramName);

		// 略称
		ctrlProgramNames.setLabelSize(120);
		ctrlProgramNames.setFieldSize(150);
		ctrlProgramNames.setSize(275, 20);
		ctrlProgramNames.setLocation(x, y += 25);
		ctrlProgramNames.setLabelText(getWord("C11156"));
		ctrlProgramNames.setMaxLength(TransUtil.PROGRAM_NAMES_LENGTH);
		pnlBodyTop.add(ctrlProgramNames);

		// 検索名称
		ctrlProgramNamek.setLabelSize(120);
		ctrlProgramNamek.setFieldSize(300);
		ctrlProgramNamek.setSize(425, 20);
		ctrlProgramNamek.setLocation(x, y += 25);
		// ロール検索名称
		ctrlProgramNamek.setLangMessageID("C11157");
		ctrlProgramNamek.setMaxLength(TransUtil.PROGRAM_NAMEK_LENGTH);
		pnlBodyTop.add(ctrlProgramNamek);

		// 開始年月日
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(x, y += 25);
		dtBeginDate.setLangMessageID("C00055");
		pnlBodyTop.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(x, y += 25);
		dtEndDate.setLangMessageID("C00261");
		pnlBodyTop.add(dtEndDate);

		// splitPane
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(300);
		splitPane.setDividerSize(2);
		splitPane.setBorder(null);

		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlBody.add(splitPane, gc);

		// メニュー一覧
		JScrollPane sp = new JScrollPane(prgPnl);
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
		btnAddProgram.setLangMessageID("C10140");// ←
		btnAddProgram.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 20, 0);
		pnlCenter.add(btnAddProgram, gc);

		// プログラム削除ボタン
		btnDeleteProgram.setLangMessageID("C10139");// →
		btnDeleteProgram.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(20, 0, 0, 0);
		pnlCenter.add(btnDeleteProgram, gc);

		// プログラム一覧
		tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		gc.gridx = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlRight.add(tbl, gc);
	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlProgramCode.setTabControlNo(i++);
		ctrlProgramName.setTabControlNo(i++);
		ctrlProgramNames.setTabControlNo(i++);
		ctrlProgramNamek.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnAddProgram.setTabControlNo(i++);
		btnDeleteProgram.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

	/**
	 * 選択プログラム一覧
	 * 
	 * @author AIS
	 */
	public class TProgramPanel extends TPanel {

		/** serialVersionUID */
		private static final long serialVersionUID = -8692998405247935610L;

		/** ツリー */
		public TTree tree;

		/** タブのタイトル */
		protected String title;

		/** ルート */
		protected DefaultMutableTreeNode root;

		/**
		 * 
		 */
		public TProgramPanel() {
			initComponents();
			allocateComponents();
		}

		/**
		 * コンストラクタ
		 */
		public void initComponents() {
			root = new DefaultMutableTreeNode();
			tree = new TTree(root);
			tree.setRootVisible(false);
		}

		/**
		 * 初期化処理
		 */
		public void allocateComponents() {

			setLayout(new GridBagLayout());
			setBackground(Color.white);

			GridBagConstraints gc1 = new GridBagConstraints();
			gc1.weightx = 1.0d;
			gc1.weighty = 1.0d;
			gc1.fill = GridBagConstraints.BOTH;
			add(tree, gc1);
		}

		/**
		 * タイトルを取得する
		 * 
		 * @return タイトル
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * タイトルを設定する
		 * 
		 * @param title
		 */
		public void setTitle(String title) {
			this.title = title;
			setName(getTabChar(title));
		}

		/**
		 * メニュータブにセットする文字列を返す
		 * 
		 * @param str タイトル
		 * @return メニュータブにセットする文字列
		 */
		protected String getTabChar(String str) {
			String rt = "<html>　";

			for (int i = 0; i < str.length(); i++) {
				rt = rt + "<br>" + str.charAt(i);
			}
			rt = rt + "<br>　";

			return rt;
		}

		/**
		 * ルートを取得する
		 * 
		 * @return root
		 */
		public DefaultMutableTreeNode getRoot() {
			return root;
		}

		/**
		 * ルートを設定する
		 * 
		 * @param root
		 */
		public void setRoot(DefaultMutableTreeNode root) {
			this.root = root;
		}
	}
}