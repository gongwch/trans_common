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
 * 換算ユニットコンポーネントのコントローラ
 * @author AIS
 *
 */
public class TExchangeUnitController extends TController {

	/** フィールド */
	protected TExchangeUnit field;

	/** 会社Entity */
	protected Company company;

	/** 計算機 */
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
	 * 初期化
	 *
	 */
	protected void init() {
		// 会社初期化
		company = getCompany();
		// イベントセット
		addEvent();
	}

	/**
	 * イベント定義
	 *
	 */
	protected void addEvent() {

		// 外貨のverifyで換算 
		field.ctrlForeignAmount.setInputVerifier(new TInputVerifier() {
			@Override
			public boolean verifyCommit(@SuppressWarnings("unused") JComponent comp) {
				return ctrlForeignAmount_verify();
			}
		});

	}

	protected boolean ctrlForeignAmount_verify() {
		// 換算
		BigDecimal keyAmount = convertKeyAmount(
				field.ctrlForeignAmount.getBigDecimal(),
				field.ctrlRate.getBigDecimal(),
				field.ctrlCurrencyReference.getEntity(),
				company);
		field.ctrlKeyAmount.setNumber(keyAmount);

		return true;
	}

	/**
	 * 基軸通貨換算
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

		// 換算
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
     * 通貨をセットする。<br>
     * 外貨フィールドは当該通貨の小数点桁数でフォーマットされる
     * @param currency 通貨
     */
    public void setCurrency(Currency currency) {
    	field.ctrlCurrencyReference.setEntity(currency);
    	field.ctrlForeignAmount.setMaxLength(13 + currency.getDecimalPoint(), currency.getDecimalPoint());
    	field.ctrlForeignAmount.setDecimalPoint(currency.getDecimalPoint());
    }

}
