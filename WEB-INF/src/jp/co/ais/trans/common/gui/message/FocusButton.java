package jp.co.ais.trans.common.gui.message;

/**
 * �t�H�[�J�X�{�^��Enum
 */
public enum FocusButton {

	/** �͂� */
	YES(0),

	/** ������ */
	NO(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private FocusButton(int value) {
		this.value = value;
	}
}
