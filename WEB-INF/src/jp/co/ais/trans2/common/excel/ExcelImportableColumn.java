package jp.co.ais.trans2.common.excel;

/**
 * インポート可能Excelカラム定義
 */
public interface ExcelImportableColumn {

	/**
	 * 名称を取得する
	 * 
	 * @return 名称
	 */
	public String getName();

	/**
	 * 列幅を取得する
	 * 
	 * @return 列幅
	 */
	public int getWidth();

	/**
	 * 最大長を取得する
	 * 
	 * @return 最大長
	 */
	public int getMaxLength();

	/**
	 * 最大長を取得する
	 * 
	 * @return 最大長
	 */
	public int getDecimalPoint();

	/**
	 * 取込を行うカラムか
	 * 
	 * @return true:取込を行う
	 */
	public boolean isImportColumn();

	/**
	 * 取込時必須のカラムか
	 * 
	 * @return true:必須
	 */
	public boolean isMandatory();

	/**
	 * カラム種別を取得する
	 * 
	 * @return カラム種別
	 */
	public ExcelColumnType getColumnType();

	/**
	 * マスタ存在チェックが必要な項目か
	 * 
	 * @return true:必要
	 */
	public boolean isChecksRelation();
}
