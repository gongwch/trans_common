package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.executedlog.*;

/**
 * ���s���O�Q�ƃG�N�Z��
 * 
 * @author AIS
 */
public class ExecutedLogExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public ExecutedLogExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param executed
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<ExecutedLog> executed) throws TException {

		try {
			createReport(executed);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param executed
	 */
	public void createReport(List<ExecutedLog> executed) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C02911"));

		// �J�����ݒ�

		// ���s����
		sheet.addColumn(getWord("C00218") + getWord("C02906"), 7600);
		// ���s���[�U�[�R�[�h
		sheet.addColumn(getWord("C00218") + getWord("C00589"), 6000);
		// ���s���[�U�[����
		sheet.addColumn(getWord("C00218") + getWord("C00691"), 6000);
		// IP�A�h���X
		sheet.addColumn(getWord("C02907"), 6000);
		// �v���O�����R�[�h
		sheet.addColumn(getWord("C00818"), 6000);
		// �v���O��������
		sheet.addColumn(getWord("C00819"), 6000);
		// �X�e�[�^�X
		sheet.addColumn(getWord("C02908"), 4600);
		// �t�H�[�}�b�g

		// ���ו`��
		for (ExecutedLog bean : executed) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(DateUtil.toYMDHMSString(bean.getExcDate()), SwingConstants.CENTER);
			newRow.addCell(bean.getExcCode());
			newRow.addCell(bean.getExcNames());
			newRow.addCell(bean.getIpAddress());

			if (ExecutedLogger.LOGIN.equals(bean.getProCode())) {
				bean.setProName(getWord("C03187")); // ���O�C��

			} else if (ExecutedLogger.LOGOUT.equals(bean.getProCode())) {
				bean.setProName(getWord("C03188")); // ���O�A�E�g
			}

			newRow.addCell(bean.getProCode());
			newRow.addCell(bean.getProName());
			newRow.addCell(bean.getStste());

		}

	}
}
