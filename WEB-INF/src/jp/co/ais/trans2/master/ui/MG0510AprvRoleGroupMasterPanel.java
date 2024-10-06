package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 承認権限ロールマスタの指示画面
 */
public class MG0510AprvRoleGroupMasterPanel extends TMainPanel {

	/** 上部 */
	public TPanel pnlBodyTop;

	/** 下部 */
	public TPanel pnlBodyButtom;

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 編集ボタン */
	public TImageButton btnModify;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 範囲検索コンポーネント */
	public TAprvRoleGroupReferenceRange ctrlSearch;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 有効期間切れを表示するか */
	public TCheckBox ctrlOutputTermEnd;

	/** 承認権限ロールマスタ一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 */
	public enum SC {

		/** BEAN */
		bean,

		/** ロールコード */
		roleCode,

		/** ロール名称 */
		roleName,

		/** ロール略称 */
		roleNames,

		/** ロール検索名称 */
		roleNamek,

		/** 開始年月日 */
		strDate,

		/** 終了年月日 */
		endDate;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 */
	public MG0510AprvRoleGroupMasterPanel(Company company) {
		super(company);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 上部
		pnlBodyTop.setLayout(null);
		TGuiUtil.setComponentSize(pnlBodyTop, 0, 100);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 範囲検索コンポーネント
		ctrlSearch.setLocation(20, 20);
		// ctrlSearch
		pnlSearchCondition.add(ctrlSearch);

		// 有効期間切れ表示
		ctrlOutputTermEnd.setLangMessageID("C11089");
		ctrlOutputTermEnd.setSize(140, 20);
		ctrlOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(ctrlOutputTermEnd);

		// 下部
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);
	}

	@Override
	public void initComponents() {
		pnlBodyTop = new TPanel();
		pnlBodyButtom = new TPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		ctrlSearch = new TAprvRoleGroupReferenceRange();
		ctrlOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.bean, "BEAN", -1);
		tbl.addColumn(SC.roleCode, "C12230", 100); // 承認グループコード
		tbl.addColumn(SC.roleName, "C12231", 200); // 承認グループ名称
		tbl.addColumn(SC.roleNames, "C12232", 200); // 承認グループ略称
		tbl.addColumn(SC.roleNamek, "C12233", 200); // 承認グループ検索名称
		tbl.addColumn(SC.strDate, "C00055", 100, SwingConstants.CENTER); // 開始年月日
		tbl.addColumn(SC.endDate, "C00261", 100, SwingConstants.CENTER); // 終了年月日
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlSearch.setTabControlNo(i++);
		ctrlOutputTermEnd.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}