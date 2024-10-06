package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.bill.*;

/**
 * �����敪�}�X�^�G�N�Z��
 */

public class BillTypeExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public BillTypeExcel(String lang) {
		super(lang);
	}

	/**
	 * ��s�ꗗ���G�N�Z���ɕԂ�
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<BillType> list) throws TException {

		try {
			createReport(list);
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
	public void createReport(List<BillType> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C10235")); // �����敪�}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("C10092"), 4200); // �����敪
		sheet.addColumn(getWord("C10096"), 8400); // ��������
		sheet.addColumn(getWord("C10095"), 10400); // ��������
		sheet.addColumn(getWord("C10097"), 8400); // �������t�H�[�}�b�g
		sheet.addColumn(getWord("C11556"), 4200); // ���׌���
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (BillType billType : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(billType.getCode());
			newRow.addCell(billType.getName());
			newRow.addCell(billType.getNamek());
			newRow.addCell(billType.getFormat());
			newRow.addCell(billType.getDetailCount());
			newRow.addCell(billType.getDateFrom());
			newRow.addCell(billType.getDateTo());
		}

	}
}