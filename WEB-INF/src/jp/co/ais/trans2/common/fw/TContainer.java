package jp.co.ais.trans2.common.fw;

import org.seasar.framework.container.factory.*;

import jp.co.ais.trans2.common.config.*;

/**
 * �R���e�i
 */
public class TContainer {

	static {
		if (!ClientConfig.isWeb() && !SingletonS2ContainerFactory.hasContainer()) {
			SingletonS2ContainerFactory.setConfigPath("trans2.dicon");
			SingletonS2ContainerFactory.init();
		}
	}

	/**
	 * �R���|�[�l���g�擾
	 * 
	 * @param cls �N���X
	 * @return �R���|�[�l���g
	 */
	public Object getComponent(Class cls) {
		return SingletonS2ContainerFactory.getContainer().getComponent(cls);
	}

	/**
	 * �R���|�[�l���g�擾
	 * 
	 * @param obj �L�[(����)
	 * @return �R���|�[�l���g
	 */
	public Object getComponent(Object obj) {
		return SingletonS2ContainerFactory.getContainer().getComponent(obj);
	}
}
