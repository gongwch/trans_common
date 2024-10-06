package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �Ȗڃ}�X�^��������
 */
public class ItemSearchCondition extends TransferBase implements Cloneable {

	/** �`�[���͎�� */
	public enum SlipInputType {
	/** �U�֓`�[���� */
	TransferSlip,

	/** �����`�[���� */
	InputCashFlowSlip,

	/** �o���`�[���� */
	OutputCashFlowSlip,

	/** �U�֓`�[���� */
	ReversingSlip,

	/** �o��Z�`�[���� */
	ExpenseSettlementSlip,

	/** �������`�[����(���v��) */
	PaymentAppropriateSlip,

	/** ���v��`�[���� */
	ReceivableAppropriateSlip,

	/** �������`�[���� */
	ReceivableErasingSlip,

	/** ���Y�v��`�[���� */
	AssetsEntrySlip,

	/** �x���˗��`�[���� */
	PaymentRequestSlip;
	}

	/** AR�Ȗڐ���敪 */
	public enum ARControlType {
		/** �ʏ� */
		Nomal,

		/** ���Ǘ��Ȗ� */
		ARAccount,

		/** ����������� */
		ARTempolaryAccount
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �W�v�Ȗڂ��܂ނ� */
	protected boolean sumItem = false;

	/** ���o���Ȗڂ��܂ނ� */
	protected boolean titleItem = false;

	/** �⏕�Ȗڂ��܂ނ� */
	protected boolean subItem = false;

	/** �Œ蕔��R�[�h */
	protected String departmentCode = null;

	/** �`�[���͎�� */
	protected SlipInputType slipInputType = null;

	/** �]���։Ȗڂ�����Ώۂɂ��邩 */
	protected boolean evaluation = false;

	/** BS��������Ȗڂ�����Ώۂɂ��邩 */
	protected boolean bsCalculateErase = false;

	/** �����Ȗڂ�����Ώۂɂ��邩 */
	protected boolean cash = false;

	/** ���Ȗڂ�����Ώۂɂ��邩 */
	protected boolean paymentItem = false;

	/** ���Ȗڂ�����Ώۂɂ��邩 */
	protected boolean receiveItem = false;

	/** AR�Ȗڐ���敪 */
	protected List<ARControlType> arControlTypes = new ArrayList<ARControlType>();

	/** �Ȗڑ̌n�R�[�h */
	protected String itemOrganizationCode = null;

	/** �Ǘ�1�I�� */
	public boolean useManagement1 = false;

	/** �Ǘ�2�I�� */
	public boolean useManagement2 = false;

	/** �Ǘ�3�I�� */
	public boolean useManagement3 = false;

	/** �Ǘ�4�I�� */
	public boolean useManagement4 = false;

	/** �Ǘ�5�I�� */
	public boolean useManagement5 = false;

	/** �Ǘ�6�I�� */
	public boolean useManagement6 = false;

	/** �Ǘ�1��I�� */
	public boolean notUseManagement1 = false;

	/** �Ǘ�2��I�� */
	public boolean notUseManagement2 = false;

	/** �Ǘ�3��I�� */
	public boolean notUseManagement3 = false;

	/** �Ǘ�4��I�� */
	public boolean notUseManagement4 = false;

	/** �Ǘ�5��I�� */
	public boolean notUseManagement5 = false;

	/** �Ǘ�6��I�� */
	public boolean notUseManagement6 = false;

	/** ���ʉ� */
	public boolean useForeignCurrency = false;

	/** �Ј��t���O */
	public boolean useEmployee = false;

	/** ��Ј��t���O */
	public boolean noUseEmployee = false;

	/** ����ŃR�[�h�t���O */
	public boolean flgZeiCode = false;

	/** �ǉ��������� */
	protected String addonWhereSql = null;

	/** PK�w�胊�X�g */
	protected List<ItemSearchPrimaryKey> pkList = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ItemSearchCondition clone() {
		try {
			ItemSearchCondition con = (ItemSearchCondition) super.clone();
			if (pkList != null) {
				List<ItemSearchPrimaryKey> list = new ArrayList();
				for (ItemSearchPrimaryKey key : pkList) {
					list.add(key.clone());
				}
				con.setPkList(list);
			}
			return con;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ����ŃR�[�h�t���O��ݒ肷��
	 * 
	 * @param flgZeiCode
	 */
	public void setFlgZeiCode(boolean flgZeiCode) {
		this.flgZeiCode = flgZeiCode;
	}

	/**
	 * ����ŃR�[�h�����͂���Ă��邩
	 * 
	 * @return flgZeiCode
	 */
	public boolean isFlgZeiCode() {
		return this.flgZeiCode;
	}

	/**
	 * �Ј����͂��\����ݒ肷��
	 * 
	 * @param useEmployee
	 */
	public void setUseEmployee(boolean useEmployee) {
		this.useEmployee = useEmployee;
	}

	/**
	 * �Ј����͂��\��
	 * 
	 * @return useEmployee
	 */
	public boolean isUseEmployee() {
		return this.useEmployee;
	}

	/**
	 * ��Ј����͂�ݒ肷��
	 * 
	 * @param noUseEmployee
	 */
	public void setNoUseEmployee(boolean noUseEmployee) {
		this.noUseEmployee = noUseEmployee;
	}

	/**
	 * ��Ј����͔���
	 * 
	 * @return noUseEmployee
	 */
	public boolean isNoUseEmployee() {
		return this.noUseEmployee;
	}

	/**
	 * ���ʉݓ��͂��\����ݒ肷��
	 * 
	 * @param useForeignCurrency
	 */
	public void setUseForeignCurrency(boolean useForeignCurrency) {
		this.useForeignCurrency = useForeignCurrency;
	}

	/**
	 * ���ʉݓ��͂��\��
	 * 
	 * @return useForeignCurrency
	 */
	public boolean isUseForeignCurrency() {
		return this.useForeignCurrency;
	}

	/**
	 * �Ǘ�1��I����ݒ肷��
	 * 
	 * @param notUseManagement1
	 */
	public void setNotUseManagement1(boolean notUseManagement1) {
		this.notUseManagement1 = notUseManagement1;
	}

	/**
	 * �Ǘ�1��I�𔻒�
	 * 
	 * @return notUseManagement1
	 */
	public boolean isNotUseManagement1() {
		return this.notUseManagement1;
	}

	/**
	 * �Ǘ�2��I����ݒ肷��
	 * 
	 * @param notUseManagement2
	 */
	public void setNotUseManagement2(boolean notUseManagement2) {
		this.notUseManagement2 = notUseManagement2;
	}

	/**
	 * �Ǘ�2��I�𔻒�
	 * 
	 * @return notUseManagement2
	 */
	public boolean isNotUseManagement2() {
		return this.notUseManagement2;
	}

	/**
	 * �Ǘ�3��I����ݒ肷��
	 * 
	 * @param notUseManagement3
	 */
	public void setNotUseManagement3(boolean notUseManagement3) {
		this.notUseManagement3 = notUseManagement3;
	}

	/**
	 * �Ǘ�3��I�𔻒�
	 * 
	 * @return notUseManagement3
	 */
	public boolean isNotUseManagement3() {
		return this.notUseManagement3;
	}

	/**
	 * �Ǘ�4��I����ݒ肷��
	 * 
	 * @param notUseManagement4
	 */
	public void setNotUseManagement4(boolean notUseManagement4) {
		this.notUseManagement4 = notUseManagement4;
	}

	/**
	 * �Ǘ�4��I�𔻒�
	 * 
	 * @return notUseManagement4
	 */
	public boolean isNotUseManagement4() {
		return this.notUseManagement4;
	}

	/**
	 * �Ǘ�5��I����ݒ肷��
	 * 
	 * @param notUseManagement5
	 */
	public void setNotUseManagement5(boolean notUseManagement5) {
		this.notUseManagement5 = notUseManagement5;
	}

	/**
	 * �Ǘ�5��I�𔻒�
	 * 
	 * @return notUseManagement5
	 */
	public boolean isNotUseManagement5() {
		return this.notUseManagement5;
	}

	/**
	 * �Ǘ�6��I����ݒ肷��
	 * 
	 * @param notUseManagement6
	 */
	public void setNotUseManagement6(boolean notUseManagement6) {
		this.notUseManagement6 = notUseManagement6;
	}

	/**
	 * �Ǘ�6��I�𔻒�
	 * 
	 * @return notUseManagement6
	 */
	public boolean isNotUseManagement6() {
		return this.notUseManagement6;
	}

	/**
	 * �Ǘ�1�I����ݒ肷��
	 * 
	 * @param useManagement1
	 */
	public void setUseManagement1(boolean useManagement1) {
		this.useManagement1 = useManagement1;
	}

	/**
	 * �Ǘ�1�I�𔻒�
	 * 
	 * @return useManagement1
	 */
	public boolean isUseManagement1() {
		return this.useManagement1;
	}

	/**
	 * �Ǘ�2�I����ݒ肷��
	 * 
	 * @param useManagement2
	 */
	public void setUseManagement2(boolean useManagement2) {
		this.useManagement2 = useManagement2;
	}

	/**
	 * �Ǘ�2�I�𔻒�
	 * 
	 * @return useManagement2
	 */
	public boolean isUseManagement2() {
		return this.useManagement2;
	}

	/**
	 * �Ǘ�3�I����ݒ肷��
	 * 
	 * @param useManagement3
	 */
	public void setUseManagement3(boolean useManagement3) {
		this.useManagement3 = useManagement3;
	}

	/**
	 * �Ǘ�3�I�𔻒�
	 * 
	 * @return useManagement3
	 */
	public boolean isUseManagement3() {
		return this.useManagement3;
	}

	/**
	 * �Ǘ�4�I����ݒ肷��
	 * 
	 * @param useManagement4
	 */
	public void setUseManagement4(boolean useManagement4) {
		this.useManagement4 = useManagement4;
	}

	/**
	 * �Ǘ�4�I�𔻒�
	 * 
	 * @return useManagement4
	 */
	public boolean isUseManagement4() {
		return this.useManagement4;
	}

	/**
	 * �Ǘ�5�I����ݒ肷��
	 * 
	 * @param useManagement5
	 */
	public void setUseManagement5(boolean useManagement5) {
		this.useManagement5 = useManagement5;
	}

	/**
	 * �Ǘ�5�I�𔻒�
	 * 
	 * @return useManagement5
	 */
	public boolean isUseManagement5() {
		return this.useManagement5;
	}

	/**
	 * �Ǘ�6�I����ݒ肷��
	 * 
	 * @param useManagement6
	 */
	public void setUseManagement6(boolean useManagement6) {
		this.useManagement6 = useManagement6;
	}

	/**
	 * �Ǘ�6�I�𔻒�
	 * 
	 * @return useManagement6
	 */
	public boolean isUseManagement6() {
		return this.useManagement6;
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
	 * @return codeLike
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
	 * @return namekLike
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
	 * @return namesLike
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
	 * @return isSumItem
	 */
	public boolean isSumItem() {
		return sumItem;
	}

	/**
	 * @param sumItem
	 */
	public void setSumItem(boolean sumItem) {
		this.sumItem = sumItem;
	}

	/**
	 * @return isTitleItem
	 */
	public boolean isTitleItem() {
		return titleItem;
	}

	/**
	 * @param titleItem
	 */
	public void setTitleItem(boolean titleItem) {
		this.titleItem = titleItem;
	}

	/**
	 * �Œ蕔��R�[�h
	 * 
	 * @param code ����R�[�h
	 */
	public void setDepartmentCode(String code) {
		this.departmentCode = code;
	}

	/**
	 * �Œ蕔��R�[�h
	 * 
	 * @return ����R�[�h
	 */
	public String getDepartmentCode() {
		return this.departmentCode;
	}

	/**
	 * �`�[���͎��
	 * 
	 * @return �`�[���͎��
	 */
	public SlipInputType getSlipInputType() {
		return slipInputType;
	}

	/**
	 * �`�[���͎��
	 * 
	 * @param slipInputType �`�[���͎��
	 */
	public void setSlipInputType(SlipInputType slipInputType) {
		this.slipInputType = slipInputType;
	}

	/**
	 * @return evaluation
	 */
	public boolean isEvaluation() {
		return evaluation;
	}

	/**
	 * @param evaluation
	 */
	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}

	/**
	 * bsCalculateErase���擾����B
	 * 
	 * @return boolean bsCalculateErase
	 */
	public boolean isBsCalculateErase() {
		return bsCalculateErase;
	}

	/**
	 * bsCalculateErase��ݒ肷��B
	 * 
	 * @param bsCalculateErase
	 */
	public void setBsCalculateErase(boolean bsCalculateErase) {
		this.bsCalculateErase = bsCalculateErase;
	}

	/**
	 * AR�Ȗڐ���敪
	 * 
	 * @return AR�Ȗڐ���敪
	 */
	public List<ARControlType> getArControlTypes() {
		return arControlTypes;
	}

	/**
	 * AR�Ȗڐ���敪
	 * 
	 * @param arControlType AR�Ȗڐ���敪
	 */
	public void setArControlTypes(ARControlType... arControlType) {
		for (ARControlType type : arControlType) {
			this.arControlTypes.add(type);
		}
	}

	/**
	 * �����Ȗڂ�����Ώۂɂ��邩
	 * 
	 * @return �����Ȗڂ�����Ώۂɂ��邩
	 */
	public boolean isCash() {
		return cash;
	}

	/**
	 * �����Ȗڂ�����Ώۂɂ��邩
	 * 
	 * @param cash
	 */
	public void setCash(boolean cash) {
		this.cash = cash;
	}

	/**
	 * �Ȗڑ̌n�R�[�hgetter
	 * 
	 * @return �Ȗڑ̌n�R�[�h
	 */
	public String getItemOrganizationCode() {
		return itemOrganizationCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�hsetter
	 * 
	 * @param itemOrganizationCode �Ȗڑ̌n�R�[�h
	 */
	public void setItemOrganizationCode(String itemOrganizationCode) {
		this.itemOrganizationCode = itemOrganizationCode;
	}

	/**
	 * paymentItem���擾����B
	 * 
	 * @return boolean paymentItem
	 */
	public boolean isPaymentItem() {
		return paymentItem;
	}

	/**
	 * paymentItem��ݒ肷��B
	 * 
	 * @param paymentItem
	 */
	public void setPaymentItem(boolean paymentItem) {
		this.paymentItem = paymentItem;
	}

	/**
	 * receiveItem���擾����B
	 * 
	 * @return boolean receiveItem
	 */
	public boolean isReceiveItem() {
		return receiveItem;
	}

	/**
	 * receiveItem��ݒ肷��B
	 * 
	 * @param receiveItem
	 */
	public void setReceiveItem(boolean receiveItem) {
		this.receiveItem = receiveItem;
	}

	/**
	 * �⏕�Ȗڂ��܂ނ�
	 * 
	 * @return subItem
	 */
	public boolean isSubItem() {
		return subItem;
	}

	/**
	 * �⏕�Ȗڂ��܂ނ�
	 * 
	 * @param subItem
	 */
	public void setSubItem(boolean subItem) {
		this.subItem = subItem;
	}

	/**
	 * �ǉ����������̎擾
	 * 
	 * @return addonWhereSql �ǉ���������
	 */
	public String getAddonWhereSql() {
		return addonWhereSql;
	}

	/**
	 * �ǉ����������̐ݒ�
	 * 
	 * @param addonWhereSql �ǉ���������
	 */
	public void setAddonWhereSql(String addonWhereSql) {
		this.addonWhereSql = addonWhereSql;
	}

	/**
	 * PK�w�胊�X�g
	 * 
	 * @return PK�w�胊�X�g
	 */
	public List<ItemSearchPrimaryKey> getPkList() {
		return pkList;
	}

	/**
	 * PK�w�胊�X�g
	 * 
	 * @param pkList
	 */
	public void setPkList(List<ItemSearchPrimaryKey> pkList) {
		this.pkList = pkList;
	}

	/**
	 * PK�w�胊�X�g�ɒǉ�
	 * 
	 * @param pk
	 */
	public void addPkList(ItemSearchPrimaryKey pk) {
		if (this.pkList == null) {
			pkList = new ArrayList();
		}
		this.pkList.add(pk);
	}

	/**
	 * PK�w�胊�X�g
	 * 
	 * @param pkList
	 */
	public void addPkList(String kaiCode, String kmkCode, String hkmCode, String ukmCode) {
		if (this.pkList == null) {
			pkList = new ArrayList();
		}
		this.pkList.add(new ItemSearchPrimaryKey(kaiCode, kmkCode, hkmCode, ukmCode));
	}

}
