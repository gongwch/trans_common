package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �v���O�����G�N�Z��
 * 
 * @author AIS
 */
public class ProgramExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public ProgramExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param list
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<Program> list) throws TException {

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
	public void createReport(List<Program> list) {

		// �V�[�g�ǉ�
		// �v���O�����}�X�^
		TExcelSheet sheet = addSheet(getWord("C02147"));

		// �J�����ݒ�

		// �V�X�e���R�[�h
		sheet.addColumn(getWord("C02351"), 4200);
		// �v���O�����R�[�h
		sheet.addColumn(getWord("C00818"), 5000);
		// �v���O��������
		sheet.addColumn(getWord("C00819"), 12000);
		// �v���O��������
		sheet.addColumn(getWord("C00820"), 8000);
		// �v���O������������
		sheet.addColumn(getWord("C00821"), 8000);
		// �R�����g
		sheet.addColumn(getWord("C00183"), 12000);
		// ���[�h���W���[����
		sheet.addColumn(getWord("C11152"), 18000);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 5000);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 5000);
		// �t�H�[�}�b�g

		// ���ו`��
		for (Program bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getSystemClassification().getCode());
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(bean.getComment());
			newRow.addCell(bean.getLoadClassName());
			newRow.addCell(DateUtil.toYMDDate(bean.getTermFrom()));
			newRow.addCell(DateUtil.toYMDDate(bean.getTermTo()));

		}

	}
}
