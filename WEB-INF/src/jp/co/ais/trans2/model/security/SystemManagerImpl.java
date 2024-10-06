package jp.co.ais.trans2.model.security;

import java.util.*;
import java.util.Map.Entry;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bunkertype.*;
import jp.co.ais.trans2.model.cargo.*;
import jp.co.ais.trans2.model.code.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.port.*;
import jp.co.ais.trans2.model.tax.*;
import jp.co.ais.trans2.model.vessel.*;
import jp.co.ais.trans2.model.voyage.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * システムマネージャ実装
 */
public class SystemManagerImpl extends TModel implements SystemManager {

	/**
	 * ユーザエントリ(ログイン)
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @throws TException
	 */
	public void entryUser(String companyCode, String userCode) throws TException {

		if (ServerConfig.isLoginManagedMode()) {
			// ログイン
			UserExclusiveManager manager = (UserExclusiveManager) getComponent(UserExclusiveManager.class);

			if (!manager.canEntry(companyCode, userCode)) {
				throw new TException(getMessage("I00155"));// 指定ユーザーは既にログインされています。
			}

			manager.exclude(companyCode, userCode);
		}

		// 強制排他解除
		Lock lock = (Lock) getComponent(Lock.class);
		lock.setCode(companyCode, userCode);
		lock.unlockAll();
	}

	/**
	 * 指定されるプログラムが開けるかどうか(メニュークリックタイミング)
	 * 
	 * @param log ログ情報
	 * @return true:開ける
	 * @throws TException
	 */
	public boolean canOpenProgram(Log log) throws TException {

		if (!ServerConfig.isSystemRegulatedNumberCheck()) {
			return true;
		}

		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" SELECT SYS_CODE ");
		sql.add(" FROM   PRG_MST ");
		sql.add(" WHERE  KAI_CODE = ? ", log.getCompany().getCode());
		sql.add(" AND    PRG_CODE = ? ", log.getProgram().getCode());

		String sysCode = Util.avoidNull(DBUtil.selectOne(sql));

		int regulatedNumber = ServerConfig.getSystemRegulatedNumber(sysCode, log.getCompany().getCode());
		if (regulatedNumber <= 0) {
			// 制限なし
			return true;
		}

		List<String> groupCompanyList = ServerConfig.getSystemRegulatedGroupList(log.getCompany().getCode());

		// 判定処理(指定会社、ただ指定ユーザ以外)
		if (groupCompanyList == null || groupCompanyList.isEmpty()) {
			sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(*) ");
			sql.add(" FROM   (SELECT KAI_CODE ");
			sql.add("               ,SYS_CODE ");
			sql.add("               ,USR_ID ");
			sql.add("         FROM   PCI_SUB_CHK_CTL chk ");
			sql.add("         WHERE  KAI_CODE = ? ", log.getCompany().getCode());
			sql.add("         AND    SYS_CODE = ? ", sysCode);
			sql.add("         AND    USR_ID <> ? ", log.getUser().getCode());
			sql.add("         GROUP  BY KAI_CODE ");
			sql.add("                  ,SYS_CODE ");
			sql.add("                  ,USR_ID) s ");

		} else {

			sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(*) ");
			sql.add(" FROM   (SELECT SYS_CODE ");
			sql.add("               ,USR_ID ");
			sql.add("         FROM   PCI_SUB_CHK_CTL chk ");
			sql.add("         WHERE  KAI_CODE IN ? ", groupCompanyList);
			sql.add("         AND    SYS_CODE = ? ", sysCode);
			sql.add("         AND    USR_ID <> ? ", log.getUser().getCode());
			sql.add("         GROUP  BY SYS_CODE ");
			sql.add("                  ,USR_ID) s ");
		}

		int result = DBUtil.selectOneInt(sql);
		return result < regulatedNumber;
	}

	/**
	 * 指定されるプログラムを開く(メニュークリックタイミング)
	 * 
	 * @param log ログ情報
	 * @throws TException
	 */
	public void openProgram(Log log) throws TException {
		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" INSERT INTO PCI_SUB_CHK_CTL ( ");
		sql.add("    KAI_CODE ");
		sql.add("   ,SYS_CODE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add("   ,USR_NAME ");
		sql.add("   ,PCI_CLIENT_NAME ");
		sql.add("   ,PCI_CHECK_IN_TIME ");
		sql.add(" ) ");
		sql.add(" SELECT ");
		sql.add("    ? ", log.getCompany().getCode());
		sql.add("   ,SYS_CODE ");
		sql.add("   ,? ", log.getProgram().getCode());
		sql.add("   ,? ", log.getUser().getCode());
		sql.add("   ,? ", log.getUser().getName());
		sql.add("   ,? ", log.getIp());
		sql.addYMDHMS("   ,? ", log.getDate());
		sql.add(" FROM   PRG_MST ");
		sql.add(" WHERE  KAI_CODE = ? ", log.getCompany().getCode());
		sql.add(" AND    PRG_CODE = ? ", log.getProgram().getCode());

		DBUtil.execute(sql);
	}

	/**
	 * 指定されるユーザが開いてるプログラムを閉じる(タブ閉じる、×閉じる)
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param programCode プログラムコード
	 * @throws TException
	 */
	public void closeProgram(String companyCode, String userCode, String programCode) throws TException {
		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" DELETE FROM PCI_SUB_CHK_CTL ");
		sql.add(" WHERE  KAI_CODE = ? ", companyCode);
		sql.add(" AND    USR_ID = ? ", userCode);
		sql.add(" AND    PRG_ID = ? ", programCode);

		DBUtil.execute(sql);
	}

	/**
	 * 指定されるユーザが開いてる全てプログラムを閉じる
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @throws TException
	 */
	public void closeAllProgram(String companyCode, String userCode) throws TException {
		VCreator sql = new VCreator();
		sql.add("");
		sql.add(" DELETE FROM PCI_SUB_CHK_CTL ");
		sql.add(" WHERE  KAI_CODE = ? ", companyCode);
		sql.add(" AND    USR_ID = ? ", userCode);

		DBUtil.execute(sql);

	}

	/**
	 * OPキャッシュ情報の取得
	 * 
	 * @param info
	 * @return OPキャッシュ情報
	 * @throws TException
	 */
	public OPLoginInfo getOPCache(OPLoginInfo info) throws TException {

		Date sysDate = getNow();

		for (Entry<OPLoginDataType, OPLoginCondition> entry : info.conditionMap.entrySet()) {
			OPLoginDataType dataType = entry.getKey();
			OPLoginCondition param = entry.getValue();
			OPLoginData bean = info.getData(dataType);

			if (bean == null) {
				bean = new OPLoginData();
				bean.setDataTypeValue(dataType.value);
				info.setData(dataType, bean);
			}

			ServerLogger.debug("current count[" + param.getNowCount() + "]:" + dataType);

			Date lastUpdateDate = param.getLastUpdateDate(); // 退避
			param.setLastUpdateDate(null);
			boolean hasDelete = hasDelete(dataType, param);
			param.setLastUpdateDate(lastUpdateDate); // 復元

			if (hasDelete) {
				// 削除あり→全件再取得
				param.setLastUpdateDate(null);
				ServerLogger.debug("has delete data:" + dataType);
			} else {
				// 削除なし→差分のみ取得
				ServerLogger.debug("no delete data:" + dataType);
			}

			List list = getList(dataType, param);

			if (list != null && !list.isEmpty()) {

				ServerLogger.debug("got update data[" + list.size() + "]:" + dataType);
				bean.setLastUpdateDate(sysDate);

				bean.setList(list);

				if (!hasDelete) {

					// 件数一致、DEL→NEWの判定が必要
					List<String> codeList = info.getCodeList(dataType);
					if (codeList != null) {
						for (Object obj : list) {
							String key = OPLoginDataType.getKey(dataType, obj);
							if (!codeList.contains(key)) {
								// 含まないKEYが存在する場合に

								// 削除あり→全件再取得
								param.setLastUpdateDate(null);
								list = getList(dataType, param);
								bean.setList(list);

								info.setResultType(dataType, OPLoginResultType.NEED_UPDATE);

								// 次の処理へ
								continue;
							}
						}
					}

					// 最低限更新のみ
					info.setResultType(dataType, OPLoginResultType.NEED_MERGE);
				} else {
					// 全件更新
					info.setResultType(dataType, OPLoginResultType.NEED_UPDATE);
				}

			} else {

				ServerLogger.debug("no update data:" + dataType);
				info.setResultType(dataType, OPLoginResultType.NO_OPERATION);
			}

		}

		// レスポンス送信不要
		info.codeListMap.clear();

		return info;
	}

	// /**
	// * @param dataType
	// * @param bean
	// * @param beanClazz
	// * @param list
	// * @return マージ結果
	// */
	// @SuppressWarnings("unused")
	// protected <T> OPLoginData merge(OPLoginDataType dataType, OPLoginData bean, Class<T> beanClazz, List list) {
	//
	// OPLoginMerge m = new OPLoginMerge<T>();
	// bean = m.merge(dataType, bean, list);
	//
	// return bean;
	// }

	/**
	 * @param dataType
	 * @param param
	 * @return true:削除あり
	 * @throws TException
	 */
	protected boolean hasDelete(OPLoginDataType dataType, OPLoginCondition param) throws TException {
		if (dataType == null || param == null) {
			return false;
		}

		switch (dataType) {
			case OP_CODE_MST: { // 1：OPアイテムマスタ
				CodeManager mgr = get(CodeManager.class);
				return mgr.hasDelete((CodeSearchCondition) param);
			}
			case CM_BNKR_TYPE_MST: { // 2：油種マスタ
				BunkerTypeManager mgr = get(BunkerTypeManager.class);
				return mgr.hasDelete((BunkerTypeSearchCondition) param);
			}
			case CUR_MST: { // 3：通貨マスタ
				CurrencyManager mgr = get(CurrencyManager.class);
				return mgr.hasDelete((CurrencySearchCondition) param);
			}
			case TRI_MST: { // 4：取引先マスタ
				CustomerManager mgr = get(CustomerManager.class);
				return mgr.hasDelete((CustomerSearchCondition) param);
			}
			case BMN_MST: { // 5：部門マスタ
				DepartmentManager mgr = get(DepartmentManager.class);
				return mgr.hasDelete((DepartmentSearchCondition) param);
			}
			case EMP_MST: { // 6：社員マスタ
				EmployeeManager mgr = get(EmployeeManager.class);
				return mgr.hasDelete((EmployeeSearchCondition) param);
			}
			case CM_VESSEL_MST: { // 7：船マスタ
				VesselManager mgr = get(VesselManager.class);
				return mgr.hasDelete((VesselSearchCondition) param);
			}
			case CM_PORT_MST: { // 8：港マスタ
				PortManager mgr = get(PortManager.class);
				return mgr.hasDelete((PortSearchCondition) param);
			}
			case CM_VOYAGE_MST: { // 9：航海マスタ
				VoyageManager mgr = get(VoyageManager.class);
				return mgr.hasDelete((VoyageSearchCondition) param);
			}
			case SZEI_MST: { // 10：消費税マスタ
				ConsumptionTaxManager mgr = get(ConsumptionTaxManager.class);
				return mgr.hasDelete((ConsumptionTaxSearchCondition) param);
			}
			case OP_CRG_MST: { // 11：カーゴマスタ
				CargoManager mgr = get(CargoManager.class);
				return mgr.hasDelete((CargoSearchCondition) param);
			}
			case OP_ITEM_MST: { // 12：OPアイテムマスタ
				OPItemManager mgr = get(OPItemManager.class);
				return mgr.hasDelete((OPItemSearchCondition) param);
			}
			case AP_HOH_MST: { // 13：支払方法マスタ
				PaymentMethodManager mgr = get(PaymentMethodManager.class);
				return mgr.hasDelete((PaymentMethodSearchCondition) param);
			}
			case AP_CBK_MST: { // 14：銀行口座マスタ
				BankAccountManager mgr = get(BankAccountManager.class);
				return mgr.hasDelete((BankAccountSearchCondition) param);
			}
			case COUNTRY_MST: { // 15：国マスタ
				CountryManager mgr = get(CountryManager.class);
				return mgr.hasDelete((CountrySearchCondition) param);
			}
			default:
				return false;
		}
	}

	/**
	 * データリスト取得
	 * 
	 * @param dataType
	 * @param param
	 * @return データリスト
	 * @throws TException
	 */
	protected List getList(OPLoginDataType dataType, OPLoginCondition param) throws TException {
		if (dataType == null || param == null) {
			return null;
		}

		switch (dataType) {
			case OP_CODE_MST: { // 1：OPアイテムマスタ
				CodeManager mgr = get(CodeManager.class);
				return mgr.get((CodeSearchCondition) param);
			}
			case CM_BNKR_TYPE_MST: { // 2：油種マスタ
				BunkerTypeManager mgr = get(BunkerTypeManager.class);
				return mgr.get((BunkerTypeSearchCondition) param);
			}
			case CUR_MST: { // 3：通貨マスタ
				CurrencyManager mgr = get(CurrencyManager.class);
				return mgr.get((CurrencySearchCondition) param);
			}
			case TRI_MST: { // 4：取引先マスタ
				CustomerManager mgr = get(CustomerManager.class);
				return mgr.get((CustomerSearchCondition) param);
			}
			case BMN_MST: { // 5：部門マスタ
				DepartmentManager mgr = get(DepartmentManager.class);
				return mgr.get((DepartmentSearchCondition) param);
			}
			case EMP_MST: { // 6：社員マスタ
				EmployeeManager mgr = get(EmployeeManager.class);
				return mgr.get((EmployeeSearchCondition) param);
			}
			case CM_VESSEL_MST: { // 7：船マスタ
				VesselManager mgr = get(VesselManager.class);
				return mgr.getVesselForSearch((VesselSearchCondition) param);
			}
			case CM_PORT_MST: { // 8：港マスタ
				PortManager mgr = get(PortManager.class);
				return mgr.get((PortSearchCondition) param);
			}
			case CM_VOYAGE_MST: { // 9：航海マスタ
				VoyageManager mgr = get(VoyageManager.class);
				return mgr.get((VoyageSearchCondition) param);
			}
			case SZEI_MST: { // 10：消費税マスタ
				ConsumptionTaxManager mgr = get(ConsumptionTaxManager.class);
				return mgr.get((ConsumptionTaxSearchCondition) param);
			}
			case OP_CRG_MST: { // 11：カーゴマスタ
				CargoManager mgr = get(CargoManager.class);
				return mgr.get((CargoSearchCondition) param);
			}
			case OP_ITEM_MST: { // 12：OPアイテムマスタ
				OPItemManager mgr = get(OPItemManager.class);
				return mgr.get((OPItemSearchCondition) param);
			}
			case AP_HOH_MST: { // 13：支払方法マスタ
				PaymentMethodManager mgr = get(PaymentMethodManager.class);
				return mgr.get((PaymentMethodSearchCondition) param);
			}
			case AP_CBK_MST: { // 14：銀行口座マスタ
				BankAccountManager mgr = get(BankAccountManager.class);
				return mgr.get((BankAccountSearchCondition) param);
			}
			case COUNTRY_MST: { // 15：国マスタ
				CountryManager mgr = get(CountryManager.class);
				return mgr.get((CountrySearchCondition) param);
			}
			default:
				return null;
		}
	}

	/**
	 * SQL creator
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * コンストラクター
		 */
		public VCreator() {
			crlf = " ";
		}
	}

}
