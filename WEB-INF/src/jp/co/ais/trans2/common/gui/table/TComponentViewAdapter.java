package jp.co.ais.trans2.common.gui.table;

/**
 * �X�v���b�h�V�[�g��̃R���|�[�l���g�\������A�_�v�^�[
 */
public abstract class TComponentViewAdapter {

	/**
	 * �u�����N����
	 * 
	 * @param row �s
	 * @param column ��
	 * @return true:�u�����N
	 */
	@SuppressWarnings("unused")
	public boolean isBlank(int row, int column) {
		return false;
	}

	/**
	 * �g�p�\����
	 * 
	 * @param row �s
	 * @param column ��
	 * @return true:�g�p��
	 */
	@SuppressWarnings("unused")
	public boolean isEnable(int row, int column) {
		return true;
	}
}
