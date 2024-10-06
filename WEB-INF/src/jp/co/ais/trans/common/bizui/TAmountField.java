package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * 金額フィールド.<br>
 * 指定された通貨コードに対する小数点桁数を自動で取得し、フォーマット設定を行う.
 */
public class TAmountField extends TNumericField {

	/** 通貨コード */
	private String currencyCode;

	/** 小数点桁数 */
	private int digit = 0;

	/**
	 * コンストラクタ.<br>
	 * ログインユーザの会社に紐づく通貨コードを自動で設定
	 */
	public TAmountField() {
		this(TClientLoginInfo.getInstance().getCompanyInfo().getBaseCurrencyCode());
	}

	/**
	 * コンストラクタ
	 * 
	 * @param currencyCode 通貨コード
	 */
	public TAmountField(String currencyCode) {
		super();

		this.currencyCode = currencyCode;
		setupDigit();

		super.setPositiveOnly(true); // 正数のみ
		super.setMaxLength(17);
	}

	/**
	 * 通貨コードに対する小数点桁数を設定
	 */
	private void setupDigit() {

		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		this.digit = compInfo.getCurrencyDigit(this.currencyCode);

		super.setNumericFormat(NumberFormatUtil.makeNumberFormat(digit));
	}

	/**
	 * 通貨コード
	 * 
	 * @return 通貨コード
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 通貨コード
	 * 
	 * @param currencyCode 通貨コード
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		setupDigit();
	}

	/**
	 * 小数点桁数
	 * 
	 * @return 小数点桁数
	 */
	public int getDigit() {
		return digit;
	}
}
