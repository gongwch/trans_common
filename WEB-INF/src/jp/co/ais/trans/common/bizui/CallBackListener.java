package jp.co.ais.trans.common.bizui;

import java.awt.*;

/**
 * �R���|�[�l���g���X�i�[<br>
 * lostfocus��REF�_�C�A���O����l���I�΂ꂽ�ۂɌĂ΂��.
 * �Â��o�[�W����<br>
 * �V�����o�[�W����:{@link jp.co.ais.trans2.common.gui.event.TCallBackListener}
 */
public class CallBackListener {

	/**
	 * �㏈��
	 */
	public void before() {
		// Orverride�p
	}

	/**
	 * �㏈��
	 */
	public void before(@SuppressWarnings("unused") Component component) {
		// Orverride�p
	}

	/**
	 * �㏈��
	 */
	public void after() {
		// Orverride�p
	}

	/**
	 * �㏈��
	 */
	public void after(@SuppressWarnings("unused") Component component) {
		// Orverride�p
	}

	/**
	 * �㏈��
	 * 
	 * @param flag
	 */
	public void after(@SuppressWarnings("unused") boolean flag) {
		// Orverride�p
	}
}
