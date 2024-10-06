package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.releasedfile.*;

/**
 * �����[�X�t�@�C���ꗗ�G�N�Z��
 * 
 * @author AIS
 */
public class ReleasedFileExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public ReleasedFileExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param executed
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<ReleasedFile> executed) throws TException {

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
	public void createReport(List<ReleasedFile> executed) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C02914"));

		// �J�����ݒ�

		// �t�H���_
		sheet.addColumn(getWord("C02912"), 15000);
		// �t�@�C��
		sheet.addColumn(getWord("C01988"), 8000);
		// �X�V����
		sheet.addColumn(getWord("C00169") + getWord("C02906"), 6600);
		// �t�@�C���T�C�Y
		sheet.addColumn(getWord("C02915"), 5000);

		// ���ו`��
		for (ReleasedFile bean : executed) {

			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getFolder());
			newRow.addCell(bean.getFile());
			newRow.addCell(DateUtil.toYMDHMSString(bean.getUpdate()), SwingConstants.CENTER);
			newRow.addCell(Long.toString(bean.getSize()), SwingConstants.RIGHT);

		}

	}
}
