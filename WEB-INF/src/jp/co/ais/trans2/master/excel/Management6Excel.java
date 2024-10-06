package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�6�ꗗ�G�N�Z��
 */
public class Management6Excel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public Management6Excel(String lang) {
		super(lang);
	}

	/**
	 * �Ǘ�6�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param company ���
	 * @param list �Ǘ�6���X�g
	 * @return �Ǘ�6�ꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<Management6> list) throws TException {

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
	 * @param list
	 */
	public void createReport(Company company, List<Management6> list) {

		String management6Name = "";
		if (Util.isNullOrEmpty(company.getAccountConfig().getManagement6Name())) {
			management6Name = getWord("C01035");
		} else {
			management6Name = company.getAccountConfig().getManagement6Name();
		}

		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(management6Name + getWord("C00500"));

		// �J�����ݒ�

		// ��ЃR�[�h
		sheet.addColumn(getWord("C00596"), 4200);
		// �Ǘ��U�R�[�h
		sheet.addColumn(management6Name + getWord("C00174"), 4200);
		// �Ǘ��U����
		sheet.addColumn(management6Name + getWord("C00518"), 8400);
		// �Ǘ��U����
		sheet.addColumn(management6Name + getWord("C00548"), 8400);
		// �Ǘ��U��������
		sheet.addColumn(management6Name + getWord("C00160"), 8400);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Management6 m6 : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(m6.getCompanyCode());
			newRow.addCell(m6.getCode());
			newRow.addCell(m6.getName());
			newRow.addCell(m6.getNames());
			newRow.addCell(m6.getNamek());
			newRow.addCell(DateUtil.toYMDString(m6.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(m6.getDateTo()), SwingConstants.CENTER);
		}
	}
}
