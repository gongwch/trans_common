package jp.co.ais.trans.common.client.util;

import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * 組織情報
 */
public class OrganizationInfo {

	/** 階層レベル */
	private int displayLevel;

	/** 上位部門コード */
	private String upperDepartmentCode = "";

	/** 上位部門名称 */
	private String upperDepartmentName = "";

	/** 部門コード */
	private String departmentCode = "";

	/** 部門名称 */
	private String departmentName = "";

	/**
	 * 情報を設定
	 * 
	 * @param infoMap データ
	 */
	void set(Map infoMap) {

		// 階層ﾚﾍﾞﾙ
		String strDpkLvl = (String) infoMap.get("KJL_LVL");
		displayLevel = !Util.isNullOrEmpty(strDpkLvl) ? Integer.parseInt(strDpkLvl) : -1;

		// 上位部門ｺｰﾄﾞ
		upperDepartmentCode = Util.avoidNull(infoMap.get("KJL_UP_DEP_CODE"));

		// 上位部門略称
		upperDepartmentName = Util.avoidNull(infoMap.get("KJL_UP_DEP_NAME_S"));

		// 部門ｺｰﾄﾞ
		departmentCode = Util.avoidNull(infoMap.get("KJL_DEP_CODE"));

		// 部門名
		departmentName = Util.avoidNull(infoMap.get("KJL_DEP_NAME_S"));
	}

	/**
	 * 階層レベル
	 * 
	 * @return 階層レベル
	 */
	public int getDisplayLevel() {
		return displayLevel;
	}

	/**
	 * 上位部門コード
	 * 
	 * @return 上位部門コード
	 */
	public String getUpperDepartmentCode() {
		return upperDepartmentCode;
	}

	/**
	 * 上位部門名称
	 * 
	 * @return 上位部門名称
	 */
	public String getUpperDepartmentName() {
		return upperDepartmentName;
	}

	/**
	 * 部門コード
	 * 
	 * @return 部門コード
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 部門名称
	 * 
	 * @return 部門名称
	 */
	public String getDepartmentName() {
		return departmentName;
	}
}