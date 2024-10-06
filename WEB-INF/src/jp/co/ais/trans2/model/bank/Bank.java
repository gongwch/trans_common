package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * ��s���
 * @author AIS
 */
public class Bank extends TransferBase implements Cloneable, TReferable {

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String names = null;

	/** �Ŗ��� */
	protected String kana = null;

	/** �������� */
	protected String namek = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** �x�X��� */
	public Branch branch;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Bank clone() {
		try {
			return (Bank) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return �x�X��� 
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return �R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return �L�����ԊJ�n 
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return �L�����ԏI��
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return ������
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return �Ŗ��� 
	 */
	public String getKana() {
		return kana;
	}

	/**
	 * @param kana
	 */
	public void setKana(String kana) {
		this.kana = kana;
	}

	/**
	 * @return �Ŗ��� 
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
	}

}
