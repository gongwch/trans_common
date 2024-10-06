package jp.co.ais.trans2.model.currency;

import java.util.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.RateType;

/**
 * �ʉ݃��[�g�}�X�^��������
 */
public class RateSearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2837114825950169565L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �ʉ݃R�[�h */
	protected String currencyCode = null;

	/** �K�p�J�n���t */
	protected Date termFrom;

	/** �K�p�J�n���t�i�J�n�j�i���������j */
	protected Date dateFrom;
	
	/** �K�p�J�n���t�i�I���j�i���������j */
	protected Date dateTo;

	/** �ʉ݃R�[�h�J�n */
	protected String currencyCodeFrom = null;

	/** �ʉ݃R�[�h�I�� */
	protected String currencyCodeTo = null;

	/** ���[�g�^�C�v */
	protected RateType rateType = null;

	/** �В背�[�g���܂ނ� */
	protected boolean companyRate;

	/** ���Z�����[�g�܂ނ� */
	protected boolean settlementRate;

	/** �В背�[�g�i�@�\�ʉ݁j���܂ނ� */
	protected boolean funcCompanyRate;
	
	/** ���Z�����[�g�i�@�\�ʉ݁j���܂ނ� */
	protected boolean funcSettlementRate;
	
	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RateSearchCondition clone() {
		try {
			return (RateSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * �В背�[�g�`�F�b�N
	 *
	 * @return companyRate �В背�[�g�`�F�b�N
	 */
	public boolean isCompanyRate() {
		return companyRate;
	}

	/**
	 * �В背�[�g�`�F�b�N
	 *
	 * @param companyRate �В背�[�g�`�F�b�N
	 */
	public void setCompanyRate(boolean companyRate) {
		this.companyRate = companyRate;
	}

	/**
	 * �В背�[�g�i�@�\�ʉ݁j�`�F�b�N
	 *
	 * @return funcCompanyRate �В背�[�g�i�@�\�ʉ݁j
	 */
	public boolean isFuncCompanyRate() {
		return funcCompanyRate;
	}

	/**
	 * �В背�[�g�i�@�\�ʉ݁j�`�F�b�N
	 *
	 * @param funcCompanyRate �В背�[�g�i�@�\�ʉ݁j�`�F�b�N
	 */
	public void setFuncCompanyRate(boolean funcCompanyRate) {
		this.funcCompanyRate = funcCompanyRate;
	}

	/**
	 * ���Z���[�g�i�@�\�ʉ݁j�`�F�b�N
	 *
	 * @return funcSettlementRate ���Z���[�g�i�@�\�ʉ݁j�`�F�b�N
	 */
	public boolean isFuncSettlementRate() {
		return funcSettlementRate;
	}

	/**
	 * ���Z���[�g�i�@�\�ʉ݁j�`�F�b�N
	 *
	 * @param funcSettlementRate ���Z���[�g�i�@�\�ʉ݁j�`�F�b�N
	 */
	public void setFuncSettlementRate(boolean funcSettlementRate) {
		this.funcSettlementRate = funcSettlementRate;
	}

	/**
	 * ���Z���[�g�`�F�b�N
	 *
	 * @return settlementRate ���Z���[�g�`�F�b�N
	 */
	public boolean isSettlementRate() {
		return settlementRate;
	}

	/**
	 * ���Z���[�g�`�F�b�N
	 *
	 * @param settlementRate ���Z���[�g�`�F�b�N
	 */
	public void setSettlementRate(boolean settlementRate) {
		this.settlementRate = settlementRate;
	}

	/**
	 * �K�p�J�n���t�i�J�n�j�i���������j
	 *
	 * @return dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * �K�p�J�n���t�i�J�n�j�i���������j
	 *
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * �K�p�J�n���t�i�I���j�i���������j
	 *
	 * @return dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * �K�p�J�n���t�i�I���j�i���������j
	 *
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * �K�p�J�n���t
	 *
	 * @return termFrom �K�p�J�n���t
	 */
	public Date getTermFrom() {
		return termFrom;
	}

	/**
	 * �K�p�J�n���t
	 *
	 * @param termFrom �K�p�J�n���t
	 */
	public void setTermFrom(Date termFrom) {
		this.termFrom = termFrom;
	}

	public String getCurrencyCodeFrom() {
		return currencyCodeFrom;
	}

	public void setCurrencyCodeFrom(String currencyCodeFrom) {
		this.currencyCodeFrom = currencyCodeFrom;
	}

	public String getCurrencyCodeTo() {
		return currencyCodeTo;
	}

	public void setCurrencyCodeTo(String currencyCodeTo) {
		this.currencyCodeTo = currencyCodeTo;
	}

	public RateType getRateType() {
		return rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

}
