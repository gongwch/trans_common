package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TRANS-OP コードDiv
 */
public enum OPCodeDivision implements TEnumRadio {

	/** ADCOM_RATE_UNIT: ADCOM Rate Unit */
	ADCOM_RATE_UNIT,

	/** CVE: CVE Rate Unit */
	CVE_RATE_UNIT,

	/** BIZ_TYPE: Biz Type */
	BIZ_TYPE, // TODO:DELETE

	/** VC_BIZ_TYPE: VC/VR Biz Type */
	VC_BIZ_TYPE,

	/** TO_BIZ_TYPE: TO Biz Type */
	TO_BIZ_TYPE,

	/** ID_BIZ_TYPE: VC(個品輸送)/VR(個品輸送) Biz Type */
	ID_BIZ_TYPE,

	/** BROKERAGE_RATE_UNIT: Brokerage Rate Unit */
	BROKERAGE_RATE_UNIT,

	/** Load(Export) / Discharge(Import) */
	LD,

	/** Category */
	CATEGORY,

	/** Cargo Type */
	CARGO_TYPE,

	/** Calculate */
	PER,

	/** COA_STATUS: COA Contract Status */
	COA_STATUS,

	/** VC_CP_FORM: VC CP FORM */
	VC_CP_FORM,

	/** CUS_DIV: CUSTOMER TYPE */
	CUS_DIV,

	/** DID: Delivery/Redelivery */
	DID,

	/** DUE_DATE: Due Date */
	DUE_DATE,

	/** FRT_RATE_UNIT: Freight Rate Unit */
	FRT_RATE_UNIT,

	/** FRT_TERM: Freight Term */
	FRT_TERM,

	/** LAYTIME_TERM: Laytime Term */
	LAYTIME_TERM,

	/** LAYTIME EVENT: Laytime Event */
	LAYTIME_EVENT,

	/** LDDC_RATE_UNIT: LD/DC Rate Unit */
	LDDC_RATE_UNIT,

	/** OH_STATUS:OFF-HIRE STATUS */
	OH_STATUS,

	/** OWRSHIP: OWNER SHIP (CHARTER TYPE) */
	OWRSHIP,

	/** PID: PID区分 */
	PID,

	/** QTY_OPT: Quantity Option */
	QTY_OPT,

	/** QTY_UNIT: Quantity Unit */
	QTY_UNIT,

	/** ROUTE: Route */
	ROUTE,

	/** VCC_STATUS: VCC STATUS */
	VCC_STATUS,

	/** VOY_STATUS: VOYAGE STATUS */
	VOY_STATUS,

	/** VSL_SIZE: VESSEL SIZE */
	VSL_SIZE,

	/** VSL_TYPE: VESSEL TYPE */
	VSL_TYPE,

	/** BROWSE: BROWSE HISTORY */
	BROWSE,

	/** INV_STATUS: INV STATUS */
	INV_STATUS,

	/** TCC_STATUS : TC CONTRACT STATUS */
	TCC_STATUS,

	/** FIX_DUR_UNIT : FIX DURATION UNIT */
	FIX_DUR_UNIT,

	/** DEL_REL_STATUS : DELIVERY/REDELIVERY DIVISION */
	DEL_REL_STATUS,

	/** HIRE_RATE_UNIT : HIRE RATE UNIT */
	HIRE_RATE_UNIT,

	/** TCC_ADCOM_RATE_UNIT : TC CONTRACT ADCOM RATE UNIT */
	TCC_ADCOM_RATE_UNIT,

	/** TCC_OWN_COMM_UNIT : TCC CONTRACT OWNER'S A/C COMM RATE UNIT */
	TCC_OWN_COMM_UNIT,

	/** TCC_DIS_COMM_UNIT : TCC DIS COMMISSION UNIT */
	TCC_DIS_COMM_UNIT,

	/** TCC_CUR_UNIT : TC CONTRACT PAY IN */
	TCC_CUR_UNIT,

	/** TCC_CYCLE : CYCLE */
	TCC_CYCLE,

	/** TCC_BROKER_RATE_UNIT : TC BROKERAGE RATE UNIT */
	TCC_BROKER_RATE_UNIT,

	/** TCC ILOHC UNIT */
	TCC_ILOHC_UNIT,

	/** TC_CP_FORM : TC CP FORM */
	TC_CP_FORM,

	/** CRG_GRP : CARGO_GROUP */
	CRG_GRP,

	/** TCC HIRE CALC TYPE */
	TCC_HIRE_CALC_TYPE,

	/** TC_HIRE_STATUS : TC HIRE STATUS */
	TC_HIRE_STATUS,

	/** TC_SOA_DISPLAY_TYPE */
	TC_SOA_DISPLAY_TYPE,

	/** TCI_HIRE_TYPE : TC INVOICE HIRE TYPE */
	TCI_HIRE_TYPE,

	/** TCI_COMM_CVE : TC INVOICE COMMISSION/CVE */
	TCI_COMM_CVE,

	/** TCI_ROUGH_STATUS : TC INVOICE ROUGH ESTIMATE STATUS */
	TCI_ROUGH_STATUS,

	/** TCI_TIMECOUNT_UNIT : TC INVOICE TIME COUNT UNIT */
	TCI_TIMECOUNT_UNIT,

	/** TCI_COM_RATE_UNIT : TC INVOICE COMMISSION RATE UNIT */
	TCI_COM_RATE_UNIT,

	/** TCI_BROKER_RATE_UNIT : TC INVOICE BROKERAGE RATE UNIT */
	TCI_BROKER_RATE_UNIT,

	/** STL_STATUS : STL_STATUS */
	STL_STATUS,

	/** MBEC_STATUS : MBEC_STATUS */
	MBEC_STATUS,

	/** TRADE: TRADE */
	TRADE,

	/** TAX_COUNTRY: 国ごとの消費税設定 */
	TAX_COUNTRY,

	/** USING_PURPOSE: 使用目的(ENGINE/AUX) */
	USING_PURPOSE,

	/** NETPAS: PIRACY CODE */
	NETPAS_PIRACY_CODE,

	/** NETPAS: CANAL CODE */
	NETPAS_CANAL_CODE,

	/** BNKR_SPLY_STATUS: 補油の取込ステータス */
	BNKR_SPLY_STATUS,

	/** BNKR_SPLY_GRADE: 補油の油種区分 */
	BNKR_SPLY_GRADE,

	/** BL_DOC_TYPE: B/Lドキュメント種別 */
	BL_DOC_TYPE,

	/** BL_CRG_TYPE: Category */
	BL_CRG_TYPE,

	/** BL Loading Term */
	BL_LD_TERM,

	/** BL DIscharging Term */
	BL_DC_TERM,

	/** BL Booking Status */
	BL_BOOK_STATE,

	/** BL 計算方法 */
	BL_CALC_TYPE,

	/** WATCHMAN */
	WATCH_MAN,

	/** IMMIGRATION */
	IMMIGRATION,

	/** COMPULSORY GARBAGE */
	COMPULSORY_GARBAGE,

	/** SMR_TIME_REGION */
	SMR_TIME_REGION,

	/** OIL_MAJOR:MAJOR社名 */
	OIL_MAJOR,

	/** OIL_MAJOR_COLOR(FLEET SCHEDULE上表示色) */
	OIL_MAJOR_COLOR,

	/** NOTIFICATION_TYPE:DASH設定の種類 */
	NOTIFICATION_TYPE,

	/** TC CONTRACT: HIREのOTHER単位(減損取崩、乗出費用、追加工事費) */
	TCC_HIRE_OZR_UNIT,

	/** 予算BUNKERING PLACE BY ROUTE */
	BGT_BNKR_PLACE,

	/** 為替レート種別: TTS/TTM/TTBなど */
	EX_RATE_TYPE,

	/** VC INVOICE採番符号 */
	VC_NUM_MNG_SIGN,

	/** VC INVOICE 摘要リスト */
	VC_INV_REMARKS,

	/** VC INVOICE 通貨記号 */
	VC_INV_CUR_SIGN;

	/**
	 * Enumを返す
	 * 
	 * @param value
	 * @return Enum
	 */
	public static OPCodeDivision get(String value) {
		if (value == null) {
			return null;
		}

		for (OPCodeDivision em : values()) {
			if (em.toString().equals(value)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * REF名称を返す
	 * 
	 * @param type
	 * @return REF名称
	 */
	public static String getCaption(OPCodeDivision type) {

		if (type == null) {
			return "";
		}

		switch (type) {
			case COA_STATUS:
				return "COA STATUS";
			case BIZ_TYPE:
				return "BIZ TYPE"; // TODO: DELETE
			case VC_BIZ_TYPE:
				return "VBIZ_TYPE";
			case TO_BIZ_TYPE:
				return "BIZ TYPE";
			case ID_BIZ_TYPE:
				return "BIZ TYPE";
			case FRT_RATE_UNIT:
				return "FREIGHT RATE UNIT";
			case QTY_OPT:
				return "QUANTITY OPTION";
			case QTY_UNIT:
				return "QTY UNIT";
			case VC_CP_FORM:
				return "VC CP FORM";
			case DUE_DATE:
				return "DUE DATE";
			case FRT_TERM:
				return "FRT TERM";
			case ADCOM_RATE_UNIT:
				return "ADCOM RATE UNIT";
			case CVE_RATE_UNIT:
				return "CVE RATE UNIT";
			case ROUTE:
				return "ROUTE";
			case PID:
				return "PID";
			case LDDC_RATE_UNIT:
				return "LDDC RATE UNIT";
			case LAYTIME_TERM:
				return "LAYTIME TERM";
			case LAYTIME_EVENT:
				return "LAYTIME EVENT";
			case BROKERAGE_RATE_UNIT:
				return "BROKERAGE RATE UNIT";
			case LD:
				return "LD";
			case CATEGORY:
				return "CATEGORY";
			case CARGO_TYPE:
				return "CARGO_TYPE";
			case PER:
				return "PER";
			case CUS_DIV:
				return "CUSTOMER DIV";
			case DID:
				return "DE/RE";
			case OH_STATUS:
				return "OFF-HIRE STATUS";
			case OWRSHIP:
				return "OWNER SHIP";
			case VCC_STATUS:
				return "VCC STATUS";
			case VOY_STATUS:
				return "VOY STATUS";
			case VSL_SIZE:
				return "VESSEL SIZE";
			case VSL_TYPE:
				return "VESSEL TYPE";
			case BROWSE:
				return "BROWSE HISTORY";
			case INV_STATUS:
				return "INV STATUS";
			case TCC_STATUS:
				return "TCC STATUS";
			case FIX_DUR_UNIT:
				return "FIX DURATION UNIT";
			case DEL_REL_STATUS:
				return "DELIVERY/REDELIVERY STATUS";
			case HIRE_RATE_UNIT:
				return "HIRE RATE UNIT";
			case TCC_ADCOM_RATE_UNIT:
				return "TC CONTRACT ADCOM RATE UNIT";
			case TCC_OWN_COMM_UNIT:
				return "TCC OWNER'S A/C COMM RATE UNIT";
			case TCC_DIS_COMM_UNIT:
				return "TCC DIS COMMISSION UNIT";
			case TCC_CUR_UNIT:
				return "TCC CUR UNIT";
			case TCC_CYCLE:
				return "TCC CYCLE";
			case TCC_BROKER_RATE_UNIT:
				return "TCC BROKERAGE RATE UNIT";
			case TCC_ILOHC_UNIT:
				return "TCC_ILOHC_UNIT";
			case TC_CP_FORM:
				return "TC CP FORM";
			case CRG_GRP:
				return "CARGO GROUP";
			case TCC_HIRE_CALC_TYPE:
				return "TCC HIRE CALC TYPE";
			case TC_HIRE_STATUS:
				return "TC HIRE STATUS";
			case TC_SOA_DISPLAY_TYPE:
				return "TC SOA DISPLAY TYPE";
			case TCI_HIRE_TYPE:
				return "TCI_HIRE_TYPE";
			case TCI_COMM_CVE:
				return "TCI COMMISSION/CVE ";
			case TCI_ROUGH_STATUS:
				return "TCI ROUGHT ESTIMATE STATUS";
			case TCI_TIMECOUNT_UNIT:
				return "TCI TIME COUNT UNIT";
			case TCI_COM_RATE_UNIT:
				return "TCI COMMISSION RATE UNIT";
			case TCI_BROKER_RATE_UNIT:
				return "TCI BROKERAGE RATE UNIT";
			case STL_STATUS:
				return "STL STATUS";
			case MBEC_STATUS:
				return "MBEC STATUS";
			case TRADE:
				return "TRADE";
			case TAX_COUNTRY:
				return "TAX COUNTRY";
			case USING_PURPOSE:
				return "USING PURPOSE";
			case NETPAS_PIRACY_CODE:
				return "NETPAS PIRACY CODE";
			case NETPAS_CANAL_CODE:
				return "NETPAS CANAL CODE";
			case BNKR_SPLY_STATUS:
				return "BNKR SPLY STATUS";
			case BNKR_SPLY_GRADE:
				return "BNKR SPLY GRADE";
			case BL_CRG_TYPE:
				return "CATEGORY";
			case BL_BOOK_STATE:
				return "BOOKING STATUS";
			case BL_LD_TERM:
				return "LD TERM";
			case BL_DC_TERM:
				return "DC TERM";
			case BL_CALC_TYPE:
				return "CALC TYPE";
			case WATCH_MAN:
				return "WATCHMAN";
			case IMMIGRATION:
				return "IMMIGRATION";
			case COMPULSORY_GARBAGE:
				return "COMPULSORY GARBAGE";
			case SMR_TIME_REGION:
				return "SMR TIME REGION";
			case OIL_MAJOR:
				return "OIL MAJOR";
			case OIL_MAJOR_COLOR:
				return "OIL MAJOR COLOR";
			case NOTIFICATION_TYPE:
				return "NOTIFICATION TYPE";
			case TCC_HIRE_OZR_UNIT:
				return "TCC_HIRE_OZR_UNIT";
			case BGT_BNKR_PLACE:
				return "BUDGET_BUNKERING_PLACE";
			case EX_RATE_TYPE:
				return "EX RATE TYPE";
			case VC_NUM_MNG_SIGN:
				return "VC_NUM_MNG_SIGN";
			case VC_INV_REMARKS:
				return "VC_INV_REMARKS";
			case VC_INV_CUR_SIGN:
				return "VC_INV_CUR_SIGN";
		}

		return "";
	}

	/**
	 * Button名称を返す
	 * 
	 * @param type
	 * @return Button名称
	 */
	public static String getName(OPCodeDivision type) {

		if (type == null) {
			return "";
		}

		switch (type) {
			case COA_STATUS:
				return "COA STATUS";
			case BIZ_TYPE:
				return "BIZ TYPE"; // TODO: DELETE
			case VC_BIZ_TYPE:
				return "VBIZ_TYPE";
			case TO_BIZ_TYPE:
				return "BIZ TYPE";
			case ID_BIZ_TYPE:
				return "BIZ TYPE";
			case FRT_RATE_UNIT:
				return "FRT RATE";
			case QTY_OPT:
				return "QTY OPT";
			case QTY_UNIT:
				return "QTY UNIT";
			case VC_CP_FORM:
				return "VC CP FORM";
			case DUE_DATE:
				return "DUE DATE";
			case FRT_TERM:
				return "FRT TERM";
			case ADCOM_RATE_UNIT:
				return "ADCOM RATE UNIT";
			case CVE_RATE_UNIT:
				return "CVE RATE UNIT";
			case ROUTE:
				return "ROUTE";
			case PID:
				return "PID";
			case LDDC_RATE_UNIT:
				return "LDDC RATE UNIT";
			case LAYTIME_TERM:
				return "LAYTIME TERM";
			case LAYTIME_EVENT:
				return "LAYTIME EVENT";
			case BROKERAGE_RATE_UNIT:
				return "BROKERAGE RATE UNIT";
			case LD:
				return "LD";
			case CATEGORY:
				return "CATEGORY";
			case CARGO_TYPE:
				return "CARGO_TYPE";
			case PER:
				return "PER";
			case CUS_DIV:
				return "CUSTOMER DIV";
			case DID:
				return "DE/RE";
			case OH_STATUS:
				return "OFF-HIRE STATUS";
			case OWRSHIP:
				return "OWNER SHIP";
			case VCC_STATUS:
				return "VCC STATUS";
			case VOY_STATUS:
				return "VOY STATUS";
			case VSL_SIZE:
				return "VESSEL SIZE";
			case VSL_TYPE:
				return "VESSEL TYPE";
			case BROWSE:
				return "BROWSE HISTORY";
			case INV_STATUS:
				return "INV STATUS";
			case TCC_STATUS:
				return "TCC STATUS";
			case FIX_DUR_UNIT:
				return "FIX DURATION UNIT";
			case DEL_REL_STATUS:
				return "DELIVERY/REDELIVERY STATUS";
			case HIRE_RATE_UNIT:
				return "HIRE RATE UNIT";
			case TCC_ADCOM_RATE_UNIT:
				return "TCC ADCOM RATE UNIT";
			case TCC_OWN_COMM_UNIT:
				return "TCC OWNER'S A/C COMM RATE UNIT";
			case TCC_DIS_COMM_UNIT:
				return "TCC DIS COMMISSION UNIT";
			case TCC_CUR_UNIT:
				return "TCC CUR UNIT";
			case TCC_CYCLE:
				return "TCC CYCLE";
			case TCC_BROKER_RATE_UNIT:
				return "TCC BROKERAGE RATE UNIT";
			case TCC_ILOHC_UNIT:
				return "TCC_ILOHC_UNIT";
			case TC_CP_FORM:
				return "TC CP FORM";
			case CRG_GRP:
				return "CARGO GROUP";
			case TCC_HIRE_CALC_TYPE:
				return "TCC HIRE CALC TYPE";
			case TC_HIRE_STATUS:
				return "TC HIRE STATUS";
			case TC_SOA_DISPLAY_TYPE:
				return "TC_SOA_DISPLAY_TYPE";
			case TCI_HIRE_TYPE:
				return "TCI_HIRE_TYPE";
			case TCI_COMM_CVE:
				return "TCI COMMISSION/CVE ";
			case TCI_ROUGH_STATUS:
				return "TCI ROUGHT ESTIMATE STATUS";
			case TCI_TIMECOUNT_UNIT:
				return "TCI TIME COUNT UNIT";
			case TCI_COM_RATE_UNIT:
				return "TCI COMMISSION RATE UNIT";
			case TCI_BROKER_RATE_UNIT:
				return "TCI BROKERAGE RATE UNIT";
			case STL_STATUS:
				return "STL STATUS";
			case MBEC_STATUS:
				return "MBEC STATUS";
			case TRADE:
				return "TRADE";
			case TAX_COUNTRY:
				return "TAX COUNTRY";
			case USING_PURPOSE:
				return "USING PURPOSE";
			case NETPAS_PIRACY_CODE:
				return "NETPAS PIRACY CODE";
			case NETPAS_CANAL_CODE:
				return "NETPAS CANAL CODE";
			case BNKR_SPLY_STATUS:
				return "BNKR SPLY STATUS";
			case BNKR_SPLY_GRADE:
				return "BNKR SPLY GRADE";
			case BL_CRG_TYPE:
				return "CATEGORY";
			case BL_BOOK_STATE:
				return "BOOKING STATE";
			case BL_DC_TERM:
				return "LD TERM";
			case BL_LD_TERM:
				return "DC TERM";
			case BL_CALC_TYPE:
				return "CALC TYPE";
			case WATCH_MAN:
				return "WATCHMAN";
			case IMMIGRATION:
				return "IMMIGRATION";
			case COMPULSORY_GARBAGE:
				return "COMPULSORY GARBAGE";
			case SMR_TIME_REGION:
				return "SMR TIME REGION";
			case OIL_MAJOR:
				return "OIL MAJOR";
			case OIL_MAJOR_COLOR:
				return "OIL MAJOR COLOR";
			case NOTIFICATION_TYPE:
				return "NOTIFICATION TYPE";
			case TCC_HIRE_OZR_UNIT:
				return "TCC_HIRE_OZR_UNIT";
			case BGT_BNKR_PLACE:
				return "BUDGET_BUNKERING_PLACE";
			case EX_RATE_TYPE:
				return "EX RATE TYPE";
			case VC_NUM_MNG_SIGN:
				return "VC_NUM_MNG_SIGN";
			case VC_INV_REMARKS:
				return "VC_INV_REMARKS";
			case VC_INV_CUR_SIGN:
				return "VC_INV_CUR_SIGN";
		}
		return "";
	}

	/**
	 * 値を返す
	 * 
	 * @param obj
	 * @return 値
	 */
	public static String getValue(Object obj) {
		if (Util.isNullOrEmpty(obj)) {
			return null;
		}

		if (obj instanceof OPCodeDivision) {
			return ((OPCodeDivision) obj).toString();
		}

		if (obj instanceof String) {
			return Util.avoidNull(obj);
		}

		return null;
	}

	/**
	 * REF名称取得
	 * 
	 * @return REF名称
	 */
	public String getCaption() {
		return getCaption(this);
	}

	/**
	 * Button名称取得
	 * 
	 * @return Button名称
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * コード区分値を返す
	 * 
	 * @return コード区分値
	 */
	public String getValue() {
		return getValue(this);
	}
}