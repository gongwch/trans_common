package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * 取引先担当者一覧エクセル
 */
public class CustomerUsrExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public CustomerUsrExcel(String lang) {
		super(lang);
	}

	/**
	 * 取引先担当者一覧をエクセルで返す
	 * 
	 * @param customerList 取引先担当者一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<CustomerUser> customerList) throws TException {

		try {
			createReport(customerList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param customerList
	 */
	public void createReport(List<CustomerUser> customerList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C00363") + getWord("C00010"));// 担当者一覧

		// カラム設定
		sheet.addColumn(getWord("C00053"), 4200);// 会社コード
		sheet.addColumn(getWord("C00786"), 4200);// 取引先コード
		sheet.addColumn(getWord("C00830"), 8400);// 取引先名称
		sheet.addColumn(getWord("C00980"), 3000);// システム区分
		sheet.addColumn(getWord("C11296"), 12000);// 担当者名称
		sheet.addColumn(getWord("NK0182") + getWord("C00518"), 12000);// 部署名称
		sheet.addColumn(getWord("C12094"), 12000);// 役職

		// 明細描画
		for (CustomerUser bean : customerList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKAI_CODE());
			newRow.addCell(bean.getTRI_CODE());
			newRow.addCell(bean.getTRI_NAME());
			newRow.addCell(bean.getSYS_KBN(), SwingConstants.CENTER);
			newRow.addCell(bean.getUSR_NAME());
			newRow.addCell(bean.getDEP_NAME());
			newRow.addCell(bean.getPOSITION());

		}

	}
}
