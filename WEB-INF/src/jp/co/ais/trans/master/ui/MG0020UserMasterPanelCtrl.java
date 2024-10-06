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
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * ���[�U�[�}�X�^
 */
public class MG0020UserMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0020";

	/** �V�K�ƕ��ʋ敪 */
	private static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	private static int UPD_KBN = 1;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0020UserMasterServlet";
	}

	/** �p�l�� */
	protected MG0020UserMasterPanel panel;

	protected Map updKenMap;

	/**
	 * REF���
	 */
	public REFDialogCtrl refCmp;

	/**
	 * REF���[�U�[(�J�n)
	 */
	public REFDialogCtrl ref1;

	/**
	 * REF���[�U�[(�I��)
	 */
	public REFDialogCtrl ref2;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0020UserMasterPanelCtrl() {
		try {
			panel = new MG0020UserMasterPanel(this);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		// ��ʏ�����
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
		updKenMap = new LinkedHashMap();
		updKenMap.put("0", getWord("C00310")); // �S��
		updKenMap.put("1", getWord("C01278")); // ���͎�
		updKenMap.put("2", getWord("C00295")); // ��������

	}

	protected void init() {
		panel.ctrlCompanyCode.requestTextFocus();

		/**
		 * ���ݒ�i��Ёj keys:�Ȃ�
		 */
		refCmp = new REFDialogCtrl(panel.ctrlCompanyCode, panel.getParentFrame());
		refCmp.setTargetServlet("MG0010EnvironmentalSettingMasterServlet");
		refCmp.setTitleID("C00053");
		refCmp.setColumnLabelIDs("C00596", "C00686", null);
		refCmp.setShowsMsgOnError(false);
		refCmp.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kind", "EnvironmentalSetting");
				keys.put("kaiCode", panel.ctrlCompanyCode.getField().getText());
				return keys;
			}

			public void goodCodeInputted() {
				codeChanged();
			}

			public void badCodeInputted() {
				codeChanged();

			}

			public void codeChanged() {
				panel.ctrlBeginUser.clearOldText();
				panel.ctrlEndUser.clearOldText();
				ref1.refreshName();
				ref2.refreshName();
			}
		});

		panel.ctrlCompanyCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCmp.refreshName();
				if (!Util.isNullOrEmpty(panel.ctrlCompanyCode.getValue().trim())
					&& Util.isNullOrEmpty(panel.ctrlCompanyCode.getNotice().getText().trim())) {
					showMessage(panel, "W00081", panel.ctrlCompanyCode.getValue());
					panel.ctrlCompanyCode.getField().clearOldText();
					panel.ctrlCompanyCode.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref1 = new REFDialogCtrl(panel.ctrlBeginUser, panel.getParentFrame());
		ref1.setTargetServlet("MG0020UserMasterServlet");
		ref1.setTitleID(getWord("C02355"));
		ref1.setColumnLabelIDs("C00589", "C00692", "C00693");
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", panel.ctrlCompanyCode.getValue());// ��ЃR�[�h
				keys.put("kind", "User");
				keys.put("endCode", panel.ctrlEndUser.getField().getText());
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
		ref2.setTargetServlet("MG0020UserMasterServlet");
		ref2.setTitleID(getWord("C02355"));
		ref2.setColumnLabelIDs("C00589", "C00692", "C00693");
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", panel.ctrlCompanyCode.getValue());// ��ЃR�[�h
				keys.put("kind", "User");
				keys.put("beginCode", panel.ctrlBeginUser.getField().getText());
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
	 * �v���O�����p�l���擾
	 * 
	 * @return �}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			MG0020EditDisplayDialogCtrl dialog = new MG0020EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			dialog.setUpdKenMap(updKenMap);
			dialog.show();
			if (!dialog.isSettle()) {
				return;
			}
			modify(dialog.getDataList(), true);
			unlockBtn();
			// �I���s�ێ�����
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String userCode = dialog.getDataList().get("usrCode").toString();
			modifySpreadRow(kaiCode, userCode, INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			MG0020EditDisplayDialogCtrl dialog = new MG0020EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			dialog.setUpdKenMap(updKenMap);
			int nomRow = panel.ssUserList.getCurrentRow();
			TableDataModel model = panel.ssUserList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("usrCode", model.getTableDataItem(nomRow, 1));
			map.put("password", model.getTableDataItem(nomRow, 2));
			map.put("lngCode", model.getTableDataItem(nomRow, 3));
			map.put("usrName", model.getTableDataItem(nomRow, 4));
			map.put("usrName_S", model.getTableDataItem(nomRow, 5));
			map.put("usrName_K", model.getTableDataItem(nomRow, 6));
			map.put("sysKbn", model.getTableDataItem(nomRow, 29));
			map.put("prcKen", model.getTableDataItem(nomRow, 22));
			map.put("updKen", model.getTableDataItem(nomRow, 30));
			map.put("empCode", model.getTableDataItem(nomRow, 24));
			map.put("depCode", model.getTableDataItem(nomRow, 25));
			map.put("keiRiKbn", model.getTableDataItem(nomRow, 31));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 27)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 28)));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}
			modify(dialog.getDataList(), false);
			// �X�v���b�h�X�V
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String userCode = dialog.getDataList().get("usrCode").toString();
			modifySpreadRow(kaiCode, userCode, UPD_KBN);

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �f�[�^����
	 */
	void enter() {
		try {
			int nomRow = panel.ssUserList.getCurrentRow();
			TableDataModel model = panel.ssUserList.getDataSource();

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
			MG0020EditDisplayDialogCtrl dialog = new MG0020EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			dialog.setUpdKenMap(updKenMap);
			int nomRow = panel.ssUserList.getCurrentRow();
			TableDataModel model = panel.ssUserList.getDataSource();
			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("usrCode", model.getTableDataItem(nomRow, 1));
			map.put("password", model.getTableDataItem(nomRow, 2));
			map.put("lngCode", model.getTableDataItem(nomRow, 3));
			map.put("usrName", model.getTableDataItem(nomRow, 4));
			map.put("usrName_S", model.getTableDataItem(nomRow, 5));
			map.put("usrName_K", model.getTableDataItem(nomRow, 6));
			map.put("sysKbn", model.getTableDataItem(nomRow, 29));
			map.put("prcKen", model.getTableDataItem(nomRow, 22));
			map.put("updKen", model.getTableDataItem(nomRow, 30));
			map.put("empCode", model.getTableDataItem(nomRow, 24));
			map.put("depCode", model.getTableDataItem(nomRow, 25));
			map.put("keiRiKbn", model.getTableDataItem(nomRow, 31));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 27)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 28)));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String userCode = dialog.getDataList().get("usrCode").toString();
			modifySpreadRow(kaiCode, userCode, INSERT_KBN);

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
	 */
	protected void modify(Map map, boolean isInsert) throws IOException {
		// super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("flag", isInsert ? "insert" : "updateUsrData");
		super.addSendValues("oldKaiCode", (String) map.get("oldKaiCode"));
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("usrCode", (String) map.get("usrCode"));
		super.addSendValues("password", (String) map.get("password"));
		super.addSendValues("lngCode", (String) map.get("lngCode"));
		super.addSendValues("usrName", (String) map.get("usrName"));
		super.addSendValues("usrName_S", (String) map.get("usrName_S"));
		super.addSendValues("usrName_k", (String) map.get("usrName_k"));
		super.addSendValues("sysKbn", (String) map.get("sysKbn"));
		super.addSendValues("depCode", (String) map.get("depCode"));
		super.addSendValues("empCode", (String) map.get("empCode"));
		super.addSendValues("prcKen", (String) map.get("prcKen"));
		super.addSendValues("updKen", (String) map.get("updKen"));
		super.addSendValues("keiriKbn", (String) map.get("keiriKbn"));
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		super.addSendValues("prgID", PROGRAM_ID);
		super.addSendValues("pwdDate", DateUtil.toYMDHMSString(Util.getCurrentDate()));

		if (!request(getServletName())) {
			errorHandler(panel);
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param kaiCode
	 * @param userCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void modifySpreadRow(String kaiCode, String userCode, int updKbn) throws TRequestException, IOException,
		ParseException {
		Vector<Vector> cells = setModifyCell(kaiCode, userCode, updKbn);
		TTable ssPanelSpread = panel.ssUserList;
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
		lockBtn(panel.ssUserList.getDataSource().getNumRows() != 0);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param kaiCode
	 * @param userCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 * @throws ParseException
	 */
	private Vector<Vector> setModifyCell(String kaiCode, String userCode, int updKbn) throws IOException,
		TRequestException, ParseException {
		super.addSendValues("flag", "find");
		super.addSendValues("kaiCode", kaiCode);
		addSendValues("beginUsrCode", Util.avoidNull(userCode));
		addSendValues("endUsrCode", Util.avoidNull(userCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssUserList.getDataSource();
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
			String impossible = this.getWord("C02366");
			String possible = this.getWord("C02365");
			colum.add(19, "");
			colum.add(20, "");
			colum.add(21, "");

			// �����������x��
			colum.add(22, colum.get(8));
			// �X�V�������x��
			String kbnValue = colum.get(9);
			String kbnText = (String) updKenMap.get(kbnValue);
			colum.add(23, kbnText);
			// �Ј��R�[�h
			colum.add(24, colum.get(10));
			// ��������R�[�h
			colum.add(25, colum.get(11));

			// �o���S���ҋ敪
			colum.add(26, "");
			String keiriText;
			String keiriValue;
			keiriValue = colum.get(12);
			keiriText = ("0".equals(keiriValue) ? this.getWord("C00140") : this.getWord("C01050"));

			colum.add(27, "");
			colum.add(28, "");

			// �J�n�N����
			String strDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(13)));
			// �I���N����
			String endDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(14)));
			colum.set(27, strDate);
			colum.set(28, endDate);

			colum.add(29, colum.get(7));

			colum.add(30, kbnValue);

			colum.set(26, keiriText);
			colum.add(31, keiriValue);

			String text;
			char[] value = colum.get(7).toCharArray();

			// ��{��v
			text = ("0".equals(String.valueOf(value[0])) ? impossible : possible);
			colum.set(7, text);

			// ���Ǘ�
			text = ("0".equals(String.valueOf(value[1])) ? impossible : possible);
			colum.set(8, text);

			// ���Ǘ�
			text = ("0".equals(String.valueOf(value[2])) ? impossible : possible);
			colum.set(9, text);

			// �Œ莑�Y
			text = ("0".equals(String.valueOf(value[3])) ? impossible : possible);
			colum.set(10, text);

			// �x����`
			text = ("0".equals(String.valueOf(value[4])) ? impossible : possible);
			colum.set(11, text);

			// ����`
			text = ("0".equals(String.valueOf(value[5])) ? impossible : possible);
			colum.set(12, text);

			// �Ǘ���v
			text = ("0".equals(String.valueOf(value[6])) ? impossible : possible);
			colum.set(13, text);

			// �e��ИA��
			text = ("0".equals(String.valueOf(value[7])) ? impossible : possible);
			colum.set(14, text);

			// �{�x�X��v
			text = ("0".equals(String.valueOf(value[8])) ? impossible : possible);
			colum.set(15, text);

			// ���ʉ݉�v
			text = ("0".equals(String.valueOf(value[9])) ? impossible : possible);
			colum.set(16, text);

			// �\�Z�Ǘ�
			text = ("0".equals(String.valueOf(value[10])) ? impossible : possible);
			colum.set(17, text);

			// �����Ǘ�
			text = ("0".equals(String.valueOf(value[11])) ? impossible : possible);
			colum.set(18, text);

			// �q��ИA��
			text = ("0".equals(String.valueOf(value[12])) ? impossible : possible);
			colum.set(19, text);

			// �O���v��v
			text = ("0".equals(String.valueOf(value[13])) ? impossible : possible);
			colum.set(20, text);

			// �L���،���v
			text = ("0".equals(String.valueOf(value[14])) ? impossible : possible);
			colum.set(21, text);
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssUserList.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * �폜����
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssUserList.getCurrentRow();
				TableDataModel model = panel.ssUserList.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // ��ЃR�[�h
				String usrCode = (String) model.getTableDataItem(nomRow, 1); // �Ǘ��R�[�h
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("usrCode", usrCode);

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
		int row = panel.ssUserList.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssUserList.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssUserList.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssUserList.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssUserList.getVertSB().setValue(0);
			panel.ssUserList.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssUserList.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {

		panel.ssUserList.setRowSelection(row, row);
		panel.ssUserList.setCurrentCell(row, 0);

	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {
		// �J�n�R�[�h�̎擾
		String strBegin = panel.ctrlBeginUser.getValue();
		// �I���R�[�h�̎擾
		String strEnd = panel.ctrlEndUser.getValue();
		// �J�n�R�[�h���I���R�[�h���傫��

		if (Util.isSmallerThen(strBegin, strEnd) == false) {
			panel.ctrlBeginUser.getField().requestFocus();
			// �x�����b�Z�[�W�\��
			showMessage(panel, "W00036", "C00528");
			return false;
		}
		return reflesh();
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
	 * �\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	protected boolean reflesh() {
		try {
			boolean dataExists = true;
			String kaiCode = panel.ctrlCompanyCode.getValue();// ��ЃR�[�h
			String beginUsrCode = panel.ctrlBeginUser.getValue();
			String endUsrCode = panel.ctrlEndUser.getValue();

			beginUsrCode = beginUsrCode.trim();
			endUsrCode = endUsrCode.trim();

			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "find");
			addSendValues("kaiCode", kaiCode);
			addSendValues("beginUsrCode", Util.avoidNull(beginUsrCode));
			addSendValues("endUsrCode", Util.avoidNull(endUsrCode));

			if (!request(getServletName())) {
				errorHandler(panel);
				return false;
			}
			// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
			Vector<Vector> cells = new Vector<Vector>();

			Iterator recordIte = getResultList().iterator();
			// �l�����邩�ǂ���
			if (!recordIte.hasNext()) {
				panel.ctrlBeginUser.getField().requestFocus();
				showMessage(panel, "W00100");
				dataExists = false;
			}
			for (int row = 0; recordIte.hasNext(); row++) {
				Iterator dataIte = ((List) recordIte.next()).iterator();

				Vector<String> colum = new Vector<String>();

				for (int column = 0; dataIte.hasNext(); column++) {
					colum.add(column, (String) dataIte.next());
				}
				String impossible = this.getWord("C02366");
				String possible = this.getWord("C02365");
				colum.add(19, "");
				colum.add(20, "");
				colum.add(21, "");

				// �����������x��
				colum.add(22, colum.get(8));
				// �X�V�������x��
				String kbnValue = colum.get(9);
				String kbnText = (String) updKenMap.get(kbnValue);
				colum.add(23, kbnText);
				// �Ј��R�[�h
				colum.add(24, colum.get(10));
				// ��������R�[�h
				colum.add(25, colum.get(11));

				// �o���S���ҋ敪
				colum.add(26, "");
				String keiriText;
				String keiriValue;
				keiriValue = colum.get(12);
				keiriText = ("0".equals(keiriValue) ? this.getWord("C00140") : this.getWord("C01050"));

				colum.add(27, "");
				colum.add(28, "");

				// �J�n�N����
				String strDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(13)));
				// �I���N����
				String endDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(14)));
				colum.set(27, strDate);
				colum.set(28, endDate);

				colum.add(29, colum.get(7));

				colum.add(30, kbnValue);

				colum.set(26, keiriText);
				colum.add(31, keiriValue);

				String text;
				char[] value = colum.get(7).toCharArray();

				// ��{��v
				text = ("0".equals(String.valueOf(value[0])) ? impossible : possible);
				colum.set(7, text);

				// ���Ǘ�
				text = ("0".equals(String.valueOf(value[1])) ? impossible : possible);
				colum.set(8, text);

				// ���Ǘ�
				text = ("0".equals(String.valueOf(value[2])) ? impossible : possible);
				colum.set(9, text);

				// �Œ莑�Y
				text = ("0".equals(String.valueOf(value[3])) ? impossible : possible);
				colum.set(10, text);

				// �x����`
				text = ("0".equals(String.valueOf(value[4])) ? impossible : possible);
				colum.set(11, text);

				// ����`
				text = ("0".equals(String.valueOf(value[5])) ? impossible : possible);
				colum.set(12, text);

				// �Ǘ���v
				text = ("0".equals(String.valueOf(value[6])) ? impossible : possible);
				colum.set(13, text);

				// �e��ИA��
				text = ("0".equals(String.valueOf(value[7])) ? impossible : possible);
				colum.set(14, text);

				// �{�x�X��v
				text = ("0".equals(String.valueOf(value[8])) ? impossible : possible);
				colum.set(15, text);

				// ���ʉ݉�v
				text = ("0".equals(String.valueOf(value[9])) ? impossible : possible);
				colum.set(16, text);

				// �\�Z�Ǘ�
				text = ("0".equals(String.valueOf(value[10])) ? impossible : possible);
				colum.set(17, text);

				// �����Ǘ�
				text = ("0".equals(String.valueOf(value[11])) ? impossible : possible);
				colum.set(18, text);

				// �q��ИA��
				text = ("0".equals(String.valueOf(value[12])) ? impossible : possible);
				colum.set(19, text);

				// �O���v��v
				text = ("0".equals(String.valueOf(value[13])) ? impossible : possible);
				colum.set(20, text);

				// �L���،���v
				text = ("0".equals(String.valueOf(value[14])) ? impossible : possible);
				colum.set(21, text);

				cells.add(row, colum);
			}

			panel.setDataList(cells);

			return dataExists;
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
			return false;
		}
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
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��ЃR�[�h�̎擾
			conds.put("kaiCode", panel.ctrlCompanyCode.getValue());
			conds.put("langCode", getLoginLanguage());
			String beginUsrCode = panel.ctrlBeginUser.getValue();
			String endUsrCode = panel.ctrlEndUser.getValue();

			conds.put("beginUsrCode", beginUsrCode);
			conds.put("endUsrCode", endUsrCode);
			this.download(panel, getServletName(), conds);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
