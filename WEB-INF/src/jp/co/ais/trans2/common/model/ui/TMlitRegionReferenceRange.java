package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 輸送実績国範囲検索フィールド
 * 
 * @author AIS
 */
public class TMlitRegionReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TMlitRegionReference ctrlRegionReferenceFrom;

	/** 終了フィールド */
	public TMlitRegionReference ctrlRegionReferenceTo;

	@Override
	public void initComponents() {
		ctrlRegionReferenceFrom = new TMlitRegionReference();
		ctrlRegionReferenceTo = new TMlitRegionReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlRegionReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRegionReferenceTo.getSearchCondition().setRegionCodeFrom(ctrlRegionReferenceFrom.getCode());
			}
		});

		ctrlRegionReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRegionReferenceFrom.getSearchCondition().setRegionCodeTo(ctrlRegionReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlRegionReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlRegionReferenceTo;
	}
}
