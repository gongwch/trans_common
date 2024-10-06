package jp.co.ais.trans2.common.objsave;

import java.io.*;

/**
 * オブジェクト保存エレメント
 */
public class SaveElement implements Serializable {

	/** 表示キー */
	protected String key = null;

	/** 値 */
	protected Object value = null;

	/**
	 * 表示キーの取得
	 * 
	 * @return key 表示キー
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 表示キーの設定
	 * 
	 * @param key 表示キー
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 値の取得
	 * 
	 * @return value 値
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 値の設定
	 * 
	 * @param value 値
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return key;
	}
}
