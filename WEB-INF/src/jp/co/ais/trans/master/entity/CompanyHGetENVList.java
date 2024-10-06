package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class CompanyHGetENVList extends ENV_MST {

	private String kAI_NAME_S = "";

	public String toString() {
		StringBuffer buff = new StringBuffer("");
		buff.append("").append(super.getKAI_CODE());
		buff.append("").append(super.getSTR_DATE());
		buff.append("").append(super.getEND_DATE());
		buff.append("/").append(kAI_NAME_S);
		buff.append("");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(super.getKAI_CODE());
		list.add(super.getSTR_DATE());
		list.add(super.getEND_DATE());
		list.add(kAI_NAME_S);

		return list;
	}

	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	public void setKAI_NAME_S(String kai_name_s) {
		if (kai_name_s == null) kai_name_s = "NULL";
		kAI_NAME_S = kai_name_s;
	}

}
