package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 消費税マスタのExcel定義クラス
 */
public class ConsumptionTaxMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0130";
	}

	public String getSheetName() {
		// 消費税マスタを返す
		return "C02324";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00573", "C00774", "C00775", "C00828", "C01283", "C00777", "C02045", "C00055",
				"C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 10, 40, 20, 40, 10, 8, 25, 6, 6 };
	}

	private Map mapUsKbn;

	/**
	 * 
	 */
	public ConsumptionTaxMasterReportExcelDefine() {
		// 売上仕入区分 0:対象外 1:売上 2:仕入
		mapUsKbn = new HashMap();
		mapUsKbn.put(0, "C02103");
		mapUsKbn.put(1, "C00026");
		mapUsKbn.put(2, "C00201");

	}

	public List convertDataToList(Object dto, String langCode) {
		SZEI_MST entity = (SZEI_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 消費税コード
		list.add(entity.getZEI_CODE());
		// 消費税名称
		list.add(entity.getZEI_NAME());
		// 消費税略称
		list.add(entity.getZEI_NAME_S());
		// 消費税検索名称
		list.add(entity.getZEI_NAME_K());
		// 売上仕入区分
		list.add(new AlignString(getWord(entity.getUS_KBN(), mapUsKbn, langCode), AlignString.CENTER));
		// 消費税率
		// DecimalFormat format = new DecimalFormat("##.0");
		// String zeiRate = format.format(Float.valueOf(String.valueOf(entity.getZEI_RATE())));
		// list.add(zeiRate);
		double rate = (Float.valueOf(entity.getZEI_RATE() * 10)).intValue();
		list.add(new AlignString(Double.toString(rate / 10), AlignString.RIGHT));// entity.getZEI_RATE());
		// 消費税計算書出力順序
		list.add(new AlignString(Util.isNullOrEmpty(entity.getSZEI_KEI_KBN()) ? getWord("C00282", langCode) : entity
			.getSZEI_KEI_KBN(), AlignString.RIGHT));
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
