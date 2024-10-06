package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ユーザマスタDao
 */
public interface USR_MSTDao extends Serializable {

	/**  */
	public Class BEAN = USR_MST.class;

	/**
	 * @return List
	 */
	public List getAllUSR_MST();

	/**  */
	public String getUsrInfo_QUERY = "KAI_CODE = ? AND USR_CODE BETWEEN ? AND ? ORDER BY USR_CODE ";

	/**
	 * @param KAI_CODE
	 * @param USR_CODE_FROM
	 * @param USR_CODE_TO
	 * @return List
	 */
	public List getUsrInfo(String KAI_CODE, String USR_CODE_FROM, String USR_CODE_TO);

	// 指定されたデータの取得(ユーザ名)
	/**  */
	public String getUSR_MSTByKaicodeUsrcode_ARGS = "KAI_CODE,USR_CODE";

	/**
	 * @param kaiCode
	 * @param usrCode
	 * @return USR_MST
	 */
	public USR_MST getUSR_MSTByKaicodeUsrcode(String kaiCode, String usrCode);

	// 指定されたデータの取得(ユーザ名)
	/**  */
	public String getUSR_MSTForLogin_ARGS = "kaiCode,usrCode,targetDate";

	/**  */
	public String getUSR_MSTForLogin_QUERY = "KAI_CODE=/*kaiCode*/ AND USR_CODE=/*usrCode*/ AND STR_DATE <= /*targetDate*/ AND END_DATE >= /*targetDate*/";

	/**
	 * @param kaiCode
	 * @param usrCode
	 * @param nowDate
	 * @return USR_MST
	 */
	public USR_MST getUSR_MSTForLogin(String kaiCode, String usrCode, Date nowDate);

	/**
	 * @param dto
	 */
	public void insert(USR_MST dto);

	/**
	 * @param dto
	 */
	public void update(USR_MST dto);

	/**
	 * @param dto
	 */
	public void delete(USR_MST dto);

	// 下記は ISFnet China 追加分
	/**  */
	public String getUSR_MSTByKaicode_QUERY = "KAI_CODE = ? ORDER BY USR_CODE ";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getUSR_MSTByKaicode(String kaiCode);

	/**  */
	public String getUsrInfoFrom_QUERY = "KAI_CODE = ? AND USR_CODE >= ? ORDER BY USR_CODE ";

	/**
	 * @param kaiCode
	 * @param usrCodenFrom
	 * @return List
	 */
	public List getUsrInfoFrom(String kaiCode, String usrCodenFrom);

	/**  */
	public String getUsrInfoTo_QUERY = "KAI_CODE = ? AND USR_CODE <= ? ORDER BY USR_CODE ";

	/**
	 * @param kaiCode
	 * @param usrCodeTo
	 * @return List
	 */
	public List getUsrInfoTo(String kaiCode, String usrCodeTo);

	// REF検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,USR_CODE,USR_NAME_S,USR_NAME_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param usrCode
	 * @param usrName_S
	 * @param usrName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String usrCode, String usrName_S, String usrName_K, String beginCode,
		String endCode);

	/** 一覧検索 */
	public String getUsrMstData_ARGS = "kaiCode, beginUsrCode, endUsrCode";

	/**
	 * 一覧検索
	 * 
	 * @param kaiCode 会社
	 * @param beginUsrCode
	 * @param endUsrCode
	 * @return List
	 */
	public List getUsrMstData(String kaiCode, String beginUsrCode, String endUsrCode);

	/** 更新ユーザマスタ */
	public String updateUsrMst_ARGS = "entity, oldKaiCode";

	/**
	 * 更新ユーザマスタ
	 * 
	 * @param entity ユーザマスタ
	 * @param oldKaiCode // old会社コード
	 * @return 更新
	 */
	public int updateUsrMst(USR_MST entity, String oldKaiCode);

	/** REF_Name */
	public String getRefName_ARGS = "kaiCode,usrCode";

	/**
	 * REF_Name
	 * 
	 * @param kaiCode
	 * @param usrCode
	 * @return List
	 */
	public USR_MST getRefName(String kaiCode, String usrCode);

	/**
	 * シフト先社員コードの所属部門コードを取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param empCode 社員コード
	 * @return List リスト
	 */
	public List<USR_MST> getUSR_MSTByKaicodeEmpCode(String kaiCode, String empCode);

	/** シフト先社員コードの所属部門コードを取得する */
	public String getUSR_MSTByKaicodeEmpCode_QUERY = "KAI_CODE = ? AND EMP_CODE = ? ";

}
