package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.item.ItemSearchCondition.SlipInputType;
import jp.co.ais.trans2.model.slip.*;

/**
 * �d�󖾍׃p�l��
 * 
 * @author AIS
 */
public class TFormSlipDetailPanel extends TSlipDetailPanel {

	/** ��J�n�ʒu */
	public final int Y_POINT = 5;

	/** �]��_�� */
	public final int MARGIN_X = 2;

	/** �]��_�c */
	public final int MARGIN_Y = 5;

	/** �w�b�_�p�l�� */
	public TPanel pnlHeader;

	/** �w�b�_�Ɩ��ׂ̋��E�� */
	public JSeparator sepSlipHeader;

	/** ���׃p�l�� */
	public TPanel pnlDetail;

	/** ���ׂ̃X�N���[���p�l�� */
	public JScrollPane scrollPane;

	/** ���ׂƍ��v�̋��E�� */
	public JSeparator sepSlip;

	/** �ŏ���TAB�ԍ� */
	public int firstTabIndex = 0;

	/** �������׌��� */
	protected int detailCount = 2;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e
	 */
	public TFormSlipDetailPanel(TSlipPanel parent) {
		super(parent);
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return �R���g���[���[
	 */
	@Override
	public TFormSlipDetailPanelCtrl createController() {
		return new TFormSlipDetailPanelCtrl(this);
	}

	/**
	 * �R���g���[���擾
	 * 
	 * @return �R���g���[��
	 */
	@Override
	public TFormSlipDetailPanelCtrl getController() {
		return (TFormSlipDetailPanelCtrl) controller;
	}

	/**
	 * �R���|�[�l���g������������
	 */
	@Override
	public void initComponents() {

		pnlHeader = new TPanel();
		pnlInput = new TPanel();

		btnRowTop = new TButton();
		btnRowUp = new TButton();
		btnRowDown = new TButton();
		btnRowUnder = new TButton();
		pnlSsUDPanel = new TPanel();

		pnlDetail = new TPanel();

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

		// �]�v���W�b�N��ۏ؂���
		ctrlKCompany = new TCompanyReference();
		ctrlKDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();
		ctrlRemark = new TRemarkReference();

		// ctrlOccurDate = new TLabelPopupCalendar();

		// ���Z�R���|��
		// ctrlCurrency = new TCurrencyReference();
		// ctrlRate = new TLabelNumericField();
		// ctrlTax = new TTaxReference();
		// ctrlTaxDivision = new TRadioPanel("C01175");

		// ctrlDrCr = new TDrCrPanel();

		// ctrlInputAmount = new TLabelNumericField();
		// ctrlTaxAmount = new TLabelNumericField();
		// ctrlKeyAmount = new TLabelNumericField();

		// ctrlCustomer = new TCustomerReference();
		// ctrlEmployee = new TEmployeeReference();
		// ctrlManage1 = new TManagement1Reference();
		// ctrlManage2 = new TManagement2Reference();
		// ctrlManage3 = new TManagement3Reference();
		// ctrlManage4 = new TManagement4Reference();
		// ctrlManage5 = new TManagement5Reference();
		// ctrlManage6 = new TManagement6Reference();

		// ctrlNonAcDetails = new TNonAccountintDetailUnit();

		// pnlDtlButtons = new TPanel();
		// pnlFreeButtons = new TPanel();
		// pnlRowButtons = new TPanel();

		btnAR = new TButton();
		btnAP = new TButton();
		btnBS = new TButton();

		// btnRowNew = new TButton();
		// btnRowInsert = new TButton();
		// btnRowCopy = new TButton();
		// btnRowDelete = new TButton();
		// btnRowEntry = new TButton();

		// btnRowTop = new TButton();
		// btnRowUp = new TButton();
		// btnRowDown = new TButton();
		// btnRowUnder = new TButton();
		tbl = new TTable();
		// pnlSpreadSheet = new TPanel();

		// pnlSsUDPanel = new TPanel();
		// pnlTotalAmount = new TPanel();

	}

	/**
	 * ���ׂ̔z�u
	 */
	@Override
	public void allocateSlipDetail() {

		TPanel pnlCenter = new TPanel(new GridBagLayout());

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			this.setLayout(new BorderLayout());

		} else {
			this.setLayout(new GridBagLayout());
		}

		setSize(1100, 800);

		Dimension buttonSize = new Dimension(20, 100);

		pnlHeader.setLayout(null);
		TGuiUtil.setComponentSize(pnlHeader, new Dimension(0, 25));

		int x = 28 + 20;
		int y = 5;
		// ���c��
		btnAR.setLangMessageID("C01080");
		TGuiUtil.setComponentSize(btnAR, buttonSize);
		btnAR.setLocation(x, y);
		pnlHeader.add(btnAR);

		x += btnAR.getWidth() + MARGIN_X;

		// ���c��
		btnAP.setLangMessageID("C01084");
		TGuiUtil.setComponentSize(btnAP, buttonSize);
		btnAP.setLocation(x, y);
		pnlHeader.add(btnAP);

		x += btnAP.getWidth() + MARGIN_X;

		// BS����
		btnBS.setLangMessageID("C04291");
		TGuiUtil.setComponentSize(btnBS, buttonSize);
		btnBS.setLocation(x, y);
		pnlHeader.add(btnBS);

		x += btnBS.getWidth() + MARGIN_X;

		Dimension size = new Dimension(0, 250);
		pnlInput.setLayout(new GridBagLayout());
		pnlInput.setPreferredSize(size);
		pnlInput.setSize(size);

		// �X�v���b�h�㉺�{�^���p�l��
		pnlSsUDPanel.setLayout(null);
		TGuiUtil.setComponentSize(pnlSsUDPanel, new Dimension(28, 0));

		// �{�^��
		TGuiUtil.setComponentSize(btnRowTop, new Dimension(20, 22));
		TGuiUtil.setComponentSize(btnRowUp, new Dimension(20, 22));
		TGuiUtil.setComponentSize(btnRowDown, new Dimension(20, 22));
		TGuiUtil.setComponentSize(btnRowUnder, new Dimension(20, 22));

		btnRowTop.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowTop.png"));
		btnRowUp.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUp.png"));
		btnRowDown.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowDown.png"));
		btnRowUnder.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/rowUnder.png"));

		btnRowTop.setLocation(X_POINT * 2, 150);
		btnRowUp.setLocation(btnRowTop.getX(), btnRowTop.getY() + btnRowTop.getHeight());
		btnRowDown.setLocation(btnRowTop.getX(), btnRowUp.getY() + btnRowUp.getHeight());
		btnRowUnder.setLocation(btnRowTop.getX(), btnRowDown.getY() + btnRowDown.getHeight());

		pnlSsUDPanel.add(btnRowTop);
		pnlSsUDPanel.add(btnRowUp);
		pnlSsUDPanel.add(btnRowDown);
		pnlSsUDPanel.add(btnRowUnder);

		GridBagConstraints gc_ = new GridBagConstraints();

		gc_.gridx = 0;
		gc_.gridy = 0;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		pnlInput.add(pnlSsUDPanel, gc_);

		// ����
		pnlDetail.setLayout(null);
		scrollPane = new JScrollPane(pnlDetail);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		TGuiUtil.setComponentSize(scrollPane, new Dimension(0, 250));
		scrollPane.getVerticalScrollBar().setUnitIncrement(48);
		scrollPane.setLocation(pnlSsUDPanel.getX() + pnlSsUDPanel.getWidth(), 0);

		// ���׍���
		gc_ = new GridBagConstraints();

		gc_.gridx = 1;
		gc_.gridy = 0;
		gc_.weightx = 1.0d;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		pnlInput.add(scrollPane, gc_);

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 0;
		gc_.weightx = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			pnlCenter.add(pnlHeader, gc_);
		} else {
			this.add(pnlHeader, gc_);
		}

		// ���E��
		sepSlipHeader = new JSeparator();
		TGuiUtil.setComponentSize(sepSlipHeader, new Dimension(0, 3));

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 1;
		gc_.weightx = 1.0d;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			pnlCenter.add(sepSlipHeader, gc_);
		} else {
			this.add(sepSlipHeader, gc_);
		}

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 2;
		gc_.weightx = 1.0d;
		gc_.weighty = 1.0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			pnlCenter.add(pnlInput, gc_);
		} else {
			this.add(pnlInput, gc_);
		}

		// ���E��
		sepSlip = new JSeparator();
		TGuiUtil.setComponentSize(sepSlip, new Dimension(0, 3));

		gc_ = new GridBagConstraints();
		gc_.gridx = 0;
		gc_.gridy = 3;
		gc_.weightx = 1.0d;
		gc_.weighty = 0d;
		gc_.fill = GridBagConstraints.BOTH;
		gc_.anchor = GridBagConstraints.NORTHWEST;
		gc_.insets = new Insets(0, 0, 0, 0);

		// ���[�U�[�ݒ�ɂ��A�X�v���b�g�y�C����\��
		if (splitPaneFlg) {
			pnlCenter.add(sepSlip, gc_);
		} else {
			this.add(sepSlip, gc_);
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
			gc_.gridy = 4;
			gc_.weighty = 0d;
			gc_.fill = GridBagConstraints.BOTH;
			gc_.anchor = GridBagConstraints.NORTHWEST;
			gc_.insets = new Insets(0, 0, 0, 0);

			add(pnlTotalAmount, gc_);
		}

	}

	/**
	 * ������
	 */
	@Override
	public void init() {
		getController().init(detailCount);
	}

	/**
	 * ������
	 * 
	 * @param count
	 */
	public void init(int count) {
		this.detailCount = count;
		getController().init(detailCount);
	}

	/**
	 * �ʉݐݒ�
	 * 
	 * @param currency �ʉ݃R�[�h
	 */
	public void setCurrecy(Currency currency) {
		getController().setCurrecy(currency);
	}

	/**
	 * ����ݒ�
	 * 
	 * @param baseDate ���
	 */
	@Override
	public void setBaseDate(Date baseDate) {
		getController().setBaseDate(baseDate);
	}

	/**
	 * ���Z�d��ݒ�
	 * 
	 * @param isClosing true:���Z�d��Afalse:�ʏ�d��
	 */
	@Override
	public void setClosingEntry(boolean isClosing) {
		getController().setClosingEntry(isClosing);
	}

	/**
	 * �f�t�H���g�����ł�
	 * 
	 * @param defaultTaxInnerDivision true:����
	 */
	@Override
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		getController().setDefaultTaxInnerDivision(defaultTaxInnerDivision);
	}

	/**
	 * �f�t�H���g���f�[�^�敪��
	 * 
	 * @param dataType �f�[�^�敪
	 */
	public void setDataType(String dataType) {

		getController().setDataType(dataType);
	}

	/**
	 * ���ׂ�Entity�`���ŃZ�b�g����.
	 * 
	 * @param details �f�[�^
	 */
	@Override
	public void setEntityList(List<SWK_DTL> details) {
		getController().setEntityList(details);
	}

	/**
	 * ���ׂ�Entity�`���Ŏ擾����
	 * 
	 * @return SWK_DTL���X�g
	 */
	@Override
	public List<SWK_DTL> getEntityList() {
		return getController().getEntityList();
	}

	/**
	 * ���ׂ̍s�����擾����
	 * 
	 * @return ���ׂ̍s��
	 */
	@Override
	public int getDetailRowCount() {
		return getController().getDetailRowCount();
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	public boolean checkInput() {
		return getController().checkInput();
	}

	/**
	 * �`�[���̓^�C�v
	 * 
	 * @param slipInputType SlipInputType
	 */
	public void setSlipInputType(SlipInputType slipInputType) {
		getController().setSlipInputType(slipInputType);
	}

	/**
	 * ���ׂ�1�s�ڂ̃t�H�[�J�X�̐ݒ�
	 */
	public void setDetailFocus() {
		if (getController().dcList != null && getController().dcList.size() > 0) {
			getController().dcList.get(0).ctrlItemDebit.ctrlItemReference.code.requestFocusInWindow();
		}
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 * @return �ŏI�ԍ�
	 */
	@Override
	public int setTabIndex(int tabControlNo) {

		btnAR.setTabControlNo(tabControlNo++);
		btnAP.setTabControlNo(tabControlNo++);
		btnBS.setTabControlNo(tabControlNo++);

		this.firstTabIndex = tabControlNo;

		for (TFormDCInputPanel dcPane : getController().dcList) {
			tabControlNo = dcPane.setTabControlNo(tabControlNo++);
		}

		tabControlNo += 90000; // 9���]�v������

		btnRowTop.setTabControlNo(tabControlNo++);
		btnRowUp.setTabControlNo(tabControlNo++);
		btnRowDown.setTabControlNo(tabControlNo++);
		btnRowUnder.setTabControlNo(tabControlNo++);

		cbxCurrencyOnTotal.setTabControlNo(tabControlNo++);

		return tabControlNo;
	}

	/**
	 * ���׃f�[�^�̒ʉ݃��X�g�쐬
	 * 
	 * @param list �ʉ݃��X�g
	 */
	public void setCurrecyList(List<Currency> list) {
		getController().setCurrecyList(list);
	}

	/**
	 * @see jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel#setTableKeyName(java.lang.String)
	 */
	@Override
	public void setTableKeyName(String tableKeyName) {
		// �����Ȃ�
	}
}
