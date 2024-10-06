package jp.co.ais.trans2.model.security;

/**
 * 排他性インターフェース
 * @author AIS
 *
 */
public interface TExclusive {

	/**
	 * 排他制御方法を返す
	 * @return 排他制御方法
	 */
	public TExclusiveControlMethod getExclusiveControlMethod();

}
