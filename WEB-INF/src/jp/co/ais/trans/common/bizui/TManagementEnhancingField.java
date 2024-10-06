package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;

/**
 * 管理マスタ検索フィールド拡張 社員・取引先検索追加
 * 
 * @author ookawara
 */
public class TManagementEnhancingField extends TManagementField {

	/** シリアルUID */
	protected static final long serialVersionUID = 1L;

	/** 取引先コード */
	public static final int TYPE_CUSTOMER = 7;

	/** 社員コード */
	public static final int TYPE_EMP = 8;

	protected boolean loanCustomer;

	/**
	 * コンストラクタ
	 */
	public TManagementEnhancingField() {
		super(TYPE_MANAGEMENT1);

		super.ctrl = new TManagementEnhancingFieldCtrl(this);
	}

	/**
	 * 借入先表示フラグ取得
	 * 
	 * @return 借入先表示フラグ
	 */
	public boolean isLoanCustomer() {
		return loanCustomer;
	}

	/**
	 * 借入先表示フラグ設定
	 * 
	 * @param loanCustomer 借入先表示フラグ
	 */
	public void setLoanCustomer(boolean loanCustomer) {
		this.loanCustomer = loanCustomer;
	}
}
