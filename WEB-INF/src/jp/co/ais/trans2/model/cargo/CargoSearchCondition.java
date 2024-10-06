package jp.co.ais.trans2.model.cargo;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * Cargoマスタ検索条件
 */
public class CargoSearchCondition extends TransferBase implements FilterableCondition, OPLoginCondition, Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** Cargoコード */
	protected String code = null;

	/** Cargoコード前方一致 */
	protected String codeLike = null;

	/** CargoコードFrom */
	protected String codeFrom = null;

	/** CargoコードTo */
	protected String codeTo = null;
	
	/**Category */
	protected String category = null;

	/** 貨物名like */
	protected String nameLike = null;

	/** グループ名like */
	protected String groupNameLike = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CargoSearchCondition clone() {
		try {
			return (CargoSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * 会社コードを取得します。
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードを設定します。
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * CargoコードFromを取得します。
	 * 
	 * @return CargoコードFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * CargoコードFromを設定します。
	 * 
	 * @param codeFrom CargoコードFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * CargoコードToを取得します。
	 * 
	 * @return CargoコードTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * CargoコードToを設定します。
	 * 
	 * @param codeTo CargoコードTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return namesLikeを戻します。
	 */
	public String getNamesLike() {
		return nameLike;
	}

	/**
	 * @param namesLike namesLikeを設定します。
	 */
	public void setNamesLike(String namesLike) {
		this.nameLike = namesLike;
	}

	/**
	 * @return 会社コード前方一致を戻します。
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike 会社コード前方一致を設定します。
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 検索名称likeを取得します。
	 * 
	 * @return 検索名称like
	 */
	public String getNamekLike() {
		return nameLike;
	}

	/**
	 * 検索名称likeを設定します。
	 * 
	 * @param namekLike 検索名称like
	 */
	public void setNamekLike(String namekLike) {
		this.nameLike = namekLike;
	}
	
	/**
	 * Categoryを取得します。
	 * 
	 * @return 検索名称like
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Categoryを設定します。
	 * 
	 * @param category 検索名称like
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * 有効期限を取得します。
	 * 
	 * @return validTerm 有効期限を戻します。
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * 有効期限を設定します。
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
		return null;
	}

	/**
	 * 検索名称likeを取得します。
	 * 
	 * @return 検索名称like
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * 検索名称likeを設定します。
	 * 
	 * @param nameLike 検索名称like
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * Gets the group name like.
	 * 
	 * @return the search group name
	 */
	public String getGroupNameLike() {
		return groupNameLike;
	}

	/**
	 * Sets the search group name like.
	 * 
	 * @param groupNameLike
	 */
	public void setGroupNameLike(String groupNameLike) {
		this.groupNameLike = groupNameLike;
	}

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * 現在件数の取得
	 * 
	 * @return nowCount 現在件数
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * 現在件数の設定
	 * 
	 * @param nowCount 現在件数
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

}
