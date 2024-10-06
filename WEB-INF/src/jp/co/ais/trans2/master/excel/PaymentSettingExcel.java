package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払条件エクセル
 */
public class PaymentSettingExcel extends TExcel {

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	public PaymentSettingExcel(String lang) {
		super(lang);
	}

	/**
	 * 一覧をエクセルで返す
	 * 
	 * @param list 摘要リスト
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(List<PaymentSetting> list) throws TException {

		try {
			createReport(list);

			return getBinary();

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param list
	 */
	public void createReport(List<PaymentSetting> list) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02325"));

		// カラム設定
		sheet.addColumn(getWord("C00786"), 5000); // 取引先コード
		sheet.addColumn(getWord("C00787"), 8000); // 取引先略称
		sheet.addColumn(getWord("C00788"), 5000); // 取引先条件コード
		sheet.addColumn(getWord("C01340") + getWord("C02056"), 4500); // \振込手数料区分
		sheet.addColumn(getWord("C02057"), 5000); // 支払条件締日
		sheet.addColumn(getWord("C02058"), 5000); // 支払条件締後月
		sheet.addColumn(getWord("C02059"), 5000); // 支払条件支払日
		sheet.addColumn(getWord("C00682"), 3000); // 支払区分
		sheet.addColumn(getWord("C00233"), 4000); // 支払方法
		sheet.addColumn(getWord("C00792"), 6000); // 振込振出銀行コード
		sheet.addColumn(getWord("C00779"), 4000); // 銀行コード
		sheet.addColumn(getWord("C00781"), 7000); // 銀行名称
		sheet.addColumn(getWord("C02055"), 4000); // 支店コード
		sheet.addColumn(getWord("C02060"), 7000); // 支店名称
		sheet.addColumn(getWord("C01326"), 3000); // 預金種目
		sheet.addColumn(getWord("C00794"), 5000); // 口座番号
		sheet.addColumn(getWord("C01068"), 8000); // 口座名義カナ
		sheet.addColumn(getWord("C02037"), 4000); // 送金目的名
		sheet.addColumn(getWord("C00795"), 8000); // 英文銀行名
		sheet.addColumn(getWord("C00796"), 8000); // 英文支店名
		sheet.addColumn(getWord("C00797"), 10000); // 英文銀行住所
		sheet.addColumn(getWord("C01334") + getWord("C10224"), 4000); // $手数料区分
		sheet.addColumn(getWord("C00799"), 8000); // 支払銀行名
		sheet.addColumn(getWord("C00800"), 8000); // 支払支店名
		sheet.addColumn(getWord("C00801"), 10000); // 支払銀行住所
		sheet.addColumn(getWord("C00802"), 8000); // 経由銀行名
		sheet.addColumn(getWord("C00803"), 8000); // 経由支店名
		sheet.addColumn(getWord("C00804"), 10000); // 経由銀行住所
		sheet.addColumn(getWord("C00805"), 10000); // 受取人住所
		sheet.addColumn(getWord("C00055"), 4000); // 開始年月日
		sheet.addColumn(getWord("C00261"), 4000); // 終了年月日

		// 明細描画
		for (PaymentSetting s1 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(s1.getCustomer().getCode());
			newRow.addCell(s1.getCustomer().getNames());
			newRow.addCell(s1.getCode());
			newRow.addCell(getWord(CashInFeeType.getCashInFeeTypeName(s1.getCashInFeeType())), SwingConstants.CENTER);
			newRow.addCell(s1.getCloseDay());
			newRow.addCell(s1.getNextMonth());
			newRow.addCell(s1.getCashDay());
			newRow.addCell(getWord(PaymentDateType.getPaymentDateTypeName(s1.getPaymentDateType())),
				SwingConstants.CENTER);

			// 支払方法
			if (s1.getPaymentMethod() == null) {
				newRow.addCell("");
			} else {
				newRow.addCell(s1.getPaymentMethod().getName(), SwingConstants.CENTER);
			}

			// 振込振出銀行コード
			if (s1.getBankAccount() == null) {
				newRow.addCell("");
			} else {
				newRow.addCell(s1.getBankAccount().getCode());
			}

			newRow.addCell(s1.getBankCode());
			newRow.addCell(s1.getBankName());
			newRow.addCell(s1.getBranchCode());
			newRow.addCell(s1.getBranchName());
			newRow.addCell(getWord(DepositKind.getDepositKindName(s1.getDepositKind())), SwingConstants.CENTER);
			newRow.addCell(s1.getAccountNo());
			newRow.addCell(s1.getAccountNameKana());

			if (s1.getRemittancePurpose() == null) {
				newRow.addCell("");
			} else {
				newRow.addCell(s1.getRemittancePurpose().getCode());
			}

			newRow.addCell(s1.getSendBankName());
			newRow.addCell(s1.getSendBranchName());
			newRow.addCell(s1.getAccountName());
			newRow
				.addCell(getWord(CommissionPaymentType.getName(s1.getCommissionPaymentType())), SwingConstants.CENTER);
			newRow.addCell(s1.getPayBankName());
			newRow.addCell(s1.getPayBranchName());
			newRow.addCell(s1.getPayBankAddress());
			newRow.addCell(s1.getViaBankName());
			newRow.addCell(s1.getViaBranchName());
			newRow.addCell(s1.getViaBankAddress());
			newRow.addCell(s1.getReceiverAddress());
			newRow.addCell(DateUtil.toYMDString(s1.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(s1.getDateTo()), SwingConstants.CENTER);
		}
	}

}
