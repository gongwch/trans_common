package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.math.*;
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
 * ���[�g�}�X�^��ʃR���g���[��
 */
public class MG0310RateMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0310";

	/** �V�K�ƕ��ʋ敪 */
	private static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	private static int UPD_KBN = 1;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0310RateMasterServlet";

	/** �p�l�� */
	protected MG0310RateMasterPanel panel;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	MG0310EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0310RateMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0310RateMasterPanel(this);
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

	/**
	 * ��ʏ�����
	 */
	protected void init() {
		// �J�n�R�[�h�̐ݒ�
		ref1 = new REFDialogCtrl(panel.ctrlBeginCurrency, panel.getParentFrame());
		ref1.setColumnLabels("C00665", "C00881", "C00882");
		ref1.setTargetServlet("MG0300CurrencyMasterServlet");
		ref1.setTitleID(getWord("C01985"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// map�̏�����
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCurrency.getField().getText());
				keys.put("kind", "Currency");
				// �f�[�^��Ԃ�
				return keys;
			}
		});

		panel.ctrlBeginCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(panel.ctrlEndCurrency, panel.getParentFrame());
		ref2.setColumnLabels("C00665", "C00881", "C00882");
		ref2.setTargetServlet("MG0300CurrencyMasterServlet");
		ref2.setTitleID(getWord("C01985"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// map�̏�����
				Map keys = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCurrency.getField().getText());
				keys.put("kind", "Currency");
				// �f�[�^��Ԃ�
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
	 * Dialog����
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MG0310EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
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
			String curCode = dialog.getDataList().get("curCode").toString();
			String strDate = DateUtil.toYMDHMSString((Date) dialog.getDataList().get("strDate"));
			modifySpreadRow(curCode, strDate, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel);
		}
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			createEditDisplayDialog("C00977");
			// ���O�s���擾����
			int nomRow = panel.ssRateList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssRateList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ʉ݃R�[�h�̐ݒ�
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			// �E�v�J�n���t�̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 2)));
			// ���[�g�̐ݒ�
			map.put("curRate", model.getTableDataItem(nomRow, 3));
			// ������ʂ̐ݒ�
			map.put("flag", "update");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ̕\��
			dialog.show(false);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) return;
			// �f�[�^��ҏW����
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			String curCode = dialog.getDataList().get("curCode").toString();
			String strDate = DateUtil.toYMDHMSString((Date) dialog.getDataList().get("strDate"));
			modifySpreadRow(curCode, strDate, UPD_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel);
		}
	}

	/**
	 * ���ʏ���
	 */
	public void copy() {
		try {
			createEditDisplayDialog("C01738");
			// ���O�s���擾����
			int nomRow = panel.ssRateList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssRateList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ʉ݃R�[�h�̐ݒ�
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			// �E�v�J�n���t�̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 2)));
			// ���[�g�̐ݒ�
			map.put("curRate", model.getTableDataItem(nomRow, 3));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ̕\��
			dialog.show(true);
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) return;
			// �f�[�^��ҏW����
			modify(dialog.getDataList(), true);
			// �X�v���b�h�X�V
			String curCode = dialog.getDataList().get("curCode").toString();
			String strDate = DateUtil.toYMDHMSString((Date) dialog.getDataList().get("strDate"));
			modifySpreadRow(curCode, strDate, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel);
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
		// �ʉ݃R�[�h�̐ݒ�
		super.addSendValues("curCode", (String) map.get("curCode"));
		// �E�v�J�n���t�̐ݒ�
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// ���[�g�̐ݒ�
		super.addSendValues("curRate", (String) map.get("curRate"));
		// �v���O�����h�c�̐ݒ�
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
	 * @param curCode
	 * @param strDate
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void modifySpreadRow(String curCode, String strDate, int updKbn) throws TRequestException, IOException,
		ParseException {
		Vector<Vector> cells = setModifyCell(curCode, strDate, updKbn);
		TTable ssPanelSpread = panel.ssRateList;
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
		lockBtn(panel.ssRateList.getDataSource().getNumRows() != 0);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param curCode
	 * @param strDate
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 * @throws ParseException
	 */
	private Vector<Vector> setModifyCell(String curCode, String strDate, int updKbn) throws IOException,
		TRequestException, ParseException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ������ʂ̐ݒ�
		addSendValues("flag", "findOne");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("curCode", Util.avoidNull(curCode));
		// �I���R�[�h�̎擾
		addSendValues("strDate", strDate);

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssRateList.getDataSource();
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
			// colum�̏�����
			for (int column = 0; dataIte.hasNext(); column++) {
				// ���ʂ�ǉ�����
				colum.add(column, (String) dataIte.next());
			}

			colum.set(2, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(2))));

			String num = NumberFormatUtil.formatNumber(new BigDecimal(colum.get(3)), 4);
			colum.set(3, num);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssRateList.getCurrentRow(), colum);
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
			int nomRow = panel.ssRateList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssRateList.getDataSource();
			// ��ЃR�[�h�̎擾
			String kaiCode = (String) model.getTableDataItem(nomRow, 0);
			// �ʉ݃R�[�h�̎擾
			String curCode = (String) model.getTableDataItem(nomRow, 1);
			// �K�p�J�n���t�̎擾
			String strDate = (String) model.getTableDataItem(nomRow, 2);
			// ������ʂ̐ݒ�
			super.addSendValues("flag", "delete");
			// ��ЃR�[�h�̐ݒ�
			super.addSendValues("kaiCode", kaiCode);
			// �ʉ݃R�[�h�̐ݒ�
			super.addSendValues("curCode", curCode);
			// �K�p�J�n���t�̐ݒ�
			super.addSendValues("strDate", strDate);

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				// �T�[�o���̃G���[�ꍇ
				errorHandler(panel);
				return;
			}

			deleteSpreadRow();
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel);
		}
	}

	/**
	 * �폜�ꍇ�A�X�v���b�h�X�V
	 */
	private void deleteSpreadRow() {
		int row = panel.ssRateList.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssRateList.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssRateList.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssRateList.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssRateList.getVertSB().setValue(0);
			panel.ssRateList.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssRateList.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssRateList.setRowSelection(row, row);
		panel.ssRateList.setCurrentCell(row, 0);
	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {
		try {
			// �J�n�R�[�h�̎擾
			String strBegin = panel.ctrlBeginCurrency.getValue();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndCurrency.getValue();
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginCurrency.getField().requestFocus();
				super.showMessage(panel, "W00036", "C00371");
				return false;
			}
			// �\���f�[�^�̎擾
			return reflesh();
			// �{�^�����b�N����

		} catch (IOException e) {
			// �{�^�����b�N�̐ݒ�
			lockBtn(false);
			// ����ɏ�������܂���ł���
			errorHandler(panel);
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
		try {
			boolean dataExists = true;
			// ��ЃR�[�h�̎擾
			String kaiCode = getLoginUserCompanyCode();
			// �J�n�R�[�h�̎擾
			String beginCurCode = panel.ctrlBeginCurrency.getValue();
			String endCurCode = panel.ctrlEndCurrency.getValue();
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "find");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", kaiCode);
			// �J�n�R�[�h�̐ݒ�
			addSendValues("beginCurCode", Util.avoidNull(beginCurCode));
			// �I���R�[�h�̎擾
			addSendValues("endCurCode", Util.avoidNull(endCurCode));

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				// �T�[�o���̃G���[�ꍇ
				errorHandler(panel);
				return false;
			}

			// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
			Vector<Vector> cells = new Vector<Vector>();
			// ���ʂ��擾����
			Iterator recordIte = getResultList().iterator();
			// �l�����邩�ǂ���
			if (!recordIte.hasNext()) {
				panel.ctrlBeginCurrency.getField().requestFocus();
				showMessage(panel, "W00100");
				dataExists = false;
			}
			for (int row = 0; recordIte.hasNext(); row++) {
				// ���ʂ̓��e���擾����
				Iterator dataIte = ((List) recordIte.next()).iterator();
				// colum�̏�����
				Vector<String> colum = new Vector<String>();
				for (int column = 0; dataIte.hasNext(); column++) {
					// ���ʂ�ǉ�����
					colum.add(column, (String) dataIte.next());
				}

				colum.set(2, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(2))));

				String num = NumberFormatUtil.formatNumber(new BigDecimal(colum.get(3)), 4);
				colum.set(3, num);

				// ���ʏW��ǉ�����
				cells.add(row, colum);
			}
			// ���ʂ�\������
			panel.setDataList(cells);
			return dataExists;
		} catch (ParseException ex) {
			errorHandler(panel);
			return false;
		}
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
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginCurCode", panel.ctrlBeginCurrency.getValue());
			conds.put("endCurCode", panel.ctrlEndCurrency.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel);
		}
	}
}
