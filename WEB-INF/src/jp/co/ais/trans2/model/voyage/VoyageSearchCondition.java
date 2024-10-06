package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * Voyageの検索条件
 * 
 * @author AIS
 */
public class VoyageSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** Voyageコード */
	protected String code = null;

	/** Voyageコード前方一致 */
	protected String codeLike = null;

	/** VoyageコードFrom */
	protected String codeFrom = null;

	/** VoyageコードTo */
	protected String codeTo = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** コードリスト */
	protected List<String> codeList;

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
	public VoyageSearchCondition clone() {
		try {
			return (VoyageSearchCondition) super.clone();

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
	 * VoyageコードFromを取得します。
	 * 
	 * @return VoyageコードFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * VoyageコードFromを設定します。
	 * 
	 * @param codeFrom VoyageコードFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * VoyageコードToを取得します。
	 * 
	 * @return VoyageコードTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * VoyageコードToを設定します。
	 * 
	 * @param codeTo VoyageコードTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return namesLikeを戻します。
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike namesLikeを設定します。
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
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
		return namekLike;
	}

	/**
	 * 検索名称likeを設定します。
	 * 
	 * @param namekLike 検索名称like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * 有効期限を取得します。
	 * 
	 * @return validTerm 有効期限を戻します。
	 */
	public Date getValidTerm() {
		return this.validTerm;
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
		return codeList;
	}

	/**
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
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
