package jp.co.ais.trans2.model.department;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * 部門情報
 * 
 * @author AIS
 */
public class Department extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

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

	@Override
	public String toString() {
		return getCode() + " " + getName();
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

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 集計部門か */
	protected boolean sumDepartment = false;

	/** 組織コード */
	protected String organizationCode = null;

	/** 組織名称 */
	protected String organizationName = null;

	/** 部門レベル */
	protected int level = -1;

	/** 上位部門コード */
	protected String superiorDepartmentCode = null;

	/** 人員1 */
	protected int personnel1 = 0;

	/** 人員2 */
	protected int personnel2 = 0;

	/** 床面積 */
	protected BigDecimal floorSpace = null;

	/** 連携1を使用するか */
	protected boolean isUseIf1 = false;

	/** 連携2を使用するか */
	protected boolean isUseIf2 = false;

	/** 連携3を使用するか */
	protected boolean isUseIf3 = false;

	/** 連携コード1 */
	protected String ifCode1 = null;

	/** 連携名称1 */
	protected String ifName1 = null;

	/** 連携コード2 */
	protected String ifCode2 = null;

	/** 連携名称2 */
	protected String ifName2 = null;

	/** 連携コード3 */
	protected String ifCode3 = null;

	/** 連携名称3 */
	protected String ifName3 = null;

	/**
	 * 上位部門コード
	 * 
	 * @return 上位部門コード
	 */
	public String getSuperiorDepartmentCode() {
		return superiorDepartmentCode;
	}

	/**
	 * 上位部門コード
	 * 
	 * @param superiorDepartmentCode 上位部門コード
	 */
	public void setSuperiorDepartmentCode(String superiorDepartmentCode) {
		this.superiorDepartmentCode = superiorDepartmentCode;
	}

	/**
	 * 集計部門か
	 * 
	 * @return true:集計部門
	 */
	public boolean isSumDepartment() {
		return sumDepartment;
	}

	/**
	 * 集計部門か
	 * 
	 * @param sumDepartment true:集計部門
	 */
	public void setSumDepartment(boolean sumDepartment) {
		this.sumDepartment = sumDepartment;
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
	 * 部門レベル
	 * 
	 * @return 部門レベルを戻します。
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 部門レベル
	 * 
	 * @param level 部門レベルを設定します。
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 組織コード
	 * 
	 * @return 組織コードを戻します。
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * 組織コード
	 * 
	 * @param organizationCode 組織コードを設定します。
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * 組織名称
	 * 
	 * @return 組織名称を戻します。
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * 組織名称
	 * 
	 * @param organizationName 組織名称を設定します。
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return 床面積を戻します。
	 */
	public BigDecimal getFloorSpace() {
		return floorSpace;
	}

	/**
	 * @param floorSpace 床面積を設定します。
	 */
	public void setFloorSpace(BigDecimal floorSpace) {
		this.floorSpace = floorSpace;
	}

	/**
	 * @return 人員1を戻します。
	 */
	public int getPersonnel1() {
		return personnel1;
	}

	/**
	 * @param personnel1 人員1を設定します。
	 */
	public void setPersonnel1(int personnel1) {
		this.personnel1 = personnel1;
	}

	/**
	 * @return 人員2を戻します。
	 */
	public int getPersonnel2() {
		return personnel2;
	}

	/**
	 * @param personnel2 人員2を設定します。
	 */
	public void setPersonnel2(int personnel2) {
		this.personnel2 = personnel2;
	}

	/**
	 * @return ifCode1を戻します。
	 */
	public String getIfCode1() {
		return ifCode1;
	}

	/**
	 * @param ifCode1 ifCode1を設定します。
	 */
	public void setIfCode1(String ifCode1) {
		this.ifCode1 = ifCode1;
	}

	/**
	 * @return ifName1を戻します。
	 */
	public String getIfName1() {
		return ifName1;
	}

	/**
	 * @param ifName1 ifName1を設定します。
	 */
	public void setIfName1(String ifName1) {
		this.ifName1 = ifName1;
	}

	/**
	 * @return ifCode2を戻します。
	 */
	public String getIfCode2() {
		return ifCode2;
	}

	/**
	 * @param ifCode2 ifCode2を設定します。
	 */
	public void setIfCode2(String ifCode2) {
		this.ifCode2 = ifCode2;
	}

	/**
	 * @return ifName2を戻します。
	 */
	public String getIfName2() {
		return ifName2;
	}

	/**
	 * @param ifName2 ifName2を設定します。
	 */
	public void setIfName2(String ifName2) {
		this.ifName2 = ifName2;
	}

	/**
	 * @return ifCode3を戻します。
	 */
	public String getIfCode3() {
		return ifCode3;
	}

	/**
	 * @param ifCode3 ifCode3を設定します。
	 */
	public void setIfCode3(String ifCode3) {
		this.ifCode3 = ifCode3;
	}

	/**
	 * @return ifName3を戻します。
	 */
	public String getIfName3() {
		return ifName3;
	}

	/**
	 * @param ifName3 ifName3を設定します。
	 */
	public void setIfName3(String ifName3) {
		this.ifName3 = ifName3;
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
