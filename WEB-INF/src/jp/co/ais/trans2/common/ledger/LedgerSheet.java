package jp.co.ais.trans2.common.ledger;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;;

/**
 * 帳票シートクラスです。<br>
 * 帳票1ページ分のデータを保持します。<br>
 * 1シートは、帳票ヘッダーデータ、明細リスト、フッターデータから構成されます。<br>
 * @author AIS
 *
 */
public class LedgerSheet implements Serializable {

	private static final long serialVersionUID = 3475428084169032258L;

	// 帳票ヘッダー
	protected LedgerSheetHeader header;

	// 帳票明細
	protected List<LedgerSheetDetail> detail;

	// 帳票フッター
	protected LedgerSheetFooter footer;	

	public LedgerSheet() {
		detail = new ArrayList<LedgerSheetDetail>();
	}

	/**
	 * 帳票ヘッダーgetter
	 * @return
	 */
	public LedgerSheetHeader getHeader() {
		return header;
	}

	/**
	 * 帳票ヘッダーsetter
	 * @param header
	 */
	public void setHeader(LedgerSheetHeader header) {
		this.header = header;
	}

	/**
	 * 帳票明細getter
	 * @return
	 */
	public List<LedgerSheetDetail> getDetail() {
		return detail;
	}

	/**
	 * 帳票明細setter
	 * @param detail
	 */
	public void setDetail(List<LedgerSheetDetail> detail) {
		this.detail = detail;
	}

	/**
	 * 明細行の行数を返す
	 * @return
	 */
	public int getRowCount() {
		return detail.size();
	}

	/**
	 * 明細を追加する
	 * @param detail
	 */
	public void addDetail(LedgerSheetDetail detail) {
		this.detail.add(detail);
	}

	/**
	 * 帳票フッターgetter
	 * @return
	 */
	public LedgerSheetFooter getFooter() {
		return footer;
	}

	/**
	 * 帳票フッターsetter
	 * @param footer
	 */
	public void setFooter(LedgerSheetFooter footer) {
		this.footer = footer;
	}

}
