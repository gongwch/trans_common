package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �V�X�e���̌�������
 * 
 * @author AIS
 */
public class SystemClassificationSearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8672402881942372338L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** �L������ */
	protected Date validTerm = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SystemClassificationSearchCondition clone() {
		try {
			return (SystemClassificationSearchCondition) super.clone();

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

	public String getCodeLike() {
		return codeLike;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public String getNamesLike() {
		return namesLike;
	}

	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

}
