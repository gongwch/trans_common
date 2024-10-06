package jp.co.ais.trans2.model.company;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * ��Џ��
 * 
 * @author AIS
 */
public class Company extends TransferBase implements TReferable {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(code).append(" ").append(name);
		sb.append(",conf=").append(accountConfig);
		sb.append(",fp=").append(fiscalPeriod);
		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String code = null;

	/** ��Ж��� */
	protected String name = null;

	/** ��З��� */
	protected String names = null;

	/** �Z��1 */
	protected String address1 = null;

	/** �Z��2 */
	protected String address2 = null;

	/** �Z���J�i */
	protected String addressKana = null;

	/** �X�֔ԍ� */
	protected String zip = null;

	/** �d�b�ԍ� */
	protected String phoneNo = null;

	/** FAX�ԍ� */
	protected String faxNo = null;

	/** �K�i���������s���Ǝғo�^�ԍ��ԍ� */
	protected String invRegNo = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** ��Дw�i�F */
	protected Color color = null;

	/** ��v�ݒ��� */
	protected AccountConfig accountConfig = null;

	/** ��v���ԏ�� */
	protected FiscalPeriod fiscalPeriod = null;

	/** �A���p��ЃR�[�h */
	protected String connCompanyCode = null;

	/** �T�C���ҁE��E�� */
	protected String signerPosition = null;

	/** �O�������S���� */
	protected String remitterName = null;

	/** �A���p�d�b�ԍ� */
	protected String connPhoneNo = null;

	/** DEBITNOTE�Z��1 */
	protected String debitNoteAddress1 = null;

	/** DEBITNOTE�Z��2 */
	protected String debitNoteAddress2 = null;

	/** DEBITNOTE�Z��3 */
	protected String debitNoteAddress3 = null;

	/** DEBITNOTE�t�b�^��� */
	protected String debitNoteInfo = null;

	/** �~�ʉ� */
	protected Currency jPY = null;

	/** �h���ʉ� */
	protected Currency uSD = null;

	/** ��Ж���(�p��) */
	protected String KAI_NAME_ENG = null;

	/** ��З���(�p��) */
	protected String KAI_NAME_S_ENG = null;

	/** ��ЏZ��1(�p��) */
	protected String JYU_1_ENG = null;

	/** ��ЏZ��2(�p��) */
	protected String JYU_2_ENG = null;

	/** ��ЏZ��3(�p��) */
	protected String JYU_3_ENG = null;

	/** �p��������SIGNER���i���Ǘ��j */
	protected String AR_SIGN_NAME = null;

	/** �C���{�C�X���x�`�F�b�N�t���O */
	protected boolean CMP_INV_CHK_FLG = false;

	/**
	 * �R���X�g���N�^.
	 */
	public Company() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param code ��ЃR�[�h
	 */
	public Company(String code) {
		this();
		this.code = code;
	}

	/**
	 * @return fiscalPeriod��߂��܂��B
	 */
	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	/**
	 * @param fiscalPeriod fiscalPeriod��ݒ肵�܂��B
	 */
	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	/**
	 * @return address1��߂��܂��B
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 address1��ݒ肵�܂��B
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return address2��߂��܂��B
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 address2��ݒ肵�܂��B
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return addressKana��߂��܂��B
	 */
	public String getAddressKana() {
		return addressKana;
	}

	/**
	 * @param addressKana addressKana��ݒ肵�܂��B
	 */
	public void setAddressKana(String addressKana) {
		this.addressKana = addressKana;
	}

	/**
	 * @return faxNo��߂��܂��B
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo faxNo��ݒ肵�܂��B
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * @return invRegNo��߂��܂��B
	 */
	public String getInvRegNo() {
		return invRegNo;
	}

	/**
	 * @param invRegNo invRegNo��ݒ肵�܂��B
	 */
	public void setInvRegNo(String invRegNo) {
		this.invRegNo = invRegNo;
	}

	/**
	 * @return phoneNo��߂��܂��B
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo phoneNo��ݒ肵�܂��B
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return zip��߂��܂��B
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip zip��ݒ肵�܂��B
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return dateFrom��߂��܂��B
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFrom��ݒ肵�܂��B
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateTo��߂��܂��B
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateTo��ݒ肵�܂��B
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return code��߂��܂��B
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code code��ݒ肵�܂��B
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return ��v�ݒ����߂��܂��B
	 */
	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	/**
	 * @param accountConfig ��v�ݒ����ݒ肵�܂��B
	 */
	public void setAccountConfig(AccountConfig accountConfig) {
		this.accountConfig = accountConfig;
	}

	/**
	 * @return name��߂��܂��B
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name��ݒ肵�܂��B
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return names��߂��܂��B
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names names��ݒ肵�܂��B
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * @return color��߂��܂��B
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color color��ݒ肵�܂��B
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * ��������
	 * 
	 * @return ��������
	 */
	public String getNamek() {
		return null;
	}

	/**
	 * �A���p��ЃR�[�h�̎擾
	 * 
	 * @return connCompanyCode �A���p��ЃR�[�h
	 */
	public String getConnCompanyCode() {
		return connCompanyCode;
	}

	/**
	 * �A���p��ЃR�[�h�̐ݒ�
	 * 
	 * @param connCompanyCode �A���p��ЃR�[�h
	 */
	public void setConnCompanyCode(String connCompanyCode) {
		this.connCompanyCode = connCompanyCode;
	}

	/**
	 * �T�C���ҁE��E���̎擾
	 * 
	 * @return signerPosition �T�C���ҁE��E��
	 */
	public String getSignerPosition() {
		return signerPosition;
	}

	/**
	 * �T�C���ҁE��E���̐ݒ�
	 * 
	 * @param signerPosition �T�C���ҁE��E��
	 */
	public void setSignerPosition(String signerPosition) {
		this.signerPosition = signerPosition;
	}

	/**
	 * �O�������S���҂̎擾
	 * 
	 * @return remitterName �O�������S����
	 */
	public String getRemitterName() {
		return remitterName;
	}

	/**
	 * �O�������S���҂̐ݒ�
	 * 
	 * @param remitterName �O�������S����
	 */
	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	/**
	 * �A���p�d�b�ԍ��̎擾
	 * 
	 * @return connPhoneNo �A���p�d�b�ԍ�
	 */
	public String getConnPhoneNo() {
		return connPhoneNo;
	}

	/**
	 * �A���p�d�b�ԍ��̐ݒ�
	 * 
	 * @param connPhoneNo �A���p�d�b�ԍ�
	 */
	public void setConnPhoneNo(String connPhoneNo) {
		this.connPhoneNo = connPhoneNo;
	}

	/**
	 * DEBITNOTE�Z��1�̎擾
	 * 
	 * @return DebitNoteAddress1 DEBITNOTE�Z��1
	 */
	public String getDebitNoteAddress1() {
		return debitNoteAddress1;
	}

	/**
	 * DEBITNOTE�Z��1�̐ݒ�
	 * 
	 * @param debitNoteAddress1 DEBITNOTE�Z��1
	 */
	public void setDebitNoteAddress1(String debitNoteAddress1) {
		this.debitNoteAddress1 = debitNoteAddress1;
	}

	/**
	 * DEBITNOTE�Z��2�̎擾
	 * 
	 * @return debitNoteAddress2 DEBITNOTE�Z��2
	 */
	public String getDebitNoteAddress2() {
		return debitNoteAddress2;
	}

	/**
	 * DEBITNOTE�Z��2�̐ݒ�
	 * 
	 * @param debitNoteAddress2 DEBITNOTE�Z��2
	 */
	public void setDebitNoteAddress2(String debitNoteAddress2) {
		this.debitNoteAddress2 = debitNoteAddress2;
	}

	/**
	 * DEBITNOTE�Z��3�̎擾
	 * 
	 * @return debitNoteAddress3 DEBITNOTE�Z��3
	 */
	public String getDebitNoteAddress3() {
		return debitNoteAddress3;
	}

	/**
	 * DEBITNOTE�Z��3�̐ݒ�
	 * 
	 * @param debitNoteAddress3 DEBITNOTE�Z��3
	 */
	public void setDebitNoteAddress3(String debitNoteAddress3) {
		this.debitNoteAddress3 = debitNoteAddress3;
	}

	/**
	 * DEBITNOTE�t�b�^���̎擾
	 * 
	 * @return debitNoteInfo DEBITNOTE�t�b�^���
	 */
	public String getDebitNoteInfo() {
		return debitNoteInfo;
	}

	/**
	 * DEBITNOTE�t�b�^���̐ݒ�
	 * 
	 * @param debitNoteInfo DEBITNOTE�t�b�^���
	 */
	public void setDebitNoteInfo(String debitNoteInfo) {
		this.debitNoteInfo = debitNoteInfo;
	}

	/**
	 * �~�ʉ݂̎擾
	 * 
	 * @return jPY �~�ʉ�
	 */
	public Currency getJPY() {
		return jPY;
	}

	/**
	 * �~�ʉ݂̐ݒ�
	 * 
	 * @param jPY �~�ʉ�
	 */
	public void setJPY(Currency jPY) {
		this.jPY = jPY;
	}

	/**
	 * �h���ʉ݂̎擾
	 * 
	 * @return uSD �h���ʉ�
	 */
	public Currency getUSD() {
		return uSD;
	}

	/**
	 * �h���ʉ݂̐ݒ�
	 * 
	 * @param uSD �h���ʉ�
	 */
	public void setUSD(Currency uSD) {
		this.uSD = uSD;
	}

	/**
	 * ��Ж���(�p��)�̎擾
	 * 
	 * @return KAI_NAME_ENG ��Ж���(�p��)
	 */
	public String getKAI_NAME_ENG() {
		return KAI_NAME_ENG;
	}

	/**
	 * ��Ж���(�p��)�̐ݒ�
	 * 
	 * @param KAI_NAME_ENG ��Ж���(�p��)
	 */
	public void setKAI_NAME_ENG(String KAI_NAME_ENG) {
		this.KAI_NAME_ENG = KAI_NAME_ENG;
	}

	/**
	 * ��З���(�p��)�̎擾
	 * 
	 * @return KAI_NAME_S_ENG ��З���(�p��)
	 */
	public String getKAI_NAME_S_ENG() {
		return KAI_NAME_S_ENG;
	}

	/**
	 * ��З���(�p��)�̐ݒ�
	 * 
	 * @param KAI_NAME_S_ENG ��З���(�p��)
	 */
	public void setKAI_NAME_S_ENG(String KAI_NAME_S_ENG) {
		this.KAI_NAME_S_ENG = KAI_NAME_S_ENG;
	}

	/**
	 * ��ЏZ��1(�p��)�̎擾
	 * 
	 * @return JYU_1_ENG ��ЏZ��1(�p��)
	 */
	public String getJYU_1_ENG() {
		return JYU_1_ENG;
	}

	/**
	 * ��ЏZ��1(�p��)�̐ݒ�
	 * 
	 * @param JYU_1_ENG ��ЏZ��1(�p��)
	 */
	public void setJYU_1_ENG(String JYU_1_ENG) {
		this.JYU_1_ENG = JYU_1_ENG;
	}

	/**
	 * ��ЏZ��2(�p��)�̎擾
	 * 
	 * @return JYU_2_ENG ��ЏZ��2(�p��)
	 */
	public String getJYU_2_ENG() {
		return JYU_2_ENG;
	}

	/**
	 * ��ЏZ��2(�p��)�̐ݒ�
	 * 
	 * @param JYU_2_ENG ��ЏZ��2(�p��)
	 */
	public void setJYU_2_ENG(String JYU_2_ENG) {
		this.JYU_2_ENG = JYU_2_ENG;
	}

	/**
	 * ��ЏZ��3(�p��)�̎擾
	 * 
	 * @return JYU_3_ENG ��ЏZ��3(�p��)
	 */
	public String getJYU_3_ENG() {
		return JYU_3_ENG;
	}

	/**
	 * ��ЏZ��3(�p��)�̐ݒ�
	 * 
	 * @param JYU_3_ENG ��ЏZ��3(�p��)
	 */
	public void setJYU_3_ENG(String JYU_3_ENG) {
		this.JYU_3_ENG = JYU_3_ENG;
	}

	/**
	 * �p��������SIGNER���i���Ǘ��j �̎擾
	 * 
	 * @return AR_SIGN_NAME �p��������SIGNER���i���Ǘ��j
	 */
	public String getAR_SIGN_NAME() {
		return AR_SIGN_NAME;
	}

	/**
	 * �p��������SIGNER���i���Ǘ��j �̐ݒ�
	 * 
	 * @param AR_SIGN_NAME �p��������SIGNER���i���Ǘ��j
	 */
	public void setAR_SIGN_NAME(String AR_SIGN_NAME) {
		this.AR_SIGN_NAME = AR_SIGN_NAME;
	}
	
	/**
	 * �C���{�C�X���x�`�F�b�N�t���O�̎擾
	 * 
	 * @return CMP_INV_CHK_FLG �C���{�C�X���x�`�F�b�N�t���O
	 */ 
	public boolean isCMP_INV_CHK_FLG() { 
	     return CMP_INV_CHK_FLG;
	}
	/**
	 * �C���{�C�X���x�`�F�b�N�t���O�̐ݒ�
	 * 
	 * @param CMP_INV_CHK_FLG �C���{�C�X���x�`�F�b�N�t���O
	 */
	public void setCMP_INV_CHK_FLG(boolean CMP_INV_CHK_FLG) {
	     this.CMP_INV_CHK_FLG = CMP_INV_CHK_FLG;
	}

}
