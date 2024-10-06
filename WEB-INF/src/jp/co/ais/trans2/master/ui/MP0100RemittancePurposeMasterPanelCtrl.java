package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * �V�����ړI�}�X�^�R���g���[��
 */
public class MP0100RemittancePurposeMasterPanelCtrl extends TController {

	/** �w����� */
	protected MP0100RemittancePurposeMasterPanel mainView = null;

	/** �ҏW��� */
	protected MP0100RemittancePurposeMasterDialog editView = null;

	/** �o�^���t */
	protected Date inpDate = null;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * ���샂�[�h
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
		mainView = new MP0100RemittancePurposeMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃{�^���������̃C�x���g
	 */
	protected void addMainViewEvent() {
		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�ҏW]�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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

			// �J�n�R�[�h <= �I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.ctrlSearch.getCodeFrom(), mainView.ctrlSearch.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlSearch.getFieldFrom().requestFocus();
				return;

			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �����ړI�����擾
			RemittanceSearchCondition condition = getSearchCondition();
			List<Remittance> list = getRemittancePurpose(condition);

			// ���������ɊY�����鑗���ړI�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage("I00022");
				return;
			}

			// �����ړI�����ꗗ�ɕ\������
			for (Remittance bean : list) {
				mainView.tbl.addRow(getRowData(bean));
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

			// �I���s�̃`�F�b�N
			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̑����ړI���擾����
			Remittance bean = getSelectedRemittancePurpose();
			inpDate = bean.getInpDate();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̑����ړI�����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, bean);

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

			// �I���s�̃`�F�b�N
			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̑����ړI���擾����
			Remittance bean = getSelectedRemittancePurpose();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̑����ړI�����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, bean);

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

			// �I���s�̃`�F�b�N
			if (!checkMainView()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {
				return;
			}

			// �����ړI�����擾
			Remittance bean = getSelectedRemittancePurpose();

			// �폜
			request(getModelClass(), "deleteRemittance", bean);

			// �폜���������ړI���ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �������b�Z�[�W
			showMessage(mainView, "I00013");

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
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �f�[�^���o
			RemittanceSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcelRemittance", condition);

			if (data == null || data.length == 0) {
				showMessage("I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11843") + ".xls"); // �����ړI�}�X�^

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MP0100RemittancePurposeMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ���ێ��x�R�[�h�m�莞
		editView.ctrlBalanceCode.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}

				ctrlRemmitanceReference_after();
			}
		});
	}

	/**
	 * ���ێ��x���̂������ړI���̂̏����l�Ƃ��ăZ�b�g�����i�����ړI���̕ύX�\�j
	 */
	protected void ctrlRemmitanceReference_after() {
		editView.ctrlPurposeName.setValue(editView.ctrlBalanceCode.getNames());
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h
	 * @param bean �����ړI�B�C���A���ʂ̏ꍇ�͓��Y�����ړI����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Remittance bean) {

		this.mode = mode_;

		if (Mode.NEW == mode) {
			// �V�K
			editView.setTitle(getWord("C01698")); // �V�K���
		}

		else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			if (Mode.MODIFY == mode) {
				// �ҏW
				editView.setTitle(getWord("C00977")); // �ҏW���
				editView.ctrlPurposeCode.setEditable(false);
				editView.setEditMode();

			} else {
				// ����
				editView.setTitle(getWord("C01738")); // ���ʉ��
			}

			editView.ctrlPurposeCode.setValue(bean.getCode());
			editView.ctrlBalanceCode.code.setValue(bean.getBalanceCode());
			editView.ctrlBalanceCode.name.setValue(bean.getBalanceName());
			editView.ctrlPurposeName.setValue(bean.getName());
		}
	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s��
			if (!isDialogInputCheck()) {
				return;
			}

			// ���͂��ꂽ�����ړI�����擾
			Remittance bean = getInputedRemittancePurpose();

			if (Mode.NEW == mode || Mode.COPY == mode) {
				// �V�K�o�^�܂��͕���

				request(getModelClass(), "entryRemittance", bean);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(bean));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);
				mainView.tbl.setRowSelection(0);

			} else if (Mode.MODIFY == mode) {
				// �C��

				request(getModelClass(), "modifyRemittance", bean);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(bean));
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
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return RemittancePurposeManager
	 */
	protected Class getModelClass() {
		return RemittanceManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�����ړI��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�����ړI
	 */
	protected Remittance getInputedRemittancePurpose() {

		Remittance bean = new Remittance();

		bean.setCompanyCode(super.getCompanyCode());
		bean.setCode(editView.ctrlPurposeCode.getValue());
		bean.setBalanceCode(editView.ctrlBalanceCode.getCode());
		bean.setName(editView.ctrlPurposeName.getValue());
		bean.setInpDate(inpDate);

		return bean;
	}

	/**
	 * @param bean
	 * @return �f�[�^�擾
	 */
	protected List<Object> getRowData(Remittance bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode()); // �����ړI�R�[�h
		list.add(bean.getBalanceCode()); // ���ێ��x�R�[�h
		list.add(bean.getName()); // �����ړI����
		list.add(bean);

		return list;
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param mainEnabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnModify.setEnabled(mainEnabled);
		mainView.btnCopy.setEnabled(mainEnabled);
		mainView.btnDelete.setEnabled(mainEnabled);
	}

	/**
	 * ���������ɊY�����鑗���ړI�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����鑗���ړI�̃��X�g
	 * @throws Exception
	 */
	protected List<Remittance> getRemittancePurpose(RemittanceSearchCondition condition) throws Exception {
		condition.setCompanyCode(getCompanyCode());
		List<Remittance> list = (List<Remittance>) request(getModelClass(), "getRemittance", condition);

		return list;
	}

	/**
	 * �w����ʂœ��͂��ꂽ�����ړI�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected RemittanceSearchCondition getSearchCondition() {

		RemittanceSearchCondition condition = createSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlSearch.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSearch.getCodeTo());

		return condition;
	}

	/**
	 * @return �����ړI���̌�������
	 */
	protected RemittanceSearchCondition createSearchCondition() {
		return new RemittanceSearchCondition();
	}

	/**
	 * �ꗗ�őI������Ă��鑗���ړI��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��鑗���ړI
	 * @throws Exception
	 */
	protected Remittance getSelectedRemittancePurpose() throws Exception {

		RemittanceSearchCondition condition = createSearchCondition();
		condition.setCode((String) mainView.tbl
			.getSelectedRowValueAt(MP0100RemittancePurposeMasterPanel.SC.purposeCode));
		condition.setCompanyCode(getCompany().getCode());

		List<Remittance> list = getRemittancePurpose(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó���
	 * 
	 * @return true�F����Afalse�F�G���[����
	 * @throws Exception
	 */
	protected boolean isDialogInputCheck() throws Exception {

		// �����ړI�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlPurposeCode.getValue())) {
			showMessage(editView, "I00037", "C00793"); // �����ړI�R�[�h����͂��Ă��������B
			editView.ctrlPurposeCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�A���ꑗ���ړI�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			RemittanceSearchCondition condition = createSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlPurposeCode.getValue());

			List<Remittance> list = getRemittancePurpose(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00793"); // �w��̑����ړI�R�[�h�͊��ɑ��݂��܂��B
				editView.ctrlPurposeCode.requestTextFocus();
				return false;
			}
		}

		// ���ێ��x�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlBalanceCode.getCode())) {
			showMessage(editView, "I00037", "C11839"); // ���ێ��x�R�[�h����͂��Ă��������B
			editView.ctrlBalanceCode.btn.requestFocus();
			return false;
		}

		// �����ړI���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlPurposeName.getValue())) {
			showMessage(editView, "I00037", "C11840"); // �����ړI���̂���͂��Ă��������B
			editView.ctrlPurposeName.requestFocus();
			return false;
		}

		return true;
	}
}
