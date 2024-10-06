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
 * ����Ȗڃ}�X�^�R���g���[��
 */
public class MG0100DetailItemMasterPanelCtrl extends TController {

	/** ����Ȗڃ}�X�^�������� */
	protected static final DetailItemSearchCondition ItemSearchCondition = null;

	/** �w����� */
	protected MG0100DetailItemMasterPanel mainView;

	/** �ҏW��� */
	protected MG0100DetailItemMasterDialog editView = null;

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
		mainView = new MG0100DetailItemMasterPanel(getCompany());
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

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {
		// �⏕�ȖځA����Ȗڂ��܂ނ��̎擾
		mainView.ctrlSerch.itemReference.getSearchCondition().setSubItem(true);
		mainView.ctrlSerch.subItemReference.getSearchCondition().setDetailItem(true);

		// �⏕�ȖځA����Ȗڂ̎g�p��
		mainView.ctrlSerch.subItemReference.setEditable(false);
		mainView.ctrlSerch.detailItemRange.ctrlDetailItemReferenceFrom.setEditable(false);
		mainView.ctrlSerch.detailItemRange.ctrlDetailItemReferenceTo.setEditable(false);

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
			if (Util.isSmallerThen(mainView.ctrlSerch.detailItemRange.getCodeFrom(),
				mainView.ctrlSerch.detailItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlSerch.detailItemRange.getFieldFrom().requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ����Ȗڏ����擾
			DetailItemSearchCondition condition = getSearchCondition();
			List<Item> list = getDetailItem(condition);

			// ���������ɊY���������Ȗڂ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ����Ȗڏ����ꗗ�ɕ\������
			for (Item detailItem : list) {
				mainView.tbl.addRow(getRowData(detailItem));
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

			// �ҏW�Ώۂ̓���Ȗڂ��擾����
			Item detailItem = getSelectedDetailItem();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̓���Ȗڏ����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, detailItem);

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

			// ���ʑΏۂ̓���Ȗڂ��擾����
			Item detailItem = getSelectedDetailItem();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̓���Ȗڏ����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, detailItem);

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

			// �폜�Ώۂ̓���Ȗڂ��擾����
			Item item = getSelectedDetailItem();

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteDetailItem(item);

			// �폜��������Ȗڂ��ꗗ����폜
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
			DetailItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getDetailItemExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00025") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0100DetailItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

		// ���o���A�W�v�ɃZ�b�g
		editView.ctrlItem.getSearchCondition().setSumItem(true);
		editView.ctrlItem.getSearchCondition().setTitleItem(true);

		editView.ctrlSubItem.getSearchCondition().setSumItem(true);
		editView.ctrlSubItem.getSearchCondition().setTitleItem(true);

		// �⏕�A����Ȗڂ��肩�ǂ����𔻕�
		editView.ctrlItem.getSearchCondition().setSubItem(true);
		editView.ctrlSubItem.getSearchCondition().setDetailItem(true);
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

		// �ȖڑI��
		editView.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !editView.ctrlItem.isValueChanged()) {
					return;
				}

				ctrlItemReference_after();
			}
		});

	}

	/**
	 * [�ȖڑI��]���̏���
	 */
	protected void ctrlItemReference_after() {
		Item entity = editView.ctrlItem.getEntity();
		if (entity != null) {
			editView.ctrlSubItem.clear();
			editView.ctrlSubItem.getSearchCondition().setItemCode(entity.getCode());
			editView.ctrlSubItem.setEditable(true);
		} else {
			editView.ctrlSubItem.clear();
			editView.ctrlSubItem.setEditable(false);
		}
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param bean ����ȖځB�C���A���ʂ̏ꍇ�͓��Y����Ȗڏ���ҏW��ʂɃZ�b�g����B
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
				editView.ctrlSubItem.setEditable(false);

				break;

			case COPY:
			case MODIFY:
				// �ҏW
				if (Mode.MODIFY == mode_) {
					editView.setTitle(getWord("C00977"));
					editView.ctrlItem.setEditable(false);
					editView.ctrlSubItem.setEditable(false);
					editView.ctrlDetailItemCode.setEditable(false);
					editView.setEditMode();
					// ����
				} else {
					editView.setTitle(getWord("C01738"));
				}
				editView.ctrlItem.setCode(bean.getCode());
				editView.ctrlItem.name.setValue(bean.getNames());
				editView.ctrlItem.setEntity(bean);
				editView.ctrlSubItem.setCode(bean.getSubItemCode());
				editView.ctrlSubItem.getSearchCondition().setItemCode(bean.getCode());
				editView.ctrlSubItem.name.setValue(bean.getSubItemNames());
				editView.ctrlDetailItemCode.setValue(bean.getDetailItem().getCode());
				editView.ctrlDetailItemName.setValue(bean.getDetailItemName());
				editView.ctrlDetailItemNames.setValue(bean.getDetailItemNames());
				editView.ctrlDetailItemNamek.setValue(bean.getDetailItemNamek());
				editView.TTaxReference.setCode(bean.getDetailItem().getConsumptionTax().getCode());
				editView.TTaxReference.name.setValue(bean.getDetailItem().getConsumptionTax().getName());
				editView.cmbtricodeflg.setSelectedItemValue(bean.getDetailItem().getClientType());
				editView.chk.chkglflg1.setSelected(bean.getDetailItem().isUseInputCashFlowSlip());
				editView.chk.chkglflg2.setSelected(bean.getDetailItem().isUseOutputCashFlowSlip());
				editView.chk.chkglflg3.setSelected(bean.getDetailItem().isUseTransferSlip());
				editView.chk.chkapflg1.setSelected(bean.getDetailItem().isUseExpenseSettlementSlip());
				editView.chk.chkapflg2.setSelected(bean.getDetailItem().isUsePaymentAppropriateSlip());
				editView.chk.chkarflg1.setSelected(bean.getDetailItem().isUseReceivableAppropriateSlip());
				editView.chk.chkarflg2.setSelected(bean.getDetailItem().isUseReceivableErasingSlip());
				editView.chk.chkfaflg1.setSelected(bean.getDetailItem().isUseAssetsEntrySlip());
				editView.chk.chkfaflg2.setSelected(bean.getDetailItem().isUsePaymentRequestSlip());
				editView.chk.chkempcodeflg.setSelected(bean.getDetailItem().isUseEmployee());
				editView.chk.chkknrflg1.setSelected(bean.getDetailItem().isUseManagement1());
				editView.chk.chkknrflg2.setSelected(bean.getDetailItem().isUseManagement2());
				editView.chk.chkknrflg3.setSelected(bean.getDetailItem().isUseManagement3());
				editView.chk.chkknrflg4.setSelected(bean.getDetailItem().isUseManagement4());
				editView.chk.chkknrflg5.setSelected(bean.getDetailItem().isUseManagement5());
				editView.chk.chkknrflg6.setSelected(bean.getDetailItem().isUseManagement6());
				editView.chk.chkhmflg1.setSelected(bean.getDetailItem().isUseNonAccount1());
				editView.chk.chkhmflg2.setSelected(bean.getDetailItem().isUseNonAccount2());
				editView.chk.chkhmflg3.setSelected(bean.getDetailItem().isUseNonAccount3());
				editView.chk.chkurizeiflg.setSelected(bean.getDetailItem().isUseSalesTaxation());
				editView.chk.chksirzeiflg.setSelected(bean.getDetailItem().isUsePurchaseTaxation());
				editView.chk.chkmcrflg.setSelected(bean.getDetailItem().isUseForeignCurrency());
				editView.chk.chkexcflg.setSelected(bean.getDetailItem().isDoesCurrencyEvaluate());
				editView.dtBeginDate.setValue(bean.getDetailItem().getDateFrom());
				editView.dtEndDate.setValue(bean.getDetailItem().getDateTo());

				// �������t���O
				if (editView.chk.chkOccurDate.isVisible()) {
					editView.chk.chkOccurDate.setSelected(bean.getDetailItem().isUseOccurDate());
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

			// ���͂��ꂽ����Ȗڏ����擾
			Item item = getInputedDetailItem();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entryDetailItem", item);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(item));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modifyDetailItem", item);

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
	 * �ҏW��ʂœ��͂��ꂽ����Ȗڂ�Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ����Ȗ�
	 */
	protected Item getInputedDetailItem() {
		Item item = new Item();
		SubItem subItem = new SubItem();
		DetailItem detailItem = new DetailItem();
		detailItem.setCompanyCode(getCompany().getCode());
		item.setCode(editView.ctrlItem.getCode());
		item.setName(editView.ctrlItem.getName());
		item.setNames(editView.ctrlItem.getNames());
		subItem.setCode(editView.ctrlSubItem.getCode());
		subItem.setName(editView.ctrlSubItem.getName());
		subItem.setNames(editView.ctrlSubItem.getNames());

		item.setSubItem(subItem);

		detailItem.setCode(editView.ctrlDetailItemCode.getValue());
		detailItem.setName(editView.ctrlDetailItemName.getValue());
		detailItem.setNames(editView.ctrlDetailItemNames.getValue());
		detailItem.setNamek(editView.ctrlDetailItemNamek.getValue());
		ConsumptionTax consumptionTax = new ConsumptionTax();
		consumptionTax.setCode(editView.TTaxReference.getCode());
		detailItem.setConsumptionTax(consumptionTax);
		detailItem.setClientType(getCustomerType());
		detailItem.setUseInputCashFlowSlip(editView.chk.chkglflg1.isSelected());
		detailItem.setUseOutputCashFlowSlip(editView.chk.chkglflg2.isSelected());
		detailItem.setUseTransferSlip(editView.chk.chkglflg3.isSelected());
		detailItem.setUseExpenseSettlementSlip(editView.chk.chkapflg1.isSelected());
		detailItem.setUsePaymentAppropriateSlip(editView.chk.chkapflg2.isSelected());
		detailItem.setUseReceivableAppropriateSlip(editView.chk.chkarflg1.isSelected());
		detailItem.setUseReceivableErasingSlip(editView.chk.chkarflg2.isSelected());
		detailItem.setUseAssetsEntrySlip(editView.chk.chkfaflg1.isSelected());
		detailItem.setUsePaymentRequestSlip(editView.chk.chkfaflg2.isSelected());
		detailItem.setUseEmployee(editView.chk.chkempcodeflg.isSelected());
		detailItem.setUseManagement1(editView.chk.chkknrflg1.isSelected());
		detailItem.setUseManagement2(editView.chk.chkknrflg2.isSelected());
		detailItem.setUseManagement3(editView.chk.chkknrflg3.isSelected());
		detailItem.setUseManagement4(editView.chk.chkknrflg4.isSelected());
		detailItem.setUseManagement5(editView.chk.chkknrflg5.isSelected());
		detailItem.setUseManagement6(editView.chk.chkknrflg6.isSelected());
		detailItem.setUseNonAccount1(editView.chk.chkhmflg1.isSelected());
		detailItem.setUseNonAccount2(editView.chk.chkhmflg2.isSelected());
		detailItem.setUseNonAccount3(editView.chk.chkhmflg3.isSelected());
		detailItem.setUseSalesTaxation(editView.chk.chkurizeiflg.isSelected());
		detailItem.setUsePurchaseTaxation(editView.chk.chksirzeiflg.isSelected());
		detailItem.setUseForeignCurrency(editView.chk.chkmcrflg.isSelected());
		detailItem.setDoesCurrencyEvaluate(editView.chk.chkexcflg.isSelected());
		detailItem.setDateFrom(editView.dtBeginDate.getValue());
		detailItem.setDateTo(editView.dtEndDate.getValue());

		// �������t���O
		if (editView.chk.chkOccurDate.isVisible()) {
			detailItem.setUseOccurDate(editView.chk.chkOccurDate.isSelected());
		} else {
			detailItem.setUseOccurDate(true);
		}

		item.getSubItem().setDetailItem(detailItem);

		return item;

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
	 * ����Ȗڏ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean ����Ȗڏ��
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ����Ȗڏ��
	 */
	protected List<Object> getRowData(Item bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode()); // �ȖڃR�[�h
		list.add(bean.getNames()); // �Ȗږ���
		list.add(bean.getSubItemCode()); // �⏕�ȖڃR�[�h
		list.add(bean.getSubItemNames()); // �⏕�Ȗږ���
		list.add(bean.getDetailItem().getCode()); // ����ȖڃR�[�h
		list.add(bean.getDetailItemName()); // ����Ȗږ���
		list.add(bean.getDetailItemNames()); // ����Ȗڗ���
		list.add(bean.getDetailItemNamek()); // ����Ȗڌ�������
		list.add(bean.getDetailItem().getConsumptionTax().getCode()); // ����ŃR�[�h
		list.add(getBoo(bean.getDetailItem().isUseInputCashFlowSlip())); // �����`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseOutputCashFlowSlip())); // �o���`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseTransferSlip())); // �U�֓`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseExpenseSettlementSlip())); // �o��Z�`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUsePaymentAppropriateSlip())); // ���v��`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseReceivableAppropriateSlip())); // ���v��`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseReceivableErasingSlip())); // �������`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseAssetsEntrySlip())); // ���Y�v��`�[���̓t���O
		list.add(getBoo(bean.getDetailItem().isUsePaymentRequestSlip())); // �x���˗��`�[���̓t���O
		list.add(getWord(getName(bean.getDetailItem().getClientType()))); // �������̓t���O
		list.add(getBoo(bean.getDetailItem().isUseEmployee())); // �Ј����̓t���O
		list.add(getBoo(bean.getDetailItem().isUseManagement1())); // �Ǘ��P���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseManagement2())); // �Ǘ�2���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseManagement3())); // �Ǘ�3���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseManagement4())); // �Ǘ�4���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseManagement5())); // �Ǘ�5���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseManagement6())); // �Ǘ�6���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseNonAccount1())); // ���v�P���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseNonAccount2())); // ���v2���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseNonAccount3())); // ���v3���̓t���O
		list.add(getBoo(bean.getDetailItem().isUseSalesTaxation())); // ����ېœ��̓t���O
		list.add(getBoo(bean.getDetailItem().isUsePurchaseTaxation())); // �d���ېœ��̓t���O
		list.add(getBoo(bean.getDetailItem().isUseForeignCurrency())); // ���ʉݓ��̓t���O
		list.add(getBoo2(bean.getDetailItem().isDoesCurrencyEvaluate())); // �]���֑Ώۃt���O
		list.add(getBoo(bean.getDetailItem().isUseOccurDate())); // �������t���O
		list.add(DateUtil.toYMDString(bean.getDetailItem().getDateFrom())); // �J�n�N����
		list.add(DateUtil.toYMDString(bean.getDetailItem().getDateTo())); // �I���N����

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
	 * ���������ɊY���������Ȗڂ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY���������Ȗڂ̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Item> getDetailItem(DetailItemSearchCondition condition) throws Exception {

		List<Item> list = (List<Item>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w����ʂœ��͂��ꂽ����Ȗڂ̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DetailItemSearchCondition getSearchCondition() {

		DetailItemSearchCondition condition = new DetailItemSearchCondition();
		condition.setSubItem(true);
		condition.setDetailItem(true);
		condition.setCompanyCode(getCompanyCode());
		condition.setItemCode(mainView.ctrlSerch.itemReference.getCode());
		condition.setSubItemCode(mainView.ctrlSerch.subItemReference.getCode());
		condition.setCodeFrom(mainView.ctrlSerch.detailItemRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSerch.detailItemRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		return condition;
	}

	/**
	 * �ꗗ�őI������Ă������Ȗڂ�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă������Ȗ�
	 * @throws Exception
	 */
	protected Item getSelectedDetailItem() throws Exception {
		DetailItemSearchCondition condition = new DetailItemSearchCondition();
		condition.setSumItem(true);
		condition.setTitleItem(true);
		condition.setCompanyCode(getCompany().getCode());
		condition.setItemCode((String) mainView.tbl.getSelectedRowValueAt(MG0100DetailItemMasterPanel.SC.kmkcode));
		condition.setSubItemCode((String) mainView.tbl.getSelectedRowValueAt(MG0100DetailItemMasterPanel.SC.hkmcode));
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0100DetailItemMasterPanel.SC.code));
		List<Item> list = getDetailItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * �w��̓���Ȗڂ��폜����
	 * 
	 * @param item
	 * @throws TException
	 */
	protected void deleteDetailItem(Item item) throws TException {
		request(getModelClass(), "deleteDetailItem", item);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �ȖڃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItem.getCode())) {
			showMessage(editView, "I00037", "C00572");
			editView.ctrlItem.requestTextFocus();
			return false;
		}

		// �⏕�ȖڃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSubItem.getCode())) {
			showMessage(editView, "I00037", "C00602");
			editView.ctrlSubItem.requestTextFocus();
			return false;
		}

		// ����ȖڃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDetailItemCode.getValue())) {
			showMessage(editView, "I00037", "C00603");
			editView.ctrlDetailItemCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓������Ȗڂ����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			DetailItemSearchCondition condition = new DetailItemSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlDetailItemCode.getValue());
			condition.setItemCode(editView.ctrlItem.getCode()); // �ȖڃR�[�h������
			condition.setSubItemCode(editView.ctrlSubItem.getCode()); // �⏕�ȖڃR�[�h������

			List<Item> list = getDetailItem(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00025");
				editView.ctrlDetailItemCode.requestTextFocus();
				return false;
			}
		}

		// ���󖼏̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDetailItemName.getValue())) {
			showMessage(editView, "I00037", "C00877");
			editView.ctrlDetailItemName.requestTextFocus();
			return false;
		}

		// ���󗪏̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDetailItemNames.getValue())) {
			showMessage(editView, "I00037", "C00878");
			editView.ctrlDetailItemNames.requestTextFocus();
			return false;
		}

		// ���󌟍����̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDetailItemNamek.getValue())) {
			showMessage(editView, "I00037", "C00879");
			editView.ctrlDetailItemNamek.requestTextFocus();
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

		// ���Ǘ��Ȗڂ̏ꍇ�A�����敪�͐ݒ�K�{
		if (getItem() != null && getItem().getApType() == APType.DEBIT) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.CUSTOMER == getCustomerType()) {
				// ���Ǘ��Ȗڂ�ݒ肷��ꍇ�A�����敪�ɂ͎d����������͓��Ӑ�&�d�����ݒ肵�Ă��������B
				showMessage(editView, "I00613");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}
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
		Item bean = editView.ctrlItem.getEntity();
		if (bean == null) {
			editView.ctrlItem.refleshEntity();
			bean = editView.ctrlItem.getEntity();
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
