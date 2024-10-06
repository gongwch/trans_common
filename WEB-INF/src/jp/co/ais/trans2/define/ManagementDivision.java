package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;

/**
 * �Ǘ��R���g���[�����ڒ�`
 */
public enum ManagementDivision {

	/** �Ȃ� */
	None(0),

	/** ���� */
	Department(1),

	/** �Ǘ�1 */
	Management1(2),

	/** �Ǘ�2 */
	Management2(3),

	/** �Ǘ�3 */
	Management3(4),

	/** �Ǘ�4 */
	Management4(5),

	/** �Ǘ�5 */
	Management5(6),

	/** �Ǘ�6 */
	Management6(7);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ManagementDivision(int value) {
		this.value = value;
	}

	/**
	 * �Ǘ��R���g���[�����擾����
	 * 
	 * @param division
	 * @return �l
	 */
	public static ManagementDivision getDivision(int division) {
		for (ManagementDivision em : values()) {
			if (em.value == division) {
				return em;
			}
		}
		return null;
	}

	/**
	 * �Ǘ��R���g���[�����擾����
	 * @return �l
	 */
	public String getValue() {
		return Util.avoidNull(value);
	}
}