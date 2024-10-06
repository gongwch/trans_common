package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * システム区分マスタマスタのExcel定義クラス
 */
public class SystemDivisionMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0320";
	}

	public String getSheetName() {
		return "C02354";
	}

	public String[] getHeaderTexts() {
		return new String[] { "C00596", "C00980", "C00832", "C00833", "C00834", "C01018" };
	}

	public short[] getColumnWidths() {
		return new short[] { 0, 7, 40, 20, 20, 12 };
	}

	private Map dataKbnMap;

	/**
	 * 
	 */
	public SystemDivisionMasterReportExcelDefine() {
		// 外部システム区分 - ０：ﾊﾟｯｹｰｼﾞ使用する １：ﾊﾟｯｹｰｼﾞ使用しない ２：外部システム
		dataKbnMap = new HashMap();
		dataKbnMap.put("0", "C02104");
		dataKbnMap.put("1", "C02105");
		dataKbnMap.put("2", "C02106");
	}

	public List convertDataToList(Object dto, String langCode) {
		SYS_MST entity = (SYS_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// システム区分
		list.add(new AlignString(entity.getSYS_KBN(), AlignString.CENTER));
		// システム区分名称
		list.add(entity.getSYS_KBN_NAME());
		// システム区分略称
		list.add(entity.getSYS_KBN_NAME_S());
		// システム区分検索名称
		list.add(entity.getSYS_KBN_NAME_K());
		// 外部システム区分
		list.add(new AlignString(getWord(entity.getOSY_KBN(), dataKbnMap, langCode), AlignString.CENTER));

		return list;
	}
}
