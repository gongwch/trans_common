package jp.co.ais.trans2.common.gui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �L�[�A�_�v�^�[(�\��t���@�\)
 */
public class TTableKeyAdapter extends KeyAdapter {

	/** �t�B�[���h */
	public TTextField editor;

	/** �X�v���b�h */
	public TTable tbl;

	/** �s�ԍ� */
	public int rowIndex;

	/** ��ԍ� */
	public int columnIndex;

	/** �^�C�v */
	public int[] types;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param editor �t�B�[���h
	 * @param tbl �X�v���b�h
	 * @param rowIndex �s�ԍ�
	 * @param columnIndex ��ԍ�
	 * @param types �^�C�v�z��(�\��t���񁨉E�S��)
	 */
	public TTableKeyAdapter(TTextField editor, TTable tbl, int rowIndex, int columnIndex, int[] types) {
		this.editor = editor;
		this.tbl = tbl;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.types = types;
	}

	@Override
	public void keyPressed(KeyEvent ev) {

		// CTRL+V�ASHIFT+INSERT�œ\��t���@�\�Ή�
		if (rowIndex != -1
			&& ((KeyEvent.VK_V == ev.getKeyCode() && ev.isControlDown()) || (KeyEvent.VK_INSERT == ev.getKeyCode() && ev
				.isShiftDown()))) {

			String[] arr = TTablePasteUtil.getArray(TTablePasteUtil.getClipboardText(), "\n");
			if (arr == null || arr.length == 0 || types == null || types.length == 0) {
				return;
			}

			// �P�s�ڂ���\��t����
			int row = rowIndex;

			// �P�s�ڂ̕ҏW���[�h���~�߂�
			tbl.editCellAt(row, columnIndex);
			if (tbl.getCellEditor(row, columnIndex) != null) {
				tbl.getCellEditor(row, columnIndex).stopCellEditing();
			}

			// �\��t����
			for (int i = 0; i < arr.length; i++) {
				String line = arr[i];
				String[] cols = TTablePasteUtil.getArray(line, "\t");

				Object obj = null;

				// �s�P�ʂœ\��t����
				for (int j = 0; j < Math.min(types.length, cols.length); j++) {
					int col = columnIndex + j;

					// ��A�E�g�o�E���h����
					if (col >= tbl.getColumnModel().getColumnCount()) {
						// �񁄍ő��A�������Ȃ�
						break;
					}

					switch (types[j]) {
						case TTablePasteUtil.NUMBER:
							obj = TTablePasteUtil.getNumber(cols[j]);
							break;
						case TTablePasteUtil.DATE:
							obj = TTablePasteUtil.getDate(cols[j]);
							break;
						case TTablePasteUtil.STRING:
							obj = cols[j];
							break;
						default:
							// �������Ȃ�
							continue;
					}

					// �\��t��
					tbl.setRowValueAt(obj, row, col);

					// �V�����ݒ�l�ɂ��v�Z�������s��
					tbl.editCellAt(row, col);
					if (tbl.getCellEditor(row, col) != null) {
						tbl.getCellEditor(row, col).stopCellEditing();
					}
				}

				row++;
				if (row >= tbl.getRowCount()) {
					break;
				}
			}

		}
	}

}
