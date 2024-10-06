package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
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
 * ��Њԕt�փ}�X�^��ʃR���g���[��
 */
public class MG0350InterCompanyTransferMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0350";

	/** �V�K�ƕ��ʋ敪 */
	private static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	private static int UPD_KBN = 1;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0350InterCompanyTransferMasterServlet";

	/** �p�l�� */
	private MG0350InterCompanyTransferMasterPanel panel;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0350InterCompanyTransferMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0350InterCompanyTransferMasterPanel(this);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		// ��ʏ�����
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
	}

	/**
	 * ��ʏ�����
	 */
	void init() {
		// �J�n�R�[�h�̐ݒ�
		ref1 = new REFDialogCtrl(panel.ctrlBeginTransferCompany, panel.getParentFrame());
		ref1.setColumnLabels("C00596", "C00686", null);
		ref1.setTargetServlet("MG0010EnvironmentalSettingMasterServlet");
		ref1.setTitleID(getWord("C00053"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("endCode", panel.ctrlEndTransferCompany.getField().getText());
				keys.put("kind", "EnvironmentalSetting");
				return keys;
			}
		});

		panel.ctrlBeginTransferCompany.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(panel.ctrlEndTransferCompany, panel.getParentFrame());
		ref2.setColumnLabels("C00596", "C00686", null);
		ref2.setTargetServlet("MG0010EnvironmentalSettingMasterServlet");
		ref2.setTitleID(getWord("C00053"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("beginCode", panel.ctrlBeginTransferCompany.getField().getText());
				keys.put("kind", "EnvironmentalSetting");
				return keys;
			}
		});

		panel.ctrlEndTransferCompany.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}
		});
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �Ǘ��}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return this.panel;
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			MG0350EditDisplayDialogCtrl dialog = new MG0350EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			// �ҏW��ʂ̕\��
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) return;
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �{�^�����b�N�̉���
			lockBtn(true);
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("ktkKaiCode").toString(), INSERT_KBN);
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
			MG0350EditDisplayDialogCtrl dialog = new MG0350EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			// ���O�s���擾����
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("ktkKaiCode", model.getTableDataItem(nomRow, 1));
			map.put("ktkDepCode", model.getTableDataItem(nomRow, 2));
			map.put("ktkKmkCode", model.getTableDataItem(nomRow, 3));
			map.put("ktkHkmCode", model.getTableDataItem(nomRow, 4));
			map.put("ktkUkmCode", model.getTableDataItem(nomRow, 5));
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ�\������
			dialog.show(false);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("ktkKaiCode").toString(), UPD_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �f�[�^����
	 */
	void enter() {
		try {
			// ���O�s���擾����
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			// �{�^�����b�N�̏���
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				lockBtn(false);
			} else {
				unlockBtn();
			}
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			ClientLogger.error(this.getClass().getName(), e);
		}
	}

	/**
	 * ���ʏ���
	 */
	public void copy() {
		try {
			MG0350EditDisplayDialogCtrl dialog = new MG0350EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			// ���O�s���擾����
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("ktkKaiCode", model.getTableDataItem(nomRow, 1));
			map.put("ktkDepCode", model.getTableDataItem(nomRow, 2));
			map.put("ktkKmkCode", model.getTableDataItem(nomRow, 3));
			map.put("ktkHkmCode", model.getTableDataItem(nomRow, 4));
			map.put("ktkUkmCode", model.getTableDataItem(nomRow, 5));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ̕\��
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("ktkKaiCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
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
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("ktkKaiCode", (String) map.get("ktkKaiCode"));
		super.addSendValues("ktkDepCode", (String) map.get("ktkDepCode"));
		super.addSendValues("ktkKmkCode", (String) map.get("ktkKmkCode"));
		super.addSendValues("ktkHkmCode", (String) map.get("ktkHkmCode"));
		super.addSendValues("ktkUkmCode", (String) map.get("ktkUkmCode"));
		super.addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param ktkCaiCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	private void modifySpreadRow(String ktkCaiCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(ktkCaiCode, updKbn);
		TTable ssPanelSpread = panel.ssJournal;
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
		lockBtn(panel.ssJournal.getDataSource().getNumRows() != 0);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param ktkCaiCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String ktkCaiCode, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginKtkKaiCode", Util.avoidNull(ktkCaiCode));
		addSendValues("endKtkKaiCode", Util.avoidNull(ktkCaiCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssJournal.getDataSource();
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = modifyDs.getCells();

		Iterator recordIte = getResultList().iterator();

		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			showMessage(panel, "W00100", "");
		}

		Vector<String> colum = new Vector<String>();

		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssJournal.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * �폜����
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssJournal.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssJournal.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // ��ЃR�[�h
				String ktkKaiCode = (String) model.getTableDataItem(nomRow, 1); // �t�։�ЃR�[�h
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("ktkKaiCode", ktkKaiCode);

				// �T�[�u���b�g�̐ڑ���
				if (!request(TARGET_SERVLET)) {
					errorHandler(panel);
					return;
				}
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
		int row = panel.ssJournal.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssJournal.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssJournal.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssJournal.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssJournal.getVertSB().setValue(0);
			panel.ssJournal.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssJournal.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {

		panel.ssJournal.setRowSelection(row, row);
		panel.ssJournal.setCurrentCell(row, 0);

	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */

	boolean find() {

		try {
			// �J�n�R�[�h�̎擾
			String strBegin = panel.ctrlBeginTransferCompany.getValue();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndTransferCompany.getValue();
			// �J�n�R�[�h���I���R�[�h���傫��

			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginTransferCompany.getField().requestFocus();
				showMessage(panel, "W00036", "C00846 ");
				return false;
			}
			return reflesh();
		} catch (IOException e) {
			lockBtn(false);

			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
		return false;
	}

	/**
	 * �{�^��(�V�K�A�폜�A�ҏW�A���ʁA���X�g�o��)���b�N
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		panel.btnDelete.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
		panel.btnCopy.setEnabled(boo);

	}

	/**
	 * �{�^�����b�N�̉���
	 */
	void unlockBtn() {
		panel.btnDelete.setEnabled(true);
		panel.btnEdit.setEnabled(true);
		panel.btnCopy.setEnabled(true);
		panel.btnListOutput.setEnabled(true);
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginTransferCompany.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginKtkKaiCode", panel.ctrlBeginTransferCompany.getValue());
			conds.put("endKtkKaiCode", panel.ctrlEndTransferCompany.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 * @throws IOException
	 */

	private boolean reflesh() throws IOException {
		boolean dataExists = true;
		String kaiCode = getLoginUserCompanyCode();

		String beginKtkCaiCode = panel.ctrlBeginTransferCompany.getField().getText();
		String endKtkCaiCode = panel.ctrlEndTransferCompany.getField().getText();

		beginKtkCaiCode = beginKtkCaiCode.trim();
		endKtkCaiCode = endKtkCaiCode.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginKtkKaiCode", Util.avoidNull(beginKtkCaiCode));
		addSendValues("endKtkKaiCode", Util.avoidNull(endKtkCaiCode));

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginTransferCompany.getField().requestFocus();
			// �x�����b�Z�[�W�\��
			showMessage(panel, "W00100");
			dataExists = false;

		}
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// ���ʂ̓��e���擾����
			Vector<String> colum = new Vector<String>();
			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
			cells.add(row, colum);
		}
		// ���ʂ�\������
		panel.setDataList(cells);
		return dataExists;
	}
}
