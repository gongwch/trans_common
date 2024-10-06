package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;

/**
 * 取引先範囲検索フィールド
 * @author AIS
 *
 */
public class TCustomerReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -3516288811418285985L;

	/** 開始フィールド */
	public TCustomerReference ctrlCustomerReferenceFrom;

	/** 終了フィールド */
	public TCustomerReference ctrlCustomerReferenceTo;

	@Override
	public void initComponents() {
		ctrlCustomerReferenceFrom = new TCustomerReference();
		ctrlCustomerReferenceTo = new TCustomerReference();
	}
	/**
	 * 初期化
	 */
	protected void init() {

		ctrlCustomerReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCustomerReferenceTo.getSearchCondition().setCodeFrom(
						ctrlCustomerReferenceFrom.getCode());
			}
		});

		ctrlCustomerReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCustomerReferenceFrom.getSearchCondition().setCodeTo(
						ctrlCustomerReferenceTo.getCode());
			}
		});

	}

	@Override
	public TCustomerReference getFieldFrom() {
		return ctrlCustomerReferenceFrom;
	}

	@Override
	public TCustomerReference getFieldTo() {
		return ctrlCustomerReferenceTo;
	}

}
