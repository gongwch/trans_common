package jp.co.ais.trans2.model.calendar;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �J�����_�[���̌�������
 * 
 * @author AIS
 */
public class CalendarSearchCondition extends TransferBase implements Cloneable {

	/** ��s�x�Ɠ��敪 */
	public static String BANK = "CAL_BNK_KBN";

	/** �Վ��x�����敪 */
	public static String TEMPORARY = "CAL_HARAI";

	/** �Ј��x�����敪 */
	public static String EMPLOYEE = "CAL_SHA";

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �J�n���t */
	protected Date dateFrom = null;

	/** �I�����t */
	protected Date dateTo = null;

	/** �敪�J���� */
	protected String divisionColumn = null;

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
	 * �J�n���t�̎擾
	 * 
	 * @return dateFrom �J�n���t
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * �J�n���t�̐ݒ�
	 * 
	 * @param dateFrom �J�n���t
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * �I�����t�̎擾
	 * 
	 * @return dateTo �I�����t
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * �I�����t�̐ݒ�
	 * 
	 * @param dateTo �I�����t
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * �敪�J�����̎擾
	 * 
	 * @return divisionColumn �敪�J����
	 */
	public String getDivisionColumn() {
		return divisionColumn;
	}

	/**
	 * �敪�J�����̐ݒ�
	 * 
	 * @param divisionColumn �敪�J����
	 */
	public void setDivisionColumn(String divisionColumn) {
		this.divisionColumn = divisionColumn;
	}

}
