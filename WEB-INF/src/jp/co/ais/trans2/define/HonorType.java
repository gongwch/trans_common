package jp.co.ais.trans2.define;

/**
 * �h�̃��X�g
 * 
 * @author AIS
 */
public enum HonorType {

	/** �ݒ薳 */
	NONE(0),

	/** �l */
	MR_JP(1),

	/** �䒆 */
	YOUR_JP(2),

	/** �� */
	DEAR_JP(3),

	/** �a */
	PER_JP(4),

	/** ���� */
	AD1_JP(5),

	/** ����F */
	AD2_JP(6),

	/** �搶 */
	TCH_JP(7),

	/** Mr. */
	MR_EN(8),

	/** Ms. */
	MS_EN(9),

	/** Mrs. */
	MRS_EN(10),

	/** Messrs. */
	MERS_EN(11),

	/** To. */
	TO_EN(12);

	/** �l */
	public int value;

	/**
	 * @param value
	 */
	private HonorType(int value) {
		this.value = value;
	}

	/**
	 * �h�̃��X�g ��Ԃ�
	 * 
	 * @param honorType �h�̃��X�g
	 * @return �h�̃��X�g
	 */
	public static HonorType getHonorType(int honorType) {

		for (HonorType em : values()) {
			if (em.value == honorType) {
				return em;
			}
		}

		return null;
	}

	/**
	 * �h�̃��X�g ��Ԃ�
	 * 
	 * @param honorType �h�̃��X�g
	 * @return �h�̃��X�g
	 */
	public static String getName(HonorType honorType) {

		if (honorType == null) {
			return null;
		}

		switch (honorType) {
			case MR_JP:
				// �l
				return "C04241";

			case YOUR_JP:
				// �䒆
				return "C02541";

			case DEAR_JP:
				// ��
				return "C12176";

			case PER_JP:
				// �a
				return "C12177";

			case AD1_JP:
				// ����
				return "C12178";

			case AD2_JP:
				// ����F
				return "C12179";

			case TCH_JP:
				// �搶
				return "C12180";

			case MR_EN:
				// Mr.
				return "C12181";

			case MS_EN:
				// Ms.
				return "C12182";

			case MRS_EN:
				// Mrs.
				return "C12183";

			case MERS_EN:
				// Messrs.
				return "CTC0102";

			case TO_EN:
				// To.
				return "C12189";

			default:
				return null;

		}
	}

	/**
	 * �h�̃��X�g�̑O�t�A��t ��Ԃ�
	 * 
	 * @param honorType �h�̃��X�g
	 * @return true:�O false:��
	 */
	public static boolean isLeadHonor(HonorType honorType) {

		if (honorType == null) {
			return false;
		}

		switch (honorType) {
			case MR_JP:
				// �l
			case YOUR_JP:
				// �䒆
			case DEAR_JP:
				// ��
			case PER_JP:
				// �a
			case TCH_JP:
				// �搶
				return false;

			case AD1_JP:
				// ����
			case AD2_JP:
				// ����F
			case MR_EN:
				// Mr.
			case MS_EN:
				// Ms.
			case MRS_EN:
				// Mrs.
			case MERS_EN:
				// Messrs.
			case TO_EN:
				// To.
				return true;

			default:
				return false;

		}
	}

}
