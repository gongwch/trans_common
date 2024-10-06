package jp.co.ais.trans2.common.gui.table;

import java.util.*;

/**
 * チャートのバー(リストデータ)表現I/F
 */
public interface TChartBarListInterface {

	/**
	 * 明細リストの取得
	 * 
	 * @return list 明細リスト
	 */
	public List getDetailList();

	/**
	 * DETAILモードの場合にバーの各明細データ
	 * 
	 * @return list バーの明細リスト
	 */
	public List getBarDetailList();

	/**
	 * DETAILモードの場合にVCC明細データ
	 * 
	 * @return list VCC明細リスト
	 */
	public List getVCCDetailList();
}
