package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;

/**
 * �C�ӑI��(�ʎw��)�R���|�[�l���g�̃R���g���[�����N���X
 * 
 * @author AIS
 */
public abstract class TOptionalSelectorController extends TController {

	/** �C�ӑI��(�ʎw��)�R���|�[�l���g */
	protected TOptionalSelector field;

	/** �ʑI���_�C�A���O */
	protected TOptionalSelectorDialog dialog = null;

	/** �_�C�A���O���J���ꂽ���̃f�[�^(�߂�{�^�����̑ޔ�p) */
	protected Vector dataWhenOpened = null;

	/** �_�C�A���O���J���ꂽ���̃f�[�^(�߂�{�^�����̑ޔ�p) */
	protected Vector dataSelectedWhenOpened = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �C�ӑI���R���|�[�l���g
	 */
	public TOptionalSelectorController(TOptionalSelector field) {

		this.field = field;

		// ������
		init();

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * ��������
	 */
	protected void init() {

		// �C�x���g��ǉ�����
		addEvent();

		field.cbo.setEnabled(false);
	}

	/** �����e�L�X�g���X�i */
	protected KeyListener txtListener = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();

				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				loadListData();
				filterListData();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	};

	/**
	 * �C�x���g��`
	 */
	protected void addEvent() {

		// �ʑI���{�^������
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btn_Click();
			}
		});

	}

	/**
	 * �_�C�A���O�̏�������
	 */
	protected void initDialog() {

		// �C�x���g��`
		addDialogEvent();

		// �_�C�A���O�̃^�C�g��
		dialog.setTitle(getWord(getDialogCaption()));

		// �I���ꗗ������
		initTable();
		refresh();
		saveListData();
		
	}

	/**
	 * �ʑI���_�C�A���O�̃C�x���g��`
	 */
	protected void addDialogEvent() {
		// �e����(�e�L�X�g)
		dialog.txtSearchCode.addKeyListener(txtListener);
		dialog.txtSearchName.addKeyListener(txtListener);

		// �ꗗ����I���{�^������
		dialog.btnTableSelect.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnTableSelect_Click();
			}
		});

		// �ꗗ����I��������{�^������
		dialog.btnTableCancel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnTableCancel_Click();
			}
		});

		// �m��{�^������
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSettle_Click(e);
			}
		});

		// �߂�{�^������
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnBack_Click();
			}
		});

	}

	/**
	 * �ʑI���{�^������
	 */
	protected void btn_Click() {

		// �ʑI���_�C�A���O�擾
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}

		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		loadItemLevel();
		loadListData();
		filterListData();

		// �\��
		dialog.setVisible(true);

	}

	/**
	 * [�ꗗ����I��]�{�^������
	 */
	protected abstract void btnTableSelect_Click();

	/**
	 * [�ꗗ����I�������]�{�^������
	 */
	protected abstract void btnTableCancel_Click();

	/**
	 * [�m��]�{�^������
	 * 
	 * @param e
	 */
	protected void btnSettle_Click(@SuppressWarnings("unused") ActionEvent e) {

		// 1000���ȏ�͕s��
		if (dialog.tblSelectedList.getRowCount() > 1000) {
			super.showMessage(dialog, "I00438"); // �C�ӑI����1000���ȉ��ɂ��Ă��������B
			return;
		}

		btnSettle_Click();
		saveSelectedListData();

	}

	/**
	 * [�m��]�{�^������
	 */
	protected abstract void btnSettle_Click();

	/**
	 * [�߂�]�{�^������
	 */
	protected void btnBack_Click() {

		dialog.setVisible(false);

	}

	/**
	 * selector��dialog�̃t�@�N�g��
	 * 
	 * @return selector��dialog
	 */
	protected abstract TOptionalSelectorDialog getSelectorDialog();

	/**
	 * �����_�C�A���O�̃L���v�V������Ԃ�
	 * 
	 * @return �����_�C�A���O�̃L���v�V����
	 */
	protected abstract String getDialogCaption();

	/**
	 * �e�[�u���̏�����
	 */
	protected abstract void initTable();

	/**
	 * �I�����e�[�u���̈ꗗ�����t���b�V��(�Ď擾)
	 */
	public abstract void refresh();

	/**
	 * @return �_�C�A���O�擾
	 */
	public TOptionalSelectorDialog getDialog() {
		return dialog;
	}

	/**
	 * @param dialog
	 */
	public void setDialog(TOptionalSelectorDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * �I�����ꂽ�R�[�h���X�g��Ԃ�
	 * 
	 * @return �I�����ꂽ�R�[�h���X�g
	 */
	public abstract List<String> getCodeList();

	/**
	 * �N���A����
	 */
	public void clear() {
		dataWhenOpened = null;
		dataSelectedWhenOpened = null;
		field.cbo.removeAllItems();
		field.cbo.setEnabled(false);
		if (dialog != null) {
			dialog.tblList.removeRow();
			dialog.tblSelectedList.removeRow();
			refresh();
		}
	}

	/**
	 * ���X�g�̍i����
	 */
	public void filterListData() {

		int codeCheck = 0;
		int namesCheck = 0;
		int codeCol = 0;
		int namesCol = 1;

		// �i����
		for (int i = dialog.tblList.getRowCount() - 1; i >= 0; i--) {
			if (!Util.isNullOrEmpty(dialog.txtSearchCode.getText())) {
				codeCheck =
					dialog.tblList.getRowValueAt(
						i, codeCol).toString().indexOf(dialog.txtSearchCode.getText());
			}
			if (!Util.isNullOrEmpty(dialog.txtSearchName.getText())) {
				namesCheck = 
					dialog.tblList.getRowValueAt(
						i, namesCol).toString().indexOf(dialog.txtSearchName.getText());
			}
			
			if (codeCheck != 0 || namesCheck == -1) {
				// �i���ݑΏۊO�Ȃ烊�X�g����폜
				dialog.tblList.removeRow(i);

			} else {
				// �i���ݑΏۂł��I���ς݂Ȃ�폜
				for (int j = 0; j < dialog.tblSelectedList.getRowCount(); j++) {
					if (dialog.tblList.getRowValueAt(i, codeCol).equals(
						dialog.tblSelectedList.getRowValueAt(j, codeCol))) {
						// ���X�g����폜
						dialog.tblList.removeRow(i);

						break;
					}
				}
			}
		}
	}

	/**
	 * �������X�g�̑ޔ�
	 */
	public void saveListData() {

		// �f�[�^��ޔ�
		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
		dataWhenOpened = (Vector) tableModel.getDataVector().clone();

		if (dialog.tblList.getRowCount() > 0) {
			dialog.tblList.setRowSelectionInterval(0, 0);
		}

	}

	/**
	 * �I�����X�g�̑ޔ�
	 */
	public void saveSelectedListData() {

		// �f�[�^��ޔ�
		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		dataSelectedWhenOpened = (Vector) tableSelectedModel.getDataVector().clone();

		if (dialog.tblSelectedList.getRowCount() > 0) {
			dialog.tblSelectedList.setRowSelectionInterval(0, 0);
		}

	}

	/**
	 * �Ȗڃ��x���̖߂�
	 */
	public void loadItemLevel() {
		return;
	}
	
	/**
	 * �������X�g�̖߂�
	 */
	public void loadListData() {

		// �ޔ�����߂�
		dialog.tblList.removeRow();
		dialog.tblSelectedList.removeRow();

		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
		if (dataWhenOpened != null) {
			for (int i = 0; i < dataWhenOpened.size(); i++) {
				tableModel.addRow((Vector) dataWhenOpened.get(i));
			}
		}

		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		if (dataSelectedWhenOpened != null) {
			for (int i = 0; i < dataSelectedWhenOpened.size(); i++) {
				tableSelectedModel.addRow((Vector) dataSelectedWhenOpened.get(i));
			}
		}

	}

	/**
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		// 
		
	}

}