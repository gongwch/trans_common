package jp.co.ais.trans2.common.model.excel;

import jp.co.ais.trans2.common.excel.*;

/**
 * 伝票エクスポート処理 エクセル列定義
 */
public enum SlipInstructionExcelColumn implements ExcelImportableColumn {

	/** 会社コード */
	KAI_CODE("C00596", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 伝票日付 */
	SLIP_DATE("C00599", ExcelColumnType.DATE, true, 10, 3000),
	/** 伝票番号 */
	SLIP_NO("C00605", ExcelColumnType.ALPHANUMERIC, true, 20, 6000),
	/** 行番号 */
	GYO_NO("C01588", ExcelColumnType.INTEGER, true, 5, 2000),
	/** 受付部門コード */
	U_DEP_CODE("C04248", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 伝票摘要コード */
	TEK_CODE("C01652", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 伝票摘要 */
	TEK("C00569", ExcelColumnType.STRING, true, 80, 13440),
	/** 承認者コード */
	SYO_EMP_CODE("C00284", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 承認日 */
	SYO_DATE("C03086", ExcelColumnType.DATE, true, 10, 3000),
	/** 依頼者コード */
	IRAI_EMP_CODE("C01945", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 依頼部門コード */
	IRAI_DEP_CODE("C01812", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 依頼日 */
	IRAI_DATE("C01946", ExcelColumnType.DATE, true, 10, 3000),
	/** システム区分 */
	SYS_KBN("C00980", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 伝票種別コード */
	DEN_SYU_CODE("C00837", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 更新区分 */
	UPD_KBN("C01069", ExcelColumnType.INTEGER, true, 10, 3840),
	/** 決算区分 */
	KSN_KBN("C00610", ExcelColumnType.INTEGER, true, 10, 3840),
	/** 科目コード */
	KMK_CODE("C00572", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 補助コード */
	HKM_CODE("C00890", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 内訳コード */
	UKM_CODE("C00876", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 計上部門コード */
	DEP_CODE("C00571", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 取引先コード */
	TRI_CODE("C00786", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 社員コード */
	EMP_CODE("C00697", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 通貨 */
	CUR_CODE("C00665", ExcelColumnType.STRING, true, 3, 3840),
	/** レート */
	CUR_RATE("C00556", ExcelColumnType.DECIMAL, true, 17, 3840, 4),
	/** 貸借区分 */
	DC_KBN("C01226", ExcelColumnType.INTEGER, true, 10, 3840),
	/** 税区分 */
	ZEI_KBN("C00673", ExcelColumnType.INTEGER, true, 3, 3840),
	/** 消費税額 */
	SZEI_KIN("C00674", ExcelColumnType.DECIMAL, true, 17, 5760, 4),
	/** 消費税コード */
	SZEI_CODE("C00573", ExcelColumnType.ALPHANUMERIC, true, 2, 3840),
	/** 行摘要コード */
	GYO_TEK_CODE("C01560", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 行摘要 */
	GYO_TEK("C00119", ExcelColumnType.STRING, true, 80, 13440),
	/** 管理１コード */
	KNR1_CODE("C01561", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 管理２コード */
	KNR2_CODE("C01562", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 管理３コード */
	KNR3_CODE("C01563", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 管理４コード */
	KNR4_CODE("C01564", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 管理５コード */
	KNR5_CODE("C01565", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 管理６コード */
	KNR6_CODE("C01566", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 非会計明細１ */
	SWK_HM1("C01291", ExcelColumnType.STRING, true, 40, 3840),
	/** 非会計明細２ */
	SWK_HM2("C01292", ExcelColumnType.STRING, true, 40, 3840),
	/** 非会計明細３ */
	SWK_HM3("C01293", ExcelColumnType.STRING, true, 40, 3840),
	/** 自動仕訳区分 */
	AUTO_KBN("C10896", ExcelColumnType.INTEGER, true, 1, 3840),
	/** 発生日 */
	HAS_DATE("C00431", ExcelColumnType.DATE, true, 10, 3000),
	/** 相手科目コード */
	AT_KMK_CODE("C10897", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** 相手補助コード */
	AT_HKM_CODE("C10898", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** 相手内訳コード */
	AT_UKM_CODE("C10899", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** 相手部門コード */
	AT_DEP_CODE("C10900", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** 計上会社コード */
	K_KAI_CODE("C00570", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** 証憑番号 */
	SEI_NO("C01178", ExcelColumnType.STRING, true, 40, 3840),
	/** 邦貨金額 */
	SWK_KIN("C00576", ExcelColumnType.DECIMAL, true, 17, 5760, 4),
	/** 入力金額 */
	SWK_IN_KIN("C00574", ExcelColumnType.DECIMAL, true, 17, 5760, 4),
	/** 支払区分 */
	SIHA_KBN("C00682", ExcelColumnType.STRING, true, 2, 3840),
	/** 支払日 */
	SIHA_DATE("C01098", ExcelColumnType.DATE, true, 10, 3000),
	/** 支払方法 */
	SIHA_HOU("C00233", ExcelColumnType.ALPHANUMERIC, true, 3, 3840),
	/** 保留区分 */
	HORYU_KBN("C10267", ExcelColumnType.INTEGER, true, 1, 3840),
	/** 取引先条件コード */
	TJK_CODE("C00788", ExcelColumnType.ALPHANUMERIC, true, 10, 5760),
	/** 入金予定日 */
	NYU_DATE("C01273", ExcelColumnType.DATE, true, 10, 3000),
	/** 受入日 */
	UKE_DATE("C00019", ExcelColumnType.DATE, true, 10, 3000),
	/** 銀行口座コード */
	CBK_CODE("C01879", ExcelColumnType.ALPHANUMERIC, true, 10, 3840),
	/** 付替区分 */
	TUKE_KBN("C10903", ExcelColumnType.INTEGER, true, 1, 3840),
	/** 入力消費税額 */
	SZEI_IN_KIN("C00575", ExcelColumnType.DECIMAL, true, 17, 5760, 4);

	
	
	/** 名称 */
	protected String name;

	/** カラム種別 */
	protected ExcelColumnType columnType;

	/** 取込を行うカラムか */
	protected boolean isImportColumn;

	/** 最大長 */
	protected int maxLength;

	/** 小数点以下桁数 */
	protected int decimalPoint = 0;

	/** 幅 */
	protected int width;

	/**
	 * コンストラクタ.
	 * 
	 * @param name
	 * @param columnType
	 * @param isImportColumn
	 * @param maxLength
	 * @param width
	 */
	private SlipInstructionExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
		int width) {
		this.name = name;
		this.columnType = columnType;
		this.isImportColumn = isImportColumn;
		this.maxLength = maxLength;
		this.width = width;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param name
	 * @param columnType
	 * @param isImportColumn
	 * @param maxLength
	 * @param decimalPoint
	 * @param width
	 */
	private SlipInstructionExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
		int width, int decimalPoint) {
		this.name = name;
		this.columnType = columnType;
		this.isImportColumn = isImportColumn;
		this.maxLength = maxLength;
		this.decimalPoint = decimalPoint;
		this.width = width;
	}

	/**
	 * 列名を取得する
	 * 
	 * @return 列名
	 */
	public String getName() {
		switch (this) {
			//
		}
		return this.name;
	}

	/**
	 * 最大長を取得する
	 * 
	 * @return 最大長
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * 最大長を取得する
	 * 
	 * @return 最大長
	 */
	public int getDecimalPoint() {
		return this.decimalPoint;
	}

	/**
	 * 列幅を取得する
	 * 
	 * @return 列幅
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * 取込を行うカラムか
	 * 
	 * @return true:取込を行う
	 */
	public boolean isImportColumn() {
		return this.isImportColumn;
	}

	/**
	 * カラム種別を取得する
	 * 
	 * @return カラム種別
	 */
	public ExcelColumnType getColumnType() {
		return this.columnType;

	}

	/**
	 * 取込時必須のカラムか<br>
	 * 必ずfalse
	 * 
	 * @return 必須でない
	 */
	public boolean isMandatory() {
		return false;
	}

	/**
	 * マスタ存在チェックを行うカラムか
	 */
	public boolean isChecksRelation() {
		return false;
	}

}
