package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理4一覧エクセル
 */
public class Management4Excel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public Management4Excel(String lang) {
		super(lang);
	}

	/**
	 * 管理4一覧をエクセルで返す
	 * 
	 * @param company 会社
	 * @param list    管理4リスト
	 * @return 管理4一覧エクセル
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management4> list) throws TException {

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
	public void createReport(Company company, List<Management4> list) {

		String management4Name = "";
		if (company.getAccountConfig().getManagement4Name() == null) {
			management4Name = getWord("C01031");
		} else {
			management4Name = company.getAccountConfig().getManagement4Name();
		}

		// シート追加 マスタ
		TExcelSheet sheet = addSheet(management4Name + getWord("C00500"));

		// カラム設定
		// 会社コード
		sheet.addColumn(getWord("C00596"), 4200);
		// コード
		sheet.addColumn(management4Name + getWord("C00174"), 4200);
		// 名称
		sheet.addColumn(management4Name + getWord("C00518"), 8400);
		// 略称
		sheet.addColumn(management4Name + getWord("C00548"), 8400);
		// 検索名称
		sheet.addColumn(management4Name + getWord("C00160"), 8400);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (Management4 m4 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m4.getCompanyCode());
			newRow.addCell(m4.getCode());
			newRow.addCell(m4.getName());
			newRow.addCell(m4.getNames());
			newRow.addCell(m4.getNamek());
			newRow.addCell(DateUtil.toYMDString(m4.getDateFrom()),SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m4.getDateTo()), SwingConstants.CENTER);
		}
	}
}
