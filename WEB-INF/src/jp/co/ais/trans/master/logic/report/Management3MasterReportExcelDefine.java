package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 管理3マスタのExcel定義クラス
 */
public class Management3MasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0200";
	}

	public String getSheetName() {
		// 管理３マスタを返す
		return "C02345";
	}

	public String[] getHeaderTexts() {
		CMP_MST info = this.getCompanyInfo(getKaiCode());
		// 言語コード
		String lang = getLangCode();
		// 管理３名 ＋ コード
		String code = String.valueOf(info.getKNR_NAME_3()) + getWord("C00174", lang);
		// 管理３名 ＋ 名称
		String name = String.valueOf(info.getKNR_NAME_3()) + getWord("C00518", lang);
		// 管理３名 ＋ 略称
		String nameS = String.valueOf(info.getKNR_NAME_3()) + getWord("C00548", lang);
		// 管理３名 ＋ 検索名称
		String nameK = String.valueOf(info.getKNR_NAME_3()) + getWord("C00160", lang);
		// タイトルを返す
		return new String[] { "C00596", code, name, nameS, nameK, "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 10, 10, 20, 20, 40, 6, 6, };
	}

	public List convertDataToList(Object dto, String langCode) {
		KNR3_MST entity = (KNR3_MST) dto;
		List list = new ArrayList();

		// 会社コード
		list.add(entity.getKAI_CODE());
		// [%管理名称3%]コード
		list.add(entity.getKNR_CODE_3());
		// [%管理名称3%]名称
		list.add(entity.getKNR_NAME_3());
		// [%管理名称3%]略称
		list.add(entity.getKNR_NAME_S_3());
		// [%管理名称3%]検索名称
		list.add(entity.getKNR_NAME_K_3());
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}

	/**
	 * @param kaiCode
	 * @return CMP_MST
	 */
	public CMP_MST getCompanyInfo(String kaiCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyControlLogic logic = (CompanyControlLogic) container.getComponent(CompanyControlLogic.class);

		CMP_MST map = null;
		// 結果を取得する。
		map = (CMP_MST) logic.findOne(kaiCode);
		return map;
	}
}
