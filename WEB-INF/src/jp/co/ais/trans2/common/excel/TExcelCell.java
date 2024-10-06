package jp.co.ais.trans2.common.excel;

import java.math.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.util.*;

/**
 * �G�N�Z���̃Z��
 */
public class TExcelCell {

	/** Cell */
	public Cell cell;

	/** �u�b�N */
	public TExcel book;

	/** �s�ԍ� */
	protected int rowIndex = -1;

	/** ��ԍ� */
	protected int columnIndex = -1;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param book
	 * @param cell
	 */
	public TExcelCell(TExcel book, Cell cell) {
		this.book = book;
		this.cell = cell;

		if (cell != null) {
			this.rowIndex = cell.getRowIndex();
			this.columnIndex = cell.getColumnIndex();
		}
	}

	/**
	 * �s�ԍ��̎擾
	 * 
	 * @return rowIndex �s�ԍ�
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * �s�ԍ��̐ݒ�
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * ��ԍ��̎擾
	 * 
	 * @return columnIndex ��ԍ�
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * ��ԍ��̐ݒ�
	 * 
	 * @param columnIndex ��ԍ�
	 */
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * ��ԍ��擾
	 * 
	 * @return ��ԍ�
	 */
	public int getCellColumnIndex() {
		return cell.getColumnIndex();
	}

	/**
	 * �s�ԍ��擾
	 * 
	 * @return �s�ԍ�
	 */
	public int getCellRowIndex() {
		return cell.getRowIndex();
	}

	/**
	 * �s�擾
	 * 
	 * @return �s
	 */
	public TExcelRow getRow() {
		TExcelRow er = new TExcelRow(book);
		er.row = cell.getRow();
		return er;
	}

	/**
	 * �w���̃Z���̒l��ݒ肷��
	 * 
	 * @param value �l
	 */
	public void setValue(String value) {
		cell.setCellValue(Util.avoidNullNT(value));
	}

	/**
	 * �w���̃Z���̒l��ݒ肷��
	 * 
	 * @param value �l
	 */
	public void setValue(int value) {
		cell.setCellValue(value);
	}

	/**
	 * �w���̃Z���̒l��ݒ肷��
	 * 
	 * @param value �l
	 */
	public void setValue(BigDecimal value) {

		if (value == null) {
			return;
		}

		cell.setCellValue(value.doubleValue());
	}

	/**
	 * �w���̃Z���̒l��ݒ肷��
	 * 
	 * @param value �l
	 */
	public void setValue(Date value) {

		if (value == null) {
			return;
		}

		cell.setCellValue(value);
	}

	/**
	 * ������\��
	 */
	@Override
	public String toString() {
		return TExcelSheet.toExcelNo(rowIndex, columnIndex);
	}

}
