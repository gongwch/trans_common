package jp.co.ais.trans2.define;


/**
 * 外部システム区分
 * 
 * @author AIS
 */
public enum OuterSystemType {
	/** パッケージ使用する */
	PACKAGE_USE(0),
	
	/** パッケージ使用しない */
	PACKAGE_UNUSE(1),
	
	/** 外部システム */
	OUTER_SYSTEM(2);

	/** 値 */
	public int value;
	
	/**
	 * コンストラクタ.
	 * 
	 * @param value
	 */
	private OuterSystemType(int value) {
		this.value = value;
	}
	
	/**
	 * 外部システム区分を返す
	 * 
	 * @param outsysType 外部システム区分
	 * @return 値
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
 * 外部システム区分を返す OutSysTypeで受け取りStringで返す
 * 
 * @param outsysType
 * @return 外部システム区分
 */
	public static String getName(OuterSystemType outsysType) {
	
		switch (outsysType) {
			case PACKAGE_USE:
				// パッケージ使用する
				return "C02104";
			case PACKAGE_UNUSE:
				// パッケージ使用しない
				return "C02105";
			case OUTER_SYSTEM:
				// 外部システム
				return "C02106";
			default:
				return null;
		}
	}
}