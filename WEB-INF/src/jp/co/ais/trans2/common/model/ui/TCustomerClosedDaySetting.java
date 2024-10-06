package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払/入金条件表示コンポ
 */
public class TCustomerClosedDaySetting extends TPanel {

	/** 締め */
	public TTextField closeDay;

	/** 支払月 */
	public TTextField nextMonth;

	/** 締め日 */
	public TTextField cashDay;

	/** ラベル */
	public TLabel lblTitle;

	/** ラベル */
	public TLabel lblClose;

	/** ラベル */
	public TLabel lblMonth;

	/** ラベル */
	public TLabel lblCashDay;

	/**
	 * コンストラクタ
	 * 
	 * @param isPayment
	 */
	public TCustomerClosedDaySetting(boolean isPayment) {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents(isPayment);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		closeDay = new TTextField();
		nextMonth = new TTextField();
		cashDay = new TTextField();

		lblClose = new TLabel();
		lblMonth = new TLabel();
		lblCashDay = new TLabel();
		lblTitle = new TLabel();
	}

	/**
	 * コンポーネントを配置する
	 * 
	 * @param isPayment
	 */
	protected void allocateComponents(boolean isPayment) {

		setLayout(new GridBagLayout());
		setOpaque(false);
		GridBagConstraints gc = new GridBagConstraints();

		// タイトル
		lblTitle.setText(isPayment ? TModelUIUtil.getShortWord("C00238") : TModelUIUtil.getShortWord("C01271"));
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle.setMaximumSize(new Dimension(75, 20));
		lblTitle.setMinimumSize(new Dimension(75, 20));
		lblTitle.setPreferredSize(new Dimension(75, 20));
		lblTitle.setSize(new Dimension(75, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(lblTitle, gc);

		// 締め日
		closeDay.setHorizontalAlignment(SwingConstants.RIGHT);
		closeDay.setMaximumSize(new Dimension(25, 20));
		closeDay.setMinimumSize(new Dimension(25, 20));
		closeDay.setPreferredSize(new Dimension(25, 20));
		closeDay.setSize(new Dimension(25, 20));
		closeDay.setEditable(false);
		gc.insets = new Insets(0, 5, 0, 0);
		add(closeDay, gc);

		// ラベル 日締め
		lblClose.setText(TModelUIUtil.getWord("C01265"));
		lblClose.setMaximumSize(new Dimension(60, 20));
		lblClose.setMinimumSize(new Dimension(60, 20));
		lblClose.setPreferredSize(new Dimension(60, 20));
		lblClose.setSize(new Dimension(60, 20));
		gc.insets = new Insets(0, 2, 0, 0);
		add(lblClose, gc);

		// 月
		nextMonth.setHorizontalAlignment(SwingConstants.RIGHT);
		nextMonth.setMaximumSize(new Dimension(25, 20));
		nextMonth.setMinimumSize(new Dimension(25, 20));
		nextMonth.setPreferredSize(new Dimension(25, 20));
		nextMonth.setSize(new Dimension(25, 20));
		nextMonth.setEditable(false);
		gc.insets = new Insets(0, 5, 0, 0);
		add(nextMonth, gc);

		// ラベル ヵ月後
		lblMonth.setText(TModelUIUtil.getWord("C11106"));
		lblMonth.setMaximumSize(new Dimension(70, 20));
		lblMonth.setMinimumSize(new Dimension(70, 20));
		lblMonth.setPreferredSize(new Dimension(70, 20));
		lblMonth.setSize(new Dimension(70, 20));
		gc.insets = new Insets(0, 2, 0, 0);
		add(lblMonth, gc);

		// 支払日
		cashDay.setHorizontalAlignment(SwingConstants.RIGHT);
		cashDay.setMaximumSize(new Dimension(25, 20));
		cashDay.setMinimumSize(new Dimension(25, 20));
		cashDay.setPreferredSize(new Dimension(25, 20));
		cashDay.setSize(new Dimension(25, 20));
		cashDay.setEditable(false);
		gc.insets = new Insets(0, 4, 0, 0);
		add(cashDay, gc);

		// ラベル 日払い
		lblCashDay.setText(isPayment ? TModelUIUtil.getWord("C00448") : TModelUIUtil.getWord("C01266"));
		lblCashDay.setMaximumSize(new Dimension(75, 20));
		lblCashDay.setMinimumSize(new Dimension(75, 20));
		lblCashDay.setPreferredSize(new Dimension(75, 20));
		lblCashDay.setSize(new Dimension(75, 20));
		gc.insets = new Insets(0, 2, 0, 0);
		add(lblCashDay, gc);

		resize();
	}

	/**
	 * サイズ再設定
	 */
	public void resize() {
		int x = lblTitle.getWidth() + 21;
		x += closeDay.getWidth() + lblClose.getWidth();
		x += nextMonth.getWidth() + lblMonth.getWidth();
		x += cashDay.getWidth() + lblCashDay.getWidth();
		setSize(x, 20);
	}

	/**
	 * 値を設定する
	 * 
	 * @param bean
	 */
	public void setValue(Customer bean) {
		if (bean.getCloseDay() == 99) {
			closeDay.setText(TModelUIUtil.getWord("C02651"));//末

		} else {
			closeDay.setValue(bean.getCloseDay());
		}

		nextMonth.setValue(bean.getNextMonth());

		if (bean.getCashDay() == 99) {
			cashDay.setText(TModelUIUtil.getWord("C02651"));//末

		} else {
			cashDay.setValue(bean.getCashDay());
		}
	}

	/**
	 * 値を設定する
	 * 
	 * @param bean
	 */
	public void setValue(PaymentSetting bean) {
		closeDay.setValue(bean.getCloseDay().equals("99") ? TModelUIUtil.getWord("C02651") : bean.getCloseDay());
		nextMonth.setValue(bean.getNextMonth());
		cashDay.setValue(bean.getCashDay().equals("99") ? TModelUIUtil.getWord("C02651") : bean.getCashDay());
	}

	/**
	 * クリア
	 */
	public void clear() {
		closeDay.setValue(null);
		nextMonth.setValue(null);
		cashDay.setValue(null);
	}

	/**
	 * 編集状態の設定
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		closeDay.setEditable(isEditable);
		nextMonth.setEditable(isEditable);
		cashDay.setEditable(isEditable);
	}
}
