package jp.co.ais.trans.common.client.util;

import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * �g�D���
 */
public class OrganizationInfo {

	/** �K�w���x�� */
	private int displayLevel;

	/** ��ʕ���R�[�h */
	private String upperDepartmentCode = "";

	/** ��ʕ��喼�� */
	private String upperDepartmentName = "";

	/** ����R�[�h */
	private String departmentCode = "";

	/** ���喼�� */
	private String departmentName = "";

	/**
	 * ����ݒ�
	 * 
	 * @param infoMap �f�[�^
	 */
	void set(Map infoMap) {

		// �K�w����
		String strDpkLvl = (String) infoMap.get("KJL_LVL");
		displayLevel = !Util.isNullOrEmpty(strDpkLvl) ? Integer.parseInt(strDpkLvl) : -1;

		// ��ʕ��庰��
		upperDepartmentCode = Util.avoidNull(infoMap.get("KJL_UP_DEP_CODE"));

		// ��ʕ��嗪��
		upperDepartmentName = Util.avoidNull(infoMap.get("KJL_UP_DEP_NAME_S"));

		// ���庰��
		departmentCode = Util.avoidNull(infoMap.get("KJL_DEP_CODE"));

		// ���喼
		departmentName = Util.avoidNull(infoMap.get("KJL_DEP_NAME_S"));
	}

	/**
	 * �K�w���x��
	 * 
	 * @return �K�w���x��
	 */
	public int getDisplayLevel() {
		return displayLevel;
	}

	/**
	 * ��ʕ���R�[�h
	 * 
	 * @return ��ʕ���R�[�h
	 */
	public String getUpperDepartmentCode() {
		return upperDepartmentCode;
	}

	/**
	 * ��ʕ��喼��
	 * 
	 * @return ��ʕ��喼��
	 */
	public String getUpperDepartmentName() {
		return upperDepartmentName;
	}

	/**
	 * ����R�[�h
	 * 
	 * @return ����R�[�h
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * ���喼��
	 * 
	 * @return ���喼��
	 */
	public String getDepartmentName() {
		return departmentName;
	}
}