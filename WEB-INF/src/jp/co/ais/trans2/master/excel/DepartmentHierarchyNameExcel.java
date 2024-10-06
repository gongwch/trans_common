package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門階層一覧エクセル
 */
public class DepartmentHierarchyNameExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public DepartmentHierarchyNameExcel(String lang) {
		super(lang);
	}

	/**
	 * 部門階層一覧をエクセルで返す
	 * 
	 * @param DepartmentList 部門階層一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcelHierarchy(List<DepartmentOrganization> DepartmentList) throws TException {

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
	public void createReport(List<DepartmentOrganization> departmentList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02473"));

		String lvl0 = departmentList.get(0).getDPK_LVL_0_NAME();
		String lvl1 = departmentList.get(0).getDPK_LVL_1_NAME();
		String lvl2 = departmentList.get(0).getDPK_LVL_2_NAME();
		String lvl3 = departmentList.get(0).getDPK_LVL_3_NAME();
		String lvl4 = departmentList.get(0).getDPK_LVL_4_NAME();
		String lvl5 = departmentList.get(0).getDPK_LVL_5_NAME();
		String lvl6 = departmentList.get(0).getDPK_LVL_6_NAME();
		String lvl7 = departmentList.get(0).getDPK_LVL_7_NAME();
		String lvl8 = departmentList.get(0).getDPK_LVL_8_NAME();
		String lvl9 = departmentList.get(0).getDPK_LVL_9_NAME();

		// カラム設定
		sheet.addColumn(getWord("C11967"), 5000);
		sheet.addColumn(getWord("C01739"), 3200);
		if (!Util.isNullOrEmpty(lvl0)) {
			sheet.addColumn(lvl0, 5000);
		} else {
			sheet.addColumn(getWord("C00722"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl1)) {
			sheet.addColumn(lvl1, 5000);
		} else {
			sheet.addColumn(getWord("C01751"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl2)) {
			sheet.addColumn(lvl2, 5000);
		} else {
			sheet.addColumn(getWord("C01752"), 5000);
		}

		if (!Util.isNullOrEmpty(lvl3)) {
			sheet.addColumn(lvl3, 5000);
		} else {
			sheet.addColumn(getWord("C01753"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl4)) {
			sheet.addColumn(lvl4, 5000);
		} else {
			sheet.addColumn(getWord("C01754"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl5)) {
			sheet.addColumn(lvl5, 5000);
		} else {
			sheet.addColumn(getWord("C01755"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl6)) {
			sheet.addColumn(lvl6, 5000);
		} else {
			sheet.addColumn(getWord("C01756"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl7)) {
			sheet.addColumn(lvl7, 5000);
		} else {
			sheet.addColumn(getWord("C01757"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl8)) {
			sheet.addColumn(lvl8, 5000);
		} else {
			sheet.addColumn(getWord("C01758"), 5000);
		}
		if (!Util.isNullOrEmpty(lvl9)) {
			sheet.addColumn(lvl9, 5000);
		} else {
			sheet.addColumn(getWord("C01759"), 5000);
		}

		// 明細描画
		for (DepartmentOrganization department : departmentList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(department.getName());
			newRow.addCell(department.getLevel());

			if (!Util.isNullOrEmpty(department.getDepName()) && department.getLevel() == 0) {
				newRow.addCell(department.getDepCode() + " " + department.getDepName());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel1()) && department.getLevel() == 1) {
				newRow.addCell(department.getLevel1() + " " + department.getLevel1Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel2()) && department.getLevel() == 2) {
				newRow.addCell(department.getLevel2() + " " + department.getLevel2Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel3()) && department.getLevel() == 3) {
				newRow.addCell(department.getLevel3() + " " + department.getLevel3Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel4()) && department.getLevel() == 4) {
				newRow.addCell(department.getLevel4() + " " + department.getLevel4Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel5()) && department.getLevel() == 5) {
				newRow.addCell(department.getLevel5() + " " + department.getLevel5Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel6()) && department.getLevel() == 6) {
				newRow.addCell(department.getLevel6() + " " + department.getLevel6Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel7()) && department.getLevel() == 7) {
				newRow.addCell(department.getLevel7() + " " + department.getLevel7Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel8()) && department.getLevel() == 8) {
				newRow.addCell(department.getLevel8() + " " + department.getLevel8Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel9()) && department.getLevel() == 9) {
				newRow.addCell(department.getLevel9() + " " + department.getLevel9Name());
			} else {
				newRow.addEmptyCell();
			}

		}

	}
}