package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.item.*;

/**
 * ��Њԕt�ւ̃R���g���[���N���X�B
 * 
 * @author AIS
 */
public class MG0350InterCompanyTransferMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0350InterCompanyTransferMasterPanel mainView = null;

	/** �w����� */
	protected MG0350InterCompanyTransferMasterDialog editView = null;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * ���샂�[�h
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

	/**
	 * �w����ʂ̃t�@�N�g��
	 */
	protected void createMainView() {

		mainView = new MG0350InterCompanyTransferMasterPanel();
		addMainViewEvent();

	}

	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		return this.mainView;
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {
		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);
	}

	/**
	 * �w����ʂ̃C�x���g��`
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

			// ���̓`�F�b�N
			if (!isInputCorrectWhenSearch()) {
				return;
			}

			// �����������擾
			InterCompanyTransferSearchCondition condition = getSearchCondition();
			List<InterCompanyTransfer> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (InterCompanyTransfer bean : list) {
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

			// �ҏW�Ώۂ̃f�[�^���擾����
			InterCompanyTransfer bean = getSelectedRowData();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̃f�[�^���Z�b�g����
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

			// ���ʑΏۂ̃f�[�^���擾����
			InterCompanyTransfer bean = getSelectedRowData();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̃f�[�^���Z�b�g����
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

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃f�[�^���擾����
			InterCompanyTransfer bean = getSelectedRowData();

			// �폜����
			doDelete(bean);

			// �폜�����s���ꗗ����폜
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
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// ���̓`�F�b�N
			if (!isInputCorrectWhenSearch()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			InterCompanyTransferSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// ��Њԕt�փ}�X�^
			printer.preview(data, getWord("C02341") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���������͂����l���Ó������`�F�b�N����
	 * 
	 * @return ���������͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isInputCorrectWhenSearch() {

		// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
		if (Util.isSmallerThen(mainView.ctrlRange.getCodeFrom(), mainView.ctrlRange.getCodeTo()) == false) {
			showMessage(editView, "I00068");
			mainView.ctrlRange.getFieldFrom().requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0350InterCompanyTransferMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

		// �ȖژA������
		editView.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				return changedItem();
			}
		});

	}

	/**
	 * �ȖڕύX����
	 * 
	 * @return false:NG
	 */
	protected boolean changedItem() {
		Item item = editView.ctrlItem.getEntity();

		if (item == null) {
			clearInputForItem();
			return true;
		}

		// �⏕�A���󂪂���ꍇ�́A�������𔽉f
		item = item.getPreferedItem();

		// �����
		CustomerType customerType = item.getClientType();
		boolean needCustomer = customerType != null && customerType != CustomerType.NONE;

		editView.ctrlCustomer.setEditable(needCustomer);
		editView.ctrlCustomer.getSearchCondition().setType(customerType);

		if (needCustomer) {
			editView.ctrlCustomer.refleshEntity();
		} else {
			editView.ctrlCustomer.clear();
		}

		return true;
	}

	/**
	 * �Ȗڊ֘A���͕��̂ݏ������
	 */
	public void clearInputForItem() {
		// �N���A
		editView.ctrlCustomer.clear();
		editView.ctrlCustomer.setEditable(false);
	}

	/**
	 * /** �ҏW��ʂ�����������
	 * 
	 * @param editMode ���샂�[�h�B
	 * @param bean �f�[�^�B�C���A���ʂ̏ꍇ�͓��Y�f�[�^��ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode editMode, InterCompanyTransfer bean) {

		this.mode = editMode;

		// �v���� ����
		editView.ctrlCompany.getSearchCondition().setGroupAccountOnly(); // �O���[�v��Ђ̂�

		// �ȖژA���X�V
		clearInputForItem();

		// �V�K�̏ꍇ
		if (Mode.NEW == editMode) {
			// �V�K���
			editView.setTitle(getWord("C01698"));

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// �ҏW
			if (Mode.MODIFY == editMode) {
				// �ҏW���
				editView.setTitle(getWord("C00977"));
				editView.ctrlCompany.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				// ���ʉ��
				editView.setTitle(getWord("C01738"));
			}

			// �f�[�^��\��t����
			editView.ctrlCompany.setEntity(bean.getTransferCompany());
			editView.ctrlDepartment.setEntity(bean.getDepartment());
			editView.ctrlItem.setEntity(bean.getItem());

			// �ȖژA��
			changedItem();

			if (editView.ctrlCustomer.isEditable()) {
				editView.ctrlCustomer.setEntity(bean.getCustomer());
			}
		}

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

			// ���͂��ꂽ�f�[�^���擾
			InterCompanyTransfer bean = getInputedData();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", bean);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(bean));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", bean);

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
	 * �w����ʂœ��͂��ꂽ�f�[�^�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected InterCompanyTransferSearchCondition getSearchCondition() {

		InterCompanyTransferSearchCondition condition = new InterCompanyTransferSearchCondition();
		condition.setCompany(getCompany());
		condition.setTransferCompanyFrom(mainView.ctrlRange.getCodeFrom());
		condition.setTransferCompanyTo(mainView.ctrlRange.getCodeTo());

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getModelClass() {
		return InterCompanyTransferManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�f�[�^��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�f�[�^
	 */
	protected InterCompanyTransfer getInputedData() {

		InterCompanyTransfer bean = new InterCompanyTransfer();
		bean.setCompanyCode(TLoginInfo.getCompany().getCode());
		bean.setTransferCompany(editView.ctrlCompany.getEntity());
		bean.setDepartment(editView.ctrlDepartment.getEntity());
		bean.setItem(editView.ctrlItem.getEntity());

		Customer customer = editView.ctrlCustomer.getEntity();
		if (customer == null) {
			customer = new Customer();
		}

		bean.setCustomer(customer);

		return bean;

	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�f�[�^
	 */
	protected String[] getRowData(InterCompanyTransfer bean) {

		return new String[] { bean.getTransferCompany().getCode(), //
				bean.getTransferCompany().getNames(),//
				bean.getDepartment().getCode(), //
				bean.getDepartment().getNames(),//
				bean.getItem().getCode(),//
				bean.getItem().getNames(), //
				bean.getItem().getSubItemCode(),//
				bean.getItem().getSubItemNames(),//
				bean.getItem().getDetailItemCode(),//
				bean.getItem().getDetailItemNames(), //
				bean.getCustomer().getCode(),//
				bean.getCustomer().getNames() };//

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
	 * ���������ɊY�����郊�X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郊�X�g
	 * @throws Exception
	 */
	protected List<InterCompanyTransfer> getList(InterCompanyTransferSearchCondition condition) throws Exception {

		List<InterCompanyTransfer> list = (List<InterCompanyTransfer>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * �ꗗ�őI������Ă���f�[�^��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���f�[�^
	 * @throws Exception
	 */
	protected InterCompanyTransfer getSelectedRowData() throws Exception {

		String transferCompanyCode = (String) mainView.tbl
			.getSelectedRowValueAt(MG0350InterCompanyTransferMasterPanel.SC.KTK_KAI_CODE);

		InterCompanyTransfer bean = (InterCompanyTransfer) request(getModelClass(), "getOne", getCompanyCode(),
			transferCompanyCode);

		if (bean == null) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException(getMessage("I00193"));
		}
		return bean;

	}

	/**
	 * �w��̃f�[�^���폜����
	 * 
	 * @param bean �}�X�^
	 * @throws Exception
	 */
	protected void doDelete(InterCompanyTransfer bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// �R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCompany.getCode())) {
			// ��ЃR�[�h
			showMessage(editView, "I00037", "C00596");
			editView.ctrlCompany.requestFocus();
			return false;
		}

		if (Mode.NEW == mode || Mode.COPY == mode) {

			// �V�K�A���ʂ̏ꍇ�͓���f�[�^�����ɑ��݂�����G���[
			InterCompanyTransferSearchCondition condition = new InterCompanyTransferSearchCondition();
			condition.setCompany(getCompany());
			condition.setTransferCompanyCode(editView.ctrlCompany.getCode());

			List<InterCompanyTransfer> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// �t�։�ЃR�[�h
				showMessage(editView, "I00055", "C02050");
				editView.ctrlCompany.requestTextFocus();
				return false;
			}

			// ���̓R�[�h�ƃ��O�C����ЃR�[�h�������ꍇ�G���[
			if (editView.ctrlCompany.getCode().equals(getCompanyCode())) {
				// ���Ђ̕t�ւ͐ݒ�ł��܂���B
				showMessage(editView, "I00263");
				editView.ctrlCompany.requestTextFocus();
				return false;
			}
		}

		// �t�֌v�㕔�傪�����͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartment.getCode())) {
			// �t�֌v�㕔��
			showMessage(editView, "I00037", "C00847");
			editView.ctrlDepartment.requestTextFocus();
			return false;
		}

		// �t�։Ȗڂ������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItem.ctrlItemReference.getCode())) {
			// �t��
			showMessage(editView, "I00037", getWord("C00375") + getCompany().getAccountConfig().getItemName());
			editView.ctrlItem.ctrlItemReference.requestTextFocus();
			return false;
		}

		// �t�֕⏕�Ȗڂ������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItem.ctrlSubItemReference.getCode())
			&& editView.ctrlItem.ctrlSubItemReference.code.isEditable()) {
			// �t��
			showMessage(editView, "I00037", getWord("C00375") + getCompany().getAccountConfig().getSubItemName());
			editView.ctrlItem.ctrlSubItemReference.requestTextFocus();
			return false;
		}

		// �t�֓���Ȗڂ������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItem.ctrlDetailItemReference.getCode())
			&& editView.ctrlItem.ctrlDetailItemReference.code.isEditable()) {
			// �t��
			showMessage(editView, "I00037", getWord("C00375") + getCompany().getAccountConfig().getDetailItemName());
			editView.ctrlItem.ctrlDetailItemReference.requestTextFocus();
			return false;
		}

		// ����悪�����͂̏ꍇ�G���[
		if (editView.ctrlCustomer.isEditable() && Util.isNullOrEmpty(editView.ctrlCustomer.getCode())) {
			// �����
			showMessage(editView, "I00037", "C00408");
			editView.ctrlCustomer.requestTextFocus();
			return false;
		}

		return true;

	}

}
