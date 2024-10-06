package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * ���}�X�^��������
 */
public class CountrySearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String nameLike = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �R�[�h2�� */
	protected String code2 = null;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CountrySearchCondition clone() {
		try {
			return (CountrySearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ��ЃR�[�h��ݒ肷��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		// �����Ȃ�
	}

	/**
	 * �R�[�h
	 * 
	 * @return �R�[�h
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * �R�[�h
	 * 
	 * @param code �R�[�h
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ����
	 * 
	 * @return ����
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * ����
	 * 
	 * @param name ����
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * �R�[�h�O����v
	 * 
	 * @return �R�[�h�O����v
	 */
	public String getCodeLike() {
		return this.codeLike;
	}

	/**
	 * �R�[�h�O����v
	 * 
	 * @param codeLike �R�[�h�O����v
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * ����like
	 * 
	 * @return ����like
	 */
	public String getNameLike() {
		return this.nameLike;
	}

	/**
	 * ����like
	 * 
	 * @param nameLike ����like
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * �J�n�R�[�h
	 * 
	 * @return �J�n�R�[�h
	 */
	public String getCodeFrom() {
		return this.codeFrom;
	}

	/**
	 * �J�n�R�[�h
	 * 
	 * @param codeFrom �J�n�R�[�h
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * �I���R�[�h
	 * 
	 * @return �I���R�[�h
	 */
	public String getCodeTo() {
		return this.codeTo;
	}

	/**
	 * �I���R�[�h
	 * 
	 * @param codeTo �I���R�[�h
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * �L������
	 * 
	 * @return �L������
	 */
	public Date getValidTerm() {
		return this.validTerm;
	}

	/**
	 * �L������
	 * 
	 * @param validTerm �L������
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * �R�[�h2���̎擾
	 * 
	 * @return code2 �R�[�h2��
	 */
	public String getCode2() {
		return code2;
	}

	/**
	 * �R�[�h2���̐ݒ�
	 * 
	 * @param code2 �R�[�h2��
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getNamekLike()
	 */
	public String getNamekLike() {
		return nameLike;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getNamesLike()
	 */
	public String getNamesLike() {
		return nameLike;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
		// ������
		return null;
	}

	/**
	 * �ŏI�X�V�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI�X�V����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI�X�V�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI�X�V����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * ���݌����̎擾
	 * 
	 * @return nowCount ���݌���
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * ���݌����̐ݒ�
	 * 
	 * @param nowCount ���݌���
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

}
