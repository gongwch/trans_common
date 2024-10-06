package jp.co.ais.trans2.common.gui.ac;

import javax.swing.text.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * ���̂܂ŏ���
 */
public class AutoCompletableCommitAdapter extends DefaultCommitAdapter {

	/** �Ώ�TTable */
	public TTable tbl;

	/** �Ώ�Column */
	public Enum col;

	/** �R�[�h�\�� */
	public boolean showCode;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param textComponent
	 */
	public AutoCompletableCommitAdapter(JTextComponent textComponent) {
		super(textComponent);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param textComponent
	 * @param tbl
	 * @param col
	 */
	public AutoCompletableCommitAdapter(JTextComponent textComponent, TTable tbl, Enum col) {
		this(textComponent, tbl, col, false);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param textComponent
	 * @param tbl
	 * @param col
	 * @param showCode true:�R�[�h�\���Afalse:���̕\��
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
	 * �l���N���A
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
	 * �l���N���A
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
