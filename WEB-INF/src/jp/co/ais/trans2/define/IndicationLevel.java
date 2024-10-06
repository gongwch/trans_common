package jp.co.ais.trans2.define;

/**
 * 開示レベル区分(レベル0～9)
 * 
 * @author AIS
 */
public enum IndicationLevel {

	/** レベル0 */
	LEVEL0(0),

	/** レベル1 */
	LEVEL1(1),

	/** レベル2 */
	LEVEL2(2),

	/** レベル3 */
	LEVEL3(3),

	/** レベル4 */
	LEVEL4(4),

	/** レベル5 */
	LEVEL5(5),

	/** レベル6 */
	LEVEL6(6),

	/** レベル7 */
	LEVEL7(7),

	/** レベル8 */
	LEVEL8(8),

	/** レベル9 */
	LEVEL9(9);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	private IndicationLevel(int value) {
		this.value = value;
	}

	/**
	 * 開示レベル区分取得
	 * 
	 * @param num 開示レベル区分
	 * @return 開示レベル区分
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
	 * 開示レベル区分取得
	 * 
	 * @param indLvl 開示レベル区分
	 * @return 開示レベル区分
	 */
	public static String getName(IndicationLevel indLvl) {

		if (indLvl == null) {
			return "";
		}

		switch (indLvl) {
			case LEVEL0:
				return "C00722"; // レベル0
			case LEVEL1:
				return "C01751"; // レベル1
			case LEVEL2:
				return "C01752"; // レベル2
			case LEVEL3:
				return "C01753"; // レベル3
			case LEVEL4:
				return "C01754"; // レベル4
			case LEVEL5:
				return "C01755"; // レベル5
			case LEVEL6:
				return "C01756"; // レベル6
			case LEVEL7:
				return "C01757"; // レベル7
			case LEVEL8:
				return "C01758"; // レベル8
			case LEVEL9:
				return "C01759"; // レベル9
			default:
				return "";
		}
	}

	/**
	 * 開示レベル区分取得
	 * 
	 * @param num 開示レベル区分取得
	 * @return 開示レベル区分
	 */
	public static String getName(int num) {
		return getName(get(num));
	}
}