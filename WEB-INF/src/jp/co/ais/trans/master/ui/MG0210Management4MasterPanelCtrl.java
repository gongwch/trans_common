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
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * �Ǘ��}�X�^��ʃR���g���[��
 * 
 * @author ookawara
 */
public class MG0210Management4MasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0210";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	MG0210EditDisplayDialogCtrl dialog;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0210Management4MasterServlet";
	}

	/**  */
	public String[] columnName;

	/** �p�l�� */
	protected MG0210Management4MasterPanel panel;

	String knrName4;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0210Management4MasterPanelCtrl() {
		knrName4 = CompanyControlHelper210.getInstance(this.getLoginUserCompanyCode()).getKnrName4();
		columnName = new String[] { "C00596", knrName4 + this.getWord("C00174"), knrName4 + this.getWord("C00518"),
				knrName4 + this.getWord("C00548"), knrName4 + this.getWord("C00160"), "C00055", "C00261" };
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0210Management4MasterPanel(this);
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

		panel.ctrlBeginManagement.getButton().setText(knrName4);
		panel.ctrlEndManagement.getButton().setText(knrName4);

	}

	void init() {
		// �J�n�R�[�h�̐ݒ�
		ref1 = new REFDialogCtrl(panel.ctrlBeginManagement, panel.getParentFrame());
		ref1.setTargetServlet(getServletName());
		ref1.setTitleID(knrName4 + this.getWord("C00500"));
		ref1.setPrefixID(knrName4);
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndManagement.getField().getText());
				keys.put("kind", "Management4");
				return keys;
			}
		});

		panel.ctrlBeginManagement.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();

				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(panel.ctrlEndManagement, panel.getParentFrame());
		ref2.setTargetServlet(getServletName());
		ref2.setTitleID(knrName4 + this.getWord("C00500"));
		ref2.setPrefixID(knrName4);
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginManagement.getField().getText());
				keys.put("kind", "Management4");
				return keys;
			}
		});

		panel.ctrlEndManagement.setInputVerifier(new TInputVerifier() {

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
		dialog = new MG0210EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C01698");

			// �ҏW��ʂ̕\��
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) return;
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �{�^�����b�N�̉���
			lockBtn(true);
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("knrCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(e);
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
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �Ǘ�4���ނ̐ݒ�
			map.put("knrCode", model.getTableDataItem(nomRow, 1));
			// �Ǘ�4���̂̐ݒ�
			map.put("knrName", model.getTableDataItem(nomRow, 2));
			// �Ǘ�4���̂̐ݒ�
			map.put("knrName_S", model.getTableDataItem(nomRow, 3));
			// �Ǘ�4�������̂̐ݒ�
			map.put("knrName_K", model.getTableDataItem(nomRow, 4));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 5)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// ������ʂ̐ݒ�
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ�\������
			dialog.show(false);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) return;
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("knrCode").toString(), UPD_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(e);
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
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �Ǘ��R�[�h�̐ݒ�
			map.put("knrCode", model.getTableDataItem(nomRow, 1));
			// �Ǘ�4���̂̐ݒ�
			map.put("knrName", model.getTableDataItem(nomRow, 2));
			// �Ǘ�4���̂̐ݒ�
			map.put("knrName_S", model.getTableDataItem(nomRow, 3));
			// �Ǘ�4�������̂̐ݒ�
			map.put("knrName_K", model.getTableDataItem(nomRow, 4));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 5)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ̕\��
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) return;
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("knrCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(e);
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
		// ��ЃR�[�h�̐ݒ�
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		// �Ǘ��R�[�h�̐ݒ�
		super.addSendValues("knrCode", (String) map.get("knrCode"));
		// �Ǘ�4���̂̐ݒ�
		super.addSendValues("knrName", (String) map.get("knrName"));
		// �Ǘ�4���̂̐ݒ�
		super.addSendValues("knrName_S", (String) map.get("knrName_S"));
		// �Ǘ�4�������̂̐ݒ�
		super.addSendValues("knrName_K", (String) map.get("knrName_K"));
		// �J�n�N�����̐ݒ�
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �J�n�N�����̐ݒ�
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
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
	 * @param krnCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String krnCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(krnCode, updKbn);
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
	 * @param knrCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String knrCode, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginknrCode", Util.avoidNull(knrCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endknrCode", Util.avoidNull(knrCode));

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
				colum.set(5, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(5))));
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(5), ex);
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
		if (this.showConfirmMessage(panel, FocusButton.NO, "Q00007")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssJournal.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssJournal.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// �Ǘ��R�[�h�̎擾
				String knrCode = (String) model.getTableDataItem(nomRow, 1);
				// ������ʂ̐ݒ�
				super.addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				super.addSendValues("kaiCode", kaiCode);
				// �Ǘ��R�[�h�̐ݒ�
				super.addSendValues("knrCode", knrCode);
				// �T�[�u���b�g�̐ڑ���
				if (!request(getServletName())) {
					errorHandler(panel);
					return;
				}
				deleteSpreadRow();
			} catch (IOException e) {
				// ����ɏ�������܂���ł���
				errorHandler(e);
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
			String strBegin = panel.ctrlBeginManagement.getValue();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndManagement.getValue();
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginManagement.getField().requestFocus();
				showMessage(panel, "W00036", knrName4);
				return false;
			}

			// �\���f�[�^�̎擾
			return reflesh();
			// �{�^�����b�N����

		} catch (IOException e) {
			// �{�^�����b�N�̐ݒ�
			lockBtn(false);

			// ����ɏ�������܂���ł���
			errorHandler(e);
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
		String beginknrCode = panel.ctrlBeginManagement.getValue();
		// �I���R�[�h�̎擾
		String endknrCode = panel.ctrlEndManagement.getValue();
		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginknrCode", Util.avoidNull(beginknrCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endknrCode", Util.avoidNull(endknrCode));

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
			panel.ctrlBeginManagement.getField().requestFocus();
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
			cells.add(row, colum);
			try {
				colum.set(5, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(5))));
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName(), ex);
			}
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
			panel.ctrlBeginManagement.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginknrCode", panel.ctrlBeginManagement.getValue());
			conds.put("endknrCode", panel.ctrlEndManagement.getValue());
			conds.put("langCode", getLoginLanguage());
			this.download(panel, getServletName(), conds);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(e);
		}
	}
}
