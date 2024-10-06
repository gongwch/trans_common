package jp.co.ais.trans2.master.excel;

import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bunkertype.*;
import jp.co.ais.trans2.model.code.*;

/**
 * Bunker Type Master Excel
 * 
 * @author IBSV
 */
public class BunkerTypeMasterExcel extends TExcel {

	/**
	 * Excel for bunker type master
	 * 
	 * @param lang
	 */
	public BunkerTypeMasterExcel(String lang) {
		super(lang);
	}

	/**
	 * Get list data excel
	 * 
	 * @param list
	 * @param codeList
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<CM_BNKR_TYPE_MST> list, List<OP_CODE_MST> codeList) throws TException {

		try {
			createReport(list, codeList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * Create report excel
	 * 
	 * @param list
	 * @param codeList
	 */
	public void createReport(List<CM_BNKR_TYPE_MST> list, List<OP_CODE_MST> codeList) {

		// Sheet in excel
		TExcelSheet sheet = addSheet(getWord("COP004") + " " + getWord("C00500"));

		String engine = getWord("COP1090"); // MAIN ENGINE
		String aux = getWord("COP1091"); // AUX

		if (codeList != null) {
			for (OP_CODE_MST code : codeList) {
				if (code.getCODE().equals(Integer.toString(VesselUsingPurpose.ENGINE.value))) {
					engine = code.getCODE_NAME();
				} else {
					aux = code.getCODE_NAME();
				}
			}
		}

		int maxColNo = 9;

		sheet.setColumnWidth(0, 25 * 256);

		for (int i = 1; i <= maxColNo; i++) {
			sheet.setColumnWidth(i, 15 * 256);
		}

		sheet.setRowNo(0);
		CellStyle headerStyle = getCellStyleManager().getHeadStyle();
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);

		{
			// •ª—Þ
			TExcelRow row = sheet.addRow();

			row.addEmptyCellWithStyle(headerStyle);
			row.addCell(getWord("COP1139"), headerStyle); // BASIC VESSEL SETTINGS
			row.addEmptyCellWithStyle(headerStyle);
			row.addEmptyCellWithStyle(headerStyle);
			row.addEmptyCellWithStyle(headerStyle);
			row.addCell(getWord("COP1140"), headerStyle); // SCRUBBER VESSEL SETTINGS
			row.addEmptyCellWithStyle(headerStyle);
			row.addEmptyCellWithStyle(headerStyle);
			row.addEmptyCellWithStyle(headerStyle);
		}

		{
			// NORMAL AREA/(S)ECA
			TExcelRow row = sheet.addRow();

			row.addEmptyCellWithStyle(headerStyle);
			row.addCell(getWord("COP1092"), headerStyle); // NORMAL AREA
			row.addEmptyCellWithStyle(headerStyle);
			row.addCell(getWord("COP398"), headerStyle); // (S)ECA
			row.addEmptyCellWithStyle(headerStyle);
			row.addCell(getWord("COP1092"), headerStyle); // NORMAL AREA
			row.addEmptyCellWithStyle(headerStyle);
			row.addCell(getWord("COP398"), headerStyle); // (S)ECA
			row.addEmptyCellWithStyle(headerStyle);
		}

		{
			// ENG/AUC
			TExcelRow row = sheet.addRow();

			row.addCell(getWord("COP048"), headerStyle);
			row.addCell(engine, headerStyle);
			row.addCell(aux, headerStyle);
			row.addCell(engine, headerStyle);
			row.addCell(aux, headerStyle);
			row.addCell(engine, headerStyle);
			row.addCell(aux, headerStyle);
			row.addCell(engine, headerStyle);
			row.addCell(aux, headerStyle);
		}

		for (CM_BNKR_TYPE_MST bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getBNKR_TYPE_CODE());

			if (bean.getENG_PRI_NOR() > 0) {
				newRow.addCell(bean.getENG_PRI_NOR());
			} else {
				newRow.addCell("");
			}
			if (bean.getAUX_PRI_NOR() > 0) {
				newRow.addCell(bean.getAUX_PRI_NOR());
			} else {
				newRow.addCell("");
			}
			if (bean.getENG_PRI_ECA() > 0) {
				newRow.addCell(bean.getENG_PRI_ECA());
			} else {
				newRow.addCell("");
			}
			if (bean.getAUX_PRI_ECA() > 0) {
				newRow.addCell(bean.getAUX_PRI_ECA());
			} else {
				newRow.addCell("");
			}

			// SCRUBBER

			if (bean.getSC_ENG_PRI_NOR() > 0) {
				newRow.addCell(bean.getSC_ENG_PRI_NOR());
			} else {
				newRow.addCell("");
			}
			if (bean.getSC_AUX_PRI_NOR() > 0) {
				newRow.addCell(bean.getSC_AUX_PRI_NOR());
			} else {
				newRow.addCell("");
			}
			if (bean.getSC_ENG_PRI_ECA() > 0) {
				newRow.addCell(bean.getSC_ENG_PRI_ECA());
			} else {
				newRow.addCell("");
			}
			if (bean.getSC_AUX_PRI_ECA() > 0) {
				newRow.addCell(bean.getSC_AUX_PRI_ECA());
			} else {
				newRow.addCell("");
			}

		}

	}
}
