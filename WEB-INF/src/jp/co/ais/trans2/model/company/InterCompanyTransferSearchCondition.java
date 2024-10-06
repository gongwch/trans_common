package jp.co.ais.trans2.model.company;

import jp.co.ais.trans.common.dt.TransferBase;
import jp.co.ais.trans.common.except.*;

/**
 * 会社間付替マスタ検索条件
 * 
 * @author AIS
 */
public class InterCompanyTransferSearchCondition extends TransferBase implements Cloneable {

	/** 会社 */
	protected Company company = null;

	/** 付替会社コード */
	protected String transferCompanyCode = null;

	/** 付替会社開始コード */
	protected String transferCompanyFrom = null;

	/** 付替会社終了コード */
	protected String transferCompanyTo = null;

	/**
	 * クローン
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
