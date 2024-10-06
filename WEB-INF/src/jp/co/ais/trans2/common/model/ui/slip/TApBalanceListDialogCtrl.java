package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.slip.TBalanceListDialog.SC;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * ���c�������p�_�C�A���O�R���g���[��
 */
public class TApBalanceListDialogCtrl extends TController {

	/** �uCancel�v���I�����ꂽ�ꍇ�ɃN���X���\�b�h����Ԃ����l */
	public static final int CANCEL_OPTION = 0;

	/** �u�m��v���I�����ꂽ�ꍇ�ɃN���X���\�b�h����Ԃ����l */
	public static final int OK_OPTION = 1;

	/** �w����� */
	public TBalanceListDialog dialog;

	/** �eContainer */
	protected Container parent;

	/** �Œ�ʉ݃R�[�h */
	protected String currencyCode = "";

	/** ��ʉ݃R�[�h */
	protected String keyCurrency = getCompany().getAccountConfig().getKeyCurrency().getCode();

	/** ��ʉ� �����_���� */
	protected int keyDigit = getCompany().getAccountConfig().getKeyCurrency().getDecimalPoint();

	/** ���� */
	protected int option = CANCEL_OPTION;

	/** ���ݕҏW���̓`�[�ԍ� */
	protected String nowSlipNo;

	/** ���ɏ����I�����Ă���f�[�^ */
	protected List<AP_ZAN> nowEraseList = new LinkedList<AP_ZAN>();

	/** true:�I���̓`�F�b�N�{�b�N�X���g�p��Client�� */
	public static boolean isUseChk = ClientConfig.isFlagOn("trans.use.zan.dialog.select.check");

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�R���|�[�l���g
	 */
	public TApBalanceListDialogCtrl(Container parent) {
		this.parent = parent;
		this.dialog = createView();

		// �w����ʂ�����������
		initView();

		// �C�x���g�ݒ�
		addDialogEvent();
	}

	/**
	 * �_�C�A���O����
	 * 
	 * @return �_�C�A���O
	 */
	protected TBalanceListDialog createView() {
		if (parent instanceof TPanel) {
			return new TBalanceListDialog(((TPanel) parent).getParentFrame());
		} else if (parent instanceof TDialog) {
			return new TBalanceListDialog(((TDialog) parent));
		} else if (parent instanceof TFrame) {
			return new TBalanceListDialog(((TFrame) parent));
		}

		return null;
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getView()
	 */
	@Override
	public TBalanceListDialog getView() {
		return dialog;
	}

	/**
	 * ��ʂ�����������
	 */
	protected void initView() {
		// �^�C�g���̐ݒ�
		dialog.setTitle(getWord("C10334"));// ���c���ꗗ

		dialog.tbl.setTableKeyName(this.getClass().toString());

		// �X�v���b�h�̐ݒ�
		dialog.tbl.addColumn(TBalanceListDialog.SC.BEAN, "", -1);
		dialog.tbl.addColumn(TBalanceListDialog.SC.CHECK, "C00324", isUseChk ? 30 : -1, dialog.chk); // �I��
		dialog.tbl.addColumn(TBalanceListDialog.SC.CUSTOMER_NAME, getWord("C01690"), 200);// �x����
		dialog.tbl.addColumn(TBalanceListDialog.SC.CLAlM_DATE, getWord("C00598"), 75, SwingConstants.CENTER);// �v���
		dialog.tbl.addColumn(TBalanceListDialog.SC.SLIP_NO, getWord("C10384"), 130);// �v��`�[�ԍ�
		dialog.tbl.addColumn(TBalanceListDialog.SC.CLAlM_NO, getWord("C01708"), 130);// �������ԍ�
		dialog.tbl.addColumn(TBalanceListDialog.SC.CLAlM_DATE, getWord("C01100"), 75, SwingConstants.CENTER);// �x���\���
		dialog.tbl.addColumn(TBalanceListDialog.SC.CUR_CODE, getWord("C00371"), 40, SwingConstants.CENTER);// �ʉ�
		dialog.tbl
			.addColumn(TBalanceListDialog.SC.NON_ERASE_INPUT_AMOUNT, getWord("C10380"), 110, SwingConstants.RIGHT);// ���������z(�O��)
		dialog.tbl.addColumn(TBalanceListDialog.SC.NON_ERASE_AMOUNT, getWord("C10381"), 110, SwingConstants.RIGHT);// ���������z
		dialog.tbl.addColumn(TBalanceListDialog.SC.DEPARTMENT_NAME, getWord("C00863"), 150);// �v�㕔��
		dialog.tbl.addColumn(TBalanceListDialog.SC.REMARKS, getWord("C00384"), 300);// �E�v

		dialog.tbl.restoreComponent();// �J������Ԃ̕���

		// ���c���ꗗ�̏ꍇ�͔�\��
		dialog.ctrlCustomerKind.setVisible(false);
		
		// �v�㕔��A�`�[�E�v�������̈ʒu����
		int width = dialog.getWidth();
		int adjSize = 40 + 110 * 2; // �ʉ݁E���z�~6
		
		int x = dialog.dtExpectedDay.getX() + dialog.dtExpectedDay.getWidth();
		int y = dialog.dtExpectedDay.getY();

		int txtDep = dialog.txtDep.getWidth();
		int txtTek = dialog.txtTek.getWidth();

		// �z�u���ă_�C�A���O�T�C�Y�𒴂���ꍇ�͎��܂�悤�ɒ���
		if (width > (x + adjSize + txtDep + txtTek)) {
			x += adjSize;
		} else {
			x = width - txtDep - txtTek - 20;
		}		
		dialog.txtDep.setLocation(x, y);
		x += dialog.txtDep.getWidth();
		dialog.txtTek.setLocation(x, y);
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addDialogEvent() {

		// ����
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doSearch();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});

		// �m��
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doSettle();
			}
		});

		// ����
		dialog.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doClose();
			}

		});

		// �s�I�����C�x���g
		dialog.tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				if (!isUseChk) {
					selectedTableRow();
				}
			}
		});

		dialog.chk.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// �I���s�ɉ����āA���v�s���Čv�Z����B
				summary();
			}

		});
		// �ꗗ����
		dialog.tbl.setAdapter(new TTableAdapter() {

			/**
			 * �w�b�_�[���N���b�N��
			 * 
			 * @param column ��
			 */
			@Override
			public void afterHeaderClicked(int column) {
				if (dialog.tbl.getRowCount() == 0 || column < 0) {
					return;
				}

				if (SC.CHECK.ordinal() == column) {
					// �I���s�ɉ����āA���v�s���Čv�Z����B
					summary();

					// �N���b�N������̃t�H�[�J�X�𓖂Ă�
					table.setColumnSelectionInterval(column, column);
				}
			}
		});
	}

	/**
	 * �N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getManagerClass() {
		return BalanceManager.class;
	}

	/**
	 * ���ɏ����I�����Ă���f�[�^���Z�b�g
	 * 
	 * @param list �����s���
	 */
	public void setNowEraseList(List<AP_ZAN> list) {
		this.nowEraseList = list;
	}

	/**
	 * �����ΏۂɊ܂߂�����`�[�ԍ�
	 * 
	 * @param slipNo �`�[�ԍ�
	 */
	public void setNowSlipNo(String slipNo) {
		this.nowSlipNo = slipNo;
	}

	/**
	 * �\��
	 * 
	 * @return ����
	 */
	public int show() {

		// �\��
		dialog.setVisible(true);

		return option;
	}

	/**
	 * ��������
	 */
	protected void doSearch() {
		try {
			if (!checkInput()) {
				return;
			}

			// �e�[�u��������
			dialog.tbl.removeRow();

			// �f�[�^���o
			BalanceCondition condition = getCondition();

			List<AP_ZAN> list = (List<AP_ZAN>) request(getManagerClass(), "getApBalance", condition);

			if (list == null || list.isEmpty()) {
				showMessage(dialog, "I00022");
				dialog.txtCustomer.requestFocus();
				return;
			}

			// �f�[�^�����݂���ꍇ�A�X�v���b�h�Ɏ擾�f�[�^��ݒ�
			for (AP_ZAN bean : list) {
				// ���ɑI��ł���ꍇ�͏��O
				if (nowEraseList.contains(bean)) {
					continue;
				}

				dialog.tbl.addRow(getRow(bean));
			}

			if (dialog.tbl.getRowCount() == 0) {
				showMessage(dialog, "I00022");
				dialog.txtCustomer.requestFocus();

			} else {
				// �s�I��
				dialog.tbl.requestFocus();
				dialog.tbl.setRowSelection(0);
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {
		if (!dialog.ctrlDepartmentRange.isSmallerFrom()) {
			showMessage("I00152", "C00467");// {0}�͊J�n�R�[�h�����I���R�[�h�Ŏw�肵�Ă��������B����
			dialog.ctrlDepartmentRange.getFieldFrom().code.requestFocus();
			return false;
		}

		if (!dialog.ctrlClientRange.isSmallerFrom()) {
			showMessage("I00152", "C00408");// {0}�͊J�n�R�[�h�����I���R�[�h�Ŏw�肵�Ă��������B �����
			dialog.ctrlClientRange.getFieldFrom().code.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * �e�[�u���ɐݒ肷��z��f�[�^���擾����
	 * 
	 * @param bean
	 * @return �s�f�[�^
	 */
	protected List<Object> getRow(AP_ZAN bean) {
		// �����_����
		int digit = bean.getZAN_CUR_DEC_KETA();

		List<Object> list = new ArrayList(SC.values().length);
		list.add(bean);
		list.add(false);
		list.add(bean.getZAN_SIHA_NAME_S()); // �x����
		list.add(DateUtil.toYMDString(bean.getZAN_DEN_DATE())); // �v���
		list.add(bean.getZAN_DEN_NO()); // �v��`�[�ԍ�
		list.add(bean.getZAN_SEI_NO()); // �������ԍ�
		list.add(DateUtil.toYMDString(bean.getZAN_SIHA_DATE())); // �x���\���
		list.add(keyCurrency.equals(bean.getZAN_CUR_CODE()) ? "" : bean.getZAN_CUR_CODE()); // �ʉ�
		list.add(NumberFormatUtil.formatNumber(bean.getZAN_IN_SIHA_KIN(), digit)); // ���������z(�O��)
		list.add(NumberFormatUtil.formatNumber(bean.getZAN_KIN(), keyDigit)); // ���������z
		list.add(bean.getZAN_DEP_NAME_S()); // �v�㕔��
		list.add(bean.getZAN_TEK()); // �E�v

		return list;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	protected BalanceCondition getCondition() {
		BalanceCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode()); // ��ЃR�[�h
		condition.setCustomerNamesLike(dialog.txtCustomer.getText()); // �x����
		condition.setSlipDate(dialog.dtPaymentDay.getValue()); // �v���
		condition.setSlipNoLike(dialog.txtSlipNo.getText()); // �v��`�[�ԍ�
		condition.setBillNoLike(dialog.txtBillNo.getText()); // �������ԍ�
		condition.setExpectedDay(dialog.dtExpectedDay.getValue()); // �x���\���
		condition.setDepartmentNameLike(dialog.txtDep.getText()); // �v�㕔��
		condition.setSlipTekLike(dialog.txtTek.getText()); // �`�[�E�v
		condition.setClientCodeFrom(dialog.ctrlClientRange.getCodeFrom()); // �J�n�����
		condition.setClientCodeTo(dialog.ctrlClientRange.getCodeTo());// �I�������
		condition.setDepartmentCodeFrom(dialog.ctrlDepartmentRange.getCodeFrom());// �J�n����
		condition.setDepartmentCodeTo(dialog.ctrlDepartmentRange.getCodeTo());// �I������
		condition.setNotIncledEraseSlipNo(this.nowSlipNo); // �ҏW���̏����`�[�ԍ�
		condition.setCurrencyCode(currencyCode); // �ʉ݃R�[�h
		return condition;
	}

	/**
	 * ������������
	 * 
	 * @return condition
	 */
	protected BalanceCondition createCondition() {
		BalanceCondition condition = new BalanceCondition();
		return condition;
	}

	/**
	 * �m�菈��
	 */
	protected void doSettle() {
		try {
			// �r��
			List<AP_ZAN> list = getSelectedList();
			List<BalanceCondition> lockList = new ArrayList<BalanceCondition>();

			String compCode = getCompanyCode();

			for (AP_ZAN zan : list) {
				BalanceCondition condition = new BalanceCondition();
				condition.setCompanyCode(compCode);
				condition.setSlipNo(zan.getZAN_DEN_NO());
				condition.setSlipLineNo(zan.getZAN_GYO_NO());
				condition.setNotIncledEraseSlipNo(this.nowSlipNo); // �ҏW���̏����`�[�ԍ�
				lockList.add(condition);
			}

			request(getManagerClass(), "lockApBalance", lockList);

			this.option = OK_OPTION;

			dialog.dispose();

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * ����{�^������
	 */
	protected void doClose() {
		this.option = CANCEL_OPTION;

		dialog.dispose();
	}

	/**
	 * �I���s�̕ύX
	 */
	protected void selectedTableRow() {
		// �m��{�^������
		dialog.btnSettle.setEnabled(dialog.tbl.getSelectedRows().length != 0);

		// �I���s�ɉ����āA���v�s���Čv�Z����B
		summary();
	}

	/**
	 * ���v���v�Z����
	 */
	protected void summary() {

		// �ϐ�������
		BigDecimal total = BigDecimal.ZERO;// ���v

		int keta = 0;

		// �I���s�̎擾
		boolean isSelected = false;
		for (AP_ZAN zan : getSelectedList()) {
			// �\����
			keta = keta < zan.getZAN_CUR_DEC_KETA() ? zan.getZAN_CUR_DEC_KETA() : keta;

			total = total.add(zan.getZAN_IN_SIHA_KIN());
	
			isSelected = true;
		}
		// �m��{�^������
		dialog.btnSettle.setEnabled(isSelected);

		// ���v�l�̐ݒ�
		dialog.ctrlTotal.setDecimalPoint(keta);
		dialog.ctrlTotal.setNumber(total);
	}

	/**
	 * �I���s�f�[�^�̎擾
	 * 
	 * @return �c�����X�g
	 */
	public List<AP_ZAN> getSelectedList() {
		
		List<AP_ZAN> list = null;

		if (isUseChk) {
			list = new ArrayList<AP_ZAN>();
			for (int i = 0; i < dialog.tbl.getRowCount(); i++) {
				Boolean isChecked = (Boolean) dialog.tbl.getRowValueAt(i, SC.CHECK);

				if (isChecked) {
					list.add((AP_ZAN) dialog.tbl.getRowValueAt(i, SC.BEAN));
				}
			}
		} else {
			int[] rows = dialog.tbl.getSelectedRows();
			list = new ArrayList<AP_ZAN>(rows.length);

			for (int i = 0; i < rows.length; i++) {
				list.add((AP_ZAN) dialog.tbl.getRowValueAt(rows[i], SC.BEAN));
			}
		}

		return list;
	}

	/**
	 * �ꗗ�̑I���`����ݒ肷��
	 * 
	 * @param model
	 */
	public void setSelectionMode(int model) {
		dialog.tbl.setSelectionMode(model);
	}

	/**
	 * ������ݒ肷��B�i�Œ�\���j
	 * 
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		dialog.ctrlClientRange.getFieldFrom().setEntity(customer);
		dialog.ctrlClientRange.getFieldTo().setEntity(customer);
		dialog.ctrlClientRange.getFieldFrom().setEditable(false);
		dialog.ctrlClientRange.getFieldTo().setEditable(false);
	}

	/**
	 * �Œ�ʉ݃R�[�h��ݒ肷��B
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
