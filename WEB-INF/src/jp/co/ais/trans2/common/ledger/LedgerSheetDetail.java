package jp.co.ais.trans2.common.ledger;

import jp.co.ais.trans.common.dt.*;

/**
 * ���[�V�[�g�̖��׃f�[�^�N���X�ł��B<br>
 * 
 * @author AIS
 */
public class LedgerSheetDetail extends TransferBase {

	/** UID */
	private static final long serialVersionUID = 7025411513860083111L;

	/** �󔒍s�� */
	protected boolean isBlank = false;

	/**
	 * �󔒍s��
	 * 
	 * @return true:��
	 */
	public boolean isBlank() {
		return isBlank;
	}

	/**
	 * �󔒍s��
	 * 
	 * @param isBlank true:��
	 */
	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}
}
