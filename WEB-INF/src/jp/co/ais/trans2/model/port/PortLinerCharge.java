package jp.co.ais.trans2.model.port;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * PortLinerCharge
 */
public class PortLinerCharge extends TransferBase {

	/**
	 * �N���[��.
	 * 
	 * @return Port Liner Charge
	 */
	@Override
	public PortLinerCharge clone() {
		try {
			return (PortLinerCharge) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** Port�R�[�h */
	protected String code = null;

	/** CHRG_NO */
	protected int CHRG_NO;

	/** CHARGE NAME */
	protected String CHARGE_NAME;

	/** LD TYPE */
	protected String LD_TYPE;

	/** CATEGORY */
	protected String CATEGORY;

	/** CATEGORY_NAME */
	protected String CATEGORY_NAME;

	/** CARGO TYPE */
	protected String CARGO_TYPE;

	/** CARGO_CODE */
	protected String CARGO_CODE;

	/** CURRENCY */
	protected String CURRENCY;

	/** AMOUNT */
	protected BigDecimal AMOUNT;

	/** CALCULATE TYPE */
	protected String PER;

	/** MINIMUM */
	protected BigDecimal MIN_AMT;

	/** HEAVY LIFT */
	protected BigDecimal HEAVY_LIFT;

	/** �o�^�N����. */
	protected Date INP_DATE = null;

	/** �X�V�N����. */
	protected Date UPD_DATE = null;

	/** �v���O����ID. */
	protected String PRG_ID = null;

	/** ���[�U�[ID. */
	protected String USR_ID = null;

	/** �����_�ȉ����� */
	protected int DEC_KETA = -1;

	/**
	 * ��ЃR�[�h���擾���܂��B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肵�܂��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Port�R�[�h���擾���܂��B
	 * 
	 * @return Port�R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Port�R�[�h��ݒ肵�܂��B
	 * 
	 * @param code Port�R�[�h
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Charge No�̂��擾���܂��B
	 * 
	 * @return Charge No��
	 */
	public int getCHRG_NO() {
		return CHRG_NO;
	}

	/**
	 * CHRG_NO�̂�ݒ肵�܂��B
	 * 
	 * @param CHRG_NO Charge No��
	 */
	public void setCHRG_NO(int CHRG_NO) {
		this.CHRG_NO = CHRG_NO;
	}

	/**
	 * Charge���̂��擾���܂��B
	 * 
	 * @return Charge����
	 */
	public String getCHARGE_NAME() {
		return CHARGE_NAME;
	}

	/**
	 * Charge���̂�ݒ肵�܂��B
	 * 
	 * @param CHARGE_NAME Charge����
	 */
	public void setCHARGE_NAME(String CHARGE_NAME) {
		this.CHARGE_NAME = CHARGE_NAME;
	}

	/**
	 * LD��ނ��擾���܂��B
	 * 
	 * @return LD_TYPE
	 */
	public String getLD_TYPE() {
		return LD_TYPE;
	}

	/**
	 * LD��ނ�ݒ肵�܂��B
	 * 
	 * @param LD_TYPE LD���
	 */
	public void setLD_TYPE(String LD_TYPE) {
		this.LD_TYPE = LD_TYPE;
	}

	/**
	 * Category���擾���܂��B
	 * 
	 * @return CAREGORY
	 */
	public String getCATEGORY() {
		return CATEGORY;
	}

	/**
	 * Category��ݒ肵�܂��B
	 * 
	 * @param CATEGORY
	 */
	public void setCATEGORY(String CATEGORY) {
		this.CATEGORY = CATEGORY;
	}

	/**
	 * Category���擾���܂��B
	 * 
	 * @return CATEGORY_NAME
	 */
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}

	/**
	 * Category��ݒ肵�܂��B
	 * 
	 * @param CATEGORY_NAME
	 */
	public void setCATEGORY_NAME(String CATEGORY_NAME) {
		this.CATEGORY_NAME = CATEGORY_NAME;
	}

	/**
	 * Cargo��ނ��擾���܂��B
	 * 
	 * @return CARGO_TYPE
	 */
	public String getCARGO_TYPE() {
		return CARGO_TYPE;
	}

	/**
	 * Cargo��ނ�ݒ肵�܂��B
	 * 
	 * @param CARGO_TYPE
	 */
	public void setCARGO_TYPE(String CARGO_TYPE) {
		this.CARGO_TYPE = CARGO_TYPE;
	}

	/**
	 * Cargo��ނ��擾���܂��B
	 * 
	 * @return CARGO_CODE
	 */
	public String getCARGO_CODE() {
		return CARGO_CODE;
	}

	/**
	 * Cargo��ނ�ݒ肵�܂��B
	 * 
	 * @param CARGO_CODE
	 */
	public void setCARGO_CODE(String CARGO_CODE) {
		this.CARGO_CODE = CARGO_CODE;
	}

	/**
	 * �ʉ݂��擾���܂��B
	 * 
	 * @return CURRENCY
	 */
	public String getCURRENCY() {
		return CURRENCY;
	}

	/**
	 * �ʉ݂�ݒ肵�܂��B
	 * 
	 * @param CURRENCY
	 */
	public void setCURRENCY(String CURRENCY) {
		this.CURRENCY = CURRENCY;
	}

	/**
	 * @return AMOUNT
	 */
	public BigDecimal getAMOUNT() {
		return AMOUNT;
	}

	/**
	 * @param AMOUNT
	 */

	public void setAMOUNT(BigDecimal AMOUNT) {
		this.AMOUNT = AMOUNT;
	}

	/**
	 * CALCULATE���擾���܂��B
	 * 
	 * @return PER
	 */
	public String getPER() {
		return PER;
	}

	/**
	 * CALCULATE��ݒ肵�܂��B
	 * 
	 * @param PER
	 */
	public void setPER(String PER) {
		this.PER = PER;
	}

	/**
	 * @return MINIMUM
	 */
	public BigDecimal getMIN_AMT() {
		return MIN_AMT;
	}

	/**
	 * @param MIN_AMT
	 */
	public void setMIN_AMT(BigDecimal MIN_AMT) {
		this.MIN_AMT = MIN_AMT;
	}

	/**
	 * @return HEAVY LIFT
	 */
	public BigDecimal getHEAVY_LIFT() {
		return HEAVY_LIFT;
	}

	/**
	 * @param HEAVY_LIFT
	 */

	public void setHEAVY_LIFT(BigDecimal HEAVY_LIFT) {
		this.HEAVY_LIFT = HEAVY_LIFT;
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
	 * �����_�ȉ������̎擾
	 * 
	 * @return dEC_KETA �����_�ȉ�����
	 */
	public int getDEC_KETA() {
		return DEC_KETA;
	}

	/**
	 * �����_�ȉ������̐ݒ�
	 * 
	 * @param DEC_KETA �����_�ȉ�����
	 */
	public void setDEC_KETA(int DEC_KETA) {
		this.DEC_KETA = DEC_KETA;
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
	 * ���[�U�[�ҏW���擾���܂��B
	 * 
	 * @return USR_ID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[�ҏW��ݒ肵�܂��B
	 * 
	 * @param USR_ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

}
