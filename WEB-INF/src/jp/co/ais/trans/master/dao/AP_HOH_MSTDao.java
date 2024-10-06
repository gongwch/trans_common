package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.List;

import jp.co.ais.trans.master.entity.*;

public interface AP_HOH_MSTDao {

	public Class BEAN = AP_HOH_MST.class;

	public List getAllAP_HOH_MST();

	public String getApHohInfo_QUERY = "KAI_CODE = ? AND HOH_HOH_CODE BETWEEN ? AND ? ORDER BY HOH_HOH_CODE ";

	public List getApHohInfo(String KAI_CODE, String HOH_HOH_CODE_FROM, String HOH_HOH_CODE_TO);

	// 指定されたデータの取得
	public String getAP_HOH_MSTByKaicodehohhohcode_ARGS = "KAI_CODE, HOH_HOH_CODE";

	public AP_HOH_MST getAP_HOH_MSTByKaicodehohhohcode(String kaiCode, String hohhohCode);

	public String getHohHohName_ARGS = "kaiCode, hohhohCode, kind";

	public AP_HOH_MST getHohHohName(String kaiCode, String hohhohCode, String kind);

	public String searchHohHohMst_ARGS = "kaiCode, hohCode, hohName,hohNameK";

	public List<Object> searchHohHohMst(String kaiCode, String hohCode, String hohName, String hohNameK);

	public void insert(AP_HOH_MST dto);

	public void update(AP_HOH_MST dto);

	public void delete(AP_HOH_MST dto);

	// 下記は ISFnet China 追加分

	public String getAllAP_HOH_MST2_ARGS = "KAI_CODE";

	public List getAllAP_HOH_MST2(String KAI_CODE);

	public String conditionSearch_ARGS = "kaiCode, hohHohCode, hohHohName, hohHohName_K,beginCode,endCode,kind";

	public List conditionSearch(String kaiCode, String hohHohCode, String hohHohName, String hohHohName_K,
		String beginCode, String endCode, String kind);

	public String query_ARGS = "kaiCode,hohHohCodeFrom,hohHohCodeTo,includeEmployeePayment,includeExternalPayment";

	public List query(String kaiCode, String hohHohCodeFrom, String hohHohCodeTo, String includeEmployeePayment,
		String includeExternalPayment);

	// ダイアログ用メソッド
	public String refDialogSearch_ARGS = "kaiCode,hohCode,sName,kName,termBasisDate";

	public List<Object> refDialogSearch(String kaiCode, String hohCode, String sName, String kName,
		Timestamp termBasisDate);

	/** パラメータ */
	public String getApHohMst_ARGS = "param";

	/**
	 * 支払方法マスタデータをパラメータ条件で取得する
	 * 
	 * @param param 支払方法マスタ検索パラメータ
	 * @return リスト<支払方法マスタ>
	 */
	public List getApHohMst(ApHohMstParameter param);

}
