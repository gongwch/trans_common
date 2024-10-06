package jp.co.ais.trans2.master.excel;

import java.math.*;
import java.util.*;

import javax.swing.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.port.*;

/**
 * サマータイムマスタエクセル
 */
public class SummerTimeExcel extends TExcel {

	/** TZ */
	protected CellStyle timezoneStyle;

	/**
	 * @param lang
	 */
	public SummerTimeExcel(String lang) {
		super(lang);
	}

	/**
	 * SummerTime Listをエクセルで返す
	 * 
	 * @param list
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<OP_SMR_TIME_MST> list) throws TException {

		try {

			initCellStyle();

			// 作成
			createReport(list);

			return getBinary();

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 書式
	 */
	protected void initCellStyle() {

		CellStyle style = workBook.createCellStyle();
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		style.setFont(cellStyleManager.getFontStyle((short) 9));

		DataFormat df = workBook.createDataFormat();
		style.setDataFormat(df.getFormat("@"));

		timezoneStyle = style;

	}

	/**
	 * SummerTime Listのエクセルを出力する
	 * 
	 * @param list
	 */
	public void createReport(List<OP_SMR_TIME_MST> list) {

		TExcelSheet sheet = addSheet(getWord("COP1292")); // サマータイムマスタ
		sheet.addColumn(getWord("CSC058"), 3000); // YEAR
		sheet.addColumn(getWord("COP1285"), 4000); // REGION CODE
		sheet.addColumn(getWord("COP1286"), 7000); // REGION NAME
		sheet.addColumn(getWord("COP1287"), 5000); // TIME DIFFERENCE
		sheet.addColumn(getWord("COP1288"), 5000); // FROM DATE(LCT)
		sheet.addColumn(getWord("COP1289"), 5000); // TO DATE(LCT)
		sheet.addColumn(getWord("COP369"), 15000); // REMARKS

		for (OP_SMR_TIME_MST bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(Integer.toString(bean.getYEAR())); // YEAR
			newRow.addCell(bean.getREGION_CODE()); // REGION CODE
			newRow.addCell(bean.getREGION_NAME()); // REGION NAME
			newRow.addCell(convertDBValueForScreen(bean.getTIME_DIFFERENCE()), timezoneStyle);
			newRow.addCell(DateUtil.toYMDHMString(bean.getFROM_DATE()), SwingConstants.CENTER); // FROM DATE
			newRow.addCell(DateUtil.toYMDHMString(bean.getTO_DATE()), SwingConstants.CENTER); // TO DATE
			newRow.addCell(bean.getREMARKS()); // REMARKS
		}
	}

	/**
	 * Convert value from database to screen
	 * 
	 * @param tz
	 * @return String of object
	 */
	public static String convertDBValueForScreen(BigDecimal tz) {

		if (DecimalUtil.isNullOrZero(tz)) {
			return "";
		}

		int sign = tz.signum();
		boolean isLowerThenTen = tz.abs().compareTo(BigDecimal.TEN) == -1;

		String str = NumberFormatUtil.formatNumber(DecimalUtil.convert10to60(tz.abs()), 2);

		String excelTime = str.replaceAll("\\.", ":").replaceAll(",", "");

		String prefixZero = isLowerThenTen ? "0" : "";

		return (sign == -1 ? "-" : "+") + prefixZero + excelTime;
	}

}
