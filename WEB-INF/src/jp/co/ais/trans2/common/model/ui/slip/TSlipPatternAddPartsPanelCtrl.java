package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 金額入力ヘッダ付きパターンパネルコントローラ
 */
public abstract class TSlipPatternAddPartsPanelCtrl extends TSlipPatternPanelCtrl {

	/** 指示画面 */
	@SuppressWarnings("hiding")
	protected TSlipPatternAddPartsPanel mainView = (TSlipPatternAddPartsPanel) super.mainView;

	/** ヘッダー明細行 */
	protected SWK_DTL headerDetail;

	/**
	 * 画面ヘッダの初期設定
	 */
	@Override
	protected void initHeaderView() {
		super.initHeaderView();

		// ヘッダ用明細
		headerDetail = createHeaderDetail();

		if (isDefaultBlankDepartment()) {
			// 計上部門の初期値がnullの場合、ユーザ情報の設定不要
			setDepartment(null);
		} else {
			// デフォルト計上部門
			setDepartment(getUser().getDepartment());
		}

		mainView.ctrlItem.getSearchCondition().setDepartmentCode(getUser().getDepartment().getCode());

		// デフォルト通貨コード
		setCurrecy(keyCurrency);

		// 基軸通貨桁数
		int digit = keyCurrency.getDecimalPoint();
		mainView.ctrlKeyAmount.setDecimalPoint(digit);

		// ヘッダー明細行追加
		mainView.ctrlDetail.setOutherDetail(headerDetail);
	}

	/**
	 * ヘッダー明細行作成
	 * 
	 * @return ヘッダー明細行
	 */
	protected SWK_DTL createHeaderDetail() {
		SWK_DTL dtl = new SWK_DTL();

		dtl.setAppropriateCompany(getCompany()); // 計上会社
		dtl.setCurrency(getCompany().getAccountConfig().getKeyCurrency()); // 通貨
		dtl.setSWK_CUR_RATE(BigDecimal.ONE); // レート1
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // 非課税
		dtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO); // 自動仕訳

		switch (SlipKind.get(slipType.getDataType())) {
			case EMPLOYEE:
			case OUTPUT_CASH_FLOW:
			case PURCHASE:
				dtl.setDC(Dc.CREDIT); // 貸
				break;

			case INPUT_CASH_FLOW:
			case SALES:
			default:
				dtl.setDC(Dc.DEBIT); // 借
				break;
		}

		return dtl;
	}

	/**
	 * 画面明細の初期設定
	 */
	@Override
	protected void initDetailView() {
		super.initDetailView();

		// 貸借のデフォルト値
		switch (SlipKind.get(slipType.getDataType())) {
			case INPUT_CASH_FLOW:
			case SALES:
				mainView.ctrlDetail.setDefaultDC(Dc.CREDIT); // 貸
				break;

			case EMPLOYEE:
			case OUTPUT_CASH_FLOW:
			case PURCHASE:
			default:
				mainView.ctrlDetail.setDefaultDC(Dc.DEBIT); // 借
				break;
		}
	}

	/**
	 * 指示画面のイベント定義。
	 */
	@Override
	protected void addViewEvent() {
		super.addViewEvent();

		// 計上部門
		mainView.ctrlDepartment.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				changedDepartment();
			}
		});

		// 科目
		mainView.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				changedItem();
			}
		});

		// 通貨
		mainView.ctrlCurrency.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				changedCurrency();
			}
		});

		// 通貨レート
		mainView.ctrlRate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!mainView.ctrlRate.isValueChanged2()) {
					return true;
				}

				changedRate();
				return true;
			}
		});

		// 入力金額
		mainView.ctrlInputAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!mainView.ctrlInputAmount.isValueChanged2()) {
					return true;
				}

				changedInputAmount();
				return true;
			}
		});

		// 基軸金額
		mainView.ctrlKeyAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!mainView.ctrlKeyAmount.isValueChanged2()) {
					return true;
				}

				changedKeyAmount();
				return true;
			}
		});
	}

	/**
	 * ヘッダー項目のチェック
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	@Override
	protected boolean checkHeaderInput() throws TException {
		if (!super.checkHeaderInput()) {
			return false;
		}

		// 金額関係のチェック
		if (!checkHeaderAmountInput()) {
			return false;
		}

		return true;
	}

	/**
	 * ヘッダー金額項目の入力チェック
	 * 
	 * @return false:NG
	 */
	protected boolean checkHeaderAmountInput() {

		// 入力金額
		BigDecimal inKin = mainView.ctrlInputAmount.getBigDecimal();

		// 邦貨金額
		BigDecimal kin = mainView.ctrlKeyAmount.getBigDecimal();

		if (!DecimalUtil.isZero(inKin) && !DecimalUtil.isZero(kin) && inKin.signum() != kin.signum()) {
			showMessage("I00125");// 入力金額と邦貨金額の符号が異なります。
			mainView.ctrlKeyAmount.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 値クリア
	 */
	@Override
	protected void clearComponents() {
		super.clearComponents();

		mainView.ctrlDepartment.clear();
		mainView.ctrlItem.clear();
		changedItem();

		mainView.ctrlEvidenceNo.clear();
		mainView.ctrlCurrency.clear();
		mainView.ctrlRate.clear();
		mainView.ctrlInputAmount.clear();
		mainView.ctrlKeyAmount.clear();
		changedKeyAmount();

		if (isDefaultBlankDepartment()) {
			// 計上部門の初期値がnullの場合、ユーザ情報の設定不要
			setDepartment(null);
		} else {
			// デフォルト計上部門
			setDepartment(getUser().getDepartment());
		}

		// デフォルト通貨
		setCurrecy(keyCurrency);

		mainView.ctrlCurrency.setEditable(false);
		mainView.ctrlRate.setEditable(false);
		mainView.ctrlKeyAmount.setEditable(false);

		// 明細クリア
		headerDetail = createHeaderDetail();
		mainView.ctrlDetail.setOutherDetail(headerDetail);
	}

	/**
	 * @return true:計上部門初期ブランク
	 */
	protected boolean isDefaultBlankDepartment() {
		return TSlipDetailPanelCtrl.departmentDefaultBlank;
	}

	/**
	 * 画面反映
	 */
	@Override
	protected void dispatch() {
		super.dispatch();

		// ヘッダ差分
		SWK_HDR hdr = slip.getHeader();

		// 計上部門
		setDepartment(hdr.getDepartment());

		// 債務科目
		mainView.ctrlItem.setEntity(hdr.getItem());
		changedItem();

		// 通貨コード
		setCurrecy(hdr.getCurrency());

		mainView.ctrlEvidenceNo.setValue(hdr.getSWK_SEI_NO()); // 請求書No.
		mainView.ctrlRate.setNumber(hdr.getSWK_CUR_RATE()); // レート
		mainView.ctrlInputAmount.setNumber(hdr.getSWK_IN_KIN()); // 外貨
		mainView.ctrlKeyAmount.setNumber(hdr.getSWK_KIN()); // 邦貨

		// 明細へ反映
		dispatchHeaderDetail(hdr);

		changedKeyAmount();
	}

	/**
	 * ヘッダ情報をヘッダ用明細に転記
	 * 
	 * @param hdr ヘッダ
	 */
	protected void dispatchHeaderDetail(SWK_HDR hdr) {
		// 明細
		headerDetail.setSWK_ZEI_KBN(2);// 税区分
		headerDetail.setSWK_ZEI_KIN(BigDecimal.ZERO);// 消費税額
		headerDetail.setSWK_ZEI_RATE(BigDecimal.ZERO);// 税率
		headerDetail.setSWK_GYO_TEK(hdr.getSWK_TEK());// 行摘要
		headerDetail.setSWK_K_KAI_CODE(getCompanyCode());// 計上会社ｺｰﾄﾞ
		headerDetail.setSWK_TUKE_KBN(0);// 会社間付替伝票区分
		headerDetail.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);// 入力消費税額
		headerDetail.setSWK_KESI_KBN(0);// 消込区分
		headerDetail.setDepartment(hdr.getDepartment()); // 計上部門
		headerDetail.setItem(hdr.getItem()); // 債務科目
		headerDetail.setSWK_SEI_NO(hdr.getSWK_SEI_NO()); // 請求書No.
		headerDetail.setCurrency(hdr.getCurrency()); // 通貨コード
		headerDetail.setSWK_CUR_RATE(hdr.getSWK_CUR_RATE()); // レート
		headerDetail.setSWK_IN_KIN(hdr.getSWK_IN_KIN()); // 外貨
		headerDetail.setSWK_KIN(hdr.getSWK_KIN()); // 邦貨
		headerDetail.setHAS_DATE(hdr.getSWK_DEN_DATE()); // 発生日（伝票日付）
	}

	/**
	 * 画面入力の反映(ヘッダ)
	 * 
	 * @param hdr ヘッダ
	 */
	@Override
	protected void reflectHeader(SWK_HDR hdr) {
		super.reflectHeader(hdr);

		hdr.setDepartment(mainView.ctrlDepartment.getEntity()); // 計上部門
		hdr.setItem(mainView.ctrlItem.getEntity()); // 債務科目

		hdr.setSWK_SEI_NO(mainView.ctrlEvidenceNo.getValue()); // 請求書No.
		hdr.setCurrency(mainView.ctrlCurrency.getEntity()); // 通貨コード
		hdr.setSWK_CUR_RATE(mainView.ctrlRate.getBigDecimal()); // レート
		hdr.setSWK_IN_KIN(mainView.ctrlInputAmount.getBigDecimal()); // 外貨
		hdr.setSWK_KIN(mainView.ctrlKeyAmount.getBigDecimal()); // 邦貨

		// 明細
		headerDetail.setSWK_ZEI_KBN(2);// 税区分
		headerDetail.setSWK_ZEI_KIN(BigDecimal.ZERO);// 消費税額
		headerDetail.setSWK_ZEI_RATE(BigDecimal.ZERO);// 税率
		headerDetail.setSWK_GYO_TEK(hdr.getSWK_TEK());// 行摘要
		headerDetail.setSWK_K_KAI_CODE(getCompanyCode());// 計上会社ｺｰﾄﾞ
		headerDetail.setSWK_TUKE_KBN(Slip.TUKE_KBN.NOMAL);// 会社間付替伝票区分(通常)
		headerDetail.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);// 入力消費税額
		headerDetail.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);// 消込区分(通常)
		headerDetail.setDepartment(mainView.ctrlDepartment.getEntity()); // 計上部門
		headerDetail.setItem(mainView.ctrlItem.getEntity()); // 債務科目
		headerDetail.setSWK_SEI_NO(mainView.ctrlEvidenceNo.getValue()); // 請求書No.
		headerDetail.setCurrency(mainView.ctrlCurrency.getEntity()); // 通貨コード
		headerDetail.setSWK_CUR_RATE(mainView.ctrlRate.getBigDecimal()); // レート
		headerDetail.setSWK_IN_KIN(mainView.ctrlInputAmount.getBigDecimal()); // 外貨
		headerDetail.setSWK_KIN(mainView.ctrlKeyAmount.getBigDecimal()); // 邦貨
		headerDetail.setHAS_DATE(mainView.ctrlSlipDate.getValue());// 発生日（伝票日付を指定）
	}

	/**
	 * 画面入力の反映(明細)
	 */
	@Override
	protected void reflectDetails() {
		SWK_HDR hdr = slip.getHeader();
		String slipRemarks = hdr.getSWK_TEK();

		slip.clearDetail();

		for (SWK_DTL dtl : mainView.ctrlDetail.getEntityList()) {
			if (this.headerDetail.equals(dtl)) {
				// ヘッダー明細は除外
				continue;
			}

			SWK_DTL cdtl = dtl.clone();

			// 内税の場合、金額の編集
			if (cdtl.isTaxInclusive()) {
				cdtl.setSWK_IN_KIN(cdtl.getSWK_IN_KIN().subtract(cdtl.getSWK_IN_ZEI_KIN()));
				cdtl.setSWK_KIN(cdtl.getSWK_KIN().subtract(cdtl.getSWK_ZEI_KIN()));
			}

			// 行摘要未設定の場合は、伝票摘要をセット
			if (Util.isNullOrEmpty(cdtl.getSWK_GYO_TEK())) {
				cdtl.setSWK_GYO_TEK(slipRemarks);
			}

			slip.addDetail(cdtl);
		}
	}

	/**
	 * 計上部門変更
	 */
	protected void changedDepartment() {
		Department dept = mainView.ctrlDepartment.getEntity();
		setDepartment(dept);

		if (dept == null) {
			return;
		}

		// 科目整合性チェック
		String nowCode = dept.getCode();
		String oldCode = mainView.ctrlItem.getSearchCondition().getDepartmentCode();

		if (!nowCode.equals(Util.avoidNull(oldCode))) {
			// 部門コードが変更になった場合、条件を変更
			mainView.ctrlItem.getSearchCondition().setDepartmentCode(nowCode);

			// 条件変更により、整合性チェックでOKなら残す
			mainView.ctrlItem.ctrlItemReference.refleshEntity();
			changedItem();
		}
	}

	/**
	 * 計上部門設定
	 * 
	 * @param dept 計上部門
	 */
	protected void setDepartment(Department dept) {
		mainView.ctrlDepartment.setEntity(dept);

		// 明細反映
		headerDetail.setDepartment(dept);

		if (dept == null) {
			mainView.ctrlItem.setEntity(null);
			mainView.ctrlItem.ctrlItemReference.setEditable(false);
			changedItem();

			return;
		}

		// 科目初期化
		mainView.ctrlItem.ctrlItemReference.setEditable(true);
	}

	/**
	 * 科目変更
	 */
	protected void changedItem() {
		Item item = mainView.ctrlItem.getEntity();

		// 明細反映
		headerDetail.setItem(item);

		if (item == null) {
			clearInputForItem();
			changedCurrency();

			// 通貨初期値
			setCurrecy(keyCurrency);
			mainView.ctrlCurrency.setEditable(false);
			return;
		}

		// 補助がある
		if (item.getSubItem() != null) {
			item = item.getSubItem();
		}
		// 内訳がある
		if (item.getDetailItem() != null) {
			item = item.getDetailItem();
		}

		// 多通貨入力フラグ
		mainView.ctrlCurrency.setEditable(item.isUseForeignCurrency());
		if (!item.isUseForeignCurrency()) {
			setCurrecy(keyCurrency);
			changedCurrency();
		}
	}

	/**
	 * 科目関連入力部のみ初期状態
	 */
	public void clearInputForItem() {
		// クリア
		mainView.ctrlCurrency.clear();
		mainView.ctrlRate.clear();
		mainView.ctrlKeyAmount.clear();

		// 入力制御
		mainView.ctrlCurrency.setEditable(false);
		mainView.ctrlRate.setEditable(false);
		mainView.ctrlKeyAmount.setEditable(false);

		// 初期値
		setCurrecy(keyCurrency);
	}

	/**
	 * 通貨変更
	 */
	protected void changedCurrency() {
		Currency currency = mainView.ctrlCurrency.getEntity();
		setCurrecy(currency);

		if (currency == null) {
			return;
		}

		// レート
		changedRate();
	}

	/**
	 * 通貨設定
	 * 
	 * @param currency 通貨
	 */
	protected void setCurrecy(Currency currency) {
		mainView.ctrlCurrency.setEntity(currency);

		// 明細反映
		headerDetail.setCurrency(currency);
		mainView.ctrlDetail.makeCurrencyComboBox();

		if (currency == null) {
			mainView.ctrlRate.clear();
			mainView.ctrlRate.setEditable(false);
			mainView.ctrlKeyAmount.clear();
			mainView.ctrlKeyAmount.setEditable(false);
			changedKeyAmount();

			return;
		}

		// 入力制御
		boolean isKey = keyCurrency.getCode().equals(currency.getCode());
		mainView.ctrlRate.setEditable(!isKey);
		mainView.ctrlKeyAmount.setEditable(!isKey);

		// レート
		mainView.ctrlRate.setNumberValue(isKey ? BigDecimal.ONE : null);
		changedRate();

		// 小数点変更
		int digit = currency.getDecimalPoint();
		mainView.ctrlInputAmount.setDecimalPoint(digit);
	}

	/**
	 * 通貨レート 変更
	 */
	protected void changedRate() {
		// 明細反映
		headerDetail.setSWK_CUR_RATE(mainView.ctrlRate.getBigDecimal());

		// 邦貨換算
		Currency currency = mainView.ctrlCurrency.getEntity();

		if (currency == null || mainView.ctrlInputAmount.isEmpty()) {
			return;
		}

		BigDecimal inAmount = mainView.ctrlInputAmount.getBigDecimal();
		mainView.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

		changedKeyAmount();
	}

	/**
	 * 入力金額の変更
	 */
	protected void changedInputAmount() {
		// 邦貨換算
		BigDecimal inAmount = mainView.ctrlInputAmount.getBigDecimal();
		mainView.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

		// 明細反映
		headerDetail.setSWK_IN_KIN(inAmount);

		changedKeyAmount();
	}

	/**
	 * 基軸金額の変更
	 */
	protected void changedKeyAmount() {
		// 明細反映
		headerDetail.setSWK_KIN(mainView.ctrlKeyAmount.getBigDecimal());

		summary();
	}

	/**
	 * 画面入力情報を元に基軸金額に換算
	 * 
	 * @param inAmount 入力金額
	 * @return 基軸通貨金額
	 */
	protected BigDecimal convertKeyAmount(BigDecimal inAmount) {

		if (DecimalUtil.isNullOrZero(inAmount)) {
			return BigDecimal.ZERO;
		}

		BigDecimal rate = mainView.ctrlRate.getBigDecimal();
		Currency currency = mainView.ctrlCurrency.getEntity();

		if (currency == null || DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(conf.getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(inAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * 邦貨金額を外貨金額に換算
	 * 
	 * @param keyAmount 邦貨金額
	 * @return 外貨金額
	 */
	protected BigDecimal convertForeignAmount(BigDecimal keyAmount) {

		if (DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		BigDecimal rate = mainView.ctrlRate.getBigDecimal();
		Currency currency = mainView.ctrlCurrency.getEntity();

		if (currency == null || DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(conf.getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(currency.getDecimalPoint());
		param.setKeyAmount(keyAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeForeignAmount(param);
	}

	/**
	 * ヘッダの基準日を返す
	 * 
	 * @return ヘッダの基準日
	 */
	protected Date getBaseDate() {
		return Util.getCurrentDate();
	}

}
