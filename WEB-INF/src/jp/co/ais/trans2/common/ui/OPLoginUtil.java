package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bunkertype.*;
import jp.co.ais.trans2.model.cargo.*;
import jp.co.ais.trans2.model.code.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
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
 * トラオペログイン共通
 */
public class OPLoginUtil {

	/** 各種ログインデータのマップ<キー,データ> */
	protected static Map<OPLoginDataType, OPLoginData> map = null;

	/** コードマスタリスト */
	protected static List<OP_CODE_MST> codeMstList = null;

	/** 区分ごとに振り分けたコードマスタリスト */
	protected static Map<String, List<OP_CODE_MST>> codeMstMap = null;

	/** 油種リスト */
	protected static List<CM_BNKR_TYPE_MST> bunkerTypeList = null;

	/** 燃料油種コードリスト */
	protected static List<String> bunkerTypeCodeList = null;

	/** 通貨リスト */
	protected static List<Currency> currencyList = null;

	/** 取引先リスト */
	protected static List<Customer> customerList = null;

	/** 部門リスト */
	protected static List<Department> departmentList = null;

	/** 社員リスト */
	protected static List<Employee> employeeList = null;

	/** 船リスト */
	protected static List<Vessel> vesselList = null;

	/** 港リスト */
	protected static List<Port> portList = null;

	/** 港リスト */
	protected static List<Port> lclPortList = null;

	/** 航海リスト */
	protected static List<Voyage> voyageList = null;

	/** 消費税リスト */
	protected static List<ConsumptionTax> taxList = null;

	/** カーゴリスト */
	protected static List<Cargo> cargoList = null;

	/** OPアイテムリスト */
	protected static List<OPItem> opItemList = null;

	// /** 支払条件リスト */
	// protected static List<PaymentSetting> paySettingList = null;

	/** 支払方法リスト */
	protected static List<PaymentMethod> payMethodList = null;

	/** 銀行口座リスト */
	protected static List<BankAccount> bankAccountList = null;

	/** 国リスト */
	protected static List<Country> countryList = null;

	/** 初期化クラス */
	protected static TLoginInitialInterface initClazz = null;

	/**
	 * 初期化クラスを設定する
	 * 
	 * @param clazz
	 */
	public static void setInitialClass(TLoginInitialInterface clazz) {
		initClazz = clazz;
	}

	/**
	 * 初期化クラスを取得する
	 * 
	 * @return clazz
	 */
	public static TLoginInitialInterface getInitialClass() {
		return initClazz;
	}

	/**
	 * 初期化クラスを取得する
	 * 
	 * @return clazz
	 */
	protected static OPLoginInitial getOPInitialClass() {
		if (initClazz instanceof OPLoginInitial) {
			return (OPLoginInitial) initClazz;
		}
		return null;
	}

	/**
	 * 初期処理(TYPE指定、バッチ用)<br>
	 * 同期
	 * 
	 * @param types
	 */
	public static void init(OPLoginDataType... types) {
		if (types == null || types.length == 0) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.init(types);

	}

	/**
	 * 初期処理(TYPE指定、バッチ用)<br>
	 * ASYNC
	 * 
	 * @param types
	 */
	public static void initAsync(OPLoginDataType... types) {
		if (types == null || types.length == 0) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.initAsync(types);
	}

	/**
	 * @param dataType
	 */
	public static void refreshAsync(OPLoginDataType dataType) {
		if (dataType == null) {
			return;
		}
		refreshAsync(dataType.value);
	}

	/**
	 * @param dataTypeValue
	 */
	public static void refreshAsync(int dataTypeValue) {
		OPLoginDataType dataType = OPLoginDataType.get(dataTypeValue);
		if (dataType == null) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.refreshAsync(dataType);

	}

	/**
	 * @param dataType
	 */
	public static void refresh(OPLoginDataType dataType) {
		if (dataType == null) {
			return;
		}
		refresh(dataType.value);
	}

	/**
	 * @param dataTypeValue
	 */
	public static void refresh(int dataTypeValue) {
		OPLoginDataType dataType = OPLoginDataType.get(dataTypeValue);
		if (dataType == null) {
			return;
		}

		OPLoginInitial clazz = getOPInitialClass();
		if (clazz == null) {
			return;
		}

		clazz.refresh(dataType);

	}

	/**
	 * 初期化済みの記憶
	 * 
	 * @param dataType
	 * @param bean
	 */
	public static void saveCache(OPLoginDataType dataType, OPLoginData bean) {
		if (map == null) {
			map = new LinkedHashMap<OPLoginDataType, OPLoginData>();
		}
		map.put(dataType, bean);
	}

	/**
	 * @param dataType
	 * @return 持っているデータ
	 */
	public static OPLoginData getCache(OPLoginDataType dataType) {
		if (map == null) {
			map = new LinkedHashMap<OPLoginDataType, OPLoginData>();
		}
		return map.get(dataType);
	}

	/**
	 * コードマスタリストの取得
	 * 
	 * @return codeMstList コードマスタリスト
	 */
	public static List<OP_CODE_MST> getCodeMstList() {
		return codeMstList;
	}

	/**
	 * 指定区分のコードマスタリストを返す
	 * 
	 * @param div
	 * @param codes コード指定
	 * @return 指定区分のコードマスタリスト
	 */
	public static List<OP_CODE_MST> getCodeMstList(OPCodeDivision div, String... codes) {
		return getCodeMstList(false, div, codes);
	}

	/**
	 * 指定区分のコードマスタリストを返す
	 * 
	 * @param div
	 * @param codes コード指定
	 * @return 指定区分のコードマスタリスト
	 */
	public static List<OP_CODE_MST> getCodeMstListLocal(OPCodeDivision div, String... codes) {
		return getCodeMstList(true, div, codes);
	}

	/**
	 * 指定区分のコードマスタリストを返す
	 * 
	 * @param isLocal true:内航モード、false:外航モード
	 * @param div
	 * @param codes コード指定
	 * @return 指定区分のコードマスタリスト
	 */
	public static List<OP_CODE_MST> getCodeMstList(boolean isLocal, OPCodeDivision div, String... codes) {
		if (codeMstMap == null) {
			return null;
		}

		if (div == null) {
			return null;
		}
		List<OP_CODE_MST> list = codeMstMap.get((isLocal ? 1 : 0) + div.toString());
		if (list == null) {
			return null;
		}

		if (codes == null || codes.length == 0) {
			// コード未指定＝全てのものでフィルターを行う
			List<OP_CODE_MST> filter = new ArrayList<OP_CODE_MST>();
			for (OP_CODE_MST bean : list) {
				filter.add(bean);
			}
			return filter;
		}

		List<OP_CODE_MST> filter = new ArrayList<OP_CODE_MST>();
		for (OP_CODE_MST bean : list) {
			for (String code : codes) { // コード個別指定した場合に表示順が効かなくなる障害対応
				if (code.equals(bean.getCODE())) {
					filter.add(bean);
				}
			}
		}

		return filter;
	}

	/**
	 * コードマスタ設定を返す
	 * 
	 * @param div
	 * @param code
	 * @return コードマスタ設定
	 */
	public static OP_CODE_MST getCodeMst(OPCodeDivision div, String code) {
		return getCodeMst(false, div, code);
	}

	/**
	 * コードマスタ設定を返す
	 * 
	 * @param div
	 * @param code
	 * @return コードマスタ設定
	 */
	public static OP_CODE_MST getCodeMstLocal(OPCodeDivision div, String code) {
		return getCodeMst(true, div, code);
	}

	/**
	 * コードマスタ設定を返す
	 * 
	 * @param isLocal true:内航モード、false:外航モード
	 * @param div
	 * @param code
	 * @return コードマスタ設定
	 */
	public static OP_CODE_MST getCodeMst(boolean isLocal, OPCodeDivision div, String code) {
		if (codeMstMap == null) {
			return null;
		}

		List<OP_CODE_MST> list = getCodeMstList(isLocal, div);
		if (list == null) {
			return null;
		}

		if (Util.isNullOrEmpty(code)) {
			return null;
		}

		for (OP_CODE_MST bean : list) {
			if (code.equals(bean.getCODE())) {
				return bean;
			}
		}

		return null;
	}

	/**
	 * コードマスタリストの設定
	 * 
	 * @param list コードマスタリスト
	 */
	public static void setCodeMstList(List<OP_CODE_MST> list) {
		codeMstList = list;

		// マップ初期化
		if (list == null) {
			codeMstMap = null;
			return;
		}

		codeMstMap = new LinkedHashMap<String, List<OP_CODE_MST>>();
		for (OPCodeDivision div : OPCodeDivision.values()) {
			List<OP_CODE_MST> values1 = new ArrayList<OP_CODE_MST>();
			List<OP_CODE_MST> values2 = new ArrayList<OP_CODE_MST>();
			codeMstMap.put("0" + div.toString(), values1);
			codeMstMap.put("1" + div.toString(), values2);
		}

		for (OP_CODE_MST bean : list) {
			OPCodeDivision div = OPCodeDivision.get(bean.getCODE_DIV());
			if (div == null) {
				continue;
			}

			int local = bean.getLCL_KBN();
			if (local != 1) {
				local = 0;
			}

			List<OP_CODE_MST> values = codeMstMap.get(local + div.toString());
			if (values != null) {
				values.add(bean);
			}
		}
	}

	/**
	 * 油種リストの取得
	 * 
	 * @return bunkerTypeList 油種リスト
	 */
	public static List<CM_BNKR_TYPE_MST> getBunkerTypeList() {
		return bunkerTypeList;
	}

	/**
	 * 燃料油種コードリストを返す
	 * 
	 * @return 燃料油種コードリスト
	 */
	public static List<String> getBunkerTypeCodeList() {
		return bunkerTypeCodeList;
	}

	/**
	 * 油種リストの設定
	 * 
	 * @param list 油種リスト
	 */
	public static void setBunkerTypeList(List<CM_BNKR_TYPE_MST> list) {
		bunkerTypeList = list;

		// 初期化
		bunkerTypeCodeList = new ArrayList<String>();

		if (bunkerTypeList != null) {
			for (CM_BNKR_TYPE_MST bean : bunkerTypeList) {
				bunkerTypeCodeList.add(bean.getBNKR_TYPE_CODE());
			}
		}
	}

	/**
	 * 当該Enumの表示名を返す
	 * 
	 * @param e
	 * @return 表示名
	 */
	public static String getName(OPEnum e) {
		return getName(false, e);
	}

	/**
	 * 当該Enumの表示名を返す
	 * 
	 * @param isLocal true:内航モード、false:外航モード
	 * @param e
	 * @return 表示名
	 */
	public static String getName(boolean isLocal, OPEnum e) {
		if (e == null) {
			return null;
		}

		OP_CODE_MST bean = getCodeMst(isLocal, e.getCodeDivision(), e.getCode());
		if (bean != null) {
			return bean.getCODE_NAME();
		}

		return null;
	}

	/**
	 * 通貨リストの取得
	 * 
	 * @return currencyList 通貨リスト
	 */
	public static List<Currency> getCurrencyList() {
		return currencyList;
	}

	/**
	 * 通貨リストの取得
	 * 
	 * @param condition
	 * @return currencyList 通貨リスト
	 */
	public static List<Currency> getCurrencyList(CurrencySearchCondition condition) {
		return getFilterList(currencyList, condition);
	}

	/**
	 * 通貨リストの設定
	 * 
	 * @param list 通貨リスト
	 */
	public static void setCurrencyList(List<Currency> list) {
		currencyList = list;
	}

	/**
	 * 取引先リストの取得
	 * 
	 * @return customerList 取引先リスト
	 */
	public static List<Customer> getCustomerList() {
		return customerList;
	}

	/**
	 * 取引先リストの取得
	 * 
	 * @param condition
	 * @return customerList 取引先リスト
	 */
	public static List<Customer> getCustomerList(CustomerSearchCondition condition) {
		return getFilterList(customerList, condition);
	}

	/**
	 * 取引先リストの設定
	 * 
	 * @param list 取引先リスト
	 */
	public static void setCustomerList(List<Customer> list) {
		customerList = list;
	}

	/**
	 * 部門リストの取得
	 * 
	 * @return departmentList 部門リスト
	 */
	public static List<Department> getDepartmentList() {
		return departmentList;
	}

	/**
	 * 部門リストの取得
	 * 
	 * @param condition
	 * @return departmentList 部門リスト
	 */
	public static List<Department> getDepartmentList(DepartmentSearchCondition condition) {
		return getFilterList(departmentList, condition);
	}

	/**
	 * 部門リストの設定
	 * 
	 * @param list 部門リスト
	 */
	public static void setDepartmentList(List<Department> list) {
		departmentList = list;
	}

	/**
	 * 社員リストの取得
	 * 
	 * @return employeeList 社員リスト
	 */
	public static List<Employee> getEmployeeList() {
		return employeeList;
	}

	/**
	 * 社員リストの取得
	 * 
	 * @param condition
	 * @return employeeList 社員リスト
	 */
	public static List<Employee> getEmployeeList(EmployeeSearchCondition condition) {
		return getFilterList(employeeList, condition);
	}

	/**
	 * 社員リストの設定
	 * 
	 * @param list 社員リスト
	 */
	public static void setEmployeeList(List<Employee> list) {
		employeeList = list;
	}

	/**
	 * 船リストの取得<br>
	 * SUSPENDDED船は対象外
	 * 
	 * @return vesselList 船リスト
	 */
	public static List<Vessel> getVesselList() {
		VesselSearchCondition condition = new VesselSearchCondition();
		condition.setIncludeSuspended(false);
		condition.setIncludeRelet(true);
		return getFilterList(vesselList, condition);
	}

	/**
	 * 船リストの取得
	 * 
	 * @param condition
	 * @return vesselList 船リスト
	 */
	public static List<Vessel> getVesselList(VesselSearchCondition condition) {
		return getFilterList(vesselList, condition);
	}

	/**
	 * 船リストの設定
	 * 
	 * @param list 船リスト
	 */
	public static void setVesselList(List<Vessel> list) {
		vesselList = list;

		// 船補足情報
		if (vesselList != null) {
			for (Vessel vsl : vesselList) {
				OP_CODE_MST mst = getCodeMst(OPCodeDivision.VSL_TYPE, vsl.getSHIP_TYPE());
				if (mst != null) {
					vsl.setSHIP_TYPE_NAME(mst.getCODE_NAME());
				} else {
					vsl.setSHIP_TYPE_NAME("");
				}
			}
		}
	}

	/**
	 * 港リストの取得
	 * 
	 * @return portList 港リスト
	 */
	public static List<Port> getPortList() {
		return portList;
	}

	/**
	 * 港リストの取得
	 * 
	 * @param isLocal true:内航
	 * @return portList 港リスト
	 */
	public static List<Port> getPortList(boolean isLocal) {
		if (isLocal) {
			return lclPortList;
		} else {
			return portList;
		}
	}

	/**
	 * 港リストの取得
	 * 
	 * @param condition
	 * @return portList 港リスト
	 */
	public static List<Port> getPortList(PortSearchCondition condition) {

		List<Port> oriList = condition.isLocal() ? lclPortList : portList;
		List<Port> list = getFilterList(oriList, condition);

		if (list == null) {
			return null;
		}

		List filter = new ArrayList();

		for (Port bean : list) {

			// 国コード
			if (!Util.isNullOrEmpty(condition.getCOU_CODE())) {
				if (!Util.equals(condition.getCOU_CODE(), bean.getCOU_CODE())) {
					continue;
				}
			}

			// (S)ECA区分
			if (condition.getS_ECA_FLG() != -1) {
				if (condition.getS_ECA_FLG() != bean.getS_ECA_FLG()) {
					continue;
				}
			}

			filter.add(bean);
		}

		return filter;
	}

	/**
	 * 港リストの設定
	 * 
	 * @param list 港リスト
	 */
	public static void setPortList(List<Port> list) {
		portList = list;

		lclPortList = new ArrayList<Port>();
		for (Port p : portList) {
			Port port = p.clone();
			port.setName(Util.nvl(port.getName_N(), port.getName())); // 内航用
			lclPortList.add(port);
		}
	}

	/**
	 * 航海リストの取得
	 * 
	 * @return voyageList 航海リスト
	 */
	public static List<Voyage> getVoyageList() {
		return voyageList;
	}

	/**
	 * 航海リストの取得
	 * 
	 * @param condition
	 * @return voyageList 航海リスト
	 */
	public static List<Voyage> getVoyageList(VoyageSearchCondition condition) {
		return getFilterList(voyageList, condition);
	}

	/**
	 * 航海リストの設定
	 * 
	 * @param list 航海リスト
	 */
	public static void setVoyageList(List<Voyage> list) {
		voyageList = list;
	}

	/**
	 * 消費税リストの取得
	 * 
	 * @return portList 消費税リスト
	 */
	public static List<ConsumptionTax> getConsumptionTaxList() {
		return taxList;
	}

	/**
	 * 消費税リストの取得
	 * 
	 * @param condition
	 * @return portList 消費税リスト
	 */
	public static List<ConsumptionTax> getConsumptionTaxList(ConsumptionTaxSearchCondition condition) {
		return getFilterList(taxList, condition);
	}

	/**
	 * 消費税リストの設定
	 * 
	 * @param list 消費税リスト
	 */
	public static void setConsumptionTaxList(List<ConsumptionTax> list) {
		taxList = list;
	}

	/**
	 * カーゴリストの取得
	 * 
	 * @return cargoList カーゴリスト
	 */
	public static List<Cargo> getCargoList() {
		return cargoList;
	}

	/**
	 * カーゴリストの取得
	 * 
	 * @param condition
	 * @return cargoList カーゴリスト
	 */
	public static List<Cargo> getCargoList(CargoSearchCondition condition) {
		return getFilterList(cargoList, condition);
	}

	/**
	 * カーゴリストの設定
	 * 
	 * @param list カーゴリスト
	 */
	public static void setCargoList(List<Cargo> list) {
		cargoList = list;
	}

	/**
	 * OPアイテムリストの取得
	 * 
	 * @return opItemList OPアイテムリスト
	 */
	public static List<OPItem> getOPItemList() {
		return opItemList;
	}

	/**
	 * OPアイテムの取得
	 * 
	 * @param itemCode
	 * @return opItem OPアイテム
	 */
	public static OPItem getOPItem(String itemCode) {
		if (opItemList == null || Util.isNullOrEmpty(itemCode)) {
			return null;
		}

		for (OPItem item : opItemList) {
			if (item.getITEM_CODE().equals(itemCode)) {
				return item;
			}
		}

		return null;
	}

	/**
	 * OPアイテムリストの取得
	 * 
	 * @param condition
	 * @return opItemList OPアイテムリスト
	 */
	public static List<OPItem> getOPItemList(OPItemSearchCondition condition) {
		List<OPItem> list = getFilterList(opItemList, condition);

		if (list == null) {
			return null;
		}

		List filter = new ArrayList();

		for (OPItem bean : list) {

			// 契約タイプ
			if (condition.getContractType() != null) {
				if (condition.getContractType().value != bean.getCTRT_TYPE()) {
					continue;
				}
			}

			// SA区分
			if (condition.getSa() != null) {
				if (condition.getSa().value != bean.getSA_KBN()) {
					continue;
				}
			}

			// DC区分
			if (condition.getDc() != null) {
				if (condition.getDc().value != bean.getDC_KBN()) {
					continue;
				}
			}

			// ADDCOM区分
			if (condition.getADCOM_KBN() != -1) {
				if (condition.getADCOM_KBN() != bean.getADCOM_KBN()) {
					continue;
				}
			}

			// BRKRG区分
			if (condition.getBRKR_KBN() != -1) {
				if (condition.getBRKR_KBN() != bean.getBRKR_KBN()) {
					continue;
				}
			}

			// 制御区分
			if (condition.getItemControlDivision() != null) {
				if (!condition.getItemControlDivision().value.equals(bean.getITEM_CTL_KBN())) {
					continue;
				}
			}

			// SUB 区分
			if (condition.getItemSubDivision() != null) {
				if (!condition.getItemSubDivision().value.equals(bean.getITEM_SUB_KBN())) {
					continue;
				}
			}

			// SUB 区分複数
			if (condition.getItemSubDivisions() != null) {
				OPItemSubDivision sub = OPItemSubDivision.get(bean.getITEM_SUB_KBN());
				if (!condition.getItemSubDivisions().contains(sub)) {
					continue;
				}
			}

			// アイテムSUB区分(BUNKER_TYPE)
			if (condition.getBrkrTypeCode() != null) {
				if (!condition.getBrkrTypeCode().equals(bean.getITEM_SUB_KBN())) {
					continue;
				}
			}

			if (ClientConfig.isFlagOn("trans.op.use.item.ownr.ship")) {
				// OWNR_SHIP_CODE
				if (condition.getOwnrShipCode() != null) {
					if (!condition.getOwnrShipCode().equals(bean.getOWNR_SHIP_CODE())) {
						continue;
					}
				}
			}

			filter.add(bean);
		}

		return filter;
	}

	/**
	 * OPアイテムリストの設定
	 * 
	 * @param list OPアイテムリスト
	 */
	public static void setOPItemList(List<OPItem> list) {
		opItemList = list;
	}

	// /**
	// * 支払条件リストの取得
	// *
	// * @return paySettingList 支払条件リスト
	// */
	// public static List<PaymentSetting> getPaySettingList() {
	// return paySettingList;
	// }
	//
	// /**
	// * 支払条件リストの取得
	// *
	// * @param condition
	// * @return paySettingList 支払条件リスト
	// */
	// public static List<PaymentSetting> getPaySettingList(PaymentSettingSearchCondition condition) {
	// return getFilterList(paySettingList, condition);
	// }
	//
	// /**
	// * 支払条件リストの設定
	// *
	// * @param list 支払条件リスト
	// */
	// public static void setPaySettingList(List<PaymentSetting> list) {
	// paySettingList = list;
	// }

	/**
	 * 支払方法リストの取得
	 * 
	 * @return payMethodList 支払方法リスト
	 */
	public static List<PaymentMethod> getPayMethodList() {
		return payMethodList;
	}

	/**
	 * 支払方法リストの取得
	 * 
	 * @param condition
	 * @return payMethodList 支払方法リスト
	 */
	public static List<PaymentMethod> getPayMethodList(PaymentMethodSearchCondition condition) {
		return getFilterList(payMethodList, condition);
	}

	/**
	 * 支払方法リストの設定
	 * 
	 * @param list 支払方法リスト
	 */
	public static void setPayMethodList(List<PaymentMethod> list) {
		payMethodList = list;
	}

	/**
	 * 銀行口座リストの取得
	 * 
	 * @return bankAccountList 銀行口座リスト
	 */
	public static List<BankAccount> getBankAccountList() {
		return bankAccountList;
	}

	/**
	 * 銀行口座リストの取得
	 * 
	 * @param condition
	 * @return bankAccountList 銀行口座リスト
	 */
	public static List<BankAccount> getBankAccountList(BankAccountSearchCondition condition) {
		return getFilterList(bankAccountList, condition);
	}

	/**
	 * 銀行口座リストの設定
	 * 
	 * @param list 銀行口座リスト
	 */
	public static void setBankAccountList(List<BankAccount> list) {
		bankAccountList = list;
	}

	/**
	 * 国リストの取得
	 * 
	 * @return countryList 国リスト
	 */
	public static List<Country> getCountryList() {
		return countryList;
	}

	/**
	 * 国リストの取得
	 * 
	 * @param condition
	 * @return countryList 国リスト
	 */
	public static List<Country> getCountryList(CountrySearchCondition condition) {
		return getFilterList(countryList, condition);
	}

	/**
	 * 国リストの設定
	 * 
	 * @param list 国リスト
	 */
	public static void setCountryList(List<Country> list) {
		countryList = list;
	}

	/**
	 * フィルターリストの取得
	 * 
	 * @param list
	 * @param condition
	 * @return list フィルターリスト
	 */
	public static List getFilterList(List<? extends FilterableEntity> list, FilterableCondition condition) {
		if (list == null) {
			return null;
		}

		List filter = new ArrayList();

		for (FilterableEntity bean : list) {
			// 会社コード

			// コード
			if (condition.getCode() != null) {
				if (!isEquals(bean.getCode(), condition.getCode())) {
					continue;
				}
			}

			// コード前方一致
			if (condition.getCodeLike() != null) {
				if (!isStartsWith(bean.getCode(), condition.getCodeLike())) {
					continue;
				}
			}

			// 略称
			if (condition.getNamesLike() != null) {
				if (!isContains(bean.getNames(), condition.getNamesLike())) {
					continue;
				}
			}

			// 検索名称
			if (condition.getNamekLike() != null) {
				if (!isContains(bean.getNamek(), condition.getNamekLike())) {
					continue;
				}
			}

			// 開始コード
			if (condition.getCodeFrom() != null) {
				if (isSmaller(bean.getCode(), condition.getCodeFrom())) {
					continue;
				}
			}

			// 終了コード
			if (condition.getCodeTo() != null) {
				if (isBigger(bean.getCode(), condition.getCodeTo())) {
					continue;
				}
			}

			// コード配列
			if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
				if (!isContains(condition.getCodeList(), bean.getCode())) {
					continue;
				}
			}

			// 有効期間
			if (condition.getValidTerm() != null) {
				if (!isBetween(condition.getValidTerm(), bean.getDateFrom(), bean.getDateTo())) {
					continue;
				}
			}

			// カスタマイズフィルター
			if (!isEquals(bean, condition)) {
				continue;
			}

			filter.add(bean);
		}

		if (filter.isEmpty()) {
			return null;
		}

		return filter;
	}

	/**
	 * @param bean
	 * @param condition
	 * @return true:等しい
	 */
	public static boolean isEquals(FilterableEntity bean, FilterableCondition condition) {

		// 個別対応
		if (bean instanceof ConsumptionTax && condition instanceof ConsumptionTaxSearchCondition) {
			// 消費税の場合、税区分をフィルターする

			ConsumptionTax tax = (ConsumptionTax) bean;
			ConsumptionTaxSearchCondition param = (ConsumptionTaxSearchCondition) condition;

			if (TaxType.SALES != tax.getTaxType() && param.isHasSales() // 売上
				|| TaxType.PURCHAESE != tax.getTaxType() && param.isHasPurcharse()) // 仕入
			{
				return false;
			}
		} else if (bean instanceof Customer && condition instanceof CustomerSearchCondition) {
			// 取引先はフラグにより詳しく分ける

			Customer customer = (Customer) bean;
			CustomerSearchCondition param = (CustomerSearchCondition) condition;

			if (CustomerType.BOTH == param.getType()) {
				if (!customer.isClient() && !customer.isPurchase()) {
					return false;
				}
			} else if (CustomerType.CUSTOMER == param.getType()) {
				if (!customer.isClient()) {
					return false;
				}
			} else if (CustomerType.VENDOR == param.getType()) {
				if (!customer.isPurchase()) {
					return false;
				}
			}

			// 取引先区分
			if (param.isCharterer() != null && param.isCharterer() != customer.isCharterer()) {
				return false;
			}
			if (param.isOwner() != null && param.isOwner() != customer.isOwner()) {
				return false;
			}
			if (param.isPortAgent() != null && param.isPortAgent() != customer.isPortAgent()) {
				return false;
			}
			if (param.isSupplier() != null && param.isSupplier() != customer.isSupplier()) {
				return false;
			}
			if (param.isBroker() != null && param.isBroker() != customer.isBroker()) {
				return false;
			}
			if (param.isOther() != null && param.isOther() != customer.isOther()) {
				return false;
			}
			if (param.isShipper() != null && param.isShipper() != customer.isShipper()) {
				return false;
			}
			if (param.isConsignee() != null && param.isConsignee() != customer.isConsignee()) {
				return false;
			}
			if (param.isNotifyParty() != null && param.isNotifyParty() != customer.isNotifyParty()) {
				return false;
			}
			if (param.isFowarder() != null && param.isFowarder() != customer.isFowarder()) {
				return false;
			}
			if (param.isBunkerTrader() != null && param.isBunkerTrader() != customer.isBunkerTrader()) {
				return false;
			}
			if (param.isBunkerSupplier() != null && param.isBunkerSupplier() != customer.isBunkerSupplier()) {
				return false;
			}

		} else if (bean instanceof PaymentSetting && condition instanceof PaymentSettingSearchCondition) {
			// 支払条件の場合
			PaymentSetting ps = (PaymentSetting) bean;
			PaymentSettingSearchCondition param = (PaymentSettingSearchCondition) condition;

			// 取引先コード条件がある場合は、該当しないデータは除外
			if (!Util.isNullOrEmpty(param.getCustomerCode())) {
				if (ps.getCustomer() == null || !Util.equals(ps.getCustomer().getCode(), param.getCustomerCode())) {
					return false;
				}
			}

		} else if (bean instanceof PaymentMethod && condition instanceof PaymentMethodSearchCondition) {
			// 支払方法の場合
			PaymentMethod paymentMethod = (PaymentMethod) bean;
			PaymentMethodSearchCondition param = (PaymentMethodSearchCondition) condition;

			// 0:社員支払
			if ((!paymentMethod.isUseEmployeePayment() && param.isUseEmployeePayment())
				|| (paymentMethod.isUseEmployeePayment() && !param.isUseEmployeePayment())) {
				return false;
			}

			// 1:社外支払
			if ((!paymentMethod.isUseExPayment() && param.isUseExPayment())
				|| (paymentMethod.isUseExPayment() && !param.isUseExPayment())) {
				return false;
			}
		} else if (bean instanceof BankAccount && condition instanceof BankAccountSearchCondition) {
			// 銀行口座の場合
			BankAccount bankAccount = (BankAccount) bean;
			BankAccountSearchCondition param = (BankAccountSearchCondition) condition;

			// 社員ＦＢ区分
			if ((!bankAccount.isUseEmployeePayment() && param.isUseEmployeePayment())
				|| (bankAccount.isUseEmployeePayment() && !param.isUseEmployeePayment())) {
				return false;
			}

			// 社外ＦＢ区分
			if ((!bankAccount.isUseExPayment() && param.isUseExPayment())
				|| (bankAccount.isUseExPayment() && !param.isUseExPayment())) {
				return false;
			}
		} else if (bean instanceof Vessel && condition instanceof VesselSearchCondition) {

			Vessel vsl = (Vessel) bean;
			VesselSearchCondition param = (VesselSearchCondition) condition;

			// SUSPENDDED
			if (BooleanUtil.toBoolean(vsl.getSUSPENDED_FLG()) && !param.isIncludeSuspended()) {
				// 船がSUSPENDDED且つ条件は含まない場合、対象外
				return false;
			}

			// RELET
			if (BooleanUtil.toBoolean(vsl.getRELET_FLG()) && !param.isIncludeRelet()) {
				// 船がRELET且つ条件は含まない場合、対象外
				return false;
			}
		} else if (bean instanceof Cargo && condition instanceof CargoSearchCondition) {
			// 貨物
			Cargo crg = (Cargo) bean;
			CargoSearchCondition param = (CargoSearchCondition) condition;
			if (!Util.isNullOrEmpty(param.getCategory())) {
				if (!isEquals(crg.getCATEGORY(), param.getCategory())) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * @param a
	 * @param b
	 * @return true: a == b
	 */
	public static boolean isEquals(String a, String b) {
		return Util.avoidNullNT(a).equals(Util.avoidNullNT(b));
	}

	/**
	 * @param a
	 * @param str
	 * @return true: a start with str
	 */
	public static boolean isStartsWith(String a, String str) {
		return Util.avoidNullNT(a).startsWith(Util.avoidNullNT(str));
	}

	/**
	 * @param a
	 * @param b
	 * @return true: a < b
	 */
	public static boolean isSmaller(String a, String b) {
		return Util.avoidNullNT(a).compareTo(Util.avoidNullNT(b)) < 0;
	}

	/**
	 * @param a
	 * @param b
	 * @return true: a > b
	 */
	public static boolean isBigger(String a, String b) {
		return Util.avoidNullNT(a).compareTo(Util.avoidNullNT(b)) > 0;
	}

	/**
	 * @param a
	 * @param str
	 * @return true: a contains str
	 */
	public static boolean isContains(String a, String str) {
		return Util.avoidNullNT(a).contains(Util.avoidNullNT(str));
	}

	/**
	 * @param list
	 * @param str
	 * @return true: list contains str
	 */
	public static boolean isContains(List<String> list, String str) {
		return list.contains(Util.avoidNullNT(str));
	}

	/**
	 * @param a
	 * @param from
	 * @param to
	 * @return a between from and to
	 */
	public static boolean isBetween(Date a, Date from, Date to) {
		if (a == null) {
			return false;
		}

		if (from != null) {
			if (!Util.isSmallerThenByYMDHMS(from, a)) {
				// not from <= a
				return false;
			}
		}

		if (to != null) {
			if (!Util.isSmallerThenByYMDHMS(a, to)) {
				// not a <= to
				return false;
			}
		}

		return true;
	}
}
