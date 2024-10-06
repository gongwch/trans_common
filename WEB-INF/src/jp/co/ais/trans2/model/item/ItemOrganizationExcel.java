package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;

/**
 * �Ȗڑ̌n���̃}�X�^�ꗗ�G�N�Z��
 * 
 * @author AIS
 */
public class ItemOrganizationExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public ItemOrganizationExcel(String lang) {
		super(lang);
	}

	/**
	 * �Ȗڑ̌n���̃}�X�^�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param itemorgList �Ȗڑ̌n���̃}�X�^
	 * @return userList
	 * @throws TException
	 */
	public byte[] getExcel(List<ItemOrganization> itemorgList) throws TException {

		try {
			createReport(itemorgList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param ItemOrgList
	 */
	public void createReport(List<ItemOrganization> ItemOrgList) {

		// �V�[�g�ǉ�
		// �Ȗڑ̌n���̃}�X�^
		TExcelSheet sheet = addSheet(getWord("C00618") + getWord("C00500"));

		// �J�����ݒ�
		// �Ȗڑ̌n�R�[�h
		sheet.addColumn(getWord("C00617"), 4200);
		// �Ȗڑ̌n����
		sheet.addColumn(getWord("C00618"), 8400);
		// �Ȗڑ̌n����
		sheet.addColumn(getWord("C00619"), 8400);
		// �Ȗڑ̌n��������
		sheet.addColumn(getWord("C00620"), 8400);
		// ���ו`��
		for (ItemOrganization bean : ItemOrgList) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNames());
			newRow.addCell(bean.getNamek());
		}

	}
}
