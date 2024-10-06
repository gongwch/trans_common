package jp.co.ais.trans.master.dao;

import jp.co.ais.trans.master.entity.*;
import java.util.*;

/**
 * パターンGL伝票Dao
 */
public interface GL_SWK_PTN_HDRDao {

	/** パターンGL伝票Bean */
	public Class BEAN = GL_SWK_PTN_HDR.class;

	/**
	 * パターンGL伝票全体取得
	 * 
	 * @return パターンGL伝票リスト
	 */
	public List getAllGL_SWK_PTN_HDR();

	/** Query属性文 */
	public String getGlSwkPtnInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * 条件検索
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return パターンGL伝票リスト
	 */
	public List getGlSwkPtnInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** 指定されたデータの取得 */
	public String getGL_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * 指定されたデータの取得
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return パターンGL伝票
	 */
	public GL_SWK_PTN_HDR getGL_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(GL_SWK_PTN_HDR dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(GL_SWK_PTN_HDR dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(GL_SWK_PTN_HDR dto);

	/** Query属性文 */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * パターンGL伝票排他 強制解除 <BR>
	 * ユーザが「編集中」のパターンGL仕訳伝票を「通常」に強制解除する。
	 * 
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** Query属性文 */
	public String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * 排他状態の伝票リスト取得
	 * 
	 * @param kAI_CODE
	 * @return 排他状態の伝票リスト
	 */
	public List<GL_SWK_PTN_HDR> getLockSlip(String kAI_CODE);

	/**
	 * パターンGL仕訳伝票排他 プログラム指定の強制解除 <BR>
	 * ユーザが「編集中」のパターンGL仕訳伝票を「通常」に強制解除する。
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
	public String getGL_SWK_PTN_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE,SWK_DEN_NO";
	
	/**
	 * PK検索
	 * 
	 * @param kaiCode 会社コード
	 * @param denNo 伝票番号
	 * @return ARパターンヘッダー
	 */
	public GL_SWK_PTN_HDR getGL_SWK_PTN_HDRByKaicodeSwkdenno(String kaiCode,String denNo);
}
