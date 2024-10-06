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
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �Ǘ��}�X�^��ʃR���g���[��
 */
public class MG0320SystemDivisionMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0320";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0320SystemDivisionMasterServlet";
	}

	/** �p�l�� */
	protected MG0320SystemDivisionMasterPanel panel;

	protected Map<String, String> dataKbnMap;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	MG0320EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0320SystemDivisionMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0320SystemDivisionMasterPanel(this);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		dataKbnMap = new HashMap<String, String>();
		dataKbnMap.put("0", getWord("C02104"));
		dataKbnMap.put("1", getWord("C02105"));
		dataKbnMap.put("2", getWord("C02106"));

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
		ref1 = new REFDialogCtrl(panel.ctrlBeginSystemDivision, panel.getParentFrame());
		ref1.setColumnLabels("C00980", "C00833", "C00834");
		ref1.setTargetServlet("MG0320SystemDivisionMasterServlet");
		ref1.setTitleID(getWord("C02354"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndSystemDivision.getField().getText());
				keys.put("kind", "SystemDivision");
				return keys;
			}
		});

		panel.ctrlBeginSystemDivision.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(panel.ctrlEndSystemDivision, panel.getParentFrame());
		ref2.setColumnLabels("C00980", "C00833", "C00834");
		ref2.setTargetServlet("MG0320SystemDivisionMasterServlet");
		ref2.setTitleID(getWord("C02354"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginSystemDivision.getField().getText());
				keys.put("kind", "SystemDivision");
				return keys;
			}
		});

		panel.ctrlEndSystemDivision.setInputVerifier(new TInputVerifier() {

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
	 * Dialog����
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MG0320EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C01698");
			dialog.setDataKbnMap(dataKbnMap);
			// �ҏW��ʂ̕\��
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) return;
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �{�^�����b�N�̉���
			unlockBtn();

			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("sysKbn").toString(), INSERT_KBN);
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
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C00977");
			dialog.setDataKbnMap(dataKbnMap);
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
			map.put("sysKbn", model.getTableDataItem(nomRow, 1));
			map.put("sysName", model.getTableDataItem(nomRow, 2));
			map.put("sysName_S", model.getTableDataItem(nomRow, 3));
			map.put("sysName_K", model.getTableDataItem(nomRow, 4));
			map.put("osyKbn", model.getTableDataItem(nomRow, 10));
			// ������ʂ̐ݒ�
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ�\������
			dialog.show(false);

			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("sysKbn").toString(), UPD_KBN);
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
			createEditDisplayDialog("C01738");
			// ���O�s���擾����
			dialog.setDataKbnMap(dataKbnMap);
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("sysKbn", model.getTableDataItem(nomRow, 1));
			map.put("sysName", model.getTableDataItem(nomRow, 2));
			map.put("sysName_S", model.getTableDataItem(nomRow, 3));
			map.put("sysName_K", model.getTableDataItem(nomRow, 4));
			map.put("osyKbn", model.getTableDataItem(nomRow, 10));
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			dialog.show(true);
			// �ҏW��ʂ̕\��
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("sysKbn").toString(), INSERT_KBN);
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
	protected void modify(Map map, boolean isInsert) throws IOException {
		// ������ʂ̐ݒ�
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("sysKbn", (String) map.get("sysKbn"));
		super.addSendValues("sysName", (String) map.get("sysName"));
		super.addSendValues("sysName_S", (String) map.get("sysName_S"));
		super.addSendValues("sysName_K", (String) map.get("sysName_K"));
		super.addSendValues("osyKbn", (String) map.get("osyKbn"));
		super.addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel);
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param sysKbn
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String sysKbn, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(sysKbn, updKbn);
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
	 * @param sysKbn
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String sysKbn, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginSysKbn", Util.avoidNull(sysKbn));
		addSendValues("endSysKbn", Util.avoidNull(sysKbn));

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
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
			// ���ʂ̓��e���擾����
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String value, text;
			// �ް��敪
			value = colum.get(5);
			text = dataKbnMap.get(value);
			colum.add(10, value);
			colum.set(5, text);
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
		if (this.showConfirmMessage(panel, FocusButton.NO, "Q00007")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssJournal.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssJournal.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // ��ЃR�[�h
				String sysKbn = (String) model.getTableDataItem(nomRow, 1); // �Ǘ��R�[�h
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("sysKbn", sysKbn);

				// �T�[�u���b�g�̐ڑ���
				if (!request(getServletName())) {
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
			String strBegin = panel.ctrlBeginSystemDivision.getValue();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndSystemDivision.getValue();
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginSystemDivision.getField().requestFocus();
				showMessage(panel, "W00036", "C00980");
				return false;
			}
			return reflesh();
			// �{�^�����b�N����

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
		// �폜�{�^�����b�N�̏���
		panel.btnDelete.setEnabled(boo);
		// �ҏW�{�^�����b�N�̏���
		panel.btnEdit.setEnabled(boo);
		// ���ʃ{�^�����b�N�̏���
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
	 * �\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginSysKbn = panel.ctrlBeginSystemDivision.getValue();
		// �I���R�[�h�̎擾
		String endSysKbn = panel.ctrlEndSystemDivision.getValue();
		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginSysKbn", Util.avoidNull(beginSysKbn));
		addSendValues("endSysKbn", Util.avoidNull(endSysKbn));

		// �T�[�o���̃G���[�ꍇ
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginSystemDivision.getField().requestFocus();
			// �x�����b�Z�[�W�\��
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
				colum.add(column, (String) dataIte.next());
			}

			String value, text;
			// �ް��敪
			value = colum.get(5);
			text = dataKbnMap.get(value);
			colum.add(10, value);
			colum.set(5, text);

			cells.add(row, colum);
		}
		// ���ʂ�\������
		panel.setDataList(cells);
		return dataExists;
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginSystemDivision.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginSysKbn", panel.ctrlBeginSystemDivision.getValue());
			conds.put("endSysKbn", panel.ctrlEndSystemDivision.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

}
