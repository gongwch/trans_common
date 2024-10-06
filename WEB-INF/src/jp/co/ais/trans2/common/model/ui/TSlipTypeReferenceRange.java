package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;


/**
 * 伝票種別範囲検索フィールド
 * @author AIS
 */
public class TSlipTypeReferenceRange extends TReferenceRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -3516288811418285985L;

	/** 開始フィールド */
	public TSlipTypeReference ctrlSlipTypeReferenceFrom;

	/** 終了フィールド */
	public TSlipTypeReference ctrlSlipTypeReferenceTo;

	@Override
	public void initComponents() {
		ctrlSlipTypeReferenceFrom = new TSlipTypeReference();
		ctrlSlipTypeReferenceTo = new TSlipTypeReference();
	}
	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlSlipTypeReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlSlipTypeReferenceTo.getSearchCondition().setCodeFrom(
					ctrlSlipTypeReferenceFrom.getCode());
			}
		});

		ctrlSlipTypeReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlSlipTypeReferenceFrom.getSearchCondition().setCodeTo(
					ctrlSlipTypeReferenceTo.getCode());
			}
		});

	}

	@Override
	public TSlipTypeReference getFieldFrom() {
		return ctrlSlipTypeReferenceFrom;
	}

	@Override
	public TSlipTypeReference getFieldTo() {
		return ctrlSlipTypeReferenceTo;
	}


}
