package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * AR仕訳ヘッダDao
 */
public interface AR_SWK_HDRDao {

	/** Entity */
	public Class BEAN = AR_SWK_HDR.class;

	/**
	 * 全データ取得
	 * 
	 * @return データリスト
	 */
	public List getAllAR_SWK_HDR();

	/** getArSwkInfoクエリ */
	public String getArSwkInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return データリスト
	 */
	public List getArSwkInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** 指定されたデータの取得 */
	public String getAR_SWK_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK検索
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AR仕訳ヘッダ
	 */
	public AR_SWK_HDR getAR_SWK_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/** getAR_SWK_HDRBySwkdenno引数 */
	public String getAR_SWK_HDRBySwkdenno_ARGS = "SWK_DEN_NO";

	/**
	 * @param swkdenNO
	 * @return データリスト
	 */
	public List getAR_SWK_HDRBySwkdenno(String swkdenNO);

	/** getAR_SWK_HDRByKaicodeSwkdenno引数 */
	public String getAR_SWK_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO";

	/**
	 * 伝票用
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @return データリスト
	 */
	public AR_SWK_HDR getAR_SWK_HDRByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/** getAR_SWK_HDRByKaicodeSwkdennoUsrid引数 */
	public String getAR_SWK_HDRByKaicodeSwkdennoUsrid_ARGS = "KAI_CODE, SWK_DEN_NO, USR_ID";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param usrId
	 * @return データ
	 */
	public AR_SWK_HDR getAR_SWK_HDRByKaicodeSwkdennoUsrid(String kaiCode, String swkdenNO, String usrId);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(AR_SWK_HDR dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(AR_SWK_HDR dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(AR_SWK_HDR dto);

	/**
	 * 伝票番号をKeyにして更新区分関連情報を更新
	 * 
	 * @param dto データ
	 */
	public void updateUpdKbn(AR_SWK_HDR dto);

	/** updateUnlockShareByUser 属性文 */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * AR仕訳伝票排他 強制解除 <BR>
	 * ユーザが「編集中」のAR仕訳伝票を「通常」に強制解除する。
	 * 
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** deleteByDenNo SQL */
	public static final String deleteByDenNo_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * 指定されたKeyに紐づくデータを全て削除する
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 */
	public void deleteByDenNo(String companyCode, String slipNo);

	/** getLockSlip sql */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * 排他伝票リスト取得
	 * 
	 * @param kAI_CODE
	 * @return 排他伝票リスト
	 */
	public List<AR_SWK_HDR> getLockSlip(String kAI_CODE);
	
	/**
	 * AR仕訳伝票排他 プログラム指定の強制解除 <BR>
	 * ユーザが「編集中」のAR仕訳伝票を「通常」に強制解除する。
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

}
