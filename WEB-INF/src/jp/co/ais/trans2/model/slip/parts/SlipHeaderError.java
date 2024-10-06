package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票ヘッダーエラー
 */
public class SlipHeaderError extends TransferBase {

	/** エラー種類 */
	public static enum ErrorType {
		
		/** データがセットされていない */
		NULL,

		/** 科目に紐付く必要なデータがセットされていない */
		NULL_ON_ITEM,
		
		/** 該当データが存在しない */
		NOT_FOUND,
		
		/** 期間OUT */
		TERM_OUT,

		/** 締められた伝票日付 */
		CLOSED_SLIP_DATE,

		/** 科目の固定部門外 */
		ITEM_FIXED_OUT,
		
		/** 基軸通貨じゃない */
		NOT_KEY_CURRENCY,

		/** 明細行が無い */
		EMPTY_DETAIL,

		/** 自社明細が無い */
		NONE_OWN_DETAIL,

		/** 金額がアンバランス */
		UNBALANCE_AMOUNT,
	}

	/** データ種類 */
	public static enum DataType {

		/** 伝票 */
		SLIP,
		
		/** 伝票日付 */
		SLIP_DATE,

		/** 部門 */
		DEPARTMENT,
		
		/** 科目 */
		ITEM,

		/** 補助科目 */
		SUB_ITEM,

		/** 内訳科目 */
		DETAIL_ITEM,

		/** 通貨 */
		CURRENCY,

		/** 請求区分 */
		BILL_TYPE,
		
		/** 伝票摘要 */
		REMARKS,
		
		/** 社員 */
		EMPLOYEE,
		
		/** 取引先 */
		CUSTOMER,
		
		/** 支払方法 */
		PAY_METHOD,
		
		/** 取引先支払条件 */
		PAY_SETTING,
		
		/** 銀行口座 */
		BANK_ACCOUNT,
		
		/** 通貨レート */
		CURRENCY_RATE,
		
		/** 入力金額 */
		IN_AMOUNT,
		
		/** 邦貨金額 */
		KEY_AMOUNT,
	}
	
	/** エラータイプ */
	protected ErrorType errorType = null;

	/** データタイプ */
	protected DataType dataType = null;

	/** ヘッダ */
	protected SWK_HDR header;

	/** 会社情報 */
	protected AccountConfig config;

	/**
	 * データタイプ
	 * 
	 * @return データタイプ
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * データタイプ
	 * 
	 * @param dataType データタイプ
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * エラータイプ
	 * 
	 * @return エラータイプ
	 */
	public ErrorType getErrorType() {
		return errorType;
	}

	/**
	 * エラータイプ
	 * 
	 * @param errorType エラータイプ
	 */
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	/**
	 * ヘッダ
	 * 
	 * @return ヘッダ
	 */
	public SWK_HDR getHeader() {
		return header;
	}

	/**
	 * ヘッダ
	 * 
	 * @param header ヘッダ
	 */
	public void setHeader(SWK_HDR header) {
		this.header = header;
	}

	/**
	 * 文字列取得
	 * 
	 * @return 文字列
	 */
	public String getDataWord() {

		switch (getDataType()) {
			case SLIP_DATE: // 伝票日付
				return "C00599";

			case DEPARTMENT: // 部門
				return "C00467";

			case ITEM: // 科目
				return config != null ? config.getItemName() : "C00077";

			case SUB_ITEM: // 補助科目
				return config != null ? config.getSubItemName() : "C00488";

			case DETAIL_ITEM: // 内訳科目
				return config != null ? config.getDetailItemName() : "C00025";

			case CURRENCY: // 通貨
				return "C00371";

			case BILL_TYPE: // 請求区分
				return "C10092";

			case REMARKS: // 伝票摘要
				return "C00569";

			case EMPLOYEE: // 社員
				return "C00246";

			case CUSTOMER: // 取引先
				return "C00408";

			case PAY_METHOD: // 支払方法
				return "C00233";

			case PAY_SETTING: // 取引先支払条件
				return "C10756";

			case BANK_ACCOUNT: // 銀行口座
				return "C00857";

			case CURRENCY_RATE: // 通貨レート
				return "C01555";

			case IN_AMOUNT: // 入力金額
				return "C00574";

			case KEY_AMOUNT: // 邦貨金額
				return "C00576";

			default:
				return "";
		}
	}

	/**
	 * 値を返す
	 * 
	 * @return 値
	 */
	public String getValue() {

		switch (getDataType()) {
			case SLIP_DATE: // 伝票日付
				return DateUtil.toYMDString(header.getSWK_DEN_DATE());

			case DEPARTMENT: // 部門
				return header.getSWK_DEP_CODE();

			case ITEM: // 科目
				return header.getSWK_KMK_CODE();

			case SUB_ITEM: // 補助科目
				return header.getSWK_HKM_CODE();

			case DETAIL_ITEM: // 内訳科目
				return header.getSWK_UKM_CODE();

			case CURRENCY: // 通貨
				return header.getSWK_CUR_CODE();

			case BILL_TYPE: // 請求区分
				return header.getSWK_SEI_KBN();

			case REMARKS: // 伝票摘要
				return header.getSWK_TEK_CODE();

			case EMPLOYEE: // 社員
				return header.getSWK_EMP_CODE();

			case CUSTOMER: // 取引先
				return header.getSWK_TRI_CODE();

			case PAY_METHOD: // 支払方法
				return header.getSWK_HOH_CODE();

			case PAY_SETTING: // 取引先支払条件
				return header.getSWK_TJK_CODE();

			case BANK_ACCOUNT: // 銀行口座
				return header.getSWK_CBK_CODE();

			case CURRENCY_RATE: // 通貨レート
				return Util.avoidNull(header.getSWK_CUR_RATE());

			case IN_AMOUNT: // 入力金額
				return Util.avoidNull(header.getSWK_IN_KIN());

			case KEY_AMOUNT: // 邦貨金額
				return Util.avoidNull(header.getSWK_KIN());

			default:
				return "";
		}
	}

	/**
	 * 会社情報
	 * 
	 * @param config 会社情報
	 */
	public void setConfig(AccountConfig config) {
		this.config = config;
	}
}
