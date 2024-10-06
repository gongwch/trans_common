package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理1一覧エクセル
 */
public class Management1Excel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public Management1Excel(String lang) {
		super(lang);
	}

	/**
	 * 管理1一覧をエクセルで返す
	 * 
	 * @param company 会社
	 * @param list 管理1リスト
	 * @return 管理1一覧エクセル
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management1> list) throws TException {

		try {
			createReport(company, list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param company
	 * @param list
	 */
	public void createReport(Company company, List<Management1> list) {

		String management1Name = "";
		if (company.getAccountConfig().getManagement1Name() == null) {
			management1Name = getWord("C01025");
		} else {
			management1Name = company.getAccountConfig().getManagement1Name();
		}

		// シート追加 マスタ
		TExcelSheet sheet = addSheet(management1Name + getWord("C00500"));

		// カラム設定
		// コード
		sheet.addColumn(management1Name + getWord("C00174"), 4200);
		// 名称
		sheet.addColumn(management1Name + getWord("C00518"), 8400);
		// 略称
		sheet.addColumn(management1Name + getWord("C00548"), 8400);
		// 検索名称
		sheet.addColumn(management1Name + getWord("C00160"), 8400);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (Management1 m1 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m1.getCode());
			newRow.addCell(m1.getName());
			newRow.addCell(m1.getNames());
			newRow.addCell(m1.getNamek());
			newRow.addCell(m1.getDateFrom());
			newRow.addCell(m1.getDateTo());
		}
	}
}
