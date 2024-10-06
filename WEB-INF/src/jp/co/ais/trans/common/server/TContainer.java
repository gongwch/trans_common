package jp.co.ais.trans.common.server;

import org.seasar.framework.container.*;

/**
 * �R���e�i.<br>
 * S2Container���b�p�[�N���X
 */
public class TContainer {

	/** �R���e�i */
	private S2Container container;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param container �R���e�i
	 */
	TContainer(S2Container container) {
		this.container = container;
	}

	/**
	 * �R���|�[�l���g�擾
	 * 
	 * @param componentKey �L�[�R�[�h�A�܂��́A�L�[�N���X
	 * @return �R���|�[�l���g
	 */
	public Object getComponent(Object componentKey) {
		return this.container.getComponent(componentKey);
	}
}
