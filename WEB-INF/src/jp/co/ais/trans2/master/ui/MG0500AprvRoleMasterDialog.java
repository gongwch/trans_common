package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 承認権限ロールマスタの編集画面
 */
public class MG0500AprvRoleMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090392L;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** ロールコード */
	public TLabelField ctrlRoleCode;

	/** ロール名称 */
	public TLabelField ctrlRoleName;

	/** ロール略称 */
	public TLabelField ctrlRoleNames;

	/** ロール検索名称 */
	public TLabelField ctrlRoleNamek;

	/** 開始年月日 */
	public TLabelPopupCalendar ctrlBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar ctrlEndDate;

	/** 左と右の境界 */
	public JSplitPane splitPane;

	/** 中央パネル */
	public TPanel pnlCenter;

	/** 右パネル */
	public TPanel pnlRight;

	/** 左パネル */
	public TPanel pnlLeft;

	/** 追加ボタン */
	public TButton btnAdd;

	/** 削除ボタン */
	public TButton btnCancel;

	/** 右テーブル一覧 */
	public TTable tblRight;

	/** 左テーブル一覧 */
	public TTable tblLeft;

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** ユーザーコード */
		userCode,
		/** ユーザー名称 */
		userName,
		/** 所属部門名称 */
		depName;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0500AprvRoleMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlRoleCode = new TLabelField();
		ctrlRoleName = new TLabelField();
		ctrlRoleNames = new TLabelField();
		ctrlRoleNamek = new TLabelField();
		ctrlBeginDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		ctrlEndDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		splitPane = new JSplitPane();
		pnlCenter = new TPanel();
		pnlRight = new TPanel();
		pnlLeft = new TPanel();
		btnAdd = new TButton();
		btnCancel = new TButton();
		tblRight = new TTable();
		tblRight.addColumn(SC.userCode, getWord("C00589"), 80);// ユーザーコード
		tblRight.addColumn(SC.userName, getWord("C00691"), 110);// ユーザー名称
		tblRight.addColumn(SC.depName, getWord("C11163"), 110);// 所属部門名称
		tblLeft = new TTable();
		tblLeft.addColumn(SC.userCode, getWord("C11938"), 80);// 権限ユーザー
		tblLeft.addColumn(SC.userName, getWord("C00691"), 110);// ユーザー名称
		tblLeft.addColumn(SC.depName, getWord("C11163"), 110);// 所属部門名称
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
		ctrlRoleCode.setLabelSize(120);
		ctrlRoleCode.setFieldSize(75);
		ctrlRoleCode.setSize(200, 20);
		ctrlRoleCode.setLocation(x, y);
		ctrlRoleCode.setLabelText(getWord("C11154"));
		ctrlRoleCode.setMaxLength(TransUtil.USER_CODE_LENGTH);
		ctrlRoleCode.setImeMode(false);
		ctrlRoleCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlRoleCode);

		// 名称
		ctrlRoleName.setLabelSize(120);
		ctrlRoleName.setFieldSize(300);
		ctrlRoleName.setSize(425, 20);
		ctrlRoleName.setLocation(x, y += 25);
		ctrlRoleName.setLabelText(getWord("C11155"));
		ctrlRoleName.setMaxLength(40);
		pnlBodyTop.add(ctrlRoleName);

		// 略称
		ctrlRoleNames.setLabelSize(120);
		ctrlRoleNames.setFieldSize(220);
		ctrlRoleNames.setSize(345, 20);
		ctrlRoleNames.setLocation(x, y += 25);
		ctrlRoleNames.setLabelText(getWord("C11156"));
		ctrlRoleNames.setMaxLength(20);
		pnlBodyTop.add(ctrlRoleNames);

		// 検索名称
		ctrlRoleNamek.setLabelSize(120);
		ctrlRoleNamek.setFieldSize(300);
		ctrlRoleNamek.setSize(425, 20);
		ctrlRoleNamek.setLocation(x, y += 25);
		// ロール検索名称
		ctrlRoleNamek.setLangMessageID("C11157");
		ctrlRoleNamek.setMaxLength(40);
		pnlBodyTop.add(ctrlRoleNamek);

		// 開始年月日
		ctrlBeginDate.setLabelSize(120);
		ctrlBeginDate.setSize(120 + ctrlBeginDate.getCalendarSize() + 5, 20);
		ctrlBeginDate.setLocation(x, y += 25);
		ctrlBeginDate.setLangMessageID("C00055");
		pnlBodyTop.add(ctrlBeginDate);

		// 終了年月日
		ctrlEndDate.setLabelSize(120);
		ctrlEndDate.setSize(120 + ctrlBeginDate.getCalendarSize() + 5, 20);
		ctrlEndDate.setLocation(x, y += 25);
		ctrlEndDate.setLangMessageID("C00261");
		pnlBodyTop.add(ctrlEndDate);

		// splitPane
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(365);
		splitPane.setDividerSize(2);
		splitPane.setBorder(null);

		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 0, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlBody.add(splitPane, gc);

		pnlRight.setLayout(new GridBagLayout());
		splitPane.setRightComponent(pnlRight);

		pnlCenter.setLayout(new GridBagLayout());
		pnlCenter.setMaximumSize(new Dimension(85, 600));
		pnlCenter.setMinimumSize(new Dimension(85, 600));
		pnlCenter.setPreferredSize(new Dimension(85, 600));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.VERTICAL;
		pnlRight.add(pnlCenter, gc);

		// 左側
		pnlLeft.setLayout(new GridBagLayout());
		splitPane.setLeftComponent(pnlLeft);

		// ユーザー追加ボタン
		btnAdd.setLangMessageID("C10140");// ←
		btnAdd.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 20, 20, 0);
		pnlCenter.add(btnAdd, gc);

		// ユーザー削除ボタン
		btnCancel.setLangMessageID("C10139");// →
		btnCancel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(20, 20, 0, 0);
		pnlCenter.add(btnCancel, gc);

		// 右一覧
		tblRight.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 20, 0, 0);
		gc.gridx = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlRight.add(tblRight, gc);

		// 左一覧
		tblLeft.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		GridBagConstraints gc1 = new GridBagConstraints();
		gc1.insets = new Insets(0, 5, 0, 0);
		gc1.gridx = 1;
		gc1.weightx = 1.0d;
		gc1.weighty = 1.0d;
		gc1.fill = GridBagConstraints.BOTH;
		pnlLeft.add(tblLeft, gc1);

	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlRoleCode.setTabControlNo(i++);
		ctrlRoleName.setTabControlNo(i++);
		ctrlRoleNames.setTabControlNo(i++);
		ctrlRoleNamek.setTabControlNo(i++);
		ctrlBeginDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnAdd.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}