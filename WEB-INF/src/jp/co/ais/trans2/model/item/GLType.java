package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.util.*;

/**
 * GL�Ȗڐ���敪�R���{�{�b�N�X���ڒ�`
 */
public enum GLType {

	/** �ʏ� */
	NOMAL("00"),

	/** �����Ȗ� */
	FUND("04"),

	/** ����Ȗ� */
	SALES("05"),

	/** ������ */
	TEMPORARY("07");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private GLType(String value) {
		this.value = value;
	}

	/**
	 * GL�Ȗڐ���敪��Ԃ�
	 * 
	 * @param gl GL�Ȗڐ���敪
	 * @return �l
	 */
	public static GLType getGl(String gl) {
		for (GLType em : values()) {
			if (em.value.equals(gl)) {
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
	 * GL�Ȗڐ���敪��Ԃ�
	 * 
	 * @param gl GL�Ȗڐ���敪
	 * @return GL�Ȗڐ���敪
	 */
	public static String getName(GLType gl) {

		if (gl == null) {
			return "";
		}

		switch (gl) {
			case NOMAL:
				return "C00372"; // �ʏ�

			case FUND:
				return "C02114"; // �����Ȗ�

			case SALES:
				return "C02115"; // ����Ȗ�

			case TEMPORARY:
				return "C02117"; // ������

			default:
				return "";
		}
	}
}