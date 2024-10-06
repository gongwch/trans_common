package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 新送金目的一覧エクセル
 */
public class RemittancePurposeExcel extends TExcel {

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	public RemittancePurposeExcel(String lang) {
		super(lang);
	}

	/**
	 * 新送金目的一覧をエクセルで返す
	 * 
	 * @param remittancePurposeList 新送金目的一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Remittance> remittancePurposeList) throws TException {

		try {
			createReport(remittancePurposeList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param remittancePurposeList
	 */
	public void createReport(List<Remittance> remittancePurposeList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C11843")); // 送金目的マスタ

		// カラム設定
		sheet.addColumn(getWord("C00793"), 5200); // 送金目的コード
		sheet.addColumn(getWord("C11839"), 5200); // 国際収支コード
		sheet.addColumn(getWord("C11840"), 8400); // 送金目的名称

		// 明細描画
		for (Remittance remittancePurpose : remittancePurposeList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(remittancePurpose.getCode());
			newRow.addCell(remittancePurpose.getBalanceCode());
			newRow.addCell(remittancePurpose.getName());
		}
	}
}