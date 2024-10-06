package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * ItemSummaryMaster - �ȖڏW�v�}�X�^ - Export Excel
 * 
 * @author AIS
 */
public class ItemSummaryExcel extends TExcel {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����敪
	 */
	public ItemSummaryExcel(String lang) {
		super(lang);
	}

	/**
	 * �ȖڏW�v�ꗗ���G�N�Z���œn��
	 * 
	 * @param list �ꗗ�f�[�^�̔z��
	 * @exception TException
	 * @return byte Excel
	 */
	public byte[] getExcel(List<ItemSummary> list) throws TException {
		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����C�A�E�g��` �e�J�����̃f�[�^���Z�b�g
	 * 
	 * @param list �ꗗ�f�[�^�̔z��
	 */
	public void createReport(List<ItemSummary> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C02019"));

		// �J�����ݒ�
		sheet.addColumn(getWord("C00617"), 4200);
		sheet.addColumn(getWord("C00619"), 8200);
		sheet.addColumn(getWord("C00572"), 6300);
		sheet.addColumn(getWord("C00730"), 8200);
		sheet.addColumn(getWord("C00624"), 8200);
		sheet.addColumn(getWord("C01609"), 6300);
		sheet.addColumn(getWord("C00625"), 6300);
		sheet.addColumn(getWord("C00626"), 4200);
		sheet.addColumn(getWord("C01300"), 4200);
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		/** ���ו`�� */
		for (ItemSummary bean : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getKmtCode());
			newRow.addCell(bean.getKmtName());
			newRow.addCell(bean.getKmkCode());
			newRow.addCell(bean.getKmkName());
			newRow.addCell(bean.getKokName());
			newRow.addCell(bean.getSumCode());
			newRow.addCell(bean.getSumName());
			newRow.addCell(bean.getOdr(),SwingConstants.RIGHT);
			newRow.addCell(bean.getHyjKbn() == 1 ? getWord("C00432") : getWord("C01297"),SwingConstants.CENTER);
			newRow.addCell(bean.getDateFrom());
			newRow.addCell(bean.getDateTo());
		}
	}
}