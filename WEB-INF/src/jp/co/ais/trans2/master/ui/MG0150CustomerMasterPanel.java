package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 取引先マスタ 指示画面
 */
public class MG0150CustomerMasterPanel extends TMainPanel {

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 編集ボタン */
	public TImageButton btnModify;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 取引先範囲検索 */
	public TCustomerReferenceRange TCustomerReferenceRange;

	/** 有効期間切れを表示するか */
	public TCheckBox chkOutputTermEnd;

	/** 一覧 */
	public TTable tbl;

	/** 担当者設定ボタン */
	public TImageButton btnUsr;

	/** 担当者一覧出力ボタン */
	public TImageButton btnUsrOut;

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** 取引先コード */
		triCode,
		/** 取引先名称 */
		triName,
		/** 取引先略称 */
		triNames,
		/** 取引先検索名称 */
		triNamek,
		/** 取引先区分 */
		triKbn,
		/** グループ会社区分 */
		groupCompanyKbn,
		/** 事業所名称 */
		officeName,
		/** 郵便番号 */
		postNum,
		/** 国コード */
		countryCode,
		/** 国名 */
		countryName,
		/** 住所カナ */
		addressKana,
		/** 住所１ */
		address1,
		/** 住所２ */
		address2,
		/** EMail Address */
		emailaddress,
		/** 取引先敬称 */
		cusTitle,
		/** 担当者所属部署 */
		departmentInCharge,
		/** 担当者 */
		chargeName,
		/** 担当者敬称 */
		chargeTitle,
		/** 電話番号 */
		telNum,
		/** FAX番号 */
		faxNum,
		/** 集計取引先コード */
		sumAiteCode,
		/** 集計取引先名称 */
		sumAiteName,
		/** 仕入区分 */
		siireKbn,
		/** 得意先区分 */
		tokuiKbn,
		/** 入金条件締め日 */
		nyukinConditionSimeDate,
		/** 入金条件締め後月 */
		nyukinConditionSimeMonth,
		/** 入金条件入金日 */
		nyukinConditionNyukinDate,
		/** 入金銀行口座コード */
		nyukinBankKouzaCode,
		/** 入金銀行口座名称 */
		nyukinBankKouzaName,
		/** 振込依頼人名 */
		hurikomiIraiName,
		/** 入金手数料区分 */
		nyukinTesuryoKbn,
		/** 非適格請求書発行事業者 */
		noInvReg,
		/** 消費税名称 */
		taxName,
		/** 適格請求書発行事業者番号 */
		invRegNo,
		/** 開始年月日 */
		dateFrom,
		/** 終了年月日 */
		dateTo
	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnUsr = new TImageButton();
		btnUsrOut = new TImageButton();
		pnlSearchCondition = new TPanel();
		TCustomerReferenceRange = new TCustomerReferenceRange();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.triCode, getWord("C00786"), 100); // 取引先コード
		tbl.addColumn(SC.triName, getWord("C00830"), 200); // 取引先名称
		tbl.addColumn(SC.triNames, getWord("C00787"), 200); // 取引先略称
		tbl.addColumn(SC.triNamek, getWord("C00836"), 200); // 取引先検索名称
		int divWidth = !MG0150CustomerMasterPanelCtrl.isNoVisibleTriDivison ? 200 : -1;
		tbl.addColumn(SC.triKbn, getWord("C03344"), divWidth); // 取引先区分
		int groupWidth = MG0150CustomerMasterPanelCtrl.isUseTRI_TYPE_GRP_FLG ? 100 : -1;
		tbl.addColumn(SC.groupCompanyKbn, getWord("C04294"), groupWidth, SwingConstants.CENTER); // グループ会社区分
		tbl.addColumn(SC.officeName, getWord("C02487"), 200); // 事業所名
		tbl.addColumn(SC.postNum, getWord("C00527"), 100); // 郵便番号
		tbl.addColumn(SC.countryCode, getWord("C11889"), 50);// 国コード
		tbl.addColumn(SC.countryName, getWord("C11890"), 80);// 国名
		tbl.addColumn(SC.addressKana, getWord("C01152"), 200); // 住所カナ
		tbl.addColumn(SC.address1, getWord("C01150"), 200); // 住所1
		tbl.addColumn(SC.address2, getWord("C01151"), 200); // 住所2
		tbl.addColumn(SC.emailaddress, getWord("CBL401"), 150); // EMail Address

		int titleWidth = MG0150CustomerMasterPanelCtrl.isUseCustomerManagementSet ? 100 : -1;
		tbl.addColumn(SC.cusTitle, getWord("C12184"), titleWidth, SwingConstants.CENTER); // 取引先敬称
		tbl.addColumn(SC.chargeTitle, getWord("C12187"), titleWidth, SwingConstants.CENTER); // 担当者敬称
		int nameWidth = MG0150CustomerMasterPanelCtrl.isUseCustomerManagementSet ? 200 : -1;

		tbl.addColumn(SC.departmentInCharge, getWord("C12185"), nameWidth); // 担当者所属部署
		tbl.addColumn(SC.chargeName, getWord("C12186"), nameWidth); // 担当者名称
		tbl.addColumn(SC.telNum, getWord("C00393"), 100); // 電話番号
		tbl.addColumn(SC.faxNum, getWord("C00690"), 100); // FAX番号
		tbl.addColumn(SC.sumAiteCode, getWord("C00871"), 100); // 集計相手先コード
		tbl.addColumn(SC.sumAiteName, getWord("C11085"), 100); // 集計相手先名称
		tbl.addColumn(SC.siireKbn, getWord("C01089"), 100, SwingConstants.CENTER); // 仕入先区分
		tbl.addColumn(SC.tokuiKbn, getWord("C01261"), 100, SwingConstants.CENTER); // 得意先区分
		tbl.addColumn(SC.nyukinConditionSimeDate, getWord("C02038"), 100); // 入金条件締め日
		tbl.addColumn(SC.nyukinConditionSimeMonth, getWord("C02039"), 100); // 入金条件締め後月
		tbl.addColumn(SC.nyukinConditionNyukinDate, getWord("C00870"), 100); // 入金条件入金日
		tbl.addColumn(SC.nyukinBankKouzaCode, getWord("C02040"), 200); // 入金銀行口座コード
		tbl.addColumn(SC.nyukinBankKouzaName, getWord("C11087"), 200); // 入金銀行口座名称
		tbl.addColumn(SC.hurikomiIraiName, getWord("C10133"), 200); // 振込依頼人名
		tbl.addColumn(SC.nyukinTesuryoKbn, getWord("C02042"), 100, SwingConstants.CENTER); // 入金手数料区分

		int invoiceWidth = MG0150CustomerMasterPanelCtrl.isInvoice ? 150 : -1;
		int invoiceNoWidth = MG0150CustomerMasterPanelCtrl.isInvoice ? 180 : -1;
		tbl.addColumn(SC.noInvReg, getWord("C12197"), invoiceWidth, SwingConstants.CENTER); // 非適格請求書発行事業者
		tbl.addColumn(SC.taxName, getWord("C00286"), invoiceWidth); // 消費税
		tbl.addColumn(SC.invRegNo, getWord("C12171"), invoiceNoWidth); // 適格請求書発行事業者登録番号

		tbl.addColumn(SC.dateFrom, getWord("C00055"), 100, SwingConstants.CENTER); // 開始年月日
		tbl.addColumn(SC.dateTo, getWord("C00261"), 100, SwingConstants.CENTER); // 終了年月日

	}

	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 担当者設定ボタン
		x = x + btnExcel.getWidth() + HEADER_MARGIN_X;
		btnUsr.setLangMessageID(getWord("C00363") + getWord("C00319"));
		btnUsr.setShortcutKey(KeyEvent.VK_F7);
		btnUsr.setSize(25, 130);
		btnUsr.setLocation(x, HEADER_Y);
		btnUsr.setVisible(MG0150CustomerMasterPanelCtrl.isVisibleTriUsrSetting);
		pnlHeader.add(btnUsr);

		// 担当者一覧出力ボタン
		x = x + btnUsr.getWidth() + HEADER_MARGIN_X;
		btnUsrOut.setLangMessageID(getWord("C00363") + getWord("C00010") + getWord("C00266"));
		btnUsrOut.setShortcutKey(KeyEvent.VK_F8);
		btnUsrOut.setSize(25, 130);
		btnUsrOut.setLocation(x, HEADER_Y);
		btnUsrOut.setVisible(MG0150CustomerMasterPanelCtrl.isVisibleTriUsrSetting);
		pnlHeader.add(btnUsrOut);

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 取引先検索範囲
		TCustomerReferenceRange.setLocation(20, 20);
		pnlSearchCondition.add(TCustomerReferenceRange);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(chkOutputTermEnd);

		// 下部
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		TCustomerReferenceRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);

		tbl.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnUsr.setTabControlNo(i++);
		btnUsrOut.setTabControlNo(i++);
	}
}
