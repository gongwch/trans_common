package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 仕訳明細入力パネル
 * 
 * @author AIS
 */
public class TFormDCInputPanel extends TPanel {

	/** 0:借方 */
	public static final int DR = TFormDCInputPanelCtrl.DR;

	/** 1:貸方 */
	public static final int CR = TFormDCInputPanelCtrl.CR;

	/** 左開始位置 */
	public final int X_POINT = 5;

	/** 上開始位置 */
	public final int Y_POINT = 2;

	/** 余白_横 */
	public final int MARGIN_X = 2;

	/** 行１色 */
	public static Color row1Color = TTable.notSelectedColor;

	/** 行２色 */
	public static Color row2Color = TTable.notSelectedColor2;

	/** 行番号のパネル */
	public TPanel pnlRowNo;

	/** 借方科目、金額のパネル */
	public TPanel pnlDebit;

	/** 管理項目のパネル */
	public TPanel pnlCenter;

	/** 貸方科目、金額のパネル */
	public TPanel pnlCredit;

	/** ボタンのパネル */
	public TPanel pnlButton;

	/** 行番号のタイトル */
	public TLabel lblRowNo;

	/** 借方科目のタイトル */
	public TLabel lblDebitItemTitle;

	/** 借方金額のタイトル */
	public TLabel lblDebitAmountTitle;

	/** 管理項目のタイトル */
	public TLabel lblCenterTitle;

	/** 貸方科目のタイトル */
	public TLabel lblCreditItemTitle;

	/** 貸方金額のタイトル */
	public TLabel lblCreditAmountTitle;

	/** 「配列」計上会社 */
	public TCompanyReference[] ctrlKCompany = new TCompanyReference[2];

	/** 「配列」計上部門 */
	public TDepartmentReference[] ctrlKDepartment = new TDepartmentReference[2];

	/** 「配列」科目 */
	public TItemGroup[] ctrlItem = new TItemGroup[2];

	/** 「配列」消費税 */
	public TTaxReference[] ctrlTax = new TTaxReference[2];

	/** 「配列」税区分 */
	public TComboBox[] ctrlTaxDivision = new TComboBox[2];

	/** 「配列」通貨リスト */
	public TComboBox[] ctrlCurrency = new TComboBox[2];

	/** 「配列」入力金額 */
	public TNumericField[] ctrlInputAmount = new TNumericField[2];

	/** 「配列」レート */
	public TLabelNumericField[] ctrlRate = new TLabelNumericField[2];

	/** 「配列」邦貨金額 */
	public TNumericField[] ctrlKeyAmount = new TNumericField[2];

	/** 「配列」消費税額 */
	public TNumericField[] ctrlTaxAmount = new TNumericField[2];

	/** 借方計上会社 */
	public TCompanyReference ctrlKCompanyDebit;

	/** 借方計上部門 */
	public TDepartmentReference ctrlKDepartmentDebit;

	/** 借方科目 */
	public TItemGroup ctrlItemDebit;

	/** 借方消費税 */
	public TTaxReference ctrlTaxDebit;

	/** 借方税区分 */
	public TComboBox ctrlTaxDivisionDebit;

	/** 借方通貨リスト */
	public TComboBox ctrlCurrencyDebit;

	/** 借方入力金額 */
	public TNumericField ctrlInputAmountDebit;

	/** 借方レート */
	public TLabelNumericField ctrlRateDebit;

	/** 借方邦貨金額 */
	public TNumericField ctrlKeyAmountDebit;

	/** 借方消費税額 */
	public TNumericField ctrlTaxAmountDebit;

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

	/** 発生日 */
	public TLabelPopupCalendar ctrlOccurDate;

	/** 行摘要 */
	public TRemarkReference ctrlRemark;

	/** 非会計明細 */
	public TNonAccountintDetailUnit ctrlNonAcDetails;

	/** 貸方計上会社 */
	public TCompanyReference ctrlKCompanyCredit;

	/** 貸方計上部門 */
	public TDepartmentReference ctrlKDepartmentCredit;

	/** 貸方科目 */
	public TItemGroup ctrlItemCredit;

	/** 貸方消費税 */
	public TTaxReference ctrlTaxCredit;

	/** 貸方通貨リスト */
	public TComboBox ctrlCurrencyCredit;

	/** 貸方入力金額 */
	public TNumericField ctrlInputAmountCredit;

	/** 貸方レート */
	public TLabelNumericField ctrlRateCredit;

	/** 貸方邦貨金額 */
	public TNumericField ctrlKeyAmountCredit;

	/** 貸方消費税額 */
	public TNumericField ctrlTaxAmountCredit;

	/** 貸方税区分 */
	public TComboBox ctrlTaxDivisionCredit;

	/** 行移動指定 */
	public TCheckBox ctrlMoveCheck;

	/** タイトル */
	public TLabel lblTitle;

	/** 行複写 */
	public TButton btnRowCopy;

	/** 反転複写 */
	public TButton btnRowCopyReverse;

	/** 行追加 */
	public TButton btnRowNew;

	/** 行削除 */
	public TButton btnRowDelete;

	/** コントローラ */
	public TFormDCInputPanelCtrl controller;

	/**
	 * コンストラクタ.
	 */
	protected TFormDCInputPanel() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		createController();

	}

	/**
	 * コントローラ作成
	 */
	protected void createController() {
		controller = new TFormDCInputPanelCtrl(this);
	}

	/**
	 * コンポーネントを初期化する
	 */
	protected void initComponents() {

		pnlRowNo = new TPanel();
		pnlDebit = new TPanel();
		pnlCenter = new TPanel();
		pnlCredit = new TPanel();
		lblRowNo = new TLabel();
		lblDebitItemTitle = new TLabel();
		lblDebitAmountTitle = new TLabel();
		lblCenterTitle = new TLabel();
		lblCreditItemTitle = new TLabel();
		lblCreditAmountTitle = new TLabel();

		ctrlKCompany[DR] = new TCompanyReference();
		ctrlKDepartment[DR] = new TDepartmentReference();
		ctrlItem[DR] = new TItemGroup();
		ctrlTax[DR] = new TTaxReference();
		ctrlTaxDivision[DR] = new TComboBox();
		ctrlCurrency[DR] = new TComboBox();
		ctrlInputAmount[DR] = new TNumericField();
		ctrlRate[DR] = new TLabelNumericField();
		ctrlKeyAmount[DR] = new TNumericField();
		ctrlTaxAmount[DR] = new TNumericField();

		ctrlKCompany[CR] = new TCompanyReference();
		ctrlKDepartment[CR] = new TDepartmentReference();
		ctrlItem[CR] = new TItemGroup();
		ctrlTax[CR] = new TTaxReference();
		ctrlTaxDivision[CR] = new TComboBox();
		ctrlCurrency[CR] = new TComboBox();
		ctrlInputAmount[CR] = new TNumericField();
		ctrlRate[CR] = new TLabelNumericField();
		ctrlKeyAmount[CR] = new TNumericField();
		ctrlTaxAmount[CR] = new TNumericField();

		ctrlKCompanyDebit = ctrlKCompany[DR];
		ctrlKDepartmentDebit = ctrlKDepartment[DR];
		ctrlItemDebit = ctrlItem[DR];
		ctrlTaxDebit = ctrlTax[DR];
		ctrlTaxDivisionDebit = ctrlTaxDivision[DR];
		ctrlCurrencyDebit = ctrlCurrency[DR];
		ctrlInputAmountDebit = ctrlInputAmount[DR];
		ctrlRateDebit = ctrlRate[DR];
		ctrlKeyAmountDebit = ctrlKeyAmount[DR];
		ctrlTaxAmountDebit = ctrlTaxAmount[DR];

		ctrlCustomer = new TCustomerReference();
		ctrlEmployee = new TEmployeeReference();
		ctrlManage1 = new TManagement1Reference();
		ctrlManage2 = new TManagement2Reference();
		ctrlManage3 = new TManagement3Reference();
		ctrlManage4 = new TManagement4Reference();
		ctrlManage5 = new TManagement5Reference();
		ctrlManage6 = new TManagement6Reference();

		ctrlOccurDate = new TLabelPopupCalendar();
		ctrlRemark = new TRemarkReference();
		ctrlNonAcDetails = new TNonAccountintDetailUnit();

		ctrlKCompanyCredit = ctrlKCompany[CR];
		ctrlKDepartmentCredit = ctrlKDepartment[CR];
		ctrlItemCredit = ctrlItem[CR];
		ctrlTaxCredit = ctrlTax[CR];
		ctrlTaxDivisionCredit = ctrlTaxDivision[CR];
		ctrlCurrencyCredit = ctrlCurrency[CR];
		ctrlInputAmountCredit = ctrlInputAmount[CR];
		ctrlRateCredit = ctrlRate[CR];
		ctrlKeyAmountCredit = ctrlKeyAmount[CR];
		ctrlTaxAmountCredit = ctrlTaxAmount[CR];

		pnlButton = new TPanel();
		ctrlMoveCheck = new TCheckBox();
		lblTitle = new TLabel();

		btnRowCopy = new TButton();
		btnRowCopyReverse = new TButton();
		btnRowNew = new TButton();
		btnRowDelete = new TButton();

	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setSize(1280, 140);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setLayout(null);

		Dimension codeSize = new Dimension(53, 20);
		Dimension nameSize = new Dimension(170, 20);
		Dimension itemSize = new Dimension(155, 60);
		Dimension itemCodeSize = new Dimension(53, 20);
		Dimension itemNameSize = new Dimension(102, 20);
		Dimension cmpNameSize = itemNameSize;
		Dimension buttonSize = new Dimension(20, 75);
		Dimension amountSize = new Dimension(106, 20);
		Dimension comboSize = new Dimension(60, 20);

		Map modelTax = new HashMap();
		modelTax.put(TaxCalcType.OUT, TModelUIUtil.getWord(TaxCalcType.OUT.getName())); // 0:外税
		modelTax.put(TaxCalcType.IN, TModelUIUtil.getWord(TaxCalcType.IN.getName())); // 1:内税

		// 行番号のタイトル
		lblRowNo.setSize(18, 138);
		lblRowNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRowNo.setOpaque(true);
		lblRowNo.setLocation(1, 1);
		lblRowNo.setBackground(row1Color);
		lblRowNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRowNo.setLangMessageID("");
		pnlRowNo.add(lblRowNo);

		pnlRowNo.setLocation(0, 0);
		pnlRowNo.setSize(20, 140);
		pnlRowNo.setLayout(null);
		add(pnlRowNo);

		int x = X_POINT;
		int y = Y_POINT;

		{

			x = X_POINT;
			y = Y_POINT;

			// 借方科目のタイトル
			lblDebitItemTitle.setSize(217, 20);
			lblDebitItemTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblDebitItemTitle.setOpaque(true);
			lblDebitItemTitle.setBackground(Color.LIGHT_GRAY);
			lblDebitItemTitle.setLocation(x, y);
			pnlDebit.add(lblDebitItemTitle);

			x += lblDebitItemTitle.getWidth() + MARGIN_X;

			// 借方金額のタイトル
			lblDebitAmountTitle.setSize(113 - MARGIN_X, 20);
			lblDebitAmountTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblDebitAmountTitle.setOpaque(true);
			lblDebitAmountTitle.setBackground(Color.LIGHT_GRAY);
			lblDebitAmountTitle.setLangMessageID("借方金額");
			lblDebitAmountTitle.setLocation(x, y);
			pnlDebit.add(lblDebitAmountTitle);

			x = lblDebitItemTitle.getX();
			y += lblDebitItemTitle.getHeight();

			// 借方計上会社
			ctrlKCompanyDebit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKCompanyDebit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKCompanyDebit.name, cmpNameSize);
			ctrlKCompanyDebit.resize();
			ctrlKCompanyDebit.setLocation(x, y);
			pnlDebit.add(ctrlKCompanyDebit);

			y += ctrlKCompanyDebit.getHeight();

			// 借方計上部門
			ctrlKDepartmentDebit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKDepartmentDebit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKDepartmentDebit.name, cmpNameSize);
			ctrlKDepartmentDebit.resize();
			ctrlKDepartmentDebit.setLocation(x, y);
			pnlDebit.add(ctrlKDepartmentDebit);

			y += ctrlKDepartmentDebit.getHeight();

			// 借方科目
			ctrlItemDebit.ctrlItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlItemReference.name, itemNameSize);
			ctrlItemDebit.ctrlSubItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlSubItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlSubItemReference.name, itemNameSize);
			ctrlItemDebit.ctrlDetailItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlDetailItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlDetailItemReference.name, itemNameSize);
			ctrlItemDebit.ctrlItemReference.resize();
			ctrlItemDebit.ctrlSubItemReference.resize();
			ctrlItemDebit.ctrlDetailItemReference.resize();
			TGuiUtil.setComponentSize(ctrlItemDebit, itemSize);
			ctrlItemDebit.setLocation(x, y);
			pnlDebit.add(ctrlItemDebit);

			x = ctrlKCompanyDebit.getX() + ctrlKCompanyDebit.getWidth() + MARGIN_X;
			y = ctrlKCompanyDebit.getY();

			// 通貨
			TGuiUtil.setComponentSize(ctrlCurrencyDebit, comboSize);
			ctrlCurrencyDebit.setLocation(x, y);
			pnlDebit.add(ctrlCurrencyDebit);

			x += ctrlCurrencyDebit.getWidth() + MARGIN_X;

			// 借方入力金額
			ctrlInputAmountDebit.setMaxLength(13, 4);
			ctrlInputAmountDebit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlInputAmountDebit, amountSize);
			ctrlInputAmountDebit.setLocation(x, y);
			pnlDebit.add(ctrlInputAmountDebit);

			x = ctrlCurrencyDebit.getX() - 5;
			y += ctrlCurrencyDebit.getHeight();

			// 借方レート
			ctrlRateDebit.setLabelSize(60);
			ctrlRateDebit.setLangMessageID("Ex.");
			ctrlRateDebit.setMaxLength(9, 4);
			ctrlRateDebit.setNumericFormat("#,##0.0000");
			TGuiUtil.setComponentSize(ctrlRateDebit.getField(), amountSize);
			TGuiUtil.setComponentWidth(ctrlRateDebit, 175);
			ctrlRateDebit.setPositiveOnly(true);
			ctrlRateDebit.setLocation(x, y);
			pnlDebit.add(ctrlRateDebit);

			x = ctrlInputAmountDebit.getX();
			y += ctrlRateDebit.getHeight();

			// 借方邦貨金額
			ctrlKeyAmountDebit.setMaxLength(13, 4);
			ctrlKeyAmountDebit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlKeyAmountDebit, amountSize);
			ctrlKeyAmountDebit.setLocation(x, y);
			pnlDebit.add(ctrlKeyAmountDebit);

			x = ctrlInputAmountDebit.getX() - 30;
			y += ctrlKeyAmountDebit.getHeight();

			// 借方消費税
			ctrlTaxDebit.btn.setVisible(false);
			TGuiUtil.setComponentWidth(ctrlTaxDebit.code, 30);
			TGuiUtil.setComponentSize(ctrlTaxDebit.name, amountSize);
			ctrlTaxDebit.resize();
			ctrlTaxDebit.setLocation(x, y);
			pnlDebit.add(ctrlTaxDebit);

			x = ctrlCurrencyDebit.getX();
			y += ctrlTaxDebit.getHeight();

			// 借方税区分
			ctrlTaxDivisionDebit.setModel(modelTax);
			TGuiUtil.setComponentSize(this.ctrlTaxDivisionDebit, comboSize);
			ctrlTaxDivisionDebit.setLocation(x, y);
			pnlDebit.add(ctrlTaxDivisionDebit);

			x += ctrlTaxDivisionDebit.getWidth() + MARGIN_X;

			// 借方消費税額
			ctrlTaxAmountDebit.setMaxLength(13, 4);
			ctrlTaxAmountDebit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlTaxAmountDebit, amountSize);
			ctrlTaxAmountDebit.setLocation(x, y);
			pnlDebit.add(ctrlTaxAmountDebit);

			y += ctrlTaxAmountDebit.getHeight();

			// サイズ調整
			pnlDebit.setSize(ctrlInputAmountDebit.getX() + ctrlInputAmountDebit.getWidth(), y);
		}

		pnlDebit.setLocation(pnlRowNo.getX() + pnlRowNo.getWidth(), Y_POINT);
		pnlDebit.setLayout(null);
		add(pnlDebit);

		{
			// 中央パネル中身

			x = X_POINT;
			y = Y_POINT;

			// 管理項目のタイトル
			lblCenterTitle.setSize(304, 20);
			lblCenterTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblCenterTitle.setOpaque(true);
			lblCenterTitle.setLocation(x, y);
			lblCenterTitle.setBackground(Color.LIGHT_GRAY);
			lblCenterTitle.setLangMessageID("管理項目");
			pnlCenter.add(lblCenterTitle);

			y += lblCenterTitle.getHeight();

			// 取引先
			TGuiUtil.setComponentSize(ctrlCustomer.code, codeSize);
			TGuiUtil.setComponentSize(ctrlCustomer.name, nameSize);
			ctrlCustomer.resize();
			ctrlCustomer.setLocation(x, y);
			pnlCenter.add(ctrlCustomer);

			y += ctrlCustomer.getHeight();

			// 社員
			TGuiUtil.setComponentSize(ctrlEmployee.code, codeSize);
			TGuiUtil.setComponentSize(ctrlEmployee.name, nameSize);
			ctrlEmployee.resize();
			ctrlEmployee.setLocation(x, y);
			pnlCenter.add(ctrlEmployee);

			y += ctrlEmployee.getHeight();

			Company company = TLoginInfo.getCompany();
			if (company != null) {

				AccountConfig conf = company.getAccountConfig();

				if (conf.isUseManagement1()) {
					// 管理1
					TGuiUtil.setComponentSize(ctrlManage1.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage1.name, nameSize);
					ctrlManage1.resize();
					ctrlManage1.setLocation(x, y);
					pnlCenter.add(ctrlManage1);

					y += ctrlManage1.getHeight();
				}

				if (conf.isUseManagement2()) {
					// 管理2
					TGuiUtil.setComponentSize(ctrlManage2.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage2.name, nameSize);
					ctrlManage2.resize();
					ctrlManage2.setLocation(x, y);
					pnlCenter.add(ctrlManage2);

					y += ctrlManage2.getHeight();
				}

				if (conf.isUseManagement3()) {
					// 管理3
					TGuiUtil.setComponentSize(ctrlManage3.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage3.name, nameSize);
					ctrlManage3.resize();
					ctrlManage3.setLocation(x, y);
					pnlCenter.add(ctrlManage3);

					y += ctrlManage3.getHeight();
				}

				if (conf.isUseManagement4()) {
					// 管理4
					TGuiUtil.setComponentSize(ctrlManage4.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage4.name, nameSize);
					ctrlManage4.resize();
					ctrlManage4.setLocation(x, y);
					pnlCenter.add(ctrlManage4);

					y += ctrlManage4.getHeight();
				}

				if (conf.isUseManagement5()) {
					// 管理5
					TGuiUtil.setComponentSize(ctrlManage5.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage5.name, nameSize);
					ctrlManage5.resize();
					ctrlManage5.setLocation(x, y);
					pnlCenter.add(ctrlManage5);

					y += ctrlManage5.getHeight();
				}

				if (conf.isUseManagement6()) {
					// 管理6
					TGuiUtil.setComponentSize(ctrlManage6.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage6.name, nameSize);
					ctrlManage6.resize();
					ctrlManage6.setLocation(x, y);
					pnlCenter.add(ctrlManage6);

					y += ctrlManage6.getHeight();
				}
			}

			y = Math.max(y, 120); // 最小値120
			pnlCenter.setSize(lblCenterTitle.getWidth() + 5, y);
		}

		// 中央パネル座標
		pnlCenter.setLocation(pnlDebit.getX() + pnlDebit.getWidth(), pnlDebit.getY());
		pnlCenter.setLayout(null);
		add(pnlCenter);

		{

			x = X_POINT;
			y = Y_POINT;

			// 貸方科目のタイトル
			lblCreditItemTitle.setSize(217, 20);
			lblCreditItemTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblCreditItemTitle.setOpaque(true);
			lblCreditItemTitle.setBackground(Color.LIGHT_GRAY);
			lblCreditItemTitle.setLocation(x, y);
			pnlCredit.add(lblCreditItemTitle);

			x += lblCreditItemTitle.getWidth() + MARGIN_X;

			// 貸方金額のタイトル
			lblCreditAmountTitle.setSize(113 - MARGIN_X, 20);
			lblCreditAmountTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblCreditAmountTitle.setOpaque(true);
			lblCreditAmountTitle.setBackground(Color.LIGHT_GRAY);
			lblCreditAmountTitle.setLangMessageID("貸方金額");
			lblCreditAmountTitle.setLocation(x, y);
			pnlCredit.add(lblCreditAmountTitle);

			x = lblCreditItemTitle.getX();
			y += lblCreditItemTitle.getHeight();

			// 貸方計上会社
			ctrlKCompanyCredit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKCompanyCredit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKCompanyCredit.name, cmpNameSize);
			ctrlKCompanyCredit.resize();
			ctrlKCompanyCredit.setLocation(x, y);
			pnlCredit.add(ctrlKCompanyCredit);

			y += ctrlKCompanyCredit.getHeight();

			// 貸方計上部門
			ctrlKDepartmentCredit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKDepartmentCredit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKDepartmentCredit.name, cmpNameSize);
			ctrlKDepartmentCredit.resize();
			ctrlKDepartmentCredit.setLocation(x, y);
			pnlCredit.add(ctrlKDepartmentCredit);

			y += ctrlKDepartmentCredit.getHeight();

			// 貸方科目
			ctrlItemCredit.ctrlItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlItemReference.name, itemNameSize);
			ctrlItemCredit.ctrlSubItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlSubItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlSubItemReference.name, itemNameSize);
			ctrlItemCredit.ctrlDetailItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlDetailItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlDetailItemReference.name, itemNameSize);
			ctrlItemCredit.ctrlItemReference.resize();
			ctrlItemCredit.ctrlSubItemReference.resize();
			ctrlItemCredit.ctrlDetailItemReference.resize();
			TGuiUtil.setComponentSize(ctrlItemCredit, itemSize);
			ctrlItemCredit.setLocation(x, y);
			pnlCredit.add(ctrlItemCredit);

			x = ctrlKCompanyCredit.getX() + ctrlKCompanyCredit.getWidth() + MARGIN_X;
			y = ctrlKCompanyCredit.getY();

			// 通貨
			TGuiUtil.setComponentSize(ctrlCurrencyCredit, comboSize);
			ctrlCurrencyCredit.setLocation(x, y);
			pnlCredit.add(ctrlCurrencyCredit);

			x += ctrlCurrencyCredit.getWidth() + MARGIN_X;

			// 貸方入力金額
			ctrlInputAmountCredit.setMaxLength(13, 4);
			ctrlInputAmountCredit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlInputAmountCredit, amountSize);
			ctrlInputAmountCredit.setLocation(x, y);
			pnlCredit.add(ctrlInputAmountCredit);

			x = ctrlCurrencyCredit.getX() - 5;
			y += ctrlCurrencyCredit.getHeight();

			// 貸方レート
			ctrlRateCredit.setLabelSize(60);
			ctrlRateCredit.setLangMessageID("Ex.");
			ctrlRateCredit.setMaxLength(9, 4);
			ctrlRateCredit.setNumericFormat("#,##0.0000");
			TGuiUtil.setComponentSize(ctrlRateCredit.getField(), amountSize);
			TGuiUtil.setComponentWidth(ctrlRateCredit, 175);
			ctrlRateCredit.setPositiveOnly(true);
			ctrlRateCredit.setLocation(x, y);
			pnlCredit.add(ctrlRateCredit);

			x = ctrlInputAmountCredit.getX();
			y += ctrlRateCredit.getHeight();

			// 貸方邦貨金額
			ctrlKeyAmountCredit.setMaxLength(13, 4);
			ctrlKeyAmountCredit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlKeyAmountCredit, amountSize);
			ctrlKeyAmountCredit.setLocation(x, y);
			pnlCredit.add(ctrlKeyAmountCredit);

			x = ctrlInputAmountCredit.getX() - 30;
			y += ctrlKeyAmountCredit.getHeight();

			// 貸方消費税
			ctrlTaxCredit.btn.setVisible(false);
			TGuiUtil.setComponentWidth(ctrlTaxCredit.code, 30);
			TGuiUtil.setComponentSize(ctrlTaxCredit.name, amountSize);
			ctrlTaxCredit.resize();
			ctrlTaxCredit.setLocation(x, y);
			pnlCredit.add(ctrlTaxCredit);

			x = ctrlCurrencyCredit.getX();
			y += ctrlTaxCredit.getHeight();

			// 貸方税区分
			ctrlTaxDivisionCredit.setModel(modelTax);
			TGuiUtil.setComponentSize(this.ctrlTaxDivisionCredit, comboSize);
			ctrlTaxDivisionCredit.setLocation(x, y);
			pnlCredit.add(ctrlTaxDivisionCredit);

			x += ctrlTaxDivisionCredit.getWidth() + MARGIN_X;

			// 貸方消費税額
			ctrlTaxAmountCredit.setMaxLength(13, 4);
			ctrlTaxAmountCredit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlTaxAmountCredit, amountSize);
			ctrlTaxAmountCredit.setLocation(x, y);
			pnlCredit.add(ctrlTaxAmountCredit);

			y += ctrlTaxAmountCredit.getHeight();

			// サイズ調整
			pnlCredit.setSize(ctrlInputAmountCredit.getX() + ctrlInputAmountCredit.getWidth(), y);
		}

		pnlCredit.setLocation(pnlCenter.getX() + pnlCenter.getWidth(), pnlDebit.getY());
		pnlCredit.setLayout(null);
		add(pnlCredit);

		x = X_POINT;
		y = 0;

		// 行移動指定
		ctrlMoveCheck.setLangMessageID("行移動");
		ctrlMoveCheck.setEnterFocusable(false);
		ctrlMoveCheck.setOpaque(false);
		TGuiUtil.setComponentSize(ctrlMoveCheck, new Dimension(150, 20));
		ctrlMoveCheck.setLocation(x, y);
		pnlButton.add(ctrlMoveCheck);

		x += ctrlMoveCheck.getWidth() + MARGIN_X;

		// タイトル
		TGuiUtil.setComponentSize(lblTitle, 175, 20);
		lblTitle.setForeground(Color.blue);
		lblTitle.setLocation(x, y);
		pnlButton.add(lblTitle);

		x = X_POINT + pnlDebit.getWidth();
		y = 0;

		// 行摘要
		TGuiUtil.setComponentSize(ctrlRemark.code, codeSize);
		TGuiUtil.setComponentSize(ctrlRemark.name, new Dimension(501, 20));
		ctrlRemark.resize();
		ctrlRemark.setLocation(x, y);
		pnlButton.add(ctrlRemark);

		x = X_POINT;
		y = Y_POINT + ctrlRemark.getHeight();

		// 行複写
		btnRowCopy.setLangMessageID("行複写");
		btnRowCopy.setLocation(x, y);
		TGuiUtil.setComponentSize(btnRowCopy, buttonSize);
		pnlButton.add(btnRowCopy);

		x += btnRowCopy.getWidth() + MARGIN_X;

		// 反転複写
		btnRowCopyReverse.setLangMessageID("反転複写");
		TGuiUtil.setComponentSize(btnRowCopyReverse, buttonSize);
		btnRowCopyReverse.setLocation(x, y);
		pnlButton.add(btnRowCopyReverse);

		x += btnRowCopyReverse.getWidth() + MARGIN_X;

		// 行追加
		btnRowNew.setLangMessageID("行追加");
		TGuiUtil.setComponentSize(btnRowNew, buttonSize);
		btnRowNew.setLocation(x, y);
		pnlButton.add(btnRowNew);

		x += btnRowNew.getWidth() + MARGIN_X;

		// 行削除
		btnRowDelete.setLangMessageID("行削除");
		TGuiUtil.setComponentSize(btnRowDelete, buttonSize);
		btnRowDelete.setLocation(x, y);
		pnlButton.add(btnRowDelete);

		x += btnRowDelete.getWidth() + MARGIN_X;

		// 発生日
		ctrlOccurDate.setLabelSize(75);
		ctrlOccurDate.setLangMessageID("C00431");
		ctrlOccurDate.setLocation(x, y);
		pnlButton.add(ctrlOccurDate);

		x += ctrlOccurDate.getWidth();

		// 非会計明細
		TPanel hm1 = ctrlNonAcDetails.getHmField(1);
		TPanel hm2 = ctrlNonAcDetails.getHmField(2);
		TPanel hm3 = ctrlNonAcDetails.getHmField(3);

		if (hm1 != null && hm1.isVisible()) {
			((ILabelField) hm1).setLabelSize(55);

			hm1.setLocation(x, y);
			pnlButton.add(hm1);

			x += hm1.getWidth();
		}

		if (hm2 != null && hm2.isVisible()) {
			((ILabelField) hm2).setLabelSize(55);

			hm2.setLocation(x, y);
			pnlButton.add(hm2);

			x += hm2.getWidth();
		}

		if (hm3 != null && hm3.isVisible()) {
			((ILabelField) hm3).setLabelSize(55);

			hm3.setLocation(x, y);
			pnlButton.add(hm3);

			x += hm3.getWidth();
		}

		// // ボタン
		// x = 993 - buttonSize.height * 4 - MARGIN_X * 4 - 30; // 注意：従来バグ：ボタンは幅、高さ逆
		// y = ctrlMoveCheck.getY() + 2;

		int maxY = Math.max(pnlDebit.getY() + pnlDebit.getHeight(), pnlCenter.getY() + pnlCenter.getHeight());

		pnlButton.setLayout(null);
		pnlButton.setLocation(pnlDebit.getX(), maxY);
		pnlButton.setSize(993, 50);
		add(pnlButton);

		// サイズ微調整
		int maxHeight = Math.max(pnlCenter.getHeight() + 53, 140);
		setSize(993, maxHeight);

		// 縦ラベル微調整
		lblRowNo.setSize(lblRowNo.getWidth(), maxHeight - 2);
		pnlRowNo.setSize(pnlRowNo.getWidth(), maxHeight);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 * @return 最終番号
	 */
	public int setTabControlNo(int tabControlNo) {

		ctrlKCompany[DR].setTabControlNo(tabControlNo++);
		ctrlKDepartment[DR].setTabControlNo(tabControlNo++);
		ctrlItem[DR].setTabControlNo(tabControlNo++);
		ctrlCurrency[DR].setTabControlNo(tabControlNo++);
		ctrlInputAmount[DR].setTabControlNo(tabControlNo++);
		ctrlRate[DR].setTabControlNo(tabControlNo++);
		ctrlKeyAmount[DR].setTabControlNo(tabControlNo++);
		ctrlTax[DR].setTabControlNo(tabControlNo++);
		ctrlTaxDivision[DR].setTabControlNo(tabControlNo++);
		ctrlTaxAmount[DR].setTabControlNo(tabControlNo++);

		ctrlKCompany[CR].setTabControlNo(tabControlNo++);
		ctrlKDepartment[CR].setTabControlNo(tabControlNo++);
		ctrlItem[CR].setTabControlNo(tabControlNo++);
		ctrlCurrency[CR].setTabControlNo(tabControlNo++);
		ctrlInputAmount[CR].setTabControlNo(tabControlNo++);
		ctrlRate[CR].setTabControlNo(tabControlNo++);
		ctrlKeyAmount[CR].setTabControlNo(tabControlNo++);
		ctrlTax[CR].setTabControlNo(tabControlNo++);
		ctrlTaxDivision[CR].setTabControlNo(tabControlNo++);
		ctrlTaxAmount[CR].setTabControlNo(tabControlNo++);

		ctrlCustomer.setTabControlNo(tabControlNo++);
		ctrlEmployee.setTabControlNo(tabControlNo++);
		ctrlManage1.setTabControlNo(tabControlNo++);
		ctrlManage2.setTabControlNo(tabControlNo++);
		ctrlManage3.setTabControlNo(tabControlNo++);
		ctrlManage4.setTabControlNo(tabControlNo++);
		ctrlManage5.setTabControlNo(tabControlNo++);
		ctrlManage6.setTabControlNo(tabControlNo++);
		ctrlRemark.setTabControlNo(tabControlNo++);
		ctrlOccurDate.setTabControlNo(tabControlNo++);
		ctrlNonAcDetails.setTabControlNo(tabControlNo++);

		btnRowCopy.setTabControlNo(tabControlNo++);
		btnRowCopyReverse.setTabControlNo(tabControlNo++);
		btnRowNew.setTabControlNo(tabControlNo++);
		btnRowDelete.setTabControlNo(tabControlNo++);

		return tabControlNo;
	}

	/**
	 * 初期表示
	 */
	public void init() {
		this.controller.init();
	}

	/**
	 * 通貨設定
	 * 
	 * @param currency 通貨
	 */
	public void setCurrecy(Currency currency) {
		controller.setCurrecy(currency, DR);
		controller.setCurrecy(currency, CR);
	}

	/**
	 * 明細をEntity形式でセットする.
	 * 
	 * @param dtl データ
	 */
	public void setEntity(SWK_DTL dtl) {
		controller.setEntity(dtl);
	}

	/**
	 * 明細をEntity形式で返す.
	 * 
	 * @return Entity
	 */
	public SWK_DTL getEntity() {
		return controller.getEntity();
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
	 * デフォルトが内税か
	 * 
	 * @param defaultTaxInnerDivision true:内税
	 */
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		controller.setDefaultTaxInnerDivision(defaultTaxInnerDivision);
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
	 * 借方邦貨金額を取得する
	 * 
	 * @return 借方邦貨金額
	 */
	public BigDecimal getDebitKeyAmount() {
		return controller.getDebitKeyAmount();
	}

	/**
	 * 貸方邦貨金額を取得する
	 * 
	 * @return 貸方邦貨金額
	 */
	public BigDecimal getCreditKeyAmount() {
		return controller.getCreditKeyAmount();
	}

	/**
	 * 借方邦貨消費税額を取得する
	 * 
	 * @return 借方邦貨消費税額
	 */
	public BigDecimal getDebitTaxAmount() {
		return controller.getDebitTaxAmount();
	}

	/**
	 * 貸方邦貨消費税額を取得する
	 * 
	 * @return 貸方邦貨消費税額
	 */
	public BigDecimal getCreditTaxAmount() {
		return controller.getCreditTaxAmount();
	}

	/**
	 * 借方入力消費税額を取得する
	 * 
	 * @return 借方邦貨消費税額
	 */
	public BigDecimal getDebitTaxInputAmount() {
		return controller.getDebitTaxInputAmount();
	}

	/**
	 * 貸方入力消費税額を取得する
	 * 
	 * @return 貸方邦貨消費税額
	 */
	public BigDecimal getCreditTaxInputAmount() {
		return controller.getCreditTaxInputAmount();
	}

	/**
	 * 借方入力金額を取得する
	 * 
	 * @return 借方入力金額
	 */
	public BigDecimal getDebitInputAmount() {
		return controller.getDebitInputAmount();
	}

	/**
	 * 貸方入力金額を取得する
	 * 
	 * @return 貸方入力金額
	 */
	public BigDecimal getCreditInputAmount() {
		return controller.getCreditInputAmount();
	}

	/**
	 * 借方通貨コードを取得する
	 * 
	 * @return 借方通貨コード
	 */
	public Currency getDebitCurrency() {
		return controller.getDebitCurrency();
	}

	/**
	 * 貸方通貨コードを取得する
	 * 
	 * @return 貸方通貨コード
	 */
	public Currency getCreditCurrency() {
		return controller.getCreditCurrency();
	}

	/**
	 * 借方消費税額を含めるか
	 * 
	 * @return 含める:true
	 */
	public boolean isDebitTaxInclusive() {
		return controller.isDebitTaxInclusive();
	}

	/**
	 * 貸方消費税額を含めるか
	 * 
	 * @return 含める:true
	 */
	public boolean isCreditTaxInclusive() {
		return controller.isCreditTaxInclusive();
	}

	/**
	 * callBackListenerを追加する
	 * 
	 * @param callBackListener callBackListener
	 */
	public void addCallBackListener(TCallBackListener callBackListener) {
		controller.addCallBackListener(callBackListener);
	}

	/**
	 * 明細データの通貨リスト作成
	 * 
	 * @param list 通貨リスト
	 */
	public void setCurrecyList(List<Currency> list) {
		controller.setCurrecyList(list);
	}

	/**
	 * 行番号の設定
	 * 
	 * @param rowNo 行番号
	 */
	public void setRowNo(int rowNo) {
		lblRowNo.setLangMessageID(String.valueOf(rowNo));

		// 行番号により色切り替える
		if (rowNo % 2 == 0) {
			// 偶数行
			lblRowNo.setBackground(row1Color);
		} else {
			lblRowNo.setBackground(row2Color);
		}
	}
}
