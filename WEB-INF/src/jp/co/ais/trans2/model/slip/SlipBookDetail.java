package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans2.common.ledger.LedgerSheetDetail;

/**
 * �`�[���[����
 * @author AIS
 *
 */
public class SlipBookDetail extends LedgerSheetDetail {

	/** ���׍s */
	protected SWK_DTL slipDetail;

	/** �s�ԍ� */
	protected int rowNo = 0;

	public SWK_DTL getSlipDetail() {
		return slipDetail;
	}

	public void setSlipDetail(SWK_DTL slipDetail) {
		this.slipDetail = slipDetail;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

}