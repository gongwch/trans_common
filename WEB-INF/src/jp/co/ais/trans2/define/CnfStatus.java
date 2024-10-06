package jp.co.ais.trans2.define;

/**
 * 確認ステータス アンカーカスタマイズ
 * 
 * @author AIS
 */
public enum CnfStatus {
	/** 未 */
	NOT_YET(0),

	/** 済 */
	ALREADY(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private CnfStatus(int value) {
		this.value = value;
	}

	/**
	 * 区分を返す
	 * 
	 * @param value 値
	 * @return 区分
	 */
	public static CnfStatus get(int value) {
		for (CnfStatus em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}

	/**
	 * intより名称を返す
	 * 
	 * @param value
	 * @return 確認ステータス 名称
	 */
	public static String getName(int value) {
		for (CnfStatus em : values()) {
			if (em.value == value) {
				return getName(em);
			}
		}
		return null;
	}

	/**
	 * enumより名称を返す
	 * 
	 * @param cnfStatus
	 * @return 確認ステータス 名称
	 */
	public static String getName(CnfStatus cnfStatus) {

		switch (cnfStatus) {
			case NOT_YET:
				return "C00503"; // 未
			case ALREADY:
				return "C01701"; // 済
			default:
				break;
		}
		return null;
	}

}
