package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans2.model.*;

/**
 * 会社の出力単位条件
 * 
 * @author AIS
 */
public class CompanyOutputCondition extends BasicOutputCondition<Company> {

	/**
	 * @return 開始会社
	 */
	public Company getCompanyFrom() {
		return getFrom();
	}

	/**
	 * 開始会社
	 * 
	 * @param departmentFrom
	 */
	public void setCompanyFrom(Company departmentFrom) {
		setFrom(departmentFrom);
	}

	/**
	 * @return 組織コード
	 */
	public String getCompanyOrganizationCode() {
		return getOrganizationCode();
	}

	/**
	 * 組織コード
	 * 
	 * @param departmentOrganizationCode
	 */
	public void setCompanyOrganizationCode(String departmentOrganizationCode) {
		setOrganizationCode(departmentOrganizationCode);
	}

	/**
	 * @return 終了会社
	 */
	public Company getCompanyTo() {
		return getTo();
	}

	/**
	 * 終了会社
	 * 
	 * @param departmentTo
	 */
	public void setCompanyTo(Company departmentTo) {
		setTo(departmentTo);
	}

	/**
	 * @return 配下会社を含むか
	 */
	public boolean isIncludeUnderCompany() {
		return isIncludeUnder();
	}

	/**
	 * 配下会社を含むか
	 * 
	 * @param includeUnderCompany
	 */
	public void setIncludeUnderCompany(boolean includeUnderCompany) {
		setIncludeUnder(includeUnderCompany);
	}

	/**
	 * @return 個別選択会社
	 */
	public List<Company> getOptionalCompanys() {
		return getOptionalEntities();
	}

	/**
	 * 個別選択会社
	 * 
	 * @param optionalCompanys
	 */
	public void setOptionalCompanys(List<Company> optionalCompanys) {
		setOptionalEntities(optionalCompanys);
	}

	/**
	 * @return 上位会社
	 */
	public Company getSuperiorCompany() {
		return getSuperior();
	}

	/**
	 * 上位会社
	 * 
	 * @param superiorCompany
	 */
	public void setSuperiorCompany(Company superiorCompany) {
		setSuperior(superiorCompany);
	}

	/**
	 * 個別選択された会社のコードリストを返す
	 * 
	 * @return 個別選択された会社のコードリスト
	 */
	public List<String> getOptionalCompanysCode() {
		return getOptionalEntitiesCode();
	}

	/**
	 * テーブルIDの取得
	 * 
	 * @return tableID テーブルID
	 */
	@Override
	public String getTableID() {
		return "EVK_MST";
	}

}
