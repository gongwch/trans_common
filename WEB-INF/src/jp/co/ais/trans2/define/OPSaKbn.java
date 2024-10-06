package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * OP SA Type
 */
public enum OPSaKbn implements TEnumRadio {

	/** 0:PAYOFF */
	PAYOFF(0),

	/** 1:ESTIMATE */
	EST(1),

	/** 2:BOTH */
	BOTH(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �l
	 */
	OPSaKbn(int value) {
		this.value = value;
	}

	/**
	 * ���̎擾
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param type
	 * @return ����
	 */
	public static String getName(OPSaKbn type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case PAYOFF:
				return "PAY OFF";
			case EST:
				return "ESTIMATE";
			case BOTH:
				return "BOTH";
			default:
				return "";
		}
	}

	/**
	 * Enum��Ԃ�
	 * 
	 * @param value
	 * @return Enum
	 */
	public static OPSaKbn get(int value) {
		for (OPSaKbn em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}
}
