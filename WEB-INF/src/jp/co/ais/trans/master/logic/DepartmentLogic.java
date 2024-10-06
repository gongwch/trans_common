package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 部門マスタビジネスロジック
 * 
 * @author roh
 */
public interface DepartmentLogic extends CommonLogic {

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	void setCompanyCode(String companyCode);

	/**
	 * 部門マスタ検索
	 * 
	 * @param strKaiCode 会社コード
	 * @param strDepCode 部門コード
	 * @param strDepName 部門略称
	 * @param strDepName_K 部門検索名
	 * @param strSlipDate 伝票日付
	 * @param strDpkSsk 組織コード
	 * @param strBmnKbn 配下部門(0:含む 1:含まない)
	 * @param strUpBmnCode 上位部門ｺｰﾄﾞ
	 * @param strDpkLvl 階層ﾚﾍﾞﾙ
	 * @param strBaseDpkLvl 初期階層ﾚﾍﾞﾙ
	 * @param strBaseBmnCode 初期部門ｺｰﾄﾞ
	 * @return 部門マスタリスト
	 */
	List searchBmnMstData(String strKaiCode, String strDepCode, String strDepName, String strDepName_K,
		String strSlipDate, String strDpkSsk, String strBmnKbn, String strUpBmnCode, String strDpkLvl,
		String strBaseDpkLvl, String strBaseBmnCode);

	/**
	 * ダイアログに表示されるデータの取得
	 * 
	 * @param kaiCode 会社コード
	 * @param depCode 部門コード
	 * @param sName 略称
	 * @param kName 検索略称
	 * @param termBasisDate 有効期間
	 * @param organization 組織
	 * @param level 上位レベル
	 * @param upperDepart 上位部門
	 * @param sumDepart 集計部門
	 * @param inputDepart 入力部門
	 * @param beginCode 開始コード
	 * @param endCode 終了コード
	 * @return 部門情報リスト
	 */
	List<Object> refSearchDepartment(String kaiCode, String depCode, String sName, String kName,
		Timestamp termBasisDate, String organization, int level, String upperDepart, boolean sumDepart,
		boolean inputDepart, String beginCode, String endCode);

	/**
	 * 部門データ検索
	 * 
	 * @param deptCode 部門コード
	 * @param beginCode 開始コード
	 * @param endCode 終了コード
	 * @return 部門データ
	 */
	BMN_MST findDepartment(String deptCode, String beginCode, String endCode);

	/**
	 * 指定部門の直下の部門リストだけを習得する。
	 * 
	 * @param comCode
	 * @param dpkCode
	 * @param DpkLvl
	 * @param inDepCode
	 * @param UpperCode
	 * @return 下位部門リスト
	 */
	List<BMN_MST> getBmnList(String comCode, String dpkCode, int DpkLvl, String inDepCode, String UpperCode);
}
