package jp.co.ais.trans.common.bizui;

import java.io.*;
import java.util.*;

/**
 * �Ȗڃt�B�[���h���͏���
 */
public class AccountItemInputParameter implements Serializable {

	/** ��ЃR�[�h */
	private String companyCode = "";

	/** �ȖڃR�[�h */
	private String itemCode = "";

	/** �Ȗڗ��� */
	private String itemAbbrName = "";

	/** �Ȗڌ������� */
	private String itemNameForSearch = "";

	/** �⏕�ȖڃR�[�h */
	private String subItemCode = "";

	/** �⏕�Ȗڗ��� */
	private String subItemAbbrName = "";

	/** �⏕�Ȗڌ������� */
	private String subItemNameForSearch = "";

	/** ����ȖڃR�[�h */
	private String breakDownItemCode = "";

	/** ����Ȗڗ��� */
	private String breakDownItemAbbrName = "";

	/** ����Ȗڌ������� */
	private String breakDownItemNameForSearch = "";

	/** �⏕�敪 */
	private String subItemDivision = "";

	/** ����敪 */
	private String breakDownItemDivision = "";

	/** �f�k�Ȗڐ���敪 */
	private String glItemCtrlDivision = "";

	/** AP����敪 */
	private String apItemCtrlDivision = "";

	/** AR����敪 */
	private String arItemCtrlDivision = "";

	/** AR����敪(�����p) */
	private String unArItemCtrlDivision = "";

	/** �`�[���t */
	private String slipDate = "";

	/** �`�[���t(Date�^) */
	private Date dateSlipDate = null;

	/** BS��������敪 */
	private String bsAccountErasingDivision = "";

	/** �W�v�敪 */
	private String summaryDivision = "";

	/** �U�֓`�[�����׸� */
	private String transferSlipInputFlag = "";

	/** ���v������׸� */
	private String saimuFlg = "";

	/** ���v������׸� */
	private String saikenFlg = "";

	/** �o��Z�`�[�����׸� */
	private String expenseInputFlag = "";

	/** �������`�[�����׸� */
	private String accountsRecivableErasingSlipInputFlag = "";

	/** ���Y�v��`�[�����׸� */
	private String assetsAppropriatingSlipInputFlag = "";

	/** �x���˗��`�[�����׸� */
	private String paymentRequestSlipInputFlag = "";

	/** ����R�[�h */
	private String departmentCode = "";

	/** �]���֑Ώۃt���O */
	private String revaluationObjectFlag = "";

	/** �����`�[���̓t���O */
	private String recivingSlipInputFlag = "";

	/** �o���`�[���̓t���O */
	private String drawingSlipInputFlag = "";

	/** ���������׸� */
	private String customerInputFlag = "";

	/** �Ј������׸� */
	private String employeeInputFlag = "";

	/** ���������̓t���O */
	private String accrualDateInputFlag = "";

	/** �Ǘ�1�����׸� */
	private String management1InputFlag = "";

	/** �Ǘ�2�����׸� */
	private String management2InputFlag = "";

	/** �Ǘ�3�����׸� */
	private String management3InputFlag = "";

	/** �Ǘ�4�����׸� */
	private String management4InputFlag = "";

	/** �Ǘ�5�����׸� */
	private String management5InputFlag = "";

	/** �Ǘ�6�����׸� */
	private String management6InputFlag = "";

	/** ���v1���̓t���O */
	private String nonAccountingDetail1Flag = "";

	/** ���v2���̓t���O */
	private String nonAccountingDetail2Flag = "";

	/** ���v3���̓t���O */
	private String nonAccountingDetail3Flag = "";

	/** ����ېœ��̓t���O */
	private String salesTaxInputFlag = "";

	/** �d���ېœ��̓t���O */
	private String purchaseTaxationInputFlag = "";

	/** ���ʉ݃t���O */
	private String multipleCurrencyInputFlag = "";

	/** ����ź��� */
	private String consumptionTaxCode = "";

	/** �Ȗڎ�� */
	private String itemType = "";

	/** �ݎ؋敪 */
	private String debitAndCreditDivision = "";

	/** �Œ蕔�庰�� */
	private String fixedDepartment = "";

	/** �������`�[���̓t���O */
	private String adjustmentSlipInputFlag = "";

	/** BG�Ȗڐ���敪 */
	private String bgItemCtrlDivision = "";

	/** ���E���Z����敪 */
	private String counterbalanceAdjustmentCtrlDivision = "";

	/** �Ј����� */
	private String employeeCode = "";

	/** �J�n�R�[�h */
	private String beginCode = "";

	/** �I���R�[�h */
	private String endCode = "";

	/** �Ȗڑ̌n�R�[�h */
	private String itemSystemCode = "";

	/** �Ȗڑ̌n�g�p�t���O */
	private String itemSystemFlg = "";

	/**
	 * ��ЃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ��ЃR�[�h��Ԃ��B<BR>
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * �ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param itemCode �ȖڃR�[�h
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * �ȖڃR�[�h��Ԃ��B<BR>
	 * 
	 * @return itemCode �ȖڃR�[�h
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * �Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param itemAbbrName �Ȗڗ���
	 */
	public void setItemAbbrName(String itemAbbrName) {
		this.itemAbbrName = itemAbbrName;
	}

	/**
	 * �Ȗڗ��̂�Ԃ��B<BR>
	 * 
	 * @return itemAbbrName �Ȗڗ���
	 */
	public String getItemAbbrName() {
		return itemAbbrName;
	}

	/**
	 * �Ȗڌ������̂�ݒ肷��B<BR>
	 * 
	 * @param itemNameForSearch �Ȗڌ�������
	 */
	public void setItemNameForSearch(String itemNameForSearch) {
		this.itemNameForSearch = itemNameForSearch;
	}

	/**
	 * �Ȗڌ������̂�Ԃ��B<BR>
	 * 
	 * @return itemNameForSearch �Ȗڌ�������
	 */
	public String getItemNameForSearch() {
		return itemNameForSearch;
	}

	/**
	 * �⏕�ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param subItemCode �⏕�ȖڃR�[�h
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h��Ԃ��B<BR>
	 * 
	 * @return subItemCode �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * �⏕�Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param subItemAbbrName �⏕�Ȗڗ���
	 */
	public void setSubItemAbbrName(String subItemAbbrName) {
		this.subItemAbbrName = subItemAbbrName;
	}

	/**
	 * �⏕�Ȗڗ��̂�Ԃ��B<BR>
	 * 
	 * @return subItemAbbrName �⏕�Ȗڗ���
	 */
	public String getSubItemAbbrName() {
		return subItemAbbrName;
	}

	/**
	 * �⏕�Ȗڌ������̂�ݒ肷��B<BR>
	 * 
	 * @param subItemNameForSearch �⏕�Ȗڌ�������
	 */
	public void setSubItemNameForSearch(String subItemNameForSearch) {
		this.subItemNameForSearch = subItemNameForSearch;
	}

	/**
	 * �⏕�Ȗڌ������̂�Ԃ��B<BR>
	 * 
	 * @return subItemNameForSearch �⏕�Ȗڌ�������
	 */
	public String getSubItemNameForSearch() {
		return subItemNameForSearch;
	}

	/**
	 * ����ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param breakDownItemCode ����ȖڃR�[�h
	 */
	public void setBreakDownItemCode(String breakDownItemCode) {
		this.breakDownItemCode = breakDownItemCode;
	}

	/**
	 * ����ȖڃR�[�h��Ԃ��B<BR>
	 * 
	 * @return breakDownItemCode ����ȖڃR�[�h
	 */
	public String getBreakDownItemCode() {
		return breakDownItemCode;
	}

	/**
	 * ����Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param breakDownItemAbbrName ����Ȗڗ���
	 */
	public void setBreakDownItemAbbrName(String breakDownItemAbbrName) {
		this.breakDownItemAbbrName = breakDownItemAbbrName;
	}

	/**
	 * ����Ȗڗ��̂�Ԃ��B<BR>
	 * 
	 * @return breakDownItemAbbrName ����Ȗڗ���
	 */
	public String getBreakDownItemAbbrName() {
		return breakDownItemAbbrName;
	}

	/**
	 * ����Ȗڌ������̂�ݒ肷��B<BR>
	 * 
	 * @param breakDownItemNameForSearch ����Ȗڌ�������
	 */
	public void setBreakDownItemNameForSearch(String breakDownItemNameForSearch) {
		this.breakDownItemNameForSearch = breakDownItemNameForSearch;
	}

	/**
	 * ����Ȗڌ������̂�Ԃ��B<BR>
	 * 
	 * @return breakDownItemNameForSearch ����Ȗڌ�������
	 */
	public String getBreakDownItemNameForSearch() {
		return breakDownItemNameForSearch;
	}

	/**
	 * �⏕�敪��ݒ肷��B<BR>
	 * 
	 * @param subItemDivision �⏕�敪 0:�Ȃ� 1:����
	 */
	public void setSubItemDivision(String subItemDivision) {
		this.subItemDivision = subItemDivision;
	}

	/**
	 * �⏕�敪��Ԃ��B<BR>
	 * 
	 * @return subItemDivision �⏕�敪 0:�Ȃ� 1:����
	 */
	public String getSubItemDivision() {
		return subItemDivision;
	}

	/**
	 * ����敪��ݒ肷��B<BR>
	 * 
	 * @param breakDownItemDivision ����敪 0:�Ȃ� 1:����
	 */
	public void setBreakDownItemDivision(String breakDownItemDivision) {
		this.breakDownItemDivision = breakDownItemDivision;
	}

	/**
	 * ����敪��Ԃ��B<BR>
	 * 
	 * @return breakDownItemDivision ����敪 0:�Ȃ� 1:����
	 */
	public String getBreakDownItemDivision() {
		return breakDownItemDivision;
	}

	/**
	 * �f�k�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param glItemCtrlDivision �f�k�Ȗڐ���敪 00:�ʏ� 01:�O���J�z���v 04:�����Ȗ� 05:����Ȗ� 06:�ב֊��Z�����v 07:������ 08:�ב֍��� 09:�ב֍��v
	 */
	public void setGlItemCtrlDivision(String glItemCtrlDivision) {
		this.glItemCtrlDivision = glItemCtrlDivision;
	}

	/**
	 * �f�k�Ȗڐ���敪��Ԃ��B<BR>
	 * 
	 * @return glItemCtrlDivision �f�k�Ȗڐ���敪 00:�ʏ� 01:�O���J�z���v 04:�����Ȗ� 05:����Ȗ� 06:�ב֊��Z�����v 07:������ 08:�ב֍��� 09:�ב֍��v
	 */
	public String getGlItemCtrlDivision() {
		return glItemCtrlDivision;
	}

	/**
	 * �`�[���t��ݒ肷��B
	 * 
	 * @param slipDate �`�[���t
	 */
	public void setSlipDate(String slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * �`�[���t��Ԃ��B
	 * 
	 * @return slipDate �`�[���t
	 */
	public String getSlipDate() {
		return slipDate;
	}

	/**
	 * BS��������敪��ݒ肷��B
	 * 
	 * @param bsAccountErasingDivision BS��������敪 0:���Ȃ� 1:����
	 */
	public void setBsAccountErasingDivision(String bsAccountErasingDivision) {
		this.bsAccountErasingDivision = bsAccountErasingDivision;
	}

	/**
	 * BS��������敪��Ԃ��B
	 * 
	 * @return bsAccountErasingDivision BS��������敪 0:���Ȃ� 1:����
	 */
	public String getBsAccountErasingDivision() {
		return bsAccountErasingDivision;
	}

	/**
	 * �W�v�敪��ݒ肷��B
	 * 
	 * @param summaryDivision �W�v�敪 0:���͉Ȗ� 1:�W�v�Ȗ� 2:���o�Ȗ�
	 */
	public void setSummaryDivision(String summaryDivision) {
		this.summaryDivision = summaryDivision;
	}

	/**
	 * �W�v�敪��Ԃ��B
	 * 
	 * @return sumKbn �W�v�敪 0:���͉Ȗ� 1:�W�v�Ȗ� 2:���o�Ȗ�
	 */
	public String getSummaryDivision() {
		return summaryDivision;
	}

	/**
	 * �U�֓`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param transferSlipInputFlag �U�֓`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setTransferSlipInputFlag(String transferSlipInputFlag) {
		this.transferSlipInputFlag = transferSlipInputFlag;
	}

	/**
	 * �U�֓`�[�����׸ނ�Ԃ��B
	 * 
	 * @return furikaeFlg �U�֓`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public String getTransferSlipInputFlag() {
		return transferSlipInputFlag;
	}

	/**
	 * �o��Z�`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param expenseInputFlag �o��Z�`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setExpenseInputFlag(String expenseInputFlag) {
		this.expenseInputFlag = expenseInputFlag;
	}

	/**
	 * �o��Z�`�[�����׸ނ�Ԃ��B
	 * 
	 * @return expenseInputFlag �o��Z�`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public String getExpenseInputFlag() {
		return expenseInputFlag;
	}

	/**
	 * ���v������׸ނ�ݒ肷��B
	 * 
	 * @param saimuFlg ���v������׸� 0:���͕s�� 1:���͉�
	 */
	public void setSaimuFlg(String saimuFlg) {
		this.saimuFlg = saimuFlg;
	}

	/**
	 * ���v������׸ނ�Ԃ��B
	 * 
	 * @return saimuFlg ���v������׸� 0:���͕s�� 1:���͉�
	 */
	public String getSaimuFlg() {
		return saimuFlg;
	}

	/**
	 * ���v������׸ނ�ݒ肷��B
	 * 
	 * @param saikenFlg ���v������׸� 0:���͕s�� 1:���͉�
	 */
	public void setSaikenFlg(String saikenFlg) {
		this.saikenFlg = saikenFlg;
	}

	/**
	 * ���v������׸ނ�Ԃ��B
	 * 
	 * @return saikenFlg ���v������׸� 0:���͕s�� 1:���͉�
	 */
	public String getSaikenFlg() {
		return saikenFlg;
	}

	/**
	 * �������`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param accountsRecivableErasingSlipInputFlag �������`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setAccountsRecivableErasingSlipInputFlag(String accountsRecivableErasingSlipInputFlag) {
		this.accountsRecivableErasingSlipInputFlag = accountsRecivableErasingSlipInputFlag;
	}

	/**
	 * �������`�[�����׸ނ�Ԃ��B
	 * 
	 * @return saikesiFlg �������`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public String getAccountsRecivableErasingSlipInputFlag() {
		return accountsRecivableErasingSlipInputFlag;
	}

	/**
	 * ���Y�v��`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param assetsAppropriatingSlipInputFlag ���Y�v��`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setAssetsAppropriatingSlipInputFlag(String assetsAppropriatingSlipInputFlag) {
		this.assetsAppropriatingSlipInputFlag = assetsAppropriatingSlipInputFlag;
	}

	/**
	 * ���Y�v��`�[�����׸ނ�Ԃ��B
	 * 
	 * @return assetsAppropriatingSlipInputFlag ���Y�v��`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public String getAssetsAppropriatingSlipInputFlag() {
		return assetsAppropriatingSlipInputFlag;
	}

	/**
	 * �x���˗��`�[�����׸ނ�ݒ肷��B
	 * 
	 * @param paymentRequestSlipInputFlag �x���˗��`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public void setPaymentRequestSlipInputFlag(String paymentRequestSlipInputFlag) {
		this.paymentRequestSlipInputFlag = paymentRequestSlipInputFlag;
	}

	/**
	 * �x���˗��`�[�����׸ނ�Ԃ��B
	 * 
	 * @return paymentRequestSlipInputFlag �x���˗��`�[�����׸� 0:���͕s�� 1:���͉�
	 */
	public String getPaymentRequestSlipInputFlag() {
		return paymentRequestSlipInputFlag;
	}

	/**
	 * ����R�[�h��ݒ肷��B
	 * 
	 * @param departmentCode ����R�[�h
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * ����R�[�h��Ԃ��B
	 * 
	 * @return bmnCode ����R�[�h
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * �]���֑Ώۃt���O��ݒ肷��B
	 * 
	 * @param revaluationObjectFlag �]���֑Ώۃt���O 0:���Ȃ� 1:���֖@ 2:�ؗ��@
	 */
	public void setRevaluationObjectFlag(String revaluationObjectFlag) {
		this.revaluationObjectFlag = revaluationObjectFlag;
	}

	/**
	 * �]���֑Ώۃt���O��Ԃ��B
	 * 
	 * @return revaluationObjectFlag �]���֑Ώۃt���O 0:���Ȃ� 1:���֖@ 2:�ؗ��@
	 */
	public String getRevaluationObjectFlag() {
		return revaluationObjectFlag;
	}

	/**
	 * �����`�[���̓t���O��ݒ肷��B
	 * 
	 * @param recivingSlipInputFlag �����`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setRecivingSlipInputFlag(String recivingSlipInputFlag) {
		this.recivingSlipInputFlag = recivingSlipInputFlag;
	}

	/**
	 * �����`�[���̓t���O��Ԃ��B
	 * 
	 * @return recivingSlipInputFlag �����`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public String getRecivingSlipInputFlag() {
		return recivingSlipInputFlag;
	}

	/**
	 * �o���`�[���̓t���O��ݒ肷��B
	 * 
	 * @param drawingSlipInputFlag �o���`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setDrawingSlipInputFlag(String drawingSlipInputFlag) {
		this.drawingSlipInputFlag = drawingSlipInputFlag;
	}

	/**
	 * �o���`�[���̓t���O��Ԃ��B
	 * 
	 * @return drawingSlipInputFlag �o���`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public String getDrawingSlipInputFlag() {
		return drawingSlipInputFlag;
	}

	/**
	 * AR����敪��ݒ肷��B
	 * 
	 * @param arItemCtrlDivision AR����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02:�����������
	 */
	public void setArItemCtrlDivision(String arItemCtrlDivision) {
		this.arItemCtrlDivision = arItemCtrlDivision;
	}

	/**
	 * AR����敪��Ԃ�
	 * 
	 * @return arItemCtrlDivision AR����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02:�����������
	 */
	public String getArItemCtrlDivision() {
		return arItemCtrlDivision;
	}

	/**
	 * AR����敪��ݒ肷��B(�����p)
	 * 
	 * @param unArItemCtrlDivision AR����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02:�����������
	 */
	public void setUnArItemCtrlDivision(String unArItemCtrlDivision) {
		this.unArItemCtrlDivision = unArItemCtrlDivision;
	}

	/**
	 * AR����敪��Ԃ�(�����p)
	 * 
	 * @return unArItemCtrlDivision AR����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02:�����������
	 */
	public String getUnArItemCtrlDivision() {
		return unArItemCtrlDivision;
	}

	/**
	 * AP����敪��ݒ肷��B
	 * 
	 * @param apItemCtrlDivision AP����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02�F�]�ƈ������Ȗ�
	 */
	public void setApItemCtrlDivision(String apItemCtrlDivision) {
		this.apItemCtrlDivision = apItemCtrlDivision;
	}

	/**
	 * AP����敪��Ԃ�
	 * 
	 * @return apItemCtrlDivision AP����敪 00:�ʏ� 01:���Ǘ��Ȗ� 02�F�]�ƈ������Ȗ�
	 */
	public String getApItemCtrlDivision() {
		return apItemCtrlDivision;
	}

	/**
	 * �Ǘ�1�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management1InputFlag �Ǘ�1�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement1InputFlag(String management1InputFlag) {
		this.management1InputFlag = management1InputFlag;
	}

	/**
	 * �Ǘ�1�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management1InputFlag �Ǘ�1�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public String getManagement1InputFlag() {
		return this.management1InputFlag;
	}

	/**
	 * �Ǘ�2�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management2InputFlag �Ǘ�2�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement2InputFlag(String management2InputFlag) {
		this.management2InputFlag = management2InputFlag;
	}

	/**
	 * �Ǘ�2�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management2InputFlag �Ǘ�2�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public String getManagement2InputFlag() {
		return this.management2InputFlag;
	}

	/**
	 * �Ǘ�3�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management3InputFlag �Ǘ�3�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement3InputFlag(String management3InputFlag) {
		this.management3InputFlag = management3InputFlag;
	}

	/**
	 * �Ǘ�3�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management3InputFlag �Ǘ�3�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public String getManagement3InputFlag() {
		return this.management3InputFlag;
	}

	/**
	 * �Ǘ�4�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management4InputFlag �Ǘ�4�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement4InputFlag(String management4InputFlag) {
		this.management4InputFlag = management4InputFlag;
	}

	/**
	 * �Ǘ�4�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management4InputFlag �Ǘ�4�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public String getManagement4InputFlag() {
		return this.management4InputFlag;
	}

	/**
	 * �Ǘ�5�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management5InputFlag �Ǘ�5�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement5InputFlag(String management5InputFlag) {
		this.management5InputFlag = management5InputFlag;
	}

	/**
	 * �Ǘ�5�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management5InputFlag �Ǘ�5�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public String getManagement5InputFlag() {
		return this.management5InputFlag;
	}

	/**
	 * �Ǘ�6�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management6InputFlag �Ǘ�6�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement6InputFlag(String management6InputFlag) {
		this.management6InputFlag = management6InputFlag;
	}

	/**
	 * �Ǘ�6�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management6InputFlag �Ǘ�6�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public String getManagement6InputFlag() {
		return this.management6InputFlag;
	}

	/**
	 * ���v1���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail1Flag ���v1���̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setNonAccountingDetail1Flag(String nonAccountingDetail1Flag) {
		this.nonAccountingDetail1Flag = nonAccountingDetail1Flag;
	}

	/**
	 * ���v1���̓t���O��Ԃ��B<BR>
	 * 
	 * @return management6InputFlag ���v1���̓t���O� 0:���͕s�� 1:���͕K�{
	 */
	public String getNonAccountingDetail1Flag() {
		return this.nonAccountingDetail1Flag;
	}

	/**
	 * ���v2���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail2Flag ���v2���̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setNonAccountingDetail2Flag(String nonAccountingDetail2Flag) {
		this.nonAccountingDetail2Flag = nonAccountingDetail2Flag;
	}

	/**
	 * ���v2���̓t���O��Ԃ��B<BR>
	 * 
	 * @return nonAccountingDetail2Flag ���v2���̓t���O� 0:���͕s�� 1:���͕K�{
	 */
	public String getNonAccountingDetail2Flag() {
		return this.nonAccountingDetail2Flag;
	}

	/**
	 * ���v3���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail3Flag ���v3���̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setNonAccountingDetail3Flag(String nonAccountingDetail3Flag) {
		this.nonAccountingDetail3Flag = nonAccountingDetail3Flag;
	}

	/**
	 * ���v3���̓t���O��Ԃ��B<BR>
	 * 
	 * @return nonAccountingDetail3Flag ���v3���̓t���O� 0:���͕s�� 1:���͕K�{
	 */
	public String getNonAccountingDetail3Flag() {
		return this.nonAccountingDetail3Flag;
	}

	/**
	 * ���������̓t���O��ݒ肷��B<BR>
	 * 
	 * @param accrualDateInputFlag ���������̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setAccrualDateInputFlag(String accrualDateInputFlag) {
		this.accrualDateInputFlag = accrualDateInputFlag;
	}

	/**
	 * ���������̓t���O��Ԃ��B<BR>
	 * 
	 * @return accrualDateInputFlag ���������̓t���O� 0:���͕s�� 1:���͕K�{
	 */
	public String getAccrualDateInputFlag() {
		return this.accrualDateInputFlag;
	}

	/**
	 * ���ʉ݃t���O��ݒ肷��B<BR>
	 * 
	 * @param multipleCurrencyInputFlag ���ʉ݃t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setMultipleCurrencyInputFlag(String multipleCurrencyInputFlag) {
		this.multipleCurrencyInputFlag = multipleCurrencyInputFlag;
	}

	/**
	 * ���ʉ݃t���O��Ԃ��B<BR>
	 * 
	 * @return multipleCurrencyInputFlag ���ʉ݃t���O 0:���͕s�� 1:���͕K�{
	 */
	public String getMultipleCurrencyInputFlag() {
		return this.multipleCurrencyInputFlag;
	}

	/**
	 * �������̓t���O��ݒ肷��B<BR>
	 * 
	 * @param customerInputFlag �������̓t���O 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ恕�d����
	 */
	public void setCustomerInputFlag(String customerInputFlag) {
		this.customerInputFlag = customerInputFlag;
	}

	/**
	 * �������̓t���O��Ԃ��B<BR>
	 * 
	 * @return customerInputFlag �������̓t���O 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ恕�d����
	 */
	public String getCustomerInputFlag() {
		return customerInputFlag;
	}

	/**
	 * �Ј����̓t���O��ݒ肷��B<BR>
	 * 
	 * @param employeeInputFlag �Ј����̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setEmployeeInputFlag(String employeeInputFlag) {
		this.employeeInputFlag = employeeInputFlag;
	}

	/**
	 * �Ј����̓t���O��Ԃ��B<BR>
	 * 
	 * @return employeeInputFlag �Ј����̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public String getEmployeeInputFlag() {
		return this.employeeInputFlag;
	}

	/**
	 * ����ېœ��̓t���O��ݒ肷��B<BR>
	 * 
	 * @param salesTaxInputFlag ����ېœ��̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setSalesTaxInputFlag(String salesTaxInputFlag) {
		this.salesTaxInputFlag = salesTaxInputFlag;
	}

	/**
	 * ����ېœ��̓t���O��Ԃ��B<BR>
	 * 
	 * @return salesTaxInputFlag ����ېœ��̓t���O 0:���͕s�� 1:���͉�
	 */
	public String getSalesTaxInputFlag() {
		return salesTaxInputFlag;
	}

	/**
	 * �d���ېœ��̓t���O��ݒ肷��B<BR>
	 * 
	 * @param purchaseTaxationInputFlag �d���ېœ��̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setPurchaseTaxationInputFlag(String purchaseTaxationInputFlag) {
		this.purchaseTaxationInputFlag = purchaseTaxationInputFlag;
	}

	/**
	 * �d���ېœ��̓t���O��Ԃ��B<BR>
	 * 
	 * @return purchaseTaxationInputFlag �d���ېœ��̓t���O 0:���͕s�� 1:���͉�
	 */
	public String getPurchaseTaxationInputFlag() {
		return this.purchaseTaxationInputFlag;
	}

	/**
	 * ����ź��ނ�ݒ肷��B<BR>
	 * 
	 * @param consumptionTaxCode ����ŃR�[�h
	 */
	public void setConsumptionTaxCode(String consumptionTaxCode) {
		this.consumptionTaxCode = consumptionTaxCode;
	}

	/**
	 * ����ź��ނ�Ԃ��B<BR>
	 * 
	 * @return consumptionTaxCode ����ŃR�[�h
	 */
	public String getConsumptionTaxCode() {
		return consumptionTaxCode;
	}

	/**
	 * �Ȗڎ�ʂ�ݒ肷��B<BR>
	 * 
	 * @param itemType �Ȗڎ�� 0:�ݎ؉Ȗ� 1:���v�Ȗ� 2:���v�����Ȗ� 3:���������Ȗ� 9:���v�Ȗ�
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * �Ȗڎ�ʂ�Ԃ��B<BR>
	 * 
	 * @return itemType �Ȗڎ�� 0:�ݎ؉Ȗ� 1:���v�Ȗ� 2:���v�����Ȗ� 3:���������Ȗ� 9:���v�Ȗ�
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * �ݎ؋敪��ݒ肷��B<BR>
	 * 
	 * @param debitAndCreditDivision �ݎ؋敪 0:�ؕ��Ȗ� 1:�ݕ��Ȗ�
	 */
	public void setDebitAndCreditDivision(String debitAndCreditDivision) {
		this.debitAndCreditDivision = debitAndCreditDivision;
	}

	/**
	 * �ݎ؋敪��Ԃ��B<BR>
	 * 
	 * @return debitAndCreditDivision �ݎ؋敪 0:�ؕ��Ȗ� 1:�ݕ��Ȗ�
	 */
	public String getDebitAndCreditDivision() {
		return debitAndCreditDivision;
	}

	/**
	 * �Œ蕔�庰�ނ�ݒ肷��B<BR>
	 * 
	 * @param fixedDepartment �Œ蕔�庰��
	 */
	public void setFixedDepartment(String fixedDepartment) {
		this.fixedDepartment = fixedDepartment;
	}

	/**
	 * �Œ蕔�庰�ނ�Ԃ��B<BR>
	 * 
	 * @return fixedDepartment �Œ蕔�庰��
	 */
	public String getFixedDepartment() {
		return fixedDepartment;
	}

	/**
	 * �������`�[���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param adjustmentSlipInputFlag �������`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setAdjustmentSlipInputFlag(String adjustmentSlipInputFlag) {
		this.adjustmentSlipInputFlag = adjustmentSlipInputFlag;
	}

	/**
	 * �������`�[���̓t���O��Ԃ��B<BR>
	 * 
	 * @return adjustmentSlipInputFlag �������`�[���̓t���O 0:���͕s�� 1:���͉�
	 */
	public String getAdjustmentSlipInputFlag() {
		return adjustmentSlipInputFlag;
	}

	/**
	 * BG�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param bgItemCtrlDivision BG�Ȗڐ���敪 00:�ʏ� 11:�\�Z�Ǘ��Ώ�
	 */
	public void setBgItemCtrlDivision(String bgItemCtrlDivision) {
		this.bgItemCtrlDivision = bgItemCtrlDivision;
	}

	/**
	 * BG�Ȗڐ���敪��Ԃ��B<BR>
	 * 
	 * @return adjustmentSlipInputFlag BG�Ȗڐ���敪 00:�ʏ� 11:�\�Z�Ǘ��Ώ�
	 */
	public String getBgItemCtrlDivision() {
		return bgItemCtrlDivision;
	}

	/**
	 * ���E���Z����敪��ݒ肷��B<BR>
	 * 
	 * @param counterbalanceAdjustmentCtrlDivision ���E���Z����敪 0:���Ȃ� 1:����
	 */
	public void setCounterbalanceAdjustmentCtrlDivision(String counterbalanceAdjustmentCtrlDivision) {
		this.counterbalanceAdjustmentCtrlDivision = counterbalanceAdjustmentCtrlDivision;
	}

	/**
	 * ���E���Z����敪��Ԃ��B<BR>
	 * 
	 * @return counterbalanceAdjustmentCtrlDivision ���E���Z����敪 0:���Ȃ� 1:����
	 */
	public String getCounterbalanceAdjustmentCtrlDivision() {
		return counterbalanceAdjustmentCtrlDivision;
	}

	/**
	 * �`�[���t��ݒ肷��B(Date�^)
	 * 
	 * @param dateSlipDate �`�[���t
	 */
	public void setDateSlipDate(Date dateSlipDate) {
		this.dateSlipDate = dateSlipDate;
	}

	/**
	 * �`�[���t��Ԃ��B(Date�^)
	 * 
	 * @return dateSlipDate �`�[���t
	 */
	public Date getDateSlipDate() {
		return dateSlipDate;
	}

	/**
	 * �Ј����ނ�ݒ肷��B<BR>
	 * 
	 * @param employeeCode �Ј�����
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * �Ј����ނ�Ԃ��B<BR>
	 * 
	 * @return employeeCode �Ј�����
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * �I���R�[�h
	 * 
	 * @return �I���R�[�h
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * �I���R�[�h
	 * 
	 * @param endCode �I���R�[�h
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * �J�n�R�[�h
	 * 
	 * @return �J�n�R�[�h
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * �J�n�R�[�h
	 * 
	 * @param beginCode �J�n�R�[�h
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�h��ݒ肷��
	 * 
	 * @return �Ȗڑ̌n�R�[�h
	 */
	public String getItemSystemCode() {
		return itemSystemCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�h���擾����
	 * 
	 * @param itemSystemCode �Ȗڑ̌n�R�[�h
	 */
	public void setItemSystemCode(String itemSystemCode) {
		this.itemSystemCode = itemSystemCode;
	}

	/**
	 * �Ȗڑ̌n�t���O��ݒ肷��
	 * 
	 * @return itemSystemFlg
	 */
	public String getItemSystemFlg() {
		return itemSystemFlg;
	}

	/**
	 * �Ȗڑ̌n�t���O���擾����
	 * 
	 * @param itemSystemFlg
	 */
	public void setItemSystemFlg(String itemSystemFlg) {
		this.itemSystemFlg = itemSystemFlg;
	}

}
