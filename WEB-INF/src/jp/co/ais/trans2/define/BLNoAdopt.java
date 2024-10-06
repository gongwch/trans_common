package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * B/L �����ݒ荀��
 */
public enum BLNoAdopt implements TEnumRadio {

	/** 0:�Ȃ� */
	NONE(0),

	/** 1:��ЃR�[�h */
	CODE(1),

	/** 2:���� */
	DEPARTMENT(2),

	/** 3:�N��(YYMM) */
	YYMM_DATE(3),

	/** 4:�N��(YYYYMM) */
	YYYYMM_DATE(4),

	/** 5:���t(YYYYMMDD) */
	YYYYMMDD_DATE(5);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �l
	 */
	BLNoAdopt(int value) {
		this.value = value;
	}

	/**
	 * ���̎擾
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̎擾.
	 * 
	 * @param type
	 * @return name
	 */
	public static String getName(BLNoAdopt type) {
		if (type == null) {
			return "";
		}
		switch (type) {
			case NONE:
				// �Ȃ�
				return "C00412";
			case CODE:
				// ��ЃR�[�h
				return "C00596";
			case DEPARTMENT:
				// ����
				return "C00467";
			case YYMM_DATE:
				// �N��(YYMM)
				return "C02164";
			case YYYYMM_DATE:
				// �N��(YYYYMM)
				return "C02165";
			case YYYYMMDD_DATE:
				// ���t(YYYYMMDD)
				return "CBL837";
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
	public static BLNoAdopt get(int value) {
		for (BLNoAdopt em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return BLNoAdopt.NONE;
	}
}
