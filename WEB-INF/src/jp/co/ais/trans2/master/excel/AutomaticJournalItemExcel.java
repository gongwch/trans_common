package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目制御区分一覧エクセル
 */
public class AutomaticJournalItemExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public AutomaticJournalItemExcel(String lang) {
		super(lang);
	}

	/**
	 * 科目制御区分一覧をエクセルで返す
	 * 
	 * @param list 科目制御区分一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<AutomaticJournalItem> list) throws TException {

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
	public void createReport(List<AutomaticJournalItem> list) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C00911"));

		// カラム設定
		sheet.addColumn(getWord("C01008"), 3200);
		sheet.addColumn(getWord("C11885"), 6400);
		sheet.addColumn(getWord("C00571"), 4200);
		sheet.addColumn(getWord("C10389"), 6400);
		sheet.addColumn(getWord("C00572"), 4200);
		sheet.addColumn(getWord("C00700"), 6400);
		sheet.addColumn(getWord("C00602"), 4200);
		sheet.addColumn(getWord("C00701"), 6400);
		sheet.addColumn(getWord("C00603"), 4200);
		sheet.addColumn(getWord("C00702"), 6400);

		// 明細描画
		for (AutomaticJournalItem bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKMK_CNT());
			newRow.addCell(bean.getKMK_CNT_NAME());
			newRow.addCell(bean.getDEP_CODE());
			newRow.addCell(bean.getDEP_NAME());
			newRow.addCell(bean.getKMK_CODE());
			newRow.addCell(bean.getKMK_NAME());
			newRow.addCell(bean.getHKM_CODE());
			newRow.addCell(bean.getHKM_NAME());
			newRow.addCell(bean.getUKM_CODE());
			newRow.addCell(bean.getUKM_NAME());
		}

	}
}