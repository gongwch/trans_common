package jp.co.ais.trans2.common.gui;

/**
 * �Œ��TTable�̏�������
 */
public interface TFixedTableInitial {

	/**
	 * �X�v���b�h������
	 * 
	 * @param tbl
	 */
	public void initSpread(TTable tbl);

	/**
	 * �Œ�񐔂̎擾
	 * 
	 * @return �Œ��
	 */
	public int getFrozenCols();

}
