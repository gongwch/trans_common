package jp.co.ais.trans.master.logic.report;

import java.util.ArrayList;
import java.util.List;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * プログラムマスタ マスタのExcel定義クラス
 */
public class ProgramMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0240";
	}

	public String getSheetName() {
		// プログラムマスタを返す
		return "C02147";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C02351", "C00818", "C00819", "C00820", "C00821", "C00822", "C00183", "C02146",
				"C00824", "C00520", "C00055", "C00261", "C02397" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 9, 11, 40, 20, 20, 6, 80, 340, 14, 7, 6, 6, 5 };
	}

	public List convertDataToList(Object dto, String langCode) {
		PRG_MST entity = (PRG_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// システムコード
		list.add(entity.getSYS_CODE());
		// プログラムコード
		list.add(entity.getPRG_CODE());
		// プログラム名称
		list.add(entity.getPRG_NAME());
		// プログラム略称
		list.add(entity.getPRG_NAME_S());
		// プログラム検索名称
		list.add(entity.getPRG_NAME_K());
		// 権限レベル
		list.add(entity.getKEN());
		// コメント
		list.add(entity.getCOM());
		// ﾛｰﾄﾞﾓｼﾞｭｰﾙﾌｧｲﾙ名
		list.add(entity.getLD_NAME());
		// 親プログラムコード
		list.add(entity.getPARENT_PRG_CODE());
		// メニュー区分
		list.add(new AlignString(getWord(entity.getMENU_KBN() == 0 ? getWord("C00519", langCode) : getWord("C02170",
				langCode), langCode), AlignString.CENTER));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());
		// 表示順序
		list.add(entity.getDISP_INDEX());

		return list;
	}
}
