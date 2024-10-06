package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�2�}�X�^�̃R���g���[���[
 * 
 * @author AIS
 */
public class MG0190Management2MasterPanelCtrl extends TController {

	/**
	 * �w�����
	 */
	protected MG0190Management2MasterPanel mainView = null;

	/**
	 * �ҏW���
	 */
	protected MG0190Management2MasterDialog editView = null;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/** ���샂�[�h */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	@Override
	public void start() {

		try {

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

			// �w����ʕ\��
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0190Management2MasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�ҏW]�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);
	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {

		try {

			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.NEW, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.management2Range.getCodeFrom(),
					mainView.management2Range.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.management2Range.getFieldFrom().requestFocus();
				return;
			}

			// �Ǘ��Q�����擾
			Management2SearchCondition condition = getSearchCondition();
			List<Management2> list = getManagement2(condition);

			// ���������ɊY������Ǘ��Q�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �Ǘ��Q�����ꗗ�ɕ\������
			for (Management2 management : list) {
				mainView.tbl.addRow(getRowData(management));
			}
			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̊Ǘ�2���擾����
			Management2 management2 = getSelectedManagement2();

			if (management2 == null) {
				return;
			}

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̊Ǘ������Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, management2);

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̊Ǘ��Q���擾����
			Management2 management2 = getSelectedManagement2();

			if (management2 == null) {
				return;
			}

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̊Ǘ��Q�����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, management2);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �폜�Ώۂ̊Ǘ��Q���擾����
			Management2 management2 = getSelectedManagement2();

			if (management2 == null) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteManagement2(management2);

			// �폜�����Ǘ��Q���ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkMainView() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �f�[�^���o
			Management2SearchCondition condition = getSearchCondition();

			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();

			String fileName = "";
			if (getCompany().getAccountConfig().getManagement2Name() == null) {
				fileName = getShortWord("C01027") + getWord("C00500") + ".xls";
			} else {
				fileName = getCompany().getAccountConfig().getManagement2Name() + getWord("C00500") + ".xls";
			}

			printer.preview(data, fileName);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0190Management2MasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param bean  �Ǘ��Q�B�C���A���ʂ̏ꍇ�͓��Y�Ǘ�1����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Management2 bean) {

		this.mode = mode_;

		// �V�K�̏ꍇ
		if (Mode.NEW == mode_) {

			// �V�K���
			editView.setTitle(getWord("C01698"));
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			return;

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {

			// �ҏW
			if (Mode.MODIFY == mode_) {
				// �ҏW���
				editView.setTitle(getWord("C00977"));
				editView.ctrlManagementCode.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				// ���ʉ��
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlManagementCode.setValue(bean.getCode());
			editView.ctrlManagementName.setValue(bean.getName());
			editView.ctrlManagementNames.setValue(bean.getNames());
			editView.ctrlManagementNamek.setValue(bean.getNamek());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());

		}

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */

	protected void btnSettle_Click() {
		try {

			// ���̓f�[�^�`�F�b�N
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽ�Ǘ�2�����擾
			Management2 management2 = getInputedManagement2();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", management2);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(management2));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", management2);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(management2));

			}

			// �ҏW��ʂ����
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �w����ʂœ��͂��ꂽ�Ǘ�2�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected Management2SearchCondition getSearchCondition() {

		Management2SearchCondition condition = new Management2SearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.management2Range.getCodeFrom());
		condition.setCodeTo(mainView.management2Range.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return Management2Manager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�Ǘ�2��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�Ǘ��Q
	 */
	protected Management2 getInputedManagement2() {

		Management2 management2 = new Management2();
		management2.setCompanyCode(getCompany().getCode());
		management2.setCode(editView.ctrlManagementCode.getValue());
		management2.setName(editView.ctrlManagementName.getValue());
		management2.setNames(editView.ctrlManagementNames.getValue());
		management2.setNamek(editView.ctrlManagementNamek.getValue());
		management2.setDateFrom(editView.dtBeginDate.getValue());
		management2.setDateTo(editView.dtEndDate.getValue());

		return management2;

	}

	/**
	 * �Ǘ��Q�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param management �Ǘ�2���
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�Ǘ��Q���
	 */
	protected String[] getRowData(Management2 management) {
		return new String[] { management.getCompanyCode(), management.getCode(), management.getName(),
				management.getNames(), management.getNamek(), DateUtil.toYMDString(management.getDateFrom()),
				DateUtil.toYMDString(management.getDateTo()) };
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * ���������ɊY������Ǘ�2�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������Ǘ��Q�̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Management2> getManagement2(Management2SearchCondition condition) throws Exception {

		List<Management2> list = (List<Management2>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă���Ǘ�2��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���Ǘ�1
	 * @throws Exception
	 */
	protected Management2 getSelectedManagement2() throws Exception {

		Management2SearchCondition condition = new Management2SearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0190Management2MasterPanel.SC.code));

		List<Management2> list = getManagement2(condition);

		if (list == null || list.isEmpty()) {
			showMessage("I00193");// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			return null;
		}
		return list.get(0);

	}

	/**
	 * �w��̊Ǘ��Q���폜����
	 * 
	 * @param management2 �Ǘ�2
	 * @throws Exception
	 */
	protected void deleteManagement2(Management2 management2) throws Exception {
		request(getModelClass(), "delete", management2);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		String caption = "";
		if (getCompany().getAccountConfig().getManagement2Name() == null) {
			caption = getShortWord("C01025");
		} else {
			caption = getCompany().getAccountConfig().getManagement2Name();
		}

		// �Ǘ��Q�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlManagementCode.getValue())) {
			// �R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", caption + getWord("C00174"));
			editView.ctrlManagementCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���Ǘ��Q�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			Management2SearchCondition condition = new Management2SearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlManagementCode.getValue());

			List<Management2> list = getManagement2(condition);

			if (list != null && !list.isEmpty()) {
				// "�w���" + caption + "�͊��ɑ��݂��܂��B"
				showMessage(editView, "I00148", caption + getWord("C00174"));
				editView.ctrlManagementCode.requestTextFocus();
				return false;
			}
		}

		// �Ǘ��Q���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlManagementName.getValue())) {
			// ���̂���͂��Ă��������B
			showMessage(editView, "I00037", caption + getWord("C00518"));
			editView.ctrlManagementName.requestTextFocus();
			return false;
		}

		// �Ǘ��Q���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlManagementNames.getValue())) {
			// ���̂���͂��Ă��������B
			showMessage(editView, "I00037", caption + getWord("C00548"));
			editView.ctrlManagementNames.requestTextFocus();
			return false;
		}

		// �Ǘ��Q�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlManagementNamek.getValue())) {
			// �������̂���͂��Ă��������B
			showMessage(editView, "I00037", caption + getWord("C00160"));
			editView.ctrlManagementNamek.requestTextFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// �J�n�N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			// �I���N��������͂��Ă��������B
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if ((!Util.isNullOrEmpty(editView.dtBeginDate.getValue()) && !Util.isNullOrEmpty(editView.dtEndDate.getValue()))
				&& !Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}
		return true;

	}

}
