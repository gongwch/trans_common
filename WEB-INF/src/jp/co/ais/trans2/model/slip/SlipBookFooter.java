package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans2.common.ledger.*;

/**
 * �`�[���[�t�b�^�[
 * 
 * @author AIS
 */
public class SlipBookFooter extends LedgerSheetFooter {

	/** �ؕ��M�ݍ��v */
	protected BigDecimal debitAmount = null;

	/** �ݕ��M�ݍ��v */
	protected BigDecimal creditAmount = null;

	/** �����_���� */
	protected int decimalPoint = 0;

	/** �ؕ��O�ݍ��v */
	protected List<BigDecimal> foreignDebitAmountList = new ArrayList<BigDecimal>();

	/** �ݕ��O�ݍ��v */
	protected List<BigDecimal> foreignCreditAmountList = new ArrayList<BigDecimal>();

	/** �O�� */
	protected List<String> foreignCurrencyCodeList = new ArrayList<String>();

	/** �O�݂̏����_���� */
	protected List<Integer> foreignDecimalPointList = new ArrayList<Integer>();

	/** �O�݌��� */
	protected int maxForeignCurrencyCount = 10;

	/**
	 * �R���X�g���N�^�[
	 */
	public SlipBookFooter() {
		for (int i = 0; i < getMaxForeignCurrencyCount(); i++) {
			foreignDebitAmountList.add(null);
			foreignCreditAmountList.add(null);
			foreignCurrencyCodeList.add(null);
			foreignDecimalPointList.add(0);
		}
	}

	/**
	 * �ؕ��M�ݍ��v�̎擾
	 * 
	 * @return debitAmount �ؕ��M�ݍ��v
	 */
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	/**
	 * �ؕ��M�ݍ��v�̐ݒ�
	 * 
	 * @param debitAmount �ؕ��M�ݍ��v
	 */
	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}

	/**
	 * �ݕ��M�ݍ��v�̎擾
	 * 
	 * @return creditAmount �ݕ��M�ݍ��v
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * �ݕ��M�ݍ��v�̐ݒ�
	 * 
	 * @param creditAmount �ݕ��M�ݍ��v
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * �����_�����̎擾
	 * 
	 * @return decimalPoint �����_����
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * �����_�����̐ݒ�
	 * 
	 * @param decimalPoint �����_����
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * �ؕ��O��1���v�̎擾
	 * 
	 * @return foreignDebitAmount1 �ؕ��O��1���v
	 */
	public BigDecimal getForeignDebitAmount1() {
		return getForeignDebitAmount(0);
	}

	/**
	 * �ؕ��O��1���v�̐ݒ�
	 * 
	 * @param foreignDebitAmount1 �ؕ��O��1���v
	 */
	public void setForeignDebitAmount1(BigDecimal foreignDebitAmount1) {
		setForeignDebitAmount(0, foreignDebitAmount1);
	}

	/**
	 * �ݕ��O��1���v�̎擾
	 * 
	 * @return foreignCreditAmount1 �ݕ��O��1���v
	 */
	public BigDecimal getForeignCreditAmount1() {
		return getForeignCreditAmount(0);
	}

	/**
	 * �ݕ��O��1���v�̐ݒ�
	 * 
	 * @param foreignCreditAmount1 �ݕ��O��1���v
	 */
	public void setForeignCreditAmount1(BigDecimal foreignCreditAmount1) {
		setForeignCreditAmount(0, foreignCreditAmount1);
	}

	/**
	 * �O��1�̎擾
	 * 
	 * @return foreignCurrencyCode1 �O��1
	 */
	public String getForeignCurrencyCode1() {
		return getForeignCurrencyCode(0);
	}

	/**
	 * �O��1�̐ݒ�
	 * 
	 * @param foreignCurrencyCode1 �O��1
	 */
	public void setForeignCurrencyCode1(String foreignCurrencyCode1) {
		setForeignCurrencyCode(0, foreignCurrencyCode1);
	}

	/**
	 * �O��1�̏����_�����̎擾
	 * 
	 * @return foreignDecimalPoint1 �O��1�̏����_����
	 */
	public int getForeignDecimalPoint1() {
		return getForeignDecimalPoint(0);
	}

	/**
	 * �O��1�̏����_�����̐ݒ�
	 * 
	 * @param foreignDecimalPoint1 �O��1�̏����_����
	 */
	public void setForeignDecimalPoint1(int foreignDecimalPoint1) {
		setForeignDecimalPoint(0, foreignDecimalPoint1);
	}

	/**
	 * �ؕ��O��2���v�̎擾
	 * 
	 * @return foreignDebitAmount2 �ؕ��O��2���v
	 */
	public BigDecimal getForeignDebitAmount2() {
		return getForeignDebitAmount(1);
	}

	/**
	 * �ؕ��O��2���v�̐ݒ�
	 * 
	 * @param foreignDebitAmount2 �ؕ��O��2���v
	 */
	public void setForeignDebitAmount2(BigDecimal foreignDebitAmount2) {
		setForeignDebitAmount(1, foreignDebitAmount2);
	}

	/**
	 * �ݕ��O��2���v�̎擾
	 * 
	 * @return foreignCreditAmount2 �ݕ��O��2���v
	 */
	public BigDecimal getForeignCreditAmount2() {
		return getForeignCreditAmount(1);
	}

	/**
	 * �ݕ��O��2���v�̐ݒ�
	 * 
	 * @param foreignCreditAmount2 �ݕ��O��2���v
	 */
	public void setForeignCreditAmount2(BigDecimal foreignCreditAmount2) {
		setForeignCreditAmount(1, foreignCreditAmount2);
	}

	/**
	 * �O��2�̎擾
	 * 
	 * @return foreignCurrencyCode2 �O��2
	 */
	public String getForeignCurrencyCode2() {
		return getForeignCurrencyCode(1);
	}

	/**
	 * �O��2�̐ݒ�
	 * 
	 * @param foreignCurrencyCode2 �O��2
	 */
	public void setForeignCurrencyCode2(String foreignCurrencyCode2) {
		setForeignCurrencyCode(1, foreignCurrencyCode2);
	}

	/**
	 * �O��2�̏����_�����̎擾
	 * 
	 * @return foreignDecimalPoint2 �O��2�̏����_����
	 */
	public int getForeignDecimalPoint2() {
		return getForeignDecimalPoint(1);
	}

	/**
	 * �O��2�̏����_�����̐ݒ�
	 * 
	 * @param foreignDecimalPoint2 �O��2�̏����_����
	 */
	public void setForeignDecimalPoint2(int foreignDecimalPoint2) {
		setForeignDecimalPoint(1, foreignDecimalPoint2);
	}

	/**
	 * �ؕ��O��index���v�̎擾
	 * 
	 * @param index
	 * @return foreignDebitAmount �ؕ��O��index���v
	 */
	public BigDecimal getForeignDebitAmount(int index) {
		return foreignDebitAmountList.get(index);
	}

	/**
	 * �ؕ��O��index���v�̐ݒ�
	 * 
	 * @param index
	 * @param foreignDebitAmount �ؕ��O��index���v
	 */
	public void setForeignDebitAmount(int index, BigDecimal foreignDebitAmount) {
		this.foreignDebitAmountList.set(index, foreignDebitAmount);
	}

	/**
	 * �ݕ��O��index���v�̎擾
	 * 
	 * @param index
	 * @return foreignCreditAmount �ݕ��O��index���v
	 */
	public BigDecimal getForeignCreditAmount(int index) {
		return foreignCreditAmountList.get(index);
	}

	/**
	 * �ݕ��O��index���v�̐ݒ�
	 * 
	 * @param index
	 * @param foreignCreditAmount �ݕ��O��index���v
	 */
	public void setForeignCreditAmount(int index, BigDecimal foreignCreditAmount) {
		this.foreignCreditAmountList.set(index, foreignCreditAmount);
	}

	/**
	 * �O�݂̎擾
	 * 
	 * @param index
	 * @return foreignCurrencyCode �O��
	 */
	public String getForeignCurrencyCode(int index) {
		return foreignCurrencyCodeList.get(index);
	}

	/**
	 * �O�݂̐ݒ�
	 * 
	 * @param index
	 * @param foreignCurrencyCode �O��
	 */
	public void setForeignCurrencyCode(int index, String foreignCurrencyCode) {
		this.foreignCurrencyCodeList.set(index, foreignCurrencyCode);
	}

	/**
	 * �O�݂̏����_�����̎擾
	 * 
	 * @param index
	 * @return foreignDecimalPoint2 �O�݂̏����_����
	 */
	public int getForeignDecimalPoint(int index) {
		return foreignDecimalPointList.get(index);
	}

	/**
	 * �O�݂̏����_�����̐ݒ�
	 * 
	 * @param index
	 * @param foreignDecimalPoint �O�݂̏����_����
	 */
	public void setForeignDecimalPoint(int index, int foreignDecimalPoint) {
		this.foreignDecimalPointList.set(index, foreignDecimalPoint);
	}

	/**
	 * �ؕ��O�ݍ��v�̎擾
	 * 
	 * @return foreignDebitAmountList �ؕ��O�ݍ��v
	 */
	public List<BigDecimal> getForeignDebitAmountList() {
		return foreignDebitAmountList;
	}

	/**
	 * �ؕ��O�ݍ��v�̐ݒ�
	 * 
	 * @param foreignDebitAmountList �ؕ��O�ݍ��v
	 */
	public void setForeignDebitAmountList(List<BigDecimal> foreignDebitAmountList) {
		this.foreignDebitAmountList = foreignDebitAmountList;
	}

	/**
	 * �ݕ��O�ݍ��v�̎擾
	 * 
	 * @return foreignCreditAmountList �ݕ��O�ݍ��v
	 */
	public List<BigDecimal> getForeignCreditAmountList() {
		return foreignCreditAmountList;
	}

	/**
	 * �ݕ��O�ݍ��v�̐ݒ�
	 * 
	 * @param foreignCreditAmountList �ݕ��O�ݍ��v
	 */
	public void setForeignCreditAmountList(List<BigDecimal> foreignCreditAmountList) {
		this.foreignCreditAmountList = foreignCreditAmountList;
	}

	/**
	 * �O�݂̎擾
	 * 
	 * @return foreignCurrencyCodeList �O��
	 */
	public List<String> getForeignCurrencyCodeList() {
		return foreignCurrencyCodeList;
	}

	/**
	 * �O�݂̐ݒ�
	 * 
	 * @param foreignCurrencyCodeList �O��
	 */
	public void setForeignCurrencyCodeList(List<String> foreignCurrencyCodeList) {
		this.foreignCurrencyCodeList = foreignCurrencyCodeList;
	}

	/**
	 * �O�݂̏����_�����̎擾
	 * 
	 * @return foreignDecimalPointList �O�݂̏����_����
	 */
	public List<Integer> getForeignDecimalPointList() {
		return foreignDecimalPointList;
	}

	/**
	 * �O�݂̏����_�����̐ݒ�
	 * 
	 * @param foreignDecimalPointList �O�݂̏����_����
	 */
	public void setForeignDecimalPointList(List<Integer> foreignDecimalPointList) {
		this.foreignDecimalPointList = foreignDecimalPointList;
	}

	/**
	 * �O�݌����̎擾
	 * 
	 * @return maxForeignCurrencyCount �O�݌���
	 */
	public int getMaxForeignCurrencyCount() {
		return maxForeignCurrencyCount;
	}

	/**
	 * �O�݌����̐ݒ�
	 * 
	 * @param maxForeignCurrencyCount �O�݌���
	 */
	public void setMaxForeignCurrencyCount(int maxForeignCurrencyCount) {
		this.maxForeignCurrencyCount = maxForeignCurrencyCount;
	}

}