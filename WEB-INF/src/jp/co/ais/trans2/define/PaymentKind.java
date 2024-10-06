package jp.co.ais.trans2.define;

/**
 * �x������ �x�����@�����R�[�h
 * 
 * @author AIS
 */
public enum PaymentKind {

	/** 01:����(�Ј�) */
	EMP_PAY_CASH("01"),
	/** 03:�����U��(�Ј�) */
	EMP_PAY_UNPAID("03"),
	/** 04:�N���W�b�g(�Ј�) */
	EMP_PAY_CREDIT("04"),
	/** 10:�Ј�����(�Ј�) */
	EMP_PAY_ACCRUED("10"),
	/** 11:���� */
	EX_PAY_CASH("11"),
	/** 12:�U��(��s����) */
	EX_PAY_BANK("12"),
	/** 13:�U��(FB�쐬) */
	EX_PAY_FB("13"),
	/** 14:���؎� */
	EX_PAY_CHECK("14"),
	/** 15:�x����` */
	EX_PAY_BILL("15"),
	/** 16:���� */
	EX_PAY_ERASE("16"),
	/** 17:���E */
	EX_PAY_OFFSET("17"),
	/** 18:�O������ */
	EX_PAY_REMITTANCE_ABROAD("18"),
	/** 19:�U��(���O�p����) */
	EX_PAY_BANK_ABROAD("19"),
	/** 99:���̑� */
	OTHER("99");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private PaymentKind(String value) {
		this.value = value;
	}

	/**
	 * �x�����@�����R�[�h���擾����
	 * 
	 * @return �x�����@�����R�[�h
	 */
	public String getPaymentKind() {
		return value;
	}

	/**
	 * �x�����@�����R�[�h��Ԃ�
	 * 
	 * @param paymentKind �x�����@�����R�[�h
	 * @return �x�����@�����R�[�h��
	 */
	public static PaymentKind getPaymentKind(String paymentKind) {
		for (PaymentKind em : values()) {
			if (em.value.equals(paymentKind)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * ��s��������̐U�o����Ԃ�
	 * 
	 * @param paymentKind �x�����@
	 * @return true:�U��o��
	 */
	public static boolean isBankAccountPayment(PaymentKind paymentKind) {
		if (paymentKind == null) {
			return false;
		}

		switch (paymentKind) {
			case EX_PAY_BANK:
			case EX_PAY_FB:
			case EX_PAY_CHECK:
			case EX_PAY_REMITTANCE_ABROAD:
			case EX_PAY_BANK_ABROAD:
			case EX_PAY_BILL:
				return true;

			default:
				return false;
		}
	}

	/**
	 * ��s��������̐U�o����Ԃ�
	 * 
	 * @param paymentKind �x�����@
	 * @return true:�U��o��
	 */
	public static boolean isBankAccountPayment(String paymentKind) {
		return isBankAccountPayment(getPaymentKind(paymentKind));
	}

	/**
	 * �f�[�^�敪���̂�Ԃ�
	 * 
	 * @param paymentKind
	 * @return �f�[�^�敪����
	 */
	public static String getPaymentKindName(PaymentKind paymentKind) {

		if (paymentKind == null) {
			return null;
		}

		switch (paymentKind) {
			case EMP_PAY_CASH:
				return "C02135";// ����(�Ј�)

			case EMP_PAY_UNPAID:
				return "C02136";// �����U��(�Ј�)

			case EMP_PAY_CREDIT:
				return "C02137";// �N���W�b�g(�Ј�)

			case EMP_PAY_ACCRUED:
				return "C02138";// �Ј�����(�Ј�)

			case EX_PAY_CASH:
				return "C00154";// ����

			case EX_PAY_BANK:
				return "C02139";// �U��(��s����)

			case EX_PAY_FB:
				return "C02140";// �U��(FB�쐬)

			case EX_PAY_CHECK:
				return "C02141";// ���؎�

			case EX_PAY_BILL:
				return "C00230";// �x����`

			case EX_PAY_ERASE:
				return "C02142";// ����

			case EX_PAY_OFFSET:
				return "C00331";// ���E

			case EX_PAY_REMITTANCE_ABROAD:
				return "C02143";// �O������

			case EX_PAY_BANK_ABROAD:
				return "C02144";// �U��(���O�p����)

			case OTHER:
				return "C00338";// ���̑�

			default:
				return null;

		}
	}
}
