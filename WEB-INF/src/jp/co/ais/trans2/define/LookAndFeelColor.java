package jp.co.ais.trans2.define;

/**
 * Look And Feel 色タイプ
 */
public enum LookAndFeelColor {

	/** blue */
	Blue,

	/** pink */
	Pink,
	
	/** orange */
	Orange,
	
	/** green */
	Green,

	/** gray */
	Gray,
	
	/** white */
	White;

	/**
	 * valueを返す
	 * 
	 * @param color
	 * @return value
	 */
	public static LookAndFeelColor get(String color) {
		if (color == null) {
			return White;
		}
		
		for (LookAndFeelColor em : values()) {
			if (em.name().equalsIgnoreCase(color)) {
				return em;
			}
		}

		return White;
	}
}
