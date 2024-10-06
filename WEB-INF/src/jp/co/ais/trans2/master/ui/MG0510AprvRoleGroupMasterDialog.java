package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.define.TransUtil;
import jp.co.ais.trans2.model.company.*;

/**
 * 承認権限ロールマスタの編集画面
 */
public class MG0510AprvRoleGroupMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** コード */
	public TLabelField ctrlGroupCode;

	/** 名称 */
	public TLabelField ctrlGroupName;

	/** 略称 */
	public TLabelField ctrlGroupNames;

	/** 検索名称 */
	public TLabelField ctrlGroupNamek;

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

	/** 左パネル行操作 ▲ */
	public TButton btnUp;

	/** 左パネル行操作 ▼ */
	public TButton btnDown;

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
		/** bean */
		bean,
		/** コード */
		code,
		/** 名称 */
		name,
	}

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0510AprvRoleGroupMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlGroupCode = new TLabelField();
		ctrlGroupName = new TLabelField();
		ctrlGroupNames = new TLabelField();
		ctrlGroupNamek = new TLabelField();
		ctrlBeginDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		ctrlEndDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMD);
		splitPane = new JSplitPane();
		pnlCenter = new TPanel();
		pnlRight = new TPanel();
		btnUp = new TButton();
		btnDown = new TButton();
		pnlLeft = new TPanel();
		btnAdd = new TButton();
		btnCancel = new TButton();
		tblRight = new TTable();
		tblRight.addColumn(SC.bean, getWord(""), -1);// bean
		tblRight.addColumn(SC.code, getWord("C11154"), 80);// コード
		tblRight.addColumn(SC.name, getWord("C11155"), 110);// 名称
		tblLeft = new TTable();
		tblLeft.addColumn(SC.bean, getWord(""), -1);// bean
		tblLeft.addColumn(SC.code, getWord("C11154"), 80);// コード
		tblLeft.addColumn(SC.name, getWord("C11155"), 110);// 名称
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
		ctrlGroupCode.setLabelSize(120);
		ctrlGroupCode.setFieldSize(75);
		ctrlGroupCode.setSize(200, 20);
		ctrlGroupCode.setLocation(x, y);
		ctrlGroupCode.setLabelText(getWord("C12230"));
		ctrlGroupCode.setMaxLength(TransUtil.USER_CODE_LENGTH);
		ctrlGroupCode.setImeMode(false);
		ctrlGroupCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlGroupCode);

		// 名称
		ctrlGroupName.setLabelSize(120);
		ctrlGroupName.setFieldSize(300);
		ctrlGroupName.setSize(425, 20);
		ctrlGroupName.setLocation(x, y += 25);
		ctrlGroupName.setLabelText(getWord("C12231"));
		ctrlGroupName.setMaxLength(40);
		pnlBodyTop.add(ctrlGroupName);

		// 略称
		ctrlGroupNames.setLabelSize(120);
		ctrlGroupNames.setFieldSize(220);
		ctrlGroupNames.setSize(345, 20);
		ctrlGroupNames.setLocation(x, y += 25);
		ctrlGroupNames.setLabelText(getWord("C12232"));
		ctrlGroupNames.setMaxLength(20);
		pnlBodyTop.add(ctrlGroupNames);

		// 検索名称
		ctrlGroupNamek.setLabelSize(120);
		ctrlGroupNamek.setFieldSize(300);
		ctrlGroupNamek.setSize(425, 20);
		ctrlGroupNamek.setLocation(x, y += 25);
		// ロール検索名称
		ctrlGroupNamek.setLangMessageID("C12233");
		ctrlGroupNamek.setMaxLength(40);
		pnlBodyTop.add(ctrlGroupNamek);

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
		gc.insets = new Insets(20, 20, 0, 0);
		gc.gridx = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlRight.add(tblRight, gc);

		TPanel pnlLeftButton = new TPanel();
		pnlLeftButton.setLayout(null);
		TGuiUtil.setComponentSize(pnlLeftButton, 60, 20);

		Icon up = ResourceUtil.getImage(TAppletMain.class, "images/btnrowup.png");
		TGuiUtil.setComponentSize(btnUp, 20, 20);
		btnUp.setIcon(up);
		btnUp.setLocation(35, 0);
		pnlLeftButton.add(btnUp);

		Icon down = ResourceUtil.getImage(TAppletMain.class, "images/btnrowdown.png");
		TGuiUtil.setComponentSize(btnDown, 20, 20);
		btnDown.setIcon(down);
		btnDown.setLocation(55, 0);
		pnlLeftButton.add(btnDown);

		GridBagConstraints gc1 = new GridBagConstraints();
		gc1.insets = new Insets(0, 5, 0, 0);
		gc1.gridx = 1;
		gc1.gridy = 1;
		gc1.weightx = 1.0d;
		gc1.weighty = 0.0d;
		gc1.fill = GridBagConstraints.HORIZONTAL;
		pnlLeft.add(pnlLeftButton, gc1);

		// 左一覧
		tblLeft.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblLeft.setSortable(false);
		gc1.gridy = 2;
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
		ctrlGroupCode.setTabControlNo(i++);
		ctrlGroupName.setTabControlNo(i++);
		ctrlGroupNames.setTabControlNo(i++);
		ctrlGroupNamek.setTabControlNo(i++);
		ctrlBeginDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnAdd.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}