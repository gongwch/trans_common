package jp.co.ais.trans.master.entity;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * 削除データ
 */
public class DEL_DTL extends MasterBase {

	/**  */
	public static final String TABLE = "DEL_DTL";

	private String kAI_CODE = "";

	private Date dEL_DEN_DATE;

	private String dEL_DEN_NO = "";

	private String pRG_ID;

	/**
	 * @return int
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
	 * @return Date
	 */
	public Date getDEL_DEN_DATE() {
		return dEL_DEN_DATE;
	}

	/**
	 * @param dEL_DEN_DATE
	 */
	public void setDEL_DEN_DATE(Date dEL_DEN_DATE) {
		this.dEL_DEN_DATE = dEL_DEN_DATE;
	}

	/**
	 * @return int
	 */
	public String getDEL_DEN_NO() {
		return dEL_DEN_NO;
	}

	/**
	 * @param dEL_DEN_NO
	 */
	public void setDEL_DEN_NO(String dEL_DEN_NO) {
		this.dEL_DEN_NO = dEL_DEN_NO;
	}

	/**
	 * @return int
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

	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/dEL_DEN_DATE=").append(dEL_DEN_DATE);
		buff.append("/dEL_DEN_NO=").append(dEL_DEN_NO);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("]");
		return buff.toString();
	}

	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(dEL_DEN_DATE);
		list.add(dEL_DEN_NO);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}

	/**
	 * ObjectArrayから状態を構築する<br>
	 * (toObjectArray()でリスト状態にしたものを元に戻す)
	 * 
	 * @param list リスト
	 * @throws ParseException
	 */
	public void reflectFromArray(List<String> list) throws ParseException {
		this.kAI_CODE = list.get(0);
		this.dEL_DEN_DATE = DateUtil.toYMDDate(list.get(1));
		this.dEL_DEN_NO = list.get(2);
		this.iNP_DATE = DateUtil.toYMDDate(list.get(3));
		this.uPD_DATE = DateUtil.toYMDDate(list.get(4));
		this.pRG_ID = list.get(5);
		this.uSR_ID = list.get(6);
	}
}
