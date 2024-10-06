package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 銀行口座マスタのExcel定義クラス
 */
public class BankAccountMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MP0030";
	}

	public String getSheetName() {
		// 銀行口座マスタを返す
		return "C02322";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C01879", "C02145", "C00665", "C00880", "C00779", "C00781", "C02055", "C02060",
				"C00858", "C10133", "C00860", "C00861", "C01326", "C00794", "C01117", "C01122", "C00571", "C00572",
				"C00602", "C00603", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 10, 40, 6, 40, 6, 30, 6, 30, 12, 40, 40, 70, 5, 10, 7, 7, 10, 10, 10, 10, 6, 6 };
	}

	private Map yknKbnMap;

	/**
	 * 
	 */
	public BankAccountMasterReportExcelDefine() {
		// 預金種目
		yknKbnMap = new LinkedHashMap();
		yknKbnMap.put(1, "C00463");
		yknKbnMap.put(2, "C00397");
		yknKbnMap.put(3, "C00045");
		yknKbnMap.put(4, "C00368");
	}

	public List convertDataToList(Object dto, String langCode) {
		AP_CBK_MST2 entity = (AP_CBK_MST2) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 銀行口座コード
		list.add(entity.getCBK_CBK_CODE());
		// 銀行口座名称
		list.add(entity.getCBK_NAME());
		// 通貨コード
		//list.add(entity.getCUR_CODE());
		
		list.add(new AlignString(entity.getCUR_CODE(),AlignString.CENTER));
		// 通貨名称
		list.add(entity.getCUR_NAME());
		// 銀行コード
		list.add(entity.getCBK_BNK_CODE());
		// 銀行名称
		list.add(entity.getBNK_NAME_S());
		// 支店コード
		list.add(entity.getCBK_STN_CODE());
		// 支店名称
		list.add(entity.getBNK_STN_NAME_S());
		// 振込依頼人コード
		list.add(entity.getCBK_IRAI_EMP_CODE());
		// 振込依頼人名
		list.add(entity.getCBK_IRAI_NAME());
		// 振込依頼人名（漢字）
		list.add(entity.getCBK_IRAI_NAME_J());
		// 振込依頼人名（英字）
		list.add(entity.getCBK_IRAI_NAME_E());
		// 預金種目
		list.add(getWord(entity.getCBK_YKN_KBN(), yknKbnMap, langCode));
		// 口座番号
		list.add(entity.getCBK_YKN_NO());
		// 社員ＦＢ区分
		list.add(new AlignString(entity.getCBK_EMP_FB_KBN() == 0 ? getWord("C02148", langCode) : getWord("C02149",
				langCode), AlignString.CENTER));
		// 社外ＦＢ区分
		list.add(new AlignString(entity.getCBK_OUT_FB_KBN() == 0 ? getWord("C02150", langCode) : getWord("C02151",
				langCode), AlignString.CENTER));
		// 計上部門コード
		list.add(entity.getCBK_DEP_CODE());
		// 科目コード
		list.add(entity.getCBK_KMK_CODE());
		// 補助科目コード
		list.add(entity.getCBK_HKM_CODE());
		// 内訳科目コード
		list.add(entity.getCBK_UKM_CODE());
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
