package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * �۔F���A�N�V����
 */
public enum DenyAction implements TEnumRadio {

	/** �ʏ�(�ЂƂO) */
	BACK_ONE,
	/** �ŏ��ɖ߂� */
	BACK_FIRST;

	/**
	 * ���̎擾
	 */
	public String getName() {
		switch (this) {
			case BACK_FIRST:
				return "����";
			case BACK_ONE:
				return "�ЂƂO";
		}
		return null;
	}

}
