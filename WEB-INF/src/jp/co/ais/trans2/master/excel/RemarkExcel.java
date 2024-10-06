package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * 摘要エクセル
 * 
 * @author AIS
 */
public class RemarkExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public RemarkExcel(String lang) {
		super(lang);
	}

	/**
	 * 帳票を返す
	 * 
	 * @param list
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getExcel(List<Remark> list) throws TException {

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
	public void createReport(List<Remark> list) {

		TExcelSheet sheet = addSheet(getWord("C02349"));

		// 摘要区分
		sheet.addColumn(getWord("C00568"), 4200);
		// データ区分
		sheet.addColumn(getWord("C00567"), 4200);
		// 摘要コード
		sheet.addColumn(getWord("C00564"), 4200);
		// 摘要名称
		sheet.addColumn(getWord("C00565"), 12600);
		// 摘要検索名称
		sheet.addColumn(getWord("C00566"), 12600);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);
		// フォーマット

		// 明細描画
		for (Remark bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.isSlipRemark() ? getWord("C00569") : getWord("C00119"), SwingConstants.CENTER);
			newRow.addCell(getWord(DataDivision.getDataDivisionName(DataDivision.getDataDivision(bean.getDataType()))),SwingConstants.CENTER);
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNamek());
			newRow.addCell(DateUtil.toYMDString(bean.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getDateTo()), SwingConstants.CENTER);

		}

	}
}
