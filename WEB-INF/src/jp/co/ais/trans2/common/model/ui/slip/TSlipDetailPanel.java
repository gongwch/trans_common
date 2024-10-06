package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票明細コンポーネント
 */
public class TSlipDetailPanel extends TPanel {

	/**
	 * 一覧のカラム定義
	 */
	public enum SC {

		/** Entity */
		bean,

		/** 計上会社コード */
		kCompCode,

		/** 計上会社 */
		kCompName,

		/** 部門コード */
		depCode,

		/** 部門 */
		depName,

		/** 科目コード */
		itemCode,

		/** 科目 */
		itemName,

		/** 補助コード */
		subItemCode,

		/** 補助 */
		subItemName,

		/** 内訳科目コード */
		dtlItemCode,

		/** 内訳 */
		dtlItemName,

		/** 税 */
		taxDivision,

		/** 消費税コード */
		taxCode,

		/** 消費税名称 */
		taxName,

		/** 税率 */
		taxRate,

		/** 金額 */
		amount,

		/** 通貨コード */
		currency,

		/** 通貨レート */
		currencyRate,

		/** 借方金額(外貨) */
		drFAmount,

		/** 借方金額 */
		drAmount,

		/** 消費税額(外貨) */
		taxFAmount,

		/** 消費税額 */
		taxAmount,

		/** 貸方金額(外貨) */
		crFAmount,

		/** 貸方金額 */
		crAmount,

		/** 行摘要コード */
		tekCode,

		/** 行摘要 */
		tek,

		/** 取引先コード */
		customerCode,

		/** 取引先 */
		customerName,

		/** 社員コード */
		empCode,

		/** 社員 */
		empName,

		/** 管理1コード */
		mng1Code,

		/** 管理1 */
		mng1Name,

		/** 管理2コード */
		mng2Code,

		/** 管理2 */
		mng2Name,

		/** 管理3コード */
		mng3Code,

		/** 管理3 */
		mng3Name,

		/** 管理4コード */
		mng4Code,

		/** 管理4 */
		mng4Name,

		/** 管理5コード */
		mng5Code,

		/** 管理5 */
		mng5Name,

		/** 管理6コード */
		mng6Code,

		/** 管理6 */
		mng6Name,

		/** 発生日 */
		issuerDay,

		/** 非会計明細1 */
		nonAcDtl1,

		/** 非会計明細2 */
		nonAcDtl2,

		/** 非会計明細3 */
		nonAcDtl3,

		/** 入力金額 */
		inputAmount;
	}

	/** 左開始位置 */
	protected int X_POINT;

	/** 入力部パネル */
	public TPanel pnlInput;

	/** 部品コントローラ */
	public TSlipDetailPanelCtrl controller;

	/** 計上会社 */
	public TCompanyReference ctrlKCompany;

	/** 計上部門 */
	public TDepartmentReference ctrlKDepartment;

	/** 科目 */
	public TItemGroup ctrlItem;

	/** 発生日 */
	public TLabelPopupCalendar ctrlOccurDate;

	/** 通貨 */
	public TCurrencyReference ctrlCurrency;

	/** レート */
	public TLabelNumericField ctrlRate;

	/** 税区分 */
	public TTaxReference ctrlTax;

	/** 計算 */
	public TRadioPanel ctrlTaxDivision;

	/** 行摘要 */
	public TRemarkReference ctrlRemark;

	/** 貸借パネル */
	public TDrCrPanel ctrlDrCr;

	/** 入力金額 */
	public TLabelNumericField ctrlInputAmount;

	/** 入力消費税額 */
	public TLabelNumericField ctrlTaxAmount;

	/** 基軸通貨金額 */
	public TLabelNumericField ctrlKeyAmount;

	/** 取引先 */
	public TCustomerReference ctrlCustomer;

	/** 社員 */
	public TEmployeeReference ctrlEmployee;

	/** 管理1 */
	public TManagement1Reference ctrlManage1;

	/** 管理2 */
	public TManagement2Reference ctrlManage2;

	/** 管理3 */
	public TManagement3Reference ctrlManage3;

	/** 管理4 */
	public TManagement4Reference ctrlManage4;

	/** 管理5 */
	public TManagement5Reference ctrlManage5;

	/** 管理6 */
	public TManagement6Reference ctrlManage6;

	/** 非会計明細 */
	public TNonAccountintDetailUnit ctrlNonAcDetails;

	/** 新規行 */
	public TButton btnRowNew;

	/** 行挿入 */
	public TButton btnRowInsert;

	/** 行複写 */
	public TButton btnRowCopy;

	/** 行削除 */
	public TButton btnRowDelete;

	/** 行確定 */
	public TButton btnRowEntry;

	/** 明細ダウンロードボタン */
	public TButton btnDownload;

	/** 明細アップロードボタン */
	public TButton btnUpload;

	/** スプレッド最上位行移動ボタン */
	public TButton btnRowTop;

	/** スプレッド上行移動ボタン */
	public TButton btnRowUp;

	/** スプレッド下行移動ボタン */
	public TButton btnRowDown;

	/** スプレッド最下位行移動ボタン */
	public TButton btnRowUnder;

	/** スプレッド */
	public TTable tbl;

	/** 借方外貨合計 */
	public TLabelNumericField ctrlFcDrTotal;

	/** 貸方外貨合計 */
	public TLabelNumericField ctrlFcCrTotal;

	/** 差額(外貨) */
	public TLabelNumericField ctrlFcDiff;

	/** 合計欄外貨通貨 */
	public TComboBox cbxCurrencyOnTotal;

	/** 借方金額合計 */
	public TLabelNumericField ctrlDrTotal;

	/** 貸方金額合計 */
	public TLabelNumericField ctrlCrTotal;

	/** 為替差 */
	public TLabelNumericField ctrlExchangeDiff;

	/** 借方消費税額合計 */
	public TLabelNumericField ctrlDrTaxTotal;

	/** 貸方消費税額合計 */
	public TLabelNumericField ctrlCrTaxTotal;

	/** 差額 */
	public TLabelNumericField ctrlDiff;

	/** 債権残高 */
	public TButton btnAR;

	/** 債務残高 */
	public TButton btnAP;

	/** 合計パネル */
	public TPanel pnlTotalAmount;

	/** スプレッドパネル */
	public TPanel pnlSpreadSheet;

	/** スプレッド上下ボタンパネル */
	public TPanel pnlSsUDPanel;

	/** 明細ボタンパネル */
	public TPanel pnlDtlButtons;

	/** 追加ボタン用パネル */
	public TPanel pnlFreeButtons;

	/** スプレッド用ボタン */
	public TPanel pnlRowButtons;

	/** スプリットペイン表示フラグ */
	public boolean splitPaneFlg = ClientConfig.isFlagOn("trans.slip.splitpane");

	/** 発生日をYYYY/MMとする判定フラグ */
	public boolean isHasDateYM = ClientConfig.isFlagOn("trans.slip.use.has.date.ym");

	/** BS勘定 */
	public TButton btnBS;

	/** 親 */
	public TSlipPanel parent;

	/**
	 * コンストラクタ.
	 */
	public TSlipDetailPanel() {
		this(null);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親
	 */
	public TSlipDetailPanel(TSlipPanel parent) {
		this.parent = parent;

		initComponents();
		allocateSlipDetail();

		controller = createController();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return コントローラー
	 */
	protected TSlipDetailPanelCtrl createController() {
		return new TSlipDetailPanelCtrl(this);
	}

	/**
	 * コントローラ取得
	 * 
	 * @return コントローラ
	 */
	public TSlipDetailPanelCtrl getController() {
		return controller;
	}

	/**
	 * コンポーネント作成
	 */
	public void initComponents() {
		X_POINT = 5;

		ctrlKCompany = new TCompanyReference();
		ctrlKDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();
		ctrlRemark = new TRemarkReference();

		if (isHasDateYM) {
			ctrlOccurDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YM);
		} else {
			ctrlOccurDate = new TLabelPopupCalendar();
		}

		// 換算コンポに
		ctrlCurrency = new TCurrencyReference();
		ctrlRate = new TLabelNumericField();
		ctrlTax = new TTaxReference();
		ctrlTaxDivision = new TRadioPanel("C01175");

		ctrlDrCr = new TDrCrPanel();

		ctrlInputAmount = new TLabelNumericField();
		ctrlTaxAmount = new TLabelNumericField();
		ctrlKeyAmount = new TLabelNumericField();

		ctrlCustomer = new TCustomerReference();
		ctrlEmployee = new TEmployeeReference();
		ctrlManage1 = new TManagement1Reference();
		ctrlManage2 = new TManagement2Reference();
		ctrlManage3 = new TManagement3Reference();
		ctrlManage4 = new TManagement4Reference();
		ctrlManage5 = new TManagement5Reference();
		ctrlManage6 = new TManagement6Reference();

		ctrlNonAcDetails = new TNonAccountintDetailUnit();

		pnlDtlButtons = new TPanel();
		pnlFreeButtons = new TPanel();
		pnlRowButtons = new TPanel();

		btnAR = new TButton();
		btnAP = new TButton();

		btnRowNew = new TButton();
		btnRowInsert = new TButton();
		btnRowCopy = new TButton();
		btnRowDelete = new TButton();
		btnRowEntry = new TButton();

		btnDownload = new TButton();
		btnUpload = new TButton();

		btnRowTop = new TButton();
		btnRowUp = new TButton();
		btnRowDown = new TButton();
		btnRowUnder = new TButton();
		tbl = new TTable();
		pnlSpreadSheet = new TPanel();

		pnlSsUDPanel = new TPanel();
		pnlTotalAmount = new TPanel();

		// 外貨
		ctrlFcDrTotal = new TLabelNumericField();
		ctrlFcCrTotal = new TLabelNumericField();
		ctrlFcDiff = new TLabelNumericField();
		cbxCurrencyOnTotal = new TComboBox();

		// 邦貨
		ctrlDrTotal = new TLabelNumericField();
		ctrlExchangeDiff = new TLabelNumericField();
		ctrlCrTotal = new TLabelNumericField();

		// 消費税
		ctrlDrTaxTotal = new TLabelNumericField();
		ctrlCrTaxTotal = new TLabelNumericField();
		ctrlDiff = new TLabelNumericField();

		// BS勘定
		btnBS = new TButton();

	}

	/**
	 * 明細の配置
	 */
	public void allocateSlipDetail() {

		TPanel pnlCenter = new TPanel(new GridBagLayout());

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			this.setLayout(new BorderLayout());

		} else {
			this.setLayout(new GridBagLayout());
		}

		pnlInput = new TPanel();
		pnlInput.setLayout(null);

		TGuiUtil.setComponentSize(pnlInput, new Dimension(0, 250));

		// 計上会社
		ctrlKCompany.btn.setLangMessageID("C01052");
		ctrlKCompany.setLocation(X_POINT, 5);
		pnlInput.add(ctrlKCompany);

		// 計上部門
		ctrlKDepartment.btn.setLangMessageID("C00863");
		ctrlKDepartment.setLocation(X_POINT, ctrlKCompany.getY() + ctrlKCompany.getHeight());
		pnlInput.add(ctrlKDepartment);

		// 科目
		ctrlItem.setLocation(X_POINT, ctrlKDepartment.getY() + ctrlKDepartment.getHeight());
		pnlInput.add(ctrlItem);

		// 発生日
		int HAS_X = ctrlKCompany.getX() + ctrlKCompany.getWidth() + 1;
		ctrlOccurDate.setLabelSize(75);
		ctrlOccurDate.setLangMessageID("C00431");
		ctrlOccurDate.setLocation(HAS_X, ctrlKCompany.getY());
		pnlInput.add(ctrlOccurDate);

		// 通貨
		ctrlCurrency.name.setVisible(false);
		ctrlCurrency.resize();
		ctrlCurrency.setLocation(HAS_X, ctrlOccurDate.getY() + ctrlOccurDate.getHeight());
		pnlInput.add(ctrlCurrency);

		// レート
		ctrlRate.setLabelSize(50);
		ctrlRate.setLangMessageID("C00556");
		ctrlRate.setMaxLength(13, 4);
		ctrlRate.setPositiveOnly(true);
		ctrlRate.setLocation(ctrlCurrency.getX() + ctrlCurrency.getWidth() + 10, ctrlCurrency.getY());
		pnlInput.add(ctrlRate);

		// 税区分
		ctrlTax.setLocation(HAS_X, ctrlCurrency.getY() + ctrlCurrency.getHeight());
		pnlInput.add(ctrlTax);

		// 消費税計算
		TGuiUtil.setComponentSize(ctrlTaxDivision, new Dimension(0, 40));
		ctrlTaxDivision.addRadioButton("C00023", 70);
		ctrlTaxDivision.addRadioButton("C00337", 70);
		ctrlTaxDivision.setLocation(HAS_X, ctrlTax.getY() + ctrlTax.getHeight());
		pnlInput.add(ctrlTaxDivision);

		// 行摘要
		ctrlRemark.setLocation(X_POINT, ctrlTaxDivision.getY() + ctrlTaxDivision.getHeight());
		pnlInput.add(ctrlRemark);

		// 貸借パネル
		ctrlDrCr.setLocation(X_POINT, ctrlRemark.getY() + ctrlRemark.getHeight() + 1);
		pnlInput.add(ctrlDrCr);

		// 入力金額
		ctrlInputAmount.setLabelSize(60);
		ctrlInputAmount.setLangMessageID("C00574");
		ctrlInputAmount.setMaxLength(13, 4);
		ctrlInputAmount.setChangeRedOfMinus(true);
		ctrlInputAmount.setLocation(ctrlDrCr.getX() + ctrlDrCr.getWidth() + 2, ctrlDrCr.getY() + 15);
		pnlInput.add(ctrlInputAmount);

		// 入力消費税額
		ctrlTaxAmount.setLabelSize(85);
		ctrlTaxAmount.setLangMessageID("C00575");
		ctrlTaxAmount.setMaxLength(13, 4);
		ctrlTaxAmount.setChangeRedOfMinus(true);
		ctrlTaxAmount.setLocation(ctrlInputAmount.getX() + ctrlInputAmount.getWidth() + 3, ctrlInputAmount.getY());
		pnlInput.add(ctrlTaxAmount);

		// 基軸通貨金額
		ctrlKeyAmount.setLabelSize(80);
		ctrlKeyAmount.setLangMessageID("C00576");
		ctrlKeyAmount.setMaxLength(13, 4);
		ctrlKeyAmount.setChangeRedOfMinus(true);
		ctrlKeyAmount.setLocation(ctrlTaxAmount.getX() + ctrlTaxAmount.getWidth() + 1, ctrlInputAmount.getY());
		pnlInput.add(ctrlKeyAmount);

		// 取引先
		ctrlCustomer.setLocation(X_POINT, ctrlDrCr.getY() + ctrlDrCr.getHeight() + 1);
		pnlInput.add(ctrlCustomer);

		// 社員
		ctrlEmployee.setLocation(X_POINT, ctrlCustomer.getY() + ctrlCustomer.getHeight());
		pnlInput.add(ctrlEmployee);

		// 管理1
		ctrlManage1.setLocation(X_POINT, ctrlEmployee.getY() + ctrlEmployee.getHeight());
		pnlInput.add(ctrlManage1);

		// 管理2
		ctrlManage2.setLocation(X_POINT, ctrlManage1.getY() + ctrlManage1.getHeight());
		pnlInput.add(ctrlManage2);

		// 管理3
		ctrlManage3.setLocation(ctrlCustomer.getX() + ctrlCustomer.getWidth() + 1, ctrlCustomer.getY());
		pnlInput.add(ctrlManage3);

		// 管理4
		ctrlManage4.setLocation(ctrlEmployee.getX() + ctrlEmployee.getWidth() + 1, ctrlEmployee.getY());
		pnlInput.add(ctrlManage4);

		// 管理5
		ctrlManage5.setLocation(ctrlManage1.getX() + ctrlManage1.getWidth() + 1, ctrlManage1.getY());
		pnlInput.add(ctrlManage5);

		// 管理6
		ctrlManage6.setLocation(ctrlManage2.getX() + ctrlManage2.getWidth() + 1, ctrlManage2.getY());
		pnlInput.add(ctrlManage6);

		// 非会計明細
		ctrlNonAcDetails.setLocation(ctrlManage3.getX() + ctrlManage3.getWidth() + 1, ctrlCustomer.getY());
		pnlInput.add(ctrlNonAcDetails);

		GridBagConstraints gc_ = new GridBagConstraints();
		gc_.weightx = 1.0d;

		gc_.gridx = 0;
		gc_.gridy = 0;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.HORIZONTAL;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			pnlCenter.add(pnlInput, gc_);
		} else {
			this.add(pnlInput, gc_);
		}

		// 明細ボタンパネル
		pnlDtlButtons.setLayout(new BoxLayout(pnlDtlButtons, BoxLayout.X_AXIS));
		TGuiUtil.setComponentSize(pnlDtlButtons, new Dimension(0, 22));

		// 追加ボタン用パネル
		pnlFreeButtons.setLayout(new BoxLayout(pnlFreeButtons, BoxLayout.X_AXIS));
		TGuiUtil.setComponentSize(pnlFreeButtons, new Dimension(300, 30));

		pnlDtlButtons.add(pnlFreeButtons);

		// 債権残高
		btnAR.setLangMessageID("C01080");
		TGuiUtil.setComponentSize(btnAR, new Dimension(100, 20));
		pnlFreeButtons.add(btnAR);

		// 債務残高
		btnAP.setLangMessageID("C01084");
		TGuiUtil.setComponentSize(btnAP, new Dimension(100, 20));
		pnlFreeButtons.add(btnAP);

		// BS勘定
		btnBS.setLangMessageID("C04291");
		TGuiUtil.setComponentSize(btnBS, new Dimension(100, 20));
		pnlFreeButtons.add(btnBS);

		// スプレッド用ボタン
		pnlRowButtons.setLayout(new BoxLayout(pnlRowButtons, BoxLayout.X_AXIS));
		TGuiUtil.setComponentSize(pnlRowButtons, new Dimension(860, 30));

		pnlDtlButtons.add(pnlRowButtons);

		// 新規行
		btnRowNew.setLangMessageID("C01744");
		btnRowNew.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F2);
		TGuiUtil.setComponentSize(btnRowNew, new Dimension(120, 20));
		pnlRowButtons.add(btnRowNew);

		// 行挿入
		btnRowInsert.setLangMessageID("C11604");
		btnRowInsert.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F3);
		TGuiUtil.setComponentSize(btnRowInsert, new Dimension(120, 20));
		pnlRowButtons.add(btnRowInsert);

		// 行複写
		btnRowCopy.setLangMessageID("C01745");
		btnRowCopy.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F4);
		TGuiUtil.setComponentSize(btnRowCopy, new Dimension(120, 20));
		pnlRowButtons.add(btnRowCopy);

		// 行削除
		btnRowDelete.setLangMessageID("C01071");
		btnRowDelete.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F5);
		TGuiUtil.setComponentSize(btnRowDelete, new Dimension(120, 20));
		pnlRowButtons.add(btnRowDelete);

		// 行確定
		btnRowEntry.setLangMessageID("C01746");
		btnRowEntry.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F6);
		btnRowEntry.setEnterFocusable(true);
		TGuiUtil.setComponentSize(btnRowEntry, new Dimension(120, 20));
		pnlRowButtons.add(btnRowEntry);

		// ダウンロードボタン
		btnDownload.setLangMessageID("C11898");
		btnDownload.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F7);
		TGuiUtil.setComponentSize(btnDownload, new Dimension(120, 20));
		pnlRowButtons.add(btnDownload);

		// アップロードボタン
		btnUpload.setLangMessageID("C11899");
		btnUpload.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F8);
		TGuiUtil.setComponentSize(btnUpload, new Dimension(120, 20));
		pnlRowButtons.add(btnUpload);

		gc_.gridx = 0;
		gc_.gridy = 1;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.HORIZONTAL;
		gc_.insets = new Insets(0, X_POINT, 0, 0);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			pnlCenter.add(pnlDtlButtons, gc_);
		} else {
			this.add(pnlDtlButtons, gc_);
		}

		// スプレッドパネル
		pnlSpreadSheet.setLayout(new BorderLayout());

		// スプレッド上下ボタンパネル
		pnlSsUDPanel.setLayout(new GridBagLayout());

		TGuiUtil.setComponentSize(pnlSsUDPanel, new Dimension(30, 0));

		// ボタン
		TGuiUtil.setComponentSize(btnRowTop, new Dimension(20, 20));
		TGuiUtil.setComponentSize(btnRowUp, new Dimension(20, 20));
		TGuiUtil.setComponentSize(btnRowDown, new Dimension(20, 20));
		TGuiUtil.setComponentSize(btnRowUnder, new Dimension(20, 20));

		btnRowTop.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowTop.png"));
		btnRowUp.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUp.png"));
		btnRowDown.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowDown.png"));
		btnRowUnder.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUnder.png"));

		GridBagConstraints gcb = new GridBagConstraints();
		gcb.insets = new Insets(0, 2, 0, 0);

		gcb.gridy = 0;
		pnlSsUDPanel.add(btnRowTop, gcb);
		gcb.gridy = 1;
		pnlSsUDPanel.add(btnRowUp, gcb);
		gcb.gridy = 2;
		pnlSsUDPanel.add(btnRowDown, gcb);
		gcb.gridy = 3;
		pnlSsUDPanel.add(btnRowUnder, gcb);

		pnlSpreadSheet.add(pnlSsUDPanel, BorderLayout.WEST);

		tbl.setSortOff();
		TGuiUtil.setComponentSize(tbl, new Dimension(0, 0));

		pnlSpreadSheet.add(tbl, BorderLayout.CENTER);

		gc_.gridx = 0;
		gc_.gridy = 2;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.insets = new Insets(0, X_POINT, 0, 0);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			pnlCenter.add(pnlSpreadSheet, gc_);
		} else {
			this.add(pnlSpreadSheet, gc_);
		}

		// 合計金額パネル
		pnlTotalAmount.setLayout(null);
		TGuiUtil.setComponentSize(pnlTotalAmount, new Dimension(0, 80));

		// 借方外貨合計
		ctrlFcDrTotal.setLangMessageID("C10725");
		ctrlFcDrTotal.setLabelSize(100);
		ctrlFcDrTotal.setNumericFormat("#,##0");
		ctrlFcDrTotal.setEditable(false);
		ctrlFcDrTotal.setChangeRedOfMinus(true);
		ctrlFcDrTotal.setLocation(30, 5);
		pnlTotalAmount.add(ctrlFcDrTotal);

		// 外貨差額
		ctrlFcDiff.setLangMessageID("C10727");
		ctrlFcDiff.setLabelSize(50);
		ctrlFcDiff.setNumericFormat("#,##0");
		ctrlFcDiff.setEditable(false);
		ctrlFcDiff.setChangeRedOfMinus(true);
		ctrlFcDiff.setLocation(ctrlFcDrTotal.getX() + ctrlFcDrTotal.getWidth() + 10, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(ctrlFcDiff);

		// 外貨通貨
		cbxCurrencyOnTotal.setSize(new Dimension(50, 20));
		cbxCurrencyOnTotal.setLocation(ctrlFcDiff.getX() + ctrlFcDiff.getWidth() + 1, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(cbxCurrencyOnTotal);

		// 貸方外貨合計
		ctrlFcCrTotal.setLangMessageID("C10726");
		ctrlFcCrTotal.setLabelSize(100);
		ctrlFcCrTotal.setNumericFormat("#,##0");
		ctrlFcCrTotal.setEditable(false);
		ctrlFcCrTotal.setChangeRedOfMinus(true);
		ctrlFcCrTotal.setLocation(ctrlFcDiff.getX() + ctrlFcDiff.getWidth() + 40, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(ctrlFcCrTotal);

		// 借方金額合計
		ctrlDrTotal.setLangMessageID("C01126");
		ctrlDrTotal.setLabelSize(100);
		ctrlDrTotal.setNumericFormat("#,##0");
		ctrlDrTotal.setEditable(false);
		ctrlDrTotal.setChangeRedOfMinus(true);
		ctrlDrTotal.setLocation(ctrlFcDrTotal.getX(), ctrlFcDrTotal.getY() + ctrlFcDrTotal.getHeight() + 1);
		pnlTotalAmount.add(ctrlDrTotal);

		// 為替差
		ctrlExchangeDiff.setLangMessageID("C10728");
		ctrlExchangeDiff.setLabelSize(50);
		ctrlExchangeDiff.setNumericFormat("#,##0");
		ctrlExchangeDiff.setEditable(false);
		ctrlExchangeDiff.setChangeRedOfMinus(true);
		ctrlExchangeDiff.setLocation(ctrlDrTotal.getX() + ctrlDrTotal.getWidth() + 10, ctrlDrTotal.getY());
		pnlTotalAmount.add(ctrlExchangeDiff);

		// 貸方金額合計
		ctrlCrTotal.setLangMessageID("C01229");
		ctrlCrTotal.setLabelSize(100);
		ctrlCrTotal.setNumericFormat("#,##0");
		ctrlCrTotal.setEditable(false);
		ctrlCrTotal.setChangeRedOfMinus(true);
		ctrlCrTotal.setLocation(ctrlExchangeDiff.getX() + ctrlExchangeDiff.getWidth() + 40, ctrlDrTotal.getY());
		pnlTotalAmount.add(ctrlCrTotal);

		// 借方消費税額合計
		ctrlDrTaxTotal.setLangMessageID("C01174");
		ctrlDrTaxTotal.setLabelSize(100);
		ctrlDrTaxTotal.setNumericFormat("#,##0");
		ctrlDrTaxTotal.setEditable(false);
		ctrlDrTaxTotal.setChangeRedOfMinus(true);
		ctrlDrTaxTotal.setLocation(ctrlDrTotal.getX(), ctrlDrTotal.getY() + ctrlDrTotal.getHeight() + 1);
		pnlTotalAmount.add(ctrlDrTaxTotal);

		// 差額
		ctrlDiff.setLangMessageID("C00191");
		ctrlDiff.setLabelSize(50);
		ctrlDiff.setNumericFormat("#,##0");
		ctrlDiff.setEditable(false);
		ctrlDiff.setChangeRedOfMinus(true);
		ctrlDiff.setLocation(ctrlDrTaxTotal.getX() + ctrlDrTaxTotal.getWidth() + 10, ctrlDrTaxTotal.getY());
		pnlTotalAmount.add(ctrlDiff);

		// 貸方消費税額合計
		ctrlCrTaxTotal.setLangMessageID("C01174");
		ctrlCrTaxTotal.setLabelSize(100);
		ctrlCrTaxTotal.setNumericFormat("#,##0");
		ctrlCrTaxTotal.setEditable(false);
		ctrlCrTaxTotal.setChangeRedOfMinus(true);
		ctrlCrTaxTotal.setLocation(ctrlDiff.getX() + ctrlDiff.getWidth() + 40, ctrlDrTaxTotal.getY());
		pnlTotalAmount.add(ctrlCrTaxTotal);

		// ユーザー設定により、スプリットペインを表示
		if (splitPaneFlg) {
			// 境界線の制御
			JSplitPane spBody = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, pnlCenter, pnlTotalAmount);
			spBody.setOpaque(false);
			pnlTotalAmount.setMinimumSize(new Dimension(0, 0));
			pnlCenter.setMinimumSize(new Dimension(0, 0));
			pnlTotalAmount.setMaximumSize(new Dimension(0, 80));
			spBody.setResizeWeight(1);

			// フッダを隠す為の設定
			TUIManager.switchMaximumMode(spBody);

			this.add(spBody, BorderLayout.CENTER);
		} else {

			gc_.gridx = 0;
			gc_.gridy = 3;
			gc_.weighty = 0d;
			gc_.fill = GridBagConstraints.HORIZONTAL;
			gc_.insets = new Insets(0, 0, 0, 0);
			this.add(pnlTotalAmount, gc_);
		}

	}

	/**
	 * 継承先でヘッダの番号をセットした後に呼ぶ
	 * 
	 * @param i 番号
	 * @return 最終番号
	 */
	public int setTabIndex(int i) {
		ctrlKCompany.setTabControlNo(i++);
		ctrlKDepartment.setTabControlNo(i++);
		ctrlItem.setTabControlNo(i++);
		ctrlOccurDate.setTabControlNo(i++);
		ctrlCurrency.setTabControlNo(i++);
		ctrlRate.setTabControlNo(i++);
		ctrlTax.setTabControlNo(i++);
		ctrlTaxDivision.setTabControlNo(i++);
		ctrlRemark.setTabControlNo(i++);
		ctrlDrCr.setTabControlNo(i++);
		ctrlInputAmount.setTabControlNo(i++);
		ctrlTaxAmount.setTabControlNo(i++);
		ctrlKeyAmount.setTabControlNo(i++);
		ctrlCustomer.setTabControlNo(i++);
		ctrlEmployee.setTabControlNo(i++);
		ctrlManage1.setTabControlNo(i++);
		ctrlManage2.setTabControlNo(i++);
		ctrlManage3.setTabControlNo(i++);
		ctrlManage4.setTabControlNo(i++);
		ctrlManage5.setTabControlNo(i++);
		ctrlManage6.setTabControlNo(i++);
		ctrlNonAcDetails.setTabControlNo(i++);

		btnAR.setTabControlNo(i++);
		btnAP.setTabControlNo(i++);
		btnBS.setTabControlNo(i++);

		btnRowNew.setTabControlNo(i++);
		btnRowInsert.setTabControlNo(i++);
		btnRowCopy.setTabControlNo(i++);
		btnRowDelete.setTabControlNo(i++);
		btnRowEntry.setTabControlNo(i++);

		btnDownload.setTabControlNo(i++);
		btnUpload.setTabControlNo(i++);

		btnRowTop.setTabControlNo(i++);
		btnRowUp.setTabControlNo(i++);
		btnRowDown.setTabControlNo(i++);
		btnRowUnder.setTabControlNo(i++);
		tbl.setTabControlNo(i++);

		cbxCurrencyOnTotal.setTabControlNo(i++);

		return i;
	}

	/**
	 * 初期化
	 */
	public void init() {
		controller.init();
	}

	/**
	 * 伝票伝票番号(編集の場合)
	 * 
	 * @param slipNo 伝票伝票番号(編集の場合)
	 */
	public void setSlipNo(String slipNo) {
		controller.setSlipNo(slipNo);
	}

	/**
	 * 明細をEntity形式でセットする.
	 * 
	 * @param details データ
	 */
	public void setEntityList(List<SWK_DTL> details) {
		controller.setEntityList(details);
	}

	/**
	 * 明細をEntity形式で取得する
	 * 
	 * @return SWK_DTLリスト
	 */
	public List<SWK_DTL> getEntityList() {
		return controller.getEntityList();
	}

	/**
	 * 明細をEntity形式で取得する
	 * 
	 * @return SWK_DTLリスト
	 */
	public int getDetailRowCount() {
		return tbl.getRowCount();
	}

	/**
	 * スプレッドテーブル状態保存キー
	 * 
	 * @param tableKeyName スプレッドテーブル状態保存キー
	 */
	public void setTableKeyName(String tableKeyName) {
		tbl.setTableKeyName(tableKeyName);
	}

	/**
	 * 基準日設定
	 * 
	 * @param baseDate 基準日
	 */
	public void setBaseDate(Date baseDate) {
		controller.setBaseDate(baseDate);
	}

	/**
	 * 決算仕訳設定
	 * 
	 * @param isClosing true:決算仕訳、false:通常仕訳
	 */
	public void setClosingEntry(boolean isClosing) {
		controller.setClosingEntry(isClosing);
	}

	/**
	 * 外部指定明細(金額合計で利用)
	 * 
	 * @param outherDetail 外部指定明細
	 */
	public void setOutherDetail(SWK_DTL outherDetail) {
		controller.setOuterDetail(outherDetail);
	}

	/**
	 * 外部指定取引先を設定(固定表示用)
	 * 
	 * @param customer 外部指定取引先
	 */
	public void setCustomer(Customer customer) {
		controller.setCustomer(customer);
	}

	/**
	 * 外部指定社員を設定(初期表示用)
	 * 
	 * @param employee 社員
	 */
	public void setEmployee(Employee employee) {
		controller.setEmployee(employee);
	}

	/**
	 * 回収取引先を設定(債権残高一覧用)
	 * 
	 * @param customer 回収取引先
	 */
	public void setCollectionCustomer(Customer customer) {
		controller.setCollectionCustomer(customer);
	}

	/**
	 * デフォルトが内税か
	 * 
	 * @param defaultTaxInnerDivision true:内税
	 */
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		controller.setDefaultTaxInnerDivision(defaultTaxInnerDivision);
	}

	/**
	 * 貸借のデフォルト値
	 * 
	 * @param dc 貸借
	 */
	public void setDefaultDC(Dc dc) {
		controller.setDefaultDC(dc);
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	public boolean checkInput() {
		return controller.checkInput();
	}

	/**
	 * 合計欄の通貨コンボボックス構築
	 */
	public void makeCurrencyComboBox() {
		controller.makeCurrencyComboBox();
	}

	/**
	 * 合計値再計算
	 */
	public void summary() {
		controller.summary();
	}

	/**
	 * 通貨設定
	 * 
	 * @param currency 通貨
	 */
	public void setCurrecy(Currency currency) {
		controller.setCurrecy(currency);

		if (tbl.getSelectedRow() != -1) {
			// 行編集済
			controller.isEditedDetail = true;
		}

		// レート
		ctrlRate.setNumberValue(controller.getCurrencyRate());

		// 発生日
		Date occurDate = ctrlOccurDate.getValue();
		if (controller.isUseHasDateChk) {
			ctrlRate.setNumberValue(controller.getCurrencyRateByOccurDate(occurDate));
		}

		controller.changedRate();
	}
}
