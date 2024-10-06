package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.department.*;

/**
 * ����K�w�ꗗ�G�N�Z��
 */
public class DepartmentHierarchyExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public DepartmentHierarchyExcel(String lang) {
		super(lang);
	}

	/**
	 * ����K�w�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param DepartmentList ����K�w�ꗗ
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
	 * �G�N�Z�����o�͂���
	 * 
	 * @param departmentList
	 */
	public void createReport(List<DepartmentOrganization> departmentList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C02473"));

		// �J�����ݒ�
		sheet.addColumn(getWord("C00467"), 5000);
		sheet.addColumn(getWord("C01739"), 3200);
		sheet.addColumn(getWord("C01751"), 5000);
		sheet.addColumn(getWord("C01752"), 5000);
		sheet.addColumn(getWord("C01753"), 5000);
		sheet.addColumn(getWord("C01754"), 5000);
		sheet.addColumn(getWord("C01755"), 5000);
		sheet.addColumn(getWord("C01756"), 5000);
		sheet.addColumn(getWord("C01757"), 5000);
		sheet.addColumn(getWord("C01758"), 5000);
		sheet.addColumn(getWord("C01759"), 5000);

		// ���ו`��
		for (DepartmentOrganization department : departmentList) {

			TExcelRow newRow = sheet.addRow();
			if (department.getLevel() == 0) {
				newRow.addCell(department.getDepCode() + " " + department.getDepName());
			} else {
				newRow.addCell(department.getLevel0() + " " + department.getLevel0Name());
			}
			newRow.addCell(department.getLevel());
			if (!Util.isNullOrEmpty(department.getLevel1()) && Util.isNullOrEmpty(department.getLevel2())) {
				newRow.addCell(department.getLevel1() + " " + department.getLevel1Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel2()) && Util.isNullOrEmpty(department.getLevel3())) {
				newRow.addCell(department.getLevel2() + " " + department.getLevel2Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel3()) && Util.isNullOrEmpty(department.getLevel4())) {
				newRow.addCell(department.getLevel3() + " " + department.getLevel3Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel4()) && Util.isNullOrEmpty(department.getLevel5())) {
				newRow.addCell(department.getLevel4() + " " + department.getLevel4Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel5()) && Util.isNullOrEmpty(department.getLevel6())) {
				newRow.addCell(department.getLevel5() + " " + department.getLevel5Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel6()) && Util.isNullOrEmpty(department.getLevel7())) {
				newRow.addCell(department.getLevel6() + " " + department.getLevel6Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel7()) && Util.isNullOrEmpty(department.getLevel8())) {
				newRow.addCell(department.getLevel7() + " " + department.getLevel7Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel8()) && Util.isNullOrEmpty(department.getLevel9())) {
				newRow.addCell(department.getLevel8() + " " + department.getLevel8Name());
			} else {
				newRow.addEmptyCell();
			}
			if (!Util.isNullOrEmpty(department.getLevel9())) {
				newRow.addCell(department.getLevel9() + " " + department.getLevel9Name());
			} else {
				newRow.addEmptyCell();
			}

		}

	}
}