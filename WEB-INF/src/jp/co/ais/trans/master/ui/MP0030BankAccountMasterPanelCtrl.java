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
 * ��s�����}�X�^��ʃR���g���[��
 * 
 * @author yangjing
 */
public class MP0030BankAccountMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MP0030";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MP0030BankAccountMasterServlet";

	/** �p�l�� */
	protected MP0030BankAccountMasterPanel panel;

	/** �P��Map */
	protected Map yknKbnMap;

	MP0030EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */
	public MP0030BankAccountMasterPanelCtrl() {

		panel = new MP0030BankAccountMasterPanel(this);

		try {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					init();
				}
			});
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00010");
		}

	}

	/**
	 * �p�l���擾
	 * 
	 * @return �ʉ݃}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return this.panel;
	}

	/**
	 * �p�l��������
	 */
	protected void init() {

		// ��s�����J�n�R�[�h
		final REFDialogCtrl ref1 = new REFDialogCtrl(panel.ctrlBeginBankAccount, panel.getParentFrame());
		ref1.setColumnLabels("C00857", "C00124", "C00794");
		ref1.setTargetServlet(TARGET_SERVLET);
		ref1.setTitleID("C02322");
		ref1.setShowsMsgOnError(false);

		panel.ctrlBeginBankAccount.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}

		});

		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndBankAccount.getField().getText());

				return keys;
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ���
			public void goodCodeInputted() {

				addSendValues("flag", "getrefitems");
				addSendValues("kaiCode", getLoginUserCompanyCode());
				addSendValues("cbkCbkCode", panel.ctrlBeginBankAccount.getField().getText());

				try {
					// ���M
					if (!request(TARGET_SERVLET)) {
						errorHandler(panel);
						return;
					}

				} catch (IOException ex) {
					// ����ɏ�������܂���ł���
					errorHandler(panel, ex, "E00009");
					return;
				}

				List result = getResultList().get(0);

				if (result != null && result.size() >= 6) {
					String text1 = (String) result.get(1) + " " + (String) result.get(3);

					String rsText = (String) result.get(4);

					if ("1".equals(rsText)) {
						rsText = getWord("C00463");
					} else if ("2".equals(rsText)) {
						rsText = getWord("C00397");
					} else if ("3".equals(rsText)) {
						rsText = getWord("C00045");
					} else {
						rsText = getWord("C00368");
					}

					String text2 = rsText + " " + (String) result.get(5);

					panel.ctrlBeginBankAccount.getNotice().setText(text1);
					panel.txtBeginDepositTypeAccountNumber.setText(text2);
				}
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ��݂��Ȃ�
			public void badCodeInputted() {
				panel.ctrlBeginBankAccount.getNotice().clear();
				panel.txtBeginDepositTypeAccountNumber.clear();
			}

			// ��s�R�[�h���N���A
			public void textCleared() {
				badCodeInputted();
			}
		});

		// ��s�����J�n�R�[�h
		final REFDialogCtrl ref2 = new REFDialogCtrl(panel.ctrlEndBankAccount, panel.getParentFrame());
		ref2.setColumnLabels("C00857", "C00124", "C00794");
		ref2.setTargetServlet("MP0030BankAccountMasterServlet");
		ref2.setTitleID("C02322");
		ref2.setShowsMsgOnError(false);

		panel.ctrlEndBankAccount.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}
		});

		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginBankAccount.getField().getText());

				return keys;
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ���
			public void goodCodeInputted() {

				addSendValues("flag", "getrefitems");
				addSendValues("kaiCode", getLoginUserCompanyCode());
				addSendValues("cbkCbkCode", panel.ctrlEndBankAccount.getField().getText());

				try {

					// ���M
					if (!request(TARGET_SERVLET)) {
						errorHandler(panel);
						return;
					}

				} catch (IOException ex) {
					// ����ɏ�������܂���ł���
					errorHandler(panel, ex, "E00009");
					return;
				}

				List result = getResultList().get(0);

				if (result != null && result.size() >= 6) {
					String text1 = (String) result.get(1) + " " + (String) result.get(3);

					String rsText = (String) result.get(4);

					if ("1".equals(rsText)) {
						rsText = getWord("C00463");
					} else if ("2".equals(rsText)) {
						rsText = getWord("C00397");
					} else if ("3".equals(rsText)) {
						rsText = getWord("C00045");
					} else {
						rsText = getWord("C00368");
					}

					String text2 = rsText + " " + (String) result.get(5);

					panel.ctrlEndBankAccount.getNotice().setText(text1);
					panel.txtEndDepositTypeAccountNumber.setText(text2);
				}
			}

			// ��s�R�[�h�ɂċ�s�}�X�^�ɑ��݂��Ȃ�
			public void badCodeInputted() {
				panel.ctrlEndBankAccount.getNotice().clear();
				panel.txtEndDepositTypeAccountNumber.clear();
			}

			// ��s�R�[�h���N���A
			public void textCleared() {
				badCodeInputted();
			}
		});

		// ����̎w�肳�ꂽ���̂�\������
		yknKbnMap = new LinkedHashMap();
		yknKbnMap.put("1", getWord("C00463"));
		yknKbnMap.put("2", getWord("C00397"));
		yknKbnMap.put("3", getWord("C00045"));
		yknKbnMap.put("4", getWord("C00368"));

		panel.txtBeginDepositTypeAccountNumber.setEnabled(true);
		panel.txtEndDepositTypeAccountNumber.setEnabled(true);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			createEditDisplayDialog("C01698");

			dialog.setYknKbnMap(yknKbnMap);
			// �ҏW��ʂ̕\��
			dialog.show();

			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}

			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);

			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("cbkCbkCode").toString(), INSERT_KBN);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * �_�C�A���O����
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MP0030EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �ҏW����
	 */

	void update() {
		try {
			createEditDisplayDialog("C00977");

			dialog.setYknKbnMap(yknKbnMap);

			// ���O�s���擾����
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ��s�����R�[�h�̐ݒ�
			map.put("cbkCbkCode", model.getTableDataItem(nomRow, 1));
			// ��s�������̂̐ݒ�
			map.put("cbkName", model.getTableDataItem(nomRow, 2));
			// �ʉ݃R�[�h�̐ݒ�
			map.put("curCode", model.getTableDataItem(nomRow, 3));
			// ��s�R�[�h�̐ݒ�
			map.put("cbkBnkCode", model.getTableDataItem(nomRow, 5));
			// �x�X�R�[�h�̐ݒ�
			map.put("cbkStnCode", model.getTableDataItem(nomRow, 7));
			// �U���˗��l�R�[�h�̐ݒ�
			map.put("cbkIraiEmpCode", model.getTableDataItem(nomRow, 9));
			// �U���˗��l���̐ݒ�
			map.put("cbkIraiName", model.getTableDataItem(nomRow, 10));
			// �U���˗��l���i�����j�̐ݒ�
			map.put("cbkIraiName_J", model.getTableDataItem(nomRow, 11));
			// �U���˗��l���i�p���j�̐ݒ�
			map.put("cbkIraiName_E", model.getTableDataItem(nomRow, 12));
			// �a����ڂ̐ݒ�
			map.put("cbkYknKbn", model.getTableDataItem(nomRow, 23));
			// �����ԍ��̐ݒ�
			map.put("cbkYknNo", model.getTableDataItem(nomRow, 14));
			// �Ј��e�a�敪�̐ݒ�
			map.put("cbkEmpFbKbn", model.getTableDataItem(nomRow, 24));
			// �ЊO�e�a�敪�̐ݒ�
			map.put("cbkOutFbKbn", model.getTableDataItem(nomRow, 25));
			// �v�㕔��R�[�h�̐ݒ�
			map.put("cbkDepCode", model.getTableDataItem(nomRow, 17));
			// �ȖڃR�[�h�̐ݒ�
			map.put("cbkKmkCode", model.getTableDataItem(nomRow, 18));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("cbkHkmCode", model.getTableDataItem(nomRow, 19));
			// ����ȖڃR�[�h�̐ݒ�
			map.put("cbkUkmCode", model.getTableDataItem(nomRow, 20));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 21)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 22)));
			// ������ʂ̐ݒ��
			map.put("flag", "update");

			// �f�[�^����ʂɐݒ肷���
			dialog.setValues(map);

			// �ҏW��ʂ�\�������
			dialog.show();

			// �ҏW��ʂ��J���Ă��܂����
			if (!dialog.isSettle()) {
				return;
			}

			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);

			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("cbkCbkCode").toString(), UPD_KBN);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * ���ʏ���
	 */

	public void copy() {
		try {
			createEditDisplayDialog("C01738");

			dialog.setYknKbnMap(yknKbnMap);

			// ���O�s���擾����
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ��s�����R�[�h�̐ݒ�
			map.put("cbkCbkCode", model.getTableDataItem(nomRow, 1));
			// ��s�������̂̐ݒ�
			map.put("cbkName", model.getTableDataItem(nomRow, 2));
			// �ʉ݃R�[�h�̐ݒ�
			map.put("curCode", model.getTableDataItem(nomRow, 3));
			// ��s�R�[�h�̐ݒ�
			map.put("cbkBnkCode", model.getTableDataItem(nomRow, 5));
			// �x�X�R�[�h�̐ݒ�
			map.put("cbkStnCode", model.getTableDataItem(nomRow, 7));
			// �U���˗��l�R�[�h�̐ݒ�
			map.put("cbkIraiEmpCode", model.getTableDataItem(nomRow, 9));
			// �U���˗��l���̐ݒ�
			map.put("cbkIraiName", model.getTableDataItem(nomRow, 10));
			// �U���˗��l���i�����j�̐ݒ�
			map.put("cbkIraiName_J", model.getTableDataItem(nomRow, 11));
			// �U���˗��l���i�p���j�̐ݒ�
			map.put("cbkIraiName_E", model.getTableDataItem(nomRow, 12));
			// �a����ڂ̐ݒ�
			map.put("cbkYknKbn", model.getTableDataItem(nomRow, 23));
			// �����ԍ��̐ݒ�
			map.put("cbkYknNo", model.getTableDataItem(nomRow, 14));
			// �Ј��e�a�敪�̐ݒ�
			map.put("cbkEmpFbKbn", model.getTableDataItem(nomRow, 24));
			// �ЊO�e�a�敪�̐ݒ�
			map.put("cbkOutFbKbn", model.getTableDataItem(nomRow, 25));
			// �v�㕔��R�[�h�̐ݒ�
			map.put("cbkDepCode", model.getTableDataItem(nomRow, 17));
			// �ȖڃR�[�h�̐ݒ�
			map.put("cbkKmkCode", model.getTableDataItem(nomRow, 18));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("cbkHkmCode", model.getTableDataItem(nomRow, 19));
			// ����ȖڃR�[�h�̐ݒ�
			map.put("cbkUkmCode", model.getTableDataItem(nomRow, 20));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 21)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 22)));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");

			// �ҏW��ʂ̕\��
			dialog.setValues(map);

			dialog.show();

			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}

			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);

			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("cbkCbkCode").toString(), INSERT_KBN);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
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
		addSendValues("flag", isInsert ? "insert" : "update");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// ��s�����R�[�h
		addSendValues("cbkCbkCode", (String) map.get("cbkCbkCode"));
		// ��s��������
		addSendValues("cbkName", (String) map.get("cbkName"));
		// �ʉ݃R�[�h
		addSendValues("curCode", (String) map.get("curCode"));
		// ��s�R�[�h
		addSendValues("cbkBnkCode", (String) map.get("cbkBnkCode"));
		// �x�X�R�[�h
		addSendValues("cbkStnCode", (String) map.get("cbkStnCode"));
		// �U���˗��l�R�[�h
		addSendValues("cbkIraiEmpCode", (String) map.get("cbkIraiEmpCode"));
		// �U���˗��l��
		addSendValues("cbkIraiName", (String) map.get("cbkIraiName"));
		// �U���˗��l���i�����j
		addSendValues("cbkIraiName_J", (String) map.get("cbkIraiName_J"));
		// �U���˗��l���i�p���j
		addSendValues("cbkIraiName_E", (String) map.get("cbkIraiName_E"));
		// �����ԍ�
		addSendValues("cbkYknNo", (String) map.get("cbkYknNo"));
		// �v�㕔��R�[�h
		addSendValues("cbkDepCode", (String) map.get("cbkDepCode"));
		// �ȖڃR�[�h
		addSendValues("cbkKmkCode", (String) map.get("cbkKmkCode"));
		// �⏕�ȖڃR�[�h
		addSendValues("cbkHkmCode", (String) map.get("cbkHkmCode"));
		// ����ȖڃR�[�h
		addSendValues("cbkUkmCode", (String) map.get("cbkUkmCode"));
		// �Ј��e�a�敪
		addSendValues("cbkEmpFbKbn", (String) map.get("cbkEmpFbKbn"));
		// �ЊO�e�a�敪
		addSendValues("cbkOutFbKbn", (String) map.get("cbkOutFbKbn"));
		// �a�����
		addSendValues("cbkYknKbn", (String) map.get("cbkYknKbn"));
		// �J�n�N����
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N����
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�̐ݒ�
		addSendValues("prgID", PROGRAM_ID);

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param cbkCbkCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 * @throws ParseException
	 */
	protected void modifySpreadRow(String cbkCbkCode, int updKbn) throws TRequestException, IOException, ParseException {
		Vector<Vector> cells = setModifyCell(cbkCbkCode, updKbn);
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
	 * @param cbkCbkCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 * @throws ParseException
	 */
	protected Vector<Vector> setModifyCell(String cbkCbkCode, int updKbn) throws IOException, TRequestException,
		ParseException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginCbkCbkCode", Util.avoidNull(cbkCbkCode));
		// �J�n�R�[�h�̐ݒ�
		addSendValues("endCbkCbkCode", Util.avoidNull(cbkCbkCode));
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
			// ���ʂ̓��e���擾����
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// ���ʂ�ǉ�����
			for (int column = 0; dataIte.hasNext(); column++) {
				// ���ʏW��ǉ�����
				colum.add(column, (String) dataIte.next());
			}

			if (Util.isNullOrEmpty(colum.get(21))) {
				colum.set(21, "");
			} else {
				colum.set(21, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(21))));
			}

			if (Util.isNullOrEmpty(colum.get(22))) {
				colum.set(22, "");
			} else {
				colum.set(22, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(22))));
			}

			String value, text;

			// �a�����
			value = colum.get(13);
			text = (String) yknKbnMap.get(value);
			colum.set(23, value);
			colum.set(13, text);

			// �Ј��e�a�敪 0:�Ј��e�a�ȊO 1:�Ј��e�a�p
			value = colum.get(15);
			text = ("0".equals(value) ? this.getWord("C02148") : this.getWord("C02149"));
			colum.set(24, value);
			colum.set(15, text);

			// �ЊO�e�a�敪
			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C02150") : this.getWord("C02151"));
			colum.set(25, value);
			colum.set(16, text);
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
		try {
			if (!this.showConfirmMessage(panel, "Q00007", "")) {
				return;
			}

			// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			// ��ЃR�[�h�̎擾
			String kaiCode = (String) model.getTableDataItem(nomRow, 0);
			// ��s�����R�[�h�̎擾
			String cbkCbkCode = (String) model.getTableDataItem(nomRow, 1);
			// ������ʂ̐ݒ�
			addSendValues("flag", "delete");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", kaiCode);
			// ��s�����R�[�h�̐ݒ�
			addSendValues("cbkCbkCode", cbkCbkCode);

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}

			deleteSpreadRow();

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
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
	protected boolean find() {
		try {
			String beginBankAccount = panel.ctrlBeginBankAccount.getValue();
			String endBankAccount = panel.ctrlEndBankAccount.getValue();

			if (Util.isSmallerThen(beginBankAccount, endBankAccount) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginBankAccount.getField().requestFocus();
				showMessage(panel, "W00036", "C00857 ");
				return false;
			}

			// �\���f�[�^�̎擾
			return reflesh();

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
			return false;
		}
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @return boolean
	 * @throws IOException
	 * @throws ParseException
	 */
	protected boolean reflesh() throws IOException, ParseException {

		boolean dataExists = true;

		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginCbkCbkCode = panel.ctrlBeginBankAccount.getValue();
		// �I���R�[�h�̎擾
		String endCbkCbkCode = panel.ctrlEndBankAccount.getValue();

		beginCbkCbkCode = beginCbkCbkCode.trim();
		endCbkCbkCode = endCbkCbkCode.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginCbkCbkCode", Util.avoidNull(beginCbkCbkCode));
		// �J�n�R�[�h�̐ݒ�
		addSendValues("endCbkCbkCode", Util.avoidNull(endCbkCbkCode));

		// ���M
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
			panel.ctrlBeginBankAccount.getField().requestFocus();
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

			if (Util.isNullOrEmpty(colum.get(21))) {
				colum.set(21, "");
			} else {
				colum.set(21, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(21))));
			}

			if (Util.isNullOrEmpty(colum.get(22))) {
				colum.set(22, "");
			} else {
				colum.set(22, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(22))));
			}

			String value, text;

			// �a�����
			value = colum.get(13);
			text = (String) yknKbnMap.get(value);
			colum.set(23, value);
			colum.set(13, text);

			// �Ј��e�a�敪 0:�Ј��e�a�ȊO 1:�Ј��e�a�p
			value = colum.get(15);
			text = ("0".equals(value) ? this.getWord("C02148") : this.getWord("C02149"));
			colum.set(24, value);
			colum.set(15, text);

			// �ЊO�e�a�敪
			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C02150") : this.getWord("C02151"));
			colum.set(25, value);
			colum.set(16, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);
		panel.setBtnLock(cells.size() > 0);

		return dataExists;
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */
	void outptExcel() {
		try {
			panel.ctrlBeginBankAccount.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel, "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();

			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginCbkCbkCode", panel.ctrlBeginBankAccount.getValue()); // �J�n
			conds.put("endCbkCbkCode", panel.ctrlEndBankAccount.getValue()); // �I��
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel, e, "E00009");
		}
	}
}
