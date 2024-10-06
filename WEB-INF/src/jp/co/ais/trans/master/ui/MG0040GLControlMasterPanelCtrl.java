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
 * GL�R���g���[���}�X�^��ʃR���g���[��
 * 
 * @author ISFnet China
 */

public class MG0040GLControlMasterPanelCtrl extends TPanelCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0040";

	/** �V�K�ƕ��ʋ敪 */
	protected static int INSERT_KBN = 0;

	/** �ҏW�敪 */
	protected static int UPD_KBN = 1;

	/**
	 * �ڑ���Servlet����
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "MG0040GLControlMasterServlet";
	}

	/** �p�l�� */
	protected MG0040GLControlMasterPanel panel;

	/**
	 * �R���X�g���N�^.
	 */

	public MG0040GLControlMasterPanelCtrl() {
		try {
			// �ꗗ��ʂ̏�����
			panel = new MG0040GLControlMasterPanel(this);
			/**
			 * ��������
			 */
			this.reflesh();
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * �p�l���擾
	 * 
	 * @return GL�R���g���[���}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return this.panel;
	}

	/**
	 * �ҏW����
	 */
	void update() {
		try {
			// �ҏW��ʂ̏�����
			MG0040EditDisplayDialogCtrl dialog = new MG0040EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			// ���O�s���擾����
			int nomRow = panel.ssGLCodeRoleList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssGLCodeRoleList.getDataSource();
			// map�̏�����
			Map<String, Object> map = new TreeMap<String, Object>();
			// ��ЃR�[�h�̐ݒ�
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			// ���Z�i�K�敪
			map.put("ksdKbn", model.getTableDataItem(nomRow, 12));
			// ���Z�`�[���͋敪
			map.put("ksnNyuKbn", model.getTableDataItem(nomRow, 9));
			// �������ʎc���\���敪
			map.put("mtZanHyjKbn", model.getTableDataItem(nomRow, 10));
			// �]���փ��[�g�敪
			map.put("excRateKbn", model.getTableDataItem(nomRow, 11));
			// ������ʂ̐ݒ�
			map.put("flag", "save");
			// �f�[�^����ʂɐݒ肷��
			dialog.setValues(map);
			// �ҏW��ʂ�\������
			dialog.show();
			// �ҏW��ʂ��J���Ă��܂���
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
	 * �f�[�^����
	 */
	void enter() {
		try {
			// ���O�s���擾����
			int nomRow = panel.ssGLCodeRoleList.getCurrentRow();
			// �f�[�^�W���擾����
			TableDataModel model = panel.ssGLCodeRoleList.getDataSource();

			// �f�[�^�`�F�b�N
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				lockBtn(false);
			} else {
				unlockBtn();
			}
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			ClientLogger.error(this.getClass().getName(), e);
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
		super.addSendValues("flag", "save");
		// ��ЃR�[�h�̐ݒ�
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		// ���Z�i�K�敪
		super.addSendValues("ksdKbn", (String) map.get("ksdKbn"));
		// ���Z�`�[���͋敪
		super.addSendValues("ksnNyuKbn", (String) map.get("ksnNyuKbn"));
		// �������ʎc���\���敪
		super.addSendValues("mtZanHyjKbn", (String) map.get("mtZanHyjKbn"));
		// �]���փ��[�g�敪
		super.addSendValues("excRateKbn", (String) map.get("excRateKbn"));
		// �v���O�����h�c�̐ݒ�
		super.addSendValues("prgID", PROGRAM_ID);

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
				int nomRow = panel.ssGLCodeRoleList.getCurrentRow();
				// �f�[�^�W���擾����
				TableDataModel model = panel.ssGLCodeRoleList.getDataSource();
				// ��ЃR�[�h�̎擾
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// ������ʂ̐ݒ�
				super.addSendValues("flag", "delete");
				// ��ЃR�[�h�̐ݒ�
				super.addSendValues("kaiCode", kaiCode);

				if (!request(getServletName())) {
					// �T�[�o���̃G���[�ꍇ
					errorHandler(panel);
				}

				// �X�v���b�h�X�V
				modifySpreadRow(kaiCode, UPD_KBN);

			} catch (Exception e) {
				// ����ɏ�������܂���ł���
				errorHandler(panel.getParentFrame(), e, "E00009");
			}
		}
	}

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	boolean find() {

		try {
			// �{�^�����b�N����
			panel.ssGLCodeRoleList.getDataSource();
			return reflesh();
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			lockBtn(false);
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
		panel.btnListOutput.setEnabled(boo);
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
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {

		boolean dataExists = true;

		// ������ʂ̐ݒ�
		addSendValues("flag", "find");

		if (!request(getServletName())) {
			// �T�[�o���̃G���[�ꍇ
			errorHandler(panel);
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();
		// ���ʂ��擾����
		Iterator recordIte = getResultList().iterator();
		// �l�����邩�ǂ���
		if (!recordIte.hasNext()) {
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

			String value;
			String text = "";
			int i = Integer.parseInt(colum.get(2));

			// ���Z�`�[���͋敪
			if (i == 0) {
				text = this.getWord("C00009");
			} else if (i == 1) {
				text = this.getWord("C00435");
			} else if (i == 2) {
				text = this.getWord("C00239");
			} else if (i == 3) {
				text = this.getWord("C00147");
			}
			colum.add(9, Integer.toString(i));
			colum.set(2, text);

			// 0:(���ʎc����)�\�����Ȃ� 1:(���ʎc����)�\������
			value = colum.get(3);
			text = ("0".equals(value) ? this.getWord("C02369") : this.getWord("C02370"));
			colum.add(10, value);
			colum.set(3, text);

			// 0:�������[�g 1:�����������[�g
			value = colum.get(4);
			text = ("0".equals(value) ? this.getWord("C00148") : this.getWord("C00535"));
			colum.add(11, value);
			colum.set(4, text);

			// ���Z�i�K�敪
			int col1 = Integer.parseInt(colum.get(1));
			if (col1 == 0) {
				colum.set(1, this.getWord("C00038"));
			} else if (col1 > 0) {
				colum.set(1, col1 + this.getWord("C00373"));
			} else {
				colum.set(1, String.valueOf(col1));
			}
			colum.add(12, String.valueOf(col1));

			cells.add(row, colum);
		}

		panel.setDataList(cells);

		// �f�[�^�W���擾����
		TableDataModel model = panel.ssGLCodeRoleList.getDataSource();

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

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	boolean checkCode(String kaiCode) {
		try {
			// ��ЃR�[�h������
			if (Util.isNullOrEmpty(kaiCode)) {
				return false;
			}
			// ������ʂ̐ݒ�
			addSendValues("flag", "checkcode");
			addSendValues("kaiCode", kaiCode);

			if (!request(getServletName())) {
				// �T�[�o���̃G���[�ꍇ
				errorHandler(panel);
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(panel);
			return false;
		}
	}

	/**
	 * �V�K�A�ҏW�A�폜�̏ꍇ�A�X�v���b�h�X�V
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param updKbn �X�V�敪
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kaiCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kaiCode, updKbn);
		TTable ssPanelSpread = panel.ssGLCodeRoleList;
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
		panel.btnDelete.setEnabled(checkCode(kaiCode));
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

		// �T�[�u���b�g�̐ڑ���
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		// ���ʂ��擾����
		List<List<String>> resultList = getResultList();

		// �l�����邩�ǂ���
		if (resultList == null || resultList.isEmpty()) {
			showMessage(panel, "W00100");
		}

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssGLCodeRoleList.getDataSource();
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = modifyDs.getCells();
		// colum�̏�����
		Vector<String> colum = new Vector<String>();
		for (List<String> list : resultList) {

			for (String value : list) {
				// ���ʂ�ǉ�����
				colum.add(value);
			}

			String value;
			String text = "";
			int i = Integer.parseInt(colum.get(2));

			// ���Z�`�[���͋敪
			if (i == 0) {
				text = this.getWord("C00009");
			} else if (i == 1) {
				text = this.getWord("C00435");
			} else if (i == 2) {
				text = this.getWord("C00239");
			} else if (i == 3) {
				text = this.getWord("C00147");
			}
			colum.add(9, Integer.toString(i));
			colum.set(2, text);

			// 0:(���ʎc����)�\�����Ȃ� 1:(���ʎc����)�\������
			value = colum.get(3);
			text = ("0".equals(value) ? this.getWord("C02369") : this.getWord("C02370"));
			colum.add(10, value);
			colum.set(3, text);

			// 0:�������[�g 1:�����������[�g
			value = colum.get(4);
			text = ("0".equals(value) ? this.getWord("C00148") : this.getWord("C00535"));
			colum.add(11, value);
			colum.set(4, text);

			// ���Z�i�K�敪
			int col1 = Integer.parseInt(colum.get(1));
			if (col1 == 0) {
				colum.set(1, this.getWord("C00038"));
			} else if (col1 > 0) {
				colum.set(1, col1 + this.getWord("C00373"));
			} else {
				colum.set(1, String.valueOf(col1));
			}
			colum.add(12, String.valueOf(col1));
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssGLCodeRoleList.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * �}�X�^�I���s��Ԃ̕ێ�
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssGLCodeRoleList.setRowSelection(row, row);
		panel.ssGLCodeRoleList.setCurrentCell(row, 0);
	}

}
