package jp.co.ais.trans.master.logic.report;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ว6}X^ฬExcel่`NX
 */
public class Management6MasterReportExcelDefine extends ReportExcelDefineBase {

	public String getFileName() {
		return "MG0230";
	}

	public String getSheetName() {
		// วU}X^๐ิท
		return "C02348";
	}

	public String[] getHeaderTexts() {
		CMP_MST info = this.getCompanyInfo(getKaiCode());
		// พ๊R[h
		String lang = getLangCode();
		// วUผ { R[h
		String code = String.valueOf(info.getKNR_NAME_6()) + getWord("C00174", lang);
		// วUผ { ผฬ
		String name = String.valueOf(info.getKNR_NAME_6()) + getWord("C00518", lang);
		// วUผ { ชฬ
		String nameS = String.valueOf(info.getKNR_NAME_6()) + getWord("C00548", lang);
		// วUผ { ๕ผฬ
		String nameK = String.valueOf(info.getKNR_NAME_6()) + getWord("C00160", lang);
		// ^Cg๐ิท
		return new String[] { "C00596", code, name, nameS, nameK, "C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// ๑๐ิท
		return new short[] { 10, 10, 20, 20, 40, 6, 6, };
	}

	public List convertDataToList(Object dto, String langCode) {
		KNR6_MST entity = (KNR6_MST) dto;
		List list = new ArrayList();

		// ๏ะR[h
		list.add(entity.getKAI_CODE());
		// [%วผฬ6%]R[h
		list.add(entity.getKNR_CODE_6());
		// [%วผฬ6%]ผฬ
		list.add(entity.getKNR_NAME_6());
		// [%วผฬ6%]ชฬ
		list.add(entity.getKNR_NAME_S_6());
		// [%วผฬ6%]๕ผฬ
		list.add(entity.getKNR_NAME_K_6());
		// JnN๚
		list.add(entity.getSTR_DATE());
		// IนN๚
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
		// ส๐ๆพท้B
		map = (CMP_MST) logic.findOne(kaiCode);
		return map;
	}
}
