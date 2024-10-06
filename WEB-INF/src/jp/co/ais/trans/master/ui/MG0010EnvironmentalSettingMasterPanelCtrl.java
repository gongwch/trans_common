package jp.co.ais.trans.master.ui;

import java.io.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���ݒ�}�X�^�p�l��
 */
public class MG0010EnvironmentalSettingMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	private static final String PROGRAM_ID = "MG0010";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0010EnvironmentalSettingMasterServlet";

	/** �p�l�� */
	protected MG0010EnvironmentalSettingMasterPanel panel;

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0010EnvironmentalSettingMasterPanelCtrl() {
		try {
			panel = new MG0010EnvironmentalSettingMasterPanel(this);

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
		// find
		this.find();
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
	 * @param titleId �^�C�g��ID
	 * @return �����敪�}�X�^�ҏW���Ctrl
	 */
	protected MG0010EditDisplayDialogCtrl createDialogCtrl(String titleId) {
		return new MG0010EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			MG0010EditDisplayDialogCtrl dialog = createDialogCtrl("C01698");

			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);
			unlockBtn();

			// �X�v���b�h�X�V �I���s�ێ�����
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, INSERT_KBN);

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ҏW����
	 */

	void update() {
		try {
			MG0010EditDisplayDialogCtrl dialog = createDialogCtrl("C00977");

			int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
			TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("kaiName", model.getTableDataItem(nomRow, 1));
			map.put("kaiName_S", model.getTableDataItem(nomRow, 2));
			map.put("jyu1", model.getTableDataItem(nomRow, 3));
			map.put("jyu2", model.getTableDataItem(nomRow, 4));
			map.put("jyuKana", model.getTableDataItem(nomRow, 5));
			map.put("zip", model.getTableDataItem(nomRow, 6));
			map.put("tel", model.getTableDataItem(nomRow, 7));
			map.put("fax", model.getTableDataItem(nomRow, 8));
			map.put("foreColor", model.getTableDataItem(nomRow, 11));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show(false);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), false);

			// �X�v���b�h�X�V �I���s�ێ�����
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, UPD_KBN);

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �f�[�^����
	 */
	void enter() {
		try {
			int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
			TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();

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
			MG0010EditDisplayDialogCtrl dialog = createDialogCtrl("C01738");
			int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
			TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("kaiName", model.getTableDataItem(nomRow, 1));
			map.put("kaiName_S", model.getTableDataItem(nomRow, 2));
			map.put("jyu1", model.getTableDataItem(nomRow, 3));
			map.put("jyu2", model.getTableDataItem(nomRow, 4));
			map.put("jyuKana", model.getTableDataItem(nomRow, 5));
			map.put("zip", model.getTableDataItem(nomRow, 6));
			map.put("tel", model.getTableDataItem(nomRow, 7));
			map.put("fax", model.getTableDataItem(nomRow, 8));
			map.put("foreColor", model.getTableDataItem(nomRow, 11));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			// �X�v���b�h�X�V �I���s�ێ�����
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ύX����
	 * 
	 * @param map �f�[�^
	 * @param isInsert �V�K(true) or �X�V(false)
	 * @throws IOException
	 * @throws TRequestException
	 */

	protected void modify(Map map, boolean isInsert) throws IOException, TRequestException {
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("kaiName", (String) map.get("kaiName"));
		super.addSendValues("kaiName_S", (String) map.get("kaiName_S"));
		super.addSendValues("jyu1", (String) map.get("jyu1"));
		super.addSendValues("jyu2", (String) map.get("jyu2"));
		super.addSendValues("jyuKana", (String) map.get("jyuKana"));
		super.addSendValues("zip", (String) map.get("zip"));
		super.addSendValues("tel", (String) map.get("tel"));
		super.addSendValues("fax", (String) map.get("fax"));
		super.addSendValues("foreColor", (String) map.get("foreColor"));
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		super.addSendValues("prgID", PROGRAM_ID);

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
	}

	/**
	 * �폜����
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {

				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
				TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();
				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // ��ЃR�[�h
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);

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
	 * ��������
	 */
	void find() {

		try {
			this.reflesh();
			// �{�^�����b�N����

		} catch (IOException e) {
			lockBtn(false);

			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
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
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @throws IOException private void reflesh() throws IOException { panel.setDataList(getDataList()); }
	 */

	/**
	 * �\���f�[�^�̎擾
	 * 
	 * @throws IOException
	 */
	protected void reflesh() throws IOException {

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");

		// ���M
		if (request(TARGET_SERVLET)) {

			// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
			Vector<Vector> cells = new Vector<Vector>();

			Iterator recordIte = getResultList().iterator();

			// �l�����邩�ǂ���
			if (!recordIte.hasNext()) {
				showMessage(panel, "W00100", "");
			}

			for (int row = 0; recordIte.hasNext(); row++) {
				Iterator dataIte = ((List) recordIte.next()).iterator();

				Vector<String> colum = new Vector<String>();

				for (int column = 0; dataIte.hasNext(); column++) {
					colum.add(column, (String) dataIte.next());
				}

				String jyu1, jyu2, jyuKana, zip, tel, fax, strDate, endDate;

				jyu1 = colum.get(11);
				jyu2 = colum.get(12);
				jyuKana = colum.get(10);
				zip = colum.get(9);
				tel = colum.get(13);
				fax = colum.get(14);
				try {
					strDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(3)));
					endDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(4)));
					colum.set(9, strDate);
					colum.set(10, endDate);
				} catch (Exception e) {
					errorHandler(panel.getParentFrame(), e, "E00009");
				}
				colum.set(3, jyu1);
				colum.set(4, jyu2);
				colum.set(5, jyuKana);
				colum.set(6, zip);
				colum.set(7, tel);
				colum.set(8, fax);

				colum.set(11, colum.get(15));
				cells.add(row, colum);
			}

			panel.setDataList(cells);
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param updKbn �X�V�敪
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kaiCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kaiCode, updKbn);
		TTable ssPanelSpread = panel.ssEnvironmentalSettingList;
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
		lockBtn(panel.ssEnvironmentalSettingList.getDataSource().getNumRows() != 0);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param updKbn �X�V�敪
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String kaiCode, int updKbn) throws IOException, TRequestException {
		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		// ���ʂ��擾����
		List<List<String>> resultList = getResultList();

		// �l�����邩�ǂ���
		if (resultList == null || resultList.isEmpty()) {
			showMessage(panel, "W00100");
		}

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssEnvironmentalSettingList.getDataSource();
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = modifyDs.getCells();
		// colum�̏�����
		Vector<String> colum = new Vector<String>();

		for (List<String> list : resultList) {

			for (String value : list) {
				// ���ʂ�ǉ�����
				colum.add(value);
			}

			String jyu1, jyu2, jyuKana, zip, tel, fax, strDate, endDate;

			jyu1 = colum.get(11);
			jyu2 = colum.get(12);
			jyuKana = colum.get(10);
			zip = colum.get(9);
			tel = colum.get(13);
			fax = colum.get(14);
			try {
				strDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(3)));
				endDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(4)));
				colum.set(9, strDate);
				colum.set(10, endDate);
			} catch (Exception e) {
				errorHandler(panel.getParentFrame(), e, "E00009");
			}
			colum.set(3, jyu1);
			colum.set(4, jyu2);
			colum.set(5, jyuKana);
			colum.set(6, zip);
			colum.set(7, tel);
			colum.set(8, fax);

			colum.set(11, colum.get(15));
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssEnvironmentalSettingList.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssEnvironmentalSettingList.setRowSelection(row, row);
		panel.ssEnvironmentalSettingList.setCurrentCell(row, 0);
	}

	/**
	 * �폜�ꍇ�A�X�v���b�h�X�V
	 */
	private void deleteSpreadRow() {
		int row = panel.ssEnvironmentalSettingList.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssEnvironmentalSettingList.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssEnvironmentalSettingList.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssEnvironmentalSettingList.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssEnvironmentalSettingList.getVertSB().setValue(0);
			panel.ssEnvironmentalSettingList.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssEnvironmentalSettingList.getDataSource().getNumRows() != 0);
	}

}
