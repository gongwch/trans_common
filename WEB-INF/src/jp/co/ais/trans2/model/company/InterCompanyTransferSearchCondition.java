package jp.co.ais.trans2.model.company;

import jp.co.ais.trans.common.dt.TransferBase;
import jp.co.ais.trans.common.except.*;

/**
 * ��Њԕt�փ}�X�^��������
 * 
 * @author AIS
 */
public class InterCompanyTransferSearchCondition extends TransferBase implements Cloneable {

	/** ��� */
	protected Company company = null;

	/** �t�։�ЃR�[�h */
	protected String transferCompanyCode = null;

	/** �t�։�ЊJ�n�R�[�h */
	protected String transferCompanyFrom = null;

	/** �t�։�ЏI���R�[�h */
	protected String transferCompanyTo = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public InterCompanyTransferSearchCondition clone() {
		try {
			return (InterCompanyTransferSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	public String getTransferCompanyCode() {
		return transferCompanyCode;
	}

	public void setTransferCompanyCode(String transferCompanyCode) {
		this.transferCompanyCode = transferCompanyCode;
	}

	public String getTransferCompanyFrom() {
		return transferCompanyFrom;
	}

	public void setTransferCompanyFrom(String transferCompanyFrom) {
		this.transferCompanyFrom = transferCompanyFrom;
	}

	public String getTransferCompanyTo() {
		return transferCompanyTo;
	}

	public void setTransferCompanyTo(String transferCompanyTo) {
		this.transferCompanyTo = transferCompanyTo;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
