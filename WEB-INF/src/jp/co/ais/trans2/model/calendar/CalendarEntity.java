package jp.co.ais.trans2.model.calendar;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �J�����_�[���
 * 
 * @author AIS
 */
public class CalendarEntity extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �J�����_�[���t */
	protected Date calDate = null;

	/** �Ј��x���Ώۓ��敪 */
	protected String calSha = null;

	/** ��s�c�Ɠ��敪 */
	protected String calBank = null;

	/** �Վ��x���Ώۓ��敪 */
	protected String calRinji = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �J�����_�[���t�̎擾
	 * 
	 * @return calDate �J�����_�[���t
	 */
	public Date getCalDate() {
		return calDate;
	}

	/**
	 * �J�����_�[���t�̐ݒ�
	 * 
	 * @param calDate �J�����_�[���t
	 */
	public void setCalDate(Date calDate) {
		this.calDate = calDate;
	}

	/**
	 * �Ј��x���Ώۓ��敪�̎擾
	 * 
	 * @return calSha �Ј��x���Ώۓ��敪
	 */
	public String getCalSha() {
		return calSha;
	}

	/**
	 * �Ј��x���Ώۓ��敪�̐ݒ�
	 * 
	 * @param calSha �Ј��x���Ώۓ��敪
	 */
	public void setCalSha(String calSha) {
		this.calSha = calSha;
	}

	/**
	 * ��s�c�Ɠ��敪�̎擾
	 * 
	 * @return calBank ��s�c�Ɠ��敪
	 */
	public String getCalBank() {
		return calBank;
	}

	/**
	 * ��s�c�Ɠ��敪�̐ݒ�
	 * 
	 * @param calBank ��s�c�Ɠ��敪
	 */
	public void setCalBank(String calBank) {
		this.calBank = calBank;
	}

	/**
	 * �Վ��x���Ώۓ��敪�̎擾
	 * 
	 * @return calRinji �Վ��x���Ώۓ��敪
	 */
	public String getCalRinji() {
		return calRinji;
	}

	/**
	 * �Վ��x���Ώۓ��敪�̐ݒ�
	 * 
	 * @param calRinji �Վ��x���Ώۓ��敪
	 */
	public void setCalRinji(String calRinji) {
		this.calRinji = calRinji;
	}

	/**
	 * ��s�x�Ɠ��̎擾
	 * 
	 * @return bankHoliday ��s�x�Ɠ�
	 */
	public boolean isBankHoliday() {
		return "1".equals(calBank);
	}
}
