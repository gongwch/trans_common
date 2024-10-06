package jp.co.ais.trans.master.logic.report;

import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 環境設定マスタのExcel定義クラス
 */
public class EnvironmentalSettingMasterReportExcelDefine extends ReportExcelDefineBase {
	public String getFileName() {
		return "MG0010";
	}
	
	public String getSheetName() {
		// 環境設定マスタを返す
		return "C00087";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] {
				"C00596", "C00685", "C00686", "C01150", "C01151", "C01152",
				"C00527", "C00393", "C00690", "C00055", "C00261"
				
			};
	}
	
	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] {
				10, 40, 20, 50, 50, 80, 10, 12, 12, 6, 6
			};
	}

	public List convertDataToList(Object dto, String langCode) {
		ENV_MST entity = (ENV_MST)dto;
		List list = new ArrayList();

		//会社コード
		list.add(entity.getKAI_CODE());
		//会社名称
		list.add(entity.getKAI_NAME());
		//会社略称
		list.add(entity.getKAI_NAME_S());
		//住所１
		list.add(entity.getJYU_1());
		//住所2
		list.add(entity.getJYU_2());
		//　住所カナ
		list.add(entity.getJYU_KANA());
		//郵便番号
		list.add(entity.getZIP());
		//電話番号
		list.add(entity.getTEL());
		//FAX番号
		list.add(entity.getFAX());
		//開始年月日
		list.add(entity.getSTR_DATE());
		//終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}

