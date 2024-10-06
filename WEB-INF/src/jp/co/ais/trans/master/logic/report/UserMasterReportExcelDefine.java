package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ユーザーマスタのExcel定義クラス
 */
public class UserMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0020";
	}

	public String getSheetName() {
		// ユーザーマスタを返す
		return "C02355";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00589", "C00597", "C00699", "C00691", "C00692", "C00693", "C00112", "C01082",
				"C01078", "C00180", "C00230", "C00020", "C00096", "C00041", "C00491", "C01222", "C00537", "C00206",
				"C00176", "C02044", "C00525", "C00297", "C00170", "C00697", "C02043", "C00139", "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 10, 10, 0, 10, 40, 40, 40, 4, 4, 4, 4, 4, 4, 4, 6, 6, 0, 4, 4, 6, 0, 4, 9, 9, 10, 10, 12,
				6, 6 };
	}

	private Map updKenMap;

	private Map keiriKbnMap;

	private Map sysKbnMap0;

	private Map sysKbnMap1;

	private Map sysKbnMap2;

	private Map sysKbnMap3;

	private Map sysKbnMap4;

	private Map sysKbnMap5;

	private Map sysKbnMap6;

	private Map sysKbnMap7;

	private Map sysKbnMap8;

	private Map sysKbnMap9;

	private Map sysKbnMap10;

	private Map sysKbnMap11;

	private Map sysKbnMap12;

	private Map sysKbnMap13;

	private Map sysKbnMap14;

	/**
	 * 
	 */
	public UserMasterReportExcelDefine() {
		// 更新権限レベル - 0:ALL 1:入力者 2:所属部門
		updKenMap = new HashMap();
		updKenMap.put(0, "C00310");
		updKenMap.put(1, "C01278");
		updKenMap.put(2, "C00295");
		// 経理担当者区分 - ０：経理担当者以外 １：経理担当者
		keiriKbnMap = new HashMap();
		keiriKbnMap.put(0, "C00140");
		keiriKbnMap.put(1, "C01050");

		// システム区分

		// 基本会計- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap0 = new HashMap();
		sysKbnMap0.put("0", "C02366");
		sysKbnMap0.put("1", "C02365");
		// 債務管理- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap1 = new HashMap();
		sysKbnMap1.put("0", "C02366");
		sysKbnMap1.put("1", "C02365");
		// 債権管理- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap2 = new HashMap();
		sysKbnMap2.put("0", "C02366");
		sysKbnMap2.put("1", "C02365");
		// 固定資産- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap3 = new HashMap();
		sysKbnMap3.put("0", "C02366");
		sysKbnMap3.put("1", "C02365");
		// 支払手形- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap4 = new HashMap();
		sysKbnMap4.put("0", "C02366");
		sysKbnMap4.put("1", "C02365");
		// 受取手形- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap5 = new HashMap();
		sysKbnMap5.put("0", "C02366");
		sysKbnMap5.put("1", "C02365");
		// 管理会計- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap6 = new HashMap();
		sysKbnMap6.put("0", "C02366");
		sysKbnMap6.put("1", "C02365");
		// 親会社連結- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap7 = new HashMap();
		sysKbnMap7.put("0", "C02366");
		sysKbnMap7.put("1", "C02365");
		// 本支店会計- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap8 = new HashMap();
		sysKbnMap8.put("0", "C02366");
		sysKbnMap8.put("1", "C02365");
		// 多通貨会計- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap9 = new HashMap();
		sysKbnMap9.put("0", "C02366");
		sysKbnMap9.put("1", "C02365");
		// 予算管理- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap10 = new HashMap();
		sysKbnMap10.put("0", "C02366");
		sysKbnMap10.put("1", "C02365");
		// 資金管理- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap11 = new HashMap();
		sysKbnMap11.put("0", "C02366");
		sysKbnMap11.put("1", "C02365");
		// 子会社連結- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap12 = new HashMap();
		sysKbnMap12.put("0", "C02366");
		sysKbnMap12.put("1", "C02365");
		// グルプ会計- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap13 = new HashMap();
		sysKbnMap13.put("0", "C02366");
		sysKbnMap13.put("1", "C02365");
		// 有価証券会計- 1:するの場合「可」、0:しないの場合「不可」
		sysKbnMap14 = new HashMap();
		sysKbnMap14.put("0", "C02366");
		sysKbnMap14.put("1", "C02365");
	}

	public List convertDataToList(Object dto, String langCode) {
		USR_MST entity = (USR_MST) dto;
		List list = new ArrayList();

		char[] sysKbn = entity.getSYS_KBN().toCharArray();
		// 会社コード
		list.add(entity.getKAI_CODE());
		// ユーザーコード
		list.add(entity.getUSR_CODE());
		// パスワード
		list.add(entity.getPASSWORD());
		// 言語コード
		list.add(entity.getLNG_CODE());
		// ユーザー名称
		list.add(entity.getUSR_NAME());
		// ユーザー略称
		list.add(entity.getUSR_NAME_S());
		// ユーザー検索名称
		list.add(entity.getUSR_NAME_K());

		// システム区分

		// 基本会計
		list.add(getWord(String.valueOf(sysKbn[0]), sysKbnMap0, langCode));
		// 債務管理
		list.add(getWord(String.valueOf(sysKbn[1]), sysKbnMap1, langCode));
		// 債権管理
		list.add(getWord(String.valueOf(sysKbn[2]), sysKbnMap2, langCode));
		// 固定資産
		list.add(getWord(String.valueOf(sysKbn[3]), sysKbnMap3, langCode));
		// 支払手形
		list.add(getWord(String.valueOf(sysKbn[4]), sysKbnMap4, langCode));
		// 受取手形
		list.add(getWord(String.valueOf(sysKbn[5]), sysKbnMap5, langCode));
		// 管理会計
		list.add(getWord(String.valueOf(sysKbn[6]), sysKbnMap6, langCode));
		// 親会社連結
		list.add(getWord(String.valueOf(sysKbn[7]), sysKbnMap7, langCode));
		// 本支店会計
		list.add(getWord(String.valueOf(sysKbn[8]), sysKbnMap8, langCode));
		// 多通貨会計
		list.add(getWord(String.valueOf(sysKbn[9]), sysKbnMap9, langCode));
		// 予算管理
		list.add(getWord(String.valueOf(sysKbn[10]), sysKbnMap10, langCode));
		// 資金管理
		list.add(getWord(String.valueOf(sysKbn[11]), sysKbnMap11, langCode));
		// 子会社連結
		list.add(getWord(String.valueOf(sysKbn[12]), sysKbnMap12, langCode));
		// グルプ会計
		list.add(getWord(String.valueOf(sysKbn[13]), sysKbnMap13, langCode));
		// 有価証券会計
		list.add(getWord(String.valueOf(sysKbn[14]), sysKbnMap14, langCode));
		// 処理権限レベル
		list.add(entity.getPRC_KEN());
		// 更新権限レベル
		list.add(getWord(entity.getUPD_KEN(), updKenMap, langCode));
		// 社員コード
		list.add(entity.getEMP_CODE());
		// 所属部門コード
		list.add(entity.getDEP_CODE());
		// 経理担当者区分
		list.add(new AlignString(getWord(entity.getKEIRI_KBN(), keiriKbnMap, langCode), AlignString.CENTER));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
