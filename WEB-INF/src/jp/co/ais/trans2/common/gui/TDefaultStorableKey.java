package jp.co.ais.trans2.common.gui;

import java.lang.reflect.Field;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * デフォルトのコンポーネント復元キー
 * 
 * @author AIS
 */
public class TDefaultStorableKey implements TStorableKey {

	/** クラス */
	protected Class clazz;

	/** field */
	protected Field field = null;

	/**
	 * コンストラクタ.
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
