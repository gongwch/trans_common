package jp.co.ais.trans2.common.gui.table;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * ��ՃZ���G�f�B�^
 */
public class TBaseCellEditor extends DefaultCellEditor {

	/**  */
	protected TTable tbl;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param tbl
	 */
	public TBaseCellEditor(JCheckBox editor, TTable tbl) {
		super(editor);
		this.tbl = tbl;

		init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param tbl
	 */
	public TBaseCellEditor(JComboBox editor, TTable tbl) {
		super(editor);
		this.tbl = tbl;

		init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param editor
	 * @param tbl
	 */
	public TBaseCellEditor(JTextField editor, TTable tbl) {
		super(editor);
		this.tbl = tbl;

		init();
	}

	/**
	 * ����������
	 */
	protected void init() {

		setClickCountToStart(1);

		editorComponent.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				onKeyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				onKeyPressed(e);
			}
		});
	}

	/**
	 * onKeyReleased
	 * 
	 * @param e
	 */
	protected void onKeyReleased(KeyEvent e) {
		if (!editorComponent.isFocusable() || !editorComponent.isFocusOwner()) {
			return;
		}

		switch (e.getKeyCode()) {
			case KeyEvent.VK_TAB:
				stopCellEditing();
				tbl.table.dispatchEvent(e);
				break;
		}
	}

	/**
	 * onKeyPressed
	 * 
	 * @param e
	 */
	protected void onKeyPressed(KeyEvent e) {
		if (!editorComponent.isFocusable() || !editorComponent.isFocusOwner()) {
			return;
		}

		JTextField editor = (editorComponent instanceof JTextField) ? (JTextField) editorComponent : null;

		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:

				if (!tbl.isTabTraverseCell()) {
					// �S��ʈړ��̏ꍇ�̂ݗL��
					tbl.table.dispatchEvent(e);
				}
				break;

			// ���t������ł�ꍇ�̐��䂪���������̂ŕ���
			case KeyEvent.VK_LEFT:
				if (editor != null && editor.getCaretPosition() == 0) {
					stopCellEditing();
					tbl.table.dispatchEvent(e);
				}

				break;

			case KeyEvent.VK_RIGHT:
				if (editor != null && editor.getCaretPosition() == editor.getText().length()) {
					stopCellEditing();
					tbl.table.dispatchEvent(e);
				}

				break;
		}
	}

	/**
	 * �ΏۃZ�����ҏW�\���ǂ���
	 * 
	 * @param row
	 * @param column
	 * @return true �ҏW��
	 */
	@SuppressWarnings("unused")
	public boolean isCellEditable(int row, int column) {
		return true;

	}

	/**
	 * �Z��Editor�̕ҏW��/�s��Ԃ�
	 * 
	 * @param e �C�x���g�I�u�W�F�N�g
	 * @return true
	 */
	@Override
	public boolean isCellEditable(EventObject e) {
		return true;
	}

}