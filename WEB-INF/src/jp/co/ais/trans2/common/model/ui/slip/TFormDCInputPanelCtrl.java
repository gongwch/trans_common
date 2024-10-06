package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;
import java.math.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 仕訳明細入力コントローラ
 * 
 * @author AIS
 */
public class TFormDCInputPanelCtrl extends TController {

	/** 0:借方 */
	public static final int DR = Dc.DEBIT.value;

	/** 1:貸方 */
	public static final int CR = Dc.CREDIT.value;

	/** 仕訳明細入力パネル */
	protected TFormDCInputPanel panel;

	/** 会計系設定 */
	protected AccountConfig conf = getCompany().getAccountConfig();

	/** 基軸通貨 */
	protected Currency keyCurrency = conf.getKeyCurrency();

	/** 基軸通貨小数点桁数 */
	protected int keyDigit = keyCurrency.getDecimalPoint();

	/** 計算ロジック */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/** デフォルトが内税 */
	protected boolean defaultTaxInnerDivision = false;

	/** 現在Bean */
	protected SWK_DTL entity;

	/** 外部指定取引先 */
	protected Customer customer;

	/** 外部指定社員 */
	protected Employee employee;

	/** 基準日(伝票日付) */
	protected Date baseDate;

	/** 決算仕訳(true:決算仕訳) */
	protected boolean isClosing;

	/** 合計計算のCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param panel 仕訳明細入力パネル
	 */
	protected TFormDCInputPanelCtrl(TFormDCInputPanel panel) {

		this.panel = panel;

		// 初期画面制御
		initView();

		// イベント登録
		addViewEvent();

		// 初期表示
		init();
	}

	/**
	 * 画面表示初期処理
	 */
	protected void initView() {

		String companyCode = getCompany().getCode();

		// 借方科目
		if (!conf.isUseDetailItem()) {

			panel.ctrlItemDebit.ctrlDetailItemReference.setVisible(false);
		}
		panel.ctrlItemDebit.getSearchCondition().setCompanyCode(companyCode);
		panel.ctrlItemDebit.getSearchCondition().setDepartmentCode(getUser().getDepartment().getCode());
		panel.ctrlItemDebit.ctrlItemReference.refleshEntity();

		// 借方消費税
		panel.ctrlTaxDebit.getSearchCondition().setCompanyCode(companyCode);

		// 管理1
		if (!conf.isUseManagement1()) {

			panel.ctrlManage1.setVisible(false);
		}
		panel.ctrlManage1.getSearchCondition().setCompanyCode(companyCode);

		// 取引先
		panel.ctrlCustomer.getSearchCondition().setCompanyCode(companyCode);

		// 摘要
		panel.ctrlRemark.getSearchCondition().setSlipRemark(false);
		panel.ctrlRemark.getSearchCondition().setSlipRowRemark(true);
		panel.ctrlRemark.getSearchCondition().setCompanyCode(companyCode);

		// 貸方科目
		if (!conf.isUseDetailItem()) {

			panel.ctrlItemCredit.ctrlDetailItemReference.setVisible(false);
		}
		panel.ctrlItemCredit.getSearchCondition().setCompanyCode(companyCode);
		panel.ctrlItemCredit.getSearchCondition().setDepartmentCode(getUser().getDepartment().getCode());
		panel.ctrlItemCredit.ctrlItemReference.refleshEntity();

		// 貸方消費税
		panel.ctrlTaxCredit.getSearchCondition().setCompanyCode(companyCode);

		// 基軸通貨の小数点設定
		panel.ctrlInputAmountDebit.setDecimalPoint(keyDigit);
		panel.ctrlTaxAmountDebit.setDecimalPoint(keyDigit);
		panel.ctrlKeyAmountDebit.setDecimalPoint(keyDigit);
		panel.ctrlInputAmountCredit.setDecimalPoint(keyDigit);
		panel.ctrlTaxAmountCredit.setDecimalPoint(keyDigit);
		panel.ctrlKeyAmountCredit.setDecimalPoint(keyDigit);

		// 通貨リスト
		initCurrencyList();
	}

	/**
	 * 通貨リスト初期化
	 */
	protected void initCurrencyList() {
		panel.ctrlCurrencyDebit.removeAllItems();
		panel.ctrlCurrencyCredit.removeAllItems();

		List<Currency> list = null;

		try {
			CurrencySearchCondition condition = new CurrencySearchCondition();
			condition.setCompanyCode(getCompanyCode());
			list = (List<Currency>) request(CurrencyManager.class, "get", condition);
		} catch (Exception ex) {
			errorHandler(ex);
		}

		if (list == null) {
			panel.ctrlCurrencyDebit.addTextValueItem(keyCurrency, keyCurrency.getCode());
			panel.ctrlCurrencyCredit.addTextValueItem(keyCurrency, keyCurrency.getCode());
		} else {
			for (Currency bean : list) {
				panel.ctrlCurrencyDebit.addTextValueItem(bean, bean.getCode());
				panel.ctrlCurrencyCredit.addTextValueItem(bean, bean.getCode());
			}
		}

		panel.ctrlCurrencyDebit.setSelectedText(keyCurrency.getCode());
		panel.ctrlCurrencyCredit.setSelectedText(keyCurrency.getCode());
	}

	/**
	 * イベント登録
	 */
	protected void addViewEvent() {

		for (int i = DR; i <= CR; i++) {

			final int dc = i;

			// 計上会社
			panel.ctrlKCompany[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public boolean afterVerify(boolean flag) {
					if (!flag) {
						return false;
					}
					changedCompany(dc);
					doAfterEvent();

					return true;
				}
			});

			// 計上部門
			panel.ctrlKDepartment[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public boolean afterVerify(boolean flag) {
					if (!flag) {
						return false;
					}
					changedDepartment(dc);
					doAfterEvent();

					return true;
				}
			});

			// 科目
			panel.ctrlItem[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public boolean afterVerify(boolean flag) {
					if (!flag) {
						return false;
					}
					changedItem(dc);
					doAfterEvent();

					return true;
				}
			});

			// 通貨
			panel.ctrlCurrency[dc].addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						changedCurrency(dc);
						doAfterEvent();
					}
				}
			});

			// 通貨レート
			panel.ctrlRate[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlRate[dc].isValueChanged2()) {
						return true;
					}

					changedRate(dc);
					doAfterEvent();
					return true;
				}
			});

			// 消費税
			panel.ctrlTax[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag) {
						return;
					}
					changedTax(dc);
					doAfterEvent();
				}
			});

			// 税区分
			panel.ctrlTaxDivision[dc].addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						changedTaxDivision(dc);
						doAfterEvent();
					}
				}
			});

			// 入力金額
			panel.ctrlInputAmount[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlInputAmount[dc].isValueChanged2()) {
						return true;
					}

					changedInputAmount(dc);
					doAfterEvent();
					return true;
				}
			});

			// 消費税金額
			panel.ctrlTaxAmount[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlTaxAmount[dc].isValueChanged2()) {
						return true;
					}

					changedTaxAmount(dc);
					doAfterEvent();
					return true;
				}
			});

			// 基軸金額
			panel.ctrlKeyAmount[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlKeyAmount[dc].isValueChanged2()) {
						return true;
					}

					changedKeyAmount(dc);
					doAfterEvent();
					return true;
				}
			});

		}

		// 発生日
		panel.ctrlOccurDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlOccurDate.isValueChanged2()) {
					return true;
				}

				changedOccurDate();
				doAfterEvent();
				return true;
			}
		});

	}

	/**
	 * 初期状態
	 */
	protected void init() {

		this.entity = null;
		panel.lblTitle.setText("");

		// クリア
		clearInput(DR);
		clearInput(CR);
	}

	// _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

	/**
	 * 入力部のみ初期状態
	 * 
	 * @param dc 貸借
	 */
	public void clearInput(int dc) {
		// クリア
		panel.ctrlKCompany[dc].clear();
		panel.ctrlKDepartment[dc].clear();
		panel.ctrlItem[dc].clear();
		panel.ctrlOccurDate.clear();
		panel.ctrlTaxDivision[dc].setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);
		panel.ctrlRemark.clear();
		panel.ctrlInputAmount[dc].clear();

		// 初期値
		setCompany(getCompany(), dc);

		if (isDefaultBlankDepartment()) {
			// 計上部門の初期値がnullの場合、ユーザ情報の設定不要
			setDepartment(null, dc);
		} else {
			// デフォルト計上部門
			setDepartment(getUser().getDepartment(), dc);
		}

		// 入力制御
		panel.ctrlKCompany[dc].setEditable(conf.isUseGroupAccount()); // グループ会計OFF時は入力不可
		panel.ctrlKDepartment[dc].setEditable(true);
		panel.ctrlItem[dc].ctrlItemReference.setEditable(!isDefaultBlankDepartment());
		panel.ctrlOccurDate.setEditable(true);
		panel.ctrlTaxDivision[dc].setEnabled(true);
		panel.ctrlRemark.setEditable(true);
		panel.ctrlInputAmount[dc].setEditable(true);

		// 科目連動系
		clearInputForItem(dc);

		// 発生日デフォルト値設定
		if (TSlipDetailPanelCtrl.allowDefaultOccurDate && panel.ctrlOccurDate.isVisible()
			&& panel.ctrlOccurDate.isEditable()) {
			panel.ctrlOccurDate.setValue(baseDate);
			changedOccurDate();
		}

	}

	/**
	 * @return true:計上部門初期ブランク
	 */
	protected boolean isDefaultBlankDepartment() {
		return TSlipDetailPanelCtrl.departmentDefaultBlank;
	}

	/**
	 * 科目関連入力部のみ初期状態
	 * 
	 * @param dc 貸借
	 */
	public void clearInputForItem(int dc) {
		// クリア
		panel.ctrlCurrency[dc].setSelectedText(keyCurrency.getCode());
		panel.ctrlRate[dc].clear();
		panel.ctrlTax[dc].clear();
		panel.ctrlTaxAmount[dc].clear();
		panel.ctrlInputAmount[dc].clear();
		panel.ctrlKeyAmount[dc].clear();
		panel.ctrlCustomer.clear();
		panel.ctrlEmployee.clear();
		panel.ctrlManage1.clear();
		panel.ctrlManage2.clear();
		panel.ctrlManage3.clear();
		panel.ctrlManage4.clear();
		panel.ctrlManage5.clear();
		panel.ctrlManage6.clear();
		panel.ctrlNonAcDetails.clear();

		// 入力制御
		panel.ctrlCurrency[dc].setEnabled(false);
		panel.ctrlRate[dc].setEditable(false);
		panel.ctrlTaxDivision[dc].setEnabled(false); // 税変更不可
		panel.ctrlTax[dc].setEditable(false);
		panel.ctrlTaxAmount[dc].setEditable(false);
		panel.ctrlInputAmount[dc].setEditable(false);
		panel.ctrlKeyAmount[dc].setEditable(false);
		panel.ctrlCustomer.setEditable(false);
		panel.ctrlEmployee.setEditable(false);
		panel.ctrlManage1.setEditable(false);
		panel.ctrlManage2.setEditable(false);
		panel.ctrlManage3.setEditable(false);
		panel.ctrlManage4.setEditable(false);
		panel.ctrlManage5.setEditable(false);
		panel.ctrlManage6.setEditable(false);
		panel.ctrlNonAcDetails.setEditable(false);

		// 初期値
		setCurrecy(keyCurrency, dc);

	}

	/**
	 * 計上会社変更
	 * 
	 * @param dc 貸借
	 * @return false:付替設定NG
	 */
	protected boolean changedCompany(int dc) {
		try {
			if (panel.ctrlKCompany[dc].isEmpty()) {
				panel.ctrlKCompany[dc].setEntity(getCompany());
			}

			Company company = panel.ctrlKCompany[dc].getEntity();

			if (company == null) {
				return false;
			}

			String code = company.getCode();

			// 会社間付替マスタが設定されていません。
			if (!getCompanyCode().equals(code) && !isAppropriateCompanyReplace(dc)) {
				showMessage("I00054");// 会社間付替マスタが設定されていません。
				panel.ctrlKCompany[dc].code.requestFocus();
				panel.ctrlKCompany[dc].code.clearOldText();

				return false;
			}

			// リフレッシュの場合、クリア不要
			if (!isNotClearByCompany()) {

				// クリア
				clearInput(dc);

				setCompany(company, dc);

				// 計上部門はログイン会社以外はブランク
				if (!getCompanyCode().equals(code)) {
					panel.ctrlKDepartment[dc].clear();
				}

				changedDepartment(dc);
			} else {

				// 会社設定のみ
				setCompany(company, dc);
			}

			return true;

		} catch (TException ex) {
			errorHandler(ex);
			return true;
		}
	}

	/**
	 * @return true:計上会社変更より明細項目最新取得を行う、存在していない場合のみクリア
	 */
	protected boolean isNotClearByCompany() {
		return true;
	}

	/**
	 * 計上科目コードのチェック
	 * 
	 * @param dc 貸借
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean isAppropriateCompanyReplace(int dc) throws TException {
		String kcompany = panel.ctrlKCompany[dc].getCode();

		List<TransferConfig> list = (List<TransferConfig>) request(CompanyManager.class, "getTransferConfig",
			getCompanyCode(), kcompany);

		if (list == null) {
			return false;
		}

		return list.size() == 2;
	}

	/**
	 * 計上部門変更
	 * 
	 * @param dc 貸借
	 */
	protected void changedDepartment(int dc) {
		Department dept = panel.ctrlKDepartment[dc].getEntity();

		// 変更前科目コード
		String itemCode = panel.ctrlItem[dc].ctrlItemReference.getCode();

		setDepartment(dept, dc);

		// 変更があったら科目変更通知
		if (!itemCode.equals(panel.ctrlItem[dc].ctrlItemReference.getCode())) {
			changedItem(dc);
		}
	}

	/**
	 * 科目変更
	 * 
	 * @param dc 貸借
	 * @return false:NG
	 */
	protected boolean changedItem(int dc) {
		Item item = panel.ctrlItem[dc].getEntity();

		// 相手制御
		int ait = CR;

		if (dc == DR) {
			ait = CR;
		} else {
			ait = DR;
		}

		clearInput(ait);

		if (item == null) {
			clearInputForItem(dc);
			changedCurrency(dc);
			return true;
		}

		// 特別処理
		{
			panel.ctrlKCompany[ait].code.setEditable(false);
			panel.ctrlKDepartment[ait].code.setEditable(false);
			panel.ctrlItem[ait].ctrlItemReference.code.setEditable(false);
			panel.ctrlKCompany[ait].clear();
			panel.ctrlKDepartment[ait].clear();
			panel.ctrlItem[ait].clear();
			panel.ctrlRate[ait].clear();
			panel.ctrlKeyAmount[ait].clear();
		}

		// 補助、内訳がある場合は、そっちを反映
		if (item.getSubItem() != null) {
			item = item.getSubItem();
		}

		if (item.getDetailItem() != null) {
			item = item.getDetailItem();
		}

		panel.ctrlInputAmount[dc].setEditable(true);

		// 多通貨入力フラグ
		panel.ctrlCurrency[dc].setEnabled(item.isUseForeignCurrency());
		if (!item.isUseForeignCurrency()) {
			setCurrecy(keyCurrency, dc);
			changedCurrency(dc);
		}

		// 消費税
		if (item.isUseSalesTaxation() || item.isUsePurchaseTaxation()) {
			// 売上課税入力、または、仕入課税入力の場合、入力可
			panel.ctrlTaxDivision[dc].setEnabled(true);
			panel.ctrlTax[dc].setEditable(true);
			panel.ctrlTax[dc].setEntity(item.getConsumptionTax());
			panel.ctrlTax[dc].getSearchCondition().setHasSales(item.isUseSalesTaxation());
			panel.ctrlTax[dc].getSearchCondition().setHasPurcharse(item.isUsePurchaseTaxation());

		} else {
			panel.ctrlTaxDivision[dc].setEnabled(false);
			panel.ctrlTax[dc].getSearchCondition().setHasSales(false);
			panel.ctrlTax[dc].getSearchCondition().setHasPurcharse(false);
			panel.ctrlTax[dc].setEditable(false);
			panel.ctrlTax[dc].setEntity(null);
		}

		// 条件が変わってるので整合性チェック
		panel.ctrlTax[dc].refleshEntity();
		changedTax(dc);

		// 取引先
		CustomerType customerType = item.getClientType();
		panel.ctrlCustomer.setEditable(customerType != CustomerType.NONE);
		panel.ctrlCustomer.getSearchCondition().setType(customerType);

		if (customerType != CustomerType.NONE) {

			// 取引先未設定の場合は初期値として、外部設定の取引先を指定
			if (Util.isNullOrEmpty(panel.ctrlCustomer.getCode()) && customer != null) {
				panel.ctrlCustomer.setEntity(customer);
			}

			panel.ctrlCustomer.refleshEntity();

		} else {
			panel.ctrlCustomer.clear();
		}

		// 社員
		panel.ctrlEmployee.setEditable(item.isUseEmployee());

		if (!item.isUseEmployee()) {
			panel.ctrlEmployee.clear();

		} else if (panel.ctrlEmployee.isEmpty() && !TSlipDetailPanelCtrl.employeeDefaultBlank) {
			Company kcompany = panel.ctrlKCompany[dc].getEntity();

			if (kcompany.getCode().equals(getCompanyCode())) {
				Employee refEmployee = employee == null ? getUser().getEmployee() : employee;
				panel.ctrlEmployee.setEntity(refEmployee);
			}
		}

		// 管理１～6
		panel.ctrlManage1.setEditable(item.isUseManagement1());
		panel.ctrlManage2.setEditable(item.isUseManagement2());
		panel.ctrlManage3.setEditable(item.isUseManagement3());
		panel.ctrlManage4.setEditable(item.isUseManagement4());
		panel.ctrlManage5.setEditable(item.isUseManagement5());
		panel.ctrlManage6.setEditable(item.isUseManagement6());

		if (!item.isUseManagement1()) panel.ctrlManage1.clear();
		if (!item.isUseManagement2()) panel.ctrlManage2.clear();
		if (!item.isUseManagement3()) panel.ctrlManage3.clear();
		if (!item.isUseManagement4()) panel.ctrlManage4.clear();
		if (!item.isUseManagement5()) panel.ctrlManage5.clear();
		if (!item.isUseManagement6()) panel.ctrlManage6.clear();

		// 非会計明細1～3
		panel.ctrlNonAcDetails.setEditable(1, item.isUseNonAccount1());
		panel.ctrlNonAcDetails.setEditable(2, item.isUseNonAccount2());
		panel.ctrlNonAcDetails.setEditable(3, item.isUseNonAccount3());

		if (!item.isUseNonAccount1()) panel.ctrlNonAcDetails.clear(1);
		if (!item.isUseNonAccount2()) panel.ctrlNonAcDetails.clear(2);
		if (!item.isUseNonAccount3()) panel.ctrlNonAcDetails.clear(3);

		if (isAllowOccurDateBlank()) {
			// 発生日ブランク可能の場合、科目フラグにより制御
			if (item.isUseOccurDate()) {
				panel.ctrlOccurDate.setEditable(true);
			} else {
				panel.ctrlOccurDate.setEditable(false);
				panel.ctrlOccurDate.clear();
			}
		}

		return true;
	}

	/**
	 * 通貨変更
	 * 
	 * @param dc 貸借
	 */
	protected void changedCurrency(int dc) {
		Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();

		setCurrecy(currency, dc);

		// レート
		panel.ctrlRate[dc].setNumberValue(getCurrencyRate(dc));

		changedRate(dc);
	}

	/**
	 * 発生日の変更
	 */
	protected void changedOccurDate() {

		int dc = DR;

		if (!Util.isNullOrEmpty(panel.ctrlItem[CR].ctrlItemReference.getCode())) {
			dc = CR;
		}

		// レート変更
		BigDecimal old = panel.ctrlRate[dc].getBigDecimal();
		BigDecimal nuw = getCurrencyRate(dc);

		// 取得レートが無い
		if (nuw == null) {
			panel.ctrlRate[dc].clear();
			return;
		}

		// レート値に変更がない
		if (old.compareTo(nuw) == 0) {
			return;
		}

		panel.ctrlRate[dc].setNumberValue(nuw);
		changedRate(dc);
	}

	/**
	 * 通貨レート 変更
	 * 
	 * @param dc 貸借
	 */
	protected void changedRate(int dc) {
		// 邦貨換算
		Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();

		if (currency == null || panel.ctrlInputAmount[dc].isEmpty()) {
			return;
		}

		BigDecimal inAmount = panel.ctrlInputAmount[dc].getBigDecimal();
		BigDecimal rate = panel.ctrlRate[dc].getBigDecimal();
		panel.ctrlKeyAmount[dc].setNumber(convertKeyAmount(inAmount, currency, rate));

		// 消費税
		changedTax(dc);

	}

	/**
	 * 入力金額の変更
	 * 
	 * @param dc 貸借
	 */
	protected void changedInputAmount(int dc) {

		// 消費税
		changedTax(dc);
	}

	/**
	 * 消費税額の変更
	 * 
	 * @param dc 貸借
	 */
	@SuppressWarnings("unused")
	protected void changedTaxAmount(int dc) {
		//
	}

	/**
	 * 基軸金額の変更
	 * 
	 * @param dc 貸借
	 */
	@SuppressWarnings("unused")
	protected void changedKeyAmount(int dc) {
		//
	}

	/**
	 * 内税/外税 切替
	 * 
	 * @param dc 貸借
	 */
	protected void changedTaxDivision(int dc) {
		changedTax(dc);
	}

	/**
	 * 消費税変更
	 * 
	 * @param dc 貸借
	 */
	protected void changedTax(int dc) {

		Currency currency = null;

		try {
			// 消費税計算
			Company kcompany = panel.ctrlKCompany[dc].getEntity();
			currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();
			ConsumptionTax tax = panel.ctrlTax[dc].getEntity();
			BigDecimal rate = panel.ctrlRate[dc].getBigDecimalValue();

			// レート0なら消費税なし
			if (kcompany == null || tax == null || DecimalUtil.isNullOrZero(tax.getRate())
				|| DecimalUtil.isNullOrZero(rate)) {
				panel.ctrlTaxAmount[dc].clear();
				panel.ctrlTaxAmount[dc].setEditable(false);
				return;
			}

			// 通貨なし
			if (currency == null) {
				return;
			}

			AccountConfig kconf = kcompany.getAccountConfig();

			// 売上、仕入の場合は入力可
			switch (tax.getTaxType()) {
				case NOT:
					panel.ctrlTaxAmount[dc].clear();
					panel.ctrlTaxAmount[dc].setEditable(false);
					break;

				case SALES:
				case PURCHAESE:
					panel.ctrlTaxAmount[dc].setEditable(true);

					BigDecimal inAmount = panel.ctrlInputAmount[dc].getBigDecimal();

					if (DecimalUtil.isZero(inAmount)) {
						panel.ctrlTaxAmount[dc].clear();
						break;
					}

					TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
					param.setInside(panel.ctrlTaxDivision[dc].getSelectedItemValue() == TaxCalcType.IN); // 内税or外税
					param.setAmount(inAmount); // 対象金額
					param.setTax(tax); // 消費税情報
					param.setDigit(currency.getDecimalPoint()); // 小数点桁数
					param.setReceivingFunction(kconf.getReceivingFunction()); // 借受
					param.setPaymentFunction(kconf.getPaymentFunction()); // 仮払
					panel.ctrlTaxAmount[dc].setNumber(calculator.calculateTax(param));
					break;
			}

		} finally {
			if (currency != null) {
				// 邦貨換算
				BigDecimal inAmount = panel.ctrlInputAmount[dc].getBigDecimal();
				BigDecimal rate = panel.ctrlRate[dc].getBigDecimal();
				panel.ctrlKeyAmount[dc].setNumber(convertKeyAmount(inAmount, currency, rate));
			}
		}
	}

	/**
	 * 基準日設定(有効日判定基準)
	 * 
	 * @param baseDate 基準日
	 */
	protected void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;

		// 有効期限;
		panel.ctrlItemDebit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlItemCredit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlTaxDebit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlTaxCredit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlRemark.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlCustomer.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlEmployee.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage1.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage2.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage3.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage4.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage5.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage6.getSearchCondition().setValidTerm(baseDate);

	}

	/**
	 * 決算仕訳設定
	 * 
	 * @param isClosing true:決算仕訳、false:通常仕訳
	 */
	public void setClosingEntry(boolean isClosing) {
		this.isClosing = isClosing;
	}

	/**
	 * 外部指定取引先を設定(固定表示用)
	 * 
	 * @param customer 外部指定取引先
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * 外部指定社員を設定(初期表示用)
	 * 
	 * @param employee 社員
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return true:グループ会計(基軸通貨異なる計上会社可)
	 */
	public static boolean isGroupAccounting() {
		return TSlipDetailPanelCtrl.groupAccounting;
	}

	/**
	 * @return true:発生日ブランク可能
	 */
	public boolean isAllowOccurDateBlank() {
		return TSlipDetailPanelCtrl.allowOccurDateBlank && !conf.isUseIfrs();
	}

	/**
	 * @param dtl
	 * @return true:発生日ブランクの場合、伝票日付を登録する
	 */
	public boolean isAllowEntryDefaultOccurDate(SWK_DTL dtl) {
		if (dtl.getHAS_DATE() != null) {
			return false;
		}

		if (!dtl.isUseItemOccurDate()) {
			return false;
		}
		return TSlipDetailPanelCtrl.allowEntryDefaultOccurDate;
	}

	/**
	 * 会社設定
	 * 
	 * @param kcompany 会社コード
	 * @param dc 貸借
	 */
	protected void setCompany(Company kcompany, int dc) {
		panel.ctrlKCompany[dc].setEntity(kcompany);

		String code = kcompany.getCode();

		// 条件変更
		panel.ctrlKDepartment[dc].getSearchCondition().setCompanyCode(code);
		panel.ctrlItem[dc].getSearchCondition().setCompanyCode(code);

		panel.ctrlTax[dc].getSearchCondition().setCompanyCode(code);
		panel.ctrlRemark.getSearchCondition().setCompanyCode(code);
		panel.ctrlCustomer.getSearchCondition().setCompanyCode(code);
		panel.ctrlEmployee.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage1.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage2.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage3.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage4.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage5.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage6.getSearchCondition().setCompanyCode(code);

		AccountConfig kconf = kcompany.getAccountConfig();

		// 名称セット
		panel.ctrlItem[dc].ctrlItemReference.btn.setText(kconf.getItemName());
		panel.ctrlItem[dc].ctrlSubItemReference.btn.setText(kconf.getSubItemName());
		panel.ctrlItem[dc].ctrlDetailItemReference.btn.setText(kconf.getDetailItemName());
		panel.ctrlManage1.btn.setText(kconf.getManagement1Name());
		panel.ctrlManage2.btn.setText(kconf.getManagement2Name());
		panel.ctrlManage3.btn.setText(kconf.getManagement3Name());
		panel.ctrlManage4.btn.setText(kconf.getManagement4Name());
		panel.ctrlManage5.btn.setText(kconf.getManagement5Name());
		panel.ctrlManage6.btn.setText(kconf.getManagement6Name());

		// 利用するか
		panel.ctrlItem[dc].ctrlDetailItemReference.code.setVisible(kconf.isUseDetailItem());
		panel.ctrlItem[dc].ctrlDetailItemReference.name.setVisible(kconf.isUseDetailItem());
		panel.ctrlManage1.setVisible(kconf.isUseManagement1());
		panel.ctrlManage2.setVisible(kconf.isUseManagement2());
		panel.ctrlManage3.setVisible(kconf.isUseManagement3());
		panel.ctrlManage4.setVisible(kconf.isUseManagement4());
		panel.ctrlManage5.setVisible(kconf.isUseManagement5());
		panel.ctrlManage6.setVisible(kconf.isUseManagement6());

		// タイトル設定
		if (dc == DR) {
			String title = createItemTitle(kconf, DR);
			panel.lblDebitItemTitle.setText(title);
			panel.lblDebitItemTitle.setToolTipText(title);
		} else {
			String title = createItemTitle(kconf, CR);
			panel.lblCreditItemTitle.setText(title);
			panel.lblCreditItemTitle.setToolTipText(title);
		}

		// リフレッシュの場合、各値再取得
		if (isNotClearByCompany()) {

			boolean isBreak = false;

			panel.ctrlKDepartment[dc].refleshEntity();
			if (panel.ctrlKDepartment[dc].getEntity() == null) {
				changedDepartment(dc);

				isBreak = true;
			}

			if (!isBreak) {
				panel.ctrlItem[dc].refleshGroupEntity();

				if (panel.ctrlItem[dc].getEntity() == null) {
					isBreak = true;
				}

				changedItem(dc);
			}

			if (!isBreak) {
				Remark oldRemark = (Remark) panel.ctrlRemark.getController().getUnspecifiedEntity();

				panel.ctrlRemark.refleshEntity();
				panel.ctrlCustomer.refleshEntity();
				panel.ctrlEmployee.refleshEntity();
				panel.ctrlManage1.refleshEntity();
				panel.ctrlManage2.refleshEntity();
				panel.ctrlManage3.refleshEntity();
				panel.ctrlManage4.refleshEntity();
				panel.ctrlManage5.refleshEntity();
				panel.ctrlManage6.refleshEntity();
				panel.ctrlTax[dc].refleshEntity();

				if (panel.ctrlRemark.getEntity() != null) {
					panel.ctrlRemark.setNames(panel.ctrlRemark.getEntity().getName());
				} else if (oldRemark != null && Util.isNullOrEmpty(oldRemark.getCode())) {
					panel.ctrlRemark.setEntity(oldRemark);
				}
				if (panel.ctrlCustomer.getEntity() != null) {
					panel.ctrlCustomer.setNames(panel.ctrlCustomer.getEntity().getNames());
				}
				if (panel.ctrlEmployee.getEntity() != null) {
					panel.ctrlEmployee.setNames(panel.ctrlEmployee.getEntity().getNames());
				}
				if (panel.ctrlManage1.getEntity() != null) {
					panel.ctrlManage1.setNames(panel.ctrlManage1.getEntity().getNames());
				}
				if (panel.ctrlManage2.getEntity() != null) {
					panel.ctrlManage2.setNames(panel.ctrlManage2.getEntity().getNames());
				}
				if (panel.ctrlManage3.getEntity() != null) {
					panel.ctrlManage3.setNames(panel.ctrlManage3.getEntity().getNames());
				}
				if (panel.ctrlManage4.getEntity() != null) {
					panel.ctrlManage4.setNames(panel.ctrlManage4.getEntity().getNames());
				}
				if (panel.ctrlManage5.getEntity() != null) {
					panel.ctrlManage5.setNames(panel.ctrlManage5.getEntity().getNames());
				}
				if (panel.ctrlManage6.getEntity() != null) {
					panel.ctrlManage6.setNames(panel.ctrlManage6.getEntity().getNames());
				}
				if (panel.ctrlTax[dc].getEntity() != null) {
					panel.ctrlTax[dc].setNames(panel.ctrlTax[dc].getEntity().getNames());
				}
			}
		}

	}

	/**
	 * 科目タイトル文字生成
	 * 
	 * @param kconf
	 * @param dc
	 * @return タイトル設定
	 */
	protected String createItemTitle(AccountConfig kconf, int dc) {
		// タイトル設定
		// 「借方 会社/部門/科目/補助/内訳」
		// 「貸方 会社/部門/科目/補助/内訳」
		StringBuilder sb = new StringBuilder();

		if (dc == DR) {
			sb.append(getWord("C00080")); // 借方
		} else {
			sb.append(getWord("C00068")); // 貸方
		}
		sb.append("  "); // 余白
		sb.append(getWord("C00053")); // 会社
		sb.append("/"); // sep
		sb.append(getWord("C00467")); // 部門
		sb.append("/"); // sep
		sb.append(kconf.getItemName()); // 科目
		sb.append("/"); // sep
		sb.append(kconf.getSubItemName()); // 補助
		if (kconf.isUseDetailItem()) {
			sb.append("/"); // sep
			sb.append(kconf.getDetailItemName()); // 内訳
		}
		return sb.toString();
	}

	/**
	 * 計上部門設定
	 * 
	 * @param dept 計上部門
	 * @param dc 貸借
	 */
	protected void setDepartment(Department dept, int dc) {
		panel.ctrlKDepartment[dc].setEntity(dept);

		if (dept == null) {
			panel.ctrlItem[dc].setEntity(null);
			panel.ctrlItem[dc].ctrlItemReference.setEditable(false);
			return;
		}

		// 科目初期化
		panel.ctrlItem[dc].ctrlItemReference.setEditable(true);

		String code = dept.getCode();

		String oldDeptCode = panel.ctrlItem[dc].getSearchCondition().getDepartmentCode();

		if (!code.equals(Util.avoidNull(oldDeptCode))) {
			// 部門コードが変更になった場合、条件を変更
			panel.ctrlItem[dc].getSearchCondition().setDepartmentCode(code);

			// 条件変更により、整合性チェックでOKなら残す
			if (!panel.ctrlItem[dc].isEmpty()) {
				if (!isNotClearByCompany()) {
					panel.ctrlItem[dc].refleshEntity();
				} else {
					panel.ctrlItem[dc].refleshGroupEntity();
				}
			}
		}
	}

	/**
	 * 通貨設定
	 * 
	 * @param currency 通貨
	 * @param dc 貸借
	 */
	protected void setCurrecy(Currency currency, int dc) {

		if (currency == null) {
			panel.ctrlCurrency[dc].setSelectedText(keyCurrency.getCode());
			panel.ctrlRate[dc].clear();
			panel.ctrlRate[dc].setEditable(false);
			panel.ctrlKeyAmount[dc].clear();
			panel.ctrlKeyAmount[dc].setEditable(false);
			return;
		}

		panel.ctrlCurrency[dc].setSelectedText(currency.getCode());

		String currencyCode = currency.getCode();
		boolean isKey = keyCurrency.getCode().equals(currencyCode);

		// レート
		panel.ctrlRate[dc].setEditable(!isKey);
		panel.ctrlRate[dc].setNumberValue(isKey ? BigDecimal.ONE : null);
		changedRate(dc);

		// 基軸金額
		panel.ctrlKeyAmount[dc].setEditable(!isKey);

		// 小数点変更
		int digit = currency.getDecimalPoint();

		// 各コンポーネント
		panel.ctrlInputAmount[dc].setDecimalPoint(digit);
		panel.ctrlTaxAmount[dc].setDecimalPoint(digit);
		changedTax(dc);
	}

	// _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

	/**
	 * 明細をEntity形式でセットする.
	 * 
	 * @param dtl データ
	 */
	protected void setEntity(SWK_DTL dtl) {

		// クリア
		init();

		if (dtl == null) {
			return;
		}

		int dc = dtl.isDR() ? DR : CR;

		setCompany(dtl.getAppropriateCompany(), dc); // 計上会社
		setDepartment(dtl.getDepartment(), dc); // 計上部門

		Item item = dtl.getItem();
		panel.ctrlItem[dc].setEntity(item != null ? item.clone() : null); // 科目
		changedItem(dc);

		setCurrecy(dtl.getCurrency(), dc); // 通貨コード

		panel.ctrlTax[dc].setEntity(dtl.getTax()); // 税
		changedTax(dc);

		if (dtl.isErasing()) {

			// 行複写不可
			panel.btnRowCopy.setEnabled(false);
			panel.btnRowCopyReverse.setEnabled(false);

			// 入力不可
			panel.ctrlKCompany[dc].setEditable(false);
			panel.ctrlKDepartment[dc].setEditable(false);
			panel.ctrlItem[dc].ctrlItemReference.setEditable(false);
			panel.ctrlItem[dc].ctrlSubItemReference.setEditable(false);
			panel.ctrlItem[dc].ctrlDetailItemReference.setEditable(false);
			panel.ctrlOccurDate.setEditable(false);
			panel.ctrlTaxDivision[dc].setEnabled(false);

			panel.ctrlCurrency[dc].setEditable(false);
			panel.ctrlRate[dc].setEditable(false);
			panel.ctrlTax[dc].setEditable(false);
			panel.ctrlTaxAmount[dc].setEditable(false);
			panel.ctrlCustomer.setEditable(false);
			panel.ctrlEmployee.setEditable(false);
			panel.ctrlManage1.setEditable(false);
			panel.ctrlManage2.setEditable(false);
			panel.ctrlManage3.setEditable(false);
			panel.ctrlManage4.setEditable(false);
			panel.ctrlManage5.setEditable(false);
			panel.ctrlManage6.setEditable(false);
			panel.ctrlNonAcDetails.setEditable(false);

			if (dtl.isAPErasing() || (TSlipDetailPanelCtrl.isUseBS && dtl.isBSErasing())) {
				// AP、BSの場合は金額入力不可
				panel.ctrlInputAmount[dc].setEditable(false);
				panel.ctrlKeyAmount[dc].setEditable(false);
			}
		}

		// 借方税区分
		if (dtl.getSWK_ZEI_KBN() == SWK_DTL.ZEI_KBN.TAX_OUT) {
			panel.ctrlTaxDivision[dc].setSelectedItemValue(TaxCalcType.OUT);

		} else if (dtl.getSWK_ZEI_KBN() == SWK_DTL.ZEI_KBN.TAX_IN) {
			panel.ctrlTaxDivision[dc].setSelectedItemValue(TaxCalcType.IN);

		} else {
			panel.ctrlTaxDivision[dc].setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);
		}

		panel.ctrlKeyAmount[dc].setNumber(dtl.getSWK_KIN()); // 金額
		panel.ctrlRate[dc].setNumberValue(dtl.getSWK_CUR_RATE()); // 通貨レート
		panel.ctrlInputAmount[dc].setNumber(dtl.getSWK_IN_KIN()); // 入力金額

		// 入力消費税額
		if (dtl.getTax() == null || DecimalUtil.isNullOrZero(dtl.getTax().getRate())) {
			panel.ctrlTaxAmount[dc].clear();

		} else {
			panel.ctrlTaxAmount[dc].setNumber(dtl.getSWK_IN_ZEI_KIN());
		}

		panel.ctrlKeyAmount[dc].setNumber(dtl.getSWK_KIN()); // 金額
		panel.ctrlRemark.setCode(dtl.getSWK_GYO_TEK_CODE()); // 行摘要コード
		panel.ctrlRemark.setNames(dtl.getSWK_GYO_TEK()); // 行摘要
		panel.ctrlCustomer.setCode(dtl.getSWK_TRI_CODE()); // 取引先コード
		panel.ctrlCustomer.setNames(dtl.getSWK_TRI_NAME_S()); // 取引先
		panel.ctrlEmployee.setCode(dtl.getSWK_EMP_CODE()); // 社員コード
		panel.ctrlEmployee.setNames(dtl.getSWK_EMP_NAME_S()); // 社員
		panel.ctrlManage1.setCode(dtl.getSWK_KNR_CODE_1()); // 管理1コード
		panel.ctrlManage1.setNames(dtl.getSWK_KNR_NAME_S_1()); // 管理1
		panel.ctrlManage2.setCode(dtl.getSWK_KNR_CODE_2()); // 管理2コード
		panel.ctrlManage2.setNames(dtl.getSWK_KNR_NAME_S_2()); // 管理2
		panel.ctrlManage3.setCode(dtl.getSWK_KNR_CODE_3()); // 管理3コード
		panel.ctrlManage3.setNames(dtl.getSWK_KNR_NAME_S_3()); // 管理3
		panel.ctrlManage4.setCode(dtl.getSWK_KNR_CODE_4()); // 管理4コード
		panel.ctrlManage4.setNames(dtl.getSWK_KNR_NAME_S_4()); // 管理4
		panel.ctrlManage5.setCode(dtl.getSWK_KNR_CODE_5()); // 管理5コード
		panel.ctrlManage5.setNames(dtl.getSWK_KNR_NAME_S_5()); // 管理5
		panel.ctrlManage6.setCode(dtl.getSWK_KNR_CODE_6()); // 管理6コード
		panel.ctrlManage6.setNames(dtl.getSWK_KNR_NAME_S_6()); // 管理6
		panel.ctrlOccurDate.setValue(getOccurDate(dtl)); // 発生日
		panel.ctrlNonAcDetails.setValue(1, dtl.getSWK_HM_1()); // 非会計明細1
		panel.ctrlNonAcDetails.setValue(2, dtl.getSWK_HM_2()); // 非会計明細2
		panel.ctrlNonAcDetails.setValue(3, dtl.getSWK_HM_3()); // 非会計明細3

		this.entity = dtl;
		if (dtl.getAR_ZAN() != null) {
			panel.lblTitle.setText("債権消込");
		} else if (dtl.isAPErasing()) {
			panel.lblTitle.setText("債務消込");
		} else if (dtl.isBSErasing()) {
			panel.lblTitle.setText("BS勘定消込");
		}
	}

	/**
	 * 画面へ貼り付ける時発生日の値
	 * 
	 * @param dtl
	 * @return 発生日
	 */
	protected Date getOccurDate(SWK_DTL dtl) {

		if (isAllowOccurDateBlank()) {

			if (dtl.getItem() != null && !dtl.isUseItemOccurDate()) {
				// 科目が発生日未使用の場合、発生日はブランクにする
				return null;
			}
		}

		return dtl.getHAS_DATE();
	}

	/**
	 * @return 明細仕訳
	 */
	protected SWK_DTL createSlipDetail() {
		return new SWK_DTL();
	}

	/**
	 * 明細をEntity形式で返す.
	 * 
	 * @return Entity
	 */
	protected SWK_DTL getEntity() {

		int dc = DR;

		if (!panel.ctrlItemDebit.ctrlItemReference.code.isEmpty()) {
			// 借
			dc = DR;

		} else if (!panel.ctrlItemCredit.ctrlItemReference.code.isEmpty()) {
			// 貸
			dc = CR;

		} else {
			// なし
			return null;
		}

		SWK_DTL dtl = this.entity;
		if (dtl == null) {
			dtl = createSlipDetail();
		}

		dtl.setDC(Dc.getDc(dc));

		dtl.setAppropriateCompany(panel.ctrlKCompany[dc].getEntity()); // 計上会社
		dtl.setDepartment(panel.ctrlKDepartment[dc].getEntity()); // 計上部門
		dtl.setItem(panel.ctrlItem[dc].getInputtedEntity()); // 科目
		dtl.setTax(panel.ctrlTax[dc].getEntity()); // 税
		dtl.setCurrency((Currency) panel.ctrlCurrency[dc].getSelectedItemValue()); // 通貨

		// 税
		ConsumptionTax tax = dtl.getTax();
		if (tax == null || tax.getTaxType() == TaxType.NOT || DecimalUtil.isNullOrZero(tax.getRate())) {
			dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		} else {
			dtl.setSWK_ZEI_KBN(((TaxCalcType) panel.ctrlTaxDivision[dc].getSelectedItemValue()).value); // 税
		}

		dtl.setSWK_CUR_RATE(panel.ctrlRate[dc].getBigDecimal()); // 通貨レート
		dtl.setSWK_IN_KIN(panel.ctrlInputAmount[dc].getBigDecimal()); // 入力金額

		BigDecimal inTaxAmount = panel.ctrlTaxAmount[dc].getBigDecimal(); // 入力消費税
		dtl.setSWK_IN_ZEI_KIN(inTaxAmount); // 入力消費税額

		dtl.setSWK_ZEI_KIN(convertKeyTaxAmount(inTaxAmount, dc)); // 消費税額(邦貨)
		dtl.setSWK_KIN(panel.ctrlKeyAmount[dc].getBigDecimal()); // 基軸金額

		dtl.setSWK_GYO_TEK_CODE(panel.ctrlRemark.getCode()); // 行摘要コード
		dtl.setSWK_GYO_TEK(panel.ctrlRemark.getNames()); // 行摘要
		dtl.setSWK_TRI_CODE(panel.ctrlCustomer.getCode()); // 取引先コード
		dtl.setSWK_TRI_NAME(panel.ctrlCustomer.getEntity() == null ? "" : panel.ctrlCustomer.getEntity().getName());
		dtl.setSWK_TRI_NAME_S(panel.ctrlCustomer.getNames());
		dtl.setSWK_EMP_CODE(panel.ctrlEmployee.getCode()); // 社員コード
		dtl.setSWK_EMP_NAME(panel.ctrlEmployee.getEntity() == null ? "" : panel.ctrlEmployee.getEntity().getName());
		dtl.setSWK_EMP_NAME_S(panel.ctrlEmployee.getNames());
		dtl.setSWK_KNR_CODE_1(panel.ctrlManage1.getCode()); // 管理1コード
		dtl.setSWK_KNR_NAME_1(panel.ctrlManage1.getEntity() == null ? "" : panel.ctrlManage1.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_1(panel.ctrlManage1.getNames());
		dtl.setSWK_KNR_CODE_2(panel.ctrlManage2.getCode()); // 管理2コード
		dtl.setSWK_KNR_NAME_2(panel.ctrlManage2.getEntity() == null ? "" : panel.ctrlManage2.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_2(panel.ctrlManage2.getNames());
		dtl.setSWK_KNR_CODE_3(panel.ctrlManage3.getCode()); // 管理3コード
		dtl.setSWK_KNR_NAME_3(panel.ctrlManage3.getEntity() == null ? "" : panel.ctrlManage3.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_3(panel.ctrlManage3.getNames());
		dtl.setSWK_KNR_CODE_4(panel.ctrlManage4.getCode()); // 管理4コード
		dtl.setSWK_KNR_NAME_4(panel.ctrlManage4.getEntity() == null ? "" : panel.ctrlManage4.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_4(panel.ctrlManage4.getNames());
		dtl.setSWK_KNR_CODE_5(panel.ctrlManage5.getCode()); // 管理5コード
		dtl.setSWK_KNR_NAME_5(panel.ctrlManage5.getEntity() == null ? "" : panel.ctrlManage5.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_5(panel.ctrlManage5.getNames());
		dtl.setSWK_KNR_CODE_6(panel.ctrlManage6.getCode()); // 管理6コード
		dtl.setSWK_KNR_NAME_6(panel.ctrlManage6.getEntity() == null ? "" : panel.ctrlManage6.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_6(panel.ctrlManage6.getNames());
		dtl.setSWK_HM_1(panel.ctrlNonAcDetails.getValue(1)); // 非会計明細1
		dtl.setSWK_HM_2(panel.ctrlNonAcDetails.getValue(2)); // 非会計明細2
		dtl.setSWK_HM_3(panel.ctrlNonAcDetails.getValue(3)); // 非会計明細3

		// 発生日
		Date occurDate = panel.ctrlOccurDate.getValue();
		dtl.setHAS_DATE(occurDate); // 発生日

		return dtl;
	}

	/**
	 * 入力値チェック
	 * 
	 * @return false:NG\
	 */
	protected boolean checkInput() {

		int dc = DR;

		if (!panel.ctrlItem[DR].ctrlItemReference.code.isEmpty()) {
			dc = DR;

		} else if (!panel.ctrlItem[CR].ctrlItemReference.code.isEmpty()) {
			dc = CR;

		} else {
			return true;
		}

		// 計上部門
		if (!checkInputBlank(panel.ctrlKDepartment[dc].code, panel.ctrlKDepartment[dc].btn.getText())) {
			return false;
		}

		// 科目
		TItemGroup item = panel.ctrlItem[dc];
		if (!checkInputBlank(item.ctrlItemReference.code, item.ctrlItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlSubItemReference.code, item.ctrlSubItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlDetailItemReference.code, item.ctrlDetailItemReference.btn.getText())) {
			return false;
		}

		Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();
		if (currency == null) {
			currency = keyCurrency;
		}

		// 発生日(ブランク可能の場合、チェック不要)
		boolean checkOccurDate = !isAllowOccurDateBlank();
		if (item.getEntity() != null) {
			Item itemBean = item.getEntity().getPreferedItem();
			if (itemBean.isUseOccurDate() && !Util.isNullOrEmpty(currency.getCode())
				&& !keyCurrency.getCode().equals(currency.getCode())) {
				// ブランク可能、且つ基軸通貨以外の場合は科目のフラグに従ってチェック
				checkOccurDate = true;
			}
		}

		if (checkOccurDate
			&& !checkInputBlank(panel.ctrlOccurDate.getCalendar(), panel.ctrlOccurDate.getLabel().getText())) {
			return false;
		}

		// レート
		if (!checkInputBlank(panel.ctrlRate[dc].getField(), panel.ctrlRate[dc].getLabel().getText())) {
			return false;
		}

		// 税区分
		if (!checkInputBlank(panel.ctrlTax[dc].code, panel.ctrlTax[dc].btn.getText())) {
			return false;
		}

		// 入力金額
		BigDecimal inKin = panel.ctrlInputAmount[dc].getBigDecimal();

		// 基軸通貨と異なる通貨の場合は、0を認める.
		if (keyCurrency.getCode().equals(currency.getCode())) {
			if (DecimalUtil.isZero(inKin)) {
				showMessage("I00037", "C00574");
				panel.ctrlInputAmount[dc].requestFocus();
				return false;
			}
		}

		// 消費税額 (0でもOK)
		BigDecimal taxInKin = panel.ctrlTaxAmount[dc].getBigDecimal();
		if (!DecimalUtil.isZero(taxInKin)) {

			if (!DecimalUtil.isZero(inKin) && inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// 入力金額と消費税額の符号が異なります。
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// 消費税額は入力金額未満である必要があります。
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}
		}

		// 邦貨金額
		BigDecimal kin = panel.ctrlKeyAmount[dc].getBigDecimal();
		if (DecimalUtil.isZero(kin)) {
			showMessage("I00037", "C00576");// {0}を入力してください。
			panel.ctrlKeyAmount[dc].requestFocus();

			return false;
		}

		if (!DecimalUtil.isZero(inKin) && inKin.signum() != kin.signum()) {
			showMessage("I00125");// 入力金額と邦貨金額の符号が異なります。
			panel.ctrlKeyAmount[dc].requestFocus();
			return false;
		}

		// 取引先
		if (!checkInputBlank(panel.ctrlCustomer.code, panel.ctrlCustomer.btn.getText())) {
			return false;
		}
		// 社員
		if (!checkInputBlank(panel.ctrlEmployee.code, panel.ctrlEmployee.btn.getText())) {
			return false;
		}
		// 管理1～6
		if (!checkInputBlank(panel.ctrlManage1.code, panel.ctrlManage1.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage2.code, panel.ctrlManage2.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage3.code, panel.ctrlManage3.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage4.code, panel.ctrlManage4.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage5.code, panel.ctrlManage5.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage6.code, panel.ctrlManage6.btn.getText())) {
			return false;
		}

		return true;
	}

	/**
	 * 入力ブランクチェック
	 * 
	 * @param field 対象フィールド
	 * @param name エラー時の表示名
	 * @return false:NG
	 */
	protected boolean checkInputBlank(TTextField field, String name) {

		if (field.isVisible() && field.isEditable() && field.isEmpty()) {
			showMessage("{0}を入力してください。", name);
			field.requestFocusInWindow();
			return false;
		}

		return true;
	}

	/**
	 * 画面入力情報を元に基軸消費税額に換算
	 * 
	 * @param taxAmount 入力消費税額
	 * @param dc 貸借
	 * @return 基軸通貨消費税額
	 */
	protected BigDecimal convertKeyTaxAmount(BigDecimal taxAmount, int dc) {

		if (DecimalUtil.isNullOrZero(taxAmount)) {
			return BigDecimal.ZERO;
		}

		Company kcompany = getCompany();

		Currency currency;
		ConsumptionTax tax;
		BigDecimal rate;

		currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();
		tax = panel.ctrlTax[dc].getEntity();
		rate = panel.ctrlRate[dc].getBigDecimal();

		if (kcompany == null || currency == null || tax == null) {
			return BigDecimal.ZERO;
		}

		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(taxAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * 画面入力情報を元に基軸金額に換算
	 * 
	 * @param amount 入力金額
	 * @param currency 通貨
	 * @param rate 通貨レート
	 * @return 基軸通貨金額
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount, Currency currency, BigDecimal rate) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		Company kcompany = getCompany();

		if ((kcompany == null) || (currency == null)) {
			return BigDecimal.ZERO;
		}

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(amount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * デフォルトが内税か
	 * 
	 * @param defaultTaxInnerDivision true:内税
	 */
	protected void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {

		this.defaultTaxInnerDivision = defaultTaxInnerDivision;

		panel.ctrlTaxDivisionDebit.setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);
		panel.ctrlTaxDivisionCredit.setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);

		changedTaxDivision(DR);
		changedTaxDivision(CR);
	}

	/**
	 * 通貨レート取得
	 * 
	 * @param dc 貸借
	 * @return レート
	 */
	protected BigDecimal getCurrencyRate(int dc) {
		try {

			Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}

			Date date = panel.ctrlOccurDate.getValue();

			if (date == null) {
				date = baseDate;
			}

			if (date == null) {
				return null;
			}

			return (BigDecimal) request(RateManager.class, isClosing ? "getSettlementRate" : "getRate", currency, date);

		} catch (TException ex) {
			errorHandler(ex);
		}
		return null;
	}

	/**
	 * 合計表示
	 */
	protected void doAfterEvent() {

		if (callBackListenerList != null && !callBackListenerList.isEmpty()) {

			for (TCallBackListener listener : callBackListenerList) {

				listener.after();
			}
		}
	}

	/**
	 * 借方邦貨金額を取得する
	 * 
	 * @return 借方邦貨金額
	 */
	protected BigDecimal getDebitKeyAmount() {
		return panel.ctrlKeyAmountDebit.getBigDecimal();
	}

	/**
	 * 貸方邦貨金額を取得する
	 * 
	 * @return 貸方邦貨金額
	 */
	protected BigDecimal getCreditKeyAmount() {
		return panel.ctrlKeyAmountCredit.getBigDecimal();
	}

	/**
	 * 借方邦貨消費税額を取得する
	 * 
	 * @return 借方邦貨消費税額
	 */
	protected BigDecimal getDebitTaxAmount() {
		return convertKeyTaxAmount(panel.ctrlTaxAmountDebit.getBigDecimal(), DR);
	}

	/**
	 * 貸方邦貨消費税額を取得する
	 * 
	 * @return 貸方邦貨消費税額
	 */
	protected BigDecimal getCreditTaxAmount() {
		return convertKeyTaxAmount(panel.ctrlTaxAmountCredit.getBigDecimal(), CR);
	}

	/**
	 * 借方入力消費税額を取得する
	 * 
	 * @return 借方邦貨消費税額
	 */
	protected BigDecimal getDebitTaxInputAmount() {
		return panel.ctrlTaxAmountDebit.getBigDecimal();
	}

	/**
	 * 貸方入力消費税額を取得する
	 * 
	 * @return 貸方邦貨消費税額
	 */
	protected BigDecimal getCreditTaxInputAmount() {
		return panel.ctrlTaxAmountCredit.getBigDecimal();
	}

	/**
	 * 借方入力金額を取得する
	 * 
	 * @return 借方入力金額
	 */
	protected BigDecimal getDebitInputAmount() {
		return panel.ctrlInputAmountDebit.getBigDecimal();
	}

	/**
	 * 貸方入力金額を取得する
	 * 
	 * @return 貸方入力金額
	 */
	protected BigDecimal getCreditInputAmount() {
		return panel.ctrlInputAmountCredit.getBigDecimal();
	}

	/**
	 * 借方通貨コードを取得する
	 * 
	 * @return 借方通貨コード
	 */
	protected Currency getDebitCurrency() {
		return (Currency) panel.ctrlCurrencyDebit.getSelectedItemValue();
	}

	/**
	 * 貸方通貨コードを取得する
	 * 
	 * @return 貸方通貨コード
	 */
	protected Currency getCreditCurrency() {
		return (Currency) panel.ctrlCurrencyCredit.getSelectedItemValue();
	}

	/**
	 * 借方消費税額を含めるか
	 * 
	 * @return 含める:true
	 */
	protected boolean isDebitTaxInclusive() {
		return panel.ctrlTaxDivisionDebit.getSelectedItemValue().equals(TaxCalcType.IN);
	}

	/**
	 * 貸方消費税額を含めるか
	 * 
	 * @return 含める:true
	 */
	protected boolean isCreditTaxInclusive() {
		return panel.ctrlTaxDivisionCredit.getSelectedItemValue().equals(TaxCalcType.IN);
	}

	/**
	 * callBackListenerを追加する
	 * 
	 * @param callBackListener callBackListener
	 */
	protected void addCallBackListener(TCallBackListener callBackListener) {

		if (callBackListenerList == null) {

			callBackListenerList = new ArrayList<TCallBackListener>();
		}

		callBackListenerList.add(callBackListener);
	}

	/**
	 * 明細データの通貨リスト作成
	 * 
	 * @param list 通貨リスト
	 */
	public void setCurrecyList(List<Currency> list) {

		if (list == null) {
			return;
		}

		panel.ctrlCurrencyDebit.removeAllItems();
		panel.ctrlCurrencyCredit.removeAllItems();

		for (Currency bean : list) {

			panel.ctrlCurrencyDebit.addTextValueItem(bean, bean.getCode());
			panel.ctrlCurrencyCredit.addTextValueItem(bean, bean.getCode());
		}

		panel.ctrlCurrencyDebit.setSelectedText(keyCurrency.getCode());
		panel.ctrlCurrencyCredit.setSelectedText(keyCurrency.getCode());
	}
}