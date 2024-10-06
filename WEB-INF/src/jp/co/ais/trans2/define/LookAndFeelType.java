package jp.co.ais.trans2.define;

/**
 * Look And Feel �^�C�v
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
	
	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private LookAndFeelType(String value) {
		this.value = value;
	}

	/**
	 * value��Ԃ�
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
	 * �X�^�C���L�[�Ɋ�Â�����`��Ԃ�
	 * 
	 * @param style �X�^�C���L�[
	 * @return ��`
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
