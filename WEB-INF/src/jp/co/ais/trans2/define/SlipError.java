package jp.co.ais.trans2.define;

/**
 * 伝票チェック エラー項目分類
 */
public enum SlipError {

	// エラー種類

	/** データがセットされていない */
	NULL,

	/** 該当データが存在しない */
	NOT_FOUND,

	/** 期間OUT */
	TERM_OUT,

	/** データ不正 */
	CLOSED,

	// データ種類

	/** 伝票日付 */
	SLIP_DATE,

	/** 部門コード */
	DEP_CODE,

	/** 部門コード(ヘッダー) */
	HDR_DEP_CODE,

	/** 科目コード */
	KMK_CODE,

	/** 科目コード(ヘッダー) */
	HDR_KMK_CODE,

	/** 補助科目コード */
	HKM_CODE,

	/** 補助科目コード(ヘッダー) */
	HDR_HKM_CODE,

	/** 内訳科目コード */
	UKM_CODE,

	/** 内訳科目コード(ヘッダー) */
	HDR_UKM_CODE,

	/** 通貨コード */
	CUR_CODE,

	/** 通貨コード(ヘッダー) */
	HDR_CUR_CODE,

	/** 請求区分(ヘッダー) */
	HDR_SEI_KBN,

	/** 伝票摘要コード */
	TEK_CODE,

	/** 行摘要コード */
	GYO_TEK_CODE,

	/** 社員コード */
	EMP_CODE,

	/** 社員コード(ヘッダー) */
	HDR_EMP_CODE,

	/** 取引先コード */
	TRI_CODE,

	/** 取引先コード(ヘッダー) */
	HDR_TRI_CODE,

	/** 支払方法コード */
	HOH_CODE,

	/** 取引先支払条件コード */
	TRI_SJ_CODE,

	/** 銀行口座コード */
	CBK_CODE,

	/** 請求区分 */
	SEI_KBN,

	/** 計上会社コード */
	K_KAI_CODE,

	/** 消費税コード */
	ZEI_CODE,

	/** 管理1コード */
	KNR_CODE_1,

	/** 管理2コード */
	KNR_CODE_2,

	/** 管理3コード */
	KNR_CODE_3,

	/** 管理4コード */
	KNR_CODE_4,

	/** 管理5コード */
	KNR_CODE_5,

	/** 管理6コード */
	KNR_CODE_6,

	/** 非会計明細1 */
	HM_1,

	/** 非会計明細2 */
	HM_2,

	/** 非会計明細3 */
	HM_3,

	/** 発生日 */
	HAS_DATE,

	/** レート */
	RATE,

	/** 入力金額 */
	FOREIGN_AMOUNT,

	/** 邦貨金額 */
	BASE_AMOUNT,

	/** 消費税額 */
	TAX_AMOUNT,
	
	/** 科目の固定部門外 */
	ITEM_FIXED_OUT
}
