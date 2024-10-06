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
import jp.co.ais.trans2.model.item.*;

/**
 * MG0065ItemSystemNameMaster - �Ȗڑ̌n���̃}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MG0065ItemSystemNameMasterPanelCtrl extends TController {

	/** �V�K��� */
	protected MG0065ItemSystemNameMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0065ItemSystemNameMasterDialog editView = null;

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
		mainView = new MG0065ItemSystemNameMasterPanel();
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
			ItemOrganizationSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlItemOrgRan.requestFocus();
				return;
			}

			// �ꗗ���N���A
			mainView.tbList.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �f�[�^�擾
			List<ItemOrganization> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (ItemOrganization item : list) {
				mainView.tbList.addRow(getRowData(item));
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
			ItemOrganization bean = getSelected();

			// �߂�l�𔻒�
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
			ItemOrganization bean = getSelected();

			// �߂�l�𔻒�
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
			ItemOrganization bean = getSelected();

			// �߂�l�𔻒�
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

			// ���������̎擾
			ItemOrganizationSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlItemOrgRan.requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcelItemOrganization", condition);

			// �f�[�^�����������ꍇ�A�G���[���b�Z�[�W�o��
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// �G�N�Z���^�C�g���Z�b�g
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00618") + getWord("C00500") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		editView = new MG0065ItemSystemNameMasterDialog(mainView.getParentFrame(), true);
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

			// ���̓`�F�b�N���s���B
			if (!isInputCorrect()) {
				return;
			}

			// ���͂��ꂽ�Ȗڑ̌n���̃}�X�^�����擾
			ItemOrganization bean = getInputtedItemOrganization();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entryItemOrganization", bean);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbList.addRow(getRowData(bean));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modifyItemOrganization", bean);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbList.modifySelectedRow(getRowData(bean));

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
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param bean �C���A���ʂ̏ꍇ�͓��Y����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, ItemOrganization bean) {

		this.mode = mode_;

		// �V�K�̏ꍇ
		if (Mode.NEW == mode_) {
			// �V�K���
			editView.setTitle(getWord("C01698"));
			return;
		}

		// �ҏW
		if (Mode.MODIFY == mode_) {
			// �ҏW���
			editView.setTitle(getWord("C00977"));
			editView.ctrlCode.setEditable(false);
			editView.setEditMode();
		}
		// ����
		else {
			// ���ʉ��
			editView.setTitle(getWord("C01738"));
		}

		// �ҏW��ʂ̏����l
		editView.ctrlCode.setValue(bean.getCode());
		editView.ctrlName.setValue(bean.getName());
		editView.ctrlNameS.setValue(bean.getNames());
		editView.ctrlNameK.setValue(bean.getNamek());

	}

	/**
	 * �ҏW��ʂ̓��͒l�擾
	 * 
	 * @return bean
	 */
	protected ItemOrganization getInputtedItemOrganization() {

		ItemOrganization bean = new ItemOrganization();

		bean.setCode(editView.ctrlCode.getValue());
		bean.setName(editView.ctrlName.getValue());
		bean.setNames(editView.ctrlNameS.getValue());
		bean.setNamek(editView.ctrlNameK.getValue());

		return bean;
	}

	/**
	 * ���������擾
	 * 
	 * @return ItemOrganizationSearchCondition ��������
	 */
	protected ItemOrganizationSearchCondition getSearchCondition() {

		ItemOrganizationSearchCondition condition = new ItemOrganizationSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlItemOrgRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlItemOrgRan.getCodeTo());

		return condition;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(ItemOrganization bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());

		return list;
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected ItemOrganization getSelected() throws Exception {

		ItemOrganization bean = (ItemOrganization) mainView.tbList
			.getSelectedRowValueAt(MG0065ItemSystemNameMasterPanel.SC.ENTITY);

		ItemOrganizationSearchCondition condition = new ItemOrganizationSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<ItemOrganization> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			showMessage("I00193");
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
	protected List<ItemOrganization> getList(ItemOrganizationSearchCondition condition) throws Exception {
		List<ItemOrganization> list = (List<ItemOrganization>) request(getModelClass(), "getItemOrganization",
			condition);
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(ItemOrganization bean) throws Exception {
		request(getModelClass(), "deleteItemOrganization", bean);
	}

	/**
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00617");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00618");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameS.getValue())) {
			showMessage(editView, "I00037", "C00619");
			editView.ctrlNameS.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameK.getValue())) {
			showMessage(editView, "I00037", "C00620");
			editView.ctrlNameK.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h&&����Ȗڑ̌n�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ItemOrganizationSearchCondition condition = new ItemOrganizationSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<ItemOrganization> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00617");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}