package jp.co.ais.trans2.common.excel;

import org.apache.poi.ss.usermodel.*;

/**
 * �Z���X�^�C���N���X
 */
public class TCellStyle {

	/** �Z���X�^�C�� */
	protected CellStyle cellStyle;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param cellStyle
	 */
	public TCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	/**
	 * �Z���X�^�C�����擾
	 * 
	 * @return cellStyle
	 */
	public CellStyle getCellStyle() {
		return this.cellStyle;
	}

	/**
	 * �Z���X�^�C����ݒ�
	 * 
	 * @param cellStyle
	 */
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
}
