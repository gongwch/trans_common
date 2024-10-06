package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * 科目体系マスタ
 */
public interface KmkTkMstLogic extends CommonLogic {

	/**
	 * 科目体系マスタ検索
	 * 
	 * @param strKaiCode 会社コード
	 * @param strKmtCode 科目体系コード
	 * @param strKmtName 科目体系略称
	 * @param strKmtName_K 科目体系検索名
	 * @param args その他パラメータ
	 * @return 科目体系マスタリスト
	 */
	public List searchKmtMstData(String strKaiCode, String strKmtCode, String strKmtName, String strKmtName_K,
		String... args);

	/**
	 * 基本科目体系を取得する<BR>
	 * 基本科目体系を取得する
	 * 
	 * @param conditionMap パラメーター
	 * @return Map
	 */
	public Map getItemSystemValue(Map conditionMap);

}
