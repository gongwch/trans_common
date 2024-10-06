package jp.co.ais.trans2.model.remark;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �E�v���
 * 
 * @author AIS
 */
public class Remark extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** �������� */
	protected String namek = null;

	/** �f�[�^�敪 */
	protected String dataType = null;

	/** �`�[�E�v�� */
	protected boolean slipRemark = true;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
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
