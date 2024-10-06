package jp.co.ais.trans2.master.excel;

import java.util.List;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.TExcel;
import jp.co.ais.trans2.common.excel.TExcelRow;
import jp.co.ais.trans2.common.excel.TExcelSheet;
import jp.co.ais.trans2.common.model.format.RateFormat;
import jp.co.ais.trans2.define.RateType;
import jp.co.ais.trans2.model.currency.*;

/**
 * 通貨レートマスタエクセル
 * 
 * @author AIS
 */
public class RateExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public RateExcel(String lang) {
		super(lang);
	}

	/**
	 * 一覧をエクセルで返す
	 * 
	 * @param list
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(List<Rate> list) throws TException {

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
	public void createReport(List<Rate> list) {

		// シート追加
		// 通貨レートマスタ
		TExcelSheet sheet = addSheet(getWord("C11158"));

		// カラム設定
		// レート区分
		sheet.addColumn(getWord("C00883"), 8400);
		// 通貨コード
		sheet.addColumn(getWord("C00665"), 4200);
		// 適用開始日
		sheet.addColumn(getWord("C03741"), 6300);
		// レート
		sheet.addColumn(getWord("C00556"), 6300);

		// 明細描画
		for (Rate rate : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(getWord(RateType.getName(rate.getRateType())));
			newRow.addCell(rate.getCurrency().getCode());
			newRow.addCell(rate.getTermFrom());
			newRow.addCell(rate.getCurrencyRate(), RateFormat.getRateDecimalPoint());
		}
	}
}