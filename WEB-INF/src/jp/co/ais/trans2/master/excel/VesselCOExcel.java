package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * ���q�O�q�敪�G�N�Z��
 * 
 * @author AIS
 */
public class VesselCOExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public VesselCOExcel(String lang) {
		super(lang);
	}

	/**
	 * ���[��Ԃ�
	 * 
	 * @param list
	 * @return ���[
	 * @throws TException
	 */
	public byte[] getExcel(List<VesselCO> list) throws TException {

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
	public void createReport(List<VesselCO> list) {

		// �V�[�g�ǉ�
		// ���q�O�q�敪�}�X�^
		TExcelSheet sheet = addSheet(getWord("C11985"));

		// �J�����ݒ�
		// �D�R�[�h
		sheet.addColumn(getWord("C11758"), 5000);
		// �D����
		sheet.addColumn(getWord("C11759"), 8000);
		// ���q/�O�q�敪
		sheet.addColumn(getWord("C11986"), 5000);

		// ���ו`��
		for (VesselCO bean : list) {
			TExcelRow newRow = sheet.addRow();
			if (bean.getCOKbn().equals("1")) {
				newRow.addCell(bean.getVesselCode());
				newRow.addCell(bean.getVesselNames());
				newRow.addCell(getWord("COP1418"), SwingConstants.CENTER);

			} else if (bean.getCOKbn().equals("2")) {
				newRow.addCell(bean.getVesselCode());
				newRow.addCell(bean.getVesselNames());
				newRow.addCell(getWord("COP1419"), SwingConstants.CENTER);

			}

		}

	}
}
