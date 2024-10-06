package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * Vessel hold master
 * 
 * @author Ngoc Nguyen
 */
public class VesselHold extends TransferBase implements Cloneable {

	/**
	 * �N���[��.
	 * 
	 * @return the OP vessel hold
	 */
	@Override
	public VesselHold clone() {
		try {
			return (VesselHold) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return return a string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("KAI_CODE=");
		sb.append(KAI_CODE);
		sb.append("/VESSEL_CODE=");
		sb.append(VESSEL_CODE);
		sb.append("/HOLD_NO=");
		sb.append(HOLD_NO);
		return sb.toString();
	}

	/** ��ЃR�[�h. */
	protected String KAI_CODE = null;

	/** VESSEL�R�[�h. */
	protected String VESSEL_CODE = null;

	/** HOLD NO. */
	protected String HOLD_NO = null;

	/** �\����. */
	protected int DISP_ODR = -1;

	/** HOLD NAME. */
	protected String HOLD_NAME = null;

	/** CENTER GRN. */
	protected BigDecimal CENTER_GRN = null;

	/** CENTER BALE. */
	protected BigDecimal CENTER_BALE = null;

	/** LEN BRE. */
	protected String LEN_BRE;

	/** TYPE SET. */
	protected String TYPE_SET;

	/** �J�n�N����. */
	protected Date STR_DATE = null;

	/** �I���N����. */
	protected Date END_DATE = null;

	/** �o�^�N����. */
	protected Date INP_DATE = null;

	/** �X�V�N����. */
	protected Date UPD_DATE = null;

	/** �v���O����ID. */
	protected String PRG_ID = null;

	/** ���[�U�[ID. */
	protected String USR_ID = null;

	/**
	 * ��ЃR�[�h�̎擾.
	 * 
	 * @return KAI_CODE ��ЃR�[�h�̎擾
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�.
	 * 
	 * @param KAI_CODE ��ЃR�[�h�̐ݒ�
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * VESSEL�R�[�h�̎擾.
	 * 
	 * @return VESSEL_CODE VESSEL�R�[�h�̎擾
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * VESSEL�R�[�h�̐ݒ�.
	 * 
	 * @param VESSEL_CODE VESSEL�R�[�h�̐ݒ�
	 */
	public void setVESSEL_CODE(String VESSEL_CODE) {
		this.VESSEL_CODE = VESSEL_CODE;
	}

	/**
	 * �\�����̎擾.
	 * 
	 * @return DISP_ODR �\�����̎擾
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * �\�����̐ݒ�.
	 * 
	 * @param DISP_ODR �\�����̐ݒ�
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * Gets the hold no.
	 * 
	 * @return the hold no
	 */
	public String getHOLD_NO() {
		return HOLD_NO;
	}

	/**
	 * Sets the hold no.
	 * 
	 * @param hOLD_NO the new hold no
	 */
	public void setHOLD_NO(String hOLD_NO) {
		HOLD_NO = hOLD_NO;
	}

	/**
	 * Gets the hold name.
	 * 
	 * @return the hold name
	 */
	public String getHOLD_NAME() {
		return HOLD_NAME;
	}

	/**
	 * Sets the hold name.
	 * 
	 * @param hOLD_NAME the new hold name
	 */
	public void setHOLD_NAME(String hOLD_NAME) {
		HOLD_NAME = hOLD_NAME;
	}

	/**
	 * Gets the center grn.
	 * 
	 * @return the center grn
	 */
	public BigDecimal getCENTER_GRN() {
		return CENTER_GRN;
	}

	/**
	 * Sets the center grn.
	 * 
	 * @param cENTER_GRN the new center grn
	 */
	public void setCENTER_GRN(BigDecimal cENTER_GRN) {
		CENTER_GRN = cENTER_GRN;
	}

	/**
	 * Gets the center bale.
	 * 
	 * @return the center bale
	 */
	public BigDecimal getCENTER_BALE() {
		return CENTER_BALE;
	}

	/**
	 * Sets the center bale.
	 * 
	 * @param cENTER_BALE the new center bale
	 */
	public void setCENTER_BALE(BigDecimal cENTER_BALE) {
		CENTER_BALE = cENTER_BALE;
	}

	/**
	 * Gets the len bre.
	 * 
	 * @return the len bre
	 */
	public String getLEN_BRE() {
		return LEN_BRE;
	}

	/**
	 * Sets the len bre.
	 * 
	 * @param lEN_BRE the new len bre
	 */
	public void setLEN_BRE(String lEN_BRE) {
		LEN_BRE = lEN_BRE;
	}

	/**
	 * Gets the type set.
	 * 
	 * @return the type set
	 */
	public String getTYPE_SET() {
		return TYPE_SET;
	}

	/**
	 * Sets the type set.
	 * 
	 * @param tYPE_SET the new type set
	 */
	public void setTYPE_SET(String tYPE_SET) {
		TYPE_SET = tYPE_SET;
	}

	/**
	 * �J�n�N�����̎擾.
	 * 
	 * @return STR_DATE �J�n�N�����̎擾
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * �J�n�N�����̐ݒ�.
	 * 
	 * @param STR_DATE �J�n�N�����̐ݒ�
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * �I���N�����̎擾.
	 * 
	 * @return END_DATE �I���N�����̎擾
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * �I���N�����̐ݒ�.
	 * 
	 * @param END_DATE �I���N�����̐ݒ�
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * �o�^�N�����̎擾.
	 * 
	 * @return INP_DATE �o�^�N�����̎擾
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^�N�����̐ݒ�.
	 * 
	 * @param INP_DATE �o�^�N�����̐ݒ�
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V�N�����̎擾.
	 * 
	 * @return UPD_DATE �X�V�N�����̎擾
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V�N�����̐ݒ�.
	 * 
	 * @param UPD_DATE �X�V�N�����̐ݒ�
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * �v���O����ID�̎擾.
	 * 
	 * @return PRG_ID �v���O����ID�̎擾
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O����ID�̐ݒ�.
	 * 
	 * @param PRG_ID �v���O����ID�̐ݒ�
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ���[�U�[ID�̎擾.
	 * 
	 * @return USR_ID ���[�U�[ID�̎擾
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[ID�̐ݒ�.
	 * 
	 * @param USR_ID ���[�U�[ID�̐ݒ�
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}
}
