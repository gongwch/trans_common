package jp.co.ais.trans2.define;


/**
 * ����P��
 */
public enum AcceptUnit {

	/** �`�[��ʒP�ʂɃG���[�`�F�b�N */
	SLIPTYPE(0),
	
	/** �`�[�ԍ��P�ʂɃG���[�`�F�b�N */
	SLIPNO(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private AcceptUnit(int value) {
		this.value = value;
	}

	/**
	 * ����P�ʖ��̂�Ԃ�
	 * 
	 * @param acceptUnit
	 * @return ����P�ʖ���
	 */
	public static String getAcceptUnitName(AcceptUnit acceptUnit) {

		if (acceptUnit == null) {
			return null;
		}

		switch (acceptUnit) {
			case SLIPTYPE:
				// �`�[��ʒP�ʂɃG���[�`�F�b�N
				return "C02101";

			case SLIPNO:
				// �`�[�ԍ��P�ʂɃG���[�`�F�b�N
				return "C02102";

			default:
				return null;

		}
	}

	/**
	 * ����P�ʂ�enum��Ԃ�
	 * 
	 * @param acceptUnit
	 * @return �f�[�^�敪����
	 */
	public static AcceptUnit getAcceptUnit(int acceptUnit) {

		if(acceptUnit == 0){

			return SLIPTYPE;
		}else if(acceptUnit == 1){

			return SLIPNO;
		}

		return null;

	}

	/**
	 * ����P�ʂ�enum��Ԃ�
	 * 
	 * @param acceptUnit
	 * @return �f�[�^�敪����
	 */
	public static int getAcceptUnitCode(AcceptUnit acceptUnit) {

		if(acceptUnit == SLIPTYPE){

			return 0;
		}else

			return 1;
		}

	}

