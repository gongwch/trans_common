package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TZ�ҏW�p�Z�������_��
 */
public class TTimezoneRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	private final TTimezoneField renderer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTimezoneRenderer(TTimezoneField renderer, TTable table) {

		super(table);

		// �����_���p�R���|�[�l���g�̐ݒ�
		this.renderer = renderer;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		TTimezoneField txt = new TTimezoneField();
		txt.setTableCellEditor(true);
		txt.setMinus15Only(renderer.isMinus15Only());

		txt.setBorder(BorderFactory.createEmptyBorder());

		// if (renderer.isUseTableFont()) {
		// txt.setFont(table.getFont());
		// }

		// �l�̐ݒ�
		setValue(txt, value);

		// �F�̐ݒ�
		txt.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// �{�[�_�[�ݒ�
		setBorder(txt, isSelected, hasFocus);

		return txt;
	}

	/**
	 * �l�̐ݒ�
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TTimezoneField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();

		} else if (value instanceof Number) {
			txt.setNumber((Number) value);

		} else {
			txt.setText(Util.avoidNull(value));

		}
	}
}