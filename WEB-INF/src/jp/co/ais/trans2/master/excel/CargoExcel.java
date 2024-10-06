package jp.co.ais.trans2.master.excel;

import java.awt.Color;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.cargo.*;

/**
 * Cargo excel
 * 
 * @author Ngoc Nguyen
 */
public class CargoExcel extends TExcel {

	/**
	 * Contructor
	 * 
	 * @param lang language code
	 */
	public CargoExcel(String lang) {
		super(lang);
	}

	/**
	 * List in Excel
	 * 
	 * @param list
	 * @return Excel
	 * @throws TException
	 */
	public byte[] getExcel(List<Cargo> list) throws TException {

		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * Outputting Excel
	 * 
	 * @param list
	 */
	public void createReport(List<Cargo> list) {

		TExcelSheet sheet = addSheet(getWord("COP182"));

		sheet.addColumn(getWord("COP032"), 4200);
		sheet.addColumn(getWord("COP033"), 8400);
		sheet.addColumn(getWord("C02242"), 4200);
		sheet.addColumn(getWord("COP034"), 8400);
		sheet.addColumn(getWord("COP035"), 8400);
		sheet.addColumn(getWord("COP076"), 4200);
		sheet.addColumn(getWord("COP1232"), 4200);
		sheet.addColumn(getWord("COP1233"), 4200);
		sheet.addColumn(getWord("COP036"), 4200);
		sheet.addColumn(getWord("COP038"), 4200);
		sheet.addColumn(getWord("COP039"), 4200);
		sheet.addColumn(getWord("COP1324"), 6500);

		for (Cargo bean : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCRG_CODE());
			newRow.addCell(bean.getCRG_NAME());
			newRow.addCell(bean.getCODE_NAME());
			newRow.addCell(bean.getCODE_NAME_GRP());
			newRow.addCell(bean.getSTOWAGE_FACTOR(), 2);
			newRow.addCell(bean.getCATEGORY_NAME());
			newRow.addCell(bean.getMLIT_ITEM_NAME());
			newRow.addCell(bean.getMLIT_SUB_ITEM_NAME());
			// TODO background/text color
			Color backColor = new Color(255, 255, 255);
			Color textColor = new Color(0, 0, 0);
			if (!Util.isNullOrEmpty(bean.getBG_COLOR())) {
				int[] rgbCode = Util.toRGBColorCode(bean.getBG_COLOR());
				backColor = new Color(rgbCode[0], rgbCode[1], rgbCode[2]);
			}

			if (!Util.isNullOrEmpty(bean.getTEXT_COLOR())) {
				int[] rgbCode = Util.toRGBColorCode(bean.getTEXT_COLOR());
				textColor = new Color(rgbCode[0], rgbCode[1], rgbCode[2]);
			}

			CellStyle newStyle = workBook.createCellStyle();
			setFillColor(newStyle, backColor);

			Font font = workBook.createFont();
			setForeColor(font, textColor);
			newStyle.setFont(font);

			newStyle.setBorderBottom(CellStyle.BORDER_THIN);
			newStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			newStyle.setBorderLeft(CellStyle.BORDER_THIN);
			newStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			newStyle.setBorderRight(CellStyle.BORDER_THIN);
			newStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			newStyle.setBorderTop(CellStyle.BORDER_THIN);
			newStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

			newRow.addCell(getWord("COP195"), newStyle);
			newRow.addCell(bean.getSTR_DATE());
			newRow.addCell(bean.getEND_DATE());
			if (bean.isMinimumCalc()) {
				newRow.addCell(getWord("C10794"));
			} else {
				newRow.addCell("");
			}
		}
	}

	/**
	 * îwåiêFê›íË
	 * 
	 * @param style
	 * @param color
	 */
	protected void setFillColor(CellStyle style, Color color) {
		TExcelOutput.setFillColor(workBook, style, color);
	}

	/**
	 * ï∂éöêFê›íË
	 * 
	 * @param font
	 * @param color
	 */
	protected void setForeColor(Font font, Color color) {
		TExcelOutput.setForeColor(workBook, font, color);
	}

}
