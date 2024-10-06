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
 * ����}�X�^��ʃR���g���[��
 */
public class MG0060DepartmentMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0060";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0060DepartmentMasterServlet";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/** �p�l�� */
	protected MG0060DepartmentMasterPanel panel;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/** ���͂̃e�L�X�g */
	private String labelInput = getWord("C01275");

	/** �W�v�̃e�L�X�g */
	private String labelSummary = getWord("C00255");

	protected MG0060EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0060DepartmentMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0060DepartmentMasterPanel(this);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
		panel.ctrlBeginDepartment.setMaxLength(10);
		panel.ctrlEndDepartment.setMaxLength(10);
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});

	}

	void init() {
		panel.ctrlBeginDepartment.requestTextFocus();
		// �J�n�R�[�h�̐ݒ�

		ref1 = new REFDialogCtrl(panel.ctrlBeginDepartment, panel.getParentFrame());
		ref1.setTargetServlet("MG0060DepartmentMasterServlet");
		ref1.setTitleID(getWord("C02338"));
		ref1.setColumnLabelIDs("C00698", "C00724", "C00725");
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "DepartmentAll");
				keys.put("endCode", panel.ctrlEndDepartment.getField().getText());
				return keys;
			}
		});

		panel.ctrlBeginDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});
		// �I���R�[�h�̐ݒ�
		ref2 = new REFDialogCtrl(panel.ctrlEndDepartment, panel.getParentFrame());
		ref2.setTargetServlet("MG0060DepartmentMasterServlet");
		ref2.setTitleID(getWord("C02338"));
		ref2.setColumnLabelIDs("C00698", "C00724", "C00725");
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "DepartmentAll");
				keys.put("beginCode", panel.ctrlBeginDepartment.getField().getText());
				return keys;
			}
		});
		panel.ctrlEndDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}
		});

	}

	/**
	 * �p�l���擾
	 * 
	 * @return ����}�X�^�p�l��
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
		dialog = new MG0060EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
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
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String depCode = dialog.getDataList().get("depCode").toString();
			modifySpreadRow(kaiCode, depCode, INSERT_KBN);
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
			int nomRow = panel.ssDepartmentList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssDepartmentList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ����R�[�h�̐ݒ�
			map.put("depCode", model.getTableDataItem(nomRow, 1));
			// ���喼�̂̐ݒ�
			map.put("depName", model.getTableDataItem(nomRow, 2));
			// ���嗪�̂̐ݒ�
			map.put("depName_S", model.getTableDataItem(nomRow, 3));
			// ���匟�����̂̐ݒ�
			map.put("depName_K", model.getTableDataItem(nomRow, 4));
			// �l�����P�̐ݒ�
			map.put("men1", model.getTableDataItem(nomRow, 5));
			// �l�����Q�̐ݒ�
			map.put("men2", model.getTableDataItem(nomRow, 6));
			// ���ʐς̐ݒ�
			map.put("space", model.getTableDataItem(nomRow, 7));
			// ����敪�̐ݒ�
			map.put("depKbn", model.getTableDataItem(nomRow, 15));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
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
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String depCode = dialog.getDataList().get("depCode").toString();
			modifySpreadRow(kaiCode, depCode, UPD_KBN);
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
			int nomRow = panel.ssDepartmentList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssDepartmentList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ����R�[�h�̐ݒ�
			map.put("depCode", model.getTableDataItem(nomRow, 1));
			// ���喼�̂̐ݒ�
			map.put("depName", model.getTableDataItem(nomRow, 2));
			// ���嗪�̂̐ݒ�
			map.put("depName_S", model.getTableDataItem(nomRow, 3));
			// ���匟�����̂̐ݒ�
			map.put("depName_K", model.getTableDataItem(nomRow, 4));
			// �l�����P�̐ݒ�
			map.put("men1", model.getTableDataItem(nomRow, 5));
			// �l�����Q�̐ݒ�
			map.put("men2", model.getTableDataItem(nomRow, 6));
			// ���ʐς̐ݒ�
			map.put("space", model.getTableDataItem(nomRow, 7));
			// ����敪�̐ݒ�
			map.put("depKbn", model.getTableDataItem(nomRow, 15));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
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

			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String depCode = dialog.getDataList().get("depCode").toString();
			modifySpreadRow(kaiCode, depCode, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param kaiCode
	 * @param depCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kaiCode, String depCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kaiCode, depCode, updKbn);
		TTable ssPanelSpread = panel.ssDepartmentList;
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
		lockBtn(panel.ssDepartmentList.getDataSource().getNumRows() != 0);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param kaiCode
	 * @param depCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String kaiCode, String depCode, int updKbn) throws IOException,
		TRequestException {
		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginDepCode", Util.avoidNull(depCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endDepCode", Util.avoidNull(depCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		// ���ʂ��擾����
		List<List<String>> resultList = getResultList();

		// �l�����邩�ǂ���
		if (resultList.isEmpty()) {
			panel.ctrlBeginDepartment.getField().requestFocus();
			showMessage(panel, "W00100");
		}

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssDepartmentList.getDataSource();
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = modifyDs.getCells();
		// colum�̏�����
		Vector<String> colum = new Vector<String>();
		for (List<String> list : resultList) {

			for (String value : list) {
				// ���ʂ�ǉ�����
				colum.add(value);
			}

			// men1
			colum.set(5, NumberFormatUtil.formatNumber(colum.get(5)));

			// men2
			colum.set(6, NumberFormatUtil.formatNumber(colum.get(6)));

			// space
			colum.set(7, NumberFormatUtil.formatNumber(colum.get(7), 2));

			// ���t
			colum.set(9, toYMDString(colum.get(9)));
			colum.set(10, toYMDString(colum.get(10)));

			// ����敪�̒ǉ�
			colum.add(15, colum.get(8));

			// ����敪���e�̐ݒ�
			colum.set(8, "0".equals(colum.get(8)) ? labelInput : labelSummary);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssDepartmentList.getCurrentRow(), colum);
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
		// ��ЃR�[�h�̐ݒ�
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		// ����R�[�h�̐ݒ�
		super.addSendValues("depCode", (String) map.get("depCode"));
		// ���喼�̂̐ݒ�
		super.addSendValues("depName", (String) map.get("depName"));
		// ���嗪�̂̐ݒ�
		super.addSendValues("depName_S", (String) map.get("depName_S"));
		// ���匟�����̂̐ݒ�
		super.addSendValues("depName_K", (String) map.get("depName_K"));
		// �l�����P�̐ݒ�
		super.addSendValues("men1", (String) map.get("men1"));
		// �l�����Q�̐ݒ�
		super.addSendValues("men2", (String) map.get("men2"));
		// ���ʐς̐ݒ�
		super.addSendValues("space", (String) map.get("space"));
		// ����敪�̐ݒ�
		super.addSendValues("depKbn", (String) map.get("depKbn"));
		// �J�n�N�����̐ݒ�
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
		super.addSendValues("prgID", PROGRAM_ID);

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
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
				int nomRow = panel.ssDepartmentList.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssDepartmentList.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// ����R�[�h�̎擾
				String depCode = (String) model.getTableDataItem(nomRow, 1);
				// ������ʂ̐ݒ�
				super.addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				super.addSendValues("kaiCode", kaiCode);
				// ����R�[�h�̐ݒ�
				super.addSendValues("depCode", depCode);

				// �T�[�o���̃G���[�ꍇ
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
		int row = panel.ssDepartmentList.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssDepartmentList.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssDepartmentList.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssDepartmentList.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssDepartmentList.getVertSB().setValue(0);
			panel.ssDepartmentList.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssDepartmentList.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssDepartmentList.setRowSelection(row, row);
		panel.ssDepartmentList.setCurrentCell(row, 0);
	}

	/**
	 * ��������
	 * 
	 * @return true:����
	 */
	boolean find() {
		try {
			// �J�n�R�[�h�̎擾
			String strBegin = panel.ctrlBeginDepartment.getValue();
			// �I���R�[�h�̎擾
			String strEnd = panel.ctrlEndDepartment.getValue();

			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				panel.ctrlBeginDepartment.getField().requestFocus();
				// �x�����b�Z�[�W�\��
				super.showMessage(panel, "W00036", "C00467");
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
	private boolean reflesh() throws IOException {

		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginDepCode = panel.ctrlBeginDepartment.getValue();
		// �I���R�[�h�̎擾
		String endDepCode = panel.ctrlEndDepartment.getValue();
		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginDepCode", Util.avoidNull(beginDepCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endDepCode", Util.avoidNull(endDepCode));

		// ���M
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}

		boolean dataExists = true;

		// ���ʂ��擾����
		List<List<String>> resultList = getResultList();

		// �l�����邩�ǂ���
		if (resultList.isEmpty()) {
			panel.ctrlBeginDepartment.getField().requestFocus();
			showMessage(panel, "W00100");
			dataExists = false;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		for (List<String> list : resultList) {

			// colum�̏�����
			Vector<String> colum = new Vector<String>();
			for (String value : list) {
				// ���ʂ�ǉ�����
				colum.add(value);
			}

			// men1
			colum.set(5, NumberFormatUtil.formatNumber(colum.get(5)));

			// men2
			colum.set(6, NumberFormatUtil.formatNumber(colum.get(6)));

			// space
			colum.set(7, NumberFormatUtil.formatNumber(colum.get(7), 2));

			// ���t
			colum.set(9, toYMDString(colum.get(9)));
			colum.set(10, toYMDString(colum.get(10)));

			// ����敪�̒ǉ�
			colum.add(15, colum.get(8));

			// ����敪���e�̐ݒ�
			colum.set(8, "0".equals(colum.get(8)) ? labelInput : labelSummary);

			// ���ʏW��ǉ�����
			cells.add(colum);
		}

		// ���ʂ�\������
		panel.setDataList(cells);

		return dataExists;
	}

	/**
	 * �s�����t������YYYY/MM/DD�`���ɐ؂�ւ���.<br>
	 * �ϊ��ł��Ȃ��ꍇ�A�ʒm�̈Ӗ��ł��̂܂ܒl��\������
	 * 
	 * @param value �Ώےl
	 * @return �ϊ���̒l
	 */
	private String toYMDString(String value) {
		try {
			return DateUtil.toYMDString(DateUtil.toYMDDate(value));

		} catch (ParseException e) {
			ClientErrorHandler.handledException(e);
			return value;
		}
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginDepartment.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginDepCode", panel.ctrlBeginDepartment.getValue());
			conds.put("endDepCode", panel.ctrlEndDepartment.getValue());

			conds.put("langCode", getLoginLanguage());

			// �T�[�u���b�g�̐ڑ���
			this.download(panel, TARGET_SERVLET, conds);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
