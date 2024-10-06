package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.term.*;

/**
 * 会計期間Entity<br>
 * 現在の会計年度、当月情報等を持つ。
 * 
 * @author AIS
 */
public class FiscalPeriod extends TransferBase {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("nendo=").append(fiscalYear);
		sb.append(",kisyu=").append(monthBeginningOfPeriod);
		sb.append(",closeM=").append(closedMonth);
		return sb.toString();
	}

	/** 会計年度 */
	protected int fiscalYear = 0;

	/** 決算段階 */
	protected int settlementStage = 0;

	/** 期首月 */
	protected int monthBeginningOfPeriod = 0;

	/** 期首日 */
	protected Date dateBeginningOfPeriod = null;

	/** 期末日 */
	protected Date dateEndOfPeriod = null;

	/** 決算期間 */
	protected SettlementTerm settlementTerm = null;

	/** 最大決算段階 */
	protected int maxSettlementStage = 0;

	/** 締め月 */
	protected int closedMonth = 0;

	/** 先行日付月数(１年) */
	protected int priorOverMonths = 12;

	/**
	 * @return 決算段階を戻します。
	 */
	public int getSettlementStage() {
		return settlementStage;
	}

	/**
	 * @param settlementStage 決算段階を設定します。
	 */
	public void setSettlementStage(int settlementStage) {
		this.settlementStage = settlementStage;
	}

	/**
	 * @return 会計年度を戻します。
	 */
	public int getFiscalYear() {
		return fiscalYear;
	}

	/**
	 * @param fiscalYear 会計年度を設定します。
	 */
	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	/**
	 * @return 期首日を戻します。
	 */
	public Date getDateBeginningOfPeriod() {
		return dateBeginningOfPeriod;
	}

	/**
	 * 指定日付の年度の期首日を戻します。
	 * 
	 * @param date 日付
	 * @return 指定日付の年度の期首日
	 */
	public Date getDateBeginningOfPeriod(Date date) {
		int fiscalYear_ = getFiscalYear(date);
		return DateUtil.getDate(fiscalYear_, getMonthBeginningOfPeriod(), 1);
	}

	/**
	 * 指定年度の期首日を戻します。
	 * 
	 * @param fiscalyear 年度
	 * @return 指定年度の期首日
	 */
	public Date getDateBeginningOfPeriod(int fiscalyear) {
		return DateUtil.getDate(fiscalyear, getMonthBeginningOfPeriod(), 1);
	}

	/**
	 * @param dateBeginningOfPeriod 期首日を設定します。
	 */
	public void setDateBeginningOfPeriod(Date dateBeginningOfPeriod) {
		this.dateBeginningOfPeriod = dateBeginningOfPeriod;
	}

	/**
	 * @return 期末日を戻します。
	 */
	public Date getDateEndOfPeriod() {
		return dateEndOfPeriod;
	}

	/**
	 * 指定日付の年度の期末日を戻します。
	 * 
	 * @param date 日付
	 * @return 期末日を戻します。
	 */
	public Date getDateEndOfPeriod(Date date) {
		int fiscalYear_ = getFiscalYear(date);
		return DateUtil.getLastDate(fiscalYear_, getMonthBeginningOfPeriod() + 11);
	}

	/**
	 * @param dateEndOfPeriod 期末日を設定します。
	 */
	public void setDateEndOfPeriod(Date dateEndOfPeriod) {
		this.dateEndOfPeriod = dateEndOfPeriod;
	}

	/**
	 * 当月の1日を返す。<br>
	 * 決算段階は考慮する。
	 * 
	 * @return 当月の1日(決算段階は考慮する)
	 */
	public Date getFirstDateOfCurrentPeriodOfSettlement() {

		// 00の場合は期首
		if (getSettlementStage() == 0 && getClosedMonth() == 0) {
			return getDateBeginningOfPeriod();
		}

		// 通常月の場合
		Date date = DateUtil.getDate(getFiscalYear(), getMonthBeginningOfPeriod(), 1);
		date = DateUtil.addMonth(date, getClosedMonth());

		// 決算月の場合
		Date settlementDate = DateUtil.getDate(getFiscalYear(), getMonthBeginningOfPeriod(), 1);
		settlementDate = DateUtil.addMonth(settlementDate, getClosedMonth() - 1);

		boolean isSettlementMonth = isSettlementMonth(settlementDate, getSettlementTerm(), getMonthBeginningOfPeriod());

		if (isSettlementMonth && getMaxSettlementStage() != getSettlementStage()) {
			date = DateUtil.addMonth(date, -1);
		}

		return date;

	}

	/**
	 * 当月の末日を返す。<br>
	 * 決算段階は考慮する(決算段階は考慮する)
	 * 
	 * @return 当月の末日
	 */
	public Date getLastDateOfCurrentPeriodOfSettlement() {
		Date date = getFirstDateOfCurrentPeriodOfSettlement();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * 当月の決算段階を返す
	 * 
	 * @return AIS
	 */
	public int getCurrentSettlementStage() {

		if (settlementStage == 0 && getClosedMonth() == 0) {
			return 0;
		}

		// 決算月ならば決算段階を考慮
		if (isSettlementMonth(getLastDateOfCurrentPeriodOfSettlement())) {

			int fiscalMonth = getFiscalMonth(getLastDateOfCurrentPeriodOfSettlement());
			if (fiscalMonth == getClosedMonth()) {
				return settlementStage + 1;
			}
			return 0;

		}

		// 通常月ならば0
		return 0;

	}

	/**
	 * 当月の1日を返す。<br>
	 * 決算段階は考慮しない。
	 * 
	 * @return 当月の1日(決算段階は考慮しない)
	 */
	public Date getFirstDateOfCurrentPeriod() {

		// 00の場合は期首
		if (getSettlementStage() == 0 && getClosedMonth() == 0) {
			return getDateBeginningOfPeriod();
		}

		// 通常月の場合
		Date date = DateUtil.getDate(getFiscalYear(), getMonthBeginningOfPeriod(), 1);
		date = DateUtil.addMonth(date, getClosedMonth());

		return date;

	}

	/**
	 * 当月の末日を返す。<br>
	 * 決算段階は考慮しない。
	 * 
	 * @return 当月の末日(決算段階は考慮しない)
	 */
	public Date getLastDateOfCurrentPeriod() {
		Date date = getFirstDateOfCurrentPeriod();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * 締め月の1日を返す。<br>
	 * 決算段階は考慮しない。
	 * 
	 * @return 締め月の1日(決算段階は考慮しない)
	 */
	public Date getFirstDateOfClosedPeriod() {

		Date date = getFirstDateOfCurrentPeriod();
		date = DateUtil.addMonth(date, -1);

		return date;

	}

	/**
	 * 締め月の末日を返す。<br>
	 * 決算段階は考慮しない。
	 * 
	 * @return 締め月の末日(決算段階は考慮しない)
	 */
	public Date getLastDateOfClosedPeriod() {
		Date date = getFirstDateOfClosedPeriod();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * 締め月の1日を返す。<br>
	 * 決算段階は考慮する。
	 * 
	 * @return 締め月の1日(決算段階は考慮する)
	 */
	public Date getFirstDateOfClosedPeriodOfSettlement() {

		Date date = getFirstDateOfCurrentPeriodOfSettlement();
		date = DateUtil.addMonth(date, -1);

		return date;

	}

	/**
	 * 締め月の末日を返す。<br>
	 * 決算段階は考慮する。
	 * 
	 * @return 締め月の末日(決算段階は考慮する)
	 */
	public Date getLastDateOfClosedPeriodOfSettlement() {
		Date date = getFirstDateOfClosedPeriodOfSettlement();
		date = DateUtil.getLastDate(date);
		return date;
	}

	/**
	 * 先行伝票入力可能日付を返す。
	 * 
	 * @return 先行伝票入力可能日付
	 */
	public Date getPriorOverDate() {

		Date date = getFirstDateOfClosedPeriodOfSettlement();
		date = DateUtil.addMonth(date, priorOverMonths);
		date = DateUtil.getLastDate(date);

		return date;
	}

	/**
	 * @return 期首月を戻します。
	 */
	public int getMonthBeginningOfPeriod() {
		return monthBeginningOfPeriod;
	}

	/**
	 * @param monthBeginningOfPeriod 期首月を設定します。
	 */
	public void setMonthBeginningOfPeriod(int monthBeginningOfPeriod) {
		this.monthBeginningOfPeriod = monthBeginningOfPeriod;
	}

	/**
	 * 決算期間を返す
	 * 
	 * @return 決算期間
	 */
	public SettlementTerm getSettlementTerm() {
		return settlementTerm;
	}

	/**
	 * 決算期間をセットする
	 * 
	 * @param settlementTerm
	 */
	public void setSettlementTerm(SettlementTerm settlementTerm) {
		this.settlementTerm = settlementTerm;
	}

	/**
	 * 締め月を返す
	 * 
	 * @return 締め月
	 */
	public int getClosedMonth() {
		return closedMonth;
	}

	/**
	 * @param closedMonth
	 */
	public void setClosedMonth(int closedMonth) {
		this.closedMonth = closedMonth;
	}

	/**
	 * 最大決算段階
	 * 
	 * @return 最大決算段階
	 */
	public int getMaxSettlementStage() {
		return maxSettlementStage;
	}

	/**
	 * 最大決算段階
	 * 
	 * @param maxSettlementStage 最大決算段階
	 */
	public void setMaxSettlementStage(int maxSettlementStage) {
		this.maxSettlementStage = maxSettlementStage;
	}

	/**
	 * 先行日付月数の取得
	 * 
	 * @return priorOverMonths 先行日付月数
	 */
	public int getPriorOverMonths() {
		return priorOverMonths;
	}

	/**
	 * 先行日付月数の設定
	 * 
	 * @param priorOverMonths 先行日付月数
	 */
	public void setPriorOverMonths(int priorOverMonths) {
		this.priorOverMonths = priorOverMonths;
	}

	/**
	 * 指定日付の年度を返す
	 * 
	 * @param date
	 * @return 指定日付の年度
	 */
	public int getFiscalYear(Date date) {

		// 対象日付の年、月を取得
		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);

		if (monthBeginningOfPeriod > month) {
			year = year - 1;
		}

		return year;
	}

	/**
	 * 指定日付の月度を返す
	 * 
	 * @param date 日付
	 * @return 指定日付の月度
	 */
	public int getFiscalMonth(Date date) {

		// 対象日付の月を取得
		int month = DateUtil.getMonth(date);

		if (month >= monthBeginningOfPeriod) {
			month = month - monthBeginningOfPeriod + 1;
		} else {
			month = month + 12 - monthBeginningOfPeriod + 1;
		}

		return month;

	}

	/**
	 * 決算月かを返す
	 * 
	 * @param date 日付
	 * @return true(決算月) / false(通常月)
	 */
	public boolean isSettlementMonth(Date date) {
		return isSettlementMonth(date, this.settlementTerm, this.monthBeginningOfPeriod);
	}

	/**
	 * 本決算月かを返す
	 * 
	 * @param date 日付
	 * @return true(決算月) / false(通常月)
	 */
	public boolean isSettlementMonthForYear(Date date) {
		return isSettlementMonth(date, SettlementTerm.YEAR, this.monthBeginningOfPeriod);
	}

	/**
	 * 決算月かを返す
	 * 
	 * @param date 日付
	 * @param term 決算伝票入力区分
	 * @param monthBeginningOfPeriod_ 期首月
	 * @return true(決算月) / false(通常月)
	 */
	public boolean isSettlementMonth(Date date, SettlementTerm term, int monthBeginningOfPeriod_) {

		int month = DateUtil.getMonth(date);
		boolean rt = false;

		// 年次決算
		if (SettlementTerm.YEAR == term) {

			int settlementMonth = monthBeginningOfPeriod_ + 11;
			if (settlementMonth > 12) {
				settlementMonth = settlementMonth - 12;
			}
			rt = (month == settlementMonth);

			// 半期決算
		} else if (SettlementTerm.HALF == term) {

			int settlementMonth = monthBeginningOfPeriod_ - 1;
			for (int i = 0; i < 2; i++) {
				settlementMonth = settlementMonth + 6;
				if (settlementMonth > 12) {
					settlementMonth = settlementMonth - 12;
				}
				if (month == settlementMonth) {
					rt = true;
				}

			}

			// 四期決算
		} else if (SettlementTerm.QUARTER == term) {

			int settlementMonth = monthBeginningOfPeriod_ - 1;
			for (int i = 0; i < 4; i++) {
				settlementMonth = settlementMonth + 3;
				if (settlementMonth > 12) {
					settlementMonth = settlementMonth - 12;
				}
				if (month == settlementMonth) {
					rt = true;
				}

			}

			// 月次決算
		} else {
			rt = true;
		}

		return rt;

	}

	/**
	 * 締められていないかを返す。<br>
	 * 締められている場合true、締められていない場合false。<br>
	 * 
	 * @param date 対象日付
	 * @param stage 決算段階
	 * @return 締められている場合true、締められていない場合false
	 */
	public boolean isClosed(Date date, int stage) {

		Date closedDate = getLastDateOfClosedPeriod();
		if (closedDate.compareTo(date) > 0) {
			return true;

		} else if (closedDate.compareTo(date) == 0 && stage <= getSettlementStage()) {
			return true;
		}

		return false;
	}

	/**
	 * 当月の会計期を返す
	 * 
	 * @return 当月の会計期
	 */
	public Enum getCurrentAccountTerm() {
		return getAccountTerm(getFirstDateOfCurrentPeriodOfSettlement(), this.settlementTerm);
	}

	/**
	 * 指定日の会計期を返す
	 * 
	 * @param date
	 * @return 指定日の会計期
	 */
	public Enum getAccountTerm(Date date) {

		return getAccountTerm(date, this.settlementTerm);
	}

	/**
	 * 指定日の会計期を、指定の決算区分に応じて返す
	 * 
	 * @param date
	 * @param _settlementTerm
	 * @return 決算区分に対応した会計期
	 */
	private Enum getAccountTerm(Date date, SettlementTerm _settlementTerm) {

		int tukido = getFiscalMonth(date);
		return AccountTerm.getCurretTerm(tukido, _settlementTerm);
	}

	/**
	 * 指定日の会計期が現在の会計期と同じ
	 * 
	 * @param date
	 * @return true：現在の会計期間内である
	 */
	public boolean isAccountTermEqual(Date date) {
		// 年度が同一かつ、会計期が同じ
		return getFiscalYear(date) == getFiscalYear() && getAccountTerm(date).equals(getCurrentAccountTerm());
	}

	/**
	 * 指定会計年度、月度、日の日付の取得
	 * 
	 * @param fiscalY 会計年度
	 * @param fiscalM 会計月度
	 * @param day 日
	 * @return 対象日付
	 */
	public Date convertDate(int fiscalY, int fiscalM, int day) {

		int year = fiscalY;
		int month = fiscalM + monthBeginningOfPeriod;
		if (month > 12) {
			month -= 12;
			year += 1;
		}

		// 対象日付を取得
		return DateUtil.getDate(year, month - 1, day);
	}

	/**
	 * 当該会計期を返す
	 * 
	 * @param strDate 適用開始年月日
	 * @param fiscalY 指定年度
	 * @return 対象決算期
	 */
	public int getAccountingPeriod(Date strDate, int fiscalY) {

		int strNendo = getFiscalYear(strDate);
		// 対象決算期
		return fiscalY - strNendo + 1;
	}

}
