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
 * �ʉ݃}�X�^��ʃR���g���[��
 */
public class MG0300CurrencyMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0300";

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
		return "MG0300CurrencyMasterServlet";
	}

	/** �p�l�� */
	protected MG0300CurrencyMasterPanel panel;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0300CurrencyMasterPanelCtrl() {
		try {
			panel = new MG0300CurrencyMasterPanel(this);
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
		ref1 = new REFDialogCtrl(panel.ctrlBeginCurrency, panel.getParentFrame());
		ref1.setColumnLabels("C00665", "C00881", "C00882");
		ref1.setTargetServlet("MG0300CurrencyMasterServlet");
		ref1.setTitleID(getWord("C01985"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new LinkedHashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCurrency.getField().getText());
				keys.put("kind", "Currency");
				return keys;
			}
		});

		panel.ctrlBeginCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		ref2 = new REFDialogCtrl(panel.ctrlEndCurrency, panel.getParentFrame());
		ref2.setColumnLabels("C00665", "C00881", "C00882");
		ref2.setTargetServlet("MG0300CurrencyMasterServlet");
		ref2.setTitleID(getWord("C01985"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new LinkedHashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCurrency.getField().getText());
				keys.put("kind", "Currency");
				return keys;
			}
		});

		panel.ctrlEndCurrency.setInputVerifier(new TInputVerifier() {

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
			MG0300EditDisplayDialogCtrl dialog = new MG0300EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");

			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("curCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			MG0300EditDisplayDialogCtrl dialog = new MG0300EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			map.put("curName", model.getTableDataItem(nomRow, 2));
			map.put("curName_S", model.getTableDataItem(nomRow, 3));
			map.put("curName_K", model.getTableDataItem(nomRow, 4));
			map.put("decKeta", model.getTableDataItem(nomRow, 7));
			map.put("ratePow", model.getTableDataItem(nomRow, 5));
			map.put("convKbn", model.getTableDataItem(nomRow, 10));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), false);

			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("curCode").toString(), UPD_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * ���ʏ���
	 */
	public void copy() {
		try {
			MG0300EditDisplayDialogCtrl dialog = new MG0300EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			map.put("curName", model.getTableDataItem(nomRow, 2));
			map.put("curName_S", model.getTableDataItem(nomRow, 3));
			map.put("curName_K", model.getTableDataItem(nomRow, 4));
			map.put("decKeta", model.getTableDataItem(nomRow, 7));
			map.put("ratePow", model.getTableDataItem(nomRow, 5));
			map.put("convKbn", model.getTableDataItem(nomRow, 10));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("curCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param curCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String curCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(curCode, updKbn);
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
	 * @param curCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String curCode, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginCurCode", Util.avoidNull(curCode));
		addSendValues("endCurCode", Util.avoidNull(curCode));

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
			Iterator dataIte = ((List) recordIte.next()).iterator();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String rate, dec;
			dec = colum.get(7);
			rate = colum.get(5);

			// �E�v�敪
			String value = colum.get(6);
			String text = ("0".equals(value) ? this.getWord("C00065") : this.getWord("C00563"));

			// ���[�g�W��
			colum.set(5, rate);
			// �����_�ȉ�����
			colum.set(7, dec);

			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
			colum.add(10, value);
			colum.set(6, text);

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
		addSendValues("flag", isInsert ? "insert" : "update");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("curCode", (String) map.get("curCode"));
		addSendValues("curName", (String) map.get("curName"));
		addSendValues("curName_S", (String) map.get("curName_S"));
		addSendValues("curName_K", (String) map.get("curName_K"));
		addSendValues("decKeta", (String) map.get("decKeta"));
		addSendValues("ratePow", (String) map.get("ratePow"));
		addSendValues("convKbn", (String) map.get("convKbn"));
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		addSendValues("prgID", PROGRAM_ID);

		if (!request(getServletName())) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel);
		}
	}

	/**
	 * �폜����
	 */
	void delete() {
		if (this.showConfirmMessage(panel, FocusButton.NO, "Q00007")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssJournal.getCurrentRow();
				TableDataModel model = panel.ssJournal.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // ��ЃR�[�h
				String curCode = (String) model.getTableDataItem(nomRow, 1); // �ʉ݃R�[�h
				addSendValues("flag", "delete");
				addSendValues("kaiCode", kaiCode);
				addSendValues("curCode", curCode);

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

		// �J�n�R�[�h�̎擾
		String strBegin = panel.ctrlBeginCurrency.getValue();
		// �I���R�[�h�̎擾
		String strEnd = panel.ctrlEndCurrency.getValue();
		try {
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginCurrency.getField().requestFocus();
				showMessage(panel, "W00036", "C00371");
				return false;
			}
			return reflesh();
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}

		return false;
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		String kaiCode = getLoginUserCompanyCode();

		String beginCurCode = panel.ctrlBeginCurrency.getValue();
		String endCurCode = panel.ctrlEndCurrency.getValue();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginCurCode", Util.avoidNull(beginCurCode));
		addSendValues("endCurCode", Util.avoidNull(endCurCode));

		// �T�[�o���̃G���[�ꍇ
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginCurrency.getField().requestFocus();
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

			String rate, dec;
			dec = colum.get(7);
			rate = colum.get(5);

			// �E�v�敪
			String value = colum.get(6);
			String text = ("0".equals(value) ? this.getWord("C00065") : this.getWord("C00563"));

			// ���[�g�W��
			colum.set(5, rate);
			// �����_�ȉ�����
			colum.set(7, dec);

			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
			colum.add(10, value);
			colum.set(6, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);

		return dataExists;
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */
	void outptExcel() {
		try {
			panel.ctrlBeginCurrency.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("langCode", getLoginLanguage());
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginCurCode", panel.ctrlBeginCurrency.getValue());
			conds.put("endCurCode", panel.ctrlEndCurrency.getValue());
			conds.put("langCode", getLoginLanguage());
			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
