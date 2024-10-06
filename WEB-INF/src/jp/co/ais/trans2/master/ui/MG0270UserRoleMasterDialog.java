package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ユーザーロールマスタの編集画面
 * 
 * @author AIS
 */
public class MG0270UserRoleMasterDialog extends TDialog {

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

	/** タブ */
	public TTabbedPane tabLvlSetting;

	/** 科目開示レベル一覧 */
	public TTable itemTbl;

	/** 部門開示レベル一覧 */
	public TTable depTbl;

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {

		/** システムコード */
		DIVISION(0),
		/** プログラムコード */
		CODE(1),
		/** プログラム名称 */
		NAMES(2);

		/** 編集モード */
		private int column;

		/**
		 * コンストラクタ
		 * 
		 * @param column
		 */
		private SC(int column) {
			this.column = column;
		}

		/**
		 * @return カラム
		 */
		protected int getColumn() {
			return column;
		}
	}

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0270UserRoleMasterDialog(Company company, Frame parent, boolean mordal) {
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
		tabLvlSetting = new TTabbedPane();
		itemTbl = new TTable();
		itemTbl.getTableHeader().setReorderingAllowed(false);
		// 全社 部門
		itemTbl.addColumn(SC.DIVISION, getWord("C11224") + "  " + getWord("C00467") + "  " + getWord("C00412"), 100);
		// 科目コード
		itemTbl.addColumn(SC.CODE, getWord("C00572"), 100);
		// 科目略称
		itemTbl.addColumn(SC.NAMES, getWord("C00730"), 200);
		TButtonGroupEditorRenderer bger = new TButtonGroupEditorRenderer(new String[] { "0", "1", "2" }, itemTbl);
		itemTbl.getColumnModel().getColumn(SC.DIVISION.getColumn()).setCellRenderer(bger);
		itemTbl.getColumnModel().getColumn(SC.DIVISION.getColumn()).setCellEditor(bger);

		depTbl = new TTable();
		depTbl.getTableHeader().setReorderingAllowed(false);
		// 全選択
		depTbl.addColumn(SC.DIVISION, getWord("C11225"), 100, TCheckBox.class);
		// 部門コード
		depTbl.addColumn(SC.CODE, getWord("C00698"), 100);
		// 部門略称
		depTbl.addColumn(SC.NAMES, getWord("C00724"), 200);
	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {

		setSize(720, 700);

		// 確定ボタン
		int x = 545 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 545;
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

		// ロールコード
		ctrlProgramCode.setLabelSize(120);
		ctrlProgramCode.setFieldSize(75);
		ctrlProgramCode.setSize(200, 20);
		ctrlProgramCode.setLocation(x, y);
		ctrlProgramCode.setLangMessageID("C11154");
		ctrlProgramCode.setMaxLength(TransUtil.PROGRAM_CODE_LENGTH);
		ctrlProgramCode.setImeMode(false);
		ctrlProgramCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlProgramCode);

		// ロール名称
		ctrlProgramName.setLabelSize(120);
		ctrlProgramName.setFieldSize(300);
		ctrlProgramName.setSize(425, 20);
		ctrlProgramName.setLocation(x, y += 25);
		ctrlProgramName.setLangMessageID("C11155");
		ctrlProgramName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBodyTop.add(ctrlProgramName);

		// ロール略称
		ctrlProgramNames.setLabelSize(120);
		ctrlProgramNames.setFieldSize(150);
		ctrlProgramNames.setSize(275, 20);
		ctrlProgramNames.setLocation(x, y += 25);
		ctrlProgramNames.setLangMessageID("C11156");
		ctrlProgramNames.setMaxLength(TransUtil.PROGRAM_NAMES_LENGTH);
		pnlBodyTop.add(ctrlProgramNames);

		// ロール検索名称"
		ctrlProgramNamek.setLabelSize(120);
		ctrlProgramNamek.setFieldSize(300);
		ctrlProgramNamek.setSize(425, 20);
		ctrlProgramNamek.setLocation(x, y += 25);
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

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(10, 10, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 1;
		pnlBody.add(tabLvlSetting, gc);

		// タブ：科目開示レベル
		TPanel pnlKmk = new TPanel();
		pnlKmk.setLayout(new GridBagLayout());
		tabLvlSetting.addTab(getWord("C11226"), pnlKmk, false);

		// ラベルパネル
		TPanel pnlKmkLbl1 = new TPanel();
		pnlKmkLbl1.setMaximumSize(new Dimension(230, 60));
		pnlKmkLbl1.setMinimumSize(new Dimension(230, 60));
		pnlKmkLbl1.setPreferredSize(new Dimension(230, 60));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 5, 10, 0);
		pnlKmk.add(pnlKmkLbl1, gc);

		// ラベル1
		TLabel lblKmk1 = new TLabel();
		// <html>※全社にチェックすると<br>集計金額を見ることができます。<html>
		lblKmk1.setLangMessageID("<html>" + getWord("C11230") + "<br>" + getWord("C11231") + "<html>");
		lblKmk1.setMaximumSize(new Dimension(230, 30));
		lblKmk1.setMinimumSize(new Dimension(230, 30));
		lblKmk1.setPreferredSize(new Dimension(230, 30));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		pnlKmkLbl1.add(lblKmk1, gc);

		// ラベル2
		TLabel lblKmk2 = new TLabel();
		lblKmk2.setMaximumSize(new Dimension(230, 30));
		lblKmk2.setMinimumSize(new Dimension(230, 30));
		lblKmk2.setPreferredSize(new Dimension(230, 30));
		// <html>※部門にチェックした場合 表示部門を<br>部門開示レベルでチェックしてください。<html>
		lblKmk2.setLangMessageID("<html>" + getWord("C11232") + "<br>" + getWord("C11233") + "<html>");
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		pnlKmkLbl1.add(lblKmk2, gc);

		// スプレッド
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 1;
		pnlKmk.add(itemTbl, gc);

		// タブ：部門開示レベル
		TPanel pnlDep = new TPanel();
		pnlDep.setLayout(new GridBagLayout());
		tabLvlSetting.addTab(getWord("C11227"), pnlDep, false);

		// ラベルパネル
		TPanel pnlDepLbl1 = new TPanel();
		pnlDepLbl1.setMaximumSize(new Dimension(220, 30));
		pnlDepLbl1.setMinimumSize(new Dimension(220, 30));
		pnlDepLbl1.setPreferredSize(new Dimension(220, 30));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 5, 10, 10);
		pnlDep.add(pnlDepLbl1, gc);

		// ラベル1
		TLabel lblDep1 = new TLabel();
		// <html>※チェックした部門のみ<br>帳票で参照することが可能です。</html>
		lblDep1.setLangMessageID("<html>" + getWord("C11234") + "<br>" + getWord("C11235") + "</html>");
		lblDep1.setMaximumSize(new Dimension(220, 30));
		lblDep1.setMinimumSize(new Dimension(220, 30));
		lblDep1.setPreferredSize(new Dimension(220, 30));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		pnlDepLbl1.add(lblDep1, gc);

		// スプレッド
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 1;
		pnlDep.add(depTbl, gc);
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
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}