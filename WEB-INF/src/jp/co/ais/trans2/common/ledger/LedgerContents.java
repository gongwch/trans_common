package jp.co.ais.trans2.common.ledger;



/**
 * ���[�ڎ��N���X
 */
public class LedgerContents {

	/** �̓^�C�g�� */
	protected String chapTitle = "";

	/** �y�[�W */
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
