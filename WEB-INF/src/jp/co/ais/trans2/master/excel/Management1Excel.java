package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�1�ꗗ�G�N�Z��
 */
public class Management1Excel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public Management1Excel(String lang) {
		super(lang);
	}

	/**
	 * �Ǘ�1�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param company ���
	 * @param list �Ǘ�1���X�g
	 * @return �Ǘ�1�ꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management1> list) throws TException {

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
	public void createReport(Company company, List<Management1> list) {

		String management1Name = "";
		if (company.getAccountConfig().getManagement1Name() == null) {
			management1Name = getWord("C01025");
		} else {
			management1Name = company.getAccountConfig().getManagement1Name();
		}

		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(management1Name + getWord("C00500"));

		// �J�����ݒ�
		// �R�[�h
		sheet.addColumn(management1Name + getWord("C00174"), 4200);
		// ����
		sheet.addColumn(management1Name + getWord("C00518"), 8400);
		// ����
		sheet.addColumn(management1Name + getWord("C00548"), 8400);
		// ��������
		sheet.addColumn(management1Name + getWord("C00160"), 8400);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Management1 m1 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m1.getCode());
			newRow.addCell(m1.getName());
			newRow.addCell(m1.getNames());
			newRow.addCell(m1.getNamek());
			newRow.addCell(m1.getDateFrom());
			newRow.addCell(m1.getDateTo());
		}
	}
}
