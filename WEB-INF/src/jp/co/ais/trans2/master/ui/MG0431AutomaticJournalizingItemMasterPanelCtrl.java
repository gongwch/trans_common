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
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * ����}�X�^ ��ʃR���g���[��
 */
public class MG0431AutomaticJournalizingItemMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0431AutomaticJournalizingItemMasterPanel mainView;

	/** �ҏW��� */
	protected MG0431AutomaticJournalizingItemMasterDialog editView = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/**
	 * ���샂�[�h�B
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
		mainView = new MG0431AutomaticJournalizingItemMasterPanel();
		addMainViewEvent();
	}

	/**
	 * ���C����ʃ{�^���������̃C�x���g
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
	 * ���C����ʂ̏�����
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
			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.AutoJnlItemRange.getCodeFrom(), mainView.AutoJnlItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.AutoJnlItemRange.getFieldFrom().requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �Ȗڐ���敪�����擾
			AutomaticJournalItemSearchCondition condition = getSearchCondition();
			List<AutomaticJournalItem> list = getAutomaticJournalItem(condition);

			// ���������ɊY�����镔�傪���݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {

				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �Ȗڐ���敪�����ꗗ�ɕ\������
			for (AutomaticJournalItem bean : list) {
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

			if (!checkMainView()) {
				return;
			}
			// �ҏW�Ώۂ̉Ȗڐ���敪���擾����
			AutomaticJournalItem bean = getSelectedAutomaticJournalItem();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̉Ȗڐ���敪�����Z�b�g����
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

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̉Ȗڐ���敪���擾����
			AutomaticJournalItem bean = getSelectedAutomaticJournalItem();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̉Ȗڐ���敪�����Z�b�g����
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

			if (!checkMainView()) {
				return;
			}

			// �폜�Ώۂ̉Ȗڐ���敪���擾����
			AutomaticJournalItem bean = getSelectedAutomaticJournalItem();

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteAutomaticJournalItem(bean);

			// �폜�����Ȗڐ���敪���ꗗ����폜
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
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {
			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.AutoJnlItemRange.getCodeFrom(), mainView.AutoJnlItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.AutoJnlItemRange.getFieldFrom().requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �f�[�^���o
			AutomaticJournalItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00911") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0431AutomaticJournalizingItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param bean ����B�C���A���ʂ̏ꍇ�͓��Y�������ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, AutomaticJournalItem bean) {

		AccountConfig ac = getCompany().getAccountConfig();

		// �Ȗ�
		editView.ctrlTItemGroup.ctrlItemReference.btn.setText(ac.getItemName());
		editView.ctrlTItemGroup.ctrlSubItemReference.btn.setText(ac.getSubItemName());
		editView.ctrlTItemGroup.ctrlDetailItemReference.btn.setText(ac.getDetailItemName());

		if (!ac.isUseDetailItem()) {
			editView.ctrlTItemGroup.ctrlDetailItemReference.setVisible(false);
		}
		this.mode = mode_;
		// �V�K
		switch (mode) {
		// �V�K
			case NEW:
				editView.setTitle(getWord("C01698"));

				break;

			case COPY:
			case MODIFY:
				// �ҏW
				if (Mode.MODIFY == mode_) {
					editView.setTitle(getWord("C00977"));
					editView.ctrlAutoJnlItemCode.setEditable(false);
					editView.setEditMode();
					// ����
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlTItemGroup.setEntity(bean.getItem());
				editView.ctrlAutoJnlItemCode.setNumber(DecimalUtil.toInt(bean.getKMK_CNT()));
				editView.ctrlAutoJnlItemName.setValue(bean.getKMK_CNT_NAME());
				editView.ctrlDepartment.code.setText(bean.getDEP_CODE());
				editView.ctrlDepartment.name.setText(bean.getDEP_NAMES());
				editView.ctrlTItemGroup.ctrlItemReference.code.setText(bean.getKMK_CODE());
				editView.ctrlTItemGroup.ctrlItemReference.name.setText(bean.getKMK_NAMES());
				if (!Util.isNullOrEmpty(bean.getHKM_CODE())) {
					editView.ctrlTItemGroup.ctrlSubItemReference.code.setText(bean.getHKM_CODE());
					editView.ctrlTItemGroup.ctrlSubItemReference.name.setText(bean.getHKM_NAMES());
					editView.ctrlTItemGroup.ctrlSubItemReference.btn.setEnabled(true);
					editView.ctrlTItemGroup.ctrlSubItemReference.code.setEditable(true);
				}
				if (!Util.isNullOrEmpty(bean.getUKM_CODE())) {
					editView.ctrlTItemGroup.ctrlDetailItemReference.code.setText(bean.getUKM_CODE());
					editView.ctrlTItemGroup.ctrlDetailItemReference.name.setText(bean.getUKM_NAMES());
					editView.ctrlTItemGroup.ctrlDetailItemReference.btn.setEnabled(true);
					editView.ctrlTItemGroup.ctrlDetailItemReference.code.setEditable(true);
				}
				
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

			// ���͂��ꂽ�Ȗڐ���敪�����擾
			AutomaticJournalItem bean = getInputedAutomaticJournalItem();

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
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return AutomaticJournalItemManager
	 */
	protected Class getModelClass() {
		return AutomaticJournalItemManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�Ȗڐ���敪��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�Ȗڐ���敪
	 */
	protected AutomaticJournalItem getInputedAutomaticJournalItem() {

		AutomaticJournalItem bean = createAutomaticJournalItem();

		bean.setKAI_CODE(getCompany().getCode());
		bean.setKMK_CNT(editView.ctrlAutoJnlItemCode.getValue());
		bean.setKMK_CNT_NAME(editView.ctrlAutoJnlItemName.getValue());
		bean.setDEP_CODE(editView.ctrlDepartment.getCode());
		bean.setDEP_NAMES(editView.ctrlDepartment.getNames());
		bean.setKMK_CODE(editView.ctrlTItemGroup.ctrlItemReference.getCode());
		bean.setKMK_NAMES(editView.ctrlTItemGroup.ctrlItemReference.getNames());
		bean.setHKM_CODE(editView.ctrlTItemGroup.ctrlSubItemReference.getCode());
		bean.setHKM_NAMES(editView.ctrlTItemGroup.ctrlSubItemReference.getNames());
		bean.setUKM_CODE(editView.ctrlTItemGroup.ctrlDetailItemReference.getCode());
		bean.setUKM_NAMES(editView.ctrlTItemGroup.ctrlDetailItemReference.getNames());

		return bean;

	}

	/**
	 * @return �Ȗڐ���敪
	 */
	protected AutomaticJournalItem createAutomaticJournalItem() {
		return new AutomaticJournalItem();
	}

	/**
	 * �Ȗڐ���敪�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �Ȗڐ���敪���
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�Ȗڐ���敪���
	 */
	protected List<Object> getRowData(AutomaticJournalItem bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean.getKMK_CNT()); // �Ȗڐ���敪
		list.add(bean.getKMK_CNT_NAME()); // �Ȗڐ���敪����
		list.add(bean.getDEP_CODE()); // ����R�[�h
		list.add(bean.getDEP_NAMES()); // ���喼��
		list.add(bean.getKMK_CODE()); // �ȖڃR�[�h
		list.add(bean.getKMK_NAMES()); // �Ȗږ���
		list.add(bean.getHKM_CODE()); // �⏕�R�[�h
		list.add(bean.getHKM_NAMES()); // �⏕����
		list.add(bean.getUKM_CODE()); // ����R�[�h
		list.add(bean.getUKM_NAMES()); // ���󖼏�

		return list;
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param mainEnabled enabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnModify.setEnabled(mainEnabled);
		mainView.btnCopy.setEnabled(mainEnabled);
		mainView.btnDelete.setEnabled(mainEnabled);
	}

	/**
	 * ���������ɊY������Ȗڐ���敪�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������Ȗڐ���敪�̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<AutomaticJournalItem> getAutomaticJournalItem(AutomaticJournalItemSearchCondition condition)
		throws Exception {

		List<AutomaticJournalItem> list = (List<AutomaticJournalItem>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w����ʂœ��͂��ꂽ�Ȗڐ���敪�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected AutomaticJournalItemSearchCondition getSearchCondition() {

		AutomaticJournalItemSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.AutoJnlItemRange.getCodeFrom());
		condition.setCodeTo(mainView.AutoJnlItemRange.getCodeTo());

		return condition;

	}

	/**
	 * @return ��������
	 */
	protected AutomaticJournalItemSearchCondition createCondition() {
		return new AutomaticJournalItemSearchCondition();
	}

	/**
	 * �ꗗ�őI������Ă���Ȗڐ���敪��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���Ȗڐ���敪
	 * @throws Exception
	 */
	protected AutomaticJournalItem getSelectedAutomaticJournalItem() throws Exception {

		AutomaticJournalItemSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(mainView.tbl
			.getSelectedRowValueAt(MG0431AutomaticJournalizingItemMasterPanel.SC.autojnlitemkbn).toString());

		List<AutomaticJournalItem> list = getAutomaticJournalItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193"); // �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
		}
		return list.get(0);
	}

	/**
	 * �w��̉Ȗڐ���敪���폜����
	 * 
	 * @param AutomaticJournalItem �Ȗڐ���敪
	 * @throws TException
	 */
	protected void deleteAutomaticJournalItem(AutomaticJournalItem AutomaticJournalItem) throws TException {
		request(getModelClass(), "delete", AutomaticJournalItem);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �Ȗڐ���敪�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlAutoJnlItemCode.getValue())) {
			showMessage(editView, "I00037", "C01008"); // �Ȗڐ���敪����͂��Ă��������B
			editView.ctrlAutoJnlItemCode.requestTextFocus();
			return false;
		}

		// �Ȗڐ���敪���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlAutoJnlItemName.getValue())) {
			showMessage(editView, "I00037", "C00518"); // ���̂���͂��Ă��������B
			editView.ctrlAutoJnlItemName.requestTextFocus();
			return false;
		}

		// ���傪�����͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartment.code.getText())) {
			showMessage(editView, "I00037", "C00698"); //  �������͂��Ă��������B
			editView.ctrlAutoJnlItemName.requestTextFocus();
			return false;
		}

		// �Ȗڂ������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlTItemGroup.ctrlItemReference.code.getText())) {
			showMessage(editView, "I00037", "C00572"); // �ȖڃR�[�h����͂��Ă��������B
			editView.ctrlAutoJnlItemName.requestTextFocus();
			return false;
		}

		// �⏕�R�[�h���g�p�Ŗ����͂̏ꍇ�G���[
		if (editView.ctrlTItemGroup.ctrlSubItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlTItemGroup.ctrlSubItemReference.code.getText())) {
				showMessage(editView, "I00037", "C00602"); // �⏕�ȖڃR�[�h����͂��Ă��������B
				editView.ctrlTItemGroup.ctrlSubItemReference.requestTextFocus();
				return false;
			}
		}
		// ����R�[�h���g�p�Ŗ����͂̏ꍇ�G���[
		if (editView.ctrlTItemGroup.ctrlDetailItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlTItemGroup.ctrlDetailItemReference.code.getText())) {
				showMessage(editView, "I00037", "C00603"); // ����ȖڃR�[�h����͂��Ă��������B
				editView.ctrlTItemGroup.ctrlDetailItemReference.requestTextFocus();
				return false;
			}
		}
		
		// �V�K�A���ʂ̏ꍇ�͓���Ȗڐ���敪�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			AutomaticJournalItemSearchCondition condition = createCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlAutoJnlItemCode.getValue());

			List<AutomaticJournalItem> list = getAutomaticJournalItem(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C01008"); // �w��̕���͊��ɑ��݂��܂��B
				editView.ctrlAutoJnlItemCode.requestTextFocus();
				return false;
			}
		}

		// �O���J�z���v = 1 �̏ꍇ�`�F�b�N 
		if (editView.ctrlAutoJnlItemCode.getIntValue() == 1) {
			// �`�F�b�N�f�[�^�擾
			AutomaticJournalItem AutomaticJournalItem = new AutomaticJournalItem();
			AutomaticJournalItem.setKMK_CODE(editView.ctrlTItemGroup.ctrlItemReference.getCode());
			int dckbn = (Integer) request(getModelClass(), "check", AutomaticJournalItem);

			// �ݎ؋敪���O���J�z���v�̏ꍇ�A�ؕ���������G���[
			if (dckbn == 0) {
				showMessage(editView, "I00258", "C01125", editView.ctrlTItemGroup.ctrlItemReference.getNames()); // �O���J�z���v���ݕ��Ȗڂł͂���܂���
				editView.ctrlTItemGroup.ctrlItemReference.requestTextFocus();
				return false;
			}
		}

		return true;

	}
}
