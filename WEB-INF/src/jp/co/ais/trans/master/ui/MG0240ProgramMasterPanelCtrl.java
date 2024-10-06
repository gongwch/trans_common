package jp.co.ais.trans.master.ui;

import java.awt.*;
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
 * �v���O�����}�X�^��ʃRi���g���[��
 */
public class MG0240ProgramMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0240";

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
		return "MG0240ProgramMasterServlet";
	}

	/** �p�l�� */
	protected MG0240ProgramMasterPanel panel;

	// �V�X�e���敪�̃f�[�^
	protected Map sysMap;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	MG0240EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0240ProgramMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0240ProgramMasterPanel(this);

			loadSysMapData();

			// ��ʏ�����
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					init();
				}
			});
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * ��ʏ�����
	 */
	void init() {
		// �J�n�R�[�h�̐ݒ�
		ref1 = new REFDialogCtrl(panel.ctrlBeginProgram, panel.getParentFrame());
		ref1.setColumnLabels("C00818", "C00820", "C00821");
		ref1.setTargetServlet("MG0240ProgramMasterServlet");
		ref1.setTitleID(getWord("C02147"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndProgram.getField().getText());
				keys.put("kind", "Program");
				return keys;
			}
		});

		panel.ctrlBeginProgram.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});
		// �I���R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(panel.ctrlEndProgram, panel.getParentFrame());
		ref2.setColumnLabels("C00818", "C00820", "C00821");
		ref2.setTargetServlet("MG0240ProgramMasterServlet");
		ref2.setTitleID(getWord("C02147"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// map�̏�����
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginProgram.getField().getText());
				keys.put("kind", "Program");
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		panel.ctrlEndProgram.setInputVerifier(new TInputVerifier() {

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
		dialog = new MG0240EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C01698");
			// �ҏW��ʂ̕\��
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ҏW��ʂ��J���Ă��܂���
			map.put("flag", "insert");

			dialog.setSysMap(sysMap);
			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			lockBtn(true);
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("prgCode").toString(), INSERT_KBN);
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
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("prgCode", model.getTableDataItem(nomRow, 2));
			map.put("sysCode", model.getTableDataItem(nomRow, 1));
			map.put("prgName", model.getTableDataItem(nomRow, 3));
			map.put("prgName_S", model.getTableDataItem(nomRow, 4));
			map.put("prgName_K", model.getTableDataItem(nomRow, 5));
			map.put("ken", model.getTableDataItem(nomRow, 6));
			map.put("com", model.getTableDataItem(nomRow, 7));
			map.put("ldName", model.getTableDataItem(nomRow, 8));
			map.put("parentPrgCode", model.getTableDataItem(nomRow, 15));
			map.put("menuKbn", model.getTableDataItem(nomRow, 16));
			// �\������ �ǉ�
			map.put("dispIndex", model.getTableDataItem(nomRow, 13));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));

			map.put("flag", "update");

			dialog.setSysMap(sysMap);
			dialog.setValues(map);
			dialog.show(false);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("prgCode").toString(), UPD_KBN);
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
			// ���O�s���擾����
			createEditDisplayDialog("C01738");
			// �f�[�^�W���擾����
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("prgCode", model.getTableDataItem(nomRow, 2));
			map.put("sysCode", model.getTableDataItem(nomRow, 1));
			map.put("prgName", model.getTableDataItem(nomRow, 3));
			map.put("prgName_S", model.getTableDataItem(nomRow, 4));
			map.put("prgName_K", model.getTableDataItem(nomRow, 5));
			map.put("ken", model.getTableDataItem(nomRow, 6));
			map.put("com", model.getTableDataItem(nomRow, 7));
			map.put("ldName", model.getTableDataItem(nomRow, 8));
			map.put("parentPrgCode", model.getTableDataItem(nomRow, 15));
			map.put("menuKbn", model.getTableDataItem(nomRow, 16));
			// �\������ �ǉ�
			map.put("dispIndex", model.getTableDataItem(nomRow, 13));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));

			map.put("flag", "copy");

			dialog.setSysMap(sysMap);
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
			modifySpreadRow(dialog.getDataList().get("prgCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param prgCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String prgCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(prgCode, updKbn);
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
	 * @param prgCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String prgCode, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginPrgCode", Util.avoidNull(prgCode));
		addSendValues("endPrgCode", Util.avoidNull(prgCode));

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
				// ���ʏW��ǉ�����
				colum.add(column, (String) dataIte.next());
			}

			try {
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
			} catch (ParseException ex) {
				ClientLogger.error(
					this.getClass().getName() + ":" + "Date Parse error:" + colum.get(9) + colum.get(10), ex);
			}
			colum.set(9, colum.get(15));

			// �\������
			colum.set(13, colum.get(17));

			String text;
			String value;

			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C00519") : this.getWord("C02170"));
			colum.set(10, text);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssJournal.getCurrentRow(), colum);
		}
		return cells;
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
		super.addSendValues("prgCode", (String) map.get("prgCode"));
		super.addSendValues("sysCode", (String) map.get("sysCode"));
		super.addSendValues("prgName", (String) map.get("prgName"));
		super.addSendValues("prgName_S", (String) map.get("prgName_S"));
		super.addSendValues("prgName_K", (String) map.get("prgName_K"));
		super.addSendValues("ken", (String) map.get("ken"));
		super.addSendValues("com", (String) map.get("com"));
		super.addSendValues("ldName", (String) map.get("ldName"));
		super.addSendValues("parentPrgCode", (String) map.get("parentPrgCode"));
		super.addSendValues("menuKbn", (String) map.get("menuKbn"));
		// �\������ �ǉ�
		super.addSendValues("dispIndex", (String) map.get("dispIndex"));
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		super.addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			errorHandler(panel);
		}
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
				String sysCode = (String) model.getTableDataItem(nomRow, 1); // �V�X�e���R�[�h
				String prgCode = (String) model.getTableDataItem(nomRow, 2); // �v���O�����R�[�h
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("sysCode", sysCode);
				super.addSendValues("prgCode", prgCode);

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
			String strBegin = panel.ctrlBeginProgram.getValue();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndProgram.getValue();
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				showMessage(panel, "W00036", "C00477");
				panel.ctrlBeginProgram.getField().requestFocus();
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
	 * ��ʃ��t���b�V��
	 */
	void outptExcel() {

		try {
			panel.ctrlBeginProgram.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");

			conds.put("kaiCode", getLoginUserCompanyCode());
			String strBegin = panel.ctrlBeginProgram.getValue();
			conds.put("beginPrgCode", strBegin);
			String strEnd = panel.ctrlEndProgram.getValue();
			conds.put("endPrgCode", strEnd);
			conds.put("langCode", getLoginLanguage());
			this.download(panel, getServletName(), conds);
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
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;

		String kaiCode = getLoginUserCompanyCode();

		String beginPrgCode = panel.ctrlBeginProgram.getValue();
		String endPrgCode = panel.ctrlEndProgram.getValue();

		beginPrgCode = beginPrgCode.trim();
		endPrgCode = endPrgCode.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginPrgCode", Util.avoidNull(beginPrgCode));
		addSendValues("endPrgCode", Util.avoidNull(endPrgCode));

		// ���M
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginProgram.getField().requestFocus();
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
				// ���ʏW��ǉ�����
				colum.add(column, (String) dataIte.next());

			}

			try {
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
			} catch (ParseException ex) {
				ClientLogger.error(
					this.getClass().getName() + ":" + "Date Parse error:" + colum.get(9) + colum.get(10), ex);
			}
			colum.set(9, colum.get(15));

			// �\������
			colum.set(13, colum.get(17));

			String text;
			String value;

			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C00519") : this.getWord("C02170"));
			colum.set(10, text);

			cells.add(row, colum);
		}
		// ���ʂ�\������
		panel.setDataList(cells);
		return dataExists;
	}

	private void loadSysMapData() throws IOException {
		addSendValues("flag", "find");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("beginSysKbn", "");
		addSendValues("endSysKbn", "");

		if (!request("MG0320SystemDivisionMasterServlet")) {
			errorHandler(panel);
			return;
		}
		List result = super.getResultList();
		sysMap = new HashMap();
		Iterator ite = result.iterator();
		while (ite.hasNext()) {
			List inner = (List) ite.next();

			// �V�X�e���敪����
			String text = (String) inner.get(3);
			// �V�X�e���敪�R�[�h
			String code = (String) inner.get(1);
			sysMap.put(code, text);
		}
	}
}
