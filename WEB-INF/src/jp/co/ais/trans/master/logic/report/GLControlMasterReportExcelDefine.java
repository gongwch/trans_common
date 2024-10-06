package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * GLコントロールマスタのExcel定義クラス
 */
public class GLControlMasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0040";
	}

	public String getSheetName() {
		// GLコントロールマスタを返す
		return "C02339";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00145", "C01056", "C00524", "C00454" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 10, 10, 16, 18, 12 };
	}

	private Map mapKsnNyuKbn;

	private Map mapMtZanHyjKbn;

	private Map mapExcRateKbn;

	/**
	 * 
	 */
	public GLControlMasterReportExcelDefine() {
		// 決算伝票入力区分 - 0:１年 1:半期 2:四半期 3:月次
		mapKsnNyuKbn = new HashMap();
		mapKsnNyuKbn.put(0, "C00009");
		mapKsnNyuKbn.put(1, "C00435");
		mapKsnNyuKbn.put(2, "C00239");
		mapKsnNyuKbn.put(3, "C00147");
		// 元帳日別残高表示区分 - 0:日別残高を表示しない 1:日別残高を表示する
		mapMtZanHyjKbn = new HashMap();
		mapMtZanHyjKbn.put(0, "C02369");
		mapMtZanHyjKbn.put(1, "C02370");
		// 評価替レート区分 - 0:月末レート 1:翌月月初レート
		mapExcRateKbn = new HashMap();
		mapExcRateKbn.put(0, "C00148");
		mapExcRateKbn.put(1, "C00535");
	}

	public List convertDataToList(Object dto, String langCode) {
		GL_CTL_MST entity = (GL_CTL_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 決算段階区分

		int ksdkbn = entity.getKSD_KBN();
		if (ksdkbn == 0) {
			list.add(new AlignString(getWord("C00038", langCode), AlignString.CENTER));
		} else if (ksdkbn > 0) {
			list.add(new AlignString(ksdkbn + getWord("C00373", langCode), AlignString.CENTER));
		} else {
			list.add(new AlignString(String.valueOf(ksdkbn), AlignString.CENTER));
		}

		// 決算伝票入力区分
		list.add(new AlignString(getWord(entity.getKSN_NYU_KBN(), mapKsnNyuKbn, langCode), AlignString.CENTER));
		// 元帳日別残高表示区分
		list.add(new AlignString(getWord(entity.getMT_ZAN_HYJ_KBN(), mapMtZanHyjKbn, langCode), AlignString.CENTER));
		// 評価替レート区分
		list.add(new AlignString(getWord(entity.getEXC_RATE_KBN(), mapExcRateKbn, langCode), AlignString.CENTER));

		return list;
	}
}
