package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理3一覧エクセル
 */
public class Management3Excel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public Management3Excel(String lang) {
		super(lang);
	}

	/**
	 * 管理3一覧をエクセルで返す
	 * 
	 * @param company 会社
	 * @param list    管理3リスト
	 * @return 管理3一覧エクセル
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management3> list) throws TException {

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
	public void createReport(Company company, List<Management3> list) {

		String management3Name = "";
		if (company.getAccountConfig().getManagement3Name() == null) {
			management3Name = getWord("C01029");
		} else {
			management3Name = company.getAccountConfig().getManagement3Name();
		}

		// シート追加 マスタ
		TExcelSheet sheet = addSheet(management3Name + getWord("C00500"));

		// カラム設定

		// 会社コード
		sheet.addColumn(getWord("C00596"), 5500);
		// コード
		sheet.addColumn(management3Name + getWord("C00174"), 5500);
		// 名称
		sheet.addColumn(management3Name + getWord("C00518"), 8400);
		// 略称
		sheet.addColumn(management3Name + getWord("C00548"), 8400);
		// 検索名称
		sheet.addColumn(management3Name + getWord("C00160"), 8400);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (Management3 m3 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m3.getCompanyCode());
			newRow.addCell(m3.getCode());
			newRow.addCell(m3.getName());
			newRow.addCell(m3.getNames());
			newRow.addCell(m3.getNamek());
			newRow.addCell(DateUtil.toYMDString(m3.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m3.getDateTo()), SwingConstants.CENTER);
		}
	}
}
