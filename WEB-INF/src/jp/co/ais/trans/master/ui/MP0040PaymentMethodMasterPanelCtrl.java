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
 * �x�����@�}�X�^��ʃR���g���[��
 * 
 * @author ISFnet China
 */
public class MP0040PaymentMethodMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MP0040";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MP0040PaymentMethodMasterServlet";

	/** �V�K�ƕ��ʋ敪 */
	private static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	private static int UPD_KBN = 1;

	/** �p�l�� */
	protected MP0040PaymentMethodMasterPanel panel;

	protected Map hohNaiCodeMap;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	/**
	 * �R���X�g���N�^.
	 */

	public MP0040PaymentMethodMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MP0040PaymentMethodMasterPanel(this);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
	}

	/**
	 * �p�l��������
	 */
	private void init() {
		// �J�n�R�[�h�̐ݒ�
		ref1 = new REFDialogCtrl(panel.ctrlBeginPaymentMethod, panel.getParentFrame());
		ref1.setColumnLabels("C00864", "C00865", "C00866");
		ref1.setTargetServlet("MP0040PaymentMethodMasterServlet");
		ref1.setTitleID("C02350");
		ref1.setShowsMsgOnError(false);

		panel.ctrlBeginPaymentMethod.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}

		});

		ref1.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndPaymentMethod.getField().getText());
				keys.put("kind", "PaymentMethod");

				return keys;
			}

		});

		// �I���R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(panel.ctrlEndPaymentMethod, panel.getParentFrame());
		ref2.setColumnLabels("C00864", "C00865", "C00866");
		ref2.setTargetServlet("MP0040PaymentMethodMasterServlet");
		ref2.setTitleID("C02350");
		ref2.setShowsMsgOnError(false);

		panel.ctrlEndPaymentMethod.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}

		});

		ref2.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginPaymentMethod.getField().getText());
				keys.put("kind", "PaymentMethod");
				return keys;
			}

		});

		// ����̎w�肳�ꂽ���̂�\������
		hohNaiCodeMap = new LinkedHashMap();
		hohNaiCodeMap.put("01", getWord("C02135"));
		hohNaiCodeMap.put("03", getWord("C02136"));
		hohNaiCodeMap.put("04", getWord("C02137"));
		hohNaiCodeMap.put("10", getWord("C02138"));
		hohNaiCodeMap.put("11", getWord("C00154"));
		hohNaiCodeMap.put("12", getWord("C02139"));
		hohNaiCodeMap.put("13", getWord("C02140"));
		hohNaiCodeMap.put("14", getWord("C02141"));
		hohNaiCodeMap.put("15", getWord("C00230"));
		hohNaiCodeMap.put("16", getWord("C02142"));
		hohNaiCodeMap.put("17", getWord("C00331"));
		hohNaiCodeMap.put("18", getWord("C02143"));
		hohNaiCodeMap.put("19", getWord("C02144"));
		hohNaiCodeMap.put("99", getWord("C00338"));

		panel.chkEmployeePayment.setSelected(true);
		panel.chkExternalPayment.setSelected(true);

	}

	/**
	 * �p�l���擾
	 * 
	 * @return �Ǘ��}�X�^�p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * Dialog����
	 * 
	 * @param titleId
	 * @return �x�����@�}�X�^�̃_�C�A���O
	 */
	protected MP0040EditDisplayDialogCtrl createEditDisplayDialogCtrl(String titleId) {
		return new MP0040EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			MP0040EditDisplayDialogCtrl dialog = createEditDisplayDialogCtrl("C01698");
			dialog.setHohNaiCodeMap(hohNaiCodeMap);
			// �ҏW��ʂ̕\��
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			unlockBtn();
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("hohHohCode").toString(), INSERT_KBN);
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
			MP0040EditDisplayDialogCtrl dialog = createEditDisplayDialogCtrl("C00977");
			dialog.setHohNaiCodeMap(hohNaiCodeMap);
			// ���O�s���擾����
			int nomRow = panel.ssJournal.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssJournal.getDataSource();
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �x�����@�R�[�h�̐ݒ�
			map.put("hohHohCode", model.getTableDataItem(nomRow, 1));
			// �x�����@���̐ݒ�
			map.put("hohHohName", model.getTableDataItem(nomRow, 2));
			// �x�����@���̐ݒ�
			map.put("hohHohName_K", model.getTableDataItem(nomRow, 3));
			// �ȖڃR�[�h�̐ݒ�
			map.put("hohKmkCode", model.getTableDataItem(nomRow, 4));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("hohHkmCode", model.getTableDataItem(nomRow, 5));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("hohUkmCode", model.getTableDataItem(nomRow, 6));
			// �v�㕔��R�[�h�̐ݒ�
			map.put("hohDepCode", model.getTableDataItem(nomRow, 7));
			// �x���Ώۋ敪�̐ݒ�
			map.put("hohSihKbn", model.getTableDataItem(nomRow, 12));
			// �x�������R�[�h�̐ݒ�
			map.put("hohNaiCode", model.getTableDataItem(nomRow, 13));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			// ������ʂ̐ݒ�
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷���
			dialog.setValues(map);
			// �ҏW��ʂ�\�������
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("hohHohCode").toString(), UPD_KBN);
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
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			// �f�[�^�`�F�b�N
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
			MP0040EditDisplayDialogCtrl dialog = createEditDisplayDialogCtrl("C01738");
			dialog.setHohNaiCodeMap(hohNaiCodeMap);
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
			// �x�����@�R�[�h�̐ݒ�
			map.put("hohHohCode", model.getTableDataItem(nomRow, 1));
			// �x�����@���̐ݒ�
			map.put("hohHohName", model.getTableDataItem(nomRow, 2));
			// �x�����@�������̂̐ݒ�
			map.put("hohHohName_K", model.getTableDataItem(nomRow, 3));
			// �ȖڃR�[�h�̐ݒ�
			map.put("hohKmkCode", model.getTableDataItem(nomRow, 4));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("hohHkmCode", model.getTableDataItem(nomRow, 5));
			// ����ȖڃR�[�h�̐ݒ�
			map.put("hohUkmCode", model.getTableDataItem(nomRow, 6));
			// �v�㕔��R�[�h�̐ݒ�
			map.put("hohDepCode", model.getTableDataItem(nomRow, 7));
			// �x���Ώۋ敪�̐ݒ�
			map.put("hohSihKbn", model.getTableDataItem(nomRow, 12));
			// �x�������R�[�h�̐ݒ�
			map.put("hohNaiCode", model.getTableDataItem(nomRow, 13));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
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
			modifySpreadRow(dialog.getDataList().get("hohHohCode").toString(), INSERT_KBN);
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
		// ��ЃR�[�h�̐ݒ�
		super.addSendValues("kaiCode", getLoginUserCompanyCode());
		// �x�����@�R�[�h�̐ݒ�
		super.addSendValues("hohHohCode", (String) map.get("hohHohCode"));
		// �x�����@���̐ݒ�
		super.addSendValues("hohHohName", (String) map.get("hohHohName"));
		// �x�����@�������̂̐ݒ�
		super.addSendValues("hohHohName_K", (String) map.get("hohHohName_K"));
		// �ȖڃR�[�h�̐ݒ�
		super.addSendValues("hohKmkCode", (String) map.get("hohKmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		super.addSendValues("hohHkmCode", (String) map.get("hohHkmCode"));
		// ����ȖڃR�[�h�̐ݒ�
		super.addSendValues("hohUkmCode", (String) map.get("hohUkmCode"));
		// �v�㕔��R�[�h�̐ݒ�
		super.addSendValues("hohDepCode", (String) map.get("hohDepCode"));
		// �x���Ώۋ敪�̐ݒ�
		super.addSendValues("hohSihKbn", (String) map.get("hohSihKbn"));
		// �x�������R�[�h�̐ݒ�
		super.addSendValues("hohNaiCode", (String) map.get("hohNaiCode"));
		// �J�n�N�����̐ݒ�
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
		super.addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param hohHohCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	private void modifySpreadRow(String hohHohCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(hohHohCode, updKbn);
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
	 * @param hohHohCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String hohHohCode, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginHohHohCode", Util.avoidNull(hohHohCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endHohHohCode", Util.avoidNull(hohHohCode));

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
			try {
				colum.set(10, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(10), ex);
			}
			// �x�������R�[�h
			String value;
			String text;

			value = colum.get(8);
			text = ("0".equals(value) ? getWord("C01119") : getWord("C01124"));
			colum.add(12, value);
			colum.set(8, text);

			value = colum.get(9);
			text = (String) hohNaiCodeMap.get(value);
			colum.add(13, value);
			colum.set(9, text);
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
				// �x�����@�R�[�h�̎擾
				String hohHohCode = (String) model.getTableDataItem(nomRow, 1);
				// ������ʂ̐ݒ�
				super.addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				super.addSendValues("kaiCode", kaiCode);
				// �x�����@�R�[�h�̐ݒ�
				super.addSendValues("hohHohCode", hohHohCode);

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
		String beginPaymentMethod = panel.ctrlBeginPaymentMethod.getValue().toString();
		String endPaymentmethod = panel.ctrlEndPaymentMethod.getValue().toString();

		try {
			if (!panel.chkEmployeePayment.isSelected() && !panel.chkExternalPayment.isSelected()) {
				// �x�����b�Z�[�W�\��
				panel.chkEmployeePayment.requestFocus();
				showMessage(panel, "I00041", "C01096");
				return false;
			}

			if (Util.isSmallerThen(beginPaymentMethod, endPaymentmethod) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginPaymentMethod.getField().requestFocus();
				showMessage(panel, "W00036", "C00233");
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
			panel.ctrlBeginPaymentMethod.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��ЃR�[�h�̎擾
			conds.put("kaiCode", getLoginUserCompanyCode());
			// �J�n�R�[�h�̎擾
			conds.put("beginHohHohCode", panel.ctrlBeginPaymentMethod.getValue());
			// �I���R�[�h�̎擾
			conds.put("endHohHohCode", panel.ctrlEndPaymentMethod.getValue());
			// �x���Ώۋ敪:�Ј��x��
			conds.put("includeEmployeePayment", panel.chkEmployeePayment.isSelected() ? "1" : "0");
			// �x���Ώۋ敪:�ЊO�x��
			conds.put("includeExternalPayment", panel.chkExternalPayment.isSelected() ? "1" : "0");
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
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginHohHohCode = panel.ctrlBeginPaymentMethod.getValue();
		// �I���R�[�h�̎擾
		String endHohHohCode = panel.ctrlEndPaymentMethod.getValue();

		beginHohHohCode = beginHohHohCode.trim();
		endHohHohCode = endHohHohCode.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginHohHohCode", Util.avoidNull(beginHohHohCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endHohHohCode", Util.avoidNull(endHohHohCode));
		// �x���Ώۋ敪:�Ј��x��
		addSendValues("includeEmployeePayment", panel.chkEmployeePayment.isSelected() ? "1" : "0");
		// �x���Ώۋ敪:�ЊO�x��
		addSendValues("includeExternalPayment", panel.chkExternalPayment.isSelected() ? "1" : "0");

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		Iterator recordIte = getResultList().iterator();
		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			panel.ctrlBeginPaymentMethod.getField().requestFocus();
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
				colum.set(10, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(10), ex);
			}
			// �x�������R�[�h
			String value;
			String text;

			value = colum.get(8);
			text = ("0".equals(value) ? getWord("C01119") : getWord("C01124"));
			colum.add(12, value);
			colum.set(8, text);

			value = colum.get(9);
			text = (String) hohNaiCodeMap.get(value);
			colum.add(13, value);
			colum.set(9, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);
		panel.setBtnLock(cells.size() > 0);
		return dataExists;
	}
}
