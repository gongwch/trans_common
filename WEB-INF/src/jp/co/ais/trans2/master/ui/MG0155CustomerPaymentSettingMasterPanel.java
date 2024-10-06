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
 * 取引先支払条件マスタの画面レイアウト
 * 
 * @author AIS
 */
public class MG0155CustomerPaymentSettingMasterPanel extends TMainPanel {

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
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** 取引先コード */
		customerCode,
		/** 取引先略称 */
		customerNames,
		/** 取引先条件コード */
		customerConditionCord,
		/** 振込手数料区分 */
		wireTransferCommissionType,
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
		/** 口座番号 */
		accountNumber,
		/** 口座名義カナ */
		accountKana,
		/** 送金目的名 */
		remittancePurposeName,
		/** 英文銀行名 */
		englishBankName,
		/** 英文支店名 */
		englishBranchName,
		/** 英文銀行住所 */
		englishBankAddress,
		/** 手数料区分 */
		commissionType,
		/** 支払銀行名称 */
		paymentBankName,
		/** 支払支店名称 */
		paymentBranchName,
		/** 支払銀行住所 */
		paymentBankAddress,
		/** 経由銀行名称 */
		viaBankName,
		/** 経由支店名称 */
		viaBranchName,
		/** 経由銀行住所 */
		viaBankAddress,
		/** 受取人住所 */
		recipientAddress,
		/** 開始年月日 */
		from,
		/** 終了年月日 */
		to,
		/** Entity */
		entity
	}

	/**
	 * コンストラクタ
	 */
	public MG0155CustomerPaymentSettingMasterPanel() {
		super();
	}

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
		tbl.addColumn(SC.customerCode, getWord("C00786"), 100);
		tbl.addColumn(SC.customerNames, getWord("C00787"), 150);
		tbl.addColumn(SC.customerConditionCord, getWord("C00788"), 110);
		tbl.addColumn(SC.wireTransferCommissionType, getWord("C01340") + getWord("C02056"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentConditionDay, getWord("C02057"), 100);
		tbl.addColumn(SC.paymentConditionMonth, getWord("C02058"), 100);
		tbl.addColumn(SC.paymentConditionPayday, getWord("C02059"), 100);
		tbl.addColumn(SC.paymentType, getWord("C00682"), 70, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentMethod, getWord("C00233"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.bankAccountCode, getWord("C00792"), 120);
		tbl.addColumn(SC.bankCode, getWord("C00779"), 100);
		tbl.addColumn(SC.bankName, getWord("C00781"), 150);
		tbl.addColumn(SC.branchCode, getWord("C02055"), 100);
		tbl.addColumn(SC.branchName, getWord("C02060"), 150);
		tbl.addColumn(SC.depositType, getWord("C01326"), 70, SwingConstants.CENTER);
		tbl.addColumn(SC.accountNumber, getWord("C00794"), 120);
		tbl.addColumn(SC.accountKana, getWord("C01068"), 150);
		tbl.addColumn(SC.remittancePurposeName, getWord("C02037"), 100);
		tbl.addColumn(SC.englishBankName, getWord("C00795"), 150);
		tbl.addColumn(SC.englishBranchName, getWord("C00796"), 150);
		tbl.addColumn(SC.englishBankAddress, getWord("C00797"), 150);
		tbl.addColumn(SC.commissionType, getWord("C01334") + getWord("C10224"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentBankName, getWord("C00799"), 150);
		tbl.addColumn(SC.paymentBranchName, getWord("C00800"), 150);
		tbl.addColumn(SC.paymentBankAddress, getWord("C00801"), 150);
		tbl.addColumn(SC.viaBankName, getWord("C00802"), 150);
		tbl.addColumn(SC.viaBranchName, getWord("C00803"), 150);
		tbl.addColumn(SC.viaBankAddress, getWord("C00804"), 150);
		tbl.addColumn(SC.recipientAddress, getWord("C00805"), 150);
		tbl.addColumn(SC.from, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.to, getWord("C00261"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.entity, "", -1, false);
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

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlCustomerReferenceRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
