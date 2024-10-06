package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * AR仕訳パタンDAO
 */
public interface AR_SWK_PTN_HDRDao {

	/** AR仕訳パタンBeans */
	public Class BEAN = AR_SWK_PTN_HDR.class;

	/**
	 * 全体検索
	 * 
	 * @return AR仕訳パタンリスト
	 */
	public List getAllAR_SWK_PTN_HDR();

	/** getArSwkPtnlInfo SQL */
	public String getArSwkPtnInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * 日付範囲、伝票番号範囲検索
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return AR仕訳パタンリスト
	 */
	public List getArSwkPtnlInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** getAR_SWK_PTN_HDRByKaicodeSwkdendateSwkdennno SQL */
	public String getAR_SWK_PTN_HDRByKaicodeSwkdendateSwkdennno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PKによる検索
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AR_SWK_PTN_HDR
	 */
	public AR_SWK_PTN_HDR getAR_SWK_PTN_HDRByKaicodeSwkdendateSwkdennno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(AR_SWK_PTN_HDR dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(AR_SWK_PTN_HDR dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(AR_SWK_PTN_HDR dto);

	/** updateUnlockShareByUser SQL */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * パターンAR仕訳伝票排他 強制解除 <BR>
	 * ユーザが「編集中」のパターンAR仕訳伝票を「通常」に強制解除する。
	 * 
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** getLockSlip sql */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * 排他伝票リスト取得
	 * 
	 * @param kAI_CODE
	 * @return 排他伝票リスト
	 */
	public List<AR_SWK_PTN_HDR> getLockSlip(String kAI_CODE);

	/**
	 * パターンAR仕訳伝票排他 プログラム指定の強制解除 <BR>
	 * ユーザが「編集中」のパターンAR仕訳伝票を「通常」に強制解除する。
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

	/** deleteByDenNo SQL */
	public static final String deleteByKaiCodeDenNo_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * 指定されたKeyに紐づくデータを全て削除する
	 * 
	 * @param kaiCode 会社コード
	 * @param ptnNo 伝票番号
	 */
	public void deleteByKaiCodeDenNo(String kaiCode, String ptnNo);

	/** パラメータ */
	public String getAR_SWK_PTN_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE,SWK_DEN_NO";

	/**
	 * PK検索
	 * 
	 * @param kaiCode 会社コード
	 * @param denNo 伝票番号
	 * @return ARパターンヘッダー
	 */
	public AR_SWK_PTN_HDR getAR_SWK_PTN_HDRByKaicodeSwkdenno(String kaiCode, String denNo);
}
