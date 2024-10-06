package jp.co.ais.trans2.common.ledger;

import java.io.*;

import jp.co.ais.trans2.common.report.*;

/**
 * 帳票ブック＆レイアウトクラスです。<br>
 * 帳票は、複数ページのシートから構成されます。<br>
 * 1のシートは、ヘッダー、明細、フッターから構成されます。<br>
 * 
 * @author AIS
 */
public class LedgerBookAndLayout implements Serializable {

	/**
	 * book
	 */
	protected LedgerBook book;

	/**
	 * layout
	 */
	protected TReportLayout layout;

	public LedgerBookAndLayout(LedgerBook book, TReportLayout layout) {
		this.book = book;
		this.layout = layout;
	}

	public LedgerBook getBook() {
		return book;
	}

	public void setBook(LedgerBook book) {
		this.book = book;
	}

	public TReportLayout getLayout() {
		return layout;
	}

	public void setLayout(TReportLayout layout) {
		this.layout = layout;
	}

}
