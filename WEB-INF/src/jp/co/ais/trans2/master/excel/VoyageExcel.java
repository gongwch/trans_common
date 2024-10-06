package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.voyage.*;

/**
 * 次航マスタエクセル
 * 
 * @author AIS
 */
public class VoyageExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public VoyageExcel(String lang) {
		super(lang);
	}

	/**
	 * 一覧をエクセルで返す
	 * 
	 * @param list
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(List<Voyage> list) throws TException {

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
	public void createReport(List<Voyage> list) {

		// シート追加
		// 会社コントロールマスタ
		TExcelSheet sheet = addSheet(getWord("C11779")); // 次航マスタ

		// カラム設定
		// 次航コード
		sheet.addColumn(getWord("C03003"), 4200);
		// 次航名称
		sheet.addColumn(getWord("C11780"), 12600);
		// 次航略称
		sheet.addColumn(getWord("C11781"), 8400);
		// 次航検索名称
		sheet.addColumn(getWord("C11782"), 12600);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (Voyage bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(bean.getDateFrom());
			newRow.addCell(bean.getDateTo());

		}

	}

}
