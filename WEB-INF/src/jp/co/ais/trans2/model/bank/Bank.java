package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 銀行情報
 * @author AIS
 */
public class Bank extends TransferBase implements Cloneable, TReferable {

	/** コード */
	protected String code = null;

	/** 略称 */
	protected String names = null;

	/** ｶﾅ名称 */
	protected String kana = null;

	/** 検索名称 */
	protected String namek = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 支店情報 */
	public Branch branch;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Bank clone() {
		try {
			return (Bank) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return 支店情報 
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return コード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 有効期間開始 
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return 有効期間終了
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return 検索名
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return ｶﾅ名称 
	 */
	public String getKana() {
		return kana;
	}

	/**
	 * @param kana
	 */
	public void setKana(String kana) {
		this.kana = kana;
	}

	/**
	 * @return ｶﾅ名称 
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
	}

}
