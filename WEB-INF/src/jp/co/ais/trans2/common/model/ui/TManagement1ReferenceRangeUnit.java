package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * 管理1範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TManagement1ReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** 範囲フィールド */
	public TManagement1ReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TManagement1OptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TManagement1ReferenceRange();
		ctrlOptionalSelector = new TManagement1OptionalSelector();
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
