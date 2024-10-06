package jp.co.ais.trans.master.entity;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.server.*;

/**
 * 
 */
public class StringTransferObject implements TInterfaceHasToObjectArray,Serializable {

	private String value;

	/**
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public List<Object> toObjectArray() {
		List list = new ArrayList(1);
		list.add(this.value);
		return list;
	}
}
