package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * ����� ���q�^�����ߊ�敪
 */
public enum CustomerNaikoSimType implements TEnumRadio {

	/** �Ȃ� */
	NONE("N"),

	/** �ϐ؊ */
	LD("L"),

	/** �g�؊ */
	DC("D");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private CustomerNaikoSimType(String value) {
		this.value = value;
	}

	/**
	 * ���q�^�����ߊ�敪���擾����
	 * 
	 * @return ���q�^�����ߊ�敪
	 */
	public String getValue() {
		return value;
	}

	/**
	 * ���q�^�����ߊ�敪��Ԃ�
	 * 
	 * @param type �^�C�v
	 * @return ���q�^�����ߊ�敪
	 */
	public static CustomerNaikoSimType get(String type) {
		for (CustomerNaikoSimType em : values()) {
			if (Util.equals(em.value, type)) {
				return em;
			}
		}

		return NONE;
	}

	/**
	 * ���̎擾
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̎擾
	 * 
	 * @param customerType
	 * @return ���q�^�����ߊ�敪����
	 */
	public static String getName(CustomerNaikoSimType customerType) {

		switch (customerType) {
			case NONE:
				return "C00412";
			case LD:
				return "CSK277"; // �ϐ؊
			case DC:
				return "CSK278"; // �g�؊
		}

		return "";
	}

	/**
	 * ���̎擾
	 * 
	 * @param type
	 * @return ���q�^�����ߊ�敪����
	 */
	public static String getName(String type) {

		CustomerNaikoSimType customerType = get(type);
		return getName(customerType);
	}

}
