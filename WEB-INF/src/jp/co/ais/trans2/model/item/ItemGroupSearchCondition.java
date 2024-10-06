package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * �ȖځE�⏕�E����̏W�������N���X�B<br>
 * �e������setter���ȖځA�⏕�A����S�ĂɓK�p�����B
 */
public class ItemGroupSearchCondition extends TransferBase {

	/** �Ȗڏ��� */
	protected ItemSearchCondition itemCondition;

	/** �⏕�Ȗڏ��� */
	protected SubItemSearchCondition subItemCondition;

	/** ����Ȗڏ��� */
	protected DetailItemSearchCondition detailItemCondition;

	/**
	 * �Ȗڏ���
	 * 
	 * @return �Ȗڏ���
	 */
	public ItemSearchCondition getItemCondition() {
		return itemCondition;
	}

	/**
	 * �Ȗڏ���
	 * 
	 * @param itemCondition �Ȗڏ���
	 */
	public void setItemCondition(ItemSearchCondition itemCondition) {
		this.itemCondition = itemCondition;
	}

	/**
	 * �⏕�Ȗڏ���
	 * 
	 * @return �⏕�Ȗڏ���
	 */
	public SubItemSearchCondition getSubItemCondition() {
		return subItemCondition;
	}

	/**
	 * �⏕�Ȗڏ���
	 * 
	 * @param subItemCondition �⏕�Ȗڏ���
	 */
	public void setSubItemCondition(SubItemSearchCondition subItemCondition) {
		this.subItemCondition = subItemCondition;
	}

	/**
	 * ����Ȗڏ���
	 * 
	 * @return ����Ȗڏ���
	 */
	public DetailItemSearchCondition getDetailItemCondition() {
		return detailItemCondition;
	}

	/**
	 * ����Ȗڏ���
	 * 
	 * @param detailItemCondition ����Ȗڏ���
	 */
	public void setDetailItemCondition(DetailItemSearchCondition detailItemCondition) {
		this.detailItemCondition = detailItemCondition;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param code ��ЃR�[�h
	 */
	public void setCompanyCode(String code) {
		itemCondition.setCompanyCode(code);

		if (subItemCondition != null) {
			subItemCondition.setCompanyCode(code);

			if (detailItemCondition != null) {
				detailItemCondition.setCompanyCode(code);
			}
		}
	}

	/**
	 * ����R�[�h�ݒ�
	 * 
	 * @param code ����R�[�h
	 */
	public void setDepartmentCode(String code) {
		itemCondition.setDepartmentCode(code);
	}

	/**
	 * ����R�[�h�ݒ�
	 * 
	 * @return ����R�[�h
	 */
	public String getDepartmentCode() {
		return itemCondition.getDepartmentCode();
	}

	/**
	 * �L�����ݒ�
	 * 
	 * @param date �L����
	 */
	public void setValidTerm(Date date) {
		itemCondition.setValidTerm(date);

		if (subItemCondition != null) {
			subItemCondition.setValidTerm(date);

			if (detailItemCondition != null) {
				detailItemCondition.setValidTerm(date);
			}
		}
	}

	/**
	 * �����Ȗڂ�����Ώۂɂ��邩
	 * @param cash
	 */
	public void setCash(boolean cash) {
		itemCondition.setCash(cash);
		if (subItemCondition != null) {
			subItemCondition.setCash(cash);
			if (detailItemCondition != null) {
				detailItemCondition.setCash(cash);
			}
		}
	}

	/**
	 * �Ȗڑ̌n�R�[�hsetter
	 * @param itemOrganizationCode �Ȗڑ̌n�R�[�h
	 */
	public void setItemOrganizationCode(String itemOrganizationCode) {
		itemCondition.setItemOrganizationCode(itemOrganizationCode);
		if (subItemCondition != null) {
			subItemCondition.setItemOrganizationCode(itemOrganizationCode);
			if (detailItemCondition != null) {
				detailItemCondition.setItemOrganizationCode(itemOrganizationCode);
			}
		}
	}

}
