package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import org.seasar.framework.container.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ユーザ認証ビジネスロジック
 * 
 * @author AIS
 */
public class UserManagerImpl implements UserManager {

	/** コンテナ */
	protected S2Container container;

	/** ユーザマスタDao */
	protected USR_MSTDao usrDao;

	/** 環境マスタDao */
	protected ENV_MSTDao envDao;

	/** 部門マスタDao */
	protected BMN_MSTDao bmnDao;

	/** 会社コントロールDao */
	protected CMP_MSTDao cmpDao;

	/** PCIチェックコントロールDao */
	protected PCI_CHK_CTLDao pciDao;

	/** 通貨マスタDAO */
	protected CUR_MSTDao curDao;

	/** 社員マスタDAO */
	protected EMP_MSTDao empDao;

	/** 会社コード */
	protected String companyCode;

	/** ユーザコード */
	protected String userCode;

	/** エラーメッセージ */
	protected String errorMsg;

	/** ユーザマスタ */
	protected USR_MST usr;

	/** 部門マスタ */
	private BMN_MST bmn;

	/** 社員マスタ */
	private EMP_MST emp;

	/** 環境設定マスタ */
	protected ENV_MST env;

	/** 会社コントロールマスタ */
	protected CMP_MST cmp;

	/**
	 * コンストラクタ
	 * 
	 * @param container コンテナ
	 */
	public UserManagerImpl(S2Container container) {
		this.container = container;
	}

	/**
	 * @param bmnDao
	 */
	public void setBmnDao(BMN_MSTDao bmnDao) {
		this.bmnDao = bmnDao;
	}

	/**
	 * @param cmpDao
	 */
	public void setCmpDao(CMP_MSTDao cmpDao) {
		this.cmpDao = cmpDao;
	}

	/**
	 * @param curDao
	 */
	public void setCurDao(CUR_MSTDao curDao) {
		this.curDao = curDao;
	}

	/**
	 * @param envDao
	 */
	public void setEnvDao(ENV_MSTDao envDao) {
		this.envDao = envDao;
	}

	/**
	 * @param pciDao
	 */
	public void setPciDao(PCI_CHK_CTLDao pciDao) {
		this.pciDao = pciDao;
	}

	/**
	 * @param usrDao
	 */
	public void setUsrDao(USR_MSTDao usrDao) {
		this.usrDao = usrDao;
	}

	/**
	 * @param empDao
	 */
	public void setEmpDao(EMP_MSTDao empDao) {
		this.empDao = empDao;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param compCode 会社コード
	 */
	public void setCompanyCode(String compCode) {
		this.companyCode = compCode;
	}

	/**
	 * ユーザコード設定
	 * 
	 * @param userCode ユーザコード
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 対応エラーメッセージ
	 * 
	 * @return int errorMsg
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}

	/**
	 * ログイン処理
	 */
	public void login() {
		// ログイン情報の書き込み
		if (ServerConfig.isLoginManagedMode()) {
			PCI_CHK_CTL pcientity = new PCI_CHK_CTL();
			// 会社コードSET
			pcientity.setKAI_CODE(companyCode);
			// ユーザーコードSET
			pcientity.setUSR_ID(userCode);
			// チェックイン時間SET
			pcientity.setPCI_CHECK_IN_TIME(Util.getCurrentDate());

			// インサート
			pciDao.insert(pcientity);
		}

		// 強制排他解除
		this.unlockAll();
	}

	/**
	 * ログアウト処理
	 */
	public void logout() {

		// ログイン情報の削除
		pciDao.delete(companyCode, userCode);

		// 強制排他解除
		this.unlockAll();
	}

	/**
	 * 強制排他解除
	 */
	private void unlockAll() {
		Lock lock = (Lock) container.getComponent(Lock.class);
		lock.setCode(this.companyCode, this.userCode);
		lock.unlockAll();
	}

	/**
	 * ユーザを照合する（ログイン認証）.<br>
	 * <ul>
	 * <li> 会社コードチェック
	 * <li> ユーザー名・パスワードチェック
	 * <li> 多重ログイン制御
	 * <li> ログイン規定数制御
	 * </ul>
	 * 
	 * @param password ユーザパスワード
	 * @return true 認証成功 false 認証失敗
	 */
	public boolean collatedUser(String password) {

		ENV_MST envMst = envDao.getENV_MSTByKaicode(companyCode);

		// 会社存在チェック
		if (envMst == null) {
			errorMsg = "W01002"; // 指定された会社コードは見つかりませんでした
			return false;
		}

		USR_MST usrMst = usrDao.getUSR_MSTForLogin(companyCode, userCode, DateUtil.toYMDDate(Util.getCurrentDate()));

		// ユーザ存在チェック(有効期間含め)
		if (usrMst == null) {
			this.errorMsg = "W01003"; // ユーザ、または、パスワードに誤りがあります
			return false;
		}

		// 多重ログインチェック
		PCI_CHK_CTL pciChkCtl = pciDao.getPCI_CHK_CTLByKaicodeUsrid(companyCode, userCode);
		if (pciChkCtl != null) {
			this.errorMsg = "W01004"; // 既にログインしている
			return false;
		}

		// ロックアウトロジック
		UserLockout loLogic = (UserLockout) container.getComponent(UserLockout.class);
		loLogic.setCode(this.companyCode, this.userCode);

		// ロックアウト確認
		if (loLogic.isLockoutManaged() && loLogic.isLockoutStatus()) {
			this.errorMsg = "W01009"; // ロックアウトされています
			return false;
		}

		// パスワードロジック
		UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
		pwLogic.setCode(this.companyCode, this.userCode);

		// パスワード認証
		if (!pwLogic.equalsNowPassword(password)) {
			this.errorMsg = "W01003"; // ユーザ、または、パスワードに誤りがあります

			if (loLogic.isLockoutManaged()) {
				// パスワード不正入力でロックアウトカウントアップ
				loLogic.countUp();
			}

			return false;
		}

		// ログイン規定数チェック
		if (ServerConfig.getRegulatedNumber() > 0 && pciDao.getCount(companyCode) >= ServerConfig.getRegulatedNumber()) {
			this.errorMsg = "W01005"; // 規定数オーバー
			return false;

		}

		// ロックアウトカウントクリア
		loLogic.clearLockout();

		return true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserManager#makeUserInfo()
	 */
	public TUserInfo makeUserInfo() throws TException {

		// 基本ユーザー情報取得
		usr = usrDao.getUSR_MSTByKaicodeUsrcode(companyCode, userCode);

		// 部門コードnullチェック
		if (usr == null || Util.isNullOrEmpty(usr.getDEP_CODE())) {
			throw new TException("W00196");
		}

		// 部門情報取得
		bmn = bmnDao.getBMN_MSTByKaicodeDepcode(companyCode, usr.getDEP_CODE());

		if (bmn == null) {
			throw new TException("W00196");
		}

		// 社員情報取得
		emp = empDao.getEMP_MSTByKaicodeEmpcode(companyCode, usr.getEMP_CODE());

		TUserInfo userInfo = new TUserInfo();
		userInfo.setCompanyCode(companyCode); // 会社コード
		userInfo.setUserCode(userCode); // ユーザーコード

		userInfo.setUserName(usr.getUSR_NAME());// ユーザー名
		userInfo.setEmployerCode(usr.getEMP_CODE()); // 社員コード
		userInfo.setEmployerShortName(emp.getEMP_NAME_S()); // 社員略称
		userInfo.setDepartmentCode(usr.getDEP_CODE()); // 所属部門コード
		userInfo.setDepartmentShortName(bmn.getDEP_NAME_S()); // 所属部門名略称
		userInfo.setProcessLevel(usr.getPRC_KEN()); // 処理権限レベル
		userInfo.setUpdateLevel(usr.getUPD_KEN()); // 更新権限レベル
		userInfo.setAccountChargePerson(Util.avoidNullAsInt(usr.getKEIRI_KBN()) == 1); // 経理区分
		userInfo.setUserLanguage(usr.getLNG_CODE()); // 言語コード

		// 会社情報
		userInfo.setCompanyInfo(makeCompanyInfo());

		return userInfo;
	}

	/**
	 * 指定された会社情報の取得
	 * 
	 * @return 会社情報
	 * @throws TException
	 */
	protected TCompanyInfo makeCompanyInfo() throws TException {

		// 会社(環境設定)情報取得
		env = envDao.getENV_MSTByKaicode(companyCode);

		if (env == null) {
			throw new TException("W00196");
		}

		// 会社背景色 設定なしならデフォルトの黒に
		if (Util.isNullOrEmpty(env.getFORECOLOR())) {
			env.setFORECOLOR("#000000");
		}

		// 会社コントロール情報取得
		cmp = cmpDao.getCMP_MST_ByKaicode(companyCode);

		if (cmp == null) {
			throw new TException("W00196");
		}

		// 会社情報
		TCompanyInfo compInfo = new TCompanyInfo();
		compInfo.setCompanyCode(companyCode);
		compInfo.setCompanyName(env.getKAI_NAME()); // 会社名
		compInfo.setCompanyNameS(env.getKAI_NAME_S()); // 会社略称
		compInfo.setForeColor(env.getFORECOLOR()); // 背景色

		compInfo.setItemName(cmp.getCMP_KMK_NAME()); // 「科目」名称
		compInfo.setSubItemName(cmp.getCMP_HKM_NAME()); // 「補助科目」名称
		compInfo.setBreakDownItem(cmp.getCMP_UKM_KBN()); // 「内訳科目」利用フラグ
		compInfo.setBreakDownItemName(cmp.getCMP_UKM_NAME()); // 「内訳科目」名称
		compInfo.setBaseCurrencyCode(cmp.getCUR_CODE()); // 基軸通貨

		// 管理区分1～6の使用判定
		int[] mgDivs = new int[6];
		mgDivs[0] = cmp.getKNR_KBN_1();
		mgDivs[1] = cmp.getKNR_KBN_2();
		mgDivs[2] = cmp.getKNR_KBN_3();
		mgDivs[3] = cmp.getKNR_KBN_4();
		mgDivs[4] = cmp.getKNR_KBN_5();
		mgDivs[5] = cmp.getKNR_KBN_6();
		compInfo.setManageDivs(mgDivs);

		// 管理区分1～6の名称
		String[] mgDivNames = new String[6];
		mgDivNames[0] = Util.avoidNull(cmp.getKNR_NAME_1());
		mgDivNames[1] = Util.avoidNull(cmp.getKNR_NAME_2());
		mgDivNames[2] = Util.avoidNull(cmp.getKNR_NAME_3());
		mgDivNames[3] = Util.avoidNull(cmp.getKNR_NAME_4());
		mgDivNames[4] = Util.avoidNull(cmp.getKNR_NAME_5());
		mgDivNames[5] = Util.avoidNull(cmp.getKNR_NAME_6());
		compInfo.setManageDivNames(mgDivNames);

		// 非会計明細区分1～3
		int[] nonAcDetailDivs = new int[3];
		nonAcDetailDivs[0] = cmp.getCMP_HM_KBN_1();
		nonAcDetailDivs[1] = cmp.getCMP_HM_KBN_2();
		nonAcDetailDivs[2] = cmp.getCMP_HM_KBN_3();
		compInfo.setNonAccountingDetailDivs(nonAcDetailDivs);

		// 非会計明細名称1～3名称
		String[] nonAcDetailNames = new String[3];
		nonAcDetailNames[0] = Util.avoidNull(cmp.getCMP_HM_NAME_1());
		nonAcDetailNames[1] = Util.avoidNull(cmp.getCMP_HM_NAME_2());
		nonAcDetailNames[2] = Util.avoidNull(cmp.getCMP_HM_NAME_3());
		compInfo.setNonAccountingDetailNames(nonAcDetailNames);

		// 期首月
		compInfo.setBeginningOfPeriodMonth(cmp.getCMP_KISYU());

		// レート換算端数処理区分
		compInfo.setRateConversionFraction(cmp.getRATE_KBN());

		// 元帳日別残高表示の判定
		compInfo.setLedgerEachDayBalanceView(BizUtil.isLedgerEachDayBalanceView(companyCode));

		// 通貨情報(コードと小数点桁数)
		compInfo.setCurrencyDigits(getCompanyCurrency());

		// 印刷時の初期値
		compInfo.setPrintDef(cmp.getPRINT_DEF());
		// 伝票印刷区分
		compInfo.setPrintKbn(cmp.getPRINT_KBN());

		// 直接プリンタ出力区分
		compInfo.setDirectPrintKbn(0);

		return compInfo;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserManager#getCompanyCurrency()
	 */
	public Map getCompanyCurrency() {
		List<CUR_MST> list = curDao.getCUR_MSTByKaicode(this.companyCode);
		Map<String, String> codeDigits = new HashMap<String, String>(list.size());

		for (CUR_MST entity : list) {
			codeDigits.put(entity.getCUR_CODE(), Util.avoidNull(entity.getDEC_KETA()));
		}

		return codeDigits;
	}
}
