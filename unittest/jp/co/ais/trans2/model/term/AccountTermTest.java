package jp.co.ais.trans2.model.term;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;

import junit.framework.*;

/**
 * 
 */
public class AccountTermTest extends TestCase {

	/**
	 * 
	 */
	public void testCorrectAccountTermYear() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.YEAR);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		assertEquals(Year.YEAR, caseYear.getCurrentAccountTerm());
	}

	/**
	 * 
	 */
	public void testCorrectAccountTermHALF() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.HALF);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		assertEquals(Half.FisrtHalf, caseYear.getCurrentAccountTerm());
	}

	/**
	 * 
	 */
	public void testCorrectAccountTermQUARTER() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.QUARTER);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		assertEquals(Quarter.FirstQuarter, caseYear.getCurrentAccountTerm());
	}

	/**
	 * 
	 */
	public void testCorrectAccountTermMONTH() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.MONTH);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		assertEquals(Month._1st, caseYear.getCurrentAccountTerm());
	}

	/**
	 * 
	 */
	public void testCorrectMonthTerm() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.MONTH);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		caseYear.setClosedMonth(0);
		assertEquals(Month._1st, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(1);
		assertEquals(Month._2nd, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(2);
		assertEquals(Month._3rd, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(3);
		assertEquals(Month._4th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(4);
		assertEquals(Month._5th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(5);
		assertEquals(Month._6th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(6);
		assertEquals(Month._7th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(7);
		assertEquals(Month._8th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(8);
		assertEquals(Month._9th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(9);
		assertEquals(Month._10th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(10);
		assertEquals(Month._11th, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(11);
		assertEquals(Month._12th, caseYear.getCurrentAccountTerm());

	}

	/**
	 * 
	 */
	public void testCorrectQuarterTerm() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.QUARTER);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		caseYear.setClosedMonth(0);
		assertEquals(Quarter.FirstQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(1);
		assertEquals(Quarter.FirstQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(2);
		assertEquals(Quarter.FirstQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(3);
		assertEquals(Quarter.SecondQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(4);
		assertEquals(Quarter.SecondQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(5);
		assertEquals(Quarter.SecondQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(6);
		assertEquals(Quarter.ThirdQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(7);
		assertEquals(Quarter.ThirdQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(8);
		assertEquals(Quarter.ThirdQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(9);
		assertEquals(Quarter.FourthQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(10);
		assertEquals(Quarter.FourthQuarter, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(11);
		assertEquals(Quarter.FourthQuarter, caseYear.getCurrentAccountTerm());

	}

	/**
	 * 
	 */
	public void testCorrectHalfTerm() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.HALF);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		caseYear.setClosedMonth(0);
		assertEquals(Half.FisrtHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(1);
		assertEquals(Half.FisrtHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(2);
		assertEquals(Half.FisrtHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(3);
		assertEquals(Half.FisrtHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(4);
		assertEquals(Half.FisrtHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(5);
		assertEquals(Half.FisrtHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(6);
		assertEquals(Half.SecondHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(7);
		assertEquals(Half.SecondHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(8);
		assertEquals(Half.SecondHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(9);
		assertEquals(Half.SecondHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(10);
		assertEquals(Half.SecondHalf, caseYear.getCurrentAccountTerm());

		caseYear.setClosedMonth(11);
		assertEquals(Half.SecondHalf, caseYear.getCurrentAccountTerm());

	}

	/**
	 * 
	 */
	public void testCorrectYearTerm() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.YEAR);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));

		for (int i = 0; i < 12; i++) {
			caseYear.setClosedMonth(i);
			assertEquals(Year.YEAR, caseYear.getCurrentAccountTerm());

		}

	}

	/**
	 * 同一会計期かチェックするメソッドテスト
	 */
	public void testEqual() {

		FiscalPeriod caseYear = new FiscalPeriod();
		caseYear.setSettlementTerm(SettlementTerm.QUARTER);
		caseYear.setFiscalYear(2011);
		caseYear.setMonthBeginningOfPeriod(4);
		caseYear.setDateBeginningOfPeriod(DateUtil.getDate(2011, 4, 1));
		caseYear.setClosedMonth(4);
		// 現在2011/8/1

		Date comp = DateUtil.getDate(2011, 7, 1);
		assertTrue(caseYear.isAccountTermEqual(comp));

		comp = DateUtil.getDate(2012, 10, 1);
		assertFalse(caseYear.isAccountTermEqual(comp));

		comp = DateUtil.getDate(2012, 7, 1);
		assertFalse(caseYear.isAccountTermEqual(comp));

		// 半期決算の場合
		caseYear.setSettlementTerm(SettlementTerm.HALF);
		comp = DateUtil.getDate(2011, 7, 1);
		assertTrue(caseYear.isAccountTermEqual(comp));

		caseYear.setSettlementTerm(SettlementTerm.HALF);
		comp = DateUtil.getDate(2011, 10, 1);
		assertFalse(caseYear.isAccountTermEqual(comp));

	}
}
