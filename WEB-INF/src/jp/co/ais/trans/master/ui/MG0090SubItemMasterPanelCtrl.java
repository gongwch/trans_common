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
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * �⏕�Ȗڃ}�X�^��ʃR���g���[��
 * 
 * @author yangjing
 */
public class MG0090SubItemMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0090";

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0090SubItemMasterServlet";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/** �p�l�� */
	protected MG0090SubItemMasterPanel panel;

	protected Map triCodeFlgMap;

	/** �⏕�Ȗڃ}�X�^�ҏW��� */
	MG0090EditDisplayDialogCtrl dialog;

	/**
	 * �R���X�g���N�^.
	 */

	public MG0090SubItemMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0090SubItemMasterPanel(this);
			// �J�n�R�[�h�ƏI���R�[�h�̏�����
			panel.ctrlBeginSubItem.getField().setEditable(false);
			panel.ctrlEndSubItem.getField().setEditable(false);
			panel.ctrlBeginSubItem.getButton().setEnabled(false);
			panel.ctrlEndSubItem.getButton().setEnabled(false);

			// ����̎w�肳�ꂽ���̂�\������
			triCodeFlgMap = new LinkedHashMap();
			triCodeFlgMap.put("0", getWord("C01279"));
			triCodeFlgMap.put("2", getWord("C00401"));
			triCodeFlgMap.put("3", getWord("C00203"));
			triCodeFlgMap.put("4", getWord("C02122"));

			EventQueue.invokeLater(new Runnable() {

				public void run() {
					init();
				}
			});

			setItemCondition();
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * �ȖڃR���|�[�l���g �����ݒ�(�w�b�_)
	 */

	public void setItemCondition() {
		// ���O�C�����[�U�[�̉�ЃR�[�h
		String itemCode = Util.avoidNull(panel.ctrlItem.getValue());
		panel.ctrlItem.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
		panel.ctrlItem.getInputParameter().setSubItemDivision("1");
		panel.ctrlItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * 
	 */
	public void setBeginItemSubCondition() {

		String beginSubItemCode = Util.avoidNull(panel.ctrlBeginSubItem.getValue());
		panel.ctrlBeginSubItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		panel.ctrlBeginSubItem.getInputParameter().setSubItemDivision("1");
		panel.ctrlBeginSubItem.getInputParameter().setSubItemCode(beginSubItemCode);
	}

	/**
	 * 
	 */
	public void setEndItemSubCondition() {

		String endSubItemCode = Util.avoidNull(panel.ctrlBeginSubItem.getValue());
		panel.ctrlEndSubItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		panel.ctrlEndSubItem.getInputParameter().setSubItemDivision("1");
		panel.ctrlEndSubItem.getInputParameter().setSubItemCode(endSubItemCode);
	}

	protected void init() {
		panel.ctrlItem.requestTextFocus();
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �⏕�Ȗڃ}�X�^�p�l��
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
			modifySpreadRow(kmkCode, hkmCode, INSERT_KBN);
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
		dialog = new MG0090EditDisplayDialogCtrl(panel.getParentFrame(), titleID);
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			createEditDisplayDialog("C00977");

			dialog.setTriCodeFlgMap(triCodeFlgMap);
			// ���O�s���擾����
			int nomRow = panel.ssSubItem.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssSubItem.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ȖڃR�[�h�̐ݒ�
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("hkmCode", model.getTableDataItem(nomRow, 2));
			// �⏕�Ȗږ��̂̐ݒ�
			map.put("hkmName", model.getTableDataItem(nomRow, 3));
			// �⏕�Ȗڗ��̂̐ݒ�
			map.put("hkmName_S", model.getTableDataItem(nomRow, 4));
			// �⏕�Ȗڌ������̂̐ݒ�
			map.put("hkmName_K", model.getTableDataItem(nomRow, 5));
			// ����敪�̐ݒ�
			map.put("ukmKbn", model.getTableDataItem(nomRow, 35));
			// ����ŃR�[�h�̐ݒ�
			map.put("zeiCode", model.getTableDataItem(nomRow, 7));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 33)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 34)));
			// �����`�[�����׸ނ̐ݒ�
			map.put("glFlg1", model.getTableDataItem(nomRow, 36));
			// �o���`�[�����׸ނ̐ݒ�
			map.put("glFlg2", model.getTableDataItem(nomRow, 37));
			// �U�֓`�[�����׸ނ̐ݒ�
			map.put("glFlg3", model.getTableDataItem(nomRow, 38));
			// �o��Z�`�[�����׸ނ̐ݒ�
			map.put("apFlg1", model.getTableDataItem(nomRow, 39));
			// �������`�[�����׸ނ̐ݒ�
			map.put("apFlg2", model.getTableDataItem(nomRow, 40));
			// ���v��`�[�����׸ނ̐ݒ�
			map.put("arFlg1", model.getTableDataItem(nomRow, 41));
			// �������`�[�����׸ނ̐ݒ�
			map.put("arFlg2", model.getTableDataItem(nomRow, 42));
			// ���Y�v��`�[�����׸ނ̐ݒ�
			map.put("faFlg1", model.getTableDataItem(nomRow, 43));
			// �x���˗��`�[�����׸ނ̐ݒ�
			map.put("faFlg2", model.getTableDataItem(nomRow, 44));
			// �������̓t���O�̐ݒ�
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 45));
			// �����������׸ނ̐ݒ�
			map.put("hasFlg", model.getTableDataItem(nomRow, 46));
			// �Ј����̓t���O�̐ݒ�
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 47));
			// �Ǘ�����1-6�t���O�̐ݒ�
			map.put("knrFlg1", model.getTableDataItem(nomRow, 48));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 49));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 50));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 51));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 52));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 53));
			// ���v����1-3�t���O�̐ݒ�
			map.put("hmFlg1", model.getTableDataItem(nomRow, 54));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 55));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 56));
			// ����ېœ��̓t���O�̐ݒ�
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 57));
			// �d���ېœ��̓t���O�̐ݒ�
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 58));
			// ���ʉݓ��̓t���O�̐ݒ�
			map.put("mcrFlg", model.getTableDataItem(nomRow, 59));
			// �]���֑Ώۃt���O�̐ݒ�
			map.put("excFlg", model.getTableDataItem(nomRow, 60));
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
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			String hkmCode = dialog.getDataList().get("hkmCode").toString();
			modifySpreadRow(kmkCode, hkmCode, UPD_KBN);
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
			int nomRow = panel.ssSubItem.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssSubItem.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �ȖڃR�[�h�̐ݒ�
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// �⏕�ȖڃR�[�h�̐ݒ�
			map.put("hkmCode", model.getTableDataItem(nomRow, 2));
			// �⏕�Ȗږ��̂̐ݒ�
			map.put("hkmName", model.getTableDataItem(nomRow, 3));
			// �⏕�Ȗڗ��̂̐ݒ�
			map.put("hkmName_S", model.getTableDataItem(nomRow, 4));
			// �⏕�Ȗڌ������̂̐ݒ�
			map.put("hkmName_K", model.getTableDataItem(nomRow, 5));
			// ����敪�̐ݒ�
			map.put("ukmKbn", model.getTableDataItem(nomRow, 35));
			// ����ŃR�[�h�̐ݒ�
			map.put("zeiCode", model.getTableDataItem(nomRow, 7));
			// �����`�[�����׸ނ̐ݒ�
			map.put("glFlg1", model.getTableDataItem(nomRow, 36));
			// �o���`�[�����׸ނ̐ݒ�
			map.put("glFlg2", model.getTableDataItem(nomRow, 37));
			// �U�֓`�[�����׸ނ̐ݒ�
			map.put("glFlg3", model.getTableDataItem(nomRow, 38));
			// �����`�[�����׸ނ̐ݒ�
			map.put("apFlg1", model.getTableDataItem(nomRow, 39));
			// �U�֓`�[�����׸ނ̐ݒ�
			map.put("apFlg2", model.getTableDataItem(nomRow, 40));
			// ���v��`�[�����׸ނ̐ݒ�
			map.put("arFlg1", model.getTableDataItem(nomRow, 41));
			// �U�֓`�[�����׸ނ̐ݒ�
			map.put("arFlg2", model.getTableDataItem(nomRow, 42));
			// ���Y�v��`�[�����׸ނ̐ݒ�
			map.put("faFlg1", model.getTableDataItem(nomRow, 43));
			// �x���˗��`�[�����׸ނ̐ݒ�
			map.put("faFlg2", model.getTableDataItem(nomRow, 44));
			// �������̓t���O�̐ݒ�
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 45));
			// �����������׸ނ̐ݒ�
			map.put("hasFlg", model.getTableDataItem(nomRow, 46));
			// �Ј����̓t���O�̐ݒ�
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 47));
			// �Ǘ�����1-6�t���O�̐ݒ�
			map.put("knrFlg1", model.getTableDataItem(nomRow, 48));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 49));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 50));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 51));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 52));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 53));
			// ���v����1-3�t���O�̐ݒ�
			map.put("hmFlg1", model.getTableDataItem(nomRow, 54));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 55));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 56));
			// ����ېœ��̓t���O�̐ݒ�
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 57));
			// �d���ېœ��̓t���O�̐ݒ�
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 58));
			// ���ʉݓ��̓t���O�̐ݒ�
			map.put("mcrFlg", model.getTableDataItem(nomRow, 59));
			// �]���֑Ώۃt���O�̐ݒ�
			map.put("excFlg", model.getTableDataItem(nomRow, 60));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 33)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 34)));
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
			modifySpreadRow(kmkCode, hkmCode, INSERT_KBN);

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
		// �ȖڃR�[�h�̐ݒ�
		addSendValues("kmkCode", (String) map.get("kmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		addSendValues("hkmCode", (String) map.get("hkmCode"));
		// �⏕�Ȗږ��̂̐ݒ�
		addSendValues("hkmName", (String) map.get("hkmName"));
		// �⏕�Ȗڗ��̂̐ݒ�
		addSendValues("hkmName_S", (String) map.get("hkmName_S"));
		// �⏕�Ȗڌ������̂̐ݒ�
		addSendValues("hkmName_K", (String) map.get("hkmName_K"));
		// ����敪�̐ݒ�
		addSendValues("ukmKbn", (String) map.get("ukmKbn"));
		// ����ŃR�[�h�̐ݒ�
		addSendValues("zeiCode", (String) map.get("zeiCode"));
		// �������̓t���O�̐ݒ�
		addSendValues("triCodeFlg", (String) map.get("triCodeFlg"));
		// �����`�[�����׸ނ̐ݒ�
		addSendValues("glFlg1", (String) map.get("glFlg1"));
		// �o���`�[�����׸ނ̐ݒ�
		addSendValues("glFlg2", (String) map.get("glFlg2"));
		// �U�֓`�[�����׸ނ̐ݒ�
		addSendValues("glFlg3", (String) map.get("glFlg3"));
		// �o��Z�`�[�����׸ނ̐ݒ�
		addSendValues("apFlg1", (String) map.get("apFlg1"));
		// �������`�[�����׸ނ̐ݒ�
		addSendValues("apFlg2", (String) map.get("apFlg2"));
		// ���v��`�[�����׸ނ̐ݒ�
		addSendValues("arFlg1", (String) map.get("arFlg1"));
		// �������`�[�����׸ނ̐ݒ�
		addSendValues("arFlg2", (String) map.get("arFlg2"));
		// ���Y�v��`�[�����׸ނ̐ݒ�
		addSendValues("faFlg1", (String) map.get("faFlg1"));
		// �x���˗��`�[�����׸ނ̐ݒ�
		addSendValues("faFlg2", (String) map.get("faFlg2"));
		// �������̓t���O�̐ݒ�
		addSendValues("triCodeFlg", (String) map.get("triCodeFlg"));
		// �x���˗��`�[�����׸ނ̐ݒ�
		addSendValues("hasFlg", (String) map.get("hasFlg"));
		// �Ј����̓t���O�̐ݒ�
		addSendValues("empCodeFlg", (String) map.get("empCodeFlg"));
		// �Ǘ�����1-6�t���O�̐ݒ�
		addSendValues("knrFlg1", (String) map.get("knrFlg1"));
		addSendValues("knrFlg2", (String) map.get("knrFlg2"));
		addSendValues("knrFlg3", (String) map.get("knrFlg3"));
		addSendValues("knrFlg4", (String) map.get("knrFlg4"));
		addSendValues("knrFlg5", (String) map.get("knrFlg5"));
		addSendValues("knrFlg6", (String) map.get("knrFlg6"));
		// ���v����1-3�t���O�̐ݒ�
		addSendValues("hmFlg1", (String) map.get("hmFlg1"));
		addSendValues("hmFlg2", (String) map.get("hmFlg2"));
		addSendValues("hmFlg3", (String) map.get("hmFlg3"));
		// ����ېœ��̓t���O�̐ݒ�
		addSendValues("uriZeiFlg", (String) map.get("uriZeiFlg"));
		// �d���ېœ��̓t���O�̐ݒ�
		addSendValues("sirZeiFlg", (String) map.get("sirZeiFlg"));
		// ���ʉݓ��̓t���O�̐ݒ�
		addSendValues("mcrFlg", (String) map.get("mcrFlg"));
		// �]���֑Ώۃt���O�̐ݒ�
		addSendValues("excFlg", (String) map.get("excFlg"));
		// �J�n�N�����̐ݒ�
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// �I���N�����̐ݒ�
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// �v���O�����h�c�̐ݒ�
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
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kmkCode, String hkmCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kmkCode, hkmCode, updKbn);
		TTable ssPanelSpread = panel.ssSubItem;
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
		lockBtn(panel.ssSubItem.getDataSource().getNumRows() != 0);
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
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String kmkCode, String hkmCode, int updKbn) throws IOException,
		TRequestException {
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �ȖڃR�[�h�̐ݒ�
		addSendValues("kmkCode", Util.avoidNull(kmkCode));
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginHkmCode", Util.avoidNull(hkmCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endHkmCode", Util.avoidNull(hkmCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssSubItem.getDataSource();
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

			String sinai = this.getWord("C01279");
			String possible = this.getWord("C01276");
			String value, text;
			// ����敪
			colum.add(35, "");
			value = colum.get(6);
			text = ("0".equals(value) ? this.getWord("C02155") : this.getWord("C02156"));
			colum.set(35, value);
			colum.set(6, text);

			// �����`�[�����׸�
			colum.add(36, "");
			value = colum.get(8);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(36, value);
			colum.set(8, text);

			// �o���`�[�����׸�
			colum.add(37, "");
			value = colum.get(9);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(37, value);
			colum.set(9, text);

			// �U�֓`�[�����׸�
			colum.add(38, "");
			value = colum.get(10);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(38, value);
			colum.set(10, text);

			// �o��Z�`�[�����׸�
			colum.add(39, "");
			value = colum.get(11);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(39, value);
			colum.set(11, text);

			// �������`�[�����׸�
			colum.add(40, "");
			value = colum.get(12);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(40, value);
			colum.set(12, text);

			// ���v��`�[�����׸�
			colum.add(41, "");
			value = colum.get(13);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(41, value);
			colum.set(13, text);

			// �������`�[�����׸�
			colum.add(42, "");
			value = colum.get(14);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(42, value);
			colum.set(14, text);

			// ���Y�v��`�[�����׸�
			colum.add(43, "");
			value = colum.get(15);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(43, value);
			colum.set(15, text);

			// �x���˗��`�[�����׸�
			colum.add(44, "");
			value = colum.get(16);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(44, value);
			colum.set(16, text);

			// �������̓t���O
			colum.add(45, "");
			value = colum.get(17);
			text = triCodeFlgMap.get(value).toString();
			colum.set(45, value);
			colum.set(17, text);

			// �����������׸�
			colum.add(46, "");
			value = colum.get(18);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(46, value);
			colum.set(18, text);

			// �Ј����̓t���O
			colum.add(47, "");
			value = colum.get(19);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(47, value);
			colum.set(19, text);

			// �Ǘ��P���̓t���O
			colum.add(48, "");
			value = colum.get(20);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(48, value);
			colum.set(20, text);

			// �Ǘ�2���̓t���O
			colum.add(49, "");
			value = colum.get(21);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(49, value);
			colum.set(21, text);

			// �Ǘ�3���̓t���O
			colum.add(50, "");
			value = colum.get(22);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(50, value);
			colum.set(22, text);

			// �Ǘ�4���̓t���O
			colum.add(51, "");
			value = colum.get(23);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(51, value);
			colum.set(23, text);

			// �Ǘ�5���̓t���O
			colum.add(52, "");
			value = colum.get(24);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(52, value);
			colum.set(24, text);

			// �Ǘ�6���̓t���O
			colum.add(53, "");
			value = colum.get(25);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(53, value);
			colum.set(25, text);

			// ���v1���̓t���O
			colum.add(54, "");
			value = colum.get(26);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(54, value);
			colum.set(26, text);

			// ���v2���̓t���O
			colum.add(55, "");
			value = colum.get(27);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(55, value);
			colum.set(27, text);

			// ���v3���̓t���O
			colum.add(56, "");
			value = colum.get(28);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(56, value);
			colum.set(28, text);

			// ����ېœ��̓t���O
			colum.add(57, "");
			value = colum.get(29);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(57, value);
			colum.set(29, text);

			// �d���ېœ��̓t���O
			colum.add(58, "");
			value = colum.get(30);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(58, value);
			colum.set(30, text);

			// ���ʉݓ��̓t���O
			colum.add(59, "");
			value = colum.get(31);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(59, value);
			colum.set(31, text);

			// �]���֑Ώۃt���O
			colum.add(60, "");
			value = colum.get(32);
			text = ("0".equals(value) ? this.getWord("C02099") : this.getWord("C02100"));
			colum.set(60, value);
			colum.set(32, text);

			try {
				colum.set(33, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(33))));
				colum.set(34, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(34))));
			} catch (ParseException e) {
				ClientLogger.error(this.getClass().getName() + ":" + colum.get(9), e);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssSubItem.getCurrentRow(), colum);
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
				int nomRow = panel.ssSubItem.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssSubItem.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// �ȖڃR�[�h�̎擾
				String kmkCode = (String) model.getTableDataItem(nomRow, 1);
				// �⏕�ȖڃR�[�h�̎擾
				String hkmCode = (String) model.getTableDataItem(nomRow, 2);
				// ������ʂ̐ݒ�
				addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				addSendValues("kaiCode", kaiCode);
				// �ȖڃR�[�h�̐ݒ�
				addSendValues("kmkCode", kmkCode);
				// �⏕�ȖڃR�[�h�̐ݒ�
				addSendValues("hkmCode", hkmCode);

				// �T�[�o���̃G���[�ꍇ
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
		int row = panel.ssSubItem.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssSubItem.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssSubItem.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssSubItem.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssSubItem.getVertSB().setValue(0);
			panel.ssSubItem.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssSubItem.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	protected void selectSpreadRow(int row) {
		panel.ssSubItem.setRowSelection(row, row);
		panel.ssSubItem.setCurrentCell(row, 0);
	}

	/**
	 * ��������
	 * 
	 * @return ��������
	 */

	boolean find() {
		String beginSubItem = panel.ctrlBeginSubItem.getValue().toString();
		String endSubItem = panel.ctrlEndSubItem.getValue().toString();
		try {
			if (Util.isSmallerThen(beginSubItem, endSubItem) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginSubItem.getField().requestFocus();
				showMessage(panel, "W00036", "C00487");
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
	 * �\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 * @throws IOException
	 */

	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �ȖڃR�[�h�̎擾
		String kmkCode = panel.ctrlItem.getField().getText();
		// �J�n�R�[�h�̎擾
		String beginHkmCode = panel.ctrlBeginSubItem.getField().getText();
		// �I���R�[�h�̎擾
		String endHkmCode = panel.ctrlEndSubItem.getField().getText();
		// ������ʂ̐ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �ȖڃR�[�h�̐ݒ�
		addSendValues("kmkCode", Util.avoidNull(kmkCode));
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginHkmCode", Util.avoidNull(beginHkmCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endHkmCode", Util.avoidNull(endHkmCode));

		// �T�[�o���̃G���[�ꍇ
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
			panel.ctrlBeginSubItem.getField().requestFocus();
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

			String sinai = this.getWord("C01279");
			String possible = this.getWord("C01276");
			String value, text;
			// ����敪
			colum.add(35, "");
			value = colum.get(6);
			text = ("0".equals(value) ? this.getWord("C02155") : this.getWord("C02156"));
			colum.set(35, value);
			colum.set(6, text);

			// �����`�[�����׸�
			colum.add(36, "");
			value = colum.get(8);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(36, value);
			colum.set(8, text);

			// �o���`�[�����׸�
			colum.add(37, "");
			value = colum.get(9);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(37, value);
			colum.set(9, text);

			// �U�֓`�[�����׸�
			colum.add(38, "");
			value = colum.get(10);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(38, value);
			colum.set(10, text);

			// �o��Z�`�[�����׸�
			colum.add(39, "");
			value = colum.get(11);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(39, value);
			colum.set(11, text);

			// �������`�[�����׸�
			colum.add(40, "");
			value = colum.get(12);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(40, value);
			colum.set(12, text);

			// ���v��`�[�����׸�
			colum.add(41, "");
			value = colum.get(13);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(41, value);
			colum.set(13, text);

			// �������`�[�����׸�
			colum.add(42, "");
			value = colum.get(14);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(42, value);
			colum.set(14, text);

			// ���Y�v��`�[�����׸�
			colum.add(43, "");
			value = colum.get(15);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(43, value);
			colum.set(15, text);

			// �x���˗��`�[�����׸�
			colum.add(44, "");
			value = colum.get(16);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(44, value);
			colum.set(16, text);

			// �������̓t���O
			colum.add(45, "");
			value = colum.get(17);
			text = triCodeFlgMap.get(value).toString();
			colum.set(45, value);
			colum.set(17, text);

			// �����������׸�
			colum.add(46, "");
			value = colum.get(18);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(46, value);
			colum.set(18, text);

			// �Ј����̓t���O
			colum.add(47, "");
			value = colum.get(19);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(47, value);
			colum.set(19, text);

			// �Ǘ��P���̓t���O
			colum.add(48, "");
			value = colum.get(20);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(48, value);
			colum.set(20, text);

			// �Ǘ�2���̓t���O
			colum.add(49, "");
			value = colum.get(21);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(49, value);
			colum.set(21, text);

			// �Ǘ�3���̓t���O
			colum.add(50, "");
			value = colum.get(22);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(50, value);
			colum.set(22, text);

			// �Ǘ�4���̓t���O
			colum.add(51, "");
			value = colum.get(23);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(51, value);
			colum.set(23, text);

			// �Ǘ�5���̓t���O
			colum.add(52, "");
			value = colum.get(24);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(52, value);
			colum.set(24, text);

			// �Ǘ�6���̓t���O
			colum.add(53, "");
			value = colum.get(25);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(53, value);
			colum.set(25, text);

			// ���v1���̓t���O
			colum.add(54, "");
			value = colum.get(26);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(54, value);
			colum.set(26, text);

			// ���v2���̓t���O
			colum.add(55, "");
			value = colum.get(27);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(55, value);
			colum.set(27, text);

			// ���v3���̓t���O
			colum.add(56, "");
			value = colum.get(28);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(56, value);
			colum.set(28, text);

			// ����ېœ��̓t���O
			colum.add(57, "");
			value = colum.get(29);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(57, value);
			colum.set(29, text);

			// �d���ېœ��̓t���O
			colum.add(58, "");
			value = colum.get(30);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(58, value);
			colum.set(30, text);

			// ���ʉݓ��̓t���O
			colum.add(59, "");
			value = colum.get(31);
			text = ("0".equals(value) ? sinai : possible);
			colum.set(59, value);
			colum.set(31, text);

			// �]���֑Ώۃt���O
			colum.add(60, "");
			value = colum.get(32);
			text = ("0".equals(value) ? this.getWord("C02099") : this.getWord("C02100"));
			colum.set(60, value);
			colum.set(32, text);

			try {
				colum.set(33, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(33))));
				colum.set(34, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(34))));
			} catch (ParseException e) {
				ClientLogger.error(this.getClass().getName() + ":" + colum.get(9), e);
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

			panel.ctrlBeginSubItem.getField().requestFocus();
			// �m�F�_�C�A���O�\��
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			// ���M����p�����[�^��ݒ�
			conds.put("flag", "report");
			// ��ЃR�[�h�̎擾
			conds.put("kaiCode", getLoginUserCompanyCode());
			// �ȖڃR�[�h�̎擾
			conds.put("kmkCode", panel.ctrlItem.getField().getText());
			// �J�n�R�[�h�̎擾
			conds.put("beginHkmCode", panel.ctrlBeginSubItem.getField().getText());
			// �I���R�[�h�̎擾
			conds.put("endHkmCode", panel.ctrlEndSubItem.getField().getText());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
