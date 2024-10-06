package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �p�X���[�h�����Ǘ��}�X�^
 * 
 * @author roh
 */
public class PWD_HST_TBL extends TransferBase {

	/**  */
	public static final String TABLE = "PWD_HST_TBL";

	/** ��ЃR�[�h */
	private String kAI_CODE;

	/** ���[�U�R�[�h */
	private String uSR_CODE;

	/** �X�V���t�� */
	private Date iNP_DATE;

	/** �p�X���[�h */
	private String pASSWORD;

	/**
	 * ���͓��t���K��
	 * 
	 * @return ���͓��t��
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * ���͓��t���ݒ�
	 * 
	 * @param inp_date
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * ��ЃR�[�h�K��
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * �p�X���[�h�K��
	 * 
	 * @return �p�X���[�h
	 */
	public String getPASSWORD() {
		return pASSWORD;
	}

	/**
	 * �p�X���[�h�ݒ�
	 * 
	 * @param password
	 */
	public void setPASSWORD(String password) {
		pASSWORD = password;
	}

	/**
	 * ���[�U�R�[�h�K��
	 * 
	 * @return ���[�U�R�[�h
	 */
	public String getUSR_CODE() {
		return uSR_CODE;
	}

	/**
	 * ���[�U�R�[�h�ݒ�
	 * 
	 * @param usr_code
	 */
	public void setUSR_CODE(String usr_code) {
		uSR_CODE = usr_code;
	}

}
