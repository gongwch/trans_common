package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * �E�v�G�N�Z��
 * 
 * @author AIS
 */
public class RemarkExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public RemarkExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param list
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<Remark> list) throws TException {

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
	public void createReport(List<Remark> list) {

		TExcelSheet sheet = addSheet(getWord("C02349"));

		// �E�v�敪
		sheet.addColumn(getWord("C00568"), 4200);
		// �f�[�^�敪
		sheet.addColumn(getWord("C00567"), 4200);
		// �E�v�R�[�h
		sheet.addColumn(getWord("C00564"), 4200);
		// �E�v����
		sheet.addColumn(getWord("C00565"), 12600);
		// �E�v��������
		sheet.addColumn(getWord("C00566"), 12600);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);
		// �t�H�[�}�b�g

		// ���ו`��
		for (Remark bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.isSlipRemark() ? getWord("C00569") : getWord("C00119"), SwingConstants.CENTER);
			newRow.addCell(getWord(DataDivision.getDataDivisionName(DataDivision.getDataDivision(bean.getDataType()))),SwingConstants.CENTER);
			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNamek());
			newRow.addCell(DateUtil.toYMDString(bean.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getDateTo()), SwingConstants.CENTER);

		}

	}
}
