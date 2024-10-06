package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�[�ꗗ�G�N�Z��
 * 
 * @author AIS
 */
public class UserExcel extends TExcel {

	/** �T�C�i�[�n�����g�� */
	protected static final boolean USE_BL_SIGNER = ServerConfig.isFlagOn("trans.user.mst.use.bl.signer");

	/** �T�C�i�[�n�����g�� */
	protected static final boolean USE_SIGNER_ATTACH = ServerConfig.isFlagOn("trans.user.mst.use.signer.attach");

	// protected static boolean APRV_ROLE = true;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public UserExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[�U�[�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param userList ���[�U�[�ꗗ
	 * @return userList
	 * @throws TException
	 */
	public byte[] getExcel(List<User> userList) throws TException {

		try {
			createReport(userList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param userList
	 */
	public void createReport(List<User> userList) {

		// �V�[�g�ǉ�
		// ���[�U�[�}�X�^
		TExcelSheet sheet = addSheet(getWord("C02355"));

		// �J�����ݒ�]
		// ���[�U�[�R�[�h
		sheet.addColumn(getWord("C00589"), 4200);
		// ���[�U�[����
		sheet.addColumn(getWord("C00691"), 8400);
		// ���[�U�[����
		sheet.addColumn(getWord("C00692"), 8400);
		// ���[�U�[��������
		sheet.addColumn(getWord("C00693"), 8400);
		// �v���O�������[���R�[�h
		sheet.addColumn(getWord("C11159"), 4200);
		// �v���O�������[������
		sheet.addColumn(getWord("C11160"), 8400);
		// ���[�U�[���[���R�[�h
		sheet.addColumn(getWord("C11161"), 4200);
		// ���[�U�[���[������
		sheet.addColumn(getWord("C11162"), 8400);
		// ���F�O���[�v�R�[�h
		sheet.addColumn(getWord("C12230"), 4200);
		// ���F�O���[�v����
		sheet.addColumn(getWord("C12231"), 8400);
		if (USE_BL_SIGNER) {
			// INV. SIGNER DEPT
			sheet.addColumn(getWord("CM0074"), 8400);
			// INV. SIGNER TITLE
			sheet.addColumn(getWord("CM0075"), 8400);
			// INV. SIGNER NAME
			sheet.addColumn(getWord("CM0076"), 8400);
		}
		if (USE_SIGNER_ATTACH) {
			// INV. SIGN FILE NAME
			sheet.addColumn(getWord("SIGN"), 8400);
		}
		// E-MAIL
		sheet.addColumn(getWord("COP065"), 8400);
		// �X�V�������x��
		sheet.addColumn(getWord("C00170"), 4200);
		// ���Z�`�[���͋敪
		sheet.addColumn(getWord("C01056"), 4200);
		// �Ј��R�[�h
		sheet.addColumn(getWord("C00697"), 4200);
		// �Ј�����
		sheet.addColumn(getWord("C00807"), 4200);
		// ��������R�[�h
		sheet.addColumn(getWord("C02043"), 4200);
		// �������喼��
		sheet.addColumn(getWord("C11163"), 4200);
		// ����
		sheet.addColumn(getWord("C00153"), 4200);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (User user : userList) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(user.getCode());
			newRow.addCell(user.getName());
			newRow.addCell(user.getNames());
			newRow.addCell(user.getNamek());
			newRow.addCell(user.getProgramRole().getCode());
			newRow.addCell(user.getProgramRole().getName());
			newRow.addCell(user.getUserRole().getCode());
			newRow.addCell(user.getUserRole().getName());
			newRow.addCell(user.getAprvRoleGroup().getAPRV_ROLE_GRP_CODE());
			newRow.addCell(user.getAprvRoleGroup().getAPRV_ROLE_GRP_NAME());
			if (USE_BL_SIGNER) {
				newRow.addCell(user.getSignerDept());
				newRow.addCell(user.getSignerTitle());
				newRow.addCell(user.getSignerName());
			}
			if (USE_SIGNER_ATTACH) {
				newRow.addCell(user.getSignFileName());
			}
			newRow.addCell(user.getEMailAddress());
			newRow.addCell(getWord(SlipRole.getSlipRoleName(user.getSlipRole())));
			newRow.addCell(getWord(UserAccountRole.getUserAccountRoleName(user.getUserAccountRole())));
			newRow.addCell(user.getEmployee().getCode());
			newRow.addCell(user.getEmployee().getName());
			newRow.addCell(user.getDepartment().getCode());
			newRow.addCell(user.getDepartment().getName());
			newRow.addCell(user.getLanguageName());
			newRow.addCell(user.getDateFrom());
			newRow.addCell(user.getDateTo());
		}

	}
}
