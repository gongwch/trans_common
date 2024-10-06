package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 輸送実績地域範囲検索フィールド
 * 
 * @author AIS
 */
public class TMlitCountryReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TMlitCountryReference ctrlCountryReferenceFrom;

	/** 終了フィールド */
	public TMlitCountryReference ctrlCountryReferenceTo;

	@Override
	public void initComponents() {
		ctrlCountryReferenceFrom = new TMlitCountryReference();
		ctrlCountryReferenceTo = new TMlitCountryReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlCountryReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlCountryReferenceTo.getSearchCondition().setCountryCodeFrom(ctrlCountryReferenceFrom.getCode());
			}
		});

		ctrlCountryReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlCountryReferenceFrom.getSearchCondition().setCountryCodeTo(ctrlCountryReferenceTo.getCode());
			}
		});
	}

	@Override
	public TReference getFieldFrom() {
		return ctrlCountryReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlCountryReferenceTo;
	}

}
