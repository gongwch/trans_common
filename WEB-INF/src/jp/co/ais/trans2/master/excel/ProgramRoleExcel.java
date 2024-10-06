package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.security.*;

/**
 * プログラムロールエクセル
 * 
 * @author AIS
 */
public class ProgramRoleExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public ProgramRoleExcel(String lang) {
		super(lang);
	}

	/**
	 * 帳票を返す
	 * 
	 * @param list
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getExcel(List<ProgramRole> list) throws TException {

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
	public void createReport(List<ProgramRole> list) {

		// シート追加
		// プログラムロールマスタ
		TExcelSheet sheet = addSheet(getWord("C11153"));

		// カラム設定
		// ロールコード
		sheet.addColumn(getWord("C11154"), 5000);
		// ロール名称
		sheet.addColumn(getWord("C11155"), 12000);
		// ロール略称
		sheet.addColumn(getWord("C11156"), 8000);
		// ロール検索名称
		sheet.addColumn(getWord("C11157"), 8000);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 5000);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 5000);
		// フォーマット

		// 明細描画
		for (ProgramRole bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(DateUtil.toYMDString(bean.getTermFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getTermTo()), SwingConstants.CENTER);

		}

	}
}
