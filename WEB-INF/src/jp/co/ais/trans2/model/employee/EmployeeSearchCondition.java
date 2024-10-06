package jp.co.ais.trans2.model.employee;

import java.util.*;

import jp.co.ais.trans.common.dt.TransferBase;
import jp.co.ais.trans.common.except.TRuntimeException;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * MG0160EmployeeMaster - 社員マスタ - SearchCondition Class
 * 
 * @author AIS
 */
public class EmployeeSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** 会社コード */
	protected String companyCode;

	/** 部門コード */
	protected String depCode;

	/** コード */
	protected String code;

	/** 開始コード */
	protected String codeFrom;

	/** 終了コード */
	protected String codeTo;

	/** コード前方一致 */
	protected String codeLike;

	/** 略称前方一致 */
	protected String namesLike;

	/** 検索名称前方一致 */
	protected String namekLike;

	/** 有効期間 */
	protected Date validTerm;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/** コード(複数) */
	protected List<String> codeList = new LinkedList<String>();

	/** 対象会社リスト(getRefの場合のみ使用) */
	protected List<String> companyCodeList = null;

	/**
	 * *
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EmployeeSearchCondition clone() {
		try {
			return (EmployeeSearchCondition) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return depCode
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

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
	 * @return codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * @return namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
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

	/**
	 * コード(複数)の取得
	 * 
	 * @return 社員コード(複数)
	 */
	public List<String> getCodeList() {
		return this.codeList;
	}

	/**
	 * コード(複数)の取得
	 * 
	 * @return 社員コード(複数)
	 */
	public String[] getEmployeeCodeList() {
		if (codeList.isEmpty()) {
			return null;
		}

		return codeList.toArray(new String[codeList.size()]);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param employeeCodeList コード(複数)
	 */
	public void setEmployeeCodeList(String[] employeeCodeList) {
		addEmployeeCode(employeeCodeList);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param list コード(複数)
	 */
	public void addEmployeeCode(String... list) {
		for (String employeeCode : list) {
			this.codeList.add(employeeCode);
		}
	}

	/**
	 * コード(複数)のクリア
	 */
	public void clearEmployeeCodeList() {
		codeList.clear();
	}

	/**
	 * 対象会社リスト(getRefの場合のみ使用)の取得
	 * 
	 * @return companyCodeList 対象会社リスト(getRefの場合のみ使用)
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * 対象会社リスト(getRefの場合のみ使用)の設定
	 * 
	 * @param companyCodeList 対象会社リスト(getRefの場合のみ使用)
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}

}