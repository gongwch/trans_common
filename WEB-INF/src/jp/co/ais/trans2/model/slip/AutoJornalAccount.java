package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * �����d��Ȗ�
 */
public class AutoJornalAccount extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** �Ȗڐ���敪 */
	protected int kind;

	/** �Ȗڐ���敪���� */
	protected String kindName;

	/** ����R�[�h */
	protected String depertmentCode;

	/** ���喼�� */
	protected String depertmentName;

	/** ���嗪�� */
	protected String depertmentNames;

	/** �ȖڃR�[�h */
	protected String itemCode;

	/** �Ȗږ��� */
	protected String itemName;

	/** �Ȗڗ��� */
	protected String itemNames;

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode;

	/** �⏕�Ȗږ��� */
	protected String subItemName;

	/** �⏕�Ȗڗ��� */
	protected String subItemNames;

	/** ����ȖڃR�[�h */
	protected String detailItemCode;

	/** ����Ȗږ��� */
	protected String detailItemName;

	/** ����Ȗڗ��� */
	protected String detailItemNames;

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
	 * ����R�[�h
	 * 
	 * @return ����R�[�h
	 */
	public String getDepertmentCode() {
		return depertmentCode;
	}

	/**
	 * ����R�[�h
	 * 
	 * @param depertmentCode ����R�[�h
	 */
	public void setDepertmentCode(String depertmentCode) {
		this.depertmentCode = depertmentCode;
	}

	/**
	 * ���喼��
	 * 
	 * @return ���喼��
	 */
	public String getDepertmentName() {
		return depertmentName;
	}

	/**
	 * ���喼��
	 * 
	 * @param depertmentName ���喼��
	 */
	public void setDepertmentName(String depertmentName) {
		this.depertmentName = depertmentName;
	}

	/**
	 * ���嗪��
	 * 
	 * @return ���嗪��
	 */
	public String getDepertmentNames() {
		return depertmentNames;
	}

	/**
	 * ���嗪��
	 * 
	 * @param depertmentNames ���嗪��
	 */
	public void setDepertmentNames(String depertmentNames) {
		this.depertmentNames = depertmentNames;
	}

	/**
	 * ����ȖڃR�[�h
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * ����ȖڃR�[�h
	 * 
	 * @param detailItemCode ����ȖڃR�[�h
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * ����Ȗږ���
	 * 
	 * @return ����Ȗږ���
	 */
	public String getDetailItemName() {
		return detailItemName;
	}

	/**
	 * ����Ȗږ���
	 * 
	 * @param detailItemName ����Ȗږ���
	 */
	public void setDetailItemName(String detailItemName) {
		this.detailItemName = detailItemName;
	}

	/**
	 * ����Ȗڗ���
	 * 
	 * @return ����Ȗڗ���
	 */
	public String getDetailItemNames() {
		return detailItemNames;
	}

	/**
	 * ����Ȗڗ���
	 * 
	 * @param detailItemNames ����Ȗڗ���
	 */
	public void setDetailItemNames(String detailItemNames) {
		this.detailItemNames = detailItemNames;
	}

	/**
	 * �ȖڃR�[�h
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * �ȖڃR�[�h
	 * 
	 * @param itemCode �ȖڃR�[�h
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * �Ȗږ���
	 * 
	 * @return �Ȗږ���
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * �Ȗږ���
	 * 
	 * @param itemName �Ȗږ���
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * �Ȗڗ���
	 * 
	 * @return �Ȗڗ���
	 */
	public String getItemNames() {
		return itemNames;
	}

	/**
	 * �Ȗڗ���
	 * 
	 * @param itemNames �Ȗڗ���
	 */
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}

	/**
	 * �⏕�ȖڃR�[�h
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h
	 * 
	 * @param subItemCode �⏕�ȖڃR�[�h
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * �⏕�Ȗږ���
	 * 
	 * @return �⏕�Ȗږ���
	 */
	public String getSubItemName() {
		return subItemName;
	}

	/**
	 * �⏕�Ȗږ���
	 * 
	 * @param subItemName �⏕�Ȗږ���
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * �⏕�Ȗڗ���
	 * 
	 * @return �⏕�Ȗڗ���
	 */
	public String getSubItemNames() {
		return subItemNames;
	}

	/**
	 * �⏕�Ȗڗ���
	 * 
	 * @param subItemNames �⏕�Ȗڗ���
	 */
	public void setSubItemNames(String subItemNames) {
		this.subItemNames = subItemNames;
	}

	/**
	 * �Ȗڐ���敪
	 * 
	 * @return �Ȗڐ���敪
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * �Ȗڐ���敪
	 * 
	 * @param kind �Ȗڐ���敪
	 */
	public void setKind(int kind) {
		this.kind = kind;
	}

	/**
	 * �Ȗڐ���敪����
	 * 
	 * @return �Ȗڐ���敪����
	 */
	public String getKindName() {
		return kindName;
	}

	/**
	 * �Ȗڐ���敪����
	 * 
	 * @param kindName �Ȗڐ���敪����
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/**
	 * �Ȗڂ����ꂩ�ǂ����𔻒肷��.
	 * 
	 * @param item �ȖڃR�[�h
	 * @param subItem �⏕�ȖڃR�[�h
	 * @param detailItem ����ȖڃR�[�h
	 * @return true:����
	 */
	public boolean equalsItem(String item, String subItem, String detailItem) {
		boolean isEqual = Util.avoidNull(itemCode).equals(Util.avoidNull(item));
		isEqual &= Util.avoidNull(subItemCode).equals(Util.avoidNull(subItem));
		isEqual &= Util.avoidNull(detailItemCode).equals(Util.avoidNull(detailItem));

		return isEqual;
	}
}
