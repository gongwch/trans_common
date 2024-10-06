package jp.co.ais.trans.master.logic.report;

import java.util.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 部門マスタのExcel定義クラス
 */
public class DepartmentMasterReportExcelDefine extends ReportExcelDefineBase {
	public String getFileName() {
		return "MG0060";
	}

	public String getSheetName() {
		// 部門マスタを返す
		return "C02338";
	}

	public String[] getHeaderTexts() {
		// タイトルを返す
		return new String[] { "C00596", "C00698", "C00723", "C00724", "C00725", "C00726", "C00727", "C00728", "C01303",
				"C00055", "C00261" };
	}

	public short[] getColumnWidths() {
		// 列幅を返す
		return new short[] { 0, 10, 40, 20, 40, 6, 6, 7, 7, 6, 6 };
	}

	public List convertDataToList(Object dto, String langCode) {
		BMN_MST entity = (BMN_MST) dto;
		List list = new ArrayList();
		FormatDecimal fd1, fd2, fd3;

		// 会社コード
		list.add(entity.getKAI_CODE());
		// 部門コード
		list.add(entity.getDEP_CODE());
		// 部門名称
		list.add(entity.getDEP_NAME());
		// 部門略称
		list.add(entity.getDEP_NAME_S());
		// 部門検索名称
		list.add(entity.getDEP_NAME_K());
		// 人員数１
		if (entity.getMEN_1() == null) {
			list.add("");
		} else {
			fd1 = new FormatDecimal(entity.getMEN_1(), "###,##0");
			list.add(fd1);
		}

		// 人員数2
		if (entity.getMEN_2() == null) {
			list.add("");
		} else {
			fd2 = new FormatDecimal(entity.getMEN_2(), "###,##0");
			list.add(fd2);
		}

		// 床面積
		if (entity.getAREA() == null) {
			list.add("");
		} else {
			fd3 = new FormatDecimal(entity.getAREA(), "###,##0.00");
			list.add(fd3);
		}

		// 部門区分
		String depKbn = String.valueOf(entity.getDEP_KBN());
		if ("0".equals(depKbn)) {
			list.add(new AlignString(getWord("C01275", langCode), AlignString.CENTER));
		} else {
			list.add(new AlignString(getWord("C00255", langCode), AlignString.CENTER));
		}
		// 開始年月日
		list.add(entity.getSTR_DATE());
		// 終了年月日
		list.add(entity.getEND_DATE());

		return list;
	}
}
