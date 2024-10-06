package jp.co.ais.trans2.master.excel;

import java.util.List;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.TExcel;
import jp.co.ais.trans2.common.excel.TExcelRow;
import jp.co.ais.trans2.common.excel.TExcelSheet;
import jp.co.ais.trans2.model.company.*;

/**
 * ��Њԕt�փ}�X�^�G�N�Z��
 * 
 * @author AIS
 */
public class InterCompanyTransferExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public InterCompanyTransferExcel(String lang) {
		super(lang);
	}

	/**
	 * �ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param list
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<InterCompanyTransfer> list) throws TException {

		try {
			createReport(company, list);
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
	public void createReport(Company company, List<InterCompanyTransfer> list) {

		// �V�[�g�ǉ�
		// ��Њԕt�փ}�X�^
		TExcelSheet sheet = addSheet(getWord("C02341"));

		AccountConfig ac = company.getAccountConfig();

		// �J�����ݒ�
		// �t�։�ЃR�[�h
		sheet.addColumn(getWord("C02050"), 6000);
		sheet.addColumn(getWord("C11150"), 8400);
		// �t�֌v�㕔��R�[�h
		sheet.addColumn(getWord("C02051"), 6000);
		// �t�֌v�㕔�嗪��
		sheet.addColumn(getWord("C11151"), 8400);
		// �t�� ����
		sheet.addColumn(getWord("C00375") + ac.getItemName() + getWord("C00174"), 6000);
		// �t�� ����
		sheet.addColumn(getWord("C00375") + ac.getItemName() + getWord("C00548"), 8400);
		// �t�� �R�[�h
		sheet.addColumn(getWord("C00375") + ac.getSubItemName() + getWord("C00174"), 6000);
		// �t�� ����
		sheet.addColumn(getWord("C00375") + ac.getSubItemName() + getWord("C00548"), 8400);
		if (ac.isUseDetailItem()) {
			// �t�� �R�[�h
			sheet.addColumn(getWord("C00375") + ac.getDetailItemName() + getWord("C00174"), 6000);
			// �t�� ����
			sheet.addColumn(getWord("C00375") + ac.getDetailItemName() + getWord("C00548"), 8400);
		}
		// �����R�[�h
		sheet.addColumn(getWord("C00786"), 6000);
		// ����旪��
		sheet.addColumn(getWord("C00787"), 8400);

		// ���ו`��
		for (InterCompanyTransfer bean : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getTransferCompany().getCode());
			newRow.addCell(bean.getTransferCompany().getNames());
			newRow.addCell(bean.getDepartment().getCode());
			newRow.addCell(bean.getDepartment().getNames());
			newRow.addCell(bean.getItem().getCode());
			newRow.addCell(bean.getItem().getNames());
			newRow.addCell(bean.getItem().getSubItemCode());
			newRow.addCell(bean.getItem().getSubItemNames());
			if (ac.isUseDetailItem()) {
				newRow.addCell(bean.getItem().getDetailItemCode());
				newRow.addCell(bean.getItem().getDetailItemNames());
			}
			newRow.addCell(bean.getCustomer().getCode());
			newRow.addCell(bean.getCustomer().getNames());
		}

	}

}
