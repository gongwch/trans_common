package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.security.*;

/**
 * �v���O�������[���G�N�Z��
 * 
 * @author AIS
 */
public class ProgramRoleExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public ProgramRoleExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param list
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<ProgramRole> list) throws TException {

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
	public void createReport(List<ProgramRole> list) {

		// �V�[�g�ǉ�
		// �v���O�������[���}�X�^
		TExcelSheet sheet = addSheet(getWord("C11153"));

		// �J�����ݒ�
		// ���[���R�[�h
		sheet.addColumn(getWord("C11154"), 5000);
		// ���[������
		sheet.addColumn(getWord("C11155"), 12000);
		// ���[������
		sheet.addColumn(getWord("C11156"), 8000);
		// ���[����������
		sheet.addColumn(getWord("C11157"), 8000);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 5000);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 5000);
		// �t�H�[�}�b�g

		// ���ו`��
		for (ProgramRole bean : list) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(DateUtil.toYMDString(bean.getTermFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getTermTo()), SwingConstants.CENTER);

		}

	}
}
