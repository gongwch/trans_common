package jp.co.ais.trans.common.gui;

import java.util.List;

/**
 * JTable�̍s�^�C�g���A��^�C�g���̃��b�Z�[�WID��o�^�^�擾���� interface. 
 * <br>
 * JTable��ColumnLabel��RowLabel�ɁA���b�Z�[�WID��o�^�^�擾����.
 */
public interface TInterfaceTableLangMessageID {

	/**
	 * ��^�C�g����messageID�擾
	 * 
	 * @return messageID�z��
	 */
	public String[] getColumnTitleMessageID();

	/**
	 * ��^�C�g����messageID�ݒ�(�z���)
	 * 
	 * @param messageIDs
	 */
	public void setColumnTitleMessageID(String[] messageIDs);

	/**
	 * ��^�C�g����messageID�ݒ�(List��)
	 * 
	 * @param list
	 */
	public void setColumnTitleMessageID(List<String> list);

	/**
	 * �s�^�C�g����messageID�擾
	 * 
	 * @return messageID�z��
	 */
	public String[] getRowTitleMessageID();

	/**
	 * �s�^�C�g����messageID�ݒ�(�z���)
	 * 
	 * @param messageIDs
	 */
	public void setRowTitleMessageID(String[] messageIDs);

	/**
	 * �s�^�C�g����messageID�ݒ�(List��)
	 * 
	 * @param list
	 */
	public void setRowTitleMessageID(List<String> list);
}
