package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * 国マスタ検索条件
 */
public class CountrySearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 名称like */
	protected String nameLike = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** コード2桁 */
	protected String code2 = null;

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
	public CountrySearchCondition clone() {
		try {
			return (CountrySearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 会社コードを設定する。
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		// 処理なし
	}

	/**
	 * コード
	 * 
	 * @return コード
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * コード
	 * 
	 * @param code コード
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 名称
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * コード前方一致
	 * 
	 * @return コード前方一致
	 */
	public String getCodeLike() {
		return this.codeLike;
	}

	/**
	 * コード前方一致
	 * 
	 * @param codeLike コード前方一致
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 名称like
	 * 
	 * @return 名称like
	 */
	public String getNameLike() {
		return this.nameLike;
	}

	/**
	 * 名称like
	 * 
	 * @param nameLike 名称like
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * 開始コード
	 * 
	 * @return 開始コード
	 */
	public String getCodeFrom() {
		return this.codeFrom;
	}

	/**
	 * 開始コード
	 * 
	 * @param codeFrom 開始コード
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * 終了コード
	 * 
	 * @return 終了コード
	 */
	public String getCodeTo() {
		return this.codeTo;
	}

	/**
	 * 終了コード
	 * 
	 * @param codeTo 終了コード
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * 有効期間
	 * 
	 * @return 有効期間
	 */
	public Date getValidTerm() {
		return this.validTerm;
	}

	/**
	 * 有効期間
	 * 
	 * @param validTerm 有効期間
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * コード2桁の取得
	 * 
	 * @return code2 コード2桁
	 */
	public String getCode2() {
		return code2;
	}

	/**
	 * コード2桁の設定
	 * 
	 * @param code2 コード2桁
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getNamekLike()
	 */
	public String getNamekLike() {
		return nameLike;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getNamesLike()
	 */
	public String getNamesLike() {
		return nameLike;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
		// 未実現
		return null;
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
