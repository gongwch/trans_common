package jp.co.ais.trans2.model.slip;

/**
 * �`�[���W�b�N�t�@�N�g���[
 */
public interface SlipLogicFactory {

	/**
	 * �`�[��ʂ����Ƀ��W�b�N�N���X��Ԃ�.
	 * 
	 * @param slipType �`�[���
	 * @return ���W�b�N�N���X
	 */
	public SlipLogic getLogic(String slipType);
	
	/**
	 * �`�[��ʂ����Ƀ��W�b�N�N���X��Ԃ�.
	 * 
	 * @param slipType �`�[���
	 * @param dataType 
	 * @return ���W�b�N�N���X
	 */
	public SlipLogic getLogic(String slipType, String dataType);

}
