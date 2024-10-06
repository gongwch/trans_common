package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * 輸送実績アイテムマスタエクセル
 */
public class MLITItemExcel extends TExcel {

	/** 会社情報 */
	protected Company company = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param company
	 */
	public MLITItemExcel(String lang, Company company) {
		super(lang);
		this.company = company;
	}

	/**
	 * 一覧をエクセルに返す
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<YJItem> list) throws TException {

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
	public void createReport(List<YJItem> list) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("CBL302")); // 輸送実績アイテムマスタ

		// カラム設定
		sheet.addColumn(getWord("CBL303"), 4200); // アイテムコード
		sheet.addColumn(getWord("CBL304"), 10400); // アイテム名称
		sheet.addColumn(getWord("CM0015"), 20800); // Remark

		// 明細描写
		for (YJItem bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getITEM_CODE());
			newRow.addCell(bean.getITEM_NAME());
			newRow.addCell(bean.getREMARK());
		}
	}
}