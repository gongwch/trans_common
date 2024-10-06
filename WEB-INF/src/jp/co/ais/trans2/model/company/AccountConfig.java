package jp.co.ais.trans2.model.company;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * ��v�̐ݒ���
 * 
 * @author AIS
 */
public class AccountConfig extends TransferBase {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("key=").append(keyCurrency);
		sb.append(",func=").append(functionalCurrency);
		return sb.toString();
	}

	/** �Ȗږ��� */
	protected String itemName = "";

	/** �⏕�Ȗږ��� */
	protected String subItemName = "";

	/** ����Ȗڂ��g�p���邩 */
	protected boolean useDetailItem = false;

	/** ����Ȗږ��� */
	protected String detailItemName = "";

	/** �Ǘ�1���g�p���邩 */
	protected boolean useManagement1 = false;

	/** �Ǘ�2���g�p���邩 */
	protected boolean useManagement2 = false;

	/** �Ǘ�3���g�p���邩 */
	protected boolean useManagement3 = false;

	/** �Ǘ�4���g�p���邩 */
	protected boolean useManagement4 = false;

	/** �Ǘ�5���g�p���邩 */
	protected boolean useManagement5 = false;

	/** �Ǘ�6���g�p���邩 */
	protected boolean useManagement6 = false;

	/** �Ǘ�1���� */
	protected String management1Name = null;

	/** �Ǘ�2���� */
	protected String management2Name = null;

	/** �Ǘ�3���� */
	protected String management3Name = null;

	/** �Ǘ�4���� */
	protected String management4Name = null;

	/** �Ǘ�5���� */
	protected String management5Name = null;

	/** �Ǘ�6���� */
	protected String management6Name = null;

	/** IFRS�@�\���g�p���邩 */
	protected boolean useIfrs = false;

	/** ��ʉ� */
	protected Currency keyCurrency = null;

	/** �@�\�ʉ� */
	protected Currency functionalCurrency = null;

	/** ���v����1�敪(0�͖��g�p) */
	protected int nonAccounting1 = 0;

	/** ���v����2�敪(0�͖��g�p) */
	protected int nonAccounting2 = 0;

	/** ���v����3�敪(0�͖��g�p) */
	protected int nonAccounting3 = 0;

	/** ���v����1���� */
	protected String nonAccounting1Name = null;

	/** ���v����2���� */
	protected String nonAccounting2Name = null;

	/** ���v����3���� */
	protected String nonAccounting3Name = null;

	/** ���[�N�t���[���F�𗘗p���邩 */
	protected boolean useWorkflowApprove = false;

	/** ���[�N�t���[���F�ł̏��F����E�۔F���A�ŏ��̃X�e�[�^�X�ɖ߂��� */
	protected boolean isBackFirstWhenWorkflowDeny = false;

	/** ���ꏳ�F���g�p���邩 */
	protected boolean useFieldApprove = false;

	/** �o�����F���g�p���邩 */
	protected boolean useApprove = false;

	/** �O���[�v��v���g�p���邩 */
	protected boolean useGroupAccount = false;

	/** �������`�F�b�N���g�p���邩 */
	protected boolean useHasDateCheck = false;

	/** ���Z�[���������� */
	protected ExchangeFraction exchangeFraction;

	/** ��������Œ[������ */
	protected ExchangeFraction paymentFunction;

	/** �������Œ[������ */
	protected ExchangeFraction receivingFunction;

	/** �`�[������邩�ǂ���(true����) */
	protected boolean slipPrint;

	/** �`�[����̏����l(true:�������) */
	protected boolean slipPrintDefault;

	/** �`�[�L�������`�F�b�N�L�� */
	protected boolean slipTermCheck;

	/** ���Z�敪 */
	protected ConvertType convertType = null;

	/** �]���փ��[�g�敪 */
	protected EvaluationRateDate evaluationRateDate = null;

	/** �����̔ԕ����� */
	protected int slipNoDigit = 0;

	/** �����̔ԍ���1 */
	protected SlipNoAdopt slipNoAdopt1 = null;

	/** �����̔ԍ���2 */
	protected SlipNoAdopt slipNoAdopt2 = null;

	/** �����̔ԍ���3 */
	protected SlipNoAdopt slipNoAdopt3 = null;

	/** ���Ԍ��Z�J�z�敪 */
	protected boolean carryJournalOfMidtermClosingForward = true;

	/**
	 * �R���X�g���N�^
	 */
	public AccountConfig() {
		super();
	}

	/**
	 * @param slipNoDigit slipNoDigit��ݒ肵�܂��B
	 */
	public void setSlipNoDigit(int slipNoDigit) {
		this.slipNoDigit = slipNoDigit;
	}

	/**
	 * @return slipNoDigit��߂��܂��B
	 */
	public int getSlipNoDigit() {
		return slipNoDigit;
	}

	/**
	 * @return useIfrs��߂��܂��B
	 */
	public boolean isUseIfrs() {
		return useIfrs;
	}

	/**
	 * @param useIfrs useIfrs��ݒ肵�܂��B
	 */
	public void setUseIfrs(boolean useIfrs) {
		this.useIfrs = useIfrs;
	}

	/**
	 * @return management1Name��߂��܂��B
	 */
	public String getManagement1Name() {
		return management1Name;
	}

	/**
	 * @param management1Name management1Name��ݒ肵�܂��B
	 */
	public void setManagement1Name(String management1Name) {
		this.management1Name = management1Name;
	}

	/**
	 * @return management2Name��߂��܂��B
	 */
	public String getManagement2Name() {
		return management2Name;
	}

	/**
	 * @param management2Name management2Name��ݒ肵�܂��B
	 */
	public void setManagement2Name(String management2Name) {
		this.management2Name = management2Name;
	}

	/**
	 * @return management3Name��߂��܂��B
	 */
	public String getManagement3Name() {
		return management3Name;
	}

	/**
	 * @param management3Name management3Name��ݒ肵�܂��B
	 */
	public void setManagement3Name(String management3Name) {
		this.management3Name = management3Name;
	}

	/**
	 * @return management4Name��߂��܂��B
	 */
	public String getManagement4Name() {
		return management4Name;
	}

	/**
	 * @param management4Name management4Name��ݒ肵�܂��B
	 */
	public void setManagement4Name(String management4Name) {
		this.management4Name = management4Name;
	}

	/**
	 * @return management5Name��߂��܂��B
	 */
	public String getManagement5Name() {
		return management5Name;
	}

	/**
	 * @param management5Name management5Name��ݒ肵�܂��B
	 */
	public void setManagement5Name(String management5Name) {
		this.management5Name = management5Name;
	}

	/**
	 * @return management6Name��߂��܂��B
	 */
	public String getManagement6Name() {
		return management6Name;
	}

	/**
	 * @param management6Name management6Name��ݒ肵�܂��B
	 */
	public void setManagement6Name(String management6Name) {
		this.management6Name = management6Name;
	}

	/**
	 * @return useManagement1��߂��܂��B
	 */
	public boolean isUseManagement1() {
		return useManagement1;
	}

	/**
	 * @param useManagement1 useManagement1��ݒ肵�܂��B
	 */
	public void setUseManagement1(boolean useManagement1) {
		this.useManagement1 = useManagement1;
	}

	/**
	 * @return useManagement2��߂��܂��B
	 */
	public boolean isUseManagement2() {
		return useManagement2;
	}

	/**
	 * @param useManagement2 useManagement2��ݒ肵�܂��B
	 */
	public void setUseManagement2(boolean useManagement2) {
		this.useManagement2 = useManagement2;
	}

	/**
	 * @return useManagement3��߂��܂��B
	 */
	public boolean isUseManagement3() {
		return useManagement3;
	}

	/**
	 * @param useManagement3 useManagement3��ݒ肵�܂��B
	 */
	public void setUseManagement3(boolean useManagement3) {
		this.useManagement3 = useManagement3;
	}

	/**
	 * @return useManagement4��߂��܂��B
	 */
	public boolean isUseManagement4() {
		return useManagement4;
	}

	/**
	 * @param useManagement4 useManagement4��ݒ肵�܂��B
	 */
	public void setUseManagement4(boolean useManagement4) {
		this.useManagement4 = useManagement4;
	}

	/**
	 * @return useManagement5��߂��܂��B
	 */
	public boolean isUseManagement5() {
		return useManagement5;
	}

	/**
	 * @param useManagement5 useManagement5��ݒ肵�܂��B
	 */
	public void setUseManagement5(boolean useManagement5) {
		this.useManagement5 = useManagement5;
	}

	/**
	 * @return useManagement6��߂��܂��B
	 */
	public boolean isUseManagement6() {
		return useManagement6;
	}

	/**
	 * @param useManagement6 useManagement6��ݒ肵�܂��B
	 */
	public void setUseManagement6(boolean useManagement6) {
		this.useManagement6 = useManagement6;
	}

	/**
	 * @return itemName��߂��܂��B
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName itemName��ݒ肵�܂��B
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return subItemName��߂��܂��B
	 */
	public String getSubItemName() {
		return subItemName;
	}

	/**
	 * @param subItemName subItemName��ݒ肵�܂��B
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * @return detailItemName��߂��܂��B
	 */
	public String getDetailItemName() {
		return detailItemName;
	}

	/**
	 * @param detailItemName detailItemName��ݒ肵�܂��B
	 */
	public void setDetailItemName(String detailItemName) {
		this.detailItemName = detailItemName;
	}

	/**
	 * @return useDetailItem��߂��܂��B
	 */
	public boolean isUseDetailItem() {
		return useDetailItem;
	}

	/**
	 * @param useDetailItem useDetailItem��ݒ肵�܂��B
	 */
	public void setUseDetailItem(boolean useDetailItem) {
		this.useDetailItem = useDetailItem;
	}

	/**
	 * �@�\�ʉ�
	 * 
	 * @return �@�\�ʉ�
	 */
	public Currency getFunctionalCurrency() {
		return functionalCurrency;
	}

	/**
	 * �@�\�ʉ�
	 * 
	 * @param functionalCurrency �@�\�ʉ�
	 */
	public void setFunctionalCurrency(Currency functionalCurrency) {
		this.functionalCurrency = functionalCurrency;
	}

	/**
	 * ��ʉ�
	 * 
	 * @return ��ʉ�
	 */
	public Currency getKeyCurrency() {
		return keyCurrency;
	}

	/**
	 * ��ʉ�
	 * 
	 * @param keyCurrency ��ʉ�
	 */
	public void setKeyCurrency(Currency keyCurrency) {
		this.keyCurrency = keyCurrency;
	}

	/**
	 * ���v���׋敪1
	 * 
	 * @return ���v���׋敪1
	 */
	public NonAccountingDivision getNonAccounting1() {
		return NonAccountingDivision.get(nonAccounting1);
	}

	/**
	 * ���v���׋敪1
	 * 
	 * @param useNotAccounting1 ���v���׋敪1
	 */
	public void setNonAccounting1(int useNotAccounting1) {
		this.nonAccounting1 = useNotAccounting1;
	}

	/**
	 * ���v���׋敪2
	 * 
	 * @return ���v���׋敪2
	 */
	public NonAccountingDivision getNonAccounting2() {
		return NonAccountingDivision.get(nonAccounting2);
	}

	/**
	 * ���v���׋敪2
	 * 
	 * @param useNotAccounting2 ���v���׋敪2
	 */
	public void setNonAccounting2(int useNotAccounting2) {
		this.nonAccounting2 = useNotAccounting2;
	}

	/**
	 * ���v���׋敪3
	 * 
	 * @return ���v���׋敪3
	 */
	public NonAccountingDivision getNonAccounting3() {
		return NonAccountingDivision.get(nonAccounting3);
	}

	/**
	 * ���v���׋敪3
	 * 
	 * @param useNotAccounting3 ���v���׋敪3
	 */
	public void setNonAccounting3(int useNotAccounting3) {
		this.nonAccounting3 = useNotAccounting3;
	}

	/**
	 * ���v����1����
	 * 
	 * @return ���v����1����
	 */
	public String getNonAccounting1Name() {
		return nonAccounting1Name;
	}

	/**
	 * ���v����1����
	 * 
	 * @param notAccounting1Name ���v����1����
	 */
	public void setNonAccounting1Name(String notAccounting1Name) {
		this.nonAccounting1Name = notAccounting1Name;
	}

	/**
	 * ���v����2����
	 * 
	 * @return ���v����2����
	 */
	public String getNonAccounting2Name() {
		return nonAccounting2Name;
	}

	/**
	 * ���v����2����
	 * 
	 * @param notAccounting2Name ���v����2����
	 */
	public void setNonAccounting2Name(String notAccounting2Name) {
		this.nonAccounting2Name = notAccounting2Name;
	}

	/**
	 * ���v����2����
	 * 
	 * @return ���v����2����
	 */
	public String getNonAccounting3Name() {
		return nonAccounting3Name;
	}

	/**
	 * ���v����2����
	 * 
	 * @param notAccounting3Name ���v����2����
	 */
	public void setNonAccounting3Name(String notAccounting3Name) {
		this.nonAccounting3Name = notAccounting3Name;
	}

	/**
	 * ���v����1���g����
	 * 
	 * @return ���v����1���g����
	 */
	public boolean isUseNotAccounting1() {
		return (NonAccountingDivision.NONE != getNonAccounting1());
	}

	/**
	 * ���v����2���g����
	 * 
	 * @return ���v����2���g����
	 */
	public boolean isUseNotAccounting2() {
		return (NonAccountingDivision.NONE != getNonAccounting2());
	}

	/**
	 * ���v����3���g����
	 * 
	 * @return ���v����3���g����
	 */
	public boolean isUseNotAccounting3() {
		return (NonAccountingDivision.NONE != getNonAccounting3());
	}

	/**
	 * ���[�N�t���[���F�𗘗p���邩���擾
	 * 
	 * @return useWorkflowApprove
	 */
	public boolean isUseWorkflowApprove() {
		return useWorkflowApprove;
	}

	/**
	 * ���[�N�t���[���F�𗘗p���邩���Z�b�g����
	 * 
	 * @param useWorkflowApprove useWorkflowApprove
	 */
	public void setUseWorkflowApprove(boolean useWorkflowApprove) {
		this.useWorkflowApprove = useWorkflowApprove;
	}

	/**
	 * ���[�N�t���[���F�ł̏��F����E�۔F���A�ŏ��̃X�e�[�^�X�ɖ߂������擾
	 * 
	 * @return isBackFirstWhenWorkflowDeny
	 */
	public boolean isBackFirstWhenWorkflowDeny() {
		return isBackFirstWhenWorkflowDeny;
	}

	/**
	 * ���[�N�t���[���F�ł̏��F����E�۔F���A�ŏ��̃X�e�[�^�X�ɖ߂������Z�b�g����
	 * 
	 * @param isBackFirstWhenWorkflowDeny isBackFirstWhenWorkflowDeny
	 */
	public void setBackFirstWhenWorkflowDeny(boolean isBackFirstWhenWorkflowDeny) {
		this.isBackFirstWhenWorkflowDeny = isBackFirstWhenWorkflowDeny;
	}

	/**
	 * ���ꏳ�F���g�p���邩
	 * 
	 * @return ���ꏳ�F���g�p���邩
	 */
	public boolean isUseFieldApprove() {
		return useFieldApprove;
	}

	/**
	 * ���ꏳ�F���g�p���邩�ݒ肷��
	 * 
	 * @param useFieldApprove ���ꏳ�F���g�p���邩
	 */
	public void setUseFieldApprove(boolean useFieldApprove) {
		this.useFieldApprove = useFieldApprove;
	}

	/**
	 * �`�[������邩�ǂ���
	 * 
	 * @return true:�������
	 */
	public boolean isSlipPrint() {
		return slipPrint;
	}

	/**
	 * �`�[������邩�ǂ���
	 * 
	 * @param slipPrint true:�������
	 */
	public void setSlipPrint(boolean slipPrint) {
		this.slipPrint = slipPrint;
	}

	/**
	 * �`�[�����~�̏����l
	 * 
	 * @return true:�������
	 */
	public boolean getSlipPrintDefault() {
		return slipPrintDefault;
	}

	/**
	 * �`�[�����~�̏����l
	 * 
	 * @param slipPrintStopDefault true:�������
	 */
	public void setSlipPrintDefault(boolean slipPrintStopDefault) {
		this.slipPrintDefault = slipPrintStopDefault;
	}

	/**
	 * useGroupAccount���擾����B
	 * 
	 * @return boolean useGroupAccount
	 */
	public boolean isUseGroupAccount() {
		return useGroupAccount;
	}

	/**
	 * useGroupAccount��ݒ肷��B
	 * 
	 * @param useGroupAccount
	 */
	public void setUseGroupAccount(boolean useGroupAccount) {
		this.useGroupAccount = useGroupAccount;
	}

	/**
	 * useHasDateCheck���擾����
	 * 
	 * @return useHasDateCheck
	 */
	public boolean isUseHasDateCheck() {
		return useHasDateCheck;
	}

	/**
	 * useHasDateCheck��ݒ肷��
	 * 
	 * @param useHasDateCheck
	 */
	public void setUseHasDateCheck(boolean useHasDateCheck) {
		this.useHasDateCheck = useHasDateCheck;
	}

	/**
	 * ���Z�[����������
	 * 
	 * @return ���Z�[����������
	 */
	public ExchangeFraction getExchangeFraction() {
		return exchangeFraction;
	}

	/**
	 * ���Z�[����������
	 * 
	 * @param exchangeFraction ���Z�[����������
	 */
	public void setExchangeFraction(ExchangeFraction exchangeFraction) {
		this.exchangeFraction = exchangeFraction;
	}

	/**
	 * ��������Œ[������
	 * 
	 * @return ��������Œ[������
	 */
	public ExchangeFraction getPaymentFunction() {
		return paymentFunction;
	}

	/**
	 * ��������Œ[������
	 * 
	 * @param paymentFunction ��������Œ[������
	 */
	public void setPaymentFunction(ExchangeFraction paymentFunction) {
		this.paymentFunction = paymentFunction;
	}

	/**
	 * �������Œ[������
	 * 
	 * @return �������Œ[������
	 */
	public ExchangeFraction getReceivingFunction() {
		return receivingFunction;
	}

	/**
	 * �������Œ[������
	 * 
	 * @param receivingFunction �������Œ[������
	 */
	public void setReceivingFunction(ExchangeFraction receivingFunction) {
		this.receivingFunction = receivingFunction;
	}

	/**
	 * �`�[�L�������`�F�b�N�L��
	 * 
	 * @param slipTermCheck true:�L
	 */
	public void setSlipTermCheck(boolean slipTermCheck) {
		this.slipTermCheck = slipTermCheck;
	}

	/**
	 * �`�[�L�������`�F�b�N�L��
	 * 
	 * @return true:�L
	 */
	public boolean isSlipTermCheck() {
		return slipTermCheck;
	}

	/**
	 * ���Z�敪
	 * 
	 * @return convertType ���Z�敪
	 */
	public ConvertType getConvertType() {
		return convertType;
	}

	/**
	 * ���Z�敪
	 * 
	 * @param convertType ���Z�敪
	 */
	public void setConvertType(ConvertType convertType) {
		this.convertType = convertType;
	}

	/**
	 * �o�����F���g�p���邩
	 * 
	 * @return �o�����F���g�p���邩
	 */
	public boolean isUseApprove() {
		return useApprove;
	}

	/**
	 * �o�����F���g�p���邩���Z�b�g����
	 * 
	 * @param useApprove �o�����F���g�p���邩
	 */
	public void setUseApprove(boolean useApprove) {
		this.useApprove = useApprove;
	}

	/**
	 * �ʉ݃^�C�v�ɕR�t���ʉ݂�Ԃ�
	 * 
	 * @param currencyType �ʉ݃^�C�v
	 * @return �ʉ݃^�C�v�ɕR�t���ʉ�
	 */
	public Currency getCurrency(CurrencyType currencyType) {
		if (CurrencyType.FUNCTIONAL == currencyType) {
			return getFunctionalCurrency();
		}
		return getKeyCurrency();
	}

	/**
	 * �]���փ��[�g�敪��Ԃ�
	 * 
	 * @return �]���փ��[�g�敪
	 */
	public EvaluationRateDate getEvaluationRateDate() {
		return evaluationRateDate;
	}

	/**
	 * �]���փ��[�g�敪���Z�b�g����
	 * 
	 * @param evaluationRateDate �]���փ��[�g�敪
	 */
	public void setEvaluationRateDate(EvaluationRateDate evaluationRateDate) {
		this.evaluationRateDate = evaluationRateDate;
	}

	/**
	 * �����ݒ荀��1��Ԃ�
	 * 
	 * @return slipNoAdopt1
	 */
	public SlipNoAdopt getSlipNoAdopt1() {
		return slipNoAdopt1;
	}

	/**
	 * �����ݒ荀��1���Z�b�g����
	 * 
	 * @param slipNoAdopt1
	 */
	public void setSlipNoAdopt1(SlipNoAdopt slipNoAdopt1) {
		this.slipNoAdopt1 = slipNoAdopt1;
	}

	/**
	 * �����ݒ荀��2��Ԃ�
	 * 
	 * @return slipNoAdopt2
	 */
	public SlipNoAdopt getSlipNoAdopt2() {
		return slipNoAdopt2;
	}

	/**
	 * �����ݒ荀��2���Z�b�g����
	 * 
	 * @param slipNoAdopt2
	 */
	public void setSlipNoAdopt2(SlipNoAdopt slipNoAdopt2) {
		this.slipNoAdopt2 = slipNoAdopt2;
	}

	/**
	 * �����ݒ荀��3��Ԃ�
	 * 
	 * @return slipNoAdopt3
	 */
	public SlipNoAdopt getSlipNoAdopt3() {
		return slipNoAdopt3;
	}

	/**
	 * �����ݒ荀��3���Z�b�g����
	 * 
	 * @param slipNoAdopt3
	 */
	public void setSlipNoAdopt3(SlipNoAdopt slipNoAdopt3) {
		this.slipNoAdopt3 = slipNoAdopt3;
	}

	/**
	 * ���Ԍ��Z�d����J��z����
	 * 
	 * @return true:���Ԍ��Z�d����J��z��
	 */
	public boolean isCarryJournalOfMidtermClosingForward() {
		return carryJournalOfMidtermClosingForward;
	}

	/**
	 * ���Ԍ��Z�d����J��z������ݒ肷��B
	 * 
	 * @param carryJournalOfMidtermClosingForward true:���Ԍ��Z�d����J��z��
	 */

	public void setCarryJournalOfMidtermClosingForward(boolean carryJournalOfMidtermClosingForward) {
		this.carryJournalOfMidtermClosingForward = carryJournalOfMidtermClosingForward;
	}

}
