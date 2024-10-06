package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �������
 */
public class Customer extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {

		StringBuilder sb = new StringBuilder();
		sb.append(getCode());
		sb.append(" / ");
		sb.append(Util.avoidNull(getName()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNames()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNamek()));

		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���� */
	protected String names = null;

	/** �������� */
	protected String namek = null;

	/** �����������ߓ� */
	protected int closeDay = 0;

	/** �����������ߌ㌎ */
	protected int nextMonth = 0;

	/** �������������� */
	protected int cashDay = 0;

	/** �������� */
	protected String officeName = null;

	/** �X�֔ԍ� */
	protected String zipCode = null;

	/** �Z���J�i */
	protected String addressKana = null;

	/** �Z���P */
	protected String address = null;

	/** �Z���Q */
	protected String address2 = null;

	/** EMail Address */
	protected String emailAddress = null;

	/** �d�b�ԍ� */
	protected String tel = null;

	/** FAX�ԍ� */
	protected String fax = null;

	/** �W�v�����R�[�h */
	protected String sumCode = null;

	/** �W�v����於�� */
	protected String sumName = null;

	/** �d���敪 */
	protected boolean purchase = false;

	/** ���Ӑ�敪 */
	protected boolean client = false;

	/** ������s�����R�[�h */
	protected String bankAccountCode = null;

	/** �U���˗��l�� */
	protected String clientName = null;

	/** �����萔���敪 */
	protected CashInFeeType cashInFeeType = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** �����敪 */
	protected CustomerType customerType = null;

	/** �������� */
	protected PaymentSetting paymentSetting = null;

	/** ��s���� */
	protected BankAccount bankAccount = null;

	/** ���R�[�h */
	protected String countryCode = null;

	/** �� */
	protected Country country = null;

	/** companyNo */
	protected String companyNo = null;

	/** CompanyType */
	protected String companyType = null;

	/** CHARTERER�t���O */
	protected boolean charterer = false;

	/** OWNER�t���O */
	protected boolean owner = false;

	/** PORT AGENT�t���O */
	protected boolean portAgent = false;

	/** SUPPLIER�t���O */
	protected boolean supplier = false;

	/** BROKER�t���O */
	protected boolean broker = false;

	/** BANK�t���O */
	protected boolean bank = false;

	/** OTHER�t���O */
	protected boolean other = false;

	/** Shipper �t���O */
	protected boolean shipper = false;

	/** Consignee �t���O */
	protected boolean consignee = false;

	/** Notify party �t���O */
	protected boolean notifyParty = false;

	/** Fowarder�t���O */
	protected boolean fowarder = false;

	/** Bunker Trader �t���O */
	protected boolean bunkerTrader = false;

	/** Bunker Supplier �t���O */
	protected boolean bunkerSupplier = false;

	/** Group Company �t���O */
	protected boolean groupCompany = false;

	/** �l����� */
	protected boolean isPersonalCustomer = false;

	/** ��K�i���������s���Ǝ҃t���O */
	protected boolean NO_INV_REG_FLG = false;

	/** ��K�i���������Ǝҏ���ŃR�[�h */
	protected String NO_INV_REG_ZEI_CODE = null;

	/** ��K�i���������s���Ǝҏ���Ŗ��� */
	protected String NO_INV_REG_ZEI_NAME = null;

	/** �K�i���������s���Ǝғo�^�ԍ� */
	protected String INV_REG_NO = null;

	/**
	 * ��s�������擾����B
	 * 
	 * @return BankAccount bankAccount
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * ��s������ݒ肷��B
	 * 
	 * @param bankAccount
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * paymentSetting���擾����B
	 * 
	 * @return CustomerPaymentSetting paymentSetting
	 */
	public PaymentSetting getPaymentSetting() {
		return paymentSetting;
	}

	/**
	 * paymentSetting��ݒ肷��B
	 * 
	 * @param paymentSetting
	 */
	public void setPaymentSetting(PaymentSetting paymentSetting) {
		this.paymentSetting = paymentSetting;
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
	 * @return dateFrom��߂��܂��B
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFrom��ݒ肵�܂��B
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateTo��߂��܂��B
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateTo��ݒ肵�܂��B
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return name��߂��܂��B
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name��ݒ肵�܂��B
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namek��߂��܂��B
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namek��ݒ肵�܂��B
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return names��߂��܂��B
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names names��ݒ肵�܂��B
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * ���������擾����B
	 * 
	 * @return String cashDay
	 */
	public int getCashDay() {
		return cashDay;
	}

	/**
	 * ��������ݒ肷��B
	 * 
	 * @param cashDay
	 */
	public void setCashDay(int cashDay) {
		this.cashDay = cashDay;
	}

	/**
	 * ���ߓ����擾����B
	 * 
	 * @return String closeDay
	 */
	public int getCloseDay() {
		return closeDay;
	}

	/**
	 * ���ߓ���ݒ肷��B
	 * 
	 * @param closeDay
	 */
	public void setCloseDay(int closeDay) {
		this.closeDay = closeDay;
	}

	/**
	 * ��������擾����B
	 * 
	 * @return String nextMonth
	 */
	public int getNextMonth() {
		return nextMonth;
	}

	/**
	 * �������ݒ肷��B
	 * 
	 * @param nextMonth
	 */
	public void setNextMonth(int nextMonth) {
		this.nextMonth = nextMonth;
	}

	/**
	 * �o�[���Z�o ���ߓ��A�㌎�����ɓ����\������Z�o
	 * 
	 * @param date ���
	 * @return �����\���
	 */
	public Date getBalanceDate(Date date) {

		// ����̓����擾
		int baseday = DateUtil.getDay(date);

		// 29���ȍ~�͑S�Č�������(��99)�ɕϊ�
		if (closeDay > 29) {
			closeDay = 99;
		}

		// ��r ���ߓ��𒴂������̏ꍇ�A�������
		if (closeDay < baseday) {
			date = DateUtil.addMonth(date, 1);
		}

		// �㌎�����炷
		date = DateUtil.addMonth(date, nextMonth);

		// �������� 29���ȍ~�͑S�Č�������

		int month = DateUtil.getMonth(date);
		date = DateUtil.getLastDate(DateUtil.getYear(date), DateUtil.getMonth(date));

		if (month == 2) {

			if (cashDay < 28) {
				date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
			} else if (DateUtil.getDay(date) == 29) {
				if (cashDay <= 28) {
					// 28���ȍ~�͑S�Č�������
					date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
				}
			} else if (DateUtil.getDay(date) == 28) {
				if (cashDay <= 27) {
					// 28���ȍ~�͑S�Č�������
					date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
				}
			}

			// 4,6,9,11��
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (cashDay <= 29) {
				// 29���ȍ~�͑S�Č�������
				date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
			}
			// 1,3,5,7,8,10,12��
		} else {
			if (cashDay <= 30) {
				// 30���ȍ~�͑S�Č�������
				date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);

			}

		}

		return date;
	}

	/**
	 * �����敪�̎擾
	 * 
	 * @return �����敪
	 */
	public CustomerType getCustomerType() {
		return customerType;
	}

	/**
	 * �����敪�̐ݒ�
	 * 
	 * @param customerType
	 */
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	/**
	 * ���Ə����擾����B
	 * 
	 * @return String officeName
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * ���Ə���ݒ肷��B
	 * 
	 * @param officeName
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * �Z��1���擾����B
	 * 
	 * @return String address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * �Z��1��ݒ肷��B
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * �Z��2���擾����B
	 * 
	 * @return String address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * �Z��2��ݒ肷��B
	 * 
	 * @param address2
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * �Z���J�i���擾����B
	 * 
	 * @return String addressKana
	 */
	public String getAddressKana() {
		return addressKana;
	}

	/**
	 * �Z���J�i��ݒ肷��B
	 * 
	 * @param addressKana
	 */
	public void setAddressKana(String addressKana) {
		this.addressKana = addressKana;
	}

	/**
	 * tel���擾����B
	 * 
	 * @return String tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * tel��ݒ肷��B
	 * 
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * �X�֔ԍ����擾����B
	 * 
	 * @return String zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * �X�֔ԍ���ݒ肷��B
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return sumCode
	 */
	public String getSumCode() {
		return sumCode;
	}

	/**
	 * @param sumCode
	 */
	public void setSumCode(String sumCode) {
		this.sumCode = sumCode;
	}

	/**
	 * @return sumName
	 */
	public String getSumName() {
		return sumName;
	}

	/**
	 * @param sumName
	 */
	public void setSumName(String sumName) {
		this.sumName = sumName;
	}

	/**
	 * �d���悩
	 * 
	 * @return true:�d����
	 */
	public boolean isPurchase() {
		return purchase;
	}

	/**
	 * �d���敪
	 * 
	 * @return �d���敪
	 */
	public int getPurchase() {
		if (purchase) {
			return 1;
		}
		return 0;
	}

	/**
	 * �d���敪
	 * 
	 * @param purchase �d���敪
	 */
	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}

	/**
	 * ���Ӑ悩
	 * 
	 * @return true:���Ӑ�
	 */
	public boolean isClient() {
		return client;
	}

	/**
	 * ���Ӑ�敪
	 * 
	 * @return ���Ӑ�敪
	 */
	public int getClient() {
		if (client) {
			return 1;
		}
		return 0;
	}

	/**
	 * ���Ӑ�
	 * 
	 * @param client ���Ӑ�
	 */
	public void setClient(boolean client) {
		this.client = client;
	}

	/**
	 * ������s�����R�[�h
	 * 
	 * @return ������s�����R�[�h
	 */
	public String getBankAccountCode() {
		return bankAccountCode;
	}

	/**
	 * ������s�����R�[�h
	 * 
	 * @param bankAccountCode ������s�����R�[�h
	 */
	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	/**
	 * �����萔���敪
	 * 
	 * @return �����萔���敪
	 */
	public CashInFeeType getCashInFeeType() {
		return cashInFeeType;
	}

	/**
	 * �����萔���敪
	 * 
	 * @param cashInFeeType �����萔���敪
	 */
	public void setCashInFeeType(CashInFeeType cashInFeeType) {
		this.cashInFeeType = cashInFeeType;
	}

	/**
	 * �U�荞�˗��l��
	 * 
	 * @return �U�荞�˗��l��
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * �U�荞�˗��l��
	 * 
	 * @param clientName �U�荞�˗��l��
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * ���R�[�h�̎擾
	 * 
	 * @return countryCode ���R�[�h
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * ���R�[�h�̐ݒ�
	 * 
	 * @param countryCode ���R�[�h
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * ���̎擾
	 * 
	 * @return country ��
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * ���̐ݒ�
	 * 
	 * @param country ��
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * companyNo�̎擾
	 * 
	 * @return companyNo companyNo
	 */
	public String getCompanyNo() {
		return companyNo;
	}

	/**
	 * companyNo�̐ݒ�
	 * 
	 * @param companyNo companyNo
	 */
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	/**
	 * CompanyType�̎擾
	 * 
	 * @return companyType CompanyType
	 */
	public String getCompanyType() {
		return companyType;
	}

	/**
	 * CompanyType�̐ݒ�
	 * 
	 * @param companyType CompanyType
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	/**
	 * CHARTERER�̎擾
	 * 
	 * @return charterer CHARTERER
	 */
	public boolean isCharterer() {
		return charterer;
	}

	/**
	 * CHARTERER�̐ݒ�
	 * 
	 * @param charterer CHARTERER
	 */
	public void setCharterer(boolean charterer) {
		this.charterer = charterer;
	}

	/**
	 * OWNER�̎擾
	 * 
	 * @return owner OWNER
	 */
	public boolean isOwner() {
		return owner;
	}

	/**
	 * OWNER�̐ݒ�
	 * 
	 * @param owner OWNER
	 */
	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	/**
	 * PORT_AGENT�̎擾
	 * 
	 * @return portAgent PORT_AGENT
	 */
	public boolean isPortAgent() {
		return portAgent;
	}

	/**
	 * PORT_AGENT�̐ݒ�
	 * 
	 * @param portAgent PORT_AGENT
	 */
	public void setPortAgent(boolean portAgent) {
		this.portAgent = portAgent;
	}

	/**
	 * SUPPLIER�̎擾
	 * 
	 * @return supplier SUPPLIER
	 */
	public boolean isSupplier() {
		return supplier;
	}

	/**
	 * SUPPLIER�̐ݒ�
	 * 
	 * @param supplier SUPPLIER
	 */
	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}

	/**
	 * BROKER�̎擾
	 * 
	 * @return broker BROKER
	 */
	public boolean isBroker() {
		return broker;
	}

	/**
	 * BROKER�̐ݒ�
	 * 
	 * @param broker BROKER
	 */
	public void setBroker(boolean broker) {
		this.broker = broker;
	}

	/**
	 * OTHER�̎擾
	 * 
	 * @return other OTHER
	 */
	public boolean isOther() {
		return other;
	}

	/**
	 * OTHER�̐ݒ�
	 * 
	 * @param other OTHER
	 */
	public void setOther(boolean other) {
		this.other = other;
	}

	/**
	 * EMail Address���擾����B
	 * 
	 * @return String emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * EMail Address��ݒ肷��B
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Shipper�̎擾
	 * 
	 * @return shipper Shipper
	 */
	public boolean isShipper() {
		return shipper;
	}

	/**
	 * Shipper�̐ݒ�
	 * 
	 * @param shipper Shipper
	 */
	public void setShipper(boolean shipper) {
		this.shipper = shipper;
	}

	/**
	 * Consignee�̎擾
	 * 
	 * @return consignee Consignee
	 */
	public boolean isConsignee() {
		return consignee;
	}

	/**
	 * Consignee�̐ݒ�
	 * 
	 * @param consignee Consignee
	 */
	public void setConsignee(boolean consignee) {
		this.consignee = consignee;
	}

	/**
	 * Notify party�̎擾
	 * 
	 * @return notifyParty Notify party
	 */
	public boolean isNotifyParty() {
		return notifyParty;
	}

	/**
	 * Notify party�̐ݒ�
	 * 
	 * @param notifyParty Notify party
	 */
	public void setNotifyParty(boolean notifyParty) {
		this.notifyParty = notifyParty;
	}

	/**
	 * Fowarder�̎擾
	 * 
	 * @return fowarder Fowarder
	 */
	public boolean isFowarder() {
		return fowarder;
	}

	/**
	 * Fowarder�̐ݒ�
	 * 
	 * @param fowarder Fowarder
	 */
	public void setFowarder(boolean fowarder) {
		this.fowarder = fowarder;
	}

	/**
	 * Bunker Trader�̎擾
	 * 
	 * @return bunkerTrader Bunker Trader
	 */
	public boolean isBunkerTrader() {
		return bunkerTrader;
	}

	/**
	 * Bunker Trader�̐ݒ�
	 * 
	 * @param bunkerTrader Bunker Trader
	 */
	public void setBunkerTrader(boolean bunkerTrader) {
		this.bunkerTrader = bunkerTrader;
	}

	/**
	 * Bunker Supplier�̎擾
	 * 
	 * @return bunkerSupplier Bunker Supplier
	 */
	public boolean isBunkerSupplier() {
		return bunkerSupplier;
	}

	/**
	 * Bunker Supplier�̐ݒ�
	 * 
	 * @param bunkerSupplier Bunker Supplier
	 */
	public void setBunkerSupplier(boolean bunkerSupplier) {
		this.bunkerSupplier = bunkerSupplier;
	}

	/**
	 * BANK�̎擾
	 * 
	 * @return bank
	 */
	public boolean isBank() {
		return bank;
	}

	/**
	 * BANK�̐ݒ�
	 * 
	 * @param bank
	 */
	public void setBank(boolean bank) {
		this.bank = bank;
	}

	/**
	 * Group Company�̎擾
	 * 
	 * @return groupCompany
	 */
	public boolean isGroupCompany() {
		return groupCompany;
	}

	/**
	 * Group Company�̐ݒ�
	 * 
	 * @param groupCompany
	 */
	public void setGroupCompany(boolean groupCompany) {
		this.groupCompany = groupCompany;
	}

	/**
	 * �l�������擾
	 * 
	 * @return isPersonalInfo �l����� true�̏ꍇ��s���������\����
	 */
	public boolean isPersonalCustomer() {
		return isPersonalCustomer;
	}

	/**
	 * �l�������Z�b�g����
	 * 
	 * @param isPersonalInfo isPersonalInfo
	 */
	public void setPersonalCustomer(boolean isPersonalInfo) {
		this.isPersonalCustomer = isPersonalInfo;
	}

	/** �S�������� */
	protected String CHARGE_DEP_NAME = null;

	/** �S���Җ� */
	protected String CHARGE_EMP_NAME = null;

	/** �����h�� */
	protected HonorType TRI_TITLE_TYPE = null;

	/** �S���Ҍh�� */
	protected HonorType EMP_TITLE_TYPE = null;

	/**
	 * �S�������� ���擾����B
	 * 
	 * @return String CHARGE_DEP_NAME
	 */
	public String getCHARGE_DEP_NAME() {
		return CHARGE_DEP_NAME;
	}

	/**
	 * �S�������� ��ݒ肷��B
	 * 
	 * @param CHARGE_DEP_NAME
	 */
	public void setCHARGE_DEP_NAME(String CHARGE_DEP_NAME) {
		this.CHARGE_DEP_NAME = CHARGE_DEP_NAME;
	}

	/**
	 * �S���Җ� ���擾����B
	 * 
	 * @return String CHARGE_EMP_NAME
	 */
	public String getCHARGE_EMP_NAME() {
		return CHARGE_EMP_NAME;
	}

	/**
	 * �S���Җ� ��ݒ肷��B
	 * 
	 * @param CHARGE_EMP_NAME
	 */
	public void setCHARGE_EMP_NAME(String CHARGE_EMP_NAME) {
		this.CHARGE_EMP_NAME = CHARGE_EMP_NAME;
	}

	/**
	 * �����h�� ���擾����B
	 * 
	 * @return String CHARGE_EMP_NAME
	 */
	public HonorType getTRI_TITLE_TYPE() {
		return TRI_TITLE_TYPE;
	}

	/**
	 * �����h�� ��ݒ肷��B
	 * 
	 * @param TRI_TITLE_TYPE
	 */
	public void setTRI_TITLE_TYPE(HonorType TRI_TITLE_TYPE) {
		this.TRI_TITLE_TYPE = TRI_TITLE_TYPE;
	}

	/**
	 * �S���Ҍh�� ���擾����B
	 * 
	 * @return String EMP_TITLE_TYPE
	 */
	public HonorType getEMP_TITLE_TYPE() {
		return EMP_TITLE_TYPE;
	}

	/**
	 * �S���Ҍh�� ��ݒ肷��B
	 * 
	 * @param EMP_TITLE_TYPE
	 */
	public void setEMP_TITLE_TYPE(HonorType EMP_TITLE_TYPE) {
		this.EMP_TITLE_TYPE = EMP_TITLE_TYPE;
	}

	/**
	 * ��K�i���������s���Ǝ҃t���O�̎擾
	 * 
	 * @return NO_INV_REG_FLG ��K�i���������s���Ǝ҃t���O
	 */
	public boolean isNO_INV_REG_FLG() {
		return NO_INV_REG_FLG;
	}

	/**
	 * ��K�i���������s���Ǝ҃t���O�̐ݒ�
	 * 
	 * @param NO_INV_REG_FLG ��K�i���������s���Ǝ҃t���O
	 */
	public void setNO_INV_REG_FLG(boolean NO_INV_REG_FLG) {
		this.NO_INV_REG_FLG = NO_INV_REG_FLG;
	}

	/**
	 * ��K�i���������Ǝҏ���ŃR�[�h�̎擾
	 * 
	 * @return NO_INV_REG_ZEI_CODE ��K�i���������Ǝҏ���ŃR�[�h
	 */
	public String getNO_INV_REG_ZEI_CODE() {
		return NO_INV_REG_ZEI_CODE;
	}

	/**
	 * ��K�i���������Ǝҏ���ŃR�[�h�̐ݒ�
	 * 
	 * @param NO_INV_REG_ZEI_CODE ��K�i���������Ǝҏ���ŃR�[�h
	 */
	public void setNO_INV_REG_ZEI_CODE(String NO_INV_REG_ZEI_CODE) {
		this.NO_INV_REG_ZEI_CODE = NO_INV_REG_ZEI_CODE;
	}

	/**
	 * ��K�i���������s���Ǝҏ���Ŗ��̂̎擾
	 * 
	 * @return NO_INV_REG_ZEI_NAME ��K�i���������s���Ǝҏ���Ŗ���
	 */
	public String getNO_INV_REG_ZEI_NAME() {
		return NO_INV_REG_ZEI_NAME;
	}

	/**
	 * ��K�i���������s���Ǝҏ���Ŗ��̂̐ݒ�
	 * 
	 * @param NO_INV_REG_ZEI_NAME ��K�i���������s���Ǝҏ���Ŗ���
	 */
	public void setNO_INV_REG_ZEI_NAME(String NO_INV_REG_ZEI_NAME) {
		this.NO_INV_REG_ZEI_NAME = NO_INV_REG_ZEI_NAME;
	}

	/**
	 * �K�i���������s���Ǝғo�^�ԍ��̎擾
	 * 
	 * @return INV_REG_NO �K�i���������s���Ǝғo�^�ԍ�
	 */
	public String getINV_REG_NO() {
		return INV_REG_NO;
	}

	/**
	 * �K�i���������s���Ǝғo�^�ԍ��̐ݒ�
	 * 
	 * @param INV_REG_NO �K�i���������s���Ǝғo�^�ԍ�
	 */
	public void setINV_REG_NO(String INV_REG_NO) {
		this.INV_REG_NO = INV_REG_NO;
	}

}
