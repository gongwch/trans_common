package jp.co.ais.trans2.model.cargo;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * CARGO���
 */
public class Cargo extends TransferBase implements AutoCompletable, FilterableEntity {

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		if (!Util.isNullOrEmpty(getName_N())) {
			return getCode() + " " + getName() +" "+ getName_N();
		} else {
			return getCode() + " " + getName();
		}		
	}

	/**
	 * @return �\�����擾
	 */
	public String getName() {
		return getCRG_NAME();
	}
	
	/**
	 * @return �\�����擾(�O�q�p)
	 */
	public String getName_N() {
		return getCRG_NAME_N();
	}

	/**
	 * @return �R�[�h�擾
	 */
	public String getCode() {
		return getCRG_CODE();
	}

	/**
	 * @return null
	 */
	public String getNamek() {
		return null;
	}

	/**
	 * @return null
	 */
	public String getNames() {
		return null;
	}

	/**
	 * @return STR_DATE
	 */
	public Date getDateFrom() {
		return STR_DATE;
	}

	/**
	 * @return END_DATE
	 */
	public Date getDateTo() {
		return END_DATE;
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** CARGO�R�[�h */
	protected String CRG_CODE = null;

	/** CARGO�O���[�v�R�[�h */
	protected String CRG_GRP_CODE = null;

	/** CARGO�� */
	protected String CRG_NAME = null;

	/** �T�C�Y�W�� */
	protected BigDecimal STOWAGE_FACTOR = null;

	/** �ݕ��P�� */
	protected String CRG_UNIT_CODE = null;
	
	/** CARGO�� (���q�p) */
	protected String CRG_NAME_N = null;

	/** �ݕ��P�� (���q�p) */
	protected String CRG_UNIT_CODE_N = null;

	/** �J�e�S���[ */
	protected String CATEGORY = null;

	/** �J�e�S���[���� */
	protected String CATEGORY_NAME = null;

	/** MLIT ITEM */
	protected String MLIT_ITEM_CODE = null;

	/** MLIT ITEM */
	protected String MLIT_ITEM_NAME = null;

	/** MLIT SUB ITEM */
	protected String MLIT_SUB_ITEM_CODE = null;

	/** MLIT SUB ITEM */
	protected String MLIT_SUB_ITEM_NAME = null;

	/** �w�i�F */
	protected String BG_COLOR = null;

	/** �����F */
	protected String TEXT_COLOR = null;

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

	/** CODE_NAME */
	protected String CODE_NAME = null;

	/** CODE_NAME_GRP */
	protected String CODE_NAME_GRP = null;

	/** �~�j�}���v�Z�t���O */
	protected int MIN_CALC_FLG = -1;

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
	 * CARGO�R�[�h�̎擾
	 * 
	 * @return CRG_CODE CARGO�R�[�h
	 */
	public String getCRG_CODE() {
		return CRG_CODE;
	}

	/**
	 * CARGO�R�[�h�̐ݒ�
	 * 
	 * @param CRG_CODE CARGO�R�[�h
	 */
	public void setCRG_CODE(String CRG_CODE) {
		this.CRG_CODE = CRG_CODE;
	}

	/**
	 * CARGO�O���[�v�R�[�h�̎擾
	 * 
	 * @return CRG_GRP_CODE CARGO�O���[�v�R�[�h
	 */
	public String getCRG_GRP_CODE() {
		return CRG_GRP_CODE;
	}

	/**
	 * CARGO�O���[�v�R�[�h�̐ݒ�
	 * 
	 * @param CRG_GRP_CODE CARGO�O���[�v�R�[�h
	 */
	public void setCRG_GRP_CODE(String CRG_GRP_CODE) {
		this.CRG_GRP_CODE = CRG_GRP_CODE;
	}

	/**
	 * CARGO���̎擾
	 * 
	 * @return CRG_NAME CARGO��
	 */
	public String getCRG_NAME() {
		return CRG_NAME;
	}

	/**
	 * CARGO���̐ݒ�
	 * 
	 * @param CRG_NAME CARGO��
	 */
	public void setCRG_NAME(String CRG_NAME) {
		this.CRG_NAME = CRG_NAME;
	}

	/**
	 * �T�C�Y�W���̎擾
	 * 
	 * @return STOWAGE_FACTOR �T�C�Y�W��
	 */
	public BigDecimal getSTOWAGE_FACTOR() {
		return STOWAGE_FACTOR;
	}

	/**
	 * �T�C�Y�W���̐ݒ�
	 * 
	 * @param STOWAGE_FACTOR �T�C�Y�W��
	 */
	public void setSTOWAGE_FACTOR(BigDecimal STOWAGE_FACTOR) {
		this.STOWAGE_FACTOR = STOWAGE_FACTOR;
	}

	/**
	 * �ݕ��P�ʂ̎擾
	 * 
	 * @return CRG_UNIT_CODE �ݕ��P��
	 */
	public String getCRG_UNIT_CODE() {
		return CRG_UNIT_CODE;
	}

	/**
	 * �ݕ��P�ʂ̐ݒ�
	 * 
	 * @param CRG_UNIT_CODE �ݕ��P��
	 */
	public void setCRG_UNIT_CODE(String CRG_UNIT_CODE) {
		this.CRG_UNIT_CODE = CRG_UNIT_CODE;
	}
	
	/**
	 * CARGO��(���q�p)�̎擾
	 * 
	 * @return CRG_NAME_N CARGO��
	 */
	public String getCRG_NAME_N() {
		return CRG_NAME_N;
	}

	/**
	 * CARGO��(���q�p)�̐ݒ�
	 * 
	 * @param CRG_NAME_N CARGO��
	 */
	public void setCRG_NAME_N(String CRG_NAME_N) {
		this.CRG_NAME_N = CRG_NAME_N;
	}

	/**
	 * �ݕ��P��(���q�p)�̎擾
	 * 
	 * @return CRG_UNIT_CODE_N �ݕ��P��
	 */
	public String getCRG_UNIT_CODE_N() {
		return CRG_UNIT_CODE_N;
	}

	/**
	 * �ݕ��P��(���q�p)�̐ݒ�
	 * 
	 * @param CRG_UNIT_CODE_N �ݕ��P��
	 */
	public void setCRG_UNIT_CODE_N(String CRG_UNIT_CODE_N) {
		this.CRG_UNIT_CODE_N = CRG_UNIT_CODE_N;
	}

	/**
	 * �J�e�S���[�̎擾
	 * 
	 * @return CATEGORY �J�e�S���[
	 */
	public String getCATEGORY() {
		return CATEGORY;
	}

	/**
	 * �J�e�S���[�̐ݒ�
	 * 
	 * @param CATEGORY �J�e�S���[
	 */
	public void setCATEGORY(String CATEGORY) {
		this.CATEGORY = CATEGORY;
	}

	/**
	 * �J�e�S���[���̂̎擾
	 * 
	 * @return CATEGORY �J�e�S���[����
	 */
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}

	/**
	 * �J�e�S���[���̂̐ݒ�
	 * 
	 * @param CATEGORY_NAME �J�e�S���[����
	 */
	public void setCATEGORY_NAME(String CATEGORY_NAME) {
		this.CATEGORY_NAME = CATEGORY_NAME;
	}

	/**
	 * MLIT ITEM�̐ݒ�
	 * 
	 * @param MLIT_ITEM_CODE MLIT ITEM
	 */
	public void setMLIT_ITEM(String MLIT_ITEM_CODE) {
		this.MLIT_ITEM_CODE = MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEM�̎擾
	 * 
	 * @return MLIT_ITEM MLIT ITEM
	 */
	public String getMLIT_ITEM() {
		return MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEM�̎擾
	 * 
	 * @return MLIT_ITEM MLIT ITEM
	 */
	public String getMLIT_ITEM_CODE() {
		return MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEM�̐ݒ�
	 * 
	 * @param MLIT_ITEM_CODE MLIT ITEM
	 */
	public void setMLIT_ITEM_CODE(String MLIT_ITEM_CODE) {
		this.MLIT_ITEM_CODE = MLIT_ITEM_CODE;
	}

	/**
	 * MLIT ITEM���̂̎擾
	 * 
	 * @return MLIT_ITEM MLIT ITEM
	 */
	public String getMLIT_ITEM_NAME() {
		return MLIT_ITEM_NAME;
	}

	/**
	 * MLIT ITEM���̂̐ݒ�
	 * 
	 * @param MLIT_ITEM_NAME MLIT ITEM����
	 */
	public void setMLIT_ITEM_NAME(String MLIT_ITEM_NAME) {
		this.MLIT_ITEM_NAME = MLIT_ITEM_NAME;
	}

	/**
	 * MLIT SUB ITEM�̎擾
	 * 
	 * @return MLIT_SUB_ITEM MLIT SUB ITEM
	 */
	public String getMLIT_SUB_ITEM() {
		return MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEM�̐ݒ�
	 * 
	 * @param MLIT_SUB_ITEM_CODE MLIT SUB ITEM
	 */
	public void setMLIT_SUB_ITEM(String MLIT_SUB_ITEM_CODE) {
		this.MLIT_SUB_ITEM_CODE = MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEM�̎擾
	 * 
	 * @return MLIT_SUB_ITEM MLIT SUB ITEM
	 */
	public String getMLIT_SUB_ITEM_CODE() {
		return MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEM�̐ݒ�
	 * 
	 * @param MLIT_SUB_ITEM_CODE MLIT SUB ITEM
	 */
	public void setMLIT_SUB_ITEM_CODE(String MLIT_SUB_ITEM_CODE) {
		this.MLIT_SUB_ITEM_CODE = MLIT_SUB_ITEM_CODE;
	}

	/**
	 * MLIT SUB ITEM���̂̎擾
	 * 
	 * @return MLIT_SUB_ITEM MLIT SUB ITEM
	 */
	public String getMLIT_SUB_ITEM_NAME() {
		return MLIT_SUB_ITEM_NAME;
	}

	/**
	 * MLIT SUB ITEM���̂̐ݒ�
	 * 
	 * @param MLIT_SUB_ITEM_NAME MLIT SUB ITEM
	 */
	public void setMLIT_SUB_ITEM_NAME(String MLIT_SUB_ITEM_NAME) {
		this.MLIT_SUB_ITEM_NAME = MLIT_SUB_ITEM_NAME;
	}

	/**
	 * �w�i�F�̎擾
	 * 
	 * @return BG_COLOR �w�i�F
	 */
	public String getBG_COLOR() {
		return BG_COLOR;
	}

	/**
	 * �w�i�F�̐ݒ�
	 * 
	 * @param BG_COLOR �w�i�F
	 */
	public void setBG_COLOR(String BG_COLOR) {
		this.BG_COLOR = BG_COLOR;
	}

	/**
	 * �����F�̎擾
	 * 
	 * @return TEXT_COLOR �����F
	 */
	public String getTEXT_COLOR() {
		return TEXT_COLOR;
	}

	/**
	 * �����F�̐ݒ�
	 * 
	 * @param TEXT_COLOR �����F
	 */
	public void setTEXT_COLOR(String TEXT_COLOR) {
		this.TEXT_COLOR = TEXT_COLOR;
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
	 * @return CODE_NAME
	 */
	public String getCODE_NAME() {
		return CODE_NAME;
	}

	/**
	 * @param cODE_NAME
	 */
	public void setCODE_NAME(String cODE_NAME) {
		CODE_NAME = cODE_NAME;
	}

	/**
	 * @return CODE_NAME_GRP
	 */
	public String getCODE_NAME_GRP() {
		return CODE_NAME_GRP;
	}

	/**
	 * @param cODE_NAME_GRP
	 */
	public void setCODE_NAME_GRP(String cODE_NAME_GRP) {
		CODE_NAME_GRP = cODE_NAME_GRP;
	}

	/**
	 * Minimum�v�Z�t���O�̎擾
	 * 
	 * @return MIN_CALC Minimum�v�Z
	 */
	public int getMIN_CALC_FLG() {
		return MIN_CALC_FLG;
	}

	/**
	 * Minimum�v�Z�t���O�̐ݒ�
	 * 
	 * @param mIN_CALC_FLG Minimum�v�Z
	 */
	public void setMIN_CALC_FLG(int mIN_CALC_FLG) {
		MIN_CALC_FLG = mIN_CALC_FLG;
	}

	/**
	 * (Liner)Minimum�v�Z�t���O�̎擾<br>
	 * true�̏ꍇ�A�ݕ����ʂ�1�����̏ꍇ���ʂ�1�Ƃ��Čv�Z����
	 * 
	 * @return MIN_CALC Minimum�v�Z
	 */
	public boolean isMinimumCalc() {
		return BooleanUtil.toBoolean(MIN_CALC_FLG);
	}

	/**
	 * (Liner)Minimum�v�Z�t���O�̎擾<br>
	 * true�̏ꍇ�A�ݕ����ʂ�1�����̏ꍇ���ʂ�1�Ƃ��Čv�Z����
	 * 
	 * @param mIN_CALC_FLG Minimum�v�Z
	 */
	public void setMinimumCalc(boolean mIN_CALC_FLG) {
		this.MIN_CALC_FLG = BooleanUtil.toInt(mIN_CALC_FLG);
	}

}
