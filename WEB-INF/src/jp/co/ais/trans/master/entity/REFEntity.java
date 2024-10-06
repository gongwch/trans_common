package jp.co.ais.trans.master.entity;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.server.*;

/**
 * 
 */
public class REFEntity implements TInterfaceHasToObjectArray,Serializable {

	private String code;

	private String name_S;

	private String name_K;

	/**
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param value
	 */
	public void setCode(String value) {
		code = value;
	}

	/**
	 * @return String
	 */
	public String getName_S() {
		return name_S;
	}

	/**
	 * @param value
	 */
	public void setName_S(String value) {
		name_S = value;
	}

	/**
	 * @return String
	 */
	public String getName_K() {
		return name_K;
	}

	/**
	 * @param value
	 */
	public void setName_K(String value) {
		name_K = value;
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();
		list.add(this.getCode());
		list.add(this.getName_S());
		list.add(this.getName_K());
		return list;
	}

}
