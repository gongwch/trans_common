package jp.co.ais.trans2.common.gui;

import java.lang.reflect.Field;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * �f�t�H���g�̃R���|�[�l���g�����L�[
 * 
 * @author AIS
 */
public class TDefaultStorableKey implements TStorableKey {

	/** �N���X */
	protected Class clazz;

	/** field */
	protected Field field = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param clazz
	 * @param field
	 */
	public TDefaultStorableKey(Class clazz, Field field) {
		this.clazz = clazz;
		this.field = field;
	}

	public String getKey() {
		String key = TLoginInfo.getCompany() == null ? "" : TLoginInfo.getCompany().getCode();
		key += "_" + (TLoginInfo.getUser() == null ? "" : TLoginInfo.getUser().getCode());
		key += "_" + clazz.getName();
		key += "." + field.getName();

		return key;
	}
}
