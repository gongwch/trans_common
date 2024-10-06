package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * ����Ȗڃ}�X�^��ʃR���g���[��
 */
public class MG0100BreakDownItemMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0100";

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0100BreakDownItemMasterServlet";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/** �p�l�� */
	protected MG0100BreakDownItemMasterPanel panel;

	protected Map triCodeFlgMap;

	/** ����Ȗڃ}�X�^�ҏW��� */
	MG0100EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */

	public MG0100BreakDownItemMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0100BreakDownItemMasterPanel(this);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
		// �J�n�R�[�h�ƏI���R�[�h�̏�����
		panel.ctrlSubItem.getField().setEditable(false);
		panel.ctrlBeginBreakDownItem.getField().setEditable(false);
		panel.ctrlEndBreakDownItem.getField().setEditable(false);

		panel.ctrlSubItem.getButton().setEnabled(false);
		panel.ctrlBeginBreakDownItem.getButton().setEnabled(false);
		panel.ctrlEndBreakDownItem.getButton().setEnabled(false);

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
		// ����̎w�肳�ꂽ���̂�\������
		triCodeFlgMap = new LinkedHashMap();
		triCodeFlgMap.put("0", getWord("C01279"));
		triCodeFlgMap.put("2", getWord("C00401"));
		triCodeFlgMap.put("3", getWord("C00203"));
		triCodeFlgMap.put("4", getWord("C02122"));

	}

	protected void init() {
		panel.ctrlItem.requestTextFocus();

	}

	/**
	 * (�Ȗ�)
	 */
	public void setItemData() {
		String itemCode = Util.avoidNull(panel.ctrlItem.getValue());
		panel.ctrlItem.getInputParameter().setSubItemDivision("1");
		panel.ctrlItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * (�⏕�Ȗ�)
	 */
	public void setSubItemData() {
		// ��ЃR�[�h
		panel.ctrlSubItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		String subItemCode = Util.avoidNull(panel.ctrlSubItem.getValue());
		panel.ctrlSubItem.getInputParameter().setBreakDownItemDivision("1");
		panel.ctrlSubItem.getInputParameter().setSubItemCode(subItemCode);
	}

	/**
	 * (����ȖڊJ�n)
	 */
	public void setBeginBreakDownItemData() {
		// ��ЃR�[�h
		panel.ctrlBeginBreakDownItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		panel.ctrlBeginBreakDownItem.getInputParameter().setSubItemCode(panel.ctrlSubItem.getValue());
		String beginBreakDownItemCode = Util.avoidNull(panel.ctrlBeginBreakDownItem.getValue());
		panel.ctrlBeginBreakDownItem.getInputParameter().setBreakDownItemCode(beginBreakDownItemCode);
		panel.ctrlBeginBreakDownItem.getInputParameter().setEndCode(panel.ctrlEndBreakDownItem.getValue());
	}

	/**
	 * (����ȖڏI��)
	 */
	public void setEndBreakDownItemData() {
		// ��ЃR�[�h
		panel.ctrlEndBreakDownItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		panel.ctrlEndBreakDownItem.getInputParameter().setSubItemCode(panel.ctrlSubItem.getValue());
		String endBreakDownItemCode = Util.avoidNull(panel.ctrlEndBreakDownItem.getValue());
		panel.ctrlEndBreakDownItem.getInputParameter().setBreakDownItemCode(endBreakDownItemCode);
		panel.ctrlEndBreakDownItem.getInputParameter().setBeginCode(panel.ctrlBeginBreakDownItem.getValue());
	}

	/**
	 * �p�l���擾
	 * 
	 * @return ����Ȗڃ}�X�^�p�l��
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
			createEditDisplayDialog("C01698");

			dialog.setTriCodeFlgMap(triCodeFlgMap);

			// �ҏW��ʂ̕\��
			dialog.show();

			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}

			// �f�[�^���ҏW����
			modify(dialog.getDataList(), true);
			// �I���s�ێ�����
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			String hkmCode = dialog.getDataList().get("hkmCode").toString();
			String ukmCode = dialog.getDataList().get("ukmCode").toString();
			modifySpreadRow(kmkCode, hkmCode, ukmCode, INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �_�C�A���O����
	 * 
	 * @param titleID
	 */
	protected void createEditDisplayDialog(String titleID) {
		dialog = new MG0100EditDisplayDialogCtrl(panel.getParentFrame(), titleID);
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			createEditDisplayDialog("C00977");

			dialog.setTriCodeFlgMap(triCodeFlgMap);
			// ���O�s���擾����
			int nomRow = panel.ssBreakDownItem.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssBreakDownItem.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ȖڃR�[�h�̐ݒ�
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("hkmCode", model.getTableDataItem(nomRow, 2));
			// ����ȖڃR�[�h�̐ݒ�
			map.put("ukmCode", model.getTableDataItem(nomRow, 3));
			// ����Ȗږ��̂̐ݒ�
			map.put("ukmName", model.getTableDataItem(nomRow, 4));
			// ����Ȗڗ��̂̐ݒ�
			map.put("ukmName_S", model.getTableDataItem(nomRow, 5));
			// ����Ȗڌ������̂̐ݒ�
			map.put("ukmName_K", model.getTableDataItem(nomRow, 6));
			// ����ŃR�[�h�̐ݒ�
			map.put("zeiCode", model.getTableDataItem(nomRow, 7));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 33)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 34)));
			// �����`�[�����׸ނ̐ݒ�
			map.put("glFlg1", model.getTableDataItem(nomRow, 35));
			// �o���`�[�����׸ނ̐ݒ�
			map.put("glFlg2", model.getTableDataItem(nomRow, 36));
			// �U�֓`�[�����׸ނ̐ݒ�
			map.put("glFlg3", model.getTableDataItem(nomRow, 37));
			// �o��Z�`�[�����׸ނ̐ݒ�
			map.put("apFlg1", model.getTableDataItem(nomRow, 38));
			// �������`�[�����׸ނ̐ݒ�
			map.put("apFlg2", model.getTableDataItem(nomRow, 39));
			// ���v��`�[�����׸ނ̐ݒ�
			map.put("arFlg1", model.getTableDataItem(nomRow, 40));
			// �������`�[�����׸ނ̐ݒ�
			map.put("arFlg2", model.getTableDataItem(nomRow, 41));
			// ���Y�v��`�[�����׸ނ̐ݒ�
			map.put("faFlg1", model.getTableDataItem(nomRow, 42));
			// �x���˗��`�[�����׸ނ̐ݒ�
			map.put("faFlg2", model.getTableDataItem(nomRow, 43));
			// ���ʉݓ��̓t���O�̐ݒ�
			map.put("mcrFlg", model.getTableDataItem(nomRow, 44));
			// �]���֑Ώۃt���O�̐ݒ�
			map.put("excFlg", model.getTableDataItem(nomRow, 45));
			// �������̓t���O�̐ݒ�
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 46));
			// �����������׸ނ̐ݒ�
			map.put("hasFlg", model.getTableDataItem(nomRow, 47));
			// �Ј����̓t���O�̐ݒ�
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 48));
			// �Ǘ��P-6���̓t���O�̐ݒ�
			map.put("knrFlg1", model.getTableDataItem(nomRow, 49));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 50));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 51));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 52));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 53));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 54));
			// ���v1-3���̓t���O�̐ݒ�
			map.put("hmFlg1", model.getTableDataItem(nomRow, 55));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 56));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 57));
			// ����ېœ��̓t���O�̐ݒ�
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 58));
			// �d���ېœ��̓t���O�̐ݒ�
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 59));
			// ������ʂ̐ݒ�ނ̐ݒ�
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
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			String hkmCode = dialog.getDataList().get("hkmCode").toString();
			String ukmCode = dialog.getDataList().get("ukmCode").toString();
			modifySpreadRow(kmkCode, hkmCode, ukmCode, UPD_KBN);
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

			dialog.setTriCodeFlgMap(triCodeFlgMap);
			// ���O�s���擾����
			int nomRow = panel.ssBreakDownItem.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssBreakDownItem.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ȖڃR�[�h�̐ݒ�
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("hkmCode", model.getTableDataItem(nomRow, 2));
			// ����ȖڃR�[�h�̐ݒ�
			map.put("ukmCode", model.getTableDataItem(nomRow, 3));
			// ����Ȗږ��̂̐ݒ�
			map.put("ukmName", model.getTableDataItem(nomRow, 4));
			// ����Ȗڗ��̂̐ݒ�
			map.put("ukmName_S", model.getTableDataItem(nomRow, 5));
			// ����Ȗڌ������̂̐ݒ�
			map.put("ukmName_K", model.getTableDataItem(nomRow, 6));
			// ����ŃR�[�h�̐ݒ�
			map.put("zeiCode", model.getTableDataItem(nomRow, 7));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 33)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 34)));
			// �����`�[�����׸ނ̐ݒ�
			map.put("glFlg1", model.getTableDataItem(nomRow, 35));
			// �o���`�[�����׸ނ̐ݒ�
			map.put("glFlg2", model.getTableDataItem(nomRow, 36));
			// �U�֓`�[�����׸ނ̐ݒ�
			map.put("glFlg3", model.getTableDataItem(nomRow, 37));
			// �o��Z�`�[�����׸ނ̐ݒ�
			map.put("apFlg1", model.getTableDataItem(nomRow, 38));
			// �������`�[�����׸ނ̐ݒ�
			map.put("apFlg2", model.getTableDataItem(nomRow, 39));
			// ���v��`�[�����׸ނ̐ݒ�
			map.put("arFlg1", model.getTableDataItem(nomRow, 40));
			// �������`�[�����׸ނ̐ݒ�
			map.put("arFlg2", model.getTableDataItem(nomRow, 41));
			// ���Y�v��`�[�����׸ނ̐ݒ�
			map.put("faFlg1", model.getTableDataItem(nomRow, 42));
			// �x���˗��`�[�����׸ނ̐ݒ�
			map.put("faFlg2", model.getTableDataItem(nomRow, 43));
			// ���ʉݓ��̓t���O�̐ݒ�
			map.put("mcrFlg", model.getTableDataItem(nomRow, 44));
			// �]���֑Ώۃt���O�̐ݒ�
			map.put("excFlg", model.getTableDataItem(nomRow, 45));
			// �������̓t���O�̐ݒ�
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 46));
			// �����������׸ނ̐ݒ�
			map.put("hasFlg", model.getTableDataItem(nomRow, 47));
			// �Ј����̓t���O�̐ݒ�
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 48));
			// �Ǘ��P-6���̓t���O�̐ݒ�
			map.put("knrFlg1", model.getTableDataItem(nomRow, 49));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 50));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 51));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 52));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 53));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 54));
			// ���v1-3���̓t���O�̐ݒ�
			map.put("hmFlg1", model.getTableDataItem(nomRow, 55));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 56));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 57));
			// ����ېœ��̓t���O�̐ݒ�
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 58));
			// �d���ېœ��̓t���O�̐ݒ�
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 59));
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

			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			String hkmCode = dialog.getDataList().get("hkmCode").toString();
			String ukmCode = dialog.getDataList().get("ukmCode").toString();
			modifySpreadRow(kmkCode, hkmCode, ukmCode, INSERT_KBN);

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
		// �ȖڃR�[�h
		addSendValues("kmkCode", (String) map.get("kmkCode"));
		// �⏕�ȖڃR�[�h
		addSendValues("hkmCode", (String) map.get("hkmCode"));
		// ����ȖڃR�[�h
		addSendValues("ukmCode", (String) map.get("ukmCode"));
		// ����Ȗږ���
		addSendValues("ukmName", (String) map.get("ukmName"));
		// ����Ȗڗ���
		addSendValues("ukmName_S", (String) map.get("ukmName_S"));
		// ����Ȗڌ�������
		addSendValues("ukmName_K", (String) map.get("ukmName_K"));
		// ����ŃR�[�h
		addSendValues("zeiCode", (String) map.get("zeiCode"));
		// �������̓t���O
		addSendValues("triCodeFlg", (String) map.get("triCodeFlg"));
		// �����`�[�����׸�
		addSendValues("glFlg1", (String) map.get("glFlg1"));
		// �o���`�[�����׸�
		addSendValues("glFlg2", (String) map.get("glFlg2"));
		// �U�֓`�[�����׸�
		addSendValues("glFlg3", (String) map.get("glFlg3"));
		// �o��Z�`�[�����׸�
		addSendValues("apFlg1", (String) map.get("apFlg1"));
		// �������`�[�����׸�
		addSendValues("apFlg2", (String) map.get("apFlg2"));
		// ���v��`�[�����׸�
		addSendValues("arFlg1", (String) map.get("arFlg1"));
		// �������`�[�����׸�
		addSendValues("arFlg2", (String) map.get("arFlg2"));
		// ���Y�v��`�[�����׸�
		addSendValues("faFlg1", (String) map.get("faFlg1"));
		// �x���˗��`�[�����׸�
		addSendValues("faFlg2", (String) map.get("faFlg2"));
		// �����������׸�
		addSendValues("hasFlg", (String) map.get("hasFlg"));
		// �Ј����̓t���O
		addSendValues("empCodeFlg", (String) map.get("empCodeFlg"));
		// �Ǘ��P-6���̓t���O
		addSendValues("knrFlg1", (String) map.get("knrFlg1"));
		addSendValues("knrFlg2", (String) map.get("knrFlg2"));
		addSendValues("knrFlg3", (String) map.get("knrFlg3"));
		addSendValues("knrFlg4", (String) map.get("knrFlg4"));
		addSendValues("knrFlg5", (String) map.get("knrFlg5"));
		addSendValues("knrFlg6", (String) map.get("knrFlg6"));
		// ���v1-3���̓t���O
		addSendValues("hmFlg1", (String) map.get("hmFlg1"));
		addSendValues("hmFlg2", (String) map.get("hmFlg2"));
		addSendValues("hmFlg3", (String) map.get("hmFlg3"));
		// ����ېœ��̓t���O
		addSendValues("uriZeiFlg", (String) map.get("uriZeiFlg"));
		// �d���ېœ��̓t���O
		addSendValues("sirZeiFlg", (String) map.get("sirZeiFlg"));
		// ���ʉݓ��̓t���O
		addSendValues("mcrFlg", (String) map.get("mcrFlg"));
		// �]���֑Ώۃt���O
		addSendValues("excFlg", (String) map.get("excFlg"));
		// �J�n�N����
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N����
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�̐ݒ�
		addSendValues("prgID", PROGRAM_ID);
		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel.getParentFrame());
		}
	}

	/**
	 * �V�K�A���ʁA�ҏW�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kmkCode, String hkmCode, String ukmCode, int updKbn)
		throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kmkCode, hkmCode, ukmCode, updKbn);
		TTable ssPanelSpread = panel.ssBreakDownItem;
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
		lockBtn(panel.ssBreakDownItem.getDataSource().getNumRows() != 0);
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
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCode
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String kmkCode, String hkmCode, String ukmCode, int updKbn)
		throws IOException, TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �ȖڃR�[�h�̐ݒ�
		addSendValues("kmkCode", Util.avoidNull(kmkCode));
		// �⏕�ȖڃR�[�h�̐ݒ�
		addSendValues("hkmCode", Util.avoidNull(hkmCode));
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginUkmCode", Util.avoidNull(ukmCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endUkmCode", Util.avoidNull(ukmCode));
		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssBreakDownItem.getDataSource();
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

			// ���ʉݓ��̓t���O
			String mcrFlg = colum.get(37);
			// �]���֑Ώۃt���O
			String excFlg = colum.get(38);
			// �������̓t���O
			String hasFlg = colum.get(17);
			// �����������׸�
			String triCodeFlg = colum.get(18);
			// �Ј����̓t���O
			String empCodeFlg = colum.get(19);
			// �Ǘ��P-6���̓t���O
			String knrFlg1 = colum.get(20);
			String knrFlg2 = colum.get(21);
			String knrFlg3 = colum.get(22);
			String knrFlg4 = colum.get(23);
			String knrFlg5 = colum.get(24);
			String knrFlg6 = colum.get(25);
			// ���v1-3���̓t���O
			String hmFlg1 = colum.get(26);
			String hmFlg2 = colum.get(27);
			String hmFlg3 = colum.get(28);
			// ����ېœ��̓t���O
			String uriZeiFlg = colum.get(29);
			// �d���ېœ��̓t���O
			String sirZeiFlg = colum.get(30);
			// �J�n�N����
			String strDate = colum.get(31);
			// �I���N����
			String endDate = colum.get(32);
			String value, text;
			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// �����`�[�����׸�
			colum.add(35, "");
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(35, value);
			colum.set(8, text);

			// �o���`�[�����׸�
			colum.add(36, "");
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(36, value);
			colum.set(9, text);

			// �U�֓`�[�����׸�
			colum.add(37, "");
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(37, value);
			colum.set(10, text);

			// �o��Z�`�[�����׸�
			colum.add(38, "");
			value = colum.get(11);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(38, value);
			colum.set(11, text);

			// �������`�[�����׸�
			colum.add(39, "");
			value = colum.get(12);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(39, value);
			colum.set(12, text);

			// ���v��`�[�����׸�
			colum.add(40, "");
			value = colum.get(13);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(40, value);
			colum.set(13, text);

			// �������`�[�����׸�
			colum.add(41, "");
			value = colum.get(14);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(41, value);
			colum.set(14, text);

			// ���Y�v��`�[�����׸�
			colum.add(42, "");
			value = colum.get(15);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(42, value);
			colum.set(15, text);

			// �x���˗��`�[�����׸�
			colum.add(43, "");
			value = colum.get(16);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(43, value);
			colum.set(16, text);

			// ���ʉݓ��̓t���O
			colum.add(44, "");
			value = mcrFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(44, value);
			colum.set(17, text);

			// �]���֑Ώۃt���O
			colum.add(45, "");
			value = excFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(45, value);
			colum.set(18, text);

			// �������̓t���O
			colum.add(46, "");
			value = hasFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(46, value);
			colum.set(19, text);

			// �����������׸�
			colum.add(47, "");
			value = triCodeFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(47, value);
			colum.set(20, text);

			String possible1 = this.getWord("C02371");
			// �Ј����̓t���O
			colum.add(48, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(48, value);
			colum.set(21, text);

			// �Ǘ��P���̓t���O
			colum.add(49, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(49, value);
			colum.set(22, text);

			// �Ǘ�2���̓t���O
			colum.add(50, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(50, value);
			colum.set(23, text);

			// �Ǘ�3���̓t���O
			colum.add(51, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(51, value);
			colum.set(24, text);

			// �Ǘ�4���̓t���O
			colum.add(52, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(52, value);
			colum.set(25, text);

			// �Ǘ�5���̓t���O
			colum.add(53, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(53, value);
			colum.set(26, text);

			// �Ǘ�6���̓t���O
			colum.add(54, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(54, value);
			colum.set(27, text);

			// ���v1���̓t���O
			colum.add(55, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(28, text);

			// ���v2���̓t���O
			colum.add(56, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(56, value);
			colum.set(29, text);

			// ���v3���̓t���O
			colum.add(57, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(57, value);
			colum.set(30, text);

			// ����ېœ��̓t���O
			colum.add(58, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(58, value);
			colum.set(31, text);

			// �d���ېœ��̓t���O
			colum.add(59, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(32, text);

			try {
				colum.set(33, DateUtil.toYMDString(DateUtil.toYMDDate(strDate)));
				colum.set(34, DateUtil.toYMDString(DateUtil.toYMDDate(endDate)));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssBreakDownItem.getCurrentRow(), colum);
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
				int nomRow = panel.ssBreakDownItem.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssBreakDownItem.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// �ȖڃR�[�h�̎擾
				String kmkCode = (String) model.getTableDataItem(nomRow, 1);
				// �⏕�ȖڃR�[�h�̎擾
				String hkmCode = (String) model.getTableDataItem(nomRow, 2);
				// ����ȖڃR�[�h�̎擾
				String ukmCode = (String) model.getTableDataItem(nomRow, 3);
				// ������ʂ̐ݒ�
				addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				addSendValues("kaiCode", kaiCode);
				// �ȖڃR�[�h�̐ݒ�
				addSendValues("kmkCode", kmkCode);
				// �⏕�ȖڃR�[�h�̐ݒ�
				addSendValues("hkmCode", hkmCode);
				// ����ȖڃR�[�h�̐ݒ�
				addSendValues("ukmCode", ukmCode);

				// �T�[�u���b�g�̐ڑ���
				if (!request(TARGET_SERVLET)) {
					errorHandler(panel.getParentFrame());
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
		int row = panel.ssBreakDownItem.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssBreakDownItem.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssBreakDownItem.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssBreakDownItem.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssBreakDownItem.getVertSB().setValue(0);
			panel.ssBreakDownItem.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssBreakDownItem.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	protected void selectSpreadRow(int row) {

		panel.ssBreakDownItem.setRowSelection(row, row);
		panel.ssBreakDownItem.setCurrentCell(row, 0);

	}

	/**
	 * ��������
	 * 
	 * @return true:����
	 */
	boolean find() {
		String beginBreakDownItem = panel.ctrlBeginBreakDownItem.getValue().toString();
		String endBreakDownItem = panel.ctrlEndBreakDownItem.getValue().toString();

		try {

			if (Util.isSmallerThen(beginBreakDownItem, endBreakDownItem) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginBreakDownItem.getField().requestFocus();
				showMessage(panel, "W00036", "C00024");
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
	 * @return true:����
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �ȖڃR�[�h�̎擾
		String kmkCode = panel.ctrlItem.getField().getText();
		// �⏕�ȖڃR�[�h�̎擾
		String hkmCode = panel.ctrlSubItem.getField().getText();
		// �J�n�R�[�h�̐ݒ�
		String beginUkmCode = panel.ctrlBeginBreakDownItem.getField().getText();
		// �I���R�[�h�̐ݒ�
		String endUkmCode = panel.ctrlEndBreakDownItem.getField().getText();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �ȖڃR�[�h�̐ݒ�
		addSendValues("kmkCode", Util.avoidNull(kmkCode));
		// �⏕�ȖڃR�[�h�̐ݒ�
		addSendValues("hkmCode", Util.avoidNull(hkmCode));
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginUkmCode", Util.avoidNull(beginUkmCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endUkmCode", Util.avoidNull(endUkmCode));

		// ���M
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel.getParentFrame());
			return false;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();
		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			panel.ctrlBeginBreakDownItem.getField().requestFocus();
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

			// ���ʉݓ��̓t���O
			String mcrFlg = colum.get(37);
			// �]���֑Ώۃt���O
			String excFlg = colum.get(38);
			// �������̓t���O
			String hasFlg = colum.get(17);
			// �����������׸�
			String triCodeFlg = colum.get(18);
			// �Ј����̓t���O
			String empCodeFlg = colum.get(19);
			// �Ǘ��P-6���̓t���O
			String knrFlg1 = colum.get(20);
			String knrFlg2 = colum.get(21);
			String knrFlg3 = colum.get(22);
			String knrFlg4 = colum.get(23);
			String knrFlg5 = colum.get(24);
			String knrFlg6 = colum.get(25);
			// ���v1-3���̓t���O
			String hmFlg1 = colum.get(26);
			String hmFlg2 = colum.get(27);
			String hmFlg3 = colum.get(28);
			// ����ېœ��̓t���O
			String uriZeiFlg = colum.get(29);
			// �d���ېœ��̓t���O
			String sirZeiFlg = colum.get(30);
			// �J�n�N����
			String strDate = colum.get(31);
			// �I���N����
			String endDate = colum.get(32);
			String value, text;
			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// �����`�[�����׸�
			colum.add(35, "");
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(35, value);
			colum.set(8, text);

			// �o���`�[�����׸�
			colum.add(36, "");
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(36, value);
			colum.set(9, text);

			// �U�֓`�[�����׸�
			colum.add(37, "");
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(37, value);
			colum.set(10, text);

			// �o��Z�`�[�����׸�
			colum.add(38, "");
			value = colum.get(11);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(38, value);
			colum.set(11, text);

			// �������`�[�����׸�
			colum.add(39, "");
			value = colum.get(12);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(39, value);
			colum.set(12, text);

			// ���v��`�[�����׸�
			colum.add(40, "");
			value = colum.get(13);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(40, value);
			colum.set(13, text);

			// �������`�[�����׸�
			colum.add(41, "");
			value = colum.get(14);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(41, value);
			colum.set(14, text);

			// ���Y�v��`�[�����׸�
			colum.add(42, "");
			value = colum.get(15);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(42, value);
			colum.set(15, text);

			// �x���˗��`�[�����׸�
			colum.add(43, "");
			value = colum.get(16);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(43, value);
			colum.set(16, text);

			// ���ʉݓ��̓t���O
			colum.add(44, "");
			value = mcrFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(44, value);
			colum.set(17, text);

			// �]���֑Ώۃt���O
			colum.add(45, "");
			value = excFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(45, value);
			colum.set(18, text);

			// �������̓t���O
			colum.add(46, "");
			value = hasFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(46, value);
			colum.set(19, text);

			// �����������׸�
			colum.add(47, "");
			value = triCodeFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(47, value);
			colum.set(20, text);

			String possible1 = this.getWord("C02371");
			// �Ј����̓t���O
			colum.add(48, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(48, value);
			colum.set(21, text);

			// �Ǘ��P���̓t���O
			colum.add(49, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(49, value);
			colum.set(22, text);

			// �Ǘ�2���̓t���O
			colum.add(50, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(50, value);
			colum.set(23, text);

			// �Ǘ�3���̓t���O
			colum.add(51, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(51, value);
			colum.set(24, text);

			// �Ǘ�4���̓t���O
			colum.add(52, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(52, value);
			colum.set(25, text);

			// �Ǘ�5���̓t���O
			colum.add(53, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(53, value);
			colum.set(26, text);

			// �Ǘ�6���̓t���O
			colum.add(54, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(54, value);
			colum.set(27, text);

			// ���v1���̓t���O
			colum.add(55, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(28, text);

			// ���v2���̓t���O
			colum.add(56, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(56, value);
			colum.set(29, text);

			// ���v3���̓t���O
			colum.add(57, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(57, value);
			colum.set(30, text);

			// ����ېœ��̓t���O
			colum.add(58, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(58, value);
			colum.set(31, text);

			// �d���ېœ��̓t���O
			colum.add(59, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(32, text);

			try {
				colum.set(33, DateUtil.toYMDString(DateUtil.toYMDDate(strDate)));
				colum.set(34, DateUtil.toYMDString(DateUtil.toYMDDate(endDate)));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
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
			panel.ctrlBeginBreakDownItem.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��ЃR�[�h�̎擾
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("kmkCode", panel.ctrlItem.getField().getText());
			// �⏕�ȖڃR�[�h�̎擾
			conds.put("hkmCode", panel.ctrlSubItem.getField().getText());
			// �J�n�R�[�h�̎擾
			conds.put("beginUkmCode", panel.ctrlBeginBreakDownItem.getField().getText());
			// �I���R�[�h�̎擾
			conds.put("endUkmCode", panel.ctrlEndBreakDownItem.getField().getText());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

}
