package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * æˆøæ “àq‰^’À’÷‚ßŠî€‹æ•ª
 */
public enum CustomerNaikoSimType implements TEnumRadio {

	/** ‚È‚µ */
	NONE("N"),

	/** ÏØŠî€ */
	LD("L"),

	/** —gØŠî€ */
	DC("D");

	/** ’l */
	public String value;

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^.
	 * 
	 * @param value ’l
	 */
	private CustomerNaikoSimType(String value) {
		this.value = value;
	}

	/**
	 * “àq‰^’À’÷‚ßŠî€‹æ•ª‚ğæ“¾‚·‚é
	 * 
	 * @return “àq‰^’À’÷‚ßŠî€‹æ•ª
	 */
	public String getValue() {
		return value;
	}

	/**
	 * “àq‰^’À’÷‚ßŠî€‹æ•ª‚ğ•Ô‚·
	 * 
	 * @param type ƒ^ƒCƒv
	 * @return “àq‰^’À’÷‚ßŠî€‹æ•ª
	 */
	public static CustomerNaikoSimType get(String type) {
		for (CustomerNaikoSimType em : values()) {
			if (Util.equals(em.value, type)) {
				return em;
			}
		}

		return NONE;
	}

	/**
	 * –¼Ìæ“¾
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * –¼Ìæ“¾
	 * 
	 * @param customerType
	 * @return “àq‰^’À’÷‚ßŠî€‹æ•ª–¼Ì
	 */
	public static String getName(CustomerNaikoSimType customerType) {

		switch (customerType) {
			case NONE:
				return "C00412";
			case LD:
				return "CSK277"; // ÏØŠî€
			case DC:
				return "CSK278"; // —gØŠî€
		}

		return "";
	}

	/**
	 * –¼Ìæ“¾
	 * 
	 * @param type
	 * @return “àq‰^’À’÷‚ßŠî€‹æ•ª–¼Ì
	 */
	public static String getName(String type) {

		CustomerNaikoSimType customerType = get(type);
		return getName(customerType);
	}

}
