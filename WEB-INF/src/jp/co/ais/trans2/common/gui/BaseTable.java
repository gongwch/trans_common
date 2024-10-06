package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;

/**
 * Table�����N���X
 */
public class BaseTable extends JTable implements TInterfaceTabControl {

	/** �^�u�� */
	protected int tabControlNo = -1;

	/** �^�u�J�ډ� */
	protected boolean isTabEnabled = true;

	/** �sIndex */
	protected int editRowIndex = 0;

	/** ��Index */
	protected int editColumnIndex = 0;

	/** �s�w�b�_�[�́u*�v�\���s�ԍ� */
	protected int rowHeaderStarIndex = -1;

	/** �s�^�C�g�����X�g */
	protected List<String> rowHeaderMessage = null;

	/** �s�w�i�F���X�g */
	protected List<Color> rowHeaderBackground = null;

	/** �s�����F���X�g */
	protected List<Color> rowHeaderForeground = null;

	/**  */
	protected TTableAdapter adapter;

	/**
	 * �R���X�g���N�^�[
	 */
	public BaseTable() {
		//
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param model
	 */
	public BaseTable(TableModel model) {
		super(model);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param adapter
	 */
	public BaseTable(TTableAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * @see javax.swing.JTable#createDefaultTableHeader()
	 */
	@Override
	protected JTableHeader createDefaultTableHeader() {
		return createTableHeader();
	}

	/**
	 * @return �e�[�u���w�b�_�[�\��
	 */
	protected JTableHeader createTableHeader() {
		return new TTableHeader(columnModel, this);
	}

	/**
	 * �l�̕ύX���A�_�v�^�[�ɒʒm����d�g�݂�ǉ�
	 * 
	 * @see javax.swing.JTable#createDefaultDataModel()
	 */
	@Override
	protected TableModel createDefaultDataModel() {

		return new DefaultTableModel() {

			@Override
			public void setValueAt(Object after, int row, int column) {
				Object before = getValueAt(row, column);

				super.setValueAt(after, row, column);

				// �ύX�ʒm
				if ((before != null || after != null) && adapter.isValueChanged(before, after, row, column)) {
					adapter.changedValueAt(before, after, row, column);
				}
			}
		};
	}

	/**
	 * �ړ���A���ҏW
	 * 
	 * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
	 */
	@Override
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
		if (!super.isEditing()) {
			editCellAt(rowIndex, columnIndex, new EventObject(this));
		}

		super.changeSelection(rowIndex, columnIndex, toggle, extend);
	}

	/**
	 * ��G�f�B�b�g��Ԃ̓��͂�ENTER�Ŋm�肳����
	 * 
	 * @see javax.swing.JTable#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
	 */
	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		boolean retValue = super.processKeyBinding(ks, e, condition, pressed);

		if (KeyStroke.getKeyStroke('\t').equals(ks) || KeyStroke.getKeyStroke('\n').equals(ks)) {
			return retValue;
		}

		if (getInputContext().isCompositionEnabled() && !isEditing() && !pressed && !ks.isOnKeyRelease()) {
			int selectedRow = getSelectedRow();
			int selectedColumn = getSelectedColumn();
			if (selectedRow != -1 && selectedColumn != -1 && !editCellAt(selectedRow, selectedColumn)) {
				return retValue;
			}
		}

		return retValue;
	}

	/**
	 * @see javax.swing.JTable#updateUI()
	 */
	@Override
	public void updateUI() {
		int oldRowHeight = rowHeight;
		super.updateUI();
		this.setRowHeight(oldRowHeight);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * 
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return adapter.isCellEditable(row, column);
	}

	/**
	 * editColumnIndex���擾����B
	 * 
	 * @return int editColumnIndex
	 */
	public int getEditColumnIndex() {
		return editColumnIndex;
	}

	/**
	 * editRowIndex���擾����B
	 * 
	 * @return int editRowIndex
	 */
	public int getEditRowIndex() {
		return editRowIndex;
	}

	/**
	 * �s�w�b�_�[�́u*�v�\���s�ԍ��̎擾
	 * 
	 * @return rowHeaderStarIndex �s�w�b�_�[�́u*�v�\���s�ԍ�
	 */
	public int getRowHeaderStarIndex() {
		return rowHeaderStarIndex;
	}

	/**
	 * �s�w�b�_�[�́u*�v�\���s�ԍ��̐ݒ�
	 * 
	 * @param rowHeaderStarIndex �s�w�b�_�[�́u*�v�\���s�ԍ�
	 */
	public void setRowHeaderStarIndex(int rowHeaderStarIndex) {
		this.rowHeaderStarIndex = rowHeaderStarIndex;
	}

	/**
	 * �s�^�C�g�����X�g�̎擾
	 * 
	 * @return rowHeaderMessage �s�^�C�g�����X�g
	 */
	public List<String> getRowHeaderMessage() {
		return rowHeaderMessage;
	}

	/**
	 * �s�^�C�g�����X�g�̐ݒ�
	 * 
	 * @param rowHeaderMessage �s�^�C�g�����X�g
	 */
	public void setRowHeaderMessage(List<String> rowHeaderMessage) {
		this.rowHeaderMessage = rowHeaderMessage;
	}

	/**
	 * �w�i�F���X�g�̎擾
	 * 
	 * @return rowHeaderBackground �w�i�F���X�g
	 */
	public List<Color> getRowHeaderBackground() {
		return rowHeaderBackground;
	}

	/**
	 * �w�i�F���X�g�̐ݒ�
	 * 
	 * @param rowHeaderBackground �w�i�F���X�g
	 */
	public void setRowHeaderBackground(List<Color> rowHeaderBackground) {
		this.rowHeaderBackground = rowHeaderBackground;
	}

	/**
	 * �����F���X�g�̎擾
	 * 
	 * @return rowHeaderMessage �����F���X�g
	 */
	public List<Color> getRowHeaderForeground() {
		return rowHeaderForeground;
	}

	/**
	 * �����F���X�g�̐ݒ�
	 * 
	 * @param rowHeaderForeground �����F���X�g
	 */
	public void setRowHeaderForeground(List<Color> rowHeaderForeground) {
		this.rowHeaderForeground = rowHeaderForeground;
	}

	/**
	 * @see javax.swing.JComponent#processMouseEvent(java.awt.event.MouseEvent)
	 */
	@Override
	protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);
	}

	@Override
	public boolean editCellAt(int row, int column, EventObject e) {

		if (!adapter.beforeEditCellAt(row, column)) {
			// �������f����
			return false;
		}

		if (!adapter.beforeEditCellAt(row, column, e)) {
			// �������f����
			return false;
		}

		return super.editCellAt(row, column, e);
	}

	@Override
	public String getToolTipText(MouseEvent ev) {
		if (adapter == null) {
			return super.getToolTipText(ev);
		}

		return adapter.getToolTipText(ev);
	}
}
