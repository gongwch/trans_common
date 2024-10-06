package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門一覧エクセル
 */
public class DepartmentExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public DepartmentExcel(String lang) {
		super(lang);
	}

	/**
	 * 部門一覧をエクセルで返す
	 * 
	 * @param DepartmentList 部門一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Department> DepartmentList) throws TException {

		try {
			createReport(DepartmentList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param departmentList
	 */
	public void createReport(List<Department> departmentList) {
		// 丹羽汽船カスタマイズ：連携コード使用判定
		boolean isUseIf1 = departmentList.get(0).isUseIf1();
		boolean isUseIf2 = departmentList.get(0).isUseIf2();
		boolean isUseIf3 = departmentList.get(0).isUseIf3();

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02338"));

		// カラム設定
		sheet.addColumn(getWord("C00698"), 3200);
		sheet.addColumn(getWord("C00723"), 8400);
		sheet.addColumn(getWord("C00724"), 4200);
		sheet.addColumn(getWord("C00725"), 6400);
		sheet.addColumn(getWord("C00726"), 4200);
		sheet.addColumn(getWord("C00727"), 4200);
		sheet.addColumn(getWord("C00728"), 3200);
		sheet.addColumn(getWord("C01303"), 3200);
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);
		if (isUseIf1) {
			sheet.addColumn(getWord("C12060"), 3200);
			sheet.addColumn(getWord("C12061"), 8400);
		}
		if (isUseIf2) {
			sheet.addColumn(getWord("C12062"), 3200);
			sheet.addColumn(getWord("C12063"), 8400);
		}
		if (isUseIf3) {
			sheet.addColumn(getWord("C12064"), 3200);
			sheet.addColumn(getWord("C12065"), 8400);
		}

		// 明細描画
		for (Department department : departmentList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(department.getCode());
			newRow.addCell(department.getName());
			newRow.addCell(department.getNames());
			newRow.addCell(department.getNamek());
			newRow.addCell(department.getPersonnel1());
			newRow.addCell(department.getPersonnel2());
			newRow.addCell(department.getFloorSpace(), 2);
			newRow.addCell(department.isSumDepartment() ? getWord("C00255") : getWord("C01275"), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(department.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(department.getDateTo()), SwingConstants.CENTER);
			if (isUseIf1) {
				newRow.addCell(department.getIfCode1());
				newRow.addCell(department.getIfName1());
			}
			if (isUseIf2) {
				newRow.addCell(department.getIfCode2());
				newRow.addCell(department.getIfName2());
			}
			if (isUseIf3) {
				newRow.addCell(department.getIfCode3());
				newRow.addCell(department.getIfName3());
			}

		}

	}
}