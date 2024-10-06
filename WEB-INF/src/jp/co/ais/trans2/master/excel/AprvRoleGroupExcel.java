package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * ���F�������[���O���[�v�ꗗ�G�N�Z��
 */
public class AprvRoleGroupExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public AprvRoleGroupExcel(String lang) {
		super(lang);
	}

	/**
	 * ���F�������[���O���[�v�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param aprvRoleList ���F�������[���O���[�v�ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<AprvRoleGroup> aprvRoleList) throws TException {

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
	public void createReport(List<AprvRoleGroup> aprvRoleList) {

		// �V�[�g�ǉ�
		// ���F�������[���}�X�^
		TExcelSheet sheet = addSheet(getWord("C11940")); // ���F�������[���}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("C12230"), 4200); // ���F�O���[�v�R�[�h
		sheet.addColumn(getWord("C12231"), 7000); // ���F�O���[�v����
		sheet.addColumn(getWord("C12232"), 6000); // ���F�O���[�v����
		sheet.addColumn(getWord("C12233"), 7000); // ���F�O���[�v��������
		sheet.addColumn(getWord("C11154"), 4200); // ���[���R�[�h
		sheet.addColumn(getWord("C11155"), 7000); // ���[������
		sheet.addColumn(getWord("C01739"), 4200); // ���x��
		sheet.addColumn(getWord("COP706"), 5500); // �J�n�N����
		sheet.addColumn(getWord("COP707"), 5500); // �I���N����

		// ���ו`��
		for (AprvRoleGroup grp : aprvRoleList) {
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {

				TExcelRow newRow = sheet.addRow();
				newRow.addCell(grp.getAPRV_ROLE_GRP_CODE());
				newRow.addCell(grp.getAPRV_ROLE_GRP_NAME());
				newRow.addCell(grp.getAPRV_ROLE_GRP_NAME_S());
				newRow.addCell(grp.getAPRV_ROLE_GRP_NAME_K());
				newRow.addCell(dtl.getAPRV_ROLE_CODE());
				newRow.addCell(dtl.getAPRV_ROLE_NAME());
				newRow.addCell(dtl.getAPRV_LEVEL());
				newRow.addCell(DateUtil.toYMDString(grp.getSTR_DATE()).toString(), SwingConstants.CENTER);
				newRow.addCell(DateUtil.toYMDString(grp.getEND_DATE()).toString(), SwingConstants.CENTER);
			}

		}

	}
}
