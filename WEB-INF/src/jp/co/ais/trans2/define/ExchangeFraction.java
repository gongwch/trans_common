package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * ���Z�[����������
 * 
 * @author AIS
 */
public enum ExchangeFraction implements TEnumRadio {
	/**
	 * �؂�̂�
	 */
	TRUNCATE(0),

	/**
	 * �؂�グ
	 */
	ROUND_UP(1),

	/**
	 * �l�̌ܓ�
	 */
	ROUND_OFF(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ExchangeFraction(int value) {
		this.value = value;
	}

	/**
	 * ���Z�[������������Ԃ�
	 * 
	 * @param exchangeFraction �l
	 * @return ���Z�[����������
	 */
	public static ExchangeFraction getExchangeFraction(int exchangeFraction) {
		for (ExchangeFraction em : values()) {
			if (em.value == exchangeFraction) {
				return em;
			}
		}
		return null;
	}

	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @return ����ID
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���Z�[�������������̂�Ԃ�
	 * 
	 * @param value ���Z�[��
	 * @return ����
	 */
	public static String getName(int value) {

		ExchangeFraction fraction = getExchangeFraction(value);
		if (fraction == null) {
			return null;
		}
		return getName(fraction);
	}

	/**
	 * ���Z�[�������������̂�Ԃ�
	 * 
	 * @param fraction ���Z�[��
	 * @return ����
	 */
	public static String getName(ExchangeFraction fraction) {

		if (fraction == null) {
			return null;
		}

		switch (fraction) {
			case ROUND_OFF:
				return "C00215";// �l�̌ܓ�
			case ROUND_UP:
				return "C00120"; // �؂�グ
			case TRUNCATE:
				return "C00121"; // �؂�̂�
			default:
				return null;
		}
	}

}
