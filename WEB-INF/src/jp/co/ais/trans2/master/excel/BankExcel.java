package jp.co.ais.trans2.master.excel;

import java.util.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.bank.*;

/**
 * 銀行マスタエクセル
 */

public class BankExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public BankExcel(String lang) {
		super(lang);
	}
	
	/**
	 * 銀行一覧をエクセルに返す
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<Bank> list) throws TException {

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
	public void createReport(List<Bank> list) {

		//シート追加
		TExcelSheet sheet = addSheet(getWord("C02323"));

		//カラム設定
		sheet.addColumn(getWord("C00779"), 4200);
		sheet.addColumn(getWord("C00780"), 4200);
		sheet.addColumn(getWord("C00781"), 8200);
		sheet.addColumn(getWord("C00782"), 4200);
		sheet.addColumn(getWord("C00829"), 8200);
		sheet.addColumn(getWord("C00783"), 8200);
		sheet.addColumn(getWord("C00784"), 4200);
		sheet.addColumn(getWord("C00785"), 8200);
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		//明細描写
		for (Bank bank : list) {
			
			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bank.getCode());
			newRow.addCell(bank.getBranch().getCode());
			newRow.addCell(bank.getNames());
			newRow.addCell(bank.getKana());
			newRow.addCell(bank.getNamek());
			newRow.addCell(bank.getBranch().getNames());
			newRow.addCell(bank.getBranch().getKana());
			newRow.addCell(bank.getBranch().getNamek());
			newRow.addCell(bank.getDateFrom());
			newRow.addCell(bank.getDateTo());
		}

	}
}