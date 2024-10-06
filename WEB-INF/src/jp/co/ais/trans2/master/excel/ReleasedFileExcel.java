package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.releasedfile.*;

/**
 * リリースファイル一覧エクセル
 * 
 * @author AIS
 */
public class ReleasedFileExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public ReleasedFileExcel(String lang) {
		super(lang);
	}

	/**
	 * 帳票を返す
	 * 
	 * @param executed
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getExcel(List<ReleasedFile> executed) throws TException {

		try {
			createReport(executed);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param executed
	 */
	public void createReport(List<ReleasedFile> executed) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02914"));

		// カラム設定

		// フォルダ
		sheet.addColumn(getWord("C02912"), 15000);
		// ファイル
		sheet.addColumn(getWord("C01988"), 8000);
		// 更新日時
		sheet.addColumn(getWord("C00169") + getWord("C02906"), 6600);
		// ファイルサイズ
		sheet.addColumn(getWord("C02915"), 5000);

		// 明細描画
		for (ReleasedFile bean : executed) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getFolder());
			newRow.addCell(bean.getFile());
			newRow.addCell(DateUtil.toYMDHMSString(bean.getUpdate()), SwingConstants.CENTER);
			newRow.addCell(Long.toString(bean.getSize()), SwingConstants.RIGHT);

		}

	}
}
