package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 
 */
public class PCI_CHK_CTL extends MasterBase {

	/**  */
	public static final String TABLE = "PCI_CHK_CTL";

	/** ユーザ名称設定（参照系クエリ用） */
	public static final String insert_NO_PERSISTENT_PROPS = "uSR_NAME";

	private String kAI_CODE;

	private String pRG_ID;

	private String pCI_CLIENT_NAME;

	private Date pCI_CHECK_IN_TIME;

	/** ユーザ名称 */
	private String uSR_NAME;

	/**
	 * ユーザ名称取得
	 * 
	 * @return ユーザ名称
	 */
	public String getUSR_NAME() {
		return uSR_NAME;
	}

	/**
	 * ユーザ名称設定
	 * 
	 * @param usr_name ユーザ名称
	 */
	public void setUSR_NAME(String usr_name) {
		uSR_NAME = usr_name;
	}

	/**
	 * @return String
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	/**
	 * @return String
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * @param pRG_ID
	 */
	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	/**
	 * @return String
	 */
	public String getPCI_CLIENT_NAME() {
		return pCI_CLIENT_NAME;
	}

	/**
	 * @param pCI_CLIENT_NAME
	 */
	public void setPCI_CLIENT_NAME(String pCI_CLIENT_NAME) {
		this.pCI_CLIENT_NAME = pCI_CLIENT_NAME;
	}

	/**
	 * @return Date
	 */
	public Date getPCI_CHECK_IN_TIME() {
		return pCI_CHECK_IN_TIME;
	}

	/**
	 * @param pCI_CHECK_IN_TIME
	 */
	public void setPCI_CHECK_IN_TIME(Date pCI_CHECK_IN_TIME) {
		this.pCI_CHECK_IN_TIME = pCI_CHECK_IN_TIME;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/pCI_CLIENT_NAME=").append(pCI_CLIENT_NAME);
		buff.append("/pCI_CHECK_IN_TIME=").append(pCI_CHECK_IN_TIME);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(pCI_CLIENT_NAME);
		list.add(pCI_CHECK_IN_TIME);

		return list;
	}
}
