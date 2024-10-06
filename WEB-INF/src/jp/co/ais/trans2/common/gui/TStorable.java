package jp.co.ais.trans2.common.gui;

import java.io.*;

/**
 * 保存⇔復元可能なコンポーネント
 * 
 * @author AIS
 */
public interface TStorable {

	/**
	 * コンポーネント情報を保存する
	 * 
	 * @param key
	 * @param obj
	 */
	public void saveComponent(TStorableKey key, Serializable obj);

	/**
	 * コンポーネント情報を復元する
	 * 
	 * @param key
	 */
	public void restoreComponent(TStorableKey key);

	/**
	 * 保存⇔復元キーを返す
	 * 
	 * @return 保存⇔復元キー
	 */
	public TStorableKey getKey();

}
