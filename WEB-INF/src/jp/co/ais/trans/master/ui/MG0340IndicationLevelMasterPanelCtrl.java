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
 * �J�����x���}�X�^��ʃR���g���[��
 */
public class MG0340IndicationLevelMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0340";

	/** �V�K�ƕ��ʋ敪 */
	private static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	private static int UPD_KBN = 1;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0340IndicationLevelMasterServlet";

	/** �p�l�� */
	private MG0340IndicationLevelMasterPanel panel;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0340IndicationLevelMasterPanelCtrl() {
		try {
			panel = new MG0340IndicationLevelMasterPanel(this);
			// �{�^�����b�N
		} catch (Exception e) {
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
		ref1 = new REFDialogCtrl(panel.ctrlBeginUser, panel.getParentFrame());
		ref1.setColumnLabels("C00589", "C00692", "C00693");
		ref1.setTargetServlet("MG0020UserMasterServlet");
		ref1.setTitleID(getWord("C02355"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndUser.getField().getText());
				keys.put("kind", "User");
				return keys;
			}
		});

		panel.ctrlBeginUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		ref2 = new REFDialogCtrl(panel.ctrlEndUser, panel.getParentFrame());
		ref2.setColumnLabels("C00589", "C00692", "C00693");
		ref2.setTargetServlet("MG0020UserMasterServlet");
		ref2.setTitleID(getWord("C02355"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginUser.getField().getText());
				keys.put("kind", "User");
				return keys;
			}
		});

		panel.ctrlEndUser.setInputVerifier(new TInputVerifier() {

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
			MG0340EditDisplayDialogCtrl dialog = new MG0340EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");

			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);
			unlockBtn();
			// �I���s�ێ�����
			String kjlUsrId = dialog.getDataList().get("kjlUsrId").toString();
			String kjlKmtCode = dialog.getDataList().get("kjlKmtCode").toString();
			String kjlDpkSsk = dialog.getDataList().get("kjlDpkSsk").toString();
			modifySpreadRow(kjlUsrId, kjlKmtCode, kjlDpkSsk, INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			MG0340EditDisplayDialogCtrl dialog = new MG0340EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kjlUsrId", model.getTableDataItem(nomRow, 1));
			map.put("kjlKmtCode", model.getTableDataItem(nomRow, 2));
			map.put("kjlDpkSsk", model.getTableDataItem(nomRow, 3));
			map.put("kjlLvl", model.getTableDataItem(nomRow, 7));
			map.put("kjlUpDepCode", model.getTableDataItem(nomRow, 5));
			map.put("kjlDepCode", model.getTableDataItem(nomRow, 6));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show(false);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), false);

			// �X�v���b�h�X�V
			String kjlUsrId = dialog.getDataList().get("kjlUsrId").toString();
			String kjlKmtCode = dialog.getDataList().get("kjlKmtCode").toString();
			String kjlDpkSsk = dialog.getDataList().get("kjlDpkSsk").toString();
			modifySpreadRow(kjlUsrId, kjlKmtCode, kjlDpkSsk, UPD_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �f�[�^����
	 */
	void enter() {
		try {
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				lockBtn(false);
			} else {
				unlockBtn();
			}
		} catch (Exception e) {
			ClientLogger.error(this.getClass().getName(), e);
		}
	}

	/**
	 * ���ʏ���
	 */
	public void copy() {
		try {
			MG0340EditDisplayDialogCtrl dialog = new MG0340EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kjlUsrId", model.getTableDataItem(nomRow, 1));
			map.put("kjlKmtCode", model.getTableDataItem(nomRow, 2));
			map.put("kjlDpkSsk", model.getTableDataItem(nomRow, 3));
			map.put("kjlLvl", model.getTableDataItem(nomRow, 7));
			map.put("kjlUpDepCode", model.getTableDataItem(nomRow, 5));
			map.put("kjlDepCode", model.getTableDataItem(nomRow, 6));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			String kjlUsrId = dialog.getDataList().get("kjlUsrId").toString();
			String kjlKmtCode = dialog.getDataList().get("kjlKmtCode").toString();
			String kjlDpkSsk = dialog.getDataList().get("kjlDpkSsk").toString();
			// �X�v���b�h�X�V
			modifySpreadRow(kjlUsrId, kjlKmtCode, kjlDpkSsk, INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param kjlUsrId
	 * @param kjlKmtCode
	 * @param kjlDpkSsk
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	private void modifySpreadRow(String kjlUsrId, String kjlKmtCode, String kjlDpkSsk, int updKbn)
		throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kjlUsrId, kjlKmtCode, kjlDpkSsk, updKbn);
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
	 * @param kjlUsrId
	 * @param kjlKmtCode
	 * @param kjlDpkSsk
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String kjlUsrId, String kjlKmtCode, String kjlDpkSsk, int updKbn)
		throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		addSendValues("flag", "findOne");
		addSendValues("kaiCode", kaiCode);
		addSendValues("kjlUsrId", kjlUsrId);
		addSendValues("kjlKmtCode", kjlKmtCode);
		addSendValues("kjlDpkSsk", kjlDpkSsk);

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

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
			String text = colum.get(4);
			colum.set(4, this.getWord("C01739") + text);
			colum.set(7, text);
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
	private void modify(Map map, boolean isInsert) throws IOException {
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", getLoginUserCompanyCode());
		super.addSendValues("kjlUsrId", (String) map.get("kjlUsrId"));
		super.addSendValues("kjlKmtCode", (String) map.get("kjlKmtCode"));
		super.addSendValues("kjlDpkSsk", (String) map.get("kjlDpkSsk"));
		super.addSendValues("kjlLvl", (String) map.get("kjlLvl"));
		super.addSendValues("kjlUpDepCode", (String) map.get("kjlUpDepCode"));
		super.addSendValues("kjlDepCode", (String) map.get("kjlDepCode"));
		super.addSendValues("prgID", PROGRAM_ID);

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
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
				TableDataModel model = panel.ssJournal.getDataSource();
				// ��ЃR�[�h
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// ���[�U�[�R�[�h
				String kjlUsrId = (String) model.getTableDataItem(nomRow, 1);
				// �Ȗڑ̌n����
				String kjlKmtCode = (String) model.getTableDataItem(nomRow, 2);
				// �g�D�R�[�h
				String kjlDpkSsk = (String) model.getTableDataItem(nomRow, 3);

				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("kjlUsrId", kjlUsrId);
				super.addSendValues("kjlKmtCode", kjlKmtCode);
				super.addSendValues("kjlDpkSsk", kjlDpkSsk);

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
			String strBegin = panel.ctrlBeginUser.getField().getText();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndUser.getField().getText();
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginUser.getField().requestFocus();
				showMessage(panel, "W00036", "C00528 ");
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
			panel.ctrlBeginUser.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginKjlUsrId", panel.ctrlBeginUser.getValue());
			conds.put("endKjlUsrId", panel.ctrlEndUser.getValue());
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

		String beginKjlUsrId = panel.ctrlBeginUser.getValue();
		String endKjlUsrId = panel.ctrlEndUser.getValue();

		beginKjlUsrId = beginKjlUsrId.trim();
		endKjlUsrId = endKjlUsrId.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginKjlUsrId", Util.avoidNull(beginKjlUsrId));
		addSendValues("endKjlUsrId", Util.avoidNull(endKjlUsrId));

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginUser.getField().requestFocus();
			// �x�����b�Z�[�W�\��
			showMessage(panel, "W00100");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
			String text = colum.get(4);
			colum.set(4, this.getWord("C01739") + text);
			colum.set(7, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);
		return dataExists;
	}
}
