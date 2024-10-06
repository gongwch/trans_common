package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * ユーザー範囲検索フィールド
 * @author AIS
 *
 */
public class TUserReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -6373347408552212634L;

	/** 開始フィールド */
	public TUserReference ctrlUserReferenceFrom;

	/** 終了フィールド */
	public TUserReference ctrlUserReferenceTo;

	@Override
	public void initComponents() {
		ctrlUserReferenceFrom = new TUserReference();
		ctrlUserReferenceTo = new TUserReference();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		ctrlUserReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlUserReferenceTo.getSearchCondition().setCodeFrom(
						ctrlUserReferenceFrom.getCode());
			}
		});

		ctrlUserReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlUserReferenceFrom.getSearchCondition().setCodeTo(
						ctrlUserReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlUserReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlUserReferenceTo;
	}

}
