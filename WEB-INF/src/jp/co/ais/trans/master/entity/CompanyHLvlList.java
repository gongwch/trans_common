package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class CompanyHLvlList extends EVK_MST {

	private static final long serialVersionUID = 297963985635686980L;

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
		if (kai_name_s == null) kai_name_s = "NULL";
		kAI_NAME_S = kai_name_s;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("");
		buff.append("").append(super.getKAI_CODE());
		buff.append("/").append(super.getDPK_SSK());
		buff.append("/").append(super.getDPK_DEP_CODE());
		buff.append("/").append(super.getDPK_LVL());
		buff.append("/").append(super.getDPK_LVL_0());
		buff.append("/").append(super.getDPK_LVL_1());
		buff.append("/").append(super.getDPK_LVL_2());
		buff.append("/").append(super.getDPK_LVL_3());
		buff.append("/").append(super.getDPK_LVL_4());
		buff.append("/").append(super.getDPK_LVL_5());
		buff.append("/").append(super.getDPK_LVL_6());
		buff.append("/").append(super.getDPK_LVL_7());
		buff.append("/").append(super.getDPK_LVL_8());
		buff.append("/").append(super.getDPK_LVL_9());
		buff.append("/").append(super.getSTR_DATE());
		buff.append("/").append(super.getEND_DATE());
		buff.append("/").append(kAI_NAME_S);
		buff.append("");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(super.getKAI_CODE());
		list.add(super.getDPK_SSK());
		list.add(super.getDPK_DEP_CODE());
		list.add(super.getDPK_LVL());
		list.add(super.getDPK_LVL_0());
		list.add(super.getDPK_LVL_1());
		list.add(super.getDPK_LVL_2());
		list.add(super.getDPK_LVL_3());
		list.add(super.getDPK_LVL_4());
		list.add(super.getDPK_LVL_5());
		list.add(super.getDPK_LVL_6());
		list.add(super.getDPK_LVL_7());
		list.add(super.getDPK_LVL_8());
		list.add(super.getDPK_LVL_9());
		list.add(super.getSTR_DATE());
		list.add(super.getEND_DATE());
		list.add(kAI_NAME_S);

		return list;
	}

}
