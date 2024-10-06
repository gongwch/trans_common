package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 会社範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TCompanyReferenceRangeUnit extends TReferenceRangeUnit {

	/** 範囲フィールド */
	public TCompanyReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TCompanyOptionalSelector ctrlOptionalSelector;

	/**
	 * コンストラクタ.
	 */
	public TCompanyReferenceRangeUnit() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param border
	 */
	public TCompanyReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param border
	 * @param title
	 */
	public TCompanyReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TCompanyReferenceRange();
		ctrlOptionalSelector = new TCompanyOptionalSelector();
	}

	@Override
	public TCompanyReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TCompanyOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// 会社範囲
		return TModelUIUtil.getWord("C11368");
	}
}
