package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * 通貨マスタ検索条件
 */
public class CurrencySearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

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

	/** 小数点以下桁数 */
	protected int decimalPoint = 0;

	/** レート係数 */
	protected int ratePow = 0;

	/** 通貨コード */
	protected String currencyCode = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** コードリスト */
	protected List<String> codeList = new ArrayList<String>();

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
	public CurrencySearchCondition clone() {
		try {
			CurrencySearchCondition condition = (CurrencySearchCondition) super.clone();
			if (codeList != null) {
				condition.codeList = new ArrayList<String>(codeList);
			}
			return condition;

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
	 * @return codeFromを戻します。
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFromを設定します。
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeToを戻します。
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeToを設定します。
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return companyCodeを戻します。
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return validTermを戻します。
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTermを設定します。
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * コード前方一致
	 * 
	 * @return コード前方一致
	 */
	public String getCodeLike() {
		return codeLike;
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
	 * 検索名称like
	 * 
	 * @return 検索名称like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * 検索名称like
	 * 
	 * @param namekLike 検索名称like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * 略称like
	 * 
	 * @return 略称like
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * 略称like
	 * 
	 * @param namesLike 略称like
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 小数点以下桁数
	 * 
	 * @return decimalPoint 小数点以下桁数
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * 小数点以下桁数
	 * 
	 * @param decimalPoint 小数点以下桁数
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * レート係数
	 * 
	 * @return ratePow レート係数
	 */
	public int getRatePow() {
		return ratePow;
	}

	/**
	 * レート係数
	 * 
	 * @param ratePow レート係数
	 */
	public void setRatePow(int ratePow) {
		this.ratePow = ratePow;
	}

	/**
	 * 通貨コード
	 * 
	 * @return currencyCode 通貨コード
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 通貨コード
	 * 
	 * @param currencyCode 通貨コード
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * コードリスト
	 * 
	 * @return コードリスト
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * コードクリア
	 */
	public void clearCodeList() {
		codeList.clear();
	}

	/**
	 * コード追加
	 * 
	 * @param codes コード配列
	 */
	public void addCode(String... codes) {
		for (String c : codes) {
			codeList.add(c);
		}
	}

	/**
	 * コード設定
	 * 
	 * @param codes コード配列
	 */
	public void setCodeList(String... codes) {
		codeList.clear();

		for (String c : codes) {
			codeList.add(c);
		}
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
