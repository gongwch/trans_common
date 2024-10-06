package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理6一覧エクセル
 */
public class Management6Excel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public Management6Excel(String lang) {
		super(lang);
	}

	/**
	 * 管理6一覧をエクセルで返す
	 * 
	 * @param company 会社
	 * @param list 管理6リスト
	 * @return 管理6一覧エクセル
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management6> list) throws TException {

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
	 * @param list
	 */
	public void createReport(Company company, List<Management6> list) {

		String management6Name = "";
		if (Util.isNullOrEmpty(company.getAccountConfig().getManagement6Name())) {
			management6Name = getWord("C01035");
		} else {
			management6Name = company.getAccountConfig().getManagement6Name();
		}

		// シート追加 マスタ
		TExcelSheet sheet = addSheet(management6Name + getWord("C00500"));

		// カラム設定

		// 会社コード
		sheet.addColumn(getWord("C00596"), 4200);
		// 管理６コード
		sheet.addColumn(management6Name + getWord("C00174"), 4200);
		// 管理６名称
		sheet.addColumn(management6Name + getWord("C00518"), 8400);
		// 管理６略称
		sheet.addColumn(management6Name + getWord("C00548"), 8400);
		// 管理６検索名称
		sheet.addColumn(management6Name + getWord("C00160"), 8400);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (Management6 m6 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m6.getCompanyCode());
			newRow.addCell(m6.getCode());
			newRow.addCell(m6.getName());
			newRow.addCell(m6.getNames());
			newRow.addCell(m6.getNamek());
			newRow.addCell(DateUtil.toYMDString(m6.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m6.getDateTo()), SwingConstants.CENTER);
		}
	}
}
