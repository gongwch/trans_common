package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �v���Ѓt�B�[���h�G���e�B�e�B
 * 
 * @author zhangbo
 */
public class AppropriateCompany extends TransferBase {

	/** ��ЃR�[�h */
	private String KAI_CODE = "";

	/** ��З��� */
	private String KAI_NAME_S = "";

	/** �J�n��ЃR�[�h */
	private String strCompanyCode;

	/** �I����ЃR�[�h */
	private String endCompanyCode;

	/** �J�n�N���� */
	private Date STR_DATE;

	/** �I���N���� */
	private Date END_DATE;

	/** �{�M�ʉ݃R�[�h */
	private String CUR_CODE;

	/** ����敪�FTRUE�FREF�GFALSE�F���X�ƃt�H�b�J�X */
	private boolean blnOptKbn;

	/** �ʉ݋敪 */
	private boolean curKbn;

	/**
	 * �{�M�ʉ݃R�[�h���擾
	 * 
	 * @return �{�M�ʉ݃R�[�h
	 */
	public String getCUR_CODE() {
		return CUR_CODE;
	}

	/**
	 * �{�M�ʉ݃R�[�h��ݒ�
	 * 
	 * @param cur_code
	 */
	public void setCUR_CODE(String cur_code) {
		CUR_CODE = cur_code;
	}

	/**
	 * �I���N�������擾
	 * 
	 * @return �I���N����
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * �I���N������ݒ�
	 * 
	 * @param end_date
	 */
	public void setEND_DATE(Date end_date) {
		END_DATE = end_date;
	}

	/**
	 * ��ЃR�[�h���擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h��ݒ�
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		KAI_CODE = kai_code;
	}

	/**
	 * ��З��̂��擾
	 * 
	 * @return ��З���
	 */
	public String getKAI_NAME_S() {
		return KAI_NAME_S;
	}

	/**
	 * ��З��̂�ݒ�
	 * 
	 * @param kai_name_s
	 */
	public void setKAI_NAME_S(String kai_name_s) {
		KAI_NAME_S = kai_name_s;
	}

	/**
	 * �J�n�N�������擾
	 * 
	 * @return �J�n�N����
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * �J�n�N������ݒ�
	 * 
	 * @param str_date
	 */
	public void setSTR_DATE(Date str_date) {
		STR_DATE = str_date;
	}

	/**
	 * ����敪���擾
	 * 
	 * @return blnOptKbn
	 */
	public boolean isBlnOptKbn() {
		return blnOptKbn;
	}

	/**
	 * ����敪��ݒ�
	 * 
	 * @param blnOptKbn
	 */
	public void setBlnOptKbn(boolean blnOptKbn) {
		this.blnOptKbn = blnOptKbn;
	}

	/**
	 * �ʉ݋敪�̎擾
	 * 
	 * @return curKbn
	 */
	public boolean isCurKbn() {
		return curKbn;
	}

	/**
	 * �ʉ݋敪�̐ݒ�
	 * 
	 * @param curKbn
	 */
	public void setCurKbn(boolean curKbn) {
		this.curKbn = curKbn;
	}

	/**
	 * �I����ЃR�[�h���擾����B
	 * 
	 * @return String �I����ЃR�[�h
	 */
	public String getEndCompanyCode() {
		return endCompanyCode;
	}

	/**
	 * �I����ЃR�[�h��ݒ肷��B
	 * 
	 * @param endCompanyCode
	 */
	public void setEndCompanyCode(String endCompanyCode) {
		this.endCompanyCode = endCompanyCode;
	}

	/**
	 * �J�n��ЃR�[�h���擾����B
	 * 
	 * @return String �J�n��ЃR�[�h
	 */
	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	/**
	 * �J�n��ЃR�[�h��ݒ肷��B
	 * 
	 * @param strCompanyCode
	 */
	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

}
