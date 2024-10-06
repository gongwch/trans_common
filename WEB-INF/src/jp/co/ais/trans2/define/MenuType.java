package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;

/**
 * メニュータイプ
 */
public enum MenuType {
	
	/** タイプ1 */
	TYPE1("1"),

	/** タイプ2 */
	TYPE2("2"),

	/** タイプ3 */
	TYPE3("3");

	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private MenuType(String value) {
		this.value = value;
	}
	
	/**
	 * メニュータイプを返す
	 * 
	 * @param type 値
	 * @return タイプ
	 */
	public static MenuType get(String type) {
		if (Util.isNullOrEmpty(type)) {
			return TYPE3;
		}
		
		for (MenuType em : values()) {
			if (em.value.equals(type)) {
				return em;
			}
		}
		
		return TYPE3;
	}
}
