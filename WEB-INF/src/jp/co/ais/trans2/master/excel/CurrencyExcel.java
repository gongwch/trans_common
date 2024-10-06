package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 通貨一覧エクセル
 */
public class CurrencyExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public CurrencyExcel(String lang) {
		super(lang);
	}

	/**
	 * 通貨一覧をエクセルで返す
	 * 
	 * @param CurrencyList 通貨一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Currency> CurrencyList) throws TException {

		try {
			createReport(CurrencyList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param currencyList
	 */
	public void createReport(List<Currency> currencyList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C01985"));

		// カラム設定
		sheet.addColumn(getWord("C00665"), 3200);// 通貨コード
		sheet.addColumn(getWord("C00880"), 9900);// 通貨名称
		sheet.addColumn(getWord("C00881"), 6400);// 通貨略称
		sheet.addColumn(getWord("C00882"), 6400);// 通貨検索名称
		sheet.addColumn(getWord("C00896"), 4200);// レート係数
		sheet.addColumn(getWord("C00884"), 4200);// 小数点以下桁数
		sheet.addColumn(getWord("C00055"), 4200);// 開始年月日
		sheet.addColumn(getWord("C00261"), 4200);// 終了年月日

		// 明細描画
		for (Currency currency : currencyList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(currency.getCode(), SwingConstants.CENTER);
			newRow.addCell(currency.getName());
			newRow.addCell(currency.getNames());
			newRow.addCell(currency.getNamek());
			newRow.addCell(currency.getRatePow());
			newRow.addCell(currency.getDecimalPoint());
			newRow.addCell(DateUtil.toYMDString(currency.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(currency.getDateTo()), SwingConstants.CENTER);
		}

	}
}