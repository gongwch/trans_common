package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * �����S���҈ꗗ�G�N�Z��
 */
public class CustomerUsrExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public CustomerUsrExcel(String lang) {
		super(lang);
	}

	/**
	 * �����S���҈ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param customerList �����S���҈ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<CustomerUser> customerList) throws TException {

		try {
			createReport(customerList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param customerList
	 */
	public void createReport(List<CustomerUser> customerList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C00363") + getWord("C00010"));// �S���҈ꗗ

		// �J�����ݒ�
		sheet.addColumn(getWord("C00053"), 4200);// ��ЃR�[�h
		sheet.addColumn(getWord("C00786"), 4200);// �����R�[�h
		sheet.addColumn(getWord("C00830"), 8400);// ����於��
		sheet.addColumn(getWord("C00980"), 3000);// �V�X�e���敪
		sheet.addColumn(getWord("C11296"), 12000);// �S���Җ���
		sheet.addColumn(getWord("NK0182") + getWord("C00518"), 12000);// ��������
		sheet.addColumn(getWord("C12094"), 12000);// ��E

		// ���ו`��
		for (CustomerUser bean : customerList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKAI_CODE());
			newRow.addCell(bean.getTRI_CODE());
			newRow.addCell(bean.getTRI_NAME());
			newRow.addCell(bean.getSYS_KBN(), SwingConstants.CENTER);
			newRow.addCell(bean.getUSR_NAME());
			newRow.addCell(bean.getDEP_NAME());
			newRow.addCell(bean.getPOSITION());

		}

	}
}
