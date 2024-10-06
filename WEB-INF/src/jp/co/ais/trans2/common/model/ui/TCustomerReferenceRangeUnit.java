package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 取引先範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TCustomerReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -7792095504739309324L;

	/** 範囲フィールド */
	public TCustomerReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TCustomerOptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TCustomerReferenceRange();
		ctrlOptionalSelector = new TCustomerOptionalSelector();
	}

	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// 取引先範囲
		return TModelUIUtil.getWord("C10338");
	}

}
