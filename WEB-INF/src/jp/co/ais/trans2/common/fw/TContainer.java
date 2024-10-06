package jp.co.ais.trans2.common.fw;

import org.seasar.framework.container.factory.*;

import jp.co.ais.trans2.common.config.*;

/**
 * コンテナ
 */
public class TContainer {

	static {
		if (!ClientConfig.isWeb() && !SingletonS2ContainerFactory.hasContainer()) {
			SingletonS2ContainerFactory.setConfigPath("trans2.dicon");
			SingletonS2ContainerFactory.init();
		}
	}

	/**
	 * コンポーネント取得
	 * 
	 * @param cls クラス
	 * @return コンポーネント
	 */
	public Object getComponent(Class cls) {
		return SingletonS2ContainerFactory.getContainer().getComponent(cls);
	}

	/**
	 * コンポーネント取得
	 * 
	 * @param obj キー(名称)
	 * @return コンポーネント
	 */
	public Object getComponent(Object obj) {
		return SingletonS2ContainerFactory.getContainer().getComponent(obj);
	}
}
