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
 * ����Ń}�X�^
 */
public class MG0130ConsumptionTaxMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0130";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/** �p�l�� */
	protected MG0130ConsumptionTaxMasterPanel panel;

	protected Map<String, String> usKbnMap;

	private REFDialogCtrl refBeginConsumptionTax;

	private REFDialogCtrl refEndConsumptionTax;

	MG0130EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */

	public MG0130ConsumptionTaxMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0130ConsumptionTaxMasterPanel(this);
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

		// ����̎w�肳�ꂽ���̂�\������
		usKbnMap = new LinkedHashMap();
		usKbnMap.put("0", getWord("C02103"));
		usKbnMap.put("1", getWord("C00026"));
		usKbnMap.put("2", getWord("C00201"));

	}

	void init() {
		// �J�n�R�[�h�̐ݒ�
		refBeginConsumptionTax = new REFDialogCtrl(panel.ctrlBeginConsumptionTax, panel.getParentFrame());
		refBeginConsumptionTax.setTargetServlet(TARGET_SERVLET);
		refBeginConsumptionTax.setTitleID("C02324");
		refBeginConsumptionTax.setColumnLabels("C00573", "C00775", "C00828");
		refBeginConsumptionTax.setShowsMsgOnError(false);
		refBeginConsumptionTax.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "ConsumptionTax");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndConsumptionTax.getField().getText());
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		panel.ctrlBeginConsumptionTax.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginConsumptionTax.refreshName();
				return true;
			}
		});

		// �I���R�[�h�̐ݒ�

		refEndConsumptionTax = new REFDialogCtrl(panel.ctrlEndConsumptionTax, panel.getParentFrame());
		refEndConsumptionTax.setTargetServlet(TARGET_SERVLET);
		refEndConsumptionTax.setTitleID("C02324");
		refEndConsumptionTax.setColumnLabels("C00573", "C00775", "C00828");
		refEndConsumptionTax.setShowsMsgOnError(false);
		refEndConsumptionTax.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "ConsumptionTax");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginConsumptionTax.getField().getText());
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		panel.ctrlEndConsumptionTax.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndConsumptionTax.refreshName();
				return true;
			}
		});

	}

	/**
	 * �p�l���擾
	 * 
	 * @return ����Ń}�X�^�p�l��
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
		dialog = new MG0130EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			createEditDisplayDialog("C01698");
			dialog.setUsKbnMap(usKbnMap);
			// �ҏW��ʂ̕\��
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			unlockBtn();
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("zeiCode").toString(), INSERT_KBN);
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
			dialog.setUsKbnMap(usKbnMap);
			// ���O�s���擾����
			int nomRow = panel.ssConsumptionTax.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssConsumptionTax.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// �f�[�^�W���擾����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ����ŃR�[�h�̐ݒ�
			map.put("zeiCode", model.getTableDataItem(nomRow, 1));
			// ����Ŗ��̂̐ݒ�
			map.put("zeiName", model.getTableDataItem(nomRow, 2));
			// ����ŗ��̂̐ݒ�
			map.put("zeiName_S", model.getTableDataItem(nomRow, 3));
			// ����Ō������̂̐ݒ�
			map.put("zeiName_K", model.getTableDataItem(nomRow, 4));
			// ����d���敪�̐ݒ�
			map.put("usKbn", model.getTableDataItem(nomRow, 14));
			// ����ŗ��̐ݒ�
			map.put("zeiRate", model.getTableDataItem(nomRow, 6));
			// ����Ōv�Z���o�͏����̐ݒ�
			String szeiKeiKbn = String.valueOf(model.getTableDataItem(nomRow, 7));
			if (szeiKeiKbn.equals(this.getWord("C00282"))) {
				szeiKeiKbn = "";
				map.put("szeiKeiKbn", szeiKeiKbn);
			} else {
				map.put("szeiKeiKbn", model.getTableDataItem(nomRow, 7));
			}
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// ������ʂ̐ݒ��
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷���
			dialog.setValues(map);
			// �ҏW��ʂ�\�������
			dialog.show(false);
			// �ҏW��ʂ��J���Ă��܂����
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("zeiCode").toString(), UPD_KBN);
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
			dialog.setUsKbnMap(usKbnMap);
			// ���O�s���擾����
			int nomRow = panel.ssConsumptionTax.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssConsumptionTax.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ����ŃR�[�h�̐ݒ�
			map.put("zeiCode", model.getTableDataItem(nomRow, 1));
			// ����Ŗ��̂̐ݒ�
			map.put("zeiName", model.getTableDataItem(nomRow, 2));
			// ����ŗ��̂̐ݒ�
			map.put("zeiName_S", model.getTableDataItem(nomRow, 3));
			// ����Ō������̂̐ݒ�
			map.put("zeiName_K", model.getTableDataItem(nomRow, 4));
			// ����d���敪�̐ݒ�
			map.put("usKbn", model.getTableDataItem(nomRow, 14));
			// ����ŗ��̐ݒ�
			map.put("zeiRate", model.getTableDataItem(nomRow, 6));
			// ����Ōv�Z���o�͏����̐ݒ�
			String szeiKeiKbn = String.valueOf(model.getTableDataItem(nomRow, 7));
			if (szeiKeiKbn.equals(this.getWord("C00282"))) {
				szeiKeiKbn = "";
				map.put("szeiKeiKbn", szeiKeiKbn);
			} else {
				map.put("szeiKeiKbn", model.getTableDataItem(nomRow, 7));
			}
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// ������ʂ̐ݒ��
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷���
			dialog.setValues(map);
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);

			modifySpreadRow(dialog.getDataList().get("zeiCode").toString(), INSERT_KBN);
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
		// ��ЃR�[�h�̐ݒ�
		super.addSendValues("kaiCode", getLoginUserCompanyCode());
		// ����ŃR�[�h�̐ݒ�
		super.addSendValues("zeiCode", (String) map.get("zeiCode"));
		// ����Ŗ��̂̐ݒ�
		super.addSendValues("zeiName", (String) map.get("zeiName"));
		// ����ŗ��̂̐ݒ�
		super.addSendValues("zeiName_S", (String) map.get("zeiName_S"));
		// ����Ō������̂̐ݒ�
		super.addSendValues("zeiName_K", (String) map.get("zeiName_K"));
		// ����d���敪�̐ݒ�
		super.addSendValues("usKbn", (String) map.get("usKbn"));
		// ����ŗ��̐ݒ�
		super.addSendValues("zeiRate", (String) map.get("zeiRate"));
		// ����Ōv�Z���o�͏����̐ݒ�
		super.addSendValues("szeiKeiKbn", (String) map.get("szeiKeiKbn"));
		// �J�n�N�����̐ݒ�
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�̐ݒ�
		super.addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel);
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param zeiCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String zeiCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(zeiCode, updKbn);
		TTable ssPanelSpread = panel.ssConsumptionTax;
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
		lockBtn(panel.ssConsumptionTax.getDataSource().getNumRows() != 0);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param zeiCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String zeiCode, int updKbn) throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginZeiCode", Util.avoidNull(zeiCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endZeiCode", Util.avoidNull(zeiCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssConsumptionTax.getDataSource();
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

			String value, text;
			// ����d���敪
			value = colum.get(5);
			text = usKbnMap.get(value);
			colum.add(14, value);
			colum.set(5, text);

			String num;
			DecimalFormat format = new DecimalFormat("#0.0");
			num = format.format(Float.valueOf((colum.get(6))));
			colum.set(6, num);
			// ����Ōv�Z���o�͏����敪
			value = colum.get(7);
			if (Util.isNullOrEmpty(value)) {
				colum.set(7, this.getWord("C00282"));
			} else {
				colum.set(7, value);
			}
			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssConsumptionTax.getCurrentRow(), colum);
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
				int nomRow = panel.ssConsumptionTax.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssConsumptionTax.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// ����ŃR�[�h�̎擾
				String zeiCode = (String) model.getTableDataItem(nomRow, 1);
				// ������ʂ̐ݒ�
				super.addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				super.addSendValues("kaiCode", kaiCode);
				// ����ŃR�[�h�̐ݒ�
				super.addSendValues("zeiCode", zeiCode);

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
		int row = panel.ssConsumptionTax.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssConsumptionTax.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssConsumptionTax.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssConsumptionTax.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssConsumptionTax.getVertSB().setValue(0);
			panel.ssConsumptionTax.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssConsumptionTax.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssConsumptionTax.setRowSelection(row, row);
		panel.ssConsumptionTax.setCurrentCell(row, 0);
	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {
		String beginConsumptionTax = panel.ctrlBeginConsumptionTax.getValue().toString();
		String endConsumptionTax = panel.ctrlEndConsumptionTax.getValue().toString();

		try {
			// �x�����b�Z�[�W�\��
			if (Util.isSmallerThen(beginConsumptionTax, endConsumptionTax) == false) {
				panel.ctrlBeginConsumptionTax.getField().requestFocus();
				showMessage(panel, "W00036", "C00286");
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
	 * ��ʃ��t���b�V��
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	private boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginZeiCode = panel.ctrlBeginConsumptionTax.getValue();
		// �I���R�[�h�̎擾
		String endZeiCode = panel.ctrlEndConsumptionTax.getValue();

		beginZeiCode = beginZeiCode.trim();
		endZeiCode = endZeiCode.trim();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginZeiCode", Util.avoidNull(beginZeiCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endZeiCode", Util.avoidNull(endZeiCode));
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
			panel.ctrlBeginConsumptionTax.getField().requestFocus();
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

			String value, text;
			// ����d���敪
			value = colum.get(5);
			text = usKbnMap.get(value);
			colum.add(14, value);
			colum.set(5, text);

			String num;
			DecimalFormat format = new DecimalFormat("#0.0");
			num = format.format(Float.valueOf((colum.get(6))));
			colum.set(6, num);
			// ����Ōv�Z���o�͏����敪
			value = colum.get(7);
			if (Util.isNullOrEmpty(value)) {
				colum.set(7, this.getWord("C00282"));
			} else {
				colum.set(7, value);
			}
			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
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
			panel.ctrlBeginConsumptionTax.getField().requestFocus();
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
			conds.put("beginZeiCode", panel.ctrlBeginConsumptionTax.getValue());
			// �I���R�[�h�̎擾
			conds.put("endZeiCode", panel.ctrlEndConsumptionTax.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
