package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 入力者マスタビジネスロジック実装
 */
public class InputPersonLogicImpl implements InputPersonLogic {

	/** 社員マスタ一覧 */
	private EMP_MSTDao empMstDao = null;

	/**
	 * 社員マスタ一覧設定します。
	 * 
	 * @param empMstDao 社員マスタ一覧
	 */
	public void setEmpMstDao(EMP_MSTDao empMstDao) {
		this.empMstDao = empMstDao;
	}

	/**
	 * 社員マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strEmpCode 社員コード
	 * @param strEmpName 社員略称
	 * @param strEmpName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @return 社員マスタリスト
	 */
	public List searchEmpMstData(String strKaiCode, String strEmpCode, String strEmpName, String strEmpName_K,
		String strSlipDate, String strDepCodeKbn, String strDepCode) {
		if ("1".equals(strDepCodeKbn)) {
			// データを取得する。
			List lstResult = empMstDao.searchEmpMstDataByUser(strKaiCode, strEmpCode, strEmpName, strEmpName_K,
				strDepCode);
			return lstResult;
		}
		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}
		// データを取得する。
		List lstResult = empMstDao.searchEmpMstData(strKaiCode, strEmpCode, strEmpName, strEmpName_K, slipDate, "", "");

		return lstResult;
	}
}
