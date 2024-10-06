package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票明細エラー
 */
public class SlipDetailError extends TransferBase {

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

		/** 基軸通貨以外 */
		NOT_KEY_CURRENCY,
	}

	/** データ種類 */
	public static enum DataType {

		/** 伝票日付 */
		SLIP_DATE,

		/** 計上会社コード */
		TRANSFER_COMPANY,

		/** 部門コード */
		DEPARTMENT,

		/** 科目コード */
		ITEM,

		/** 補助科目コード */
		SUB_ITEM,

		/** 内訳科目コード */
		DETAIL_ITEM,

		/** 通貨コード */
		CURRENCY,

		/** 行摘要コード */
		REMARKS,

		/** 社員コード */
		EMPLOYEE,

		/** 取引先コード */
		CUSTOMER,

		/** 消費税コード */
		CONSUMPTION_TAX,

		/** 管理1コード */
		MANAGE1,

		/** 管理2コード */
		MANAGE2,

		/** 管理3コード */
		MANAGE3,

		/** 管理4コード */
		MANAGE4,

		/** 管理5コード */
		MANAGE5,

		/** 管理6コード */
		MANAGE6,

		/** 発生日 */
		ACCRUAL_DATE,

		/** レート */
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

	/** 行番号 */
	protected int rowNo = -1;

	/** 対象データ */
	protected SWK_DTL detail;

	/** 会社情報 */
	protected AccountConfig accountConfig;

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
	 * 対象データ
	 * 
	 * @return 対象データ
	 */
	public SWK_DTL getDetail() {
		return detail;
	}

	/**
	 * 対象データ
	 * 
	 * @param dtl
	 */
	public void setDetail(SWK_DTL dtl) {
		this.detail = dtl;
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
	 * 行番号
	 * 
	 * @return 行番号
	 */
	public int getRowNo() {
		return rowNo;
	}

	/**
	 * 行番号
	 * 
	 * @param rowNo 行番号
	 */
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
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

			case TRANSFER_COMPANY: // 計上会社
				return "C01052";

			case DEPARTMENT: // 部門
				return "C00467";

			case ITEM: // 科目
				return getItemName();

			case SUB_ITEM: // 補助科目
				return getSubItemName();

			case DETAIL_ITEM: // 内訳科目
				return getDetailItemName();

			case CURRENCY: // 通貨
				return "C00371";

			case REMARKS: // 行摘要
				return "C00119";

			case EMPLOYEE: // 社員
				return "C00246";

			case CUSTOMER: // 取引先
				return "C00408";

			case CONSUMPTION_TAX: // 消費税
				return "C00286";

			case MANAGE1: // 管理1
				return getManagement1Name();

			case MANAGE2: // 管理2
				return getManagement2Name();

			case MANAGE3: // 管理3
				return getManagement3Name();

			case MANAGE4: // 管理4
				return getManagement4Name();

			case MANAGE5: // 管理5
				return getManagement5Name();

			case MANAGE6: // 管理6
				return getManagement6Name();

			case ACCRUAL_DATE: // 発生日
				return "C00431";

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
	 * 科目表示名
	 * 
	 * @return 科目表示名
	 */
	protected String getItemName() {
		String name = accountConfig != null ? accountConfig.getItemName() : "C00077";
		return Util.isNullOrEmpty(name) ? "C00077" : name;
	}

	/**
	 * 補助科目表示名
	 * 
	 * @return 補助科目表示名
	 */
	protected String getSubItemName() {
		String name = accountConfig != null ? accountConfig.getSubItemName() : "C00488";
		return Util.isNullOrEmpty(name) ? "C00488" : name;
	}

	/**
	 * 内訳科目表示名
	 * 
	 * @return 内訳科目表示名
	 */
	protected String getDetailItemName() {
		String name = accountConfig != null ? accountConfig.getDetailItemName() : "C00025";
		return Util.isNullOrEmpty(name) ? "C00025" : name;
	}

	/**
	 * 管理1表示名
	 * 
	 * @return 管理1表示名
	 */
	protected String getManagement1Name() {
		String name = accountConfig != null ? accountConfig.getManagement1Name() : "C01025";
		return Util.isNullOrEmpty(name) ? "C01025" : name;
	}

	/**
	 * 管理2表示名
	 * 
	 * @return 管理2表示名
	 */
	protected String getManagement2Name() {
		String name = accountConfig != null ? accountConfig.getManagement2Name() : "C01027";
		return Util.isNullOrEmpty(name) ? "C01027" : name;
	}

	/**
	 * 管理3表示名
	 * 
	 * @return 管理3表示名
	 */
	protected String getManagement3Name() {
		String name = accountConfig != null ? accountConfig.getManagement3Name() : "C01029";
		return Util.isNullOrEmpty(name) ? "C01029" : name;
	}

	/**
	 * 管理4表示名
	 * 
	 * @return 管理4表示名
	 */
	protected String getManagement4Name() {
		String name = accountConfig != null ? accountConfig.getManagement4Name() : "C01031";
		return Util.isNullOrEmpty(name) ? "C01031" : name;
	}

	/**
	 * 管理5表示名
	 * 
	 * @return 管理5表示名
	 */
	protected String getManagement5Name() {
		String name = accountConfig != null ? accountConfig.getManagement5Name() : "C01033";
		return Util.isNullOrEmpty(name) ? "C01033" : name;
	}

	/**
	 * 管理6表示名
	 * 
	 * @return 管理6表示名
	 */
	protected String getManagement6Name() {
		String name = accountConfig != null ? accountConfig.getManagement6Name() : "C01035";
		return Util.isNullOrEmpty(name) ? "C01035" : name;
	}

	/**
	 * 値を返す
	 * 
	 * @return 値
	 */
	public String getValue() {

		switch (getDataType()) {
			case SLIP_DATE: // 伝票日付
				return DateUtil.toYMDString(detail.getSWK_DEN_DATE());

			case TRANSFER_COMPANY: // 計上会社
				return detail.getSWK_K_KAI_CODE();

			case DEPARTMENT: // 部門
				return detail.getSWK_DEP_CODE();

			case ITEM: // 科目
				return detail.getSWK_KMK_CODE();

			case SUB_ITEM: // 補助科目
				return detail.getSWK_HKM_CODE();

			case DETAIL_ITEM: // 内訳科目
				return detail.getSWK_UKM_CODE();

			case CURRENCY: // 通貨
				return detail.getSWK_CUR_CODE();

			case REMARKS: // 行摘要
				return detail.getSWK_GYO_TEK_CODE();

			case EMPLOYEE: // 社員
				return detail.getSWK_EMP_CODE();

			case CUSTOMER: // 取引先
				return detail.getSWK_TRI_CODE();

			case CONSUMPTION_TAX: // 消費税
				return detail.getSWK_ZEI_CODE();

			case MANAGE1: // 管理1
				return detail.getSWK_KNR_CODE_1();

			case MANAGE2: // 管理2
				return detail.getSWK_KNR_CODE_2();

			case MANAGE3: // 管理3
				return detail.getSWK_KNR_CODE_3();

			case MANAGE4: // 管理4
				return detail.getSWK_KNR_CODE_4();

			case MANAGE5: // 管理5
				return detail.getSWK_KNR_CODE_5();

			case MANAGE6: // 管理6
				return detail.getSWK_KNR_CODE_6();

			case ACCRUAL_DATE: // 発生日
				return DateUtil.toYMDString(detail.getHAS_DATE());

			case CURRENCY_RATE: // 通貨レート
				return Util.avoidNull(detail.getSWK_CUR_RATE());

			case IN_AMOUNT: // 入力金額
				return Util.avoidNull(detail.getSWK_IN_KIN());

			case KEY_AMOUNT: // 邦貨金額
				return Util.avoidNull(detail.getSWK_KIN());

			default:
				return "";
		}
	}

	/**
	 * 会社情報
	 * 
	 * @param config 会社情報
	 */
	public void setAccountConfig(AccountConfig config) {
		this.accountConfig = config;
	}
}
