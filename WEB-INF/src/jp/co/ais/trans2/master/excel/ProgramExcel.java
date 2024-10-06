package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.program.*;

/**
 * プログラムエクセル
 * 
 * @author AIS
 */
public class ProgramExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public ProgramExcel(String lang) {
		super(lang);
	}

	/**
	 * 帳票を返す
	 * 
	 * @param list
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getExcel(List<Program> list) throws TException {

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
	public void createReport(List<Program> list) {

		// シート追加
		// プログラムマスタ
		TExcelSheet sheet = addSheet(getWord("C02147"));

		// カラム設定

		// システムコード
		sheet.addColumn(getWord("C02351"), 4200);
		// プログラムコード
		sheet.addColumn(getWord("C00818"), 5000);
		// プログラム名称
		sheet.addColumn(getWord("C00819"), 12000);
		// プログラム略称
		sheet.addColumn(getWord("C00820"), 8000);
		// プログラム検索名称
		sheet.addColumn(getWord("C00821"), 8000);
		// コメント
		sheet.addColumn(getWord("C00183"), 12000);
		// ロードモジュール名
		sheet.addColumn(getWord("C11152"), 18000);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 5000);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 5000);
		// フォーマット

		// 明細描画
		for (Program bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getSystemClassification().getCode());
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(bean.getComment());
			newRow.addCell(bean.getLoadClassName());
			newRow.addCell(DateUtil.toYMDDate(bean.getTermFrom()));
			newRow.addCell(DateUtil.toYMDDate(bean.getTermTo()));

		}

	}
}
