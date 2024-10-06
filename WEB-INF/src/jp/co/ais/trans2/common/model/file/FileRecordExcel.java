package jp.co.ais.trans2.common.model.file;

import java.util.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.file.*;

/**
 * �捞�����G�N�Z��
 */
public class FileRecordExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public FileRecordExcel(String lang) {
		super(lang);
	}

	/**
	 * �捞�������X�g
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<TFile> list) throws TException {

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
	public void createReport(List<TFile> list) {

		// �V�[�g�ǉ�
		// �捞����
		TExcelSheet sheet = addSheet(getWord("C11098") + ".xls");

		// �J�����ݒ�
		// �捞��
		sheet.addColumn(getWord("C10101"), 6300);
		// ���[�U�[ID
		sheet.addColumn(getWord("C10995"), 4200);
		// �t�@�C������
		sheet.addColumn(getWord("C11099"), 8000);

		// ���ו`��
		for (TFile file : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(DateUtil.toYMDHMSString(file.getInputDate()));
			newRow.addCell(file.getUserCode());
			newRow.addCell(file.getFileName());

		}

	}

}
