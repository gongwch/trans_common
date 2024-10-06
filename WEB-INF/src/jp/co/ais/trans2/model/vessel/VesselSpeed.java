package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * VESSEL�X�s�[�h�}�X�^(CM_VESSEL_SPD_MST)
 */
public class VesselSpeed extends TransferBase implements Cloneable {

	/** �N���[�� */
	@Override
	public VesselSpeed clone() {
		try {
			return (VesselSpeed) super.clone();
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
		sb.append("/SPD_BAL=");
		sb.append(SPD_BAL);
		sb.append("/SPD_LAD=");
		sb.append(SPD_LAD);
		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** VESSEL�R�[�h */
	protected String VESSEL_CODE = null;

	/** SPEED�^�C�v�R�[�h */
	protected String SPD_TYPE_CODE = null;

	/** �\���� */
	protected int DISP_ODR = -1;

	/** BALLAST�X�s�[�h */
	protected BigDecimal SPD_BAL = null;

	/** LADEN�X�s�[�h */
	protected BigDecimal SPD_LAD = null;

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

	/** �C��R������ */
	protected List<VesselSeaCons> seaConsList = new ArrayList<VesselSeaCons>();

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
	 * VESSEL�R�[�h�̎擾
	 * 
	 * @return VESSEL_CODE VESSEL�R�[�h�̎擾
	 */
	public String getVESSEL_CODE() {
		return VESSEL_CODE;
	}

	/**
	 * VESSEL�R�[�h�̐ݒ�
	 * 
	 * @param VESSEL_CODE VESSEL�R�[�h�̐ݒ�
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
	 * �\�����̎擾
	 * 
	 * @return DISP_ODR �\�����̎擾
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * �\�����̐ݒ�
	 * 
	 * @param DISP_ODR �\�����̐ݒ�
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * BALLAST�X�s�[�h�̎擾
	 * 
	 * @return SPD_BAL BALLAST�X�s�[�h�̎擾
	 */
	public BigDecimal getSPD_BAL() {
		return SPD_BAL;
	}

	/**
	 * BALLAST�X�s�[�h�̐ݒ�
	 * 
	 * @param SPD_BAL BALLAST�X�s�[�h�̐ݒ�
	 */
	public void setSPD_BAL(BigDecimal SPD_BAL) {
		this.SPD_BAL = SPD_BAL;
	}

	/**
	 * LADEN�X�s�[�h�̎擾
	 * 
	 * @return SPD_LAD LADEN�X�s�[�h�̎擾
	 */
	public BigDecimal getSPD_LAD() {
		return SPD_LAD;
	}

	/**
	 * LADEN�X�s�[�h�̐ݒ�
	 * 
	 * @param SPD_LAD LADEN�X�s�[�h�̐ݒ�
	 */
	public void setSPD_LAD(BigDecimal SPD_LAD) {
		this.SPD_LAD = SPD_LAD;
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

	/**
	 * �C��R������̎擾
	 * 
	 * @return seaConsList �C��R������
	 */
	public List<VesselSeaCons> getSeaConsList() {
		return seaConsList;
	}

	/**
	 * �C��R������̐ݒ�
	 * 
	 * @param seaConsList �C��R������
	 */
	public void setSeaConsList(List<VesselSeaCons> seaConsList) {
		this.seaConsList = seaConsList;
	}

	/**
	 * �C��R������̑}��
	 * 
	 * @param seaConses
	 */
	public void addSeaCons(VesselSeaCons... seaConses) {
		if (this.seaConsList == null) {
			this.seaConsList = new ArrayList<VesselSeaCons>();
		}

		for (VesselSeaCons entity : seaConses) {
			this.seaConsList.add(entity);
		}
	}
}
