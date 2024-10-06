package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理5一覧エクセル
 */
public class Management5Excel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public Management5Excel(String lang) {
		super(lang);
	}

	/**
	 * 管理5一覧をエクセルで返す
	 * 
	 * @param company 会社
	 * @param list 管理5リスト
	 * @return 管理5一覧エクセル
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management5> list) throws TException {

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
	public void createReport(Company company, List<Management5> list) {

		String management5Name = "";
		if (Util.isNullOrEmpty(company.getAccountConfig().getManagement5Name())) {
			management5Name = getWord("C01033");
		} else {
			management5Name = company.getAccountConfig().getManagement5Name();
		}

		// シート追加 マスタ
		TExcelSheet sheet = addSheet(management5Name + getWord("C00500"));

		// カラム設定
		// 会社コード
		sheet.addColumn(getWord("C00596"), 4200);
		// コード
		sheet.addColumn(management5Name + getWord("C00174"), 4200);
		// 名称
		sheet.addColumn(management5Name + getWord("C00518"), 8400);
		// 略称
		sheet.addColumn(management5Name + getWord("C00548"), 8400);
		// 検索名称
		sheet.addColumn(management5Name + getWord("C00160"), 8400);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (Management5 m1 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m1.getCompanyCode());
			newRow.addCell(m1.getCode());
			newRow.addCell(m1.getName());
			newRow.addCell(m1.getNames());
			newRow.addCell(m1.getNamek());
			newRow.addCell(DateUtil.toYMDString(m1.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m1.getDateTo()), SwingConstants.CENTER);
		}
	}
}
