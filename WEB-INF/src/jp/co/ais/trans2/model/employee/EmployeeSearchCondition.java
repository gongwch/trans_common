package jp.co.ais.trans2.model.employee;

import java.util.*;

import jp.co.ais.trans.common.dt.TransferBase;
import jp.co.ais.trans.common.except.TRuntimeException;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * MG0160EmployeeMaster - �Ј��}�X�^ - SearchCondition Class
 * 
 * @author AIS
 */
public class EmployeeSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ����R�[�h */
	protected String depCode;

	/** �R�[�h */
	protected String code;

	/** �J�n�R�[�h */
	protected String codeFrom;

	/** �I���R�[�h */
	protected String codeTo;

	/** �R�[�h�O����v */
	protected String codeLike;

	/** ���̑O����v */
	protected String namesLike;

	/** �������̑O����v */
	protected String namekLike;

	/** �L������ */
	protected Date validTerm;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/** �R�[�h(����) */
	protected List<String> codeList = new LinkedList<String>();

	/** �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p) */
	protected List<String> companyCodeList = null;

	/**
	 * *
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public EmployeeSearchCondition clone() {
		try {
			return (EmployeeSearchCondition) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return depCode
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * @return code
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
	 * @return codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * @return namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
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

	/**
	 * �R�[�h(����)�̎擾
	 * 
	 * @return �Ј��R�[�h(����)
	 */
	public List<String> getCodeList() {
		return this.codeList;
	}

	/**
	 * �R�[�h(����)�̎擾
	 * 
	 * @return �Ј��R�[�h(����)
	 */
	public String[] getEmployeeCodeList() {
		if (codeList.isEmpty()) {
			return null;
		}

		return codeList.toArray(new String[codeList.size()]);
	}

	/**
	 * �R�[�h(����)�̐ݒ�
	 * 
	 * @param employeeCodeList �R�[�h(����)
	 */
	public void setEmployeeCodeList(String[] employeeCodeList) {
		addEmployeeCode(employeeCodeList);
	}

	/**
	 * �R�[�h(����)�̐ݒ�
	 * 
	 * @param list �R�[�h(����)
	 */
	public void addEmployeeCode(String... list) {
		for (String employeeCode : list) {
			this.codeList.add(employeeCode);
		}
	}

	/**
	 * �R�[�h(����)�̃N���A
	 */
	public void clearEmployeeCodeList() {
		codeList.clear();
	}

	/**
	 * �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)�̎擾
	 * 
	 * @return companyCodeList �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)�̐ݒ�
	 * 
	 * @param companyCodeList �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}

}