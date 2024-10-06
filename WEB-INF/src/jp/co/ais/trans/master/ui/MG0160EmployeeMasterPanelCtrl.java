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
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �Ј��}�X�^��ʃR���g���[��
 * 
 * @author ookawara
 */
public class MG0160EmployeeMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0160";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	MG0160EditDisplayDialogCtrl dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0160EmployeeMasterServlet";
	}

	/** �p�l�� */
	protected MG0160EmployeeMasterPanel panel;

	protected REFDialogCtrl refBeginEmployee;

	protected REFDialogCtrl refEndEmployee;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0160EmployeeMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0160EmployeeMasterPanel(this);
			// �{�^�����b�N
			lockBtn(false);

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

	void init() {
		// �J�n�R�[�h�̐ݒ�

		refBeginEmployee = new REFDialogCtrl(panel.ctrlBeginEmployee, panel.getParentFrame());
		refBeginEmployee.setColumnLabelIDs("C00697", "C00808", "C00809");
		refBeginEmployee.setTargetServlet(getServletName());
		refBeginEmployee.setTitleID("C00913");
		refBeginEmployee.setShowsMsgOnError(false);
		refBeginEmployee.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndEmployee.getField().getText());
				keys.put("kind", "Employee");
				return keys;
			}
		});

		panel.ctrlBeginEmployee.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginEmployee.refreshName();

				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		refEndEmployee = new REFDialogCtrl(panel.ctrlEndEmployee, panel.getParentFrame());
		refEndEmployee.setColumnLabelIDs("C00697", "C00808", "C00809");
		refEndEmployee.setTargetServlet(getServletName());
		refEndEmployee.setTitleID("C00913");
		refEndEmployee.setShowsMsgOnError(false);
		refEndEmployee.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginEmployee.getField().getText());
				keys.put("kind", "Employee");
				return keys;
			}
		});

		panel.ctrlEndEmployee.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndEmployee.refreshName();

				return true;
			}
		});
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �Ј��}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return this.panel;
	}

	/**
	 * �{�^�����b�N�̐���
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		panel.btnDelete.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
		panel.btnCopy.setEnabled(boo);
	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {
		try {
			// �J�n�R�[�h�̎擾
			String strBegin = panel.ctrlBeginEmployee.getValue();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndEmployee.getValue();
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginEmployee.getField().requestFocus();
				super.showMessage(panel, "W00036", "C00246");
				return false;
			}

			// �\���f�[�^�̎擾
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
	 * Dialog����
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MG0160EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
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
			modifySpreadRow(dialog.getDataList().get("empCode").toString(), INSERT_KBN);
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
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �Ј��R�[�h�̐ݒ�
			map.put("empCode", model.getTableDataItem(nomRow, 1));
			// �Ј�����
			map.put("empName", model.getTableDataItem(nomRow, 2));
			// �Ј�����
			map.put("empName_S", model.getTableDataItem(nomRow, 3));
			// �Ј���������
			map.put("empName_K", model.getTableDataItem(nomRow, 4));
			// �U����s�b�c
			map.put("empFuriBnkCode", model.getTableDataItem(nomRow, 5));
			// �U���x�X�b�c
			map.put("empFuriStnCode", model.getTableDataItem(nomRow, 6));
			// �U�������ԍ�
			map.put("empYknNo", model.getTableDataItem(nomRow, 7));
			// �U�������a�����
			map.put("empKozaKbn", model.getTableDataItem(nomRow, 13));
			// �������`�J�i
			map.put("empYknKana", model.getTableDataItem(nomRow, 9));
			// �U�o��s�����R�[�h
			map.put("empCbkCode", model.getTableDataItem(nomRow, 10));
			// �J�n�N����
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			// �I���N����
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));
			// ������ʂ̐ݒ�
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("empCode").toString(), UPD_KBN);
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
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �Ј��R�[�h�̐ݒ�
			map.put("empCode", model.getTableDataItem(nomRow, 1));
			// �Ј����̂̐ݒ�
			map.put("empName", model.getTableDataItem(nomRow, 2));
			// �Ј����̂̐ݒ�
			map.put("empName_S", model.getTableDataItem(nomRow, 3));
			// �Ј��������̂̐ݒ�
			map.put("empName_K", model.getTableDataItem(nomRow, 4));
			// �U����s�b�c�̐ݒ�
			map.put("empFuriBnkCode", model.getTableDataItem(nomRow, 5));
			// �U���x�X�b�c�̐ݒ�
			map.put("empFuriStnCode", model.getTableDataItem(nomRow, 6));
			// �U�������ԍ��̐ݒ�
			map.put("empYknNo", model.getTableDataItem(nomRow, 7));
			// �U�������a����ʂ̐ݒ�
			map.put("empKozaKbn", model.getTableDataItem(nomRow, 13));
			// �������`�J�i�̐ݒ�
			map.put("empYknKana", model.getTableDataItem(nomRow, 9));
			// �U�o��s�����R�[�h�̐ݒ�
			map.put("empCbkCode", model.getTableDataItem(nomRow, 10));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW��
			modify(dialog.getDataList(), true);
			modifySpreadRow(dialog.getDataList().get("empCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param empCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String empCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(empCode, updKbn);
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
	 * @param empCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String empCode, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginEmpCode", Util.avoidNull(empCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endEmpCode", Util.avoidNull(empCode));

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
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(12))));
			} catch (ParseException ex) {
				// ����ɏ�������܂���ł���
				errorHandler(panel);
			}

			// �U�������a�����
			String value = colum.get(8);
			String text = ("1".equals(value) ? this.getWord("C00465") : this.getWord("C02154"));
			colum.add(13, value);
			colum.set(8, text);

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
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// �Ј��R�[�h�̎擾
				String empCode = (String) model.getTableDataItem(nomRow, 1);
				// ������ʂ̐ݒ�
				addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				addSendValues("kaiCode", kaiCode);
				// �Ј��R�[�h�̎擾
				addSendValues("empCode", empCode);

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
	 * �ύX����
	 * 
	 * @param map �f�[�^
	 * @param isInsert �V�K(true) or �X�V(false)
	 * @throws IOException
	 */
	protected void modify(Map map, boolean isInsert) throws IOException {
		// ������ʂ̐ݒ�
		addSendValues("flag", isInsert ? "insert" : "update");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �Ј��R�[�h�̐ݒ�
		addSendValues("empCode", (String) map.get("empCode"));
		// �Ј����̂̐ݒ�
		addSendValues("empName", (String) map.get("empName"));
		// �Ј����̂̐ݒ�
		addSendValues("empName_S", (String) map.get("empName_S"));
		// �Ј��������̂̐ݒ�
		addSendValues("empName_K", (String) map.get("empName_K"));
		// �U����s�b�c�̐ݒ�
		addSendValues("empFuriBnkCode", (String) map.get("empFuriBnkCode"));
		// �U���x�X�b�c�̐ݒ�
		addSendValues("empFuriStnCode", (String) map.get("empFuriStnCode"));
		// �U�������ԍ��̐ݒ�
		addSendValues("empYknNo", (String) map.get("empYknNo"));
		// �U�������a����ʂ̐ݒ�
		addSendValues("empKozaKbn", (String) map.get("empKozaKbn"));
		// �������`�J�i�̐ݒ�
		addSendValues("empYknKana", (String) map.get("empYknKana"));
		// �U�o��s�����R�[�h�̐ݒ�
		addSendValues("empCbkCode", (String) map.get("empCbkCode"));
		// �J�n�N�����̐ݒ�
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
		addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel);
		}
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginEmpCode = panel.ctrlBeginEmployee.getValue();
		// �I���R�[�h�̎擾
		String endEmpCode = panel.ctrlEndEmployee.getValue();

		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginEmpCode", Util.avoidNull(beginEmpCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endEmpCode", Util.avoidNull(endEmpCode));

		// ���M
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();
		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			panel.ctrlBeginEmployee.getField().requestFocus();
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
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(12))));
			} catch (ParseException ex) {
				// ����ɏ�������܂���ł���
				errorHandler(panel);
			}

			// �U�������a�����
			String value = colum.get(8);
			String text = ("1".equals(value) ? this.getWord("C00465") : this.getWord("C02154"));
			colum.add(13, value);
			colum.set(8, text);

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
			panel.ctrlBeginEmployee.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginEmpCode", panel.ctrlBeginEmployee.getValue());
			conds.put("endEmpCode", panel.ctrlEndEmployee.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
