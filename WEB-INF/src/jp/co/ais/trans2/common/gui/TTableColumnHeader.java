package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.table.*;

/**
 * �w�b�_�`��\�̃J����
 */
public interface TTableColumnHeader {

	/**
	 * �w�b�_�`��҂̍쐬
	 * 
	 * @param tbl
	 * @param backGround
	 * @param foreGround
	 * @return �w�b�_�`���
	 */
	public TableCellRenderer createHeaderRenderer(TTable tbl, Color backGround, Color foreGround);

}
