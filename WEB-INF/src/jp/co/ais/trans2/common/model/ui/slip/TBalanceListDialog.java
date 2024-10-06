package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * �c���ꗗ�̎w����ʁB
 * 
 * @author AIS
 */
public class TBalanceListDialog extends TDialog {

	/** true:�I���̓`�F�b�N�{�b�N�X���g�p��Client�� */
	public static boolean isUseChk = ClientConfig.isFlagOn("trans.use.zan.dialog.select.check");

	/**
	 * �e�[�u���񖼗񋓑�
	 */
	public enum SC {
		/** �G���e�B�e�B */
		BEAN,

		/** �I���`�F�b�N�{�b�N�X */
		CHECK,

		/** ����� */
		CUSTOMER_NAME,

		/** ������ */
		CLAlM_DATE,

		/** �`�[�ԍ� */
		SLIP_NO,

		/** �������ԍ� */
		CLAlM_NO,

		/** �����\��� */
		PAYMENT_DATE,

		/** �ʉ� */
		CUR_CODE,

		/** ���������z�i�O�݁j */
		NON_ERASE_INPUT_AMOUNT,

		/** ���������z */
		NON_ERASE_AMOUNT,

		/** �������z�i�O�݁j */
		INPUT_AMOUNT,

		/** �������z */
		AMOUNT,

		/** �������z�i�O�݁j */
		ERASE_INPUT_AMOUNT,

		/** �������z */
		ERASE_AMOUNT,

		/** �v�㕔�� */
		DEPARTMENT_NAME,

		/** �E�v */
		REMARKS;
	}

	/** �������� */
	public TTextField txtCustomer;

	/** ������/�v������� */
	public TPopupCalendar dtPaymentDay;

	/** �`�[�ԍ����� */
	public TTextField txtSlipNo;

	/** �������ԍ����� */
	public TTextField txtBillNo;

	/** �\������� */
	public TPopupCalendar dtExpectedDay;

	/** �v�㕔����� */
	public TTextField txtDep;

	/** �`�[�E�v���� */
	public TTextField txtTek;

	/** �����{�^�� */
	public TButton btnSearch;

	/** �m��{�^�� */
	public TButton btnSettle;

	/** ����{�^�� */
	public TButton btnClose;

	/** ���v */
	public TLabelNumericField ctrlTotal;

	/** ���W�I�{�^�� �ʎ���� */
	public TRadioPanel ctrlCustomerKind;

	/** ����� �͈͎w�� */
	public TCustomerReferenceRange ctrlClientRange;

	/** ���� �͈͎w�� */
	public TDepartmentReferenceRange ctrlDepartmentRange;

	/** �X�v���b�h */
	public TTable tbl;

	/** �I�� */
	public TCheckBox chk;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�t���[��
	 */
	public TBalanceListDialog(Frame parent) {
		super(parent, true);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�t���[��
	 */
	public TBalanceListDialog(Dialog parent) {
		super(parent, true);
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {
		gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setResizable(true);

		// �{�f�B�̈�
		pnlBody = new TPanel();
		pnlBody.setLayout(new GridBagLayout());
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {
		txtCustomer = new TTextField();
		dtPaymentDay = new TPopupCalendar();
		txtSlipNo = new TTextField();
		txtBillNo = new TTextField();
		dtExpectedDay = new TPopupCalendar();
		txtDep = new TTextField();
		txtTek = new TTextField();

		btnSearch = new TButton();
		btnSettle = new TButton();
		btnClose = new TButton();
		ctrlTotal = new TLabelNumericField();
		ctrlCustomerKind = new TRadioPanel("C00408");// �����
		ctrlClientRange = new TCustomerReferenceRange();
		ctrlDepartmentRange = new TDepartmentReferenceRange();
		tbl = new TTable();
		chk = new TCheckBox();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {
		Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSize(rect.width, 600);

		// �ꗗ
		if (isUseChk) {
			// �`�F�b�N�̓X�y�[�X�Őݒ��
			tbl.addCheckBoxColumn(SC.CHECK.ordinal());
			tbl.table.enableInputMethods(false); // �e�[�u����IME�͏��OFF
		} else {
			// �����ʂ�
			tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tbl.addSpreadSheetSelectChange(btnSettle);
			tbl.setEnterToButton(true);
		}

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(10, 10, 0, 10);
		pnlBody.add(tbl, gc);

		// BODY �����p
		TPanel pnlSearch = new TPanel();
		pnlSearch.setLayout(null);
		pnlSearch.setMaximumSize(new Dimension(this.getWidth(), 150));
		pnlSearch.setMinimumSize(new Dimension(this.getWidth(), 150));
		pnlSearch.setPreferredSize(new Dimension(this.getWidth(), 150));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlSearch, gc);

		// ��������
		int x = 40 + (isUseChk ? 30 : 0);
		int y = 2;
		txtCustomer.setLocation(x, y);
		txtCustomer.setImeMode(true);
		txtCustomer.setMaxLength(20);
		txtCustomer.setSize(200, 20);
		pnlSearch.add(txtCustomer);

		// ������/�v�������
		x = txtCustomer.getX() + txtCustomer.getWidth();
		dtPaymentDay.setLocation(x, y);
		dtPaymentDay.setAllowableBlank(true);
		dtPaymentDay.clear();
		pnlSearch.add(dtPaymentDay);

		// �`�[�ԍ�����
		x = dtPaymentDay.getX() + dtPaymentDay.getWidth();
		txtSlipNo.setLocation(x, y);
		txtSlipNo.setSize(130, 20);
		txtSlipNo.setImeMode(false);
		txtSlipNo.setMaxLength(20);
		pnlSearch.add(txtSlipNo);

		// �������ԍ�����
		x = txtSlipNo.getX() + txtSlipNo.getWidth();
		txtBillNo.setLocation(x, y);
		txtBillNo.setSize(130, 20);
		txtBillNo.setImeMode(false);
		txtBillNo.setMaxLength(20);
		pnlSearch.add(txtBillNo);

		// �\�������
		x = txtBillNo.getX() + txtBillNo.getWidth();
		dtExpectedDay.setLocation(x, y);
		dtExpectedDay.setAllowableBlank(true);
		dtExpectedDay.clear();
		pnlSearch.add(dtExpectedDay);

		// �v�㕔�����
		x = dtExpectedDay.getX() + dtExpectedDay.getWidth() + 260;
		txtDep.setLocation(x, y);
		txtDep.setSize(150, 20);
		txtDep.setImeMode(true);
		txtDep.setMaxLength(20);
		txtDep.clear();
		pnlSearch.add(txtDep);
		
		// �`�[�E�v����
		x = txtDep.getX() + txtDep.getWidth();
		txtTek.setLocation(x, y);
		txtTek.setSize(300, 20);
		txtTek.setImeMode(true);
		txtTek.setMaxLength(80);
		txtTek.clear();
		pnlSearch.add(txtTek);

		x = 30;
		y = 32;

		// �����{�^��
		btnSearch.setLangMessageID("C00155");// ����
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, y);
		pnlSearch.add(btnSearch);

		// �m��{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");// �m��
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setEnabled(false);
		btnSettle.setLocation(x, y);
		pnlSearch.add(btnSettle);

		// ����{�^��
		x = x + btnSettle.getWidth() + HEADER_MARGIN_X;
		btnClose.setLangMessageID("C00405");// ���
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, y);
		pnlSearch.add(btnClose);

		// ���v
		x = x + btnClose.getWidth() + HEADER_MARGIN_X;
		ctrlTotal.setLabelSize(30);
		ctrlTotal.setNumericFormat("#,##0");
		ctrlTotal.setLangMessageID("C00165");// ���v
		ctrlTotal.setLocation(x, y + 2);
		ctrlTotal.setEditable(false);
		pnlSearch.add(ctrlTotal);

		// �����
		x = x + ctrlTotal.getWidth() + 10;
		ctrlCustomerKind.addRadioButton("C10343", 100);// ��������
		ctrlCustomerKind.addRadioButton("C10344", 100);// ���̑������
		ctrlCustomerKind.setLocation(x, y - 6);
		pnlSearch.add(ctrlCustomerKind);

		// �͈͎w��
		x = 30;
		y = 70;
		TPanel pnlRenge = new TPanel();
		pnlRenge.setLayout(null);
		pnlRenge.setLangMessageID("C00433");// �͈͎w��
		pnlRenge.setLocation(x, y);
		pnlRenge.setSize(ctrlClientRange.getWidth() + ctrlDepartmentRange.getWidth() + 50, 75);
		pnlSearch.add(pnlRenge);

		// �����͈͎w��
		ctrlClientRange.setLocation(20, 20);
		Insets zero = new Insets(0, 0, 0, 0);
		ctrlClientRange.ctrlCustomerReferenceFrom.btn.setMargin(zero);
		ctrlClientRange.ctrlCustomerReferenceTo.btn.setMargin(zero);
		ctrlClientRange.ctrlCustomerReferenceFrom.btn.setLangMessageID("C10345");// �J�n�����
		ctrlClientRange.ctrlCustomerReferenceTo.btn.setLangMessageID("C10346");// �I�������
		ctrlClientRange.setCheckExist(true);
		pnlRenge.add(ctrlClientRange);

		// ����͈͎w��
		ctrlDepartmentRange.setLocation(ctrlClientRange.getX() + ctrlClientRange.getWidth() + 10, 20);
		ctrlDepartmentRange.ctrlDepartmentReferenceFrom.btn.setLangMessageID("C10347");// �J�n����
		ctrlDepartmentRange.ctrlDepartmentReferenceTo.btn.setLangMessageID("C10169");// �I������
		ctrlDepartmentRange.setCheckExist(true);
		pnlRenge.add(ctrlDepartmentRange);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		txtCustomer.setTabControlNo(++i);
		dtPaymentDay.setTabControlNo(++i);
		txtSlipNo.setTabControlNo(++i);
		txtBillNo.setTabControlNo(++i);
		dtExpectedDay.setTabControlNo(++i);
		txtDep.setTabControlNo(++i);
		txtTek.setTabControlNo(++i);
		btnSearch.setTabControlNo(++i);
		btnSettle.setTabControlNo(++i);
		btnClose.setTabControlNo(++i);
		ctrlCustomerKind.setTabControlNo(++i);
		ctrlClientRange.setTabControlNo(++i);
		ctrlDepartmentRange.setTabControlNo(++i);
		tbl.setTabControlNo(++i);
	}
}
