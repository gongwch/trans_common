package jp.co.ais.trans2.common.gui.ac;

import javax.swing.text.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 名称まで処理
 */
public class AutoCompletableCommitAdapter extends DefaultCommitAdapter {

	/** 対象TTable */
	public TTable tbl;

	/** 対象Column */
	public Enum col;

	/** コード表示 */
	public boolean showCode;

	/**
	 * コンストラクター
	 * 
	 * @param textComponent
	 */
	public AutoCompletableCommitAdapter(JTextComponent textComponent) {
		super(textComponent);
	}

	/**
	 * コンストラクター
	 * 
	 * @param textComponent
	 * @param tbl
	 * @param col
	 */
	public AutoCompletableCommitAdapter(JTextComponent textComponent, TTable tbl, Enum col) {
		this(textComponent, tbl, col, false);
	}

	/**
	 * コンストラクター
	 * 
	 * @param textComponent
	 * @param tbl
	 * @param col
	 * @param showCode true:コード表示、false:名称表示
	 */
	public AutoCompletableCommitAdapter(JTextComponent textComponent, TTable tbl, Enum col, boolean showCode) {
		super(textComponent);

		this.tbl = tbl;
		this.col = col;
		this.showCode = showCode;
	}

	@Override
	public void commit(Object value) {
		if (value != null) {
			if (value instanceof AutoCompletable) {
				commitSuccess((AutoCompletable) value);
			} else {
				commitText(value.toString());
			}
		} else {
			commitBlank();
		}
	}

	/**
	 * 値をクリア
	 */
	public void commitBlank() {
		if (tbl != null) {
			int row = tbl.getSelectedRow();
			if (textComponent instanceof TTableComponent) {
				row = ((TTableComponent) textComponent).getRowIndex();
			}
			tbl.setRowValueAt("", row, col.ordinal());
		} else {
			textComponent.setText("");
		}
	}

	/**
	 * 値をクリア
	 * 
	 * @param txt
	 */
	public void commitText(String txt) {
		if (tbl != null) {
			int row = tbl.getSelectedRow();
			if (textComponent instanceof TTableComponent) {
				row = ((TTableComponent) textComponent).getRowIndex();
			}
			tbl.stopCellEditing();
			tbl.setRowValueAt(txt, row, col.ordinal());
		} else {
			textComponent.setText(txt);
		}
	}

	/**
	 * @param value
	 */
	public void commitSuccess(AutoCompletable value) {
		String txt = showCode ? value.getCode() : value.getName();

		if (tbl != null) {
			int row = tbl.getSelectedRow();
			if (textComponent instanceof TTableComponent) {
				row = ((TTableComponent) textComponent).getRowIndex();
			}
			tbl.stopCellEditing();
			tbl.setRowValueAt(txt, row, col.ordinal());
		} else {
			textComponent.setText(txt);
		}
	}

	@Override
	public String getText(Object value) {
		if (value != null) {
			if (value instanceof AutoCompletable) {
				AutoCompletable bean = (AutoCompletable) value;
				return bean.getName();
			} else {
				return value.toString();
			}
		} else {
			return "";
		}
	}

	@Override
	public boolean isDoPush() {
		return true;
	}
}
