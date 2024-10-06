package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 輸送実績アイテム範囲検索フィールド
 * 
 * @author AIS
 */
public class TMlitItemReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TMlitItemReference ctrlItemReferenceFrom;

	/** 終了フィールド */
	public TMlitItemReference ctrlItemReferenceTo;

	@Override
	public void initComponents() {
		ctrlItemReferenceFrom = new TMlitItemReference();
		ctrlItemReferenceTo = new TMlitItemReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemReferenceTo.getSearchCondition().setItemCodeFrom(ctrlItemReferenceFrom.getCode());
			}
		});

		ctrlItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemReferenceFrom.getSearchCondition().setItemCodeTo(ctrlItemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlItemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlItemReferenceTo;
	}

}
