package jp.co.ais.trans2.common.model.report;

import java.util.*;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.report.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.report.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 削除伝票リストレイアウト
 * 
 * @author AIS
 */
public class DeleteSlipListLayout extends TReportLayout {

	/** 日付 */
	protected String date;

	/** 削除伝票NO */
	protected static double NO_WIDTH = 4.5d;

	/** 削除日 */
	protected static double DATE_WIDTH = 3.9d;

	/** 削除ユーザー表示の場合、削除伝票NO */
	protected static double NO_USR_WIDTH = 2.5d;

	/** 削除ユーザー表示の場合、削除日 */
	protected static double DATE_USR_WIDTH = 2.0d;

	/** 削除ユーザー */
	protected static double USR_WIDTH = 3.9d;

	/**
	 * 削除伝票リストデータレイアウト
	 * 
	 * @param lang 言語コード
	 * @throws TException
	 */
	public DeleteSlipListLayout(String lang) throws TException {
		super(lang, ReportLayout.A4_PORTRAIT);
	}

	@Override
	protected void initFrameSize() {
		setFrameSize(17.0, 27.7);
		setFrameLocation(2.0, 1.0);
	}

	@Override
	protected void initWord() {

		SlipCondition condition = getBook().getCondition();
		// 伝票日付
		date = "( " + getWord("C00599") + "： " + DateUtil.toYMDString(condition.getSlipDateFrom()) + " ～ "
			+ DateUtil.toYMDString(condition.getSlipDateTo()) + " )";
	}

	protected DeleteSlipListBook getBook() {
		return (DeleteSlipListBook) book;
	}

	/**
	 * ヘッダーの描画
	 * 
	 * @param header ヘッダー
	 * @throws TException
	 */
	@Override
	protected void drawHeader(@SuppressWarnings("unused") LedgerSheetHeader header) throws TException {

		SlipCondition condition = getBook().getCondition();

		boolean showUserInfo = getBook().isShowUserInfo();
		// タイトル
		// 削除伝票リスト
		drawer.drawText(titleStyle, getWord("C01610"), 0.0, 0.0);

		// 会社名
		drawer.drawText(leftStyle, condition.getCompany().getName(), 0.0, 0.0);

		// 対象年月
		drawer.drawText(centerStyle, date, 0.0, 1.0);

		// 出力情報
		drawOutputInformation(drawer, 0.0, 0.40);
		// 明細のヘッダータイトル
		LedgerCellTable cellTable = new LedgerCellTable();

		if (!showUserInfo) {
			// 削除伝票番号
			cellTable.addCell(leftStyle, NO_WIDTH, getWord("C11097"));
			// 削除日
			cellTable.addCell(leftStyle, DATE_WIDTH, getWord("C01613"));
			// 削除伝票番号
			cellTable.addCell(leftStyle, NO_WIDTH, getWord("C11097"));
			// 削除日
			cellTable.addCell(leftStyle, DATE_WIDTH, getWord("C01613"));

		} else {
			// 削除伝票番号
			cellTable.addCell(leftStyle, NO_USR_WIDTH, getWord("C11097"));
			// 削除日
			cellTable.addCell(leftStyle, DATE_USR_WIDTH, getWord("C01613"));
			// 削除ユーザー
			cellTable.addCell(leftStyle, USR_WIDTH, getWord("C11924"));
			// 削除伝票番号
			cellTable.addCell(leftStyle, NO_USR_WIDTH, getWord("C11097"));
			// 削除日
			cellTable.addCell(leftStyle, DATE_USR_WIDTH, getWord("C01613"));
			// 削除ユーザー
			cellTable.addCell(leftStyle, USR_WIDTH, getWord("C11924"));

		}

		drawer.drawText(cellTable, new JCUnit.Point(JCUnit.CM, 0.0, 2.00));
		drawer.drawLine(titleLineStyle, 0.0, 2.40, drawer.getLedgerDoubleWidth(), 2.40);

	}

	/**
	 * 明細の描画
	 * 
	 * @param list
	 */
	@Override
	protected void drawDetail(List<LedgerSheetDetail> list) throws TException {

		double y = 2.40;

		int maxRowNo = getBook().getMaxRowCount() / 2;

		boolean showUserInfo = getBook().isShowUserInfo();

		if (!showUserInfo) {
			for (int i = 0; i < list.size(); i++) {

				DeleteSlipListDetail detail = (DeleteSlipListDetail) list.get(i);

				LedgerCellTable cellTable = new LedgerCellTable();

				if (i < maxRowNo) {
					cellTable.addCell(leftStyle, NO_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
					cellTable.addCell(leftStyle, NO_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_WIDTH, "");

					drawer.drawLine(brokenLineStyle, 0.0, y + 0.5, drawer.getLedgerDoubleWidth(), y + 0.5);

				} else {
					cellTable.addCell(leftStyle, NO_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_WIDTH, "");
					cellTable.addCell(leftStyle, NO_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
				}

				drawer.drawText(cellTable, 0.0, y + 0.10);

				y = y + 0.5;

				if (i == maxRowNo - 1) {
					y = 2.40;
				}

			}

		} else {
			for (int i = 0; i < list.size(); i++) {

				DeleteSlipListDetail detail = (DeleteSlipListDetail) list.get(i);

				LedgerCellTable cellTable = new LedgerCellTable();

				if (i < maxRowNo) {
					cellTable.addCell(leftStyle, NO_USR_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
					cellTable.addCell(leftStyle, USR_WIDTH,
						StringUtil.leftBX(detail.getUsrID() + " " + detail.getUsrName(), 20));
					cellTable.addCell(leftStyle, NO_USR_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, "");
					cellTable.addCell(leftStyle, USR_WIDTH, "");

					drawer.drawLine(brokenLineStyle, 0.0, y + 0.5, drawer.getLedgerDoubleWidth(), y + 0.5);

				} else {
					cellTable.addCell(leftStyle, NO_USR_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, "");
					cellTable.addCell(leftStyle, USR_WIDTH, "");
					cellTable.addCell(leftStyle, NO_USR_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
					cellTable.addCell(leftStyle, USR_WIDTH,
						StringUtil.leftBX(detail.getUsrID() + " " + detail.getUsrName(), 20));
				}

				drawer.drawText(cellTable, 0.0, y + 0.10);

				y = y + 0.5;

				if (i == maxRowNo - 1) {
					y = 2.40;
				}

			}
		}

	}

}