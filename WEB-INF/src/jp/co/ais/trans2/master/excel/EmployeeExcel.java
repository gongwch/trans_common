package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * MG0160EmployeeMaster - �Ј��}�X�^ - Export Excel
 * 
 * @author AIS
 */
public class EmployeeExcel extends TExcel {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����敪
	 */
	public EmployeeExcel(String lang) {
		super(lang);
	}

	/**
	 * �Ј��ꗗ���G�N�Z���œn��
	 * 
	 * @param list �ꗗ�f�[�^�̔z��
	 * @exception TException
	 * @return byte Excel
	 */
	public byte[] getExcel(List<Employee> list) throws TException {
		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����C�A�E�g��` �e�J�����̃f�[�^���Z�b�g
	 * 
	 * @param list �ꗗ�f�[�^�̔z��
	 */
	public void createReport(List<Employee> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C00913"));

		// �J�����ݒ�
		sheet.addColumn(getWord("C00697"), 5000);
		sheet.addColumn(getWord("C00807"), 8000);
		sheet.addColumn(getWord("C00808"), 8000);
		sheet.addColumn(getWord("C00809"), 10000);
		sheet.addColumn(getWord("C00810"), 8000);
		sheet.addColumn(getWord("C00475"), 8000);
		sheet.addColumn(getWord("C00811"), 5000);
		sheet.addColumn(getWord("C02470"), 8000);
		sheet.addColumn(getWord("C00812"), 5000);
		sheet.addColumn(getWord("C00473"), 8000);
		sheet.addColumn(getWord("C00471"), 5000);
		sheet.addColumn(getWord("C00813"), 5000);
		sheet.addColumn(getWord("C01068"), 8000);
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Employee bean : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(bean.getCbkCode());
			newRow.addCell(bean.getCbkName());
			newRow.addCell(bean.getBnkCode());
			newRow.addCell(bean.getBnkName());
			newRow.addCell(bean.getStnCode());
			newRow.addCell(bean.getStnName());
			newRow.addCell(getWord(bean.getKozaKbnText()), 0);
			newRow.addCell(bean.getYknNo());
			newRow.addCell(bean.getYknKana());
			newRow.addCell(bean.getDateFrom());
			newRow.addCell(bean.getDateTo());
		}
	}
}