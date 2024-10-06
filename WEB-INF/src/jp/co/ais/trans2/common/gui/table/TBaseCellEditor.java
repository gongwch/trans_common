package jp.co.ais.trans2.common.gui.table;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 基盤セルエディタ
 */
public class TBaseCellEditor extends DefaultCellEditor {

	/**  */
	protected TTable tbl;

	/**
	 * コンストラクタ
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
	 * コンストラクタ
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
	 * コンストラクタ
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
	 * 初期化処理
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
					// 全画面移動の場合のみ有効
					tbl.table.dispatchEvent(e);
				}
				break;

			// 日付が並んでる場合の制御がおかしいので封印
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
	 * 対象セルが編集可能かどうか
	 * 
	 * @param row
	 * @param column
	 * @return true 編集可
	 */
	@SuppressWarnings("unused")
	public boolean isCellEditable(int row, int column) {
		return true;

	}

	/**
	 * セルEditorの編集可/不可を返す
	 * 
	 * @param e イベントオブジェクト
	 * @return true
	 */
	@Override
	public boolean isCellEditable(EventObject e) {
		return true;
	}

}