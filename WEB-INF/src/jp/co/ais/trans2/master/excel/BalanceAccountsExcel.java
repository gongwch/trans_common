package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * ���ێ��x�ꗗ�G�N�Z��
 */
public class BalanceAccountsExcel extends TExcel {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����R�[�h
	 */
	public BalanceAccountsExcel(String lang) {
		super(lang);
	}

	/**
	 * ���ێ��x�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param remittanceList ���ێ��x�ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Remittance> remittanceList) throws TException {

		try {
			createReport(remittanceList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param remittanceList
	 */
	public void createReport(List<Remittance> remittanceList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C11841")); // ���ێ��x�R�[�h�}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("C11839"), 5200); // ���ێ��x�R�[�h
		sheet.addColumn(getWord("C11842"), 8400); // ���ێ��x����

		// ���ו`��
		for (Remittance remittance : remittanceList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(remittance.getCode());
			newRow.addCell(remittance.getName());
		}
	}
}