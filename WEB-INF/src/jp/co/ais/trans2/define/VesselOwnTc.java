package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * VCVesselOwnTc
 */
@Deprecated
public enum VesselOwnTc implements TEnumRadio {

	/** 0:OWN */
	OWN(0),

	/** 1:TC */
	TC(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �l
	 */
	VesselOwnTc(int value) {
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
	 * @param value
	 * @return ����
	 */
	public static String getName(int value) {
		return getName(get(value));
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param type
	 * @return ����
	 */
	public static String getName(VesselOwnTc type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case OWN:
				return "OWN";
			case TC:
				return "TC";
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
	public static VesselOwnTc get(int value) {
		for (VesselOwnTc em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}
}
