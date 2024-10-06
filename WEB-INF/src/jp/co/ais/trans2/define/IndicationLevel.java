package jp.co.ais.trans2.define;

/**
 * �J�����x���敪(���x��0�`9)
 * 
 * @author AIS
 */
public enum IndicationLevel {

	/** ���x��0 */
	LEVEL0(0),

	/** ���x��1 */
	LEVEL1(1),

	/** ���x��2 */
	LEVEL2(2),

	/** ���x��3 */
	LEVEL3(3),

	/** ���x��4 */
	LEVEL4(4),

	/** ���x��5 */
	LEVEL5(5),

	/** ���x��6 */
	LEVEL6(6),

	/** ���x��7 */
	LEVEL7(7),

	/** ���x��8 */
	LEVEL8(8),

	/** ���x��9 */
	LEVEL9(9);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 */
	private IndicationLevel(int value) {
		this.value = value;
	}

	/**
	 * �J�����x���敪�擾
	 * 
	 * @param num �J�����x���敪
	 * @return �J�����x���敪
	 */
	public static IndicationLevel get(int num) {
		for (IndicationLevel em : values()) {
			if (em.value == num) {
				return em;
			}
		}
		return null;
	}

	/**
	 * �J�����x���敪�擾
	 * 
	 * @param indLvl �J�����x���敪
	 * @return �J�����x���敪
	 */
	public static String getName(IndicationLevel indLvl) {

		if (indLvl == null) {
			return "";
		}

		switch (indLvl) {
			case LEVEL0:
				return "C00722"; // ���x��0
			case LEVEL1:
				return "C01751"; // ���x��1
			case LEVEL2:
				return "C01752"; // ���x��2
			case LEVEL3:
				return "C01753"; // ���x��3
			case LEVEL4:
				return "C01754"; // ���x��4
			case LEVEL5:
				return "C01755"; // ���x��5
			case LEVEL6:
				return "C01756"; // ���x��6
			case LEVEL7:
				return "C01757"; // ���x��7
			case LEVEL8:
				return "C01758"; // ���x��8
			case LEVEL9:
				return "C01759"; // ���x��9
			default:
				return "";
		}
	}

	/**
	 * �J�����x���敪�擾
	 * 
	 * @param num �J�����x���敪�擾
	 * @return �J�����x���敪
	 */
	public static String getName(int num) {
		return getName(get(num));
	}
}