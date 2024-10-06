package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �R���{�{�b�N�X�p�Z�������_��
 */
public class TComboBoxRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	protected final TComboBox renderer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param cmbBox
	 * @param table
	 */
	public TComboBoxRenderer(TComboBox cmbBox, TTable table) {
		super(table);

		// �����_���p�R���|�[�l���g��ݒ�
		this.renderer = cmbBox;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		TComboBox box = new TComboBox();

		// ���I����Ԃŋ󔒕\���ɂ��Ȃ��ꍇ�̓R�����g������
		// if (Util.isNullOrEmpty(value) && renderer.getModel().getSize() != 0) {
		// Object element = renderer.getModel().getElementAt(0);
		// box.addItem(element.toString());
		//
		// } else {
		box.addItem((value == null) ? "" : value.toString());
		// }

		// �F�̐ݒ�
		box.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		box.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// �{�[�_�[�ݒ�
		setBorder(box, isSelected, hasFocus);

		return box;
	}

}