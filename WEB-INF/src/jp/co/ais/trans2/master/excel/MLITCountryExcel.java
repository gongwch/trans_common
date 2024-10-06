package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.mlit.region.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �A�����ђn��}�X�^�G�N�Z��
 */
public class MLITCountryExcel extends TExcel {

	/** ��Џ�� */
	protected Company company = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param company
	 */
	public MLITCountryExcel(String lang, Company company) {
		super(lang);
		this.company = company;
	}

	/**
	 * �ꗗ���G�N�Z���ɕԂ�
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<YJRegion> list) throws TException {

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
	public void createReport(List<YJRegion> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("CBL537")); // �A�����ђn��}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("CBL538"), 4200); // ���R�[�h
		sheet.addColumn(getWord("CBL539"), 10400); // ������
		sheet.addColumn(getWord("CBL540"), 4200); // �n��R�[�h
		sheet.addColumn(getWord("CBL541"), 10400); // �n�於��
		sheet.addColumn(getWord("CM0015"), 20800); // Remark

		// ���ו`��
		for (YJRegion bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getREGION_CODE());
			newRow.addCell(bean.getREGION_NAME());
			newRow.addCell(bean.getCOUNTRY_CODE());
			newRow.addCell(bean.getCOUNTRY_NAME());
			newRow.addCell(bean.getCOUNTRY_REMARK());
		}
	}
}