package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 船範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TVesselReferenceRangeUnit extends TReferenceRangeUnit {

	/** 範囲フィールド */
	public TVesselReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TVesselOptionalSelector ctrlOptionalSelector;

	/**
	 * コンストラクタ.
	 */
	public TVesselReferenceRangeUnit() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param border
	 */
	public TVesselReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param border
	 * @param title
	 */
	public TVesselReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TVesselReferenceRange();
		ctrlOptionalSelector = new TVesselOptionalSelector();

	}

	@Override
	public TVesselReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TVesselOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// 船範囲
		return TModelUIUtil.getWord("C11761"); // 船範囲
	}
}
