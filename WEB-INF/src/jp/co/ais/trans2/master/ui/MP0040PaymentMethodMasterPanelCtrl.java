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
import jp.co.ais.trans2.model.payment.*;

/**
 * MP0040PaymentMethodMaster - �x�����@�}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MP0040PaymentMethodMasterPanelCtrl extends TController {

	/** �w����� */
	protected MP0040PaymentMethodMasterPanel mainView = null;

	/** �ҏW��� */
	protected MP0040PaymentMethodMasterDialog editView = null;

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
		mainView = new MP0040PaymentMethodMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
		mainView.ctrlPayRefRan.ctrlPayReferenceFrom.name.setEditable(false);
		mainView.ctrlPayRefRan.ctrlPayReferenceTo.name.setEditable(false);

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
			PaymentMethodSearchCondition condition = getSearchCondition();

			// �x���Ώۋ敪���`�F�b�N
			if (!mainView.chkEmployeePayment.isSelected() && !mainView.chkExternalPayment.isSelected()) {
				showMessage(mainView, "I00041", "C01096");
				mainView.chkEmployeePayment.requestFocus();
				return;
			}

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlPayRefRan.requestFocus();
				return;
			}

			// �ꗗ���N���A
			mainView.tbList.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �f�[�^�擾
			List<PaymentMethod> list = getPaymentMethod(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (PaymentMethod smr : list) {
				mainView.tbList.addRow(getRowData(smr));
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
			PaymentMethod bean = getSelected();

			// �߂�l��NULL�̏ꍇ
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
			PaymentMethod bean = getSelected();

			// �߂�l��NULL�̏ꍇ
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
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {
				return;
			}

			// �폜�Ώۂ̃f�[�^�擾
			PaymentMethod bean = getSelected();

			// �߂�l��NULL�̏ꍇ
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

			showMessage(mainView.getParentFrame(), "I00013");
			return;

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
			PaymentMethodSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlPayRefRan.requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			// �f�[�^�����������ꍇ�A�G���[���b�Z�[�W�o��
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// �G�N�Z���^�C�g���Z�b�g
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02350") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		// �ҏW��ʂ𐶐�
		editView = new MP0040PaymentMethodMasterDialog(mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �ҏW��ʂ̃C�x���g��`
	 */
	protected void addEditViewEvent() {
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		editView.ctrlPaymentInternalCode.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					cboPaymentKind_Change();
				}
			}
		});

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {
		try {

			if (!isInputCorrect()) {
				return;
			}

			PaymentMethod bean = getInputtedPaymentMethod();

			switch (mode) {

				case NEW:
				case COPY:

					request(getModelClass(), "entry", bean);

					mainView.tbList.addRow(getRowData(bean));

					setMainButtonEnabled(true);

					break;

				case MODIFY:

					request(getModelClass(), "modify", bean);

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
	 * ����(�Ј�)�E�Ј������E�����E�����E���E�E���̑� �ȊO���R���{�{�b�N�X�őI�������͕s�{�l����
	 */
	protected void cboPaymentKind_Change() {

		boolean isEdit = false;

		Object obj = editView.ctrlPaymentInternalCode.getSelectedItemValue();
		if (!Util.isNullOrEmpty(obj)) {
			PaymentKind kind = (PaymentKind) obj;
			isEdit = kind == PaymentKind.EMP_PAY_CASH || kind == PaymentKind.EMP_PAY_UNPAID
				|| kind == PaymentKind.EMP_PAY_ACCRUED || kind == PaymentKind.EX_PAY_CASH
				|| kind == PaymentKind.EX_PAY_ERASE || kind == PaymentKind.EX_PAY_OFFSET || kind == PaymentKind.OTHER;
		}

		if (isEdit) {
			editView.ctrlItemGroup.ctrlItemReference.setEditable(true);
			editView.ctrlDepartment.setEditable(true);
		} else {
			editView.ctrlItemGroup.ctrlItemReference.setEditable(false);
			editView.ctrlItemGroup.ctrlItemReference.clear();
			editView.ctrlItemGroup.ctrlSubItemReference.setEditable(false);
			editView.ctrlItemGroup.ctrlSubItemReference.clear();
			editView.ctrlItemGroup.ctrlDetailItemReference.setEditable(false);
			editView.ctrlItemGroup.ctrlDetailItemReference.clear();
			editView.ctrlDepartment.setEditable(false);
			editView.ctrlDepartment.clear();
		}

	}

	/**
	 * �ҏW��ʏ�����
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, PaymentMethod bean) {

		this.mode = editMode;

		switch (editMode) {

			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.ctrlStrDate.setValue(editView.ctrlStrDate.getCalendar().getMinimumDate());
				editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());
				cboPaymentKind_Change(); // �����Z�b�g

				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));

					editView.ctrlPayCode.setEditable(false);
					editView.setEditMode();

				} else {

					editView.setTitle(getWord("C01738"));

				}

				editView.ctrlPayCode.setValue(bean.getCode());
				editView.ctrlPayName.setValue(bean.getName());
				editView.ctrlPayNameK.setValue(bean.getNamek());

				// �ȖڃR�[�h
				editView.ctrlItemGroup.ctrlItemReference.setCode(bean.getItemCode());
				// �⏕�R�[�h
				editView.ctrlItemGroup.ctrlSubItemReference.setCode(bean.getSubItemCode());
				// ����R�[�h
				editView.ctrlItemGroup.ctrlDetailItemReference.setCode(bean.getDetailItemCode());
				// �ȖځA�⏕�A����̗���
				editView.ctrlItemGroup.refleshGroupEntity();

				// �v�㕔��
				editView.ctrlDepartment.setCode(bean.getDepartmentCode());
				editView.ctrlDepartment.refleshEntity();

				editView.rdoEmployeePayment.setSelected(bean.isUseEmployeePayment());
				editView.rdoExternalPayment.setSelected(bean.isUseExPayment());

				editView.ctrlPaymentInternalCode.setSelectedItemValue(bean.getPaymentKind());

				editView.ctrlStrDate.setValue(bean.getDateFrom());
				editView.ctrlEndDate.setValue(bean.getDateTo());

				break;
		}

	}

	/**
	 * �ҏW��ʂ̓��͒l�擾
	 * 
	 * @return PaymentMethod
	 */
	protected PaymentMethod getInputtedPaymentMethod() {

		PaymentMethod bean = new PaymentMethod();

		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlPayCode.getValue());
		bean.setName(editView.ctrlPayName.getValue());
		bean.setNamek(editView.ctrlPayNameK.getValue());

		bean.setItemCode(editView.ctrlItemGroup.ctrlItemReference.getCode());

		bean.setSubItemCode(editView.ctrlItemGroup.ctrlSubItemReference.getCode());

		bean.setDetailItemCode(editView.ctrlItemGroup.ctrlDetailItemReference.getCode());

		bean.setDepartmentCode(editView.ctrlDepartment.getCode());

		bean.setPaymentDivision(editView.rdoEmployeePayment.isSelected() ? 0 : 1);
		bean.setPaymentKind((PaymentKind) editView.ctrlPaymentInternalCode.getSelectedItemValue());

		bean.setDateFrom(editView.ctrlStrDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		return bean;
	}

	/**
	 * ���������擾
	 * 
	 * @return PaymentMethodSearchCondition ��������
	 */
	protected PaymentMethodSearchCondition getSearchCondition() {

		PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlPayRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlPayRefRan.getCodeTo());

		// �x���Ώۋ敪�ɂ�錟���`�F�b�N
		if (mainView.chkEmployeePayment.isSelected() && !mainView.chkExternalPayment.isSelected()) {
			condition.setUseEmployeePayment(true);
			condition.setUseExPayment(false);
		} else if (!mainView.chkEmployeePayment.isSelected() && mainView.chkExternalPayment.isSelected()) {
			condition.setUseEmployeePayment(false);
			condition.setUseExPayment(true);
		} else {
			condition.setUseEmployeePayment(false);
			condition.setUseExPayment(false);
		}

		// �L�������ɂ�錟���`�F�b�N
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
	protected List<Object> getRowData(PaymentMethod bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNamek());

		list.add(bean.getItemCode()); // �ȖڃR�[�h

		list.add(bean.getSubItemCode()); // �⏕�ȖڃR�[�h

		list.add(bean.getDetailItemCode()); // ����ȖڃR�[�h

		list.add(bean.getDepartmentCode()); // �v�㕔��R�[�h

		list.add(bean.isUseEmployeePayment() ? getWord("C01119") : getWord("C01124"));

		list.add(getWord(PaymentKind.getPaymentKindName(bean.getPaymentKind())));
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
	protected PaymentMethod getSelected() throws Exception {

		PaymentMethod bean = (PaymentMethod) mainView.tbList
			.getSelectedRowValueAt(MP0040PaymentMethodMasterPanel.SC.ENTITY);

		PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<PaymentMethod> list = getPaymentMethod(condition);

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
	protected List<PaymentMethod> getPaymentMethod(PaymentMethodSearchCondition condition) throws Exception {
		List<PaymentMethod> list = (List<PaymentMethod>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(PaymentMethod bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return PaymentMethodManager.class;
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {

		// �x�����@�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlPayCode.getValue())) {
			showMessage(editView, "I00037", "C00864");
			editView.ctrlPayCode.requestTextFocus();
			return false;
		}

		// �x�����@���̃R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlPayName.getValue())) {
			showMessage(editView, "I00037", "C00865");
			editView.ctrlPayName.requestTextFocus();
			return false;
		}

		// �x�����@�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlPayNameK.getValue())) {
			showMessage(editView, "I00037", "C00866");
			editView.ctrlPayNameK.requestTextFocus();
			return false;
		}

		// �ȖڃR�[�h�������͂̏ꍇ�G���[
		if (editView.ctrlItemGroup.ctrlItemReference.code.isEditable()
			&& Util.isNullOrEmpty(editView.ctrlItemGroup.ctrlItemReference.getCode())) {
			showMessage(editView, "I00037", "C00077");
			editView.ctrlItemGroup.ctrlItemReference.requestTextFocus();
			return false;
		}

		// �⏕�ȖڃR�[�h�������͂̏ꍇ�G���[
		if (editView.ctrlItemGroup.ctrlSubItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlItemGroup.ctrlSubItemReference.getCode())) {
				showMessage(editView, "I00037", "C00488");
				editView.ctrlItemGroup.ctrlSubItemReference.requestTextFocus();
				return false;
			}
		}

		// ����ȖڃR�[�h�������͂̏ꍇ�G���[
		if (editView.ctrlItemGroup.ctrlDetailItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlItemGroup.ctrlDetailItemReference.getCode())) {
				showMessage(editView, "I00037", "C00025");
				editView.ctrlItemGroup.ctrlDetailItemReference.requestTextFocus();
				return false;
			}
		}

		// �v�㕔��R�[�h�������͂̏ꍇ�G���[
		if (editView.ctrlDepartment.code.isEditable() && Util.isNullOrEmpty(editView.ctrlDepartment.getCode())) {
			showMessage(editView, "I00037", "C00571");
			editView.ctrlDepartment.requestTextFocus();
			return false;
		}

		// �x�������R�[�h�̐ݒ�i�ЊO�x�����̏ꍇ�A�����R�[�h<=10���ƃG���[�j
		if (editView.rdoExternalPayment.isSelected()) {
			// �x���������R�[�h��10�ȉ��̂Ƃ��̓G���[
			if (editView.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() < 5) {
				// �x�����b�Z�[�W�\��
				showMessage(editView, "W00208");
				editView.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// �x�������R�[�h�̐ݒ�i�Ј��x�����̏ꍇ�A�����R�[�h>10���ƃG���[�j
		if (editView.rdoEmployeePayment.isSelected()) {
			// �x���������R�[�h��10�ȉ��̂Ƃ��̓G���[
			if (editView.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() > 4) {
				// �x�����b�Z�[�W�\��
				showMessage(editView, "W00209");
				editView.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlStrDate.getValue())) {
			showMessage(editView, "I00037", "COP706");
			editView.ctrlStrDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "COP707");
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N���� <= �I���N�����`�F�b�N
		if ((!Util.isNullOrEmpty(editView.ctrlStrDate.getValue()) && !Util.isNullOrEmpty(editView.ctrlEndDate
			.getValue())) && !Util.isSmallerThenByYMD(editView.ctrlStrDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlStrDate.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h && ����x�����@�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlPayCode.getValue());

			List<PaymentMethod> list = getPaymentMethod(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00233");
				editView.ctrlPayCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}

}