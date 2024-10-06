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
 * 取引先支払条件マスタ(海外用)の画面レイアウト
 * 
 * @author AIS
 */
public class MG0156CustomerPaymentSettingMasterPanel extends TMainPanel {

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {

		/** Entity */
		entity,

		/** 取引先コード */
		customerCode,

		/** 取引先略称 */
		customerNames,

		/** 取引先条件コード */
		customerConditionCode,

		/** 支払条件締日 */
		paymentConditionDay,

		/** 支払条件締後月 */
		paymentConditionMonth,

		/** 支払条件支払日 */
		paymentConditionPayday,

		/** 支払区分 */
		paymentType,

		/** 支払方法 */
		paymentMethod,

		/** 振込振出銀行口座コード */
		bankAccountCode,

		/** 銀行コード */
		bankCode,

		/** 銀行名称 */
		bankName,

		/** 支店コード */
		branchCode,

		/** 支店名称 */
		branchName,

		/** 預金種目 */
		depositType,

		/** バンクチャージ区分 */
		bankChargeType,

		/** 振込手数料区分 */
		cashInFee,

		/** 手数料区分 */
		commissionPaymentType,

		/** 口座番号 */
		accountNumber,

		/** 銀行SWIFTコード */
		bankSwiftCode,

		/** 英文銀行名 */
		englishBankName,

		/** 英文支店名 */
		englishBranchName,

		/** 銀行国コード */
		bankCountryCode,

		/** 英文銀行住所 */
		englishBankAddress,

		/** 口座名義カナ */
		accountKana,

		/** 送金目的名 */
		remittancePurposeName,

		/** 経由銀行SWIFTコード */
		viaBankSwiftCode,

		/** 経由銀行名称 */
		viaBankName,

		/** 経由支店名称 */
		viaBranchName,

		/** 経由銀行国コード */
		viaBankCountryCode,

		/** 経由銀行住所 */
		viaBankAddress,

		/** 受取人国コード */
		recipientCountryCode,

		/** 受取人住所 */
		recipientAddress,

		/** 開始年月日 */
		from,

		/** 終了年月日 */
		to
	}

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

	/** 取引先コード範囲検索パネル */
	public TPanel pnlCustomerReferenceRange;

	/** 取引先コード範囲検索 */
	public TCustomerReferenceRange ctrlCustomerReferenceRange;

	/** 有効期間切れを表示するか */
	public TCheckBox chkOutputTermEnd;

	/** 一覧 */
	public TPanel pnlBodyBottom;

	/** 取引先支払条件マスタ一覧 */
	public TTable tbl;

	/**
	 * コンストラクタ
	 */
	public MG0156CustomerPaymentSettingMasterPanel() {
		super();
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlCustomerReferenceRange = new TPanel();
		ctrlCustomerReferenceRange = new TCustomerReferenceRange();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.entity, "", -1, false);
		tbl.addColumn(SC.customerCode, "C00786", 100);
		tbl.addColumn(SC.customerNames, "C00787", 150);
		tbl.addColumn(SC.customerConditionCode, "C00788", 110);
		tbl.addColumn(SC.paymentConditionDay, "C02057", 100, SwingConstants.RIGHT);
		tbl.addColumn(SC.paymentConditionMonth, "C02058", 100, SwingConstants.RIGHT);
		tbl.addColumn(SC.paymentConditionPayday, "C02059", 100, SwingConstants.RIGHT);
		tbl.addColumn(SC.paymentType, "C00682", 70, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentMethod, "C00233", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.bankAccountCode, "C00792", 120);
		tbl.addColumn(SC.bankCode, "C00779", 100);
		tbl.addColumn(SC.bankName, "C00781", 150);
		tbl.addColumn(SC.branchCode, "C02055", 100);
		tbl.addColumn(SC.branchName, "C02060", 150);
		tbl.addColumn(SC.depositType, "C01326", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.bankChargeType, "C11423", 100, SwingConstants.CENTER); // バンクチャージ区分
		tbl.addColumn(SC.cashInFee, getWord("C02056"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.commissionPaymentType, getWord("C10224"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.accountNumber, "C00794", 120);
		tbl.addColumn(SC.bankSwiftCode, "C11418", 120); // 銀行SWIFTコード
		tbl.addColumn(SC.englishBankName, "C00795", 150);
		tbl.addColumn(SC.englishBranchName, "C00796", 150);
		tbl.addColumn(SC.bankCountryCode, "C11417", 100); // 銀行国コード
		tbl.addColumn(SC.englishBankAddress, "C00797", 150);
		tbl.addColumn(SC.accountKana, "C01068", 150); // 口座名義カナ
		tbl.addColumn(SC.remittancePurposeName, "C02037", 100); // 送金目的名
		tbl.addColumn(SC.viaBankSwiftCode, "C11422", 120); // 経由銀行SWIFTコード
		tbl.addColumn(SC.viaBankName, "C00802", 150);
		tbl.addColumn(SC.viaBranchName, "C00803", 150);
		tbl.addColumn(SC.viaBankCountryCode, "C11421", 100); // 経由銀行国コード
		tbl.addColumn(SC.viaBankAddress, "C00804", 150);
		tbl.addColumn(SC.recipientCountryCode, "C11416", 100); // 受取人国コード
		tbl.addColumn(SC.recipientAddress, "C00805", 150);
		tbl.addColumn(SC.from, "C00055", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.to, "C00261", 100, SwingConstants.CENTER);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
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

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 取引先コード範囲パネル
		pnlCustomerReferenceRange.setLayout(null);
		pnlCustomerReferenceRange.setLangMessageID("C01060"); // 検索条件
		pnlCustomerReferenceRange.setLocation(30, 10);
		pnlCustomerReferenceRange.setSize(600, 75);
		pnlBodyTop.add(pnlCustomerReferenceRange);

		// 取引先範囲
		ctrlCustomerReferenceRange.setLocation(20, 20);
		pnlCustomerReferenceRange.add(ctrlCustomerReferenceRange);

		// 有効期限切れ表示
		chkOutputTermEnd.setLangMessageID("C11089"); // 有効期間切れ表示
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlCustomerReferenceRange.add(chkOutputTermEnd);

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

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlCustomerReferenceRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
