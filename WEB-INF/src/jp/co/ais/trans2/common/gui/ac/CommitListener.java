package jp.co.ais.trans2.common.gui.ac;

/**
 * �l����
 */
public interface CommitListener {

	/**
	 * �l�I����̏���
	 * 
	 * @param value
	 */
	public void commit(Object value);

	/**
	 * �v�b�V������l���擾
	 * 
	 * @param value
	 * @return �ݒ�l
	 */
	public String getText(Object value);

	/**
	 * @return true: push���������s����
	 */
	public boolean isDoPush();
}
