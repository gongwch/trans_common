package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * �����}�X�^��������
 */
public class CustomerSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �W�v�R�[�h���������邩 */
	protected boolean searchSumCode = false;

	/** �W�v���̂������p�t���O */
	protected boolean searchSumFlg = false;

	/** �����敪 */
	private CustomerType type;

	/** �����敪:�����̎��A�ǂ�����`�F�b�N����Ă�����̂̂ݒ��o���邩 */
	protected boolean customerTypeBoth = false;

	/** �����R�[�h(����) */
	protected List<String> customerCodeList = new LinkedList<String>();

	/** �W�v�敪 */
	protected CustomerSumType customerSumType;

	/** CHARTERER�t���O */
	protected Boolean charterer = null;

	/** OWNER�t���O */
	protected Boolean owner = null;

	/** PORT AGENT�t���O */
	protected Boolean portAgent = null;

	/** SUPPLIER�t���O */
	protected Boolean supplier = null;

	/** BROKER�t���O */
	protected Boolean broker = null;

	/** BANK�t���O */
	protected Boolean bank = null;

	/** OTHER�t���O */
	protected Boolean other = null;

	/** Shipper�t���O */
	protected Boolean shipper = null;

	/** Consignee�t���O */
	protected Boolean consignee = null;

	/** Notify party�t���O */
	protected Boolean notifyParty = null;

	/** Fowarder�t���O */
	protected Boolean fowarder = null;

	/** Bunker Trader�t���O */
	protected Boolean bunkerTrader = null;

	/** Bunker Supplier�t���O */
	protected Boolean bunkerSupplier = null;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/** �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p) */
	protected List<String> companyCodeList = null;

	/** �C���{�C�X���x�t���O */
	protected boolean invoiceFlg = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CustomerSearchCondition clone() {
		try {
			return (CustomerSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return code��߂��܂��B
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code code��ݒ肵�܂��B
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return codeFrom��߂��܂��B
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFrom��ݒ肵�܂��B
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeTo��߂��܂��B
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeTo��ݒ肵�܂��B
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return companyCode��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCode��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return validTerm��߂��܂��B
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTerm��ݒ肵�܂��B
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * @return �����܂�
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return �����܂�
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return �����܂�
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * �����^�C�v
	 * 
	 * @param type �^�C�v
	 */
	public void setType(CustomerType type) {
		this.type = type;
	}

	/**
	 * �����^�C�v
	 * 
	 * @return type �����^�C�v
	 */
	public CustomerType getType() {
		return type;
	}

	/**
	 * �����敪:�����K�{���ǂ����̔���
	 * 
	 * @return true:�����K�{
	 */
	public boolean isCustomerTypeBoth() {
		return customerTypeBoth;
	}

	/**
	 * �����敪:�����K�{�̐ݒ�
	 * 
	 * @param customerTypeBoth
	 */
	public void setCustomerTypeBoth(boolean customerTypeBoth) {
		this.customerTypeBoth = customerTypeBoth;
	}

	/**
	 * searchSumCode���擾����B
	 * 
	 * @return boolean searchSumCode
	 */
	public boolean isSearchSumCode() {
		return searchSumCode;
	}

	/**
	 * searchSumCode��ݒ肷��B
	 * 
	 * @param searchSumCode
	 */
	public void setSearchSumCode(boolean searchSumCode) {
		this.searchSumCode = searchSumCode;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return searchSumFlg
	 */
	public boolean isSearchSumFlg() {
		return searchSumFlg;
	}

	/**
	 * @param searchSumFlg
	 */
	public void setSearchSumFlg(boolean searchSumFlg) {
		this.searchSumFlg = searchSumFlg;
	}

	/**
	 * �����R�[�h(����)�̎擾
	 * 
	 * @return �����R�[�h(����)
	 */
	public List<String> getCodeList() {
		return this.customerCodeList;
	}

	/**
	 * �����R�[�h(����)�̎擾
	 * 
	 * @return �����R�[�h(����)
	 */
	public String[] getCustomerCodeList() {
		if (customerCodeList.isEmpty()) {
			return null;
		}

		return customerCodeList.toArray(new String[customerCodeList.size()]);
	}

	/**
	 * �����R�[�h(����)�̐ݒ�
	 * 
	 * @param customerCodeList �����R�[�h(����)
	 */
	public void setCustomerCodeList(String[] customerCodeList) {
		addCustomerCode(customerCodeList);
	}

	/**
	 * �����R�[�h(����)�̐ݒ�
	 * 
	 * @param list �����R�[�h(����)
	 */
	public void addCustomerCode(String... list) {
		for (String customerCode : list) {
			this.customerCodeList.add(customerCode);
		}
	}

	/**
	 * �����R�[�h(����)�̃N���A
	 */
	public void clearCustomerCodeList() {
		customerCodeList.clear();
	}

	/**
	 * �W�v�敪�̎擾
	 * 
	 * @return �W�v�敪
	 */
	public CustomerSumType getCustomerSumType() {
		return customerSumType;
	}

	/**
	 * �W�v�敪�̐ݒ�
	 * 
	 * @param sumType
	 */
	public void setCustomerSumType(CustomerSumType sumType) {
		this.customerSumType = sumType;
	}

	/**
	 * CHARTERER�̎擾
	 * 
	 * @return charterer CHARTERER
	 */
	public Boolean isCharterer() {
		return charterer;
	}

	/**
	 * CHARTERER�̐ݒ�
	 * 
	 * @param charterer CHARTERER
	 */
	public void setCharterer(Boolean charterer) {
		this.charterer = charterer;
	}

	/**
	 * OWNER�̎擾
	 * 
	 * @return owner OWNER
	 */
	public Boolean isOwner() {
		return owner;
	}

	/**
	 * OWNER�̐ݒ�
	 * 
	 * @param owner OWNER
	 */
	public void setOwner(Boolean owner) {
		this.owner = owner;
	}

	/**
	 * PORT_AGENT�̎擾
	 * 
	 * @return portAgent PORT_AGENT
	 */
	public Boolean isPortAgent() {
		return portAgent;
	}

	/**
	 * PORT_AGENT�̐ݒ�
	 * 
	 * @param portAgent PORT_AGENT
	 */
	public void setPortAgent(Boolean portAgent) {
		this.portAgent = portAgent;
	}

	/**
	 * SUPPLIER�̎擾
	 * 
	 * @return supplier SUPPLIER
	 */
	public Boolean isSupplier() {
		return supplier;
	}

	/**
	 * SUPPLIER�̐ݒ�
	 * 
	 * @param supplier SUPPLIER
	 */
	public void setSupplier(Boolean supplier) {
		this.supplier = supplier;
	}

	/**
	 * BROKER�̎擾
	 * 
	 * @return broker BROKER
	 */
	public Boolean isBroker() {
		return broker;
	}

	/**
	 * BROKER�̐ݒ�
	 * 
	 * @param broker BROKER
	 */
	public void setBroker(Boolean broker) {
		this.broker = broker;
	}

	/**
	 * BANK�̎擾
	 * 
	 * @return bank BANK
	 */
	public Boolean isBank() {
		return bank;
	}

	/**
	 * BANK�̐ݒ�
	 * 
	 * @param bank BANK
	 */
	public void setBank(Boolean bank) {
		this.bank = bank;
	}

	/**
	 * OTHER�̎擾
	 * 
	 * @return other OTHER
	 */
	public Boolean isOther() {
		return other;
	}

	/**
	 * OTHER�̐ݒ�
	 * 
	 * @param other OTHER
	 */
	public void setOther(Boolean other) {
		this.other = other;
	}

	/**
	 * Shipper�̎擾
	 * 
	 * @return shipper Shipper
	 */
	public Boolean isShipper() {
		return shipper;
	}

	/**
	 * Shipper�̐ݒ�
	 * 
	 * @param shipper Shipper
	 */
	public void setShipper(Boolean shipper) {
		this.shipper = shipper;
	}

	/**
	 * Consignee�̎擾
	 * 
	 * @return consignee Consignee
	 */
	public Boolean isConsignee() {
		return consignee;
	}

	/**
	 * Consignee�̐ݒ�
	 * 
	 * @param consignee Consignee
	 */
	public void setConsignee(Boolean consignee) {
		this.consignee = consignee;
	}

	/**
	 * Notify party�̎擾
	 * 
	 * @return notifyParty Notify party
	 */
	public Boolean isNotifyParty() {
		return notifyParty;
	}

	/**
	 * Notify party�̐ݒ�
	 * 
	 * @param notifyParty Notify party
	 */
	public void setNotifyParty(Boolean notifyParty) {
		this.notifyParty = notifyParty;
	}

	/**
	 * Fowarder�̎擾
	 * 
	 * @return fowarder Fowarder
	 */
	public Boolean isFowarder() {
		return fowarder;
	}

	/**
	 * Fowarder�̐ݒ�
	 * 
	 * @param fowarder Fowarder
	 */
	public void setFowarder(Boolean fowarder) {
		this.fowarder = fowarder;
	}

	/**
	 * Bunker Trader�̎擾
	 * 
	 * @return bunkerTrader Bunker Trader
	 */
	public Boolean isBunkerTrader() {
		return bunkerTrader;
	}

	/**
	 * Bunker Trader�̐ݒ�
	 * 
	 * @param bunkerTrader Bunker Trader
	 */
	public void setBunkerTrader(Boolean bunkerTrader) {
		this.bunkerTrader = bunkerTrader;
	}

	/**
	 * Bunker Supplier�̎擾
	 * 
	 * @return bunkerSupplier Bunker Supplier
	 */
	public Boolean isBunkerSupplier() {
		return bunkerSupplier;
	}

	/**
	 * Bunker Supplier�̐ݒ�
	 * 
	 * @param bunkerSupplier Bunker Supplier
	 */
	public void setBunkerSupplier(Boolean bunkerSupplier) {
		this.bunkerSupplier = bunkerSupplier;
	}

	/**
	 * �ŏI�X�V�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI�X�V����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI�X�V�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI�X�V����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * ���݌����̎擾
	 * 
	 * @return nowCount ���݌���
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * ���݌����̐ݒ�
	 * 
	 * @param nowCount ���݌���
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

	/**
	 * �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)�̎擾
	 * 
	 * @return companyCodeList �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)�̐ݒ�
	 * 
	 * @param companyCodeList �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}

	/**
	 * �C���{�C�X���x�t���O�̎擾
	 * 
	 * @return invoiceFlg �C���{�C�X���x�t���O
	 */
	public boolean isInvoiceFlg() {
		return invoiceFlg;
	}

	/**
	 * �C���{�C�X���x�t���O�̐ݒ�
	 * 
	 * @param invoiceFlg �C���{�C�X���x�t���O
	 */
	public void setInvoiceFlg(boolean invoiceFlg) {
		this.invoiceFlg = invoiceFlg;
	}

}
