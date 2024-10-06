package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.bill.*;

/**
 * 請求区分マスタエクセル
 */

public class BillTypeExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public BillTypeExcel(String lang) {
		super(lang);
	}

	/**
	 * 銀行一覧をエクセルに返す
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<BillType> list) throws TException {

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
	public void createReport(List<BillType> list) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C10235")); // 請求区分マスタ

		// カラム設定
		sheet.addColumn(getWord("C10092"), 4200); // 請求区分
		sheet.addColumn(getWord("C10096"), 8400); // 請求名称
		sheet.addColumn(getWord("C10095"), 10400); // 検索名称
		sheet.addColumn(getWord("C10097"), 8400); // 請求書フォーマット
		sheet.addColumn(getWord("C11556"), 4200); // 明細件数
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描写
		for (BillType billType : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(billType.getCode());
			newRow.addCell(billType.getName());
			newRow.addCell(billType.getNamek());
			newRow.addCell(billType.getFormat());
			newRow.addCell(billType.getDetailCount());
			newRow.addCell(billType.getDateFrom());
			newRow.addCell(billType.getDateTo());
		}

	}
}