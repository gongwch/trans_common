package jp.co.ais.trans2.common.gui;

import java.awt.event.*;
import java.util.*;

/**
 * JTable�Ƃ�Adapter
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public class TTableAdapter {

	/** ���e�[�u�� */
	protected BaseTable table;

	/**
	 * �e�[�u���擾
	 * 
	 * @return �e�[�u��
	 */
	public BaseTable getTable() {
		return table;
	}

	/**
	 * �e�[�u���ݒ�
	 * 
	 * @param table �e�[�u��
	 */
	public void setTable(BaseTable table) {
		this.table = table;
		this.table.adapter = this;
	}

	/**
	 * �w��Z���̃G�f�B�b�g��<br>
	 * <br>
	 * <b>�����Ӂ�������������������������������������������<br>
	 * ��override����convertXXXXIndexToModel��Y�ꂸ<br>
	 * ��������������������������������������������������</b>
	 * 
	 * @param row �s
	 * @param column ��
	 * @return true:��
	 */
	public boolean isCellEditable(int row, int column) {
		return table.getModel().isCellEditable(table.convertRowIndexToModel(row),
			table.convertColumnIndexToModel(column));
	}

	/**
	 * �w��l�̕ύX�m�F
	 * 
	 * @param before �ύX�O�̒l
	 * @param after �ύX��̒l
	 * @param row �s
	 * @param column ��
	 * @return true:�ύX����
	 */
	public boolean isValueChanged(Object before, Object after, int row, int column) {

		if ((before != null && after == null) || (before == null && after != null)) {
			return true;
		}

		return !before.toString().equals(after.toString());
	}

	/**
	 * �l���ύX���ꂽ�ꍇ�ɌĂяo�����
	 * 
	 * @param before �ύX�O�̒l
	 * @param after �ύX��̒l
	 * @param row �s
	 * @param column ��
	 */
	public void changedValueAt(Object before, Object after, int row, int column) {
		// Override�p
	}

	/**
	 * �w�b�_�[���N���b�N�O
	 * 
	 * @param column ��
	 */
	public void beforeHeaderClicked(int column) {
		// Override�p
	}

	/**
	 * �w�b�_�[���N���b�N��
	 * 
	 * @param column ��
	 */
	public void afterHeaderClicked(int column) {
		// Override�p
	}

	/**
	 * �s�ԍ����N���b�N��
	 */
	public void afterRowHeaderClicked() {
		// Override�p
	}

	/**
	 * �s�I����ԕύX
	 */
	public void rowSelectionChanged() {
		// Override�p
	}

	/**
	 * ����ۑ�������
	 */
	public void fireTableColumnChanged() {
		// Override�p
	}

	/**
	 * MTable�̃t�b�^�[���W�ύX����
	 */
	public void fireMTableFooterChanged() {
		// Override�p
	}

	/**
	 * �Z���ҏW�O�̏���
	 * 
	 * @param row
	 * @param column
	 * @return true:�ҏW�������s���Afalse:�������f
	 */
	public boolean beforeEditCellAt(int row, int column) {
		return true;
	}

	/**
	 * �Z���ҏW�O�̏���
	 * 
	 * @param row
	 * @param column
	 * @param e
	 * @return true:�ҏW�������s���Afalse:�������f
	 */
	public boolean beforeEditCellAt(int row, int column, EventObject e) {
		return true;
	}

	/**
	 * @param event
	 * @return Tool tips
	 */
	public String getToolTipText(MouseEvent event) {
		return null;
	}

	/**
	 * �w�b�_�[Tooltip�擾
	 * 
	 * @param event
	 * @return Tool tips
	 */
	public String getHeaderToolTipText(MouseEvent event) {
		return null;
	}
}
