package jp.co.ais.trans2.common.excel;

import java.math.*;
import java.util.*;

import javax.swing.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * �G�N�Z���̍s
 * 
 * @author AIS
 */
public class TExcelRow {

	/** ROW */
	public Row row;

	/** �u�b�N */
	public TExcel book;

	/** ���ݎQ�Ƃ��Ă���J�����ԍ� */
	public short columnNo = 0;

	/**
	 * @param book
	 */
	public TExcelRow(TExcel book) {
		this.book = book;
	}

	/**
	 * �Z����ǉ�����
	 * 
	 * @param value �l
	 */
	public void addCell(String value) {
		addCell(value, SwingConstants.LEFT);
	}

	/**
	 * �Z����ǉ�����
	 * 
	 * @param value �l
	 * @param align
	 */
	public void addCell(String value, int align) {

		CellStyle style;
		switch (align) {
			case SwingConstants.LEFT:
				style = book.getCellStyleManager().getLeftStyle();
				break;

			case SwingConstants.CENTER:
				style = book.getCellStyleManager().getCenterStyle();
				break;

			default:
				style = book.getCellStyleManager().getRightStyle();
				break;
		}

		Cell newcell = createCell(row, columnNo, style);

		newcell.setCellValue(Util.avoidNullNT(value));
		columnNo++;
	}

	/**
	 * �X�^�C�����w�肵�ăZ����ǉ�����B
	 * 
	 * @param value
	 * @param style
	 */
	public void addCell(String value, CellStyle style) {
		Cell newcell = createCell(row, columnNo, style);
		newcell.setCellValue(Util.avoidNullNT(value));
		columnNo++;
	}

	/**
	 * �X�^�C�����w�肵�Đ��l�Z����ǉ�����B
	 * 
	 * @param num
	 * @param style
	 */
	public void addNumberCell(BigDecimal num, CellStyle style) {
		Cell newcell = createCell(row, columnNo, style);
		if (num == null) {
			newcell.setCellValue("");
		} else {
			newcell.setCellValue(num.doubleValue());
		}
		columnNo++;
	}

	/**
	 * ���t�̃Z����ǉ�����
	 * 
	 * @param value
	 */
	public void addCell(Date value) {
		Cell newcell = createCell(row, columnNo, book.getCellStyleManager().getDateCellStyle());
		if (value != null) {
			newcell.setCellValue(value);
		}
		columnNo++;
	}

	/**
	 * BigDecimal�̃Z����ǉ�����
	 * 
	 * @param value
	 */
	public void addCell(BigDecimal value) {
		addCell(value, 0);
	}

	/**
	 * BigDecimal�̃Z����ǉ�����
	 * 
	 * @param value
	 * @param decimalPoint
	 */
	public void addCell(BigDecimal value, int decimalPoint) {
		addCell(value, NumberFormatUtil.makeExcelNumberFormat(decimalPoint));
	}

	/**
	 * BigDecimal�̃Z����ǉ�����
	 * 
	 * @param value
	 * @param format
	 */
	public void addCell(BigDecimal value, String format) {

		TExcelCellStyleManager cm = book.getCellStyleManager();

		if (!cm.getDecimalStyles().containsKey(format)) {

			CellStyle decimalCellStyle = cm.createCellStyle();
			decimalCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			decimalCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			decimalCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			decimalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			decimalCellStyle.setFont(cm.getFontStyle());

			// �t�H�[�}�b�g���쐬����
			DataFormat df = cm.createDataFormat();
			decimalCellStyle.setDataFormat(df.getFormat(format));
			cm.getDecimalStyles().put(format, decimalCellStyle);
		}

		Cell newcell = createCell(row, columnNo, cm.decimalStyles.get(format));

		if (value != null) {
			newcell.setCellValue(value.doubleValue());
		}
		columnNo++;
	}

	/**
	 * @param date
	 * @param format
	 */
	public void addCell(Date date, String format) {
		TExcelCellStyleManager cm = book.getCellStyleManager();
		
		if (!cm.getStyleMap().containsKey(format)) {
			CellStyle dateCellStyle = cm.createCellStyle();
			dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			dateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
			dateCellStyle.setFont(cm.getFontStyle());
			// �t�H�[�}�b�g���쐬����
			DataFormat df = cm.createDataFormat();
			dateCellStyle.setDataFormat(df.getFormat(format));
			cm.getStyleMap().put(format, dateCellStyle);
		}

		Cell newCell = createCell(row, columnNo, cm.getStyleMap().get(format));
		if (date != null) {
			newCell.setCellValue(date);
		}
		columnNo++;
	}

	/**
	 * int�̃Z����ǉ�����
	 * 
	 * @param value
	 */
	public void addCell(int value) {
		addCell(new BigDecimal(value), 0);
	}

	/**
	 * ��Z����ǉ�����
	 */
	public void addEmptyCell() {
		CellStyle style = book.getCellStyleManager().getDateCellStyle();
		addEmptyCellWithStyle(style);
	}

	/**
	 * �w��X�^�C���̋�Z����ǉ�����
	 * 
	 * @param style �w��X�^�C��
	 */
	public void addEmptyCellWithStyle(CellStyle style) {
		createCell(row, columnNo, style);
		columnNo++;
	}

	/**
	 * �Z���𐶐�����
	 * 
	 * @param row_
	 * @param cellIndex
	 * @param style
	 * @return Cell
	 */
	public Cell createCell(Row row_, int cellIndex, CellStyle style) {
		Cell newcell = row_.createCell(cellIndex);
		newcell.setCellStyle(style);
		return newcell;
	}

	/**
	 * �w���̃Z���̒l��ݒ肷��
	 * 
	 * @param columnNo_ ��
	 * @param value �l
	 */
	public void setCellValueWithDefaultStyle(int columnNo_, String value) {
		Cell newcell = row.getCell((short) columnNo_);
		if (newcell == null) {
			ServerLogger.error("row=" + row.getRowNum() + ", col=" + columnNo_ + " is null");
		}

		newcell.setCellValue(Util.avoidNullNT(value));
	}

	/**
	 * �w���̃Z���̒l��ݒ肷��
	 * 
	 * @param columnNo_ ��
	 * @param value �l
	 */
	public void setCellValueWithDefaultStyle(int columnNo_, BigDecimal value) {
		Cell newcell = row.getCell((short) columnNo_);

		if (newcell == null) {
			ServerLogger.error("row=" + row.getRowNum() + ", col=" + columnNo_ + " is null.");
		}

		if (value != null) {
			newcell.setCellValue(value.doubleValue());
		}
	}

	/**
	 * �w���̃Z���̒l��ݒ肷��
	 * 
	 * @param columnNo_ ��
	 * @param value �l
	 * @param template �e���v���[�g�Z��
	 */
	public void setCellValue(int columnNo_, String value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// null�̏ꍇ�͕ύX�Ȃ�
		if (!Util.isNullOrEmpty(value)) {
			newcell.setCellValue(Util.avoidNullNT(value));
		}
	}

	/**
	 * �w����BigDecimal�̃Z���̒l��ݒ肷��
	 * 
	 * @param columnNo_ ��
	 * @param value
	 * @param template �e���v���[�g�Z��
	 */
	public void setCellValue(int columnNo_, BigDecimal value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// null�̏ꍇ�͕ύX�Ȃ�
		if (!DecimalUtil.isNullOrZero(value)) {
			newcell.setCellValue(value.doubleValue());
		}

	}

	/**
	 * �w����BigDecimal�̃Z���̒l��ݒ肷��
	 * 
	 * @param columnNo_ ��
	 * @param value
	 * @param template �e���v���[�g�Z��
	 */
	public void setCellValueIncludeZero(int columnNo_, BigDecimal value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// null�̏ꍇ�͕ύX�Ȃ�
		if (value != null) {
			newcell.setCellValue(value.doubleValue());
		}

	}

	/**
	 * �w����double�̃Z���̒l��ݒ肷��
	 * 
	 * @param columnNo_ ��
	 * @param value
	 * @param template �e���v���[�g�Z��
	 */
	public void setCellValue(int columnNo_, double value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// null�̏ꍇ�͕ύX�Ȃ�
		if (value != 0) {
			newcell.setCellValue(value);
		}

	}

	/**
	 * �w����double�̃Z���̒l��ݒ肷��
	 * 
	 * @param columnNo_ ��
	 * @param value
	 * @param template �e���v���[�g�Z��
	 */
	public void setCellValueIncludeZero(int columnNo_, double value, Enum template) {

		CellStyle style = getCellStyle(columnNo_, template);
		Cell newcell = createCell(row, (short) columnNo_, style);

		// null�̏ꍇ�͕ύX�Ȃ�
		newcell.setCellValue(value);

	}

	/**
	 * �Z���X�^�C���ݒ�
	 * 
	 * @param columnNo_ ��
	 * @param template �e���v���[�g�Z��
	 */
	public void setCellStyle(int columnNo_, Enum template) {
		getCellStyle(columnNo_, template);
	}

	/**
	 * �Z���X�^�C���擾
	 * 
	 * @param columnNo_ ��
	 * @param template �e���v���[�g�Z��
	 * @return �Z���X�^�C��
	 */
	public CellStyle getCellStyle(int columnNo_, Enum template) {
		CellStyle style = null;

		if (book.getCellStyleManager().getTemplateStyle(template) == null) {
			// �e���v���[�g�Z���X�^�C���Ȃ�

			Cell cell = row.getCell((short) columnNo_);
			if (cell == null) {
				ServerLogger.error("row=" + row.getRowNum() + ", col=" + columnNo_ + " is null.");
			}

			style = cell.getCellStyle();

			// Enum�ɒ����񂹂���
			if (template.name().indexOf("Center") > 0) {
				style.setAlignment(CellStyle.ALIGN_CENTER);
			}

			book.getCellStyleManager().setTemplateStyle(template, style);

		} else {
			// �e���v���[�g�Z���X�^�C������

			return book.getCellStyleManager().getTemplateStyle(template);
		}

		return style;
	}

	/**
	 * �s�����ݒ�
	 * 
	 * @param height ����
	 */
	public void setHeight(short height) {
		row.setHeight(height);
	}
}
