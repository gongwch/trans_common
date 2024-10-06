package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;

/**
 * MG0070ItemSummaryMaster - �ȖڏW�v�}�X�^ - Bean Class
 * 
 * @author AIS
 */
public class ItemSummary extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �Ȗڑ̌n�R�[�h */
	protected String kmtCode;

	/** �Ȗڑ̌n���� */
	protected String kmtName;

	/** �ȖڃR�[�h */
	protected String kmkCode;

	/** �Ȗڗ��� */
	protected String kmkName;

	/** �Ȗڌ������� */
	protected String kmkNamek;

	/** ���\�Ȗږ��� */
	protected String kokName;

	/** �W�v����ȖڃR�[�h */
	protected String sumCode;

	/** �W�v����Ȗڗ��� */
	protected String sumName;

	/** �Ȗڏo�͏��� */
	protected String odr;

	/** �\���敪 */
	protected int hyjKbn;

	/** �J�n�N���� */
	protected Date dateFrom;

	/** �I���N���� */
	protected Date dateTo;

	/** �o�^���t */
	protected Date inpDate;

	/** �W�v�敪 */
	protected ItemSumType itemSumType = null;

	/** �ݎ؋敪 */
	protected Dc dc = null;

	/** �Ȗڎ�� */
	protected ItemType itemType = null;

	/**
	 * @return ��ЃR�[�h���擾
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h���Z�b�g
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �J�n�N�������擾
	 * 
	 * @return dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * �J�n�N�������Z�b�g
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * �I���N�������擾
	 * 
	 * @return dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * �I���N�������Z�b�g
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * �\���敪���擾
	 * 
	 * @return hyjKbn
	 */
	public int getHyjKbn() {
		return hyjKbn;
	}

	/**
	 * �\���敪���Z�b�g
	 * 
	 * @param hyjKbn
	 */
	public void setHyjKbn(int hyjKbn) {
		this.hyjKbn = hyjKbn;
	}

	/**
	 * �ȖڃR�[�h���擾
	 * 
	 * @return kmkCode
	 */
	public String getKmkCode() {
		return kmkCode;
	}

	/**
	 * �ȖڃR�[�h���Z�b�g
	 * 
	 * @param kmkCode
	 */
	public void setKmkCode(String kmkCode) {
		this.kmkCode = kmkCode;
	}

	/**
	 * �Ȗڗ��̂��擾
	 * 
	 * @return kmkName
	 */
	public String getKmkName() {
		return kmkName;
	}

	/**
	 * �Ȗڗ��̂��Z�b�g
	 * 
	 * @param kmkName
	 */
	public void setKmkName(String kmkName) {
		this.kmkName = kmkName;
	}

	/**
	 * �Ȗڌ������̂��擾
	 * 
	 * @return kmkNamek
	 */
	public String getKmkNamek() {
		return kmkNamek;
	}

	/**
	 * �Ȗڌ������̂��Z�b�g
	 * 
	 * @param kmkNamek
	 */
	public void setKmkNamek(String kmkNamek) {
		this.kmkNamek = kmkNamek;
	}

	/**
	 * �Ȗڑ̌n�R�[�h���擾
	 * 
	 * @return kmtCode
	 */
	public String getKmtCode() {
		return kmtCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�h���Z�b�g
	 * 
	 * @param kmtCode
	 */
	public void setKmtCode(String kmtCode) {
		this.kmtCode = kmtCode;
	}

	/**
	 * �Ȗڑ̌n���̂��擾
	 * 
	 * @return kmtName
	 */
	public String getKmtName() {
		return kmtName;
	}

	/**
	 * �Ȗڑ̌n���̂��Z�b�g
	 * 
	 * @param kmtName
	 */
	public void setKmtName(String kmtName) {
		this.kmtName = kmtName;
	}

	/**
	 * ���\�Ȗږ��̂��擾
	 * 
	 * @return kokName
	 */
	public String getKokName() {
		return kokName;
	}

	/**
	 * ���\�Ȗږ��̂��Z�b�g
	 * 
	 * @param kokName
	 */
	public void setKokName(String kokName) {
		this.kokName = kokName;
	}

	/**
	 * �Ȗڏo�͏������擾
	 * 
	 * @return odr
	 */
	public String getOdr() {
		return odr;
	}

	/**
	 * �Ȗڏo�͏������Z�b�g
	 * 
	 * @param odr
	 */
	public void setOdr(String odr) {
		this.odr = odr;
	}

	/**
	 * �W�v����ȖڃR�[�h���擾
	 * 
	 * @return sumCode
	 */
	public String getSumCode() {
		return sumCode;
	}

	/**
	 * �W�v����ȖڃR�[�h���Z�b�g
	 * 
	 * @param sumCode
	 */
	public void setSumCode(String sumCode) {
		this.sumCode = sumCode;
	}

	/**
	 * �W�v����Ȗڗ��̂��擾
	 * 
	 * @return sumName
	 */
	public String getSumName() {
		return sumName;
	}

	/**
	 * �W�v����Ȗڗ��̂��Z�b�g
	 * 
	 * @param sumName
	 */
	public void setSumName(String sumName) {
		this.sumName = sumName;
	}

	/**
	 * �o�^���t���擾
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * * �o�^���t���Z�b�g
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * �W�v�敪���擾
	 * 
	 * @return �W�v�敪
	 */
	public ItemSumType getItemSumType() {
		return this.itemSumType;
	}

	/**
	 * �W�v�敪���Z�b�g
	 * 
	 * @param itemSumType �W�v�敪
	 */
	public void setItemSumType(ItemSumType itemSumType) {
		this.itemSumType = itemSumType;
	}

	/**
	 * �ݎ؋敪���擾
	 * 
	 * @return �ݎ؋敪
	 */
	public Dc getDc() {
		return this.dc;
	}

	/**
	 * �ݎ؋敪���Z�b�g
	 * 
	 * @param dc �ݎ؋敪
	 */
	public void setDc(Dc dc) {
		this.dc = dc;
	}

	/**
	 * �Ȗڎ�ʂ��擾
	 * 
	 * @return �Ȗڎ��
	 */
	public ItemType getItemType() {
		return this.itemType;
	}

	/**
	 * �Ȗڎ�ʂ��Z�b�g
	 * 
	 * @param itemType �Ȗڎ��
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
}
