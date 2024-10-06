package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * �ʉݏ��
 */
public class Currency extends TransferBase implements AutoCompletable, FilterableEntity {

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(companyCode).append("-").append(code);
		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���� */
	protected String names = null;

	/** �������� */
	protected String namek = null;

	/** �����_�ȉ����� */
	protected int decimalPoint = 0;

	/** ���[�g�W�� */
	protected int ratePow = 0;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

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
	 * @return companyCode��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCode��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * @return namek��߂��܂��B
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namek��ݒ肵�܂��B
	 */
	public void setNamek(String namek) {
		this.namek = namek;
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
	 * �����_�ȉ�����
	 * 
	 * @return �����_�ȉ�����
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * �����_�ȉ�����
	 * 
	 * @param decimalPoint �����_�ȉ�����
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * ���[�g�W��
	 * 
	 * @return ratePow ���[�g�W��
	 */
	public int getRatePow() {
		return ratePow;
	}

	/**
	 * ���[�g�W��
	 * 
	 * @param ratePow ���[�g�W��
	 */
	public void setRatePow(int ratePow) {
		this.ratePow = ratePow;
	}

	/**
	 * �ʉ݃R�[�h�����ꂩ�ǂ���
	 * 
	 * @param currency �ʉ�
	 * @return true:����
	 */
	public boolean equalsCode(Currency currency) {
		if (currency == null) {
			return false;
		}

		return Util.avoidNull(code).equals(Util.avoidNull(currency.getCode()));
	}
}
