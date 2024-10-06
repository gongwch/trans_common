package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * Voyage範囲検索フィールド
 * 
 * @author AIS
 */
public class TVoyageReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TVoyageReference ctrlVoyageReferenceFrom;

	/** 終了フィールド */
	public TVoyageReference ctrlVoyageReferenceTo;

	@Override
	public void initComponents() {
		ctrlVoyageReferenceFrom = new TVoyageReference();
		ctrlVoyageReferenceTo = new TVoyageReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlVoyageReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVoyageReferenceTo.getSearchCondition().setCodeFrom(ctrlVoyageReferenceFrom.getCode());
			}
		});

		ctrlVoyageReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVoyageReferenceFrom.getSearchCondition().setCodeTo(ctrlVoyageReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlVoyageReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlVoyageReferenceTo;
	}

}
