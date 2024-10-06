package jp.co.ais.trans2.model.vessel;

import jp.co.ais.trans.common.dt.*;

/**
 * ���q�O�q�敪Entity
 * 
 * @author AIS
 */
public class VesselCO extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �D�R�[�h */
	protected String vesselCode = null;

	/** �D���� */
	protected String vesselNames = null;

	/** ���q�O�q�敪 */
	protected String coKbn = null;

	/**
	 * @return companyCode��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCode��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return coKbn��߂��܂��B
	 */
	public String getCOKbn() {
		return coKbn;
	}

	/**
	 * @param coKbn coKbn��ݒ肵�܂��B
	 */
	public void setCOKbn(String coKbn) {
		this.coKbn = coKbn;
	}

	/**
	 * @return vesselCode��߂��܂��B
	 */
	public String getVesselCode() {
		return vesselCode;
	}

	/**
	 * @param vesselCode vesselCode��ݒ肵�܂��B
	 */
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	/**
	 * @return vesselNames��߂��܂��B
	 */
	public String getVesselNames() {
		return vesselNames;
	}

	/**
	 * @param vesselNames vesselNames��ݒ肵�܂��B
	 */
	public void setVesselNames(String vesselNames) {
		this.vesselNames = vesselNames;
	}
}
