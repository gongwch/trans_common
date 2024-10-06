package jp.co.ais.trans2.define;

/**
 * �`�[�ɑ΂��錠��
 * 
 * @author AIS
 */
public enum SlipRole {
	/** �S�� */
	ALL(0),
	
	/** ���͎� */
	SELF(1),
	
	/** �������� */
	DEPARTMENT(2);

	/** �l */
	public int value;

	private SlipRole(int value) {
		this.value = value;
	}

	/**
	 * �`�[�ɑ΂��錠����Ԃ�
	 * 
	 * @param slipRole �`�[�ɑ΂��錠��
	 * @return �`�[�ɑ΂��錠��
	 */
	public static SlipRole getSlipRole(int slipRole) {

		for (SlipRole em : values()) {
			if (em.value == slipRole) {
				return em;
			}
		}

		return null;
	}

	/**
	 * �`�[�ɑ΂��錠�����̂�Ԃ�
	 * 
	 * @param slipRole �`�[�ɑ΂��錠��
	 * @return �`�[�ɑ΂��錠������
	 */
	public static String getSlipRoleName(SlipRole slipRole) {

		if (slipRole == null) {
			return null;
		}

		switch (slipRole) {
			case ALL:
				// �S��
				return "C00310";

			case SELF:
				// ���͎�
				return "C01278";
				
			case DEPARTMENT:
				// ��������
				return "C00295";

			default:
				return null;

		}
	}

}
