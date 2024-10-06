package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class CompanyHierarchicalOrgCode extends EVK_MST {

	private String kAI_NAME_S = "";

	/**
	 * @return String
	 */
	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	/**
	 * @param kai_name_s
	 */
	public void setKAI_NAME_S(String kai_name_s) {
		kAI_NAME_S = kai_name_s;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("");
		buff.append("").append(super.getDPK_SSK());
		buff.append("/").append(super.getDPK_LVL_0());
		buff.append("/").append(kAI_NAME_S);
		buff.append("/").append(super.getDPK_DEP_CODE());
		buff.append("");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(super.getDPK_SSK());
		list.add(super.getDPK_LVL_0());
		list.add(kAI_NAME_S);
		list.add(super.getDPK_DEP_CODE());

		return list;
	}
}
