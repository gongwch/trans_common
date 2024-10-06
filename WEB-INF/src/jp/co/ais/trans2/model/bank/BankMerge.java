package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ��s���p�����
 */
public class BankMerge extends TransferBase {

	/** ����s�R�[�h */
	protected String oldBankCode;

	/** ����s�x�X�R�[�h */
	protected String oldBankOffCode;

	/** ����s�� */
	protected String oldBankName;

	/** �V��s�R�[�h */
	protected String newBankCode;

	/** �V��s�� */
	protected String newBankName;

	/** �V��s�J�i(FB) */
	protected String newBankKanaFb;

	/** �V��s�J�i */
	protected String newBankKana;

	/** �V��s�x�X�R�[�h */
	protected String newBankOffCode;

	/** �V��s�x�X�� */
	protected String newBankOffName;

	/** �V��s�x�X�J�i(FB) */
	protected String newBankOffKanaFb;

	/** �V��s�x�X�J�i */
	protected String newBankOffKana;

	/** �J�n�N���� */
	protected Date dateFrom;

	/** �I���N���� */
	protected Date dateTo;

	/** �X�V�� */
	protected int renew;

	/** �ǉ��� */
	protected int add;


	/**
	 * @return ����s�R�[�h
	 */
	public String getOldBankCode() {
		return oldBankCode;
	}

	/**
	 * @param oldBankCode ����s�R�[�h
	 */
	public void setOldBankCode(String oldBankCode) {
		this.oldBankCode = oldBankCode;
	}

	/**
	 * @return ����s�x�X�R�[�h
	 */
	public String getOldBankOffCode() {
		return oldBankOffCode;
	}

	/**
	 * @param oldBankOffCode ����s�x�X�R�[�h
	 */
	public void setOldBankOffCode(String oldBankOffCode) {
		this.oldBankOffCode = oldBankOffCode;
	}

	/**
	 * @return ����s��
	 */
	public String getOldBankName() {
		return oldBankName;
	}

	/**
	 * @param oldBankName ����s��
	 */
	public void setOldBankName(String oldBankName) {
		this.oldBankName = oldBankName;
	}

	/**
	 * @return �V��s�R�[�h
	 */
	public String getNewBankCode() {
		return newBankCode;
	}

	/**
	 * @param newBankCode �V��s�R�[�h
	 */
	public void setNewBankCode(String newBankCode) {
		this.newBankCode = newBankCode;
	}

	/**
	 * @return �V��s��
	 */
	public String getNewBankName() {
		return newBankName;
	}

	/**
	 * @param newBankName �V��s��
	 */
	public void setNewBankName(String newBankName) {
		this.newBankName = newBankName;
	}

	/**
	 * @return �V��s�J�i(FB)
	 */
	public String getNewBankKanaFb() {
		return newBankKanaFb;
	}

	/**
	 * @param newBankKanaFb �V��s�J�i(FB)
	 */
	public void setNewBankKanaFb(String newBankKanaFb) {
		this.newBankKanaFb = newBankKanaFb;
	}

	/**
	 * @return �V��s�J�i
	 */
	public String getNewBankKana() {
		return newBankKana;
	}

	/**
	 * @param newBankKana �V��s�J�i
	 */
	public void setNewBankKana(String newBankKana) {
		this.newBankKana = newBankKana;
	}

	/**
	 * @return �V��s�x�X�R�[�h
	 */
	public String getNewBankOffCode() {
		return newBankOffCode;
	}

	/**
	 * @param newBankOffCode �V��s�x�X�R�[�h
	 */
	public void setNewBankOffCode(String newBankOffCode) {
		this.newBankOffCode = newBankOffCode;
	}

	/**
	 * @return �V��s�x�X��
	 */
	public String getNewBankOffName() {
		return newBankOffName;
	}

	/**
	 * @param newBankOffName �V��s�x�X��
	 */
	public void setNewBankOffName(String newBankOffName) {
		this.newBankOffName = newBankOffName;
	}

	/**
	 * @return �V��s�x�X�J�i(FB)
	 */
	public String getNewBankOffKanaFb() {
		return newBankOffKanaFb;
	}

	/**
	 * @param newBankOffKanaFb �V��s�x�X�J�i(FB)
	 */
	public void setNewBankOffKanaFb(String newBankOffKanaFb) {
		this.newBankOffKanaFb = newBankOffKanaFb;
	}

	/**
	 * @return �V��s�x�X�J�i
	 */
	public String getNewBankOffKana() {
		return newBankOffKana;
	}

	/**
	 * @param newBankOffKana �V��s�x�X�J�i
	 */
	public void setNewBankOffKana(String newBankOffKana) {
		this.newBankOffKana = newBankOffKana;
	}

	/**
	 * @return �J�n�N����
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom �J�n�N����
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return �I���N����
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo �I���N����
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	/**
	 * @return renew
	 */
	public int getRenew() {
		return renew;
	}

	/**
	 * @param renew
	 */
	public void setRenew(int renew) {
		this.renew = renew;
	}

	/**
	 * @return add
	 */
	public int getAdd() {
		return add;
	}

	/**
	 * @param add
	 */
	public void setAdd(int add) {
		this.add = add;
	}


}
