package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 銀行口座範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TBankAccountReferenceRangeUnit extends TReferenceRangeUnit {

	/** 範囲フィールド */
	public TBankAccountReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TBankAccountOptionalSelector ctrlOptionalSelector;

	/**
	 * コンストラクタ
	 */
	public TBankAccountReferenceRangeUnit() {
		super(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param border
	 */
	public TBankAccountReferenceRangeUnit(boolean border) {
		super(border, false);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TBankAccountReferenceRange();
		ctrlOptionalSelector = new TBankAccountOptionalSelector();
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
		// 銀行口座範囲
		return TModelUIUtil.getWord("C11101");
	}

}
