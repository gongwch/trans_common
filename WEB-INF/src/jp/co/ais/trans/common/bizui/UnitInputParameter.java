package jp.co.ais.trans.common.bizui;

import java.util.*;

/**
 * �e�R���|�[�l���g��InputParameter�ɏ������Z�b�g
 * 
 */
public class UnitInputParameter extends AccountItemInputParameter {
	/** AccountItemInputParameter�N���X */
	private List<AccountItemInputParameter> paramList;

	/**
	 * @param paramList
	 */
	public UnitInputParameter(List<AccountItemInputParameter> paramList) {
		this.paramList = Collections.synchronizedList(new LinkedList<AccountItemInputParameter>());
		this.paramList = paramList;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param companyCode ��ЃR�[�h
	 * 
	 */
	public void setCompanyCode(String companyCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setCompanyCode(companyCode);
		}
	}

	/**
	 * �ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param itemCode �ȖڃR�[�h
	 * 
	 */
	public void setItemCode(String itemCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemCode(itemCode);
		}
	}

	/**
	 * �Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param itemAbbrName �Ȗڗ���
	 * 
	 */
	public void setItemAbbrName(String itemAbbrName) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemAbbrName(itemAbbrName);
		}
	}

	/**
	 * �Ȗڌ������̂�ݒ肷��B<BR>
	 * 
	 * @param itemNameForSearch �Ȗڌ�������
	 * 
	 */
	public void setItemNameForSearch(String itemNameForSearch) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemNameForSearch(itemNameForSearch);
		}
	}

	/**
	 * �⏕�ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param subItemCode �⏕�ȖڃR�[�h
	 * 
	 */
	public void setSubItemCode(String subItemCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemCode(subItemCode);
		}
	}

	/**
	 * �⏕�Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param subItemAbbrName �⏕�Ȗڗ���
	 * 
	 */
	public void setSubItemAbbrName(String subItemAbbrName) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemAbbrName(subItemAbbrName);
		}
	}

	/**
	 * �⏕�Ȗڌ������̂�ݒ肷��B<BR>
	 * 
	 * @param subItemNameForSearch �⏕�Ȗڌ�������
	 * 
	 */
	public void setSubItemNameForSearch(String subItemNameForSearch) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemNameForSearch(subItemNameForSearch);
		}
	}

	/**
	 * ����ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param breakDownItemCode ����ȖڃR�[�h
	 * 
	 */
	public void setBreakDownItemCode(String breakDownItemCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemCode(breakDownItemCode);
		}
	}

	/**
	 * ����Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param breakDownItemAbbrName ����Ȗڗ���
	 * 
	 */
	public void setBreakDownItemAbbrName(String breakDownItemAbbrName) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemAbbrName(breakDownItemAbbrName);
		}
	}

	/**
	 * ����Ȗڌ������̂�ݒ肷��B<BR>
	 * 
	 * @param breakDownItemNameForSearch ����Ȗڌ�������
	 * 
	 */
	public void setBreakDownItemNameForSearch(String breakDownItemNameForSearch) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemNameForSearch(breakDownItemNameForSearch);
		}
	}

	/**
	 * �⏕�敪��ݒ肷��B<BR>
	 * 
	 * @param subItemDivision �⏕�敪 0:�Ȃ� 1:����
	 * 
	 */
	public void setSubItemDivision(String subItemDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setSubItemDivision(subItemDivision);
		}
	}

	/**
	 * ����敪��ݒ肷��B<BR>
	 * 
	 * @param breakDownItemDivision ����敪 0:�Ȃ� 1:����
	 * 
	 */
	public void setBreakDownItemDivision(String breakDownItemDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setBreakDownItemDivision(breakDownItemDivision);
		}
	}

	/**
	 * �f�k�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param glItemCtrlDivision �f�k�Ȗڐ���敪 00:�ʏ� 01:�O���J�z���v 04:�����Ȗ� 05:����Ȗ� 06:�ב֊��Z�����v 07:������ 08:�ב֍���
	 *            09:�ב֍��v
	 * 
	 */
	public void setGlItemCtrlDivision(String glItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setGlItemCtrlDivision(glItemCtrlDivision);
		}
	}

	/**
	 * �`�[���t��ݒ肷��B
	 * 
	 * @param slipDate �`�[���t
	 */
	public void setSlipDate(String slipDate) {
		for (AccountItemInputParameter param : paramList) {
			param.setSlipDate(slipDate);
		}
	}

	/**
	 * BS��������敪��ݒ肷��B
	 * 
	 * @param bsAccountErasingDivision BS��������敪 0:���Ȃ� 1:����
	 */
	public void setBsAccountErasingDivision(String bsAccountErasingDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setBsAccountErasingDivision(bsAccountErasingDivision);
		}
	}

	/**
	 * �W�v�敪��ݒ肷��B
	 */
	public void setSummaryDivision(String summaryDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setSummaryDivision(summaryDivision);
		}
	}

	/**
	 * �U�֓`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param transferSlipInputFlag �U�֓`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setTransferSlipInputFlag(String transferSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setTransferSlipInputFlag(transferSlipInputFlag);
		}
	}

	/**
	 * �o��Z�`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param expenseInputFlag �o��Z�`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setExpenseInputFlag(String expenseInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setExpenseInputFlag(expenseInputFlag);
		}
	}

	/**
	 * ���v������׸ނ�ݒ肷��B
	 * 
	 * @param saimuFlg ���v������׸� 0:���͕s�� 1:���͉�
	 */
	public void setSaimuFlg(String saimuFlg) {
		for (AccountItemInputParameter param : paramList) {
			param.setSaimuFlg(saimuFlg);
		}
	}

	/**
	 * ���v������׸ނ�ݒ肷��B
	 * 
	 * @param saikenFlg ���v������׸� 0:���͕s�� 1:���͉�
	 */
	public void setSaikenFlg(String saikenFlg) {
		for (AccountItemInputParameter param : paramList) {
			param.setSaikenFlg(saikenFlg);
		}
	}

	/**
	 * �������`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param accountsRecivableErasingSlipInputFlag �������`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setAccountsRecivableErasingSlipInputFlag(String accountsRecivableErasingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAccountsRecivableErasingSlipInputFlag(accountsRecivableErasingSlipInputFlag);
		}
	}

	/**
	 * ���Y�v��`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param assetsAppropriatingSlipInputFlag ���Y�v��`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setAssetsAppropriatingSlipInputFlag(String assetsAppropriatingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAssetsAppropriatingSlipInputFlag(assetsAppropriatingSlipInputFlag);
		}
	}

	/**
	 * �x���˗��`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param paymentRequestSlipInputFlag �x���˗��`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setPaymentRequestSlipInputFlag(String paymentRequestSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setPaymentRequestSlipInputFlag(paymentRequestSlipInputFlag);
		}
	}

	/**
	 * ����R�[�h��ݒ肷��B
	 * 
	 * @param departmentCode ����R�[�h
	 */
	public void setDepartmentCode(String departmentCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setDepartmentCode(departmentCode);
		}
	}

	/**
	 * �]���֑Ώۃt���O��ݒ肷��B
	 * 
	 * @param revaluationObjectFlag �]���֑Ώۃt���O 0:���Ȃ� 1:���֖@ 2:�ؗ��@
	 */
	public void setRevaluationObjectFlag(String revaluationObjectFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setRevaluationObjectFlag(revaluationObjectFlag);
		}
	}

	/**
	 * �����`�[���̓t���O��ݒ肷��B
	 * 
	 * @param recivingSlipInputFlag �����`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setRecivingSlipInputFlag(String recivingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setRecivingSlipInputFlag(recivingSlipInputFlag);
		}
	}

	/**
	 * �o���`�[���̓t���O��ݒ肷��B
	 * 
	 * @param drawingSlipInputFlag �o���`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setDrawingSlipInputFlag(String drawingSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setDrawingSlipInputFlag(drawingSlipInputFlag);
		}
	}

	/**
	 * AR����敪��ݒ肷��B
	 * 
	 * @param arItemCtrlDivision AR����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02:�����������
	 */
	public void setArItemCtrlDivision(String arItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setArItemCtrlDivision(arItemCtrlDivision);
		}
	}
	
	/**
	 * AR����敪��ݒ肷��B(�����p)
	 * 
	 * @param unArItemCtrlDivision AR����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02:�����������
	 */
	public void setUnArItemCtrlDivision(String unArItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setUnArItemCtrlDivision(unArItemCtrlDivision);
		}
	}
	
	/**
	 * AP����敪��ݒ肷��B
	 * 
	 * @param apItemCtrlDivision AP����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02�F�]�ƈ������Ȗ�
	 */
	public void setApItemCtrlDivision(String apItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setApItemCtrlDivision(apItemCtrlDivision);
		}
	}

	/**
	 * �Ǘ�1�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management1InputFlag �Ǘ�1�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement1InputFlag(String management1InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement1InputFlag(management1InputFlag);
		}
	}

	/**
	 * �Ǘ�2�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management2InputFlag �Ǘ�2�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement2InputFlag(String management2InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement2InputFlag(management2InputFlag);
		}
	}

	/**
	 * �Ǘ�3�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management3InputFlag �Ǘ�3�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement3InputFlag(String management3InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement3InputFlag(management3InputFlag);
		}
	}

	/**
	 * �Ǘ�4�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management4InputFlag �Ǘ�4�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement4InputFlag(String management4InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement4InputFlag(management4InputFlag);
		}
	}

	/**
	 * �Ǘ�5�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management5InputFlag �Ǘ�5�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement5InputFlag(String management5InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement5InputFlag(management5InputFlag);
		}
	}

	/**
	 * �Ǘ�6�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management6InputFlag �Ǘ�6�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement6InputFlag(String management6InputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setManagement6InputFlag(management6InputFlag);
		}
	}

	/**
	 * ���v1���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param  nonAccountingDetail1Flag ���v1���̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setNonAccountingDetail1Flag(String nonAccountingDetail1Flag) {
		for (AccountItemInputParameter param : paramList) {
			param.setNonAccountingDetail1Flag(nonAccountingDetail1Flag);
		}
	}

	/**
	 * ���v2���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail2Flag ���v2���̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setNonAccountingDetail2Flag(String nonAccountingDetail2Flag) {
		for (AccountItemInputParameter param : paramList) {
			param.setNonAccountingDetail2Flag(nonAccountingDetail2Flag);
		}
	}

	/**
	 * ���v3���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail3Flag ���v3���̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setNonAccountingDetail3Flag(String nonAccountingDetail3Flag) {
		for (AccountItemInputParameter param : paramList) {
			param.setNonAccountingDetail3Flag(nonAccountingDetail3Flag);
		}
	}

	/**
	 * ���������̓t���O��ݒ肷��B<BR>
	 * 
	 * @param accrualDateInputFlag ���������̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setAccrualDateInputFlag(String accrualDateInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAccrualDateInputFlag(accrualDateInputFlag);
		}
	}

	/**
	 * ���ʉ݃t���O��ݒ肷��B<BR>
	 * 
	 * @param multipleCurrencyInputFlag ���ʉ݃t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setMultipleCurrencyInputFlag(String multipleCurrencyInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setMultipleCurrencyInputFlag(multipleCurrencyInputFlag);
		}
	}

	/**
	 * �������̓t���O��ݒ肷��B<BR>
	 * 
	 * @param customerInputFlag �������̓t���O 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ恕�d����
	 * 
	 */
	public void setCustomerInputFlag(String customerInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setCustomerInputFlag(customerInputFlag);
		}
	}

	/**
	 * �Ј����̓t���O��ݒ肷��B<BR>
	 * 
	 * @param employeeInputFlag �Ј����̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setEmployeeInputFlag(String employeeInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setEmployeeInputFlag(employeeInputFlag);
		}
	}

	/**
	 * ����ېœ��̓t���O��ݒ肷��B<BR>
	 * 
	 * @param salesTaxInputFlag ����ېœ��̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setSalesTaxInputFlag(String salesTaxInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setSalesTaxInputFlag(salesTaxInputFlag);
		}
	}

	/**
	 * �d���ېœ��̓t���O��ݒ肷��B<BR>
	 * 
	 * @param purchaseTaxationInputFlag �d���ېœ��̓t���O 0:���͕s�� 1:���͉�
	 * 
	 */
	public void setPurchaseTaxationInputFlag(String purchaseTaxationInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setPurchaseTaxationInputFlag(purchaseTaxationInputFlag);
		}
	}

	/**
	 * ����ź��ނ�ݒ肷��B<BR>
	 * 
	 * @param consumptionTaxCode ����ŃR�[�h
	 * 
	 */
	public void setConsumptionTaxCode(String consumptionTaxCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setConsumptionTaxCode(consumptionTaxCode);
		}
	}

	/**
	 * �Ȗڎ�ʂ�ݒ肷��B<BR>
	 * 
	 * @param itemType �Ȗڎ�� 0:�ݎ؉Ȗ� 1:���v�Ȗ� 2:���v�����Ȗ� 3:���������Ȗ� 9:���v�Ȗ�
	 * 
	 */
	public void setItemType(String itemType) {
		for (AccountItemInputParameter param : paramList) {
			param.setItemType(itemType);
		}
	}

	/**
	 * �ݎ؋敪��ݒ肷��B<BR>
	 * 
	 * @param debitAndCreditDivision �ݎ؋敪 0:�ؕ��Ȗ� 1:�ݕ��Ȗ�
	 * 
	 */
	public void setDebitAndCreditDivision(String debitAndCreditDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setDebitAndCreditDivision(debitAndCreditDivision);
		}
	}

	/**
	 * �Œ蕔�庰�ނ�ݒ肷��B<BR>
	 * 
	 * @param fixedDepartment �Œ蕔�庰��
	 * 
	 */
	public void setFixedDepartment(String fixedDepartment) {
		for (AccountItemInputParameter param : paramList) {
			param.setFixedDepartment(fixedDepartment);
		}
	}

	/**
	 * �������`�[���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param adjustmentSlipInputFlag �������`�[���̓t���O 0:���͕s�� 1:���͉�
	 * 
	 */
	public void setAdjustmentSlipInputFlag(String adjustmentSlipInputFlag) {
		for (AccountItemInputParameter param : paramList) {
			param.setAdjustmentSlipInputFlag(adjustmentSlipInputFlag);
		}
	}

	/**
	 * BG�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param bgItemCtrlDivision BG�Ȗڐ���敪 00:�ʏ� 11:�\�Z�Ǘ��Ώ�
	 * 
	 */
	public void setBgItemCtrlDivision(String bgItemCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setBgItemCtrlDivision(bgItemCtrlDivision);
		}
	}

	/**
	 * ���E���Z����敪��ݒ肷��B<BR>
	 * 
	 * @param counterbalanceAdjustmentCtrlDivision ���E���Z����敪 0:���Ȃ� 1:����
	 * 
	 */
	public void setCounterbalanceAdjustmentCtrlDivision(String counterbalanceAdjustmentCtrlDivision) {
		for (AccountItemInputParameter param : paramList) {
			param.setCounterbalanceAdjustmentCtrlDivision(counterbalanceAdjustmentCtrlDivision);
		}
	}

	/**
	 * �`�[���t��ݒ肷��B(Date�^)
	 * 
	 * @param dateSlipDate �`�[���t
	 */
	public void setDateSlipDate(Date dateSlipDate) {
		for (AccountItemInputParameter param : paramList) {
			param.setDateSlipDate(dateSlipDate);
		}
	}

	/**
	 * �Ј����ނ�ݒ肷��B<BR>
	 * 
	 * @param employeeCode �Ј�����
	 * 
	 */
	public void setEmployeeCode(String employeeCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setEmployeeCode(employeeCode);
		}
	}

	/**
	 * �I���R�[�h
	 * 
	 * @param endCode �I���R�[�h
	 */
	public void setEndCode(String endCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setEndCode(endCode);
		}
	}

	/**
	 * �J�n�R�[�h
	 * 
	 * @param beginCode �J�n�R�[�h
	 */
	public void setBeginCode(String beginCode) {
		for (AccountItemInputParameter param : paramList) {
			param.setBeginCode(beginCode);
		}
	}

}
