package jp.co.ais.trans2.common.model.ui;

import java.math.BigDecimal;
import javax.swing.JComponent;
import jp.co.ais.trans.common.gui.TInputVerifier;
import jp.co.ais.trans.common.util.DecimalUtil;
import jp.co.ais.trans2.common.client.TController;
import jp.co.ais.trans2.model.calc.TCalculator;
import jp.co.ais.trans2.model.calc.TCalculatorFactory;
import jp.co.ais.trans2.model.calc.TExchangeAmount;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * ���Z���j�b�g�R���|�[�l���g�̃R���g���[��
 * @author AIS
 *
 */
public class TExchangeUnitController extends TController {

	/** �t�B�[���h */
	protected TExchangeUnit field;

	/** ���Entity */
	protected Company company;

	/** �v�Z�@ */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/**
	 *
	 * @param field
	 */
	public TExchangeUnitController(TExchangeUnit field) {

		this.field = field;

		init();
	}

	/**
	 * ������
	 *
	 */
	protected void init() {
		// ��Џ�����
		company = getCompany();
		// �C�x���g�Z�b�g
		addEvent();
	}

	/**
	 * �C�x���g��`
	 *
	 */
	protected void addEvent() {

		// �O�݂�verify�Ŋ��Z 
		field.ctrlForeignAmount.setInputVerifier(new TInputVerifier() {
			@Override
			public boolean verifyCommit(@SuppressWarnings("unused") JComponent comp) {
				return ctrlForeignAmount_verify();
			}
		});

	}

	protected boolean ctrlForeignAmount_verify() {
		// ���Z
		BigDecimal keyAmount = convertKeyAmount(
				field.ctrlForeignAmount.getBigDecimal(),
				field.ctrlRate.getBigDecimal(),
				field.ctrlCurrencyReference.getEntity(),
				company);
		field.ctrlKeyAmount.setNumber(keyAmount);

		return true;
	}

	/**
	 * ��ʉ݊��Z
	 * @param foreignAmount
	 * @param rate
	 * @param currency
	 * @param company
	 * @return
	 */
	protected BigDecimal convertKeyAmount(
			BigDecimal foreignAmount,
			BigDecimal rate,
			Currency currency,
			Company company) {

		if (DecimalUtil.isNullOrZero(foreignAmount)) {
			return BigDecimal.ZERO;
		}

		if (company == null || currency == null) {
			return BigDecimal.ZERO;
		}

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(company.getAccountConfig().getExchangeFraction());
		param.setConvertType(company.getAccountConfig().getConvertType());
		param.setDigit(company.getAccountConfig().getKeyCurrency().getDecimalPoint());
		param.setForeignAmount(foreignAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

    /**
     * �ʉ݂��Z�b�g����B<br>
     * �O�݃t�B�[���h�͓��Y�ʉ݂̏����_�����Ńt�H�[�}�b�g�����
     * @param currency �ʉ�
     */
    public void setCurrency(Currency currency) {
    	field.ctrlCurrencyReference.setEntity(currency);
    	field.ctrlForeignAmount.setMaxLength(13 + currency.getDecimalPoint(), currency.getDecimalPoint());
    	field.ctrlForeignAmount.setDecimalPoint(currency.getDecimalPoint());
    }

}
