package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 通貨範囲検索フィールド
 * 
 * @author AIS
 *
 */
public class TCurrencyReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TCurrencyReference ctrlCurrencyReferenceFrom;

	/** 終了フィールド */
	public TCurrencyReference ctrlCurrencyReferenceTo;

	@Override
	public void initComponents() {
		ctrlCurrencyReferenceFrom = new TCurrencyReference();
		ctrlCurrencyReferenceTo = new TCurrencyReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlCurrencyReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCurrencyReferenceTo.getSearchCondition().setCodeFrom(ctrlCurrencyReferenceFrom.getCode());
			}
		});

		ctrlCurrencyReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlCurrencyReferenceFrom.getSearchCondition().setCodeTo(ctrlCurrencyReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlCurrencyReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlCurrencyReferenceTo;
	}

}
