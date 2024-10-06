package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * 部門の検索条件
 * 
 * @author AIS
 */
public class DepartmentSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 組織コード */
	protected String organizationCode = null;

	/** 上位部門 */
	protected String superiorDepartmentCode = null;

	/** 集計部門を含めるか */
	protected boolean sumDepartment = false;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/**
	 * 階層レベル。マイナス値の場合は指定無し。
	 */
	protected int level = -1;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/** コード(複数) */
	protected List<String> departmentCodeList = new LinkedList<String>();

	/** 対象会社リスト(getRefの場合のみ使用) */
	protected List<String> companyCodeList = null;

	/** 連携1を使用するか */
	protected boolean isUseIf1 = false;

	/** 連携2を使用するか */
	protected boolean isUseIf2 = false;

	/** 連携3を使用するか */
	protected boolean isUseIf3 = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DepartmentSearchCondition clone() {
		try {
			return (DepartmentSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * @return String
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * @param organizationCode
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return boolean
	 */
	public boolean isSumDepartment() {
		return sumDepartment;
	}

	/**
	 * @param sumDepartment
	 */
	public void setSumDepartment(boolean sumDepartment) {
		this.sumDepartment = sumDepartment;
	}

	/**
	 * @return int
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @param level
	 */
	public void setLevel(IndicationLevel level) {
		this.setLevel(level.value);
	}

	/**
	 * @param level
	 */
	public void setDownLevel(IndicationLevel level) {
		this.setLevel(level.value - 1);
	}

	/**
	 * @return String
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
	 * @return String
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
	 * @return String
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
	 * @return String
	 */
	public String getSuperiorDepartmentCode() {
		return superiorDepartmentCode;
	}

	/**
	 * @param superiorDepartmentCode
	 */
	public void setSuperiorDepartmentCode(String superiorDepartmentCode) {
		this.superiorDepartmentCode = superiorDepartmentCode;
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
	 * 部門コード(複数)の取得
	 * 
	 * @return 部門コード(複数)
	 */
	public List<String> getCodeList() {
		return this.departmentCodeList;
	}

	/**
	 * 部門コード(複数)の取得
	 * 
	 * @return 部門コード(複数)
	 */
	public String[] getDepartmentCodeList() {
		if (departmentCodeList.isEmpty()) {
			return null;
		}

		return departmentCodeList.toArray(new String[departmentCodeList.size()]);
	}

	/**
	 * 部門コード(複数)の設定
	 * 
	 * @param departmentCodeList 部門コード(複数)
	 */
	public void setDepartmentCodeList(String[] departmentCodeList) {
		addDepartmentCode(departmentCodeList);
	}

	/**
	 * 部門コード(複数)の設定
	 * 
	 * @param list 部門コード(複数)
	 */
	public void addDepartmentCode(String... list) {
		for (String departmentCode : list) {
			this.departmentCodeList.add(departmentCode);
		}
	}

	/**
	 * 部門コード(複数)のクリア
	 */
	public void clearDepartmentCodeList() {
		departmentCodeList.clear();
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

	/**
	 * @return boolean
	 */
	public boolean isUseIf1() {
		return isUseIf1;
	}

	/**
	 * @param isUseIf1
	 */
	public void setUseIf1(boolean isUseIf1) {
		this.isUseIf1 = isUseIf1;
	}

	/**
	 * @return boolean
	 */
	public boolean isUseIf2() {
		return isUseIf2;
	}

	/**
	 * @param isUseIf2
	 */
	public void setUseIf2(boolean isUseIf2) {
		this.isUseIf2 = isUseIf2;
	}

	/**
	 * @return boolean
	 */
	public boolean isUseIf3() {
		return isUseIf3;
	}

	/**
	 * @param isUseIf3
	 */
	public void setUseIf3(boolean isUseIf3) {
		this.isUseIf3 = isUseIf3;
	}
}
