package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 摘要範囲検索フィールド
 * 
 * @author AIS
 */
public class TRemarkReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TRemarkReference ctrlRemReferenceFrom;

	/** 終了フィールド */
	public TRemarkReference ctrlRemReferenceTo;

	@Override
	public void initComponents() {
		ctrlRemReferenceFrom = new TRemarkReference();
		ctrlRemReferenceTo = new TRemarkReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlRemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRemReferenceTo.getSearchCondition().setCodeFrom(ctrlRemReferenceFrom.getCode());
			}
		});

		ctrlRemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlRemReferenceFrom.getSearchCondition().setCodeTo(ctrlRemReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlRemReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlRemReferenceTo;
	}

}
