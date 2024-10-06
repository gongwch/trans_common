package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 送金目的範囲検索フィールド
 * 
 * @author AIS
 */
public class TRemittanceReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TRemittanceReference ctrlReferenceFrom;

	/** 終了フィールド */
	public TRemittanceReference ctrlReferenceTo;

	@Override
	public void initComponents() {
		ctrlReferenceFrom = new TRemittanceReference();
		ctrlReferenceTo = new TRemittanceReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlReferenceTo.getSearchCondition().setCodeFrom(ctrlReferenceFrom.getCode());
			}
		});

		ctrlReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlReferenceFrom.getSearchCondition().setCodeTo(ctrlReferenceTo.getCode());
			}
		});
	}

	@Override
	public TRemittanceReference getFieldFrom() {
		return ctrlReferenceFrom;
	}

	@Override
	public TRemittanceReference getFieldTo() {
		return ctrlReferenceTo;
	}
}
