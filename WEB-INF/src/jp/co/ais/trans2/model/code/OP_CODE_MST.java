package jp.co.ais.trans2.model.code;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.ac.*;

/**
 * OP_CODE_MST
 */
public class OP_CODE_MST extends TransferBase implements Cloneable, AutoCompletable {

	/**
	 * �N���[��
	 */
	@Override
	public OP_CODE_MST clone() {
		try {
			OP_CODE_MST bean = (OP_CODE_MST) super.clone();

			return bean;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(CODE_DIV).append("]").append(CODE).append(" ").append(CODE_NAME);
		return sb.toString();
	}

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		return getCODE_NAME();
	}

	/**
	 * @return �R�[�h
	 */
	public String getCode() {
		return getCODE();
	}

	/**
	 * @return ����
	 */
	public String getName() {
		return getCODE_NAME();
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �R�[�h */
	protected String CODE = null;

	/** �R�[�h�敪 */
	protected String CODE_DIV = null;

	/** �\���� */
	protected int DISP_ODR = 0;

	/** �R�[�h�� */
	protected String CODE_NAME = null;

	/** 1:���q���[�h�A�ȊO:�O�q���[�h */
	protected int LCL_KBN = -1;

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
	 * �R�[�h�̎擾
	 * 
	 * @return CODE �R�[�h
	 */
	public String getCODE() {
		return CODE;
	}

	/**
	 * �R�[�h�̐ݒ�
	 * 
	 * @param CODE �R�[�h
	 */
	public void setCODE(String CODE) {
		this.CODE = CODE;
	}

	/**
	 * �R�[�h�敪�̎擾
	 * 
	 * @return CODE_DIV �R�[�h�敪
	 */
	public String getCODE_DIV() {
		return CODE_DIV;
	}

	/**
	 * �R�[�h�敪�̐ݒ�
	 * 
	 * @param CODE_DIV �R�[�h�敪
	 */
	public void setCODE_DIV(String CODE_DIV) {
		this.CODE_DIV = CODE_DIV;
	}

	/**
	 * �\�����̎擾
	 * 
	 * @return DISP_ODR �\����
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * �\�����̐ݒ�
	 * 
	 * @param DISP_ODR �\����
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * �R�[�h���̎擾
	 * 
	 * @return CODE_NAME �R�[�h��
	 */
	public String getCODE_NAME() {
		return CODE_NAME;
	}

	/**
	 * �R�[�h���̐ݒ�
	 * 
	 * @param CODE_NAME �R�[�h��
	 */
	public void setCODE_NAME(String CODE_NAME) {
		this.CODE_NAME = CODE_NAME;
	}

	/**
	 * 1:���q���[�h�A�ȊO:�O�q���[�h�̎擾
	 * 
	 * @return LCL_KBN 1:���q���[�h�A�ȊO:�O�q���[�h
	 */
	public int getLCL_KBN() {
		return LCL_KBN;
	}

	/**
	 * 1:���q���[�h�A�ȊO:�O�q���[�h�̐ݒ�
	 * 
	 * @param LCL_KBN 1:���q���[�h�A�ȊO:�O�q���[�h
	 */
	public void setLCL_KBN(int LCL_KBN) {
		this.LCL_KBN = LCL_KBN;
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

}
