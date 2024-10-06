package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vessel master excel
 * 
 * @author Ngoc Nguyen
 */
public class VesselExcel extends TExcel {

	/** true:燃料管理使用 */
	protected boolean isUseBM = false;

	/** OP機能不使用 */
	public static boolean isNotUseOP = ServerConfig.isFlagOn("trans.op.not.use");

	/** OP VESSEL SHORT NAME 利用判定 */
	public static boolean isUseShortName = ServerConfig.isFlagOn("trans.op.use.vessel.short.name");

	/**
	 * Instantiates new {@link VesselExcel}
	 * 
	 * @param lang the lang
	 */
	public VesselExcel(String lang) {
		super(lang);
	}

	/**
	 * List in Excel
	 * 
	 * @param list
	 * @param isUseBMLocal true:燃料管理使用
	 * @return Excel
	 * @throws TException
	 */
	public byte[] getExcel(List<Vessel> list, boolean isUseBMLocal) throws TException {

		try {
			this.isUseBM = isUseBMLocal;
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
	public void createReport(List<Vessel> list) {

		String sheetName = getWord("COP258");

		if (isNotUseOP) {
			sheetName = getWord("C11772");
		}

		TExcelSheet sheet = addSheet(sheetName);

		if (isNotUseOP) {
			// 船コード
			sheet.addColumn(getWord("C11758"), 4200);
			// 船名称
			sheet.addColumn(getWord("C11773"), 12600);
			// 船略称
			sheet.addColumn(getWord("C11759"), 8400);
			// 船検索名称
			sheet.addColumn(getWord("C11774"), 12600);
			// 開始年月日
			sheet.addColumn(getWord("C00055"), 4200);
			// 終了年月日
			sheet.addColumn(getWord("C00261"), 4200);

		} else {
			sheet.addColumn(getWord("COP894"), 4200); // VESSEL CODE
			sheet.addColumn(getWord("C00922"), 4200);
			if (isUseShortName) {
				sheet.addColumn(getWord("COP1323"), 4200);
			}
			sheet.addColumn(getWord("C01836"), 4200); // OWNER CODE
			sheet.addColumn(getWord("CTC0059"), 4200);
			sheet.addColumn(getWord("COP009"), 4200);
			sheet.addColumn(getWord("COP010"), 4200);
			sheet.addColumn(getWord("COP011"), 4200);
			sheet.addColumn(getWord("COP224"), 4200);
			sheet.addColumn(getWord("C01397"), 4200);
			sheet.addColumn(getWord("C03575"), 4200);
			sheet.addColumn(getWord("COP232"), 4200);
			sheet.addColumn(getWord("COP248"), 4200);
			sheet.addColumn(getWord("COP065"), 4200);
			sheet.addColumn(getWord("COP066"), 4200);
			sheet.addColumn(getWord("COP251"), 4200);
			sheet.addColumn(getWord("COP067"), 4200);
			sheet.addColumn(getWord("COP068"), 4200);
			sheet.addColumn(getWord("COP070"), 4200);
			sheet.addColumn(getWord("COP071"), 4200);
			sheet.addColumn(getWord("COP072"), 4200);
			sheet.addColumn(getWord("COP021"), 4200); // Fleet Mv. User
			sheet.addColumn(getWord("COP818"), 4200); // ESTIMATE
			sheet.addColumn(getWord("COP1157"), 4200); // RELET
		}

		if (isUseBM) {
			sheet.addColumn(getWord("C11775"), 5000); // 貯蔵品部門コード
			sheet.addColumn(getWord("C11776"), 12600); // 貯蔵品部門名称
			sheet.addColumn(getWord("C11777"), 5000); // 燃料費部門コード
			sheet.addColumn(getWord("C11778"), 12600); // 燃料費部門名称
		}

		for (Vessel bean : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			if (isUseShortName) {
				newRow.addCell(bean.getSHORT_NAME());
			}
			if (isNotUseOP) {
				newRow.addCell(bean.getNames());
				newRow.addCell(bean.getNamek());
				newRow.addCell(bean.getDateFrom());
				newRow.addCell(bean.getDateTo());

			} else {
				newRow.addCell(bean.getOWNR_CODE());
				newRow.addCell(bean.getTRI_NAME());
				newRow.addCell(bean.getOWNR_SHIP_NAME());
				newRow.addCell(bean.getVESSEL_TYPE_NAME());
				newRow.addCell(bean.getVESSEL_SIZE_NAME());
				newRow.addCell(bean.getCALL_SIGN());
				newRow.addCell(bean.getFLAG());
				newRow.addCell(bean.getCLASS());
				newRow.addCell(bean.getIMO_NO());
				newRow.addCell(bean.getOFCL_NO());
				newRow.addCell(bean.getE_MAIL());
				newRow.addCell(bean.getPHONE_1());
				newRow.addCell(bean.getPHONE_2());
				newRow.addCell(bean.getFAX());
				newRow.addCell(bean.getTELEX());
				newRow.addCell(bean.getCHTR_PI_NAME());
				newRow.addCell(bean.getOWR_PI_NAME());
				newRow.addCell(bean.getNEXT_DRY_DOCK_DATE());
				newRow.addCell(bean.getFLEET_USR_NAME());
				newRow.addCell(BooleanUtil.toBoolean(bean.getEST_FLG()) ? getWord("C10794") : "");
				newRow.addCell(BooleanUtil.toBoolean(bean.getRELET_FLG()) ? getWord("C10794") : "");
			}

			if (isUseBM) {
				newRow.addCell(bean.getStockCode());
				newRow.addCell(bean.getStockName());
				newRow.addCell(bean.getFuelCode());
				newRow.addCell(bean.getFuelName());
			}

		}
	}

}
