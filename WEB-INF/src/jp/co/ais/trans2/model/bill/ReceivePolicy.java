package jp.co.ais.trans2.model.bill;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �������j�}�X�^���
 * 
 * @author AIS
 */
public class ReceivePolicy extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** ���̓t�H���_�� */
	protected String inFolder = null;

	/** �ޔ��t�H���_�� */
	protected String outFolder = null;

	/** ���R�[�h�� */
	protected int length = 0;

	/** CR/LF�敪 */
	protected String lineType = null;

	/** �萔���͈͊J�n���z */
	protected BigDecimal feeFrom = null;

	/** �萔���͈͏I�����z */
	protected BigDecimal feeTo = null;

	/** �萔���ȖڃR�[�h */
	protected String itemCode = null;

	/** �萔���⏕�ȖڃR�[�h */
	protected String subItemCode = null;

	/** �萔������ȖڃR�[�h */
	protected String detailCode = null;

	/** �萔���v�㕔��R�[�h */
	protected String departmentCode = null;

	/** �萔������ŃR�[�h */
	protected String taxCode = null;

	/** �������ԍ������׸� */
	protected boolean compulsoryInputBillNo = false;

	/** �������쐬�׸� */
	protected boolean checkBillMake = false;

	/** �U�������ʒm�t�H�[�}�b�g�^�C�v */
	protected String format = null;

	/** �o�^���t */
	protected Date inpDate = null;

	/** �X�V���t */
	protected Date updDate = null;

	/** �v���O����ID */
	protected String prgId = null;

	/** ���[�U�[ID */
	protected String usrId = null;

	/** ����ŏ�� */
	protected ConsumptionTax tax = null;

	/** �������ԍ� �����̔Ԃ��g�p���邩 */
	protected boolean useInvNumbering = false;

	/** �����̔ԕ����� */
	protected int invNoDigit = 0;

	/** �����̔ԍ���1 */
	protected InvoiceNoAdopt invNoAdopt1 = null;

	/** �����̔ԍ���2 */
	protected InvoiceNoAdopt invNoAdopt2 = null;

	/** �����̔ԍ���3 */
	protected InvoiceNoAdopt invNoAdopt3 = null;

	/** ����������1���� */
	protected String invNo1Name = null;

	/** ����������2���� */
	protected String invNo2Name = null;

	/** ����������3���� */
	protected String invNo3Name = null;

	/**
	 * checkBillMake���擾����B
	 * 
	 * @return boolean checkBillMake
	 */
	public boolean isCheckBillMake() {
		return checkBillMake;
	}

	/**
	 * checkBillMake��ݒ肷��B
	 * 
	 * @param checkBillMake
	 */
	public void setCheckBillMake(boolean checkBillMake) {
		this.checkBillMake = checkBillMake;
	}

	/**
	 * companyCode���擾����B
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * departmentCode���擾����B
	 * 
	 * @return String departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * departmentCode��ݒ肷��B
	 * 
	 * @param departmentCode
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * detailCode���擾����B
	 * 
	 * @return String detailCode
	 */
	public String getDetailCode() {
		return detailCode;
	}

	/**
	 * detailCode��ݒ肷��B
	 * 
	 * @param detailCode
	 */
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	/**
	 * feeFrom���擾����B
	 * 
	 * @return BigDecimal feeFrom
	 */
	public BigDecimal getFeeFrom() {
		return feeFrom;
	}

	/**
	 * feeFrom��ݒ肷��B
	 * 
	 * @param feeFrom
	 */
	public void setFeeFrom(BigDecimal feeFrom) {
		this.feeFrom = feeFrom;
	}

	/**
	 * feeTo���擾����B
	 * 
	 * @return BigDecimal feeTo
	 */
	public BigDecimal getFeeTo() {
		return feeTo;
	}

	/**
	 * feeTo��ݒ肷��B
	 * 
	 * @param feeTo
	 */
	public void setFeeTo(BigDecimal feeTo) {
		this.feeTo = feeTo;
	}

	/**
	 * format���擾����B
	 * 
	 * @return String format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * format��ݒ肷��B
	 * 
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * inFolder���擾����B
	 * 
	 * @return String inFolder
	 */
	public String getInFolder() {
		return inFolder;
	}

	/**
	 * inFolder��ݒ肷��B
	 * 
	 * @param inFolder
	 */
	public void setInFolder(String inFolder) {
		this.inFolder = inFolder;
	}

	/**
	 * inpDate���擾����B
	 * 
	 * @return Date inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * inpDate��ݒ肷��B
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * compulsoryInputBillNo���擾����B
	 * 
	 * @return boolean compulsoryInputBillNo
	 */
	public boolean isCompulsoryInputBillNo() {
		return compulsoryInputBillNo;
	}

	/**
	 * compulsoryInputBillNo��ݒ肷��B
	 * 
	 * @param compulsoryInputBillNo
	 */
	public void setCompulsoryInputBillNo(boolean compulsoryInputBillNo) {
		this.compulsoryInputBillNo = compulsoryInputBillNo;
	}

	/**
	 * itemCode���擾����B
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCode��ݒ肷��B
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * length���擾����B
	 * 
	 * @return String length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * length��ݒ肷��B
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * lineType���擾����B
	 * 
	 * @return String lineType
	 */
	public String getLineType() {
		return lineType;
	}

	/**
	 * lineType��ݒ肷��B
	 * 
	 * @param lineType
	 */
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	/**
	 * outFolder���擾����B
	 * 
	 * @return String outFolder
	 */
	public String getOutFolder() {
		return outFolder;
	}

	/**
	 * outFolder��ݒ肷��B
	 * 
	 * @param outFolder
	 */
	public void setOutFolder(String outFolder) {
		this.outFolder = outFolder;
	}

	/**
	 * prgId���擾����B
	 * 
	 * @return String prgId
	 */
	public String getPrgId() {
		return prgId;
	}

	/**
	 * prgId��ݒ肷��B
	 * 
	 * @param prgId
	 */
	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}

	/**
	 * subItemCode���擾����B
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCode��ݒ肷��B
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * taxCode���擾����B
	 * 
	 * @return String taxCode
	 */
	public String getTaxCode() {
		return taxCode;
	}

	/**
	 * taxCode��ݒ肷��B
	 * 
	 * @param taxCode
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	/**
	 * updDate���擾����B
	 * 
	 * @return Date updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * updDate��ݒ肷��B
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * usrId���擾����B
	 * 
	 * @return String usrId
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * usrId��ݒ肷��B
	 * 
	 * @param usrId
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	/**
	 * tax���擾����B
	 * 
	 * @return ConsumptionTax tax
	 */
	public ConsumptionTax getTax() {
		return tax;
	}

	/**
	 * tax��ݒ肷��B
	 * 
	 * @param tax
	 */
	public void setTax(ConsumptionTax tax) {
		this.tax = tax;
	}

	/**
	 * @return useInvNumbering��߂��܂��B
	 */
	public boolean isInvNumbering() {
		return useInvNumbering;
	}

	/**
	 * @param useInvNumbering useInvNumbering��ݒ肵�܂��B
	 */
	public void setInvNumbering(boolean useInvNumbering) {
		this.useInvNumbering = useInvNumbering;
	}

	/**
	 * @param invNoDigit invNoDigit��ݒ肵�܂��B
	 */
	public void setInvNoDigit(int invNoDigit) {
		this.invNoDigit = invNoDigit;
	}

	/**
	 * @return invNoDigit��߂��܂��B
	 */
	public int getInvNoDigit() {
		return invNoDigit;
	}

	/**
	 * ����������1����
	 * 
	 * @return ����������1����
	 */
	public String getInvNo1Name() {
		return invNo1Name;
	}

	/**
	 * ����������1����
	 * 
	 * @param invNo1Name ���������� 1����
	 */
	public void setInvNo1Name(String invNo1Name) {
		this.invNo1Name = invNo1Name;
	}

	/**
	 * ����������2����
	 * 
	 * @return ����������2����
	 */
	public String getInvNo2Name() {
		return invNo2Name;
	}

	/**
	 * ����������2����
	 * 
	 * @param invNo2Name ���������� 2����
	 */
	public void setInvNo2Name(String invNo2Name) {
		this.invNo2Name = invNo2Name;
	}

	/**
	 * ����������3����
	 * 
	 * @return ����������3����
	 */
	public String getInvNo3Name() {
		return invNo3Name;
	}

	/**
	 * ����������3����
	 * 
	 * @param invNo3Name ���������� 3����
	 */
	public void setInvNo3Name(String invNo3Name) {
		this.invNo3Name = invNo3Name;
	}

	/**
	 * �����̔ԍ���1
	 * 
	 * @return �����̔ԍ���1
	 */
	public InvoiceNoAdopt getInvoiceNoAdopt1() {
		return invNoAdopt1;
	}

	/**
	 * �����̔ԍ���1
	 * 
	 * @param invNoAdopt1 �����̔ԍ���1
	 */
	public void setInvoiceNoAdopt1(InvoiceNoAdopt invNoAdopt1) {
		this.invNoAdopt1 = invNoAdopt1;
	}

	/**
	 * �����̔ԍ���2
	 * 
	 * @return �����̔ԍ���2
	 */
	public InvoiceNoAdopt getInvoiceNoAdopt2() {
		return invNoAdopt2;
	}

	/**
	 * �����̔ԍ���2
	 * 
	 * @param invNoAdopt2 �����̔ԍ���2
	 */
	public void setInvoiceNoAdopt2(InvoiceNoAdopt invNoAdopt2) {
		this.invNoAdopt2 = invNoAdopt2;
	}

	/**
	 * �����̔ԍ���3
	 * 
	 * @return �����̔ԍ���3
	 */
	public InvoiceNoAdopt getInvoiceNoAdopt3() {
		return invNoAdopt3;
	}

	/**
	 * �����̔ԍ���3
	 * 
	 * @param invNoAdopt3 �����̔ԍ���3
	 */
	public void setInvoiceNoAdopt3(InvoiceNoAdopt invNoAdopt3) {
		this.invNoAdopt3 = invNoAdopt3;
	}
}
