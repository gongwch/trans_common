package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �v���O�����}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0240ProgramMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0240ProgramMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0240ProgramMasterDialog editView = null;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * ���샂�[�h�B
	 * 
	 * @author AIS
	 */
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
		mainView = new MG0240ProgramMasterPanel();
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
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {
		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �v���O���������擾
			ProgramSearchCondition condition = getSearchCondition();
			condition.setLang(super.getUser().getLanguage());

			// �f�[�^���o
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// �v���O�����}�X�^
			printer.preview(data, getWord("C02147") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

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
			if (Util.isSmallerThen(mainView.ctrlProgramRange.getCodeFrom(), mainView.ctrlProgramRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlProgramRange.getFieldFrom().requestFocus();
				return;
			}

			// �v���O���������擾
			ProgramSearchCondition condition = getSearchCondition();
			List<Program> list = getProgram(condition);

			// ���������ɊY������v���O���������݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �v���O���������ꗗ�ɕ\������
			for (Program program : list) {
				mainView.tbl.addRow(getRowData(program));
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

			// �ҏW�Ώۂ̃v���O�������擾����
			Program program = getSelectedProgram();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̃v���O���������Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, program);

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

			// ���ʑΏۂ̃v���O�������擾����
			Program program = getSelectedProgram();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̃v���O���������Z�b�g����
			createEditView();
			initEditView(Mode.COPY, program);

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

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃v���O�������擾����
			Program program = getSelectedProgram();

			// �폜����
			deleteProgram(program);

			// �폜�����v���O�������ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

			// �������b�Z�[�W
			showMessage(mainView.getParentFrame(), "I00013");

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
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0240ProgramMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param program �v���O�����B�C���A���ʂ̏ꍇ�͓��Y�v���O��������ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Program program) {

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
				editView.ctrlProgramCode.setEditable(false);
				editView.ctrlSystem.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				// ���ʉ��
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlSystem.setEntity(program.getSystemClassification());
			editView.ctrlProgramCode.setValue(program.getCode());
			editView.ctrlProgramName.setValue(program.getName());
			editView.ctrlProgramNames.setValue(program.getNames());
			editView.ctrlProgramNamek.setValue(program.getNamek());
			editView.ctrlComment.setValue(program.getComment());
			editView.ctrlModuleName.setValue(program.getLoadClassName());
			editView.dtBeginDate.setValue(program.getTermFrom());
			editView.dtEndDate.setValue(program.getTermTo());

		}

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {
		try {
			// ���̓`�F�b�N���s���B
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽ�v���O���������擾
			Program program = getInputedProgram();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", program);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(program));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

			}
			// �C���̏ꍇ
			else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", program);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(program));
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
	 * �w����ʂœ��͂��ꂽ�v���O�����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setSystemCode(mainView.ctrlSystem.getCode());
		condition.setCodeFrom(mainView.ctrlProgramRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlProgramRange.getCodeTo());
		condition.setExecutable(false);
		condition.setNotExecutable(false);

		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * ���f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getModelClass() {
		return ProgramManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�v���O������Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�v���O����
	 */
	protected Program getInputedProgram() {

		Program program = new Program();
		program.setCompanyCode(TLoginInfo.getCompany().getCode());
		SystemClassification sysBean = new SystemClassification();
		sysBean.setCode(editView.ctrlSystem.getCode());
		sysBean.setName(editView.ctrlSystem.getName());
		program.setSystemClassification(sysBean);
		program.setCode(editView.ctrlProgramCode.getValue());
		program.setName(editView.ctrlProgramName.getValue());
		program.setNames(editView.ctrlProgramNames.getValue());
		program.setNamek(editView.ctrlProgramNamek.getValue());
		program.setComment(editView.ctrlComment.getValue());
		program.setLoadClassName(editView.ctrlModuleName.getValue());
		program.setTermFrom(editView.dtBeginDate.getValue());
		program.setTermTo(editView.dtEndDate.getValue());

		return program;

	}

	/**
	 * �v���O���������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param program �v���O����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�v���O����
	 */
	protected String[] getRowData(Program program) {
		return new String[] { program.getSystemClassification().getCode(), program.getCode(), program.getName(),
				program.getNames(), program.getNamek(), program.getComment(), program.getLoadClassName(),
				DateUtil.toYMDString(program.getTermFrom()), DateUtil.toYMDString(program.getTermTo()) };
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
	 * ���������ɊY������v���O�����̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������v���O�����̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Program> getProgram(ProgramSearchCondition condition) throws Exception {

		List<Program> list = (List<Program>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă���v���O������Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���v���O����
	 * @throws Exception
	 */
	protected Program getSelectedProgram() throws Exception {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0240ProgramMasterPanel.SC.code));

		condition.setSystemCode((String) mainView.tbl.getSelectedRowValueAt(MG0240ProgramMasterPanel.SC.systemCode));

		List<Program> list = getProgram(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �w��̃v���O�������폜����
	 * 
	 * @param program �v���O����
	 * @throws Exception
	 */
	protected void deleteProgram(Program program) throws Exception {
		request(getModelClass(), "delete", program);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �V�X�e���敪�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSystem.getCode())) {
			showMessage(editView, "I00037", "C00980");
			editView.ctrlSystem.code.requestFocus();
			return false;
		}

		// �v���O�����R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramCode.getValue())) {
			showMessage(editView, "I00037", "C00818");
			editView.ctrlProgramCode.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���v���O���������ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			ProgramSearchCondition condition = new ProgramSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlProgramCode.getValue());

			List<Program> list = getProgram(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C00818");
				editView.ctrlProgramCode.requestTextFocus();
				return false;
			}
		}

		// �v���O�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramName.getValue())) {
			showMessage(editView, "I00037", "C00819");
			editView.ctrlProgramName.requestFocus();
			return false;
		}

		// �v���O�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramNames.getValue())) {
			showMessage(editView, "I00037", "C00820");
			editView.ctrlProgramNames.requestFocus();
			return false;
		}

		// �v���O�����������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlProgramNamek.getValue())) {
			showMessage(editView, "I00037", "C00821");
			editView.ctrlProgramNamek.requestFocus();
			return false;
		}

		// ���[�h���W���[���t�@�C�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlModuleName.getValue())) {
			showMessage(editView, "I00037", "C00823");
			editView.ctrlModuleName.requestFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}
		return true;

	}

}
