package jp.co.ais.trans2.common.ledger;

import jp.co.ais.trans.common.dt.*;

/**
 * 帳票シートの明細データクラスです。<br>
 * 
 * @author AIS
 */
public class LedgerSheetDetail extends TransferBase {

	/** UID */
	private static final long serialVersionUID = 7025411513860083111L;

	/** 空白行か */
	protected boolean isBlank = false;

	/**
	 * 空白行か
	 * 
	 * @return true:空白
	 */
	public boolean isBlank() {
		return isBlank;
	}

	/**
	 * 空白行か
	 * 
	 * @param isBlank true:空白
	 */
	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}
}
