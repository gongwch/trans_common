package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * ���F�������[���ꗗ�G�N�Z��
 */
public class AprvRoleExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public AprvRoleExcel(String lang) {
		super(lang);
	}

	/**
	 * ���F�������[���ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param aprvRoleList ���F�������[���ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<AprvRole> aprvRoleList) throws TException {

		try {
			createReport(aprvRoleList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param aprvRoleList
	 */
	public void createReport(List<AprvRole> aprvRoleList) {

		// �V�[�g�ǉ�
		// ���F�������[���}�X�^
		TExcelSheet sheet = addSheet(getWord("C11940")); // ���F�������[���}�X�^

		// �J�����ݒ�
		// ���[���R�[�h
		sheet.addColumn(getWord("C11154"), 4200);
		// ���[������
		sheet.addColumn(getWord("C11155"), 7000);
		// ���[������
		sheet.addColumn(getWord("C11156"), 6000);
		// ���[����������
		sheet.addColumn(getWord("C11157"), 7000);
		// �J�n�N����
		sheet.addColumn(getWord("COP706"), 5500);
		// �I���N����
		sheet.addColumn(getWord("COP707"), 5500);

		// ���ו`��
		for (AprvRole role : aprvRoleList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(role.getAPRV_ROLE_CODE());
			newRow.addCell(role.getAPRV_ROLE_NAME());
			newRow.addCell(role.getAPRV_ROLE_NAME_S());
			newRow.addCell(role.getAPRV_ROLE_NAME_K());
			newRow.addCell(DateUtil.toYMDString(role.getSTR_DATE()).toString(), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(role.getEND_DATE()).toString(), SwingConstants.CENTER);
		}

	}
}
