package jp.co.ais.trans2.master.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 銀行口座マスタ<br>
 * IBAN等情報追加版
 * 
 * @author AIS
 */
public class MP0031BankAccountMasterPanel extends MP0030BankAccountMasterPanel {

	/** 一覧のカラム定義 */
	public enum SC {
		/** 銀行口座コード */
		code,
		/** 銀行口座名称 */
		name,
		/** 通貨コード */
		curCode,
		/** 銀行コード */
		bankCode,
		/** 銀行名称 */
		bankName,
		/** 銀行名称（英字） */
		bankName_e,
		/** 支店コード */
		stnCode,
		/** 支店名称 */
		stnName,
		/** 支店名称（英字） */
		stnName_e,
		/** 振込依頼人コード */
		iraiCode,
		/** 振込依頼人名（ｶﾅ） */
		iraiName,
		/** 振込依頼人名（漢字） */
		iraiName_j,
		/** 振込依頼人名（英字） */
		iraiName_e,
		/** 預金種目 */
		yokinKbn,
		/** 口座番号 */
		kouzaNo,
		/** 社員FB区分 */
		empFB,
		/** 社外FB区分 */
		syagaiFB,
		/** 計上部門コード */
		depCode,
		/** 計上部門略称 */
		depNames,
		/** 科目コード */
		itemCode,
		/** 科目略称 */
		itemNames,
		/** 補助科目コード */
		subItemCode,
		/** 補助科目略称 */
		subItemNames,
		/** 内訳科目コード */
		detailItemCode,
		/** 内訳科目略称 */
		detailItemNames,
		/** 銀行識別子 */
		bankIndentify,
		/** 口座識別子 */
		bankAccountIndentify,
		/** IBANコード */
		iBan,
		/** SWIFTコード */
		swift,
		/** BANK COUNTRY */
		bankCountry,
		/** 住所1 */
		bnkAdr1,
		/** 住所1（英字） */
		bnkAdr1_e,
		/** 住所2 */
		bnkAdr2,
		/** 住所2（英字） */
		bnkAdr2_e,
		/** 開始日 */
		dateFrom,
		/** 終了日 */
		dateTo

	}

	/**
	 * コンストラクタ.
	 */
	public MP0031BankAccountMasterPanel() {
		super();
	}

	/**
	 * 画面コンポーネントの構築
	 */
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		ctrlRange = new TBankAccountReferenceRange(true);
		chkOutputTermEnd = new TCheckBox();

		pnlSearchCondition = new TPanel();
		tbl = new TTable();
		tbl.addColumn(SC.code, getWord("C01879"), 100);
		tbl.addColumn(SC.name, getWord("C02145"), 200);
		tbl.addColumn(SC.curCode, getWord("C00665"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.bankCode, getWord("C00779"), 100);
		tbl.addColumn(SC.bankName, getWord("C00781"), 200);
		tbl.addColumn(SC.bankName_e, getWord("C11891"), 200); // 銀行名称(英字)
		tbl.addColumn(SC.stnCode, getWord("C02055"), 100);
		tbl.addColumn(SC.stnName, getWord("C02060"), 200);
		tbl.addColumn(SC.stnName_e, getWord("C11892"), 200); // 支店名称(英字)
		tbl.addColumn(SC.iraiCode, getWord("C00858"), 110);
		tbl.addColumn(SC.iraiName, getWord("C00859"), 200);
		tbl.addColumn(SC.iraiName_j, getWord("C00860"), 200);
		tbl.addColumn(SC.iraiName_e, getWord("C00861"), 200);
		tbl.addColumn(SC.yokinKbn, getWord("C01326"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.kouzaNo, getWord("C00794"), 100);
		tbl.addColumn(SC.empFB, getWord("C01117"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.syagaiFB, getWord("C01122"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.depCode, getWord("C00571"), 100);
		tbl.addColumn(SC.depNames, getWord("C00724"), 200);
		tbl.addColumn(SC.itemCode, getWord("C00572"), 100);
		tbl.addColumn(SC.itemNames, getWord("C00730"), 200);
		tbl.addColumn(SC.subItemCode, getWord("C00602"), 100);
		tbl.addColumn(SC.subItemNames, getWord("C00739"), 200);
		tbl.addColumn(SC.detailItemCode, getWord("C00603"), 100);
		tbl.addColumn(SC.detailItemNames, getWord("C01594"), 200);
		tbl.addColumn(SC.bankIndentify, getWord("C11457"), 100); // 銀行識別子
		tbl.addColumn(SC.bankAccountIndentify, getWord("C11458"), 200); // 口座識別子
		tbl.addColumn(SC.iBan, getWord("C11456"), 200); // IBANコード
		tbl.addColumn(SC.swift, getWord("C10414"), 100); // SWIFTコード
		tbl.addColumn(SC.bankCountry, getWord("C11425"), 100, SwingConstants.CENTER); // BANK COUNTRY
		tbl.addColumn(SC.bnkAdr1, getWord("C00687"), 200); // 住所1
		tbl.addColumn(SC.bnkAdr1_e, getWord("C11893"), 200); // 住所1(英字)
		tbl.addColumn(SC.bnkAdr2, getWord("C00688"), 200); // 住所2
		tbl.addColumn(SC.bnkAdr2_e, getWord("C11894"), 200); // 住所2(英字)
		tbl.addColumn(SC.dateFrom, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, getWord("C00261"), 100, SwingConstants.CENTER);
	}

}
