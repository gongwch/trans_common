package jp.co.ais.trans.master.dao;

import java.sql.Timestamp;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 銀行口座マスタDao
 */
public interface AP_CBK_MSTDao {

	/** bean */
	public Class BEAN = AP_CBK_MST.class;

	/**
	 * 全体検索
	 * 
	 * @return 銀行口座リスト
	 */
	public List getAllAP_CBK_MST();

	/** パラメータ */
	public String getApCbkInfo_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE BETWEEN ? AND ? ORDER BY CBK_CBK_CODE ";

	/**
	 * @param KAI_CODE
	 * @param CBK_CBK_CODE_FROM
	 * @param CBK_CBK_CODE_TO
	 * @return 銀行口座
	 */
	public List getApCbkInfo(String KAI_CODE, String CBK_CBK_CODE_FROM, String CBK_CBK_CODE_TO);

	// 指定されたデータの取得
	/** パラメータ */
	public String getAP_CBK_MSTByKaicodecbkcbkcode_ARGS = "KAI_CODE, CBK_CBK_CODE";

	/**
	 * 主キー指定
	 * 
	 * @param kaiCode
	 * @param cbkcbkCode
	 * @return 銀行口座
	 */
	public AP_CBK_MST getAP_CBK_MSTByKaicodecbkcbkcode(String kaiCode, String cbkcbkCode);

	/** パラメータ */
	public String getAP_CBK_MSTByKaicodecbkcbkcodeoutfbkbn_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE = ? AND CBK_OUT_FB_KBN = 1";

	/**
	 * 社外区分を持つ銀行口座取得
	 * 
	 * @param kaiCode
	 * @param cbkcbkCode
	 * @return 銀行口座
	 */
	public AP_CBK_MST getAP_CBK_MSTByKaicodecbkcbkcodeoutfbkbn(String kaiCode, String cbkcbkCode);

	/** パラメータ */
	public String getAP_CBK_MSTfbInfo_ARGS = "kaiCode, cbkcbkCode, fbKbn";

	/**
	 * 社外区分指定
	 * 
	 * @param kaiCode
	 * @param cbkcbkCode
	 * @param fbKbn
	 * @return 銀行口座
	 */
	public AP_CBK_MST getAP_CBK_MSTfbInfo(String kaiCode, String cbkcbkCode, String fbKbn);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(AP_CBK_MST dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(AP_CBK_MST dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(AP_CBK_MST dto);

	// 下記は ISFnet China 追加分

	/** パラメータ */
	public String getAllAP_CBK_MST2_QUERY = "KAI_CODE = ? ORDER BY CBK_CBK_CODE";

	/**
	 * すべてのレコードを取得
	 * 
	 * @param kaiCode
	 * @return 銀行口座
	 */
	public List getAllAP_CBK_MST2(String kaiCode);

	/** パラメータ */
	public String getApCbkInfoFrom_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE >= ? ORDER BY CBK_CBK_CODE ";

	/**
	 * 区間のデータを取得
	 * 
	 * @param kaiCode
	 * @param cbkCbkCodeFrom
	 * @return 銀行口座
	 */
	public List getApCbkInfoFrom(String kaiCode, String cbkCbkCodeFrom);

	/** パラメータ */
	public String getApCbkInfoTo_QUERY = "KAI_CODE = ? AND CBK_CBK_CODE <= ? ORDER BY CBK_CBK_CODE ";

	/**
	 * 区間のデータを取得
	 * 
	 * @param kaiCode
	 * @param cbkCbkCodeTo
	 * @return 銀行口座
	 */
	public List getApCbkInfoTo(String kaiCode, String cbkCbkCodeTo);

	/** パラメータ */
	public String conditionSearch_ARGS = "kaiCode,cbkCode,bnkName,cbkYknNo,beginCode,endCode,word1,word2,word3,word4,wordUnknown, tablespace";

	/**
	 * ＲＥＦ検索
	 * 
	 * @param kaiCode
	 * @param cbkCode
	 * @param bnkName
	 * @param cbkYknNo
	 * @param beginCode
	 * @param endCode
	 * @param word1
	 * @param word2
	 * @param word3
	 * @param word4
	 * @param wordUnknown
	 * @param tablespace
	 * @return 銀行口座
	 */
	public List conditionSearch(String kaiCode, String cbkCode, String bnkName, String cbkYknNo, String beginCode,
		String endCode, String word1, String word2, String word3, String word4, String wordUnknown, String tablespace);

	/** パラメータ */
	public String searchApCbkMstData_ARGS = "kaiCode, code, nameS, nameK,outKbn";

	/**
	 * 社外振出銀行口座一覧を取得
	 * 
	 * @param strKaiCode
	 * @param strBnkCode
	 * @param strBnkName
	 * @param strYnkNo
	 * @param outKbn
	 * @return 銀行口座
	 */
	public List searchApCbkMstData(String strKaiCode, String strBnkCode, String strBnkName, String strYnkNo, int outKbn);

	/** パラメータ */
	public String getCbkName_ARGS = "kaiCode,cbkCode,cbkKbn,tablespace";

	/**
	 * 銀行名、口座番号文字列取得
	 * 
	 * @param kaiCode
	 * @param cbkCode
	 * @param cbkKbn
	 * @param tablespace
	 * @return 銀行口座
	 */
	public AP_CBK_MST getCbkName(String kaiCode, String cbkCode, String cbkKbn, String tablespace);

	// 共通銀行口座コンポーナント用SQL
	/** パラメータ */
	public String searchCommonCbkMstData_ARGS = "kaiCode, code, nameS, nameK,outKbn, empKbn,termBasisDate";

	/**
	 * 銀行口座検索
	 * 
	 * @param strKaiCode
	 * @param strBnkCode
	 * @param strBnkName
	 * @param strYnkNo
	 * @param outKbn
	 * @param empKbn
	 * @param termBasisDate
	 * @return 銀行口座率祖
	 */
	public List searchCommonCbkMstData(String strKaiCode, String strBnkCode, String strBnkName, String strYnkNo,
		boolean outKbn, boolean empKbn, Timestamp termBasisDate);

	/** パラメータ */
	public String getDefaultReceivedAccount_ARGS = "kaiCode, cbkCode";

	/**
	 * デフォルトの支払条件取得
	 * 
	 * @param kaiCode
	 * @param cbkCode
	 * @return 銀行口座
	 */
	public AP_CBK_MST getDefaultReceivedAccount(String kaiCode, String cbkCode);

	/** パラメータ */
	public String getApCbkMst_ARGS = "param";

	/**
	 * パラメータでデータを取得する
	 * 
	 * @param param 銀行口座パラメータ
	 * @return リスト<銀行口座マスタ>
	 */
	public List getApCbkMst(ApCbkMstParameter param);
	
	/** パラメータ */
	public String getCbkMstData_ARGS = "kaiCode, cbkCode, slipDate";

	/**
	 * 銀行口座検索
	 * @param kaiCode 
	 * @param cbkCode 
	 * @param slipDate 
	 * 
	 * @return リスト 銀行口座マスタ
	 */
	public List getCbkMstData(String kaiCode, String cbkCode, Date slipDate);

}
