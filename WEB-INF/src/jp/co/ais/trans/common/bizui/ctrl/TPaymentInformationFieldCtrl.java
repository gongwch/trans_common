package jp.co.ais.trans.common.bizui.ctrl;

import java.text.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * �x�����t�B�[���h�R���g���[��
 */
public class TPaymentInformationFieldCtrl extends TAppletClientBase {

	/** �t�B�[���h */
	private TPaymentInformationField field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param paymentInfoField field
	 */
	public TPaymentInformationFieldCtrl(TPaymentInformationField paymentInfoField) {
		try {
			this.field = paymentInfoField;
		} catch (Exception e) {
			errorHandler(paymentInfoField, e, "E00010");
		}
	}

	/**
	 * �x�����@���͎� ��ʐ���
	 */
	public void paymentMethodScreenCtrl() {
		// �x�������R�[�h
		String naiCode = Util.avoidNull(field.getPaymentMethodField().getNaiCode());

		// �U�o��s����
		// �x�����@�����͂���Ă���Ƃ�
		if (!Util.isNullOrEmpty(field.getPaymentMethodField().getValue())) {
			if (!Util.isNullOrEmpty(naiCode)) {
				// �x�������R�[�h��11�E16�E17�E99�̂Ƃ��͉�ʃ��b�N
				if ("11".equals(naiCode) || "16".equals(naiCode) || "17".equals(naiCode) || "99".equals(naiCode)) {
					lockBankAccount();
				}
				// ��ʃ��b�N����
				else {
					unLockBankAccount();
				}
			}
			// �x�������R�[�h�������ꍇ�A��ʃ��b�N
			else {
				lockBankAccount();
			}
		}
		// �x�����̓R�[�h�����͂���Ă��Ȃ��B
		else {
			lockBankAccount();
		}
	}

	/**
	 * �U�o��s ��ʃ��b�N
	 */
	private void lockBankAccount() {
		// �U�o��s�R�[�h
		field.getBankAccountField().setValue("");
		// �U�o��s����
		field.getBankAccountField().setNoticeValue("");
		// �U�o��s�{�^���s��
		field.getBankAccountField().getButton().setEnabled(false);
		// �U�o��s�R�[�h�s��
		field.getBankAccountField().getField().setEditableEnabled(false);
	}

	/**
	 * �U�o��s ��ʃ��b�N����
	 */
	private void unLockBankAccount() {
		// �U�o��s�{�^����
		field.getBankAccountField().getButton().setEnabled(true);
		// �U�o��s�R�[�h��
		field.getBankAccountField().getField().setEditableEnabled(true);
	}

	/**
	 * �x���������͎� ��ʐ���
	 */
	public void paymentConditionScreenCtrl() {
		PaymentInformationParameter param = field.getPaymentConditionField().getParameter();
		if (!Util.isNullOrEmpty(field.getPaymentConditionField().getValue())) {
			// �L���������؂�Ă���ꍇ�A�e�R���|�[�l���g���N���A
			if (!field.getPaymentConditionField().IsTermBasisDate()) {
				// �x�������R�[�h�u�����N
				field.getPaymentConditionField().setValue("");
				// �x�����@�R�[�h�u�����N
				field.getPaymentMethodField().setValue("");
				// �x���\����u�����N
				field.getCalendar().setValue(null);
				// �x���敪�F�Վ�
				field.getDivisonComboBox().getComboBox().setSelectedIndex(1);
			} else if (param.getFlag()) {
				// �x�����@�R�[�h
				field.getPaymentMethodField().setValue(param.getSihaHouCode());
				// �x�����@����
				field.getPaymentMethodField().setNoticeValue(param.getSihaHouName());

				// �U�o��s����
				if ("11".equals(param.getSihaNaiCode()) || "16".equals(param.getSihaNaiCode())
					|| "17".equals(param.getSihaNaiCode()) || "99".equals(param.getSihaNaiCode())) {
					// �U�o��s�R�[�h
					field.getBankAccountField().setValue("");
					// �U�o��s����
					field.getBankAccountField().setNoticeValue("");
					// �U�o��s�{�^���s��
					field.getBankAccountField().getButton().setEnabled(false);
					// �U�o��s�R�[�h�s��
					field.getBankAccountField().getField().setEditableEnabled(false);
				} else {
					if (Util.isNullOrEmpty(param.getHuriCode())) {
						// �U�o��s�R�[�h
						field.getBankAccountField().setValue("");
						// �U�o��s����
						field.getBankAccountField().setNoticeValue("");
					} else {
						// �U�o��s����
						field.getBankAccountField().setNoticeValue(param.getHuriName());
						// �U�o��s�R�[�h
						field.getBankAccountField().setValue(param.getHuriCode());
					}
					// �U�o��s�{�^���s��
					field.getBankAccountField().getButton().setEnabled(true);
					// �U�o��s�R�[�h�s��
					field.getBankAccountField().getField().setEditableEnabled(true);
				}

				// �x���敪���Z�b�g
				// �x���敪 10�F�莞�̏ꍇ
				if ("10".equals(param.getSihaKbn())) {
					field.getDivisonComboBox().getComboBox().setSelectedIndex(0);
				} else {
					field.getDivisonComboBox().getComboBox().setSelectedIndex(1);
				}
				// �x�������Z�b�g
				try {
					field.getCalendar().setEnabled(true);
					field.getCalendar().setValue(DateUtil.toYMDDate(param.getSihaDate()));
				} catch (ParseException e) {
					// �u�����N
					field.getCalendar().setEnabled(false);
					field.getCalendar().setValue(null);
				}
			}
		}
	}
}
