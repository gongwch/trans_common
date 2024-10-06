package jp.co.ais.trans2.common.excel;

import org.apache.poi.ss.usermodel.*;

/**
 * セルスタイルクラス
 */
public class TCellStyle {

	/** セルスタイル */
	protected CellStyle cellStyle;

	/**
	 * コンストラクタ
	 * 
	 * @param cellStyle
	 */
	public TCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	/**
	 * セルスタイルを取得
	 * 
	 * @return cellStyle
	 */
	public CellStyle getCellStyle() {
		return this.cellStyle;
	}

	/**
	 * セルスタイルを設定
	 * 
	 * @param cellStyle
	 */
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
}
