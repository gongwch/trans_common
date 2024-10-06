package jp.co.ais.trans.master.logic.report;

import java.util.ArrayList;
import java.util.List;

import jp.co.ais.trans.master.entity.BNK_MST;

/**
 * 銀行マスタのExcel定義クラス
 */
public class BankMasterReportExcelDefine extends ReportExcelDefineBase {
	public String getFileName() {
		return "MG0140";
	}
	
	public String getSheetName() {
		// 銀行マスタを返す
		return "C02323";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] {"C00779", "C00780", "C00781", "C00782", "C00829", "C00783",
				"C00784", "C00785", "C00055", "C00261" 
			};
	}
	
	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] {
				5, 9, 30, 30, 30, 30, 30, 30, 6, 6
			};
	}

	public List convertDataToList(Object dto, String langCode) {
		BNK_MST entity = (BNK_MST)dto;
		List list = new ArrayList();

		//銀行コード
		list.add(entity.getBNK_CODE());
		//銀行支店コード
		list.add(entity.getBNK_STN_CODE());
		//　銀行名
		list.add(entity.getBNK_NAME_S());
		//銀行カナ名称
		list.add(entity.getBNK_KANA());
		//銀行検索名称
		list.add(entity.getBNK_NAME_K());
		//銀行支店名
		list.add(entity.getBNK_STN_NAME_S());
		//銀行支店カナ名称
		list.add(entity.getBNK_STN_KANA());
		//銀行支店検索名称
		list.add(entity.getBNK_STN_NAME_K());
		//開始年月日
		list.add(entity.getSTR_DATE());
		//終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}

