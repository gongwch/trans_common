package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * 伝票日付範囲フィールド
 * 
 * @author AIS
 */
public class TSlipDateRange extends TDateRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -5671715054170282481L;

	/** コントローラ */
	protected TSlipDateRangeController controller;

	/**
	 * コンストラクタ.
	 */
	public TSlipDateRange() {

		this(TYPE_YMD);

	}

	/**
	 * コンストラクタ.
	 * 
	 * @param calType 日付表示形式
	 */
	public TSlipDateRange(int calType) {

		super(calType);

		// コントローラ生成
		controller = new TSlipDateRangeController(this);

	}

	/**
	 * コンポーネントを初期化する<BR>
	 * 
	 * @param calType 日付表示形式
	 */
	@Override
	protected void initComponents(int calType) {
		dateFrom = new TSlipDate(calType);
		dateTo = new TSlipDate(calType);
	}

	/**
	 * 年度跨ぎをチェックする
	 * 
	 * @return true:年度を跨いでいる
	 */
	public boolean isOverFiscalYear() {
		return controller.isOverFiscalYear();
	}

	/**
	 * 伝票日付（開始）を取得する
	 * 
	 * @return 伝票日付（開始）
	 */
	@Override
	public TSlipDate getDateFrom() {
		return (TSlipDate) dateFrom;
	}

	/**
	 * 伝票日付（終了）を取得する
	 * 
	 * @return 伝票日付（終了）
	 */
	@Override
	public TSlipDate getDateTo() {
		return (TSlipDate) dateTo;
	}

}
