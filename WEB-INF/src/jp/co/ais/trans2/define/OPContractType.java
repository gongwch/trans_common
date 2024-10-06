package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * OP Contract Type
 */
public enum OPContractType implements TEnumRadio {

	/** 0:TC-OUT */
	TC_OUT(0),

	/** 1:TC-IN */
	TC_IN(1),

	/** 3:VC */
	VC(3),

	/** 4:VC(�i) */
	IC(4),

	/** 5:VR */
	VR(5),

	/** 6:VR(�i) */
	IR(6),

	/** 7:BL �\�����FVC(�i) */
	BL(7),

	/** 8:VR(BL-Liner) �\�����FVR(�i) */
	BR(8),

	/** 10:�^�q�ϑ� */
	OS(10),

	/** 9:���� */
	COM(9);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �l
	 */
	OPContractType(int value) {
		this.value = value;
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
	 * @return ����
	 */
	public String getNames() {
		return getNames(this);
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param type
	 * @return ����
	 */
	public static String getName(OPContractType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case TC_OUT:
				return "COP190"; // TC-OUT
			case TC_IN:
				return "COP506"; // TC-IN
			case VC:
				return "COP191"; // VC
			case IC:
				return "COP1150"; // VC(�i)
			case VR:
				return "COP1151"; // VR
			case IR:
				return "COP1152"; // VR(�i)
			case BL:
				return "COP1150"; // VC(�i) LINER���p�敪�ɂ��IC�����s��
			case BR:
				return "COP1152"; // VR(�i) LINER���p�敪�ɂ��IR�����s��
			case OS:
				return "COP1512";//�^�q�ϑ�
			case COM:
				return "COP898"; // COMM
			default:
				return "";
		}
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param type
	 * @return ����
	 */
	public static String getNames(OPContractType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case TC_OUT:
				return "COP344"; // TO
			case TC_IN:
				return "COP526"; // TI
			case VC:
				return "COP191"; // VC
			case IC:
				return "COP1150"; // VC(�i)
			case VR:
				return "COP1151"; // VR
			case IR:
				return "COP1152"; // VR(�i)
			case BL:
				return "COP1150"; // VC(�i) LINER���p�敪�ɂ��IC�����s��
			case BR:
				return "COP1152"; // VR(�i) LINER���p�敪�ɂ��IR�����s��
			case OS:
				return "COP1512";//�^�q�ϑ�
			case COM:
				return "COP898"; // COMM
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
	public static OPContractType get(int value) {
		for (OPContractType em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}
}
