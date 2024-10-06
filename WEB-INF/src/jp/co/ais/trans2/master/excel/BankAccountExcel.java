package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 銀行口座一覧エクセル
 */
public class BankAccountExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public BankAccountExcel(String lang) {
		super(lang);
	}

	/**
	 * 銀行口座一覧をエクセルで返す
	 * 
	 * @param bankAccountList 銀行口座一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<BankAccount> bankAccountList) throws TException {

		try {
			createReport(bankAccountList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param bankAccountList
	 */
	public void createReport(List<BankAccount> bankAccountList) {

		// シート追加
		// 銀行口座マスタ
		TExcelSheet sheet = addSheet(getWord("C02322"));

		// カラム設定
		// 銀行口座コード
		sheet.addColumn(getWord("C01879"), 4200);
		// 銀行口座名称
		sheet.addColumn(getWord("C02145"), 8400);
		// 通貨コード
		sheet.addColumn(getWord("C00665"), 3000);
		// 銀行コード
		sheet.addColumn(getWord("C00779"), 4200);
		// 銀行名称
		sheet.addColumn(getWord("C00781"), 8400);
		// 銀行名称（英字）
		sheet.addColumn(getWord("C11891"), 8400);
		// 支店コード
		sheet.addColumn(getWord("C02055"), 4200);
		// 支店名称
		sheet.addColumn(getWord("C02060"), 8400);
		// 支店名称（英字）
		sheet.addColumn(getWord("C11892"), 8400);
		// 振込依頼人コード
		sheet.addColumn(getWord("C00858"), 4800);
		// 振込依頼人名（ｶﾅ）"
		sheet.addColumn(getWord("C00859"), 8400);
		// 振込依頼人名（漢字）
		sheet.addColumn(getWord("C00860"), 8400);
		// 振込依頼人名（英字）
		sheet.addColumn(getWord("C00861"), 8400);
		// 預金種目
		sheet.addColumn(getWord("C01326"), 2500);
		// 口座番号
		sheet.addColumn(getWord("C00794"), 4200);
		// 社員FB区分
		sheet.addColumn(getWord("C01117"), 4000);
		// 社外FB区分
		sheet.addColumn(getWord("C01122"), 4000);
		// 計上部門コード
		sheet.addColumn(getWord("C00571"), 4200);
		// 計上部門略称
		sheet.addColumn(getWord("C00863") + getWord("C00548"), 8400);
		// 科目コード
		sheet.addColumn(getWord("C00572"), 4200);
		// 科目略称
		sheet.addColumn(getWord("C00730"), 8400);
		// 補助科目コード
		sheet.addColumn(getWord("C00602"), 4200);
		// 補助科目略称
		sheet.addColumn(getWord("C00739"), 8400);
		// 内訳科目コード
		sheet.addColumn(getWord("C00603"), 4200);
		// 内訳科目略称
		sheet.addColumn(getWord("C01594"), 8400);
		// SWIFTコード
		sheet.addColumn(getWord("C10414"), 4200);
		// BANK COUNTRY
		sheet.addColumn(getWord("C11425"), 4200);
		// 住所1
		sheet.addColumn(getWord("C00687"), 8400);
		// 住所1(英字)
		sheet.addColumn(getWord("C11893"), 8400);
		// 住所2
		sheet.addColumn(getWord("C00688"), 8400);
		// 住所2(英字)
		sheet.addColumn(getWord("C11894"), 8400);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (BankAccount bankAccoount : bankAccountList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bankAccoount.getCode());
			newRow.addCell(bankAccoount.getName());
			newRow.addCell(bankAccoount.getCurrencyCode(), SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getBankCode());
			newRow.addCell(bankAccoount.getBankName());
			newRow.addCell(bankAccoount.getBankNameE());
			newRow.addCell(bankAccoount.getBranchCode());
			newRow.addCell(bankAccoount.getBranchName());
			newRow.addCell(bankAccoount.getBranchNameE());
			newRow.addCell(bankAccoount.getClientCode());
			newRow.addCell(bankAccoount.getClientName());
			newRow.addCell(bankAccoount.getClientNameJ());
			newRow.addCell(bankAccoount.getClientNameE());
			newRow.addCell(getWord(DepositKind.getDepositKindName(bankAccoount.getDepositKind())),
				SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getAccountNo());
			newRow.addCell(bankAccoount.isUseEmployeePayment() ? getWord("C02149") : getWord("C02148"),
				SwingConstants.CENTER);
			newRow
				.addCell(bankAccoount.isUseExPayment() ? getWord("C02151") : getWord("C02150"), SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getDepartmentCode());
			newRow.addCell(bankAccoount.getDepartmentNames());
			newRow.addCell(bankAccoount.getItemCode());
			newRow.addCell(bankAccoount.getItemNames());
			newRow.addCell(bankAccoount.getSubItemCode());
			newRow.addCell(bankAccoount.getSubItemNames());
			newRow.addCell(bankAccoount.getDetailItemCode());
			newRow.addCell(bankAccoount.getDetailItemNames());
			newRow.addCell(bankAccoount.getSwift());
			newRow.addCell(bankAccoount.getBankCountry()); // Bank Country
			newRow.addCell(bankAccoount.getBnkAdr1());
			newRow.addCell(bankAccoount.getBnkAdr1E());
			newRow.addCell(bankAccoount.getBnkAdr2());
			newRow.addCell(bankAccoount.getBnkAdr2E());
			newRow.addCell(DateUtil.toYMDString(bankAccoount.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bankAccoount.getDateTo()), SwingConstants.CENTER);
		}

	}
}
