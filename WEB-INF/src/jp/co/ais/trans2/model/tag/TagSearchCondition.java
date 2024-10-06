package jp.co.ais.trans2.model.tag;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * MG0460TagMaster - 付箋マスタ - SearchCondition Class
 * 
 * @author AIS
 */
public class TagSearchCondition extends TransferBase implements Cloneable {

	/** 会社コードの定義 */
	protected String companyCode = null;

	/** 付箋コードの定義 */
	protected String tagCode = null;

	/** 付箋タイトルの定義 */
	protected String tagTitle = null;

	/** 付箋コード曖昧検索の定義 */
	protected String codeLike = null;

	/** 付箋タイトル曖昧検索の定義 */
	protected String titleLike = null;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TagSearchCondition clone() {
		try {
			return (TagSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 付箋コードの取得
	 * 
	 * @return tagCode
	 */
	public String getTagCode() {
		return tagCode;
	}

	/**
	 * 付箋コードの設定
	 * 
	 * @param tagCode
	 */
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	/**
	 * 付箋タイトルの取得
	 * 
	 * @return tagTitle
	 */
	public String getTagTitle() {
		return tagTitle;
	}

	/**
	 * 付箋タイトルの設定
	 * 
	 * @param tagTitle
	 */
	public void setTagTitle(String tagTitle) {
		this.tagTitle = tagTitle;
	}

	/**
	 * 付箋コード曖昧検索の取得
	 * 
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * 付箋コード曖昧検索の設定
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 付箋タイトル曖昧検索の取得
	 * 
	 * @return titleLike
	 */
	public String getTitleLike() {
		return titleLike;
	}

	/**
	 * 付箋タイトル曖昧検索の設定
	 * 
	 * @param titleLike
	 */
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

}