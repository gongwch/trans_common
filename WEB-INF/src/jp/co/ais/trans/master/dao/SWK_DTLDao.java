package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 仕訳ジャーナルDao
 */
public interface SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_DTL.class;

	/**
	 * 全件データ取得
	 * 
	 * @return データリスト
	 */
	public List getAllSWK_DTL();

	/** getSwkDtlInfo QUERY */
	public String getSwkDtlInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO, SWK_GYO_NO ";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return データリスト
	 */
	public List getSwkDtlInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	/** getSwkDtlInfo QUERY */
	public String getSwkListByKaiCodeAndDenNo_QUERY = "KAI_CODE = ?  AND SWK_DEN_NO = ? ORDER BY SWK_GYO_NO ";

	/**
	 * 指定の伝票番号に当たる全ての仕訳を取得
	 * 
	 * @param KAI_CODE
	 * @param SWK_DEN_NO
	 * @return List<SWK_DTL>
	 */
	public List<SWK_DTL> getSwkListByKaiCodeAndDenNo(String KAI_CODE, String SWK_DEN_NO);

	/** getSWK_DTLByKaicodeSwkdendateSwkdennoSwkGyono引数 */
	public String getSWK_DTLByKaicodeSwkdendateSwkdennoSwkGyono_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO, SWK_GYO_NO";

	/**
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @param swkgyoNO
	 * @return データリスト
	 */
	public List<Object> getSWK_DTLByKaicodeSwkdendateSwkdennoSwkGyono(String kaiCode, Date swkdenDATE, String swkdenNO,
		String swkgyoNO);

	/** getSWK_DTLByKaicodeSwkdennoSwkGyono引数 */
	public String getSWK_DTLByKaicodeSwkdennoSwkGyono_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_GYO_NO";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @param swkgyoNO
	 * @return データ
	 */
	public SWK_DTL getSWK_DTLByKaicodeSwkdennoSwkGyono(String kaiCode, String swkdenNO, String swkgyoNO);

	/** getSwkDtlByKeta引数 */
	public String getSwkDtlByKeta_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_DC_KBN";

	/**
	 * 桁数
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @param dcKbn
	 * @return 桁数
	 */
	public int getSwkDtlByKeta(String kaiCode, String swkdenNO, int dcKbn);

	/** getSwkDtlByKaicodeSwkden引数 */
	public String getSwkDtlByKaicodeSwkden_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_DC_KBN";

	/**
	 * データ取得
	 * 
	 * @param kaiCode
	 * @param swkdenNO
	 * @param dcKbn
	 * @return データ
	 */
	public SWK_DTL getSwkDtlByKaicodeSwkden(String kaiCode, String swkdenNO, int dcKbn);

	/** getAllTukeCompany(2:付替先自動仕訳行) */
	public String getAllTukeCompany_SQL = "SELECT KAI_CODE FROM SWK_DTL WHERE SWK_DEN_NO = ? AND SWK_TUKE_KBN IN (1, 2) GROUP BY KAI_CODE";

	/**
	 * 付替先会社コードを取得する
	 * 
	 * @param slipNo 伝票番号
	 * @return 付替先会社コードリスト
	 */
	public List<SWK_DTL> getAllTukeCompany(String slipNo);

	/** getTukeOriginalCompany */
	public String getTukeOriginalCompany_SQL = "SELECT KAI_CODE FROM SWK_DTL WHERE SWK_DEN_NO = ? AND SWK_TUKE_KBN = 0";

	/**
	 * 指定伝票番号の付替元会社コードを取得する
	 * 
	 * @param slipNo 伝票番号
	 * @return 付替先会社コード
	 */
	public String getTukeOriginalCompany(String slipNo);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(SWK_DTL dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(SWK_DTL dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(SWK_DTL dto);

	/** updateForSeiDenNoDenDate引数 */
	public String updateForSeiDenNoDenDate_ARGS = "kaiCode, denNo, gyoNo, seiDenNo, denDate,prgId,usrId,updDate";

	/**
	 * @param kaiCode
	 * @param denNo
	 * @param gyoNo
	 * @param seiDenNo
	 * @param denDate
	 * @param prgId
	 * @param usrId
	 * @param updDate
	 */
	public void updateForSeiDenNoDenDate(String kaiCode, String denNo, String gyoNo, String seiDenNo, Date denDate,
		String prgId, String usrId, Date updDate);

	/** updateForSeiDenNo引数 */
	public String updateForSeiDenNo_ARGS = "kaiCode, seiDenNo, gyoNo, denDate, prgId, usrId, updDate";

	/**
	 * @param kaiCode
	 * @param seiDenNo
	 * @param gyoNo
	 * @param denDate
	 * @param prgId
	 * @param usrId
	 */
	public void updateForSeiDenNo(String kaiCode, String seiDenNo, String gyoNo, Date denDate, String prgId,
		String usrId);

	/** updateClearanceData引数 */
	public String updateClearanceData_ARGS = "kaiCode, denNo, date, usrId,prgId";

	/**
	 * @param kaiCode
	 * @param denNo
	 * @param date
	 * @param usrId
	 * @param prgId
	 */
	public void updateClearanceData(String kaiCode, String denNo, Date date, String usrId, String prgId);

	/** updateSwkDtlByCoItem引数 */
	public String updateSwkDtlByCoItem_ARGS = "KAI_CODE, SWK_DEN_NO, SWK_DC_KBN, SWK_KMK_CODE, SWK_HKM_CODE, SWK_UKM_CODE, SWK_DEP_CODE";

	/**
	 * 相手科目を更新する
	 * 
	 * @param kaiCode
	 * @param denNo
	 * @param dcKbn
	 * @param strKmkCode
	 * @param strHkmCode
	 * @param strUkmCode
	 * @param strDepCode
	 */
	public void updateSwkDtlByCoItem(String kaiCode, String denNo, int dcKbn, String strKmkCode, String strHkmCode,
		String strUkmCode, String strDepCode);

	/** updateUPD_KBN SQL */
	public static final String updateUpdKbn_SQL = "UPDATE SWK_DTL SET SWK_UPD_KBN = ?, USR_ID = ?, PRG_ID = ? WHERE SWK_DEN_NO = ?   AND SWK_UPD_KBN <> '4'";

	/**
	 * 更新区分変更.<br>
	 * 条件は伝票番号のみ
	 * 
	 * @param swkUpdKbn 更新区分
	 * @param userID ユーザID
	 * @param prgID プログラムID
	 * @param swkDenNo 伝票番号
	 */
	@Deprecated
	public void updateUpdKbn(String swkUpdKbn, String userID, String prgID, String swkDenNo);

	/** updateUPD_KBN SQL */
	public static final String updateKbnDateId_SQL = "UPDATE SWK_DTL SET SWK_UPD_KBN = ?, USR_ID = ?, PRG_ID = ?, UPD_DATE = ? WHERE SWK_DEN_NO = ?   AND SWK_UPD_KBN <> '4'";

	/**
	 * 更新区分変更.<br>
	 * 条件は伝票番号のみ
	 * 
	 * @param swkUpdKbn 更新区分
	 * @param userID ユーザID
	 * @param prgID プログラムID
	 * @param updDate 更新日付
	 * @param swkDenNo 伝票番号
	 */
	public void updateKbnDateId(String swkUpdKbn, String userID, String prgID, Date updDate, String swkDenNo);

	/** deleteByKaicodeDenno SQL */
	public static final String deleteByKaicodeDenno_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * 指定されたKeyに紐づくデータを全て削除する
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 */
	public void deleteByKaicodeDenno(String companyCode, String slipNo);

	/**
	 * 指定の伝票番号を取得
	 */
	public String getSWK_DTLByKaicodeSwkdennoSwkdendate_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ? AND SWK_DEN_DATE = ? ";

	/**
	 * 指定の伝票番号を取得
	 * 
	 * @param kaiCode 会社コード
	 * @param denNo 伝票番号 *
	 * @param denDate 伝票日付
	 * @return SWK_DTLリスタ
	 */
	public List<SWK_DTL> getSWK_DTLByKaicodeSwkdennoSwkdendate(String kaiCode, String denNo, Date denDate);

	/**
	 * 仕訳ジャーナルを更新(AP社員支払)
	 */
	public String updateDetail_ARGS = "bean";

	/**
	 * 仕訳ジャーナルを更新(AP社員支払)
	 * 
	 * @param bean 仕訳ジャーナル
	 */
	public void updateDetail(SWK_DTL bean);

	/** SQL */
	public String getCountByUpdateDivision_ARGS = "slipNo, kbn";

	/** SQL */
	public String getCountByUpdateDivision_SQL = "SELECT COUNT(*) FROM SWK_DTL WHERE SWK_DEN_NO = /*slipNo*/ AND SWK_UPD_KBN IN /*kbn*/(999)";

	/**
	 * 指定更新区分状態にある仕訳の件数を返す.(会社跨ぐ)
	 * 
	 * @param slipNo 伝票番号
	 * @param kbn 対象更新区分
	 * @return 件数
	 */
	public int getCountByUpdateDivision(String slipNo, int... kbn);

	/** SQL */
	public String deleteSwkDtlByDenNo_ARGS = "kaiCode, swkDenNo";

	/**
	 * 不要な仕訳データを削除する
	 * 
	 * @param kaiCode 会社コード
	 * @param swkDenNo 伝票番号
	 * @return 削除行
	 */
	public int deleteSwkDtlByDenNo(String kaiCode, String swkDenNo);
}
