package jp.co.ais.trans2.model.remark;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 摘要情報
 * 
 * @author AIS
 */
public class Remark extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 検索名称 */
	protected String namek = null;

	/** データ区分 */
	protected String dataType = null;

	/** 伝票摘要か */
	protected boolean slipRemark = true;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamek() {
		return namek;
	}

	public void setNamek(String namek) {
		this.namek = namek;
	}

	public boolean isSlipRemark() {
		return slipRemark;
	}

	public void setSlipRemark(boolean slipRemark) {
		this.slipRemark = slipRemark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
