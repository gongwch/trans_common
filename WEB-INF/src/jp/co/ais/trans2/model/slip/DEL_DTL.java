package jp.co.ais.trans2.model.slip;

import java.util.*;
import jp.co.ais.trans.common.dt.*;

/**
 * �폜�`�[�f�[�^
 */
public class DEL_DTL extends TransferBase {

	/**  */
	public static final String TABLE = "DEL_DTL";

	/** ��ЃR�[�h */
	protected String kAI_CODE = "";

	/** �`�[���t */
	protected Date dEL_DEN_DATE;

	/** �`�[�ԍ� */
	protected String dEL_DEN_NO = "";

	/** �o�^���t */
	protected Date iNP_DATE;

	/** �X�V���t */
	protected Date uPD_DATE;

	/** ���[�U�[�h�c */
	protected String uSR_ID = "";

	/** �v���O�����h�c */
	protected String pRG_ID;

	/**
	 * dEL_DEN_DATE���擾����B
	 * 
	 * @return Date dEL_DEN_DATE
	 */
	public Date getDEL_DEN_DATE() {
		return dEL_DEN_DATE;
	}

	/**
	 * dEL_DEN_DATE��ݒ肷��B
	 * 
	 * @param del_den_date
	 */
	public void setDEL_DEN_DATE(Date del_den_date) {
		dEL_DEN_DATE = del_den_date;
	}

	/**
	 * dEL_DEN_NO���擾����B
	 * 
	 * @return String dEL_DEN_NO
	 */
	public String getDEL_DEN_NO() {
		return dEL_DEN_NO;
	}

	/**
	 * dEL_DEN_NO��ݒ肷��B
	 * 
	 * @param del_den_no
	 */
	public void setDEL_DEN_NO(String del_den_no) {
		dEL_DEN_NO = del_den_no;
	}

	/**
	 * iNP_DATE���擾����B
	 * 
	 * @return String iNP_DATE
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * iNP_DATE��ݒ肷��B
	 * 
	 * @param inp_date
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * kAI_CODE���擾����B
	 * 
	 * @return String kAI_CODE
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * kAI_CODE��ݒ肷��B
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * pRG_ID���擾����B
	 * 
	 * @return String pRG_ID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * pRG_ID��ݒ肷��B
	 * 
	 * @param prg_id
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * uPD_DATE���擾����B
	 * 
	 * @return String uPD_DATE
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * uPD_DATE��ݒ肷��B
	 * 
	 * @param upd_date
	 */
	public void setUPD_DATE(Date upd_date) {
		uPD_DATE = upd_date;
	}

	/**
	 * uSR_ID���擾����B
	 * 
	 * @return String uSR_ID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * uSR_ID��ݒ肷��B
	 * 
	 * @param usr_id
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

}
