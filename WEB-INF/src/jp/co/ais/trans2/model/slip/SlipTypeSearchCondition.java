package jp.co.ais.trans2.model.slip;

import java.util.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �`�[��ʂ̌�������
 * 
 * @author AIS
 */
public class SlipTypeSearchCondition extends TransferBase implements Cloneable {

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

	/** ���V�X�敪�Q�ƃt���O */
	protected boolean isReferOtherSystemDivision = false;

	/** �C���{�C�X���x�t���O */
	protected boolean invoiceFlg = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SlipTypeSearchCondition clone() {
		try {
			return (SlipTypeSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * isReferOtherSystemDivision���擾����B
	 * 
	 * @return boolean isReferOtherSystemDivision
	 */
	public boolean isReferOtherSystemDivision() {
		return isReferOtherSystemDivision;
	}

	/**
	 * isReferOtherSystemDivision��ݒ肷��B
	 * 
	 * @param isReferOtherSystemDivision
	 */
	public void setReferOtherSystemDivision(boolean isReferOtherSystemDivision) {
		this.isReferOtherSystemDivision = isReferOtherSystemDivision;
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

	/**
	 * �C���{�C�X���x�t���O�̎擾
	 * 
	 * @return invoiceFlg �C���{�C�X���x�t���O
	 */
	public boolean isInvoiceFlg() {
		return invoiceFlg;
	}

	/**
	 * �C���{�C�X���x�t���O�̐ݒ�
	 * 
	 * @param invoiceFlg �C���{�C�X���x�t���O
	 */
	public void setInvoiceFlg(boolean invoiceFlg) {
		this.invoiceFlg = invoiceFlg;
	}

}
