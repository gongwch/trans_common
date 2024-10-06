package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * 港範囲検索フィールド
 * 
 * @author AIS
 */
public class TPortReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TPortReference ctrlPortReferenceFrom;

	/** 終了フィールド */
	public TPortReference ctrlPortReferenceTo;

	@Override
	public void initComponents() {
		ctrlPortReferenceFrom = new TPortReference();
		ctrlPortReferenceTo = new TPortReference();
	}

	/**
	 * 会社コード設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		ctrlPortReferenceFrom.setCompanyCode(companyCode);
		ctrlPortReferenceTo.setCompanyCode(companyCode);
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlPortReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPortReferenceTo.getSearchCondition().setCodeFrom(ctrlPortReferenceFrom.getCode());
			}
		});

		ctrlPortReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlPortReferenceFrom.getSearchCondition().setCodeTo(ctrlPortReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlPortReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlPortReferenceTo;
	}

}
