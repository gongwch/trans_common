package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 排他コントロールDao
 */
public interface HAITA_CTLDao extends Serializable {

	/** Entity定義 */
	public Class BEAN = HAITA_CTL.class;

	/**
	 * 全データ取得
	 * 
	 * @return 全データ
	 */
	public List getAllHAITA_CTL();

	/** getHaitaCtlInfo QUERY */
	public String getHaitaCtlInfo_QUERY = "KAI_CODE = ? AND SHORI_KBN BETWEEN ? AND ? AND TRI_CODE BETWEEN ? AND ? ORDER BY SHORI_KBN, TRI_CODE ";

	/**
	 * データ取得
	 * 
	 * @param KAI_CODE
	 * @param SHORI_KBN_FROM
	 * @param SHORI_KBN_TO
	 * @param TRI_CODE_FROM
	 * @param TRI_CODE_TO
	 * @return リストデータ
	 */
	public List getHaitaCtlInfo(String KAI_CODE, String SHORI_KBN_FROM, String SHORI_KBN_TO, String TRI_CODE_FROM,
		String TRI_CODE_TO);

	/** getHAITA_CTLByKaicodeShorikbnTricode ARGS */
	public String getHAITA_CTLByKaicodeShorikbnTricode_ARGS = "KAI_CODE, SHORI_KBN, TRI_CODE";

	/**
	 * データ取得
	 * 
	 * @param kaiCode
	 * @param shoriKBN
	 * @param triCode
	 * @return Entity
	 */
	public HAITA_CTL getHAITA_CTLByKaicodeShorikbnTricode(String kaiCode, String shoriKBN, String triCode);

	/** getHAITA_CTLByKaicodeShorikbnTricodeGyono ARGS */
	public String getHAITA_CTLByKaicodeShorikbnTricodeGyono_ARGS = "KAI_CODE, SHORI_KBN, TRI_CODE, GYO_NO";

	/**
	 * データ取得
	 * 
	 * @param kaiCode
	 * @param shoriKBN
	 * @param triCode
	 * @param gyoNo
	 * @return Entity
	 */
	public HAITA_CTL getHAITA_CTLByKaicodeShorikbnTricodeGyono(String kaiCode, String shoriKBN, String triCode,
		String gyoNo);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(HAITA_CTL dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(HAITA_CTL dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(HAITA_CTL dto);

	/** deleteLockUserQuery */
	public static final String deleteLockUser_SQL = "DELETE FROM HAITA_CTL WHERE KAI_CODE = ? AND USR_ID = ?";

	/**
	 * ユーザコードで削除(排他解除)<br>
	 * 0件エラー無し
	 * 
	 * @param kaiCode 会社コード
	 * @param userCode ユーザコード
	 * @return 更新件数
	 */
	public int deleteLockUser(String kaiCode, String userCode);

	/** deleteByUsrPrg Query */
	public static final String deleteByUsrPrg_SQL = "DELETE FROM HAITA_CTL WHERE KAI_CODE = ? AND USR_ID = ? AND PRG_ID = ?";

	/**
	 * ユーザコードとプログラムIDで削除(排他解除).<br>
	 * 0件エラー無し
	 * 
	 * @param kaiCode 会社コード
	 * @param userCode ユーザコード
	 * @param prgId プログラムID
	 * @return 更新件数
	 */
	public int deleteByUsrPrg(String kaiCode, String userCode, String prgId);

	/** 指定したユーザーの取引先排他を解除 (0件エラー無し) */
	public static final String deleteByKaicodeShorikbnUsrID_SQL = "DELETE FROM HAITA_CTL WHERE KAI_CODE = ? AND SHORI_KBN = ? AND USR_ID = ?";

	/**
	 * 指定したユーザーの取引先排他を解除(0件エラー無し)
	 * 
	 * @param KaiCode
	 * @param shoriKbn
	 * @param usrId
	 * @return 更新件数
	 */
	public int deleteByKaicodeShorikbnUsrID(String KaiCode, String shoriKbn, String usrId);

}
