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
 * ���������}�X�^��ʃR���g���[��
 * 
 * @author mayongliang
 */
public class MG0155CustomerConditionMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0155";

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
		return "MG0155CustomerConditionMasterServlet";
	}

	/** �p�l�� */
	protected MG0155CustomerConditionMasterPanel panel;

	private REFDialogCtrl refBeginCustomer;

	private REFDialogCtrl refEndCustomer;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0155CustomerConditionMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0155CustomerConditionMasterPanel(this);
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

		refBeginCustomer = new REFDialogCtrl(panel.ctrlBeginCustomer, panel.getParentFrame());
		refBeginCustomer.setTargetServlet("MG0150CustomerMasterServlet");
		refBeginCustomer.setTitleID("C02326");
		refBeginCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refBeginCustomer.setShowsMsgOnError(false);
		refBeginCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCustomer.getField().getText());
				keys.put("kind", "Customer");
				return keys;
			}

		});

		panel.ctrlBeginCustomer.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginCustomer.refreshName();

				return true;
			}
		});

		// �I���R�[�h�̐ݒ�
		refEndCustomer = new REFDialogCtrl(panel.ctrlEndCustomer, panel.getParentFrame());
		refEndCustomer.setTargetServlet("MG0150CustomerMasterServlet");
		refEndCustomer.setTitleID("C02326");
		refEndCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refEndCustomer.setShowsMsgOnError(false);
		refEndCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCustomer.getField().getText());
				keys.put("kind", "Customer");
				return keys;
			}

		});

		panel.ctrlEndCustomer.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndCustomer.refreshName();

				return true;
			}
		});

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
			MG0155EditDisplayDialogCtrl dialog = new MG0155EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			// �ҏW��ʂ̕\��
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �I���s�ێ�����
			String triCode = dialog.getDataList().get("triCode").toString();
			String triSjCode = dialog.getDataList().get("tjkCode").toString();
			modifySpreadRow(triCode, triSjCode, INSERT_KBN);
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
			MG0155EditDisplayDialogCtrl dialog = new MG0155EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			// ���O�s���擾����
			int nomRow = panel.ssCustomerCondition.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssCustomerCondition.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �����R�[�h�̐ݒ�
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// ���������R�[�h�̐ݒ�
			map.put("tjkCode", model.getTableDataItem(nomRow, 3));
			// �U���萔���敪�̐ݒ�
			map.put("furiTesuKen", model.getTableDataItem(nomRow, 30));
			// �x���������ߓ��̐ݒ�
			map.put("sjcDate", model.getTableDataItem(nomRow, 5));
			// �x���������ߌ㌎�̐ݒ�
			map.put("sjrMon", model.getTableDataItem(nomRow, 6));
			// �x�������x�����̐ݒ�
			map.put("sjpDate", model.getTableDataItem(nomRow, 7));
			// �x���敪�̐ݒ�
			map.put("sihaKbn", model.getTableDataItem(nomRow, 31));
			// �x�����@�̐ݒ�
			map.put("sihaHouCode", model.getTableDataItem(nomRow, 9));
			// �U���U�o��s�������ނ̐ݒ�
			map.put("furiCbkCode", model.getTableDataItem(nomRow, 10));
			// ��s�R�[�h�̐ݒ�
			map.put("bnkCode", model.getTableDataItem(nomRow, 11));
			// �x�X�R�[�h�̐ݒ�
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 12));
			// �a����ڂ̐ݒ�
			map.put("yknKbn", model.getTableDataItem(nomRow, 32));
			// �����ԍ��̐ݒ�
			map.put("yknNo", model.getTableDataItem(nomRow, 14));
			// �������`�J�i�̐ݒ�
			map.put("yknKana", model.getTableDataItem(nomRow, 15));
			// �����ړI���̐ݒ�
			map.put("gsMktCode", model.getTableDataItem(nomRow, 16));
			// ��d����s���̂̐ݒ�
			map.put("gsBnkName", model.getTableDataItem(nomRow, 17));
			// ��d���x�X���̂̐ݒ�
			map.put("gsStnName", model.getTableDataItem(nomRow, 18));
			// �������`�̐ݒ�
			map.put("yknName", model.getTableDataItem(nomRow, 19));
			// �萔���敪�̐ݒ�
			map.put("gsTesuKbn", model.getTableDataItem(nomRow, 33));
			// �萔���敪�̐ݒ�
			map.put("sihaBnkName", model.getTableDataItem(nomRow, 21));
			// �x���x�X���̂̐ݒ�
			map.put("sihaStnName", model.getTableDataItem(nomRow, 22));
			// �x����s�Z���̐ݒ�
			map.put("sihaBnkAdr", model.getTableDataItem(nomRow, 23));
			// �x����s�Z���̐ݒ�
			map.put("keiyuBnkName", model.getTableDataItem(nomRow, 24));
			// �o�R�x�X���̂̐ݒ�
			map.put("keiyuStnName", model.getTableDataItem(nomRow, 25));
			// �o�R�x�X���̂̐ݒ�
			map.put("keiyuBnkAdr", model.getTableDataItem(nomRow, 26));
			// ���l�Z���̐ݒ�
			map.put("ukeAdr", model.getTableDataItem(nomRow, 27));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 28)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 29)));
			// ������ʂ̐ݒ�
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
			String triCode = dialog.getDataList().get("triCode").toString();
			String triSjCode = dialog.getDataList().get("tjkCode").toString();
			modifySpreadRow(triCode, triSjCode, UPD_KBN);
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
			MG0155EditDisplayDialogCtrl dialog = new MG0155EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			// ���O�s���擾����
			int nomRow = panel.ssCustomerCondition.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssCustomerCondition.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �����R�[�h�̐ݒ�
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// ���������R�[�h
			map.put("tjkCode", model.getTableDataItem(nomRow, 3));
			// �U���萔���敪
			map.put("furiTesuKen", model.getTableDataItem(nomRow, 30));
			// �x���������ߓ��̐ݒ�
			map.put("sjcDate", model.getTableDataItem(nomRow, 5));
			// �x���������ߌ㌎�̐ݒ�
			map.put("sjrMon", model.getTableDataItem(nomRow, 6));
			// �x���������ߌ㌎�̐ݒ�
			map.put("sjpDate", model.getTableDataItem(nomRow, 7));
			// �x���敪�̐ݒ�
			map.put("sihaKbn", model.getTableDataItem(nomRow, 31));
			// �x�����@�̐ݒ�
			map.put("sihaHouCode", model.getTableDataItem(nomRow, 9));
			// �U���U�o��s�������ނ̐ݒ�
			map.put("furiCbkCode", model.getTableDataItem(nomRow, 10));
			// ��s�R�[�h�̐ݒ�
			map.put("bnkCode", model.getTableDataItem(nomRow, 11));
			// ��s�R�[�h�̐ݒ�
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 12));
			// �a����ڂ̐ݒ�
			map.put("yknKbn", model.getTableDataItem(nomRow, 32));
			// �a����ڂ̐ݒ�
			map.put("yknNo", model.getTableDataItem(nomRow, 14));
			// �������`�J�i�̐ݒ�
			map.put("yknKana", model.getTableDataItem(nomRow, 15));
			// �����ړI���̐ݒ�
			map.put("gsMktCode", model.getTableDataItem(nomRow, 16));
			// ��d����s���̂̐ݒ�
			map.put("gsBnkName", model.getTableDataItem(nomRow, 17));
			// ��d���x�X���̂̐ݒ�
			map.put("gsStnName", model.getTableDataItem(nomRow, 18));
			// �������`�̐ݒ�
			map.put("yknName", model.getTableDataItem(nomRow, 19));
			// �萔���敪�̐ݒ�
			map.put("gsTesuKbn", model.getTableDataItem(nomRow, 33));
			// �x����s���̂̐ݒ�
			map.put("sihaBnkName", model.getTableDataItem(nomRow, 21));
			// �x���x�X���̂̐ݒ�
			map.put("sihaStnName", model.getTableDataItem(nomRow, 22));
			// �x����s�Z���̐ݒ�
			map.put("sihaBnkAdr", model.getTableDataItem(nomRow, 23));
			// �o�R��s���̂̐ݒ�
			map.put("keiyuBnkName", model.getTableDataItem(nomRow, 24));
			// �o�R��s���̂̐ݒ�
			map.put("keiyuStnName", model.getTableDataItem(nomRow, 25));
			// �o�R��s�Z���̐ݒ�
			map.put("keiyuBnkAdr", model.getTableDataItem(nomRow, 26));
			// ���l�Z���̐ݒ�
			map.put("ukeAdr", model.getTableDataItem(nomRow, 27));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 28)));
			// �J�n�N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 29)));
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

			String triCode = dialog.getDataList().get("triCode").toString();
			String triSjCode = dialog.getDataList().get("tjkCode").toString();
			modifySpreadRow(triCode, triSjCode, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param triCode
	 * @param triSjCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String triCode, String triSjCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(triCode, triSjCode, updKbn);
		TTable ssPanelSpread = panel.ssCustomerCondition;
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
		lockBtn(panel.ssCustomerCondition.getDataSource().getNumRows() != 0);
	}

	/**
	 * �{�^�����b�N�̐���
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		panel.btnCopy.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
		panel.btnDelete.setEnabled(boo);
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param triCode
	 * @param triSjCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String triCode, String triSjCode, int updKbn) throws IOException,
		TRequestException {
		String text;
		String value;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "findOneInfo");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("triCode", Util.avoidNull(triCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("triSjCode", Util.avoidNull(triSjCode));
		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssCustomerCondition.getDataSource();
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

			// �U���萔���敪
			value = colum.get(4);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(30, value);
			colum.set(4, text);

			// �x���敪
			value = colum.get(8);
			text = ("00".equals(value) ? this.getWord("C02166") : this.getWord("C02167"));
			colum.add(31, value);
			colum.set(8, text);

			// �a�����
			value = colum.get(13);

			if (value.equals("1")) {
				text = this.getWord("C00465");
			} else if (value.equals("2")) {
				text = this.getWord("C02154");
			} else if (value.equals("3")) {
				text = this.getWord("C02168");
			} else {
				text = this.getWord("C02169");
			}
			colum.add(32, value);
			colum.set(13, text);

			// �萔���敪
			value = colum.get(20);
			text = ("1".equals(value) ? this.getWord("C00021") : this.getWord("C02319"));
			colum.add(33, value);
			colum.set(20, text);
			try {
				colum.set(28, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(28))));
				colum.set(29, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(29))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(26), ex);
			}

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssCustomerCondition.getCurrentRow(), colum);
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
		addSendValues("flag", isInsert ? "insert" : "update");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �����R�[�h�̐ݒ�
		addSendValues("triCode", (String) map.get("triCode"));
		// �����R�[�h�̐ݒ�
		addSendValues("tjkCode", (String) map.get("tjkCode"));
		// �U���萔���敪�̐ݒ�
		addSendValues("furiTesuKen", (String) map.get("furiTesuKen"));
		// �x���������ߓ��̐ݒ�
		addSendValues("sjcDate", (String) map.get("sjcDate"));
		// �x���������ߌ㌎�̐ݒ�
		addSendValues("sjrMon", (String) map.get("sjrMon"));
		// �x�������x�����̐ݒ�
		addSendValues("sjpDate", (String) map.get("sjpDate"));
		// �x���敪�̐ݒ�
		addSendValues("sihaKbn", (String) map.get("sihaKbn"));
		// �x�����@�̐ݒ�
		addSendValues("sihaHouCode", (String) map.get("sihaHouCode"));
		// �U���U�o��s�������ނ̐ݒ�
		addSendValues("furiCbkCode", (String) map.get("furiCbkCode"));
		// ��s�R�[�h�̐ݒ�
		addSendValues("bnkCode", (String) map.get("bnkCode"));
		// �x�X�R�[�h�̐ݒ�
		addSendValues("bnkStnCode", (String) map.get("bnkStnCode"));
		// �a����ڂ̐ݒ�
		addSendValues("yknKbn", (String) map.get("yknKbn"));
		// �a����ڂ̐ݒ�
		addSendValues("yknNo", (String) map.get("yknNo"));
		// �a����ڂ̐ݒ�
		addSendValues("yknName", (String) map.get("yknName"));
		// �������`�J�i�̐ݒ�
		addSendValues("yknKana", (String) map.get("yknKana"));
		// �����ړI���̐ݒ�
		addSendValues("gsMktCode", (String) map.get("gsMktCode"));
		// �����ړI���̐ݒ�
		addSendValues("gsStnName", (String) map.get("gsStnName"));
		// ��d����s���̂̐ݒ�
		addSendValues("gsBnkName", (String) map.get("gsBnkName"));
		// �萔���敪�̐ݒ�
		addSendValues("gsTesuKbn", (String) map.get("gsTesuKbn"));
		// �萔���敪�̐ݒ�
		addSendValues("sihaBnkName", (String) map.get("sihaBnkName"));
		// �x���x�X���̂̐ݒ�
		addSendValues("sihaStnName", (String) map.get("sihaStnName"));
		// �x���x�X���̂̐ݒ�
		addSendValues("sihaBnkAdr", (String) map.get("sihaBnkAdr"));
		// �o�R��s���̂̐ݒ�
		addSendValues("keiyuBnkName", (String) map.get("keiyuBnkName"));
		// �o�R�x�X���̂̐ݒ�
		addSendValues("keiyuStnName", (String) map.get("keiyuStnName"));
		// �o�R��s�Z���̐ݒ�
		addSendValues("keiyuBnkAdr", (String) map.get("keiyuBnkAdr"));
		// ���l�Z���̐ݒ�
		addSendValues("ukeAdr", (String) map.get("ukeAdr"));
		// �J�n�N�����̐ݒ�
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
		addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			// �T�[�o���̃G���[�ꍇ
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
				int nomRow = panel.ssCustomerCondition.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssCustomerCondition.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// �����R�[�h�̎擾
				String triCode = (String) model.getTableDataItem(nomRow, 1);
				// ���������R�[�h�̎擾
				String tjkCode = (String) model.getTableDataItem(nomRow, 3);
				// ������ʂ̐ݒ�
				addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				addSendValues("kaiCode", kaiCode);
				// �����R�[�h�̐ݒ�
				addSendValues("triCode", triCode);
				// ���������R�[�h�̐ݒ�
				addSendValues("tjkCode", tjkCode);

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
	protected void deleteSpreadRow() {
		int row = panel.ssCustomerCondition.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssCustomerCondition.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssCustomerCondition.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssCustomerCondition.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssCustomerCondition.getVertSB().setValue(0);
			panel.ssCustomerCondition.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssCustomerCondition.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssCustomerCondition.setRowSelection(row, row);
		panel.ssCustomerCondition.setCurrentCell(row, 0);
	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {

		try {
			// �J�n�R�[�h�̎擾
			String beginTriSjCode = panel.ctrlBeginCustomer.getField().getText();
			// �I���R�[�h�̎擾
			String endTriSjCode = panel.ctrlEndCustomer.getField().getText();
			// �J�n�R�[�h���I���R�[�h���傫��
			if (Util.isSmallerThen(beginTriSjCode, endTriSjCode) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginCustomer.getField().requestFocus();
				showMessage(panel, "W00036", "C00408");
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
	 * ��ʃ��t���b�V��
	 * 
	 * @return boolean
	 * @throws IOException
	 */

	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		String text;
		String value;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginTriSjCode = panel.ctrlBeginCustomer.getField().getText();
		// �I���R�[�h�̎擾
		String endTriSjCode = panel.ctrlEndCustomer.getField().getText();

		beginTriSjCode = beginTriSjCode.trim();
		endTriSjCode = endTriSjCode.trim();
		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginTriSjCode", Util.avoidNull(beginTriSjCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endTriSjCode", Util.avoidNull(endTriSjCode));

		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginCustomer.getField().requestFocus();
			// �x�����b�Z�[�W�\��
			showMessage(panel, "W00100");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			// �U���萔���敪
			value = colum.get(4);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(30, value);
			colum.set(4, text);

			// �x���敪
			value = colum.get(8);
			text = ("00".equals(value) ? this.getWord("C02166") : this.getWord("C02167"));
			colum.add(31, value);
			colum.set(8, text);

			// �a�����
			value = colum.get(13);

			if (value.equals("1")) {
				text = this.getWord("C00465");
			} else if (value.equals("2")) {
				text = this.getWord("C02154");
			} else if (value.equals("3")) {
				text = this.getWord("C02168");
			} else {
				text = this.getWord("C02169");
			}
			colum.add(32, value);
			colum.set(13, text);

			// �萔���敪
			value = colum.get(20);
			text = ("1".equals(value) ? this.getWord("C00021") : this.getWord("C02319"));
			colum.add(33, value);
			colum.set(20, text);
			try {
				colum.set(28, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(28))));
				colum.set(29, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(29))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(26), ex);
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
			panel.ctrlBeginCustomer.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginTriSjCode", panel.ctrlBeginCustomer.getValue());
			conds.put("endTriSjCode", panel.ctrlEndCustomer.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
