package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �C��R������ʃ}�X�^(CM_VESSEL_SEA_CONS_MST)
 */
public class VesselSeaCons extends TransferBase implements Cloneable {

	/** �N���[�� */
	@Override
	public VesselSeaCons clone() {
		try {
			return (VesselSeaCons) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("KAI_CODE=");
		sb.append(KAI_CODE);
		sb.append("/VESSEL_CODE=");
		sb.append(VESSEL_CODE);
		sb.append("/SPD_TYPE_CODE=");
		sb.append(SPD_TYPE_CODE);
		sb.append("/USING_PU_KBN=");
		sb.append(USING_PU_KBN);
		sb.append("/SEA_CONS_BAL=");
		sb.append(SEA_CONS_BAL);
		sb.append("/SEA_CONS_LAD=");
		sb.append(SEA_CONS_LAD);
		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** VESSEL_CODE */
	protected String VESSEL_CODE = null;

	/** SPEED�^�C�v�R�[�h */
	protected String SPD_TYPE_CODE = null;

	/** �g�p�ړI�敪 */
	protected int USING_PU_KBN = 0;

	/** �C��R�������(BALLAST) */
	protected BigDecimal SEA_CONS_BAL = null;

	/** �C��R�������(LADEN) */
	protected BigDecimal SEA_CONS_LAD = null;

	/** �J�n�N���� */
	protected Date STR_DATE = null;

	/** �I���N���� */
	protected Date END_DATE = null;

	/** �o�^�N���� */
	protected Date INP_DATE = null;

	/** �X�V�N���� */
	protected Date UPD_DATE = null;

	/** �v���O����ID */
	protected String PRG_ID = null;

	/** ���[�U�[ID */
	protected String USR_ID = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return KAI_CODE ��ЃR�[�h�̎擾
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param KAI_CODE ��ЃR�[�h�̐ݒ�
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * VESSEL_CODE�̎擾
	 * 
	 * @return VESSEL_CODE VESSEL_CODE�̎擾
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * VESSEL_CODE�̐ݒ�
	 * 
	 * @param VESSEL_CODE VESSEL_CODE�̐ݒ�
	 */
	public void setVESSEL_CODE(String VESSEL_CODE) {
		this.VESSEL_CODE = VESSEL_CODE;
	}

	/**
	 * SPEED�^�C�v�R�[�h�̎擾
	 * 
	 * @return SPD_TYPE_CODE SPEED�^�C�v�R�[�h�̎擾
	 */
	public String getSPD_TYPE_CODE() {
		return SPD_TYPE_CODE;
	}

	/**
	 * SPEED�^�C�v�R�[�h�̐ݒ�
	 * 
	 * @param SPD_TYPE_CODE SPEED�^�C�v�R�[�h�̐ݒ�
	 */
	public void setSPD_TYPE_CODE(String SPD_TYPE_CODE) {
		this.SPD_TYPE_CODE = SPD_TYPE_CODE;
	}

	/**
	 * �g�p�ړI�敪�̎擾
	 * 
	 * @return USING_PU_KBN �g�p�ړI�敪
	 */
	public int getUSING_PU_KBN() {
		return USING_PU_KBN;
	}

	/**
	 * �g�p�ړI�敪�̐ݒ�
	 * 
	 * @param USING_PU_KBN �g�p�ړI�敪
	 */
	public void setUSING_PU_KBN(int USING_PU_KBN) {
		this.USING_PU_KBN = USING_PU_KBN;
	}

	/**
	 * �C��R�������(BALLAST)�̎擾
	 * 
	 * @return SEA_CONS_BAL �C��R�������(BALLAST)�̎擾
	 */
	public BigDecimal getSEA_CONS_BAL() {
		return SEA_CONS_BAL;
	}

	/**
	 * �C��R�������(BALLAST)�̐ݒ�
	 * 
	 * @param SEA_CONS_BAL �C��R�������(BALLAST)�̐ݒ�
	 */
	public void setSEA_CONS_BAL(BigDecimal SEA_CONS_BAL) {
		this.SEA_CONS_BAL = SEA_CONS_BAL;
	}

	/**
	 * �C��R�������(LADEN)�̎擾
	 * 
	 * @return SEA_CONS_LAD �C��R�������(LADEN)�̎擾
	 */
	public BigDecimal getSEA_CONS_LAD() {
		return SEA_CONS_LAD;
	}

	/**
	 * �C��R�������(LADEN)�̐ݒ�
	 * 
	 * @param SEA_CONS_LAD �C��R�������(LADEN)�̐ݒ�
	 */
	public void setSEA_CONS_LAD(BigDecimal SEA_CONS_LAD) {
		this.SEA_CONS_LAD = SEA_CONS_LAD;
	}

	/**
	 * �J�n�N�����̎擾
	 * 
	 * @return STR_DATE �J�n�N�����̎擾
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * �J�n�N�����̐ݒ�
	 * 
	 * @param STR_DATE �J�n�N�����̐ݒ�
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * �I���N�����̎擾
	 * 
	 * @return END_DATE �I���N�����̎擾
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * �I���N�����̐ݒ�
	 * 
	 * @param END_DATE �I���N�����̐ݒ�
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * �o�^�N�����̎擾
	 * 
	 * @return INP_DATE �o�^�N�����̎擾
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^�N�����̐ݒ�
	 * 
	 * @param INP_DATE �o�^�N�����̐ݒ�
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V�N�����̎擾
	 * 
	 * @return UPD_DATE �X�V�N�����̎擾
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V�N�����̐ݒ�
	 * 
	 * @param UPD_DATE �X�V�N�����̐ݒ�
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * �v���O����ID�̎擾
	 * 
	 * @return PRG_ID �v���O����ID�̎擾
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O����ID�̐ݒ�
	 * 
	 * @param PRG_ID �v���O����ID�̐ݒ�
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ���[�U�[ID�̎擾
	 * 
	 * @return USR_ID ���[�U�[ID�̎擾
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[ID�̐ݒ�
	 * 
	 * @param USR_ID ���[�U�[ID�̐ݒ�
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
