package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 自動仕訳科目範囲検索フィールド
 * @author AIS
 *
 */
public class TAutomaticJournalizingItemReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TAutomaticJournalizingItemReference ctrlReferenceFrom;

	/** 終了フィールド */
	public TAutomaticJournalizingItemReference ctrlReferenceTo;

	@Override
	public void initComponents() {
		ctrlReferenceFrom = new TAutomaticJournalizingItemReference();
		ctrlReferenceTo = new TAutomaticJournalizingItemReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlReferenceTo.getSearchCondition().setCodeFrom(
						ctrlReferenceFrom.getCode());
			}
		});

		ctrlReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlReferenceFrom.getSearchCondition().setCodeTo(
						ctrlReferenceTo.getCode());
			}
		});

	}

	@Override
	public TAutomaticJournalizingItemReference getFieldFrom() {
		return ctrlReferenceFrom;
	}

	@Override
	public TAutomaticJournalizingItemReference getFieldTo() {
		return ctrlReferenceTo;
	}

}
