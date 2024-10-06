package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * �ʉ݃}�X�^��������
 */
public class CurrencySearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

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

	/** �����_�ȉ����� */
	protected int decimalPoint = 0;

	/** ���[�g�W�� */
	protected int ratePow = 0;

	/** �ʉ݃R�[�h */
	protected String currencyCode = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �R�[�h���X�g */
	protected List<String> codeList = new ArrayList<String>();

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
	public CurrencySearchCondition clone() {
		try {
			CurrencySearchCondition condition = (CurrencySearchCondition) super.clone();
			if (codeList != null) {
				condition.codeList = new ArrayList<String>(codeList);
			}
			return condition;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * �����_�ȉ�����
	 * 
	 * @return decimalPoint �����_�ȉ�����
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * �����_�ȉ�����
	 * 
	 * @param decimalPoint �����_�ȉ�����
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * ���[�g�W��
	 * 
	 * @return ratePow ���[�g�W��
	 */
	public int getRatePow() {
		return ratePow;
	}

	/**
	 * ���[�g�W��
	 * 
	 * @param ratePow ���[�g�W��
	 */
	public void setRatePow(int ratePow) {
		this.ratePow = ratePow;
	}

	/**
	 * �ʉ݃R�[�h
	 * 
	 * @return currencyCode �ʉ݃R�[�h
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * �ʉ݃R�[�h
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * �R�[�h���X�g
	 * 
	 * @return �R�[�h���X�g
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * �R�[�h�N���A
	 */
	public void clearCodeList() {
		codeList.clear();
	}

	/**
	 * �R�[�h�ǉ�
	 * 
	 * @param codes �R�[�h�z��
	 */
	public void addCode(String... codes) {
		for (String c : codes) {
			codeList.add(c);
		}
	}

	/**
	 * �R�[�h�ݒ�
	 * 
	 * @param codes �R�[�h�z��
	 */
	public void setCodeList(String... codes) {
		codeList.clear();

		for (String c : codes) {
			codeList.add(c);
		}
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
