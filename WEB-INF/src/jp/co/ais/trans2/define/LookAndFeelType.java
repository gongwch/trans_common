package jp.co.ais.trans2.define;

/**
 * Look And Feel タイプ
 * 
 * @author AIS
 */
public enum LookAndFeelType {

	/** Windows */
	WINDOWS("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),

	/** Windows Classic */
	CLASSIC("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"),

	/** Defult */
	MINT("jp.co.ais.plaf.mint.MintLookAndFeel"),
	
	/** Acryl */
	ACRYL("jp.co.ais.plaf.acryl.AcrylLookAndFeel"),
	
	/** Hifi */
	HIFI("jp.co.ais.plaf.hifi.HiFiLookAndFeel"),
	
	/** Aero */
	AERO("jp.co.ais.plaf.aero.AeroLookAndFeel");
	
	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private LookAndFeelType(String value) {
		this.value = value;
	}

	/**
	 * valueを返す
	 * 
	 * @param type
	 * @return value
	 */
	public static LookAndFeelType get(String type) {
		if (type == null) {
			return MINT;
		}

		for (LookAndFeelType em : values()) {
			if (em.value.equals(type)) {
				return em;
			}
		}

		return MINT;
	}
	
	/**
	 * スタイルキーに基づいた定義を返す
	 * 
	 * @param style スタイルキー
	 * @return 定義
	 */
	public static LookAndFeelType getStyle(String style) {
		if (style == null) {
			return MINT;
		}

		for (LookAndFeelType em : values()) {
			if (em.name().equals(style)) {
				return em;
			}
		}

		return MINT;
	}

}
