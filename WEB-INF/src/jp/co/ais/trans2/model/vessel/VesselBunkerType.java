package jp.co.ais.trans2.model.vessel;

import jp.co.ais.trans2.model.bunkertype.*;

/**
 * �D������ݒ�
 */
public class VesselBunkerType extends CM_BNKR_TYPE_MST {

	/** �D�R�[�h */
	protected String VESSEL_CODE = null;

	/**
	 * �D�R�[�h�̎擾
	 * 
	 * @return VESSEL_CODE �D�R�[�h
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * �D�R�[�h�̐ݒ�
	 * 
	 * @param VESSEL_CODE �D�R�[�h
	 */
	public void setVESSEL_CODE(String VESSEL_CODE) {
		this.VESSEL_CODE = VESSEL_CODE;
	}

}
