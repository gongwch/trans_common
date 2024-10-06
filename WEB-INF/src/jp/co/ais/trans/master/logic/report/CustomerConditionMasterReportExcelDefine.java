package jp.co.ais.trans.master.logic.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.TRI_SJ_MST;

/**
 * 取引先条件マスタのExcel定義クラス
 */
public class CustomerConditionMasterReportExcelDefine extends ReportExcelDefineBase {

	private Map mapYknKbn;

	/**
	 * コンストラクタ
	 */
	public CustomerConditionMasterReportExcelDefine() {
		// 預金種目 1:普通預金 2:当座預金 3:外貨預金 4:貯蓄預金
		mapYknKbn = new HashMap();
		mapYknKbn.put("1", "C00465");
		mapYknKbn.put("2", "C02154");
		mapYknKbn.put("3", "C02168");
		mapYknKbn.put("4", "C02169");
	}

	public String getFileName() {
		return "MG0155";
	}

	public String getSheetName() {
		// 取引先支払条件ﾏｽﾀを返す
		return "C02325";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00786", "C00787", "C00788", "C02056", "C02057", "C02058", "C02059", "C00682",
				"C00233", "C02061", "C00779", "C02055", "C01326", "C00794", "C01068", "C00793", "C02037", "C00795",
				"C00796", "C00797", "C10224", "C00799", "C00800", "C00801", "C00802", "C00803", "C00804", "C00805",
				"C00055", "C00261" };

	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 11, 13, 11, 11, 13, 13, 13, 6, 5, 18, 5, 5, 6, 30, 30, 11, 11, 35, 35, 35, 6, 35, 35,
				35, 35, 35, 35, 35, 6, 6 };
	}

	public List convertDataToList(Object dto, String langCode) {
		TRI_SJ_MST entity = (TRI_SJ_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 取引先コード
		list.add(entity.getTRI_CODE());
		// 取引先略称
		list.add(entity.getTRI_NAME_S());
		// 取引先条件コード
		list.add(entity.getTJK_CODE());
		// 振込手数料区分
		list.add(new AlignString(entity.getFURI_TESU_KBN() == 1 ? getWord("C00399", langCode) : getWord("C00327",
			langCode), AlignString.CENTER));
		// 支払条件締め日
		// list.add(Integer.parseInt(entity.getSJC_DATE()));
		list.add(new AlignString(entity.getSJC_DATE(), AlignString.LEFT));
		// 支払条件締め後月
		// list.add(Integer.parseInt(entity.getSJR_MON()));
		list.add(new AlignString(entity.getSJR_MON(), AlignString.LEFT));
		// 支払条件支払日
		// list.add(Integer.parseInt(entity.getSJP_DATE()));
		list.add(new AlignString(entity.getSJP_DATE(), AlignString.LEFT));
		// 支払区分
		list.add(new AlignString("00".equals(entity.getSIHA_KBN()) ? getWord("C02166", langCode) : getWord("C02167",
			langCode), AlignString.CENTER));
		// 支払方法
		list.add(entity.getSIHA_HOU_CODE());
		// 振込振出銀行口座ｺｰﾄﾞ
		list.add(entity.getFURI_CBK_CODE());
		// 銀行コード
		list.add(entity.getBNK_CODE());
		// 支店コード
		list.add(entity.getBNK_STN_CODE());
		// 預金種目
		list.add(new AlignString(getWord(entity.getYKN_KBN(), mapYknKbn, langCode), AlignString.CENTER));
		// 口座番号
		list.add(entity.getYKN_NO());
		// 口座名義カナ
		list.add(entity.getYKN_KANA());
		// 送金目的コード
		list.add(entity.getGS_MKT_CODE());
		// 送金目的名
		list.add(entity.getMKT_NAME());
		// 被仕向銀行名称
		list.add(entity.getGS_BNK_NAME());
		// 被仕向支店名称
		list.add(entity.getGS_STN_NAME());
		// 口座名義
		list.add(entity.getYKN_NAME());
		// 手数料区分
		list.add(new AlignString(entity.getGS_TESU_KBN() == 1 ? getWord("C00021", langCode) : getWord("C02319",
			langCode), AlignString.CENTER));
		// 支払銀行名称
		list.add(entity.getSIHA_BNK_NAME());
		// 支払支店名称
		list.add(entity.getSIHA_STN_NAME());
		// 支払銀行住所
		list.add(entity.getSIHA_BNK_ADR());
		// 経由銀行名称
		list.add(entity.getKEIYU_BNK_NAME());
		// 経由支店名称
		list.add(entity.getKEIYU_STN_NAME());
		// 経由銀行住所
		list.add(entity.getKEIYU_BNK_ADR());
		// 受取人住所
		list.add(entity.getUKE_ADR());
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
