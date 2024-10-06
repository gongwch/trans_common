package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;

/**
 * Voyage
 */
public class Voyage extends TransferBase implements FilterableEntity {

	/** 会社コード */
	protected String companyCode = null;

	/** Voyageコード */
	protected String code = null;

	/** Voyage名称 */
	protected String name = null;

	/** Voyage略称 */
	protected String names = null;

	/** Voyage検索略称 */
	protected String namek = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

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
	 * Voyageコードを取得します。
	 * 
	 * @return Voyageコード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Voyageコードを設定します。
	 * 
	 * @param code Voyageコード
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Voyage名称を取得します。
	 * 
	 * @return Voyage名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Voyage名称を設定します。
	 * 
	 * @param name Voyage名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Voyage略称を取得します。
	 * 
	 * @return Voyage略称
	 */
	public String getNames() {
		return names;
	}

	/**
	 * Voyage略称を設定します。
	 * 
	 * @param names Voyage略称
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * Voyage検索略称を取得します。
	 * 
	 * @return Voyage検索略称
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * Voyage検索略称を設定します。
	 * 
	 * @param namek Voyage検索略称
	 */
	public void setNamek(String namek) {
		this.namek = namek;
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
}
