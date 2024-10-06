package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�3�ꗗ�G�N�Z��
 */
public class Management3Excel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public Management3Excel(String lang) {
		super(lang);
	}

	/**
	 * �Ǘ�3�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param company ���
	 * @param list    �Ǘ�3���X�g
	 * @return �Ǘ�3�ꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management3> list) throws TException {

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
	public void createReport(Company company, List<Management3> list) {

		String management3Name = "";
		if (company.getAccountConfig().getManagement3Name() == null) {
			management3Name = getWord("C01029");
		} else {
			management3Name = company.getAccountConfig().getManagement3Name();
		}

		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(management3Name + getWord("C00500"));

		// �J�����ݒ�

		// ��ЃR�[�h
		sheet.addColumn(getWord("C00596"), 5500);
		// �R�[�h
		sheet.addColumn(management3Name + getWord("C00174"), 5500);
		// ����
		sheet.addColumn(management3Name + getWord("C00518"), 8400);
		// ����
		sheet.addColumn(management3Name + getWord("C00548"), 8400);
		// ��������
		sheet.addColumn(management3Name + getWord("C00160"), 8400);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Management3 m3 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m3.getCompanyCode());
			newRow.addCell(m3.getCode());
			newRow.addCell(m3.getName());
			newRow.addCell(m3.getNames());
			newRow.addCell(m3.getNamek());
			newRow.addCell(DateUtil.toYMDString(m3.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m3.getDateTo()), SwingConstants.CENTER);
		}
	}
}
