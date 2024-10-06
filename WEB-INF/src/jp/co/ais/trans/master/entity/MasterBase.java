package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.server.*;

/**
 * 
 */
public abstract class MasterBase extends TransferBase implements TInterfaceHasToObjectArray {

	protected Date iNP_DATE;

	protected Date uPD_DATE;

	protected String uSR_ID;

	/**
	 * @return iNP_DATE
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * @param iNP_DATE
	 */
	public void setINP_DATE(Date iNP_DATE) {
		this.iNP_DATE = iNP_DATE;
	}

	/**
	 * @return uPD_DATE
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * @param uPD_DATE
	 */
	public void setUPD_DATE(Date uPD_DATE) {
		this.uPD_DATE = uPD_DATE;
	}

	/**
	 * @return uSR_ID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * @param uSR_ID
	 */
	public void setUSR_ID(String uSR_ID) {
		this.uSR_ID = uSR_ID;
	}

}
