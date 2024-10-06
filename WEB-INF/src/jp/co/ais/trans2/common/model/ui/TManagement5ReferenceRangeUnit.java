package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.TLoginInfo;

/**
 * �Ǘ�5�͈͌��� + �ʑI���t�B�[���h
 * 
 * @author AIS
 */
public class TManagement5ReferenceRangeUnit extends TReferenceRangeUnit {

	/** �͈̓t�B�[���h */
	public TManagement5ReferenceRange ctrlReferenceRange;

	/** �ʑI���t�B�[���h */
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
		// �I��
		return TLoginInfo.getCompany().getAccountConfig().getItemName() + TModelUIUtil.getWord("C00324");
	}

}
