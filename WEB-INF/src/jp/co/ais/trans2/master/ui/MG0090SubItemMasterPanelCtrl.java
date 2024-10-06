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
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �⏕�Ȗڃ}�X�^�R���g���[��
 */
public class MG0090SubItemMasterPanelCtrl extends TController {

	/** �⏕�Ȗڃ}�X�^�������� */
	protected static final SubItemSearchCondition ItemSearchCondition = null;

	/** �w����� */
	protected MG0090SubItemMasterPanel mainView;

	/** �ҏW��� */
	protected MG0090SubItemMasterDialog editView = null;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
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
		mainView = new MG0090SubItemMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃{�^���������̃C�x���g
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

		// �ȖڃR�[�h�������ɓ��͂��ꂽ�ꍇ�ɓ��͉ƂȂ�B
		mainView.ctrlSerch.itemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !mainView.ctrlSerch.itemReference.isValueChanged()) {
					return;
				}

				itemReference_after();
			}

			private void itemReference_after() {
				Item item = mainView.ctrlSerch.itemReference.getEntity();

				if (item != null) {
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.setEditable(true);
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.setEditable(true);
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.getSearchCondition().setItemCode(
						item.getCode());
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.getSearchCondition().setItemCode(
						item.getCode());
				} else {
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.setEditable(false);
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.setEditable(false);
				}
			}
		});
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {
		// ���o���A�W�v�ɃZ�b�g
		mainView.ctrlSerch.itemReference.getSearchCondition().setSumItem(true);
		mainView.ctrlSerch.itemReference.getSearchCondition().setTitleItem(true);
		// �⏕�Ȗڂ��肩�ǂ����𔻕�
		mainView.ctrlSerch.itemReference.getSearchCondition().setSubItem(true);

		mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.setEditable(false);
		mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.setEditable(false);
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
			if (Util.isSmallerThen(mainView.ctrlSerch.subItemRange.getCodeFrom(),
				mainView.ctrlSerch.subItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlSerch.subItemRange.getFieldFrom().requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �⏕�Ȗڏ����擾
			SubItemSearchCondition condition = getSearchCondition();
			List<Item> list = getSubItem(condition);

			// ���������ɊY������⏕�Ȗڂ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �⏕�Ȗڏ����ꗗ�ɕ\������
			for (Item subItem : list) {
				mainView.tbl.addRow(getRowData(subItem));
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

			// �ҏW�Ώۂ̕⏕�Ȗڂ��擾����
			Item subItem = getSelectedSubItem();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̕⏕�Ȗڏ����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, subItem);

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

			// ���ʑΏۂ̕⏕�Ȗڂ��擾����
			Item subItem = getSelectedSubItem();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̕⏕�Ȗڏ����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, subItem);

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

			// �폜�Ώۂ̕⏕�Ȗڂ��擾����
			Item item = getSelectedSubItem();

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteSubItem(item);

			// �폜�����⏕�Ȗڂ��ꗗ����폜
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

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �f�[�^���o
			SubItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getSubItemExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00488") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0090SubItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

		// ���o���A�W�v�ɃZ�b�g
		editView.itemReference.getSearchCondition().setSumItem(true);
		editView.itemReference.getSearchCondition().setTitleItem(true);

		// �⏕�Ȗڂ��肩�ǂ����𔻕�
		editView.itemReference.getSearchCondition().setSubItem(true);
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
	 * @param bean �⏕�ȖځB�C���A���ʂ̏ꍇ�͓��Y�⏕�Ȗڏ���ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Item bean) {

		this.mode = mode_;
		// �V�K
		switch (mode) {
		// �V�K
			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
				editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

				break;

			case COPY:
			case MODIFY:
				// �ҏW
				if (Mode.MODIFY == mode_) {
					editView.setTitle(getWord("C00977"));
					editView.itemReference.setEditable(false);
					editView.ctrlSubItemCode.setEditable(false);
					editView.setEditMode();
					// ����
				} else {
					editView.setTitle(getWord("C01738"));
				}
				editView.itemReference.setCode(bean.getCode());
				editView.itemReference.name.setValue(bean.getNames());
				editView.itemReference.setEntity(bean);
				editView.ctrlSubItemCode.setValue(bean.getSubItem().getCode());
				editView.ctrlSubItemName.setValue(bean.getSubItemName());
				editView.ctrlSubItemNames.setValue(bean.getSubItemNames());
				editView.ctrlSubItemNamek.setValue(bean.getSubItemNamek());

				editView.ctrlFixDepartment.setCode(bean.getSubItem().getFixedDepartmentCode());
				editView.ctrlFixDepartment.name.setValue(bean.getSubItem().getFixedDepartmentName());

				editView.TTaxReference.setCode(bean.getSubItem().getConsumptionTax().getCode());
				editView.TTaxReference.name.setValue(bean.getSubItem().getConsumptionTax().getName());
				if (bean.getSubItem().hasDetailItem() == true) {
					editView.rdoYes.setSelected(true);
				} else {
					editView.rdoNo.setSelected(true);
				}
				editView.cmbtricodeflg.setSelectedItemValue(bean.getSubItem().getClientType());
				editView.chk.chkglflg1.setSelected(bean.getSubItem().isUseInputCashFlowSlip());
				editView.chk.chkglflg2.setSelected(bean.getSubItem().isUseOutputCashFlowSlip());
				editView.chk.chkglflg3.setSelected(bean.getSubItem().isUseTransferSlip());
				editView.chk.chkapflg1.setSelected(bean.getSubItem().isUseExpenseSettlementSlip());
				editView.chk.chkapflg2.setSelected(bean.getSubItem().isUsePaymentAppropriateSlip());
				editView.chk.chkarflg1.setSelected(bean.getSubItem().isUseReceivableAppropriateSlip());
				editView.chk.chkarflg2.setSelected(bean.getSubItem().isUseReceivableErasingSlip());
				editView.chk.chkfaflg1.setSelected(bean.getSubItem().isUseAssetsEntrySlip());
				editView.chk.chkfaflg2.setSelected(bean.getSubItem().isUsePaymentRequestSlip());
				editView.chk.chkempcodeflg.setSelected(bean.getSubItem().isUseEmployee());
				editView.chk.chkknrflg1.setSelected(bean.getSubItem().isUseManagement1());
				editView.chk.chkknrflg2.setSelected(bean.getSubItem().isUseManagement2());
				editView.chk.chkknrflg3.setSelected(bean.getSubItem().isUseManagement3());
				editView.chk.chkknrflg4.setSelected(bean.getSubItem().isUseManagement4());
				editView.chk.chkknrflg5.setSelected(bean.getSubItem().isUseManagement5());
				editView.chk.chkknrflg6.setSelected(bean.getSubItem().isUseManagement6());
				editView.chk.chkhmflg1.setSelected(bean.getSubItem().isUseNonAccount1());
				editView.chk.chkhmflg2.setSelected(bean.getSubItem().isUseNonAccount2());
				editView.chk.chkhmflg3.setSelected(bean.getSubItem().isUseNonAccount3());
				editView.chk.chkurizeiflg.setSelected(bean.getSubItem().isUseSalesTaxation());
				editView.chk.chksirzeiflg.setSelected(bean.getSubItem().isUsePurchaseTaxation());
				editView.chk.chkmcrflg.setSelected(bean.getSubItem().isUseForeignCurrency());
				editView.chk.chkexcflg.setSelected(bean.getSubItem().isDoesCurrencyEvaluate());
				editView.dtBeginDate.setValue(bean.getSubItem().getDateFrom());
				editView.dtEndDate.setValue(bean.getSubItem().getDateTo());

				// �������t���O
				if (editView.chk.chkOccurDate.isVisible()) {
					editView.chk.chkOccurDate.setSelected(bean.getSubItem().isUseOccurDate());
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

			// ���͂��ꂽ�⏕�Ȗڏ����擾
			Item item = getInputedSubItem();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entrySubItem", item);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(item));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modifySubItem", item);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(item));

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
	 * @return ItemManager
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�⏕�Ȗڂ�Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�⏕�Ȗ�
	 */
	protected Item getInputedSubItem() {
		Item item = createItem();
		SubItem subItem = createSubItem();
		subItem.setCompanyCode(getCompany().getCode());
		item.setCode(editView.itemReference.getCode());
		item.setName(editView.itemReference.getName());
		item.setNames(editView.itemReference.getNames());
		subItem.setCode(editView.ctrlSubItemCode.getValue());
		subItem.setName(editView.ctrlSubItemName.getValue());
		subItem.setNames(editView.ctrlSubItemNames.getValue());
		subItem.setNamek(editView.ctrlSubItemNamek.getValue());

		subItem.setFixedDepartmentCode(editView.ctrlFixDepartment.getCode());

		ConsumptionTax consumptionTax = createConsumptionTax();
		consumptionTax.setCode(editView.TTaxReference.getCode());
		subItem.setConsumptionTax(consumptionTax);
		if (editView.rdoNo.isSelected()) {
			subItem.setDetailItem(false);
		} else {
			subItem.setDetailItem(true);
		}
		subItem.setClientType(getCustomerType());
		subItem.setUseInputCashFlowSlip(editView.chk.chkglflg1.isSelected());
		subItem.setUseOutputCashFlowSlip(editView.chk.chkglflg2.isSelected());
		subItem.setUseTransferSlip(editView.chk.chkglflg3.isSelected());
		subItem.setUseExpenseSettlementSlip(editView.chk.chkapflg1.isSelected());
		subItem.setUsePaymentAppropriateSlip(editView.chk.chkapflg2.isSelected());
		subItem.setUseReceivableAppropriateSlip(editView.chk.chkarflg1.isSelected());
		subItem.setUseReceivableErasingSlip(editView.chk.chkarflg2.isSelected());
		subItem.setUseAssetsEntrySlip(editView.chk.chkfaflg1.isSelected());
		subItem.setUsePaymentRequestSlip(editView.chk.chkfaflg2.isSelected());
		subItem.setUseEmployee(editView.chk.chkempcodeflg.isSelected());
		subItem.setUseManagement1(editView.chk.chkknrflg1.isSelected());
		subItem.setUseManagement2(editView.chk.chkknrflg2.isSelected());
		subItem.setUseManagement3(editView.chk.chkknrflg3.isSelected());
		subItem.setUseManagement4(editView.chk.chkknrflg4.isSelected());
		subItem.setUseManagement5(editView.chk.chkknrflg5.isSelected());
		subItem.setUseManagement6(editView.chk.chkknrflg6.isSelected());
		subItem.setUseNonAccount1(editView.chk.chkhmflg1.isSelected());
		subItem.setUseNonAccount2(editView.chk.chkhmflg2.isSelected());
		subItem.setUseNonAccount3(editView.chk.chkhmflg3.isSelected());
		subItem.setUseSalesTaxation(editView.chk.chkurizeiflg.isSelected());
		subItem.setUsePurchaseTaxation(editView.chk.chksirzeiflg.isSelected());
		subItem.setUseForeignCurrency(editView.chk.chkmcrflg.isSelected());
		subItem.setDoesCurrencyEvaluate(editView.chk.chkexcflg.isSelected());
		subItem.setDateFrom(editView.dtBeginDate.getValue());
		subItem.setDateTo(editView.dtEndDate.getValue());

		// �������t���O
		if (editView.chk.chkOccurDate.isVisible()) {
			subItem.setUseOccurDate(editView.chk.chkOccurDate.isSelected());
		} else {
			subItem.setUseOccurDate(true);
		}

		item.setSubItem(subItem);

		return item;

	}

	/**
	 * @return �Ȗ�
	 */
	protected Item createItem() {
		return new Item();
	}

	/**
	 * @return �⏕�Ȗ�
	 */
	protected SubItem createSubItem() {
		return new SubItem();
	}

	/**
	 * @return �����
	 */
	protected ConsumptionTax createConsumptionTax() {
		return new ConsumptionTax();
	}

	/**
	 * �������̓t���O��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected CustomerType getCustomerType() {
		CustomerType selectedData = (CustomerType) editView.cmbtricodeflg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * �⏕�Ȗڏ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �⏕�Ȗڏ��
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�⏕�Ȗڏ��
	 */
	protected List<Object> getRowData(Item bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode()); // �ȖڃR�[�h
		list.add(bean.getNames()); // �Ȗږ���
		list.add(bean.getSubItem().getCode()); // �⏕�ȖڃR�[�h
		list.add(bean.getSubItemName()); // �⏕�Ȗږ���
		list.add(bean.getSubItemNames()); // �⏕�Ȗڗ���
		list.add(bean.getSubItemNamek()); // �⏕�Ȗڌ�������
		list.add(getUtiName(bean.getSubItem().hasDetailItem()));// ����敪
		list.add(bean.getSubItem().getFixedDepartmentCode()); // �Œ蕔�庰��
		list.add(bean.getSubItem().getConsumptionTax().getCode()); // ����ŃR�[�h
		list.add(getBoo(bean.getSubItem().isUseInputCashFlowSlip())); // �����`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUseOutputCashFlowSlip())); // �o���`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUseTransferSlip())); // �U�֓`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUseExpenseSettlementSlip())); // �o��Z�`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUsePaymentAppropriateSlip())); // ���v��`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUseReceivableAppropriateSlip())); // ���v��`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUseReceivableErasingSlip())); // �������`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUseAssetsEntrySlip())); // ���Y�v��`�[���̓t���O
		list.add(getBoo(bean.getSubItem().isUsePaymentRequestSlip())); // �x���˗��`�[���̓t���O
		list.add(getWord(getName(bean.getSubItem().getClientType()))); // �������̓t���O
		list.add(getBoo(bean.getSubItem().isUseEmployee())); // �Ј����̓t���O
		list.add(getBoo(bean.getSubItem().isUseManagement1())); // �Ǘ��P���̓t���O
		list.add(getBoo(bean.getSubItem().isUseManagement2())); // �Ǘ�2���̓t���O
		list.add(getBoo(bean.getSubItem().isUseManagement3())); // �Ǘ�3���̓t���O
		list.add(getBoo(bean.getSubItem().isUseManagement4())); // �Ǘ�4���̓t���O
		list.add(getBoo(bean.getSubItem().isUseManagement5())); // �Ǘ�5���̓t���O
		list.add(getBoo(bean.getSubItem().isUseManagement6())); // �Ǘ�6���̓t���O
		list.add(getBoo(bean.getSubItem().isUseNonAccount1())); // ���v�P���̓t���O
		list.add(getBoo(bean.getSubItem().isUseNonAccount2())); // ���v2���̓t���O
		list.add(getBoo(bean.getSubItem().isUseNonAccount3())); // ���v3���̓t���O
		list.add(getBoo(bean.getSubItem().isUseSalesTaxation())); // ����ېœ��̓t���O
		list.add(getBoo(bean.getSubItem().isUsePurchaseTaxation())); // �d���ېœ��̓t���O
		list.add(getBoo(bean.getSubItem().isUseForeignCurrency())); // ���ʉݓ��̓t���O
		list.add(getBoo2(bean.getSubItem().isDoesCurrencyEvaluate())); // �]���֑Ώۃt���O
		list.add(getBoo(bean.getSubItem().isUseOccurDate())); // �������t���O
		list.add(DateUtil.toYMDString(bean.getSubItem().getDateFrom())); // �J�n�N����
		list.add(DateUtil.toYMDString(bean.getSubItem().getDateTo())); // �I���N����

		return list;
	}

	/**
	 * BOOLEAN��String�ł�Ԃ��B�Ǘ��Ȗڂ������t���O�̕\���p�B
	 * 
	 * @param castString
	 * @return string
	 */
	public String getBoo(boolean castString) {

		if (castString) {
			return getWord("C01276");// ���͉�
		} else {
			return getWord("C01279");// ���͕s��

		}
	}

	/**
	 * BOOLEAN��String�ł�Ԃ��B�Ǘ��Ȗڕ\���p�B
	 * 
	 * @param castStringKnr
	 * @return string
	 */
	public String getBoo1(boolean castStringKnr) {

		if (castStringKnr) {
			return getWord("C02371");// ���͕K�{
		} else {
			return getWord("C01279");// ���͕s��

		}
	}

	/**
	 * BOOLEAN��String�ł�Ԃ��B�]���֕\���p�B
	 * 
	 * @param castStringExc
	 * @return string
	 */
	public String getBoo2(boolean castStringExc) {

		if (castStringExc) {
			return getWord("C02100");// ����
		} else {
			return getWord("C02099");// ���Ȃ�

		}
	}

	/**
	 * @param customerType
	 * @return ���Ӑ�敪����
	 */
	public static String getName(CustomerType customerType) {

		if (customerType == null) {
			return "";
		}

		switch (customerType) {
			case NONE:
				return "C01279";// ���͕s��
			case CUSTOMER:
				return "C00401";// ���Ӑ�
			case VENDOR:
				return "C00203";// �d����
			case BOTH:
				return "C02122";// ���Ӑ恕�d����
			default:
				return "";
		}

	}

	/**
	 * @param hasDetailItem
	 * @return ����
	 */
	public String getUtiName(boolean hasDetailItem) {

		if (hasDetailItem) {
			return getWord("C02156");// �L
		} else {
			return getWord("C02155");// ��
		}
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
	 * ���������ɊY������⏕�Ȗڂ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������⏕�Ȗڂ̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Item> getSubItem(SubItemSearchCondition condition) throws Exception {

		List<Item> list = (List<Item>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w����ʂœ��͂��ꂽ�⏕�Ȗڂ̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected SubItemSearchCondition getSearchCondition() {

		SubItemSearchCondition condition = new SubItemSearchCondition();
		condition.setSumItem(true);
		condition.setTitleItem(true);
		condition.setCompanyCode(getCompanyCode());
		condition.setItemCode(mainView.ctrlSerch.itemReference.getCode());
		condition.setCodeFrom(mainView.ctrlSerch.subItemRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSerch.subItemRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		return condition;
	}

	/**
	 * �ꗗ�őI������Ă���⏕�Ȗڂ�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���⏕�Ȗ�
	 * @throws Exception
	 */
	protected Item getSelectedSubItem() throws Exception {
		SubItemSearchCondition condition = new SubItemSearchCondition();
		condition.setSumItem(true);
		condition.setTitleItem(true);
		condition.setCompanyCode(getCompany().getCode());
		condition.setItemCode((String) mainView.tbl.getSelectedRowValueAt(MG0090SubItemMasterPanel.SC.kmkcode));
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0090SubItemMasterPanel.SC.code));
		List<Item> list = getSubItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * �w��̕⏕�Ȗڂ��폜����
	 * 
	 * @param item
	 * @throws TException
	 */
	protected void deleteSubItem(Item item) throws TException {
		request(getModelClass(), "deleteSubItem", item);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �ȖڃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.itemReference.getCode())) {
			showMessage(editView, "I00037", "C00572");
			editView.itemReference.requestTextFocus();
			return false;
		}

		// �⏕�ȖڃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSubItemCode.getValue())) {
			showMessage(editView, "I00037", "C00602");
			editView.ctrlSubItemCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���⏕�Ȗڂ����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			SubItemSearchCondition condition = new SubItemSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlSubItemCode.getValue());
			condition.setItemCode(editView.itemReference.getCode()); // �ȖڃR�[�h������

			List<Item> list = getSubItem(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00488");
				editView.ctrlSubItemCode.requestTextFocus();
				return false;
			}
		}

		// �⏕���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSubItemName.getValue())) {
			showMessage(editView, "I00037", "C00934");
			editView.ctrlSubItemName.requestTextFocus();
			return false;
		}

		// �⏕���̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSubItemNames.getValue())) {
			showMessage(editView, "I00037", "C00933");
			editView.ctrlSubItemNames.requestTextFocus();
			return false;
		}

		// �⏕�������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSubItemNamek.getValue())) {
			showMessage(editView, "I00037", "C00935");
			editView.ctrlSubItemNamek.requestTextFocus();
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

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// ���Ǘ��Ȗڂ̏ꍇ�A�����敪�͐ݒ�
		if (getItem() != null && getItem().getApType() == APType.DEBIT) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.CUSTOMER == getCustomerType()) {
				// ���Ǘ��Ȗڂ�ݒ肷��ꍇ�A�����敪�ɂ͎d����������͓��Ӑ�&�d�����ݒ肵�Ă��������B
				showMessage(editView, "I00613");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}
		// ���Ǘ��Ȗڂ̏ꍇ�A�����̓��͕͂K�{
		if (ARType.NOMAl != getItem().getArType()) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.VENDOR == getCustomerType()) {
				// ���Ǘ��Ȗڂ�ݒ肷��ꍇ�A�����敪�ɂ͓��Ӑ�������͓��Ӑ�&�d�����ݒ肵�Ă��������B
				showMessage(editView, "I00614");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}

		if (!Util.isNullOrEmpty(editView.TTaxReference.getCode())) {
			// ����ŏ����擾
			ConsumptionTax tax = getConsumptionTax();

			// ����ŃR�[�h�ƉېŃt���O�̊֘A�`�F�b�N
			if (tax.getTaxType() == TaxType.SALES && !editView.chk.chkurizeiflg.isSelected()
				|| tax.getTaxType() == TaxType.PURCHAESE && !editView.chk.chksirzeiflg.isSelected()) {
				showMessage(editView, "W00220");
				return false;
			}
		}
		return true;
	}

	/**
	 * �ҏW��ʓ����̉Ȗڏ����擾
	 * 
	 * @return �Ȗڏ��
	 */
	protected Item getItem() {
		Item bean = editView.itemReference.getEntity();
		if (bean == null) {
			editView.itemReference.refleshEntity();
			bean = editView.itemReference.getEntity();
		}
		return bean;
	}

	/**
	 * ����ŏ����擾
	 * 
	 * @return tax ����ŏ��
	 * @throws Exception
	 */
	protected ConsumptionTax getConsumptionTax() throws Exception {

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.TTaxReference.getCode());

		List<ConsumptionTax> taxList = (List<ConsumptionTax>) request(ConsumptionTaxManager.class, "get", condition);

		return taxList.get(0);

	}
}
