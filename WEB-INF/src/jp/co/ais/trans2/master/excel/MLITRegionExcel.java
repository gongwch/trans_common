package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * 輸送実績国マスタエクセル
 */
public class MLITRegionExcel extends TExcel {

	/** 会社情報 */
	protected Company company = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param company
	 */
	public MLITRegionExcel(String lang, Company company) {
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
	public byte[] getExcel(List<YJRegion> list) throws TException {

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
	public void createReport(List<YJRegion> list) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("CBL542")); // 輸送実績国マスタ

		// カラム設定
		sheet.addColumn(getWord("CBL538"), 4200); // 国コード
		sheet.addColumn(getWord("CBL539"), 10400); // 国名称
		sheet.addColumn(getWord("CM0015"), 20800); // Remark

		// 明細描写
		for (YJRegion bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getREGION_CODE());
			newRow.addCell(bean.getREGION_NAME());
			newRow.addCell(bean.getREGION_REMARK());
		}
	}
}