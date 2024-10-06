package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * ���F�^�C�v
 */
public enum ApproveType implements TEnumRadio {

	/** �ʏ� */
	REGULAR(0),
	/** ���[�N�t���[���F */
	WORKFLOW(1);

	/** DB�l */
	private int value;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value DB�l
	 */
	private ApproveType(int value) {
		this.value = value;
	}

	/**
	 * ���̂��擾
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̂��擾
	 * 
	 * @param type
	 * @return ����
	 */
	public static String getName(ApproveType type) {
		if (type == null) {
			return null;
		}
		switch (type) {
			case REGULAR:
				return "�ʏ�";
			case WORKFLOW:
				return "���[�N�t���[���F";
		}
		return null;
	}

	/**
	 * DB�l���擾
	 * 
	 * @return DB�l
	 */
	public int getDBValue() {
		return this.value;
	}

	/**
	 * int�l�ɑΉ�����Enum�l�擾
	 * 
	 * @param val
	 * @return Enum�l
	 */
	public static ApproveType get(int val) {
		for (ApproveType t : values()) {
			if (t.getDBValue() == val) {
				return t;
			}
		}
		return null;
	}
}
