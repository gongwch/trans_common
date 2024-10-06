package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 部門範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TDepartmentReferenceRangeUnit extends TReferenceRangeUnit {

	/** 範囲フィールド */
	public TDepartmentReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TDepartmentOptionalSelector ctrlOptionalSelector;

	/**
	 * コンストラクタ.
	 */
	public TDepartmentReferenceRangeUnit() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param border
	 */
	public TDepartmentReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param border
	 * @param title
	 */
	public TDepartmentReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TDepartmentReferenceRange();
		ctrlOptionalSelector = new TDepartmentOptionalSelector();
	}

	@Override
	public TDepartmentReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TDepartmentOptionalSelector getOptionalSelector() {
		return ctrlOptionalSelector;
	}

	@Override
	public String getBorderTitle() {
		// 部門範囲
		return TModelUIUtil.getWord("C11128");
	}
}
