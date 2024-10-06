package jp.co.ais.trans2.model.vessel;

import jp.co.ais.trans2.model.bunkertype.*;

/**
 * 船毎油種設定
 */
public class VesselBunkerType extends CM_BNKR_TYPE_MST {

	/** 船コード */
	protected String VESSEL_CODE = null;

	/**
	 * 船コードの取得
	 * 
	 * @return VESSEL_CODE 船コード
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * 船コードの設定
	 * 
	 * @param VESSEL_CODE 船コード
	 */
	public void setVESSEL_CODE(String VESSEL_CODE) {
		this.VESSEL_CODE = VESSEL_CODE;
	}

}
