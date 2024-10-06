package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans2.common.ledger.*;

/**
 * 伝票帳票フッター
 * 
 * @author AIS
 */
public class SlipBookFooter extends LedgerSheetFooter {

	/** 借方邦貨合計 */
	protected BigDecimal debitAmount = null;

	/** 貸方邦貨合計 */
	protected BigDecimal creditAmount = null;

	/** 小数点桁数 */
	protected int decimalPoint = 0;

	/** 借方外貨合計 */
	protected List<BigDecimal> foreignDebitAmountList = new ArrayList<BigDecimal>();

	/** 貸方外貨合計 */
	protected List<BigDecimal> foreignCreditAmountList = new ArrayList<BigDecimal>();

	/** 外貨 */
	protected List<String> foreignCurrencyCodeList = new ArrayList<String>();

	/** 外貨の小数点桁数 */
	protected List<Integer> foreignDecimalPointList = new ArrayList<Integer>();

	/** 外貨件数 */
	protected int maxForeignCurrencyCount = 10;

	/**
	 * コンストラクター
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
	 * 借方邦貨合計の取得
	 * 
	 * @return debitAmount 借方邦貨合計
	 */
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	/**
	 * 借方邦貨合計の設定
	 * 
	 * @param debitAmount 借方邦貨合計
	 */
	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}

	/**
	 * 貸方邦貨合計の取得
	 * 
	 * @return creditAmount 貸方邦貨合計
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * 貸方邦貨合計の設定
	 * 
	 * @param creditAmount 貸方邦貨合計
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * 小数点桁数の取得
	 * 
	 * @return decimalPoint 小数点桁数
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * 小数点桁数の設定
	 * 
	 * @param decimalPoint 小数点桁数
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * 借方外貨1合計の取得
	 * 
	 * @return foreignDebitAmount1 借方外貨1合計
	 */
	public BigDecimal getForeignDebitAmount1() {
		return getForeignDebitAmount(0);
	}

	/**
	 * 借方外貨1合計の設定
	 * 
	 * @param foreignDebitAmount1 借方外貨1合計
	 */
	public void setForeignDebitAmount1(BigDecimal foreignDebitAmount1) {
		setForeignDebitAmount(0, foreignDebitAmount1);
	}

	/**
	 * 貸方外貨1合計の取得
	 * 
	 * @return foreignCreditAmount1 貸方外貨1合計
	 */
	public BigDecimal getForeignCreditAmount1() {
		return getForeignCreditAmount(0);
	}

	/**
	 * 貸方外貨1合計の設定
	 * 
	 * @param foreignCreditAmount1 貸方外貨1合計
	 */
	public void setForeignCreditAmount1(BigDecimal foreignCreditAmount1) {
		setForeignCreditAmount(0, foreignCreditAmount1);
	}

	/**
	 * 外貨1の取得
	 * 
	 * @return foreignCurrencyCode1 外貨1
	 */
	public String getForeignCurrencyCode1() {
		return getForeignCurrencyCode(0);
	}

	/**
	 * 外貨1の設定
	 * 
	 * @param foreignCurrencyCode1 外貨1
	 */
	public void setForeignCurrencyCode1(String foreignCurrencyCode1) {
		setForeignCurrencyCode(0, foreignCurrencyCode1);
	}

	/**
	 * 外貨1の小数点桁数の取得
	 * 
	 * @return foreignDecimalPoint1 外貨1の小数点桁数
	 */
	public int getForeignDecimalPoint1() {
		return getForeignDecimalPoint(0);
	}

	/**
	 * 外貨1の小数点桁数の設定
	 * 
	 * @param foreignDecimalPoint1 外貨1の小数点桁数
	 */
	public void setForeignDecimalPoint1(int foreignDecimalPoint1) {
		setForeignDecimalPoint(0, foreignDecimalPoint1);
	}

	/**
	 * 借方外貨2合計の取得
	 * 
	 * @return foreignDebitAmount2 借方外貨2合計
	 */
	public BigDecimal getForeignDebitAmount2() {
		return getForeignDebitAmount(1);
	}

	/**
	 * 借方外貨2合計の設定
	 * 
	 * @param foreignDebitAmount2 借方外貨2合計
	 */
	public void setForeignDebitAmount2(BigDecimal foreignDebitAmount2) {
		setForeignDebitAmount(1, foreignDebitAmount2);
	}

	/**
	 * 貸方外貨2合計の取得
	 * 
	 * @return foreignCreditAmount2 貸方外貨2合計
	 */
	public BigDecimal getForeignCreditAmount2() {
		return getForeignCreditAmount(1);
	}

	/**
	 * 貸方外貨2合計の設定
	 * 
	 * @param foreignCreditAmount2 貸方外貨2合計
	 */
	public void setForeignCreditAmount2(BigDecimal foreignCreditAmount2) {
		setForeignCreditAmount(1, foreignCreditAmount2);
	}

	/**
	 * 外貨2の取得
	 * 
	 * @return foreignCurrencyCode2 外貨2
	 */
	public String getForeignCurrencyCode2() {
		return getForeignCurrencyCode(1);
	}

	/**
	 * 外貨2の設定
	 * 
	 * @param foreignCurrencyCode2 外貨2
	 */
	public void setForeignCurrencyCode2(String foreignCurrencyCode2) {
		setForeignCurrencyCode(1, foreignCurrencyCode2);
	}

	/**
	 * 外貨2の小数点桁数の取得
	 * 
	 * @return foreignDecimalPoint2 外貨2の小数点桁数
	 */
	public int getForeignDecimalPoint2() {
		return getForeignDecimalPoint(1);
	}

	/**
	 * 外貨2の小数点桁数の設定
	 * 
	 * @param foreignDecimalPoint2 外貨2の小数点桁数
	 */
	public void setForeignDecimalPoint2(int foreignDecimalPoint2) {
		setForeignDecimalPoint(1, foreignDecimalPoint2);
	}

	/**
	 * 借方外貨index合計の取得
	 * 
	 * @param index
	 * @return foreignDebitAmount 借方外貨index合計
	 */
	public BigDecimal getForeignDebitAmount(int index) {
		return foreignDebitAmountList.get(index);
	}

	/**
	 * 借方外貨index合計の設定
	 * 
	 * @param index
	 * @param foreignDebitAmount 借方外貨index合計
	 */
	public void setForeignDebitAmount(int index, BigDecimal foreignDebitAmount) {
		this.foreignDebitAmountList.set(index, foreignDebitAmount);
	}

	/**
	 * 貸方外貨index合計の取得
	 * 
	 * @param index
	 * @return foreignCreditAmount 貸方外貨index合計
	 */
	public BigDecimal getForeignCreditAmount(int index) {
		return foreignCreditAmountList.get(index);
	}

	/**
	 * 貸方外貨index合計の設定
	 * 
	 * @param index
	 * @param foreignCreditAmount 貸方外貨index合計
	 */
	public void setForeignCreditAmount(int index, BigDecimal foreignCreditAmount) {
		this.foreignCreditAmountList.set(index, foreignCreditAmount);
	}

	/**
	 * 外貨の取得
	 * 
	 * @param index
	 * @return foreignCurrencyCode 外貨
	 */
	public String getForeignCurrencyCode(int index) {
		return foreignCurrencyCodeList.get(index);
	}

	/**
	 * 外貨の設定
	 * 
	 * @param index
	 * @param foreignCurrencyCode 外貨
	 */
	public void setForeignCurrencyCode(int index, String foreignCurrencyCode) {
		this.foreignCurrencyCodeList.set(index, foreignCurrencyCode);
	}

	/**
	 * 外貨の小数点桁数の取得
	 * 
	 * @param index
	 * @return foreignDecimalPoint2 外貨の小数点桁数
	 */
	public int getForeignDecimalPoint(int index) {
		return foreignDecimalPointList.get(index);
	}

	/**
	 * 外貨の小数点桁数の設定
	 * 
	 * @param index
	 * @param foreignDecimalPoint 外貨の小数点桁数
	 */
	public void setForeignDecimalPoint(int index, int foreignDecimalPoint) {
		this.foreignDecimalPointList.set(index, foreignDecimalPoint);
	}

	/**
	 * 借方外貨合計の取得
	 * 
	 * @return foreignDebitAmountList 借方外貨合計
	 */
	public List<BigDecimal> getForeignDebitAmountList() {
		return foreignDebitAmountList;
	}

	/**
	 * 借方外貨合計の設定
	 * 
	 * @param foreignDebitAmountList 借方外貨合計
	 */
	public void setForeignDebitAmountList(List<BigDecimal> foreignDebitAmountList) {
		this.foreignDebitAmountList = foreignDebitAmountList;
	}

	/**
	 * 貸方外貨合計の取得
	 * 
	 * @return foreignCreditAmountList 貸方外貨合計
	 */
	public List<BigDecimal> getForeignCreditAmountList() {
		return foreignCreditAmountList;
	}

	/**
	 * 貸方外貨合計の設定
	 * 
	 * @param foreignCreditAmountList 貸方外貨合計
	 */
	public void setForeignCreditAmountList(List<BigDecimal> foreignCreditAmountList) {
		this.foreignCreditAmountList = foreignCreditAmountList;
	}

	/**
	 * 外貨の取得
	 * 
	 * @return foreignCurrencyCodeList 外貨
	 */
	public List<String> getForeignCurrencyCodeList() {
		return foreignCurrencyCodeList;
	}

	/**
	 * 外貨の設定
	 * 
	 * @param foreignCurrencyCodeList 外貨
	 */
	public void setForeignCurrencyCodeList(List<String> foreignCurrencyCodeList) {
		this.foreignCurrencyCodeList = foreignCurrencyCodeList;
	}

	/**
	 * 外貨の小数点桁数の取得
	 * 
	 * @return foreignDecimalPointList 外貨の小数点桁数
	 */
	public List<Integer> getForeignDecimalPointList() {
		return foreignDecimalPointList;
	}

	/**
	 * 外貨の小数点桁数の設定
	 * 
	 * @param foreignDecimalPointList 外貨の小数点桁数
	 */
	public void setForeignDecimalPointList(List<Integer> foreignDecimalPointList) {
		this.foreignDecimalPointList = foreignDecimalPointList;
	}

	/**
	 * 外貨件数の取得
	 * 
	 * @return maxForeignCurrencyCount 外貨件数
	 */
	public int getMaxForeignCurrencyCount() {
		return maxForeignCurrencyCount;
	}

	/**
	 * 外貨件数の設定
	 * 
	 * @param maxForeignCurrencyCount 外貨件数
	 */
	public void setMaxForeignCurrencyCount(int maxForeignCurrencyCount) {
		this.maxForeignCurrencyCount = maxForeignCurrencyCount;
	}

}