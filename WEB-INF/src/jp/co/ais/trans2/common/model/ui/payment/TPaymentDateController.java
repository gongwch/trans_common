package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �x�����R���|�[�l���g�̃R���g���[���[
 * 
 * @author AIS
 */
public class TPaymentDateController extends TController {

	/** �x�����R���|�[�l���g */
	protected TPaymentDate field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TPaymentDateController(TPaymentDate field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {

		// �C�x���g��` �x���敪
		field.ctrlPayType.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					// �x�����擾
					setPaymentDate();
				}
			}

		});

	}

	/**
	 * �Q�Ƃ���R���|�[�l���g��ݒ肷��
	 */
	public void setReferenceComponentEvent() {

		// �C�x���g��` �`�[���t
		field.ctrlSlipDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {

				if (!field.ctrlSlipDate.isValueChanged2()) {
					return true;
				}
				// �C�����[�h�͕ύX���Ȃ�
				if (field.isModifyMode()) {
					return true;
				}

				// �x�����擾
				setPaymentDate();
				return true;
			}
		});

		// �C�x���g��` �x������
		field.ctrlPaymentSetting.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				// �C�����[�h�͕ύX���Ȃ�
				if (field.isModifyMode()) {
					return true;
				}

				// �x�����擾
				setPaymentDate();
				return true;
			}
		});

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �x�������Ɠ`�[���t�����Ɏx������ݒ肷��
	 */
	public void setPaymentDate() {

		try {

			// �f�[�^�������ꍇ�͏����I��
			if (field.ctrlPaymentSetting.getEntity() == null || field.ctrlSlipDate.getValue() == null) {
				return;
			}
			// �C�����[�h�͕ύX���Ȃ�
			if (field.isModifyMode()) {
				return;
			}

			// �x�������Ɠ`�[���t�̎擾
			PaymentSetting bean = field.ctrlPaymentSetting.getEntity().clone();
			Date slipDate = field.ctrlSlipDate.getValue();
			bean.setPaymentDateType(field.ctrlPayType.getPaymentDateType());
			Date paymentDate = null;

			// �莞�Œ��ߓ��ɒl������ꍇ�͒��ߓ������Ɏx�������擾
			if (field.ctrlPayType.getPaymentDateType().equals(PaymentDateType.REGULAR)
				&& !Util.isNullOrEmpty(field.closeDay)) {
				paymentDate = (Date) request(getManagerClass(), "getPaymentDate", bean, field.closeDay);
			} else {
				paymentDate = (Date) request(getManagerClass(), "getPaymentDate", bean, slipDate);
			}

			// �߂�l���x�����ɐݒ�
			field.calendar.setValue(paymentDate);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getManagerClass() {
		return PaymentManager.class;
	}

	/**
	 * �x���\�����ǂ���
	 * 
	 * @return true:�x���\
	 */
	public boolean isPaymentDate() {

		try {

			// �x�������Ɠ`�[���t�̎擾
			Date paymentDate = field.calendar.getValue();
			PaymentDateType paymentDateType = field.ctrlPayType.getPaymentDateType();

			// �x�������u�����N�̏ꍇ��false��Ԃ�
			if (Util.isNullOrEmpty(paymentDate)) {
				return false;
			}

			return (Boolean) request(getManagerClass(), "isPaymentDate", paymentDateType, paymentDate);

		} catch (Exception e) {
			errorHandler(e);
			return false;
		}
	}

}
