package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �Ȗڏ��
 */
public class Item extends TransferBase implements Cloneable, TReferable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6334305355484348314L;

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

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** �Œ蕔��R�[�h */
	protected String fixedDepartmentCode = null;

	/** �Œ蕔�喼�� */
	protected String fixedDepartmentName = null;

	/** �⏕�Ȗڂ����� */
	protected boolean hasSubItem = false;

	/** �⏕�Ȗ� */
	protected SubItem subItem = null;

	/** �����敪 */
	protected CustomerType clientType = null;

	/** �Ј����̓t���O */
	protected boolean useEmployee = false;

	/** �Ǘ��P���̓t���O */
	protected boolean useManagement1 = false;

	/** �Ǘ��Q���̓t���O */
	protected boolean useManagement2 = false;

	/** �Ǘ��R���̓t���O */
	protected boolean useManagement3 = false;

	/** �Ǘ��S���̓t���O */
	protected boolean useManagement4 = false;

	/** �Ǘ��T���̓t���O */
	protected boolean useManagement5 = false;

	/** �Ǘ��U���̓t���O */
	protected boolean useManagement6 = false;

	/** ���v1���̓t���O */
	protected boolean useNonAccount1 = false;

	/** ���v2���̓t���O */
	protected boolean useNonAccount2 = false;

	/** ���v3���̓t���O */
	protected boolean useNonAccount3 = false;

	/** ����ېœ��̓t���O */
	protected boolean useSalesTaxation = false;

	/** �d���ېœ��̓t���O */
	protected boolean usePurchaseTaxation = false;

	/** ���ʉݓ��̓t���O */
	protected boolean useForeignCurrency = false;

	/** �]���֑Ώۃt���O */
	protected boolean doesCurrencyEvaluate = false;

	/** ����� */
	protected ConsumptionTax consumptionTax = null;

	/** �W�v�敪 */
	protected ItemSumType itemSumType = null;

	/** �ݎ؋敪 */
	protected Dc dc = null;

	/** �Ȗڎ�� */
	protected ItemType itemType = null;

	/** �f�k�Ȗڐ���敪 */
	protected GLType glType = null;

	/** �`�o�Ȗڐ���敪 */
	protected APType apType = null;

	/** �`�q�Ȗڐ���敪 */
	protected ARType arType = null;

	/** �a�f�Ȗڐ���敪 */
	protected BGType bgType = null;

	/** ���E�Ȗڐ���敪 */
	protected boolean doesOffsetItem = false;

	/** BS��������敪 */
	protected boolean doesBsOffset = false;

	/** �]���֑Ώۃt���O */
	protected EvaluationMethod evaluationMethod = null;

	/** �����`�[���̓t���O */
	protected boolean useInputCashFlowSlip = false;

	/** �o���`�[���̓t���O */
	protected boolean useOutputCashFlowSlip = false;

	/** �U�֓`�[���̓t���O */
	protected boolean useTransferSlip = false;

	/** �o��Z�`�[���̓t���O */
	protected boolean useExpenseSettlementSlip = false;

	/** ���v��`�[���̓t���O */
	protected boolean usePaymentAppropriateSlip = false;

	/** ���v��`�[���̓t���O */
	protected boolean useReceivableAppropriateSlip = false;

	/** �������`�[���̓t���O */
	protected boolean useReceivableErasingSlip = false;

	/** ���Y�v��`�[���̓t���O */
	protected boolean useAssetsEntrySlip = false;

	/** �x���˗��`�[���̓t���O */
	protected boolean usePaymentRequestSlip = false;

	/** �q�C���x�v�Z�Ώۂ��ǂ��� */
	protected boolean useVoyageCalculation = false;

	/** �������g�p */
	protected boolean useOccurDate = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Item clone() {
		try {
			Item clone = (Item) super.clone();

			if (hasSubItem && this.subItem != null) {
				clone.setSubItem(this.subItem.clone());
			}

			return clone;

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
	 * �⏕�Ȗ�
	 * 
	 * @return �⏕�Ȗ�
	 */
	public SubItem getSubItem() {
		return subItem;
	}

	/**
	 * �⏕�Ȗ�
	 * 
	 * @param subItem �⏕�Ȗ�
	 */
	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;

		this.hasSubItem = subItem != null;
	}

	/**
	 * �⏕�Ȗڂ����݂��邩
	 * 
	 * @return true:����
	 */
	public boolean hasSubItem() {
		return hasSubItem;
	}

	/**
	 * �⏕�Ȗڂ����݂��邩
	 * 
	 * @param hasSubItem true:����
	 */
	public void setSubItem(boolean hasSubItem) {
		this.hasSubItem = hasSubItem;
	}

	/**
	 * �⏕�ȖڃR�[�h��Ԃ�
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		if (getSubItem() != null) {
			return getSubItem().getCode();
		}
		return null;
	}

	/**
	 * �⏕�Ȗږ��̂�Ԃ�
	 * 
	 * @return �⏕�Ȗږ���
	 */
	public String getSubItemName() {
		if (getSubItem() != null) {
			return getSubItem().getName();
		}
		return null;
	}

	/**
	 * �⏕�Ȗڗ��̂�Ԃ�
	 * 
	 * @return �⏕�Ȗڗ���
	 */
	public String getSubItemNames() {
		if (getSubItem() != null) {
			return getSubItem().getNames();
		}
		return null;
	}

	/**
	 * �⏕�Ȗڌ������̂�Ԃ�
	 * 
	 * @return �⏕�Ȗڌ�������
	 */
	public String getSubItemNamek() {
		if (getSubItem() != null) {
			return getSubItem().getNamek();
		}
		return null;
	}

	/**
	 * ����Ȗڂ�Ԃ�
	 * 
	 * @return ����Ȗ�
	 */
	public DetailItem getDetailItem() {
		if (getSubItem() != null) {
			return getSubItem().getDetailItem();
		}
		return null;
	}

	/**
	 * ����ȖڃR�[�h��Ԃ�
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getDetailItemCode() {
		if (getDetailItem() != null) {
			return getDetailItem().getCode();
		}
		return null;
	}

	/**
	 * ����Ȗږ��̂�Ԃ�
	 * 
	 * @return ����Ȗږ���
	 */
	public String getDetailItemName() {
		if (getDetailItem() != null) {
			return getDetailItem().getName();
		}
		return null;
	}

	/**
	 * ����Ȗڗ��̂�Ԃ�
	 * 
	 * @return ����Ȗڗ���
	 */
	public String getDetailItemNames() {
		if (getDetailItem() != null) {
			return getDetailItem().getNames();
		}
		return null;
	}

	/**
	 * ����Ȗڌ������̂�Ԃ�
	 * 
	 * @return ����Ȗڌ�������
	 */
	public String getDetailItemNamek() {
		if (getDetailItem() != null) {
			return getDetailItem().getNamek();
		}
		return null;
	}

	/**
	 * ���ʉ݂�F�߂邩�ǂ���
	 * 
	 * @return true:�F�߂�
	 */
	public boolean isUseForeignCurrency() {
		return useForeignCurrency;
	}

	/**
	 * ���ʉ݂�F�߂邩�ǂ���
	 * 
	 * @param useForeignCurrency true:�F�߂�
	 */
	public void setUseForeignCurrency(boolean useForeignCurrency) {
		this.useForeignCurrency = useForeignCurrency;
	}

	/**
	 * �d���ېł�F�߂邩
	 * 
	 * @return true:�F�߂�
	 */
	public boolean isUsePurchaseTaxation() {
		return usePurchaseTaxation;
	}

	/**
	 * �d���ېł�F�߂邩
	 * 
	 * @param usePurchaseTaxation true:�F�߂�
	 */
	public void setUsePurchaseTaxation(boolean usePurchaseTaxation) {
		this.usePurchaseTaxation = usePurchaseTaxation;
	}

	/**
	 * ����ېł�F�߂邩
	 * 
	 * @return true:�F�߂�
	 */
	public boolean isUseSalesTaxation() {
		return useSalesTaxation;
	}

	/**
	 * ����ېł�F�߂邩
	 * 
	 * @param useSalesTaxation true:�F�߂�
	 */
	public void setUseSalesTaxation(boolean useSalesTaxation) {
		this.useSalesTaxation = useSalesTaxation;
	}

	/**
	 * �����
	 * 
	 * @return ����ł�߂��܂��B
	 */
	public ConsumptionTax getConsumptionTax() {
		return consumptionTax;
	}

	/**
	 * �����
	 * 
	 * @param consumptionTax ����ł�ݒ肵�܂��B
	 */
	public void setConsumptionTax(ConsumptionTax consumptionTax) {
		this.consumptionTax = consumptionTax;
	}

	/**
	 * �Œ蕔��R�[�h
	 * 
	 * @return �Œ蕔��R�[�h��߂��܂��B
	 */
	public String getFixedDepartmentCode() {
		return fixedDepartmentCode;
	}

	/**
	 * �Œ蕔��R�[�h
	 * 
	 * @param fixedDepartmentCode �Œ蕔��R�[�h��ݒ肵�܂��B
	 */
	public void setFixedDepartmentCode(String fixedDepartmentCode) {
		this.fixedDepartmentCode = fixedDepartmentCode;
	}

	/**
	 * �]���ւ��Ώۂ�
	 * 
	 * @return true:�Ώ�
	 */
	public boolean isDoesCurrencyEvaluate() {
		return doesCurrencyEvaluate;
	}

	/**
	 * �]���ւ��Ώۂ�
	 * 
	 * @param doseCurrencyEvaluate true:�Ώ�
	 */
	public void setDoesCurrencyEvaluate(boolean doseCurrencyEvaluate) {
		this.doesCurrencyEvaluate = doseCurrencyEvaluate;
	}

	/**
	 * �Ј�����
	 * 
	 * @return �Ј����͂�߂��܂��B
	 */
	public boolean isUseEmployee() {
		return useEmployee;
	}

	/**
	 * �Ј�����
	 * 
	 * @param useEmployee �Ј����͂�ݒ肵�܂��B
	 */
	public void setUseEmployee(boolean useEmployee) {
		this.useEmployee = useEmployee;
	}

	/**
	 * �����
	 * 
	 * @return ������߂��܂��B
	 */
	public CustomerType getClientType() {
		return clientType;
	}

	/**
	 * �����
	 * 
	 * @param clientType ������ݒ肵�܂��B
	 */
	public void setClientType(CustomerType clientType) {
		this.clientType = clientType;
	}

	/**
	 * �Ǘ�1����
	 * 
	 * @return �Ǘ�1���͂�߂��܂��B
	 */
	public boolean isUseManagement1() {
		return useManagement1;
	}

	/**
	 * �Ǘ�1����
	 * 
	 * @param useManagement1 �Ǘ�1���͂�ݒ肵�܂��B
	 */
	public void setUseManagement1(boolean useManagement1) {
		this.useManagement1 = useManagement1;
	}

	/**
	 * �Ǘ�2����
	 * 
	 * @return �Ǘ�2���͂�߂��܂��B
	 */
	public boolean isUseManagement2() {
		return useManagement2;
	}

	/**
	 * �Ǘ�2����
	 * 
	 * @param useManagement2 �Ǘ�2���͂�ݒ肵�܂��B
	 */
	public void setUseManagement2(boolean useManagement2) {
		this.useManagement2 = useManagement2;
	}

	/**
	 * �Ǘ�3����
	 * 
	 * @return �Ǘ�3���͂�߂��܂��B
	 */
	public boolean isUseManagement3() {
		return useManagement3;
	}

	/**
	 * �Ǘ�3����
	 * 
	 * @param useManagement3 �Ǘ�3���͂�ݒ肵�܂��B
	 */
	public void setUseManagement3(boolean useManagement3) {
		this.useManagement3 = useManagement3;
	}

	/**
	 * �Ǘ�4����
	 * 
	 * @return �Ǘ�4���͂�߂��܂��B
	 */
	public boolean isUseManagement4() {
		return useManagement4;
	}

	/**
	 * �Ǘ�4����
	 * 
	 * @param useManagement4 �Ǘ�4���͂�ݒ肵�܂��B
	 */
	public void setUseManagement4(boolean useManagement4) {
		this.useManagement4 = useManagement4;
	}

	/**
	 * �Ǘ�5����
	 * 
	 * @return �Ǘ�5���͂�߂��܂��B
	 */
	public boolean isUseManagement5() {
		return useManagement5;
	}

	/**
	 * �Ǘ�5����
	 * 
	 * @param useManagement5 �Ǘ�5���͂�ݒ肵�܂��B
	 */
	public void setUseManagement5(boolean useManagement5) {
		this.useManagement5 = useManagement5;
	}

	/**
	 * �Ǘ�6����
	 * 
	 * @return �Ǘ�6���͂�߂��܂��B
	 */
	public boolean isUseManagement6() {
		return useManagement6;
	}

	/**
	 * �Ǘ�6����
	 * 
	 * @param useManagement6 �Ǘ�6���͂�ݒ肵�܂��B
	 */
	public void setUseManagement6(boolean useManagement6) {
		this.useManagement6 = useManagement6;
	}

	/**
	 * ���v����1�𗘗p���邩
	 * 
	 * @return true:���p
	 */
	public boolean isUseNonAccount1() {
		return useNonAccount1;
	}

	/**
	 * ���v����1�𗘗p���邩
	 * 
	 * @param useNonAccount1 true:���p
	 */
	public void setUseNonAccount1(boolean useNonAccount1) {
		this.useNonAccount1 = useNonAccount1;
	}

	/**
	 * ��v����2�𗘗p���邩
	 * 
	 * @return true:���p
	 */
	public boolean isUseNonAccount2() {
		return useNonAccount2;
	}

	/**
	 * ��v����2�𗘗p���邩
	 * 
	 * @param useNonAccount2 true:���p
	 */
	public void setUseNonAccount2(boolean useNonAccount2) {
		this.useNonAccount2 = useNonAccount2;
	}

	/**
	 * ��v����3�𗘗p���邩
	 * 
	 * @return true:���p
	 */
	public boolean isUseNonAccount3() {
		return useNonAccount3;
	}

	/**
	 * ��v����3�𗘗p���邩
	 * 
	 * @param useNonAccount3 true:���p
	 */
	public void setUseNonAccount3(boolean useNonAccount3) {
		this.useNonAccount3 = useNonAccount3;
	}

	/**
	 * �����𗘗p���邩
	 * 
	 * @return true:���p
	 */
	public boolean isUseCustomer() {
		CustomerType customerType = getClientType();
		return customerType != CustomerType.NONE;
	}

	/**
	 * �����`�[���̓t���O
	 * 
	 * @return �����`�[���̓t���O��߂��܂��B
	 */
	public boolean isUseInputCashFlowSlip() {
		return useInputCashFlowSlip;
	}

	/**
	 * �����`�[���̓t���O
	 * 
	 * @param depositSlip �����`�[���̓t���O�t���O��ݒ肵�܂��B
	 */
	public void setUseInputCashFlowSlip(boolean depositSlip) {
		this.useInputCashFlowSlip = depositSlip;
	}

	/**
	 * �o���`�[���̓t���O
	 * 
	 * @return �o���`�[���̓t���O��߂��܂��B
	 */
	public boolean isUseOutputCashFlowSlip() {
		return useOutputCashFlowSlip;
	}

	/**
	 * �o���`�[���̓t���O
	 * 
	 * @param paymentSlip �o���`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUseOutputCashFlowSlip(boolean paymentSlip) {
		this.useOutputCashFlowSlip = paymentSlip;
	}

	/**
	 * �U�֓`�[���̓t���O
	 * 
	 * @return �U�֓`�[���̓t���O��߂��܂��B
	 */
	public boolean isUseTransferSlip() {
		return useTransferSlip;
	}

	/**
	 * �U�֓`�[���̓t���O
	 * 
	 * @param transferSlip �U�֓`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUseTransferSlip(boolean transferSlip) {
		this.useTransferSlip = transferSlip;
	}

	/**
	 * �o��Z�`�[���̓t���O
	 * 
	 * @return �o��Z�`�[���̓t���O��߂��܂��B
	 */
	public boolean isUseExpenseSettlementSlip() {
		return useExpenseSettlementSlip;
	}

	/**
	 * �o��Z�`�[���̓t���O
	 * 
	 * @param billSlip �o��Z�`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUseExpenseSettlementSlip(boolean billSlip) {
		this.useExpenseSettlementSlip = billSlip;
	}

	/**
	 * ���v��`�[���̓t���O
	 * 
	 * @return ���v��`�[���̓t���O��߂��܂��B
	 */
	public boolean isUsePaymentAppropriateSlip() {
		return usePaymentAppropriateSlip;
	}

	/**
	 * ���v��`�[���̓t���O
	 * 
	 * @param debtUpSlip ���v��`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUsePaymentAppropriateSlip(boolean debtUpSlip) {
		this.usePaymentAppropriateSlip = debtUpSlip;
	}

	/**
	 * ���v��`�[���̓t���O
	 * 
	 * @return ���v��`�[���̓t���O��߂��܂��B
	 */
	public boolean isUseReceivableAppropriateSlip() {
		return useReceivableAppropriateSlip;
	}

	/**
	 * ���v��`�[���̓t���O
	 * 
	 * @param claimUpSlip ���v��`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUseReceivableAppropriateSlip(boolean claimUpSlip) {
		this.useReceivableAppropriateSlip = claimUpSlip;
	}

	/**
	 * �������`�[���̓t���O
	 * 
	 * @return �������`�[���̓t���O��߂��܂��B
	 */
	public boolean isUseReceivableErasingSlip() {
		return useReceivableErasingSlip;
	}

	/**
	 * �������`�[���̓t���O
	 * 
	 * @param claimKesiSlip �������`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUseReceivableErasingSlip(boolean claimKesiSlip) {
		this.useReceivableErasingSlip = claimKesiSlip;
	}

	/**
	 * ���Y�v��`�[���̓t���O
	 * 
	 * @return ���Y�v��`�[���̓t���O��߂��܂��B
	 */
	public boolean isUseAssetsEntrySlip() {
		return useAssetsEntrySlip;
	}

	/**
	 * ���Y�v��`�[���̓t���O
	 * 
	 * @param propertySlip ���Y�v��`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUseAssetsEntrySlip(boolean propertySlip) {
		this.useAssetsEntrySlip = propertySlip;
	}

	/**
	 * �x���˗��`�[���̓t���O
	 * 
	 * @return �x���˗��`�[���̓t���O��߂��܂��B
	 */
	public boolean isUsePaymentRequestSlip() {
		return usePaymentRequestSlip;
	}

	/**
	 * �x���˗��`�[���̓t���O
	 * 
	 * @param paymentRequestSlip �x���˗��`�[���̓t���O��ݒ肵�܂��B
	 */
	public void setUsePaymentRequestSlip(boolean paymentRequestSlip) {
		this.usePaymentRequestSlip = paymentRequestSlip;
	}

	/**
	 * �q�C���x�v�Z�Ώۂ��ǂ����̎擾
	 * 
	 * @return useVoyageCalculation �q�C���x�v�Z�Ώۂ��ǂ���
	 */
	public boolean isUseVoyageCalculation() {
		return useVoyageCalculation;
	}

	/**
	 * �q�C���x�v�Z�Ώۂ��ǂ����̐ݒ�
	 * 
	 * @param useVoyageCalculation �q�C���x�v�Z�Ώۂ��ǂ���
	 */
	public void setUseVoyageCalculation(boolean useVoyageCalculation) {
		this.useVoyageCalculation = useVoyageCalculation;
	}

	/**
	 * �Œ蕔�喼��
	 * 
	 * @return fixedDepartmentName �Œ蕔�喼�̂�߂��܂��B
	 */
	public String getFixedDepartmentName() {
		return fixedDepartmentName;
	}

	/**
	 * �Œ蕔�喼��
	 * 
	 * @param fixedDepartmentName �Œ蕔�喼�̂�ݒ肵�܂��B
	 */
	public void setFixedDepartmentName(String fixedDepartmentName) {
		this.fixedDepartmentName = fixedDepartmentName;
	}

	/**
	 * �W�v�敪
	 * 
	 * @return �W�v�敪
	 */
	public ItemSumType getItemSumType() {
		return this.itemSumType;
	}

	/**
	 * �W�v�敪
	 * 
	 * @param itemSumType �W�v�敪
	 */
	public void setItemSumType(ItemSumType itemSumType) {
		this.itemSumType = itemSumType;
	}

	/**
	 * �ݎ؋敪
	 * 
	 * @return �ݎ؋敪
	 */
	public Dc getDc() {
		return this.dc;
	}

	/**
	 * �ݎ؋敪
	 * 
	 * @param dc �ݎ؋敪
	 */
	public void setDc(Dc dc) {
		this.dc = dc;
	}

	/**
	 * �Ȗڎ��
	 * 
	 * @return �Ȗڎ��
	 */
	public ItemType getItemType() {
		return this.itemType;
	}

	/**
	 * �Ȗڎ��
	 * 
	 * @param itemType �Ȗڎ��
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	/**
	 * GL�Ȗڐ���敪
	 * 
	 * @return GL�Ȗڐ���敪
	 */
	public GLType getGlType() {
		return this.glType;
	}

	/**
	 * GL�Ȗڐ���敪
	 * 
	 * @param glType GL�Ȗڐ���敪
	 */
	public void setGlType(GLType glType) {
		this.glType = glType;
	}

	/**
	 * AP�Ȗڐ���敪
	 * 
	 * @return AP�Ȗڐ���敪
	 */
	public APType getApType() {
		return this.apType;
	}

	/**
	 * AP�Ȗڐ���敪
	 * 
	 * @param apType AP�Ȗڐ���敪
	 */
	public void setApType(APType apType) {
		this.apType = apType;
	}

	/**
	 * AR�Ȗڐ���敪
	 * 
	 * @return AR�Ȗڐ���敪
	 */
	public ARType getArType() {
		return this.arType;
	}

	/**
	 * AR�Ȗڐ���敪
	 * 
	 * @param arType AR�Ȗڐ���敪
	 */
	public void setArType(ARType arType) {
		this.arType = arType;
	}

	/**
	 * BG�Ȗڐ���敪
	 * 
	 * @return BG�Ȗڐ���敪
	 */
	public BGType getBgType() {
		return this.bgType;
	}

	/**
	 * BG�Ȗڐ���敪
	 * 
	 * @param bgType BG�Ȗڐ���敪
	 */
	public void setBgType(BGType bgType) {
		this.bgType = bgType;
	}

	/**
	 * ���E�Ȗڐ���敪
	 * 
	 * @return true:����Afalse:���Ȃ�
	 */
	public boolean isDoesOffsetItem() {
		return this.doesOffsetItem;
	}

	/**
	 * ���E�Ȗڐ���敪
	 * 
	 * @param offsetItem true:����Afalse:���Ȃ�
	 */
	public void setDoesOffsetItem(boolean offsetItem) {
		this.doesOffsetItem = offsetItem;
	}

	/**
	 * BS��������敪
	 * 
	 * @return true:����Afalse:���Ȃ�
	 */
	public boolean isDoesBsOffset() {
		return this.doesBsOffset;
	}

	/**
	 * BS��������敪
	 * 
	 * @param bsAcOffset true:����Afalse:���Ȃ�
	 */
	public void setDoesBsOffset(boolean bsAcOffset) {
		this.doesBsOffset = bsAcOffset;
	}

	/**
	 * �]���֕���
	 * 
	 * @return �]���֕���
	 */
	public EvaluationMethod getEvaluationMethod() {
		return this.evaluationMethod;
	}

	/**
	 * �]���֕���
	 * 
	 * @param evaluationMethod �]���֕���
	 */
	public void setEvaluationMethod(EvaluationMethod evaluationMethod) {
		this.evaluationMethod = evaluationMethod;
	}

	/**
	 * �������g�p�̎擾
	 * 
	 * @return useOccurDate �������g�p
	 */
	public boolean isUseOccurDate() {
		return useOccurDate;
	}

	/**
	 * �������g�p�̐ݒ�
	 * 
	 * @param useOccurDate �������g�p
	 */
	public void setUseOccurDate(boolean useOccurDate) {
		this.useOccurDate = useOccurDate;
	}

	/**
	 * ����Ȗڂ��ǂ����𔻒�(����܂�)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof Item)) {
			return false;
		}

		Item item = (Item) obj;

		// �C���X�^���X�ꏏ
		if (item == this) {
			return true;
		}

		// ���
		if (!this.companyCode.equals(item.getCompanyCode())) {
			return false;
		}

		// �Ȗ�
		if (!this.code.equals(item.getCode())) {
			return false;
		}

		// �⏕
		if (!Util.avoidNull(this.getSubItemCode()).equals(Util.avoidNull(item.getSubItemCode()))) {
			return false;
		}

		// ����
		if (!Util.avoidNull(this.getDetailItemCode()).equals(Util.avoidNull(item.getDetailItemCode()))) {
			return false;
		}

		return true;
	}

	/**
	 * �K�����Ȗڏ���Ԃ�
	 * 
	 * @return �Ȗ�
	 */
	public Item getPreferedItem() {
		Item item = this;

		if (hasSubItem) {
			SubItem sub = this.getSubItem();
			if (sub != null && sub.hasDetailItem) {
				item = getDetailItem();

			} else {
				item = sub;
			}
		}

		return item != null ? item : this;
	}
}
