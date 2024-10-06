package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 管理1範囲検索フィールド
 * 
 * @author AIS
 */
public class TManagement1ReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TManagement1Reference ctrlManagement1ReferenceFrom;

	/** 終了フィールド */
	public TManagement1Reference ctrlManagement1ReferenceTo;

	@Override
	public void initComponents() {
		ctrlManagement1ReferenceFrom = new TManagement1Reference();
		ctrlManagement1ReferenceTo = new TManagement1Reference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlManagement1ReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement1ReferenceTo.getSearchCondition().setCodeFrom(ctrlManagement1ReferenceFrom.getCode());
			}
		});

		ctrlManagement1ReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlManagement1ReferenceFrom.getSearchCondition().setCodeTo(ctrlManagement1ReferenceTo.getCode());
			}
		});

	}

	@Override
	public TManagement1Reference getFieldFrom() {
		return ctrlManagement1ReferenceFrom;
	}

	@Override
	public TManagement1Reference getFieldTo() {
		return ctrlManagement1ReferenceTo;
	}

}
