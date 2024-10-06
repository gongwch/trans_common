package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * 会社範囲検索フィールド
 * @author AIS
 *
 */
public class TCompanyReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -2285923663736390643L;

	/** 開始フィールド */
	public TCompanyReference ctrlCompanyReferenceFrom;

	/** 終了フィールド */
	public TCompanyReference ctrlCompanyReferenceTo;

	@Override
	public void initComponents() {
		ctrlCompanyReferenceFrom = new TCompanyReference();
		ctrlCompanyReferenceTo = new TCompanyReference();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		ctrlCompanyReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCompanyReferenceTo.getSearchCondition().setCodeFrom(
						ctrlCompanyReferenceFrom.getCode());
			}
		});

		ctrlCompanyReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCompanyReferenceFrom.getSearchCondition().setCodeTo(
						ctrlCompanyReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlCompanyReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlCompanyReferenceTo;
	}

}
