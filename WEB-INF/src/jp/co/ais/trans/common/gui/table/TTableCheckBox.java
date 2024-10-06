package jp.co.ais.trans.common.gui.table;

import java.awt.event.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable�pCheckBox
 */
public class TTableCheckBox extends TCheckBox {

	/** �J�����ԍ� */
	private int column = 0;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �A���X�v���b�h
	 * @param row �X�v���b�h���̍s�ԍ�
	 * @param column �X�v���b�h���̃J�����ԍ�
	 */
	public TTableCheckBox(TTable table, int row, int column) {

		setHorizontalAlignment(JCTableEnum.CENTER);
		setOpaque(false);
		setIndex(row);
		this.column = column;

		addItemListener(new SelectedListener(table));
	}

	/**
	 * �X�v���b�h�p���X�i�[
	 */
	private class SelectedListener implements ItemListener {

		/** �ΏۃX�v���b�h */
		private TTable table;

		/**
		 * �R���X�g���N�^.
		 * 
		 * @param table �ΏۃX�v���b�h
		 */
		public SelectedListener(TTable table) {
			this.table = table;
		}

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent e) {
			TCheckBox checkBox = (TCheckBox) e.getSource();
			int row = table.isSort() ? table.getCurrentRow() : checkBox.getIndex();

			table.requestFocusInWindow();
			table.setRowSelection(row, row);

			// �f�[�^�̕�
			Object obj = table.getDataSource().getTableDataItem(row, column);
			if (obj instanceof Boolean) {
				((JCVectorDataSource) table.getDataSource()).setCell(row, column,
					e.getStateChange() == ItemEvent.SELECTED);
			}
		}
	}
}
