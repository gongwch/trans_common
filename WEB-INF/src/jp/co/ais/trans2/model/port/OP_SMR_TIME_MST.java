package jp.co.ais.trans2.model.port;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �T�}�[�^�C���}�X�^
 */
public class OP_SMR_TIME_MST extends TransferBase implements Cloneable {

	/** �N���[�� */
	@Override
	public OP_SMR_TIME_MST clone() {
		try {
			return (OP_SMR_TIME_MST) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return �\����
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\nregion=").append(REGION_CODE);
		sb.append("\ttz=").append(TIME_DIFFERENCE);
		sb.append("\tfrom=").append(DateUtil.toYMDHMString(FROM_DATE));
		sb.append("\tto=").append(DateUtil.toYMDHMString(TO_DATE));

		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �N */
	protected int YEAR = 0;

	/** REGION�R�[�h */
	protected String REGION_CODE = null;

	/** REGION���� */
	protected String REGION_NAME = null;

	/** ���� */
	protected BigDecimal TIME_DIFFERENCE = null;

	/** �L������(LCT)�J�n */
	protected Date FROM_DATE = null;

	/** �L������(LCT)�I�� */
	protected Date TO_DATE = null;

	/** REMARKS */
	protected String REMARKS = null;

	/** �o�^���� */
	protected Date INP_DATE = null;

	/** �X�V���� */
	protected Date UPD_DATE = null;

	/** �v���O����ID */
	protected String PRG_ID = null;

	/** ���[�U�[ID */
	protected String USR_ID = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return KAI_CODE ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * �N�̎擾
	 * 
	 * @return YEAR �N
	 */
	public int getYEAR() {
		return YEAR;
	}

	/**
	 * �N�̐ݒ�
	 * 
	 * @param YEAR �N
	 */
	public void setYEAR(int YEAR) {
		this.YEAR = YEAR;
	}

	/**
	 * REGION�R�[�h�̎擾
	 * 
	 * @return REGION_CODE REGION�R�[�h
	 */
	public String getREGION_CODE() {
		return REGION_CODE;
	}

	/**
	 * REGION�R�[�h�̐ݒ�
	 * 
	 * @param REGION_CODE REGION�R�[�h
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * REGION���̂̎擾
	 * 
	 * @return REGION_NAME REGION����
	 */
	public String getREGION_NAME() {
		return REGION_NAME;
	}

	/**
	 * REGION���̂̐ݒ�
	 * 
	 * @param REGION_NAME REGION����
	 */
	public void setREGION_NAME(String REGION_NAME) {
		this.REGION_NAME = REGION_NAME;
	}

	/**
	 * �����̎擾
	 * 
	 * @return TIME_DIFFERENCE ����
	 */
	public BigDecimal getTIME_DIFFERENCE() {
		return TIME_DIFFERENCE;
	}

	/**
	 * �����̐ݒ�
	 * 
	 * @param TIME_DIFFERENCE ����
	 */
	public void setTIME_DIFFERENCE(BigDecimal TIME_DIFFERENCE) {
		this.TIME_DIFFERENCE = TIME_DIFFERENCE;
	}

	/**
	 * �L������(LCT)�J�n�̎擾
	 * 
	 * @return FROM_DATE �L������(LCT)�J�n
	 */
	public Date getFROM_DATE() {
		return FROM_DATE;
	}

	/**
	 * �L������(LCT)�J�n�̐ݒ�
	 * 
	 * @param FROM_DATE �L������(LCT)�J�n
	 */
	public void setFROM_DATE(Date FROM_DATE) {
		this.FROM_DATE = FROM_DATE;
	}

	/**
	 * �L������(LCT)�I���̎擾
	 * 
	 * @return TO_DATE �L������(LCT)�I��
	 */
	public Date getTO_DATE() {
		return TO_DATE;
	}

	/**
	 * �L������(LCT)�I���̐ݒ�
	 * 
	 * @param TO_DATE �L������(LCT)�I��
	 */
	public void setTO_DATE(Date TO_DATE) {
		this.TO_DATE = TO_DATE;
	}

	/**
	 * REMARKS�̎擾
	 * 
	 * @return REMARKS REMARKS
	 */
	public String getREMARKS() {
		return REMARKS;
	}

	/**
	 * REMARKS�̐ݒ�
	 * 
	 * @param REMARKS REMARKS
	 */
	public void setREMARKS(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	/**
	 * �o�^�����̎擾
	 * 
	 * @return INP_DATE �o�^����
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^�����̐ݒ�
	 * 
	 * @param INP_DATE �o�^����
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V�����̎擾
	 * 
	 * @return UPD_DATE �X�V����
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V�����̐ݒ�
	 * 
	 * @param UPD_DATE �X�V����
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * �v���O����ID�̎擾
	 * 
	 * @return PRG_ID �v���O����ID
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O����ID�̐ݒ�
	 * 
	 * @param PRG_ID �v���O����ID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ���[�U�[ID�̎擾
	 * 
	 * @return USR_ID ���[�U�[ID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[ID�̐ݒ�
	 * 
	 * @param USR_ID ���[�U�[ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
