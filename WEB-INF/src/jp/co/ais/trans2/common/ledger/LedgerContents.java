package jp.co.ais.trans2.common.ledger;



/**
 * 帳票目次クラス
 */
public class LedgerContents {

	/** 章タイトル */
	protected String chapTitle = "";

	/** ページ */
	protected int sheetNo = 0;

	
	public String getChapTitle() {
		return chapTitle;
	}

	
	public void setChapTitle(String chapTitle) {
		this.chapTitle = chapTitle;
	}

	
	public int getSheetNo() {
		return sheetNo;
	}

	
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}
}
