package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[���׃R���|�[�l���g
 */
public class TSlipDetailPanel extends TPanel {

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {

		/** Entity */
		bean,

		/** �v���ЃR�[�h */
		kCompCode,

		/** �v���� */
		kCompName,

		/** ����R�[�h */
		depCode,

		/** ���� */
		depName,

		/** �ȖڃR�[�h */
		itemCode,

		/** �Ȗ� */
		itemName,

		/** �⏕�R�[�h */
		subItemCode,

		/** �⏕ */
		subItemName,

		/** ����ȖڃR�[�h */
		dtlItemCode,

		/** ���� */
		dtlItemName,

		/** �� */
		taxDivision,

		/** ����ŃR�[�h */
		taxCode,

		/** ����Ŗ��� */
		taxName,

		/** �ŗ� */
		taxRate,

		/** ���z */
		amount,

		/** �ʉ݃R�[�h */
		currency,

		/** �ʉ݃��[�g */
		currencyRate,

		/** �ؕ����z(�O��) */
		drFAmount,

		/** �ؕ����z */
		drAmount,

		/** ����Ŋz(�O��) */
		taxFAmount,

		/** ����Ŋz */
		taxAmount,

		/** �ݕ����z(�O��) */
		crFAmount,

		/** �ݕ����z */
		crAmount,

		/** �s�E�v�R�[�h */
		tekCode,

		/** �s�E�v */
		tek,

		/** �����R�[�h */
		customerCode,

		/** ����� */
		customerName,

		/** �Ј��R�[�h */
		empCode,

		/** �Ј� */
		empName,

		/** �Ǘ�1�R�[�h */
		mng1Code,

		/** �Ǘ�1 */
		mng1Name,

		/** �Ǘ�2�R�[�h */
		mng2Code,

		/** �Ǘ�2 */
		mng2Name,

		/** �Ǘ�3�R�[�h */
		mng3Code,

		/** �Ǘ�3 */
		mng3Name,

		/** �Ǘ�4�R�[�h */
		mng4Code,

		/** �Ǘ�4 */
		mng4Name,

		/** �Ǘ�5�R�[�h */
		mng5Code,

		/** �Ǘ�5 */
		mng5Name,

		/** �Ǘ�6�R�[�h */
		mng6Code,

		/** �Ǘ�6 */
		mng6Name,

		/** ������ */
		issuerDay,

		/** ���v����1 */
		nonAcDtl1,

		/** ���v����2 */
		nonAcDtl2,

		/** ���v����3 */
		nonAcDtl3,

		/** ���͋��z */
		inputAmount;
	}

	/** ���J�n�ʒu */
	protected int X_POINT;

	/** ���͕��p�l�� */
	public TPanel pnlInput;

	/** ���i�R���g���[�� */
	public TSlipDetailPanelCtrl controller;

	/** �v���� */
	public TCompanyReference ctrlKCompany;

	/** �v�㕔�� */
	public TDepartmentReference ctrlKDepartment;

	/** �Ȗ� */
	public TItemGroup ctrlItem;

	/** ������ */
	public TLabelPopupCalendar ctrlOccurDate;

	/** �ʉ� */
	public TCurrencyReference ctrlCurrency;

	/** ���[�g */
	public TLabelNumericField ctrlRate;

	/** �ŋ敪 */
	public TTaxReference ctrlTax;

	/** �v�Z */
	public TRadioPanel ctrlTaxDivision;

	/** �s�E�v */
	public TRemarkReference ctrlRemark;

	/** �ݎ؃p�l�� */
	public TDrCrPanel ctrlDrCr;

	/** ���͋��z */
	public TLabelNumericField ctrlInputAmount;

	/** ���͏���Ŋz */
	public TLabelNumericField ctrlTaxAmount;

	/** ��ʉ݋��z */
	public TLabelNumericField ctrlKeyAmount;

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

	/** ���v���� */
	public TNonAccountintDetailUnit ctrlNonAcDetails;

	/** �V�K�s */
	public TButton btnRowNew;

	/** �s�}�� */
	public TButton btnRowInsert;

	/** �s���� */
	public TButton btnRowCopy;

	/** �s�폜 */
	public TButton btnRowDelete;

	/** �s�m�� */
	public TButton btnRowEntry;

	/** ���׃_�E�����[�h�{�^�� */
	public TButton btnDownload;

	/** ���׃A�b�v���[�h�{�^�� */
	public TButton btnUpload;

	/** �X�v���b�h�ŏ�ʍs�ړ��{�^�� */
	public TButton btnRowTop;

	/** �X�v���b�h��s�ړ��{�^�� */
	public TButton btnRowUp;

	/** �X�v���b�h���s�ړ��{�^�� */
	public TButton btnRowDown;

	/** �X�v���b�h�ŉ��ʍs�ړ��{�^�� */
	public TButton btnRowUnder;

	/** �X�v���b�h */
	public TTable tbl;

	/** �ؕ��O�ݍ��v */
	public TLabelNumericField ctrlFcDrTotal;

	/** �ݕ��O�ݍ��v */
	public TLabelNumericField ctrlFcCrTotal;

	/** ���z(�O��) */
	public TLabelNumericField ctrlFcDiff;

	/** ���v���O�ݒʉ� */
	public TComboBox cbxCurrencyOnTotal;

	/** �ؕ����z���v */
	public TLabelNumericField ctrlDrTotal;

	/** �ݕ����z���v */
	public TLabelNumericField ctrlCrTotal;

	/** �ב֍� */
	public TLabelNumericField ctrlExchangeDiff;

	/** �ؕ�����Ŋz���v */
	public TLabelNumericField ctrlDrTaxTotal;

	/** �ݕ�����Ŋz���v */
	public TLabelNumericField ctrlCrTaxTotal;

	/** ���z */
	public TLabelNumericField ctrlDiff;

	/** ���c�� */
	public TButton btnAR;

	/** ���c�� */
	public TButton btnAP;

	/** ���v�p�l�� */
	public TPanel pnlTotalAmount;

	/** �X�v���b�h�p�l�� */
	public TPanel pnlSpreadSheet;

	/** �X�v���b�h�㉺�{�^���p�l�� */
	public TPanel pnlSsUDPanel;

	/** ���׃{�^���p�l�� */
	public TPanel pnlDtlButtons;

	/** �ǉ��{�^���p�p�l�� */
	public TPanel pnlFreeButtons;

	/** �X�v���b�h�p�{�^�� */
	public TPanel pnlRowButtons;

	/** �X�v���b�g�y�C���\���t���O */
	public boolean splitPaneFlg = ClientConfig.isFlagOn("trans.slip.splitpane");

	/** ��������YYYY/MM�Ƃ��锻��t���O */
	public boolean isHasDateYM = ClientConfig.isFlagOn("trans.slip.use.has.date.ym");

	/** BS���� */
	public TButton btnBS;

	/** �e */
	public TSlipPanel parent;

	/**
	 * �R���X�g���N�^.
	 */
	public TSlipDetailPanel() {
		this(null);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e
	 */
	public TSlipDetailPanel(TSlipPanel parent) {
		this.parent = parent;

		initComponents();
		allocateSlipDetail();

		controller = createController();
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return �R���g���[���[
	 */
	protected TSlipDetailPanelCtrl createController() {
		return new TSlipDetailPanelCtrl(this);
	}

	/**
	 * �R���g���[���擾
	 * 
	 * @return �R���g���[��
	 */
	public TSlipDetailPanelCtrl getController() {
		return controller;
	}

	/**
	 * �R���|�[�l���g�쐬
	 */
	public void initComponents() {
		X_POINT = 5;

		ctrlKCompany = new TCompanyReference();
		ctrlKDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();
		ctrlRemark = new TRemarkReference();

		if (isHasDateYM) {
			ctrlOccurDate = new TLabelPopupCalendar(TPopupCalendar.TYPE_YM);
		} else {
			ctrlOccurDate = new TLabelPopupCalendar();
		}

		// ���Z�R���|��
		ctrlCurrency = new TCurrencyReference();
		ctrlRate = new TLabelNumericField();
		ctrlTax = new TTaxReference();
		ctrlTaxDivision = new TRadioPanel("C01175");

		ctrlDrCr = new TDrCrPanel();

		ctrlInputAmount = new TLabelNumericField();
		ctrlTaxAmount = new TLabelNumericField();
		ctrlKeyAmount = new TLabelNumericField();

		ctrlCustomer = new TCustomerReference();
		ctrlEmployee = new TEmployeeReference();
		ctrlManage1 = new TManagement1Reference();
		ctrlManage2 = new TManagement2Reference();
		ctrlManage3 = new TManagement3Reference();
		ctrlManage4 = new TManagement4Reference();
		ctrlManage5 = new TManagement5Reference();
		ctrlManage6 = new TManagement6Reference();

		ctrlNonAcDetails = new TNonAccountintDetailUnit();

		pnlDtlButtons = new TPanel();
		pnlFreeButtons = new TPanel();
		pnlRowButtons = new TPanel();

		btnAR = new TButton();
		btnAP = new TButton();

		btnRowNew = new TButton();
		btnRowInsert = new TButton();
		btnRowCopy = new TButton();
		btnRowDelete = new TButton();
		btnRowEntry = new TButton();

		btnDownload = new TButton();
		btnUpload = new TButton();

		btnRowTop = new TButton();
		btnRowUp = new TButton();
		btnRowDown = new TButton();
		btnRowUnder = new TButton();
		tbl = new TTable();
		pnlSpreadSheet = new TPanel();

		pnlSsUDPanel = new TPanel();
		pnlTotalAmount = new TPanel();

		// �O��
		ctrlFcDrTotal = new TLabelNumericField();
		ctrlFcCrTotal = new TLabelNumericField();
		ctrlFcDiff = new TLabelNumericField();
		cbxCurrencyOnTotal = new TComboBox();

		// �M��
		ctrlDrTotal = new TLabelNumericField();
		ctrlExchangeDiff = new TLabelNumericField();
		ctrlCrTotal = new TLabelNumericField();

		// �����
		ctrlDrTaxTotal = new TLabelNumericField();
		ctrlCrTaxTotal = new TLabelNumericField();
		ctrlDiff = new TLabelNumericField();

		// BS����
		btnBS = new TButton();

	}

	/**
	 * ���ׂ̔z�u
	 */
	public void allocateSlipDetail() {

		TPanel pnlCenter = new TPanel(new GridBagLayout());

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			this.setLayout(new BorderLayout());

		} else {
			this.setLayout(new GridBagLayout());
		}

		pnlInput = new TPanel();
		pnlInput.setLayout(null);

		TGuiUtil.setComponentSize(pnlInput, new Dimension(0, 250));

		// �v����
		ctrlKCompany.btn.setLangMessageID("C01052");
		ctrlKCompany.setLocation(X_POINT, 5);
		pnlInput.add(ctrlKCompany);

		// �v�㕔��
		ctrlKDepartment.btn.setLangMessageID("C00863");
		ctrlKDepartment.setLocation(X_POINT, ctrlKCompany.getY() + ctrlKCompany.getHeight());
		pnlInput.add(ctrlKDepartment);

		// �Ȗ�
		ctrlItem.setLocation(X_POINT, ctrlKDepartment.getY() + ctrlKDepartment.getHeight());
		pnlInput.add(ctrlItem);

		// ������
		int HAS_X = ctrlKCompany.getX() + ctrlKCompany.getWidth() + 1;
		ctrlOccurDate.setLabelSize(75);
		ctrlOccurDate.setLangMessageID("C00431");
		ctrlOccurDate.setLocation(HAS_X, ctrlKCompany.getY());
		pnlInput.add(ctrlOccurDate);

		// �ʉ�
		ctrlCurrency.name.setVisible(false);
		ctrlCurrency.resize();
		ctrlCurrency.setLocation(HAS_X, ctrlOccurDate.getY() + ctrlOccurDate.getHeight());
		pnlInput.add(ctrlCurrency);

		// ���[�g
		ctrlRate.setLabelSize(50);
		ctrlRate.setLangMessageID("C00556");
		ctrlRate.setMaxLength(13, 4);
		ctrlRate.setPositiveOnly(true);
		ctrlRate.setLocation(ctrlCurrency.getX() + ctrlCurrency.getWidth() + 10, ctrlCurrency.getY());
		pnlInput.add(ctrlRate);

		// �ŋ敪
		ctrlTax.setLocation(HAS_X, ctrlCurrency.getY() + ctrlCurrency.getHeight());
		pnlInput.add(ctrlTax);

		// ����Ōv�Z
		TGuiUtil.setComponentSize(ctrlTaxDivision, new Dimension(0, 40));
		ctrlTaxDivision.addRadioButton("C00023", 70);
		ctrlTaxDivision.addRadioButton("C00337", 70);
		ctrlTaxDivision.setLocation(HAS_X, ctrlTax.getY() + ctrlTax.getHeight());
		pnlInput.add(ctrlTaxDivision);

		// �s�E�v
		ctrlRemark.setLocation(X_POINT, ctrlTaxDivision.getY() + ctrlTaxDivision.getHeight());
		pnlInput.add(ctrlRemark);

		// �ݎ؃p�l��
		ctrlDrCr.setLocation(X_POINT, ctrlRemark.getY() + ctrlRemark.getHeight() + 1);
		pnlInput.add(ctrlDrCr);

		// ���͋��z
		ctrlInputAmount.setLabelSize(60);
		ctrlInputAmount.setLangMessageID("C00574");
		ctrlInputAmount.setMaxLength(13, 4);
		ctrlInputAmount.setChangeRedOfMinus(true);
		ctrlInputAmount.setLocation(ctrlDrCr.getX() + ctrlDrCr.getWidth() + 2, ctrlDrCr.getY() + 15);
		pnlInput.add(ctrlInputAmount);

		// ���͏���Ŋz
		ctrlTaxAmount.setLabelSize(85);
		ctrlTaxAmount.setLangMessageID("C00575");
		ctrlTaxAmount.setMaxLength(13, 4);
		ctrlTaxAmount.setChangeRedOfMinus(true);
		ctrlTaxAmount.setLocation(ctrlInputAmount.getX() + ctrlInputAmount.getWidth() + 3, ctrlInputAmount.getY());
		pnlInput.add(ctrlTaxAmount);

		// ��ʉ݋��z
		ctrlKeyAmount.setLabelSize(80);
		ctrlKeyAmount.setLangMessageID("C00576");
		ctrlKeyAmount.setMaxLength(13, 4);
		ctrlKeyAmount.setChangeRedOfMinus(true);
		ctrlKeyAmount.setLocation(ctrlTaxAmount.getX() + ctrlTaxAmount.getWidth() + 1, ctrlInputAmount.getY());
		pnlInput.add(ctrlKeyAmount);

		// �����
		ctrlCustomer.setLocation(X_POINT, ctrlDrCr.getY() + ctrlDrCr.getHeight() + 1);
		pnlInput.add(ctrlCustomer);

		// �Ј�
		ctrlEmployee.setLocation(X_POINT, ctrlCustomer.getY() + ctrlCustomer.getHeight());
		pnlInput.add(ctrlEmployee);

		// �Ǘ�1
		ctrlManage1.setLocation(X_POINT, ctrlEmployee.getY() + ctrlEmployee.getHeight());
		pnlInput.add(ctrlManage1);

		// �Ǘ�2
		ctrlManage2.setLocation(X_POINT, ctrlManage1.getY() + ctrlManage1.getHeight());
		pnlInput.add(ctrlManage2);

		// �Ǘ�3
		ctrlManage3.setLocation(ctrlCustomer.getX() + ctrlCustomer.getWidth() + 1, ctrlCustomer.getY());
		pnlInput.add(ctrlManage3);

		// �Ǘ�4
		ctrlManage4.setLocation(ctrlEmployee.getX() + ctrlEmployee.getWidth() + 1, ctrlEmployee.getY());
		pnlInput.add(ctrlManage4);

		// �Ǘ�5
		ctrlManage5.setLocation(ctrlManage1.getX() + ctrlManage1.getWidth() + 1, ctrlManage1.getY());
		pnlInput.add(ctrlManage5);

		// �Ǘ�6
		ctrlManage6.setLocation(ctrlManage2.getX() + ctrlManage2.getWidth() + 1, ctrlManage2.getY());
		pnlInput.add(ctrlManage6);

		// ���v����
		ctrlNonAcDetails.setLocation(ctrlManage3.getX() + ctrlManage3.getWidth() + 1, ctrlCustomer.getY());
		pnlInput.add(ctrlNonAcDetails);

		GridBagConstraints gc_ = new GridBagConstraints();
		gc_.weightx = 1.0d;

		gc_.gridx = 0;
		gc_.gridy = 0;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.HORIZONTAL;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			pnlCenter.add(pnlInput, gc_);
		} else {
			this.add(pnlInput, gc_);
		}

		// ���׃{�^���p�l��
		pnlDtlButtons.setLayout(new BoxLayout(pnlDtlButtons, BoxLayout.X_AXIS));
		TGuiUtil.setComponentSize(pnlDtlButtons, new Dimension(0, 22));

		// �ǉ��{�^���p�p�l��
		pnlFreeButtons.setLayout(new BoxLayout(pnlFreeButtons, BoxLayout.X_AXIS));
		TGuiUtil.setComponentSize(pnlFreeButtons, new Dimension(300, 30));

		pnlDtlButtons.add(pnlFreeButtons);

		// ���c��
		btnAR.setLangMessageID("C01080");
		TGuiUtil.setComponentSize(btnAR, new Dimension(100, 20));
		pnlFreeButtons.add(btnAR);

		// ���c��
		btnAP.setLangMessageID("C01084");
		TGuiUtil.setComponentSize(btnAP, new Dimension(100, 20));
		pnlFreeButtons.add(btnAP);

		// BS����
		btnBS.setLangMessageID("C04291");
		TGuiUtil.setComponentSize(btnBS, new Dimension(100, 20));
		pnlFreeButtons.add(btnBS);

		// �X�v���b�h�p�{�^��
		pnlRowButtons.setLayout(new BoxLayout(pnlRowButtons, BoxLayout.X_AXIS));
		TGuiUtil.setComponentSize(pnlRowButtons, new Dimension(860, 30));

		pnlDtlButtons.add(pnlRowButtons);

		// �V�K�s
		btnRowNew.setLangMessageID("C01744");
		btnRowNew.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F2);
		TGuiUtil.setComponentSize(btnRowNew, new Dimension(120, 20));
		pnlRowButtons.add(btnRowNew);

		// �s�}��
		btnRowInsert.setLangMessageID("C11604");
		btnRowInsert.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F3);
		TGuiUtil.setComponentSize(btnRowInsert, new Dimension(120, 20));
		pnlRowButtons.add(btnRowInsert);

		// �s����
		btnRowCopy.setLangMessageID("C01745");
		btnRowCopy.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F4);
		TGuiUtil.setComponentSize(btnRowCopy, new Dimension(120, 20));
		pnlRowButtons.add(btnRowCopy);

		// �s�폜
		btnRowDelete.setLangMessageID("C01071");
		btnRowDelete.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F5);
		TGuiUtil.setComponentSize(btnRowDelete, new Dimension(120, 20));
		pnlRowButtons.add(btnRowDelete);

		// �s�m��
		btnRowEntry.setLangMessageID("C01746");
		btnRowEntry.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F6);
		btnRowEntry.setEnterFocusable(true);
		TGuiUtil.setComponentSize(btnRowEntry, new Dimension(120, 20));
		pnlRowButtons.add(btnRowEntry);

		// �_�E�����[�h�{�^��
		btnDownload.setLangMessageID("C11898");
		btnDownload.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F7);
		TGuiUtil.setComponentSize(btnDownload, new Dimension(120, 20));
		pnlRowButtons.add(btnDownload);

		// �A�b�v���[�h�{�^��
		btnUpload.setLangMessageID("C11899");
		btnUpload.setShortcutKey(KeyEvent.VK_CONTROL, KeyEvent.VK_F8);
		TGuiUtil.setComponentSize(btnUpload, new Dimension(120, 20));
		pnlRowButtons.add(btnUpload);

		gc_.gridx = 0;
		gc_.gridy = 1;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.HORIZONTAL;
		gc_.insets = new Insets(0, X_POINT, 0, 0);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			pnlCenter.add(pnlDtlButtons, gc_);
		} else {
			this.add(pnlDtlButtons, gc_);
		}

		// �X�v���b�h�p�l��
		pnlSpreadSheet.setLayout(new BorderLayout());

		// �X�v���b�h�㉺�{�^���p�l��
		pnlSsUDPanel.setLayout(new GridBagLayout());

		TGuiUtil.setComponentSize(pnlSsUDPanel, new Dimension(30, 0));

		// �{�^��
		TGuiUtil.setComponentSize(btnRowTop, new Dimension(20, 20));
		TGuiUtil.setComponentSize(btnRowUp, new Dimension(20, 20));
		TGuiUtil.setComponentSize(btnRowDown, new Dimension(20, 20));
		TGuiUtil.setComponentSize(btnRowUnder, new Dimension(20, 20));

		btnRowTop.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowTop.png"));
		btnRowUp.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUp.png"));
		btnRowDown.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowDown.png"));
		btnRowUnder.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUnder.png"));

		GridBagConstraints gcb = new GridBagConstraints();
		gcb.insets = new Insets(0, 2, 0, 0);

		gcb.gridy = 0;
		pnlSsUDPanel.add(btnRowTop, gcb);
		gcb.gridy = 1;
		pnlSsUDPanel.add(btnRowUp, gcb);
		gcb.gridy = 2;
		pnlSsUDPanel.add(btnRowDown, gcb);
		gcb.gridy = 3;
		pnlSsUDPanel.add(btnRowUnder, gcb);

		pnlSpreadSheet.add(pnlSsUDPanel, BorderLayout.WEST);

		tbl.setSortOff();
		TGuiUtil.setComponentSize(tbl, new Dimension(0, 0));

		pnlSpreadSheet.add(tbl, BorderLayout.CENTER);

		gc_.gridx = 0;
		gc_.gridy = 2;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.insets = new Insets(0, X_POINT, 0, 0);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			pnlCenter.add(pnlSpreadSheet, gc_);
		} else {
			this.add(pnlSpreadSheet, gc_);
		}

		// ���v���z�p�l��
		pnlTotalAmount.setLayout(null);
		TGuiUtil.setComponentSize(pnlTotalAmount, new Dimension(0, 80));

		// �ؕ��O�ݍ��v
		ctrlFcDrTotal.setLangMessageID("C10725");
		ctrlFcDrTotal.setLabelSize(100);
		ctrlFcDrTotal.setNumericFormat("#,##0");
		ctrlFcDrTotal.setEditable(false);
		ctrlFcDrTotal.setChangeRedOfMinus(true);
		ctrlFcDrTotal.setLocation(30, 5);
		pnlTotalAmount.add(ctrlFcDrTotal);

		// �O�ݍ��z
		ctrlFcDiff.setLangMessageID("C10727");
		ctrlFcDiff.setLabelSize(50);
		ctrlFcDiff.setNumericFormat("#,##0");
		ctrlFcDiff.setEditable(false);
		ctrlFcDiff.setChangeRedOfMinus(true);
		ctrlFcDiff.setLocation(ctrlFcDrTotal.getX() + ctrlFcDrTotal.getWidth() + 10, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(ctrlFcDiff);

		// �O�ݒʉ�
		cbxCurrencyOnTotal.setSize(new Dimension(50, 20));
		cbxCurrencyOnTotal.setLocation(ctrlFcDiff.getX() + ctrlFcDiff.getWidth() + 1, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(cbxCurrencyOnTotal);

		// �ݕ��O�ݍ��v
		ctrlFcCrTotal.setLangMessageID("C10726");
		ctrlFcCrTotal.setLabelSize(100);
		ctrlFcCrTotal.setNumericFormat("#,##0");
		ctrlFcCrTotal.setEditable(false);
		ctrlFcCrTotal.setChangeRedOfMinus(true);
		ctrlFcCrTotal.setLocation(ctrlFcDiff.getX() + ctrlFcDiff.getWidth() + 40, ctrlFcDrTotal.getY());
		pnlTotalAmount.add(ctrlFcCrTotal);

		// �ؕ����z���v
		ctrlDrTotal.setLangMessageID("C01126");
		ctrlDrTotal.setLabelSize(100);
		ctrlDrTotal.setNumericFormat("#,##0");
		ctrlDrTotal.setEditable(false);
		ctrlDrTotal.setChangeRedOfMinus(true);
		ctrlDrTotal.setLocation(ctrlFcDrTotal.getX(), ctrlFcDrTotal.getY() + ctrlFcDrTotal.getHeight() + 1);
		pnlTotalAmount.add(ctrlDrTotal);

		// �ב֍�
		ctrlExchangeDiff.setLangMessageID("C10728");
		ctrlExchangeDiff.setLabelSize(50);
		ctrlExchangeDiff.setNumericFormat("#,##0");
		ctrlExchangeDiff.setEditable(false);
		ctrlExchangeDiff.setChangeRedOfMinus(true);
		ctrlExchangeDiff.setLocation(ctrlDrTotal.getX() + ctrlDrTotal.getWidth() + 10, ctrlDrTotal.getY());
		pnlTotalAmount.add(ctrlExchangeDiff);

		// �ݕ����z���v
		ctrlCrTotal.setLangMessageID("C01229");
		ctrlCrTotal.setLabelSize(100);
		ctrlCrTotal.setNumericFormat("#,##0");
		ctrlCrTotal.setEditable(false);
		ctrlCrTotal.setChangeRedOfMinus(true);
		ctrlCrTotal.setLocation(ctrlExchangeDiff.getX() + ctrlExchangeDiff.getWidth() + 40, ctrlDrTotal.getY());
		pnlTotalAmount.add(ctrlCrTotal);

		// �ؕ�����Ŋz���v
		ctrlDrTaxTotal.setLangMessageID("C01174");
		ctrlDrTaxTotal.setLabelSize(100);
		ctrlDrTaxTotal.setNumericFormat("#,##0");
		ctrlDrTaxTotal.setEditable(false);
		ctrlDrTaxTotal.setChangeRedOfMinus(true);
		ctrlDrTaxTotal.setLocation(ctrlDrTotal.getX(), ctrlDrTotal.getY() + ctrlDrTotal.getHeight() + 1);
		pnlTotalAmount.add(ctrlDrTaxTotal);

		// ���z
		ctrlDiff.setLangMessageID("C00191");
		ctrlDiff.setLabelSize(50);
		ctrlDiff.setNumericFormat("#,##0");
		ctrlDiff.setEditable(false);
		ctrlDiff.setChangeRedOfMinus(true);
		ctrlDiff.setLocation(ctrlDrTaxTotal.getX() + ctrlDrTaxTotal.getWidth() + 10, ctrlDrTaxTotal.getY());
		pnlTotalAmount.add(ctrlDiff);

		// �ݕ�����Ŋz���v
		ctrlCrTaxTotal.setLangMessageID("C01174");
		ctrlCrTaxTotal.setLabelSize(100);
		ctrlCrTaxTotal.setNumericFormat("#,##0");
		ctrlCrTaxTotal.setEditable(false);
		ctrlCrTaxTotal.setChangeRedOfMinus(true);
		ctrlCrTaxTotal.setLocation(ctrlDiff.getX() + ctrlDiff.getWidth() + 40, ctrlDrTaxTotal.getY());
		pnlTotalAmount.add(ctrlCrTaxTotal);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			// ���E���̐���
			JSplitPane spBody = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, pnlCenter, pnlTotalAmount);
			spBody.setOpaque(false);
			pnlTotalAmount.setMinimumSize(new Dimension(0, 0));
			pnlCenter.setMinimumSize(new Dimension(0, 0));
			pnlTotalAmount.setMaximumSize(new Dimension(0, 80));
			spBody.setResizeWeight(1);

			// �t�b�_���B���ׂ̐ݒ�
			TUIManager.switchMaximumMode(spBody);

			this.add(spBody, BorderLayout.CENTER);
		} else {

			gc_.gridx = 0;
			gc_.gridy = 3;
			gc_.weighty = 0d;
			gc_.fill = GridBagConstraints.HORIZONTAL;
			gc_.insets = new Insets(0, 0, 0, 0);
			this.add(pnlTotalAmount, gc_);
		}

	}

	/**
	 * �p����Ńw�b�_�̔ԍ����Z�b�g������ɌĂ�
	 * 
	 * @param i �ԍ�
	 * @return �ŏI�ԍ�
	 */
	public int setTabIndex(int i) {
		ctrlKCompany.setTabControlNo(i++);
		ctrlKDepartment.setTabControlNo(i++);
		ctrlItem.setTabControlNo(i++);
		ctrlOccurDate.setTabControlNo(i++);
		ctrlCurrency.setTabControlNo(i++);
		ctrlRate.setTabControlNo(i++);
		ctrlTax.setTabControlNo(i++);
		ctrlTaxDivision.setTabControlNo(i++);
		ctrlRemark.setTabControlNo(i++);
		ctrlDrCr.setTabControlNo(i++);
		ctrlInputAmount.setTabControlNo(i++);
		ctrlTaxAmount.setTabControlNo(i++);
		ctrlKeyAmount.setTabControlNo(i++);
		ctrlCustomer.setTabControlNo(i++);
		ctrlEmployee.setTabControlNo(i++);
		ctrlManage1.setTabControlNo(i++);
		ctrlManage2.setTabControlNo(i++);
		ctrlManage3.setTabControlNo(i++);
		ctrlManage4.setTabControlNo(i++);
		ctrlManage5.setTabControlNo(i++);
		ctrlManage6.setTabControlNo(i++);
		ctrlNonAcDetails.setTabControlNo(i++);

		btnAR.setTabControlNo(i++);
		btnAP.setTabControlNo(i++);
		btnBS.setTabControlNo(i++);

		btnRowNew.setTabControlNo(i++);
		btnRowInsert.setTabControlNo(i++);
		btnRowCopy.setTabControlNo(i++);
		btnRowDelete.setTabControlNo(i++);
		btnRowEntry.setTabControlNo(i++);

		btnDownload.setTabControlNo(i++);
		btnUpload.setTabControlNo(i++);

		btnRowTop.setTabControlNo(i++);
		btnRowUp.setTabControlNo(i++);
		btnRowDown.setTabControlNo(i++);
		btnRowUnder.setTabControlNo(i++);
		tbl.setTabControlNo(i++);

		cbxCurrencyOnTotal.setTabControlNo(i++);

		return i;
	}

	/**
	 * ������
	 */
	public void init() {
		controller.init();
	}

	/**
	 * �`�[�`�[�ԍ�(�ҏW�̏ꍇ)
	 * 
	 * @param slipNo �`�[�`�[�ԍ�(�ҏW�̏ꍇ)
	 */
	public void setSlipNo(String slipNo) {
		controller.setSlipNo(slipNo);
	}

	/**
	 * ���ׂ�Entity�`���ŃZ�b�g����.
	 * 
	 * @param details �f�[�^
	 */
	public void setEntityList(List<SWK_DTL> details) {
		controller.setEntityList(details);
	}

	/**
	 * ���ׂ�Entity�`���Ŏ擾����
	 * 
	 * @return SWK_DTL���X�g
	 */
	public List<SWK_DTL> getEntityList() {
		return controller.getEntityList();
	}

	/**
	 * ���ׂ�Entity�`���Ŏ擾����
	 * 
	 * @return SWK_DTL���X�g
	 */
	public int getDetailRowCount() {
		return tbl.getRowCount();
	}

	/**
	 * �X�v���b�h�e�[�u����ԕۑ��L�[
	 * 
	 * @param tableKeyName �X�v���b�h�e�[�u����ԕۑ��L�[
	 */
	public void setTableKeyName(String tableKeyName) {
		tbl.setTableKeyName(tableKeyName);
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
	 * �O���w�薾��(���z���v�ŗ��p)
	 * 
	 * @param outherDetail �O���w�薾��
	 */
	public void setOutherDetail(SWK_DTL outherDetail) {
		controller.setOuterDetail(outherDetail);
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
	 * ���������ݒ�(���c���ꗗ�p)
	 * 
	 * @param customer ��������
	 */
	public void setCollectionCustomer(Customer customer) {
		controller.setCollectionCustomer(customer);
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
	 * �ݎ؂̃f�t�H���g�l
	 * 
	 * @param dc �ݎ�
	 */
	public void setDefaultDC(Dc dc) {
		controller.setDefaultDC(dc);
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
	 * ���v���̒ʉ݃R���{�{�b�N�X�\�z
	 */
	public void makeCurrencyComboBox() {
		controller.makeCurrencyComboBox();
	}

	/**
	 * ���v�l�Čv�Z
	 */
	public void summary() {
		controller.summary();
	}

	/**
	 * �ʉݐݒ�
	 * 
	 * @param currency �ʉ�
	 */
	public void setCurrecy(Currency currency) {
		controller.setCurrecy(currency);

		if (tbl.getSelectedRow() != -1) {
			// �s�ҏW��
			controller.isEditedDetail = true;
		}

		// ���[�g
		ctrlRate.setNumberValue(controller.getCurrencyRate());

		// ������
		Date occurDate = ctrlOccurDate.getValue();
		if (controller.isUseHasDateChk) {
			ctrlRate.setNumberValue(controller.getCurrencyRateByOccurDate(occurDate));
		}

		controller.changedRate();
	}
}
