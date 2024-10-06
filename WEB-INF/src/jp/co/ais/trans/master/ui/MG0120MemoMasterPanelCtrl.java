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
 * �E�v�}�X�^
 */
public class MG0120MemoMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0120";

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
		return "MG0120MemoMasterServlet";
	}

	/** �p�l�� */
	protected MG0120MemoMasterPanel panel;

	protected Map dataKbnMap;

	private REFDialogCtrl refBeginMemo;

	private REFDialogCtrl refEndMemo;

	/**
	 * �R���X�g���N�^.
	 */

	public MG0120MemoMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0120MemoMasterPanel(this);
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
		refBeginMemo = new REFDialogCtrl(panel.ctrlBeginMemo, panel.getParentFrame());
		refBeginMemo.setColumnLabelIDs("C00564", "C01741", "C00566");
		refBeginMemo.setTargetServlet(getServletName());
		refBeginMemo.setTitleID("C02349");
		refBeginMemo.setShowsMsgOnError(false);
		refBeginMemo.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndMemo.getField().getText());
				keys.put("kind", "Memo");
				return keys;
			}
		});

		panel.ctrlBeginMemo.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginMemo.refreshName();

				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		refEndMemo = new REFDialogCtrl(panel.ctrlEndMemo, panel.getParentFrame());
		refEndMemo.setColumnLabelIDs("C00564", "C01741", "C00566");
		refEndMemo.setTargetServlet(getServletName());
		refEndMemo.setTitleID("C02349");
		refEndMemo.setShowsMsgOnError(false);
		refEndMemo.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginMemo.getField().getText());
				keys.put("kind", "Memo");
				return keys;
			}
		});

		panel.ctrlEndMemo.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndMemo.refreshName();
				return true;
			}
		});

		// ����̎w�肳�ꂽ���̂�\������
		dataKbnMap = new LinkedHashMap();
		dataKbnMap.put("11", getWord("C00419"));
		dataKbnMap.put("12", getWord("C00264"));
		dataKbnMap.put("13", getWord("C00469"));
		dataKbnMap.put("14", getWord("C02079"));
		dataKbnMap.put("15", getWord("C02080"));
		dataKbnMap.put("21", getWord("C02081"));
		dataKbnMap.put("22", getWord("C02082"));
		dataKbnMap.put("23", getWord("C00314"));
		dataKbnMap.put("24", getWord("C02083"));
		dataKbnMap.put("25", getWord("C02084"));
		dataKbnMap.put("26", getWord("C02085"));
		dataKbnMap.put("27", getWord("C02086"));
		dataKbnMap.put("28", getWord("C04290"));
		dataKbnMap.put("2E", getWord("C02088"));
		dataKbnMap.put("2T", getWord("C02089"));
		dataKbnMap.put("31", getWord("C01978"));
		dataKbnMap.put("32", getWord("C01979"));
		dataKbnMap.put("33", getWord("C02090"));
		dataKbnMap.put("40", getWord("C00096"));
		dataKbnMap.put("41", getWord("C02091"));
		dataKbnMap.put("42", getWord("C02092"));
		dataKbnMap.put("51", getWord("C02093"));
		dataKbnMap.put("52", getWord("C02094"));
		dataKbnMap.put("61", getWord("C02095"));
		dataKbnMap.put("62", getWord("C02096"));

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
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			MG0120EditDisplayDialogCtrl dialog = new MG0120EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			dialog.setDataKbnMap(dataKbnMap);
			// �ҏW��ʂ̕\��
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �I���s�ێ�����
			String tekCode = dialog.getDataList().get("tekCode").toString();
			String tekKbn = dialog.getDataList().get("tekKbn").toString();
			modifySpreadRow(tekCode, tekKbn, INSERT_KBN);
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
			MG0120EditDisplayDialogCtrl dialog = new MG0120EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			dialog.setDataKbnMap(dataKbnMap);
			// ���O�s���擾����
			int nomRow = panel.ssMemo.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssMemo.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �E�v�敪�̐ݒ�
			map.put("tekKbn", model.getTableDataItem(nomRow, 8));
			// �ް��敪�̐ݒ�
			map.put("dataKbn", model.getTableDataItem(nomRow, 9));
			// �E�v�R�[�h�̐ݒ�
			map.put("tekCode", model.getTableDataItem(nomRow, 3));
			// �E�v���̂̐ݒ�
			map.put("tekName", model.getTableDataItem(nomRow, 4));
			// �E�v���̂̐ݒ�
			map.put("tekName_K", model.getTableDataItem(nomRow, 5));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 7)));
			// ������ʂ̐ݒ�
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ�\������
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂����
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			String tekCode = dialog.getDataList().get("tekCode").toString();
			String tekKbn = dialog.getDataList().get("tekKbn").toString();
			modifySpreadRow(tekCode, tekKbn, UPD_KBN);
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
			MG0120EditDisplayDialogCtrl dialog = new MG0120EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			dialog.setDataKbnMap(dataKbnMap);
			// ���O�s���擾����
			int nomRow = panel.ssMemo.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssMemo.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �E�v�敪�̐ݒ�
			map.put("tekKbn", model.getTableDataItem(nomRow, 8));
			// �ް��敪�̐ݒ�
			map.put("dataKbn", model.getTableDataItem(nomRow, 9));
			// �E�v�R�[�h�̐ݒ�
			map.put("tekCode", model.getTableDataItem(nomRow, 3));
			// �E�v���̂̐ݒ�
			map.put("tekName", model.getTableDataItem(nomRow, 4));
			// �E�v�������̂̐ݒ�
			map.put("tekName_K", model.getTableDataItem(nomRow, 5));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 7)));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ�\������
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);

			String tekCode = dialog.getDataList().get("tekCode").toString();
			String tekKbn = dialog.getDataList().get("tekKbn").toString();
			modifySpreadRow(tekCode, tekKbn, INSERT_KBN);

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
		addSendValues("flag", isInsert ? "insert" : "update");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �ް��敪�̐ݒ�
		addSendValues("dataKbn", (String) map.get("dataKbn"));
		// �E�v�敪�̐ݒ�
		addSendValues("tekKbn", (String) map.get("tekKbn"));
		// �E�v�R�[�h�̐ݒ�
		addSendValues("tekCode", (String) map.get("tekCode"));
		// �E�v���̂̐ݒ�
		addSendValues("tekName", (String) map.get("tekName"));
		// �E�v�������̂̐ݒ�
		addSendValues("tekName_K", (String) map.get("tekName_K"));
		// �J�n�N�����̐ݒ�
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
		addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			errorHandler(panel);
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param tekCode
	 * @param tekKbn
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String tekCode, String tekKbn, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(tekCode, tekKbn, updKbn);
		TTable ssPanelSpread = panel.ssMemo;
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
		lockBtn(panel.ssMemo.getDataSource().getNumRows() != 0);
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
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param tekCode
	 * @param tekKbn
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String tekCode, String tekKbn, int updKbn) throws IOException,
		TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginTekCode", Util.avoidNull(tekCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endTekCode", Util.avoidNull(tekCode));
		// �E�v�敪�̐ݒ�
		addSendValues("tekKbn", Util.avoidNull(tekKbn));

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssMemo.getDataSource();
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

			try {
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
				colum.set(7, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(7))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
			}
			String value, text;
			// �E�v�敪
			value = colum.get(1);
			text = ("0".equals(value) ? this.getWord("C00569") : this.getWord("C00119"));
			colum.add(8, value);
			colum.set(1, text);

			// �ް��敪
			colum.add(9, "");
			value = colum.get(2);
			text = (String) dataKbnMap.get(value);
			colum.set(9, value);
			colum.set(2, text);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssMemo.getCurrentRow(), colum);
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
				int nomRow = panel.ssMemo.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssMemo.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// �E�v�敪�̎擾
				String tekKbn = (String) model.getTableDataItem(nomRow, 8);
				// �E�v�R�[�h�̎擾
				String tekCode = (String) model.getTableDataItem(nomRow, 3);
				// ������ʂ̐ݒ�
				addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				addSendValues("kaiCode", kaiCode);
				// �E�v�敪�̐ݒ�
				addSendValues("tekKbn", tekKbn);
				// �E�v�R�[�h�̐ݒ�
				addSendValues("tekCode", tekCode);

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
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {

		String beginTekCode = panel.ctrlBeginMemo.getValue().toString();
		String endTekCode = panel.ctrlEndMemo.getValue().toString();
		try {
			if (!Util.isSmallerThen(beginTekCode, endTekCode)) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginMemo.getField().requestFocus();
				showMessage(panel, "W00036", "C00384");
				return false;
			}
			// �\���f�[�^�̎擾
			return reflesh();
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
		return false;
	}

	/**
	 * �폜�ꍇ�A�X�v���b�h�X�V
	 */
	private void deleteSpreadRow() {
		int row = panel.ssMemo.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssMemo.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssMemo.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssMemo.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssMemo.getVertSB().setValue(0);
			panel.ssMemo.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssMemo.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssMemo.setRowSelection(row, row);
		panel.ssMemo.setCurrentCell(row, 0);
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
		// �J�n�R�[�h�̐ݒ�
		String beginTekCode = panel.ctrlBeginMemo.getValue();
		// �I���R�[�h�̎擾
		String endTekCode = panel.ctrlEndMemo.getValue();
		// �E�v�敪�̎擾
		String tekKbn = "";
		if (panel.rdoSlipMemo.isSelected()) {
			tekKbn = "0";
		} else if (panel.rdoRowMemo.isSelected()) {
			tekKbn = "1";
		}
		beginTekCode = beginTekCode.trim();
		endTekCode = endTekCode.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginTekCode", Util.avoidNull(beginTekCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endTekCode", Util.avoidNull(endTekCode));
		// �E�v�敪�̐ݒ�
		addSendValues("tekKbn", Util.avoidNull(tekKbn));

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			panel.ctrlBeginMemo.getField().requestFocus();
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

			try {
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
				colum.set(7, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(7))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
			}
			String value, text;
			// �E�v�敪
			value = colum.get(1);
			text = ("0".equals(value) ? this.getWord("C00569") : this.getWord("C00119"));
			colum.add(8, value);
			colum.set(1, text);

			// �ް��敪
			colum.add(9, "");
			value = colum.get(2);
			text = (String) dataKbnMap.get(value);
			colum.set(9, value);
			colum.set(2, text);

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
			panel.ctrlBeginMemo.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			// �E�v�敪�̎擾
			String tekKbn = "";
			if (panel.rdoSlipMemo.isSelected()) {
				tekKbn = "0";
			} else if (panel.rdoRowMemo.isSelected()) {
				tekKbn = "1";
			}

			Map conds = new HashMap();
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��ЃR�[�h�̎擾
			conds.put("kaiCode", getLoginUserCompanyCode());
			// �J�n�R�[�h�̎擾
			conds.put("beginTekCode", panel.ctrlBeginMemo.getField().getText());
			// �I���R�[�h�̎擾
			conds.put("endTekCode", panel.ctrlEndMemo.getField().getText());
			// ����R�[�h�̎擾
			conds.put("langCode", getLoginLanguage());
			// �E�v�敪�̑fy��
			conds.put("tekKbn", tekKbn);
			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
