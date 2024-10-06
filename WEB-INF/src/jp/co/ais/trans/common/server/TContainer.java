package jp.co.ais.trans.common.server;

import org.seasar.framework.container.*;

/**
 * コンテナ.<br>
 * S2Containerラッパークラス
 */
public class TContainer {

	/** コンテナ */
	private S2Container container;

	/**
	 * コンストラクタ
	 * 
	 * @param container コンテナ
	 */
	TContainer(S2Container container) {
		this.container = container;
	}

	/**
	 * コンポーネント取得
	 * 
	 * @param componentKey キーコード、または、キークラス
	 * @return コンポーネント
	 */
	public Object getComponent(Object componentKey) {
		return this.container.getComponent(componentKey);
	}
}
