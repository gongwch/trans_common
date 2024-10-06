package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReferenceRange;
import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.event.TCallBackListener;
import jp.co.ais.trans2.model.tax.*;

/**
 * 消費税範囲検索フィールド
 * @author AIS
 *
 */
public class TTaxReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TTaxReference ctrlTaxReferenceFrom;

	/** 終了フィールド */
	public TTaxReference ctrlTaxReferenceTo;

	@Override
	public void initComponents() {
		ctrlTaxReferenceFrom = new TTaxReference();
		ctrlTaxReferenceTo = new TTaxReference();
	}
	/**
	 * 初期化
	 */
	protected void init() {

		ctrlTaxReferenceFrom.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlTaxReferenceTo.getSearchCondition().setCodeFrom(
						ctrlTaxReferenceFrom.getCode());
			}
		});

		ctrlTaxReferenceTo.addCallBackListener(new TCallBackListener() {
			@Override
			public void after() {
				ctrlTaxReferenceFrom.getSearchCondition().setCodeTo(
						ctrlTaxReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlTaxReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlTaxReferenceTo;
	}

	/**
	 * 開始フィールドで選択された科目Entityを返す
	 * @return 開始フィールドで選択された科目Entity
	 */
	public ConsumptionTax getEntityFrom() {
		return ctrlTaxReferenceFrom.getEntity();
	}

	/**
	 * 終了フィールドで選択された科目Entityを返す
	 * 
	 * @return 終了フィールドで選択された科目Entity
	 */
	public ConsumptionTax getEntityTo() {
		return ctrlTaxReferenceTo.getEntity();
	}

}
