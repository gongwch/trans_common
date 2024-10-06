package jp.co.ais.trans2.common.gui;

/**
 * 固定列TTableの初期処理
 */
public interface TFixedTableInitial {

	/**
	 * スプレッド初期化
	 * 
	 * @param tbl
	 */
	public void initSpread(TTable tbl);

	/**
	 * 固定列数の取得
	 * 
	 * @return 固定列数
	 */
	public int getFrozenCols();

}
