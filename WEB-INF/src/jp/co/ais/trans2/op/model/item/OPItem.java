package jp.co.ais.trans2.op.model.item;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * OP ITEM
 */
public class OPItem extends TransferBase implements TReferable, AutoCompletable, FilterableEntity, Cloneable {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ITEM_CODE=");
		sb.append(ITEM_CODE);
		sb.append("/ITEM_NAME=");
		sb.append(ITEM_NAME);
		sb.append("/ITEM_CTL_KBN=");
		sb.append(ITEM_CTL_KBN);
		sb.append("/ITEM_SUB_KBN=");
		sb.append(ITEM_SUB_KBN);
		return sb.toString();
	}

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object clone()
	 */
	@Override
	public OPItem clone() {
		try {
			OPItem bean = (OPItem) super.clone();
			return bean;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return �\����
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** ITEM�R�[�h */
	protected String ITEM_CODE = null;

	/** ITEM���� */
	protected String ITEM_NAME = null;

	/** ITEM���� */
	protected String ITEM_NAME_S = null;

	/** ITEM�������� */
	protected String ITEM_NAME_K = null;

	/** �������� */
	protected String ITEM_INV_NAME = null;

	/** CONTRACT�^�C�v */
	protected int CTRT_TYPE = -1;

	/** SA �敪 */
	protected int SA_KBN = -1;

	/** �ݎ؋敪 */
	protected int DC_KBN = -1;

	/** �A�C�e������敪 */
	protected String ITEM_CTL_KBN = null;

	/** �A�C�e��SUB�敪 */
	protected String ITEM_SUB_KBN = null;

	/** �R�~�b�V�����敪 */
	protected int ADCOM_KBN = -1;

	/** Brokarage�敪 */
	protected int BRKR_KBN = -1;

	/** ��������敪 */
	protected int OCCUR_BASE_KBN = -1;

	/** OWNR_SHIP_CODE */
	protected String OWNR_SHIP_CODE = null;

	/** OWNR_SHIP_NAME */
	protected String OWNR_SHIP_NAME = null;

	/** �ȖڃR�[�h */
	protected String KMK_CODE = null;

	/** �⏕�ȖڃR�[�h */
	protected String HKM_CODE = null;

	/** ����ȖڃR�[�h */
	protected String UKM_CODE = null;

	/** �Ȗږ��� */
	protected String KMK_NAME = null;

	/** �⏕�Ȗږ��� */
	protected String HKM_NAME = null;

	/** ����Ȗږ��� */
	protected String UKM_NAME = null;

	/** �Ȗڗ��� */
	protected String KMK_NAME_S = null;

	/** �⏕�Ȗڗ��� */
	protected String HKM_NAME_S = null;

	/** ����Ȗڗ��� */
	protected String UKM_NAME_S = null;

	/** ����ŃR�[�h */
	protected String ZEI_CODE = null;

	/** ����Ŗ��� */
	protected String ZEI_NAME = null;

	/** ����Ŗ��� */
	protected String ZEI_NAME_S = null;

	/** �J���ȖڃR�[�h */
	protected String KURI_KMK_CODE = null;

	/** �J���⏕�ȖڃR�[�h */
	protected String KURI_HKM_CODE = null;

	/** �J������ȖڃR�[�h */
	protected String KURI_UKM_CODE = null;

	/** �J���Ȗږ��� */
	protected String KURI_KMK_NAME = null;

	/** �J���Ȗږ��� */
	protected String KURI_KMK_NAME_S = null;

	/** �J���⏕�Ȗږ��� */
	protected String KURI_HKM_NAME = null;

	/** �J���⏕�Ȗږ��� */
	protected String KURI_HKM_NAME_S = null;

	/** �J������Ȗږ��� */
	protected String KURI_UKM_NAME = null;

	/** �J������Ȗږ��� */
	protected String KURI_UKM_NAME_S = null;

	/** �J���Ȗ� ����ŃR�[�h */
	protected String KURI_ZEI_CODE = null;

	/** �J���Ȗڂ̏���Ŗ��� */
	protected String KURI_ZEI_NAME = null;

	/** �J���Ȗڂ̏���Ŗ��� */
	protected String KURI_ZEI_NAME_S = null;

	/** ����ȖڃR�[�h */
	protected String AT_KMK_CODE = null;

	/** ����⏕�ȖڃR�[�h */
	protected String AT_HKM_CODE = null;

	/** �������ȖڃR�[�h */
	protected String AT_UKM_CODE = null;

	/** ����Ȗږ��� */
	protected String AT_KMK_NAME = null;

	/** ����⏕�Ȗږ��� */
	protected String AT_HKM_NAME = null;

	/** �������Ȗږ��� */
	protected String AT_UKM_NAME = null;

	/** ����Ȗڗ��� */
	protected String AT_KMK_NAME_S = null;

	/** ����⏕�Ȗڗ��� */
	protected String AT_HKM_NAME_S = null;

	/** �������Ȗڗ��� */
	protected String AT_UKM_NAME_S = null;

	/** �������ŃR�[�h */
	protected String AT_ZEI_CODE = null;

	/** �������Ŗ��� */
	protected String AT_ZEI_NAME = null;

	/** �������Ŗ��� */
	protected String AT_ZEI_NAME_S = null;

	/** ����J���ȖڃR�[�h */
	protected String AT_KURI_KMK_CODE = null;

	/** ����J���⏕�ȖڃR�[�h */
	protected String AT_KURI_HKM_CODE = null;

	/** ����J������ȖڃR�[�h */
	protected String AT_KURI_UKM_CODE = null;

	/** ����J���Ȗږ��� */
	protected String AT_KURI_KMK_NAME = null;

	/** ����J���⏕�Ȗږ��� */
	protected String AT_KURI_HKM_NAME = null;

	/** ����J������Ȗږ��� */
	protected String AT_KURI_UKM_NAME = null;

	/** ����J���Ȗږ��� */
	protected String AT_KURI_KMK_NAME_S = null;

	/** ����J���⏕�Ȗږ��� */
	protected String AT_KURI_HKM_NAME_S = null;

	/** ����J������Ȗږ��� */
	protected String AT_KURI_UKM_NAME_S = null;

	/** ����J���Ȗ� ����ŃR�[�h */
	protected String AT_KURI_ZEI_CODE = null;

	/** ����J���Ȗڂ̏���Ŗ��� */
	protected String AT_KURI_ZEI_NAME = null;

	/** ����J���Ȗڂ̏���Ŗ��� */
	protected String AT_KURI_ZEI_NAME_S = null;

	/** �T�Z�ȖڃR�[�h */
	protected String EST_KMK_CODE = null;

	/** �T�Z�⏕�ȖڃR�[�h */
	protected String EST_HKM_CODE = null;

	/** �T�Z����ȖڃR�[�h */
	protected String EST_UKM_CODE = null;

	/** �T�Z�Ȗږ��� */
	protected String EST_KMK_NAME = null;

	/** �T�Z�Ȗږ��� */
	protected String EST_KMK_NAME_S = null;

	/** �T�Z�⏕�Ȗږ��� */
	protected String EST_HKM_NAME = null;

	/** �T�Z�⏕�Ȗږ��� */
	protected String EST_HKM_NAME_S = null;

	/** �T�Z����Ȗږ��� */
	protected String EST_UKM_NAME = null;

	/** �T�Z����Ȗږ��� */
	protected String EST_UKM_NAME_S = null;

	/** �T�Z�Ȗ� ����ŃR�[�h */
	protected String EST_ZEI_CODE = null;

	/** �T�Z�Ȗڂ̏���Ŗ��� */
	protected String EST_ZEI_NAME = null;

	/** �T�Z�Ȗڂ̏���Ŗ��� */
	protected String EST_ZEI_NAME_S = null;

	/** ����T�Z�ȖڃR�[�h */
	protected String AT_EST_KMK_CODE = null;

	/** ����T�Z�⏕�ȖڃR�[�h */
	protected String AT_EST_HKM_CODE = null;

	/** ����T�Z����ȖڃR�[�h */
	protected String AT_EST_UKM_CODE = null;

	/** ����T�Z�Ȗږ��� */
	protected String AT_EST_KMK_NAME = null;

	/** ����T�Z�⏕�Ȗږ��� */
	protected String AT_EST_HKM_NAME = null;

	/** ����T�Z����Ȗږ��� */
	protected String AT_EST_UKM_NAME = null;

	/** ����T�Z�Ȗږ��� */
	protected String AT_EST_KMK_NAME_S = null;

	/** ����T�Z�⏕�Ȗږ��� */
	protected String AT_EST_HKM_NAME_S = null;

	/** ����T�Z����Ȗږ��� */
	protected String AT_EST_UKM_NAME_S = null;

	/** ����T�Z�Ȗ� ����ŃR�[�h */
	protected String AT_EST_ZEI_CODE = null;

	/** ����T�Z�Ȗڂ̏���Ŗ��� */
	protected String AT_EST_ZEI_NAME = null;

	/** ����T�Z�Ȗڂ̏���Ŗ��� */
	protected String AT_EST_ZEI_NAME_S = null;

	/** �s�E�v */
	protected String ROW_OUTLINE = null;

	/** �J�n�N���� */
	protected Date STR_DATE = null;

	/** �I���N���� */
	protected Date END_DATE = null;

	/** �o�^���t */
	protected Date INP_DATE = null;

	/** �X�V���t */
	protected Date UPD_DATE = null;

	/** �v���O�����h�c */
	protected String PRG_ID = null;

	/** ���[�U�[�h�c */
	protected String USR_ID = null;

	/** BNKR_TYPE_CODE */
	protected String BNKR_TYPE_CODE = null;

	/** VC/VR�ϊ��A�C�e���R�[�h */
	protected String VCVR_ITEM_CODE = null;

	/** ���v�ؕ��@�ȖڃR�[�h */
	protected String NEW_ACC_DR_KMK_CODE = null;

	/** ���v�ؕ��@�⏕�ȖڃR�[�h */
	protected String NEW_ACC_DR_HKM_CODE = null;

	/** ���v�ؕ��@����ȖڃR�[�h */
	protected String NEW_ACC_DR_UKM_CODE = null;

	/** ���v�ؕ��@����ŃR�[�h */
	protected String NEW_ACC_DR_ZEI_CODE = null;

	/** ���v�ؕ��@�Ȗږ��� */
	protected String NEW_ACC_DR_KMK_NAME = null;

	/** ���v�ؕ��@�⏕�Ȗږ��� */
	protected String NEW_ACC_DR_HKM_NAME = null;

	/** ���v�ؕ��@����Ȗږ��� */
	protected String NEW_ACC_DR_UKM_NAME = null;

	/** ���v�ؕ��@����Ŗ��� */
	protected String NEW_ACC_DR_ZEI_NAME = null;

	/** ���v�ؕ��@�Ȗ� */
	protected String NEW_ACC_DR_KMK_NAME_S = null;

	/** ���v�ؕ��@�⏕�Ȗڗ��� */
	protected String NEW_ACC_DR_HKM_NAME_S = null;

	/** ���v�ؕ��@����Ȗڗ��� */
	protected String NEW_ACC_DR_UKM_NAME_S = null;

	/** ���v�ؕ��@����ŗ��� */
	protected String NEW_ACC_DR_ZEI_NAME_S = null;

	/** ���v�ݕ��@�ȖڃR�[�h */
	protected String NEW_ACC_CR_KMK_CODE = null;

	/** ���v�ݕ��@�⏕�ȖڃR�[�h */
	protected String NEW_ACC_CR_HKM_CODE = null;

	/** ���v�ݕ��@����ȖڃR�[�h */
	protected String NEW_ACC_CR_UKM_CODE = null;

	/** ���v�ݕ��@����ŃR�[�h */
	protected String NEW_ACC_CR_ZEI_CODE = null;

	/** ���v�ݕ��@�Ȗږ��� */
	protected String NEW_ACC_CR_KMK_NAME = null;

	/** ���v�ݕ��@�⏕�Ȗږ��� */
	protected String NEW_ACC_CR_HKM_NAME = null;

	/** ���v�ݕ��@����Ȗږ��� */
	protected String NEW_ACC_CR_UKM_NAME = null;

	/** ���v�ݕ��@����Ŗ��� */
	protected String NEW_ACC_CR_ZEI_NAME = null;

	/** ���v�ݕ��@�Ȗ� */
	protected String NEW_ACC_CR_KMK_NAME_S = null;

	/** ���v�ݕ��@�⏕�Ȗڗ��� */
	protected String NEW_ACC_CR_HKM_NAME_S = null;

	/** ���v�ݕ��@����Ȗڗ��� */
	protected String NEW_ACC_CR_UKM_NAME_S = null;

	/** ���v�ݕ��@����ŗ��� */
	protected String NEW_ACC_CR_ZEI_NAME_S = null;

	/** ���v�ؕ��A�ȖڃR�[�h */
	protected String NEW_ACC_DR_2_KMK_CODE = null;

	/** ���v�ؕ��A�⏕�ȖڃR�[�h */
	protected String NEW_ACC_DR_2_HKM_CODE = null;

	/** ���v�ؕ��A����ȖڃR�[�h */
	protected String NEW_ACC_DR_2_UKM_CODE = null;

	/** ���v�ؕ��A����ŃR�[�h */
	protected String NEW_ACC_DR_2_ZEI_CODE = null;

	/** ���v�ؕ��A�Ȗږ��� */
	protected String NEW_ACC_DR_2_KMK_NAME = null;

	/** ���v�ؕ��A�⏕�Ȗږ��� */
	protected String NEW_ACC_DR_2_HKM_NAME = null;

	/** ���v�ؕ��A����Ȗږ��� */
	protected String NEW_ACC_DR_2_UKM_NAME = null;

	/** ���v�ؕ��A����Ŗ��� */
	protected String NEW_ACC_DR_2_ZEI_NAME = null;

	/** ���v�ؕ��A�Ȗ� */
	protected String NEW_ACC_DR_2_KMK_NAME_S = null;

	/** ���v�ؕ��A�⏕�Ȗڗ��� */
	protected String NEW_ACC_DR_2_HKM_NAME_S = null;

	/** ���v�ؕ��A����Ȗڗ��� */
	protected String NEW_ACC_DR_2_UKM_NAME_S = null;

	/** ���v�ؕ��A����ŗ��� */
	protected String NEW_ACC_DR_2_ZEI_NAME_S = null;

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
	 * ITEM�R�[�h�̎擾
	 * 
	 * @return ITEM_CODE ITEM�R�[�h
	 */
	public String getITEM_CODE() {
		return ITEM_CODE;
	}

	/**
	 * ITEM�R�[�h�̐ݒ�
	 * 
	 * @param ITEM_CODE ITEM�R�[�h
	 */
	public void setITEM_CODE(String ITEM_CODE) {
		this.ITEM_CODE = ITEM_CODE;
	}

	/**
	 * ITEM���̂̎擾
	 * 
	 * @return ITEM_NAME ITEM����
	 */
	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	/**
	 * ITEM���̂̐ݒ�
	 * 
	 * @param ITEM_NAME ITEM����
	 */
	public void setITEM_NAME(String ITEM_NAME) {
		this.ITEM_NAME = ITEM_NAME;
	}

	/**
	 * ITEM���̂̎擾
	 * 
	 * @return ITEM_NAME_S ITEM����
	 */
	public String getITEM_NAME_S() {
		return ITEM_NAME_S;
	}

	/**
	 * ITEM���̂̐ݒ�
	 * 
	 * @param ITEM_NAME_S ITEM����
	 */
	public void setITEM_NAME_S(String ITEM_NAME_S) {
		this.ITEM_NAME_S = ITEM_NAME_S;
	}

	/**
	 * ITEM�������̂̎擾
	 * 
	 * @return ITEM_NAME_K ITEM��������
	 */
	public String getITEM_NAME_K() {
		return ITEM_NAME_K;
	}

	/**
	 * ITEM�������̂̐ݒ�
	 * 
	 * @param ITEM_NAME_K ITEM��������
	 */
	public void setITEM_NAME_K(String ITEM_NAME_K) {
		this.ITEM_NAME_K = ITEM_NAME_K;
	}

	/**
	 * ���������̎擾
	 * 
	 * @return ITEM_INV_NAME ��������
	 */
	public String getITEM_INV_NAME() {
		return ITEM_INV_NAME;
	}

	/**
	 * ���������̐ݒ�
	 * 
	 * @param ITEM_INV_NAME ��������
	 */
	public void setITEM_INV_NAME(String ITEM_INV_NAME) {
		this.ITEM_INV_NAME = ITEM_INV_NAME;
	}

	/**
	 * CONTRACT�^�C�v�̎擾
	 * 
	 * @return CTRT_TYPE CONTRACT�^�C�v
	 */
	public int getCTRT_TYPE() {
		return CTRT_TYPE;
	}

	/**
	 * CONTRACT�^�C�v�̐ݒ�
	 * 
	 * @param CTRT_TYPE CONTRACT�^�C�v
	 */
	public void setCTRT_TYPE(int CTRT_TYPE) {
		this.CTRT_TYPE = CTRT_TYPE;
	}

	/**
	 * SA�敪�̎擾
	 * 
	 * @return SA_KBN SA�敪
	 */
	public int getSA_KBN() {
		return SA_KBN;
	}

	/**
	 * SA�敪�̐ݒ�
	 * 
	 * @param SA_KBN SA�敪
	 */
	public void setSA_KBN(int SA_KBN) {
		this.SA_KBN = SA_KBN;
	}

	/**
	 * �ݎ؋敪�̎擾
	 * 
	 * @return DC_KBN �ݎ؋敪
	 */
	public int getDC_KBN() {
		return DC_KBN;
	}

	/**
	 * �ݎ؋敪�̐ݒ�
	 * 
	 * @param DC_KBN �ݎ؋敪
	 */
	public void setDC_KBN(int DC_KBN) {
		this.DC_KBN = DC_KBN;
	}

	/**
	 * �A�C�e������敪�̎擾
	 * 
	 * @return ITEM_CTL_KBN �A�C�e������敪
	 */
	public String getITEM_CTL_KBN() {
		return ITEM_CTL_KBN;
	}

	/**
	 * �A�C�e������敪�̐ݒ�
	 * 
	 * @param ITEM_CTL_KBN �A�C�e������敪
	 */
	public void setITEM_CTL_KBN(String ITEM_CTL_KBN) {
		this.ITEM_CTL_KBN = ITEM_CTL_KBN;
	}

	/**
	 * �A�C�e��SUB�敪�̎擾
	 * 
	 * @return ITEM_SUB_KBN �A�C�e��SUB�敪
	 */
	public String getITEM_SUB_KBN() {
		return ITEM_SUB_KBN;
	}

	/**
	 * �A�C�e��SUB�敪�̐ݒ�
	 * 
	 * @param ITEM_SUB_KBN �A�C�e��SUB�敪
	 */
	public void setITEM_SUB_KBN(String ITEM_SUB_KBN) {
		this.ITEM_SUB_KBN = ITEM_SUB_KBN;
	}

	/**
	 * �R�~�b�V�����敪�̎擾
	 * 
	 * @return ADCOM_KBN �R�~�b�V�����敪
	 */
	public int getADCOM_KBN() {
		return ADCOM_KBN;
	}

	/**
	 * �R�~�b�V�����敪�̐ݒ�
	 * 
	 * @param ADCOM_KBN �R�~�b�V�����敪
	 */
	public void setADCOM_KBN(int ADCOM_KBN) {
		this.ADCOM_KBN = ADCOM_KBN;
	}

	/**
	 * Brokerage�敪�̎擾
	 * 
	 * @return BRKR_KBN Brokerage�敪
	 */
	public int getBRKR_KBN() {
		return BRKR_KBN;
	}

	/**
	 * Brokerage�敪�̐ݒ�
	 * 
	 * @param BRKR_KBN Brokerage�敪
	 */
	public void setBRKR_KBN(int BRKR_KBN) {
		this.BRKR_KBN = BRKR_KBN;
	}

	/**
	 * ��������敪�̎擾
	 * 
	 * @return OCCUR_BASE_KBN ��������敪
	 */
	public int getOCCUR_BASE_KBN() {
		return OCCUR_BASE_KBN;
	}

	/**
	 * ��������敪�̐ݒ�
	 * 
	 * @param OCCUR_BASE_KBN ��������敪
	 */
	public void setOCCUR_BASE_KBN(int OCCUR_BASE_KBN) {
		this.OCCUR_BASE_KBN = OCCUR_BASE_KBN;
	}

	/**
	 * OWNR_SHIP_CODE �̎擾
	 * 
	 * @return OWNR_SHIP_CODE
	 */
	public String getOWNR_SHIP_CODE() {
		return OWNR_SHIP_CODE;
	}

	/**
	 * OWNR_SHIP_CODE �̐ݒ�
	 * 
	 * @param OWNR_SHIP_CODE
	 */
	public void setOWNR_SHIP_CODE(String OWNR_SHIP_CODE) {
		this.OWNR_SHIP_CODE = OWNR_SHIP_CODE;
	}

	/**
	 * OWNR_SHIP_NAME �̎擾
	 * 
	 * @return OWNR_SHIP_NAME
	 */
	public String getOWNR_SHIP_NAME() {
		return OWNR_SHIP_NAME;
	}

	/**
	 * OWNR_SHIP_NAME �̐ݒ�
	 * 
	 * @param OWNR_SHIP_NAME
	 */
	public void setOWNR_SHIP_NAME(String OWNR_SHIP_NAME) {
		this.OWNR_SHIP_NAME = OWNR_SHIP_NAME;
	}

	/**
	 * �ȖڃR�[�h�̎擾
	 * 
	 * @return KMK_CODE �ȖڃR�[�h
	 */
	public String getKMK_CODE() {
		return KMK_CODE;
	}

	/**
	 * �ȖڃR�[�h�̐ݒ�
	 * 
	 * @param KMK_CODE �ȖڃR�[�h
	 */
	public void setKMK_CODE(String KMK_CODE) {
		this.KMK_CODE = KMK_CODE;
	}

	/**
	 * �⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return HKM_CODE �⏕�ȖڃR�[�h
	 */
	public String getHKM_CODE() {
		return HKM_CODE;
	}

	/**
	 * �⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param HKM_CODE �⏕�ȖڃR�[�h
	 */
	public void setHKM_CODE(String HKM_CODE) {
		this.HKM_CODE = HKM_CODE;
	}

	/**
	 * ����ȖڃR�[�h�̎擾
	 * 
	 * @return UKM_CODE ����ȖڃR�[�h
	 */
	public String getUKM_CODE() {
		return UKM_CODE;
	}

	/**
	 * ����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param UKM_CODE ����ȖڃR�[�h
	 */
	public void setUKM_CODE(String UKM_CODE) {
		this.UKM_CODE = UKM_CODE;
	}

	/**
	 * �Ȗڗ��̂̎擾
	 * 
	 * @return KMK_NAME_S �Ȗڗ���
	 */
	public String getKMK_NAME_S() {
		return KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̐ݒ�
	 * 
	 * @param KMK_NAME_S �Ȗڗ���
	 */
	public void setKMK_NAME_S(String KMK_NAME_S) {
		this.KMK_NAME_S = KMK_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̎擾
	 * 
	 * @return HKM_NAME_S �⏕�Ȗڗ���
	 */
	public String getHKM_NAME_S() {
		return HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̐ݒ�
	 * 
	 * @param HKM_NAME_S �⏕�Ȗڗ���
	 */
	public void setHKM_NAME_S(String HKM_NAME_S) {
		this.HKM_NAME_S = HKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂̎擾
	 * 
	 * @return UKM_NAME_S ����Ȗڗ���
	 */
	public String getUKM_NAME_S() {
		return UKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂̐ݒ�
	 * 
	 * @param UKM_NAME_S ����Ȗڗ���
	 */
	public void setUKM_NAME_S(String UKM_NAME_S) {
		this.UKM_NAME_S = UKM_NAME_S;
	}

	/**
	 * �Ȗ�-�⏕-����̗��̂̎擾
	 * 
	 * @return �Ȗ�-�⏕-����̗���
	 */
	public String getAccountName() {
		return Util.avoidNull(KMK_NAME_S) + " " + Util.avoidNull(HKM_NAME_S) + " " + Util.avoidNull(UKM_NAME_S);
	}

	/**
	 * �Ȗ�-�⏕-����̃R�[�h�̎擾
	 * 
	 * @return �Ȗ�-�⏕-����̃R�[�h
	 */
	public String getAccountCode() {

		return Util.avoidNull(KMK_CODE) + " " + Util.avoidNull(HKM_CODE) + " " + Util.avoidNull(UKM_CODE);
	}

	/**
	 * ����ŃR�[�h�̎擾
	 * 
	 * @return ZEI_CODE ����ŃR�[�h
	 */
	public String getZEI_CODE() {
		return ZEI_CODE;
	}

	/**
	 * ����ŃR�[�h�̐ݒ�
	 * 
	 * @param ZEI_CODE ����ŃR�[�h
	 */
	public void setZEI_CODE(String ZEI_CODE) {
		this.ZEI_CODE = ZEI_CODE;
	}

	/**
	 * ROW_OUTLINE�̎擾
	 * 
	 * @return ROW_OUTLINE ROW_OUTLINE
	 */
	public String getROW_OUTLINE() {
		return ROW_OUTLINE;
	}

	/**
	 * ROW_OUTLINE�̐ݒ�
	 * 
	 * @param ROW_OUTLINE ROW_OUTLINE
	 */
	public void setROW_OUTLINE(String ROW_OUTLINE) {
		this.ROW_OUTLINE = ROW_OUTLINE;
	}

	/**
	 * KURI_KMK_CODE �ȖڃR�[�h�̎擾
	 * 
	 * @return KURI_KMK_CODE �ڃR�[�h
	 */
	public String getKURI_KMK_CODE() {
		return KURI_KMK_CODE;
	}

	/**
	 * KURI_KMK_CODE �ȖڃR�[�h�̐ݒ�
	 * 
	 * @param KURI_KMK_CODE �ȖڃR�[�h
	 */
	public void setKURI_KMK_CODE(String KURI_KMK_CODE) {
		this.KURI_KMK_CODE = KURI_KMK_CODE;
	}

	/**
	 * KURI�⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return KURI_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public String getKURI_HKM_CODE() {
		return KURI_HKM_CODE;
	}

	/**
	 * KURI�⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param KURI_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public void setKURI_HKM_CODE(String KURI_HKM_CODE) {
		this.KURI_HKM_CODE = KURI_HKM_CODE;
	}

	/**
	 * AddCom����ȖڃR�[�h�̎擾
	 * 
	 * @return ADDCOM_UKM_CODE AddCom����ȖڃR�[�h
	 */
	public String getKURI_UKM_CODE() {
		return KURI_UKM_CODE;
	}

	/**
	 * KURI����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param KURI_UKM_CODE AddCom����ȖڃR�[�h
	 */
	public void setKURI_UKM_CODE(String KURI_UKM_CODE) {
		this.KURI_UKM_CODE = KURI_UKM_CODE;
	}

	/**
	 * KURI�Ȗڂ̏���ŃR�[�h�̎擾
	 * 
	 * @return KURI_ZEI_CODE �ڂ̏���ŃR�[�h
	 */
	public String getKURI_ZEI_CODE() {
		return KURI_ZEI_CODE;
	}

	/**
	 * KURI�Ȗڂ̏���ŃR�[�h�̐ݒ�
	 * 
	 * @param KURI_ZEI_CODE �Ȗڂ̏���ŃR�[�h
	 */
	public void setKURI_ZEI_CODE(String KURI_ZEI_CODE) {
		this.KURI_ZEI_CODE = KURI_ZEI_CODE;
	}

	/**
	 * �Ȗږ��̂̎擾
	 * 
	 * @return KMK_NAME �Ȗږ���
	 */
	public String getKMK_NAME() {
		return KMK_NAME;
	}

	/**
	 * �Ȗږ��̂̐ݒ�
	 * 
	 * @param KMK_NAME �Ȗږ���
	 */
	public void setKMK_NAME(String KMK_NAME) {
		this.KMK_NAME = KMK_NAME;
	}

	/**
	 * �⏕�Ȗږ��̂̎擾
	 * 
	 * @return HKM_NAME �⏕�Ȗږ���
	 */
	public String getHKM_NAME() {
		return HKM_NAME;
	}

	/**
	 * �⏕�Ȗږ��̂̐ݒ�
	 * 
	 * @param HKM_NAME �⏕�Ȗږ���
	 */
	public void setHKM_NAME(String HKM_NAME) {
		this.HKM_NAME = HKM_NAME;
	}

	/**
	 * ����Ȗږ��̂̎擾
	 * 
	 * @return UKM_NAME ����Ȗږ���
	 */
	public String getUKM_NAME() {
		return UKM_NAME;
	}

	/**
	 * ����Ȗږ��̂̐ݒ�
	 * 
	 * @param UKM_NAME ����Ȗ�
	 */
	public void setUKM_NAME(String UKM_NAME) {
		this.UKM_NAME = UKM_NAME;
	}

	/**
	 * ����Ŗ��̂̎擾
	 * 
	 * @return ZEI_CODE ����Ŗ���
	 */
	public String getZEI_NAME() {
		return ZEI_NAME;
	}

	/**
	 * ����Ŗ��̂̐ݒ�
	 * 
	 * @param ZEI_NAME ����Ŗ���
	 */
	public void setZEI_NAME(String ZEI_NAME) {
		this.ZEI_NAME = ZEI_NAME;
	}

	/**
	 * ����Ŗ��̂̎擾
	 * 
	 * @return ZEI_CODE ����Ŗ���
	 */
	public String getZEI_NAME_S() {
		return ZEI_NAME_S;
	}

	/**
	 * ����Ŗ��̂̐ݒ�
	 * 
	 * @param ZEI_NAME_S ����Ŗ���
	 */
	public void setZEI_NAME_S(String ZEI_NAME_S) {
		this.ZEI_NAME_S = ZEI_NAME_S;
	}

	/**
	 * KURI�Ȗږ��̂̎擾
	 * 
	 * @return KURI_KMK_NAME �Ȗږ���
	 */
	public String getKURI_KMK_NAME() {
		return KURI_KMK_NAME;
	}

	/**
	 * KURI�Ȗږ��̂̐ݒ�
	 * 
	 * @param KURI_KMK_NAME �Ȗږ���
	 */
	public void setKURI_KMK_NAME(String KURI_KMK_NAME) {
		this.KURI_KMK_NAME = KURI_KMK_NAME;
	}

	/**
	 * KURI�⏕�Ȗږ��̂̎擾
	 * 
	 * @return KURI_HKM_NAME �⏕�Ȗږ���
	 */
	public String getKURI_HKM_NAME() {
		return KURI_HKM_NAME;
	}

	/**
	 * KURI�⏕�Ȗږ��̂̐ݒ�
	 * 
	 * @param KURI_HKM_NAME �⏕�Ȗږ���
	 */
	public void setKURI_HKM_NAME(String KURI_HKM_NAME) {
		this.KURI_HKM_NAME = KURI_HKM_NAME;
	}

	/**
	 * KURI����Ȗږ��̂̎擾
	 * 
	 * @return KURI_UKM_NAME ����Ȗږ���
	 */
	public String getKURI_UKM_NAME() {
		return KURI_UKM_NAME;
	}

	/**
	 * KURI�Ȗږ��̂̐ݒ�
	 * 
	 * @param KURI_UKM_NAME ����Ȗږ���
	 */
	public void setKURI_UKM_NAME(String KURI_UKM_NAME) {
		this.KURI_UKM_NAME = KURI_UKM_NAME;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̎擾
	 * 
	 * @return KURI_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public String getKURI_ZEI_NAME() {
		return KURI_ZEI_NAME;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̐ݒ�
	 * 
	 * @param KURI_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public void setKURI_ZEI_NAME(String KURI_ZEI_NAME) {
		this.KURI_ZEI_NAME = KURI_ZEI_NAME;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̎擾
	 * 
	 * @return KURI_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public String getKURI_ZEI_NAME_S() {
		return KURI_ZEI_NAME_S;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̐ݒ�
	 * 
	 * @param KURI_ZEI_NAME_S �Ȗڂ̏���Ŗ���
	 */
	public void setKURI_ZEI_NAME_S(String KURI_ZEI_NAME_S) {
		this.KURI_ZEI_NAME_S = KURI_ZEI_NAME_S;
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
	 * �o�^���t�̎擾
	 * 
	 * @return INP_DATE �o�^���t
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^���t�̐ݒ�
	 * 
	 * @param INP_DATE �o�^���t
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V���t�̎擾
	 * 
	 * @return UPD_DATE �X�V���t
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V���t�̐ݒ�
	 * 
	 * @param UPD_DATE �X�V���t
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * �v���O�����h�c�̎擾
	 * 
	 * @return PRG_ID �v���O�����h�c
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O�����h�c�̐ݒ�
	 * 
	 * @param PRG_ID �v���O�����h�c
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ���[�U�[�h�c�̎擾
	 * 
	 * @return USR_ID ���[�U�[�h�c
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[�h�c�̐ݒ�
	 * 
	 * @param USR_ID ���[�U�[�h�c
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * �ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_KMK_CODE �ȖڃR�[�h
	 */
	public String getAT_KMK_CODE() {
		return AT_KMK_CODE;
	}

	/**
	 * �ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_KMK_CODE �ȖڃR�[�h
	 */
	public void setAT_KMK_CODE(String AT_KMK_CODE) {
		this.AT_KMK_CODE = AT_KMK_CODE;
	}

	/**
	 * �⏕�ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public String getAT_HKM_CODE() {
		return AT_HKM_CODE;
	}

	/**
	 * �⏕�ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public void setAT_HKM_CODE(String AT_HKM_CODE) {
		this.AT_HKM_CODE = AT_HKM_CODE;
	}

	/**
	 * ����ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_UKM_CODE ����ȖڃR�[�h
	 */
	public String getAT_UKM_CODE() {
		return AT_UKM_CODE;
	}

	/**
	 * ����ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_UKM_CODE ����ȖڃR�[�h
	 */
	public void setAT_UKM_CODE(String AT_UKM_CODE) {
		this.AT_UKM_CODE = AT_UKM_CODE;
	}

	/**
	 * �Ȗڗ��̂̎擾�i����j
	 * 
	 * @return AT_KMK_NAME_S �Ȗڗ���
	 */
	public String getAT_KMK_NAME_S() {
		return AT_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̐ݒ�i����j
	 * 
	 * @param AT_KMK_NAME_S �Ȗڗ���
	 */
	public void setAT_KMK_NAME_S(String AT_KMK_NAME_S) {
		this.AT_KMK_NAME_S = AT_KMK_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̎擾�i����j
	 * 
	 * @return AT_HKM_NAME_S �⏕�Ȗڗ���
	 */
	public String getAT_HKM_NAME_S() {
		return AT_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̐ݒ�i����j
	 * 
	 * @param AT_HKM_NAME_S �⏕�Ȗڗ���
	 */
	public void setAT_HKM_NAME_S(String AT_HKM_NAME_S) {
		this.AT_HKM_NAME_S = AT_HKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂̎擾�i����j
	 * 
	 * @return AT_UKM_NAME_S ����Ȗڗ���
	 */
	public String getAT_UKM_NAME_S() {
		return AT_UKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂̐ݒ�i����j
	 * 
	 * @param AT_UKM_NAME_S ����Ȗڗ���
	 */
	public void setAT_UKM_NAME_S(String AT_UKM_NAME_S) {
		this.AT_UKM_NAME_S = AT_UKM_NAME_S;
	}

	/**
	 * �Ȗ�-�⏕-����̗��̂̎擾�i����j
	 * 
	 * @return �Ȗ�-�⏕-����̗���
	 */
	public String getAT_AccountName() {
		return Util.avoidNull(AT_KMK_NAME_S) + " " + Util.avoidNull(AT_HKM_NAME_S) + " "
			+ Util.avoidNull(AT_UKM_NAME_S);
	}

	/**
	 * ����ŃR�[�h�̎擾�i����j
	 * 
	 * @return AT_ZEI_CODE ����ŃR�[�h
	 */
	public String getAT_ZEI_CODE() {
		return AT_ZEI_CODE;
	}

	/**
	 * ����ŃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_ZEI_CODE ����ŃR�[�h
	 */
	public void setAT_ZEI_CODE(String AT_ZEI_CODE) {
		this.AT_ZEI_CODE = AT_ZEI_CODE;
	}

	/**
	 * AT_KURI_KMK_CODE �ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_KURI_KMK_CODE �ڃR�[�h
	 */
	public String getAT_KURI_KMK_CODE() {
		return AT_KURI_KMK_CODE;
	}

	/**
	 * AT_KURI_KMK_CODE �ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_KURI_KMK_CODE �ȖڃR�[�h
	 */
	public void setAT_KURI_KMK_CODE(String AT_KURI_KMK_CODE) {
		this.AT_KURI_KMK_CODE = AT_KURI_KMK_CODE;
	}

	/**
	 * KURI�⏕�ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_KURI_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public String getAT_KURI_HKM_CODE() {
		return AT_KURI_HKM_CODE;
	}

	/**
	 * KURI�⏕�ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_KURI_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public void setAT_KURI_HKM_CODE(String AT_KURI_HKM_CODE) {
		this.AT_KURI_HKM_CODE = AT_KURI_HKM_CODE;
	}

	/**
	 * KURI����ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_KURI_UKM_CODE ����ȖڃR�[�h
	 */
	public String getAT_KURI_UKM_CODE() {
		return AT_KURI_UKM_CODE;
	}

	/**
	 * KURI����ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_KURI_UKM_CODE ����ȖڃR�[�h
	 */
	public void setAT_KURI_UKM_CODE(String AT_KURI_UKM_CODE) {
		this.AT_KURI_UKM_CODE = AT_KURI_UKM_CODE;
	}

	/**
	 * KURI�Ȗڂ̏���ŃR�[�h�̎擾�i����j
	 * 
	 * @return AT_KURI_ZEI_CODE �ڂ̏���ŃR�[�h
	 */
	public String getAT_KURI_ZEI_CODE() {
		return AT_KURI_ZEI_CODE;
	}

	/**
	 * KURI�Ȗڂ̏���ŃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_KURI_ZEI_CODE �Ȗڂ̏���ŃR�[�h
	 */
	public void setAT_KURI_ZEI_CODE(String AT_KURI_ZEI_CODE) {
		this.AT_KURI_ZEI_CODE = AT_KURI_ZEI_CODE;
	}

	/**
	 * �Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_KMK_NAME �Ȗږ���
	 */
	public String getAT_KMK_NAME() {
		return AT_KMK_NAME;
	}

	/**
	 * �Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_KMK_NAME �Ȗږ���
	 */
	public void setAT_KMK_NAME(String AT_KMK_NAME) {
		this.AT_KMK_NAME = AT_KMK_NAME;
	}

	/**
	 * �⏕�Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_HKM_NAME �⏕�Ȗږ���
	 */
	public String getAT_HKM_NAME() {
		return AT_HKM_NAME;
	}

	/**
	 * �⏕�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_HKM_NAME �⏕�Ȗږ���
	 */
	public void setAT_HKM_NAME(String AT_HKM_NAME) {
		this.AT_HKM_NAME = AT_HKM_NAME;
	}

	/**
	 * ����Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_UKM_NAME ����Ȗږ���
	 */
	public String getAT_UKM_NAME() {
		return AT_UKM_NAME;
	}

	/**
	 * ����Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_UKM_NAME ����Ȗ�
	 */
	public void setAT_UKM_NAME(String AT_UKM_NAME) {
		this.AT_UKM_NAME = AT_UKM_NAME;
	}

	/**
	 * ����Ŗ��̂̎擾�i����j
	 * 
	 * @return ZEI_NAME ����Ŗ���
	 */
	public String getAT_ZEI_NAME() {
		return AT_ZEI_NAME;
	}

	/**
	 * ����Ŗ��̂̐ݒ�i����j
	 * 
	 * @param AT_ZEI_NAME_S ����Ŗ���
	 */
	public void setAT_ZEI_NAME_S(String AT_ZEI_NAME_S) {
		this.AT_ZEI_NAME_S = AT_ZEI_NAME_S;
	}

	/**
	 * ����Ŗ��̂̎擾�i����j
	 * 
	 * @return ZEI_NAME_S ����Ŗ���
	 */
	public String getAT_ZEI_NAME_S() {
		return AT_ZEI_NAME_S;
	}

	/**
	 * ����Ŗ��̂̐ݒ�i����j
	 * 
	 * @param AT_ZEI_NAME_S ����Ŗ���
	 */
	public void setAT_ZEI_NAME(String AT_ZEI_NAME_S) {
		this.AT_ZEI_NAME_S = AT_ZEI_NAME_S;
	}

	/**
	 * KURI�Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_KURI_KMK_NAME �Ȗږ���
	 */
	public String getAT_KURI_KMK_NAME() {
		return AT_KURI_KMK_NAME;
	}

	/**
	 * KURI�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_KURI_KMK_NAME �Ȗږ���
	 */
	public void setAT_KURI_KMK_NAME(String AT_KURI_KMK_NAME) {
		this.AT_KURI_KMK_NAME = AT_KURI_KMK_NAME;
	}

	/**
	 * KURI�⏕�Ȗږ��̂̎擾�i����j
	 * 
	 * @return KURI_HKM_NAME �⏕�Ȗږ���
	 */
	public String getAT_KURI_HKM_NAME() {
		return AT_KURI_HKM_NAME;
	}

	/**
	 * KURI�⏕�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_KURI_HKM_NAME �⏕�Ȗږ���
	 */
	public void setAT_KURI_HKM_NAME(String AT_KURI_HKM_NAME) {
		this.AT_KURI_HKM_NAME = AT_KURI_HKM_NAME;
	}

	/**
	 * KURI����Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_KURI_UKM_NAME ����Ȗږ���
	 */
	public String getAT_KURI_UKM_NAME() {
		return AT_KURI_UKM_NAME;
	}

	/**
	 * KURI�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_KURI_UKM_NAME ����Ȗږ���
	 */
	public void setAT_KURI_UKM_NAME(String AT_KURI_UKM_NAME) {
		this.AT_KURI_UKM_NAME = AT_KURI_UKM_NAME;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̎擾�i����j
	 * 
	 * @return AT_KURI_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public String getAT_KURI_ZEI_NAME() {
		return AT_KURI_ZEI_NAME;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̐ݒ�i����j
	 * 
	 * @param AT_KURI_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public void setAT_KURI_ZEI_NAME(String AT_KURI_ZEI_NAME) {
		this.AT_KURI_ZEI_NAME = AT_KURI_ZEI_NAME;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̎擾�i����j
	 * 
	 * @return AT_KURI_ZEI_NAME_S �Ȗڂ̏���Ŗ���
	 */
	public String getAT_KURI_ZEI_NAME_S() {
		return AT_KURI_ZEI_NAME_S;
	}

	/**
	 * KURI�Ȗڂ̏���Ŗ��̂̐ݒ�i����j
	 * 
	 * @param AT_KURI_ZEI_NAME_S �Ȗڂ̏���Ŗ���
	 */
	public void setAT_KURI_ZEI_NAME_S(String AT_KURI_ZEI_NAME_S) {
		this.AT_KURI_ZEI_NAME_S = AT_KURI_ZEI_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̎擾
	 * 
	 * @return KURI_KMK_NAME_S �Ȗڗ���
	 */
	public String getKURI_KMK_NAME_S() {
		return KURI_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̐ݒ�
	 * 
	 * @param KURI_KMK_NAME_S �Ȗڗ���
	 */
	public void setKURI_KMK_NAME_S(String KURI_KMK_NAME_S) {
		this.KURI_KMK_NAME_S = KURI_KMK_NAME_S;
	}

	/**
	 * �Ȗږ��̂̎擾�i����j
	 * 
	 * @return KURI_HKM_NAME_S �Ȗږ���
	 */
	public String getKURI_HKM_NAME_S() {
		return KURI_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̐ݒ�
	 * 
	 * @param KURI_HKM_NAME_S �⏕�Ȗڗ���
	 */
	public void setKURI_HKM_NAME_S(String KURI_HKM_NAME_S) {
		this.KURI_HKM_NAME_S = KURI_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̎擾
	 * 
	 * @return KURI_HKM_NAME_S �⏕�Ȗڗ���
	 */
	public String getKURI_UKM_NAME_S() {
		return KURI_UKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂̐ݒ�i����j
	 * 
	 * @param KURI_UKM_NAME_S ����Ȗڗ���
	 */
	public void setKURI_UKM_NAME_S(String KURI_UKM_NAME_S) {
		this.KURI_UKM_NAME_S = KURI_UKM_NAME_S;
	}

	/**
	 * �Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_KURI_KMK_NAME_S �Ȗږ���
	 */
	public String getAT_KURI_KMK_NAME_S() {
		return AT_KURI_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̐ݒ�
	 * 
	 * @param AT_KURI_KMK_NAME_S �Ȗڗ���
	 */
	public void setAT_KURI_KMK_NAME_S(String AT_KURI_KMK_NAME_S) {
		this.AT_KURI_KMK_NAME_S = AT_KURI_KMK_NAME_S;
	}

	/**
	 * �⏕�Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_KURI_HKM_NAME_S �⏕�Ȗږ���
	 */
	public String getAT_KURI_HKM_NAME_S() {
		return AT_KURI_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_KURI_HKM_NAME_S �⏕�Ȗږ���
	 */
	public void setAT_KURI_HKM_NAME_S(String AT_KURI_HKM_NAME_S) {
		this.AT_KURI_HKM_NAME_S = AT_KURI_HKM_NAME_S;
	}

	/**
	 * ����Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_KURI_UKM_NAME_S ����Ȗږ���
	 */
	public String getAT_KURI_UKM_NAME_S() {
		return AT_KURI_UKM_NAME_S;
	}

	/**
	 * ����Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_KURI_UKM_NAME_S ����Ȗ�
	 */
	public void setAT_KURI_UKM_NAME_S(String AT_KURI_UKM_NAME_S) {
		this.AT_KURI_UKM_NAME_S = AT_KURI_UKM_NAME_S;
	}

	/**
	 * EST_KMK_CODE �ȖڃR�[�h�̎擾
	 * 
	 * @return EST_KMK_CODE �ڃR�[�h
	 */
	public String getEST_KMK_CODE() {
		return EST_KMK_CODE;
	}

	/**
	 * EST_KMK_CODE �ȖڃR�[�h�̐ݒ�
	 * 
	 * @param EST_KMK_CODE �ȖڃR�[�h
	 */
	public void setEST_KMK_CODE(String EST_KMK_CODE) {
		this.EST_KMK_CODE = EST_KMK_CODE;
	}

	/**
	 * EST�⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return EST_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public String getEST_HKM_CODE() {
		return EST_HKM_CODE;
	}

	/**
	 * EST�⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param EST_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public void setEST_HKM_CODE(String EST_HKM_CODE) {
		this.EST_HKM_CODE = EST_HKM_CODE;
	}

	/**
	 * AddCom����ȖڃR�[�h�̎擾
	 * 
	 * @return ADDCOM_UKM_CODE AddCom����ȖڃR�[�h
	 */
	public String getEST_UKM_CODE() {
		return EST_UKM_CODE;
	}

	/**
	 * EST����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param EST_UKM_CODE AddCom����ȖڃR�[�h
	 */
	public void setEST_UKM_CODE(String EST_UKM_CODE) {
		this.EST_UKM_CODE = EST_UKM_CODE;
	}

	/**
	 * EST�Ȗڂ̏���ŃR�[�h�̎擾
	 * 
	 * @return EST_ZEI_CODE �ڂ̏���ŃR�[�h
	 */
	public String getEST_ZEI_CODE() {
		return EST_ZEI_CODE;
	}

	/**
	 * EST�Ȗڂ̏���ŃR�[�h�̐ݒ�
	 * 
	 * @param EST_ZEI_CODE �Ȗڂ̏���ŃR�[�h
	 */
	public void setEST_ZEI_CODE(String EST_ZEI_CODE) {
		this.EST_ZEI_CODE = EST_ZEI_CODE;
	}

	/**
	 * EST�Ȗږ��̂̎擾
	 * 
	 * @return EST_KMK_NAME �Ȗږ���
	 */
	public String getEST_KMK_NAME() {
		return EST_KMK_NAME;
	}

	/**
	 * EST�Ȗږ��̂̐ݒ�
	 * 
	 * @param EST_KMK_NAME �Ȗږ���
	 */
	public void setEST_KMK_NAME(String EST_KMK_NAME) {
		this.EST_KMK_NAME = EST_KMK_NAME;
	}

	/**
	 * EST�⏕�Ȗږ��̂̎擾
	 * 
	 * @return EST_HKM_NAME �⏕�Ȗږ���
	 */
	public String getEST_HKM_NAME() {
		return EST_HKM_NAME;
	}

	/**
	 * EST�⏕�Ȗږ��̂̐ݒ�
	 * 
	 * @param EST_HKM_NAME �⏕�Ȗږ���
	 */
	public void setEST_HKM_NAME(String EST_HKM_NAME) {
		this.EST_HKM_NAME = EST_HKM_NAME;
	}

	/**
	 * EST����Ȗږ��̂̎擾
	 * 
	 * @return EST_UKM_NAME ����Ȗږ���
	 */
	public String getEST_UKM_NAME() {
		return EST_UKM_NAME;
	}

	/**
	 * EST�Ȗږ��̂̐ݒ�
	 * 
	 * @param EST_UKM_NAME ����Ȗږ���
	 */
	public void setEST_UKM_NAME(String EST_UKM_NAME) {
		this.EST_UKM_NAME = EST_UKM_NAME;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̎擾
	 * 
	 * @return EST_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public String getEST_ZEI_NAME() {
		return EST_ZEI_NAME;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̐ݒ�
	 * 
	 * @param EST_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public void setEST_ZEI_NAME(String EST_ZEI_NAME) {
		this.EST_ZEI_NAME = EST_ZEI_NAME;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̎擾
	 * 
	 * @return EST_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public String getEST_ZEI_NAME_S() {
		return EST_ZEI_NAME_S;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̐ݒ�
	 * 
	 * @param EST_ZEI_NAME_S �Ȗڂ̏���Ŗ���
	 */
	public void setEST_ZEI_NAME_S(String EST_ZEI_NAME_S) {
		this.EST_ZEI_NAME_S = EST_ZEI_NAME_S;
	}

	/**
	 * AT_EST_KMK_CODE �ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_EST_KMK_CODE �ڃR�[�h
	 */
	public String getAT_EST_KMK_CODE() {
		return AT_EST_KMK_CODE;
	}

	/**
	 * AT_EST_KMK_CODE �ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_EST_KMK_CODE �ȖڃR�[�h
	 */
	public void setAT_EST_KMK_CODE(String AT_EST_KMK_CODE) {
		this.AT_EST_KMK_CODE = AT_EST_KMK_CODE;
	}

	/**
	 * EST�⏕�ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_EST_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public String getAT_EST_HKM_CODE() {
		return AT_EST_HKM_CODE;
	}

	/**
	 * EST�⏕�ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_EST_HKM_CODE �⏕�ȖڃR�[�h
	 */
	public void setAT_EST_HKM_CODE(String AT_EST_HKM_CODE) {
		this.AT_EST_HKM_CODE = AT_EST_HKM_CODE;
	}

	/**
	 * EST����ȖڃR�[�h�̎擾�i����j
	 * 
	 * @return AT_EST_UKM_CODE ����ȖڃR�[�h
	 */
	public String getAT_EST_UKM_CODE() {
		return AT_EST_UKM_CODE;
	}

	/**
	 * EST����ȖڃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_EST_UKM_CODE ����ȖڃR�[�h
	 */
	public void setAT_EST_UKM_CODE(String AT_EST_UKM_CODE) {
		this.AT_EST_UKM_CODE = AT_EST_UKM_CODE;
	}

	/**
	 * EST�Ȗڂ̏���ŃR�[�h�̎擾�i����j
	 * 
	 * @return AT_EST_ZEI_CODE �ڂ̏���ŃR�[�h
	 */
	public String getAT_EST_ZEI_CODE() {
		return AT_EST_ZEI_CODE;
	}

	/**
	 * EST�Ȗڂ̏���ŃR�[�h�̐ݒ�i����j
	 * 
	 * @param AT_EST_ZEI_CODE �Ȗڂ̏���ŃR�[�h
	 */
	public void setAT_EST_ZEI_CODE(String AT_EST_ZEI_CODE) {
		this.AT_EST_ZEI_CODE = AT_EST_ZEI_CODE;
	}

	/**
	 * EST�Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_EST_KMK_NAME �Ȗږ���
	 */
	public String getAT_EST_KMK_NAME() {
		return AT_EST_KMK_NAME;
	}

	/**
	 * EST�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_EST_KMK_NAME �Ȗږ���
	 */
	public void setAT_EST_KMK_NAME(String AT_EST_KMK_NAME) {
		this.AT_EST_KMK_NAME = AT_EST_KMK_NAME;
	}

	/**
	 * EST�⏕�Ȗږ��̂̎擾�i����j
	 * 
	 * @return EST_HKM_NAME �⏕�Ȗږ���
	 */
	public String getAT_EST_HKM_NAME() {
		return AT_EST_HKM_NAME;
	}

	/**
	 * EST�⏕�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_EST_HKM_NAME �⏕�Ȗږ���
	 */
	public void setAT_EST_HKM_NAME(String AT_EST_HKM_NAME) {
		this.AT_EST_HKM_NAME = AT_EST_HKM_NAME;
	}

	/**
	 * EST����Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_EST_UKM_NAME ����Ȗږ���
	 */
	public String getAT_EST_UKM_NAME() {
		return AT_EST_UKM_NAME;
	}

	/**
	 * EST�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_EST_UKM_NAME ����Ȗږ���
	 */
	public void setAT_EST_UKM_NAME(String AT_EST_UKM_NAME) {
		this.AT_EST_UKM_NAME = AT_EST_UKM_NAME;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̎擾�i����j
	 * 
	 * @return AT_EST_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public String getAT_EST_ZEI_NAME() {
		return AT_EST_ZEI_NAME;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̐ݒ�i����j
	 * 
	 * @param AT_EST_ZEI_NAME �Ȗڂ̏���Ŗ���
	 */
	public void setAT_EST_ZEI_NAME(String AT_EST_ZEI_NAME) {
		this.AT_EST_ZEI_NAME = AT_EST_ZEI_NAME;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̎擾�i����j
	 * 
	 * @return AT_EST_ZEI_NAME_S �Ȗڂ̏���Ŗ���
	 */
	public String getAT_EST_ZEI_NAME_S() {
		return AT_EST_ZEI_NAME_S;
	}

	/**
	 * EST�Ȗڂ̏���Ŗ��̂̐ݒ�i����j
	 * 
	 * @param AT_EST_ZEI_NAME_S �Ȗڂ̏���Ŗ���
	 */
	public void setAT_EST_ZEI_NAME_S(String AT_EST_ZEI_NAME_S) {
		this.AT_EST_ZEI_NAME_S = AT_EST_ZEI_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̎擾
	 * 
	 * @return EST_KMK_NAME_S �Ȗڗ���
	 */
	public String getEST_KMK_NAME_S() {
		return EST_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̐ݒ�
	 * 
	 * @param EST_KMK_NAME_S �Ȗڗ���
	 */
	public void setEST_KMK_NAME_S(String EST_KMK_NAME_S) {
		this.EST_KMK_NAME_S = EST_KMK_NAME_S;
	}

	/**
	 * �Ȗږ��̂̎擾�i����j
	 * 
	 * @return EST_HKM_NAME_S �Ȗږ���
	 */
	public String getEST_HKM_NAME_S() {
		return EST_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̐ݒ�
	 * 
	 * @param EST_HKM_NAME_S �⏕�Ȗڗ���
	 */
	public void setEST_HKM_NAME_S(String EST_HKM_NAME_S) {
		this.EST_HKM_NAME_S = EST_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗڗ��̂̎擾
	 * 
	 * @return EST_HKM_NAME_S �⏕�Ȗڗ���
	 */
	public String getEST_UKM_NAME_S() {
		return EST_UKM_NAME_S;
	}

	/**
	 * ����Ȗڗ��̂̐ݒ�i����j
	 * 
	 * @param EST_UKM_NAME_S ����Ȗڗ���
	 */
	public void setEST_UKM_NAME_S(String EST_UKM_NAME_S) {
		this.EST_UKM_NAME_S = EST_UKM_NAME_S;
	}

	/**
	 * �Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_EST_KMK_NAME_S �Ȗږ���
	 */
	public String getAT_EST_KMK_NAME_S() {
		return AT_EST_KMK_NAME_S;
	}

	/**
	 * �Ȗڗ��̂̐ݒ�
	 * 
	 * @param AT_EST_KMK_NAME_S �Ȗڗ���
	 */
	public void setAT_EST_KMK_NAME_S(String AT_EST_KMK_NAME_S) {
		this.AT_EST_KMK_NAME_S = AT_EST_KMK_NAME_S;
	}

	/**
	 * �⏕�Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_EST_HKM_NAME_S �⏕�Ȗږ���
	 */
	public String getAT_EST_HKM_NAME_S() {
		return AT_EST_HKM_NAME_S;
	}

	/**
	 * �⏕�Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_EST_HKM_NAME_S �⏕�Ȗږ���
	 */
	public void setAT_EST_HKM_NAME_S(String AT_EST_HKM_NAME_S) {
		this.AT_EST_HKM_NAME_S = AT_EST_HKM_NAME_S;
	}

	/**
	 * ����Ȗږ��̂̎擾�i����j
	 * 
	 * @return AT_EST_UKM_NAME_S ����Ȗږ���
	 */
	public String getAT_EST_UKM_NAME_S() {
		return AT_EST_UKM_NAME_S;
	}

	/**
	 * ����Ȗږ��̂̐ݒ�i����j
	 * 
	 * @param AT_EST_UKM_NAME_S ����Ȗ�
	 */
	public void setAT_EST_UKM_NAME_S(String AT_EST_UKM_NAME_S) {
		this.AT_EST_UKM_NAME_S = AT_EST_UKM_NAME_S;
	}

	/**
	 * SA�A�C�e���R�[�h���擾���܂��B
	 * 
	 * @return SA�A�C�e���R�[�h
	 */
	public String getCode() {
		return getITEM_CODE();
	}

	/**
	 * SA�A�C�e���R�[�h��ݒ肵�܂��B
	 * 
	 * @param code SA�A�C�e���R�[�h
	 */
	public void setCode(String code) {
		setITEM_CODE(code);
	}

	/**
	 * SA�A�C�e�����̂��擾���܂��B
	 * 
	 * @return SA�A�C�e������
	 */
	public String getName() {
		return getITEM_NAME();
	}

	/**
	 * SA�A�C�e�����̂�ݒ肵�܂��B
	 * 
	 * @param name SA�A�C�e������
	 */
	public void setName(String name) {
		setITEM_NAME(name);
	}

	/**
	 * �A�C�e�����̂��擾���܂��B
	 * 
	 * @return SA�A�C�e������
	 */
	public String getNames() {
		return getITEM_NAME_S();
	}

	/**
	 * �A�C�e�����̂�ݒ肷��B
	 * 
	 * @param names SA�A�C�e������
	 */
	public void setNames(String names) {
		setITEM_NAME_S(names);
	}

	/**
	 * �A�C�e���������̂��擾���܂��B
	 * 
	 * @return SA�A�C�e����������
	 */
	public String getNamek() {
		return getITEM_NAME_K();
	}

	/**
	 * �A�C�e���������̂�ݒ肷��B
	 * 
	 * @param namek SA�A�C�e����������
	 */
	public void setNamek(String namek) {
		setITEM_NAME_K(namek);
	}

	/**
	 * �J�n����߂��܂��B
	 * 
	 * @return �J�n��
	 */
	public Date getDateFrom() {
		return STR_DATE;
	}

	/**
	 * �I������߂��܂��B
	 * 
	 * @return �I����
	 */
	public Date getDateTo() {
		return END_DATE;
	}

	/**
	 * Return value for BNKR_TYPE_CODE
	 * 
	 * @return BNKR_TYPE_CODE
	 */
	public String getBNKR_TYPE_CODE() {
		return BNKR_TYPE_CODE;
	}

	/**
	 * Set value for bNKR_TYPE_CODE
	 * 
	 * @param bNKR_TYPE_CODE
	 */
	public void setBNKR_TYPE_CODE(String bNKR_TYPE_CODE) {
		BNKR_TYPE_CODE = bNKR_TYPE_CODE;
	}

	/**
	 * VC/VR�ϊ��A�C�e���R�[�h�̎擾
	 * 
	 * @return VCVR_ITEM_CODE VC/VR�ϊ��A�C�e���R�[�h
	 */
	public String getVCVR_ITEM_CODE() {
		return VCVR_ITEM_CODE;
	}

	/**
	 * VC/VR�ϊ��A�C�e���R�[�h�̐ݒ�
	 * 
	 * @param VCVR_ITEM_CODE VC/VR�ϊ��A�C�e���R�[�h
	 */
	public void setVCVR_ITEM_CODE(String VCVR_ITEM_CODE) {
		this.VCVR_ITEM_CODE = VCVR_ITEM_CODE;
	}

	/**
	 * ���v�ؕ��@�ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_KMK_CODE ���v�ؕ��@�ȖڃR�[�h
	 */
	public String getNEW_ACC_DR_KMK_CODE() {
		return NEW_ACC_DR_KMK_CODE;
	}

	/**
	 * ���v�ؕ��@�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_KMK_CODE ���v�ؕ��@�ȖڃR�[�h
	 */
	public void setNEW_ACC_DR_KMK_CODE(String NEW_ACC_DR_KMK_CODE) {
		this.NEW_ACC_DR_KMK_CODE = NEW_ACC_DR_KMK_CODE;
	}

	/**
	 * ���v�ؕ��@�⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_HKM_CODE ���v�ؕ��@�⏕�ȖڃR�[�h
	 */
	public String getNEW_ACC_DR_HKM_CODE() {
		return NEW_ACC_DR_HKM_CODE;
	}

	/**
	 * ���v�ؕ��@�⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_HKM_CODE ���v�ؕ��@�⏕�ȖڃR�[�h
	 */
	public void setNEW_ACC_DR_HKM_CODE(String NEW_ACC_DR_HKM_CODE) {
		this.NEW_ACC_DR_HKM_CODE = NEW_ACC_DR_HKM_CODE;
	}

	/**
	 * ���v�ؕ��@����ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_UKM_CODE ���v�ؕ��@����ȖڃR�[�h
	 */
	public String getNEW_ACC_DR_UKM_CODE() {
		return NEW_ACC_DR_UKM_CODE;
	}

	/**
	 * ���v�ؕ��@����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_UKM_CODE ���v�ؕ��@����ȖڃR�[�h
	 */
	public void setNEW_ACC_DR_UKM_CODE(String NEW_ACC_DR_UKM_CODE) {
		this.NEW_ACC_DR_UKM_CODE = NEW_ACC_DR_UKM_CODE;
	}

	/**
	 * ���v�ؕ��@����ŃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_ZEI_CODE ���v�ؕ��@����ŃR�[�h
	 */
	public String getNEW_ACC_DR_ZEI_CODE() {
		return NEW_ACC_DR_ZEI_CODE;
	}

	/**
	 * ���v�ؕ��@����ŃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_ZEI_CODE ���v�ؕ��@����ŃR�[�h
	 */
	public void setNEW_ACC_DR_ZEI_CODE(String NEW_ACC_DR_ZEI_CODE) {
		this.NEW_ACC_DR_ZEI_CODE = NEW_ACC_DR_ZEI_CODE;
	}

	/**
	 * ���v�ؕ��@�Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_KMK_NAME ���v�ؕ��@�Ȗږ���
	 */
	public String getNEW_ACC_DR_KMK_NAME() {
		return NEW_ACC_DR_KMK_NAME;
	}

	/**
	 * ���v�ؕ��@�Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_KMK_NAME ���v�ؕ��@�Ȗږ���
	 */
	public void setNEW_ACC_DR_KMK_NAME(String NEW_ACC_DR_KMK_NAME) {
		this.NEW_ACC_DR_KMK_NAME = NEW_ACC_DR_KMK_NAME;
	}

	/**
	 * ���v�ؕ��@�⏕�Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_HKM_NAME ���v�ؕ��@�⏕�Ȗږ���
	 */
	public String getNEW_ACC_DR_HKM_NAME() {
		return NEW_ACC_DR_HKM_NAME;
	}

	/**
	 * ���v�ؕ��@�⏕�Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_HKM_NAME ���v�ؕ��@�⏕�Ȗږ���
	 */
	public void setNEW_ACC_DR_HKM_NAME(String NEW_ACC_DR_HKM_NAME) {
		this.NEW_ACC_DR_HKM_NAME = NEW_ACC_DR_HKM_NAME;
	}

	/**
	 * ���v�ؕ��@����Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_UKM_NAME ���v�ؕ��@����Ȗږ���
	 */
	public String getNEW_ACC_DR_UKM_NAME() {
		return NEW_ACC_DR_UKM_NAME;
	}

	/**
	 * ���v�ؕ��@����Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_UKM_NAME ���v�ؕ��@����Ȗږ���
	 */
	public void setNEW_ACC_DR_UKM_NAME(String NEW_ACC_DR_UKM_NAME) {
		this.NEW_ACC_DR_UKM_NAME = NEW_ACC_DR_UKM_NAME;
	}

	/**
	 * ���v�ؕ��@����Ŗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_ZEI_NAME ���v�ؕ��@����Ŗ���
	 */
	public String getNEW_ACC_DR_ZEI_NAME() {
		return NEW_ACC_DR_ZEI_NAME;
	}

	/**
	 * ���v�ؕ��@����Ŗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_ZEI_NAME ���v�ؕ��@����Ŗ���
	 */
	public void setNEW_ACC_DR_ZEI_NAME(String NEW_ACC_DR_ZEI_NAME) {
		this.NEW_ACC_DR_ZEI_NAME = NEW_ACC_DR_ZEI_NAME;
	}

	/**
	 * ���v�ؕ��@�Ȗڂ̎擾
	 * 
	 * @return NEW_ACC_DR_KMK_NAME_S ���v�ؕ��@�Ȗ�
	 */
	public String getNEW_ACC_DR_KMK_NAME_S() {
		return NEW_ACC_DR_KMK_NAME_S;
	}

	/**
	 * ���v�ؕ��@�Ȗڂ̐ݒ�
	 * 
	 * @param NEW_ACC_DR_KMK_NAME_S ���v�ؕ��@�Ȗ�
	 */
	public void setNEW_ACC_DR_KMK_NAME_S(String NEW_ACC_DR_KMK_NAME_S) {
		this.NEW_ACC_DR_KMK_NAME_S = NEW_ACC_DR_KMK_NAME_S;
	}

	/**
	 * ���v�ؕ��@�⏕�Ȗڗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_HKM_NAME_S ���v�ؕ��@�⏕�Ȗڗ���
	 */
	public String getNEW_ACC_DR_HKM_NAME_S() {
		return NEW_ACC_DR_HKM_NAME_S;
	}

	/**
	 * ���v�ؕ��@�⏕�Ȗڗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_HKM_NAME_S ���v�ؕ��@�⏕�Ȗڗ���
	 */
	public void setNEW_ACC_DR_HKM_NAME_S(String NEW_ACC_DR_HKM_NAME_S) {
		this.NEW_ACC_DR_HKM_NAME_S = NEW_ACC_DR_HKM_NAME_S;
	}

	/**
	 * ���v�ؕ��@����Ȗڗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_UKM_NAME_S ���v�ؕ��@����Ȗڗ���
	 */
	public String getNEW_ACC_DR_UKM_NAME_S() {
		return NEW_ACC_DR_UKM_NAME_S;
	}

	/**
	 * ���v�ؕ��@����Ȗڗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_UKM_NAME_S ���v�ؕ��@����Ȗڗ���
	 */
	public void setNEW_ACC_DR_UKM_NAME_S(String NEW_ACC_DR_UKM_NAME_S) {
		this.NEW_ACC_DR_UKM_NAME_S = NEW_ACC_DR_UKM_NAME_S;
	}

	/**
	 * ���v�ؕ��@����ŗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_ZEI_NAME_S ���v�ؕ��@����ŗ���
	 */
	public String getNEW_ACC_DR_ZEI_NAME_S() {
		return NEW_ACC_DR_ZEI_NAME_S;
	}

	/**
	 * ���v�ؕ��@����ŗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_ZEI_NAME_S ���v�ؕ��@����ŗ���
	 */
	public void setNEW_ACC_DR_ZEI_NAME_S(String NEW_ACC_DR_ZEI_NAME_S) {
		this.NEW_ACC_DR_ZEI_NAME_S = NEW_ACC_DR_ZEI_NAME_S;
	}

	/**
	 * ���v�ݕ��@�ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_CR_KMK_CODE ���v�ݕ��@�ȖڃR�[�h
	 */
	public String getNEW_ACC_CR_KMK_CODE() {
		return NEW_ACC_CR_KMK_CODE;
	}

	/**
	 * ���v�ݕ��@�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_CR_KMK_CODE ���v�ݕ��@�ȖڃR�[�h
	 */
	public void setNEW_ACC_CR_KMK_CODE(String NEW_ACC_CR_KMK_CODE) {
		this.NEW_ACC_CR_KMK_CODE = NEW_ACC_CR_KMK_CODE;
	}

	/**
	 * ���v�ݕ��@�⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_CR_HKM_CODE ���v�ݕ��@�⏕�ȖڃR�[�h
	 */
	public String getNEW_ACC_CR_HKM_CODE() {
		return NEW_ACC_CR_HKM_CODE;
	}

	/**
	 * ���v�ݕ��@�⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_CR_HKM_CODE ���v�ݕ��@�⏕�ȖڃR�[�h
	 */
	public void setNEW_ACC_CR_HKM_CODE(String NEW_ACC_CR_HKM_CODE) {
		this.NEW_ACC_CR_HKM_CODE = NEW_ACC_CR_HKM_CODE;
	}

	/**
	 * ���v�ݕ��@����ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_CR_UKM_CODE ���v�ݕ��@����ȖڃR�[�h
	 */
	public String getNEW_ACC_CR_UKM_CODE() {
		return NEW_ACC_CR_UKM_CODE;
	}

	/**
	 * ���v�ݕ��@����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_CR_UKM_CODE ���v�ݕ��@����ȖڃR�[�h
	 */
	public void setNEW_ACC_CR_UKM_CODE(String NEW_ACC_CR_UKM_CODE) {
		this.NEW_ACC_CR_UKM_CODE = NEW_ACC_CR_UKM_CODE;
	}

	/**
	 * ���v�ݕ��@����ŃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_CR_ZEI_CODE ���v�ݕ��@����ŃR�[�h
	 */
	public String getNEW_ACC_CR_ZEI_CODE() {
		return NEW_ACC_CR_ZEI_CODE;
	}

	/**
	 * ���v�ݕ��@����ŃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_CR_ZEI_CODE ���v�ݕ��@����ŃR�[�h
	 */
	public void setNEW_ACC_CR_ZEI_CODE(String NEW_ACC_CR_ZEI_CODE) {
		this.NEW_ACC_CR_ZEI_CODE = NEW_ACC_CR_ZEI_CODE;
	}

	/**
	 * ���v�ݕ��@�Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_CR_KMK_NAME ���v�ݕ��@�Ȗږ���
	 */
	public String getNEW_ACC_CR_KMK_NAME() {
		return NEW_ACC_CR_KMK_NAME;
	}

	/**
	 * ���v�ݕ��@�Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_CR_KMK_NAME ���v�ݕ��@�Ȗږ���
	 */
	public void setNEW_ACC_CR_KMK_NAME(String NEW_ACC_CR_KMK_NAME) {
		this.NEW_ACC_CR_KMK_NAME = NEW_ACC_CR_KMK_NAME;
	}

	/**
	 * ���v�ݕ��@�⏕�Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_CR_HKM_NAME ���v�ݕ��@�⏕�Ȗږ���
	 */
	public String getNEW_ACC_CR_HKM_NAME() {
		return NEW_ACC_CR_HKM_NAME;
	}

	/**
	 * ���v�ݕ��@�⏕�Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_CR_HKM_NAME ���v�ݕ��@�⏕�Ȗږ���
	 */
	public void setNEW_ACC_CR_HKM_NAME(String NEW_ACC_CR_HKM_NAME) {
		this.NEW_ACC_CR_HKM_NAME = NEW_ACC_CR_HKM_NAME;
	}

	/**
	 * ���v�ݕ��@����Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_CR_UKM_NAME ���v�ݕ��@����Ȗږ���
	 */
	public String getNEW_ACC_CR_UKM_NAME() {
		return NEW_ACC_CR_UKM_NAME;
	}

	/**
	 * ���v�ݕ��@����Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_CR_UKM_NAME ���v�ݕ��@����Ȗږ���
	 */
	public void setNEW_ACC_CR_UKM_NAME(String NEW_ACC_CR_UKM_NAME) {
		this.NEW_ACC_CR_UKM_NAME = NEW_ACC_CR_UKM_NAME;
	}

	/**
	 * ���v�ݕ��@����Ŗ��̂̎擾
	 * 
	 * @return NEW_ACC_CR_ZEI_NAME ���v�ݕ��@����Ŗ���
	 */
	public String getNEW_ACC_CR_ZEI_NAME() {
		return NEW_ACC_CR_ZEI_NAME;
	}

	/**
	 * ���v�ݕ��@����Ŗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_CR_ZEI_NAME ���v�ݕ��@����Ŗ���
	 */
	public void setNEW_ACC_CR_ZEI_NAME(String NEW_ACC_CR_ZEI_NAME) {
		this.NEW_ACC_CR_ZEI_NAME = NEW_ACC_CR_ZEI_NAME;
	}

	/**
	 * ���v�ݕ��@�Ȗڂ̎擾
	 * 
	 * @return NEW_ACC_CR_KMK_NAME_S ���v�ݕ��@�Ȗ�
	 */
	public String getNEW_ACC_CR_KMK_NAME_S() {
		return NEW_ACC_CR_KMK_NAME_S;
	}

	/**
	 * ���v�ݕ��@�Ȗڂ̐ݒ�
	 * 
	 * @param NEW_ACC_CR_KMK_NAME_S ���v�ݕ��@�Ȗ�
	 */
	public void setNEW_ACC_CR_KMK_NAME_S(String NEW_ACC_CR_KMK_NAME_S) {
		this.NEW_ACC_CR_KMK_NAME_S = NEW_ACC_CR_KMK_NAME_S;
	}

	/**
	 * ���v�ݕ��@�⏕�Ȗڗ��̂̎擾
	 * 
	 * @return NEW_ACC_CR_HKM_NAME_S ���v�ݕ��@�⏕�Ȗڗ���
	 */
	public String getNEW_ACC_CR_HKM_NAME_S() {
		return NEW_ACC_CR_HKM_NAME_S;
	}

	/**
	 * ���v�ݕ��@�⏕�Ȗڗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_CR_HKM_NAME_S ���v�ݕ��@�⏕�Ȗڗ���
	 */
	public void setNEW_ACC_CR_HKM_NAME_S(String NEW_ACC_CR_HKM_NAME_S) {
		this.NEW_ACC_CR_HKM_NAME_S = NEW_ACC_CR_HKM_NAME_S;
	}

	/**
	 * ���v�ݕ��@����Ȗڗ��̂̎擾
	 * 
	 * @return NEW_ACC_CR_UKM_NAME_S ���v�ݕ��@����Ȗڗ���
	 */
	public String getNEW_ACC_CR_UKM_NAME_S() {
		return NEW_ACC_CR_UKM_NAME_S;
	}

	/**
	 * ���v�ݕ��@����Ȗڗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_CR_UKM_NAME_S ���v�ݕ��@����Ȗڗ���
	 */
	public void setNEW_ACC_CR_UKM_NAME_S(String NEW_ACC_CR_UKM_NAME_S) {
		this.NEW_ACC_CR_UKM_NAME_S = NEW_ACC_CR_UKM_NAME_S;
	}

	/**
	 * ���v�ݕ��@����ŗ��̂̎擾
	 * 
	 * @return NEW_ACC_CR_ZEI_NAME_S ���v�ݕ��@����ŗ���
	 */
	public String getNEW_ACC_CR_ZEI_NAME_S() {
		return NEW_ACC_CR_ZEI_NAME_S;
	}

	/**
	 * ���v�ݕ��@����ŗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_CR_ZEI_NAME_S ���v�ݕ��@����ŗ���
	 */
	public void setNEW_ACC_CR_ZEI_NAME_S(String NEW_ACC_CR_ZEI_NAME_S) {
		this.NEW_ACC_CR_ZEI_NAME_S = NEW_ACC_CR_ZEI_NAME_S;
	}

	/**
	 * ���v�ؕ��A�ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_2_KMK_CODE ���v�ؕ��A�ȖڃR�[�h
	 */
	public String getNEW_ACC_DR_2_KMK_CODE() {
		return NEW_ACC_DR_2_KMK_CODE;
	}

	/**
	 * ���v�ؕ��A�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_KMK_CODE ���v�ؕ��A�ȖڃR�[�h
	 */
	public void setNEW_ACC_DR_2_KMK_CODE(String NEW_ACC_DR_2_KMK_CODE) {
		this.NEW_ACC_DR_2_KMK_CODE = NEW_ACC_DR_2_KMK_CODE;
	}

	/**
	 * ���v�ؕ��A�⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_2_HKM_CODE ���v�ؕ��A�⏕�ȖڃR�[�h
	 */
	public String getNEW_ACC_DR_2_HKM_CODE() {
		return NEW_ACC_DR_2_HKM_CODE;
	}

	/**
	 * ���v�ؕ��A�⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_HKM_CODE ���v�ؕ��A�⏕�ȖڃR�[�h
	 */
	public void setNEW_ACC_DR_2_HKM_CODE(String NEW_ACC_DR_2_HKM_CODE) {
		this.NEW_ACC_DR_2_HKM_CODE = NEW_ACC_DR_2_HKM_CODE;
	}

	/**
	 * ���v�ؕ��A����ȖڃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_2_UKM_CODE ���v�ؕ��A����ȖڃR�[�h
	 */
	public String getNEW_ACC_DR_2_UKM_CODE() {
		return NEW_ACC_DR_2_UKM_CODE;
	}

	/**
	 * ���v�ؕ��A����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_UKM_CODE ���v�ؕ��A����ȖڃR�[�h
	 */
	public void setNEW_ACC_DR_2_UKM_CODE(String NEW_ACC_DR_2_UKM_CODE) {
		this.NEW_ACC_DR_2_UKM_CODE = NEW_ACC_DR_2_UKM_CODE;
	}

	/**
	 * ���v�ؕ��A����ŃR�[�h�̎擾
	 * 
	 * @return NEW_ACC_DR_2_ZEI_CODE ���v�ؕ��A����ŃR�[�h
	 */
	public String getNEW_ACC_DR_2_ZEI_CODE() {
		return NEW_ACC_DR_2_ZEI_CODE;
	}

	/**
	 * ���v�ؕ��A����ŃR�[�h�̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_ZEI_CODE ���v�ؕ��A����ŃR�[�h
	 */
	public void setNEW_ACC_DR_2_ZEI_CODE(String NEW_ACC_DR_2_ZEI_CODE) {
		this.NEW_ACC_DR_2_ZEI_CODE = NEW_ACC_DR_2_ZEI_CODE;
	}

	/**
	 * ���v�ؕ��A�Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_2_KMK_NAME ���v�ؕ��A�Ȗږ���
	 */
	public String getNEW_ACC_DR_2_KMK_NAME() {
		return NEW_ACC_DR_2_KMK_NAME;
	}

	/**
	 * ���v�ؕ��A�Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_KMK_NAME ���v�ؕ��A�Ȗږ���
	 */
	public void setNEW_ACC_DR_2_KMK_NAME(String NEW_ACC_DR_2_KMK_NAME) {
		this.NEW_ACC_DR_2_KMK_NAME = NEW_ACC_DR_2_KMK_NAME;
	}

	/**
	 * ���v�ؕ��A�⏕�Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_2_HKM_NAME ���v�ؕ��A�⏕�Ȗږ���
	 */
	public String getNEW_ACC_DR_2_HKM_NAME() {
		return NEW_ACC_DR_2_HKM_NAME;
	}

	/**
	 * ���v�ؕ��A�⏕�Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_HKM_NAME ���v�ؕ��A�⏕�Ȗږ���
	 */
	public void setNEW_ACC_DR_2_HKM_NAME(String NEW_ACC_DR_2_HKM_NAME) {
		this.NEW_ACC_DR_2_HKM_NAME = NEW_ACC_DR_2_HKM_NAME;
	}

	/**
	 * ���v�ؕ��A����Ȗږ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_2_UKM_NAME ���v�ؕ��A����Ȗږ���
	 */
	public String getNEW_ACC_DR_2_UKM_NAME() {
		return NEW_ACC_DR_2_UKM_NAME;
	}

	/**
	 * ���v�ؕ��A����Ȗږ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_UKM_NAME ���v�ؕ��A����Ȗږ���
	 */
	public void setNEW_ACC_DR_2_UKM_NAME(String NEW_ACC_DR_2_UKM_NAME) {
		this.NEW_ACC_DR_2_UKM_NAME = NEW_ACC_DR_2_UKM_NAME;
	}

	/**
	 * ���v�ؕ��A����Ŗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_2_ZEI_NAME ���v�ؕ��A����Ŗ���
	 */
	public String getNEW_ACC_DR_2_ZEI_NAME() {
		return NEW_ACC_DR_2_ZEI_NAME;
	}

	/**
	 * ���v�ؕ��A����Ŗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_ZEI_NAME ���v�ؕ��A����Ŗ���
	 */
	public void setNEW_ACC_DR_2_ZEI_NAME(String NEW_ACC_DR_2_ZEI_NAME) {
		this.NEW_ACC_DR_2_ZEI_NAME = NEW_ACC_DR_2_ZEI_NAME;
	}

	/**
	 * ���v�ؕ��A�Ȗڂ̎擾
	 * 
	 * @return NEW_ACC_DR_2_KMK_NAME_S ���v�ؕ��A�Ȗ�
	 */
	public String getNEW_ACC_DR_2_KMK_NAME_S() {
		return NEW_ACC_DR_2_KMK_NAME_S;
	}

	/**
	 * ���v�ؕ��A�Ȗڂ̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_KMK_NAME_S ���v�ؕ��A�Ȗ�
	 */
	public void setNEW_ACC_DR_2_KMK_NAME_S(String NEW_ACC_DR_2_KMK_NAME_S) {
		this.NEW_ACC_DR_2_KMK_NAME_S = NEW_ACC_DR_2_KMK_NAME_S;
	}

	/**
	 * ���v�ؕ��A�⏕�Ȗڗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_2_HKM_NAME_S ���v�ؕ��A�⏕�Ȗڗ���
	 */
	public String getNEW_ACC_DR_2_HKM_NAME_S() {
		return NEW_ACC_DR_2_HKM_NAME_S;
	}

	/**
	 * ���v�ؕ��A�⏕�Ȗڗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_HKM_NAME_S ���v�ؕ��A�⏕�Ȗڗ���
	 */
	public void setNEW_ACC_DR_2_HKM_NAME_S(String NEW_ACC_DR_2_HKM_NAME_S) {
		this.NEW_ACC_DR_2_HKM_NAME_S = NEW_ACC_DR_2_HKM_NAME_S;
	}

	/**
	 * ���v�ؕ��A����Ȗڗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_2_UKM_NAME_S ���v�ؕ��A����Ȗڗ���
	 */
	public String getNEW_ACC_DR_2_UKM_NAME_S() {
		return NEW_ACC_DR_2_UKM_NAME_S;
	}

	/**
	 * ���v�ؕ��A����Ȗڗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_UKM_NAME_S ���v�ؕ��A����Ȗڗ���
	 */
	public void setNEW_ACC_DR_2_UKM_NAME_S(String NEW_ACC_DR_2_UKM_NAME_S) {
		this.NEW_ACC_DR_2_UKM_NAME_S = NEW_ACC_DR_2_UKM_NAME_S;
	}

	/**
	 * ���v�ؕ��A����ŗ��̂̎擾
	 * 
	 * @return NEW_ACC_DR_2_ZEI_NAME_S ���v�ؕ��A����ŗ���
	 */
	public String getNEW_ACC_DR_2_ZEI_NAME_S() {
		return NEW_ACC_DR_2_ZEI_NAME_S;
	}

	/**
	 * ���v�ؕ��A����ŗ��̂̐ݒ�
	 * 
	 * @param NEW_ACC_DR_2_ZEI_NAME_S ���v�ؕ��A����ŗ���
	 */
	public void setNEW_ACC_DR_2_ZEI_NAME_S(String NEW_ACC_DR_2_ZEI_NAME_S) {
		this.NEW_ACC_DR_2_ZEI_NAME_S = NEW_ACC_DR_2_ZEI_NAME_S;
	}

}
