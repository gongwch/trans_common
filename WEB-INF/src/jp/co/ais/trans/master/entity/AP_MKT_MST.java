package jp.co.ais.trans.master.entity;

import java.util.*;

public class AP_MKT_MST extends MasterBase {
	public static final String TABLE = "AP_MKT_MST";

	private String mKT_CODE = "";

	private String mKT_NAME;

	public String getMKT_CODE() {
		return mKT_CODE;
	}

	public void setMKT_CODE(String mkt_code) {
		mKT_CODE = mkt_code;
	}

	public String getMKT_NAME() {
		return mKT_NAME;
	}

	public void setMKT_NAME(String mkt_name) {
		mKT_NAME = mkt_name;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/mKT_CODE=").append(mKT_CODE);
		buff.append("/mKT_NAME=").append(mKT_NAME);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(mKT_CODE);
		list.add(mKT_NAME);

		return list;
	}
}
