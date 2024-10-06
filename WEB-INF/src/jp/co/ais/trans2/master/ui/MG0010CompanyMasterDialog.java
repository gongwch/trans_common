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
 * 会社コントロールマスタの編集画面
 * 
 * @author AIS
 */
public class MG0010CompanyMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** タブ */
	public TTabbedPane tab;

	/** 会社情報設定タブ */
	public TPanel pnlBase;

	/** 会社情報設定(英語版)タブ */
	public TPanel pnlBaseEng;

	/** 科目と管理設定タブ */
	public TPanel pnlAccount;

	/** 伝票設定タブ */
	public TPanel pnlSlip;

	/** 決算設定タブ */
	public TPanel pnlSettlement;

	/** その他設定タブ */
	public TPanel pnlElse;

	/** 環境設定タブ */
	public TPanel pnlEnv;

	/** 会社情報パネル */
	public TPanel pnlCompany;

	/** 会社情報(英語版)パネル */
	public TPanel pnlCompanyEng;

	/** コード */
	public TLabelField ctrlCode;

	/** 名称 */
	public TLabelField ctrlName;

	/** 略称 */
	public TLabelField ctrlNames;

	/** 住所1 */
	public TLabelField ctrlAddress1;

	/** 住所2 */
	public TLabelField ctrlAddress2;

	/** 住所ｶﾅ */
	public TLabelField ctrlKana;

	/** 郵便番号 */
	public TLabelField ctrlZipCode;

	/** 電話番号 */
	public TLabelField ctrlPhoneNo;

	/** FAX番号 */
	public TLabelField ctrlFaxNo;

	/** 適格請求書発行事業者登録番号 */
	public TLabelField ctrlInvRegNo;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/** 名称(英語版) */
	public TLabelField ctrlEngName;

	/** 略称(英語版) */
	public TLabelField ctrlEngNames;

	/** 住所1(英語版) */
	public TLabelField ctrlEngAddress1;

	/** 住所2(英語版) */
	public TLabelField ctrlEngAddress2;

	/** 科目設定 */
	public TPanel pnlItem;

	/** 科目名称 */
	public TLabelField ctrlItemName;

	/** 補助科目名称 */
	public TLabelField ctrlSubItemName;

	/** 内訳科目管理 */
	public TCheckBox chkDetailItem;

	/** 内訳科目名称 */
	public TTextField ctrlDetailItemName;

	/** 管理区分設定 */
	public TPanel pnlManagement;

	/** 管理1管理 */
	public TCheckBox chkManagement1;

	/** 管理1名称 */
	public TTextField ctrlManagement1Name;

	/** 管理2管理 */
	public TCheckBox chkManagement2;

	/** 管理2名称 */
	public TTextField ctrlManagement2Name;

	/** 管理3管理 */
	public TCheckBox chkManagement3;

	/** 管理3名称 */
	public TTextField ctrlManagement3Name;

	/** 管理4管理 */
	public TCheckBox chkManagement4;

	/** 管理4名称 */
	public TTextField ctrlManagement4Name;

	/** 管理5管理 */
	public TCheckBox chkManagement5;

	/** 管理5名称 */
	public TTextField ctrlManagement5Name;

	/** 管理6管理 */
	public TCheckBox chkManagement6;

	/** 管理6名称 */
	public TTextField ctrlManagement6Name;

	/** 非会計明細区分設定 */
	public TPanel pnlNotAccount;

	/** 非会計明細1管理 */
	public TLabelComboBox cboNotAccount1;

	/** 非会計明細1名称 */
	public TTextField ctrlNotAccount1Name;

	/** 非会計明細2管理 */
	public TLabelComboBox cboNotAccount2;

	/** 非会計明細2名称 */
	public TTextField ctrlNotAccount2Name;

	/** 非会計明細3管理 */
	public TLabelComboBox cboNotAccount3;

	/** 非会計明細3名称 */
	public TTextField ctrlNotAccount3Name;

	/** 伝票番号設定 */
	public TPanel pnlSlipNo;

	/** 伝票番号連番桁数 */
	public TLabelNumericField ctrlSlipNoDigit;

	/** 自動設定項目1 */
	public TLabelComboBox cboSlipNoAdopt1;

	/** 自動設定項目2 */
	public TLabelComboBox cboSlipNoAdopt2;

	/** 自動設定項目3 */
	public TLabelComboBox cboSlipNoAdopt3;

	/** 端数処理設定 */
	public TPanel pnlFraction;

	/** レート換算端数処理 */
	public TLabelComboBox cboExchangeFraction;

	/** 仮受消費税端数処理 */
	public TLabelComboBox cboTemporaryGetTaxFraction;

	/** 仮払消費税端数処理 */
	public TLabelComboBox cboTemporaryPaymentTaxFraction;

	/** 伝票印刷に関する設定 */
	public TPanel pnlSlipPrint;

	/** 伝票印刷区分 */
	public TCheckBox chkSlipPrint;

	/** 印刷時の初期値 */
	public TCheckBox chkSlipPrintInitial;

	/** 決算段階区分 */
	public TPanel pnlSettlementStage;

	/** 決算を行う/行わない */
	public TLabelComboBox cboSettlement;

	/** 決算段階 */
	public TNumericField ctrlSettlementStage;

	/** 決算段階 */
	public TLabel lblSettlementStage;

	/** 決算伝票入力区分 */
	public TLabelComboBox cboSettlementPer;

	/** 換算区分 */
	public TPanel pnlConvertType;

	/** 換算区分 */
	public TLabelComboBox cboConvertType;

	/** 評価替レート区分 */
	public TPanel pnlForeignCurrencyExchangeRate;

	/** 評価替レート区分 */
	public TLabelComboBox cboForeignCurrencyExchangeRate;

	/** 中間決算繰越区分 */
	public TPanel pnlCarryJournalOfMidtermClosingForward;

	/** 中間決算繰越区分 */
	public TLabelComboBox cboCarryJournalOfMidtermClosingForward;

	/** 会計期間 */
	public TPanel pnlFiscalPeriod;

	/** 期首月 */
	public TLabelNumericField ctrlInitialMonth;

	/** 承認 */
	public TPanel pnlApprove;

	/** ワークフロー承認 */
	public TEnumComboBox<ApproveType> cboWorkFlowApprove;

	/** (ワークフロー承認)否認制御 */
	public TEnumComboBox<DenyAction> cboDenyAction;

	/** 現場承認 */
	public TCheckBox chkFieldApprove;

	/** 経理承認 */
	public TCheckBox chkAccountantApprove;

	/** 通貨 */
	public TPanel pnlCurrency;

	/** 基軸通貨 */
	public TCurrencyReference ctrlKeyCurrency;

	/** 機能通貨 */
	public TCurrencyReference ctrlFunctionCurrency;

	/** グループ会計設定 */
	public TPanel pnlGrp;

	/** グループ会計区分 */
	public TCheckBox chkGrpKbn;

	/** IFRS区分設定 */
	public TPanel pnlIfrs;

	/** IFRS区分 */
	public TCheckBox chkIfrsKbn;

	/** 発生日チェック設定 */
	public TPanel pnlHasDateKbn;

	/** 発生日チェック区分 */
	public TCheckBox chkHasDateKbn;

	/** 英文請求書SIGNER欄（債権管理）設定 */
	public TPanel pnlEnSigner;

	/** 英文請求書SIGNER欄（債権管理） */
	public TTextField ctrlEnSignerText;

	/** インボイス制度設定 */
	public TPanel pnlInvoiceSystem;

	/** チェックボックスインボイス使用 */
	public TCheckBox ctrlInvoiceChk;

	/** 色設定パネル */
	public TPanel pnlColor;

	/** 会社色 */
	public TColor ctrlCompanyColor;

	/** SPC会社情報タブ */
	public TPanel pnlSpc;

	/** SPC会社情報設定 */
	public TPanel pnlSpcInfomation;

	/** メモ情報：連結用会社コード */
	public TLabelField ctrlConnCompanyCode;

	/** メモ情報：サイン者・役職名 */
	public TLabelField ctrlSignerPosition;

	/** メモ情報：外国送金担当者 */
	public TLabelField ctrlRemitterName;

	/** メモ情報：連絡先電話番号 */
	public TLabelField ctrlConnPhoneNo;

	/** メモ情報：DebitNote住所1 */
	public TLabelField ctrlDebitNoteAddress1;

	/** メモ情報：DebitNote住所2 */
	public TLabelField ctrlDebitNoteAddress2;

	/** メモ情報：DebitNote住所3 */
	public TLabelField ctrlDebitNoteAddress3;

	/** メモ情報：DebitNoteフッタ情報ラベル */
	public TLabel ctrlDebitNoteInfoLabel;

	/** メモ情報：DebitNoteフッタ情報 */
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

		// INVOICE使用(会社基礎情報英語版)
		ctrlEngName = new TLabelField();
		ctrlEngNames = new TLabelField();
		ctrlEngAddress1 = new TLabelField();
		ctrlEngAddress2 = new TLabelField();

		// SPC会社情報タブ
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

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
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

		// 基本設定タブ
		pnlBase.setLayout(null);
		tab.addTab(getWord("C11165"), pnlBase);// 会社情報

		// 会計設定タブ
		pnlAccount.setLayout(null);
		tab.addTab(getWord("C11166"), pnlAccount);// 科目と管理

		// 伝票設定タブ
		pnlSlip.setLayout(null);
		tab.addTab(getWord("C11172"), pnlSlip);// 伝 票

		// 決算設定タブ
		pnlSettlement.setLayout(null);
		tab.addTab(getWord("C11173"), pnlSettlement);// 決 算

		// その他
		pnlElse.setLayout(null);
		tab.addTab(getWord("C00338"), pnlElse);// その他

		// 環境設定タブ
		pnlEnv.setLayout(null);
		tab.addTab(getWord("C11174"), pnlEnv);// 環 境

		// 会社情報
		pnlCompany.setLayout(null);
		pnlCompany.setBorder(TBorderFactory.createTitledBorder(getWord("C11175")));// 会社情報設定
		pnlCompany.setSize(750, 400);
		pnlCompany.setLocation(10, 10);
		pnlBase.add(pnlCompany);

		// 会社コード
		ctrlCode.setLabelSize(110);
		ctrlCode.setFieldSize(75);
		ctrlCode.setSize(190, 20);
		ctrlCode.setLocation(10, 20);
		ctrlCode.setLabelText(getWord("C00596"));
		ctrlCode.setMaxLength(TransUtil.COMPANY_CODE_LENGTH);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		pnlCompany.add(ctrlCode);

		// 名称
		ctrlName.setLabelSize(110);
		ctrlName.setFieldSize(300);
		ctrlName.setSize(415, 20);
		ctrlName.setLocation(10, 45);
		ctrlName.setLabelText(getWord("C00685"));
		ctrlName.setMaxLength(TransUtil.COMPANY_NAME_LENGTH);
		pnlCompany.add(ctrlName);

		// 略称
		ctrlNames.setLabelSize(110);
		ctrlNames.setFieldSize(150);
		ctrlNames.setSize(265, 20);
		ctrlNames.setLocation(10, 70);
		ctrlNames.setLabelText(TModelUIUtil.getShortWord(("C00686")));
		ctrlNames.setMaxLength(TransUtil.COMPANY_NAMES_LENGTH);
		pnlCompany.add(ctrlNames);

		// 住所1
		ctrlAddress1.setLabelSize(110);
		ctrlAddress1.setFieldSize(500);
		ctrlAddress1.setSize(615, 20);
		ctrlAddress1.setLocation(10, 95);
		ctrlAddress1.setLabelText(getWord("C00687"));
		ctrlAddress1.setMaxLength(TransUtil.COMPANY_ADDRESS_LENGTH);
		pnlCompany.add(ctrlAddress1);

		// 住所2
		ctrlAddress2.setLabelSize(110);
		ctrlAddress2.setFieldSize(500);
		ctrlAddress2.setSize(615, 20);
		ctrlAddress2.setLocation(10, 120);
		ctrlAddress2.setLabelText(getWord("C00688"));
		ctrlAddress2.setMaxLength(TransUtil.COMPANY_ADDRESS_LENGTH);
		pnlCompany.add(ctrlAddress2);

		// 住所ｶﾅ
		ctrlKana.setLabelSize(110);
		ctrlKana.setFieldSize(500);
		ctrlKana.setSize(615, 20);
		ctrlKana.setLocation(10, 145);
		ctrlKana.setLabelText(getWord("C01152"));
		ctrlKana.setMaxLength(TransUtil.COMPANY_KANA_LENGTH);
		pnlCompany.add(ctrlKana);

		// 郵便番号
		ctrlZipCode.setLabelSize(110);
		ctrlZipCode.setFieldSize(75);
		ctrlZipCode.setSize(190, 20);
		ctrlZipCode.setLocation(10, 170);
		ctrlZipCode.setLabelText(getWord("C00527"));
		ctrlZipCode.setImeMode(false);
		ctrlZipCode.setMaxLength(TransUtil.ZIP_LENGTH);
		pnlCompany.add(ctrlZipCode);

		// 電話番号
		ctrlPhoneNo.setLabelSize(110);
		ctrlPhoneNo.setFieldSize(90);
		ctrlPhoneNo.setSize(205, 20);
		ctrlPhoneNo.setLocation(10, 195);
		ctrlPhoneNo.setLabelText(getWord("C00393"));
		ctrlPhoneNo.setImeMode(false);
		ctrlPhoneNo.setMaxLength(TransUtil.PHONE_NO_LENGTH);
		pnlCompany.add(ctrlPhoneNo);

		// FAX番号
		ctrlFaxNo.setLabelSize(110);
		ctrlFaxNo.setFieldSize(90);
		ctrlFaxNo.setSize(205, 20);
		ctrlFaxNo.setLocation(10, 220);
		ctrlFaxNo.setLabelText(getWord("C00690"));
		ctrlFaxNo.setImeMode(false);
		ctrlFaxNo.setMaxLength(TransUtil.FAX_NO_LENGTH);
		pnlCompany.add(ctrlFaxNo);
		// invoice制度
		if (MG0010CompanyMasterPanelCtrl.isInvoice) {
			// 適格請求書発行事業者登録番号
			ctrlInvRegNo.setLabelSize(170);
			ctrlInvRegNo.setFieldSize(120);
			ctrlInvRegNo.setSize(305, 20);
			ctrlInvRegNo.setLocation(ctrlCode.getX() + ctrlCode.getWidth() + 125, ctrlFaxNo.getY());
			ctrlInvRegNo.setLabelText(getWord("C12171"));
			ctrlInvRegNo.setImeMode(false);
			ctrlInvRegNo.setMaxLength(TransUtil.INV_REG_NO_LENGTH);
			pnlCompany.add(ctrlInvRegNo);

		}
		// 開始年月日
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 245);
		dtBeginDate.setLangMessageID("C00055");
		pnlCompany.add(dtBeginDate);

		// 終了年月日
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 270);
		dtEndDate.setLangMessageID("C00261");
		pnlCompany.add(dtEndDate);

		{
			// INVOICEタブ
			if (MG0010CompanyMasterPanelCtrl.isShowCompanyEng) {
				// デフォルト非表示

				pnlBaseEng.setLayout(null);
				tab.addTab(getWord("COP988"), pnlBaseEng);
			}

			// 会社情報設定(英語版)
			pnlCompanyEng.setLayout(null);
			pnlCompanyEng.setBorder(TBorderFactory.createTitledBorder(getWord("COP988")));
			pnlCompanyEng.setSize(750, 200);
			pnlCompanyEng.setLocation(10, 10);
			pnlBaseEng.add(pnlCompanyEng);

			// 名称(英字)
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

			// 略称(英字)
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

			// 住所1(英字)
			ctrlEngAddress1.setLabelSize(110);
			ctrlEngAddress1.setFieldSize(600);
			ctrlEngAddress1.setSize(715, 20);
			ctrlEngAddress1.setLocation(0, ctrlEngNames.getY() + ctrlEngNames.getHeight() + 5);
			ctrlEngAddress1.setLabelText(getWord("C11893"));
			ctrlEngAddress1.setMaxLength(200);
			ctrlEngAddress1.setImeMode(false);
			pnlCompanyEng.add(ctrlEngAddress1);

			// 住所2(英字)
			ctrlEngAddress2.setLabelSize(110);
			ctrlEngAddress2.setFieldSize(600);
			ctrlEngAddress2.setSize(715, 20);
			ctrlEngAddress2.setLocation(0, ctrlEngAddress1.getY() + ctrlEngAddress1.getHeight() + 5);
			ctrlEngAddress2.setLabelText(getWord("C11894"));
			ctrlEngAddress2.setMaxLength(200);
			ctrlEngAddress2.setImeMode(false);
			pnlCompanyEng.add(ctrlEngAddress2);
		}

		// 科目
		pnlItem.setLayout(null);
		pnlItem.setBorder(TBorderFactory.createTitledBorder(getWord("C11181")));
		pnlItem.setSize(260, 105);
		pnlItem.setLocation(10, 10);
		pnlAccount.add(pnlItem);

		// 科目名称
		ctrlItemName.setLabelSize(100);
		ctrlItemName.setFieldSize(120);
		ctrlItemName.setSize(225, 20);
		ctrlItemName.setLocation(10, 20);
		ctrlItemName.setLabelText(getWord("C00700"));
		ctrlItemName.setMaxLength(TransUtil.KMK_HKM_UKM_NAME_LENGTH);
		pnlItem.add(ctrlItemName);

		// 補助科目名称
		ctrlSubItemName.setLabelSize(100);
		ctrlSubItemName.setFieldSize(120);
		ctrlSubItemName.setSize(225, 20);
		ctrlSubItemName.setLocation(10, 45);
		ctrlSubItemName.setLabelText(TModelUIUtil.getShortWord(("C00701")));
		ctrlSubItemName.setMaxLength(TransUtil.KMK_HKM_UKM_NAME_LENGTH);
		pnlItem.add(ctrlSubItemName);

		// 内訳科目管理
		chkDetailItem.setSize(100, 20);
		chkDetailItem.setHorizontalTextPosition(TCheckBox.LEFT);
		chkDetailItem.setHorizontalAlignment(TCheckBox.RIGHT);
		chkDetailItem.setLocation(15, 70);
		chkDetailItem.setLangMessageID("C00942");
		pnlItem.add(chkDetailItem);

		// 内訳科目名称
		ctrlDetailItemName.setSize(120, 20);
		ctrlDetailItemName.setLocation(115, 70);
		ctrlDetailItemName.setMaxLength(TransUtil.KMK_HKM_UKM_NAME_LENGTH);
		pnlItem.add(ctrlDetailItemName);

		// 管理設定
		pnlManagement.setLayout(null);
		pnlManagement.setBorder(TBorderFactory.createTitledBorder(getWord("C11182")));
		pnlManagement.setSize(260, 180);
		pnlManagement.setLocation(10, 120);
		pnlAccount.add(pnlManagement);

		// 管理区分1
		chkManagement1.setSize(100, 20);
		chkManagement1.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement1.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement1.setLocation(15, 20);
		chkManagement1.setLangMessageID("C00936");
		pnlManagement.add(chkManagement1);

		// 管理1名称
		ctrlManagement1Name.setSize(120, 20);
		ctrlManagement1Name.setLocation(115, 20);
		ctrlManagement1Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement1Name);

		// 管理区分2
		chkManagement2.setSize(100, 20);
		chkManagement2.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement2.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement2.setLocation(15, 45);
		chkManagement2.setLangMessageID("C00937");
		pnlManagement.add(chkManagement2);

		// 管理2名称
		ctrlManagement2Name.setSize(120, 20);
		ctrlManagement2Name.setLocation(115, 45);
		ctrlManagement2Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement2Name);

		// 管理区分3
		chkManagement3.setSize(100, 20);
		chkManagement3.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement3.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement3.setLocation(15, 70);
		chkManagement3.setLangMessageID("C00938");
		pnlManagement.add(chkManagement3);

		// 管理3名称
		ctrlManagement3Name.setSize(120, 20);
		ctrlManagement3Name.setLocation(115, 70);
		ctrlManagement3Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement3Name);

		// 管理区分4
		chkManagement4.setSize(100, 20);
		chkManagement4.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement4.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement4.setLocation(15, 95);
		chkManagement4.setLangMessageID("C00939");
		pnlManagement.add(chkManagement4);

		// 管理4名称
		ctrlManagement4Name.setSize(120, 20);
		ctrlManagement4Name.setLocation(115, 95);
		ctrlManagement4Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement4Name);

		// 管理区分5
		chkManagement5.setSize(100, 20);
		chkManagement5.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement5.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement5.setLocation(15, 120);
		chkManagement5.setLangMessageID("C00940");
		pnlManagement.add(chkManagement5);

		// 管理5名称
		ctrlManagement5Name.setSize(120, 20);
		ctrlManagement5Name.setLocation(115, 120);
		ctrlManagement5Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement5Name);

		// 管理区分6
		chkManagement6.setSize(100, 20);
		chkManagement6.setHorizontalTextPosition(TCheckBox.LEFT);
		chkManagement6.setHorizontalAlignment(TCheckBox.RIGHT);
		chkManagement6.setLocation(15, 145);
		chkManagement6.setLangMessageID("C00941");
		pnlManagement.add(chkManagement6);

		// 管理6名称
		ctrlManagement6Name.setSize(120, 20);
		ctrlManagement6Name.setLocation(115, 145);
		ctrlManagement6Name.setMaxLength(TransUtil.KNR_NAME_LENGTH);
		pnlManagement.add(ctrlManagement6Name);

		// 非会計明細設定
		pnlNotAccount.setLayout(null);
		pnlNotAccount.setBorder(TBorderFactory.createTitledBorder(getWord("C11183")));
		pnlNotAccount.setSize(370, 105);
		pnlNotAccount.setLocation(10, 305);
		pnlAccount.add(pnlNotAccount);

		// 非会計明細区分1
		cboNotAccount1.setLabelSize(100);
		cboNotAccount1.setComboSize(110);
		cboNotAccount1.setSize(215, 20);
		cboNotAccount1.setLocation(20, 20);
		cboNotAccount1.setLangMessageID("C00943");
		initNotAccountComboBox(cboNotAccount1.getComboBox());
		pnlNotAccount.add(cboNotAccount1);

		// 非会計明細1名称
		ctrlNotAccount1Name.setSize(120, 20);
		ctrlNotAccount1Name.setLocation(240, 20);
		ctrlNotAccount1Name.setMaxLength(TransUtil.HM_NAME_LENGTH);
		pnlNotAccount.add(ctrlNotAccount1Name);

		// 非会計明細区分2
		cboNotAccount2.setLabelSize(100);
		cboNotAccount2.setComboSize(110);
		cboNotAccount2.setSize(215, 20);
		cboNotAccount2.setLocation(20, 45);
		cboNotAccount2.setLangMessageID("C00944");
		initNotAccountComboBox(cboNotAccount2.getComboBox());
		pnlNotAccount.add(cboNotAccount2);

		// 非会計明細2名称
		ctrlNotAccount2Name.setSize(120, 20);
		ctrlNotAccount2Name.setLocation(240, 45);
		ctrlNotAccount2Name.setMaxLength(TransUtil.HM_NAME_LENGTH);
		pnlNotAccount.add(ctrlNotAccount2Name);

		// 非会計明細区分3
		cboNotAccount3.setLabelSize(100);
		cboNotAccount3.setComboSize(110);
		cboNotAccount3.setSize(215, 20);
		cboNotAccount3.setLocation(20, 70);
		cboNotAccount3.setLangMessageID("C00945");
		initNotAccountComboBox(cboNotAccount3.getComboBox());
		pnlNotAccount.add(cboNotAccount3);

		// 非会計明細3名称
		ctrlNotAccount3Name.setSize(120, 20);
		ctrlNotAccount3Name.setLocation(240, 70);
		ctrlNotAccount3Name.setMaxLength(TransUtil.HM_NAME_LENGTH);
		pnlNotAccount.add(ctrlNotAccount3Name);

		// 伝票番号設定
		pnlSlipNo.setLayout(null);
		pnlSlipNo.setBorder(TBorderFactory.createTitledBorder(getWord("C11184")));// 伝票番号設定
		pnlSlipNo.setSize(270, 130);
		pnlSlipNo.setLocation(10, 10);
		pnlSlip.add(pnlSlipNo);

		// 自動採番部桁数
		ctrlSlipNoDigit.setLabelSize(85);
		ctrlSlipNoDigit.setFieldSize(20);
		ctrlSlipNoDigit.setSize(145, 20);
		ctrlSlipNoDigit.setLocation(10, 20);
		ctrlSlipNoDigit.setPositiveOnly(true);
		ctrlSlipNoDigit.setLabelText(TModelUIUtil.getShortWord(("C00224")));
		ctrlSlipNoDigit.setMaxLength(1);
		pnlSlipNo.add(ctrlSlipNoDigit);

		// 自動設定項目1
		cboSlipNoAdopt1.setLabelSize(100);
		cboSlipNoAdopt1.setComboSize(140);
		cboSlipNoAdopt1.setSize(245, 20);
		cboSlipNoAdopt1.setLocation(10, 45);
		cboSlipNoAdopt1.setLangMessageID("C00713");
		initSlipNoComboBox(cboSlipNoAdopt1.getComboBox());
		pnlSlipNo.add(cboSlipNoAdopt1);

		// 自動設定項目2
		cboSlipNoAdopt2.setLabelSize(100);
		cboSlipNoAdopt2.setComboSize(140);
		cboSlipNoAdopt2.setSize(245, 20);
		cboSlipNoAdopt2.setLocation(10, 70);
		cboSlipNoAdopt2.setLangMessageID("C00714");
		initSlipNoComboBox(cboSlipNoAdopt2.getComboBox());
		pnlSlipNo.add(cboSlipNoAdopt2);

		// 自動設定項目3
		cboSlipNoAdopt3.setLabelSize(100);
		cboSlipNoAdopt3.setComboSize(140);
		cboSlipNoAdopt3.setSize(245, 20);
		cboSlipNoAdopt3.setLocation(10, 95);
		cboSlipNoAdopt3.setLangMessageID("C00715");
		initSlipNoComboBox(cboSlipNoAdopt3.getComboBox());
		pnlSlipNo.add(cboSlipNoAdopt3);

		// 端数処理
		pnlFraction.setLayout(null);
		pnlFraction.setBorder(TBorderFactory.createTitledBorder(getWord("C11185")));
		pnlFraction.setSize(270, 105);
		pnlFraction.setLocation(10, 145);
		pnlSlip.add(pnlFraction);

		// レート換算端数処理
		cboExchangeFraction.setLabelSize(120);
		cboExchangeFraction.setComboSize(100);
		cboExchangeFraction.setSize(225, 20);
		cboExchangeFraction.setLocation(20, 20);
		cboExchangeFraction.setLangMessageID(TModelUIUtil.getShortWord("C00557"));
		cboExchangeFraction.getComboBox().addTextValueItem(ExchangeFraction.TRUNCATE, getWord("C00121"));// 切捨
		cboExchangeFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_OFF, getWord("C00215"));// 四捨五入
		pnlFraction.add(cboExchangeFraction);

		// 仮受消費税端数処理
		cboTemporaryGetTaxFraction.setLabelSize(120);
		cboTemporaryGetTaxFraction.setComboSize(100);
		cboTemporaryGetTaxFraction.setSize(225, 20);
		cboTemporaryGetTaxFraction.setLocation(20, 45);
		cboTemporaryGetTaxFraction.setLangMessageID(TModelUIUtil.getShortWord("C00082"));
		cboTemporaryGetTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.TRUNCATE, getWord("C00121"));// 切捨
		cboTemporaryGetTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_UP, getWord("C00120"));// 切上
		cboTemporaryGetTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_OFF, getWord("C00215"));// 四捨五入
		pnlFraction.add(cboTemporaryGetTaxFraction);

		// 仮払消費税端数処理
		cboTemporaryPaymentTaxFraction.setLabelSize(120);
		cboTemporaryPaymentTaxFraction.setComboSize(100);
		cboTemporaryPaymentTaxFraction.setSize(225, 20);
		cboTemporaryPaymentTaxFraction.setLocation(20, 70);
		cboTemporaryPaymentTaxFraction.setLangMessageID(TModelUIUtil.getShortWord("C00083"));
		cboTemporaryPaymentTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.TRUNCATE, getWord("C00121"));// 切捨
		cboTemporaryPaymentTaxFraction.getComboBox().addTextValueItem(ExchangeFraction.ROUND_OFF, getWord("C00215"));// 四捨五入
		pnlFraction.add(cboTemporaryPaymentTaxFraction);

		// 伝票印刷設定
		pnlSlipPrint.setLayout(null);
		pnlSlipPrint.setBorder(TBorderFactory.createTitledBorder(getWord("C11186")));
		pnlSlipPrint.setSize(270, 75);
		pnlSlipPrint.setLocation(10, 255);
		pnlSlip.add(pnlSlipPrint);

		// 伝票印刷区分
		chkSlipPrint.setSize(100, 20);
		chkSlipPrint.setLocation(20, 20);
		chkSlipPrint.setLangMessageID("C01248");
		pnlSlipPrint.add(chkSlipPrint);

		// 印刷時の初期値
		chkSlipPrintInitial.setSize(150, 20);
		chkSlipPrintInitial.setLocation(20, 45);
		chkSlipPrintInitial.setLangMessageID("C01000");
		pnlSlipPrint.add(chkSlipPrintInitial);

		// 換算設定
		pnlConvertType.setLayout(null);
		pnlConvertType.setBorder(TBorderFactory.createTitledBorder(getWord("C11187")));
		pnlConvertType.setSize(270, 55);
		pnlConvertType.setLocation(10, 335);
		pnlSlip.add(pnlConvertType);

		// 換算区分
		cboConvertType.setLabelSize(120);
		cboConvertType.setComboSize(100);
		cboConvertType.setSize(225, 20);
		cboConvertType.setLocation(20, 20);
		cboConvertType.setLangMessageID("C00897");
		cboConvertType.getComboBox().addTextValueItem(ConvertType.MULTIPLICATION, TModelUIUtil.getShortWord("C00065"));
		cboConvertType.getComboBox().addTextValueItem(ConvertType.DIVIDE, TModelUIUtil.getShortWord("C00563"));
		pnlConvertType.add(cboConvertType);

		// 決算設定
		pnlSettlementStage.setLayout(null);
		pnlSettlementStage.setBorder(TBorderFactory.createTitledBorder(getWord("C11188")));// 決算設定
		pnlSettlementStage.setSize(380, 80);
		pnlSettlementStage.setLocation(10, 10);
		pnlSettlement.add(pnlSettlementStage);

		// 決算段階区分
		cboSettlement.setLabelSize(140);
		cboSettlement.setComboSize(140);
		cboSettlement.setSize(295, 20);
		cboSettlement.setLocation(20, 20);
		cboSettlement.setLangMessageID("C00145");
		cboSettlement.getComboBox().addItem(getWord("C00037"));// 行う
		cboSettlement.getComboBox().addItem(getWord("C00038"));// 行わない
		pnlSettlementStage.add(cboSettlement);

		// 決算段階
		ctrlSettlementStage.setSize(20, 20);
		ctrlSettlementStage.setPositiveOnly(true);
		ctrlSettlementStage.setLocation(320, 20);
		ctrlSettlementStage.setMaxLength(1);
		pnlSettlementStage.add(ctrlSettlementStage);

		lblSettlementStage.setLangMessageID("C00373");// 次
		lblSettlementStage.setSize(40, 20);
		lblSettlementStage.setLocation(345, 20);
		pnlSettlementStage.add(lblSettlementStage);

		// 決算伝票入力区分
		cboSettlementPer.setLabelSize(140);
		cboSettlementPer.setComboSize(140);
		cboSettlementPer.setSize(295, 20);
		cboSettlementPer.setLocation(20, 45);
		cboSettlementPer.setLangMessageID("C00139");
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.YEAR, getWord("C00009"));// 一年
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.HALF, getWord("C00435"));// 半期
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.QUARTER, getWord("C10592"));// 四半期
		cboSettlementPer.getComboBox().addTextValueItem(SettlementTerm.MONTH, getWord("C00147"));// 月次
		pnlSettlementStage.add(cboSettlementPer);

		// 評価替レート区分
		pnlForeignCurrencyExchangeRate.setLayout(null);
		pnlForeignCurrencyExchangeRate.setBorder(TBorderFactory.createTitledBorder(getWord("C00454")));
		pnlForeignCurrencyExchangeRate.setSize(380, 55);
		pnlForeignCurrencyExchangeRate.setLocation(10, 95);
		pnlSettlement.add(pnlForeignCurrencyExchangeRate);

		// 決算伝票入力区分
		cboForeignCurrencyExchangeRate.setLabelSize(140);
		cboForeignCurrencyExchangeRate.setComboSize(140);
		cboForeignCurrencyExchangeRate.setSize(295, 20);
		cboForeignCurrencyExchangeRate.setLocation(20, 20);
		cboForeignCurrencyExchangeRate.setLangMessageID("C00454");
		cboForeignCurrencyExchangeRate.getComboBox().addTextValueItem(EvaluationRateDate.LAST_DATE,
			TModelUIUtil.getShortWord("C00148"));// 月末レート
		cboForeignCurrencyExchangeRate.getComboBox().addTextValueItem(EvaluationRateDate.FIRST_DATE,
			TModelUIUtil.getShortWord("C00535"));// 翌月月初レート
		pnlForeignCurrencyExchangeRate.add(cboForeignCurrencyExchangeRate);

		// 中間決算繰越区分
		pnlCarryJournalOfMidtermClosingForward.setLayout(null);
		pnlCarryJournalOfMidtermClosingForward.setBorder(TBorderFactory.createTitledBorder(getWord("C11141")));// 中間決算繰越区分
		pnlCarryJournalOfMidtermClosingForward.setSize(380, 55);
		pnlCarryJournalOfMidtermClosingForward.setLocation(10, 155);
		pnlSettlement.add(pnlCarryJournalOfMidtermClosingForward);

		// 中間決算繰越区分
		cboCarryJournalOfMidtermClosingForward.setLabelSize(160);
		cboCarryJournalOfMidtermClosingForward.setComboSize(140);
		cboCarryJournalOfMidtermClosingForward.setSize(305, 20);
		cboCarryJournalOfMidtermClosingForward.setLocation(5, 20);
		cboCarryJournalOfMidtermClosingForward.setLangMessageID(TModelUIUtil.getShortWord("C11141"));
		cboCarryJournalOfMidtermClosingForward.getComboBox().addTextValueItem(0, TModelUIUtil.getShortWord("C11148"));// 繰り越す
		cboCarryJournalOfMidtermClosingForward.getComboBox().addTextValueItem(1, TModelUIUtil.getShortWord("C11218"));// 繰り越さない
		pnlCarryJournalOfMidtermClosingForward.add(cboCarryJournalOfMidtermClosingForward);

		// 会計期間
		pnlFiscalPeriod.setLayout(null);
		pnlFiscalPeriod.setBorder(TBorderFactory.createTitledBorder(getWord("C00051")));// 会計期間
		pnlFiscalPeriod.setSize(250, 55);
		pnlFiscalPeriod.setLocation(10, 10);
		pnlElse.add(pnlFiscalPeriod);

		// 期首月
		ctrlInitialMonth.setLabelSize(70);
		ctrlInitialMonth.setFieldSize(20);
		ctrlInitialMonth.setSize(95, 20);
		ctrlInitialMonth.setLocation(0, 20);
		ctrlInitialMonth.setPositiveOnly(true);
		ctrlInitialMonth.setLabelText(TModelUIUtil.getShortWord("C00105"));
		ctrlInitialMonth.setMaxLength(2, 0);
		pnlFiscalPeriod.add(ctrlInitialMonth);

		// 承認
		pnlApprove.setLayout(null);
		pnlApprove.setBorder(TBorderFactory.createTitledBorder(getWord("C01168")));
		pnlApprove.setSize(450, 75);
		pnlApprove.setLocation(10, 70);
		pnlElse.add(pnlApprove);

		// 承認タイプ
		cboWorkFlowApprove.setLabelSize(70);
		cboWorkFlowApprove.setComboSize(140);
		cboWorkFlowApprove.setLocation(0, 20);
		cboWorkFlowApprove.setLangMessageID("承認方法"); // TODO
		cboWorkFlowApprove.addItem(ApproveType.REGULAR);
		cboWorkFlowApprove.addItem(ApproveType.WORKFLOW);
		pnlApprove.add(cboWorkFlowApprove);

		cboDenyAction.setLabelSize(70);
		cboDenyAction.setComboSize(80);
		cboDenyAction.setLocation(cboWorkFlowApprove.getX() + cboWorkFlowApprove.getWidth(), 20);
		cboDenyAction.setLangMessageID("否認制御"); // TODO
		cboDenyAction.addItem(DenyAction.BACK_ONE);
		cboDenyAction.addItem(DenyAction.BACK_FIRST);
		pnlApprove.add(cboDenyAction);

		// 現場承認
		chkFieldApprove.setSize(100, 20);
		chkFieldApprove.setLocation(20, 40);
		chkFieldApprove.setLangMessageID("C00157");
		pnlApprove.add(chkFieldApprove);

		// 経理承認
		chkAccountantApprove.setSize(100, 20);
		chkAccountantApprove.setLocation(120, 40);
		chkAccountantApprove.setLangMessageID("C01616");
		pnlApprove.add(chkAccountantApprove);

		// 通貨
		pnlCurrency.setLayout(null);
		pnlCurrency.setBorder(TBorderFactory.createTitledBorder(getWord("C00371")));
		pnlCurrency.setSize(320, 80);
		pnlCurrency.setLocation(10, 150);
		pnlElse.add(pnlCurrency);

		// 基軸通貨
		ctrlKeyCurrency.setLocation(20, 20);
		ctrlKeyCurrency.btn.setLangMessageID("C00907");
		pnlCurrency.add(ctrlKeyCurrency);

		// 機能通貨
		ctrlFunctionCurrency.setLocation(20, 45);
		ctrlFunctionCurrency.btn.setLangMessageID("C11084");
		pnlCurrency.add(ctrlFunctionCurrency);

		// グループ会計区分(パネル)
		pnlGrp.setLayout(null);
		pnlGrp.setBorder(TBorderFactory.createTitledBorder(getWord("C11142")));
		pnlGrp.setSize(250, 55);
		pnlGrp.setLocation(10, 235);
		pnlElse.add(pnlGrp);

		// グループ会計区分(チェックボックス)
		chkGrpKbn.setSize(100, 20);
		chkGrpKbn.setLocation(20, 20);
		chkGrpKbn.setLangMessageID("C00281");
		pnlGrp.add(chkGrpKbn);

		// IFRS区分(パネル)
		pnlIfrs.setLayout(null);
		pnlIfrs.setBorder(TBorderFactory.createTitledBorder(getWord("C11143")));
		pnlIfrs.setSize(250, 55);
		pnlIfrs.setLocation(10, 295);
		pnlElse.add(pnlIfrs);

		// IFRS区分(チェックボックス)
		chkIfrsKbn.setSize(100, 20);
		chkIfrsKbn.setLocation(20, 20);
		chkIfrsKbn.setLangMessageID("C00281");
		pnlIfrs.add(chkIfrsKbn);

		// 発生日チェック区分(パネル)
		pnlHasDateKbn.setLayout(null);
		pnlHasDateKbn.setBorder(TBorderFactory.createTitledBorder(getWord("C12297")));
		pnlHasDateKbn.setSize(250, 55);
		pnlHasDateKbn.setLocation(10, 355);
		pnlElse.add(pnlHasDateKbn);

		// 発生日チェック区分(チェックボックス)
		chkHasDateKbn.setSize(100, 20);
		chkHasDateKbn.setLocation(20, 20);
		chkHasDateKbn.setLangMessageID("C00281");
		pnlHasDateKbn.add(chkHasDateKbn);

		int y = 415;

		// 英文請求書SIGNER欄（債権管理）(パネル)
		if (MG0010CompanyMasterPanelCtrl.isShowARSignerEng) {
			pnlEnSigner.setLayout(null);
			pnlEnSigner.setBorder(TBorderFactory.createTitledBorder(getWord("C12175")));
			pnlEnSigner.setSize(460, 55);
			pnlEnSigner.setLocation(10, y);
			pnlElse.add(pnlEnSigner);

			// 英文請求書SIGNER欄（債権管理）
			ctrlEnSignerText.setSize(380, 20);
			ctrlEnSignerText.setLocation(20, 20);
			ctrlEnSignerText.setMaxLength(60);
			pnlEnSigner.add(ctrlEnSignerText);
			y += 60;
		}

		// invoice制度
		if (MG0010CompanyMasterPanelCtrl.isInvoice) {

			pnlInvoiceSystem.setLayout(null);
			pnlInvoiceSystem.setBorder(TBorderFactory.createTitledBorder(getWord("C12220")));
			pnlInvoiceSystem.setSize(250, 55);
			pnlInvoiceSystem.setLocation(10, y);
			pnlElse.add(pnlInvoiceSystem);

			// invoice制度 使用する
			ctrlInvoiceChk.setSize(100, 20);
			ctrlInvoiceChk.setLocation(20, 20);
			ctrlInvoiceChk.setLangMessageID("CLM0290");
			pnlInvoiceSystem.add(ctrlInvoiceChk);

		}

		// 色設定パネル
		pnlColor.setLayout(null);
		pnlColor.setBorder(TBorderFactory.createTitledBorder(getWord("C11144")));
		pnlColor.setSize(220, 55);
		pnlColor.setLocation(10, 10);
		pnlEnv.add(pnlColor);

		// 会社色
		ctrlCompanyColor.setCaption(TModelUIUtil.getShortWord("C11219"));
		ctrlCompanyColor.setLocation(10, 20);
		pnlColor.add(ctrlCompanyColor);

		// SPC会社情報設定タブ
		if (!MG0010CompanyMasterPanelCtrl.isNotShowSpc) {
			// デフォルトもしくは表示する時のみ表示

			pnlSpc.setLayout(null);
			tab.addTab(getWord("C11786"), pnlSpc);// SPC会社情報
		}

		// SPC会社情報設定パネル
		pnlSpcInfomation.setLayout(null);
		pnlSpcInfomation.setBorder(TBorderFactory.createTitledBorder(getWord("C11786")));
		pnlSpcInfomation.setSize(470, 350);
		pnlSpcInfomation.setLocation(20, 10);
		pnlSpc.add(pnlSpcInfomation);

		// 連結用会社コード
		ctrlConnCompanyCode.setLabelSize(110);
		ctrlConnCompanyCode.setFieldSize(100);
		ctrlConnCompanyCode.setSize(220, 20);
		ctrlConnCompanyCode.setLocation(20, 20);
		ctrlConnCompanyCode.setLabelText(getWord("C11762"));
		ctrlConnCompanyCode.setMaxLength(TransUtil.COMPANY_CODE_LENGTH);
		ctrlConnCompanyCode.setImeMode(false);
		pnlSpcInfomation.add(ctrlConnCompanyCode);

		// サイン者・役職名
		ctrlSignerPosition.setLabelSize(110);
		ctrlSignerPosition.setFieldSize(200);
		ctrlSignerPosition.setSize(320, 20);
		ctrlSignerPosition.setLocation(20, 45);
		ctrlSignerPosition.setLabelText(getWord("C11763"));
		ctrlSignerPosition.setMaxLength(TransUtil.COMPANY_NAME_LENGTH);
		ctrlSignerPosition.setImeMode(true);
		pnlSpcInfomation.add(ctrlSignerPosition);

		// 外国送金担当者
		ctrlRemitterName.setLabelSize(110);
		ctrlRemitterName.setFieldSize(200);
		ctrlRemitterName.setSize(320, 20);
		ctrlRemitterName.setLocation(20, 70);
		ctrlRemitterName.setLabelText(getWord("C11764"));
		ctrlRemitterName.setMaxLength(TransUtil.COMPANY_NAME_LENGTH);
		ctrlRemitterName.setImeMode(true);
		pnlSpcInfomation.add(ctrlRemitterName);

		// 連絡用電話番号
		ctrlConnPhoneNo.setLabelSize(110);
		ctrlConnPhoneNo.setFieldSize(200);
		ctrlConnPhoneNo.setSize(320, 20);
		ctrlConnPhoneNo.setLocation(20, 95);
		ctrlConnPhoneNo.setLabelText(getWord("C11765"));
		ctrlConnPhoneNo.setMaxLength(20);
		ctrlConnPhoneNo.setImeMode(true);
		pnlSpcInfomation.add(ctrlConnPhoneNo);

		// DebitNote住所1
		ctrlDebitNoteAddress1.setLabelSize(120);
		ctrlDebitNoteAddress1.setFieldSize(300);
		ctrlDebitNoteAddress1.setLocation(12, 125);
		ctrlDebitNoteAddress1.setLabelText(getWord("C11787"));
		ctrlDebitNoteAddress1.setMaxLength(80);
		pnlSpcInfomation.add(ctrlDebitNoteAddress1);

		// DebitNote住所2
		ctrlDebitNoteAddress2.setLabelSize(120);
		ctrlDebitNoteAddress2.setFieldSize(300);
		ctrlDebitNoteAddress2.setLocation(ctrlDebitNoteAddress1.getX(), ctrlDebitNoteAddress1.getY()
			+ ctrlDebitNoteAddress1.getHeight());
		ctrlDebitNoteAddress2.setLabelText(getWord(""));
		ctrlDebitNoteAddress2.setMaxLength(80);
		pnlSpcInfomation.add(ctrlDebitNoteAddress2);

		// DebitNote住所3
		ctrlDebitNoteAddress3.setLabelSize(120);
		ctrlDebitNoteAddress3.setFieldSize(300);
		ctrlDebitNoteAddress3.setLocation(ctrlDebitNoteAddress1.getX(), ctrlDebitNoteAddress2.getY()
			+ ctrlDebitNoteAddress2.getHeight());
		ctrlDebitNoteAddress3.setLabelText(getWord(""));
		ctrlDebitNoteAddress3.setMaxLength(80);
		pnlSpcInfomation.add(ctrlDebitNoteAddress3);

		// DebitNoteフッタ情報ラベル
		ctrlDebitNoteInfoLabel.setSize(120, 20);
		ctrlDebitNoteInfoLabel.setText(getWord("C11770"));
		ctrlDebitNoteInfoLabel.setLocation(15, 200);
		pnlSpcInfomation.add(ctrlDebitNoteInfoLabel);

		// DebitNoteフッタ情報
		ctrlDebitNoteInfo.setMaxLength(512);
		ctrlDebitNoteInfo.setAltEnterToChangingLine(true);
		ctrlDebitNoteInfo.setFont(ctrlDebitNoteInfo.getFont().deriveFont(ctrlDebitNoteInfoLabel.getFont().getSize2D()));
		ctrlDebitNoteInfo.setImeMode(true);

		JScrollPane sp = new JScrollPane(ctrlDebitNoteInfo);
		sp.setBorder(MintBorderFactory.getInstance().getTextFieldBorder());
		TGuiUtil.setComponentSize(sp, new Dimension(300, 120));
		// ステップ
		sp.getVerticalScrollBar().setUnitIncrement(40);
		sp.getHorizontalScrollBar().setUnitIncrement(40);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setLocation(137, 200);
		pnlSpcInfomation.add(sp);

	}

	/**
	 * 非会計明細コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
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
	 * 伝票番号自動設定項目コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
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
