package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 消費税範囲検索フィールド + 個別選択フィールド
 * 
 * @author AIS
 */
public class TTaxReferenceRangeUnit extends TReferenceRangeUnit {

	/** 範囲フィールド */
	public TTaxReferenceRange ctrlTaxReferenceRange;

	/** 消費税個別選択 */
	public TTaxOptionalSelector ctrlTaxOptionalSelector;

	/** */
	public TTaxReferenceRangeUnit() {
		super(false);
	}

	/**
	 * @param border ボーダーを表示する場合true
	 */
	public TTaxReferenceRangeUnit(boolean border) {
		super(border);
	}

	/**
	 * @param border ボーダーを表示する場合true
	 * @param title タイトル表示かどうか
	 */
	public TTaxReferenceRangeUnit(boolean border, boolean title) {
		super(border, title);
	}

	/**
	 * コンポーネントの初期化
	 */
	@Override
	public void initComponents() {
		ctrlTaxReferenceRange = new TTaxReferenceRange();
		ctrlTaxOptionalSelector = new TTaxOptionalSelector();
	}

	/**
	 * 任意選択(個別指定)コンポーネントのgetter
	 * 
	 * @return 任意選択(個別指定)コンポーネント
	 */
	@Override
	public TOptionalSelector getOptionalSelector() {
		return ctrlTaxOptionalSelector;
	}

	/**
	 * 任意選択(個別指定)コンポーネントのgetter
	 * 
	 * @return 任意選択(個別指定)コンポーネント
	 */
	@Override
	public TReferenceRange getReferenceRange() {
		return ctrlTaxReferenceRange;
	}

	/**
	 * 開始フィールドで選択された科目Entityを返す
	 * 
	 * @return 開始フィールドで選択された科目Entity
	 */
	public ConsumptionTax getEntityFrom() {
		return ctrlTaxReferenceRange.getEntityFrom();
	}

	/**
	 * 終了フィールドで選択された科目Entityを返す
	 * 
	 * @return 終了フィールドで選択された科目Entity
	 */
	public ConsumptionTax getEntityTo() {
		return ctrlTaxReferenceRange.getEntityTo();
	}

	/**
	 * ボーダーのタイトルを返す
	 * 
	 * @return ボーターのタイトル
	 */
	@Override
	public String getBorderTitle() {
		// 選択
		return TModelUIUtil.getWord("C00286");
	}

}
