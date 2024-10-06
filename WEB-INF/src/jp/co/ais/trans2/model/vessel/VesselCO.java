package jp.co.ais.trans2.model.vessel;

import jp.co.ais.trans.common.dt.*;

/**
 * 内航外航区分Entity
 * 
 * @author AIS
 */
public class VesselCO extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** 船コード */
	protected String vesselCode = null;

	/** 船略称 */
	protected String vesselNames = null;

	/** 内航外航区分 */
	protected String coKbn = null;

	/**
	 * @return companyCodeを戻します。
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return coKbnを戻します。
	 */
	public String getCOKbn() {
		return coKbn;
	}

	/**
	 * @param coKbn coKbnを設定します。
	 */
	public void setCOKbn(String coKbn) {
		this.coKbn = coKbn;
	}

	/**
	 * @return vesselCodeを戻します。
	 */
	public String getVesselCode() {
		return vesselCode;
	}

	/**
	 * @param vesselCode vesselCodeを設定します。
	 */
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	/**
	 * @return vesselNamesを戻します。
	 */
	public String getVesselNames() {
		return vesselNames;
	}

	/**
	 * @param vesselNames vesselNamesを設定します。
	 */
	public void setVesselNames(String vesselNames) {
		this.vesselNames = vesselNames;
	}
}
