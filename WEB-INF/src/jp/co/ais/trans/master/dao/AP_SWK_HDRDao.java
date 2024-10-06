package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * AP仕訳ヘッダDao
 */
public interface AP_SWK_HDRDao {

	/** AP仕訳ヘッダ */
	public Class BEAN = AP_SWK_HDR.class;

	/**
	 * 全体検索
	 * 
	 * @return AP仕訳ヘッダリスト
	 */
	public List getAllAP_SWK_HDR();

	/** getApSwkInfoクエリ */
	public String getApSwkInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO ";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return データリスト
	 */
	public List getApSwkInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** 指定されたデータの取得 */
	public String getAP_SWK_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * PK検索
	 * 
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AP仕訳ヘッダ
	 */
	public AP_SWK_HDR getAP_SWK_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/** getApSwkInfo引数 */
	public String getAP_SWK_HDRBySwkdenno_ARGS = "SWK_DEN_NO";

	/**
	 * @param swkdenNO
	 * @return データリスト
	 */
	public List getAP_SWK_HDRBySwkdenno(String swkdenNO);

	/** getAP_SWK_HDRByKaicodeSwkdenno_ARGS引数 */
	public String getAP_SWK_HDRByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO";

	/**
	 * 伝票用
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @return データ
	 */
	public AP_SWK_HDR getAP_SWK_HDRByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/** getAP_SWK_HDRByKaicodeSwkdennoUsrid_ARGS引数 */
	public String getAP_SWK_HDRByKaicodeSwkdennoUsrid_ARGS = "KAI_CODE, SWK_DEN_NO, USR_ID";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param usrId
	 * @return データ
	 */
	public AP_SWK_HDR getAP_SWK_HDRByKaicodeSwkdennoUsrid(String kaiCode, String swkdenNO, String usrId);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(AP_SWK_HDR dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(AP_SWK_HDR dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(AP_SWK_HDR dto);

	/**
	 * 伝票番号をKeyにして更新区分関連情報を更新
	 * 
	 * @param dto データ
	 */
	public void updateUpdKbn(AP_SWK_HDR dto);

	/** updateUnlockShareByUser sql */
	public String updateUnlockShareByUser_ARGS = "kaiCode, usrId, prgId";

	/**
	 * AP仕訳伝票排他 強制解除 <BR>
	 * ユーザが「編集中」のAP仕訳伝票を「通常」に強制解除する。
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

	/** getLockSlip SQL */
	public static final String getLockSlip_QUERY = "KAI_CODE = ? AND SWK_SHR_KBN =1 ";

	/**
	 * 排他伝票リスト取得
	 * 
	 * @param kAI_CODE
	 * @return 排他伝票リスト
	 */
	public List<AP_SWK_HDR> getLockSlip(String kAI_CODE);

	/**
	 * AP仕訳伝票排他 プログラム指定の強制解除 <BR>
	 * ユーザが「編集中」のAP仕訳伝票を「通常」に強制解除する。
	 */
	public String updateUnlockShareByUsrPrg_ARGS = "kaiCode, usrId, prgId";

	/**
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @param prgId プログラムID
	 */
	public void updateUnlockShareByUsrPrg(String kaiCode, String usrId, String prgId);

	/** データを更新する(AP社員支払) */
	public String updateApEmpPayData_ARGS = "bean";

	/**
	 * データを更新する(AP社員支払)
	 * 
	 * @param bean AP仕訳ｼﾞｬｰﾅﾙBEAN
	 */
	public void updateApEmpPayData(AP_SWK_HDR bean);

	/** データを更新する(AP社員支払) */
	public String updateApEmpPayCancelData_ARGS = "bean";

	/**
	 * データを更新する(AP社員支払)
	 * 
	 * @param bean AP仕訳ｼﾞｬｰﾅﾙBEAN
	 */
	public void updateApEmpPayCancelData(AP_SWK_HDR bean);

	/** 仮払伝票番号を更新する SQL */
	public static final String updateKaridrdenno_SQL = "UPDATE AP_SWK_HDR SET SWK_KARI_DR_DEN_NO = ? WHERE KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * 仮払伝票番号を更新する
	 * 
	 * @param drDenNo 仮払伝票番号
	 * @param kaiCode 会社コード
	 * @param denNo 伝票番号
	 */
	public void updateKaridrdenno(String drDenNo, String kaiCode, String denNo);

	/** ＡＰ仕訳ジャーナルヘッダを更新する(社員支払受付締め) */
	public String updateSwkHdrAcceptClos_ARGS = "bean";

	/**
	 * ＡＰ仕訳ジャーナルヘッダを更新する(社員支払受付締め)
	 * 
	 * @param bean AP仕訳ｼﾞｬｰﾅﾙBEAN
	 */
	public void updateSwkHdrAcceptClos(AP_SWK_HDR bean);
	
	/** deleteByKaicodeDenno SQL */
	public static final String deleteByKaicodeDenno_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * 指定されたKeyに紐づくデータを全て削除する
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 */
	public void deleteByKaicodeDenno(String companyCode, String slipNo);

}
