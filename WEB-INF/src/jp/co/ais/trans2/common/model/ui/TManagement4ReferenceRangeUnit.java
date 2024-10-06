package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * 管理4範囲検索 + 個別選択フィールド
 * 
 * @author AIS
 */
public class TManagement4ReferenceRangeUnit extends TReferenceRangeUnit {

	/** 範囲フィールド */
	public TManagement4ReferenceRange ctrlReferenceRange;

	/** 個別選択フィールド */
	public TManagement4OptionalSelector ctrlOptionalSelector;

	@Override
	public void initComponents() {
		ctrlReferenceRange = new TManagement4ReferenceRange();
		ctrlOptionalSelector = new TManagement4OptionalSelector();
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
