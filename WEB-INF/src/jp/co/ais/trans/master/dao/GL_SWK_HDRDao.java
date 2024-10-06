package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * GL仕訳ヘッダDao
 */
public interface GL_SWK_HDRDao {

	/** Entity */
	public Class BEAN = GL_SWK_HDR.class;

	/**
	 * 全データ取得
	 * 
	 * @return データリスト
	 */
	public List getAllGL_SWK_HDR();

	/** getGlSwkInfo引数 */
	public String getGlSwkInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * 条件による仕訳ヘッダ情報リスト取得
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return 仕訳ヘッダ情報リスト
	 */
	public List getGlSwkInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** 指定されたデータの取得 */
	public String getGL_SWK_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK検索
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return GL仕訳ヘッダ
	 */
	public GL_SWK_HDR getGL_SWK_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/** getGL_SWK_HDRByKaicodeSwkdenno引数 */
	public String getGL_SWK_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO";

	/**
	 * PK検索
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @return データ
	 */
	public GL_SWK_HDR getGL_SWK_HDRByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/** getGL_SWK_HDRBySwkdenno引数 */
	public String getGL_SWK_HDRBySwkdenno_ARGS = "SWK_DEN_NO";

	/**
	 * @param swkdenNO
	 * @return データリスト
	 */
	public List getGL_SWK_HDRBySwkdenno(String swkdenNO);

	/** getGL_SWK_HDRByKaicodeSwkdennoUsrid引数 */
	public String getGL_SWK_HDRByKaicodeSwkdennoUsrid_ARGS = "KAI_CODE, SWK_DEN_NO, USR_ID";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param usrId
	 * @return データ
	 */
	public GL_SWK_HDR getGL_SWK_HDRByKaicodeSwkdennoUsrid(String kaiCode, String swkdenNO, String usrId);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(GL_SWK_HDR dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(GL_SWK_HDR dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(GL_SWK_HDR dto);

	/**
	 * 伝票番号をKeyにして更新区分関連情報を更新
	 * 
	 * @param dto データ
	 */
	public void updateUpdKbn(GL_SWK_HDR dto);

	/** updateUnlockShareByUser */
	public static final String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * GL仕訳伝票排他 強制解除 <BR>
	 * ユーザが「編集中」のGL仕訳伝票を「通常」に強制解除する。
	 * 
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUser(String kaiCode, String usrId, String prgId);

	/** getGL_SWK_HDRByBeforeDenNo */
	public static final String getGL_SWK_HDRByBeforeDenNo_ARGS = "KAI_CODE, SWK_BEFORE_DEN_NO, SWK_DATA_KBN";

	/**
	 * GL仕訳伝票 データ区分と訂正前伝票番号で検索 <BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param beforeDenNo 訂正前伝票番号
	 * @param dataKbn データ区分
	 * @return GL_SWK_HDR
	 */
	public GL_SWK_HDR getGL_SWK_HDRByBeforeDenNo(String kaiCode, String beforeDenNo, String dataKbn);

	/** deleteByKaicodeDenno SQL */
	public static final String deleteByKaicodeDenno_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * 指定されたKeyに紐づくデータを全て削除する
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 */
	public void deleteByKaicodeDenno(String companyCode, String slipNo);

	/** QUERY 更新状態の伝票リスト取得 */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1";

	/**
	 * 更新状態の伝票リスト取得
	 * 
	 * @param kAI_CODE
	 * @return 更新状態の伝票リスト
	 */
	public List<GL_SWK_HDR> getLockSlip(String kAI_CODE);

	/**
	 * GL仕訳伝票排他 プログラム指定の強制解除 <BR>
	 * ユーザが「編集中」のGL仕訳伝票を「通常」に強制解除する。
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

	/** パラメータ */
	public String updateLockShareByUsrUser_ARGS = "kaiCode, denNo, prgId, usrId";

	/**
	 * 排他開始
	 * 
	 * @param kaiCode
	 * @param denNo
	 * @param prgId
	 * @param usrId
	 */
	public void updateLockShareByUsrUser(String kaiCode, String denNo, String prgId, String usrId);

}
