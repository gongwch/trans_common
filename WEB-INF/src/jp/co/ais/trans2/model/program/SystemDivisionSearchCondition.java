package jp.co.ais.trans2.model.program;

import java.util.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �V�X�e���敪�̌�������
 * 
 * @author AIS
 */
public class SystemDivisionSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected List<String> codeList = new LinkedList<String>();

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SystemDivisionSearchCondition clone() {
		try {
			return (SystemDivisionSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �R�[�h
	 * 
	 * @return �R�[�h
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * �R�[�h
	 * 
	 * @param codes �R�[�h
	 */
	public void addCode(String... codes) {
		for (String code : codes) {
			codeList.add(code);
		}
	}

	/**
	 * �R�[�h
	 * 
	 * @param codes �R�[�h
	 */
	public void setCode(String... codes) {
		codeList.clear();

		for (String code : codes) {
			codeList.add(code);
		}
	}

	/**
	 * �J�n�R�[�h
	 * 
	 * @return �J�n�R�[�h
	 */
	public String getCodeFrom() {
		return codeFrom;
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
	 * �I���R�[�h
	 * 
	 * @return �I���R�[�h
	 */
	public String getCodeTo() {
		return codeTo;
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
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

}
