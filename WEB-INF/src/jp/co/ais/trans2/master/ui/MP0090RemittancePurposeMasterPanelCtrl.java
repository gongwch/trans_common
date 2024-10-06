package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.remittance.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * MP0090RemittancePurposeMaster - �����ړI�}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MP0090RemittancePurposeMasterPanelCtrl extends TController {

	/** �w����� */
	protected MP0090RemittancePurposeMasterPanel mainView = null;

	/** �ҏW��� */
	protected MP0090RemittancePurposeMasterDialog editView = null;

	/** ���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p���� */
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

			// �w����ʏ�����
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
	 * �w����ʐ���, �C�x���g��`
	 */
	protected void createMainView() {
		mainView = new MP0090RemittancePurposeMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * �w����ʂ̃C�x���g��`
	 */
	protected void addMainViewEvent() {
		// [�V�K]�{�^��
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�ҏW]�{�^��
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�폜]�{�^��
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�G�N�Z��]�{�^��
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {
		try {

			// �ҏW��ʐ���
			createEditView();

			// �ҏW��ʏ�����
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

			// ���������擾
			RemittanceSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlRemittancePurposeRefRan.requestFocus();
				return;
			}

			// �ꗗ���N���A
			mainView.tbList.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �����ړI�����擾
			List<Remittance> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (Remittance cst : list) {
				mainView.tbList.addRow(getRowData(cst));
			}

			// ���C���{�^������
			setMainButtonEnabled(true);

			mainView.tbList.setRowSelectionInterval(0, 0);

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

			// �ҏW�Ώۂ̃f�[�^�擾
			Remittance bean = getSelected();

			if (bean == null) {
				return;
			}

			// �ҏW��ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.MODIFY, bean);

			// �ҏW��ʕ\��
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

			// ���ʑΏۂ̃f�[�^�擾
			Remittance bean = getSelected();

			if (bean == null) {
				return;
			}

			// ���ʉ�ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.COPY, bean);

			// ���ʉ�ʕ\��
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

			// �m�F���b�Z�[�W�\��
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃f�[�^�擾
			Remittance bean = getSelected();

			if (bean == null) {
				return;
			}

			// �폜���s
			doDelete(bean);

			// �폜�����s���ꗗ����폜
			mainView.tbList.removeSelectedRow();

			// �폜������A�ꗗ�̃��R�[�h��0���̏ꍇ�A���C���{�^������
			if (mainView.tbList.getRowCount() == 0) {
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

		if (mainView.tbList.getSelectedRow() == -1) {
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

			// ���������擾
			RemittanceSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlRemittancePurposeRefRan.requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcelRemittancePurpose", condition);

			// �f�[�^�����������ꍇ�A�G���[���b�Z�[�W�o��
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// �G�N�Z���^�C�g���Z�b�g
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11843") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		editView = new MP0090RemittancePurposeMasterDialog(mainView.getParentFrame(), true);
		addEditViewEvent();
	}

	/**
	 * �ҏW��ʂ̃C�x���g��`
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
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {
		try {

			// ���̓f�[�^�`�F�b�N
			if (!isInputCorrect()) {
				return;
			}

			// ���̓f�[�^�擾
			Remittance bean = getInputtedRemittance();

			// �V�K�o�^�̏ꍇ
			switch (mode) {

			case NEW:
			case COPY:

				// �V�K�o�^ - ���ʓo�^
				request(getModelClass(), "entry", bean);

				// �ǉ������ꗗ�ɔ��f
				mainView.tbList.addRow(getRowData(bean));

				// ���C���{�^������
				setMainButtonEnabled(true);

				break;

			case MODIFY:

				// �C��
				request(getModelClass(), "modify", bean);

				// �C�������ꗗ�ɔ��f
				mainView.tbList.modifySelectedRow(getRowData(bean));

				break;
			}

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
	 * �ҏW��ʏ�����
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, Remittance bean) {

		this.mode = editMode;

		switch (editMode) {

		case NEW:
			editView.setTitle(getWord("C01698"));

			break;

		case MODIFY:
		case COPY:

			if (Mode.MODIFY == editMode) {

				editView.setTitle(getWord("C00977"));
				editView.ctrlRemittancePurposeCode.setEditable(false);
				editView.setEditMode();

			} else {

				editView.setTitle(getWord("C01738"));

			}

			editView.ctrlRemittancePurposeCode.setValue(bean.getCode());
			editView.ctrlRemittancePurpose.setValue(bean.getName());

			break;
		}
	}

	/**
	 * �ҏW��ʂ̓��͒l�擾
	 * 
	 * @return Summary
	 */
	protected Remittance getInputtedRemittance() {

		Remittance bean = new Remittance();

		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlRemittancePurposeCode.getValue());
		bean.setName(editView.ctrlRemittancePurpose.getValue());

		return bean;
	}

	/**
	 * ���������擾
	 * 
	 * @return SummarySearchCondition ��������
	 */
	protected RemittanceSearchCondition getSearchCondition() {

		RemittanceSearchCondition condition = new RemittanceSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlRemittancePurposeRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlRemittancePurposeRefRan.getCodeTo());

		return condition;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Remittance bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode()); // �����ړI�R�[�h
		list.add(bean.getName()); // �����ړI���J�i

		list.add(bean);

		return list;
	}

	/**
	 * @return ��������
	 */
	protected ConsumptionTaxSearchCondition createCondition() {
		return new ConsumptionTaxSearchCondition();
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected Remittance getSelected() throws Exception {

		Remittance bean = (Remittance) mainView.tbList
				.getSelectedRowValueAt(MP0090RemittancePurposeMasterPanel.SC.ENTITY);

		RemittanceSearchCondition condition = new RemittanceSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<Remittance> list = getList(condition);

		// ���b�Z�[�W�ǉ��K�v ##########
		if (list == null || list.isEmpty()) {
			showMessage("I00193");// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			return null;
		}
		return list.get(0);
	}

	/**
	 * ���������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<Remittance> getList(RemittanceSearchCondition condition) throws Exception {
		List<Remittance> list = (List<Remittance>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(Remittance bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return RemittanceManager.class;
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {

		if (Util.isNullOrEmpty(editView.ctrlRemittancePurposeCode.getValue())) {
			showMessage(editView, "I00037", "C00793"); // �����ړI�R�[�h
			editView.ctrlRemittancePurposeCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlRemittancePurpose.getValue())) {
			showMessage(editView, "I00037", "C00947"); // �����ړI
			editView.ctrlRemittancePurpose.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h&&���ꑗ���ړI�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			RemittanceSearchCondition condition = new RemittanceSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlRemittancePurposeCode.getValue());

			List<Remittance> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlRemittancePurposeCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}