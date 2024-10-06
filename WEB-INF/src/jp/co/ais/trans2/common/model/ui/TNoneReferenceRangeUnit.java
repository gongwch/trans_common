package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 範囲検索 + 個別選択フィールド(ブランク)
 * @author AIS
 *
 */
public class TNoneReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -7792095504739309324L;

	/** 範囲フィールド */
	public TNoneReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TNoneOptionalSelector ctrlNoneSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TNoneReferenceRange();
		ctrlNoneSelector = new TNoneOptionalSelector();
	}

	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlReferenceRange;
	}

	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlNoneSelector;
	}

	@Override
	public String getBorderTitle() {
		return "";
	}

}
