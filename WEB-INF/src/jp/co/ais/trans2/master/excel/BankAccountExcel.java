package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * ��s�����ꗗ�G�N�Z��
 */
public class BankAccountExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public BankAccountExcel(String lang) {
		super(lang);
	}

	/**
	 * ��s�����ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param bankAccountList ��s�����ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<BankAccount> bankAccountList) throws TException {

		try {
			createReport(bankAccountList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param bankAccountList
	 */
	public void createReport(List<BankAccount> bankAccountList) {

		// �V�[�g�ǉ�
		// ��s�����}�X�^
		TExcelSheet sheet = addSheet(getWord("C02322"));

		// �J�����ݒ�
		// ��s�����R�[�h
		sheet.addColumn(getWord("C01879"), 4200);
		// ��s��������
		sheet.addColumn(getWord("C02145"), 8400);
		// �ʉ݃R�[�h
		sheet.addColumn(getWord("C00665"), 3000);
		// ��s�R�[�h
		sheet.addColumn(getWord("C00779"), 4200);
		// ��s����
		sheet.addColumn(getWord("C00781"), 8400);
		// ��s���́i�p���j
		sheet.addColumn(getWord("C11891"), 8400);
		// �x�X�R�[�h
		sheet.addColumn(getWord("C02055"), 4200);
		// �x�X����
		sheet.addColumn(getWord("C02060"), 8400);
		// �x�X���́i�p���j
		sheet.addColumn(getWord("C11892"), 8400);
		// �U���˗��l�R�[�h
		sheet.addColumn(getWord("C00858"), 4800);
		// �U���˗��l���i�Łj"
		sheet.addColumn(getWord("C00859"), 8400);
		// �U���˗��l���i�����j
		sheet.addColumn(getWord("C00860"), 8400);
		// �U���˗��l���i�p���j
		sheet.addColumn(getWord("C00861"), 8400);
		// �a�����
		sheet.addColumn(getWord("C01326"), 2500);
		// �����ԍ�
		sheet.addColumn(getWord("C00794"), 4200);
		// �Ј�FB�敪
		sheet.addColumn(getWord("C01117"), 4000);
		// �ЊOFB�敪
		sheet.addColumn(getWord("C01122"), 4000);
		// �v�㕔��R�[�h
		sheet.addColumn(getWord("C00571"), 4200);
		// �v�㕔�嗪��
		sheet.addColumn(getWord("C00863") + getWord("C00548"), 8400);
		// �ȖڃR�[�h
		sheet.addColumn(getWord("C00572"), 4200);
		// �Ȗڗ���
		sheet.addColumn(getWord("C00730"), 8400);
		// �⏕�ȖڃR�[�h
		sheet.addColumn(getWord("C00602"), 4200);
		// �⏕�Ȗڗ���
		sheet.addColumn(getWord("C00739"), 8400);
		// ����ȖڃR�[�h
		sheet.addColumn(getWord("C00603"), 4200);
		// ����Ȗڗ���
		sheet.addColumn(getWord("C01594"), 8400);
		// SWIFT�R�[�h
		sheet.addColumn(getWord("C10414"), 4200);
		// BANK COUNTRY
		sheet.addColumn(getWord("C11425"), 4200);
		// �Z��1
		sheet.addColumn(getWord("C00687"), 8400);
		// �Z��1(�p��)
		sheet.addColumn(getWord("C11893"), 8400);
		// �Z��2
		sheet.addColumn(getWord("C00688"), 8400);
		// �Z��2(�p��)
		sheet.addColumn(getWord("C11894"), 8400);
		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (BankAccount bankAccoount : bankAccountList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bankAccoount.getCode());
			newRow.addCell(bankAccoount.getName());
			newRow.addCell(bankAccoount.getCurrencyCode(), SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getBankCode());
			newRow.addCell(bankAccoount.getBankName());
			newRow.addCell(bankAccoount.getBankNameE());
			newRow.addCell(bankAccoount.getBranchCode());
			newRow.addCell(bankAccoount.getBranchName());
			newRow.addCell(bankAccoount.getBranchNameE());
			newRow.addCell(bankAccoount.getClientCode());
			newRow.addCell(bankAccoount.getClientName());
			newRow.addCell(bankAccoount.getClientNameJ());
			newRow.addCell(bankAccoount.getClientNameE());
			newRow.addCell(getWord(DepositKind.getDepositKindName(bankAccoount.getDepositKind())),
				SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getAccountNo());
			newRow.addCell(bankAccoount.isUseEmployeePayment() ? getWord("C02149") : getWord("C02148"),
				SwingConstants.CENTER);
			newRow
				.addCell(bankAccoount.isUseExPayment() ? getWord("C02151") : getWord("C02150"), SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getDepartmentCode());
			newRow.addCell(bankAccoount.getDepartmentNames());
			newRow.addCell(bankAccoount.getItemCode());
			newRow.addCell(bankAccoount.getItemNames());
			newRow.addCell(bankAccoount.getSubItemCode());
			newRow.addCell(bankAccoount.getSubItemNames());
			newRow.addCell(bankAccoount.getDetailItemCode());
			newRow.addCell(bankAccoount.getDetailItemNames());
			newRow.addCell(bankAccoount.getSwift());
			newRow.addCell(bankAccoount.getBankCountry()); // Bank Country
			newRow.addCell(bankAccoount.getBnkAdr1());
			newRow.addCell(bankAccoount.getBnkAdr1E());
			newRow.addCell(bankAccoount.getBnkAdr2());
			newRow.addCell(bankAccoount.getBnkAdr2E());
			newRow.addCell(DateUtil.toYMDString(bankAccoount.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bankAccoount.getDateTo()), SwingConstants.CENTER);
		}

	}
}
