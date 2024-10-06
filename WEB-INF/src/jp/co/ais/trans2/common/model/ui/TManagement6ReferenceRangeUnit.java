package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * 管理6範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TManagement6ReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** 範囲フィールド */
	public TManagement6ReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TManagement6OptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TManagement6ReferenceRange();
		ctrlOptionalSelector = new TManagement6OptionalSelector();
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
