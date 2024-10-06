package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �V�X�e���敪�ꗗ�G�N�Z��
 * 
 * @author AIS
 */
public class SystemDivisionExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public SystemDivisionExcel(String lang) {
		super(lang);
	}

	/**
	 * �V�X�e���敪�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param systemList �V�X�e���敪
	 * @return userList
	 * @throws TException
	 */
	public byte[] getExcel(List<SystemDivision> systemList) throws TException {

		try {
			createReport(systemList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param systemList
	 */
	public void createReport(List<SystemDivision> systemList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C02354"));

		// �J�����ݒ�
		// �V�X�e���敪
		sheet.addColumn(getWord("C00980"), 4200);
		// �V�X�e���敪����
		sheet.addColumn(getWord("C00832"), 8400);
		// �V�X�e���敪����
		sheet.addColumn(getWord("C00833"), 8400);
		// ���[�U�[��������
		sheet.addColumn(getWord("C00834"), 8400);
		// �O���V�X�e���敪
		sheet.addColumn(getWord("C01018"), 8400);
		// ���ו`��
		for (SystemDivision bean : systemList) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode(), SwingConstants.CENTER);
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
			newRow.addCell(getWord(OuterSystemType.getName(bean.getOuterSystemType())), SwingConstants.CENTER);
		}

	}
}
