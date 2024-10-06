package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 支払区分一覧
 */
public class TPaymentDateComboBox extends TLabelComboBox {

	/** 用途判別フラグ */
	protected boolean isSearch = false;

	/**
	 * コンストラクタ.
	 */
	public TPaymentDateComboBox() {

		super();

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

	}

	/**
	 * 検索用の場合はtypeをtrueに
	 * 
	 * @param type
	 */
	public TPaymentDateComboBox(boolean type) {
		super();

		this.isSearch = type;
		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		//
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setLabelSize(75);
		setComboSize(60);
		setPreferredSize(new Dimension(getComboSize() + getLabelSize() + 5, 20));
		setSize(new Dimension(getComboSize() + getLabelSize() + 5, 20));
		setLangMessageID("C00682");// 支払区分

		if (isSearch) {
			combo.addItem(TModelUIUtil.getShortWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.NONE)));
		}
		combo.addItem(TModelUIUtil.getShortWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.REGULAR)));
		combo.addItem(TModelUIUtil.getShortWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.TEMPORARY)));

	}

	/**
	 * 選択した支払区分を取得する
	 * 
	 * @return 支払区分
	 */
	public PaymentDateType getPaymentDateType() {
		if (isSearch) {
			if (combo.getSelectedIndex() == 0) {
				return PaymentDateType.NONE;
			} else if (combo.getSelectedIndex() == 1) {
				return PaymentDateType.REGULAR;
			} else {
				return PaymentDateType.TEMPORARY;
			}
		} else {
			if (combo.getSelectedIndex() == 0) {
				return PaymentDateType.REGULAR;
			} else {
				return PaymentDateType.TEMPORARY;
			}
		}

	}

	/**
	 * 支払区分を設定する
	 * 
	 * @param paymentDateType
	 */
	public void setPaymentDateType(PaymentDateType paymentDateType) {

		if (isSearch) {
			if (paymentDateType.equals(PaymentDateType.NONE)) {
				combo.setSelectedIndex(0);
			} else if (paymentDateType.equals(PaymentDateType.REGULAR)) {
				combo.setSelectedIndex(1);
			} else {
				combo.setSelectedIndex(2);
			}

		} else {
			if (paymentDateType.equals(PaymentDateType.REGULAR)) {
				combo.setSelectedIndex(0);
			} else {
				combo.setSelectedIndex(1);
			}

		}
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		if (isSearch) {
			setPaymentDateType(PaymentDateType.NONE);
		} else {
			setPaymentDateType(PaymentDateType.REGULAR);
		}

	}

}
