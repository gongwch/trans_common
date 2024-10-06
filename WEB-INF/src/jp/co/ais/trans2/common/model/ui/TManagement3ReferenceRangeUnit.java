package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * 管理3範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TManagement3ReferenceRangeUnit extends TReferenceRangeUnit {

	/** serialVersionUID */
	private static final long serialVersionUID = -1996886242872822247L;

	/** 範囲フィールド */
	public TManagement3ReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TManagement3OptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TManagement3ReferenceRange();
		ctrlOptionalSelector = new TManagement3OptionalSelector();
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
