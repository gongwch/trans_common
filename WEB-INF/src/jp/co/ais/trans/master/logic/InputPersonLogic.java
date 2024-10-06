package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * 入力者マスタビジネスロジック
 */
public interface InputPersonLogic {

	/**
	 * 社員マスタ検索(入力者)
	 * 
	 * @param strKaiCode 会社コー
	 * @param strEmpCode 社員コード
	 * @param strEmpName 社員略称
	 * @param strEmpName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param strDepCodeKbn
	 * @param strDepCode
	 * @return 社員マスタリスト
	 */
	public abstract List searchEmpMstData(String strKaiCode, String strEmpCode, String strEmpName, String strEmpName_K,
		String strSlipDate, String strDepCodeKbn, String strDepCode);
}
