package jp.co.ais.trans.common.gui.table;

import java.awt.event.*;

import javax.swing.*;

import com.klg.jclass.cell.*;
import com.klg.jclass.cell.editors.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable�p �Z��Editor
 */
public class TCellEditor extends JCStringCellEditor {

	/** �Ώۃe�[�u�� */
	protected TTable table;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �Ώۃe�[�u��
	 */
	public TCellEditor(final TTable table) {
		this(table, 128); // �w��Ȃ���128�o�C�g
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �Ώۃe�[�u��
	 * @param maxLength �ő���͕����o�C�g��
	 */
	public TCellEditor(final TTable table, int maxLength) {
		this.table = table;

		// �X�v�b�h�����ݒ�
		setDocument(new TStringPlainDocument(this, maxLength));

		// �x�[�X�N���X��JCCellEditorSupport��ύX
		initSupport();

		// ���X�g�t�H�[�J�X�R�~�b�g
		setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {

				TCellEditor.this.verifyCommit();

				return true;
			}
		});
	}

	/**
	 * ���X�g�t�H�[�J�X������
	 */
	protected void verifyCommit() {
		table.commitEdit(true);
	}

	/**
	 * �x�[�X�N���X��JCCellEditorSupport��ύX
	 */
	protected void initSupport() {

		support = new JCCellEditorSupport() {

			/**
			 * ESCAPE�G���[���
			 */
			@Override
			public void fireCancelEditing(JCCellEditorEvent cevt) {
				try {
					super.fireCancelEditing(cevt);

				} catch (Exception ex) {
					// ESCAPE�G���[���
				}
			}
		};
	}

	/**
	 * FocusGained����
	 * 
	 * @param e �C�x���g
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
	 * �S�p���͂��������ǂ���
	 * 
	 * @param ime true:��(�f�t�H���g)�Afalse:�s��
	 */
	public void setImeMode(boolean ime) {
		enableInputMethods(ime);

		((TStringPlainDocument) getDocument()).setImeMode(ime);
	}

	/**
	 * ESC�L�[��������
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
