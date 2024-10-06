package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * MG0070ItemSummaryMaster - �ȖڏW�v�}�X�^ - SearchCondition Class
 * 
 * @author AIS
 */
public class ItemSummarySearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** �Ȗڑ̌n�R�[�h */
	protected String kmtCode;

	/** �ȖڃR�[�h */
	protected String kmkCode;

	/** �R�[�h�O����v */
	protected String codeLike;

	/** ���̂����܂� */
	protected String namesLike;

	/** �������̂����܂� */
	protected String namekLike;

	/** �J�n�R�[�h */
	protected String codeFrom;

	/** �I���R�[�h */
	protected String codeTo;

	/** �L������ */
	protected Date validTerm;

	/**
	 * * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ItemSummarySearchCondition clone() {
		try {
			return (ItemSummarySearchCondition) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ��ЃR�[�h
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�h
	 * @return kmtCode
	 */
	public String getKmtCode() {
		return kmtCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�h
	 * @param kmtCode �ݒ肷�� kmtCode
	 */
	public void setKmtCode(String kmtCode) {
		this.kmtCode = kmtCode;
	}

	/**
	 * �ȖڃR�[�h
	 * @return kmkCode
	 */
	public String getKmkCode() {
		return kmkCode;
	}

	/**
	 * �ȖڃR�[�h
	 * @param kmkCode �ݒ肷�� kmkCode
	 */
	public void setKmkCode(String kmkCode) {
		this.kmkCode = kmkCode;
	}

	/**
	 * �R�[�h�O����v
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * �R�[�h�O����v
	 * @param codeLike �ݒ肷�� codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * ���̂����܂�����
	 * @return namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * ���̂����܂�����
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * �������̂����܂�����
	 * @return namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * �������̂����܂�����
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * �R�[�h�J�n
	 * @return codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * �R�[�h�J�n
	 * @param codeFrom �ݒ肷�� codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * �R�[�h�I��
	 * @return codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * �R�[�h�I��
	 * @param codeTo �ݒ肷�� codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * �L�������؂�
	 * @return validTerm��߂��܂��B
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * �L�������؂�
	 * @param validTerm validTerm��ݒ肵�܂��B
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}
}