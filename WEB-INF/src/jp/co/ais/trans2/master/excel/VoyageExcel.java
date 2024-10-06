package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.voyage.*;

/**
 * ���q�}�X�^�G�N�Z��
 * 
 * @author AIS
 */
public class VoyageExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public VoyageExcel(String lang) {
		super(lang);
	}

	/**
	 * �ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param list
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(List<Voyage> list) throws TException {

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
	public void createReport(List<Voyage> list) {

		// �V�[�g�ǉ�
		// ��ЃR���g���[���}�X�^
		TExcelSheet sheet = addSheet(getWord("C11779")); // ���q�}�X�^

		// �J�����ݒ�
		// ���q�R�[�h
		sheet.addColumn(getWord("C03003"), 4200);
		// ���q����
		sheet.addColumn(getWord("C11780"), 12600);
		// ���q����
		sheet.addColumn(getWord("C11781"), 8400);
		// ���q��������
		sheet.addColumn(getWord("C11782"), 12600);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Voyage bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(bean.getDateFrom());
			newRow.addCell(bean.getDateTo());

		}

	}

}
