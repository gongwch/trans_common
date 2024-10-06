package jp.co.ais.trans2.model.mlit.region;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 輸送実績国・地域マスタの検索条件
 * 
 * @author AIS
 */
public class YJRegionSearchCondition extends TransferBase implements Cloneable {

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public YJRegionSearchCondition clone() {
		try {
			return (YJRegionSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** 会社コード */
	protected String companyCode = null;

	/** 国コード */
	protected String regionCode = null;

	/** 地域コード */
	protected String countryCode = null;

	/** 開始国コード */
	protected String regionCodeFrom = null;

	/** 終了国コード */
	protected String regionCodeTo = null;

	/** 開始地域コード */
	protected String countryCodeFrom = null;

	/** 終了地域コード */
	protected String countryCodeTo = null;

	/** 国コード前方一致 */
	protected String regionCodeLike = null;

	/** 国名称like */
	protected String regionNameLike = null;

	/** 地域コード前方一致 */
	protected String countryCodeLike = null;

	/** 地域名称like */
	protected String countryNameLike = null;

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
	 * 国コードの取得
	 * 
	 * @return regionCode 国コード
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * 国コードの設定
	 * 
	 * @param regionCode 国コード
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * 地域コードの取得
	 * 
	 * @return countryCode 地域コード
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * 地域コードの設定
	 * 
	 * @param countryCode 地域コード
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 開始国コードの取得
	 * 
	 * @return regionCodeFrom 開始国コード
	 */
	public String getRegionCodeFrom() {
		return regionCodeFrom;
	}

	/**
	 * 開始国コードの設定
	 * 
	 * @param regionCodeFrom 開始国コード
	 */
	public void setRegionCodeFrom(String regionCodeFrom) {
		this.regionCodeFrom = regionCodeFrom;
	}

	/**
	 * 終了国コードの取得
	 * 
	 * @return regionCodeTo 終了国コード
	 */
	public String getRegionCodeTo() {
		return regionCodeTo;
	}

	/**
	 * 終了国コードの設定
	 * 
	 * @param regionCodeTo 終了国コード
	 */
	public void setRegionCodeTo(String regionCodeTo) {
		this.regionCodeTo = regionCodeTo;
	}

	/**
	 * 開始地域コードの取得
	 * 
	 * @return countryCodeFrom 開始地域コード
	 */
	public String getCountryCodeFrom() {
		return countryCodeFrom;
	}

	/**
	 * 開始地域コードの設定
	 * 
	 * @param countryCodeFrom 開始地域コード
	 */
	public void setCountryCodeFrom(String countryCodeFrom) {
		this.countryCodeFrom = countryCodeFrom;
	}

	/**
	 * 終了地域コードの取得
	 * 
	 * @return countryCodeTo 終了地域コード
	 */
	public String getCountryCodeTo() {
		return countryCodeTo;
	}

	/**
	 * 終了地域コードの設定
	 * 
	 * @param countryCodeTo 終了地域コード
	 */
	public void setCountryCodeTo(String countryCodeTo) {
		this.countryCodeTo = countryCodeTo;
	}

	/**
	 * 国コード前方一致の取得
	 * 
	 * @return regionCodeLike 国コード前方一致
	 */
	public String getRegionCodeLike() {
		return regionCodeLike;
	}

	/**
	 * 国コード前方一致の設定
	 * 
	 * @param regionCodeLike 国コード前方一致
	 */
	public void setRegionCodeLike(String regionCodeLike) {
		this.regionCodeLike = regionCodeLike;
	}

	/**
	 * 国名称likeの取得
	 * 
	 * @return regionNameLike 国名称like
	 */
	public String getRegionNameLike() {
		return regionNameLike;
	}

	/**
	 * 国名称likeの設定
	 * 
	 * @param regionNameLike 国名称like
	 */
	public void setRegionNameLike(String regionNameLike) {
		this.regionNameLike = regionNameLike;
	}

	/**
	 * 地域コード前方一致の取得
	 * 
	 * @return countryCodeLike 地域コード前方一致
	 */
	public String getCountryCodeLike() {
		return countryCodeLike;
	}

	/**
	 * 地域コード前方一致の設定
	 * 
	 * @param countryCodeLike 地域コード前方一致
	 */
	public void setCountryCodeLike(String countryCodeLike) {
		this.countryCodeLike = countryCodeLike;
	}

	/**
	 * 地域名称likeの取得
	 * 
	 * @return countryNameLike 地域名称like
	 */
	public String getCountryNameLike() {
		return countryNameLike;
	}

	/**
	 * 地域名称likeの設定
	 * 
	 * @param countryNameLike 地域名称like
	 */
	public void setCountryNameLike(String countryNameLike) {
		this.countryNameLike = countryNameLike;
	}
}
