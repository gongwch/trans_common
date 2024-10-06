package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社階層一覧エクセル
 */
public class CompanyHierarchyNameExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public CompanyHierarchyNameExcel(String lang) {
		super(lang);
	}

	/**
	 * 会社階層一覧をエクセルで返す
	 * 
	 * @param companyList
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcelHierarchy(List<CompanyOrganization> companyList) throws TException {

		try {
			createReport(companyList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param companyList
	 */
	public void createReport(List<CompanyOrganization> companyList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02220"));

		String lvl0 = companyList.get(0).getDPK_LVL_0_NAME();
		String lvl1 = companyList.get(0).getDPK_LVL_1_NAME();
		String lvl2 = companyList.get(0).getDPK_LVL_2_NAME();
		String lvl3 = companyList.get(0).getDPK_LVL_3_NAME();
		String lvl4 = companyList.get(0).getDPK_LVL_4_NAME();
		String lvl5 = companyList.get(0).getDPK_LVL_5_NAME();
		String lvl6 = companyList.get(0).getDPK_LVL_6_NAME();
		String lvl7 = companyList.get(0).getDPK_LVL_7_NAME();
		String lvl8 = companyList.get(0).getDPK_LVL_8_NAME();
		String lvl9 = companyList.get(0).getDPK_LVL_9_NAME();

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
		for (CompanyOrganization company : companyList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(company.getName());
			newRow.addCell(company.getLevel());

			if (!Util.isNullOrEmpty(company.getLevel0()) && company.getLevel() == 0) {
				newRow.addCell(company.getLevel0() + " " + company.getLevel0Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel1()) && company.getLevel() == 1) {
				newRow.addCell(company.getLevel1() + " " + company.getLevel1Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel2()) && company.getLevel() == 2) {
				newRow.addCell(company.getLevel2() + " " + company.getLevel2Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel3()) && company.getLevel() == 3) {
				newRow.addCell(company.getLevel3() + " " + company.getLevel3Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel4()) && company.getLevel() == 4) {
				newRow.addCell(company.getLevel4() + " " + company.getLevel4Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel5()) && company.getLevel() == 5) {
				newRow.addCell(company.getLevel5() + " " + company.getLevel5Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel6()) && company.getLevel() == 6) {
				newRow.addCell(company.getLevel6() + " " + company.getLevel6Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel7()) && company.getLevel() == 7) {
				newRow.addCell(company.getLevel7() + " " + company.getLevel7Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel8()) && company.getLevel() == 8) {
				newRow.addCell(company.getLevel8() + " " + company.getLevel8Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(company.getLevel9()) && company.getLevel() == 9) {
				newRow.addCell(company.getLevel9() + " " + company.getLevel9Name());
			} else {
				newRow.addEmptyCell();
			}

		}

	}
}