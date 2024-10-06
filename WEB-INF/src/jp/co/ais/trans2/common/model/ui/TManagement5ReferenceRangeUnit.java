package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * 管理5範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TManagement5ReferenceRangeUnit extends TReferenceRangeUnit {

	/** 範囲フィールド */
	public TManagement5ReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TManagement5OptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TManagement5ReferenceRange();
		ctrlOptionalSelector = new TManagement5OptionalSelector();
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
		// 選択
		return TLoginInfo.getCompany().getAccountConfig().getItemName() + TModelUIUtil.getWord("C00324");
	}

}
