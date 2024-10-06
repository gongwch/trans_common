package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * �A�����э��}�X�^�G�N�Z��
 */
public class MLITRegionExcel extends TExcel {

	/** ��Џ�� */
	protected Company company = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param company
	 */
	public MLITRegionExcel(String lang, Company company) {
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
		TExcelSheet sheet = addSheet(getWord("CBL542")); // �A�����э��}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("CBL538"), 4200); // ���R�[�h
		sheet.addColumn(getWord("CBL539"), 10400); // ������
		sheet.addColumn(getWord("CM0015"), 20800); // Remark

		// ���ו`��
		for (YJRegion bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getREGION_CODE());
			newRow.addCell(bean.getREGION_NAME());
			newRow.addCell(bean.getREGION_REMARK());
		}
	}
}