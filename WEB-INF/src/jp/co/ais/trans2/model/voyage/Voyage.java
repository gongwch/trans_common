package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;

/**
 * Voyage
 */
public class Voyage extends TransferBase implements FilterableEntity {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** Voyage�R�[�h */
	protected String code = null;

	/** Voyage���� */
	protected String name = null;

	/** Voyage���� */
	protected String names = null;

	/** Voyage�������� */
	protected String namek = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

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
	 * Voyage�R�[�h���擾���܂��B
	 * 
	 * @return Voyage�R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Voyage�R�[�h��ݒ肵�܂��B
	 * 
	 * @param code Voyage�R�[�h
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Voyage���̂��擾���܂��B
	 * 
	 * @return Voyage����
	 */
	public String getName() {
		return name;
	}

	/**
	 * Voyage���̂�ݒ肵�܂��B
	 * 
	 * @param name Voyage����
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Voyage���̂��擾���܂��B
	 * 
	 * @return Voyage����
	 */
	public String getNames() {
		return names;
	}

	/**
	 * Voyage���̂�ݒ肵�܂��B
	 * 
	 * @param names Voyage����
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * Voyage�������̂��擾���܂��B
	 * 
	 * @return Voyage��������
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * Voyage�������̂�ݒ肵�܂��B
	 * 
	 * @param namek Voyage��������
	 */
	public void setNamek(String namek) {
		this.namek = namek;
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
}
