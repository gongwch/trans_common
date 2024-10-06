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
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * MG0070ItemSummaryMaster - �ȖڏW�v�}�X�^ - Controller Class
 * 
 * @author AIS
 */
public class MG0070ItemSummaryMasterPanelCtrl extends TController {

	/** �V�K��� */
	protected MG0070ItemSummaryMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0070ItemSummaryMasterDialog editView = null;

	/** ���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p���� */
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
		mainView = new MG0070ItemSummaryMasterPanel();
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

			// �ҏW�ҏW��ʏ�����
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
			ItemSummarySearchCondition condition = getSearchCondition();

			// �J�n <= �I�� �`�F�b�N
			if (!Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo())) {
				showMessage(mainView, "I00068");
				mainView.ctrlAccSumRef.requestFocus();
				return;
			}

			// �ꗗ���N���A
			mainView.tbl.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �f�[�^�擾
			List<ItemSummary> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (ItemSummary bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}

			// ���C���{�^������
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

			// �ҏW�Ώۂ̃f�[�^�擾
			ItemSummary bean = getSelected();

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

			// ���ʑΏۂ̃f�[�^�擾
			ItemSummary bean = getSelected();

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

			// �m�F���b�Z�[�W�\��
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃f�[�^�擾
			ItemSummary bean = getSelected();

			// �폜���s
			doDelete(bean);

			// �폜�����s���ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜������A�ꗗ�̃��R�[�h��0���̏ꍇ�A���C���{�^������
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
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {
		try {

			// �J�n <= �I�� �`�F�b�N
			if (Util.isSmallerThen(mainView.ctrlAccSumRef.getCodeFrom(), mainView.ctrlAccSumRef.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlAccSumRef.requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// ���������擾
			ItemSummarySearchCondition condition = getSearchCondition();

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			// �f�[�^�����������ꍇ�A�G���[���b�Z�[�W�o��
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// �G�N�Z���^�C�g���Z�b�g
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02019") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		editView = new MG0070ItemSummaryMasterDialog(mainView.getParentFrame(), true);
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
			ItemSummary bean = getInputtedAccountSummary();

			// �V�K�o�^�̏ꍇ
			switch (mode) {
				case NEW:
				case COPY:

					// �V�K�o�^ - ���ʓo�^
					request(getModelClass(), "entry", bean);

					// �ǉ������ꗗ�ɔ��f
					mainView.tbl.addRow(getRowData(bean));

					// ���C���{�^������
					setMainButtonEnabled(true);

					break;

				case MODIFY:

					// �C��
					request(getModelClass(), "modify", bean);

					// �C�������ꗗ�ɔ��f
					mainView.tbl.modifySelectedRow(getRowData(bean));

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
	protected void initEditView(Mode editMode, ItemSummary bean) {

		editView.ctrlSumCode.getSearchCondition().setTitleItem(true);
		editView.ctrlSumCode.getSearchCondition().setSumItem(true);
		editView.ctrlKmkCode.getSearchCondition().setSumItem(true);
		editView.ctrlKmkCode.getSearchCondition().setTitleItem(true);

		this.mode = editMode;

		switch (editMode) {
			case NEW:
				// �ҏW��ʃ^�C�g���Z�b�g
				editView.setTitle(getWord("C01698"));
				break;
			case MODIFY:
			case COPY:
				// �ҏW
				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlKmtCode.setEditable(false);
					editView.ctrlKmkCode.setEditable(false);
					editView.setEditMode();

					// ����
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlKmtCode.code.setValue(bean.getKmtCode());
				editView.ctrlKmtCode.name.setValue(bean.getKmtName());
				editView.ctrlKmkCode.code.setValue(bean.getKmkCode());
				editView.ctrlKmkCode.name.setValue(bean.getKmkName());
				editView.ctrlKokName.setValue(bean.getKokName());
				editView.ctrlSumCode.code.setValue(bean.getSumCode());
				editView.ctrlSumCode.name.setValue(bean.getSumName());
				editView.ctrlOdr.setValue(bean.getOdr());
				if (bean.getHyjKbn() == 1) {
					editView.ctrlHyjKbnVisible.setSelected(true);
				} else {
					editView.ctrlHyjKbnInvisible.setSelected(true);
				}
				editView.ctrlBeginDate.setValue(bean.getDateFrom());
				editView.ctrlEndDate.setValue(bean.getDateTo());
				break;
		}
	}

	/**
	 * �ҏW��ʂ̓��͒l�擾
	 * 
	 * @return AccountSummary
	 */
	protected ItemSummary getInputtedAccountSummary() {

		ItemSummary bean = new ItemSummary();

		bean.setKmtCode(editView.ctrlKmtCode.getCode());
		bean.setKmtName(editView.ctrlKmtCode.getNames());
		bean.setKmkCode(editView.ctrlKmkCode.getCode());
		bean.setKmkName(editView.ctrlKmkCode.getNames());
		bean.setKokName(editView.ctrlKokName.getValue());
		bean.setSumCode(editView.ctrlSumCode.getCode());
		bean.setSumName(editView.ctrlSumCode.getNames());
		bean.setOdr(editView.ctrlOdr.getValue());

		if (editView.ctrlHyjKbnVisible.isSelected()) {
			bean.setHyjKbn(1);
		} else {
			bean.setHyjKbn(0);
		}

		bean.setDateFrom(editView.ctrlBeginDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		return bean;
	}

	/**
	 * ���������擾
	 * 
	 * @return AccountSummarySearchCondition ��������
	 */
	protected ItemSummarySearchCondition getSearchCondition() {

		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setKmtCode(mainView.ctrlAccSumRef.getCodeUp());
		condition.setCodeFrom(mainView.ctrlAccSumRef.getCodeFrom());
		condition.setCodeTo(mainView.ctrlAccSumRef.getCodeTo());

		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(ItemSummary bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getKmtCode());
		list.add(bean.getKmtName());
		list.add(bean.getKmkCode());
		list.add(bean.getKmkName());
		list.add(bean.getKokName());
		list.add(bean.getSumCode());
		list.add(bean.getSumName());
		list.add(bean.getOdr());
		list.add(bean.getHyjKbn() == 1 ? getWord("C00432") : getWord("C01297"));
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);
		return list;
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected ItemSummary getSelected() throws Exception {

		ItemSummary bean = (ItemSummary) mainView.tbl.getSelectedRowValueAt(MG0070ItemSummaryMasterPanel.SC.ENTITY);

		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setKmtCode(bean.getKmtCode());
		condition.setKmkCode(bean.getKmkCode());

		List<ItemSummary> list = getList(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * ���������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition
	 * @return list
	 * @throws Exception
	 */
	protected List<ItemSummary> getList(ItemSummarySearchCondition condition) throws Exception {
		List<ItemSummary> list = (List<ItemSummary>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(ItemSummary bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * �C���^�[�t�F�[�X��Ԃ�
	 * 
	 * @return �C���^�[�t�F�[�X
	 */
	protected Class getModelClass() {
		return ItemSummaryManager.class;
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */
	protected boolean isInputCorrect() throws Exception {
		// �Ȗڑ̌n�R�[�h������
		if (Util.isNullOrEmpty(editView.ctrlKmtCode.getCode())) {
			showMessage(editView, "I00037", "C00617");
			editView.ctrlKmtCode.requestTextFocus();
			return false;
		}
		// �ȖڃR�[�h������
		if (Util.isNullOrEmpty(editView.ctrlKmkCode.getCode())) {
			showMessage(editView, "I00037", "C00572");
			editView.ctrlKmkCode.requestTextFocus();
			return false;
		}
		// ���\�Ȗږ��̖�����
		if (Util.isNullOrEmpty(editView.ctrlKokName.getValue())) {
			showMessage(editView, "I00037", "C00624");
			editView.ctrlKokName.requestTextFocus();
			return false;
		}

		// �o�͏���������
		if (Util.isNullOrEmpty(editView.ctrlOdr.getValue())) {
			showMessage(editView, "I00037", "C00776");
			editView.ctrlOdr.requestTextFocus();
			return false;
		}
		// �J�n�N����������
		if (Util.isNullOrEmpty(editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}
		//�W�v�����Ȗځ��Ȗڂ��ǂ����A�`�F�b�N
		if(Util.equals(editView.ctrlSumCode.getCode(), editView.ctrlKmkCode.getCode())) {
			showMessage(editView, "I00893","C00625","C00572");
			editView.ctrlSumCode.requestTextFocus();
			return false;
		}
		// �I���N����������
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}
		// �J�n <= �I��
		if (Util.isSmallerThenByYMD(editView.ctrlEndDate.getValue(), editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00068");
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h&&����Ȗڑ̌n�R�[�h&&����ȖڃR�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setKmtCode(editView.ctrlKmtCode.getCode());
			condition.setKmkCode(editView.ctrlKmkCode.getCode());

			List<ItemSummary> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C00077");
				editView.ctrlKmtCode.requestTextFocus();
				return false;
			}
		}

		return true;
	}
}