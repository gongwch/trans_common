package jp.co.ais.trans2.model.mlit.item;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �A�����уA�C�e���}�X�^�̌�������
 * 
 * @author AIS
 */
public class YJItemSearchCondition extends TransferBase implements Cloneable {

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public YJItemSearchCondition clone() {
		try {
			return (YJItemSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �A�C�e���R�[�h */
	protected String itemCode = null;

	/** �T�u�A�C�e���R�[�h */
	protected String subItemCode = null;

	/** �J�n�A�C�e���R�[�h */
	protected String itemCodeFrom = null;

	/** �I���A�C�e���R�[�h */
	protected String itemCodeTo = null;

	/** �J�n�T�u�A�C�e���R�[�h */
	protected String subItemCodeFrom = null;

	/** �I���T�u�A�C�e���R�[�h */
	protected String subItemCodeTo = null;

	/** �A�C�e���R�[�h�O����v */
	protected String itemCodeLike = null;

	/** �A�C�e������like */
	protected String itemNameLike = null;

	/** �A�C�e���R�[�h�O����v */
	protected String subItemCodeLike = null;

	/** �A�C�e������like */
	protected String subItemNameLike = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �A�C�e���R�[�h�̎擾
	 * 
	 * @return itemCode �A�C�e���R�[�h
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * �A�C�e���R�[�h�̐ݒ�
	 * 
	 * @param itemCode �A�C�e���R�[�h
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * �T�u�A�C�e���R�[�h�̎擾
	 * 
	 * @return subItemCode �T�u�A�C�e���R�[�h
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * �T�u�A�C�e���R�[�h�̐ݒ�
	 * 
	 * @param subItemCode �T�u�A�C�e���R�[�h
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * �J�n�A�C�e���R�[�h�̎擾
	 * 
	 * @return itemCodeFrom �J�n�A�C�e���R�[�h
	 */
	public String getItemCodeFrom() {
		return itemCodeFrom;
	}

	/**
	 * �J�n�A�C�e���R�[�h�̐ݒ�
	 * 
	 * @param itemCodeFrom �J�n�A�C�e���R�[�h
	 */
	public void setItemCodeFrom(String itemCodeFrom) {
		this.itemCodeFrom = itemCodeFrom;
	}

	/**
	 * �I���A�C�e���R�[�h�̎擾
	 * 
	 * @return itemCodeTo �I���A�C�e���R�[�h
	 */
	public String getItemCodeTo() {
		return itemCodeTo;
	}

	/**
	 * �I���A�C�e���R�[�h�̐ݒ�
	 * 
	 * @param itemCodeTo �I���A�C�e���R�[�h
	 */
	public void setItemCodeTo(String itemCodeTo) {
		this.itemCodeTo = itemCodeTo;
	}

	/**
	 * �J�n�T�u�A�C�e���R�[�h�̎擾
	 * 
	 * @return subItemCodeFrom �J�n�T�u�A�C�e���R�[�h
	 */
	public String getSubItemCodeFrom() {
		return subItemCodeFrom;
	}

	/**
	 * �J�n�T�u�A�C�e���R�[�h�̐ݒ�
	 * 
	 * @param subItemCodeFrom �J�n�T�u�A�C�e���R�[�h
	 */
	public void setSubItemCodeFrom(String subItemCodeFrom) {
		this.subItemCodeFrom = subItemCodeFrom;
	}

	/**
	 * �I���T�u�A�C�e���R�[�h�̎擾
	 * 
	 * @return subItemCodeTo �I���T�u�A�C�e���R�[�h
	 */
	public String getSubItemCodeTo() {
		return subItemCodeTo;
	}

	/**
	 * �I���T�u�A�C�e���R�[�h�̐ݒ�
	 * 
	 * @param subItemCodeTo �I���T�u�A�C�e���R�[�h
	 */
	public void setSubItemCodeTo(String subItemCodeTo) {
		this.subItemCodeTo = subItemCodeTo;
	}

	/**
	 * �A�C�e���R�[�h�O����v�̎擾
	 * 
	 * @return itemCodeLike �A�C�e���R�[�h�O����v
	 */
	public String getItemCodeLike() {
		return itemCodeLike;
	}

	/**
	 * �A�C�e���R�[�h�O����v�̐ݒ�
	 * 
	 * @param itemCodeLike �A�C�e���R�[�h�O����v
	 */
	public void setItemCodeLike(String itemCodeLike) {
		this.itemCodeLike = itemCodeLike;
	}

	/**
	 * �A�C�e������like�̎擾
	 * 
	 * @return itemNameLike �A�C�e������like
	 */
	public String getItemNameLike() {
		return itemNameLike;
	}

	/**
	 * �A�C�e������like�̐ݒ�
	 * 
	 * @param itemNameLike �A�C�e������like
	 */
	public void setItemNameLike(String itemNameLike) {
		this.itemNameLike = itemNameLike;
	}

	/**
	 * �T�u�A�C�e���R�[�h�O����v�̎擾
	 * 
	 * @return subItemCodeLike �T�u�A�C�e���R�[�h�O����v
	 */
	public String getSubItemCodeLike() {
		return subItemCodeLike;
	}

	/**
	 * �T�u�A�C�e���R�[�h�O����v�̐ݒ�
	 * 
	 * @param subItemCodeLike �T�u�A�C�e���R�[�h�O����v
	 */
	public void setSubItemCodeLike(String subItemCodeLike) {
		this.subItemCodeLike = subItemCodeLike;
	}

	/**
	 * �T�u�A�C�e������like�̎擾
	 * 
	 * @return subItemNameLike �T�u�A�C�e������like
	 */
	public String getSubItemNameLike() {
		return subItemNameLike;
	}

	/**
	 * �T�u�A�C�e������like�̐ݒ�
	 * 
	 * @param subItemNameLike �T�u�A�C�e������like
	 */
	public void setSubItemNameLike(String subItemNameLike) {
		this.subItemNameLike = subItemNameLike;
	}
}
