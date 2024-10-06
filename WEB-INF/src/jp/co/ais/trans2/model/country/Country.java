package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * 国情報
 */
public class Country extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	@Override
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();
		sb.append(Util.avoidNull(code));
		sb.append("/");
		sb.append(Util.avoidNull(code2));
		sb.append("/");
		sb.append(Util.avoidNull(name));
		sb.append("/");
		sb.append(Util.avoidNull(name2));
		sb.append("/");
		sb.append(Util.avoidNull(number));
		return sb.toString();
	}

	/** 国コード(3桁) */
	protected String code;

	/** 国コード(2桁) */
	protected String code2;

	/** 国番号 */
	protected String number;

	/** 国名称 */
	protected String name;

	/** 国名称2 */
	protected String name2;

	/** 有効期間開始 */
	protected Date dateFrom;

	/** 有効期間終了 */
	protected Date dateTo;

	/**
	 * 国コード(3桁)
	 * 
	 * @return 国コード(3桁)
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * 国コード(3桁)
	 * 
	 * @param code 国コード(3桁)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 国コード(2桁)
	 * 
	 * @return 国コード(2桁)
	 */
	public String getCode2() {
		return this.code2;
	}

	/**
	 * 国コード(2桁)
	 * 
	 * @param code2 国コード(2桁)
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}

	/**
	 * 国番号
	 * 
	 * @return 国番号
	 */
	public String getNumber() {
		return this.number;
	}

	/**
	 * 国番号
	 * 
	 * @param number 国番号
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 国名称
	 * 
	 * @return 国名称
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 国名称
	 * 
	 * @param name 国名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 国名称2
	 * 
	 * @return 国名称2
	 */
	public String getName2() {
		return this.name2;
	}

	/**
	 * 国名称2
	 * 
	 * @param name2 国名称2
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * 有効期間開始
	 * 
	 * @return 有効期間開始
	 */
	public Date getDateFrom() {
		return this.dateFrom;
	}

	/**
	 * 有効期間開始
	 * 
	 * @param dateFrom 有効期間開始
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * 有効期間終了
	 * 
	 * @return 有効期間終了
	 */
	public Date getDateTo() {
		return this.dateTo;
	}

	/**
	 * 有効期間終了
	 * 
	 * @param dateTo 有効期間終了
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @deprecated
	 * @see jp.co.ais.trans2.model.TReferable#getNames()
	 */
	@Override
	public String getNames() {
		return null;
	}

	/**
	 * @deprecated
	 * @see jp.co.ais.trans2.model.TReferable#setNames(java.lang.String)
	 */
	@Override
	public void setNames(String names) {
		// なし
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableEntity#getNamek()
	 */
	public String getNamek() {
		return name2;
	}

}
