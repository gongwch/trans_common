package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 国際収支一覧エクセル
 */
public class BalanceAccountsExcel extends TExcel {

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	public BalanceAccountsExcel(String lang) {
		super(lang);
	}

	/**
	 * 国際収支一覧をエクセルで返す
	 * 
	 * @param remittanceList 国際収支一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Remittance> remittanceList) throws TException {

		try {
			createReport(remittanceList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param remittanceList
	 */
	public void createReport(List<Remittance> remittanceList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C11841")); // 国際収支コードマスタ

		// カラム設定
		sheet.addColumn(getWord("C11839"), 5200); // 国際収支コード
		sheet.addColumn(getWord("C11842"), 8400); // 国際収支名称

		// 明細描画
		for (Remittance remittance : remittanceList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(remittance.getCode());
			newRow.addCell(remittance.getName());
		}
	}
}