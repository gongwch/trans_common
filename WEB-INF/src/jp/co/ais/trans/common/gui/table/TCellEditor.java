package jp.co.ais.trans.common.gui.table;

import java.awt.event.*;

import javax.swing.*;

import com.klg.jclass.cell.*;
import com.klg.jclass.cell.editors.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable用 セルEditor
 */
public class TCellEditor extends JCStringCellEditor {

	/** 対象テーブル */
	protected TTable table;

	/**
	 * コンストラクタ.
	 * 
	 * @param table 対象テーブル
	 */
	public TCellEditor(final TTable table) {
		this(table, 128); // 指定なしは128バイト
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param table 対象テーブル
	 * @param maxLength 最大入力文字バイト数
	 */
	public TCellEditor(final TTable table, int maxLength) {
		this.table = table;

		// スプッド桁数設定
		setDocument(new TStringPlainDocument(this, maxLength));

		// ベースクラスのJCCellEditorSupportを変更
		initSupport();

		// ロストフォーカスコミット
		setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {

				TCellEditor.this.verifyCommit();

				return true;
			}
		});
	}

	/**
	 * ロストフォーカス時処理
	 */
	protected void verifyCommit() {
		table.commitEdit(true);
	}

	/**
	 * ベースクラスのJCCellEditorSupportを変更
	 */
	protected void initSupport() {

		support = new JCCellEditorSupport() {

			/**
			 * ESCAPEエラー回避
			 */
			@Override
			public void fireCancelEditing(JCCellEditorEvent cevt) {
				try {
					super.fireCancelEditing(cevt);

				} catch (Exception ex) {
					// ESCAPEエラー回避
				}
			}
		};
	}

	/**
	 * FocusGained処理
	 * 
	 * @param e イベント
	 */
	@Override
	public void focusGained(FocusEvent e) {
		super.focusGained(e);
	}

	/**
	 * @see com.klg.jclass.cell.editors.BaseCellEditor#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);

		if (isVisible) {
			selectAll();
		}
	}

	/**
	 * 全角入力を許すかどうか
	 * 
	 * @param ime true:可(デフォルト)、false:不可
	 */
	public void setImeMode(boolean ime) {
		enableInputMethods(ime);

		((TStringPlainDocument) getDocument()).setImeMode(ime);
	}

	/**
	 * ESCキー処理無効
	 */
	@Override
	public void keyPressed(KeyEvent ev) {
		if (ev.getKeyCode() == KeyEvent.VK_ESCAPE) {
			ev.consume();
			return;
		}

		super.keyPressed(ev);
	}
}
