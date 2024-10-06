package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;

/**
 * ���j���[�^�C�v
 */
public enum MenuType {
	
	/** �^�C�v1 */
	TYPE1("1"),

	/** �^�C�v2 */
	TYPE2("2"),

	/** �^�C�v3 */
	TYPE3("3");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private MenuType(String value) {
		this.value = value;
	}
	
	/**
	 * ���j���[�^�C�v��Ԃ�
	 * 
	 * @param type �l
	 * @return �^�C�v
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
