package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * 換算端数処理方式
 * 
 * @author AIS
 */
public enum ExchangeFraction implements TEnumRadio {
	/**
	 * 切り捨て
	 */
	TRUNCATE(0),

	/**
	 * 切り上げ
	 */
	ROUND_UP(1),

	/**
	 * 四捨五入
	 */
	ROUND_OFF(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ExchangeFraction(int value) {
		this.value = value;
	}

	/**
	 * 換算端数処理方式を返す
	 * 
	 * @param exchangeFraction 値
	 * @return 換算端数処理方式
	 */
	public static ExchangeFraction getExchangeFraction(int exchangeFraction) {
		for (ExchangeFraction em : values()) {
			if (em.value == exchangeFraction) {
				return em;
			}
		}
		return null;
	}

	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * 名称を返す
	 * 
	 * @return 名称ID
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 換算端数処理方式名称を返す
	 * 
	 * @param value 換算端数
	 * @return 名称
	 */
	public static String getName(int value) {

		ExchangeFraction fraction = getExchangeFraction(value);
		if (fraction == null) {
			return null;
		}
		return getName(fraction);
	}

	/**
	 * 換算端数処理方式名称を返す
	 * 
	 * @param fraction 換算端数
	 * @return 名称
	 */
	public static String getName(ExchangeFraction fraction) {

		if (fraction == null) {
			return null;
		}

		switch (fraction) {
			case ROUND_OFF:
				return "C00215";// 四捨五入
			case ROUND_UP:
				return "C00120"; // 切り上げ
			case TRUNCATE:
				return "C00121"; // 切り捨て
			default:
				return null;
		}
	}

}
