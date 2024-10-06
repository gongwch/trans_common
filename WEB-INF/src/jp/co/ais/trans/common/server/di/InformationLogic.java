package jp.co.ais.trans.common.server.di;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 情報取得ロジック
 */
public interface InformationLogic {

	/**
	 * 会社コード
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode);

	/**
	 * ユーザコード
	 * 
	 * @param userCode ユーザコード
	 */
	public void setUserCode(String userCode);

	/**
	 * 部門単位の組織コードを取得
	 * 
	 * @return 組織コードリスト
	 */
	public String[] getOrganizationCodeList();
	
	/**
	 * 会社単位の組織コードを取得
	 * 
	 * @return 組織コードリスト
	 */
	public String[] getCmpOrganizationCodeList();

	/**
	 * 開示レベル情報を取得する
	 * 
	 * @param kmkCode 科目体系コード
	 * @param orgCode 組織コード
	 * @return 開示レベル
	 * @throws TException
	 */
	public Map getIndicationLevelData(String kmkCode, String orgCode) throws TException;

	/**
	 * 科目統合情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return ItemUnionData 科目統合情報データ
	 */
	public ItemUnionData getItemUnionData(String kaiCode, String kmkCode, String hkmCode, String ukmCode);
	
	/**
	 * 科目情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @return KMK_MST 科目情報
	 */
	public KMK_MST getItemDataBean(String kaiCode, String kmkCode);

	/**
	 * 補助科目情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @return HKM_MST 補助科目情報
	 */
	public HKM_MST getSubItemDataBean(String kaiCode, String kmkCode, String hkmCode);

	/**
	 * 内訳科目情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return UKM_MST 内訳科目情報
	 */
	public UKM_MST getBreakDownItemDataBean(String kaiCode, String kmkCode, String hkmCode, String ukmCode);
	
	/**
	 * 科目情報を取得する
	 * 
	 * @param kmkCode 科目コード
	 * @return 科目情報
	 * @throws TException
	 */
	public Map getItemData(String kmkCode) throws TException;

	/**
	 * 補助科目情報を取得する
	 * 
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @return 補助科目情報
	 * @throws TException
	 */
	public Map getSubItemData(String kmkCode, String hkmCode) throws TException;

	/**
	 * 内訳科目情報を取得する
	 * 
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return 内訳科目情報
	 * @throws TException
	 */
	public Map getBreakDownItemData(String kmkCode, String hkmCode, String ukmCode) throws TException;

	/**
	 * 略称を取得する。<BR>
	 * 
	 * @param strKaiCode 会社コード
	 * @param strDpkSsk 画面入力の組織ｺｰﾄﾞ
	 * @param strDepCode 画面入力の部門ｺｰﾄﾞ
	 * @param intpanelLevel 部門略称を検索時に、画面入力した階層ﾚﾍﾞﾙ。 上位部門略称を検索時に、画面入力した階層レベル-1
	 * @param intkjlLvl 上位部門略称を検索時に、開示レベル（初期化のレベル） 部門略称を検索時に、画面入力した階層レベル-1
	 * @param strkjlDepCode 上位部門略称を検索時に、開示部門ｺｰﾄﾞ（初期化の部門コード）。 部門略称を検索時に、画面入力した上位部門コード
	 * @param strType 組織検索タイプ 部門 or 会社
	 * @return 部門略称
	 */
	public String organizationSearchNameS(String strKaiCode, String strDpkSsk, String strDepCode, Integer intpanelLevel,
			Integer intkjlLvl, String strkjlDepCode, String strType);
	
	/**
	 * 会社マスタデータ取得<BR>
	 * 
	 * @param kaiCode 会社
	 * @return CMP_MST データリスト
	 */
	public CMP_MST getCmpMstDeta(String kaiCode);
	
	/**
	 * 締め制御テーブルデータを検索
	 * 
	 * @param kaiCode
	 * @param year
	 * @param month
	 * @return 締め制御テーブルデータ
	 */
	public SIM_CTL findSimCtl(String kaiCode, int year, int month);
	
	/**
	 * GLコントロール検索
	 * 
	 * @param strKaiCode
	 *            会社コード
	 * @return GLコントロール
	 */
	public GL_CTL_MST findGlCtlMstInfo(String strKaiCode);
	
	/**
	 * 通貨マスタ検索
	 * 
	 * @param strKaiCode
	 *            会社コー
	 * @param strCurCode
	 *            通貨コード
	 * @param slipDate
	 *            伝票日付
	 * @return 通貨マスタリスト
	 */
	public CUR_MST findCurMstInfo(String strKaiCode, String strCurCode,
			Date slipDate);

}
