package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * 部門範囲検索フィールド
 * @author AIS
 *
 */
public class TDepartmentReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = 396055456213700038L;

	/** 開始フィールド */
	public TDepartmentReference ctrlDepartmentReferenceFrom;

	/** 終了フィールド */
	public TDepartmentReference ctrlDepartmentReferenceTo;

	@Override
	public void initComponents() {
		ctrlDepartmentReferenceFrom = new TDepartmentReference();
		ctrlDepartmentReferenceTo = new TDepartmentReference();
	}
	/**
	 * 初期化
	 */
	protected void init() {

		ctrlDepartmentReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlDepartmentReferenceTo.getSearchCondition().setCodeFrom(
						ctrlDepartmentReferenceFrom.getCode());
			}
		});

		ctrlDepartmentReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlDepartmentReferenceFrom.getSearchCondition().setCodeTo(
						ctrlDepartmentReferenceTo.getCode());
			}
		});

	}

	@Override
	public TDepartmentReference getFieldFrom() {
		return ctrlDepartmentReferenceFrom;
	}

	@Override
	public TDepartmentReference getFieldTo() {
		return ctrlDepartmentReferenceTo;
	}

}
