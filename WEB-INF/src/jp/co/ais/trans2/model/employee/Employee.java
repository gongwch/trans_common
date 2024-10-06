package jp.co.ais.trans2.model.employee;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * MG0160EmployeeMaster - 社員マスタ - Bean Class
 * 
 * @author AIS
 */
public class Employee extends TransferBase implements AutoCompletable, FilterableEntity {

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCode());
		sb.append(" / ");
		sb.append(Util.avoidNull(getName()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNames()));

		return sb.toString();
	}

	/** 社員コード */
	protected String code;

	/** 社員名称 */
	protected String name;

	/** 社員略称 */
	protected String names;

	/** 社員検索名称 */
	protected String namek;

	/** 振出銀行口座コード */
	protected String cbkCode;

	/** 振出銀行口座名称 */
	protected String cbkName;

	/** 振込銀行コード */
	protected String bnkCode;

	/** 振込銀行名称 */
	protected String bnkName;

	/** 振込支店コード */
	protected String stnCode;

	/** 振込支店名称 */
	protected String stnName;

	/** 振込口座預金種別 */
	protected DepositKind kozaKbn;

	/** 振込口座番号 */
	protected String yknNo;

	/** 口座名義カナ */
	protected String yknKana;

	/** 開始 */
	protected Date dateFrom;

	/** 終了 */
	protected Date dateTo;

	/**
	 * @return code
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
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return names
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

	/**
	 * @return namek
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
	 * @return cbkCode
	 */
	public String getCbkCode() {
		return cbkCode;
	}

	/**
	 * @param cbkCode
	 */
	public void setCbkCode(String cbkCode) {
		this.cbkCode = cbkCode;
	}

	/**
	 * @return cbkName
	 */
	public String getCbkName() {
		return cbkName;
	}

	/**
	 * @param cbkName
	 */
	public void setCbkName(String cbkName) {
		this.cbkName = cbkName;
	}

	/**
	 * @return bnkCode
	 */
	public String getBnkCode() {
		return bnkCode;
	}

	/**
	 * @param bnkCode
	 */
	public void setBnkCode(String bnkCode) {
		this.bnkCode = bnkCode;
	}

	/**
	 * @return bnkName
	 */
	public String getBnkName() {
		return bnkName;
	}

	/**
	 * @param bnkName
	 */
	public void setBnkName(String bnkName) {
		this.bnkName = bnkName;
	}

	/**
	 * @return stnCode
	 */
	public String getStnCode() {
		return stnCode;
	}

	/**
	 * @param stnCode
	 */
	public void setStnCode(String stnCode) {
		this.stnCode = stnCode;
	}

	/**
	 * @return stnName
	 */
	public String getStnName() {
		return stnName;
	}

	/**
	 * @param stnName
	 */
	public void setStnName(String stnName) {
		this.stnName = stnName;
	}

	/**
	 * @return kozaKbn
	 */
	public DepositKind getKozaKbn() {
		return kozaKbn;
	}

	/**
	 * @return kozaKbn Value
	 */
	public int getKozaKbnValue() {
		return getKozaKbn().value;
	}

	/**
	 * @return kozaKbn Text
	 */
	public String getKozaKbnText() {
		return DepositKind.getDepositKindName(getKozaKbn());
	}

	/**
	 * @param kozaKbn
	 */
	public void setKozaKbn(DepositKind kozaKbn) {
		this.kozaKbn = kozaKbn;
	}

	/**
	 * @return yknNo
	 */
	public String getYknNo() {
		return yknNo;
	}

	/**
	 * @param yknNo
	 */
	public void setYknNo(String yknNo) {
		this.yknNo = yknNo;
	}

	/**
	 * @return yknKana
	 */
	public String getYknKana() {
		return yknKana;
	}

	/**
	 * @param yknKana
	 */
	public void setYknKana(String yknKana) {
		this.yknKana = yknKana;
	}

	/**
	 * @return dateFrom
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
	 * @return dateTo
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
}