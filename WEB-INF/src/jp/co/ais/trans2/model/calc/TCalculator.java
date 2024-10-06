package jp.co.ais.trans2.model.calc;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * �v�Z���W�b�N
 */
public class TCalculator {

	/**
	 * �O�݂���ʉ݂Ɋ��Z
	 * 
	 * @param param ���Z�p�����[�^
	 * @return ��ʉ݋��z
	 */
	public BigDecimal exchangeKeyAmount(TExchangeAmount param) {

		BigDecimal rate = param.getRate(); // �O�݃��[�g
		BigDecimal foreignMoney = param.getForeignAmount(); // �O�݋��z

		if (DecimalUtil.isNullOrZero(rate) || DecimalUtil.isNullOrZero(foreignMoney)) {
			return BigDecimal.ZERO;
		}

		// ���[�g���Z�[������
		ExchangeFraction exchangeFraction = param.getExchangeFraction();

		if (exchangeFraction == null) {
			// ���[�g���Z�[���������w�肳��Ă��܂���B
			throw new TRuntimeException("I00117");
		}

		BigDecimal ratePow = BigDecimal.ONE.scaleByPowerOfTen(param.getRatePow()); // ���[�g�W��
		int decimalPoint = param.getDigit(); // �����_����

		// �߂�l
		BigDecimal amount = BigDecimal.ZERO;

		// ���Z�敪���|�Z�̏ꍇ
		if (param.getConvertType() == ConvertType.MULTIPLICATION) {

			// ��Z����
			amount = foreignMoney.multiply(rate.multiply(ratePow));

			// �[���������s���A�l��Ԃ�
			switch (exchangeFraction) {
				case TRUNCATE: // �؎�
					return DecimalUtil.truncateNum(amount, decimalPoint);

				case ROUND_UP: // �؏�
					return DecimalUtil.ceilingNum(amount, decimalPoint);

				case ROUND_OFF: // �l�̌ܓ�
					return DecimalUtil.roundNum(amount, decimalPoint);

				default:
					return amount;
			}

		} else {
			// ��Βl�擾
			amount = foreignMoney.abs();

			// �[�������Ə��Z�������ꏏ�ɍs�Ȃ�
			switch (exchangeFraction) {
				case TRUNCATE:
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.FLOOR);
					break;

				case ROUND_UP: // �؏�
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.CEILING);
					break;

				default: // �l�̌ܓ�
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.HALF_UP);
					break;
			}

			// �����̏ꍇ�A������
			return (foreignMoney.signum() == -1) ? amount.negate() : amount;
		}
	}

	/**
	 * ��ʉ݋��z���O�݂Ɋ��Z
	 * 
	 * @param rate ���[�g
	 * @param keyAmount ��ʉ݋��z
	 * @param currency �ʉ�
	 * @param convertType �ϊ��^�C�v
	 * @param exchangeFraction �[������
	 * @return �O�݋��z
	 */
	public BigDecimal exchangeForeignAmount(BigDecimal rate, BigDecimal keyAmount, Currency currency,
		ConvertType convertType, ExchangeFraction exchangeFraction) {

		TExchangeAmount param = new TExchangeAmount();
		param.setRate(rate);
		param.setKeyAmount(keyAmount);
		param.setDigit(currency.getDecimalPoint());
		param.setConvertType(convertType);
		param.setExchangeFraction(exchangeFraction);

		return exchangeForeignAmount(param);
	}

	/**
	 * ��ʉ݋��z���O�݂Ɋ��Z
	 * 
	 * @param param ���Z�p�����[�^
	 * @return �O�݋��z
	 */
	public BigDecimal exchangeForeignAmount(TExchangeAmount param) {

		BigDecimal rate = param.getRate();
		BigDecimal keyAmount = param.getKeyAmount();

		if (DecimalUtil.isNullOrZero(rate) || DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		// ���[�g���Z�[������
		ExchangeFraction exchangeFraction = param.getExchangeFraction();

		if (exchangeFraction == null) {
			// ���[�g���Z�[���������w�肳��Ă��܂���B
			throw new TRuntimeException("I00117");
		}

		BigDecimal ratePow = BigDecimal.ONE.scaleByPowerOfTen(param.getRatePow());
		int decimalPoint = param.getDigit(); // �����_����
		ConvertType convertType = param.getConvertType();

		// �߂�l
		BigDecimal amount = BigDecimal.ZERO;

		// ���Z�敪���|�Z�̏ꍇ�͋t�Ȃ̂Ŋ��Z
		if (convertType == ConvertType.MULTIPLICATION) {

			// ��Βl�擾
			amount = keyAmount.abs();

			// �[�������Ə��Z�������ꏏ�ɍs�Ȃ�
			switch (exchangeFraction) {
				case TRUNCATE:
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.FLOOR);
					break;

				case ROUND_UP: // �؏�
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.CEILING);
					break;

				default: // �l�̌ܓ�
					amount = amount.divide(rate.multiply(ratePow), decimalPoint, RoundingMode.HALF_UP);
					break;
			}

			// �����̏ꍇ�A������
			return (keyAmount.signum() == -1) ? amount.negate() : amount;

			// ���Z�敪�����Z�̏ꍇ�͋t�Ȃ̂Ŋ|�Z
		} else {

			// ��Z����
			amount = keyAmount.multiply(rate.multiply(ratePow));

			// �[���������s���A�l��Ԃ�
			switch (exchangeFraction) {
				case TRUNCATE: // �؎�
					return DecimalUtil.truncateNum(amount, decimalPoint);

				case ROUND_UP: // �؏�
					return DecimalUtil.ceilingNum(amount, decimalPoint);

				case ROUND_OFF: // �l�̌ܓ�
					return DecimalUtil.roundNum(amount, decimalPoint);

				default:
					return amount;
			}
		}
	}

	/**
	 * ����Ōv�Z<br>
	 * �� = ���z �~ �ŗ� �� 100(����) or 100 + RATE(�O��)
	 * 
	 * @param param �p�����[�^
	 * @return ����
	 */
	public BigDecimal calculateTax(TTaxCalculation param) {

		BigDecimal amount = param.getAmount(); // �Ώۋ��z
		BigDecimal taxRate = param.getTax().getRate(); // �Ń��[�g

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		// ����
		int signum = amount.signum();

		// �܂���Βl��
		amount = amount.abs();

		// �|�Z
		amount = amount.multiply(taxRate);

		// ���鐔�l
		BigDecimal divideNumber = param.isInside() ? DecimalUtil.HUNDRED.add(taxRate) : DecimalUtil.HUNDRED;

		// �[���v�Z�̎�ނ����
		ExchangeFraction fraction = null;
		switch (param.getTax().getTaxType()) {
			case SALES:
				fraction = param.getReceivingFunction(); // ����ې�(�������Œ[������)
				break;

			case PURCHAESE:
				fraction = param.getPaymentFunction(); // �d���ې�(��������Œ[������)
				break;
				// �ΏۊO�̏ꍇ��ZERO��Ԃ�
			case NOT:
				return BigDecimal.ZERO;
		}

		switch (fraction) {
			case TRUNCATE: // �؎̂�
				amount = amount.divide(divideNumber, param.getDigit(), RoundingMode.FLOOR);
				break;

			case ROUND_UP: // �؏�
				amount = amount.divide(divideNumber, param.getDigit(), RoundingMode.CEILING);
				break;

			default: // �l�̌ܓ�
				amount = amount.divide(divideNumber, param.getDigit(), RoundingMode.HALF_UP);
				break;
		}

		// �}�C�i�X�̏ꍇ�͕������]���ĕԂ�
		return (signum == -1) ? amount.negate() : amount;
	}
}
