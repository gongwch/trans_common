package jp.co.ais.trans.master.ui;

import java.io.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;

/**
 * ��ЃR���g���[���}�X�^
 * 
 * @author yanwei
 */
public class MG0031CompanyControlMasterPanelCtrl extends MG0030CompanyControlMasterPanelCtrl {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0031";

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	/**
	 * �R���X�g���N�^.
	 */
	public MG0031CompanyControlMasterPanelCtrl() {
		super();
		cmpHmKbnMap = new LinkedHashMap();
		cmpHmKbnMap.put("0", this.getWord("C00282"));
		cmpHmKbnMap.put("1", this.getWord("C02160"));
		cmpHmKbnMap.put("2", this.getWord("C02161"));
		cmpHmKbnMap.put("3", this.getWord("C00446"));
		// ����
		cmpHmKbnMap.put("4", this.getWord("C02906"));
	}

	/**
	 * �����������B
	 */
	@Override
	public void initCtrl() {
		panel = new MG0031CompanyControlMasterPanel(this);
		super.panel = panel;
	}

	/**
	 * �p�l���擾
	 * 
	 * @return ��ЃR���g���[���}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			// �ҏW��ʂ̏�����
			MG0031EditDisplayDialogCtrl dialog = new MG0031EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			dialog.setSelectMap(cmpHmKbnMap, jidMap);
			// ���O�s���擾����
			int nomRow = panel.ssCompanyCodeRoleList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssCompanyCodeRoleList.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ��ЃR�[�h�̐ݒ�
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			// ��ЃR�[�h �̐ݒ�
			map.put("cmpKmkName", model.getTableDataItem(nomRow, 1));
			// �⏕�Ȗږ��� �̐ݒ�
			map.put("cmpHkmName", model.getTableDataItem(nomRow, 2));
			// ����ȖڊǗ��̐ݒ�
			map.put("cmpUkmKbn", model.getTableDataItem(nomRow, 37));
			// ����Ȗږ��̂̐ݒ�
			map.put("cmpUkmName", model.getTableDataItem(nomRow, 4));
			// �Ǘ��敪1-6�̐ݒ�
			map.put("knrKbn1", model.getTableDataItem(nomRow, 38));
			map.put("knrKbn2", model.getTableDataItem(nomRow, 39));
			map.put("knrKbn3", model.getTableDataItem(nomRow, 40));
			map.put("knrKbn4", model.getTableDataItem(nomRow, 41));
			map.put("knrKbn5", model.getTableDataItem(nomRow, 42));
			map.put("knrKbn6", model.getTableDataItem(nomRow, 43));
			// �Ǘ�����1-6�̐ݒ�
			map.put("knrName1", model.getTableDataItem(nomRow, 11));
			map.put("knrName2", model.getTableDataItem(nomRow, 12));
			map.put("knrName3", model.getTableDataItem(nomRow, 13));
			map.put("knrName4", model.getTableDataItem(nomRow, 14));
			map.put("knrName5", model.getTableDataItem(nomRow, 15));
			map.put("knrName6", model.getTableDataItem(nomRow, 16));
			// ���v���׋敪1-3�̐ݒ�
			map.put("cmpHmKbn1", model.getTableDataItem(nomRow, 44));
			map.put("cmpHmKbn2", model.getTableDataItem(nomRow, 45));
			map.put("cmpHmKbn3", model.getTableDataItem(nomRow, 46));
			// ���v���ז���1-3�̐ݒ�
			map.put("cmpHmName1", model.getTableDataItem(nomRow, 20));
			map.put("cmpHmName2", model.getTableDataItem(nomRow, 21));
			map.put("cmpHmName3", model.getTableDataItem(nomRow, 22));
			// ���񌎂̐ݒ�
			map.put("cmpKisyu", model.getTableDataItem(nomRow, 23));
			// �����ݒ荀�ڂP-3�̐ݒ�
			map.put("jid1", model.getTableDataItem(nomRow, 47));
			map.put("jid2", model.getTableDataItem(nomRow, 48));
			map.put("jid3", model.getTableDataItem(nomRow, 49));
			// �����̔ԕ������̐ݒ�
			map.put("autoNoKeta", model.getTableDataItem(nomRow, 27));
			// �`�[����敪�̐ݒ�
			map.put("printKbn", model.getTableDataItem(nomRow, 50));
			// ������̏����l�̐ݒ�
			map.put("printDef", model.getTableDataItem(nomRow, 51));
			// ���ꏳ�F�׸ނ̐ݒ�
			map.put("cmpGShoninFlg", model.getTableDataItem(nomRow, 52));
			// �o�����F�׸ނ̐ݒ�
			map.put("cmpKShoninFlg", model.getTableDataItem(nomRow, 53));
			// �{�M�ʉ݃R�[�h�̐ݒ�
			map.put("curCode", model.getTableDataItem(nomRow, 32));
			// ���[�g���Z�[�������̐ݒ�
			map.put("rateKbn", model.getTableDataItem(nomRow, 54));
			// �������Œ[�������̐ݒ�
			map.put("kuKbn", model.getTableDataItem(nomRow, 55));
			// ��������Œ[�������̐ݒ�
			map.put("kbKbn", model.getTableDataItem(nomRow, 56));
			// ���ڈ���敪
			map.put("directKbn", model.getTableDataItem(nomRow, 57));
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
			modify(dialog.getDataList());

			// �X�v���b�h�X�V
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, UPD_KBN);
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
			MG0031EditDisplayDialogCtrl dialog = new MG0031EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			dialog.setSelectMap(cmpHmKbnMap, jidMap);
			// ���O�s���擾����
			int nomRow = panel.ssCompanyCodeRoleList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssCompanyCodeRoleList.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ��ЃR�[�h �̐ݒ�
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			// �Ȗږ��̂̐ݒ�
			map.put("cmpKmkName", model.getTableDataItem(nomRow, 1));
			// �⏕�Ȗږ��̂̐ݒ�
			map.put("cmpHkmName", model.getTableDataItem(nomRow, 2));
			// ����ȖڊǗ��̐ݒ�
			map.put("cmpUkmKbn", model.getTableDataItem(nomRow, 37));
			// ����Ȗږ��̂̐ݒ�
			map.put("cmpUkmName", model.getTableDataItem(nomRow, 4));
			// �Ǘ��敪1-6�̐ݒ�
			map.put("knrKbn1", model.getTableDataItem(nomRow, 38));
			map.put("knrKbn2", model.getTableDataItem(nomRow, 39));
			map.put("knrKbn3", model.getTableDataItem(nomRow, 40));
			map.put("knrKbn4", model.getTableDataItem(nomRow, 41));
			map.put("knrKbn5", model.getTableDataItem(nomRow, 42));
			map.put("knrKbn6", model.getTableDataItem(nomRow, 43));
			// �Ǘ�����1-6�̐ݒ�
			map.put("knrName1", model.getTableDataItem(nomRow, 11));
			map.put("knrName2", model.getTableDataItem(nomRow, 12));
			map.put("knrName3", model.getTableDataItem(nomRow, 13));
			map.put("knrName4", model.getTableDataItem(nomRow, 14));
			map.put("knrName5", model.getTableDataItem(nomRow, 15));
			map.put("knrName6", model.getTableDataItem(nomRow, 16));
			// ���v���׋敪1-3�̐ݒ�
			map.put("cmpHmKbn1", model.getTableDataItem(nomRow, 44));
			map.put("cmpHmKbn2", model.getTableDataItem(nomRow, 45));
			map.put("cmpHmKbn3", model.getTableDataItem(nomRow, 46));
			// ���v���ז���1-3�̐ݒ�
			map.put("cmpHmName1", model.getTableDataItem(nomRow, 20));
			map.put("cmpHmName2", model.getTableDataItem(nomRow, 21));
			map.put("cmpHmName3", model.getTableDataItem(nomRow, 22));
			// ���񌎂̐ݒ�
			map.put("cmpKisyu", model.getTableDataItem(nomRow, 23));
			// �����ݒ荀�ڂP-3�̐ݒ�
			map.put("jid1", model.getTableDataItem(nomRow, 47));
			map.put("jid2", model.getTableDataItem(nomRow, 48));
			map.put("jid3", model.getTableDataItem(nomRow, 49));
			// �����̔ԕ������̐ݒ�
			map.put("autoNoKeta", model.getTableDataItem(nomRow, 27));
			// �`�[����敪�̐ݒ�
			map.put("printKbn", model.getTableDataItem(nomRow, 50));
			// ������̏����l�̐ݒ�
			map.put("printDef", model.getTableDataItem(nomRow, 51));
			// ���ꏳ�F�׸ނ̐ݒ�
			map.put("cmpGShoninFlg", model.getTableDataItem(nomRow, 52));
			// �o�����F�׸ނ̐ݒ�
			map.put("cmpKShoninFlg", model.getTableDataItem(nomRow, 53));
			// �{�M�ʉ݃R�[�h�̐ݒ�
			map.put("curCode", model.getTableDataItem(nomRow, 32));
			// ���[�g���Z�[�������̐ݒ�
			map.put("rateKbn", model.getTableDataItem(nomRow, 54));
			// �������Œ[�������̐ݒ�
			map.put("kuKbn", model.getTableDataItem(nomRow, 55));
			// ��������Œ[�������̐ݒ�
			map.put("kbKbn", model.getTableDataItem(nomRow, 56));
			// ���ڈ���敪
			map.put("directKbn", model.getTableDataItem(nomRow, 57));
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
			modify(dialog.getDataList());

			// �X�v���b�h�X�V
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, INSERT_KBN);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ύX����
	 * 
	 * @param map �f�[�^
	 * @throws IOException
	 */
	protected void modify(Map map) throws IOException {
		// ������ʂ̐ݒ�
		addSendValues("flag", "save");
		// ��ЃR�[�h �̐ݒ�
		addSendValues("kaiCode", (String) map.get("kaiCode"));
		// �Ȗږ��̂̐ݒ�
		addSendValues("cmpKmkName", (String) map.get("cmpKmkName"));
		// �⏕�Ȗږ��̂̐ݒ�
		addSendValues("cmpHkmName", (String) map.get("cmpHkmName"));
		// ����ȖڊǗ��̐ݒ�
		addSendValues("cmpUkmName", (String) map.get("cmpUkmName"));
		// ����Ȗږ��̂̐ݒ�
		addSendValues("cmpUkmKbn", (String) map.get("cmpUkmKbn"));
		// �Ǘ��敪1-6�̐ݒ�
		addSendValues("knrKbn1", (String) map.get("knrKbn1"));
		addSendValues("knrKbn2", (String) map.get("knrKbn2"));
		addSendValues("knrKbn3", (String) map.get("knrKbn3"));
		addSendValues("knrKbn4", (String) map.get("knrKbn4"));
		addSendValues("knrKbn5", (String) map.get("knrKbn5"));
		addSendValues("knrKbn6", (String) map.get("knrKbn6"));
		// �Ǘ�����1-6�̐ݒ�
		addSendValues("knrName1", (String) map.get("knrName1"));
		addSendValues("knrName2", (String) map.get("knrName2"));
		addSendValues("knrName3", (String) map.get("knrName3"));
		addSendValues("knrName4", (String) map.get("knrName4"));
		addSendValues("knrName5", (String) map.get("knrName5"));
		addSendValues("knrName6", (String) map.get("knrName6"));
		// ���v���׋敪1-3�̐ݒ�
		addSendValues("cmpHmKbn1", (String) map.get("cmpHmKbn1"));
		addSendValues("cmpHmKbn2", (String) map.get("cmpHmKbn2"));
		addSendValues("cmpHmKbn3", (String) map.get("cmpHmKbn3"));
		// ���v���ז���1-3�̐ݒ�
		addSendValues("cmpHmName1", (String) map.get("cmpHmName1"));
		addSendValues("cmpHmName2", (String) map.get("cmpHmName2"));
		addSendValues("cmpHmName3", (String) map.get("cmpHmName3"));
		// ���񌎂̐ݒ�
		addSendValues("cmpKisyu", (String) map.get("cmpKisyu"));
		// �����ݒ荀�ڂP-3�̐ݒ�
		addSendValues("jid1", (String) map.get("jid1"));
		addSendValues("jid2", (String) map.get("jid2"));
		addSendValues("jid3", (String) map.get("jid3"));
		// �����̔ԕ������̐ݒ�
		addSendValues("autoNoKeta", (String) map.get("autoNoKeta"));
		// �`�[����敪�̐ݒ�
		addSendValues("printKbn", (String) map.get("printKbn"));
		// ������̏����l�̐ݒ�
		addSendValues("printDef", (String) map.get("printDef"));
		// ���ꏳ�F�׸ނ̐ݒ�
		addSendValues("cmpGShoninFlg", (String) map.get("cmpGShoninFlg"));
		// �o�����F�׸ނ̐ݒ�
		addSendValues("cmpKShoninFlg", (String) map.get("cmpKShoninFlg"));
		// �{�M�ʉ݃R�[�h�̐ݒ�
		addSendValues("curCode", (String) map.get("curCode"));
		// ���[�g���Z�[�������̐ݒ�
		addSendValues("rateKbn", (String) map.get("rateKbn"));
		// �������Œ[�������̐ݒ�
		addSendValues("kuKbn", (String) map.get("kuKbn"));
		// ��������Œ[�������̐ݒ�
		addSendValues("kbKbn", (String) map.get("kbKbn"));
		// �v���O�����h�c�̐ݒ�
		addSendValues("prgID", PROGRAM_ID);
		// ���ڈ���敪
		addSendValues("directKbn", (String) map.get("directKbn"));

		if (!request(TARGET_SERVLET)) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel);
		}
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {

		boolean dataExists = true;

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");

		// �v���O�����R�[�h
		addSendValues("prgCode", PROGRAM_ID);

		if (!request(TARGET_SERVLET)) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel);
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();
		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			showMessage(panel, "W00084");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
			String impossible = this.getWord("C00282");
			String possible = this.getWord("C00281");
			String value, text;

			String dirPrintvalue = colum.get(40);

			// ����ȖڊǗ�
			value = colum.get(3);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(37, value);
			colum.set(3, text);

			// �Ǘ��敪1
			value = colum.get(5);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(38, value);
			colum.set(5, text);

			// �Ǘ��敪2
			value = colum.get(6);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(39, value);
			colum.set(6, text);

			// �Ǘ��敪3
			value = colum.get(7);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(40, value);
			colum.set(7, text);

			// �Ǘ��敪4
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(41, value);
			colum.set(8, text);

			// �Ǘ��敪5
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(42, value);
			colum.set(9, text);

			// �Ǘ��敪6
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(43, value);
			colum.set(10, text);

			// ���v���׋敪1
			value = colum.get(17);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(44, value);
			colum.set(17, text);

			// ���v���׋敪2
			value = colum.get(18);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(45, value);
			colum.set(18, text);

			// ���v���׋敪3
			value = colum.get(19);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(46, value);
			colum.set(19, text);

			// �����ݒ荀�ڂP
			value = colum.get(24);
			text = (String) jidMap.get(value);
			colum.add(47, value);
			colum.set(24, text);

			// �����ݒ荀��2
			value = colum.get(25);
			text = (String) jidMap.get(value);
			colum.add(48, value);
			colum.set(25, text);

			// �����ݒ荀��3
			value = colum.get(26);
			text = (String) jidMap.get(value);
			colum.add(49, value);
			colum.set(26, text);

			value = colum.get(27);
			colum.set(27, "0".equals(value) ? "" : value);

			// �`�[����敪
			value = colum.get(28);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(50, value);
			colum.set(28, text);

			// ������̏����l
			value = colum.get(29);
			text = ("0".equals(value) ? this.getWord("C02367") : this.getWord("C02368"));
			colum.add(51, value);
			colum.set(29, text);

			String impossible1 = this.getWord("C02099");
			String possible1 = this.getWord("C02100");

			// ���ꏳ�F�׸�
			value = colum.get(30);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(52, value);
			colum.set(30, text);

			// �o�����F�׸�
			value = colum.get(31);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(53, value);
			colum.set(31, text);

			// ���[�g���Z�[������
			value = colum.get(33);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(54, value);
			colum.set(33, text);

			// �������Œ[������
			value = colum.get(34);
			if ("0".equals(value)) {
				text = this.getWord("C00121");
			} else if ("1".equals(value)) {
				text = this.getWord("C00120");
			} else {
				text = this.getWord("C00215");
			}
			colum.add(55, value);
			colum.set(34, text);

			// ��������Œ[������
			value = colum.get(35);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(56, value);
			colum.set(35, text);

			// ���ڈ���敪
			text = ("0".equals(dirPrintvalue) ? this.getWord("C02367") : this.getWord("C02368"));
			colum.add(57, dirPrintvalue);
			colum.set(36, text);

			cells.add(row, colum);
		}

		((MG0031CompanyControlMasterPanel) panel).setDataList(cells);

		// �f�[�^�W���擾����
		TableDataModel model = ((MG0031CompanyControlMasterPanel) panel).ssCompanyCodeRoleList.getDataSource();

		if (model.getNumRows() > 0) {
			String kaiCode = (String) model.getTableDataItem(0, 0);
			panel.btnDelete.setEnabled(checkCode(kaiCode));
		}

		return dataExists;
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */
	void outptExcel() {

		try {
			if (!find()) return;
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��ЃR�[�h�̎擾
			conds.put("kaiCode", "");
			conds.put("langCode", getLoginLanguage());
			conds.put("prgCode", PROGRAM_ID);
			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �V�K�A�ҏW�A�폜�̏ꍇ�A�X�v���b�h�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param updKbn �X�V�敪
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String kaiCode, int updKbn) throws IOException, TRequestException {
		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �v���O�����R�[�h
		addSendValues("prgCode", PROGRAM_ID);

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

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssCompanyCodeRoleList.getDataSource();
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = modifyDs.getCells();
		// colum�̏�����
		Vector<String> colum = new Vector<String>();
		for (List<String> list : resultList) {

			for (String value : list) {
				// ���ʂ�ǉ�����
				colum.add(value);
			}

			String impossible = this.getWord("C00282");
			String possible = this.getWord("C00281");
			String value, text;

			String dirPrintvalue = colum.get(40);

			// ����ȖڊǗ�
			value = colum.get(3);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(37, value);
			colum.set(3, text);

			// �Ǘ��敪1
			value = colum.get(5);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(38, value);
			colum.set(5, text);

			// �Ǘ��敪2
			value = colum.get(6);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(39, value);
			colum.set(6, text);

			// �Ǘ��敪3
			value = colum.get(7);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(40, value);
			colum.set(7, text);

			// �Ǘ��敪4
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(41, value);
			colum.set(8, text);

			// �Ǘ��敪5
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(42, value);
			colum.set(9, text);

			// �Ǘ��敪6
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(43, value);
			colum.set(10, text);

			// ���v���׋敪1
			value = colum.get(17);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(44, value);
			colum.set(17, text);

			// ���v���׋敪2
			value = colum.get(18);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(45, value);
			colum.set(18, text);

			// ���v���׋敪3
			value = colum.get(19);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(46, value);
			colum.set(19, text);

			// �����ݒ荀�ڂP
			value = colum.get(24);
			text = (String) jidMap.get(value);
			colum.add(47, value);
			colum.set(24, text);

			// �����ݒ荀��2
			value = colum.get(25);
			text = (String) jidMap.get(value);
			colum.add(48, value);
			colum.set(25, text);

			// �����ݒ荀��3
			value = colum.get(26);
			text = (String) jidMap.get(value);
			colum.add(49, value);
			colum.set(26, text);

			value = colum.get(27);
			colum.set(27, "0".equals(value) ? "" : value);

			// �`�[����敪
			value = colum.get(28);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(50, value);
			colum.set(28, text);

			// ������̏����l
			value = colum.get(29);
			text = ("0".equals(value) ? this.getWord("C02367") : this.getWord("C02368"));
			colum.add(51, value);
			colum.set(29, text);

			String impossible1 = this.getWord("C02099");
			String possible1 = this.getWord("C02100");

			// ���ꏳ�F�׸�
			value = colum.get(30);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(52, value);
			colum.set(30, text);

			// �o�����F�׸�
			value = colum.get(31);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(53, value);
			colum.set(31, text);

			// ���[�g���Z�[������
			value = colum.get(33);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(54, value);
			colum.set(33, text);

			// �������Œ[������
			value = colum.get(34);
			if ("0".equals(value)) {
				text = this.getWord("C00121");
			} else if ("1".equals(value)) {
				text = this.getWord("C00120");
			} else {
				text = this.getWord("C00215");
			}
			colum.add(55, value);
			colum.set(34, text);

			// ��������Œ[������
			value = colum.get(35);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(56, value);
			colum.set(35, text);

			// ���ڈ���敪
			text = ("0".equals(dirPrintvalue) ? this.getWord("C02367") : this.getWord("C02368"));
			colum.add(57, dirPrintvalue);
			colum.set(36, text);
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssCompanyCodeRoleList.getCurrentRow(), colum);
		}
		return cells;
	}
}
