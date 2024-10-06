package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払/入金条件表示コンポ(数値入力用)
 */
public class TCustomerClosedDayNumSetting extends TCustomerClosedDaySetting {

	/**
	 * コンストラクタ
	 * 
	 * @param isPayment
	 */
	public TCustomerClosedDayNumSetting(boolean isPayment) {
		super(isPayment);
	}

	/**
	 * コンポーネントを初期化する(数値フィールド)
	 */
	@Override
	protected void initComponents() {
		closeDay = new TNumericField();
		nextMonth = new TNumericField();
		cashDay = new TNumericField();

		lblClose = new TLabel();
		lblMonth = new TLabel();
		lblCashDay = new TLabel();
		lblTitle = new TLabel();

		closeDay.setMaxLength(2);
		((TNumericField) closeDay).setPositiveOnly(true);
		nextMonth.setMaxLength(2);
		((TNumericField) nextMonth).setPositiveOnly(true);
		cashDay.setMaxLength(2);
		((TNumericField) cashDay).setPositiveOnly(true);
	}

	/**
	 * 値を設定する
	 * 
	 * @param bean
	 */
	@Override
	public void setValue(Customer bean) {
		closeDay.setValue(bean.getCloseDay());
		nextMonth.setValue(bean.getNextMonth());
		cashDay.setValue(bean.getCashDay());
	}

	/**
	 * 値を設定する
	 * 
	 * @param bean
	 */
	@Override
	public void setValue(PaymentSetting bean) {
		closeDay.setText(bean.getCloseDay());
		nextMonth.setText(bean.getNextMonth());
		cashDay.setText(bean.getCashDay());
	}

	/**
	 * 締日
	 * 
	 * @return 締日
	 */
	public int getCloseDay() {
		return ((TNumericField) closeDay).getInt();
	}

	/**
	 * 支払月
	 * 
	 * @return 支払月
	 */
	public int getNextMonth() {
		return ((TNumericField) nextMonth).getInt();
	}

	/**
	 * 支払日
	 * 
	 * @return 支払日
	 */
	public int getCashDay() {
		return ((TNumericField) cashDay).getInt();
	}
}
