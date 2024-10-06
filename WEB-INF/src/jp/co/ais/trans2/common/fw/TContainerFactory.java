package jp.co.ais.trans2.common.fw;

/**
 * コンテナファクトリ
 */
public class TContainerFactory {

	/**
	 * コンテナ取得
	 * 
	 * @return コンテナ
	 */
	public static TContainer getContainer() {
		return new TContainer();
	}
}
