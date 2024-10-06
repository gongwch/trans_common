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
 * �����}�X�^��ʃR���g���[��
 * 
 * @author mayongliang
 */

public class MG0150CustomerMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0150";

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
		return "MG0150CustomerMasterServlet";
	}

	/** �p�l�� */
	protected MG0150CustomerMasterPanel panel;

	private REFDialogCtrl refBeginCustomer;

	private REFDialogCtrl refEndCustomer;

	/**
	 * �R���X�g���N�^.
	 */
	public MG0150CustomerMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0150CustomerMasterPanel(this);
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
		refBeginCustomer.setTargetServlet(getServletName());
		refBeginCustomer.setTitleID("C02326");
		refBeginCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refBeginCustomer.setShowsMsgOnError(false);
		refBeginCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��s�R�[�h�̐ݒ�
				keys.put("kind", "Customer");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCustomer.getField().getText());
				// �f�[�^��Ԃ�
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
		refEndCustomer.setTargetServlet(getServletName());
		refEndCustomer.setTitleID("C02326");
		refEndCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refEndCustomer.setShowsMsgOnError(false);
		refEndCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��s�R�[�h�̐ݒ�
				keys.put("kind", "Customer");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCustomer.getField().getText());
				// �f�[�^��Ԃ�
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
		return this.panel;
	}

	/**
	 * �V�K�o�^����
	 */
	void insert() {
		try {
			// �ҏW��ʂ̏�����
			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			// �ҏW��ʂ̕\��
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), "insert");
			// �I���s�ێ�����
			modifySpreadRow(dialog.getDataList().get("triCode").toString(), INSERT_KBN);
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
			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			// ���O�s���擾����
			int nomRow = panel.ssCustomer.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssCustomer.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �����R�[�h�̐ݒ�
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// ����於�̂̐ݒ�
			map.put("triName", model.getTableDataItem(nomRow, 2));
			// ����旪�̂̐ݒ�
			map.put("triName_S", model.getTableDataItem(nomRow, 3));
			// ����挟�����̂̐ݒ�
			map.put("triName_K", model.getTableDataItem(nomRow, 4));
			// �X�֔ԍ��̐ݒ�
			map.put("zip", model.getTableDataItem(nomRow, 6));
			// �Z���J�i�̐ݒ�
			map.put("jyuKana", model.getTableDataItem(nomRow, 7));
			// �Z���P�̐ݒ�
			map.put("jyu1", model.getTableDataItem(nomRow, 8));
			// �Z���Q�̐ݒ�
			map.put("jyu2", model.getTableDataItem(nomRow, 9));
			// �d�b�ԍ��̐ݒ�
			map.put("tel", model.getTableDataItem(nomRow, 10));
			// FAX�ԍ��̐ݒ�
			map.put("fax", model.getTableDataItem(nomRow, 11));
			// �W�v�����R�[�h�̐ݒ�
			map.put("sumCode", model.getTableDataItem(nomRow, 12));
			// �d����敪�̐ݒ�
			map.put("siireKbn", model.getTableDataItem(nomRow, 25));
			// ���Ӑ�敪�̐ݒ�
			map.put("tokuiKbn", model.getTableDataItem(nomRow, 26));
			// �����������ߓ��̐ݒ�
			map.put("njCDate", model.getTableDataItem(nomRow, 15));
			// �����������ߌ㌎�̐ݒ�
			map.put("njRMon", model.getTableDataItem(nomRow, 16));
			// ���������������̐ݒ�
			map.put("njPDate", model.getTableDataItem(nomRow, 17));
			// ������s�������ނ̐ݒ�
			map.put("nknCbkCode", model.getTableDataItem(nomRow, 18));
			// ����`�ԋ敪�̐ݒ�
			map.put("triKbn", model.getTableDataItem(nomRow, 27));
			// �X�|�b�g�`�[�ԍ��̐ݒ�
			map.put("spotDenNo", model.getTableDataItem(nomRow, 21));
			// ���Ə����̂̐ݒ�
			map.put("jigName", model.getTableDataItem(nomRow, 5));
			// �U���˗��l���̐ݒ�
			map.put("iraiName", model.getTableDataItem(nomRow, 20));
			// �����萔���敪�̐ݒ�
			map.put("nyuTesuKbn", model.getTableDataItem(nomRow, 28));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 23)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 24)));
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
			modify(dialog.getDataList(), "update");
			// �X�v���b�h�X�V
			modifySpreadRow(dialog.getDataList().get("triCode").toString(), UPD_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * spot����
	 */
	public void spot() {
		try {
			// SPOT����ł��邩�ۂ�
			boolean bol = checkTriKbn();
			// SPOT����łȂ��ꍇ
			if (!bol) {
				// �x�����b�Z�[�W�\��
				showMessage(panel, "W00210");
				return;
			}

			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C02425");
			// ���O�s���擾����
			int nomRow = panel.ssCustomer.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssCustomer.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �����R�[�h�̐ݒ�
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// ����於�̂̐ݒ�
			map.put("triName", model.getTableDataItem(nomRow, 2));
			// ����旪�̂̐ݒ�
			map.put("triName_S", model.getTableDataItem(nomRow, 3));
			// ����挟�����̂̐ݒ�
			map.put("triName_K", model.getTableDataItem(nomRow, 4));
			// �X�֔ԍ��̐ݒ�
			map.put("zip", model.getTableDataItem(nomRow, 6));
			// �Z���J�i�̐ݒ�
			map.put("jyuKana", model.getTableDataItem(nomRow, 7));
			// �Z���P�̐ݒ�
			map.put("jyu1", model.getTableDataItem(nomRow, 8));
			// �Z���Q�̐ݒ�
			map.put("jyu2", model.getTableDataItem(nomRow, 9));
			// �d�b�ԍ��̐ݒ�
			map.put("tel", model.getTableDataItem(nomRow, 10));
			// FAX�ԍ��̐ݒ�
			map.put("fax", model.getTableDataItem(nomRow, 11));
			// �W�v�����R�[�h�̐ݒ�
			map.put("sumCode", model.getTableDataItem(nomRow, 12));
			// �d����敪�̐ݒ�
			map.put("siireKbn", model.getTableDataItem(nomRow, 25));
			// ���Ӑ�敪�̐ݒ�
			map.put("tokuiKbn", model.getTableDataItem(nomRow, 26));
			// �����������ߓ��̐ݒ�
			map.put("njCDate", model.getTableDataItem(nomRow, 15));
			// �����������ߓ��̐ݒ�
			map.put("njRMon", model.getTableDataItem(nomRow, 16));
			// ���������������̐ݒ�
			map.put("njPDate", model.getTableDataItem(nomRow, 17));
			// ������s�������ނ̐ݒ�
			map.put("nknCbkCode", model.getTableDataItem(nomRow, 18));
			// ����`�ԋ敪�̐ݒ�
			map.put("triKbn", "00");
			// �X�|�b�g�`�[�ԍ��̐ݒ�
			map.put("spotDenNo", model.getTableDataItem(nomRow, 21));
			// ���Ə����̂̐ݒ�
			map.put("jigName", model.getTableDataItem(nomRow, 5));
			// �U���˗��l���̐ݒ�
			map.put("iraiName", model.getTableDataItem(nomRow, 20));
			// �����萔���敪�̐ݒ�
			map.put("nyuTesuKbn", model.getTableDataItem(nomRow, 28));
			// �J�n�N�����̐ݒ�
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 23)));
			// �I���N�����̐ݒ�
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 24)));
			// ������ʂ̐ݒ�
			map.put("flag", "spot");

			// ����R�[�h���Z�b�g
			dialog.setTriCode((String) model.getTableDataItem(nomRow, 1));

			// �f�[�^����ʂɐݒ肷���
			dialog.setValues(map);
			// �ҏW��ʂ�\�������
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂����
			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), "spot");
			// �\���f�[�^�̎擾
			this.reflesh();
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * ����`�ԋ敪�`�F�b�N
	 * 
	 * @return true: SPOT�����ł��� false:SPOT�����ł͂Ȃ�
	 * @throws IOException
	 */
	private boolean checkTriKbn() throws IOException {
		// ���O�s���擾����
		int nomRow = panel.ssCustomer.getCurrentRow();
		// �f�[�^�W���擾����
		TableDataModel model = panel.ssCustomer.getDataSource();

		String triCode = (String) model.getTableDataItem(nomRow, 1);

		// ������ʂ̐ݒ�
		addSendValues("flag", "checkTriKbn");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �x���R�[�h�̐ݒ�
		addSendValues("triCode", triCode);

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			errorHandler(panel);
		}

		Map data = super.getResult();

		if ("00".equals(data.get("tRI_KBN"))) {
			return false;
		}

		return true;
	}

	/**
	 * ���ʏ���
	 */
	public void copy() {
		try {
			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			// ���O�s���擾����
			int nomRow = panel.ssCustomer.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssCustomer.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// �����R�[�h�̐ݒ�
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// ����於��
			map.put("triName", model.getTableDataItem(nomRow, 2));
			// ����旪��
			map.put("triName_S", model.getTableDataItem(nomRow, 3));
			// ����挟������
			map.put("triName_K", model.getTableDataItem(nomRow, 4));
			// �X�֔ԍ�
			map.put("zip", model.getTableDataItem(nomRow, 6));
			// �Z���J�i
			map.put("jyuKana", model.getTableDataItem(nomRow, 7));
			// �Z���P
			map.put("jyu1", model.getTableDataItem(nomRow, 8));
			// �Z���Q
			map.put("jyu2", model.getTableDataItem(nomRow, 9));
			// �d�b�ԍ�
			map.put("tel", model.getTableDataItem(nomRow, 10));
			// FAX�ԍ�
			map.put("fax", model.getTableDataItem(nomRow, 11));
			// �W�v�����R�[�h
			map.put("sumCode", model.getTableDataItem(nomRow, 12));
			// �d����敪
			map.put("siireKbn", model.getTableDataItem(nomRow, 25));
			// ���Ӑ�敪
			map.put("tokuiKbn", model.getTableDataItem(nomRow, 26));
			// �����������ߓ�
			map.put("njCDate", model.getTableDataItem(nomRow, 15));
			// �����������ߌ㌎
			map.put("njRMon", model.getTableDataItem(nomRow, 16));
			// ��������������
			map.put("njPDate", model.getTableDataItem(nomRow, 17));
			// ������s��������
			map.put("nknCbkCode", model.getTableDataItem(nomRow, 18));
			// ����`�ԋ敪
			map.put("triKbn", model.getTableDataItem(nomRow, 27));
			// �X�|�b�g�`�[�ԍ�
			map.put("spotDenNo", model.getTableDataItem(nomRow, 21));
			// ���Ə�����
			map.put("jigName", model.getTableDataItem(nomRow, 5));
			// �U���˗��l��
			map.put("iraiName", model.getTableDataItem(nomRow, 20));
			// �����萔���敪
			map.put("nyuTesuKbn", model.getTableDataItem(nomRow, 28));
			// �J�n�N����
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 23)));
			// �I���N����
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 24)));
			// ������ʂ̐ݒ�
			map.put("flag", "insert");
			// �ҏW��ʂ̕\��
			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}
			// �f�[�^���ҏW����
			modify(dialog.getDataList(), "insert");

			modifySpreadRow(dialog.getDataList().get("triCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �ύX����
	 * 
	 * @param map �f�[�^
	 * @param flag �V�K(true) or �X�V(false)
	 * @throws IOException
	 */
	protected void modify(Map map, String flag) throws IOException {
		// ������ʂ̐ݒ�
		addSendValues("flag", flag);
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �����R�[�h�̐ݒ�
		addSendValues("triCode", (String) map.get("triCode"));
		// �ߋ��̎����R�[�h�̐ݒ�
		addSendValues("oldTriCode", (String) map.get("oldTriCode"));
		// ����於�̂̐ݒ�
		addSendValues("triName", (String) map.get("triName"));
		// ����旪�̂̐ݒ�
		addSendValues("triName_S", (String) map.get("triName_S"));
		// ����挟�����̂̐ݒ�
		addSendValues("triName_K", (String) map.get("triName_K"));
		// �X�֔ԍ��̐ݒ�
		addSendValues("zip", (String) map.get("zip"));
		// �Z���J�i�̐ݒ�
		addSendValues("jyuKana", (String) map.get("jyuKana"));
		// �Z���P�̐ݒ�
		addSendValues("jyu1", (String) map.get("jyu1"));
		// �Z���Q�̐ݒ�
		addSendValues("jyu2", (String) map.get("jyu2"));
		// �d�b�ԍ��̐ݒ�
		addSendValues("tel", (String) map.get("tel"));
		// FAX�ԍ��̐ݒ�
		addSendValues("fax", (String) map.get("fax"));
		// �W�v�����R�[�h�̐ݒ�
		addSendValues("sumCode", (String) map.get("sumCode"));
		// �d����敪�̐ݒ�
		addSendValues("siireKbn", (String) map.get("siireKbn"));
		// ���Ӑ�敪�̐ݒ�
		addSendValues("tokuiKbn", (String) map.get("tokuiKbn"));
		// �����������ߓ��̐ݒ�
		addSendValues("njCDate", (String) map.get("njCDate"));
		// �����������ߌ㌎�̐ݒ�
		addSendValues("njRMon", (String) map.get("njRMon"));
		// ���������������̐ݒ�
		addSendValues("njPDate", (String) map.get("njPDate"));
		// ������s�������ނ̐ݒ�
		addSendValues("nknCbkCode", (String) map.get("nknCbkCode"));
		// ����`�ԋ敪�̐ݒ�
		addSendValues("triKbn", (String) map.get("triKbn"));
		// �X�|�b�g�`�[�ԍ��̐ݒ�
		addSendValues("spotDenNo", (String) map.get("spotDenNo"));
		// ���Ə����̂̐ݒ�
		addSendValues("jigName", (String) map.get("jigName"));
		// �U���˗��l���̐ݒ�
		addSendValues("iraiName", (String) map.get("iraiName"));
		// �����萔���敪�̐ݒ�
		addSendValues("nyuTesuKbn", (String) map.get("nyuTesuKbn"));
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
	 * @param triCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	void modifySpreadRow(String triCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(triCode, updKbn);
		TTable ssPanelSpread = panel.ssCustomer;
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
		lockBtn(panel.ssCustomer.getDataSource().getNumRows() != 0);
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
	 * @param updKbn
	 * @return �X�V�̃Z�[��
	 * @throws IOException
	 * @throws TRequestException
	 */
	Vector<Vector> setModifyCell(String triCode, int updKbn) throws IOException, TRequestException {
		String text;
		String value;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginTriCode", Util.avoidNull(triCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endTriCode", Util.avoidNull(triCode));

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssCustomer.getDataSource();
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

			for (int column = 0; dataIte.hasNext(); column++) {
				// ���ʏW��ǉ�����
				colum.add(column, (String) dataIte.next());
			}

			// ���Ə�����
			String jigName = colum.get(20);
			// �U���˗��l��
			String iraiName = colum.get(21);
			colum.set(20, iraiName);
			// �X�|�b�g�`�[�ԍ�
			String spotDenNo = colum.get(19);
			colum.set(21, spotDenNo);
			// ����`�ԋ敪
			String triKbn = colum.get(18);
			// ������s��������
			String nknCbkCode = colum.get(17);
			colum.set(18, nknCbkCode);
			// ��������������
			String njPDate = colum.get(16);
			colum.set(17, njPDate);
			// �����������ߌ㌎
			String njRMon = colum.get(15);
			colum.set(16, njRMon);
			// �����������ߓ�
			String njCDate = colum.get(14);
			colum.set(15, njCDate);
			// ���Ӑ�敪
			String tokuiKbn = colum.get(13);
			// �d����敪
			String siireKbn = colum.get(12);
			// �W�v�����R�[�h
			String sumCode = colum.get(11);
			colum.set(12, sumCode);
			// FAX�ԍ�
			String fax = colum.get(10);
			colum.set(11, fax);
			// �Z���Q
			String jyu2 = colum.get(8);
			// �Z���P
			String jyu1 = colum.get(7);
			colum.set(8, jyu1);
			// �Z���J�i
			String jyuKana = colum.get(6);
			colum.set(7, jyuKana);
			// �X�֔ԍ�
			String zip = colum.get(5);
			colum.set(6, zip);
			colum.set(5, jigName);
			// �d�b�ԍ�
			String tel = colum.get(9);
			colum.set(10, tel);
			colum.set(9, jyu2);

			// �d����敪
			value = siireKbn;
			text = ("0".equals(value) ? this.getWord("C01295") : this.getWord("C00203"));
			colum.add(25, value);
			colum.set(13, text);

			// ���Ӑ�敪
			value = tokuiKbn;
			text = ("0".equals(value) ? this.getWord("C01296") : this.getWord("C00401"));
			colum.add(26, value);
			colum.set(14, text);

			// ����`�ԋ敪
			value = triKbn;
			text = ("00".equals(value) ? this.getWord("C00372") : this.getWord("C00308"));
			colum.add(27, value);
			colum.set(19, text);

			// �����萔��
			value = colum.get(22);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(28, value);
			colum.set(22, text);

			try {
				colum.set(23, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(23))));
				colum.set(24, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(24))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(23), ex);
			}
		}
		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssCustomer.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * �폜����
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007")) {
			try {
				// �I������Ă���s��1�ڂ�2�ڂ̃J�������擾
				int nomRow = panel.ssCustomer.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssCustomer.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// ��ЃR�[�h�̎擾
				String triCode = (String) model.getTableDataItem(nomRow, 1);
				// �����R�[�h�̎擾
				addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				addSendValues("kaiCode", kaiCode);
				// �����R�[�h�̐ݒ�
				addSendValues("triCode", triCode);

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
	void deleteSpreadRow() {
		int row = panel.ssCustomer.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssCustomer.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssCustomer.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssCustomer.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// �ێ����Ă������l���X�N���[���o�[�ɃZ�b�g
			panel.ssCustomer.getVertSB().setValue(0);
			panel.ssCustomer.getHorizSB().setValue(0);
		}
		// �{�^�����b�N����
		lockBtn(panel.ssCustomer.getDataSource().getNumRows() != 0);
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {

		panel.ssCustomer.setRowSelection(row, row);
		panel.ssCustomer.setCurrentCell(row, 0);

	}

	/**
	 * ��������
	 * 
	 * @return true:����
	 */
	boolean find() {
		try {
			String beginCustomer = panel.ctrlBeginCustomer.getValue().toString();
			String endCustomer = panel.ctrlEndCustomer.getValue().toString();

			if (Util.isSmallerThen(beginCustomer, endCustomer) == false) {
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
	 * @return true:����
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		String text;
		String value;
		// ��ЃR�[�h�̎擾
		String kaiCode = getLoginUserCompanyCode();
		// �J�n�R�[�h�̎擾
		String beginTriCode = panel.ctrlBeginCustomer.getValue();
		// �I���R�[�h�̎擾
		String endTriCode = panel.ctrlEndCustomer.getValue();

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "find");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", kaiCode);
		// �J�n�R�[�h�̐ݒ�
		addSendValues("beginTriCode", Util.avoidNull(beginTriCode));
		// �I���R�[�h�̐ݒ�
		addSendValues("endTriCode", Util.avoidNull(endTriCode));

		// ���M
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();

		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
			panel.ctrlBeginCustomer.getField().requestFocus();
			showMessage(panel, "W00100");
			dataExists = false;
		}

		for (int row = 0; recordIte.hasNext(); row++) {
			// ���ʂ̓��e���擾����
			Iterator dataIte = ((List) recordIte.next()).iterator();

			// ���ʂ�ǉ�����
			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				// ���ʏW��ǉ�����
				colum.add(column, (String) dataIte.next());
			}

			// ���Ə�����
			String jigName = colum.get(20);
			// �U���˗��l��
			String iraiName = colum.get(21);
			colum.set(20, iraiName);
			// �X�|�b�g�`�[�ԍ�
			String spotDenNo = colum.get(19);
			colum.set(21, spotDenNo);
			// ����`�ԋ敪
			String triKbn = colum.get(18);
			// ������s��������
			String nknCbkCode = colum.get(17);
			colum.set(18, nknCbkCode);
			// ��������������
			String njPDate = colum.get(16);
			colum.set(17, njPDate);
			// �����������ߌ㌎
			String njRMon = colum.get(15);
			colum.set(16, njRMon);
			// �����������ߓ�
			String njCDate = colum.get(14);
			colum.set(15, njCDate);
			// ���Ӑ�敪
			String tokuiKbn = colum.get(13);
			// �d����敪
			String siireKbn = colum.get(12);
			// �W�v�����R�[�h
			String sumCode = colum.get(11);
			colum.set(12, sumCode);
			// FAX�ԍ�
			String fax = colum.get(10);
			colum.set(11, fax);
			// �Z���Q
			String jyu2 = colum.get(8);
			// �Z���P
			String jyu1 = colum.get(7);
			colum.set(8, jyu1);
			// �Z���J�i
			String jyuKana = colum.get(6);
			colum.set(7, jyuKana);
			// �X�֔ԍ�
			String zip = colum.get(5);
			colum.set(6, zip);
			colum.set(5, jigName);
			// �d�b�ԍ�
			String tel = colum.get(9);
			colum.set(10, tel);
			colum.set(9, jyu2);

			// �d����敪
			value = siireKbn;
			text = ("0".equals(value) ? this.getWord("C01295") : this.getWord("C00203"));
			colum.add(25, value);
			colum.set(13, text);

			// ���Ӑ�敪
			value = tokuiKbn;
			text = ("0".equals(value) ? this.getWord("C01296") : this.getWord("C00401"));
			colum.add(26, value);
			colum.set(14, text);

			// ����`�ԋ敪
			value = triKbn;
			text = ("00".equals(value) ? this.getWord("C00372") : this.getWord("C00308"));
			colum.add(27, value);
			colum.set(19, text);

			// �����萔��
			value = colum.get(22);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(28, value);
			colum.set(22, text);

			cells.add(row, colum);
			try {
				colum.set(23, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(23))));
				colum.set(24, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(24))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(23), ex);
			}
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
			String beginCustomer = panel.ctrlBeginCustomer.getValue().toString();
			String endCustomer = panel.ctrlEndCustomer.getValue().toString();

			if (Util.isSmallerThen(beginCustomer, endCustomer) == false) {
				// �x�����b�Z�[�W�\��
				panel.ctrlBeginCustomer.getField().requestFocus();
				showMessage(panel, "W00036", "C00408");
				return;
			}

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
			conds.put("beginTriCode", panel.ctrlBeginCustomer.getValue());
			// �I���R�[�h�̎擾
			conds.put("endTriCode", panel.ctrlEndCustomer.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
