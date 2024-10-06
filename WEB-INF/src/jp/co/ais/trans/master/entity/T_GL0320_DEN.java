package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class T_GL0320_DEN extends MasterBase {

	/**  */
	public static final String TABLE = "T_GL0320_DEN";

	private String kAICODE;

	private String dENNO;

	private String dENTABLE;

	/**
	 * @return String
	 */
	public String getKAICODE() {
		return kAICODE;
	}

	/**
	 * @param kAICODE
	 */
	public void setKAICODE(String kAICODE) {
		this.kAICODE = kAICODE;
	}

	/**
	 * @return String
	 */
	public String getDENNO() {
		return dENNO;
	}

	/**
	 * @param dENNO
	 */
	public void setDENNO(String dENNO) {
		this.dENNO = dENNO;
	}

	/**
	 * @return String
	 */
	public String getDENTABLE() {
		return dENTABLE;
	}

	/**
	 * @param dENTABLE
	 */
	public void setDENTABLE(String dENTABLE) {
		this.dENTABLE = dENTABLE;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAICODE=").append(kAICODE);
		buff.append("/dENNO=").append(dENNO);
		buff.append("/dENTABLE=").append(dENTABLE);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAICODE);
		list.add(dENNO);
		list.add(dENTABLE);

		return list;
	}
}
