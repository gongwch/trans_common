package jp.co.ais.trans2.model.mlit.region;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �A������<br>
 * ��Bean
 */
public class YJRegion extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE;

	/** ���R�[�h */
	protected String REGION_CODE;

	/** ������ */
	protected String REGION_NAME;

	/** ���l */
	protected String REGION_REMARK;

	/** �n��R�[�h */
	protected String COUNTRY_CODE;

	/** �n�於�� */
	protected String COUNTRY_NAME;

	/** ���l�i�n��j */
	protected String COUNTRY_REMARK;

	/** �o�^���t */
	protected Date INP_DATE;

	/** �X�V���t */
	protected Date UPD_DATE;

	/** Program ID */
	protected String PRG_ID;

	/** User ID */
	protected String USR_ID;

	/**
	 * ��ЃR�[�h ���擾����
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return this.KAI_CODE;
	}

	/**
	 * ��ЃR�[�h ��ݒ�
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * ���R�[�h���擾����
	 * 
	 * @return ���R�[�h
	 */
	public String getREGION_CODE() {
		return this.REGION_CODE;
	}

	/**
	 * ���R�[�h��ݒ�
	 * 
	 * @param REGION_CODE ���R�[�h
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * �����̂��擾����
	 * 
	 * @return ������
	 */
	public String getREGION_NAME() {
		return this.REGION_NAME;
	}

	/**
	 * �����̂�ݒ�
	 * 
	 * @param REGION_NAME ������
	 */
	public void setREGION_NAME(String REGION_NAME) {
		this.REGION_NAME = REGION_NAME;
	}

	/**
	 * ���l���擾����
	 * 
	 * @return ���l
	 */
	public String getREGION_REMARK() {
		return this.REGION_REMARK;
	}

	/**
	 * ���l��ݒ�
	 * 
	 * @param REGION_REMARK ���l
	 */
	public void setREGION_REMARK(String REGION_REMARK) {
		this.REGION_REMARK = REGION_REMARK;
	}

	/**
	 * �n��R�[�h���擾����
	 * 
	 * @return �n��R�[�h
	 */
	public String getCOUNTRY_CODE() {
		return this.COUNTRY_CODE;
	}

	/**
	 * �n��R�[�h��ݒ�
	 * 
	 * @param COUNTRY_CODE �n��R�[�h
	 */
	public void setCOUNTRY_CODE(String COUNTRY_CODE) {
		this.COUNTRY_CODE = COUNTRY_CODE;
	}

	/**
	 * �n�於�̂��擾����
	 * 
	 * @return �n�於��
	 */
	public String getCOUNTRY_NAME() {
		return this.COUNTRY_NAME;
	}

	/**
	 * �n�於�̂�ݒ�
	 * 
	 * @param COUNTRY_NAME �n�於��
	 */
	public void setCOUNTRY_NAME(String COUNTRY_NAME) {
		this.COUNTRY_NAME = COUNTRY_NAME;
	}

	/**
	 * ���l�i�n��j���擾����
	 * 
	 * @return ���l�i�n��j
	 */
	public String getCOUNTRY_REMARK() {
		return this.COUNTRY_REMARK;
	}

	/**
	 * ���l�i�n��j��ݒ�
	 * 
	 * @param COUNTRY_REMARK ���l�i�n��j
	 */
	public void setCOUNTRY_REMARK(String COUNTRY_REMARK) {
		this.COUNTRY_REMARK = COUNTRY_REMARK;
	}

	/**
	 * �o�^���t���擾����
	 * 
	 * @return �o�^���t
	 */
	public Date getINP_DATE() {
		return this.INP_DATE;
	}

	/**
	 * �o�^���t��ݒ�
	 * 
	 * @param INP_DATE �o�^���t
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V���t���擾����
	 * 
	 * @return �X�V���t
	 */
	public Date getUPD_DATE() {
		return this.UPD_DATE;
	}

	/**
	 * �X�V���t��ݒ�
	 * 
	 * @param UPD_DATE �X�V���t
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * Program ID���擾����
	 * 
	 * @return Program ID
	 */
	public String getPRG_ID() {
		return this.PRG_ID;
	}

	/**
	 * Program ID��ݒ�
	 * 
	 * @param PRG_ID Program ID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * User ID���擾����
	 * 
	 * @return User ID
	 */
	public String getUSR_ID() {
		return this.USR_ID;
	}

	/**
	 * User ID��ݒ�
	 * 
	 * @param USR_ID User ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
