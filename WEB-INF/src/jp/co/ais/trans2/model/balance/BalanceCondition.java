package jp.co.ais.trans2.model.balance;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;

/**
 * �c����������
 */
public class BalanceCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** �����R�[�h�i�J�n�j */
	protected String clientCodeFrom;

	/** �����R�[�h�i�I���j */
	protected String clientCodeTo;

	/** ����R�[�h�i�J�n�j */
	protected String departmentCodeFrom;

	/** ����R�[�h�i�I���j */
	protected String departmentCodeTo;

	/** �����/�x����R�[�h�i���C�N�j */
	protected String customerNamesLike;

	/** ����/�v��`�[�ԍ��i�O����v�j */
	protected String slipNoLike;

	/** �������ԍ��i�O����v�j */
	protected String billNoLike;

	/** ����/�x���\��� */
	protected Date expectedDay;

	/** ����/�v��`�[�ԍ� */
	protected String slipNo;

	/** �`�[�s�ԍ� */
	protected int slipLineNo = -1;

	/** ������/�v��� */
	protected Date slipDate;

	/** �v���O����ID */
	protected String programCode;

	/** ���[�U�[ID */
	protected String userCode;

	/** �X�V���t */
	protected Date updateDate;

	/** �ΏۂƂ��Ċ܂߂Ȃ������`�[�ԍ� */
	protected String notIncledEraseSlipNo;

	/** �����`�[���t */
	protected Date eraseSlipDate;

	/** �����`�[�ԍ� */
	protected String eraseSlipNo;

	/** �����`�[�s�ԍ� */
	protected int eraseSlipLineNo = -1;

	/** �ʉ݃R�[�h */
	protected String currencyCode;

	/** ����/�v��`�[�ԍ�(�����w��p) */
	protected List<String> slipNoList = new LinkedList<String>();

	/** �������z(�M��) */
	protected BigDecimal kin;

	/** �s�ԍ����X�g(�`�[�ԍ����X�g�ƃy�A) */
	protected List<Integer> rowNoList = new ArrayList<Integer>();

	/** �v�㕔��i���C�N�j */
	protected String departmentNameLike;

	/** �`�[�E�v�i���C�N�j */
	protected String slipTekLike;

	/**
	 * �N���[��
	 */
	@Override
	public BalanceCondition clone() {
		try {
			return (BalanceCondition) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * LIKE���ϊ����������ɃR���o�[�g
	 * 
	 * @return �����N���X
	 */
	protected BalanceCondition toSQL() {
		BalanceCondition clone = this.clone();
		clone.setCustomerNamesLike(SQLUtil.getLikeStatement(customerNamesLike, SQLUtil.NCHAR_AMBI));
		clone.setSlipNoLike(SQLUtil.getLikeStatement(slipNoLike, SQLUtil.CHAR_FRONT));
		clone.setBillNoLike(SQLUtil.getLikeStatement(billNoLike, SQLUtil.NCHAR_AMBI));

		clone.setDepartmentNameLike(SQLUtil.getLikeStatement(departmentNameLike, SQLUtil.NCHAR_AMBI));
		clone.setSlipTekLike(SQLUtil.getLikeStatement(slipTekLike, SQLUtil.NCHAR_AMBI));

		return clone;
	}

	/**
	 * �������ԍ��i�O����v�j
	 * 
	 * @return �������ԍ��i�O����v�j
	 */
	public String getBillNoLike() {
		return billNoLike;
	}

	/**
	 * �������ԍ��i�O����v�j
	 * 
	 * @param billCodeLike �������ԍ��i�O����v�j
	 */
	public void setBillNoLike(String billCodeLike) {
		this.billNoLike = billCodeLike;
	}

	/**
	 * ������/�v���
	 * 
	 * @return ������/�v���
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * ������/�v���
	 * 
	 * @param slipDate ������/�v���
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �����R�[�h�i�J�n�j
	 * 
	 * @return �����R�[�h�i�J�n�j
	 */
	public String getClientCodeFrom() {
		return clientCodeFrom;
	}

	/**
	 * �����R�[�h�i�J�n�j
	 * 
	 * @param customerCodeFrom �����R�[�h�i�J�n�j
	 */
	public void setClientCodeFrom(String customerCodeFrom) {
		this.clientCodeFrom = customerCodeFrom;
	}

	/**
	 * �����R�[�h�i�I���j
	 * 
	 * @return �����R�[�h�i�I���j
	 */
	public String getClientCodeTo() {
		return clientCodeTo;
	}

	/**
	 * �����R�[�h�i�I���j
	 * 
	 * @param customerCodeTo �����R�[�h�i�I���j
	 */
	public void setClientCodeTo(String customerCodeTo) {
		this.clientCodeTo = customerCodeTo;
	}

	/**
	 * �����/�x���旪�́i�B���j
	 * 
	 * @return �����/�x���旪�́i�B���j
	 */
	public String getCustomerNamesLike() {
		return customerNamesLike;
	}

	/**
	 * �����/�x���旪�́i�B���j
	 * 
	 * @param customerNamesLike �����/�x���旪�́i�B���j
	 */
	public void setCustomerNamesLike(String customerNamesLike) {
		this.customerNamesLike = customerNamesLike;
	}

	/**
	 * ����R�[�h�i�J�n�j
	 * 
	 * @return ����R�[�h�i�J�n�j
	 */
	public String getDepartmentCodeFrom() {
		return departmentCodeFrom;
	}

	/**
	 * ����R�[�h�i�J�n�j
	 * 
	 * @param departmentCodeFrom ����R�[�h�i�J�n�j
	 */
	public void setDepartmentCodeFrom(String departmentCodeFrom) {
		this.departmentCodeFrom = departmentCodeFrom;
	}

	/**
	 * ����R�[�h�i�I���j
	 * 
	 * @return ����R�[�h�i�I���j
	 */
	public String getDepartmentCodeTo() {
		return departmentCodeTo;
	}

	/**
	 * ����R�[�h�i�I���j
	 * 
	 * @param departmentCodeTo ����R�[�h�i�I���j
	 */
	public void setDepartmentCodeTo(String departmentCodeTo) {
		this.departmentCodeTo = departmentCodeTo;
	}

	/**
	 * �����\���
	 * 
	 * @return �����\���
	 */
	public Date getExpectedDay() {
		return expectedDay;
	}

	/**
	 * �����\���
	 * 
	 * @param paymentExpectedDay �����\���
	 */
	public void setExpectedDay(Date paymentExpectedDay) {
		this.expectedDay = paymentExpectedDay;
	}

	/**
	 * �����`�[�ԍ��i�O����v�j
	 * 
	 * @return �����`�[�ԍ��i�O����v�j
	 */
	public String getSlipNoLike() {
		return slipNoLike;
	}

	/**
	 * �����`�[�ԍ��i�O����v�j
	 * 
	 * @param slipNoLike �����`�[�ԍ��i�O����v�j
	 */
	public void setSlipNoLike(String slipNoLike) {
		this.slipNoLike = slipNoLike;
	}

	/**
	 * �`�[�s�ԍ�
	 * 
	 * @return �`�[�s�ԍ�
	 */
	public int getSlipLineNo() {
		return slipLineNo;
	}

	/**
	 * �`�[�s�ԍ�
	 * 
	 * @param slipLineNo �`�[�s�ԍ�
	 */
	public void setSlipLineNo(int slipLineNo) {
		this.slipLineNo = slipLineNo;
	}

	/**
	 * �`�[�ԍ�
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * �`�[�ԍ�
	 * 
	 * @param slipNo �`�[�ԍ�
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * �v���O����ID
	 * 
	 * @return �v���O����ID
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * �v���O����ID
	 * 
	 * @param programCode �v���O����ID
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * �X�V���t
	 * 
	 * @return �X�V���t
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * �X�V���t
	 * 
	 * @param updateDate �X�V���t
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * ���[�U�[ID
	 * 
	 * @return ���[�U�[ID
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ���[�U�[ID
	 * 
	 * @param userCode ���[�U�[ID
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * �x�����ѓ�
	 * 
	 * @return �x�����ѓ�
	 */
	public Date getEraseSlipDate() {
		return eraseSlipDate;
	}

	/**
	 * �x�����ѓ�
	 * 
	 * @param paymentSlipDate �x�����ѓ�
	 */
	public void setEraseSlipDate(Date paymentSlipDate) {
		this.eraseSlipDate = paymentSlipDate;
	}

	/**
	 * �ΏۂƂ��Ċ܂߂�����`�[�ԍ�
	 * 
	 * @return �ΏۂƂ��Ċ܂߂�����`�[�ԍ�
	 */
	public String getNotIncledEraseSlipNo() {
		return notIncledEraseSlipNo;
	}

	/**
	 * �ΏۂƂ��Ċ܂߂�����`�[�ԍ�
	 * 
	 * @param incledEraseSlipNo �ΏۂƂ��Ċ܂߂�����`�[�ԍ�
	 */
	public void setNotIncledEraseSlipNo(String incledEraseSlipNo) {
		this.notIncledEraseSlipNo = incledEraseSlipNo;
	}

	/**
	 * �����`�[�ԍ�
	 * 
	 * @return �����`�[�ԍ�
	 */
	public String getEraseSlipNo() {
		return eraseSlipNo;
	}

	/**
	 * �����`�[�ԍ�
	 * 
	 * @param eraseSlipNo �����`�[�ԍ�
	 */
	public void setEraseSlipNo(String eraseSlipNo) {
		this.eraseSlipNo = eraseSlipNo;
	}

	/**
	 * �����`�[�s�ԍ�
	 * 
	 * @return �����`�[�s�ԍ�
	 */
	public int getEraseSlipLineNo() {
		return eraseSlipLineNo;
	}

	/**
	 * �����`�[�s�ԍ�
	 * 
	 * @param eraseSlipLineNo �����`�[�s�ԍ�
	 */
	public void setEraseSlipLineNo(int eraseSlipLineNo) {
		this.eraseSlipLineNo = eraseSlipLineNo;
	}

	/**
	 * �ʉ݃R�[�h���擾����B
	 * 
	 * @return String currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * �ʉ݃R�[�h��ݒ肷��B
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * �������z(�M��)
	 * 
	 * @param kin �������z(�M��)
	 */
	public void setKin(BigDecimal kin) {
		this.kin = kin;
	}

	/**
	 * �������z(�M��)
	 * 
	 * @return kin �������z(�M��)
	 */
	public BigDecimal getKin() {
		return kin;
	}

	/**
	 * ����/�v��`�[�ԍ�(�����w��p)
	 * 
	 * @return ����/�v��`�[�ԍ����X�g
	 */
	public List<String> getSlipNoList() {
		return slipNoList;
	}

	/**
	 * ����/�v��`�[�ԍ�(�����w��p)
	 * 
	 * @param slipNoList ����/�v��`�[�ԍ����X�g
	 */
	public void setSlipNoList(List<String> slipNoList) {
		this.slipNoList = slipNoList;
	}

	/**
	 * ����/�v��`�[�ԍ����X�g�ւ̒ǉ�(�����w��p)
	 * 
	 * @param slipNo_ ����/�v��`�[�ԍ�
	 */
	public void addSlipNo(String slipNo_) {
		this.slipNoList.add(slipNo_);
	}

	/**
	 * �s�ԍ����X�g�̎擾
	 * 
	 * @return rowNo �s�ԍ����X�g
	 */
	public List<Integer> getRowNoList() {
		return rowNoList;
	}

	/**
	 * �`�[�ԍ��̒ǉ�
	 * 
	 * @param slipNo1 �`�[�ԍ�
	 * @param rowNo1 �s�ԍ�
	 */
	public void add(String slipNo1, Integer rowNo1) {
		this.slipNoList.add(slipNo1);
		this.rowNoList.add(rowNo1);
	}

	/**
	 * �`�[�ԍ��A�s�ԍ��̃N���A
	 */
	public void clear() {
		this.slipNoList.clear();
		this.rowNoList.clear();
	}
	
	/**
	 * �v�㕔��i���C�N�j
	 * 
	 * @return �v�㕔��i���C�N�j
	 */
	public String getDepartmentNameLike() {
		return departmentNameLike;
	}

	/**
	 * �v�㕔��i���C�N�j
	 * 
	 * @param departmentNameLike �v�㕔��i���C�N�j
	 */
	public void setDepartmentNameLike(String departmentNameLike) {
		this.departmentNameLike = departmentNameLike;
	}

	/**
	 * �`�[�E�v�i���C�N�j
	 * 
	 * @return �`�[�E�v�i���C�N�j
	 */
	public String getSlipTekLike() {
		return slipTekLike;
	}

	/**
	 * �`�[�E�v�i���C�N�j
	 * 
	 * @param slipTekLike �`�[�E�v�i���C�N�j
	 */
	public void setSlipTekLike(String slipTekLike) {
		this.slipTekLike = slipTekLike;
	}
}
