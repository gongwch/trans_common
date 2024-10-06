package jp.co.ais.trans.common.gui.table;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TTable�p ���z�Z��Editor
 */
public class TAmountCellEditor extends TCellEditor {

	/** �����_���� */
	private int digit;

	/**
	 * �R���X�g���N�^.<br>
	 * �ő���͐�17�A�����_����0�A�}�C�i�X��
	 * 
	 * @param table �Ώۃe�[�u��
	 */
	public TAmountCellEditor(final TTable table) {
		this(table, 17);
	}

	/**
	 * �R���X�g���N�^.<br>
	 * �����_����0�A�}�C�i�X��
	 * 
	 * @param table �Ώۃe�[�u��
	 * @param maxLength �ő���͐�
	 */
	public TAmountCellEditor(final TTable table, int maxLength) {
		this(table, maxLength, 0);
	}

	/**
	 * �R���X�g���N�^.<br>
	 * �}�C�i�X��
	 * 
	 * @param table �Ώۃe�[�u��
	 * @param maxLength �ő���͐�
	 * @param digit �����_����
	 */
	public TAmountCellEditor(final TTable table, int maxLength, int digit) {
		this(table, maxLength, digit, false);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �Ώۃe�[�u��
	 * @param maxLength �ő���͐�
	 * @param digit �����_����
	 * @param isPositive true:�}�C�i�X�s��
	 */
	public TAmountCellEditor(final TTable table, int maxLength, int digit, boolean isPositive) {
		super(table, maxLength);

		this.digit = digit;

		setHorizontalAlignment(CellStyleModel.RIGHT);

		// �X�v�b�h���l���̓`�F�b�N ����12��
		setDocument(new TNumericPlainDocument(this, maxLength, digit, isPositive));
	}

	/**
	 * @see com.klg.jclass.cell.editors.BaseCellEditor#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);

		if (isVisible) {
			setCellEditorText(getCellEditorText().replace(",", ""));
			selectAll();
		}
	}

	/**
	 * ���X�g�t�H�[�J�X������
	 */
	@Override
	protected void verifyCommit() {

		String txt = getCellEditorText();

		if (!Util.isNumber(txt)) {
			setCellEditorText("");
		}

		super.verifyCommit();
	}

	/**
	 * �����_�����̐ݒ�
	 * 
	 * @param digit �����_����
	 */
	public void setDecimalPoint(int digit) {
		this.digit = digit;

		TNumericPlainDocument doc = (TNumericPlainDocument) getDocument();
		doc.setDecimalPoint(digit);
	}

	/**
	 * �����_�����̎擾
	 * 
	 * @return �����_����
	 */
	public int getDecimalPoint() {
		return this.digit;
	}

	/**
	 * �S�p���͖͂���(�Z�b�g����Ă�����)
	 * 
	 * @param ime �w��Ӗ��Ȃ�
	 */
	@Override
	public void setImeMode(boolean ime) {
		enableInputMethods(false);
	}
}
