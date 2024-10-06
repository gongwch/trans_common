package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.exclusive.*;

/**
 * �r�����׈ꗗ�G�N�Z��
 */
public class ExclusiveDetailExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public ExclusiveDetailExcel(String lang) {
		super(lang);
	}

	/**
	 * �r�����׈ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param list �r�����׃��X�g
	 * @return �r�����׈ꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(List<ExclusiveDetail> list) throws TException {

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
	public void createReport(List<ExclusiveDetail> list) {

		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(getWord("C11951"));

		// �J�����ݒ�
		// ��ЃR�[�h
		sheet.addColumn(getWord("C00596"), 4200);
		// �����敪
		sheet.addColumn(getWord("C02829"), 4200);
		// �r���L�[
		sheet.addColumn(getWord("C11950"), 8400);
		// �s�ԍ�
		sheet.addColumn(getWord("C01588"), 4200);
		// ��������
		sheet.addColumn(getWord("C11757"), 8400);
		// �v���O����ID
		sheet.addColumn(getWord("C10994"), 4200);
		// �v���O��������
		sheet.addColumn(getWord("C00819"), 8400);
		// ���[�U�[ID
		sheet.addColumn(getWord("C10995"), 4200);
		// ���[�U�[����
		sheet.addColumn(getWord("C00691"), 8400);

		// ���ו`��
		for (ExclusiveDetail bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKAI_CODE());
			newRow.addCell(bean.getSHORI_KBN(), SwingConstants.CENTER);
			newRow.addCell(bean.getHAITA_KEY());
			newRow.addCell(bean.getGYO_NO());
			newRow.addCell(DateUtil.toYMDHMString(bean.getINP_DATE()), SwingConstants.CENTER);
			newRow.addCell(bean.getPRG_ID());
			newRow.addCell(bean.getPRG_NAME());
			newRow.addCell(bean.getUSR_ID());
			newRow.addCell(bean.getUSR_NAME());
		}
	}
}
