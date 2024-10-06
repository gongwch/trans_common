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
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0320SystemDivisionMasterPanel.SC;
import jp.co.ais.trans2.model.program.*;

/**
 * �V�X�e���敪�}�X�^ �R���g���[���[
 */
public class MG0320SystemDivisionMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0320SystemDivisionMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0320SystemDivisionMasterDialog editView = null;

	/** ���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p */
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
		mainView = new MG0320SystemDivisionMasterPanel();
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

			// ��������
			SystemDivisionSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// �x�����b�Z�[�W�\��
				showMessage(mainView, "I00449");// �J�n�����I���ɂ��Ă��������B
				mainView.TSystemDivisionReferenceRange.ctrlSysDivReferenceFrom.code.requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �V�X�e���敪�����擾
			List<SystemDivision> list = getSystemDivision(condition);

			// ���������ɊY������V�X�e���敪�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �V�X�e���敪�����ꗗ�ɕ\������
			for (SystemDivision SystemDivision : list) {
				mainView.tbl.addRow(getRowData(SystemDivision));
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

			// �ҏW�Ώۂ̃V�X�e���敪�����擾����
			SystemDivision bean = getSelectedSystemDivision();

			// �߂�l�𔻒�
			if (bean == null) {
				return;
			}

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̃V�X�e���敪�����Z�b�g����
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

			// ���ʑΏۂ̃V�X�e���敪���擾����
			SystemDivision bean = getSelectedSystemDivision();

			// �߂�l�𔻒�
			if (bean == null) {
				return;
			}

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̃V�X�e���敪�����Z�b�g����
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

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃V�X�e���敪���擾����
			SystemDivision bean = getSelectedSystemDivision();

			// �߂�l�𔻒�
			if (bean == null) {
				return;
			}

			// �폜����
			deleteSystemDivision(bean);

			// �폜�����V�X�e���敪���ꗗ����폜
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
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			SystemDivisionSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// �V�X�e���敪�}�X�^
			printer.preview(data, getWord("C02354") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʍ쐬�B�ҏW��ʂ�V�K�ɐ������A�C�x���g���`����B
	 */
	protected void createEditView() {
		editView = new MG0320SystemDivisionMasterDialog(mainView.getParentFrame(), true);
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
	 * @param bean �C���A���ʂ̏ꍇ�͓��Y����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, SystemDivision bean) {

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
			editView.ctrlKbn.setEditable(false);
			editView.setEditMode();
		}
		// ����
		else {
			// ���ʉ��
			editView.setTitle(getWord("C01738"));
		}

		// �ҏW��ʂ̏����l
		editView.ctrlKbn.setValue(bean.getCode());
		editView.ctrlKbnName.setValue(bean.getName());
		editView.ctrlKbnNameS.setValue(bean.getNames());
		editView.ctrlKbnNameK.setValue(bean.getNamek());
		editView.ctrlExternalKbn.setSelectedItemValue(bean.getOuterSystemType());

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

			// ���͂��ꂽ�V�X�e���敪�����擾
			SystemDivision bean = getInputedSystemDivision();

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
	 * �w����ʂœ��͂��ꂽ�V�X�e���敪�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected SystemDivisionSearchCondition getSearchCondition() {

		SystemDivisionSearchCondition condition = new SystemDivisionSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.TSystemDivisionReferenceRange.getCodeFrom());
		condition.setCodeTo(mainView.TSystemDivisionReferenceRange.getCodeTo());

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClass() {
		return SystemDivisionManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ����Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�V�X�e���敪
	 */
	protected SystemDivision getInputedSystemDivision() {

		SystemDivision bean = new SystemDivision();
		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlKbn.getValue());
		bean.setName(editView.ctrlKbnName.getValue());
		bean.setNames(editView.ctrlKbnNameS.getValue());
		bean.setNamek(editView.ctrlKbnNameK.getValue());
		bean.setOuterSystemType((OuterSystemType) editView.ctrlExternalKbn.getSelectedItemValue());
		return bean;

	}

	/**
	 * �V�X�e���敪�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �V�X�e���敪
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�V�X�e���敪
	 */
	protected List<Object> getRowData(SystemDivision bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(getWord(OuterSystemType.getName(bean.getOuterSystemType())));
		return list;
	}

	/**
	 * �ꗗ�őI������Ă���V�X�e���敪��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���V�X�e���敪
	 * @throws Exception
	 */
	protected SystemDivision getSelectedSystemDivision() throws Exception {
		SystemDivision bean = (SystemDivision) mainView.tbl.getSelectedRowValueAt(SC.ENTITY);

		SystemDivisionSearchCondition condition = new SystemDivisionSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(bean.getCode());

		List<SystemDivision> list = getSystemDivision(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			showMessage("I00193");
			return null;

		}
		return list.get(0);
	}

	/**
	 * ���������ɊY������V�X�e���敪�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������V�X�e���敪�̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<SystemDivision> getSystemDivision(SystemDivisionSearchCondition condition) throws Exception {

		List<SystemDivision> list = (List<SystemDivision>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w��̃V�X�e���敪���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void deleteSystemDivision(SystemDivision bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// �V�X�e���敪�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlKbn.getValue())) {
			// �V�X�e���敪����͂��Ă��������B
			showMessage(editView, "I00037", "C00980");
			editView.ctrlKbn.requestTextFocus();
			return false;
		}

		// �V�X�e���敪���̂������͂̏ꍇ�G���[ if
		if (Util.isNullOrEmpty(editView.ctrlKbnName.getValue())) {
			// �V�X�e���敪���̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00832");
			editView.ctrlKbnName.requestTextFocus();
			return false;
		}

		// �V�X�e���敪���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlKbnNameS.getValue())) {
			// �V�X�e���敪���̂���͂��Ă��������B"
			showMessage(editView, "I00037", "C00833");
			editView.ctrlKbnNameS.requestTextFocus();
			return false;
		}

		// �V�X�e���敪�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlKbnNameK.getValue())) {
			// �V�X�e���敪�������̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00834");
			editView.ctrlKbnNameK.requestTextFocus();
			return false;
		}

		// �O���V�X�e���敪�����I���̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlExternalKbn.getSelectedItemValue())) {
			// �O���V�X�e���敪����͂��Ă��������B
			showMessage(editView, "I00037", "C01018");
			editView.ctrlExternalKbn.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���̃V�X�e���敪�����݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			SystemDivisionSearchCondition condition = new SystemDivisionSearchCondition();
			condition.setCode(editView.ctrlKbn.getValue());
			condition.setCompanyCode(getCompanyCode());
			List<SystemDivision> list = getSystemDivision(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00980");
				editView.ctrlKbn.requestTextFocus();
				return false;
			}
		}

		return true;
	}

}
