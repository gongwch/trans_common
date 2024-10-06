package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * ��s�}�X�^��ʃR���g���[��
 * 
 * @author ookawara
 */
public class MG0140BankMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0140";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0140BankMasterServlet";

	/** �V�K�ƕ��ʋ敪 */
	private static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	private static int UPD_KBN = 1;

	/** �p�l�� */
	protected MG0140BankMasterPanel panel;

	/** ��s Enter������FLAG */
	boolean inputFlag = false;

	protected REFDialogCtrl refBank;

	protected REFDialogCtrl refBeginBankBranch;

	protected REFDialogCtrl refEndBankBranch;

	/** ��s�}�X�^�_�C�A���O */
	MG0140EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0140BankMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0140BankMasterPanel(this);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		// �J�n�R�[�h�ƏI���R�[�h�̏�����
		panel.ctrlBeginBankBranch.getField().setEditable(false);
		panel.ctrlEndBankBranch.getField().setEditable(false);
		panel.ctrlBeginBankBranch.getButton().setEnabled(false);
		panel.ctrlEndBankBranch.getButton().setEnabled(false);

		// ��ʏ�����
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});

	}

	void init() {
		// ��s�R�[�h�̐ݒ�

		refBank = new REFDialogCtrl(panel.ctrlBank, panel.getParentFrame());
		refBank.setTargetServlet(TARGET_SERVLET);
		refBank.setTitleID("C02323");
		refBank.setColumnLabels("C00779", "C00781", "C00829");
		refBank.setShowsMsgOnError(false);
		refBank.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Bank");
				return keys;
			}

			public void goodCodeInputted() {
				panel.ctrlBeginBankBranch.getField().setEditableEnabled(true);
				panel.ctrlEndBankBranch.getField().setEditableEnabled(true);
				panel.ctrlBeginBankBranch.getButton().setEnabled(true);
				panel.ctrlEndBankBranch.getButton().setEnabled(true);

			}

			public void badCodeInputted() {
				panel.ctrlBeginBankBranch.getField().setText(null);
				panel.ctrlBeginBankBranch.getField().setEditableEnabled(false);
				panel.ctrlBeginBankBranch.getNotice().setEditable(true);
				panel.ctrlBeginBankBranch.getNotice().setText(null);
				panel.ctrlBeginBankBranch.getNotice().setEditable(false);
				panel.ctrlBeginBankBranch.getButton().setEnabled(false);

				panel.ctrlEndBankBranch.getField().setText(null);
				panel.ctrlEndBankBranch.getField().setEditableEnabled(false);
				panel.ctrlEndBankBranch.getNotice().setEditable(true);
				panel.ctrlEndBankBranch.getNotice().setText(null);
				panel.ctrlEndBankBranch.getNotice().setEditable(false);
				panel.ctrlEndBankBranch.getButton().setEnabled(false);
			}

		});

		// ��s Enter�L������
		panel.ctrlBank.getField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_ENTER && inputFlag == false) {
					refBank.refreshName();
					if (panel.ctrlBank.isValueChanged()) {
						if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
							&& !Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
							panel.ctrlBeginBankBranch.getField().requestFocus();
							panel.ctrlBeginBankBranch.getField().setText(null);
							panel.ctrlBeginBankBranch.getNotice().setText(null);
							panel.ctrlEndBankBranch.getField().setText(null);
							panel.ctrlEndBankBranch.getNotice().setText(null);
						}
					}

					if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
						&& Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
						showMessage(panel, "W00081", panel.ctrlBank.getValue());
						panel.ctrlBank.getField().clearOldText();
						panel.ctrlBank.getField().requestFocus();
					}
				}
				inputFlag = false;
			}
		});

		panel.ctrlBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBank.refreshName();
				if (panel.ctrlBank.isValueChanged()) {
					if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
						&& !Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
						panel.ctrlBeginBankBranch.getField().setText(null);
						panel.ctrlBeginBankBranch.getNotice().setText(null);

						panel.ctrlEndBankBranch.getField().setText(null);
						panel.ctrlEndBankBranch.getNotice().setText(null);
					}
				}

				if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
					&& Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
					showMessage(panel, "W00081", panel.ctrlBank.getValue());
					panel.ctrlBank.getField().clearOldText();
					panel.ctrlBank.getField().requestFocus();
					inputFlag = true;
					return false;

				}
				return true;
			}
		});

		// �J�n�R�[�h�̐ݒ�
		refBeginBankBranch = new REFDialogCtrl(panel.ctrlBeginBankBranch, panel.getParentFrame());
		refBeginBankBranch.setTargetServlet(TARGET_SERVLET);
		refBeginBankBranch.setTitleID("C00778");
		refBeginBankBranch.setColumnLabels("C00780", "C00783", "C00785");
		refBeginBankBranch.setShowsMsgOnError(false);
		refBeginBankBranch.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��s�R�[�h�̐ݒ�
				keys.put("kind", "BankBranch");
				keys.put("bnkCode", panel.ctrlBank.getField().getText());
				keys.put("endCode", panel.ctrlEndBankBranch.getField().getText());
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		panel.ctrlBeginBankBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginBankBranch.refreshName();
				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		refEndBankBranch = new REFDialogCtrl(panel.ctrlEndBankBranch, panel.getParentFrame());
		refEndBankBranch.setTargetServlet(TARGET_SERVLET);
		refEndBankBranch.setTitleID("C00778");
		refEndBankBranch.setColumnLabels("C00780", "C00783", "C00785");
		refEndBankBranch.setShowsMsgOnError(false);
		refEndBankBranch.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��s�R�[�h�̐ݒ�
				keys.put("kind", "BankBranch");
				keys.put("bnkCode", panel.ctrlBank.getField().getText());
				keys.put("beginCode", panel.ctrlBeginBankBranch.getField().getText());
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		panel.ctrlEndBankBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndBankBranch.refreshName();
				return true;
			}
		});

	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C01698");
			// �ҏW��ʂ̕\��
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �I���s�ێ�����
			String bankCode = dialog.getDataList().get("bnkCode").toString();
			String bnkStnCode = dialog.getDataList().get("bnkStnCode").toString();
			modifySpreadRow(bankCode, bnkStnCode, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			createEditDisplayDialog("C00977");
			// ���O�s���擾����
			int nomRow = panel.ssBank.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssBank.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ��s�R�[�h�̐ݒ�
			map.put("bnkCode", model.getTableDataItem(nomRow, 0));
			// ��s�x�X�R�[�h�̐ݒ�
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 1));
			// ��s���̐ݒ�
			map.put("bnkName_S", model.getTableDataItem(nomRow, 2));
			// ��s�J�i���̂̐ݒ�
			map.put("bnkKana", model.getTableDataItem(nomRow, 3));
			// ��s�������̂̐ݒ�
			map.put("bnkName_K", model.getTableDataItem(nomRow, 4));
			// ��s�x�X���̐ݒ�
			map.put("bnkStnName_S", model.getTableDataItem(nomRow, 5));
			// ��s�x�X�J�i���̂̐ݒ�
			map.put("bnkStnKana", model.getTableDataItem(nomRow, 6));
			// ��s�x�X�������̂̐ݒ�
			map.put("bnkStnName_K", model.getTableDataItem(nomRow, 7));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// ������ʂ̐ݒ�
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ�\������
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			String bankCode = dialog.getDataList().get("bnkCode").toString();
			String bnkStnCode = dialog.getDataList().get("bnkStnCode").toString();
			modifySpreadRow(bankCode, bnkStnCode, UPD_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * ���ʏ���
	 */
	public void copy() {
		try {
			createEditDisplayDialog("C01738");
			// ���O�s���擾����
			int nomRow = panel.ssBank.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssBank.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ��s�R�[�h�̐ݒ�
			map.put("bnkCode", model.getTableDataItem(nomRow, 0));
			// ��s�x�X�R�[�h�̐ݒ�
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 1));
			// ��s���̐ݒ�
			map.put("bnkName_S", model.getTableDataItem(nomRow, 2));
			// ��s�J�i���̂̐ݒ�
			map.put("bnkKana", model.getTableDataItem(nomRow, 3));
			// ��s�������̂̐ݒ�
			map.put("bnkName_K", model.getTableDataItem(nomRow, 4));
			// ��s�x�X���̐ݒ�
			map.put("bnkStnName_S", model.getTableDataItem(nomRow, 5));
			// ��s�x�X�J�i���̂̐ݒ�
			map.put("bnkStnKana", model.getTableDataItem(nomRow, 6));
			// ��s�x�X�������̂̐ݒ�
			map.put("bnkStnName_K", model.getTableDataItem(nomRow, 7));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ̕\��
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);

			String bankCode = dialog.getDataList().get("bnkCode").toString();
			String bnkStnCode = dialog.getDataList().get("bnkStnCode").toString();
			modifySpreadRow(bankCode, bnkStnCode, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * ��������
	 * 
	 * @return ��������
	 */
	protected boolean find() {
		String beginBreakDownItem = panel.ctrlBeginBankBranch.getValue().toString();
		String endBreakDownItem = panel.ctrlEndBankBranch.getValue().toString();

		try {
			if (Util.isSmallerThen(beginBreakDownItem, endBreakDownItem) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginBankBranch.getField().requestFocus();
				showMessage(panel, "W00036", "C00778");
				return false;
			}

			return reflesh();

		} catch (IOException e) {
			// �{�^�����b�N�̐ݒ�
			lockBtn(false);
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
		return false;
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param bnkCode
	 * @param bankBranchCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	private void modifySpreadRow(String bnkCode, String bankBranchCode, int updKbn) throws TRequestException,
		IOException {
		Vector<Vector> cells = setModifyCell(bnkCode, bankBranchCode, updKbn);
		TTable ssPanelSpread = panel.ssBank;
		int row = ssPanelSpread.getCurrentRow();
		panel.setDataList(cells);
		if (cells.size() == 0) {
			ssPanelSpread.setRowSelection(-999, 0);
		} else {
			// �V�K�ƕ��ʏꍇ
			if (updKbn == INSERT_KBN) {
				if (cells.size() != 1) {
					int lastRow = cells.size() - 1;
					selectSpreadRow(lastRow);
					// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
					ssPanelSpread.getVertSB().setValue(lastRow * ssPanelSpread.getPixelHeight(row));
				} else {
					selectSpreadRow(0);
				}

			} else if (updKbn == UPD_KBN) {
				// �ҏW�ꍇ
				selectSpreadRow(row);
				// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
				ssPanelSpread.getVertSB().setValue(row * ssPanelSpread.getPixelHeight(row));
			}
			ssPanelSpread.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssBank.getDataSource().getNumRows() != 0);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param bnkCode
	 * @param bankBranchCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String bnkCode, String bankBranchCode, int updKbn) throws IOException,
		TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// ��s�R�[�h�̐ݒ�
		addSendValues("bnkCode", bnkCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginBnkStnCode", Util.avoidNull(bankBranchCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endBnkStnCode", Util.avoidNull(bankBranchCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssBank.getDataSource();
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = modifyDs.getCells();

		Iterator recordIte = getResultList().iterator();

		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			showMessage(panel, "W00100", "");
		}

		Vector<String> colum = new Vector<String>();

		for (int row = 0; recordIte.hasNext(); row++) {
			// ���ʂ̓��e���擾����
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				// ���ʏW��ǉ�����
				colum.add(column, (String) dataIte.next());
			}

			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssBank.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @return �f�[�^�����݂��邩�ǂ���
	 * @throws IOException
	 */

	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// ��s�R�[�h�̎擾
		String bnkCode = panel.ctrlBank.getValue();
		// �J�n�R�[�h�̎擾
		String beginBnkStnCode = panel.ctrlBeginBankBranch.getValue();
		// �I���R�[�h�̎擾
		String endBnkStnCode = panel.ctrlEndBankBranch.getValue();
		beginBnkStnCode = beginBnkStnCode.trim();
		endBnkStnCode = endBnkStnCode.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// ��s�R�[�h�̐ݒ�
		addSendValues("bnkCode", bnkCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginBnkStnCode", Util.avoidNull(beginBnkStnCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endBnkStnCode", Util.avoidNull(endBnkStnCode));

		// �T�[�o���̃G���[�ꍇ
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();
		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			panel.ctrlBeginBankBranch.getField().requestFocus();
			showMessage(panel, "W00100");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			// ���ʂ̓��e���擾����
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// colum�̏�����
			Vector<String> colum = new Vector<String>();
			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				// ���ʏW��ǉ�����
				colum.add(column, (String) dataIte.next());
			}

			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
			cells.add(row, colum);
		}
		// ���ʂ�\������
		panel.setDataList(cells);
		return dataExists;
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �ʉ݃}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * �{�^�����b�N�̐���
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		panel.btnCopy.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
		panel.btnDelete.setEnabled(boo);
	}

	/**
	 * �폜����
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssBank.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssBank.getDataSource();
				// ��s�R�[�h�̎擾
				String bnkCode = (String) model.getTableDataItem(nomRow, 0);
				// ��s�x�X�R�[�h�̐ݒ�
				String bnkStnCode = (String) model.getTableDataItem(nomRow, 1);
				// ������ʂ̐ݒ�
				addSendValues("flag", "delete");
				// ��s�R�[�h�̎擾
				addSendValues("bnkCode", bnkCode);
				// ��s�x�X�R�[�h�̐ݒ�
				addSendValues("bnkStnCode", bnkStnCode);

				// �T�[�u���b�g�̐ڑ���
				request(TARGET_SERVLET);
				deleteSpreadRow();
			} catch (IOException e) {
				// ����ɏ�������܂���ł���
				errorHandler(panel.getParentFrame(), e, "E00009");
			}
		}
	}

	/**
	 * �폜�ꍇ�A�X�v���b�h�X�V
	 */
	private void deleteSpreadRow() {
		int row = panel.ssBank.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssBank.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssBank.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssBank.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssBank.getVertSB().setValue(0);
			panel.ssBank.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssBank.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {

		panel.ssBank.setRowSelection(row, row);
		panel.ssBank.setCurrentCell(row, 0);

	}

	/**
	 * �ύX����
	 * 
	 * @param map �f�[�^
	 * @param isInsert �V�K(true) or �X�V(false)
	 * @throws IOException
	 */
	private void modify(Map map, boolean isInsert) throws IOException {
		// ������ʂ̐ݒ�
		addSendValues("flag", isInsert ? "insert" : "update");
		// ��s�R�[�h�̐ݒ�
		addSendValues("bnkCode", (String) map.get("bnkCode"));
		// ��s�x�X�R�[�h�̐ݒ�
		addSendValues("bnkStnCode", (String) map.get("bnkStnCode"));
		// ��s���̐ݒ�
		addSendValues("bnkName_S", (String) map.get("bnkName_S"));
		// ��s�J�i���̂̐ݒ�
		addSendValues("bnkKana", (String) map.get("bnkKana"));
		// ��s�������̂̐ݒ�
		addSendValues("bnkName_K", (String) map.get("bnkName_K"));
		// ��s�x�X���̐ݒ�
		addSendValues("bnkStnName_S", (String) map.get("bnkStnName_S"));
		// ��s�x�X�J�i���̂̐ݒ�
		addSendValues("bnkStnKana", (String) map.get("bnkStnKana"));
		// ��s�x�X�������̂̐ݒ�
		addSendValues("bnkStnName_K", (String) map.get("bnkStnName_K"));
		// �J�n�N�����̐ݒ�
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
		addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
		}
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginBankBranch.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��s�R�[�h�̎擾
			conds.put("bnkCode", panel.ctrlBank.getValue());
			// �J�n�R�[�h�̎擾
			conds.put("beginBnkStnCode", panel.ctrlBeginBankBranch.getField().getText());
			// �I���R�[�h�̎擾
			conds.put("endBnkStnCode", panel.ctrlEndBankBranch.getField().getText());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * Dialog����
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MG0140EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}
}
