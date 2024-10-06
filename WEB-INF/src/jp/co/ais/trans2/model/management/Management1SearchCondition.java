package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �Ǘ�1�̌�������
 * 
 * @author AIS
 */
public class Management1SearchCondition extends TransferBase implements Cloneable {

	/** ��� */
	protected Company company = null;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �R�[�h(����) */
	protected List<String> codeList = new LinkedList<String>();

	/** �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p) */
	protected List<String> companyCodeList = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Management1SearchCondition clone() {
		try {
			return (Management1SearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return codeFrom��߂��܂��B
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFrom��ݒ肵�܂��B
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeTo��߂��܂��B
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeTo��ݒ肵�܂��B
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
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
	 * @return validTerm��߂��܂��B
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTerm��ݒ肵�܂��B
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
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
	 * �R�[�h�O����v
	 * 
	 * @return �R�[�h�O����v
	 */
	public String getCodeLike() {
		return codeLike;
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
	 * ��������like
	 * 
	 * @return ��������like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * ��������like
	 * 
	 * @param namekLike ��������like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * ����like
	 * 
	 * @return ����like
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * ����like
	 * 
	 * @param namesLike ����like
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * ���
	 * 
	 * @return ���
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * ���
	 * 
	 * @param company ���
	 */
	public void setCompany(Company company) {
		this.company = company;

		if (company == null) {
			setCompanyCode(null);

		} else {
			setCompanyCode(company.getCode());
		}
	}

	/**
	 * �R�[�h(����)�̎擾
	 * 
	 * @return �R�[�h(����)
	 */
	public List<String> getCodeList() {
		return this.codeList;
	}

	/**
	 * �R�[�h(����)�̎擾
	 * 
	 * @return �R�[�h(����)
	 */
	public String[] getManagement1CodeList() {
		if (codeList.isEmpty()) {
			return null;
		}

		return codeList.toArray(new String[codeList.size()]);
	}

	/**
	 * �R�[�h(����)�̐ݒ�
	 * 
	 * @param management1CodeList �R�[�h(����)
	 */
	public void setManagement1CodeList(String[] management1CodeList) {
		addManagement1Code(management1CodeList);
	}

	/**
	 * �R�[�h(����)�̐ݒ�
	 * 
	 * @param list �R�[�h(����)
	 */
	public void addManagement1Code(String... list) {
		for (String management1Code : list) {
			this.codeList.add(management1Code);
		}
	}

	/**
	 * �R�[�h(����)�̃N���A
	 */
	public void clearManagement1CodeList() {
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
