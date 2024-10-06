package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.plaf.mint.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��ЃR���g���[���}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class MG0010CompanyMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �^�u */
	public TTabbedPane tab;

	/** ��Џ��ݒ�^�u */
	public TPanel pnlBase;

	/** ��Џ��ݒ�(�p���)�^�u */
	public TPanel pnlBaseEng;

	/** �ȖڂƊǗ��ݒ�^�u */
	public TPanel pnlAccount;

	/** �`�[�ݒ�^�u */
	public TPanel pnlSlip;

	/** ���Z�ݒ�^�u */
	public TPanel pnlSettlement;

	/** ���̑��ݒ�^�u */
	public TPanel pnlElse;

	/** ���ݒ�^�u */
	public TPanel pnlEnv;

	/** ��Џ��p�l�� */
	public TPanel pnlCompany;

	/** ��Џ��(�p���)�p�l�� */
	public TPanel pnlCompanyEng;

	/** �R�[�h */
	public TLabelField ctrlCode;

	/** ���� */
	public TLabelField ctrlName;

	/** ���� */
	public TLabelField ctrlNames;

	/** �Z��1 */
	public TLabelField ctrlAddress1;

	/** �Z��2 */
	public TLabelField ctrlAddress2;

	/** �Z���� */
	public TLabelField ctrlKana;

	/** �X�֔ԍ� */
	public TLabelField ctrlZipCode;

	/** �d�b�ԍ� */
	public TLabelField ctrlPhoneNo;

	/** FAX�ԍ� */
	public TLabelField ctrlFaxNo;

	/** �K�i���������s���Ǝғo�^�ԍ� */
	public TLabelField ctrlInvRegNo;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/** ����(�p���) */
	public TLabelField ctrlEngName;

	/** ����(�p���) */
	public TLabelField ctrlEngNames;

	/** �Z��1(�p���) */
	public TLabelField ctrlEngAddress1;

	/** �Z��2(�p���) */
	public TLabelField ctrlEngAddress2;

	/** �Ȗڐݒ� */
	public TPanel pnlItem;

	/** �Ȗږ��� */
	public TLabelField ctrlItemName;

	/** �⏕�Ȗږ��� */
	public TLabelField ctrlSubItemName;

	/** ����ȖڊǗ� */
	public TCheckBox chkDetailItem;

	/** ����Ȗږ��� */
	public TTextField ctrlDetailItemName;

	/** �Ǘ��敪�ݒ� */
	public TPanel pnlManagement;

	/** �Ǘ�1�Ǘ� */
	public TCheckBox chkManagement1;

	/** �Ǘ�1���� */
	public TTextField ctrlManagement1Name;

	/** �Ǘ�2�Ǘ� */
	public TCheckBox chkManagement2;

	/** �Ǘ�2���� */
	public TTextField ctrlManagement2Name;

	/** �Ǘ�3�Ǘ� */
	public TCheckBox chkManagement3;

	/** �Ǘ�3���� */
	public TTextField ctrlManagement3Name;

	/** �Ǘ�4�Ǘ� */
	public TCheckBox chkManagement4;

	/** �Ǘ�4���� */
	public TTextField ctrlManagement4Name;

	/** �Ǘ�5�Ǘ� */
	public TCheckBox chkManagement5;

	/** �Ǘ�5���� */
	public TTextField ctrlManagement5Name;

	/** �Ǘ�6�Ǘ� */
	public TCheckBox chkManagement6;

	/** �Ǘ�6���� */
	public TTextField ctrlManagement6Name;

	/** ���v���׋敪�ݒ� */
	public TPanel pnlNotAccount;

	/** ���v����1�Ǘ� */
	public TLabelComboBox cboNotAccount1;

	/** ���v����1���� */
	public TTextField ctrlNotAccount1Name;

	/** ���v����2�Ǘ� */
	public TLabelComboBox cboNotAccount2;

	/** ���v����2���� */
	public TTextField ctrlNotAccount2Name;

	/** ���v����3�Ǘ� */
	public TLabelComboBox cboNotAccount3;

	/** ���v����3���� */
	public TTextField ctrlNotAccount3Name;

	/** �`�[�ԍ��ݒ� */
	public TPanel pnlSlipNo;

	/** �`�[�ԍ��A�Ԍ��� */
	public TLabelNumericField ctrlSlipNoDigit;

	/** �����ݒ荀��1 */
	public TLabelComboBox cboSlipNoAdopt1;

	/** �����ݒ荀��2 */
	public TLabelComboBox cboSlipNoAdopt2;

	/** �����ݒ荀��3 */
	public TLabelComboBox cboSlipNoAdopt3;

	/** �[�������ݒ� */
	public TPanel pnlFraction;

	/** ���[�g���Z�[������ */
	public TLabelComboBox cboExchangeFraction;

	/** �������Œ[������ */
	public TLabelComboBox cboTemporaryGetTaxFraction;

	/** ��������Œ[������ */
	public TLabelComboBox cboTemporaryPaymentTaxFraction;

	/** �`�[����Ɋւ���ݒ� */
	public TPanel pnlSlipPrint;

	/** �`�[����敪 */
	public TCheckBox chkSlipPrint;

	/** ������̏����l */
	public TCheckBox chkSlipPrintInitial;

	/** ���Z�i�K�敪 */
	public TPanel pnlSettlementStage;

	/** ���Z���s��/�s��Ȃ� */
	public TLabelComboBox cboSettlement;

	/** ���Z�i�K */
	public TNumericField ctrlSettlementStage;

	/** ���Z�i�K */
	public TLabel lblSettlementStage;

	/** ���Z�`�[���͋敪 */
	public TLabelComboBox cboSettlementPer;

	/** ���Z�敪 */
	public TPanel pnlConvertType;

	/** ���Z�敪 */
	public TLabelComboBox cboConvertType;

	/** �]���փ��[�g�敪 */
	public TPanel pnlForeignCurrencyExchangeRate;

	/** �]���փ��[�g�敪 */
	public TLabelComboBox cboForeignCurrencyExchangeRate;

	/** ���Ԍ��Z�J�z�敪 */
	public TPanel pnlCarryJournalOfMidtermClosingForward;

	/** ���Ԍ��Z�J�z�敪 */
	public TLabelComboBox cboCarryJournalOfMidtermClosingForward;

	/** ��v���� */
	public TPanel pnlFiscalPeriod;

	/** ���� */
	public TLabelNumericField ctrlInitialMonth;

	/** ���F */
	public TPanel pnlApprove;

	/** ���[�N�t���[���F */
	public TEnumComboBox<ApproveType> cboWorkFlowApprove;

	/** (���[�N�t���[���F)�۔F���� */
	public TEnumComboBox<DenyAction> cboDenyAction;

	/** ���ꏳ�F */
	public TCheckBox chkFieldApprove;

	/** �o�����F */
	public TCheckBox chkAccountantApprove;

	/** �ʉ� */
	public TPanel pnlCurrency;

	/** ��ʉ� */
	public TCurrencyReference ctrlKeyCurrency;

	/** �@�\�ʉ� */
	public TCurrencyReference ctrlFunctionCurrency;

	/** �O���[�v��v�ݒ� */
	public TPanel pnlGrp;

	/** �O���[�v��v�敪 */
	public TCheckBox chkGrpKbn;

	/** IFRS�敪�ݒ� */
	public TPanel pnlIfrs;

	/** IFRS�敪 */
	public TCheckBox chkIfrsKbn;

	/** �������`�F�b�N�ݒ� */
	public TPanel pnlHasDateKbn;

	/** �������`�F�b�N�敪 */
	public TCheckBox chkHasDateKbn;

	/** �p��������SIGNER���i���Ǘ��j�ݒ� */
	public TPanel pnlEnSigner;

	/** �p��������SIGNER���i���Ǘ��j */
	public TTextField ctrlEnSignerText;

	/** �C���{�C�X���x�ݒ� */
	public TPanel pnlInvoiceSystem;

	/** �`�F�b�N�{�b�N�X�C���{�C�X�g�p */
	public TCheckBox ctrlInvoiceChk;

	/** �F�ݒ�p�l�� */
	public TPanel pnlColor;

	/** ��АF */
	public TColor ctrlCompanyColor;

	/** SPC��Џ��^�u */
	public TPanel pnlSpc;

	/** SPC��Џ��ݒ� */
	public TPanel pnlSpcInfomation;

	/** �������F�A���p��ЃR�[�h */
	public TLabelField ctrlConnCompanyCode;

	/** �������F�T�C���ҁE��E�� */
	public TLabelField ctrlSignerPosition;

	/** �������F�O�������S���� */
	public TLabelField ctrlRemitterName;

	/** �������F�A����d�b�ԍ� */
	public TLabelField ctrlConnPhoneNo;

	/** �������FDebitNote�Z��1 */
	public TLabelField ctrlDebitNoteAddress1;

	/** �������FDebitNote�Z��2 */
	public TLabelField ctrlDebitNoteAddress2;

	/** �������FDebitNote�Z��3 */
	public TLabelField ctrlDebitNoteAddress3;

	/** �������FDebitNote�t�b�^��񃉃x�� */
	public TLabel ctrlDebitNoteInfoLabel;

	/** �������FDebitNote�t�b�^��� */
	public TTextArea ctrlDebitNoteInfo;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0010CompanyMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		tab = new TTabbedPane();
		pnlBase = new TPanel();
		pnlBaseEng = new TPanel();
		pnlAccount = new TPanel();
		pnlSlip = new TPanel();
		pnlSettlement = new TPanel();
		pnlElse = new TPanel();
		pnlEnv = new TPanel();
		pnlCompany = new TPanel();
		pnlCompanyEng = new TPanel();

		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNames = new TLabelField();
		ctrlAddress1 = new TLabelField();
		ctrlAddress2 = new TLabelField();
		ctrlKana = new TLabelField();
		ctrlZipCode = new TLabelField();
		ctrlPhoneNo = new TLabelField();
		ctrlFaxNo = new TLabelField();
		ctrlInvRegNo = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		pnlItem = new TPanel();
		ctrlItemName = new TLabelField();
		ctrlSubItemName = new TLabelField();
		chkDetailItem = new TCheckBox();
		ctrlDetailItemName = new TTextField();
		pnlManagement = new TPanel();
		chkManagement1 = new TCheckBox();
		ctrlManagement1Name = new TTextField();
		chkManagement2 = new TCheckBox();
		ctrlManagement2Name = new TTextField();
		chkManagement3 = new TCheckBox();
		ctrlManagement3Name = new TTextField();
		chkManagement4 = new TCheckBox();
		ctrlManagement4Name = new TTextField();
		chkManagement5 = new TCheckBox();
		ctrlManagement5Name = new TTextField();
		chkManagement6 = new TCheckBox();
		ctrlManagement6Name = new TTextField();
		pnlNotAccount = new TPanel();
		cboNotAccount1 = new TLabelComboBox();
		ctrlNotAccount1Name = new TTextField();
		cboNotAccount2 = new TLabelComboBox();
		ctrlNotAccount2Name = new TTextField();
		cboNotAccount3 = new TLabelComboBox();
		ctrlNotAccount3Name = new TTextField();
		pnlSlipNo = new TPanel();
		ctrlSlipNoDigit = new TLabelNumericField();
		cboSlipNoAdopt1 = new TLabelComboBox();
		cboSlipNoAdopt2 = new TLabelComboBox();
		cboSlipNoAdopt3 = new TLabelComboBox();
		pnlFraction = new TPanel();
		cboExchangeFraction = new TLabelComboBox();
		cboTemporaryGetTaxFraction = new TLabelComboBox();
		cboTemporaryPaymentTaxFraction = new TLabelComboBox();
		pnlSlipPrint = new TPanel();
		chkSlipPrint = new TCheckBox();
		chkSlipPrintInitial = new TCheckBox();
		pnlConvertType = new TPanel();
		cboConvertType = new TLabelComboBox();
		pnlSettlementStage = new TPanel();
		cboSettlement = new TLabelComboBox();
		ctrlSettlementStage = new TNumericField();
		lblSettlementStage = new TLabel();
		cboSettlementPer = new TLabelComboBox();
		pnlForeignCurrencyExchangeRate = new TPanel();
		cboForeignCurrencyExchangeRate = new TLabelComboBox();
		pnlCarryJournalOfMidtermClosingForward = new TPanel();
		cboCarryJournalOfMidtermClosingForward = new TLabelComboBox();
		pnlFiscalPeriod = new TPanel();
		ctrlInitialMonth = new TLabelNumericField();
		pnlApprove = new TPanel();
		cboWorkFlowApprove = new TEnumComboBox<ApproveType>();
		cboDenyAction = new TEnumComboBox<DenyAction>();
		chkFieldApprove = new TCheckBox();
		chkAccountantApprove = new TCheckBox();
		pnlCurrency = new TPanel();
		ctrlKeyCurrency = new TCurrencyReference();
		ctrlFunctionCurrency = new TCurrencyReference();
		pnlGrp = new TPanel();
		chkGrpKbn = new TCheckBox();
		pnlIfrs = new TPanel();
		chkIfrsKbn = new TCheckBox();
		pnlHasDateKbn = new TPanel();
		chkHasDateKbn = new TCheckBox();

		pnlEnSigner = new TPanel();
		ctrlEnSignerText = new TTextField();
		pnlColor = new TPanel();
		ctrlCompanyColor = new TColor();
		pnlInvoiceSystem = new TPanel();
		ctrlInvoiceChk = new TCheckBox();

		// INVOICE�g�p(��Њ�b���p���)
		ctrlEngName = new TLabelField();
		ctrlEngNames = new TLabelField();
		ctrlEngAddress1 = new TLabelField();
		ctrlEngAddress2 = new TLabelField();

		// SPC��Џ��^�u
		pnlSpc = new TPanel();
		pnlSpcInfomation = new TPanel();
		ctrlConnCompanyCode = new TLabelField();
		ctrlSignerPosition = new TLabelField();
		ctrlRemitterName = new TLabelField();
		ctrlConnPhoneNo = new TLabelField();
		ctrlDebitNoteAddress1 = new TLabelField();
		ctrlDebitNoteAddress2 = new TLabelField();
		ctrlDebitNoteAddress3 = new TLabelField();
		ctrlDebitNoteInfoLabel = new TLabel();
		ctrlDebitNoteInfo = new TTextArea();
	}

	@SuppressWarnings("static-access")
	@Override
	public void allocateComponents() {

		setSize(800, 600);

		// �m��{�^��
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		@SuppressWarnings("hiding")
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 10, 10, 10);

		pnlBody.add(tab, gc);

		// ��{�ݒ�^�u
		pnlBase.setLayout(null);
		tab.addTab(getWord("C11165"), pnlBase);// ��Џ��

		// ��v�ݒ�^�u
		pnlAccount.setLayout(null);
		tab.addTab(getWord("C11166"), pnlAccount);// �ȖڂƊǗ�

		// �`�[�ݒ�^�u
		pnlSlip.setLayout(null);
		tab.addTab(getWord("C11172"), pnlSlip);// �` �[

		// ���Z�ݒ�^�u
		pnlSettlement.setLayout(null);
		tab.addTab(getWord("C11173"), pnlSettlement);// �� �Z

		// ���̑�
		pnlElse.setLayout(null);
		tab.addTab(getWord("C00338"), pnlElse);// ���̑�

		// ���ݒ�^�u
		pnlEnv.setLayout(null);
		tab.addTab(getWord("C11174"), pnlEnv);// �� ��

		// ��Џ��
		pnlCompany.setLayout(null);
		pnlCompany.setBorder(TBorderFactory.createTitledBorder(getWord("C11175")));// ��Џ��ݒ�
		pnlCompany.setSize(750, 400);
		pnlCompany.setLocation(10, 10);
		pnlBase.add(pnlCompany);

		// ��ЃR�[�h
		ctrlCode.setLabelSize(110);
		ctrlCode.setFieldSize(75);
		ctrlCode.setSize(190, 20);
		ctrlCode.setLocation(10, 20);
		ctrlCode.setLabelText(getWord("C00596"));
		ctrlCode.setMaxLength(TransUtil.COMPANY_CODE_LENGTH);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		pnlCompany.add(ctrlCode);

		// ����
		ctrlName.setLabelSize(110);
		ctrlName.setFieldSize(300);
		ctrlName.setSize(415, 20);
		ctrlName.setLocation(10, 45);
		ctrlName.setLabelText(getWord("C00685"));
		ctrlName.setMaxLength(TransUtil.COMPANY_NAME_LENGTH);
		pnlCompany.add(ctrlName);

		// ����
		ctrlNames.setLabelSize(110);
		ctrlNames.setFieldSize(150);
		ctrlNames.setSize(265, 20);
		ctrlNames.setLocation(10, 70);
		ctrlNames.setLabelText(TModelUIUtil.getShortWord(("C00686")));
		ctrlNames.setMaxLength(TransUtil.COMPANY_NAMES_LENGTH);
		pnlCompany.add(ctrlNames);

		// �Z��1
		ctrlAddress1.setLabelSize(110);
		ctrlAddress1.setFieldSize(500);
		ctrlAddress1.setSize(615, 20);
		ctrlAddress1.setLocation(10, 95);
		ctrlAddress1.setLabelText(getWord("C00687"));
		ctrlAddress1.setMaxLength(TransUtil.COMPANY_ADDRESS_LENGTH);
		pnlCompany.add(ctrlAddress1);

		// �Z��2
		ctrlAddress2.setLabelSize(110);
		ctrlAddress2.setFieldSize(500);
		ctrlAddress2.setSize(615, 20);
		ctrlAddress2.setLocation(10, 120);
		ctrlAddress2.setLabelText(getWord("C00688"));
		ctrlAddress2.setMaxLength(TransUtil.COMPANY_ADDRESS_LENGTH);
		pnlCompany.add(ctrlAddress2);

		// �Z����
		ctrlKana.setLabelSize(110);
		ctrlKana.setFieldSize(500);
		ctrlKana.setSize(615, 20);
		ctrlKana.setLocation(10, 145);
		ctrlKana.setLabelText(getWord("C01152"));
		ctrlKana.setMaxLength(TransUtil.COMPANY_KANA_LENGTH);
		pnlCompany.add(ctrlKana);

		// �X�֔ԍ�
		ctrlZipCode.setLabelSize(110);
		ctrlZipCode.setFieldSize(75);
		ctrlZipCode.setSize(190, 20);
		ctrlZipCode.setLocation(10, 170);
		ctrlZipCode.setLabelText(getWord("C00527"));
		ctrlZipCode.setImeMode(false);
		ctrlZipCode.setMaxLength(TransUtil.ZIP_LENGTH);
		pnlCompany.add(ctrlZipCode);

		// �d�b�ԍ�
		ctrlPhoneNo.setLabelSize(110);
		ctrlPhoneNo.setFieldSize(90);
		ctrlPhoneNo.setSize(205, 20);
		ctrlPhoneNo.setLocation(10, 195);
		ctrlPhoneNo.setLabelText(getWord("C00393"));
		ctrlPhoneNo.setImeMode(false);
		ctrlPhoneNo.setMaxLength(TransUtil.PHONE_NO_LENGTH);
		pnlCompany.add(ctrlPhoneNo);

		// FAX�ԍ�
		ctrlFaxNo.setLabelSize(110);
		ctrlFaxNo.setFieldSize(90);
		ctrlFaxNo.setSize(205, 20);
		ctrlFaxNo.setLocation(10, 220);
		ctrlFaxNo.setLabelText(getWord("C00690"));
		ctrlFaxNo.setImeMode(false);
		ctrlFaxNo.setMaxLength(TransUtil.FAX_NO_LENGTH);
		pnlCompany.add(ctrlFaxNo);
		// invoice���x
		if (MG0010CompanyMasterPanelCtrl.isInvoice) {
			// �K�i���������s���Ǝғo�^�ԍ�
			ctrlInvRegNo.setLabelSize(170);
			ctrlInvRegNo.setFieldSize(120);
			ctrlInvRegNo.setSize(305, 20);
			ctrlInvRegNo.setLocation(ctrlCode.getX() + ctrlCode.getWidth() + 125, ctrlFaxNo.getY());
			ctrlInvRegNo.setLabelText(getWord("C12171"));
			ctrlInvRegNo.setImeMode(false);
			ctrlInvRegNo.setMaxLength(TransUtil.INV_REG_NO_LENGTH);
			pnlCompany.add(ctrlInvRegNo);

		}
		// �J�n�N����
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 245);
		dtBeginDate.setLangMessageID("C00055");
		pnlCompany.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 270);
		dtEndDate.setLangMessageID("C00261");
		pnlCompany.add(dtEndDate);

		{
			// INVOICE�^�u
			if (MG0010CompanyMasterPanelCtrl.isShowCompanyEng) {
				// �f�t�H���g��\��

				pnlBaseEng.setLayout(null);
				tab.addTab(getWord("COP988"), pnlBaseEng);
			}

			// ��Џ��ݒ�(�p���)
			pnlCompanyEng.setLayout(null);
			pnlCompanyEng.setBorder(TBorderFactory.createTitledBorder(getWord("COP988")));
			pnlCompanyEng.setSize(750, 200);
			pnlCompanyEng.setLocation(10, 10);
			pnlBaseEng.add(pnlCompanyEng);

			// ����(�p��)
			ctrlEngName.setLabelSize(110);
			ctrlEngName.setFieldSize(600);
			ctrlEngName.setSize(715, 20);
			ctrlEngName.setLocation(0, 30);
			StringBuilder sb = new StringBuilder();
			sb.append(getWord("C00685"));
			sb.append(getWord("C11896"));
			ctrlEngName.setLabelText(sb.toString());
			ctrlEngName.setMaxLength(200);
			ctrlEngName.setImeMode(false);
			pnlCompanyEng.add(ctrlEngName);

			// ����(�p��)
			ctrlEngNames.setLabelSize(110);
			ctrlEngNames.setFieldSize(600);
			ctrlEngNames.setSize(715, 20);
			ctrlEngNames.setLocation(0, ctrlEngName.getY() + ctrlEngName.getHeight() + 5);
			sb = new StringBuilder();
			sb.append(getWord("C00686"));
			sb.append(getWord("C11896"));
			ctrlEngNames.setLabelText(sb.toString());
			ctrlEngNames.setMaxLength(200);
			ctrlEngNames.setImeMode(false);
			pnlCompanyEng.add(ctrlEngNames);

			// �Z��1(�p��)
			ctrlEngAddress1.setLabelSize(110);
			ctrlEngAddress1.setFieldSize(600);
			ctrlEngAddress1.setSize(715, 20);
			ctrlEngAddress1.setLocation(0, ctrlEngNames.getY() + ctrlEngNames.getHeight() + 5);
			ctrlEngAddress1.setLabelText(getWord("C11893"));
			ctrlEngAddress1.setMaxLength(200);
			ctrlEngAddress1.setImeMode(false);
			pnlCompanyEng.add(ctrlEngAddress1);

			// �Z��2(�p��)
			ctrlEngAddress2.setLabelSize(110);
			ctrlEngAddress2.setFieldSize(600);
			ctrlEngAddress2.setSize(715, 20);
			ctrlEngAddress2.setLocation(0, ctrlEngAddress1.getY() + ctrlEngAddress1.getHeight() + 5);
			ctrlEngAddress2.setLabelText(getWord("C11894"));
			ctrlEngAddress2.setMaxLength(200);
			ctrlEngAddress2.setImeMode(false);
			pnlCompanyEng.add(ctrlEngAddress2);
		}

		// �Ȗ�
		pnlItem.setLayout(null);
		pnlItem.setBorder(TBorderFactory.createTitledBorder(getWord("C11181")));
		pnlItem.setSize(260, 105);
		pnlItem.setLocation(10, 10);
		pnlAccount.add(pnlItem);

		// �Ȗږ���
		ctrlItemName.setLabelSize(100);
		ctrlItemName.setFieldSize(120);
		ctrlItemName.setSize(225, 20);
		ctrlItemName.setLocation(10, 20);
		ctrlItemName.setLabelText(getWord("C00700"));
		ctrlItemName.setMaxLength(TransUtil.KMK_HKM_UKM_NAME_LENGTH);
		pnlItem.add(ctrlItemName);

		// �⏕�Ȗږ���
		ctrlSubItemName.setLabelSize(100);
		ctrlSubItemName.setFieldSize(120);
		ctrlSubItemName.setSize(225, 20);
		ctrlSubItemName.setLocation(10, 45);
		ctrlSubItemName.setLabelText(TModelUIUtil.getShortWord(("C00701")));
		ctrlSubItemName.setMaxLength(TransUtil.KMK_HKM_UKM_NAME_LENGTH);
		pnlItem.add(ctrlSubItemName);

		// ����ȖڊǗ�
		chkDetailItem.setSize(100, 20);
		chkDetailItem.setHorizontalTextPosition(TCheckBox.LEFT);
		chkDetailItem.setHorizontalAlignment(TCheckBox.RIGHT);
		chkDetailItem.setLocation(15, 70);
		chkDetailItem.setLangMessageID("C00942");
		pnlItem.add(chkDetailItem);

		// ����Ȗږ���
		ctrlDetailItemName.setSize(120, 20);
		ctrlDetailItemName.setLocation(115, 70);
		ctrlDetailItemName.setMaxLength(TransUtil.KMK_HKM_UKM_NAME_LENGTH);
		pnlItem.add(ctrlDetailItemName);

		// �Ǘ��ݒ�
		pnlManagement.setLayout(null);
		pnlManagement.setBorder(TBorderFactory.createTitledBorder(getWord("C11182")));
		pnlManagement.setSize(260, 180);
		pnlManagement.setLocation(10, 120);
		pnlAccount.add(pnlManagement);

		// �Ǘ��敪1
		chkManagement1.setSize(100, 20);
		chkManagement1.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement1.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement1.setLocation(15, 20);
		chkManagement1.setLangMessageID("C00936");
		pnlManagement.add(chkManagement1);

		// �Ǘ�1����
		ctrlManagement1Name.setSize(120, 20);
		ctrlManagement1Name.setLocation(115, 20);
		ctrlManagement1Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement1Name);

		// �Ǘ��敪2
		chkManagement2.setSize(100, 20);
		chkManagement2.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement2.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement2.setLocation(15, 45);
		chkManagement2.setLangMessageID("C00937");
		pnlManagement.add(chkManagement2);

		// �Ǘ�2����
		ctrlManagement2Name.setSize(120, 20);
		ctrlManagement2Name.setLocation(115, 45);
		ctrlManagement2Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement2Name);

		// �Ǘ��敪3
		chkManagement3.setSize(100, 20);
		chkManagement3.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement3.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement3.setLocation(15, 70);
		chkManagement3.setLangMessageID("C00938");
		pnlManagement.add(chkManagement3);

		// �Ǘ�3����
		ctrlManagement3Name.setSize(120, 20);
		ctrlManagement3Name.setLocation(115, 70);
		ctrlManagement3Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement3Name);

		// �Ǘ��敪4
		chkManagement4.setSize(100, 20);
		chkManagement4.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement4.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement4.setLocation(15, 95);
		chkManagement4.setLangMessageID("C00939");
		pnlManagement.add(chkManagement4);

		// �Ǘ�4����
		ctrlManagement4Name.setSize(120, 20);
		ctrlManagement4Name.setLocation(115, 95);
		ctrlManagement4Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement4Name);

		// �Ǘ��敪5
		chkManagement5.setSize(100, 20);
		chkManagement5.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement5.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement5.setLocation(15, 120);
		chkManagement5.setLangMessageID("C00940");
		pnlManagement.add(chkManagement5);

		// �Ǘ�5����
		ctrlManagement5Name.setSize(120, 20);
		ctrlManagement5Name.setLocation(115, 120);
		ctrlManagement5Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement5Name);

		// �Ǘ��敪6
		chkManagement6.setSize(100, 20);
		chkManagement6.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement6.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement6.setLocation(15, 145);
		chkManagement6.setLangMessageID("C00941");
		pnlManagement.add(chkManagement6);

		// �Ǘ�6����
		ctrlManagement6Name.setSize(120, 20);
		ctrlManagement6Name.setLocation(115, 145);
		ctrlManagement6Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement6Name);

		// ���v���אݒ�
		pnlNotAccount.setLayout(null);
		pnlNotAccount.setBorder(TBorderFactory.createTitledBorder(getWord("C11183")));
		pnlNotAccount.setSize(370, 105);
		pnlNotAccount.setLocation(10, 305);
		pnlAccount.add(pnlNotAccount);

		// ���v���׋敪1
		cboNotAccount1.setLabelSize(100);
		cboNotAccount1.setComboSize(110);
		cboNotAccount1.setSize(215, 20);
		cboNotAccount1.setLocation(20, 20);
		cboNotAccount1.setLangMessageID("C00943");
		initNotAccountComboBox(cboNotAccount1.getComboBox());
		pnlNotAccount.add(cboNotAccount1);

		// ���v����1����
		ctrlNotAccount1Name.setSize(120, 20);
		ctrlNotAccount1Name.setLocation(240, 20);
		ctrlNotAccount1Name.setMaxLength(TransUtil.HM_NAME_LENGTH);
		pnlNotAccount.add(ctrlNotAccount1Name);

		// ���v���׋敪2
		cboNotAccount2.setLabelSize(100);
		cboNotAccount2.setComboSize(110);
		cboNotAccount2.setSize(215, 20);
		cboNotAccount2.setLocation(20, 45);
		cboNotAccount2.setLangMessageID("C00944");
		initNotAccountComboBox(cboNotAccount2.getComboBox());
		pnlNotAccount.add(cboNotAccount2);

		// ���v����2����
		ctrlNotAccount2Name.setSize(120, 20);
		ctrlNotAccount2Name.setLocation(240, 45);
		ctrlNotAccount2Name.setMaxLength(TransUtil.HM_NAME_LENGTH);
		pnlNotAccount.add(ctrlNotAccount2Name);

		// ���v���׋敪3
		cboNotAccount3.setLabelSize(100);
		cboNotAccount3.setComboSize(110);
		cboNotAccount3.setSize(215, 20);
		cboNotAccount3.setLocation(20, 70);
		cboNotAccount3.setLangMessageID("C00945");
		initNotAccountComboBox(cboNotAccount3.getComboBox());
		pnlNotAccount.add(cboNotAccount3);

		// ���v����3����
		ctrlNotAccount3Name.setSize(120, 20);
		ctrlNotAccount3Name.setLocation(240, 70);
		ctrlNotAccount3Name.setMaxLength(TransUtil.HM_NAME_LENGTH);
		pnlNotAccount.add(ctrlNotAccount3Name);

		// �`�[�ԍ��ݒ�
		pnlSlipNo.setLayout(null);
		pnlSlipNo.setBorder(TBorderFactory.createTitledBorder(getWord("C11184")));// �`�[�ԍ��ݒ�
		pnlSlipNo.setSize(270, 130);
		pnlSlipNo.setLocation(10, 10);
		pnlSlip.add(pnlSlipNo);

		// �����̔ԕ�����
		ctrlSlipNoDigit.setLabelSize(85);
		ctrlSlipNoDigit.setFieldSize(20);
		ctrlSlipNoDigit.setSize(145, 20);
		ctrlSlipNoDigit.setLocation(10, 20);
		ctrlSlipNoDigit.setPositiveOnly(true);
		ctrlSlipNoDigit.setLabelText(TModelUIUtil.getShortWord(("C00224")));
		ctrlSlipNoDigit.setMaxLength(1);
		pnlSlipNo.add(ctrlSlipNoDigit);

		// �����ݒ荀��1
		cboSlipNoAdopt1.setLabelSize(100);
		cboSlipNoAdopt1.setComboSize(140);
		cboSlipNoAdopt1.setSize(245, 20);
		cboSlipNoAdopt1.setLocation(10, 45);
		cboSlipNoAdopt1.setLangMessageID("C00713");
		initSlipNoComboBox(cboSlipNoAdopt1.getComboBox());
		pnlSlipNo.add(cboSlipNoAdopt1);

		// �����ݒ荀��2
		cboSlipNoAdopt2.setLabelSize(100);
		cboSlipNoAdopt2.setComboSize(140);
		cboSlipNoAdopt2.setSize(245, 20);
		cboSlipNoAdopt2.setLocation(10, 70);
		cboSlipNoAdopt2.setLangMessageID("C00714");
		initSlipNoComboBox(cboSlipNoAdopt2.getComboBox());
		pnlSlipNo.add(cboSlipNoAdopt2);

		// �����ݒ荀��3
		cboSlipNoAdopt3.setLabelSize(100);
		cboSlipNoAdopt3.setComboSize(140);
		cboSlipNoAdopt3.setSize(245, 20);
		cboSlipNoAdopt3.setLocation(10, 95);
		cboSlipNoAdopt3.setLangMessageID("C00715");
		initSlipNoComboBox(cboSlipNoAdopt3.getComboBox());
		pnlSlipNo.add(cboSlipNoAdopt3);

		// �[������
		pnlFraction.setLayout(null);
		pnlFraction.setBorder(TBorderFactory.createTitledBorder(getWord("C11185")));
		pnlFraction.setSize(270, 105);
		pnlFraction.setLocation(10, 145);
		pnlSlip.add(pnlFraction);

		// ���[�g���Z�[������
		cboExchangeFraction.setLabelSize(120);
		cboExchangeFraction.setComboSize(100);
		cboExchangeFraction.setSize(225, 20);
		cboExchangeFraction.setLocation(20, 20);
		cboExchangeFraction.setLangMessageID(TModelUIUtil.getShortWord("C00557"));
		cboExchangeFraction.getComboBox().addTextValueItem(ExchangeFraction.TRUNCATE, getWord("C00121"));// �؎�
		cboExchangeFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_OFF, getWord("C00215"));// �l�̌ܓ�
		pnlFraction.add(cboExchangeFraction);

		// �������Œ[������
		cboTemporaryGetTaxFraction.setLabelSize(120);
		cboTemporaryGetTaxFraction.setComboSize(100);
		cboTemporaryGetTaxFraction.setSize(225, 20);
		cboTemporaryGetTaxFraction.setLocation(20, 45);
		cboTemporaryGetTaxFraction.setLangMessageID(TModelUIUtil.getShortWord("C00082"));
		cboTemporaryGetTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.TRUNCATE, getWord("C00121"));// �؎�
		cboTemporaryGetTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_UP, getWord("C00120"));// �؏�
		cboTemporaryGetTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_OFF, getWord("C00215"));// �l�̌ܓ�
		pnlFraction.add(cboTemporaryGetTaxFraction);

		// ��������Œ[������
		cboTemporaryPaymentTaxFraction.setLabelSize(120);
		cboTemporaryPaymentTaxFraction.setComboSize(100);
		cboTemporaryPaymentTaxFraction.setSize(225, 20);
		cboTemporaryPaymentTaxFraction.setLocation(20, 70);
		cboTemporaryPaymentTaxFraction.setLangMessageID(TModelUIUtil.getShortWord("C00083"));
		cboTemporaryPaymentTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.TRUNCATE, getWord("C00121"));// �؎�
		cboTemporaryPaymentTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_OFF, getWord("C00215"));// �l�̌ܓ�
		pnlFraction.add(cboTemporaryPaymentTaxFraction);

		// �`�[����ݒ�
		pnlSlipPrint.setLayout(null);
		pnlSlipPrint.setBorder(TBorderFactory.createTitledBorder(getWord("C11186")));
		pnlSlipPrint.setSize(270, 75);
		pnlSlipPrint.setLocation(10, 255);
		pnlSlip.add(pnlSlipPrint);

		// �`�[����敪
		chkSlipPrint.setSize(100, 20);
		chkSlipPrint.setLocation(20, 20);
		chkSlipPrint.setLangMessageID("C01248");
		pnlSlipPrint.add(chkSlipPrint);

		// ������̏����l
		chkSlipPrintInitial.setSize(150, 20);
		chkSlipPrintInitial.setLocation(20, 45);
		chkSlipPrintInitial.setLangMessageID("C01000");
		pnlSlipPrint.add(chkSlipPrintInitial);

		// ���Z�ݒ�
		pnlConvertType.setLayout(null);
		pnlConvertType.setBorder(TBorderFactory.createTitledBorder(getWord("C11187")));
		pnlConvertType.setSize(270, 55);
		pnlConvertType.setLocation(10, 335);
		pnlSlip.add(pnlConvertType);

		// ���Z�敪
		cboConvertType.setLabelSize(120);
		cboConvertType.setComboSize(100);
		cboConvertType.setSize(225, 20);
		cboConvertType.setLocation(20, 20);
		cboConvertType.setLangMessageID("C00897");
		cboConvertType.getComboBox().addTextValueItem(ConvertType.MULTIPLICATION, TModelUIUtil.getShortWord("C00065"));
		cboConvertType.getComboBox().addTextValueItem(ConvertType.DIVIDE, TModelUIUtil.getShortWord("C00563"));
		pnlConvertType.add(cboConvertType);

		// ���Z�ݒ�
		pnlSettlementStage.setLayout(null);
		pnlSettlementStage.setBorder(TBorderFactory.createTitledBorder(getWord("C11188")));// ���Z�ݒ�
		pnlSettlementStage.setSize(380, 80);
		pnlSettlementStage.setLocation(10, 10);
		pnlSettlement.add(pnlSettlementStage);

		// ���Z�i�K�敪
		cboSettlement.setLabelSize(140);
		cboSettlement.setComboSize(140);
		cboSettlement.setSize(295, 20);
		cboSettlement.setLocation(20, 20);
		cboSettlement.setLangMessageID("C00145");
		cboSettlement.getComboBox().addItem(getWord("C00037"));// �s��
		cboSettlement.getComboBox().addItem(getWord("C00038"));// �s��Ȃ�
		pnlSettlementStage.add(cboSettlement);

		// ���Z�i�K
		ctrlSettlementStage.setSize(20, 20);
		ctrlSettlementStage.setPositiveOnly(true);
		ctrlSettlementStage.setLocation(320, 20);
		ctrlSettlementStage.setMaxLength(1);
		pnlSettlementStage.add(ctrlSettlementStage);

		lblSettlementStage.setLangMessageID("C00373");// ��
		lblSettlementStage.setSize(40, 20);
		lblSettlementStage.setLocation(345, 20);
		pnlSettlementStage.add(lblSettlementStage);

		// ���Z�`�[���͋敪
		cboSettlementPer.setLabelSize(140);
		cboSettlementPer.setComboSize(140);
		cboSettlementPer.setSize(295, 20);
		cboSettlementPer.setLocation(20, 45);
		cboSettlementPer.setLangMessageID("C00139");
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.YEAR, getWord("C00009"));// ��N
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.HALF, getWord("C00435"));// ����
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.QUARTER, getWord("C10592"));// �l����
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.MONTH, getWord("C00147"));// ����
		pnlSettlementStage.add(cboSettlementPer);

		// �]���փ��[�g�敪
		pnlForeignCurrencyExchangeRate.setLayout(null);
		pnlForeignCurrencyExchangeRate.setBorder(TBorderFactory.createTitledBorder(getWord("C00454")));
		pnlForeignCurrencyExchangeRate.setSize(380, 55);
		pnlForeignCurrencyExchangeRate.setLocation(10, 95);
		pnlSettlement.add(pnlForeignCurrencyExchangeRate);

		// ���Z�`�[���͋敪
		cboForeignCurrencyExchangeRate.setLabelSize(140);
		cboForeignCurrencyExchangeRate.setComboSize(140);
		cboForeignCurrencyExchangeRate.setSize(295, 20);
		cboForeignCurrencyExchangeRate.setLocation(20, 20);
		cboForeignCurrencyExchangeRate.setLangMessageID("C00454");
		cboForeignCurrencyExchangeRate.getComboBox().addTextValueItem(EvaluationRateDate.LAST_DATE,
			TModelUIUtil.getShortWord("C00148"));// �������[�g
		cboForeignCurrencyExchangeRate.getComboBox().addTextValueItem(EvaluationRateDate.FIRST_DATE,
			TModelUIUtil.getShortWord("C00535"));// �����������[�g
		pnlForeignCurrencyExchangeRate.add(cboForeignCurrencyExchangeRate);

		// ���Ԍ��Z�J�z�敪
		pnlCarryJournalOfMidtermClosingForward.setLayout(null);
		pnlCarryJournalOfMidtermClosingForward.setBorder(TBorderFactory.createTitledBorder(getWord("C11141")));// ���Ԍ��Z�J�z�敪
		pnlCarryJournalOfMidtermClosingForward.setSize(380, 55);
		pnlCarryJournalOfMidtermClosingForward.setLocation(10, 155);
		pnlSettlement.add(pnlCarryJournalOfMidtermClosingForward);

		// ���Ԍ��Z�J�z�敪
		cboCarryJournalOfMidtermClosingForward.setLabelSize(160);
		cboCarryJournalOfMidtermClosingForward.setComboSize(140);
		cboCarryJournalOfMidtermClosingForward.setSize(305, 20);
		cboCarryJournalOfMidtermClosingForward.setLocation(5, 20);
		cboCarryJournalOfMidtermClosingForward.setLangMessageID(TModelUIUtil.getShortWord("C11141"));
		cboCarryJournalOfMidtermClosingForward.getComboBox().addTextValueItem(0, TModelUIUtil.getShortWord("C11148"));// �J��z��
		cboCarryJournalOfMidtermClosingForward.getComboBox().addTextValueItem(1, TModelUIUtil.getShortWord("C11218"));// �J��z���Ȃ�
		pnlCarryJournalOfMidtermClosingForward.add(cboCarryJournalOfMidtermClosingForward);

		// ��v����
		pnlFiscalPeriod.setLayout(null);
		pnlFiscalPeriod.setBorder(TBorderFactory.createTitledBorder(getWord("C00051")));// ��v����
		pnlFiscalPeriod.setSize(250, 55);
		pnlFiscalPeriod.setLocation(10, 10);
		pnlElse.add(pnlFiscalPeriod);

		// ����
		ctrlInitialMonth.setLabelSize(70);
		ctrlInitialMonth.setFieldSize(20);
		ctrlInitialMonth.setSize(95, 20);
		ctrlInitialMonth.setLocation(0, 20);
		ctrlInitialMonth.setPositiveOnly(true);
		ctrlInitialMonth.setLabelText(TModelUIUtil.getShortWord("C00105"));
		ctrlInitialMonth.setMaxLength(2, 0);
		pnlFiscalPeriod.add(ctrlInitialMonth);

		// ���F
		pnlApprove.setLayout(null);
		pnlApprove.setBorder(TBorderFactory.createTitledBorder(getWord("C01168")));
		pnlApprove.setSize(450, 75);
		pnlApprove.setLocation(10, 70);
		pnlElse.add(pnlApprove);

		// ���F�^�C�v
		cboWorkFlowApprove.setLabelSize(70);
		cboWorkFlowApprove.setComboSize(140);
		cboWorkFlowApprove.setLocation(0, 20);
		cboWorkFlowApprove.setLangMessageID("���F���@"); // TODO
		cboWorkFlowApprove.addItem(ApproveType.REGULAR);
		cboWorkFlowApprove.addItem(ApproveType.WORKFLOW);
		pnlApprove.add(cboWorkFlowApprove);

		cboDenyAction.setLabelSize(70);
		cboDenyAction.setComboSize(80);
		cboDenyAction.setLocation(cboWorkFlowApprove.getX() + cboWorkFlowApprove.getWidth(), 20);
		cboDenyAction.setLangMessageID("�۔F����"); // TODO
		cboDenyAction.addItem(DenyAction.BACK_ONE);
		cboDenyAction.addItem(DenyAction.BACK_FIRST);
		pnlApprove.add(cboDenyAction);

		// ���ꏳ�F
		chkFieldApprove.setSize(100, 20);
		chkFieldApprove.setLocation(20, 40);
		chkFieldApprove.setLangMessageID("C00157");
		pnlApprove.add(chkFieldApprove);

		// �o�����F
		chkAccountantApprove.setSize(100, 20);
		chkAccountantApprove.setLocation(120, 40);
		chkAccountantApprove.setLangMessageID("C01616");
		pnlApprove.add(chkAccountantApprove);

		// �ʉ�
		pnlCurrency.setLayout(null);
		pnlCurrency.setBorder(TBorderFactory.createTitledBorder(getWord("C00371")));
		pnlCurrency.setSize(320, 80);
		pnlCurrency.setLocation(10, 150);
		pnlElse.add(pnlCurrency);

		// ��ʉ�
		ctrlKeyCurrency.setLocation(20, 20);
		ctrlKeyCurrency.btn.setLangMessageID("C00907");
		pnlCurrency.add(ctrlKeyCurrency);

		// �@�\�ʉ�
		ctrlFunctionCurrency.setLocation(20, 45);
		ctrlFunctionCurrency.btn.setLangMessageID("C11084");
		pnlCurrency.add(ctrlFunctionCurrency);

		// �O���[�v��v�敪(�p�l��)
		pnlGrp.setLayout(null);
		pnlGrp.setBorder(TBorderFactory.createTitledBorder(getWord("C11142")));
		pnlGrp.setSize(250, 55);
		pnlGrp.setLocation(10, 235);
		pnlElse.add(pnlGrp);

		// �O���[�v��v�敪(�`�F�b�N�{�b�N�X)
		chkGrpKbn.setSize(100, 20);
		chkGrpKbn.setLocation(20, 20);
		chkGrpKbn.setLangMessageID("C00281");
		pnlGrp.add(chkGrpKbn);

		// IFRS�敪(�p�l��)
		pnlIfrs.setLayout(null);
		pnlIfrs.setBorder(TBorderFactory.createTitledBorder(getWord("C11143")));
		pnlIfrs.setSize(250, 55);
		pnlIfrs.setLocation(10, 295);
		pnlElse.add(pnlIfrs);

		// IFRS�敪(�`�F�b�N�{�b�N�X)
		chkIfrsKbn.setSize(100, 20);
		chkIfrsKbn.setLocation(20, 20);
		chkIfrsKbn.setLangMessageID("C00281");
		pnlIfrs.add(chkIfrsKbn);

		// �������`�F�b�N�敪(�p�l��)
		pnlHasDateKbn.setLayout(null);
		pnlHasDateKbn.setBorder(TBorderFactory.createTitledBorder(getWord("C12297")));
		pnlHasDateKbn.setSize(250, 55);
		pnlHasDateKbn.setLocation(10, 355);
		pnlElse.add(pnlHasDateKbn);

		// �������`�F�b�N�敪(�`�F�b�N�{�b�N�X)
		chkHasDateKbn.setSize(100, 20);
		chkHasDateKbn.setLocation(20, 20);
		chkHasDateKbn.setLangMessageID("C00281");
		pnlHasDateKbn.add(chkHasDateKbn);

		int y = 415;

		// �p��������SIGNER���i���Ǘ��j(�p�l��)
		if (MG0010CompanyMasterPanelCtrl.isShowARSignerEng) {
			pnlEnSigner.setLayout(null);
			pnlEnSigner.setBorder(TBorderFactory.createTitledBorder(getWord("C12175")));
			pnlEnSigner.setSize(460, 55);
			pnlEnSigner.setLocation(10, y);
			pnlElse.add(pnlEnSigner);

			// �p��������SIGNER���i���Ǘ��j
			ctrlEnSignerText.setSize(380, 20);
			ctrlEnSignerText.setLocation(20, 20);
			ctrlEnSignerText.setMaxLength(60);
			pnlEnSigner.add(ctrlEnSignerText);
			y += 60;
		}

		// invoice���x
		if (MG0010CompanyMasterPanelCtrl.isInvoice) {

			pnlInvoiceSystem.setLayout(null);
			pnlInvoiceSystem.setBorder(TBorderFactory.createTitledBorder(getWord("C12220")));
			pnlInvoiceSystem.setSize(250, 55);
			pnlInvoiceSystem.setLocation(10, y);
			pnlElse.add(pnlInvoiceSystem);

			// invoice���x �g�p����
			ctrlInvoiceChk.setSize(100, 20);
			ctrlInvoiceChk.setLocation(20, 20);
			ctrlInvoiceChk.setLangMessageID("CLM0290");
			pnlInvoiceSystem.add(ctrlInvoiceChk);

		}

		// �F�ݒ�p�l��
		pnlColor.setLayout(null);
		pnlColor.setBorder(TBorderFactory.createTitledBorder(getWord("C11144")));
		pnlColor.setSize(220, 55);
		pnlColor.setLocation(10, 10);
		pnlEnv.add(pnlColor);

		// ��АF
		ctrlCompanyColor.setCaption(TModelUIUtil.getShortWord("C11219"));
		ctrlCompanyColor.setLocation(10, 20);
		pnlColor.add(ctrlCompanyColor);

		// SPC��Џ��ݒ�^�u
		if (!MG0010CompanyMasterPanelCtrl.isNotShowSpc) {
			// �f�t�H���g�������͕\�����鎞�̂ݕ\��

			pnlSpc.setLayout(null);
			tab.addTab(getWord("C11786"), pnlSpc);// SPC��Џ��
		}

		// SPC��Џ��ݒ�p�l��
		pnlSpcInfomation.setLayout(null);
		pnlSpcInfomation.setBorder(TBorderFactory.createTitledBorder(getWord("C11786")));
		pnlSpcInfomation.setSize(470, 350);
		pnlSpcInfomation.setLocation(20, 10);
		pnlSpc.add(pnlSpcInfomation);

		// �A���p��ЃR�[�h
		ctrlConnCompanyCode.setLabelSize(110);
		ctrlConnCompanyCode.setFieldSize(100);
		ctrlConnCompanyCode.setSize(220, 20);
		ctrlConnCompanyCode.setLocation(20, 20);
		ctrlConnCompanyCode.setLabelText(getWord("C11762"));
		ctrlConnCompanyCode.setMaxLength(TransUtil.COMPANY_CODE_LENGTH);
		ctrlConnCompanyCode.setImeMode(false);
		pnlSpcInfomation.add(ctrlConnCompanyCode);

		// �T�C���ҁE��E��
		ctrlSignerPosition.setLabelSize(110);
		ctrlSignerPosition.setFieldSize(200);
		ctrlSignerPosition.setSize(320, 20);
		ctrlSignerPosition.setLocation(20, 45);
		ctrlSignerPosition.setLabelText(getWord("C11763"));
		ctrlSignerPosition.setMaxLength(TransUtil.COMPANY_NAME_LENGTH);
		ctrlSignerPosition.setImeMode(true);
		pnlSpcInfomation.add(ctrlSignerPosition);

		// �O�������S����
		ctrlRemitterName.setLabelSize(110);
		ctrlRemitterName.setFieldSize(200);
		ctrlRemitterName.setSize(320, 20);
		ctrlRemitterName.setLocation(20, 70);
		ctrlRemitterName.setLabelText(getWord("C11764"));
		ctrlRemitterName.setMaxLength(TransUtil.COMPANY_NAME_LENGTH);
		ctrlRemitterName.setImeMode(true);
		pnlSpcInfomation.add(ctrlRemitterName);

		// �A���p�d�b�ԍ�
		ctrlConnPhoneNo.setLabelSize(110);
		ctrlConnPhoneNo.setFieldSize(200);
		ctrlConnPhoneNo.setSize(320, 20);
		ctrlConnPhoneNo.setLocation(20, 95);
		ctrlConnPhoneNo.setLabelText(getWord("C11765"));
		ctrlConnPhoneNo.setMaxLength(20);
		ctrlConnPhoneNo.setImeMode(true);
		pnlSpcInfomation.add(ctrlConnPhoneNo);

		// DebitNote�Z��1
		ctrlDebitNoteAddress1.setLabelSize(120);
		ctrlDebitNoteAddress1.setFieldSize(300);
		ctrlDebitNoteAddress1.setLocation(12, 125);
		ctrlDebitNoteAddress1.setLabelText(getWord("C11787"));
		ctrlDebitNoteAddress1.setMaxLength(80);
		pnlSpcInfomation.add(ctrlDebitNoteAddress1);

		// DebitNote�Z��2
		ctrlDebitNoteAddress2.setLabelSize(120);
		ctrlDebitNoteAddress2.setFieldSize(300);
		ctrlDebitNoteAddress2.setLocation(ctrlDebitNoteAddress1.getX(), ctrlDebitNoteAddress1.getY()
			+ ctrlDebitNoteAddress1.getHeight());
		ctrlDebitNoteAddress2.setLabelText(getWord(""));
		ctrlDebitNoteAddress2.setMaxLength(80);
		pnlSpcInfomation.add(ctrlDebitNoteAddress2);

		// DebitNote�Z��3
		ctrlDebitNoteAddress3.setLabelSize(120);
		ctrlDebitNoteAddress3.setFieldSize(300);
		ctrlDebitNoteAddress3.setLocation(ctrlDebitNoteAddress1.getX(), ctrlDebitNoteAddress2.getY()
			+ ctrlDebitNoteAddress2.getHeight());
		ctrlDebitNoteAddress3.setLabelText(getWord(""));
		ctrlDebitNoteAddress3.setMaxLength(80);
		pnlSpcInfomation.add(ctrlDebitNoteAddress3);

		// DebitNote�t�b�^��񃉃x��
		ctrlDebitNoteInfoLabel.setSize(120, 20);
		ctrlDebitNoteInfoLabel.setText(getWord("C11770"));
		ctrlDebitNoteInfoLabel.setLocation(15, 200);
		pnlSpcInfomation.add(ctrlDebitNoteInfoLabel);

		// DebitNote�t�b�^���
		ctrlDebitNoteInfo.setMaxLength(512);
		ctrlDebitNoteInfo.setAltEnterToChangingLine(true);
		ctrlDebitNoteInfo.setFont(ctrlDebitNoteInfo.getFont().deriveFont(ctrlDebitNoteInfoLabel.getFont().getSize2D()));
		ctrlDebitNoteInfo.setImeMode(true);

		JScrollPane sp = new JScrollPane(ctrlDebitNoteInfo);
		sp.setBorder(MintBorderFactory.getInstance().getTextFieldBorder());
		TGuiUtil.setComponentSize(sp, new Dimension(300, 120));
		// �X�e�b�v
		sp.getVerticalScrollBar().setUnitIncrement(40);
		sp.getHorizontalScrollBar().setUnitIncrement(40);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setLocation(137, 200);
		pnlSpcInfomation.add(sp);

	}

	/**
	 * ���v���׃R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void initNotAccountComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(NonAccountingDivision.NONE,
			getWord(NonAccountingDivision.getNonAccountName(NonAccountingDivision.NONE)));
		comboBox.addTextValueItem(NonAccountingDivision.NUMBER,
			getWord(NonAccountingDivision.getNonAccountName(NonAccountingDivision.NUMBER)));
		comboBox.addTextValueItem(NonAccountingDivision.CHAR,
			getWord(NonAccountingDivision.getNonAccountName(NonAccountingDivision.CHAR)));
		comboBox.addTextValueItem(NonAccountingDivision.YMD_DATE,
			getWord(NonAccountingDivision.getNonAccountName(NonAccountingDivision.YMD_DATE)));
		comboBox.addTextValueItem(NonAccountingDivision.YMDHM_DATE,
			getWord(NonAccountingDivision.getNonAccountName(NonAccountingDivision.YMDHM_DATE)));
	}

	/**
	 * �`�[�ԍ������ݒ荀�ڃR���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void initSlipNoComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(SlipNoAdopt.NONE, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.NONE)));
		comboBox.addTextValueItem(SlipNoAdopt.YY_DATE, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.YY_DATE)));
		comboBox
			.addTextValueItem(SlipNoAdopt.YYYY_DATE, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.YYYY_DATE)));
		comboBox
			.addTextValueItem(SlipNoAdopt.YYMM_DATE, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.YYMM_DATE)));
		comboBox.addTextValueItem(SlipNoAdopt.YYYYMM_DATE,
			getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.YYYYMM_DATE)));
		comboBox.addTextValueItem(SlipNoAdopt.USER, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.USER)));
		comboBox.addTextValueItem(SlipNoAdopt.DEPARTMENT,
			getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.DEPARTMENT)));
		comboBox.addTextValueItem(SlipNoAdopt.DIVISION, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.DIVISION)));
		comboBox.addTextValueItem(SlipNoAdopt.CODE, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.CODE)));
		comboBox.addTextValueItem(SlipNoAdopt.SLIPTYPE, getWord(SlipNoAdopt.getSlipNoAdoptName(SlipNoAdopt.SLIPTYPE)));
	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNames.setTabControlNo(i++);
		ctrlAddress1.setTabControlNo(i++);
		ctrlAddress2.setTabControlNo(i++);
		ctrlKana.setTabControlNo(i++);
		ctrlZipCode.setTabControlNo(i++);
		ctrlPhoneNo.setTabControlNo(i++);
		ctrlFaxNo.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		ctrlInvRegNo.setTabControlNo(i++);

		ctrlItemName.setTabControlNo(i++);
		ctrlSubItemName.setTabControlNo(i++);
		chkDetailItem.setTabControlNo(i++);
		ctrlDetailItemName.setTabControlNo(i++);
		chkManagement1.setTabControlNo(i++);
		ctrlManagement1Name.setTabControlNo(i++);
		chkManagement2.setTabControlNo(i++);
		ctrlManagement2Name.setTabControlNo(i++);
		chkManagement3.setTabControlNo(i++);
		ctrlManagement3Name.setTabControlNo(i++);
		chkManagement4.setTabControlNo(i++);
		ctrlManagement4Name.setTabControlNo(i++);
		chkManagement5.setTabControlNo(i++);
		ctrlManagement5Name.setTabControlNo(i++);
		chkManagement6.setTabControlNo(i++);
		ctrlManagement6Name.setTabControlNo(i++);
		cboNotAccount1.setTabControlNo(i++);
		ctrlNotAccount1Name.setTabControlNo(i++);
		cboNotAccount2.setTabControlNo(i++);
		ctrlNotAccount2Name.setTabControlNo(i++);
		cboNotAccount3.setTabControlNo(i++);
		ctrlNotAccount3Name.setTabControlNo(i++);

		ctrlSlipNoDigit.setTabControlNo(i++);
		cboSlipNoAdopt1.setTabControlNo(i++);
		cboSlipNoAdopt2.setTabControlNo(i++);
		cboSlipNoAdopt3.setTabControlNo(i++);
		cboExchangeFraction.setTabControlNo(i++);
		cboTemporaryGetTaxFraction.setTabControlNo(i++);
		cboTemporaryPaymentTaxFraction.setTabControlNo(i++);
		chkSlipPrint.setTabControlNo(i++);
		chkSlipPrintInitial.setTabControlNo(i++);
		cboConvertType.setTabControlNo(i++);

		cboSettlement.setTabControlNo(i++);
		ctrlSettlementStage.setTabControlNo(i++);
		cboSettlementPer.setTabControlNo(i++);
		cboForeignCurrencyExchangeRate.setTabControlNo(i++);
		cboCarryJournalOfMidtermClosingForward.setTabControlNo(i++);

		ctrlInitialMonth.setTabControlNo(i++);
		chkFieldApprove.setTabControlNo(i++);
		chkAccountantApprove.setTabControlNo(i++);
		ctrlKeyCurrency.setTabControlNo(i++);
		ctrlFunctionCurrency.setTabControlNo(i++);
		chkGrpKbn.setTabControlNo(i++);
		chkIfrsKbn.setTabControlNo(i++);
		chkHasDateKbn.setTabControlNo(i++);
		ctrlEnSignerText.setTabControlNo(i++);
		ctrlInvoiceChk.setTabControlNo(i++);

		ctrlCompanyColor.setTabControlNo(i++);

		ctrlEngName.setTabControlNo(i++);
		ctrlEngNames.setTabControlNo(i++);
		ctrlEngAddress1.setTabControlNo(i++);
		ctrlEngAddress2.setTabControlNo(i++);

		ctrlConnCompanyCode.setTabControlNo(i++);
		ctrlSignerPosition.setTabControlNo(i++);
		ctrlRemitterName.setTabControlNo(i++);
		ctrlConnPhoneNo.setTabControlNo(i++);
		ctrlDebitNoteAddress1.setTabControlNo(i++);
		ctrlDebitNoteAddress2.setTabControlNo(i++);
		ctrlDebitNoteAddress3.setTabControlNo(i++);
		ctrlDebitNoteInfo.setTabControlNo(i++);

		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);

	}

}
