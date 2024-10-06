package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.util.*;

/**
 * AR�Ȗڐ���敪�R���{�{�b�N�X���ڒ�`
 */
public enum ARType {

	/** �ʏ� */
	NOMAl("00"),

	/** ���Ǘ��Ȗ� */
	AR("01"),

	/** ����������� */
	AR_COLLECT("02");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ARType(String value) {
		this.value = value;
	}

	/**
	 * AR�Ȗڐ���敪��Ԃ�
	 * 
	 * @param ar AR�Ȗڐ���敪
	 * @return �l
	 */
	public static ARType get(String ar) {
		for (ARType em : values()) {
			if (em.value.equals(ar)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * @return �l
	 */
	public String getValue() {
		return Util.avoidNull(value);
	}

	/**
	 * AR�Ȗڐ���敪��Ԃ�
	 * 
	 * @param ar AR�Ȗڐ���敪
	 * @return AR�Ȗڐ���敪
	 */
	public static String getName(ARType ar) {

		if (ar == null) {
			return "";
		}

		switch (ar) {
			case NOMAl:
				return "C00372"; // �ʏ�

			case AR:
				return "C02120"; // ���Ǘ��Ȗ�

			case AR_COLLECT:
				return "C02121"; // �����������

			default:
				return "";
		}
	}
}