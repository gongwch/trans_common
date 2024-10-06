package jp.co.ais.trans.master.entity;

import java.util.*;
import java.util.Date;

public class AP_UKE_CTL extends MasterBase {
	public static final String TABLE = "AP_UKE_CTL";

	private String kAI_CODE = null;
	private Date uTK_DATE;
	private int uTK_LAST_NO;
	private Date iNP_DATE;
	private Date uPD_DATE;
	private String pRG_ID = null;
	private String uSR_ID = null;
	

	// 会社コード
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	// 受付日
	public Date getUTK_DATE() {
		return uTK_DATE;
	}

	public void setUTK_DATE(Date uTK_DATE) {
		this.uTK_DATE = uTK_DATE;
	}

	// 最終番号
	public int getUTK_LAST_NO() {
		return uTK_LAST_NO;
	}

	public void setUTK_LAST_NO(int uTK_LAST_NO) {
		this.uTK_LAST_NO = uTK_LAST_NO;
	}

	// 登録日
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	public void setINP_DATE(Date iNP_DATE) {
		this.iNP_DATE = iNP_DATE;
	}

	// 更新日
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	public void setUPD_DATE(Date uPD_DATE) {
		this.uPD_DATE = uPD_DATE;
	}
	
	// プログラムID
	public String getPRG_ID() {
		return pRG_ID;
	}

	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	// ユーザーID
	public String getUSR_ID() {
		return uSR_ID;
	}

	public void setUSR_ID(String uSR_ID) {
		this.uSR_ID = uSR_ID;
	}

	
	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/uTK_DATE=").append(uTK_DATE);
		buff.append("/uTK_LAST_NO=").append(uTK_LAST_NO);
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
		list.add(uTK_DATE);
		list.add(uTK_LAST_NO);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);

		return list;
	}
}
