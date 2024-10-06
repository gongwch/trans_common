package jp.co.ais.trans2.define;


/**
 * �O���V�X�e���敪
 * 
 * @author AIS
 */
public enum OuterSystemType {
	/** �p�b�P�[�W�g�p���� */
	PACKAGE_USE(0),
	
	/** �p�b�P�[�W�g�p���Ȃ� */
	PACKAGE_UNUSE(1),
	
	/** �O���V�X�e�� */
	OUTER_SYSTEM(2);

	/** �l */
	public int value;
	
	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value
	 */
	private OuterSystemType(int value) {
		this.value = value;
	}
	
	/**
	 * �O���V�X�e���敪��Ԃ�
	 * 
	 * @param outsysType �O���V�X�e���敪
	 * @return �l
	 */
	public static OuterSystemType get(int outsysType) {
		for (OuterSystemType em : values()) {
			if (em.value == outsysType) {
				return em;
			}
		}
		
		return null;
	}


/**
 * �O���V�X�e���敪��Ԃ� OutSysType�Ŏ󂯎��String�ŕԂ�
 * 
 * @param outsysType
 * @return �O���V�X�e���敪
 */
	public static String getName(OuterSystemType outsysType) {
	
		switch (outsysType) {
			case PACKAGE_USE:
				// �p�b�P�[�W�g�p����
				return "C02104";
			case PACKAGE_UNUSE:
				// �p�b�P�[�W�g�p���Ȃ�
				return "C02105";
			case OUTER_SYSTEM:
				// �O���V�X�e��
				return "C02106";
			default:
				return null;
		}
	}
}