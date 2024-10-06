package jp.co.ais.trans.common.server.di;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.server.dao.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.dao.CMP_MSTDao;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.entity.CMP_MST;

/**
 * 情報取得ロジック
 */
public class InformationLogicImpl implements InformationLogic {

	/** 開示レベルマスタDAO */
	private IndicationLevelDao levelDao;

	/** 部門階層マスタDAO */
	private DPK_MSTDao dpkMstDao;

	/** 部門マスタDAO */
	private BMN_MSTDao bmnMstDao;

	/** 科目マスタDAO */
	private KMK_MSTDao kmkMstDao;

	/** 補助科目マスタDAO */
	private HKM_MSTDao hkmMstDao;

	/** 内訳科目マスタDAO */
	private UKM_MSTDao ukmMstDao;

	/** 会社コード */
	private String companyCode;

	/** ユーザコード */
	private String userCode;

	/** 会社マスタDao */
	private CMP_MSTDao cmpMstDao;

	/** 決算コントロールマスタDao */
	private GL_CTL_MSTDao glCtlMstDao;

	/** 締め制御テーブルDao */
	private SIM_CTLDao simCtlDao;

	/** 通貨マスタ検索用DAO */
	private CUR_MSTDao curMstDao;

	/** 会社階層マスタ検索用DAO */
	private EVK_MSTDao evkMstDao;
	
	/** 環境設定マスタDao */
	private ENV_MSTDao envMstDao;

	/**
	 * 開示レベルマスタDAO設定
	 * 
	 * @param levelDao
	 */
	public void setIndicationLevelDao(IndicationLevelDao levelDao) {
		this.levelDao = levelDao;
	}

	/**
	 * 部門階層マスタDAOの設定.
	 * 
	 * @param dPK_MSTDao DPK_MSTDao
	 */
	public void setDPK_MSTDao(DPK_MSTDao dPK_MSTDao) {
		this.dpkMstDao = dPK_MSTDao;
	}

	/**
	 * 部門マスタDAOの設定.
	 * 
	 * @param bMN_MSTDao BMN_MSTDao
	 */
	public void setBMN_MSTDao(BMN_MSTDao bMN_MSTDao) {
		this.bmnMstDao = bMN_MSTDao;
	}

	/**
	 * 科目マスタDAO設定
	 * 
	 * @param kmkMstDao
	 */
	public void setKMK_MSTDao(KMK_MSTDao kmkMstDao) {
		this.kmkMstDao = kmkMstDao;
	}

	/**
	 * 補助科目マスタDAO設定
	 * 
	 * @param hkmMstDao
	 */
	public void setHKM_MSTDao(HKM_MSTDao hkmMstDao) {
		this.hkmMstDao = hkmMstDao;
	}

	/**
	 * 内訳科目マスタDAO設定
	 * 
	 * @param ukmMstDao
	 */
	public void setUKM_MSTDao(UKM_MSTDao ukmMstDao) {
		this.ukmMstDao = ukmMstDao;
	}

	/**
	 * 会社コード
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ユーザコード
	 * 
	 * @param userCode ユーザコード
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 会社コントロールマスタマスタDAO設定
	 * 
	 * @param cmpMstDao
	 */
	public void setCMP_MSTDao(CMP_MSTDao cmpMstDao) {
		this.cmpMstDao = cmpMstDao;
	}

	/**
	 * 決算コントロールマスタDaoインスタンスの設定<BR>
	 * diconファイルのpropertyとして定義したGL_CTL_MSTDaoが渡される。
	 * 
	 * @param glCtlMstDao 決算コントロールマスタDao
	 */
	public void setGlCtlMstDao(GL_CTL_MSTDao glCtlMstDao) {
		this.glCtlMstDao = glCtlMstDao;
	}

	/**
	 * 締め制御テーブルDaoインスタンスの設定<BR>
	 * diconファイルのpropertyとして定義したSimCtlDaoが渡される。
	 * 
	 * @param simCtlDao 締め制御テーブルDao
	 */
	public void setSimCtlDao(SIM_CTLDao simCtlDao) {
		this.simCtlDao = simCtlDao;
	}

	/**
	 * 通貨マスタ検索用DAOを設定する。
	 * 
	 * @param curMstDao 通貨マスタ検索用DAO
	 */
	public void setCurMstDao(CUR_MSTDao curMstDao) {
		this.curMstDao = curMstDao;
	}

	/**
	 * 会社階層マスタ検索用DAOを設定する。
	 * 
	 * @param evkMstDao 会社階層マスタ検索用DAO
	 */
	public void setEvkMstDao(EVK_MSTDao evkMstDao) {
		this.evkMstDao = evkMstDao;
	}
	
	/**
	 * 環境設定マスタ検索用DAOを設定する。
	 * 
	 * @param envMstDao 環境設定マスタ検索用DAO
	 */
	public void setEnvMstDao(ENV_MSTDao envMstDao) {
		this.envMstDao = envMstDao;
	}

	/**
	 * 部門単位の組織コードリストを取得
	 * 
	 * @return 組織コードリスト
	 */
	public String[] getOrganizationCodeList() {

		// 部門階層ﾏｽﾀ.組織ｺｰﾄﾞを昇順に検索する
		List<DPK_MST> dpkList = dpkMstDao.getDpkSsk(this.companyCode);

		String[] orgCodes = new String[dpkList.size()];

		for (int i = 0; i < dpkList.size(); i++) {
			orgCodes[i] = dpkList.get(i).getDPK_SSK();
		}

		return orgCodes;
	}

	/**
	 * 会社単位の組織コードリストを取得
	 * 
	 * @return 組織コードリスト
	 */
	public String[] getCmpOrganizationCodeList() {

		// 会社階層ﾏｽﾀ.組織ｺｰﾄﾞを昇順に検索する
		List<EVK_MST> envList = evkMstDao.getDpkSsk(this.companyCode);

		String[] orgCodes = new String[envList.size()];

		for (int i = 0; i < envList.size(); i++) {
			orgCodes[i] = envList.get(i).getDPK_SSK();
		}

		return orgCodes;
	}

	/**
	 * 開示レベル情報を取得する
	 * 
	 * @param kmkCode 科目体系コード
	 * @param orgCode 組織コード
	 * @return 開示レベル
	 * @throws TException
	 */
	public Map getIndicationLevelData(String kmkCode, String orgCode) throws TException {

		IndicationLevel entity = levelDao.getIndicationLevel(companyCode, userCode, kmkCode, orgCode);

		if (entity == null) {
			throw new TException("W00100", "C00057", companyCode, userCode, kmkCode, orgCode);
		}

		return entity.toObjectMap();
	}

	/**
	 * 科目統合情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return ItemUnionData 科目統合情報データ
	 */
	public ItemUnionData getItemUnionData(String kaiCode, String kmkCode, String hkmCode, String ukmCode) {

		ItemUnionData bean = new ItemUnionData();

		bean.setKAI_CODE(kaiCode);
		bean.setKMK_MST(getItemDataBean(kaiCode, kmkCode));
		bean.setHKM_MST(getSubItemDataBean(kaiCode, kmkCode, hkmCode));
		bean.setUKM_MST(getBreakDownItemDataBean(kaiCode, kmkCode, hkmCode, ukmCode));

		return bean;
	}

	/**
	 * 科目情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @return KMK_MST 科目情報
	 */
	public KMK_MST getItemDataBean(String kaiCode, String kmkCode) {

		KMK_MST kmkMst = new KMK_MST();
		kmkMst = this.kmkMstDao.getKMK_MSTByKaicodeKmkcode(kaiCode, kmkCode);

		return kmkMst;
	}

	/**
	 * 補助科目情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @return HKM_MST 補助科目情報
	 */
	public HKM_MST getSubItemDataBean(String kaiCode, String kmkCode, String hkmCode) {

		HKM_MST hkmMst = new HKM_MST();
		hkmMst = this.hkmMstDao.getHKM_MSTByKaicodeKmkcodeHkmcode(kaiCode, kmkCode, hkmCode);

		return hkmMst;
	}

	/**
	 * 内訳科目情報を取得する
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return UKM_MST 内訳科目情報
	 */
	public UKM_MST getBreakDownItemDataBean(String kaiCode, String kmkCode, String hkmCode, String ukmCode) {

		UKM_MST ukmMst = new UKM_MST();
		ukmMst = this.ukmMstDao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, ukmCode);

		return ukmMst;
	}

	/**
	 * 科目情報を取得する
	 * 
	 * @param kmkCode 科目コード
	 * @return 科目情報
	 * @throws TException
	 */
	public Map getItemData(String kmkCode) throws TException {

		KMK_MST kmkMst = this.kmkMstDao.getKMK_MSTByKaicodeKmkcode(companyCode, kmkCode);

		if (kmkMst == null) {
			throw new TException("W00100", "C00077", companyCode, kmkCode);
		}

		return kmkMst.toObjectMap();
	}

	/**
	 * 補助科目情報を取得する
	 * 
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @return 補助科目情報
	 * @throws TException
	 */
	public Map getSubItemData(String kmkCode, String hkmCode) throws TException {

		// KMK_MSTを取得する
		HKM_MST hkmMst = this.hkmMstDao.getHKM_MSTByKaicodeKmkcodeHkmcode(companyCode, kmkCode, hkmCode);

		if (hkmMst == null) {
			throw new TException("W00100", "C00488", companyCode, kmkCode, hkmCode);
		}

		return hkmMst.toObjectMap();
	}

	/**
	 * 内訳科目情報を取得する
	 * 
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return 内訳科目情報
	 * @throws TException
	 */
	public Map getBreakDownItemData(String kmkCode, String hkmCode, String ukmCode) throws TException {

		// KMK_MSTを取得する
		UKM_MST ukmMst = this.ukmMstDao
			.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(companyCode, kmkCode, hkmCode, ukmCode);

		if (ukmMst == null) {
			throw new TException("W00100", "C00025", companyCode, kmkCode, hkmCode, ukmCode);
		}

		return ukmMst.toObjectMap();
	}

	/**
	 * 組織コンポーネントの略称を取得する。<BR>
	 * 
	 * @param strKaiCode 会社コード
	 * @param strDpkSsk 画面入力の組織ｺｰﾄﾞ
	 * @param strDepCode 画面入力の部門ｺｰﾄﾞ
	 * @param intpanelLevel 部門略称を検索時に、画面入力した階層ﾚﾍﾞﾙ。 上位部門略称を検索時に、画面入力した階層レベル-1
	 * @param intkjlLvl 上位部門略称を検索時に、開示レベル（初期化のレベル） 部門略称を検索時に、画面入力した階層レベル-1
	 * @param strkjlDepCode 上位部門略称を検索時に、開示部門ｺｰﾄﾞ（初期化の部門コード）。 部門略称を検索時に、画面入力した上位部門コード
	 * @param strType 組織コンポーネントのタイプ
	 * @return 部門略称
	 */
	public String organizationSearchNameS(String strKaiCode, String strDpkSsk, String strDepCode,
		Integer intpanelLevel, Integer intkjlLvl, String strkjlDepCode, String strType) {

		String name_s = "";
		// 部門の場合
		if (strType.equals("UpDep") || strType.equals("Dep")) {

			name_s = bmnMstDao.getDepNameS(strKaiCode, strDpkSsk, strDepCode, intpanelLevel, intkjlLvl, strkjlDepCode);
			// 会社の場合
		} else if (strType.equals("UpCompany") || strType.equals("Company")) {
			
			name_s = envMstDao.getCmpNameS(strKaiCode, strDpkSsk, strDepCode, intpanelLevel, intkjlLvl, strkjlDepCode);
		}

		return name_s;

	}

	/**
	 * 会社マスタデータ取得
	 */
	public CMP_MST getCmpMstDeta(String kaiCode) {
		return cmpMstDao.getCMP_MST_ByKaicode(kaiCode);
	}

	/**
	 * 締め制御テーブルデータを検索
	 * 
	 * @param kaiCode
	 * @param year
	 * @param month
	 * @return 締め制御テーブルデータ
	 */
	public SIM_CTL findSimCtl(String kaiCode, int year, int month) {

		return simCtlDao.getSimCtlByKaiCodeNendoSimmon(kaiCode, year, month);
	}

	/**
	 * GLコントロール検索
	 * 
	 * @param strKaiCode 会社コード
	 * @return GLコントロール
	 */
	public GL_CTL_MST findGlCtlMstInfo(String strKaiCode) {
		return glCtlMstDao.getGL_CTL_MSTByIKaicode(strKaiCode);
	}

	/**
	 * 通貨マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strCurCode 通貨コード
	 * @param slipDate 伝票日付
	 * @return 通貨マスタリスト
	 */
	public CUR_MST findCurMstInfo(String strKaiCode, String strCurCode, Date slipDate) {
		return curMstDao.searchCurMstInfo(strKaiCode, strCurCode, slipDate);
	}

}
