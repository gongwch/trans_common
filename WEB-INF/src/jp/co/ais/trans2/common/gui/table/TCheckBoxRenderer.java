package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �`�F�b�N�{�b�N�X�p�Z�������_��
 */
public class TCheckBoxRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	protected final TCheckBox renderer;

	/** �u�����N�\��Adapter */
	protected TComponentViewAdapter viewAdapter;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param table
	 */
	public TCheckBoxRenderer(TTable table) {

		this(new TCheckBox(), table);

	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param chkBox
	 * @param table
	 */
	public TCheckBoxRenderer(TCheckBox chkBox, TTable table) {

		super(table);

		// �����_���p�R���|�[�l���g��ݒ�
		this.renderer = chkBox;
		this.renderer.setHorizontalAlignment(SwingConstants.CENTER);
		this.renderer.setOpaque(true);
	}

	/**
	 * �u�����N�\���A�_�v�^�[
	 * 
	 * @param adapter �A�_�v�^�[
	 */
	public void setViewAdapter(TComponentViewAdapter adapter) {
		this.viewAdapter = adapter;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		if (viewAdapter != null) {

			if (viewAdapter.isBlank(row, column)) {
				TTextField label = new TTextField();
				label.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
				setBorder(label, isSelected, hasFocus);

				return label;
			}

			// �g�p�s�̏ꍇ�A�g�p�s�R���|��߂�
			if (!viewAdapter.isEnable(row, column)) {

				// �l�̐ݒ�
				renderer.setEnabled(false);
				renderer.setSelected(false);

			} else {
				renderer.setEnabled(true);
			}
		}

		// �l�̐ݒ�
		if (value == null) {
			renderer.setSelected(false);
		} else {
			renderer.setSelected(BooleanUtil.toBoolean(value.toString()));
		}

		// �F�̐ݒ�
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// �{�[�_�[�ݒ�
		setBorder(renderer, isSelected, hasFocus);

		return renderer;
	}

	/**
	 * �����_����
	 * 
	 * @return �����_����
	 */
	public TCheckBox getRenderer() {
		return renderer;
	}
}