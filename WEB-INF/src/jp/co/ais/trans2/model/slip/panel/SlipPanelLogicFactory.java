package jp.co.ais.trans2.model.slip.panel;

/**
 * �`�[���W�b�N�t�@�N�g���[
 */
public interface SlipPanelLogicFactory {

	/**
	 * �`�[��ʂ����Ƀ��W�b�N�N���X��Ԃ�.
	 * 
	 * @param slipType �`�[���
	 * @return ���W�b�N�N���X
	 */
	public SlipPanelLogic getLogic(String slipType);

}
