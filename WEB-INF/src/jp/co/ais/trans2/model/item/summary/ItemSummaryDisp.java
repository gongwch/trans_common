package jp.co.ais.trans2.model.item.summary;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �ȖڏW�vEntity
 * 
 * @author AIS
 */
public class ItemSummaryDisp extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �e�v���O�����R�[�h */
	protected String parentCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �ȖڃO���[�v�ɑ�����v���O�����Q */
	protected Item item = null;

	/** �ȖڏW�v�O���[�v�ɑ�����v���O�����Q */
	protected ItemSummary itemSummary = null;

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ItemSummaryDisp clone() {
		try {
			ItemSummaryDisp bean = (ItemSummaryDisp) super.clone();
			bean.setItem(this.item);
			bean.setItemSummary(this.itemSummary);
			return bean;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �m�[�h�ɕ\�����閼�̂��擾����
	 * 
	 * @return �m�[�h�ɕ\�����閼��
	 */
	public String getViewName() {
		return this.toString();
	}

	/**
	 * �I�u�W�F�N�g�̃I���W�i�������\����Ԃ�.
	 * 
	 * @return ����
	 */
	@Override
	public String toString() {
		if (itemSummary == null) {
			return "";
		}
		return itemSummary.getKmkCode() + " " + itemSummary.getKokName() + " ( " + itemSummary.getOdr() + " ) ";
	}

	/**
	 * �G���e�B�e�B�̔�r�Ɏg�p����B
	 * 
	 * @param obj ��r
	 * @return boolean
	 */
	public boolean equals(ItemSummaryDisp obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
		}
	}

	/**
	 * ��ЃR�[�h���擾����B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �e�v���O�����R�[�h���擾����B
	 * 
	 * @return �e�v���O�����R�[�h
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * �e�v���O�����R�[�h��ݒ肷��B
	 * 
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * �ȖڃR�[�h���擾����B
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * �ȖڃR�[�h��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * �ȖڃO���[�v�����擾����B
	 * 
	 * @return �ȖڃO���[�v
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * �ȖڃO���[�v��ݒ肷��B
	 * 
	 * @param item
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * �ȖڏW�v�O���[�v�����擾����B
	 * 
	 * @return �ȖڏW�v�O���[�v
	 */
	public ItemSummary getItemSummary() {
		return itemSummary;
	}

	/**
	 * �ȖڏW�v�O���[�v��ݒ肷��B
	 * 
	 * @param itemSummary
	 */
	public void setItemSummary(ItemSummary itemSummary) {
		this.itemSummary = itemSummary;
	}
}
