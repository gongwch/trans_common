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
import jp.co.ais.trans2.model.voyage.*;

/**
 * ���q�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class CM0020VoyageMasterPanelCtrl extends TController {

	/** �w����� */
	protected CM0020VoyageMasterPanel mainView = null;

	/** �ҏW��� */
	protected CM0020VoyageMasterDialog editView = null;

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
		mainView = new CM0020VoyageMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
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

		// ���O�C����ЃR�[�h���Z�b�g����
		mainView.ctrlVoyageRange.ctrlVoyageReferenceFrom.setCompanyCode(getCompany().getCode());
		mainView.ctrlVoyageRange.ctrlVoyageReferenceTo.setCompanyCode(getCompany().getCode());

		// ���q�R�[�h�J�n�Ƀt�H�[�J�X
		mainView.ctrlVoyageRange.ctrlVoyageReferenceFrom.code.requestFocus();

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
			if (Util.isSmallerThen(mainView.ctrlVoyageRange.getCodeFrom(), mainView.ctrlVoyageRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlVoyageRange.getFieldFrom().requestFocus();
				return;

			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ���q�����擾
			VoyageSearchCondition condition = getSearchCondition();
			List<Voyage> list = getVoyage(condition);

			// ���������ɊY�����鎟�q��񂪑��݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���q�����ꗗ�ɕ\������
			for (Voyage voyage : list) {
				mainView.tbl.addRow(getRowData(voyage));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̎��q�����擾����
			Voyage voyage = getSelectedVoyage();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̎��q�����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, voyage);

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̎��q���擾����
			Voyage voyage = getSelectedVoyage();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̎��q�����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, voyage);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̎��q�����擾����
			Voyage voyage = getSelectedVoyage();

			// �폜����
			deleteVoyage(voyage);

			// �폜�������q�����ꗗ����폜
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
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �f�[�^���o

			VoyageSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11779") + ".xls");// ���q�}�X�^

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new CM0020VoyageMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode ���샂�[�h�B
	 * @param bean ���q���B�C���A���ʂ̏ꍇ�͓��Y���q����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(@SuppressWarnings("hiding") Mode mode, Voyage bean) {

		this.mode = mode;

		// �V�K�̏ꍇ
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));// �V�K���

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			return;

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// �ҏW
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977"));// �ҏW���
				editView.ctrlCode.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				editView.setTitle(getWord("C01738"));// ���ʉ��
			}

			// ���q���
			editView.ctrlCode.setValue(bean.getCode());
			editView.ctrlName.setValue(bean.getName());
			editView.ctrlNames.setValue(bean.getNames());
			editView.ctrlNamek.setValue(bean.getNamek());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());
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

			// ���͂��ꂽ���q�����擾
			Voyage voyage = getInputedVoyage();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^

				request(getModelClass(), "entry", voyage);
				// �ҏW��ʂ����
				btnClose_Click();

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(voyage));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", voyage);
				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(voyage));

				// �ҏW��ʂ����
				btnClose_Click();

			}

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
	 * �w����ʂœ��͂��ꂽ���q���̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected VoyageSearchCondition getSearchCondition() {

		VoyageSearchCondition condition = createVoyageSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlVoyageRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlVoyageRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * ���f���C���^�[�t�F�[�X��Ԃ�
	 * 
	 * @return ���f���C���^�[�t�F�[�X
	 */
	protected Class getModelClass() {
		return VoyageManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ���q����Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ���q���
	 */
	protected Voyage getInputedVoyage() {

		// ���q���
		Voyage voyage = createVoyage();
		voyage.setCompanyCode(getCompany().getCode());
		voyage.setCode(editView.ctrlCode.getValue());
		voyage.setName(editView.ctrlName.getValue());
		voyage.setNames(editView.ctrlNames.getValue());
		voyage.setNamek(editView.ctrlNamek.getValue());
		voyage.setDateFrom(editView.dtBeginDate.getValue());
		voyage.setDateTo(editView.dtEndDate.getValue());

		return voyage;

	}

	/**
	 * @return ���q���̌�������
	 */
	protected VoyageSearchCondition createVoyageSearchCondition() {
		return new VoyageSearchCondition();
	}

	/**
	 * @return ���q���
	 */
	protected Voyage createVoyage() {
		return new Voyage();
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Voyage bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);
		return list;
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
	 * ���������ɊY�����鎟�q�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����鎟�q���̃��X�g
	 * @throws Exception
	 */
	protected List<Voyage> getVoyage(VoyageSearchCondition condition) throws Exception {

		List<Voyage> list = (List<Voyage>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă��鎟�q�f�[�^��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��鎟�q���
	 * @throws Exception
	 */
	protected Voyage getSelectedVoyage() throws Exception {

		VoyageSearchCondition condition = createVoyageSearchCondition();
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(CM0020VoyageMasterPanel.SC.code));

		List<Voyage> list = getVoyage(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �w��̎��q�����폜����
	 * 
	 * @param voyage ���q���
	 * @throws Exception
	 */
	protected void deleteVoyage(Voyage voyage) throws Exception {

		request(getModelClass(), "delete", voyage);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {
		int tabIndex = 0;
		// -- ��{�^�u --
		// ���q�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C03003");// ���q�R�[�h
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓��ꎟ�q�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			VoyageSearchCondition condition = createVoyageSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<Voyage> list = getVoyage(condition);

			if (list != null && !list.isEmpty()) {
				// �w��̎��q�R�[�h�͊��ɑ��݂��܂�
				showMessage(editView, "I00148", "C03003");
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		// ���q���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C11780");// ���q����
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// ���q���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			showMessage(editView, "I00037", "C11781");// ���q����
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// ���q�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			showMessage(editView, "I00037", "C11782");// ���q��������
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNamek.requestTextFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");// �J�n�N����
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");// �I���N����
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		return true;

	}

}
