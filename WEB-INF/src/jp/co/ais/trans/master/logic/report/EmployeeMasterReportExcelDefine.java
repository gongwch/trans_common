package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 社員マスタのExcel定義クラス
 */
public class EmployeeMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0160";
	}

	public String getSheetName() {
		// 社員マスタを返す
		return "C00913";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00697", "C00807", "C00808", "C00809", "C00811", "C00812", "C00813", "C00471",
				"C01068", "C00810", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 10, 40, 20, 40, 10, 10, 10, 12, 30, 14, 6, 6 };
	}

	private Map empKozaKbnMap;

	/**
	 * 
	 */
	public EmployeeMasterReportExcelDefine() {
		empKozaKbnMap = new HashMap();
		empKozaKbnMap.put(1, "C00465");
		empKozaKbnMap.put(2, "C02154");
	}

	public List convertDataToList(Object dto, String langCode) {
		EMP_MST entity = (EMP_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 社員コード
		list.add(entity.getEMP_CODE());
		// 社員名称
		list.add(entity.getEMP_NAME());
		// 社員略称
		list.add(entity.getEMP_NAME_S());
		// 社員検索名称
		list.add(entity.getEMP_NAME_K());
		// 振込銀行コード
		list.add(entity.getEMP_FURI_BNK_CODE());
		// 振込支店コード
		list.add(entity.getEMP_FURI_STN_CODE());
		// 振込口座番号
		list.add(entity.getEMP_YKN_NO());
		// 振込口座預金種別
		list.add(getWord(entity.getEMP_KOZA_KBN(), empKozaKbnMap, langCode));
		// 口座名義カナ
		list.add(entity.getEMP_YKN_KANA());
		// 振出銀行口座コード
		list.add(entity.getEMP_CBK_CODE());
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}

}
