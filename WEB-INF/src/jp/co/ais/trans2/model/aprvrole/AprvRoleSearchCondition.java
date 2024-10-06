package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 承認権限マスタ検索条件
 */
public class AprvRoleSearchCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** ユーザーコード */
	protected String userCode = null;

	/** 承認権限グループ */
	private List<AprvRoleGroup> aprvGrpList;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AprvRoleSearchCondition clone() {
		try {
			return (AprvRoleSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return codeを戻します。
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code codeを設定します。
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * コード前方一致を戻す
	 * 
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * コード前方一致を設定する
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 略称likeを戻す
	 * 
	 * @return namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * 略称likeを設定する
	 * 
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 検索名称likeを戻す
	 * 
	 * @return namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * 検索名称likeを設定する
	 * 
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * 開始コードを戻す
	 * 
	 * @return codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * 開始コードを設定する
	 * 
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * 終了コードを戻す
	 * 
	 * @return codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * 終了コードを設定する
	 * 
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * 有効期間を戻す
	 * 
	 * @return validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * 有効期間を設定する
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * ユーザーコード
	 * 
	 * @return ユーザーコード
	 */
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * ユーザーコードを指定する
	 * 
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<AprvRoleGroup> getAprvGrpList() {
		return aprvGrpList;
	}

	/**
	 * 承認グループリストを指定
	 * 
	 * @param aprvGrpList
	 */
	public void setAprvRoleGroupList(List<AprvRoleGroup> aprvGrpList) {
		this.aprvGrpList = aprvGrpList;
	}

}
