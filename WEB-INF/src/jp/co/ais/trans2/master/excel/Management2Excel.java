package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�1�ꗗ�G�N�Z��
 */
public class Management2Excel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public Management2Excel(String lang) {
		super(lang);
	}

	/**
	 * �Ǘ��Q�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param company ���
	 * @param list    �Ǘ��Q���X�g
	 * @return �Ǘ��Q�ꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management2> list) throws TException {

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
	public void createReport(Company company, List<Management2> list) {

		String management2Name = "";
		if (Util.isNullOrEmpty(company.getAccountConfig().getManagement2Name())) {
			management2Name = getWord("C01025");
		} else {
			management2Name = company.getAccountConfig().getManagement2Name();
		}

		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(management2Name + getWord("C00500"));

		// �J�����ݒ�
		// ��ЃR�[�h
		sheet.addColumn(getWord("C00596"), 8400);
		// �R�[�h
		sheet.addColumn(management2Name + getWord("C00174"), 8400);
		// ����
		sheet.addColumn(management2Name + getWord("C00518"), 8400);
		// ����
		sheet.addColumn(management2Name + getWord("C00548"), 8400);
		// ��������
		sheet.addColumn(management2Name + getWord("C00160"), 8400);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Management2 m2 : list) {

			TExcelRow newRow = sheet.addRow();
			
			newRow.addCell(m2.getCompanyCode());
			newRow.addCell(m2.getCode());
			newRow.addCell(m2.getName());
			newRow.addCell(m2.getNames());
			newRow.addCell(m2.getNamek());
			newRow.addCell(DateUtil.toYMDString(m2.getDateFrom()),SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m2.getDateTo()),SwingConstants.CENTER);
		}
	}
}
