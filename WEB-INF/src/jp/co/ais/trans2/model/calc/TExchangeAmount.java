package jp.co.ais.trans2.model.calc;

import java.math.*;

import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * �ʉ݊��Z�R���|�[�l���gentity
 */
public class TExchangeAmount {

	/** �M�݋��z */
	protected BigDecimal keyAmount;

	/** �O�݋��z */
	protected BigDecimal foreignAmount;

	/** ���[�g */
	protected BigDecimal rate;

	/** ���[�g�W�� */
	protected int ratePow;

	/** �����_���� */
	protected int digit;

	/** ���[�g���Z�[������ */
	protected ExchangeFraction exchangeFraction = null;

	/** ���Z�敪 */
	protected ConvertType convertType = null;

	/**
	 * �M�݋��z
	 * 
	 * @return �M�݋��z
	 */
	public BigDecimal getKeyAmount() {
		return this.keyAmount;
	}

	/**
	 * �M�݋��z
	 * 
	 * @param keyAmount �M�݋��z
	 */
	public void setKeyAmount(BigDecimal keyAmount) {
		this.keyAmount = keyAmount;
	}

	/**
	 * ���[�g���Z�[���������擾����
	 * 
	 * @return ���[�g���Z�[������
	 */
	public ExchangeFraction getExchangeFraction() {
		return exchangeFraction;
	}

	/**
	 * ���[�g���Z�[��������ݒ肷��
	 * 
	 * @param fraction
	 */
	public void setExchangeFraction(ExchangeFraction fraction) {
		this.exchangeFraction = fraction;
	}

	/**
	 * ���Z�敪���擾����
	 * 
	 * @return ���Z�敪
	 */
	public ConvertType getConvertType() {
		return convertType;
	}

	/**
	 * ���Z�敪��ݒ肷��
	 * 
	 * @param type
	 */
	public void setConvertType(ConvertType type) {
		this.convertType = type;
	}

	/**
	 * �����_�������擾����
	 * 
	 * @return digit
	 */
	public int getDigit() {
		return digit;
	}

	/**
	 * �����_������ݒ肷��
	 * 
	 * @param digit �����_����
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * �O�݋��z���擾����
	 * 
	 * @return foreignMoney
	 */
	public BigDecimal getForeignAmount() {
		return foreignAmount;
	}

	/**
	 * �O�݋��z��ݒ肷��
	 * 
	 * @param foreignMoney �O�݋��z
	 */
	public void setForeignAmount(BigDecimal foreignMoney) {
		this.foreignAmount = foreignMoney;
	}

	/**
	 * ���[�g���擾����
	 * 
	 * @return rate
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * ���[�g��ݒ肷��
	 * 
	 * @param rate ���[�g
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * ���[�g�W�����擾����
	 * 
	 * @return ratePow
	 */
	public int getRatePow() {
		return ratePow;
	}

	/**
	 * ���[�g�W����ݒ肷��
	 * 
	 * @param ratePow ���[�g�W��
	 */
	public void setRatePow(int ratePow) {
		this.ratePow = ratePow;
	}

	/**
	 * �ʉ݃}�X�^���犷�Z����ݒ肷��
	 * 
	 * @param currency �ʉ݃}�X�^
	 */
	public void setCurrency(Currency currency) {

		this.ratePow = currency.getRatePow();
		this.digit = currency.getDecimalPoint();

	}

	/**
	 * ��v��񂩂犷�Z����ݒ肷��
	 * 
	 * @param config ��v���
	 */
	public void setAccountConfig(AccountConfig config) {

		this.exchangeFraction = config.getExchangeFraction();
		this.convertType = config.getConvertType();

	}
}