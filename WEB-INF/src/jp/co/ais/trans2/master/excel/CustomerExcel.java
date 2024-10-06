package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * �����ꗗ�G�N�Z��
 */
public class CustomerExcel extends TExcel {

	/** true:�O���[�v��Ћ敪�L�� */
	public static boolean isUseTRI_TYPE_GRP_FLG = ServerConfig.isFlagOn("trans.kt.MG0150.group.comp.flag");

	/** true:�O���[�v��Ћ敪�L�� */
	protected boolean isUseTRI_TYPE_PSN_FLG = ServerConfig.isFlagOn("trans.kt.MG0150.tri.person.flag");

	/** �����敪��\�����Ȃ����ǂ��� true:�\�����Ȃ� */
	public static boolean isNoVisibleTriDivison = ServerConfig.isFlagOn("trans.MG0150.no.visible.tri.division");

	/** �����̌h��/�S������/�S���҂Ȃǂ̐ݒ��\�����邩�ǂ��� true:�\������ */
	protected static boolean isUseCustomerManagementSet = ServerConfig
		.isFlagOn("trans.usr.customer.managementi.setting");

	/** invoice�̐ݒ��\�����邩�ǂ��� true:�\������ */
	protected boolean isInvoice = false;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param condition
	 */
	public CustomerExcel(String lang, CustomerSearchCondition condition) {
		super(lang);
		isInvoice = condition.isInvoiceFlg();
	}

	/**
	 * �����ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param customerList �����ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Customer> customerList) throws TException {

		try {
			createReport(customerList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param customerList
	 */
	public void createReport(List<Customer> customerList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C02326"));// �����}�X�^

		// �J�����ݒ�
		sheet.addColumn(getWord("C00786"), 4200);// �����R�[�h
		sheet.addColumn(getWord("C00830"), 8400);// ����於��
		sheet.addColumn(getWord("C00787"), 8400);// ����旪��
		sheet.addColumn(getWord("C00836"), 8400);// ����挟������
		if (!isNoVisibleTriDivison) {
			sheet.addColumn(getWord("C03344"), 15000);// �����敪
		}
		if (isUseTRI_TYPE_GRP_FLG) {
			sheet.addColumn(getWord("C04294"), 4200);// �O���[�v��Ћ敪
		}
		if (isUseTRI_TYPE_PSN_FLG) {
			sheet.addColumn(getWord("C12300"), 4200);// ��s������\�����Ȃ�
		}
		sheet.addColumn(getWord("C02487"), 8400);// ���Ə���
		sheet.addColumn(getWord("C00527"), 4200);// �X�֔ԍ�
		sheet.addColumn(getWord("C11889"), 4200);// ���R�[�h
		sheet.addColumn(getWord("C11890"), 4200);// ����
		sheet.addColumn(getWord("C01152"), 8400);// �Z���J�i
		sheet.addColumn(getWord("C01150"), 8400);// �Z��1
		sheet.addColumn(getWord("C01151"), 8400);// �Z��2
		sheet.addColumn(getWord("CBL401"), 8400);// EMail Address
		if (isUseCustomerManagementSet) {
			sheet.addColumn(getWord("C12184"), 4200);// �����h��
			sheet.addColumn(getWord("C12187"), 4200);// �S���Ҍh��
			sheet.addColumn(getWord("C12185"), 8400);// �S��������
			sheet.addColumn(getWord("C12186"), 8400);// �S���Җ�
		}

		sheet.addColumn(getWord("C00393"), 4200);// �d�b�ԍ�
		sheet.addColumn(getWord("C00690"), 4200);// FAX�ԍ�
		sheet.addColumn(getWord("C00871"), 4200);// �W�v�����R�[�h
		sheet.addColumn(getWord("C11085"), 8400);// �W�v����於��
		sheet.addColumn(getWord("C01089"), 4200);// �d����敪
		sheet.addColumn(getWord("C01261"), 4200);// ���Ӑ�敪
		sheet.addColumn(getWord("C02038"), 4200);// �����������ߓ�
		sheet.addColumn(getWord("C02039"), 4200);// �����������ߌ㌎
		sheet.addColumn(getWord("C00870"), 4200);// ��������������
		sheet.addColumn(getWord("C02040"), 8400);// ������s�����R�[�h
		sheet.addColumn(getWord("C11087"), 8400);// ������s��������
		sheet.addColumn(getWord("C10133"), 4200);// �U���˗��l��
		sheet.addColumn(getWord("C02042"), 4200);// �����萔���敪

		if (isInvoice) {
			sheet.addColumn(getWord("C12197"), 6000);// ��K�i���������s���Ǝ�
			sheet.addColumn(getWord("C00286"), 6000);// �����
			sheet.addColumn(getWord("C12171"), 7500);// �K�i���������s���Ǝғo�^�ԍ�
		}

		sheet.addColumn(getWord("C00055"), 4200);// �J�n�N����
		sheet.addColumn(getWord("C00261"), 4200);// �I���N����

		// ���ו`��
		for (Customer customer : customerList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(customer.getCode());
			newRow.addCell(customer.getName());
			newRow.addCell(customer.getNames());
			newRow.addCell(customer.getNamek());
			if (!isNoVisibleTriDivison) {
				newRow.addCell(getTriType(customer));
			}
			if (isUseTRI_TYPE_GRP_FLG) {
				newRow.addCell(customer.isGroupCompany() ? getWord("C10583") : "");// �O���[�v��Ћ敪
			}
			if (isUseTRI_TYPE_PSN_FLG) {
				newRow.addCell(customer.isPersonalCustomer() ? getWord("C10794") : getWord("C10795")); // �͂�/������
			}
			newRow.addCell(customer.getOfficeName());
			newRow.addCell(customer.getZipCode());
			newRow.addCell(customer.getCountryCode());// ���R�[�h
			newRow.addCell(customer.getCountry() != null ? customer.getCountry().getName() : "");// ����
			newRow.addCell(customer.getAddressKana());
			newRow.addCell(customer.getAddress());
			newRow.addCell(customer.getAddress2());
			newRow.addCell(customer.getEmailAddress());
			if (isUseCustomerManagementSet) {
				newRow.addCell(getWord(HonorType.getName(customer.getTRI_TITLE_TYPE())), SwingConstants.CENTER); // �����h��
				newRow.addCell(getWord(HonorType.getName(customer.getEMP_TITLE_TYPE())), SwingConstants.CENTER); // �S���Ҍh��
				newRow.addCell(customer.getCHARGE_DEP_NAME()); // �S��������
				newRow.addCell(customer.getCHARGE_EMP_NAME()); // �S���Җ�
			}

			newRow.addCell(customer.getTel());
			newRow.addCell(customer.getFax());
			newRow.addCell(customer.getSumCode());
			newRow.addCell(customer.getSumName());
			if (CustomerType.NONE == customer.getCustomerType()) {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.NONE)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.NONE)), SwingConstants.CENTER);
			} else if (CustomerType.VENDOR == customer.getCustomerType()) {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.VENDOR)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.NONE)), SwingConstants.CENTER);
			} else if (CustomerType.CUSTOMER == customer.getCustomerType()) {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.NONE)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.CUSTOMER)), SwingConstants.CENTER);
			} else {
				newRow.addCell(getWord(CustomerType.getVenderName(CustomerType.BOTH)), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getCustomerName(CustomerType.BOTH)), SwingConstants.CENTER);
			}
			newRow.addCell(customer.getCloseDay());
			newRow.addCell(customer.getNextMonth());
			newRow.addCell(customer.getCashDay());
			newRow.addCell(customer.getBankAccount().getCode());
			newRow.addCell(customer.getBankAccount().getName());
			newRow.addCell(customer.getClientName());
			newRow.addCell(getWord(CashInFeeType.getCashInFeeTypeName(customer.getCashInFeeType())),
				SwingConstants.CENTER);

			if (isInvoice) {
				newRow.addCell(customer.isNO_INV_REG_FLG() ? getWord("C12198") : "", SwingConstants.CENTER);// ��K�i���������s���Ǝ�
				newRow.addCell(customer.getNO_INV_REG_ZEI_NAME());// �����
				newRow.addCell(customer.getINV_REG_NO());// �K�i���������s���Ǝғo�^�ԍ�
			}
			newRow.addCell(customer.getDateFrom());
			newRow.addCell(customer.getDateTo());
		}

	}

	/**
	 * �I������Ă�������敪��A��
	 * 
	 * @param customer
	 * @return �I�����ꂽ�����敪
	 */
	protected String getTriType(Customer customer) {

		// �����敪
		StringBuilder sb = new StringBuilder();
		if (customer.isCharterer()) {
			sb.append(getWord("CTC0058"));
		}
		if (customer.isOwner()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0059"));
		}
		if (customer.isPortAgent()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0115"));
		}
		if (customer.isSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0116"));
		}
		if (customer.isBroker()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0078"));
		}
		// BANK
		if (customer.isBank()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("COP532"));
		}
		if (customer.isOther()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0117"));
		}
		if (customer.isShipper()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL206"));
		}
		if (customer.isConsignee()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL209"));
		}
		if (customer.isNotifyParty()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL320"));
		}
		if (customer.isFowarder()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL833"));
		}
		if (customer.isBunkerTrader()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL834"));
		}
		if (customer.isBunkerSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL835"));
		}

		return sb.toString();
	}

}
