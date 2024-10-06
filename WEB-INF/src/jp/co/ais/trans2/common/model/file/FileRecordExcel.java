package jp.co.ais.trans2.common.model.file;

import java.util.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.file.*;

/**
 * 取込履歴エクセル
 */
public class FileRecordExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public FileRecordExcel(String lang) {
		super(lang);
	}

	/**
	 * 取込履歴リスト
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<TFile> list) throws TException {

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
	public void createReport(List<TFile> list) {

		// シート追加
		// 取込履歴
		TExcelSheet sheet = addSheet(getWord("C11098") + ".xls");

		// カラム設定
		// 取込日
		sheet.addColumn(getWord("C10101"), 6300);
		// ユーザーID
		sheet.addColumn(getWord("C10995"), 4200);
		// ファイル名称
		sheet.addColumn(getWord("C11099"), 8000);

		// 明細描画
		for (TFile file : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(DateUtil.toYMDHMSString(file.getInputDate()));
			newRow.addCell(file.getUserCode());
			newRow.addCell(file.getFileName());

		}

	}

}
