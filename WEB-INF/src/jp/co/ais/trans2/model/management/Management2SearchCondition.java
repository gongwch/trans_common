package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �Ǘ�2�̌�������
 * 
 * @author AIS
 */
public class Management2SearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 3233838287647082966L;

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
	public Management2SearchCondition clone() {
		try {
			return (Management2SearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	public String getCodeLike() {
		return codeLike;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public String getNamekLike() {
		return namekLike;
	}

	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	public String getNamesLike() {
		return namesLike;
	}

	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
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
	public String[] getManagement2CodeList() {
		if (codeList.isEmpty()) {
			return null;
		}

		return codeList.toArray(new String[codeList.size()]);
	}

	/**
	 * �R�[�h(����)�̐ݒ�
	 * 
	 * @param management2CodeList �R�[�h(����)
	 */
	public void setManagement2CodeList(String[] management2CodeList) {
		addManagement2Code(management2CodeList);
	}

	/**
	 * �R�[�h(����)�̐ݒ�
	 * 
	 * @param list �R�[�h(����)
	 */
	public void addManagement2Code(String... list) {
		for (String management2Code : list) {
			this.codeList.add(management2Code);
		}
	}

	/**
	 * �R�[�h(����)�̃N���A
	 */
	public void clearManagement2CodeList() {
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
