package jp.co.ais.trans2.common.ui;

import jp.co.ais.trans.common.util.*;
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
 * OP各種データ
 */
public enum OPLoginDataType {

	/** 1：OPアイテムマスタ */
	OP_CODE_MST(1),

	/** 2：油種マスタ */
	CM_BNKR_TYPE_MST(2),

	/** 3：通貨マスタ */
	CUR_MST(3),

	/** 4：取引先マスタ */
	TRI_MST(4),

	/** 5：部門マスタ */
	BMN_MST(5),

	/** 6：社員マスタ */
	EMP_MST(6),

	/** 7：船マスタ */
	CM_VESSEL_MST(7),

	/** 8：港マスタ */
	CM_PORT_MST(8),

	/** 9：航海マスタ */
	CM_VOYAGE_MST(9),

	/** 10：消費税マスタ */
	SZEI_MST(10),

	/** 11：カーゴマスタ */
	OP_CRG_MST(11),

	/** 12：OPアイテムマスタ */
	OP_ITEM_MST(12),

	/** 13：支払方法マスタ */
	AP_HOH_MST(13),

	/** 14：銀行口座マスタ */
	AP_CBK_MST(14),

	/** 15：国マスタ */
	COUNTRY_MST(15);

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value
	 */
	OPLoginDataType(int value) {
		this.value = value;
	}

	/**
	 * @param value
	 * @return Enum
	 */
	public static OPLoginDataType get(int value) {
		for (OPLoginDataType e : values()) {
			if (e.value == value) {
				return e;
			}
		}

		return null;
	}

	/**
	 * @param e データ区分
	 * @return キャッシュファイル名
	 */
	public static String getFileName(OPLoginDataType e) {
		if (e == null) {
			return null;
		}

		return e.value + "_" + e.toString() + ".op";
	}

	/**
	 * ソートキーの取得(基本は識別キーを返す。油種だけ特殊)
	 * 
	 * @param e
	 * @param obj
	 * @return ソートキー
	 */
	public static String getSortKey(OPLoginDataType e, Object obj) {
		if (e == null || obj == null) {
			return "";
		}

		if (e == CM_BNKR_TYPE_MST) {
			// 油種特殊
			CM_BNKR_TYPE_MST entity = (CM_BNKR_TYPE_MST) obj;
			StringBuilder sb = new StringBuilder();
			sb.append(entity.getKAI_CODE());
			sb.append("<>");
			sb.append(StringUtil.fillLeft(Integer.toString(entity.getDISP_ODR()), 5, '0'));
			sb.append("<>");
			sb.append(entity.getBNKR_TYPE_CODE());
			return sb.toString();

		} else if (e == OP_CODE_MST) {
			// CODE特殊
			OP_CODE_MST entity = (OP_CODE_MST) obj;
			StringBuilder sb = new StringBuilder();
			sb.append(entity.getKAI_CODE());
			sb.append("<>");
			sb.append(entity.getCODE_DIV());
			sb.append("<>");
			sb.append(StringUtil.fillLeft(Integer.toString(entity.getDISP_ODR()), 5, '0'));
			sb.append("<>");
			sb.append(entity.getCODE());
			return sb.toString();
		}

		return getKey(e, obj);
	}

	/**
	 * @param e
	 * @param obj
	 * @return 識別キー
	 */
	public static String getKey(OPLoginDataType e, Object obj) {
		if (e == null || obj == null) {
			return "";
		}

		switch (e) {

			case OP_CODE_MST:
			// 1：OPアイテム
			{
				OP_CODE_MST entity = (OP_CODE_MST) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getCODE_DIV());
				sb.append("<>");
				sb.append(entity.getCODE());
				sb.append("<>");
				sb.append(entity.getLCL_KBN());
				return sb.toString();
			}

			case CM_BNKR_TYPE_MST:
			// 2：油種
			{
				CM_BNKR_TYPE_MST entity = (CM_BNKR_TYPE_MST) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getBNKR_TYPE_CODE());
				return sb.toString();
			}

			case CUR_MST:
			// 3：通貨
			{
				Currency entity = (Currency) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case TRI_MST:
			// 4：取引先
			{
				Customer entity = (Customer) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case BMN_MST:
			// 5：部門
			{
				Department entity = (Department) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case EMP_MST:
			// 6：社員
			{
				Employee entity = (Employee) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCode());
				return sb.toString();
			}

			case CM_VESSEL_MST:
			// 7：船
			{
				Vessel entity = (Vessel) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case CM_PORT_MST:
			// 8：港
			{
				Port entity = (Port) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case CM_VOYAGE_MST:
			// 9：航海
			{
				Voyage entity = (Voyage) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case SZEI_MST:
			// 10：消費税
			{
				ConsumptionTax entity = (ConsumptionTax) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case OP_CRG_MST:
			// 11：カーゴ
			{
				Cargo entity = (Cargo) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case OP_ITEM_MST:
			// 12：OPアイテム
			{
				OPItem entity = (OPItem) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getKAI_CODE());
				sb.append("<>");
				sb.append(entity.getITEM_CODE());
				return sb.toString();
			}

			case AP_HOH_MST:
			// 13：支払方法
			{
				PaymentMethod entity = (PaymentMethod) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case AP_CBK_MST:
			// 14：銀行口座
			{
				BankAccount entity = (BankAccount) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCompanyCode());
				sb.append("<>");
				sb.append(entity.getCode());
				return sb.toString();
			}

			case COUNTRY_MST:
			// 15：国
			{
				Country entity = (Country) obj;
				StringBuilder sb = new StringBuilder();
				sb.append(entity.getCode());
				return sb.toString();
			}
		}

		return "";
	}

	/**
	 * @param e
	 * @return クラス取得
	 */
	public static Class getEntityClass(OPLoginDataType e) {
		if (e == null) {
			return null;
		}

		switch (e) {

			case OP_CODE_MST:
			// 1：OPアイテム
			{
				return OP_CODE_MST.class;
			}

			case CM_BNKR_TYPE_MST:
			// 2：油種
			{
				return CM_BNKR_TYPE_MST.class;

			}

			case CUR_MST:
			// 3：通貨
			{
				return Currency.class;

			}

			case TRI_MST:
			// 4：取引先
			{
				return Customer.class;

			}

			case BMN_MST:
			// 5：部門
			{
				return Department.class;

			}

			case EMP_MST:
			// 6：社員
			{
				return Employee.class;

			}

			case CM_VESSEL_MST:
			// 7：船
			{
				return Vessel.class;

			}

			case CM_PORT_MST:
			// 8：港
			{
				return Port.class;

			}

			case CM_VOYAGE_MST:
			// 9：航海
			{
				return Voyage.class;

			}

			case SZEI_MST:
			// 10：消費税
			{
				return ConsumptionTax.class;

			}

			case OP_CRG_MST:
			// 11：カーゴ
			{
				return Cargo.class;

			}

			case OP_ITEM_MST:
			// 12：OPアイテム
			{
				return OPItem.class;

			}

			case AP_HOH_MST:
			// 13：支払方法
			{
				return PaymentMethod.class;

			}

			case AP_CBK_MST:
			// 14：銀行口座
			{
				return BankAccount.class;

			}

			case COUNTRY_MST:
			// 15：国
			{
				return Country.class;

			}
		}

		return null;
	}
}
