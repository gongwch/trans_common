package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �d�󖾍ד��̓p�l��
 * 
 * @author AIS
 */
public class TFormDCInputPanel extends TPanel {

	/** 0:�ؕ� */
	public static final int DR = TFormDCInputPanelCtrl.DR;

	/** 1:�ݕ� */
	public static final int CR = TFormDCInputPanelCtrl.CR;

	/** ���J�n�ʒu */
	public final int X_POINT = 5;

	/** ��J�n�ʒu */
	public final int Y_POINT = 2;

	/** �]��_�� */
	public final int MARGIN_X = 2;

	/** �s�P�F */
	public static Color row1Color = TTable.notSelectedColor;

	/** �s�Q�F */
	public static Color row2Color = TTable.notSelectedColor2;

	/** �s�ԍ��̃p�l�� */
	public TPanel pnlRowNo;

	/** �ؕ��ȖځA���z�̃p�l�� */
	public TPanel pnlDebit;

	/** �Ǘ����ڂ̃p�l�� */
	public TPanel pnlCenter;

	/** �ݕ��ȖځA���z�̃p�l�� */
	public TPanel pnlCredit;

	/** �{�^���̃p�l�� */
	public TPanel pnlButton;

	/** �s�ԍ��̃^�C�g�� */
	public TLabel lblRowNo;

	/** �ؕ��Ȗڂ̃^�C�g�� */
	public TLabel lblDebitItemTitle;

	/** �ؕ����z�̃^�C�g�� */
	public TLabel lblDebitAmountTitle;

	/** �Ǘ����ڂ̃^�C�g�� */
	public TLabel lblCenterTitle;

	/** �ݕ��Ȗڂ̃^�C�g�� */
	public TLabel lblCreditItemTitle;

	/** �ݕ����z�̃^�C�g�� */
	public TLabel lblCreditAmountTitle;

	/** �u�z��v�v���� */
	public TCompanyReference[] ctrlKCompany = new TCompanyReference[2];

	/** �u�z��v�v�㕔�� */
	public TDepartmentReference[] ctrlKDepartment = new TDepartmentReference[2];

	/** �u�z��v�Ȗ� */
	public TItemGroup[] ctrlItem = new TItemGroup[2];

	/** �u�z��v����� */
	public TTaxReference[] ctrlTax = new TTaxReference[2];

	/** �u�z��v�ŋ敪 */
	public TComboBox[] ctrlTaxDivision = new TComboBox[2];

	/** �u�z��v�ʉ݃��X�g */
	public TComboBox[] ctrlCurrency = new TComboBox[2];

	/** �u�z��v���͋��z */
	public TNumericField[] ctrlInputAmount = new TNumericField[2];

	/** �u�z��v���[�g */
	public TLabelNumericField[] ctrlRate = new TLabelNumericField[2];

	/** �u�z��v�M�݋��z */
	public TNumericField[] ctrlKeyAmount = new TNumericField[2];

	/** �u�z��v����Ŋz */
	public TNumericField[] ctrlTaxAmount = new TNumericField[2];

	/** �ؕ��v���� */
	public TCompanyReference ctrlKCompanyDebit;

	/** �ؕ��v�㕔�� */
	public TDepartmentReference ctrlKDepartmentDebit;

	/** �ؕ��Ȗ� */
	public TItemGroup ctrlItemDebit;

	/** �ؕ������ */
	public TTaxReference ctrlTaxDebit;

	/** �ؕ��ŋ敪 */
	public TComboBox ctrlTaxDivisionDebit;

	/** �ؕ��ʉ݃��X�g */
	public TComboBox ctrlCurrencyDebit;

	/** �ؕ����͋��z */
	public TNumericField ctrlInputAmountDebit;

	/** �ؕ����[�g */
	public TLabelNumericField ctrlRateDebit;

	/** �ؕ��M�݋��z */
	public TNumericField ctrlKeyAmountDebit;

	/** �ؕ�����Ŋz */
	public TNumericField ctrlTaxAmountDebit;

	/** ����� */
	public TCustomerReference ctrlCustomer;

	/** �Ј� */
	public TEmployeeReference ctrlEmployee;

	/** �Ǘ�1 */
	public TManagement1Reference ctrlManage1;

	/** �Ǘ�2 */
	public TManagement2Reference ctrlManage2;

	/** �Ǘ�3 */
	public TManagement3Reference ctrlManage3;

	/** �Ǘ�4 */
	public TManagement4Reference ctrlManage4;

	/** �Ǘ�5 */
	public TManagement5Reference ctrlManage5;

	/** �Ǘ�6 */
	public TManagement6Reference ctrlManage6;

	/** ������ */
	public TLabelPopupCalendar ctrlOccurDate;

	/** �s�E�v */
	public TRemarkReference ctrlRemark;

	/** ���v���� */
	public TNonAccountintDetailUnit ctrlNonAcDetails;

	/** �ݕ��v���� */
	public TCompanyReference ctrlKCompanyCredit;

	/** �ݕ��v�㕔�� */
	public TDepartmentReference ctrlKDepartmentCredit;

	/** �ݕ��Ȗ� */
	public TItemGroup ctrlItemCredit;

	/** �ݕ������ */
	public TTaxReference ctrlTaxCredit;

	/** �ݕ��ʉ݃��X�g */
	public TComboBox ctrlCurrencyCredit;

	/** �ݕ����͋��z */
	public TNumericField ctrlInputAmountCredit;

	/** �ݕ����[�g */
	public TLabelNumericField ctrlRateCredit;

	/** �ݕ��M�݋��z */
	public TNumericField ctrlKeyAmountCredit;

	/** �ݕ�����Ŋz */
	public TNumericField ctrlTaxAmountCredit;

	/** �ݕ��ŋ敪 */
	public TComboBox ctrlTaxDivisionCredit;

	/** �s�ړ��w�� */
	public TCheckBox ctrlMoveCheck;

	/** �^�C�g�� */
	public TLabel lblTitle;

	/** �s���� */
	public TButton btnRowCopy;

	/** ���]���� */
	public TButton btnRowCopyReverse;

	/** �s�ǉ� */
	public TButton btnRowNew;

	/** �s�폜 */
	public TButton btnRowDelete;

	/** �R���g���[�� */
	public TFormDCInputPanelCtrl controller;

	/**
	 * �R���X�g���N�^.
	 */
	protected TFormDCInputPanel() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		createController();

	}

	/**
	 * �R���g���[���쐬
	 */
	protected void createController() {
		controller = new TFormDCInputPanelCtrl(this);
	}

	/**
	 * �R���|�[�l���g������������
	 */
	protected void initComponents() {

		pnlRowNo = new TPanel();
		pnlDebit = new TPanel();
		pnlCenter = new TPanel();
		pnlCredit = new TPanel();
		lblRowNo = new TLabel();
		lblDebitItemTitle = new TLabel();
		lblDebitAmountTitle = new TLabel();
		lblCenterTitle = new TLabel();
		lblCreditItemTitle = new TLabel();
		lblCreditAmountTitle = new TLabel();

		ctrlKCompany[DR] = new TCompanyReference();
		ctrlKDepartment[DR] = new TDepartmentReference();
		ctrlItem[DR] = new TItemGroup();
		ctrlTax[DR] = new TTaxReference();
		ctrlTaxDivision[DR] = new TComboBox();
		ctrlCurrency[DR] = new TComboBox();
		ctrlInputAmount[DR] = new TNumericField();
		ctrlRate[DR] = new TLabelNumericField();
		ctrlKeyAmount[DR] = new TNumericField();
		ctrlTaxAmount[DR] = new TNumericField();

		ctrlKCompany[CR] = new TCompanyReference();
		ctrlKDepartment[CR] = new TDepartmentReference();
		ctrlItem[CR] = new TItemGroup();
		ctrlTax[CR] = new TTaxReference();
		ctrlTaxDivision[CR] = new TComboBox();
		ctrlCurrency[CR] = new TComboBox();
		ctrlInputAmount[CR] = new TNumericField();
		ctrlRate[CR] = new TLabelNumericField();
		ctrlKeyAmount[CR] = new TNumericField();
		ctrlTaxAmount[CR] = new TNumericField();

		ctrlKCompanyDebit = ctrlKCompany[DR];
		ctrlKDepartmentDebit = ctrlKDepartment[DR];
		ctrlItemDebit = ctrlItem[DR];
		ctrlTaxDebit = ctrlTax[DR];
		ctrlTaxDivisionDebit = ctrlTaxDivision[DR];
		ctrlCurrencyDebit = ctrlCurrency[DR];
		ctrlInputAmountDebit = ctrlInputAmount[DR];
		ctrlRateDebit = ctrlRate[DR];
		ctrlKeyAmountDebit = ctrlKeyAmount[DR];
		ctrlTaxAmountDebit = ctrlTaxAmount[DR];

		ctrlCustomer = new TCustomerReference();
		ctrlEmployee = new TEmployeeReference();
		ctrlManage1 = new TManagement1Reference();
		ctrlManage2 = new TManagement2Reference();
		ctrlManage3 = new TManagement3Reference();
		ctrlManage4 = new TManagement4Reference();
		ctrlManage5 = new TManagement5Reference();
		ctrlManage6 = new TManagement6Reference();

		ctrlOccurDate = new TLabelPopupCalendar();
		ctrlRemark = new TRemarkReference();
		ctrlNonAcDetails = new TNonAccountintDetailUnit();

		ctrlKCompanyCredit = ctrlKCompany[CR];
		ctrlKDepartmentCredit = ctrlKDepartment[CR];
		ctrlItemCredit = ctrlItem[CR];
		ctrlTaxCredit = ctrlTax[CR];
		ctrlTaxDivisionCredit = ctrlTaxDivision[CR];
		ctrlCurrencyCredit = ctrlCurrency[CR];
		ctrlInputAmountCredit = ctrlInputAmount[CR];
		ctrlRateCredit = ctrlRate[CR];
		ctrlKeyAmountCredit = ctrlKeyAmount[CR];
		ctrlTaxAmountCredit = ctrlTaxAmount[CR];

		pnlButton = new TPanel();
		ctrlMoveCheck = new TCheckBox();
		lblTitle = new TLabel();

		btnRowCopy = new TButton();
		btnRowCopyReverse = new TButton();
		btnRowNew = new TButton();
		btnRowDelete = new TButton();

	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setSize(1280, 140);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setLayout(null);

		Dimension codeSize = new Dimension(53, 20);
		Dimension nameSize = new Dimension(170, 20);
		Dimension itemSize = new Dimension(155, 60);
		Dimension itemCodeSize = new Dimension(53, 20);
		Dimension itemNameSize = new Dimension(102, 20);
		Dimension cmpNameSize = itemNameSize;
		Dimension buttonSize = new Dimension(20, 75);
		Dimension amountSize = new Dimension(106, 20);
		Dimension comboSize = new Dimension(60, 20);

		Map modelTax = new HashMap();
		modelTax.put(TaxCalcType.OUT, TModelUIUtil.getWord(TaxCalcType.OUT.getName())); // 0:�O��
		modelTax.put(TaxCalcType.IN, TModelUIUtil.getWord(TaxCalcType.IN.getName())); // 1:����

		// �s�ԍ��̃^�C�g��
		lblRowNo.setSize(18, 138);
		lblRowNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRowNo.setOpaque(true);
		lblRowNo.setLocation(1, 1);
		lblRowNo.setBackground(row1Color);
		lblRowNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRowNo.setLangMessageID("");
		pnlRowNo.add(lblRowNo);

		pnlRowNo.setLocation(0, 0);
		pnlRowNo.setSize(20, 140);
		pnlRowNo.setLayout(null);
		add(pnlRowNo);

		int x = X_POINT;
		int y = Y_POINT;

		{

			x = X_POINT;
			y = Y_POINT;

			// �ؕ��Ȗڂ̃^�C�g��
			lblDebitItemTitle.setSize(217, 20);
			lblDebitItemTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblDebitItemTitle.setOpaque(true);
			lblDebitItemTitle.setBackground(Color.LIGHT_GRAY);
			lblDebitItemTitle.setLocation(x, y);
			pnlDebit.add(lblDebitItemTitle);

			x += lblDebitItemTitle.getWidth() + MARGIN_X;

			// �ؕ����z�̃^�C�g��
			lblDebitAmountTitle.setSize(113 - MARGIN_X, 20);
			lblDebitAmountTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblDebitAmountTitle.setOpaque(true);
			lblDebitAmountTitle.setBackground(Color.LIGHT_GRAY);
			lblDebitAmountTitle.setLangMessageID("�ؕ����z");
			lblDebitAmountTitle.setLocation(x, y);
			pnlDebit.add(lblDebitAmountTitle);

			x = lblDebitItemTitle.getX();
			y += lblDebitItemTitle.getHeight();

			// �ؕ��v����
			ctrlKCompanyDebit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKCompanyDebit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKCompanyDebit.name, cmpNameSize);
			ctrlKCompanyDebit.resize();
			ctrlKCompanyDebit.setLocation(x, y);
			pnlDebit.add(ctrlKCompanyDebit);

			y += ctrlKCompanyDebit.getHeight();

			// �ؕ��v�㕔��
			ctrlKDepartmentDebit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKDepartmentDebit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKDepartmentDebit.name, cmpNameSize);
			ctrlKDepartmentDebit.resize();
			ctrlKDepartmentDebit.setLocation(x, y);
			pnlDebit.add(ctrlKDepartmentDebit);

			y += ctrlKDepartmentDebit.getHeight();

			// �ؕ��Ȗ�
			ctrlItemDebit.ctrlItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlItemReference.name, itemNameSize);
			ctrlItemDebit.ctrlSubItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlSubItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlSubItemReference.name, itemNameSize);
			ctrlItemDebit.ctrlDetailItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlDetailItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemDebit.ctrlDetailItemReference.name, itemNameSize);
			ctrlItemDebit.ctrlItemReference.resize();
			ctrlItemDebit.ctrlSubItemReference.resize();
			ctrlItemDebit.ctrlDetailItemReference.resize();
			TGuiUtil.setComponentSize(ctrlItemDebit, itemSize);
			ctrlItemDebit.setLocation(x, y);
			pnlDebit.add(ctrlItemDebit);

			x = ctrlKCompanyDebit.getX() + ctrlKCompanyDebit.getWidth() + MARGIN_X;
			y = ctrlKCompanyDebit.getY();

			// �ʉ�
			TGuiUtil.setComponentSize(ctrlCurrencyDebit, comboSize);
			ctrlCurrencyDebit.setLocation(x, y);
			pnlDebit.add(ctrlCurrencyDebit);

			x += ctrlCurrencyDebit.getWidth() + MARGIN_X;

			// �ؕ����͋��z
			ctrlInputAmountDebit.setMaxLength(13, 4);
			ctrlInputAmountDebit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlInputAmountDebit, amountSize);
			ctrlInputAmountDebit.setLocation(x, y);
			pnlDebit.add(ctrlInputAmountDebit);

			x = ctrlCurrencyDebit.getX() - 5;
			y += ctrlCurrencyDebit.getHeight();

			// �ؕ����[�g
			ctrlRateDebit.setLabelSize(60);
			ctrlRateDebit.setLangMessageID("Ex.");
			ctrlRateDebit.setMaxLength(9, 4);
			ctrlRateDebit.setNumericFormat("#,##0.0000");
			TGuiUtil.setComponentSize(ctrlRateDebit.getField(), amountSize);
			TGuiUtil.setComponentWidth(ctrlRateDebit, 175);
			ctrlRateDebit.setPositiveOnly(true);
			ctrlRateDebit.setLocation(x, y);
			pnlDebit.add(ctrlRateDebit);

			x = ctrlInputAmountDebit.getX();
			y += ctrlRateDebit.getHeight();

			// �ؕ��M�݋��z
			ctrlKeyAmountDebit.setMaxLength(13, 4);
			ctrlKeyAmountDebit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlKeyAmountDebit, amountSize);
			ctrlKeyAmountDebit.setLocation(x, y);
			pnlDebit.add(ctrlKeyAmountDebit);

			x = ctrlInputAmountDebit.getX() - 30;
			y += ctrlKeyAmountDebit.getHeight();

			// �ؕ������
			ctrlTaxDebit.btn.setVisible(false);
			TGuiUtil.setComponentWidth(ctrlTaxDebit.code, 30);
			TGuiUtil.setComponentSize(ctrlTaxDebit.name, amountSize);
			ctrlTaxDebit.resize();
			ctrlTaxDebit.setLocation(x, y);
			pnlDebit.add(ctrlTaxDebit);

			x = ctrlCurrencyDebit.getX();
			y += ctrlTaxDebit.getHeight();

			// �ؕ��ŋ敪
			ctrlTaxDivisionDebit.setModel(modelTax);
			TGuiUtil.setComponentSize(this.ctrlTaxDivisionDebit, comboSize);
			ctrlTaxDivisionDebit.setLocation(x, y);
			pnlDebit.add(ctrlTaxDivisionDebit);

			x += ctrlTaxDivisionDebit.getWidth() + MARGIN_X;

			// �ؕ�����Ŋz
			ctrlTaxAmountDebit.setMaxLength(13, 4);
			ctrlTaxAmountDebit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlTaxAmountDebit, amountSize);
			ctrlTaxAmountDebit.setLocation(x, y);
			pnlDebit.add(ctrlTaxAmountDebit);

			y += ctrlTaxAmountDebit.getHeight();

			// �T�C�Y����
			pnlDebit.setSize(ctrlInputAmountDebit.getX() + ctrlInputAmountDebit.getWidth(), y);
		}

		pnlDebit.setLocation(pnlRowNo.getX() + pnlRowNo.getWidth(), Y_POINT);
		pnlDebit.setLayout(null);
		add(pnlDebit);

		{
			// �����p�l�����g

			x = X_POINT;
			y = Y_POINT;

			// �Ǘ����ڂ̃^�C�g��
			lblCenterTitle.setSize(304, 20);
			lblCenterTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblCenterTitle.setOpaque(true);
			lblCenterTitle.setLocation(x, y);
			lblCenterTitle.setBackground(Color.LIGHT_GRAY);
			lblCenterTitle.setLangMessageID("�Ǘ�����");
			pnlCenter.add(lblCenterTitle);

			y += lblCenterTitle.getHeight();

			// �����
			TGuiUtil.setComponentSize(ctrlCustomer.code, codeSize);
			TGuiUtil.setComponentSize(ctrlCustomer.name, nameSize);
			ctrlCustomer.resize();
			ctrlCustomer.setLocation(x, y);
			pnlCenter.add(ctrlCustomer);

			y += ctrlCustomer.getHeight();

			// �Ј�
			TGuiUtil.setComponentSize(ctrlEmployee.code, codeSize);
			TGuiUtil.setComponentSize(ctrlEmployee.name, nameSize);
			ctrlEmployee.resize();
			ctrlEmployee.setLocation(x, y);
			pnlCenter.add(ctrlEmployee);

			y += ctrlEmployee.getHeight();

			Company company = TLoginInfo.getCompany();
			if (company != null) {

				AccountConfig conf = company.getAccountConfig();

				if (conf.isUseManagement1()) {
					// �Ǘ�1
					TGuiUtil.setComponentSize(ctrlManage1.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage1.name, nameSize);
					ctrlManage1.resize();
					ctrlManage1.setLocation(x, y);
					pnlCenter.add(ctrlManage1);

					y += ctrlManage1.getHeight();
				}

				if (conf.isUseManagement2()) {
					// �Ǘ�2
					TGuiUtil.setComponentSize(ctrlManage2.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage2.name, nameSize);
					ctrlManage2.resize();
					ctrlManage2.setLocation(x, y);
					pnlCenter.add(ctrlManage2);

					y += ctrlManage2.getHeight();
				}

				if (conf.isUseManagement3()) {
					// �Ǘ�3
					TGuiUtil.setComponentSize(ctrlManage3.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage3.name, nameSize);
					ctrlManage3.resize();
					ctrlManage3.setLocation(x, y);
					pnlCenter.add(ctrlManage3);

					y += ctrlManage3.getHeight();
				}

				if (conf.isUseManagement4()) {
					// �Ǘ�4
					TGuiUtil.setComponentSize(ctrlManage4.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage4.name, nameSize);
					ctrlManage4.resize();
					ctrlManage4.setLocation(x, y);
					pnlCenter.add(ctrlManage4);

					y += ctrlManage4.getHeight();
				}

				if (conf.isUseManagement5()) {
					// �Ǘ�5
					TGuiUtil.setComponentSize(ctrlManage5.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage5.name, nameSize);
					ctrlManage5.resize();
					ctrlManage5.setLocation(x, y);
					pnlCenter.add(ctrlManage5);

					y += ctrlManage5.getHeight();
				}

				if (conf.isUseManagement6()) {
					// �Ǘ�6
					TGuiUtil.setComponentSize(ctrlManage6.code, codeSize);
					TGuiUtil.setComponentSize(ctrlManage6.name, nameSize);
					ctrlManage6.resize();
					ctrlManage6.setLocation(x, y);
					pnlCenter.add(ctrlManage6);

					y += ctrlManage6.getHeight();
				}
			}

			y = Math.max(y, 120); // �ŏ��l120
			pnlCenter.setSize(lblCenterTitle.getWidth() + 5, y);
		}

		// �����p�l�����W
		pnlCenter.setLocation(pnlDebit.getX() + pnlDebit.getWidth(), pnlDebit.getY());
		pnlCenter.setLayout(null);
		add(pnlCenter);

		{

			x = X_POINT;
			y = Y_POINT;

			// �ݕ��Ȗڂ̃^�C�g��
			lblCreditItemTitle.setSize(217, 20);
			lblCreditItemTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblCreditItemTitle.setOpaque(true);
			lblCreditItemTitle.setBackground(Color.LIGHT_GRAY);
			lblCreditItemTitle.setLocation(x, y);
			pnlCredit.add(lblCreditItemTitle);

			x += lblCreditItemTitle.getWidth() + MARGIN_X;

			// �ݕ����z�̃^�C�g��
			lblCreditAmountTitle.setSize(113 - MARGIN_X, 20);
			lblCreditAmountTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblCreditAmountTitle.setOpaque(true);
			lblCreditAmountTitle.setBackground(Color.LIGHT_GRAY);
			lblCreditAmountTitle.setLangMessageID("�ݕ����z");
			lblCreditAmountTitle.setLocation(x, y);
			pnlCredit.add(lblCreditAmountTitle);

			x = lblCreditItemTitle.getX();
			y += lblCreditItemTitle.getHeight();

			// �ݕ��v����
			ctrlKCompanyCredit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKCompanyCredit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKCompanyCredit.name, cmpNameSize);
			ctrlKCompanyCredit.resize();
			ctrlKCompanyCredit.setLocation(x, y);
			pnlCredit.add(ctrlKCompanyCredit);

			y += ctrlKCompanyCredit.getHeight();

			// �ݕ��v�㕔��
			ctrlKDepartmentCredit.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlKDepartmentCredit.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlKDepartmentCredit.name, cmpNameSize);
			ctrlKDepartmentCredit.resize();
			ctrlKDepartmentCredit.setLocation(x, y);
			pnlCredit.add(ctrlKDepartmentCredit);

			y += ctrlKDepartmentCredit.getHeight();

			// �ݕ��Ȗ�
			ctrlItemCredit.ctrlItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlItemReference.name, itemNameSize);
			ctrlItemCredit.ctrlSubItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlSubItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlSubItemReference.name, itemNameSize);
			ctrlItemCredit.ctrlDetailItemReference.btn.setVisible(false);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlDetailItemReference.code, itemCodeSize);
			TGuiUtil.setComponentSize(ctrlItemCredit.ctrlDetailItemReference.name, itemNameSize);
			ctrlItemCredit.ctrlItemReference.resize();
			ctrlItemCredit.ctrlSubItemReference.resize();
			ctrlItemCredit.ctrlDetailItemReference.resize();
			TGuiUtil.setComponentSize(ctrlItemCredit, itemSize);
			ctrlItemCredit.setLocation(x, y);
			pnlCredit.add(ctrlItemCredit);

			x = ctrlKCompanyCredit.getX() + ctrlKCompanyCredit.getWidth() + MARGIN_X;
			y = ctrlKCompanyCredit.getY();

			// �ʉ�
			TGuiUtil.setComponentSize(ctrlCurrencyCredit, comboSize);
			ctrlCurrencyCredit.setLocation(x, y);
			pnlCredit.add(ctrlCurrencyCredit);

			x += ctrlCurrencyCredit.getWidth() + MARGIN_X;

			// �ݕ����͋��z
			ctrlInputAmountCredit.setMaxLength(13, 4);
			ctrlInputAmountCredit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlInputAmountCredit, amountSize);
			ctrlInputAmountCredit.setLocation(x, y);
			pnlCredit.add(ctrlInputAmountCredit);

			x = ctrlCurrencyCredit.getX() - 5;
			y += ctrlCurrencyCredit.getHeight();

			// �ݕ����[�g
			ctrlRateCredit.setLabelSize(60);
			ctrlRateCredit.setLangMessageID("Ex.");
			ctrlRateCredit.setMaxLength(9, 4);
			ctrlRateCredit.setNumericFormat("#,##0.0000");
			TGuiUtil.setComponentSize(ctrlRateCredit.getField(), amountSize);
			TGuiUtil.setComponentWidth(ctrlRateCredit, 175);
			ctrlRateCredit.setPositiveOnly(true);
			ctrlRateCredit.setLocation(x, y);
			pnlCredit.add(ctrlRateCredit);

			x = ctrlInputAmountCredit.getX();
			y += ctrlRateCredit.getHeight();

			// �ݕ��M�݋��z
			ctrlKeyAmountCredit.setMaxLength(13, 4);
			ctrlKeyAmountCredit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlKeyAmountCredit, amountSize);
			ctrlKeyAmountCredit.setLocation(x, y);
			pnlCredit.add(ctrlKeyAmountCredit);

			x = ctrlInputAmountCredit.getX() - 30;
			y += ctrlKeyAmountCredit.getHeight();

			// �ݕ������
			ctrlTaxCredit.btn.setVisible(false);
			TGuiUtil.setComponentWidth(ctrlTaxCredit.code, 30);
			TGuiUtil.setComponentSize(ctrlTaxCredit.name, amountSize);
			ctrlTaxCredit.resize();
			ctrlTaxCredit.setLocation(x, y);
			pnlCredit.add(ctrlTaxCredit);

			x = ctrlCurrencyCredit.getX();
			y += ctrlTaxCredit.getHeight();

			// �ݕ��ŋ敪
			ctrlTaxDivisionCredit.setModel(modelTax);
			TGuiUtil.setComponentSize(this.ctrlTaxDivisionCredit, comboSize);
			ctrlTaxDivisionCredit.setLocation(x, y);
			pnlCredit.add(ctrlTaxDivisionCredit);

			x += ctrlTaxDivisionCredit.getWidth() + MARGIN_X;

			// �ݕ�����Ŋz
			ctrlTaxAmountCredit.setMaxLength(13, 4);
			ctrlTaxAmountCredit.setChangeRedOfMinus(true);
			TGuiUtil.setComponentSize(ctrlTaxAmountCredit, amountSize);
			ctrlTaxAmountCredit.setLocation(x, y);
			pnlCredit.add(ctrlTaxAmountCredit);

			y += ctrlTaxAmountCredit.getHeight();

			// �T�C�Y����
			pnlCredit.setSize(ctrlInputAmountCredit.getX() + ctrlInputAmountCredit.getWidth(), y);
		}

		pnlCredit.setLocation(pnlCenter.getX() + pnlCenter.getWidth(), pnlDebit.getY());
		pnlCredit.setLayout(null);
		add(pnlCredit);

		x = X_POINT;
		y = 0;

		// �s�ړ��w��
		ctrlMoveCheck.setLangMessageID("�s�ړ�");
		ctrlMoveCheck.setEnterFocusable(false);
		ctrlMoveCheck.setOpaque(false);
		TGuiUtil.setComponentSize(ctrlMoveCheck, new Dimension(150, 20));
		ctrlMoveCheck.setLocation(x, y);
		pnlButton.add(ctrlMoveCheck);

		x += ctrlMoveCheck.getWidth() + MARGIN_X;

		// �^�C�g��
		TGuiUtil.setComponentSize(lblTitle, 175, 20);
		lblTitle.setForeground(Color.blue);
		lblTitle.setLocation(x, y);
		pnlButton.add(lblTitle);

		x = X_POINT + pnlDebit.getWidth();
		y = 0;

		// �s�E�v
		TGuiUtil.setComponentSize(ctrlRemark.code, codeSize);
		TGuiUtil.setComponentSize(ctrlRemark.name, new Dimension(501, 20));
		ctrlRemark.resize();
		ctrlRemark.setLocation(x, y);
		pnlButton.add(ctrlRemark);

		x = X_POINT;
		y = Y_POINT + ctrlRemark.getHeight();

		// �s����
		btnRowCopy.setLangMessageID("�s����");
		btnRowCopy.setLocation(x, y);
		TGuiUtil.setComponentSize(btnRowCopy, buttonSize);
		pnlButton.add(btnRowCopy);

		x += btnRowCopy.getWidth() + MARGIN_X;

		// ���]����
		btnRowCopyReverse.setLangMessageID("���]����");
		TGuiUtil.setComponentSize(btnRowCopyReverse, buttonSize);
		btnRowCopyReverse.setLocation(x, y);
		pnlButton.add(btnRowCopyReverse);

		x += btnRowCopyReverse.getWidth() + MARGIN_X;

		// �s�ǉ�
		btnRowNew.setLangMessageID("�s�ǉ�");
		TGuiUtil.setComponentSize(btnRowNew, buttonSize);
		btnRowNew.setLocation(x, y);
		pnlButton.add(btnRowNew);

		x += btnRowNew.getWidth() + MARGIN_X;

		// �s�폜
		btnRowDelete.setLangMessageID("�s�폜");
		TGuiUtil.setComponentSize(btnRowDelete, buttonSize);
		btnRowDelete.setLocation(x, y);
		pnlButton.add(btnRowDelete);

		x += btnRowDelete.getWidth() + MARGIN_X;

		// ������
		ctrlOccurDate.setLabelSize(75);
		ctrlOccurDate.setLangMessageID("C00431");
		ctrlOccurDate.setLocation(x, y);
		pnlButton.add(ctrlOccurDate);

		x += ctrlOccurDate.getWidth();

		// ���v����
		TPanel hm1 = ctrlNonAcDetails.getHmField(1);
		TPanel hm2 = ctrlNonAcDetails.getHmField(2);
		TPanel hm3 = ctrlNonAcDetails.getHmField(3);

		if (hm1 != null && hm1.isVisible()) {
			((ILabelField) hm1).setLabelSize(55);

			hm1.setLocation(x, y);
			pnlButton.add(hm1);

			x += hm1.getWidth();
		}

		if (hm2 != null && hm2.isVisible()) {
			((ILabelField) hm2).setLabelSize(55);

			hm2.setLocation(x, y);
			pnlButton.add(hm2);

			x += hm2.getWidth();
		}

		if (hm3 != null && hm3.isVisible()) {
			((ILabelField) hm3).setLabelSize(55);

			hm3.setLocation(x, y);
			pnlButton.add(hm3);

			x += hm3.getWidth();
		}

		// // �{�^��
		// x = 993 - buttonSize.height * 4 - MARGIN_X * 4 - 30; // ���ӁF�]���o�O�F�{�^���͕��A�����t
		// y = ctrlMoveCheck.getY() + 2;

		int maxY = Math.max(pnlDebit.getY() + pnlDebit.getHeight(), pnlCenter.getY() + pnlCenter.getHeight());

		pnlButton.setLayout(null);
		pnlButton.setLocation(pnlDebit.getX(), maxY);
		pnlButton.setSize(993, 50);
		add(pnlButton);

		// �T�C�Y������
		int maxHeight = Math.max(pnlCenter.getHeight() + 53, 140);
		setSize(993, maxHeight);

		// �c���x��������
		lblRowNo.setSize(lblRowNo.getWidth(), maxHeight - 2);
		pnlRowNo.setSize(pnlRowNo.getWidth(), maxHeight);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 * @return �ŏI�ԍ�
	 */
	public int setTabControlNo(int tabControlNo) {

		ctrlKCompany[DR].setTabControlNo(tabControlNo++);
		ctrlKDepartment[DR].setTabControlNo(tabControlNo++);
		ctrlItem[DR].setTabControlNo(tabControlNo++);
		ctrlCurrency[DR].setTabControlNo(tabControlNo++);
		ctrlInputAmount[DR].setTabControlNo(tabControlNo++);
		ctrlRate[DR].setTabControlNo(tabControlNo++);
		ctrlKeyAmount[DR].setTabControlNo(tabControlNo++);
		ctrlTax[DR].setTabControlNo(tabControlNo++);
		ctrlTaxDivision[DR].setTabControlNo(tabControlNo++);
		ctrlTaxAmount[DR].setTabControlNo(tabControlNo++);

		ctrlKCompany[CR].setTabControlNo(tabControlNo++);
		ctrlKDepartment[CR].setTabControlNo(tabControlNo++);
		ctrlItem[CR].setTabControlNo(tabControlNo++);
		ctrlCurrency[CR].setTabControlNo(tabControlNo++);
		ctrlInputAmount[CR].setTabControlNo(tabControlNo++);
		ctrlRate[CR].setTabControlNo(tabControlNo++);
		ctrlKeyAmount[CR].setTabControlNo(tabControlNo++);
		ctrlTax[CR].setTabControlNo(tabControlNo++);
		ctrlTaxDivision[CR].setTabControlNo(tabControlNo++);
		ctrlTaxAmount[CR].setTabControlNo(tabControlNo++);

		ctrlCustomer.setTabControlNo(tabControlNo++);
		ctrlEmployee.setTabControlNo(tabControlNo++);
		ctrlManage1.setTabControlNo(tabControlNo++);
		ctrlManage2.setTabControlNo(tabControlNo++);
		ctrlManage3.setTabControlNo(tabControlNo++);
		ctrlManage4.setTabControlNo(tabControlNo++);
		ctrlManage5.setTabControlNo(tabControlNo++);
		ctrlManage6.setTabControlNo(tabControlNo++);
		ctrlRemark.setTabControlNo(tabControlNo++);
		ctrlOccurDate.setTabControlNo(tabControlNo++);
		ctrlNonAcDetails.setTabControlNo(tabControlNo++);

		btnRowCopy.setTabControlNo(tabControlNo++);
		btnRowCopyReverse.setTabControlNo(tabControlNo++);
		btnRowNew.setTabControlNo(tabControlNo++);
		btnRowDelete.setTabControlNo(tabControlNo++);

		return tabControlNo;
	}

	/**
	 * �����\��
	 */
	public void init() {
		this.controller.init();
	}

	/**
	 * �ʉݐݒ�
	 * 
	 * @param currency �ʉ�
	 */
	public void setCurrecy(Currency currency) {
		controller.setCurrecy(currency, DR);
		controller.setCurrecy(currency, CR);
	}

	/**
	 * ���ׂ�Entity�`���ŃZ�b�g����.
	 * 
	 * @param dtl �f�[�^
	 */
	public void setEntity(SWK_DTL dtl) {
		controller.setEntity(dtl);
	}

	/**
	 * ���ׂ�Entity�`���ŕԂ�.
	 * 
	 * @return Entity
	 */
	public SWK_DTL getEntity() {
		return controller.getEntity();
	}

	/**
	 * ����ݒ�
	 * 
	 * @param baseDate ���
	 */
	public void setBaseDate(Date baseDate) {
		controller.setBaseDate(baseDate);
	}

	/**
	 * ���Z�d��ݒ�
	 * 
	 * @param isClosing true:���Z�d��Afalse:�ʏ�d��
	 */
	public void setClosingEntry(boolean isClosing) {
		controller.setClosingEntry(isClosing);
	}

	/**
	 * �O���w�������ݒ�(�Œ�\���p)
	 * 
	 * @param customer �O���w������
	 */
	public void setCustomer(Customer customer) {
		controller.setCustomer(customer);
	}

	/**
	 * �O���w��Ј���ݒ�(�����\���p)
	 * 
	 * @param employee �Ј�
	 */
	public void setEmployee(Employee employee) {
		controller.setEmployee(employee);
	}

	/**
	 * �f�t�H���g�����ł�
	 * 
	 * @param defaultTaxInnerDivision true:����
	 */
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		controller.setDefaultTaxInnerDivision(defaultTaxInnerDivision);
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	public boolean checkInput() {
		return controller.checkInput();
	}

	/**
	 * �ؕ��M�݋��z���擾����
	 * 
	 * @return �ؕ��M�݋��z
	 */
	public BigDecimal getDebitKeyAmount() {
		return controller.getDebitKeyAmount();
	}

	/**
	 * �ݕ��M�݋��z���擾����
	 * 
	 * @return �ݕ��M�݋��z
	 */
	public BigDecimal getCreditKeyAmount() {
		return controller.getCreditKeyAmount();
	}

	/**
	 * �ؕ��M�ݏ���Ŋz���擾����
	 * 
	 * @return �ؕ��M�ݏ���Ŋz
	 */
	public BigDecimal getDebitTaxAmount() {
		return controller.getDebitTaxAmount();
	}

	/**
	 * �ݕ��M�ݏ���Ŋz���擾����
	 * 
	 * @return �ݕ��M�ݏ���Ŋz
	 */
	public BigDecimal getCreditTaxAmount() {
		return controller.getCreditTaxAmount();
	}

	/**
	 * �ؕ����͏���Ŋz���擾����
	 * 
	 * @return �ؕ��M�ݏ���Ŋz
	 */
	public BigDecimal getDebitTaxInputAmount() {
		return controller.getDebitTaxInputAmount();
	}

	/**
	 * �ݕ����͏���Ŋz���擾����
	 * 
	 * @return �ݕ��M�ݏ���Ŋz
	 */
	public BigDecimal getCreditTaxInputAmount() {
		return controller.getCreditTaxInputAmount();
	}

	/**
	 * �ؕ����͋��z���擾����
	 * 
	 * @return �ؕ����͋��z
	 */
	public BigDecimal getDebitInputAmount() {
		return controller.getDebitInputAmount();
	}

	/**
	 * �ݕ����͋��z���擾����
	 * 
	 * @return �ݕ����͋��z
	 */
	public BigDecimal getCreditInputAmount() {
		return controller.getCreditInputAmount();
	}

	/**
	 * �ؕ��ʉ݃R�[�h���擾����
	 * 
	 * @return �ؕ��ʉ݃R�[�h
	 */
	public Currency getDebitCurrency() {
		return controller.getDebitCurrency();
	}

	/**
	 * �ݕ��ʉ݃R�[�h���擾����
	 * 
	 * @return �ݕ��ʉ݃R�[�h
	 */
	public Currency getCreditCurrency() {
		return controller.getCreditCurrency();
	}

	/**
	 * �ؕ�����Ŋz���܂߂邩
	 * 
	 * @return �܂߂�:true
	 */
	public boolean isDebitTaxInclusive() {
		return controller.isDebitTaxInclusive();
	}

	/**
	 * �ݕ�����Ŋz���܂߂邩
	 * 
	 * @return �܂߂�:true
	 */
	public boolean isCreditTaxInclusive() {
		return controller.isCreditTaxInclusive();
	}

	/**
	 * callBackListener��ǉ�����
	 * 
	 * @param callBackListener callBackListener
	 */
	public void addCallBackListener(TCallBackListener callBackListener) {
		controller.addCallBackListener(callBackListener);
	}

	/**
	 * ���׃f�[�^�̒ʉ݃��X�g�쐬
	 * 
	 * @param list �ʉ݃��X�g
	 */
	public void setCurrecyList(List<Currency> list) {
		controller.setCurrecyList(list);
	}

	/**
	 * �s�ԍ��̐ݒ�
	 * 
	 * @param rowNo �s�ԍ�
	 */
	public void setRowNo(int rowNo) {
		lblRowNo.setLangMessageID(String.valueOf(rowNo));

		// �s�ԍ��ɂ��F�؂�ւ���
		if (rowNo % 2 == 0) {
			// �����s
			lblRowNo.setBackground(row1Color);
		} else {
			lblRowNo.setBackground(row2Color);
		}
	}
}
