package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.program.*;

/**
 * システム区分一覧エクセル
 * 
 * @author AIS
 */
public class SystemDivisionExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public SystemDivisionExcel(String lang) {
		super(lang);
	}

	/**
	 * システム区分一覧をエクセルで返す
	 * 
	 * @param systemList システム区分
	 * @return userList
	 * @throws TException
	 */
	public byte[] getExcel(List<SystemDivision> systemList) throws TException {

		try {
			createReport(systemList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param systemList
	 */
	public void createReport(List<SystemDivision> systemList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02354"));

		// カラム設定
		// システム区分
		sheet.addColumn(getWord("C00980"), 4200);
		// システム区分名称
		sheet.addColumn(getWord("C00832"), 8400);
		// システム区分略称
		sheet.addColumn(getWord("C00833"), 8400);
		// ユーザー検索名称
		sheet.addColumn(getWord("C00834"), 8400);
		// 外部システム区分
		sheet.addColumn(getWord("C01018"), 8400);
		// 明細描画
		for (SystemDivision bean : systemList) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode(), SwingConstants.CENTER);
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(getWord(OuterSystemType.getName(bean.getOuterSystemType())), SwingConstants.CENTER);
		}

	}
}
