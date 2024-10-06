package jp.co.ais.trans2.model.mlit.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �A������<br>
 * �A�C�e��Bean
 */
public class YJItem extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE;

	/** �A�C�e���R�[�h */
	protected String ITEM_CODE;

	/** �A�C�e������ */
	protected String ITEM_NAME;

	/** ���l */
	protected String REMARK;

	/** �A�C�e���⏕�R�[�h */
	protected String ITEM_SUB_CODE;

	/** �A�C�e���⏕���� */
	protected String ITEM_SUB_NAME;

	/** ���l�i�⏕�j */
	protected String SUB_REMARK;

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
	 * �A�C�e���R�[�h���擾����
	 * 
	 * @return �A�C�e���R�[�h
	 */
	public String getITEM_CODE() {
		return this.ITEM_CODE;
	}

	/**
	 * �A�C�e���R�[�h��ݒ�
	 * 
	 * @param ITEM_CODE �A�C�e���R�[�h
	 */
	public void setITEM_CODE(String ITEM_CODE) {
		this.ITEM_CODE = ITEM_CODE;
	}

	/**
	 * �A�C�e�����̂��擾����
	 * 
	 * @return �A�C�e������
	 */
	public String getITEM_NAME() {
		return this.ITEM_NAME;
	}

	/**
	 * �A�C�e�����̂�ݒ�
	 * 
	 * @param ITEM_NAME �A�C�e������
	 */
	public void setITEM_NAME(String ITEM_NAME) {
		this.ITEM_NAME = ITEM_NAME;
	}

	/**
	 * ���l���擾����
	 * 
	 * @return ���l
	 */
	public String getREMARK() {
		return this.REMARK;
	}

	/**
	 * ���l��ݒ�
	 * 
	 * @param REMARK ���l
	 */
	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}

	/**
	 * �A�C�e���⏕�R�[�h���擾����
	 * 
	 * @return �A�C�e���⏕�R�[�h
	 */
	public String getITEM_SUB_CODE() {
		return this.ITEM_SUB_CODE;
	}

	/**
	 * �A�C�e���⏕�R�[�h��ݒ�
	 * 
	 * @param ITEM_SUB_CODE �A�C�e���⏕�R�[�h
	 */
	public void setITEM_SUB_CODE(String ITEM_SUB_CODE) {
		this.ITEM_SUB_CODE = ITEM_SUB_CODE;
	}

	/**
	 * �A�C�e���⏕���̂��擾����
	 * 
	 * @return �A�C�e���⏕����
	 */
	public String getITEM_SUB_NAME() {
		return this.ITEM_SUB_NAME;
	}

	/**
	 * �A�C�e���⏕���̂�ݒ�
	 * 
	 * @param ITEM_SUB_NAME �A�C�e���⏕����
	 */
	public void setITEM_SUB_NAME(String ITEM_SUB_NAME) {
		this.ITEM_SUB_NAME = ITEM_SUB_NAME;
	}

	/**
	 * ���l�i�⏕�j���擾����
	 * 
	 * @return ���l�i�⏕�j
	 */
	public String getSUB_REMARK() {
		return this.SUB_REMARK;
	}

	/**
	 * ���l�i�⏕�j��ݒ�
	 * 
	 * @param SUB_REMARK ���l�i�⏕�j
	 */
	public void setSUB_REMARK(String SUB_REMARK) {
		this.SUB_REMARK = SUB_REMARK;
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
