package jp.co.ais.trans2.common.model.ui.payment;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 支払方法範囲検索フィールド
 * 
 * @author AIS
 */
public class TPaymentMethodReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TPaymentMethodReference ctrlPayReferenceFrom;

	/** 終了フィールド */
	public TPaymentMethodReference ctrlPayReferenceTo;

	@Override
	public void initComponents() {
		ctrlPayReferenceFrom = new TPaymentMethodReference();
		ctrlPayReferenceTo = new TPaymentMethodReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlPayReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPayReferenceTo.getSearchCondition().setCodeFrom(ctrlPayReferenceFrom.getCode());
			}
		});

		ctrlPayReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPayReferenceFrom.getSearchCondition().setCodeTo(ctrlPayReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlPayReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlPayReferenceTo;
	}

}
