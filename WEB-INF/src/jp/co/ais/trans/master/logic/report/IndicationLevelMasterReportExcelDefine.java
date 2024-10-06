package jp.co.ais.trans.master.logic.report;

import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 開示ﾚﾍﾞﾙﾏｽﾀマスタのExcel定義クラス
 */
public class IndicationLevelMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0340";
	}
	
	public String getSheetName() {
		// 開示ﾚﾍﾞﾙマスタを返す
		return "C02340";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] {
				"C00596", "C00589", "C02048", "C00335", "C00057", "C01909", "C00698"
			};
	}
	
	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] {
				0, 10, 10, 6, 6, 10, 10 
			};
	}


	public List convertDataToList(Object dto, String langCode) {
		KJL_MST entity = (KJL_MST)dto;
		List list = new ArrayList();

		//会社コード
		list.add(entity.getKAI_CODE());
		//ユーザーコード
		list.add(entity.getKJL_USR_ID());
		//　科目体系ｺｰﾄﾞ
		list.add(entity.getKJL_KMT_CODE());
		//組織コード
		list.add(entity.getKJL_DPK_SSK());
		//　開示レベル
		String kjlLvl = String.valueOf(entity.getKJL_LVL());
		list.add(getWord("C01739", langCode)+ kjlLvl);
		//上位部門コード
		list.add(entity.getKJL_UP_DEP_CODE());
		//　部門コード
		list.add(entity.getKJL_DEP_CODE());
		
		return list;
	}
}
