package jp.co.ais.trans2.model.bank;

/**
 * 支店検索条件
 * 
 * @author AIS
 */
public class BranchSearchCondition extends BankSearchCondition implements Cloneable {

	/** 銀行コード */
	protected String bankCode = null;

	/**
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public BranchSearchCondition clone() {
		return (BranchSearchCondition) super.clone();
	}
}
