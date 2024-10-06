package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����уT�u�A�C�e���}�X�^�G�N�Z��
 */
public class MLITSubItemExcel extends TExcel {

	/** ��Џ�� */
	protected Company company = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param company
	 */
	public MLITSubItemExcel(String lang, Company company) {
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
	public byte[] getExcel(List<YJItem> list) throws TException {

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
	public void createReport(List<YJItem> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("CBL305")); // �A�����уT�u�A�C�e���}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("CBL303"), 4200); // �A�C�e���R�[�h
		sheet.addColumn(getWord("CBL304"), 10400); // �A�C�e������
		sheet.addColumn(getWord("CBL306"), 4200); // �T�u�A�C�e���R�[�h
		sheet.addColumn(getWord("CBL307"), 10400); // �T�u�A�C�e������
		sheet.addColumn(getWord("CM0015"), 20800); // Remark

		// ���ו`��
		for (YJItem bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getITEM_CODE());
			newRow.addCell(bean.getITEM_NAME());
			newRow.addCell(bean.getITEM_SUB_CODE());
			newRow.addCell(bean.getITEM_SUB_NAME());
			newRow.addCell(bean.getSUB_REMARK());
		}
	}
}