package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;

/**
 * 帳簿管理の伝票
 */
public class SlipBooks implements Serializable, Cloneable {

	/** 伝票 */
	protected Slip slip;

	/**
	 * コンストラクタ.
	 * 
	 * @param slip 元データ(帳簿分けナシの状態)
	 */
	public SlipBooks(Slip slip) {
		this.slip = slip;
	}

	/**
	 * 伝票の種類
	 * 
	 * @return 伝票の種類
	 */
	public SlipKind getSlipKind() {
		return slip.getSlipKind();
	}

	/**
	 * 元の伝票を取得
	 * 
	 * @return 元伝票
	 */
	public Slip getSlip() {
		return slip;
	}

	/**
	 * 自国通貨帳簿
	 * 
	 * @return 伝票
	 */
	public Slip getOwnBookSlip() {
		Slip newSlip = slip.clone();

		List<SWK_DTL> list = newSlip.getDetails();
		for (SWK_DTL dtl : list.toArray(new SWK_DTL[list.size()])) {
			if (dtl.isFunctionalCurrency() || !AccountBook.isOWN(dtl.getSWK_ADJ_KBN())) {
				// 自国じゃない仕訳は除外
				list.remove(dtl);
			}
		}

		return newSlip;
	}

	/**
	 * IFRS帳簿
	 * 
	 * @return 伝票
	 */
	public Slip getIFRSBookSlip() {
		Slip newSlip = slip.clone();

		List<SWK_DTL> list = newSlip.getDetails();
		for (SWK_DTL dtl : list.toArray(new SWK_DTL[list.size()])) {
			if (dtl.isFunctionalCurrency() || !AccountBook.isIFRS(dtl.getSWK_ADJ_KBN())) {
				// IFRSじゃない仕訳は除外
				list.remove(dtl);
			}
		}

		return newSlip;
	}

	/**
	 * 機能通貨帳簿
	 * 
	 * @return 伝票
	 */
	public Slip getIFRSFuncSlip() {
		Slip newSlip = slip.clone();

		List<SWK_DTL> list = newSlip.getDetails();
		for (SWK_DTL dtl : list.toArray(new SWK_DTL[list.size()])) {
			if (!dtl.isFunctionalCurrency()) {
				// 機能通貨仕訳以外は除外
				list.remove(dtl);
			}
		}

		return newSlip;
	}

	/**
	 * 伝票のステータス(更新区分)を返す
	 * 
	 * @return 伝票のステータス(更新区分)
	 */
	public SlipState getSlipState() {
		return getSlip().getHeader().getSlipState();
	}

	@Override
	public SlipBooks clone() {

		try {

			SlipBooks slipBooks = (SlipBooks) super.clone();
			Slip slip1 = this.slip.clone();
			slipBooks.slip = slip1;
			return slipBooks;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

}
