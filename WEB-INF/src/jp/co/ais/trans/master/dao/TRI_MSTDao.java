package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * 取引先マスタDao
 */
public interface TRI_MSTDao {

	/** beans */
	public Class BEAN = TRI_MST.class;

	/**
	 * 全体検索
	 * 
	 * @return List
	 */
	public List getAllTRI_MST();

	/** パラメータ */
	public String getTriInfo_QUERY = "KAI_CODE = ? AND TRI_CODE BETWEEN ? AND ? ORDER BY TRI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE_FROM
	 * @param TRI_CODE_TO
	 * @return List
	 */
	public List getTriInfo(String KAI_CODE, String TRI_CODE_FROM, String TRI_CODE_TO);

	/** パラメータ */
	public String getTriDateRangeInfo_QUERY = "KAI_CODE = ? AND TRI_CODE = ? AND ? BETWEEN STR_DATE AND END_DATE ORDER BY TRI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param TRI_CODE
	 * @param DATE
	 * @return List
	 */
	public List getTriDateRangeInfo(String KAI_CODE, String TRI_CODE, Date DATE);

	/** パラメータ */
	public String getTRI_MSTByKaicodeTricode_ARGS = "KAI_CODE, TRI_CODE";

	/**
	 * 指定されたデータの取得
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @return TRI_MST
	 */
	public TRI_MST getTRI_MSTByKaicodeTricode(String kaiCode, String triCode);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(TRI_MST dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(TRI_MST dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(TRI_MST dto);

	/** パラメータ */
	public String getAllTRI_MST2_QUERY = "KAI_CODE = ? ORDER BY TRI_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllTRI_MST2(String kaiCode);

	/**
	 * Query属性
	 */
	public String getTRI_MSTByKaiCode_ARGS = "KAI_CODE";

	/**
	 * 会社コードで取引先リストを取得（整列なし）
	 * 
	 * @param KAI_CODE 会社コード
	 * @return 取引先リスト
	 */
	public List<TRI_MST> getTRI_MSTByKaiCode(String KAI_CODE);

	/** パラメータ */
	public String getTriInfoFrom_QUERY = "KAI_CODE = ? AND TRI_CODE >= ? ORDER BY TRI_CODE ";

	/**
	 * @param kaiCode
	 * @param triCodeFrom
	 * @return List
	 */
	public List getTriInfoFrom(String kaiCode, String triCodeFrom);

	/** パラメータ */
	public String getTriInfoTo_QUERY = "KAI_CODE = ? AND TRI_CODE <= ? ORDER BY TRI_CODE ";

	/**
	 * @param kaiCode
	 * @param triCodeTo
	 * @return List
	 */
	public List getTriInfoTo(String kaiCode, String triCodeTo);

	/** REF検索 */
	public String conditionSearch_ARGS = "kaiCode,triCode,triName_S,triName_K,beginCode,endCode, kind";

	/**
	 * REFダイアログ検索
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param triName_S
	 * @param triName_K
	 * @param beginCode
	 * @param endCode
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String triCode, String triName_S, String triName_K, String beginCode,
		String endCode, String kind);

	/** 集計取引先を条件とした検索 */
	public String getTriInfoBySumCodeKaiCode_QUERY = "KAI_CODE = ? AND SUM_CODE = ?";

	/**
	 * @param kaiCode
	 * @param sumCode
	 * @return List<Object>
	 */
	public List<Object> getTriInfoBySumCodeKaiCode(String kaiCode, String sumCode);

	/** REF検索 */
	public String conditionTokuiSearch_ARGS = "kaiCode,tekCode";

	/**
	 * 得意先の場合
	 * 
	 * @param kaiCode
	 * @param tekCode
	 * @return TRI_MST
	 */
	public TRI_MST conditionTokuiSearch(String kaiCode, String tekCode);

	/** パラメータ */
	public String conditionSiireSearch_ARGS = "kaiCode,tekCode";

	/**
	 * 仕入先の場合
	 * 
	 * @param kaiCode
	 * @param tekCode
	 * @return TRI_MST
	 */
	public TRI_MST conditionSiireSearch(String kaiCode, String tekCode);

	/** パラメータ */
	public String conditionToOrSiiSearch_ARGS = "kaiCode,tekCode";

	/**
	 * 得意先＆仕入先の場合
	 * 
	 * @param kaiCode
	 * @param tekCode
	 * @return TRI_MST
	 */
	public TRI_MST conditionToOrSiiSearch(String kaiCode, String tekCode);

	/** 取引先検索の引数 */
	public String searchRefCustomer_ARGS = "kaiCode, triCode, sName, kName, termBasisDate, siire, tokui, beginCode, endCode";

	/**
	 * 取引先検索（ダイアログ用）
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param siire
	 * @param tokui
	 * @param beginCode
	 * @param endCode
	 * @return 取引先リスト
	 */
	public List searchRefCustomer(String kaiCode, String triCode, String sName, String kName, Timestamp termBasisDate,
		boolean siire, boolean tokui, String beginCode, String endCode);

	/** getTRI_MSTByBeginEnd引数 */
	public String getTRI_MSTByBeginEnd_ARGS = "kaiCode, triCode, beginCode, endCode";

	/**
	 * 開始・終了ステータスを基準にしたデータ検索<br>
	 * 開始・終了コードが指定されている場合、その範囲内に該当しない場合はnullが返る.
	 * 
	 * @param kaiCode 会社コード
	 * @param triCode 対象取引先コード
	 * @param beginCode 開始取引先コード
	 * @param endCode 終了取引先コード
	 * @return 取引先データ
	 */
	public TRI_MST getTRI_MSTByBeginEnd(String kaiCode, String triCode, String beginCode, String endCode);

	/** 依頼名称更新パラメータ */
	public final String updateIraiName_ARGS = "dto";

	/**
	 * 依頼名称を更新
	 * 
	 * @param dto
	 */
	public void updateIraiName(TRI_MST dto);
	
	/** 取引先検索の引数 */
	public String getTriMstData_ARGS = "kaiCode, triCode, slipDate";

	/**
	 * 取引先検索
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param slipDate 
	 * @return 取引先リスト
	 */
	public List getTriMstData(String kaiCode, String triCode, Date slipDate);
}
