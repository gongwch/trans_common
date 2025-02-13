package jp.co.ais.trans.master.logic.report;

import java.util.ArrayList;
import java.util.List;

import jp.co.ais.trans.master.entity.BNK_MST;

/**
 * โs}X^ฬExcel่`NX
 */
public class BankMasterReportExcelDefine extends ReportExcelDefineBase {
	public String getFileName() {
		return "MG0140";
	}
	
	public String getSheetName() {
		// โs}X^๐ิท
		return "C02323";
	}

	public String[] getHeaderTexts() {
		// ^Cg๐ิท
		return new String[] {"C00779", "C00780", "C00781", "C00782", "C00829", "C00783",
				"C00784", "C00785", "C00055", "C00261" 
			};
	}
	
	public short[] getColumnWidths() {
		// ๑๐ิท
		return new short[] {
				5, 9, 30, 30, 30, 30, 30, 30, 6, 6
			};
	}

	public List convertDataToList(Object dto, String langCode) {
		BNK_MST entity = (BNK_MST)dto;
		List list = new ArrayList();

		//โsR[h
		list.add(entity.getBNK_CODE());
		//โsxXR[h
		list.add(entity.getBNK_STN_CODE());
		//@โsผ
		list.add(entity.getBNK_NAME_S());
		//โsJiผฬ
		list.add(entity.getBNK_KANA());
		//โs๕ผฬ
		list.add(entity.getBNK_NAME_K());
		//โsxXผ
		list.add(entity.getBNK_STN_NAME_S());
		//โsxXJiผฬ
		list.add(entity.getBNK_STN_KANA());
		//โsxX๕ผฬ
		list.add(entity.getBNK_STN_NAME_K());
		//JnN๚
		list.add(entity.getSTR_DATE());
		//IนN๚
		list.add(entity.getEND_DATE());

		return list;
	}
}

