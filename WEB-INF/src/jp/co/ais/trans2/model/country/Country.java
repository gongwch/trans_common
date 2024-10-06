package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * �����
 */
public class Country extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	@Override
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();
		sb.append(Util.avoidNull(code));
		sb.append("/");
		sb.append(Util.avoidNull(code2));
		sb.append("/");
		sb.append(Util.avoidNull(name));
		sb.append("/");
		sb.append(Util.avoidNull(name2));
		sb.append("/");
		sb.append(Util.avoidNull(number));
		return sb.toString();
	}

	/** ���R�[�h(3��) */
	protected String code;

	/** ���R�[�h(2��) */
	protected String code2;

	/** ���ԍ� */
	protected String number;

	/** ������ */
	protected String name;

	/** ������2 */
	protected String name2;

	/** �L�����ԊJ�n */
	protected Date dateFrom;

	/** �L�����ԏI�� */
	protected Date dateTo;

	/**
	 * ���R�[�h(3��)
	 * 
	 * @return ���R�[�h(3��)
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * ���R�[�h(3��)
	 * 
	 * @param code ���R�[�h(3��)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ���R�[�h(2��)
	 * 
	 * @return ���R�[�h(2��)
	 */
	public String getCode2() {
		return this.code2;
	}

	/**
	 * ���R�[�h(2��)
	 * 
	 * @param code2 ���R�[�h(2��)
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}

	/**
	 * ���ԍ�
	 * 
	 * @return ���ԍ�
	 */
	public String getNumber() {
		return this.number;
	}

	/**
	 * ���ԍ�
	 * 
	 * @param number ���ԍ�
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * ������
	 * 
	 * @return ������
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * ������
	 * 
	 * @param name ������
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ������2
	 * 
	 * @return ������2
	 */
	public String getName2() {
		return this.name2;
	}

	/**
	 * ������2
	 * 
	 * @param name2 ������2
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * �L�����ԊJ�n
	 * 
	 * @return �L�����ԊJ�n
	 */
	public Date getDateFrom() {
		return this.dateFrom;
	}

	/**
	 * �L�����ԊJ�n
	 * 
	 * @param dateFrom �L�����ԊJ�n
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * �L�����ԏI��
	 * 
	 * @return �L�����ԏI��
	 */
	public Date getDateTo() {
		return this.dateTo;
	}

	/**
	 * �L�����ԏI��
	 * 
	 * @param dateTo �L�����ԏI��
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @deprecated
	 * @see jp.co.ais.trans2.model.TReferable#getNames()
	 */
	@Override
	public String getNames() {
		return null;
	}

	/**
	 * @deprecated
	 * @see jp.co.ais.trans2.model.TReferable#setNames(java.lang.String)
	 */
	@Override
	public void setNames(String names) {
		// �Ȃ�
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableEntity#getNamek()
	 */
	public String getNamek() {
		return name2;
	}

}
