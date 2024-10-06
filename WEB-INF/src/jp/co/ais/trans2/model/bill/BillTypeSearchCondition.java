package jp.co.ais.trans2.model.bill;

import java.util.*;

import jp.co.ais.trans.common.dt.TransferBase;
import jp.co.ais.trans.common.except.*;

/**
 * 請求区分マスタの検索条件
 * 
 * @author AIS
 */
public class BillTypeSearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 3233838287647082966L;

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 名称like */
	protected String nameLike = null;

	/** 検索名称 like */
	protected String namekLike = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public BillTypeSearchCondition clone() {
		try {
			return (BillTypeSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * codeを取得する。
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * codeを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * codeFromを取得する。
	 * 
	 * @return String codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * codeFromを設定する。
	 * 
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * codeLikeを取得する。
	 * 
	 * @return String codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * codeLikeを設定する。
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * codeToを取得する。
	 * 
	 * @return String codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * codeToを設定する。
	 * 
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * companyCodeを取得する。
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCodeを設定する。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * namekLikeを取得する。
	 * 
	 * @return String namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * namekLikeを設定する。
	 * 
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * nameLikeを取得する。
	 * 
	 * @return String nameLike
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * nameLikeを設定する。
	 * 
	 * @param nameLike
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * validTermを取得する。
	 * 
	 * @return Date validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * validTermを設定する。
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

}
