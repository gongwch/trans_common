package jp.co.ais.trans2.model.bank;

/**
 * �x�X��������
 * 
 * @author AIS
 */
public class BranchSearchCondition extends BankSearchCondition implements Cloneable {

	/** ��s�R�[�h */
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
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public BranchSearchCondition clone() {
		return (BranchSearchCondition) super.clone();
	}
}
