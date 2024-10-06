package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * システム区分範囲検索フィールド
 * 
 * @author AIS
 */
public class TSystemDivisionReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TSystemDivisionReference ctrlSysDivReferenceFrom;

	/** 終了フィールド */
	public TSystemDivisionReference ctrlSysDivReferenceTo;

	@Override
	public void initComponents() {
		ctrlSysDivReferenceFrom = new TSystemDivisionReference();
		ctrlSysDivReferenceTo = new TSystemDivisionReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlSysDivReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSysDivReferenceTo.getSearchCondition().setCodeFrom(ctrlSysDivReferenceFrom.getCode());
			}
		});

		ctrlSysDivReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSysDivReferenceFrom.getSearchCondition().setCodeTo(ctrlSysDivReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlSysDivReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlSysDivReferenceTo;
	}

}
