package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 社員範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TEmployeeReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = 1829071159623026452L;

	/** 範囲フィールド */
	public TEmployeeReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TEmployeeOptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TEmployeeReferenceRange();
		ctrlOptionalSelector = new TEmployeeOptionalSelector();
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
		// 社員範囲
		return TModelUIUtil.getWord("C11131");
	}

}
