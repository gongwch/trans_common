package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�4�ꗗ�G�N�Z��
 */
public class Management4Excel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public Management4Excel(String lang) {
		super(lang);
	}

	/**
	 * �Ǘ�4�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param company ���
	 * @param list    �Ǘ�4���X�g
	 * @return �Ǘ�4�ꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management4> list) throws TException {

		try {
			createReport(company, list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param company
	 * @param list
	 */
	public void createReport(Company company, List<Management4> list) {

		String management4Name = "";
		if (company.getAccountConfig().getManagement4Name() == null) {
			management4Name = getWord("C01031");
		} else {
			management4Name = company.getAccountConfig().getManagement4Name();
		}

		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(management4Name + getWord("C00500"));

		// �J�����ݒ�
		// ��ЃR�[�h
		sheet.addColumn(getWord("C00596"), 4200);
		// �R�[�h
		sheet.addColumn(management4Name + getWord("C00174"), 4200);
		// ����
		sheet.addColumn(management4Name + getWord("C00518"), 8400);
		// ����
		sheet.addColumn(management4Name + getWord("C00548"), 8400);
		// ��������
		sheet.addColumn(management4Name + getWord("C00160"), 8400);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Management4 m4 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m4.getCompanyCode());
			newRow.addCell(m4.getCode());
			newRow.addCell(m4.getName());
			newRow.addCell(m4.getNames());
			newRow.addCell(m4.getNamek());
			newRow.addCell(DateUtil.toYMDString(m4.getDateFrom()),SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m4.getDateTo()), SwingConstants.CENTER);
		}
	}
}
