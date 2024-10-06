package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * 社員範囲検索フィールド
 * 
 * @author AIS
 */
public class TEmployeeReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TEmployeeReference ctrlEmployeeReferenceFrom;

	/** 終了フィールド */
	public TEmployeeReference ctrlEmployeeReferenceTo;

	@Override
	public void initComponents() {
		ctrlEmployeeReferenceFrom = new TEmployeeReference();
		ctrlEmployeeReferenceTo = new TEmployeeReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlEmployeeReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlEmployeeReferenceTo.getSearchCondition().setCodeFrom(ctrlEmployeeReferenceFrom.getCode());
			}
		});

		ctrlEmployeeReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlEmployeeReferenceFrom.getSearchCondition().setCodeTo(ctrlEmployeeReferenceTo.getCode());
			}
		});

	}

	@Override
	public TEmployeeReference getFieldFrom() {
		return ctrlEmployeeReferenceFrom;
	}

	@Override
	public TEmployeeReference getFieldTo() {
		return ctrlEmployeeReferenceTo;
	}

}
