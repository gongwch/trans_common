package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �x��/���������\���R���|(���l���͗p)
 */
public class TCustomerClosedDayNumSetting extends TCustomerClosedDaySetting {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isPayment
	 */
	public TCustomerClosedDayNumSetting(boolean isPayment) {
		super(isPayment);
	}

	/**
	 * �R���|�[�l���g������������(���l�t�B�[���h)
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
	 * �l��ݒ肷��
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
	 * �l��ݒ肷��
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
	 * ����
	 * 
	 * @return ����
	 */
	public int getCloseDay() {
		return ((TNumericField) closeDay).getInt();
	}

	/**
	 * �x����
	 * 
	 * @return �x����
	 */
	public int getNextMonth() {
		return ((TNumericField) nextMonth).getInt();
	}

	/**
	 * �x����
	 * 
	 * @return �x����
	 */
	public int getCashDay() {
		return ((TNumericField) cashDay).getInt();
	}
}
