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
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �Ȗڃ}�X�^�R���g���[��
 */
public class MG0080ItemMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0080ItemMasterPanel mainView;

	/** �ҏW��� */
	protected MG0080ItemMasterDialog editView = null;

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

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
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
	 * @see jp.co.ais.trans2.common.client.TController#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0080ItemMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * �w����ʃ{�^���������C�x���g
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
	 * �ҏW��ʏW�v�敪���W�I�{�^���������̃C�x���g
	 */
	protected void addSubViewEvent() {
		// [����]�{�^������
		editView.rdoImput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (editView.rdoImput.isSelected()) {
					btnImput_Click();
				}
			}
		});

		// [�W�v]�{�^������
		editView.rdoSum.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSum_Click();
			}
		});

		// [���o]�{�^������
		editView.rdoTitle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSum_Click();
			}
		});

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {
		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

		// ���o���A�W�v�ɃZ�b�g
		mainView.itemRange.ctrlItemReferenceFrom.getSearchCondition().setSumItem(true);
		mainView.itemRange.ctrlItemReferenceFrom.getSearchCondition().setTitleItem(true);
		mainView.itemRange.ctrlItemReferenceTo.getSearchCondition().setSumItem(true);
		mainView.itemRange.ctrlItemReferenceTo.getSearchCondition().setTitleItem(true);

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
			if (Util.isSmallerThen(mainView.itemRange.getCodeFrom(), mainView.itemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.itemRange.getFieldFrom().requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �Ȗڏ����擾
			ItemSearchCondition condition = getSearchCondition();
			List<Item> list = getItem(condition);

			// ���������ɊY������Ȗڂ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �Ȗڏ����ꗗ�ɕ\������
			for (Item item : list) {
				mainView.tbl.addRow(getRowData(item));
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

			// �ҏW�Ώۂ̉Ȗڂ��擾����
			Item item = getSelectedItem();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̉Ȗڏ����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, item);

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

			// ���ʑΏۂ̉Ȗڂ��擾����
			Item item = getSelectedItem();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̉Ȗڏ����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, item);

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

			// �폜�Ώۂ̉Ȗڂ��擾����
			Item item = getSelectedItem();

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜����
			deleteItem(item);

			// �폜�����Ȗڂ��ꗗ����폜
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
			ItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02342") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ҏW���[�W�v][���o]�{�^������
	 */
	protected void btnSum_Click() {

		editView.comHjokbn.setComboEnabled(false);
		editView.comHjokbn.setSelectedIndex(0);
		editView.ctrlFixDepartment.setEditable(false);
		editView.ctrlFixDepartment.clear();
		editView.ctrlTax.setEditable(false);
		editView.ctrlTax.clear();
		editView.cmbcntgl.setComboEnabled(false);
		editView.cmbcntgl.setSelectedIndex(0);
		editView.cmbcntap.setComboEnabled(false);
		editView.cmbcntap.setSelectedIndex(0);
		editView.cmbcntar.setComboEnabled(false);
		editView.cmbcntar.setSelectedIndex(0);
		editView.cmbcntbg.setComboEnabled(false);
		editView.cmbcntbg.setSelectedIndex(0);
		editView.cmbtricodeflg.setComboEnabled(false);
		editView.cmbtricodeflg.setSelectedIndex(0);
		editView.cmbcntsousai.setComboEnabled(false);
		editView.cmbcntsousai.setSelectedIndex(0);
		editView.cmbkesikbn.setComboEnabled(false);
		editView.cmbkesikbn.setSelectedIndex(0);
		editView.cmbexcflg.setComboEnabled(false);
		editView.cmbexcflg.setSelectedIndex(0);
		editView.chk.chkglflg1.setEnabled(false);
		editView.chk.chkglflg1.setSelected(false);
		editView.chk.chkglflg2.setEnabled(false);
		editView.chk.chkglflg2.setSelected(false);
		editView.chk.chkglflg3.setEnabled(false);
		editView.chk.chkglflg3.setSelected(false);
		editView.chk.chkapflg1.setEnabled(false);
		editView.chk.chkapflg1.setSelected(false);
		editView.chk.chkapflg2.setEnabled(false);
		editView.chk.chkapflg2.setSelected(false);
		editView.chk.chkarflg1.setEnabled(false);
		editView.chk.chkarflg1.setSelected(false);
		editView.chk.chkarflg2.setEnabled(false);
		editView.chk.chkarflg2.setSelected(false);
		editView.chk.chkfaflg1.setEnabled(false);
		editView.chk.chkfaflg1.setSelected(false);
		editView.chk.chkfaflg2.setEnabled(false);
		editView.chk.chkfaflg2.setSelected(false);
		editView.chk.chkmcrflg.setEnabled(false);
		editView.chk.chkmcrflg.setSelected(false);
		editView.chk.chkempcodeflg.setEnabled(false);
		editView.chk.chkempcodeflg.setSelected(false);
		editView.chk.chkknrflg1.setEnabled(false);
		editView.chk.chkknrflg1.setSelected(false);
		editView.chk.chkknrflg2.setEnabled(false);
		editView.chk.chkknrflg2.setSelected(false);
		editView.chk.chkknrflg3.setEnabled(false);
		editView.chk.chkknrflg3.setSelected(false);
		editView.chk.chkknrflg4.setEnabled(false);
		editView.chk.chkknrflg4.setSelected(false);
		editView.chk.chkknrflg5.setEnabled(false);
		editView.chk.chkknrflg5.setSelected(false);
		editView.chk.chkknrflg6.setEnabled(false);
		editView.chk.chkknrflg6.setSelected(false);
		editView.chk.chkhmflg1.setEnabled(false);
		editView.chk.chkhmflg1.setSelected(false);
		editView.chk.chkhmflg2.setEnabled(false);
		editView.chk.chkhmflg2.setSelected(false);
		editView.chk.chkhmflg3.setEnabled(false);
		editView.chk.chkhmflg3.setSelected(false);
		editView.chk.chkurizeiflg.setEnabled(false);
		editView.chk.chkurizeiflg.setSelected(false);
		editView.chk.chksirzeiflg.setEnabled(false);
		editView.chk.chksirzeiflg.setSelected(false);
		editView.chk.chkOccurDate.setEnabled(false);

	}

	/**
	 * �ҏW���[����]�{�^������
	 */
	protected void btnImput_Click() {

		editView.comHjokbn.setComboEnabled(true);
		editView.comHjokbn.setSelectedIndex(1);
		editView.ctrlFixDepartment.setEditable(true);
		editView.ctrlTax.setEditable(true);
		editView.cmbcntgl.setComboEnabled(true);
		editView.cmbcntgl.setSelectedIndex(1);
		editView.cmbcntap.setComboEnabled(true);
		editView.cmbcntap.setSelectedIndex(1);
		editView.cmbcntar.setComboEnabled(true);
		editView.cmbcntar.setSelectedIndex(1);
		editView.cmbcntbg.setComboEnabled(true);
		editView.cmbcntbg.setSelectedIndex(1);
		editView.cmbtricodeflg.setComboEnabled(true);
		editView.cmbtricodeflg.setSelectedIndex(1);
		editView.cmbcntsousai.setComboEnabled(true);
		editView.cmbcntsousai.setSelectedIndex(1);
		editView.cmbkesikbn.setComboEnabled(true);
		editView.cmbkesikbn.setSelectedIndex(1);
		editView.cmbexcflg.setComboEnabled(true);
		editView.cmbexcflg.setSelectedIndex(1);
		editView.chk.chkglflg1.setEnabled(true);
		editView.chk.chkglflg2.setEnabled(true);
		editView.chk.chkglflg3.setEnabled(true);
		editView.chk.chkapflg1.setEnabled(true);
		editView.chk.chkapflg2.setEnabled(true);
		editView.chk.chkarflg1.setEnabled(true);
		editView.chk.chkarflg2.setEnabled(true);
		editView.chk.chkfaflg1.setEnabled(true);
		editView.chk.chkfaflg2.setEnabled(true);
		editView.chk.chkmcrflg.setEnabled(true);
		editView.chk.chkempcodeflg.setEnabled(true);
		editView.chk.chkknrflg1.setEnabled(true);
		editView.chk.chkknrflg2.setEnabled(true);
		editView.chk.chkknrflg3.setEnabled(true);
		editView.chk.chkknrflg4.setEnabled(true);
		editView.chk.chkknrflg5.setEnabled(true);
		editView.chk.chkknrflg6.setEnabled(true);
		editView.chk.chkhmflg1.setEnabled(true);
		editView.chk.chkhmflg2.setEnabled(true);
		editView.chk.chkhmflg3.setEnabled(true);
		editView.chk.chkurizeiflg.setEnabled(true);
		editView.chk.chksirzeiflg.setEnabled(true);
		editView.chk.chkOccurDate.setEnabled(true);

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0080ItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();
		addSubViewEvent();
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
	 * @param mode_ ���샂�[�h�B
	 * @param bean �ȖځB�C���A���ʂ̏ꍇ�͓��Y�Ȗڏ���ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Item bean) {

		this.mode = mode_;

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
					editView.ctrlItemCode.setEditable(false);
					editView.setEditMode();
					// ����
				} else {
					editView.setTitle(getWord("C01738"));
				}
				if (ItemSumType.INPUT != bean.getItemSumType()) {
					btnSum_Click();
				}

				editView.ctrlItemCode.setValue(bean.getCode());
				editView.ctrlItemName.setValue(bean.getName());
				editView.ctrlItemNames.setValue(bean.getNames());
				editView.ctrlItemNamek.setValue(bean.getNamek());

				if (ItemSumType.INPUT == bean.getItemSumType()) {
					editView.rdoImput.setSelected(true);
				} else if (ItemSumType.SUM == bean.getItemSumType()) {
					editView.rdoSum.setSelected(true);
				} else {
					editView.rdoTitle.setSelected(true);
				}

				if (Dc.DEBIT == bean.getDc()) {
					editView.rdoDebit.setSelected(true);
				} else {
					editView.rdoCredit.setSelected(true);
				}

				editView.comKmkshu.setSelectedItemValue(bean.getItemType());

				if (bean.getItemSumType() == ItemSumType.INPUT) {
					editView.comHjokbn.setSelectedItemValue(bean.hasSubItem());
					editView.ctrlFixDepartment.setCode(bean.getFixedDepartmentCode());
					editView.ctrlFixDepartment.name.setValue(bean.getFixedDepartmentName());
					editView.ctrlTax.setCode(bean.getConsumptionTax().getCode());
					editView.ctrlTax.name.setValue(bean.getConsumptionTax().getName());
					editView.cmbcntgl.setSelectedItemValue(bean.getGlType());
					editView.cmbcntap.setSelectedItemValue(bean.getApType());
					editView.cmbcntar.setSelectedItemValue(bean.getArType());
					editView.cmbcntbg.setSelectedItemValue(bean.getBgType());
					editView.cmbtricodeflg.setSelectedItemValue(bean.getClientType());
					editView.cmbcntsousai.setSelectedItemValue(bean.isDoesOffsetItem());
					editView.cmbkesikbn.setSelectedItemValue(bean.isDoesBsOffset());
					editView.cmbexcflg.setSelectedItemValue(bean.getEvaluationMethod());
					editView.chk.chkglflg1.setSelected(bean.isUseInputCashFlowSlip());
					editView.chk.chkglflg2.setSelected(bean.isUseOutputCashFlowSlip());
					editView.chk.chkglflg3.setSelected(bean.isUseTransferSlip());
					editView.chk.chkapflg1.setSelected(bean.isUseExpenseSettlementSlip());
					editView.chk.chkapflg2.setSelected(bean.isUsePaymentAppropriateSlip());
					editView.chk.chkarflg1.setSelected(bean.isUseReceivableAppropriateSlip());
					editView.chk.chkarflg2.setSelected(bean.isUseReceivableErasingSlip());
					editView.chk.chkfaflg1.setSelected(bean.isUseAssetsEntrySlip());
					editView.chk.chkfaflg2.setSelected(bean.isUsePaymentRequestSlip());
					editView.chk.chkmcrflg.setSelected(bean.isUseForeignCurrency());
					editView.chk.chkempcodeflg.setSelected(bean.isUseEmployee());
					editView.chk.chkknrflg1.setSelected(bean.isUseManagement1());
					editView.chk.chkknrflg2.setSelected(bean.isUseManagement2());
					editView.chk.chkknrflg3.setSelected(bean.isUseManagement3());
					editView.chk.chkknrflg4.setSelected(bean.isUseManagement4());
					editView.chk.chkknrflg5.setSelected(bean.isUseManagement5());
					editView.chk.chkknrflg6.setSelected(bean.isUseManagement6());
					editView.chk.chkhmflg1.setSelected(bean.isUseNonAccount1());
					editView.chk.chkhmflg2.setSelected(bean.isUseNonAccount2());
					editView.chk.chkhmflg3.setSelected(bean.isUseNonAccount3());
					editView.chk.chkurizeiflg.setSelected(bean.isUseSalesTaxation());
					editView.chk.chksirzeiflg.setSelected(bean.isUsePurchaseTaxation());

					// �������t���O
					if (editView.chk.chkOccurDate.isVisible()) {
						editView.chk.chkOccurDate.setSelected(bean.isUseOccurDate());
					}

				}

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

			// ���͂��ꂽ�Ȗڏ����擾
			Item item = getInputedItem();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", item);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(item));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", item);

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
	 * �ҏW��ʂœ��͂��ꂽ�Ȗڂ�Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�Ȗ�
	 */
	protected Item getInputedItem() {

		Item item = createItem();

		item.setCompanyCode(getCompany().getCode());
		item.setCode(editView.ctrlItemCode.getValue());
		item.setName(editView.ctrlItemName.getValue());
		item.setNames(editView.ctrlItemNames.getValue());
		item.setNamek(editView.ctrlItemNamek.getValue());
		item.setDateFrom(editView.dtBeginDate.getValue());
		item.setDateTo(editView.dtEndDate.getValue());

		if (editView.rdoImput.isSelected()) {
			item.setItemSumType(ItemSumType.INPUT);
		} else if (editView.rdoSum.isSelected()) {
			item.setItemSumType(ItemSumType.SUM);
		} else {
			item.setItemSumType(ItemSumType.TITLE);
		}

		item.setItemType(getKmkShu());

		if (editView.rdoCredit.isSelected()) {
			item.setDc(Dc.CREDIT);
		} else {
			item.setDc(Dc.DEBIT);
		}

		if (item.getItemSumType() == ItemSumType.INPUT) {

			if (editView.comHjokbn.getSelectedIndex() == 1) {
				item.setSubItem(false);
			} else {
				item.setSubItem(true);
			}
			ConsumptionTax consumptionTax = createConsumptionTax();
			consumptionTax.setCode(editView.ctrlTax.getCode());
			item.setConsumptionTax(consumptionTax);
			item.setUseEmployee(editView.chk.chkempcodeflg.isSelected());
			item.setUseManagement1(editView.chk.chkknrflg1.isSelected());
			item.setUseManagement2(editView.chk.chkknrflg2.isSelected());
			item.setUseManagement3(editView.chk.chkknrflg3.isSelected());
			item.setUseManagement4(editView.chk.chkknrflg4.isSelected());
			item.setUseManagement5(editView.chk.chkknrflg5.isSelected());
			item.setUseManagement6(editView.chk.chkknrflg6.isSelected());
			item.setUseNonAccount1(editView.chk.chkhmflg1.isSelected());
			item.setUseNonAccount2(editView.chk.chkhmflg2.isSelected());
			item.setUseNonAccount3(editView.chk.chkhmflg3.isSelected());
			item.setUseSalesTaxation(editView.chk.chkurizeiflg.isSelected());
			item.setUsePurchaseTaxation(editView.chk.chksirzeiflg.isSelected());
			item.setUseForeignCurrency(editView.chk.chkmcrflg.isSelected());
			item.setEvaluationMethod(getEvaluationMethod());
			item.setFixedDepartmentCode(editView.ctrlFixDepartment.getCode());
			item.setGlType(getGl());
			item.setApType(getAp());
			item.setArType(getAr());
			item.setBgType(getBg());
			item.setDoesOffsetItem(isDoesOffsetItem());
			item.setDoesBsOffset(isDoesBsOffset());
			item.setClientType(getCustomerType());
			item.setUseInputCashFlowSlip(editView.chk.chkglflg1.isSelected());
			item.setUseOutputCashFlowSlip(editView.chk.chkglflg2.isSelected());
			item.setUseTransferSlip(editView.chk.chkglflg3.isSelected());
			item.setUseExpenseSettlementSlip(editView.chk.chkapflg1.isSelected());
			item.setUsePaymentAppropriateSlip(editView.chk.chkapflg2.isSelected());
			item.setUseReceivableAppropriateSlip(editView.chk.chkarflg1.isSelected());
			item.setUseReceivableErasingSlip(editView.chk.chkarflg2.isSelected());
			item.setUseAssetsEntrySlip(editView.chk.chkfaflg1.isSelected());
			item.setUsePaymentRequestSlip(editView.chk.chkfaflg2.isSelected());

			// �������t���O
			if (editView.chk.chkOccurDate.isVisible()) {
				item.setUseOccurDate(editView.chk.chkOccurDate.isSelected());
			} else {
				item.setUseOccurDate(true);
			}

		}

		return item;

	}

	/**
	 * @return ����Ń}�X�^��������
	 */
	protected ConsumptionTaxSearchCondition createConsumptionTaxSearchCondition() {
		return new ConsumptionTaxSearchCondition();
	}

	/**
	 * @return �Ȗڃ}�X�^��������
	 */
	protected ItemSearchCondition createItemSearchCondition() {
		return new ItemSearchCondition();
	}

	/**
	 * @return �����
	 */
	protected ConsumptionTax createConsumptionTax() {
		return new ConsumptionTax();
	}

	/**
	 * @return �Ȗ�
	 */
	protected Item createItem() {
		return new Item();
	}

	/**
	 * �Ȗڎ�ʂ�Ԃ�
	 * 
	 * @return selectedData
	 */
	protected ItemType getKmkShu() {
		ItemType selectedData = (ItemType) editView.comKmkshu.getSelectedItemValue();

		return selectedData;
	}

	/**
	 * GL�敪��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected GLType getGl() {
		GLType selectedData = (GLType) editView.cmbcntgl.getSelectedItemValue();

		return selectedData;
	}

	/**
	 * AP�敪��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected APType getAp() {
		if (Util.isNullOrEmpty(editView.cmbcntap.getSelectedItemValue())) {
			return APType.NOMAL;
		}
		APType selectedData = (APType) editView.cmbcntap.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * AR�敪��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected ARType getAr() {
		if (Util.isNullOrEmpty(editView.cmbcntar.getSelectedItemValue())) {
			return ARType.NOMAl;
		}
		ARType selectedData = (ARType) editView.cmbcntar.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * BG�敪��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected BGType getBg() {
		BGType selectedData = (BGType) editView.cmbcntbg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * �������̓t���O��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected CustomerType getCustomerType() {
		if (Util.isNullOrEmpty(editView.cmbtricodeflg.getSelectedItemValue())) {
			return CustomerType.NONE;
		}
		CustomerType selectedData = (CustomerType) editView.cmbtricodeflg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * �]���֑Ώۃt���O��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected EvaluationMethod getEvaluationMethod() {
		EvaluationMethod selectedData = (EvaluationMethod) editView.cmbexcflg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * ���E�Ȗڐ���敪��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected Boolean isDoesOffsetItem() {
		Boolean selectedData = (Boolean) editView.cmbcntsousai.getComboBox().getSelectedItemValue();

		return selectedData;
	}

	/**
	 * BS��������敪��Ԃ�
	 * 
	 * @return selectedData
	 */
	protected Boolean isDoesBsOffset() {
		Boolean selectedData = (Boolean) editView.cmbkesikbn.getComboBox().getSelectedItemValue();

		return selectedData;
	}

	/**
	 * �Ȗڏ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param item �Ȗڏ��
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�Ȗڏ��
	 */
	protected List<Object> getRowData(Item item) {
		List<Object> list = new ArrayList<Object>();

		list.add(item.getCode()); // �ȖڃR�[�h
		list.add(item.getName()); // �Ȗږ���
		list.add(item.getNames()); // �Ȗڗ���
		list.add(item.getNamek()); // �Ȗڌ�������
		list.add(getWord(ItemSumType.getName(item.getItemSumType()))); // �W�v�敪
		list.add(getWord(ItemType.getName(item.getItemType()))); // �Ȗڎ��
		list.add(getWord(Dc.getName(item.getDc()))); // �ݎ؋敪

		if (item.getItemSumType() == ItemSumType.INPUT) {
			list.add(item.hasSubItem() ? getWord("C00006") : getWord("C00412")); // �⏕�敪
			list.add(item.getFixedDepartmentCode()); // �Œ蕔�庰��
			list.add(item.getConsumptionTax().getCode());// ����ŃR�[�h
			list.add(getWord(GLType.getName(item.getGlType()))); // GL�Ȗڐ���敪
			list.add(getWord(APType.getName(item.getApType()))); // AP�Ȗڐ���敪
			list.add(getWord(ARType.getName(item.getArType()))); // AR�Ȗڐ���敪
			list.add(getWord(BGType.getName(item.getBgType()))); // BG�Ȗڐ���敪
			list.add(getWord((CustomerType.getName(item.getClientType())))); // �������̓t���O
			list.add(getWord(getDivisionName(item.isDoesOffsetItem()))); // ���E�Ȗڐ���敪
			list.add(getWord(getDivisionName(item.isDoesBsOffset()))); // BS��������敪
			list.add(getWord(EvaluationMethod.getName(item.getEvaluationMethod()))); // �]���֑Ώۃt���O
			list.add(getWord(getBoo(item.isUseInputCashFlowSlip()))); // �����`�[���̓t���O
			list.add(getWord(getBoo(item.isUseOutputCashFlowSlip()))); // �o���`�[���̓t���O
			list.add(getWord(getBoo(item.isUseTransferSlip()))); // �U�֓`�[���̓t���O
			list.add(getWord(getBoo(item.isUseExpenseSettlementSlip()))); // �o��Z�`�[���̓t���O
			list.add(getWord(getBoo(item.isUsePaymentAppropriateSlip()))); // ���v��`�[���̓t���O
			list.add(getWord(getBoo(item.isUseReceivableAppropriateSlip()))); // ���v��`�[���̓t���O
			list.add(getWord(getBoo(item.isUseReceivableErasingSlip()))); // �������`�[���̓t���O
			list.add(getWord(getBoo(item.isUseAssetsEntrySlip()))); // ���Y�v��`�[���̓t���O
			list.add(getWord(getBoo(item.isUsePaymentRequestSlip()))); // �x���˗��`�[���̓t���O
			list.add(getWord(getBoo(item.isUseForeignCurrency()))); // ���ʉݓ��̓t���O
			list.add(getWord(getBoo1(item.isUseEmployee()))); // �Ј����̓t���O
			list.add(getWord(getBoo1(item.isUseManagement1()))); // �Ǘ��P���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement2()))); // �Ǘ�2���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement3()))); // �Ǘ�3���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement4()))); // �Ǘ�4���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement5()))); // �Ǘ�5���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement6()))); // �Ǘ�6���̓t���O
			list.add(getWord(getBoo(item.isUseNonAccount1()))); // ���v�P���̓t���O
			list.add(getWord(getBoo(item.isUseNonAccount2()))); // ���v2���̓t���O
			list.add(getWord(getBoo(item.isUseNonAccount3()))); // ���v3���̓t���O
			list.add(getWord(getBoo(item.isUseSalesTaxation()))); // ����ېœ��̓t���O
			list.add(getWord(getBoo(item.isUsePurchaseTaxation()))); // �d���ېœ��̓t���O
			list.add(getWord(getBoo(item.isUseOccurDate()))); // �������t���O
			list.add(DateUtil.toYMDString(item.getDateFrom())); // �J�n�N����
			list.add(DateUtil.toYMDString(item.getDateTo())); // �I���N����
		} else {
			list.add(""); // �⏕�敪
			list.add(""); // �Œ蕔�庰��
			list.add(""); // ����ŃR�[�h
			list.add(""); // GL�Ȗڐ���敪
			list.add(""); // AP�Ȗڐ���敪
			list.add(""); // AR�Ȗڐ���敪
			list.add(""); // BG�Ȗڐ���敪
			list.add(""); // �������̓t���O
			list.add(""); // ���E�Ȗڐ���敪
			list.add(""); // BS��������敪
			list.add(""); // �]���֑Ώۃt���O
			list.add(""); // �����`�[���̓t���O
			list.add(""); // �o���`�[���̓t���O
			list.add(""); // �U�֓`�[���̓t���O
			list.add(""); // �o��Z�`�[���̓t���O
			list.add(""); // ���v��`�[���̓t���O
			list.add(""); // ���v��`�[���̓t���O
			list.add(""); // �������`�[���̓t���O
			list.add(""); // ���Y�v��`�[���̓t���O
			list.add(""); // �x���˗��`�[���̓t���O
			list.add(""); // ���ʉݓ��̓t���O
			list.add(""); // �Ј����̓t���O
			list.add(""); // �Ǘ��P���̓t���O
			list.add(""); // �Ǘ�2���̓t���O
			list.add(""); // �Ǘ�3���̓t���O
			list.add(""); // �Ǘ�4���̓t���O
			list.add(""); // �Ǘ�5���̓t���O
			list.add(""); // �Ǘ�6���̓t���O
			list.add(""); // ���v�P���̓t���O
			list.add(""); // ���v2���̓t���O
			list.add(""); // ���v3���̓t���O
			list.add(""); // ����ېœ��̓t���O
			list.add(""); // �d���ېœ��̓t���O
			list.add(""); // �������t���O
			list.add(DateUtil.toYMDString(item.getDateFrom())); // �J�n�N����
			list.add(DateUtil.toYMDString(item.getDateTo())); // �I���N����

		}

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
			return "C01276"; // ���͉�
		} else {
			return "C01279"; // ���͕s��
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
			return "C02371"; // ���͕K�{
		} else {
			return "C01279"; // ���͕s��
		}
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param bool
	 * @return String
	 */
	public static String getDivisionName(boolean bool) {

		if (bool) {
			return "C02100"; // ����
		} else {
			return "C02099"; // ���Ȃ�
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
	 * ���������ɊY������Ȗڂ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������Ȗڂ̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Item> getItem(ItemSearchCondition condition) throws Exception {

		List<Item> list = (List<Item>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w����ʂœ��͂��ꂽ�Ȗڂ̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected ItemSearchCondition getSearchCondition() {

		ItemSearchCondition condition = createItemSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.itemRange.getCodeFrom());
		condition.setCodeTo(mainView.itemRange.getCodeTo());
		condition.setSumItem(true);
		condition.setTitleItem(true);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		return condition;

	}

	/**
	 * �ꗗ�őI������Ă���Ȗڂ�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���Ȗ�
	 * @throws Exception
	 */
	protected Item getSelectedItem() throws Exception {

		ItemSearchCondition condition = createItemSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0080ItemMasterPanel.SC.code));
		condition.setSumItem(true);
		condition.setTitleItem(true);
		List<Item> list = getItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193"); // �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
		}
		return list.get(0);
	}

	/**
	 * �w��̉Ȗڂ��폜����
	 * 
	 * @param item �Ȗ�
	 * @throws TException
	 */
	protected void deleteItem(Item item) throws TException {
		request(getModelClass(), "delete", item);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �ȖڃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItemCode.getValue())) {
			showMessage(editView, "I00037", "C00572"); // �ȖڃR�[�h����͂��Ă��������B
			editView.ctrlItemCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���Ȗڂ����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ItemSearchCondition condition = createItemSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlItemCode.getValue());
			condition.setSumItem(true);
			condition.setTitleItem(true);

			List<Item> list = getItem(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00077"); // �w��̉Ȗڂ͊��ɑ��݂��܂��B
				editView.ctrlItemCode.requestTextFocus();
				return false;
			}
		}

		// �Ȗږ��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItemName.getValue())) {
			showMessage(editView, "I00037", "C00700"); // �Ȗږ��̂���͂��Ă��������B
			editView.ctrlItemName.requestTextFocus();
			return false;
		}

		// �Ȗڗ��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItemNames.getValue())) {
			showMessage(editView, "I00037", "C00730"); // �Ȗڗ��̂���͂��Ă��������B
			editView.ctrlItemNames.requestTextFocus();
			return false;
		}

		// �Ȗڌ������̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlItemNamek.getValue())) {
			showMessage(editView, "I00037", "C00731"); // �Ȗڌ������̂���͂��Ă��������B
			editView.ctrlItemNamek.requestTextFocus();
			return false;
		}

		// �Ȗڎ�ʂ������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.comKmkshu.getSelectedItemValue())) {
			showMessage(editView, "I00037", "C01007"); // �Ȗڎ�ʂ���͂��Ă��������B
			editView.comKmkshu.requestTextFocus();
			return false;
		}

		// �W�v�敪�̓��̓{�^�������͂���Ă���ꍇ
		if (editView.rdoImput.isSelected()) {

			// �⏕�敪�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.comHjokbn.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01314"); // �⏕�敪����͂��Ă��������B
				editView.comHjokbn.requestTextFocus();
				return false;
			}

			// GL�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbcntgl.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00968"); // GL�Ȗڐ���敪����͂��Ă��������B
				editView.cmbcntgl.requestTextFocus();
				return false;
			}

			// AP�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbcntap.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00959"); // AP�Ȗڐ���敪����͂��Ă��������B
				editView.cmbcntap.requestTextFocus();
				return false;
			}

			// AR�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbcntar.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00960"); // AR�Ȗڐ���敪����͂��Ă��������B
				editView.cmbcntar.requestTextFocus();
				return false;
			}

			// BG�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbcntbg.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00962"); // BG�Ȗڐ���敪����͂��Ă��������B
				editView.cmbcntbg.requestTextFocus();
				return false;
			}

			// �������̓t���O�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbtricodeflg.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01134"); // �������̓t���O����͂��Ă��������B
				editView.cmbtricodeflg.requestTextFocus();
				return false;
			}

			// ���E�Ȗڐ���敪�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbcntsousai.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01217"); // ���E�Ȗڐ���敪����͂��Ă��������B
				editView.cmbcntsousai.requestTextFocus();
				return false;
			}

			// BS��������敪�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbkesikbn.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C02078"); // BS��������敪����͂��Ă��������B
				editView.cmbkesikbn.requestTextFocus();
				return false;
			}

			// �]���֑Ώۃt���O�������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.cmbexcflg.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01301"); // �]���֑Ώۃt���O����͂��Ă��������B
				editView.cmbexcflg.requestTextFocus();
				return false;
			}
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055"); // �J�n�N��������͂��Ă��������B
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261"); // �I���N��������͂��Ă��������B
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// ���Ǘ��Ȗڂ̏ꍇ�A�����̓��͕͂K�{
		if (APType.DEBIT == getAp()) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.CUSTOMER == getCustomerType()) {
				// ���Ǘ��Ȗڂ�ݒ肷��ꍇ�A�����敪�ɂ͎d����������͓��Ӑ�&�d�����ݒ肵�Ă��������B
				showMessage(editView, "I00613");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}
		// ���Ǘ��Ȗڂ̏ꍇ�A�����̓��͕͂K�{
		if (ARType.NOMAl != getAr()) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.VENDOR == getCustomerType()) {
				// ���Ǘ��Ȗڂ�ݒ肷��ꍇ�A�����敪�ɂ͓��Ӑ�������͓��Ӑ�&�d�����ݒ肵�Ă��������B
				showMessage(editView, "I00614");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}

		if (!Util.isNullOrEmpty(editView.ctrlTax.getCode())) {
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
	 * ����ŏ����擾
	 * 
	 * @return tax ����ŏ��
	 * @throws Exception
	 */
	protected ConsumptionTax getConsumptionTax() throws Exception {

		ConsumptionTaxSearchCondition condition = createConsumptionTaxSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.ctrlTax.getCode());

		List<ConsumptionTax> taxList = (List<ConsumptionTax>) request(ConsumptionTaxManager.class, "get", condition);

		return taxList.get(0);

	}

}