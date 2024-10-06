package jp.co.ais.trans.master.logic.report;

import java.util.*;
import jp.co.ais.trans.master.entity.*;
/**
 * 会社間付替マスタのExcel定義クラス
 */
public class InterCompanyTransferMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0350";
	}	
	
	public String getSheetName() {
		// 会社間付替マスタを返す
		return "C02341";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] {
				"C00596", "C02050", "C02051", "C02052", "C02053", "C02054"
			};
	}
	
	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] {
				0, 10, 16, 11, 16, 16
			};
	}
	
	public List convertDataToList(Object dto, String langCode) {
		KTK_MST entity = (KTK_MST) dto;
		List list = new ArrayList();
		
		//会社コード
		list.add(entity.getKAI_CODE());
		//付替会社コード
		list.add(entity.getKTK_KAI_CODE());
		//付替計上部門コード
		list.add(entity.getKTK_DEP_CODE());
		//付替科目コード
		list.add(entity.getKTK_KMK_CODE());
		//付替補助科目コード
		list.add(entity.getKTK_HKM_CODE());
		//付替内訳科目コード
		list.add(entity.getKTK_UKM_CODE());
		
		return list;

	}
}
