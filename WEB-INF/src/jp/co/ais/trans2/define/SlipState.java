package jp.co.ais.trans2.define;

/**
 * �`�[�̍X�V�敪
 * 
 * @author AIS
 */
public enum SlipState  {
	
	/** �o�^ */
	ENTRY(1),

	/** ���ꏳ�F */
	FIELD_APPROVE(2),
	
	/** �o�����F */
	APPROVE(3),
	
	/** �X�V */
	UPDATE(4),
	
	/** ����۔F */
	FIELD_DENY(11),
	
	/** �o���۔F */
	DENY(12);
	
	
	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private SlipState(int value) {
		this.value = value;
	}

	/**
	 * �X�V�敪���擾����
	 * 
	 * @return �X�V�敪
	 */
	public int getSlipState() {
		return value;
	}
	

	/**
	 * �X�V�敪���̂�Ԃ�
	 * 
	 * @return �X�V�敪����
	 */
	public String getName() {
		return getSlipStateName(this);
	}
	
	/**
	 * �X�V�敪��Ԃ�
	 * 
	 * @param slipState �`�[�̏��
	 * @return �X�V�敪��
	 */
	public static SlipState getSlipState(int slipState) {
		for (SlipState em : values()) {
			if (em.value == slipState) {
				return em;
			}
		}
		
		return null;
	}

	/**
	 * �X�V�敪���̂�Ԃ�
	 * 
	 * @param slipState �`�[�̏��
	 * @return �X�V�敪����
	 */
	public static String getSlipStateName(SlipState slipState) {

		if (slipState == null) {
			return null;
		}

		switch (slipState) {
			case ENTRY:
				return "C01258";// �o�^

			case FIELD_APPROVE:
				return "C00157";// ���ꏳ�F
				
			case APPROVE:
				return "C01616";// �o�����F
				
			case UPDATE:
				return "C00169";// �X�V
				
			case FIELD_DENY:
				return "C01617";// ����۔F

			case DENY:
				return "C01615";// �o���۔F
		
			default:
				return null;
		}
	}

	/**
	 * �d��C���^�[�t�F�[�X�敪���̂�Ԃ�(�`�[��ʃ}�X�^�Ŏg�p)
	 * 
	 * @param slipState �`�[�̏��
	 * @return �X�V�敪����
	 */
	public static SlipState getJnlIfDivName(int slipState) {

		if(slipState == 0){
			return ENTRY;
		}else{
			return APPROVE;
		}
	}

	/**
	 * �d��C���^�[�t�F�[�X�敪�R�[�h��Ԃ�(�`�[��ʃ}�X�^�Ŏg�p)
	 * 
	 * @param slipState �`�[�̏��
	 * @return �X�V�敪����
	 */
	public static int getJnlIfDivCode(SlipState slipState) {

		if(slipState == ENTRY){
			return 0;
		}else{
			return 1;
		}
	}

	/**
	 * �X�V�敪���̂�Ԃ�
	 * 
	 * @param slipState �`�[�̏��
	 * @return �X�V�敪����
	 */
	public static String getSlipStateName(int slipState) {
		return getSlipStateName(getSlipState(slipState));
	}
	
}
