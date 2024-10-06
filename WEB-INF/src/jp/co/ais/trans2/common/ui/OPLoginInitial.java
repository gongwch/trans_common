package jp.co.ais.trans2.common.ui;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.model.bunkertype.*;
import jp.co.ais.trans2.model.cargo.*;
import jp.co.ais.trans2.model.code.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.port.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.tax.*;
import jp.co.ais.trans2.model.vessel.*;
import jp.co.ais.trans2.model.voyage.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * トラオペ初期処理
 */
public class OPLoginInitial extends TController implements TLoginInitialInterface {

	/** true:CACHEを使う。差分のみ更新する */
	public static final boolean isUseCache = ClientConfig.isFlagOn("trans.op.init.use.cache");

	/** システム日時 */
	protected Date sysDate;

	/** 保存フォルダ */
	protected String parentFolder;

	/** 初期化処理済み、今後はリフレッシュのみ */
	protected boolean isInitialized;

	/**
	 * 処理名
	 */
	public String getName() {
		return "trans-operator initial";
	}

	/**
	 * 親フォルダを事前作成
	 */
	public void makeParentFolder() {
		String aisDir = SystemUtil.getAisDir() + getSaveKey(getCompanyCode()) + "\\";
		File file = new File(aisDir);
		file.mkdirs();

		parentFolder = aisDir;
	}

	/**
	 * @return 保存フォルダ
	 */
	public String getParentFolder() {
		return parentFolder;
	}

	/**
	 * 保存キーの取得
	 * 
	 * @param companyCode
	 * @return 保存キー
	 */
	protected String getSaveKey(String companyCode) {
		return "init_" + getClientSaveKey() + "_" + companyCode;
	}

	/**
	 * @return クライアントへ保持する時、当該製品の識別子
	 */
	public static String getClientSaveKey() {
		String url = jp.co.ais.trans.common.config.ClientConfig.getBaseAddress();
		url = url.replaceAll("/", "_");
		url = url.replaceAll(":", "_");
		url = url.replaceAll("\\\\", "");

		if (Util.isNullOrEmpty(url)) {
			url = ClientConfig.getTitle();
		}

		return url;
	}

	/**
	 * 初期処理
	 */
	public void init() {

		// initTest(OPLoginDataType.values());
		initEnhance(false, true, OPLoginDataType.values());

		// ClientLogger.memory("initial start");
		//
		// OPLoginUtil.setInitialClass(this);
		//
		// try {
		// makeParentFolder();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// // 初期化：コードマスタ
		// refreshAsync(OPLoginDataType.OP_CODE_MST);
		//
		// // 初期化：油種マスタ
		// refreshAsync(OPLoginDataType.CM_BNKR_TYPE_MST);
		//
		// // 初期化：通貨マスタ
		// refreshAsync(OPLoginDataType.CUR_MST);
		//
		// // 初期化：取引先マスタ
		// refreshAsync(OPLoginDataType.TRI_MST);
		//
		// // 初期化：部門マスタ
		// refreshAsync(OPLoginDataType.BMN_MST);
		//
		// // 初期化：社員マスタ
		// refreshAsync(OPLoginDataType.EMP_MST);
		//
		// // 初期化：船マスタ
		// refreshAsync(OPLoginDataType.CM_VESSEL_MST);
		//
		// // 初期化：港マスタ
		// refreshAsync(OPLoginDataType.CM_PORT_MST);
		//
		// // 初期化：航海マスタ
		// refreshAsync(OPLoginDataType.CM_VOYAGE_MST);
		//
		// // 初期化：消費税マスタ
		// refreshAsync(OPLoginDataType.SZEI_MST);
		//
		// // 初期化：カーゴマスタ
		// refreshAsync(OPLoginDataType.OP_CRG_MST);
		//
		// // 初期化：OPアイテムマスタ
		// refreshAsync(OPLoginDataType.OP_ITEM_MST);
		//
		// // 初期化：支払方法マスタ
		// refreshAsync(OPLoginDataType.AP_HOH_MST);
		//
		// // 初期化：銀行口座マスタ
		// refreshAsync(OPLoginDataType.AP_CBK_MST);
		//
		// // 初期化：国マスタ
		// refreshAsync(OPLoginDataType.COUNTRY_MST);
		//
		// ClientLogger.memory("initial end");
		//
		// isInitialized = true;
	}

	/**
	 * 初期処理(TYPE指定、バッチ用)<br>
	 * 同期
	 * 
	 * @param types
	 */
	public void init(OPLoginDataType... types) {

		// ClientLogger.memory("initial start");
		//
		// OPLoginUtil.setInitialClass(this);
		//
		// try {
		// makeParentFolder();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// for (OPLoginDataType type : types) {
		// refresh(type);
		// }
		//
		// ClientLogger.memory("initial end");
		//
		// isInitialized = true;

		initEnhance(false, false, types);
	}

	/**
	 * 初期処理(TYPE指定、バッチ用)<br>
	 * ASYNC
	 * 
	 * @param types
	 */
	public void initAsync(OPLoginDataType... types) {

		// ClientLogger.memory("initial start");
		//
		// OPLoginUtil.setInitialClass(this);
		//
		// try {
		// makeParentFolder();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// for (OPLoginDataType type : types) {
		// refreshAsync(type);
		// }
		//
		// ClientLogger.memory("initial end");
		//
		// isInitialized = true;

		initEnhance(true, false, types);
	}

	/**
	 * 初期処理(TYPE指定、バッチ用)<br>
	 * ASYNC
	 * 
	 * @param isAsync
	 * @param isLogin
	 * @param types
	 */
	public void initEnhance(final boolean isAsync, final boolean isLogin, OPLoginDataType... types) {
		
		ClientLogger.memory("initial start");

		OPLoginUtil.setInitialClass(this);

		try {
			makeParentFolder();
		} catch (Exception e) {
			e.printStackTrace();
		}

		final OPLoginInfo info = getOPLoginInfo(types);

		try {

			if (isAsync) {
				Thread th = new Thread(new Runnable() {

					public void run() {
						try {
							cacheLoginInfo(isAsync, info, isLogin);

							ClientLogger.memory("initial end");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				th.start();

			} else {
				cacheLoginInfo(isAsync, info, isLogin);
				ClientLogger.memory("initial end");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		isInitialized = true;
	}

	/**
	 * @param isAsync
	 * @param info
	 * @param isLogin
	 * @throws TException
	 */
	protected void cacheLoginInfo(boolean isAsync, OPLoginInfo info, boolean isLogin) throws TException {

		Map<OPLoginDataType, List> cacheListMap = new HashMap<OPLoginDataType, List>();
		if (info != null && info.dataMap != null) {
			for (OPLoginDataType key : info.dataMap.keySet()) {
				OPLoginData bean = info.getData(key);
				List list = bean != null ? bean.getList() : null;
				cacheListMap.put(key, list);

				List<String> codeList = new ArrayList<String>();

				if (list != null) {
					for (Object obj : list) {
						codeList.add(OPLoginDataType.getKey(key, obj));
					}
				}

				// 送信用情報をクリアする
				bean.setList(null);
				info.setCodeList(key, codeList);
			}
		}

		OPLoginInfo newInfo = (OPLoginInfo) request(SystemManager.class, "getOPCache", info);

		if (newInfo != null && newInfo.resultMap != null) {
			for (Entry<OPLoginDataType, OPLoginResultType> entry : newInfo.resultMap.entrySet()) {

				// キャッシュ用データ
				OPLoginDataType dataType = entry.getKey();
				OPLoginResultType result = entry.getValue();

				// 新規情報
				OPLoginData newBean = newInfo.getData(dataType);
				List newList = newBean.getList();

				// 既存リストを設定する
				newBean.setList(cacheListMap.get(dataType));

				String fileName = getFileName(dataType);
				boolean needSave = false;

				if (isUseCache) {
					if (OPLoginResultType.NEED_UPDATE == result) {
						// 更新必要
						newBean.setList(newList);

						needSave = true;

					} else if (OPLoginResultType.NEED_MERGE == result) {

						Class beanClazz = OPLoginDataType.getEntityClass(dataType);

						// マージ処理
						merge(dataType, newBean, beanClazz, newList);

						needSave = true;

					} else if (OPLoginResultType.CLEAR_ALL == result) {
						newBean.setList(new ArrayList());

						needSave = true;

					} else {
						newBean.setList(cacheListMap.get(dataType));
					}

				} else {
					// キャッシュ使用しない
				}

				int count = newBean.getList() != null ? newBean.getList().size() : 0;
				String procName = "";

				if (isLogin) {
					procName = "login";
				} else {
					procName = "refresh";
				}

				ClientLogger.debug(procName + " info:" + fileName + " count[" + count + "]:" + dataType);

				// 初期化済み記憶
				OPLoginUtil.saveCache(dataType, newBean);

				Class beanClazz = OPLoginDataType.getEntityClass(dataType);

				setList(newBean.getList(), beanClazz);

				if (needSave) {
					if (isAsync) {
						saveObjectAsync(newBean, fileName);
					} else {
						saveObject(newBean, fileName);
					}
				}
			}
		}
	}

	/**
	 * @param dataType
	 * @param bean
	 * @param beanClazz
	 * @param list
	 * @return マージ結果
	 */
	@SuppressWarnings("unused")
	protected <T> OPLoginData merge(OPLoginDataType dataType, OPLoginData bean, Class<T> beanClazz, List list) {

		OPLoginMerge m = new OPLoginMerge<T>();
		bean = m.merge(dataType, bean, list);

		return bean;
	}

	/**
	 * 非同期処理
	 * 
	 * @param dataType
	 */
	public void refreshAsync(final OPLoginDataType dataType) {
		// Thread th = new Thread(new Runnable() {
		//
		// public void run() {
		// refresh(dataType, true);
		// }
		// });
		// th.start();
		initAsync(dataType);
	}

	/**
	 * 更新処理
	 * 
	 * @param dataType
	 */
	public void refresh(OPLoginDataType dataType) {
		// refresh(dataType, false); // 同期処理
		init(dataType);
	}

	/**
	 * @param dataType
	 * @return ファイル名
	 */
	public String getFileName(OPLoginDataType dataType) {

		StringBuilder sb = new StringBuilder();
		sb.append(getParentFolder());
		sb.append(OPLoginDataType.getFileName(dataType));

		String fileName = sb.toString();
		return fileName;
	}

	/**
	 * 更新処理
	 * 
	 * @param dataType
	 * @param isAsync true:非同期処理
	 */
	public void refresh(OPLoginDataType dataType, boolean isAsync) {
		if (dataType == null) {
			return;
		}

		String fileName = getFileName(dataType);

		OPLoginData bean = null;
		Date currentTimestamp = null;

		if (isUseCache) {

			try {
				sysDate = (Date) request(isAsync, CompanyManager.class, "getSystemDate");
			} catch (TException e) {
				sysDate = Util.getCurrentDate();
			}

			try {

				bean = OPLoginUtil.getCache(dataType);

				if (bean == null) {
					ClientLogger.debug("refresh load:" + fileName);
					try {
						bean = (OPLoginData) getObject(fileName);
					} catch (Exception ex) {
						bean = null; // タイプ異常
					}
				} else {
					ClientLogger.debug("refresh " + dataType.toString() + " using memory cache.");
				}

				if (bean != null) {
					currentTimestamp = bean.getLastUpdateDate();
				}

				Date timeoutDate = getTimeoutDate(dataType);

				if (timeoutDate != null && currentTimestamp != null
					&& Util.isSmallerThenByYMDHMS(currentTimestamp, timeoutDate)) {
					// 設定上タイムアウト指定あり
					// 持っているタイムスタンプ＜タイムアウト日時、強制的に再取得
					bean = null;
					currentTimestamp = null;
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		switch (dataType) {

			case OP_CODE_MST:
				bean = refreshOP_CODE_MST(bean, isAsync); // 1：OPアイテムマスタ
				break;

			case CM_BNKR_TYPE_MST:
				bean = refreshCM_BNKR_TYPE_MST(bean, isAsync); // 2：油種マスタ
				break;

			case CUR_MST:
				bean = refreshCUR_MST(bean, isAsync); // 3：通貨マスタ
				break;

			case TRI_MST:
				bean = refreshTRI_MST(bean, isAsync); // 4：取引先マスタ
				break;

			case BMN_MST:
				bean = refreshBMN_MST(bean, isAsync); // 5：部門マスタ
				break;

			case EMP_MST:
				bean = refreshEMP_MST(bean, isAsync); // 6：社員マスタ
				break;

			case CM_VESSEL_MST:
				bean = refreshCM_VESSEL_MST(bean, isAsync); // 7：船マスタ
				break;

			case CM_PORT_MST:
				bean = refreshCM_PORT_MST(bean, isAsync); // 8：港マスタ
				break;

			case CM_VOYAGE_MST:
				bean = refreshCM_VOYAGE_MST(bean, isAsync); // 9：航海マスタ
				break;

			case SZEI_MST:
				bean = refreshSZEI_MST(bean, isAsync); // 10：消費税マスタ
				break;

			case OP_CRG_MST:
				bean = refreshOP_CRG_MST(bean, isAsync); // 11：カーゴマスタ
				break;

			case OP_ITEM_MST:
				bean = refreshOP_ITEM_MST(bean, isAsync); // 12：OPアイテムマスタ
				break;

			case AP_HOH_MST:
				bean = refreshAP_HOH_MST(bean, isAsync); // 13：支払方法マスタ
				break;

			case AP_CBK_MST:
				bean = refreshAP_CBK_MST(bean, isAsync); // 14：銀行口座マスタ
				break;

			case COUNTRY_MST:
				bean = refreshCOUNTRY_MST(bean, isAsync); // 15：国マスタ
				break;
		}

		if (isUseCache) {

			Date newTimestamp = null;
			if (bean != null) {
				// 初期化済み記憶
				OPLoginUtil.saveCache(dataType, bean);

				newTimestamp = bean.getLastUpdateDate();
			}

			if (newTimestamp != null //
				&& !DateUtil.equalsAvoidNull(currentTimestamp, newTimestamp) //
				|| !(new File(fileName).exists())) {

				if (isAsync) {
					saveObjectAsync(bean, fileName);
				} else {
					saveObject(bean, fileName);
				}
			}
		}
	}

	/**
	 * 指定マスタデータはタイムアウトかどうかの基準日時を返す
	 * 
	 * @param dataType
	 * @return 指定マスタデータはタイムアウトかどうか
	 */
	protected static Date getTimeoutDate(OPLoginDataType dataType) {
		String key = "trans.op.init.cache.timeout";
		try {
			Date timeoutDate = DateUtil.toDate(ClientConfig.getProperty(key + "." + dataType.value));
			return timeoutDate;
		} catch (Throwable e1) {
			// 個別設定なければ、共有設定を取得してみる

			try {
				Date timeoutDate = DateUtil.toDate(ClientConfig.getProperty(key));
				return timeoutDate;
			} catch (Throwable e2) {
				// エラーなし
			}
		}
		return null;
	}

	/**
	 * @param fileName
	 * @return Object
	 */
	public static Object getObject(String fileName) {
		long start = System.currentTimeMillis();
		ClientLogger.debug("load start:" + fileName);

		Object obj = FileUtil.getObject(fileName);

		long end = System.currentTimeMillis();
		ClientLogger.debug("load end:" + ((end - start) / 1000d));

		return obj;
	}

	/**
	 * 保存処理
	 * 
	 * @param obj
	 * @param fileName
	 */
	public static void saveObject(Object obj, String fileName) {
		long start = System.currentTimeMillis();
		ClientLogger.debug("save start:" + fileName);

		FileUtil.saveObject(obj, fileName);

		long end = System.currentTimeMillis();
		ClientLogger.debug("save end:" + ((end - start) / 1000d));
	}

	/**
	 * 非同期保存処理
	 * 
	 * @param obj
	 * @param fileName
	 */
	public static void saveObjectAsync(final Object obj, final String fileName) {
		Thread th = new Thread(new Runnable() {

			public void run() {
				saveObject(obj, fileName);
			}
		});
		th.start();
	}

	/**
	 * エンティティ作成
	 * 
	 * @return エンティティ
	 */
	protected OPLoginData createOPLoginData() {
		return new OPLoginData();
	}

	/**
	 * 一括処理用エンティティ作成
	 * 
	 * @return 一括処理用エンティティ
	 */
	protected OPLoginInfo createOPLoginInfo() {
		return new OPLoginInfo();
	}

	/**
	 * 一括処理用エンティティ取得(各パラメータ設定)
	 * 
	 * @param types
	 * @return 一括処理用エンティティ
	 */
	protected OPLoginInfo getOPLoginInfo(OPLoginDataType... types) {
		OPLoginInfo info = createOPLoginInfo();

		for (OPLoginDataType type : types) {
			OPLoginCondition param = getCondition(type);
			if (param != null) {
				// 共通条件

				param.setCompanyCode(getCompanyCode());

				if (isUseCache) {
					// キャッシュを使う時に既存キャッシュを取得してみる

					// 指定タイプのキャッシュ情報及びタイムスタンプの設定
					setupDataAndTimestamp(info, type, OPLoginDataType.getEntityClass(type));

					OPLoginData bean = info.getData(type);

					if (bean != null) {
						param.setLastUpdateDate(bean.getLastUpdateDate());
						param.setNowCount(bean.getList().size());
					} else {
						param.setLastUpdateDate(null);
						param.setNowCount(0);
					}

				} else {
					// そのまま
				}

				info.setCondition(type, param);
			}
		}

		return info;
	}

	/**
	 * 指定タイプのキャッシュ情報及びタイムスタンプの設定
	 * 
	 * @param info
	 * @param dataType
	 * @param clazz
	 */
	@SuppressWarnings("unused")
	protected <T> void setupDataAndTimestamp(OPLoginInfo info, OPLoginDataType dataType, Class<T> clazz) {
		if (info == null || dataType == null) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(getParentFolder());
		sb.append(OPLoginDataType.getFileName(dataType));

		String fileName = sb.toString();

		OPLoginData bean = null;
		Date currentTimestamp = null;

		if (isUseCache) {
			try {

				bean = OPLoginUtil.getCache(dataType);

				if (bean == null) {
					ClientLogger.debug("refresh load:" + fileName);
					try {
						bean = (OPLoginData) getObject(fileName);
						ClientLogger.debug("refresh loaded:" + fileName);
					} catch (Exception ex) {
						bean = null; // タイプ異常
						ClientLogger.debug("refresh load faild:" + fileName);
					}
				} else {
					int count = bean.getList() != null ? bean.getList().size() : 0;
					ClientLogger.debug("refresh " + dataType.toString() + " using memory cache. count[" + count + "]");
				}

				if (bean != null) {
					currentTimestamp = bean.getLastUpdateDate();
				}

				Date timeoutDate = getTimeoutDate(dataType);

				if (timeoutDate != null && currentTimestamp != null
					&& Util.isSmallerThenByYMDHMS(currentTimestamp, timeoutDate)) {
					// 設定上タイムアウト指定あり
					// 持っているタイムスタンプ＜タイムアウト日時、強制的に再取得
					bean = null;
					currentTimestamp = null;
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (bean == null) {
			bean = createOPLoginData();
			bean.setDataTypeValue(dataType.value);
		}
		if (bean.getList() == null) {
			bean.setList(new ArrayList<T>());
		}

		info.setData(dataType, bean);
		info.setTimestamp(dataType, currentTimestamp);

	}

	/**
	 * @param dataType
	 * @return 各タイプの検索条件
	 */
	protected OPLoginCondition getCondition(OPLoginDataType dataType) {

		if (dataType == null) {
			return null;
		}

		switch (dataType) {
			case OP_CODE_MST: { // 1：OPアイテムマスタ
				CodeSearchCondition param = new CodeSearchCondition();
				return param;
			}
			case CM_BNKR_TYPE_MST: { // 2：油種マスタ
				BunkerTypeSearchCondition param = new BunkerTypeSearchCondition();
				return param;
			}
			case CUR_MST: { // 3：通貨マスタ
				CurrencySearchCondition param = new CurrencySearchCondition();
				return param;
			}
			case TRI_MST: { // 4：取引先マスタ
				CustomerSearchCondition param = new CustomerSearchCondition();
				return param;
			}
			case BMN_MST: { // 5：部門マスタ
				DepartmentSearchCondition param = new DepartmentSearchCondition();
				return param;
			}
			case EMP_MST: { // 6：社員マスタ
				EmployeeSearchCondition param = new EmployeeSearchCondition();
				return param;
			}
			case CM_VESSEL_MST: { // 7：船マスタ
				VesselSearchCondition param = new VesselSearchCondition();
				param.setIncludeDetail(false); // 詳細情報含まない
				param.setIncludeSuspended(true); // SUSPENDDEDを含む(画面処理はOPLoginUtilでフィルターかける)
				param.setIncludeRelet(true); // RELETも対象
				return param;
			}
			case CM_PORT_MST: { // 8：港マスタ
				PortSearchCondition param = new PortSearchCondition();
				return param;
			}
			case CM_VOYAGE_MST: { // 9：航海マスタ
				VoyageSearchCondition param = new VoyageSearchCondition();
				return param;
			}
			case SZEI_MST: { // 10：消費税マスタ
				ConsumptionTaxSearchCondition param = new ConsumptionTaxSearchCondition();
				return param;
			}
			case OP_CRG_MST: { // 11：カーゴマスタ
				CargoSearchCondition param = new CargoSearchCondition();
				return param;
			}
			case OP_ITEM_MST: { // 12：OPアイテムマスタ
				OPItemSearchCondition param = new OPItemSearchCondition();
				param.setOnlyShowAccountName(true);
				return param;
			}
			case AP_HOH_MST: { // 13：支払方法マスタ
				PaymentMethodSearchCondition param = new PaymentMethodSearchCondition();
				return param;
			}
			case AP_CBK_MST: { // 14：銀行口座マスタ
				BankAccountSearchCondition param = new BankAccountSearchCondition();
				return param;
			}
			case COUNTRY_MST: { // 15：国マスタ
				CountrySearchCondition param = new CountrySearchCondition();
				return param;
			}
			default:
				return null;
		}
	}

	/**
	 * @param dataType
	 * @param beanClazz
	 * @param mgrClazz
	 * @param bean
	 * @param param
	 * @param isAsync true:非同期処理
	 * @return リフレッシュ処理
	 */
	public <T> OPLoginData refresh(OPLoginDataType dataType, Class<T> beanClazz, Class mgrClazz, OPLoginData bean,
		OPLoginCondition param, boolean isAsync) {
		try {

			param.setCompanyCode(getCompanyCode());

			if (isUseCache) {
				// キャッシュを使う時に既存キャッシュを取得してみる

				if (bean == null) {
					bean = createOPLoginData();
					bean.setDataTypeValue(dataType.value);
				}
				if (bean.getList() == null) {
					bean.setList(new ArrayList<T>());
				}

				param.setLastUpdateDate(bean.getLastUpdateDate());
				param.setNowCount(bean.getList().size());

				boolean hasDelete = (Boolean) request(isAsync, mgrClazz, "hasDelete", param);

				if (hasDelete) {
					// 削除あり→全件再取得
					param.setLastUpdateDate(null);
					ClientLogger.debug("has delete data:" + dataType);
				} else {
					// 削除なし→差分のみ取得
					ClientLogger.debug("no delete data:" + dataType);
				}

				String method = "get";
				if (OPLoginDataType.CM_VESSEL_MST == dataType) {
					method = "getVesselForSearch";

					// FIXME: 次回訂正予定
					// } else if (OPLoginDataType.TRI_MST == dataType) {
					// method = "getCustomerForSearch";
				}

				Object list = request(isAsync, mgrClazz, method, param);

				if (hasDelete) {
					// 削除あり→全て更新
					bean.setList((List) list);
				} else {
					// 削除なし→マージ処理
					OPLoginMerge m = new OPLoginMerge<T>();
					bean = m.merge(dataType, bean, list);
				}

				if (list != null && !((List) list).isEmpty()) {
					bean.setLastUpdateDate(sysDate);
				}

				setList(bean.getList(), beanClazz);

			} else {

				Object list = request(isAsync, mgrClazz, "get", param);
				setList(list, beanClazz);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}

	/**
	 * @param isAsync
	 * @param cls
	 * @param methodName
	 * @param arg
	 * @return リクエスト処理
	 * @throws TException
	 */
	public Object request(boolean isAsync, Class cls, String methodName, Object... arg) throws TException {
		if (isAsync) {
			// 非同期の場合は新インスタンスで実行する
			OPLoginInitial ctrl = new OPLoginInitial();
			try {

				Object obj = ctrl.request(cls, methodName, arg);

				ctrl.finalize();

				return obj;

			} catch (Throwable e) {
				// エラーなし
				throw new TException(e);
			} finally {
				System.gc();
			}
		} else {
			return super.request(cls, methodName, arg);
		}
	}

	/**
	 * @param list
	 * @param clazz
	 */
	protected synchronized static void setList(Object list, Class clazz) {
		if (clazz.equals(OP_CODE_MST.class)) {
			OPLoginUtil.setCodeMstList((List<OP_CODE_MST>) list);

		} else if (clazz.equals(CM_BNKR_TYPE_MST.class)) {
			OPLoginUtil.setBunkerTypeList((List<CM_BNKR_TYPE_MST>) list);

		} else if (clazz.equals(Currency.class)) {
			OPLoginUtil.setCurrencyList((List<Currency>) list);

		} else if (clazz.equals(Customer.class)) {
			OPLoginUtil.setCustomerList((List<Customer>) list);

		} else if (clazz.equals(Department.class)) {
			OPLoginUtil.setDepartmentList((List<Department>) list);

		} else if (clazz.equals(Employee.class)) {
			OPLoginUtil.setEmployeeList((List<Employee>) list);

		} else if (clazz.equals(Vessel.class)) {
			OPLoginUtil.setVesselList((List<Vessel>) list);

		} else if (clazz.equals(Port.class)) {
			OPLoginUtil.setPortList((List<Port>) list);

		} else if (clazz.equals(Voyage.class)) {
			OPLoginUtil.setVoyageList((List<Voyage>) list);

		} else if (clazz.equals(ConsumptionTax.class)) {
			OPLoginUtil.setConsumptionTaxList((List<ConsumptionTax>) list);

		} else if (clazz.equals(Cargo.class)) {
			OPLoginUtil.setCargoList((List<Cargo>) list);

		} else if (clazz.equals(OPItem.class)) {
			OPLoginUtil.setOPItemList((List<OPItem>) list);

		} else if (clazz.equals(PaymentMethod.class)) {
			OPLoginUtil.setPayMethodList((List<PaymentMethod>) list);

		} else if (clazz.equals(BankAccount.class)) {
			OPLoginUtil.setBankAccountList((List<BankAccount>) list);

		} else if (clazz.equals(Country.class)) {
			OPLoginUtil.setCountryList((List<Country>) list);

		}
	}

	/**
	 * 1：OPアイテムマスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshOP_CODE_MST(OPLoginData bean, boolean isAsync) {
		CodeSearchCondition param = new CodeSearchCondition();
		return refresh(OPLoginDataType.OP_CODE_MST, OP_CODE_MST.class, CodeManager.class, bean, param, isAsync);
	}

	/**
	 * 2：油種マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshCM_BNKR_TYPE_MST(OPLoginData bean, boolean isAsync) {
		BunkerTypeSearchCondition param = new BunkerTypeSearchCondition();
		return refresh(OPLoginDataType.CM_BNKR_TYPE_MST, CM_BNKR_TYPE_MST.class, BunkerTypeManager.class, bean, param,
			isAsync);
	}

	/**
	 * 3：通貨マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshCUR_MST(OPLoginData bean, boolean isAsync) {
		CurrencySearchCondition param = new CurrencySearchCondition();
		return refresh(OPLoginDataType.CUR_MST, Currency.class, CurrencyManager.class, bean, param, isAsync);
	}

	/**
	 * 4：取引先マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshTRI_MST(OPLoginData bean, boolean isAsync) {
		CustomerSearchCondition param = new CustomerSearchCondition();
		return refresh(OPLoginDataType.TRI_MST, Customer.class, CustomerManager.class, bean, param, isAsync);
	}

	/**
	 * 5：部門マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshBMN_MST(OPLoginData bean, boolean isAsync) {
		DepartmentSearchCondition param = new DepartmentSearchCondition();
		return refresh(OPLoginDataType.BMN_MST, Department.class, DepartmentManager.class, bean, param, isAsync);
	}

	/**
	 * 6：社員マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshEMP_MST(OPLoginData bean, boolean isAsync) {
		EmployeeSearchCondition param = new EmployeeSearchCondition();
		return refresh(OPLoginDataType.EMP_MST, Employee.class, EmployeeManager.class, bean, param, isAsync);
	}

	/**
	 * 7：船マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshCM_VESSEL_MST(OPLoginData bean, boolean isAsync) {
		VesselSearchCondition param = new VesselSearchCondition();
		param.setIncludeDetail(false); // 詳細情報含まない
		param.setIncludeSuspended(true); // SUSPENDDEDを含む(画面処理はOPLoginUtilでフィルターかける)
		param.setIncludeRelet(true); // RELETも対象
		return refresh(OPLoginDataType.CM_VESSEL_MST, Vessel.class, VesselManager.class, bean, param, isAsync);
	}

	/**
	 * 8：港マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshCM_PORT_MST(OPLoginData bean, boolean isAsync) {
		PortSearchCondition param = new PortSearchCondition();
		return refresh(OPLoginDataType.CM_PORT_MST, Port.class, PortManager.class, bean, param, isAsync);
	}

	/**
	 * 9：航海マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshCM_VOYAGE_MST(OPLoginData bean, boolean isAsync) {
		VoyageSearchCondition param = new VoyageSearchCondition();
		return refresh(OPLoginDataType.CM_VOYAGE_MST, Voyage.class, VoyageManager.class, bean, param, isAsync);
	}

	/**
	 * 10：消費税マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshSZEI_MST(OPLoginData bean, boolean isAsync) {
		ConsumptionTaxSearchCondition param = new ConsumptionTaxSearchCondition();
		return refresh(OPLoginDataType.SZEI_MST, ConsumptionTax.class, ConsumptionTaxManager.class, bean, param,
			isAsync);
	}

	/**
	 * 11：カーゴマスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshOP_CRG_MST(OPLoginData bean, boolean isAsync) {
		CargoSearchCondition param = new CargoSearchCondition();
		return refresh(OPLoginDataType.OP_CRG_MST, Cargo.class, CargoManager.class, bean, param, isAsync);
	}

	/**
	 * 12：OPアイテムマスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshOP_ITEM_MST(OPLoginData bean, boolean isAsync) {
		OPItemSearchCondition param = new OPItemSearchCondition();
		param.setOnlyShowAccountName(true);
		return refresh(OPLoginDataType.OP_ITEM_MST, OPItem.class, OPItemManager.class, bean, param, isAsync);
	}

	/**
	 * 13：支払方法マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshAP_HOH_MST(OPLoginData bean, boolean isAsync) {
		PaymentMethodSearchCondition param = new PaymentMethodSearchCondition();
		return refresh(OPLoginDataType.AP_HOH_MST, PaymentMethod.class, PaymentMethodManager.class, bean, param,
			isAsync);
	}

	/**
	 * 14：銀行口座マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshAP_CBK_MST(OPLoginData bean, boolean isAsync) {
		BankAccountSearchCondition param = new BankAccountSearchCondition();
		return refresh(OPLoginDataType.AP_CBK_MST, BankAccount.class, BankAccountManager.class, bean, param, isAsync);
	}

	/**
	 * 15：国マスタ
	 * 
	 * @param bean
	 * @param isAsync true:非同期処理
	 * @return bean
	 */
	protected OPLoginData refreshCOUNTRY_MST(OPLoginData bean, boolean isAsync) {
		CountrySearchCondition param = new CountrySearchCondition();
		return refresh(OPLoginDataType.COUNTRY_MST, Country.class, CountryManager.class, bean, param, isAsync);
	}

}
