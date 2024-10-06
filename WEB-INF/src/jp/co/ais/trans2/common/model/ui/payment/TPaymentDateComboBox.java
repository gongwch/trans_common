package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �x���敪�ꗗ
 */
public class TPaymentDateComboBox extends TLabelComboBox {

	/** �p�r���ʃt���O */
	protected boolean isSearch = false;

	/**
	 * �R���X�g���N�^.
	 */
	public TPaymentDateComboBox() {

		super();

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

	}

	/**
	 * �����p�̏ꍇ��type��true��
	 * 
	 * @param type
	 */
	public TPaymentDateComboBox(boolean type) {
		super();

		this.isSearch = type;
		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		//
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setLabelSize(75);
		setComboSize(60);
		setPreferredSize(new Dimension(getComboSize() + getLabelSize() + 5, 20));
		setSize(new Dimension(getComboSize() + getLabelSize() + 5, 20));
		setLangMessageID("C00682");// �x���敪

		if (isSearch) {
			combo.addItem(TModelUIUtil.getShortWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.NONE)));
		}
		combo.addItem(TModelUIUtil.getShortWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.REGULAR)));
		combo.addItem(TModelUIUtil.getShortWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.TEMPORARY)));

	}

	/**
	 * �I�������x���敪���擾����
	 * 
	 * @return �x���敪
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
	 * �x���敪��ݒ肷��
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
	 * ����������
	 */
	public void init() {
		if (isSearch) {
			setPaymentDateType(PaymentDateType.NONE);
		} else {
			setPaymentDateType(PaymentDateType.REGULAR);
		}

	}

}
