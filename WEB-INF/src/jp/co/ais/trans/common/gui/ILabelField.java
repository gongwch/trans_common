package jp.co.ais.trans.common.gui;

/**
 * ���x���A�t�B�[���h�R���|
 */
public interface ILabelField {

	/**
	 * Label getter
	 * 
	 * @return ���x��
	 */
	public TLabel getLabel();

	/**
	 * label ���̐ݒ�.
	 * 
	 * @param size ��
	 */
	public void setLabelSize(int size);

	/**
	 * label ���̎擾
	 * 
	 * @return ��
	 */
	public int getLabelSize();
}
