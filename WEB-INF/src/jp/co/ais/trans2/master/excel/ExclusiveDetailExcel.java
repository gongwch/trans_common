package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.exclusive.*;

/**
 * 排他明細一覧エクセル
 */
public class ExclusiveDetailExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public ExclusiveDetailExcel(String lang) {
		super(lang);
	}

	/**
	 * 排他明細一覧をエクセルで返す
	 * 
	 * @param list 排他明細リスト
	 * @return 排他明細一覧エクセル
	 * @throws TException
	 */
	public byte[] getExcel(List<ExclusiveDetail> list) throws TException {

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
	public void createReport(List<ExclusiveDetail> list) {

		// シート追加 マスタ
		TExcelSheet sheet = addSheet(getWord("C11951"));

		// カラム設定
		// 会社コード
		sheet.addColumn(getWord("C00596"), 4200);
		// 処理区分
		sheet.addColumn(getWord("C02829"), 4200);
		// 排他キー
		sheet.addColumn(getWord("C11950"), 8400);
		// 行番号
		sheet.addColumn(getWord("C01588"), 4200);
		// 処理日時
		sheet.addColumn(getWord("C11757"), 8400);
		// プログラムID
		sheet.addColumn(getWord("C10994"), 4200);
		// プログラム名称
		sheet.addColumn(getWord("C00819"), 8400);
		// ユーザーID
		sheet.addColumn(getWord("C10995"), 4200);
		// ユーザー名称
		sheet.addColumn(getWord("C00691"), 8400);

		// 明細描画
		for (ExclusiveDetail bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKAI_CODE());
			newRow.addCell(bean.getSHORI_KBN(), SwingConstants.CENTER);
			newRow.addCell(bean.getHAITA_KEY());
			newRow.addCell(bean.getGYO_NO());
			newRow.addCell(DateUtil.toYMDHMString(bean.getINP_DATE()), SwingConstants.CENTER);
			newRow.addCell(bean.getPRG_ID());
			newRow.addCell(bean.getPRG_NAME());
			newRow.addCell(bean.getUSR_ID());
			newRow.addCell(bean.getUSR_NAME());
		}
	}
}
