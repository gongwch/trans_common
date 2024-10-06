package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * 残高一覧の指示画面。
 * 
 * @author AIS
 */
public class TBalanceListDialog extends TDialog {

	/** true:選択はチェックボックスを使用＜Client＞ */
	public static boolean isUseChk = ClientConfig.isFlagOn("trans.use.zan.dialog.select.check");

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** エンティティ */
		BEAN,

		/** 選択チェックボックス */
		CHECK,

		/** 取引先 */
		CUSTOMER_NAME,

		/** 請求日 */
		CLAlM_DATE,

		/** 伝票番号 */
		SLIP_NO,

		/** 請求書番号 */
		CLAlM_NO,

		/** 入金予定日 */
		PAYMENT_DATE,

		/** 通貨 */
		CUR_CODE,

		/** 未消込金額（外貨） */
		NON_ERASE_INPUT_AMOUNT,

		/** 未消込金額 */
		NON_ERASE_AMOUNT,

		/** 請求金額（外貨） */
		INPUT_AMOUNT,

		/** 請求金額 */
		AMOUNT,

		/** 消込金額（外貨） */
		ERASE_INPUT_AMOUNT,

		/** 消込金額 */
		ERASE_AMOUNT,

		/** 計上部門 */
		DEPARTMENT_NAME,

		/** 摘要 */
		REMARKS;
	}

	/** 取引先条件 */
	public TTextField txtCustomer;

	/** 請求日/計上日条件 */
	public TPopupCalendar dtPaymentDay;

	/** 伝票番号条件 */
	public TTextField txtSlipNo;

	/** 請求書番号条件 */
	public TTextField txtBillNo;

	/** 予定日条件 */
	public TPopupCalendar dtExpectedDay;

	/** 計上部門条件 */
	public TTextField txtDep;

	/** 伝票摘要条件 */
	public TTextField txtTek;

	/** 条件ボタン */
	public TButton btnSearch;

	/** 確定ボタン */
	public TButton btnSettle;

	/** 取消ボタン */
	public TButton btnClose;

	/** 合計 */
	public TLabelNumericField ctrlTotal;

	/** ラジオボタン 個別取引先 */
	public TRadioPanel ctrlCustomerKind;

	/** 取引先 範囲指定 */
	public TCustomerReferenceRange ctrlClientRange;

	/** 部門 範囲指定 */
	public TDepartmentReferenceRange ctrlDepartmentRange;

	/** スプレッド */
	public TTable tbl;

	/** 選択 */
	public TCheckBox chk;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親フレーム
	 */
	public TBalanceListDialog(Frame parent) {
		super(parent, true);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親フレーム
	 */
	public TBalanceListDialog(Dialog parent) {
		super(parent, true);
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {
		gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setResizable(true);

		// ボディ領域
		pnlBody = new TPanel();
		pnlBody.setLayout(new GridBagLayout());
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {
		txtCustomer = new TTextField();
		dtPaymentDay = new TPopupCalendar();
		txtSlipNo = new TTextField();
		txtBillNo = new TTextField();
		dtExpectedDay = new TPopupCalendar();
		txtDep = new TTextField();
		txtTek = new TTextField();

		btnSearch = new TButton();
		btnSettle = new TButton();
		btnClose = new TButton();
		ctrlTotal = new TLabelNumericField();
		ctrlCustomerKind = new TRadioPanel("C00408");// 取引先
		ctrlClientRange = new TCustomerReferenceRange();
		ctrlDepartmentRange = new TDepartmentReferenceRange();
		tbl = new TTable();
		chk = new TCheckBox();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {
		Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSize(rect.width, 600);

		// 一覧
		if (isUseChk) {
			// チェックはスペースで設定可
			tbl.addCheckBoxColumn(SC.CHECK.ordinal());
			tbl.table.enableInputMethods(false); // テーブルのIMEは常にOFF
		} else {
			// 既存通り
			tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tbl.addSpreadSheetSelectChange(btnSettle);
			tbl.setEnterToButton(true);
		}

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(10, 10, 0, 10);
		pnlBody.add(tbl, gc);

		// BODY 条件用
		TPanel pnlSearch = new TPanel();
		pnlSearch.setLayout(null);
		pnlSearch.setMaximumSize(new Dimension(this.getWidth(), 150));
		pnlSearch.setMinimumSize(new Dimension(this.getWidth(), 150));
		pnlSearch.setPreferredSize(new Dimension(this.getWidth(), 150));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlSearch, gc);

		// 取引先条件
		int x = 40 + (isUseChk ? 30 : 0);
		int y = 2;
		txtCustomer.setLocation(x, y);
		txtCustomer.setImeMode(true);
		txtCustomer.setMaxLength(20);
		txtCustomer.setSize(200, 20);
		pnlSearch.add(txtCustomer);

		// 請求日/計上日条件
		x = txtCustomer.getX() + txtCustomer.getWidth();
		dtPaymentDay.setLocation(x, y);
		dtPaymentDay.setAllowableBlank(true);
		dtPaymentDay.clear();
		pnlSearch.add(dtPaymentDay);

		// 伝票番号条件
		x = dtPaymentDay.getX() + dtPaymentDay.getWidth();
		txtSlipNo.setLocation(x, y);
		txtSlipNo.setSize(130, 20);
		txtSlipNo.setImeMode(false);
		txtSlipNo.setMaxLength(20);
		pnlSearch.add(txtSlipNo);

		// 請求書番号条件
		x = txtSlipNo.getX() + txtSlipNo.getWidth();
		txtBillNo.setLocation(x, y);
		txtBillNo.setSize(130, 20);
		txtBillNo.setImeMode(false);
		txtBillNo.setMaxLength(20);
		pnlSearch.add(txtBillNo);

		// 予定日条件
		x = txtBillNo.getX() + txtBillNo.getWidth();
		dtExpectedDay.setLocation(x, y);
		dtExpectedDay.setAllowableBlank(true);
		dtExpectedDay.clear();
		pnlSearch.add(dtExpectedDay);

		// 計上部門条件
		x = dtExpectedDay.getX() + dtExpectedDay.getWidth() + 260;
		txtDep.setLocation(x, y);
		txtDep.setSize(150, 20);
		txtDep.setImeMode(true);
		txtDep.setMaxLength(20);
		txtDep.clear();
		pnlSearch.add(txtDep);
		
		// 伝票摘要条件
		x = txtDep.getX() + txtDep.getWidth();
		txtTek.setLocation(x, y);
		txtTek.setSize(300, 20);
		txtTek.setImeMode(true);
		txtTek.setMaxLength(80);
		txtTek.clear();
		pnlSearch.add(txtTek);

		x = 30;
		y = 32;

		// 条件ボタン
		btnSearch.setLangMessageID("C00155");// 検索
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, y);
		pnlSearch.add(btnSearch);

		// 確定ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");// 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setEnabled(false);
		btnSettle.setLocation(x, y);
		pnlSearch.add(btnSettle);

		// 取消ボタン
		x = x + btnSettle.getWidth() + HEADER_MARGIN_X;
		btnClose.setLangMessageID("C00405");// 取消
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, y);
		pnlSearch.add(btnClose);

		// 合計
		x = x + btnClose.getWidth() + HEADER_MARGIN_X;
		ctrlTotal.setLabelSize(30);
		ctrlTotal.setNumericFormat("#,##0");
		ctrlTotal.setLangMessageID("C00165");// 合計
		ctrlTotal.setLocation(x, y + 2);
		ctrlTotal.setEditable(false);
		pnlSearch.add(ctrlTotal);

		// 取引先
		x = x + ctrlTotal.getWidth() + 10;
		ctrlCustomerKind.addRadioButton("C10343", 100);// 回収取引先
		ctrlCustomerKind.addRadioButton("C10344", 100);// その他取引先
		ctrlCustomerKind.setLocation(x, y - 6);
		pnlSearch.add(ctrlCustomerKind);

		// 範囲指定
		x = 30;
		y = 70;
		TPanel pnlRenge = new TPanel();
		pnlRenge.setLayout(null);
		pnlRenge.setLangMessageID("C00433");// 範囲指定
		pnlRenge.setLocation(x, y);
		pnlRenge.setSize(ctrlClientRange.getWidth() + ctrlDepartmentRange.getWidth() + 50, 75);
		pnlSearch.add(pnlRenge);

		// 取引先範囲指定
		ctrlClientRange.setLocation(20, 20);
		Insets zero = new Insets(0, 0, 0, 0);
		ctrlClientRange.ctrlCustomerReferenceFrom.btn.setMargin(zero);
		ctrlClientRange.ctrlCustomerReferenceTo.btn.setMargin(zero);
		ctrlClientRange.ctrlCustomerReferenceFrom.btn.setLangMessageID("C10345");// 開始取引先
		ctrlClientRange.ctrlCustomerReferenceTo.btn.setLangMessageID("C10346");// 終了取引先
		ctrlClientRange.setCheckExist(true);
		pnlRenge.add(ctrlClientRange);

		// 部門範囲指定
		ctrlDepartmentRange.setLocation(ctrlClientRange.getX() + ctrlClientRange.getWidth() + 10, 20);
		ctrlDepartmentRange.ctrlDepartmentReferenceFrom.btn.setLangMessageID("C10347");// 開始部門
		ctrlDepartmentRange.ctrlDepartmentReferenceTo.btn.setLangMessageID("C10169");// 終了部門
		ctrlDepartmentRange.setCheckExist(true);
		pnlRenge.add(ctrlDepartmentRange);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		txtCustomer.setTabControlNo(++i);
		dtPaymentDay.setTabControlNo(++i);
		txtSlipNo.setTabControlNo(++i);
		txtBillNo.setTabControlNo(++i);
		dtExpectedDay.setTabControlNo(++i);
		txtDep.setTabControlNo(++i);
		txtTek.setTabControlNo(++i);
		btnSearch.setTabControlNo(++i);
		btnSettle.setTabControlNo(++i);
		btnClose.setTabControlNo(++i);
		ctrlCustomerKind.setTabControlNo(++i);
		ctrlClientRange.setTabControlNo(++i);
		ctrlDepartmentRange.setTabControlNo(++i);
		tbl.setTabControlNo(++i);
	}
}
