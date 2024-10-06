package jp.co.ais.trans2.model.remark;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �E�v�̌�������
 * 
 * @author AIS
 */
public class RemarkSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String nameLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �f�[�^�敪 */
	protected String dataType = null;

	/** �`�[�E�v�𒊏o���邩 */
	protected boolean slipRemark = true;

	/** �s�E�v�𒊏o���邩 */
	protected boolean slipRowRemark = true;

	/** �f�[�^�敪���X�g */
	protected List<String> dataTypeList = new ArrayList<String>();
	
	

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RemarkSearchCondition clone() {
		try {
			RemarkSearchCondition condition = (RemarkSearchCondition) super.clone();
			if (dataTypeList != null) {
				condition.dataTypeList = new ArrayList<String>(dataTypeList);
			}
			return condition;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeFrom() {
		return codeFrom;
	}

	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	public String getCodeLike() {
		return codeLike;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public String getCodeTo() {
		return codeTo;
	}

	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

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

	public String getNamekLike() {
		return namekLike;
	}

	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public boolean isSlipRemark() {
		return slipRemark;
	}

	public void setSlipRemark(boolean slipRemark) {
		this.slipRemark = slipRemark;
	}

	public boolean isSlipRowRemark() {
		return slipRowRemark;
	}

	public void setSlipRowRemark(boolean slipRowRemark) {
		this.slipRowRemark = slipRowRemark;
	}

	public Date getValidTerm() {
		return validTerm;
	}

	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * �f�[�^�敪���X�g�̎擾
	 * 
	 * @return dataTypeList �f�[�^�敪���X�g
	 */
	public List<String> getDataTypeList() {
		return dataTypeList;
	}

	/**
	 * �f�[�^�敪���X�g�̐ݒ�
	 * 
	 * @param dataTypeList �f�[�^�敪���X�g
	 */
	public void setDataTypeList(List<String> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	/**
	 * �f�[�^�敪�̒ǉ�
	 * 
	 * @param dataTypes �f�[�^�敪�z��
	 */
	public void addDataType(String... dataTypes) {
		for (String type : dataTypes) {
			this.dataTypeList.add(type);
		}
	}

}
