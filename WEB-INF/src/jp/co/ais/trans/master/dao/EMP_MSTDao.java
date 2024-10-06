package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface EMP_MSTDao {

	/**  */
	public Class BEAN = EMP_MST.class;

	/**
	 * @return List
	 */
	public List getAllEMP_MST();

	/**  */
	public String getEmpInfo_QUERY = "KAI_CODE = ? AND EMP_CODE BETWEEN ? AND ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param EMP_CODE_FROM
	 * @param EMP_CODE_TO
	 * @return List
	 */
	public List getEmpInfo(String KAI_CODE, String EMP_CODE_FROM, String EMP_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getEMP_MSTByKaicodeEmpcode_ARGS = "KAI_CODE,EMP_CODE";

	/**
	 * @param kaiCode
	 * @param empCode
	 * @return EMP_MST
	 */
	public EMP_MST getEMP_MSTByKaicodeEmpcode(String kaiCode, String empCode);

	/**
	 * @param dto
	 */
	public void insert(EMP_MST dto);

	/**
	 * @param dto
	 */
	public void update(EMP_MST dto);

	/**
	 * @param dto
	 */
	public void delete(EMP_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllEMP_MST2_QUERY = "KAI_CODE = ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getAllEMP_MST2(String KAI_CODE);

	/**  */
	public String getEmpInfoFrom_QUERY = "KAI_CODE = ? AND EMP_CODE >= ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param EMP_CODE_FROM
	 * @return List
	 */
	public List getEmpInfoFrom(String KAI_CODE, String EMP_CODE_FROM);

	/**  */
	public String getEmpInfoTo_QUERY = "KAI_CODE = ? AND EMP_CODE <= ? ORDER BY EMP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param EMP_CODE_TO
	 * @return List
	 */
	public List getEmpInfoTo(String KAI_CODE, String EMP_CODE_TO);

	// 条件検索
	/**  */
	public String conditionSearch_ARGS = "kaiCode,empCode,empName_S,empName_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param empCode
	 * @param empName_S
	 * @param empName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String empCode, String empName_S, String empName_K, String beginCode,
		String endCode);

	/** パラメーター */
	public String searchEmpNamesByUser_ARGS = "kaiCode, empCode, depCode";

	/**
	 * 社員マスタデータの社員略称を検索<BR>
	 * 社員マスタデータの社員略称を検索
	 * 
	 * @param kaiCode 会社コード
	 * @param empCode 社員コード
	 * @param depCode 所属部門コード
	 * @return 社員マスタヘッダーデータ
	 */
	public EMP_MST searchEmpNamesByUser(String kaiCode, String empCode, String depCode);

	/** 引数 */
	public String searchEmpMstData_ARGS = "kaiCode,empCode,empName,empName_K,slipDate,startCode,endCode";

	/**
	 * 社員マスタ検索
	 * 
	 * @param kaiCode 会社コー
	 * @param empCode 社員コード
	 * @param empName 社員略称
	 * @param empName_K 社員検索名
	 * @param slipDate 伝票日付
	 * @param startCode 開始コード
	 * @param endCode 終了コード
	 * @return 社員マスタリスト
	 */
	public List searchEmpMstData(String kaiCode, String empCode, String empName, String empName_K, Date slipDate,
		String startCode, String endCode);

	/** 引数 */
	public String searchEmpMstDataByUser_ARGS = "kaiCode,empCode,empName,empName_K,depCode";

	/**
	 * 社員マスタ検索
	 * 
	 * @param kaiCode 会社コー
	 * @param empCode 社員コード
	 * @param empName 社員略称
	 * @param empName_K 社員検索名
	 * @param depCode 所属部門コード
	 * @return 社員マスタリスト
	 */
	public List searchEmpMstDataByUser(String kaiCode, String empCode, String empName, String empName_K, String depCode);

	/** 引数 */
	public String refDialogSearch_ARGS = "kaiCode,empCode,sName,kName,termBasisDate,user,depCode,beginCode,endCode";

	/**
	 * ダイアログ社員情報検索
	 * 
	 * @param kaiCode
	 * @param empCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param user
	 * @param depCode
	 * @param beginCode
	 * @param endCode
	 * @return 社員マスタリスト
	 */
	public List<Object> refDialogSearch(String kaiCode, String empCode, String sName, String kName,
		Timestamp termBasisDate, String user, String depCode, String beginCode, String endCode);
	
	/** 引数 */
	public String getEmpMstData_ARGS = "kaiCode,empCode,slipDate";

	/**
	 * 社員マスタ検索
	 * 
	 * @param kaiCode 会社コード
	 * @param empCode 社員コード
	 * @param slipDate 伝票日付
	 * @return 社員マスタリスト
	 */
	public List getEmpMstData(String kaiCode, String empCode, Date slipDate);

}
