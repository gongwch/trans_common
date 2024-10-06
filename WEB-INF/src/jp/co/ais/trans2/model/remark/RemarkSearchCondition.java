package jp.co.ais.trans2.model.remark;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 摘要の検索条件
 * 
 * @author AIS
 */
public class RemarkSearchCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 名称like */
	protected String nameLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** データ区分 */
	protected String dataType = null;

	/** 伝票摘要を抽出するか */
	protected boolean slipRemark = true;

	/** 行摘要を抽出するか */
	protected boolean slipRowRemark = true;

	/** データ区分リスト */
	protected List<String> dataTypeList = new ArrayList<String>();
	
	

	/**
	 * クローン
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
	 * データ区分リストの取得
	 * 
	 * @return dataTypeList データ区分リスト
	 */
	public List<String> getDataTypeList() {
		return dataTypeList;
	}

	/**
	 * データ区分リストの設定
	 * 
	 * @param dataTypeList データ区分リスト
	 */
	public void setDataTypeList(List<String> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	/**
	 * データ区分の追加
	 * 
	 * @param dataTypes データ区分配列
	 */
	public void addDataType(String... dataTypes) {
		for (String type : dataTypes) {
			this.dataTypeList.add(type);
		}
	}

}
