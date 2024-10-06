package jp.co.ais.trans.common.bizui;

import java.util.*;

import jp.co.ais.trans.common.bizui.ctrl.*;

/**
 * 支払条件フィールド拡張 デフォルトの支払条件表示機能を追加
 * 
 * @author ookawara
 */

public class TPaymentConditionEnhancingField extends TPaymentConditionField {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** 通貨コード */
	private String curCode;

	/** 伝票日付 */
	private Date slipDate;

	/** 結果用BEANクラス */
	private PaymentInformationParameter param;
	
	/** 有効期限チェックフラグ */
	private boolean isTermBasisDate = true;

	/**
	 * コンストラクタ
	 */
	public TPaymentConditionEnhancingField() {
		super();
		this.ctrl = new TPaymentConditionEnhancingFieldCtrl(this);
		super.ctrl = this.ctrl;
		param = new PaymentInformationParameter();

	}

	/**
	 * 通貨コードを設定
	 * 
	 * @param curCode
	 */
	public void setCurrencyCode(String curCode) {
		this.curCode = curCode;
	}

	/**
	 * 通貨コードを返す
	 * 
	 * @return curCode 通貨コード
	 */
	public String getCurrencyCode() {
		return this.curCode;
	}

	/**
	 * 伝票日付を返す
	 * 
	 * @return slipDate 伝票日付
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * 伝票日付を設定
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * デフォルトの支払い条件を取得し、コード・名称にセット
	 */
	public void setDefaultPaymentCondition() {
		((TPaymentConditionEnhancingFieldCtrl) this.ctrl).setDefaultPaymentCondition();
	}

	/**
	 * 条件用BEANクラスをセットする。<BR>
	 * 
	 * @param param PaymentInformationParameterクラス
	 */
	public void setParameter(PaymentInformationParameter param) {
		this.param = param;
	}

	/**
	 * 条件用BEANクラスを取得する。<BR>
	 * 
	 * @return param PaymentInformationParameterクラス
	 */
	public PaymentInformationParameter getParameter() {
		return this.param;
	}
	
	/**
	 * 有効期限フラグを設定する。<BR>
	 * 
	 * @param isTermBasisDate 判定フラグ true:有効期限内 false:有効期限外
	 * 
	 */
	public void setIsTermBasisDate(boolean isTermBasisDate) {
		this.isTermBasisDate = isTermBasisDate;
	}

	/**
	 * 有効期限フラグを返す。<BR>
	 * 
	 * @return isTermBasisDate 判定フラグ true:有効期限内 false:有効期限外
	 * 
	 */
	public boolean IsTermBasisDate() {
		return isTermBasisDate;
	}
}
