package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;

/**
 * 銀行口座フィールド拡張 デフォルトの入金口座セット機能を追加
 * 
 * @author ookawara
 */
public class TBankAccountEnhancingField extends TBankAccountField {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** 通貨コード */
	private String curCode;

	/** ｺﾝﾄﾛｰﾙクラス */
	private TBankAccountEnhancingFieldCtrl fieldCtrl;

	/**
	 * コンストラクタ
	 */
	public TBankAccountEnhancingField() {
		super();
		this.fieldCtrl = new TBankAccountEnhancingFieldCtrl(this);
		super.ctrl = this.fieldCtrl;
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
	 * デフォルトの支払い条件を取得し、コード・名称にセット
	 */
	public void setDefaultReceivedAccount() {
		this.fieldCtrl.setDefaultReceivedAccount();
	}
}
