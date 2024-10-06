package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * 通貨情報
 */
public class Currency extends TransferBase implements AutoCompletable, FilterableEntity {

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(companyCode).append("-").append(code);
		return sb.toString();
	}

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 略称 */
	protected String names = null;

	/** 検索名称 */
	protected String namek = null;

	/** 小数点以下桁数 */
	protected int decimalPoint = 0;

	/** レート係数 */
	protected int ratePow = 0;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

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
	 * @return dateFromを戻します。
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFromを設定します。
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateToを戻します。
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateToを設定します。
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return nameを戻します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name nameを設定します。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namekを戻します。
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namekを設定します。
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return namesを戻します。
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names namesを設定します。
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * 小数点以下桁数
	 * 
	 * @return 小数点以下桁数
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
	 * 通貨コードが同一かどうか
	 * 
	 * @param currency 通貨
	 * @return true:同じ
	 */
	public boolean equalsCode(Currency currency) {
		if (currency == null) {
			return false;
		}

		return Util.avoidNull(code).equals(Util.avoidNull(currency.getCode()));
	}
}
