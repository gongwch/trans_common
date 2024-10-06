package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * 伝票をBookにコンバートするビジネスロジック
 * 
 * @author AIS
 */
public abstract class SlipToBookConverter extends TModel {

	/**
	 * SlipをBookに変換する
	 * 
	 * @param slip 伝票
	 * @return SlipBook
	 */
	public SlipBook toBook(Slip slip) {

		try {

			CompanyManager cm = (CompanyManager) getComponent(CompanyManager.class);
			Company company = cm.get(slip.getCompanyCode());

			return toBook(slip, company);

		} catch (TException e) {
			return null;
		}

	}

	/**
	 * SlipをBookに変換する
	 * 
	 * @param slip
	 * @param company
	 * @return SlipBook
	 */
	public SlipBook toBook(Slip slip, Company company) {

		// book生成
		SlipBook book = createSlipBook();
		book.setCompany(company);

		// ヘッダー生成
		SlipBookHeader header = getSlipBookHeader(slip);
		book.addHeader(header);

		// 明細追加
		int rowNo = 1;
		for (SWK_DTL row : slip.getDetails()) {
			SlipBookDetail detail = createSlipBookDetail();
			detail.setSlipDetail(row);
			detail.setRowNo(rowNo);
			book.addDetail(detail);
			rowNo++;
		}

		// フッター(合計金額)生成
		SlipBookFooter footer = getSlipBookFooter(slip);
		book.getLastSheet().setFooter(footer);

		return book;

	}

	/**
	 * SlipBookのインスタンスを生成する。<br>
	 * 各々のSlipBookがあれば当該メソッドをOverrideすること。
	 * 
	 * @return book
	 */
	protected SlipBook createSlipBook() {
		return new SlipBook();
	}

	/**
	 * @return 明細
	 */
	protected SlipBookDetail createSlipBookDetail() {
		return new SlipBookDetail();
	}

	/**
	 * ヘッダーマッピング
	 * 
	 * @param slip
	 * @return SlipBookHeader
	 */
	protected SlipBookHeader getSlipBookHeader(Slip slip) {

		SlipBookHeader header = createSlipBookHeader();

		// タイトル
		header.setTitle(slip.getHeader().getSWK_DEN_SYU_NAME_K());

		// 伝票日付
		header.setSlipDate(slip.getSlipDate());

		// 入力部門
		header.setDepartmentCode(slip.getHeader().getSWK_IRAI_DEP_CODE());
		header.setDepartmentName(slip.getHeader().getSWK_IRAI_DEP_NAME());

		// 入力者コード
		header.setEmployeeCode(slip.getHeader().getSWK_IRAI_EMP_CODE());
		header.setEmployeeName(slip.getHeader().getSWK_IRAI_EMP_NAME());

		// 伝票番号
		header.setSlipNo(slip.getHeader().getSWK_DEN_NO());
		header.setUpdateCount(slip.getHeader().getSWK_UPD_CNT());

		// 証憑番号
		header.setVoucherNo(slip.getHeader().getSWK_SEI_NO());

		// 摘要
		header.setRemarkCode(slip.getHeader().getSWK_TEK_CODE());
		header.setRemark(slip.getHeader().getSWK_TEK());

		// 帳簿
		AccountBook book = slip.getDetails().get(0).getAccountBook();
		header.setAccountBook(book);
		if (AccountBook.BOTH == book) {
			header.setAccountBook(AccountBook.OWN);
		}

		// 決算段階
		header.setSettlementStage(slip.getHeader().getSWK_KSN_KBN());

		return header;

	}

	/**
	 * SlipBookHeaderのインスタンスを生成する。<br>
	 * 各々のSlipBookHeaderがあれば当該メソッドをOverrideすること。
	 * 
	 * @return bookHeader
	 */
	protected SlipBookHeader createSlipBookHeader() {
		return new SlipBookHeader();
	}

	/**
	 * フッターを返す
	 * 
	 * @param slip
	 * @return SlipBookFooter
	 */
	protected SlipBookFooter getSlipBookFooter(Slip slip) {

		SlipBookFooter footer = createSlipBookFooter();

		// 借方合計
		footer.setDebitAmount(slip.getDebitKeyAmount());

		// 貸方合計
		footer.setCreditAmount(slip.getCreditKeyAmount());

		// 外貨全部(フッター定義件数に従う)
		for (int i = 0; i < footer.getMaxForeignCurrencyCount(); i++) {
			Currency foreignCurrency = slip.getForeignCurrencyAt(i);
			if (foreignCurrency != null) {

				footer.setForeignCurrencyCode(i, foreignCurrency.getCode());
				footer.setForeignDecimalPoint(i, foreignCurrency.getDecimalPoint());

				// 借方外貨計
				footer.setForeignDebitAmount(i, slip.getDebitForeignAmountAt(i));

				// 貸方外貨計
				footer.setForeignCreditAmount(i, slip.getCreditForeignAmountAt(i));
			}
		}

		return footer;

	}

	/**
	 * @return フッター
	 */
	protected SlipBookFooter createSlipBookFooter() {
		SlipBookFooter footer = new SlipBookFooter();

		int count = 2;
		try {
			count = Util.avoidNullAsInt(ServerConfig.getProperty("trans.slip.max.foreign.currency.count"));
		} catch (Throwable e) {
			// 処理なし
			count = 2;
		}

		footer.setMaxForeignCurrencyCount(count);

		return footer;
	}
}
