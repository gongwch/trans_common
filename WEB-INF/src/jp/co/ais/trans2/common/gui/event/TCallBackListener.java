package jp.co.ais.trans2.common.gui.event;

/**
 * �R���|�[�l���g���X�i�[<br>
 * lostfocus��REF�_�C�A���O����l���I�΂ꂽ�ۂɌĂ΂��.
 */
public class TCallBackListener {

	/**
	 * �㏈��
	 */
	public void before() {
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
	 * 
	 * @param flag
	 */
	public void after(@SuppressWarnings("unused")
	boolean flag) {
		// Orverride�p
	}

	/**
	 * �㏈��(Verify�p)
	 * 
	 * @param flag
	 * @return false:NG
	 */
	public boolean afterVerify(@SuppressWarnings("unused")
	boolean flag) {
		// Orverride�p
		return true;
	}
}
