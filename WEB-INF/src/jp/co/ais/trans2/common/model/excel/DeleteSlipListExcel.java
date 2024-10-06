package jp.co.ais.trans2.common.model.excel;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 削除伝票リストエクセル
 * 
 * @author AIS
 */
public class DeleteSlipListExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public DeleteSlipListExcel(String lang) {
		super(lang);
	}

	/**
	 * エクセルを返す
	 * 
	 * @param book
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(LedgerBook book) throws TException {
		try {
			createReport(book);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param book
	 */
	public void createReport(LedgerBook book) {

		DeleteSlipListBook listBook = (DeleteSlipListBook) book;

		// 表示区分
		boolean showUserInfo = listBook.isShowUserInfo();

		// シート取得
		TExcelSheet sheet = createSheet(listBook);

		for (LedgerSheet bookSheet : book.getSheet()) {

			for (LedgerSheetDetail detailRow : bookSheet.getDetail()) {

				DeleteSlipListDetail bean = (DeleteSlipListDetail) detailRow;

				TExcelRow newrow = sheet.addRow();
				// 削除伝票No
				newrow.addCell(bean.getDelSlipNo());
				// 削除日
				newrow.addCell(DateUtil.toYMDDate(bean.getDelDate()));

				if (showUserInfo) {

					// ユーザーID
					newrow.addCell(bean.getUsrID());

					// ユーザー名
					newrow.addCell(bean.getUsrName());
				}
			}
		}

	}

	/**
	 * シートを作成する。
	 * 
	 * @param listBook
	 * @return シート
	 */
	private TExcelSheet createSheet(DeleteSlipListBook listBook) {

		// 新規ワークシートを作成する
		// 削除伝票リスト
		TExcelSheet sheetExcel = addSheet(getWord("C01610"));
		// 削除伝票番号
		sheetExcel.addColumn(getWord("C11097"), 9000);
		// 削除日
		sheetExcel.addColumn(getWord("C01613"), 3600);

		if (listBook.isShowUserInfo()) {
			// ユーザーID
			sheetExcel.addColumn(getWord("C10995"), 4000);

			// ユーザー名
			sheetExcel.addColumn(getWord("C11923"), 6000);
		}

		return sheetExcel;

	}

}
