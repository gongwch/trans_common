package jp.co.ais.trans2.common.ledger;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * 帳票ブッククラスです。<br>
 * 帳票は、複数ページのシートから構成されます。<br>
 * 1のシートは、ヘッダー、明細、フッターから構成されます。<br>
 * 
 * @author AIS
 */
public class LedgerBook implements Serializable {

	/**  */
	private static final long serialVersionUID = 6717516728568642593L;

	/** シート */
	protected List<LedgerSheet> sheet;

	/** 目次 */
	protected List<LedgerContents> contents;

	/** 1シートあたりの表示行数 */
	protected int maxRowCount;

	/** 現在保持している行数 */
	protected int currentRowCount = 0;

	/** デフォルトの1ページあたりの表示行数 */
	public final static int DEFAULT_MAX_ROW_COUNT = 30;

	/** 処理日 */
	protected String currentDate = null;

	/** タイトル */
	protected String title = null;

	/**
	 * コンストラクタ
	 */
	public LedgerBook() {

		maxRowCount = DEFAULT_MAX_ROW_COUNT;

		sheet = new ArrayList<LedgerSheet>();
		contents = new ArrayList<LedgerContents>();

	}

	/**
	 * 処理日を取得します。
	 * 
	 * @param lngCode
	 * @return 処理日
	 */
	public String getCurrentDate(String lngCode) {

		if (currentDate == null) {
			setCurrentDate(lngCode);
		}
		return currentDate.substring(0, 10);

	}

	/**
	 * 処理時間を取得します。
	 * 
	 * @param lngCode
	 * @return 処理時間
	 */
	public String getCurrentTime(String lngCode) {

		if (currentDate == null) {
			setCurrentDate(lngCode);
		}
		return currentDate.substring(11, currentDate.length());

	}

	/**
	 * 出力時のタイムスタンプセット
	 * 
	 * @param lngCode
	 */
	private void setCurrentDate(String lngCode) {
		currentDate = Util.getCurrentDateString(lngCode);
	}

	/**
	 * シートを追加します。
	 * 
	 * @param gedgerSheet シート
	 */
	public void addSheet(LedgerSheet gedgerSheet) {
		this.sheet.add(gedgerSheet);
	}

	/**
	 * 全てのシートを返します。
	 * 
	 * @return List<帳票シートクラス>
	 */
	public List<LedgerSheet> getSheet() {
		return sheet;
	}

	/**
	 * 指定のインデックスのシートを返します。
	 * 
	 * @param index インデックス
	 * @return 帳票シートクラス
	 */
	public LedgerSheet getSheetAt(int index) {
		return sheet.get(index);
	}

	/**
	 * ヘッダーを追加します。
	 * 
	 * @param header
	 */
	public void addHeader(LedgerSheetHeader header) {
		LedgerSheet newSheet = new LedgerSheet();
		newSheet.setHeader(header);
		addSheet(newSheet);
		currentRowCount = 0;
	}

	/**
	 * 明細行を追加します。
	 * 
	 * @param detail
	 */
	public void addDetail(LedgerSheetDetail detail) {

		// 行数無制限の場合は無条件に追加する。
		if (maxRowCount <= 0) {
			getLastSheet().addDetail(detail);
			return;
		}

		// ページの先頭の場合は空白行は追加しない
		if (detail.isBlank()) {
			if (getLastSheet().getRowCount() == maxRowCount || getLastSheet().getRowCount() == 0) {
				return;
			}
		}

		// 現在のシートが最大行に達している場合、ヘッダーをコピーし、次のシートに明細を追加する
		if (getLastSheet().getRowCount() == maxRowCount) {
			addHeader(getLastSheet().getHeader());
			addDetail(detail);
		} else {
			getLastSheet().addDetail(detail);
		}

	}

	/**
	 * 表示行数を指定して明細行を追加します。<br>
	 * ※addDetail(LedgerSheetDetail detail)と併用するとバグの元なので注意
	 * 
	 * @param detail
	 * @param addCount 追加行数
	 */
	public void addDetail(LedgerSheetDetail detail, int addCount) {

		// 行数無制限の場合は無条件に追加する。
		if (maxRowCount <= 0) {
			getLastSheet().addDetail(detail);
			currentRowCount += addCount;
			return;
		}

		// ページの先頭の場合は空白行は追加しない
		if (detail.isBlank()) {
			if (currentRowCount == maxRowCount || currentRowCount == 0) {
				return;
			}
		}

		// 入りきらない場合は新規ページを生成する
		if (currentRowCount != 0 && currentRowCount + addCount > maxRowCount) {
			addHeader(getLastSheet().getHeader());
			addDetail(detail);
			currentRowCount += addCount;
			return;
		}

		// 現在のシートが最大行に達している場合、ヘッダーをコピーし、次のシートに明細を追加する
		if (currentRowCount == maxRowCount) {
			addHeader(getLastSheet().getHeader());
			addDetail(detail);
			currentRowCount += addCount;

		} else {
			getLastSheet().addDetail(detail);
			currentRowCount += addCount;

		}

	}

	/**
	 * 明細行をまとめて追加します。<br>
	 * 追加するページに十分な余白が無かった場合、次のページから追加します。
	 * 
	 * @param detail
	 */
	public void addDetailsWhenNoSpaceThenNewPage(List<? extends LedgerSheetDetail> detail) {

		// 行数無制限の場合は無条件に追加する。
		if (maxRowCount <= 0) {
			for (LedgerSheetDetail bean : detail) {
				addDetail(bean);
			}
			return;
		}

		// 入りきらない場合は新規ページを生成する
		if (getLastSheet().getRowCount() != 0 && getLastSheet().getRowCount() + detail.size() > maxRowCount) {
			addHeader(getLastSheet().getHeader());
		}

		for (LedgerSheetDetail bean : detail) {
			addDetail(bean);
		}

	}

	/**
	 * 1ページあたりの表示行数getter
	 * 
	 * @return Max行
	 */
	public int getMaxRowCount() {
		return maxRowCount;
	}

	/**
	 * 1ページあたりの表示行数setter
	 * 
	 * @param maxRowCount
	 */
	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	/**
	 * @return 帳票シートクラス
	 */
	public LedgerSheet getLastSheet() {
		return sheet.get(sheet.size() - 1);
	}

	/**
	 * 帳票の総頁数を返します。
	 * 
	 * @return 総頁数
	 */
	public int getSheetCount() {
		return sheet.size();
	}

	/**
	 * 目次を追加
	 * 
	 * @param ledgerContents 帳票目次クラス
	 */
	public void addContents(LedgerContents ledgerContents) {

		this.contents.add(ledgerContents);
	}

	/**
	 * 目次を返す
	 * 
	 * @return List<帳票目次クラス>
	 */
	public List<LedgerContents> getContents() {
		return contents;
	}

	/**
	 * タイトルを返す
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * タイトルをセットする
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 要素があるか
	 * 
	 * @return isEmpty
	 */
	public boolean isEmpty() {
		return (sheet == null || sheet.isEmpty());
	}

	/**
	 * 現在の最終シートの最終行を返す
	 * 
	 * @return 現在の最終シートの最終行
	 */
	public LedgerSheetDetail getCurrentDetail() {
		if (isEmpty()) {
			return null;
		}
		LedgerSheet lastSheet = getLastSheet();
		// 現在シートに行が無い場合は前シートの最終行
		if (lastSheet.detail == null || lastSheet.detail.isEmpty()) {
			if (sheet.size() > 1) {
				lastSheet = sheet.get(sheet.size() - 2);
				if (lastSheet.detail == null || lastSheet.detail.isEmpty()) {
					return null;
				}
				return lastSheet.detail.get(lastSheet.detail.size() - 1);
			}
			return null;
		}
		return lastSheet.detail.get(lastSheet.detail.size() - 1);
	}

}
