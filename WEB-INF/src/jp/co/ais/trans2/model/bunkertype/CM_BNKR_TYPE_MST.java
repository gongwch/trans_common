package jp.co.ais.trans2.model.bunkertype;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.ac.*;

/**
 * OP_BNKR_TYPE_MST
 */
public class CM_BNKR_TYPE_MST extends TransferBase implements Cloneable, AutoCompletable {

	/**
	 * �N���[��
	 */
	@Override
	public CM_BNKR_TYPE_MST clone() {
		try {
			CM_BNKR_TYPE_MST bean = (CM_BNKR_TYPE_MST) super.clone();

			return bean;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		return getBNKR_TYPE_CODE();
	}

	/**
	 * @return �R�[�h
	 */
	public String getCode() {
		return getBNKR_TYPE_CODE();
	}

	/**
	 * @return ����
	 */
	public String getName() {
		return getBNKR_TYPE_CODE();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("KAI_CODE=");
		sb.append(KAI_CODE);
		sb.append("/BNKR_TYPE_CODE=");
		sb.append(BNKR_TYPE_CODE);
		sb.append("/ENG_PRI_NOR=");
		sb.append(ENG_PRI_NOR);
		sb.append("/AUX_PRI_NOR=");
		sb.append(AUX_PRI_NOR);
		sb.append("/ENG_PRI_ECA=");
		sb.append(ENG_PRI_ECA);
		sb.append("/AUX_PRI_ECA=");
		sb.append(AUX_PRI_ECA);
		sb.append("/lclDEC=");
		sb.append(LCL_DEC_KETA);
		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** BUNKER�^�C�v�R�[�h */
	protected String BNKR_TYPE_CODE = null;

	/** Keep BUNKER�^�C�v�R�[�h */
	protected String KEEP_BNKR_TYPE_CODE = null;

	/** GRADE_CODE */
	protected String GRADE_CODE = null;

	/** GRADE���� */
	protected String GRADE_NAME = null;

	/** ORDER */
	protected int DISP_ODR = 0;

	/** MAIN_ENGINE�g�p�� */
	protected int ENG_PRI_NOR = -1;

	/** AUX�g�p�� */
	protected int AUX_PRI_NOR = -1;

	/** S_ECA_MAIN_ENGINE�g�p�� */
	protected int ENG_PRI_ECA = -1;

	/** S_ECA_AUX�g�p�� */
	protected int AUX_PRI_ECA = -1;

	/** SCRUBBER_MAIN_ENGINE�g�p�� */
	protected int SC_ENG_PRI_NOR = -1;

	/** SCRUBBER_AUX�g�p�� */
	protected int SC_AUX_PRI_NOR = -1;

	/** SCRUBBER_S_ECA_MAIN_ENGINE�g�p�� */
	protected int SC_ENG_PRI_ECA = -1;

	/** SCRUBBER_S_ECA_AUX�g�p�� */
	protected int SC_AUX_PRI_ECA = -1;

	/** (���q)�����_�ȉ����� */
	protected int LCL_DEC_KETA = 0;

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

	/** 0:���g�p�A1:�g�p���� */
	protected int USE_FLG = 1;

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
	 * BUNKER�^�C�v�R�[�h�̎擾
	 * 
	 * @return BNKR_TYPE_CODE BUNKER�^�C�v�R�[�h
	 */
	public String getBNKR_TYPE_CODE() {
		return BNKR_TYPE_CODE;
	}

	/**
	 * BUNKER�^�C�v�R�[�h�̐ݒ�
	 * 
	 * @param BNKR_TYPE_CODE BUNKER�^�C�v�R�[�h
	 */
	public void setBNKR_TYPE_CODE(String BNKR_TYPE_CODE) {
		this.BNKR_TYPE_CODE = BNKR_TYPE_CODE;
	}

	/**
	 * BUNKER�^�C�v�R�[�h�̎擾
	 * 
	 * @return KEEP_BNKR_TYPE_CODE BUNKER�^�C�v�R�[�h
	 */
	public String getKEEP_BNKR_TYPE_CODE() {
		return KEEP_BNKR_TYPE_CODE;
	}

	/**
	 * BUNKER�^�C�v�R�[�h�̐ݒ�
	 * 
	 * @param KEEP_BNKR_TYPE_CODE BUNKER�^�C�v�R�[�h
	 */
	public void setKEEP_BNKR_TYPE_CODE(String KEEP_BNKR_TYPE_CODE) {
		this.KEEP_BNKR_TYPE_CODE = KEEP_BNKR_TYPE_CODE;
	}

	/**
	 * GRADE_CODE�̎擾
	 * 
	 * @return GRADE_CODE GRADE_CODE�̎擾
	 */
	public String getGRADE_CODE() {
		return this.GRADE_CODE;
	}

	/**
	 * GRADE_CODE�̐ݒ�
	 * 
	 * @param GRADE_CODE GRADE_CODE�̐ݒ�
	 */
	public void setGRADE_CODE(String GRADE_CODE) {
		this.GRADE_CODE = GRADE_CODE;
	}

	/**
	 * GRADE���̂̎擾
	 * 
	 * @return GRADE_NAME GRADE����
	 */
	public String getGRADE_NAME() {
		return GRADE_NAME;
	}

	/**
	 * GRADE���̂̐ݒ�
	 * 
	 * @param GRADE_NAME GRADE����
	 */
	public void setGRADE_NAME(String GRADE_NAME) {
		this.GRADE_NAME = GRADE_NAME;
	}

	/**
	 * ORDER�̎擾
	 * 
	 * @return DISP_ODR ORDER
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * ORDER�̐ݒ�
	 * 
	 * @param DISP_ODR ORDER
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * MAIN_ENGINE�g�p���̎擾
	 * 
	 * @return ENG_PRI_NOR MAIN_ENGINE�g�p��
	 */
	public int getENG_PRI_NOR() {
		return ENG_PRI_NOR;
	}

	/**
	 * MAIN_ENGINE�g�p���̐ݒ�
	 * 
	 * @param ENG_PRI_NOR MAIN_ENGINE�g�p��
	 */
	public void setENG_PRI_NOR(int ENG_PRI_NOR) {
		this.ENG_PRI_NOR = ENG_PRI_NOR;
	}

	/**
	 * AUX�g�p���̎擾
	 * 
	 * @return AUX_PRI_NOR AUX�g�p��
	 */
	public int getAUX_PRI_NOR() {
		return AUX_PRI_NOR;
	}

	/**
	 * AUX�g�p���̐ݒ�
	 * 
	 * @param AUX_PRI_NOR AUX�g�p��
	 */
	public void setAUX_PRI_NOR(int AUX_PRI_NOR) {
		this.AUX_PRI_NOR = AUX_PRI_NOR;
	}

	/**
	 * S_ECA_MAIN_ENGINE�g�p���̎擾
	 * 
	 * @return ENG_PRI_ECA S_ECA_MAIN_ENGINE�g�p��
	 */
	public int getENG_PRI_ECA() {
		return ENG_PRI_ECA;
	}

	/**
	 * S_ECA_MAIN_ENGINE�g�p���̐ݒ�
	 * 
	 * @param ENG_PRI_ECA S_ECA_MAIN_ENGINE�g�p��
	 */
	public void setENG_PRI_ECA(int ENG_PRI_ECA) {
		this.ENG_PRI_ECA = ENG_PRI_ECA;
	}

	/**
	 * S_ECA_AUX�g�p���̎擾
	 * 
	 * @return AUX_PRI_ECA S_ECA_AUX�g�p��
	 */
	public int getAUX_PRI_ECA() {
		return AUX_PRI_ECA;
	}

	/**
	 * S_ECA_AUX�g�p���̐ݒ�
	 * 
	 * @param AUX_PRI_ECA S_ECA_AUX�g�p��
	 */
	public void setAUX_PRI_ECA(int AUX_PRI_ECA) {
		this.AUX_PRI_ECA = AUX_PRI_ECA;
	}

	/**
	 * SCRUBBER_MAIN_ENGINE�g�p���̎擾
	 * 
	 * @return SC_ENG_PRI_NOR SCRUBBER_MAIN_ENGINE�g�p��
	 */
	public int getSC_ENG_PRI_NOR() {
		return SC_ENG_PRI_NOR;
	}

	/**
	 * SCRUBBER_MAIN_ENGINE�g�p���̐ݒ�
	 * 
	 * @param SC_ENG_PRI_NOR SCRUBBER_MAIN_ENGINE�g�p��
	 */
	public void setSC_ENG_PRI_NOR(int SC_ENG_PRI_NOR) {
		this.SC_ENG_PRI_NOR = SC_ENG_PRI_NOR;
	}

	/**
	 * SCRUBBER_AUX�g�p���̎擾
	 * 
	 * @return SC_AUX_PRI_NOR SCRUBBER_AUX�g�p��
	 */
	public int getSC_AUX_PRI_NOR() {
		return SC_AUX_PRI_NOR;
	}

	/**
	 * SCRUBBER_AUX�g�p���̐ݒ�
	 * 
	 * @param SC_AUX_PRI_NOR SCRUBBER_AUX�g�p��
	 */
	public void setSC_AUX_PRI_NOR(int SC_AUX_PRI_NOR) {
		this.SC_AUX_PRI_NOR = SC_AUX_PRI_NOR;
	}

	/**
	 * SCRUBBER_S_ECA_MAIN_ENGINE�g�p���̎擾
	 * 
	 * @return SC_ENG_PRI_ECA SCRUBBER_S_ECA_MAIN_ENGINE�g�p��
	 */
	public int getSC_ENG_PRI_ECA() {
		return SC_ENG_PRI_ECA;
	}

	/**
	 * SCRUBBER_S_ECA_MAIN_ENGINE�g�p���̐ݒ�
	 * 
	 * @param SC_ENG_PRI_ECA SCRUBBER_S_ECA_MAIN_ENGINE�g�p��
	 */
	public void setSC_ENG_PRI_ECA(int SC_ENG_PRI_ECA) {
		this.SC_ENG_PRI_ECA = SC_ENG_PRI_ECA;
	}

	/**
	 * SCRUBBER_S_ECA_AUX�g�p���̎擾
	 * 
	 * @return SC_AUX_PRI_ECA SCRUBBER_S_ECA_AUX�g�p��
	 */
	public int getSC_AUX_PRI_ECA() {
		return SC_AUX_PRI_ECA;
	}

	/**
	 * SCRUBBER_S_ECA_AUX�g�p���̐ݒ�
	 * 
	 * @param SC_AUX_PRI_ECA SCRUBBER_S_ECA_AUX�g�p��
	 */
	public void setSC_AUX_PRI_ECA(int SC_AUX_PRI_ECA) {
		this.SC_AUX_PRI_ECA = SC_AUX_PRI_ECA;
	}

	/**
	 * (���q)�����_�ȉ������̎擾
	 * 
	 * @return LCL_DEC_KETA (���q)�����_�ȉ�����
	 */
	public int getLCL_DEC_KETA() {
		return LCL_DEC_KETA;
	}

	/**
	 * (���q)�����_�ȉ������̐ݒ�
	 * 
	 * @param LCL_DEC_KETA (���q)�����_�ȉ�����
	 */
	public void setLCL_DEC_KETA(int LCL_DEC_KETA) {
		this.LCL_DEC_KETA = LCL_DEC_KETA;
	}

	/**
	 * �J�n�N�����̎擾
	 * 
	 * @return STR_DATE �J�n�N����
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * �J�n�N�����̐ݒ�
	 * 
	 * @param STR_DATE �J�n�N����
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * �I���N�����̎擾
	 * 
	 * @return END_DATE �I���N����
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * �I���N�����̐ݒ�
	 * 
	 * @param END_DATE �I���N����
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * �o�^�N�����̎擾
	 * 
	 * @return INP_DATE �o�^�N����
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^�N�����̐ݒ�
	 * 
	 * @param INP_DATE �o�^�N����
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V�N�����̎擾
	 * 
	 * @return UPD_DATE �X�V�N����
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V�N�����̐ݒ�
	 * 
	 * @param UPD_DATE �X�V�N����
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

	/**
	 * 0:���g�p�A1:�g�p����̎擾
	 * 
	 * @return USE_FLG 0:���g�p�A1:�g�p����
	 */
	public int getUSE_FLG() {
		return USE_FLG;
	}

	/**
	 * 0:���g�p�A1:�g�p����̐ݒ�
	 * 
	 * @param USE_FLG 0:���g�p�A1:�g�p����
	 */
	public void setUSE_FLG(int USE_FLG) {
		this.USE_FLG = USE_FLG;
	}

}
