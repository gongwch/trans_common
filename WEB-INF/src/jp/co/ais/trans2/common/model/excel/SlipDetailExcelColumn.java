package jp.co.ais.trans2.common.model.excel;

import jp.co.ais.trans2.common.excel.*;

/**
 * 伝票明細エクセル列定義
 */
public enum SlipDetailExcelColumn implements ExcelImportableColumn {

	/** 計上会社コード */
	K_KAI_CODE("計上会社コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 計上会社 */
	K_KAI_NAME("計上会社", ExcelColumnType.STRING, true, 20, 6000),
	/** 計上部門コード */
	K_DEP_CODE("計上部門コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 計上部門 */
	K_DEP_NAME("計上部門", ExcelColumnType.STRING, false, 20, 6000),
	/** 科目コード */
	KMK_CODE("科目コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 科目名 */
	KMK_NAME("科目名", ExcelColumnType.STRING, false, 20, 6000),
	/** 補助コード */
	HKM_CODE("補助コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 補助名 */
	HKM_NAME("補助名", ExcelColumnType.STRING, false, 20, 6000),
	/** 内訳コード */
	UKM_CODE("内訳コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 内訳名 */
	UKM_NAME("内訳名", ExcelColumnType.STRING, false, 20, 6000),
	/** 発生日 */
	HAS_DATE("発生日", ExcelColumnType.DATE, true, 10, 3000),
	/** 通貨 */
	CUR_CODE("通貨", ExcelColumnType.STRING, true, 3, 1472),
	/** 税 */
	ZEI("税", ExcelColumnType.STRING, true, 3, 3840),
	/** 消費税コード */
	SZEI_CODE("消費税コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 2, 3840),
	/** 消費税名 */
	SZEI_NAME("消費税名", ExcelColumnType.STRING, false, 20, 6000),
	/** 借方金額(税込) */
	DR_KIN("借方金額(税込)", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** 貸方金額(税込) */
	CR_KIN("貸方金額(税込)", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** 借方消費税額 */
	DR_TAX("借方消費税額", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** 貸方消費税額 */
	CR_TAX("貸方消費税額", ExcelColumnType.DECIMAL, true, 17, 7680, 4),
	/** 行摘要コード */
	GYO_TEK_CODE("行摘要コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3000),
	/** 行摘要 */
	GYO_TEK("行摘要", ExcelColumnType.STRING, true, 80, 13440),
	/** 取引先コード */
	TRI_CODE("取引先コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 取引先名 */
	TRI_NAME("取引先名", ExcelColumnType.STRING, false, 40, 10240),
	/** 社員コード */
	EMP_CODE("社員コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 社員名 */
	EMP_NAME("社員名", ExcelColumnType.STRING, false, 20, 6000),
	/** 管理１コード */
	KNR1_CODE("管理１コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 管理１名 */
	KNR1_NAME("管理１名", ExcelColumnType.STRING, false, 20, 6000),
	/** 管理２コード */
	KNR2_CODE("管理２コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 管理２名 */
	KNR2_NAME("管理２名", ExcelColumnType.STRING, false, 20, 6000),
	/** 管理３コード */
	KNR3_CODE("管理３コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 管理３名 */
	KNR3_NAME("管理３名", ExcelColumnType.STRING, false, 20, 6000),
	/** 管理４コード */
	KNR4_CODE("管理４コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 管理４名 */
	KNR4_NAME("管理４名", ExcelColumnType.STRING, false, 20, 6000),
	/** 管理５コード */
	KNR5_CODE("管理５コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 管理５名 */
	KNR5_NAME("管理５名", ExcelColumnType.STRING, false, 20, 6000),
	/** 管理６コード */
	KNR6_CODE("管理６コード", ExcelColumnType.ALPHANUMERIC_SYMBOLS, true, 10, 3840),
	/** 管理６名 */
	KNR6_NAME("管理６名", ExcelColumnType.STRING, false, 20, 6000),
	/** 非会計明細１ */
	SWK_HM1("非会計明細１", ExcelColumnType.STRING, true, 40, 3840),
	/** 非会計明細２ */
	SWK_HM2("非会計明細２", ExcelColumnType.STRING, true, 40, 3840),
	/** 非会計明細３ */
	SWK_HM3("非会計明細３", ExcelColumnType.STRING, true, 40, 3840),;

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
	private SlipDetailExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
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
	private SlipDetailExcelColumn(String name, ExcelColumnType columnType, boolean isImportColumn, int maxLength,
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
