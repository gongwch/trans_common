package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * �����ړI�ꗗ�G�N�Z��
 */
public class RemittancePurposeOldExcel extends TExcel {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����R�[�h
	 */
	public RemittancePurposeOldExcel(String lang) {
		super(lang);
	}

	/**
	 * �����ړI�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param remittancePurposeList �����ړI�ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Remittance> remittancePurposeList) throws TException {

		try {
			createReport(remittancePurposeList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param remittancePurposeList
	 */
	public void createReport(List<Remittance> remittancePurposeList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C11843")); // �����ړI�}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("C00793"), 5200); // �����ړI�R�[�h
		sheet.addColumn(getWord("C10119"), 8400); // �����ړI���J�i

		// ���ו`��
		for (Remittance remittancePurpose : remittancePurposeList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(remittancePurpose.getCode());
			newRow.addCell(remittancePurpose.getName());
		}
	}
}