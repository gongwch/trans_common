package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.attach.verify.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 添付ファイル検証結果エクセル
 */
public class AttachmentVerifyExcel extends TExcel {

	/** 編集中シート */
	TExcelSheet sheet;

	/**
	 * @param lang
	 */
	public AttachmentVerifyExcel(String lang) {
		super(lang);
	}

	/**
	 * Excel取得
	 * 
	 * @param list
	 * @return excel
	 * @throws TException
	 */
	public byte[] getReport(List<AttachmentVerifyResult> list) throws TException {
		try {
			// シート初期化
			initSheet();
			// タイトル行記述
			drawTitleRow();
			// 明細行記述
			drawDetailRow(list);

			return super.getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * 
	 */
	protected void drawTitleRow() {
		// カラム設定
		sheet.addColumn(getWord("会社コード"), 3200);
		sheet.addColumn(getWord("データ種別"), 6400);
		sheet.addColumn(getWord("キー情報"), 6400);
		sheet.addColumn(getWord("追加情報"), 6400);
		sheet.addColumn(getWord("ファイル名"), 6400);
		sheet.addColumn(getWord("サーバーファイル名"), 6400);
		sheet.addColumn(getWord("メッセージ"), 4200);
	}

	/**
	 * @param list
	 */
	protected void drawDetailRow(List<AttachmentVerifyResult> list) {
		// 明細描画
		for (AttachmentVerifyResult bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKAI_CODE());
			newRow.addCell(bean.getTYPE().toString());
			newRow.addCell(bean.getKEY1());
			newRow.addCell(bean.getKEY2());
			newRow.addCell(bean.getKEY3());
			newRow.addCell(bean.getKEY4());
			newRow.addCell(bean.getFILE_NAME());
			newRow.addCell(bean.getSRV_FILE_NAME());
			newRow.addCell(bean.getMESSAGE());
		}

	}

	/**
	 * 
	 */
	protected void initSheet() {
		// シート追加
		sheet = addSheet(getWord("C00911"));
	}
}
