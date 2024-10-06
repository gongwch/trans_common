package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 管理３範囲検索フィールド
 * 
 * @author AIS
 */
public class TManagement3ReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TManagement3Reference ctrlManagement3ReferenceFrom;

	/** 終了フィールド */
	public TManagement3Reference ctrlManagement3ReferenceTo;

	@Override
	public void initComponents() {
		ctrlManagement3ReferenceFrom = new TManagement3Reference();
		ctrlManagement3ReferenceTo = new TManagement3Reference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlManagement3ReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement3ReferenceTo.getSearchCondition().setCodeFrom(ctrlManagement3ReferenceFrom.getCode());
			}
		});

		ctrlManagement3ReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement3ReferenceFrom.getSearchCondition().setCodeTo(ctrlManagement3ReferenceTo.getCode());
			}
		});

	}

	@Override
	public TManagement3Reference getFieldFrom() {
		return ctrlManagement3ReferenceFrom;
	}

	@Override
	public TManagement3Reference getFieldTo() {
		return ctrlManagement3ReferenceTo;
	}

}
