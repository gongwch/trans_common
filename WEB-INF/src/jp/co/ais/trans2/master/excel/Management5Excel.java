package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�5�ꗗ�G�N�Z��
 */
public class Management5Excel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public Management5Excel(String lang) {
		super(lang);
	}

	/**
	 * �Ǘ�5�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param company ���
	 * @param list �Ǘ�5���X�g
	 * @return �Ǘ�5�ꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management5> list) throws TException {

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
	public void createReport(Company company, List<Management5> list) {

		String management5Name = "";
		if (Util.isNullOrEmpty(company.getAccountConfig().getManagement5Name())) {
			management5Name = getWord("C01033");
		} else {
			management5Name = company.getAccountConfig().getManagement5Name();
		}

		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(management5Name + getWord("C00500"));

		// �J�����ݒ�
		// ��ЃR�[�h
		sheet.addColumn(getWord("C00596"), 4200);
		// �R�[�h
		sheet.addColumn(management5Name + getWord("C00174"), 4200);
		// ����
		sheet.addColumn(management5Name + getWord("C00518"), 8400);
		// ����
		sheet.addColumn(management5Name + getWord("C00548"), 8400);
		// ��������
		sheet.addColumn(management5Name + getWord("C00160"), 8400);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Management5 m1 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m1.getCompanyCode());
			newRow.addCell(m1.getCode());
			newRow.addCell(m1.getName());
			newRow.addCell(m1.getNames());
			newRow.addCell(m1.getNamek());
			newRow.addCell(DateUtil.toYMDString(m1.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m1.getDateTo()), SwingConstants.CENTER);
		}
	}
}
