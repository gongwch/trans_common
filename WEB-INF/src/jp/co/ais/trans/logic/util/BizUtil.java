package jp.co.ais.trans.logic.util;

import java.math.*;
import java.text.*;
import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bill.*;

/**
 * TRANS業務共通ユーティリティ
 * 
 * @version 1.0
 * @author AIS Y.NAGAHASHI
 */
public class BizUtil {

	/**
	 * 期首月を取得する
	 * 
	 * @param companyCode 会社コード
	 * @return 期首月
	 */
	public static int getInitialMonth(String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 会社コントロールマスタより期首月取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}
		return cmpMst.getCMP_KISYU().intValue();
	}

	/**
	 * 決算伝票入力可能月判定
	 * 
	 * @param date 対象日付
	 * @param companyCode 会社コード
	 * @return 決算伝票入力可能月の場合はTRUE
	 */
	public static boolean isKsnMonth(Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		int i;
		boolean judge = false;

		// 締め制御テーブルより年度取得
		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
		SIM_CTL simCtl = simdao.getSIM_CTLByIKaicode(companyCode);

		if (simCtl == null) {
			throw new TEnvironmentException("W00084", "C10258", companyCode);
		}

		int year = simCtl.getNENDO();

		// 会社コントロールマスタより期首月取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		// 決算コントロールマスタより決算伝票入力区分取得
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);
		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		int ksnNyuKbn = glCtlMst.getKSN_NYU_KBN();

		Date ksndate = new GregorianCalendar(year, initialMonth - 1, 1).getTime();

		ksndate = DateUtil.addMonth(ksndate, -1);

		switch (ksnNyuKbn) {
			case 0:
				// 年決算
				ksndate = DateUtil.addYear(ksndate, 1);
				if (DateUtil.getMonth(date) == DateUtil.getMonth(ksndate)) {
					judge = true;
				}
				break;

			case 1:
				// 半期決算
				for (i = 1; i < 3; i++) {
					ksndate = DateUtil.addMonth(ksndate, 6);
					if (DateUtil.getMonth(date) == DateUtil.getMonth(ksndate)) {
						judge = true;
					}
				}
				break;

			case 2:
				// 四半期決算
				for (i = 1; i < 5; i++) {
					ksndate = DateUtil.addMonth(ksndate, 3);
					if (DateUtil.getMonth(date) == DateUtil.getMonth(ksndate)) {
						judge = true;
					}
				}
				break;

			default:
				judge = true;
				break;

		}

		// 末日でない場合、決算伝票入力不可能
		if (DateUtil.getDay(date) != DateUtil.getLastDay(DateUtil.getYear(date), DateUtil.getMonth(date))) {
			judge = false;
		}

		return judge;

	}

	/**
	 * 直近の決算月末日を取得
	 * 
	 * @param date 現在日付
	 * @param companyCode 会社コード
	 * @return 直近の決算月末日
	 */
	public static String getKsnDate(Date date, String companyCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		int i;

		// 締め制御テーブルより年度取得
		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
		SIM_CTL simCtl = simdao.getSIM_CTLByIKaicode(companyCode);

		if (simCtl == null) {
			throw new TEnvironmentException("W00084", "C10258", companyCode);
		}

		int year = simCtl.getNENDO();

		// 会社コントロールマスタより期首月取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		// 決算コントロールマスタより決算伝票入力区分取得
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);
		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		int ksnNyuKbn = glCtlMst.getKSN_NYU_KBN();

		Date ksndate = new GregorianCalendar(year, initialMonth - 1, 1).getTime();

		ksndate = DateUtil.addMonth(ksndate, -1);

		// 先の決算伝票日付か
		boolean isNextKsnDate = true;

		// 一時保存用
		Date ksndateTmp = new Date();

		// 決算区分
		int ksndivision = simCtl.getKSN_KBN();

		// 決算段階区分
		int ksnDdivision = getAccountStageDivision(companyCode);

		// 決算区分と決算段階区分を比較
		// isKsnMonthの仕様に合わせるため、月末日を求める(addMonthで+1,addDayで-1)
		if (ksndivision < ksnDdivision) {
			isNextKsnDate = false;
		}

		switch (ksnNyuKbn) {
			case 0:
				// 年決算
				ksndate = DateUtil.addYear(ksndate, 1);
				break;

			case 1:
				// 半期決算
				for (i = 1; i < 3; i++) {
					ksndateTmp = DateUtil.addMonth(ksndate, 6);

					if (DateUtil.getMonth(ksndateTmp) <= DateUtil.getMonth(date)) {
						ksndate = ksndateTmp;
					} else {
						if (isNextKsnDate) {
							ksndate = ksndateTmp;
						}
						break;
					}
				}
				break;

			case 2:
				// 四半期決算
				for (i = 1; i < 5; i++) {
					ksndateTmp = DateUtil.addMonth(ksndate, 3);

					if (DateUtil.getMonth(ksndateTmp) <= DateUtil.getMonth(date)) {
						ksndate = ksndateTmp;
					} else {
						if (isNextKsnDate) {
							ksndate = ksndateTmp;
						}
						break;
					}
				}
				break;

			default:
				break;

		}

		return DateUtil.toYMDString(DateUtil.getLastDate(ksndate));
	}

	/**
	 * 基軸通貨換算.<br>
	 * 指定コードを元にマスタから情報取得
	 * 
	 * @param money 外貨金額
	 * @param rate 外貨レート
	 * @param baseCurrencyCode 基軸通貨コード
	 * @param foreignCurrencyCode 外貨コード
	 * @param companyCode 会社コード
	 * @return 基軸通貨に換算した金額
	 */
	public static BigDecimal convertToBaseCurrency(BigDecimal money, double rate, String baseCurrencyCode,
		String foreignCurrencyCode, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 会社コントロールマスタ
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST companyInfo = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (companyInfo == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		// 通貨マスタ
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);

		// 基軸通貨
		CUR_MST baseCurrencyInfo = curdao.getCUR_MST(companyCode, baseCurrencyCode);

		if (baseCurrencyInfo == null) {
			throw new TEnvironmentException("W00084", "C01985", baseCurrencyCode);
		}

		// 外貨
		CUR_MST foreignCurrencyInfo = curdao.getCUR_MST(companyCode, foreignCurrencyCode);

		if (foreignCurrencyInfo == null) {
			throw new TEnvironmentException("W00084", "C01985", foreignCurrencyCode);
		}

		return convertToBaseCurrency(money, rate, baseCurrencyInfo, foreignCurrencyInfo, companyInfo);
	}

	/**
	 * 基軸通貨換算
	 * 
	 * @param money 外貨金額
	 * @param rate 外貨レート
	 * @param baseCurrencyInfo 基軸通貨情報(エンティティ)
	 * @param foreignCurrencyInfo 外貨情報(エンティティ)
	 * @param companyInfo 会社情報(エンティティ)
	 * @return 基軸通貨に換算した金額
	 */
	public static BigDecimal convertToBaseCurrency(BigDecimal money, double rate, CUR_MST baseCurrencyInfo,
		CUR_MST foreignCurrencyInfo, CMP_MST companyInfo) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		CurrencyConvert logic = (CurrencyConvert) container.getComponent(CurrencyConvert.class);
		return logic.getAmountToBase(money, rate, baseCurrencyInfo, foreignCurrencyInfo, companyInfo);

	}

	/**
	 * 外貨換算.<br>
	 * 指定コードを元にマスタから情報取得
	 * 
	 * @param money 邦貨金額
	 * @param rate 外貨レート
	 * @param foreignCurrencyCode 外貨コード
	 * @param companyCode 会社コード
	 * @return 外貨通貨に換算した金額
	 */
	public static BigDecimal convertToForeignCurrency(BigDecimal money, double rate, String foreignCurrencyCode,
		String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 会社コントロールマスタ
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST companyInfo = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (companyInfo == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		// 外貨
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);
		CUR_MST foreignCurrencyInfo = curdao.getCUR_MST(companyCode, foreignCurrencyCode);

		if (foreignCurrencyInfo == null) {
			throw new TEnvironmentException("W00084", "C01985", foreignCurrencyCode);
		}

		return convertToForeignCurrency(money, rate, foreignCurrencyInfo, companyInfo);
	}

	/**
	 * 外貨換算
	 * 
	 * @param money 邦貨金額
	 * @param rate 外貨レート
	 * @param foreignCurrencyInfo 外貨情報(エンティティ)
	 * @param companyInfo 会社情報(エンティティ)
	 * @return 外貨通貨に換算した金額
	 */
	public static BigDecimal convertToForeignCurrency(BigDecimal money, double rate, CUR_MST foreignCurrencyInfo,
		CMP_MST companyInfo) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		CurrencyConvert logic = (CurrencyConvert) container.getComponent(CurrencyConvert.class);
		return logic.getAmountToForeign(money, rate, foreignCurrencyInfo, companyInfo);

	}

	/**
	 * 対象日付時点でのレート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 対象日付
	 * @param companyCode 会社コード
	 * @return レート(Double)
	 */
	public static double getCurrencyRate(String currencyCode, Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 対象日付から最新のレート取得
		RATE_MSTDao ratedao = (RATE_MSTDao) container.getComponent(RATE_MSTDao.class);
		List ratemst = ratedao.getNewRate(companyCode, currencyCode, date);

		if (ratemst.isEmpty()) {
			return -1;
		}

		return ((RATE_MST) ratemst.get(0)).getCUR_RATE().doubleValue();
	}

	/**
	 * 対象日付時点でのレート取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param date 対象日付
	 * @param companyCode 会社コード
	 * @return レート(BigDecimal)
	 */
	public static BigDecimal getCurrencyRateDecimal(String currencyCode, Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 対象日付から最新のレート取得
		RATE_MSTDao ratedao = (RATE_MSTDao) container.getComponent(RATE_MSTDao.class);
		List ratemst = ratedao.getNewRate(companyCode, currencyCode, date);

		if (ratemst.isEmpty()) {
			return new BigDecimal("1");
		}

		return ((RATE_MST) ratemst.get(0)).getCUR_RATE();
	}

	/**
	 * 対象通貨の小数点以下桁数取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param companyCode 会社コード
	 * @return 小数点以下桁数
	 */
	public static int getCurrencyDigit(String currencyCode, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 通貨マスタより小数点以下桁数取得
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);
		CUR_MST curMst = curdao.getCUR_MST(companyCode, currencyCode);

		if (curMst == null) {
			throw new TEnvironmentException("W00084", "C01985", currencyCode);
		}

		return curMst.getDEC_KETA();
	}

	/**
	 * 対象通貨の小数点以下桁数取得
	 * 
	 * @param currencyCode 通貨コード
	 * @param companyCode 会社コード
	 * @return 小数点以下桁数
	 */
	public static CUR_MST getCurrency(String currencyCode, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 通貨マスタより小数点以下桁数取得
		CUR_MSTDao curdao = (CUR_MSTDao) container.getComponent(CUR_MSTDao.class);
		CUR_MST curMst = curdao.getCUR_MST(companyCode, currencyCode);

		if (curMst == null) {
			throw new TEnvironmentException("W00084", "C01985", currencyCode);
		}

		return curMst;
	}

	/**
	 * 基軸通貨の小数点以下桁数取得
	 * 
	 * @param companyCode 会社コード
	 * @return 基軸通貨 小数点以下桁数
	 */
	public static int getBaseCurrencyDigit(String companyCode) {
		return getCurrencyDigit(getBaseCurrency(companyCode), companyCode);
	}

	/**
	 * 基軸通貨コード取得
	 * 
	 * @param companyCode 会社コード
	 * @return 基軸通貨コード
	 */
	public static String getBaseCurrency(String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 会社コントロールマスタより基軸通貨コード取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		return cmpMst.getCUR_CODE();
	}

	/**
	 * 消費税計算(内税)
	 * 
	 * @param money 対象金額
	 * @param taxRate 税レート(%)
	 * @param division 売上仕入区分
	 * @param digit 少数点以下桁数
	 * @param companyCode 会社コード
	 * @return 内税
	 */
	public static BigDecimal calculateTaxInside(BigDecimal money, double taxRate, int division, int digit,
		String companyCode) {

		// 会社マスタより仮受消費税端数処理、仮払消費税端数処理を取得
		int fractionTempPay = 0;
		int fractionTempTake = 0;

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 会社コントロールマスタより基軸通貨コード取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		fractionTempPay = cmpMst.getKB_KBN();
		fractionTempTake = cmpMst.getKU_KBN();

		double tax;

		tax = money.doubleValue() * taxRate / (100 + taxRate);
		BigDecimal returnTax = new BigDecimal(tax);

		if (division == 1) { // 売上課税（仮受消費税）
			switch (fractionTempTake) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}

		} else if (division == 2) {
			switch (fractionTempPay) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}
		}

		return returnTax;
	}

	/**
	 * 消費税計算(外税)
	 * 
	 * @param money 対象金額
	 * @param taxRate 税レート(%)
	 * @param division 売上仕入区分
	 * @param digit 少数点以下桁数
	 * @param companyCode 会社コード
	 * @return 外税
	 */
	public static BigDecimal calculateTaxOutside(BigDecimal money, double taxRate, int division, int digit,
		String companyCode) {
		// 会社マスタより仮受消費税端数処理、仮払消費税端数処理を取得
		int fractionTempPay = 0;
		int fractionTempTake = 0;

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 会社コントロールマスタより基軸通貨コード取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		fractionTempPay = cmpMst.getKB_KBN();
		fractionTempTake = cmpMst.getKU_KBN();

		double tax;
		tax = money.doubleValue() * taxRate / 100;
		BigDecimal returnTax = new BigDecimal(tax);

		if (division == 1) { // 売上課税（仮受消費税）
			switch (fractionTempTake) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}

		} else if (division == 2) {
			switch (fractionTempPay) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, digit);
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, digit);
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, digit);
					break;

			}
		}

		return returnTax;
	}

	/**
	 * 年度取得 期首月に応じた対象日付の年度を取得
	 * 
	 * @param ymd 対象年月日
	 * @param companyCode 会社コード
	 * @return 対象日付の年度
	 * @throws ParseException
	 */
	public static int getFiscalYear(String ymd, String companyCode) throws ParseException {
		return getFiscalYear(DateUtil.toYMDDate(ymd), companyCode);
	}

	/**
	 * 年度取得 期首月に応じた対象日付の年度を取得
	 * 
	 * @param date 対象年月日
	 * @param companyCode 会社コード
	 * @return 対象日付の年度
	 */
	public static int getFiscalYear(Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 対象年月日の年
		int year = DateUtil.getYear(date);

		// 対象年月日の月
		int month = DateUtil.getMonth(date);

		// 会社コントロールマスタより期首月取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		if (initialMonth > month) {
			year = year - 1;
		}

		return year;
	}

	/**
	 * 月度取得 期首月に応じた対象日付の月度を取得
	 * 
	 * @param ymd 対象年月日
	 * @param companyCode 会社コード
	 * @return 対象日付の月度
	 * @throws ParseException
	 */
	public static int getFiscalMonth(String ymd, String companyCode) throws ParseException {

		return getFiscalMonth(DateUtil.toYMDDate(ymd), companyCode);
	}

	/**
	 * 月度取得 期首月に応じた対象日付の月度を取得
	 * 
	 * @param date 対象年月日
	 * @param companyCode 会社コード
	 * @return 対象日付の月度
	 */
	public static int getFiscalMonth(Date date, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 対象年月日の月
		int month = DateUtil.getMonth(date);

		// 会社コントロールマスタより期首月取得
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int initialMonth = cmpMst.getCMP_KISYU().intValue();

		if (month >= initialMonth) {
			month = month - initialMonth + 1;
		} else {
			month = month + 12 - initialMonth + 1;
		}

		return month;
	}

	/**
	 * 締め年月取得 締め年月関連の値を取得する。 <br>
	 * 月度は締め月から何ヶ月目を取得したいか指定(0ならば締め月)。
	 * 
	 * @param monthFromCloseDate 月度
	 * @param companyCode 会社コード
	 * @return 対象年月末日("yyyy/mm/dd"形式)
	 */
	public static String getCloseDate(int monthFromCloseDate, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		int addMonth; // 加算月数
		int simmonth; // 締め月
		Date simStartdate; // 第１月開始日
		Date date = null;

		try {
			// 締め制御テーブルより締め月、第１月開始日取得
			SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
			SIM_CTL sim = simdao.getSIM_CTLByIKaicode(companyCode);
			simmonth = sim.getSIM_MON();
			simStartdate = sim.getSIM_STR_DATE();

			// 増分初期値
			addMonth = simmonth + monthFromCloseDate;

			// 対象日付取得
			date = DateUtil.addDay(DateUtil.addMonth(simStartdate, addMonth), -1);

		} catch (Exception e) {
			// no error
		}

		return DateUtil.toYMDString(date);
	}

	/**
	 * 締め年月取得（決算段階） 締め年月関連の値を取得する。(決算段階を考慮した締め年月を取得) <br>
	 * 月度は締め月から何ヶ月目を取得したいか指定(0ならば締め月)。
	 * 
	 * @param monthFromCloseDate 月度
	 * @param companyCode 会社コード
	 * @return 対象年月末日("yyyy/mm/dd"形式)
	 */
	public static String getCloseDateForAccountStage(int monthFromCloseDate, String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 締め制御テーブルより締め月、第１月開始日、決算区分取得
		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);
		SIM_CTL simCtl = simdao.getSIM_CTLByIKaicode(companyCode);

		if (simCtl == null) {
			throw new TEnvironmentException("W00084", "C10258", companyCode);
		}

		// 締め月
		int simmonth = simCtl.getSIM_MON();
		// 第１月開始日
		Date simStartdate = simCtl.getSIM_STR_DATE();
		// 決算区分
		int ksndivision = simCtl.getKSN_KBN();

		// 加算月数: 増分初期値
		int addMonth = simmonth + monthFromCloseDate;

		// 決算段階区分
		int ksnDdivision = getAccountStageDivision(companyCode);

		// 決算区分と決算段階区分を比較
		// isKsnMonthの仕様に合わせるため、月末日を求める(addMonthで+1,addDayで-1)
		if (ksndivision < ksnDdivision && !(simmonth == 0 && ksndivision == 0)
			&& isKsnMonth(DateUtil.addDay(DateUtil.addMonth(simStartdate, simmonth - 1 + 1), -1), companyCode)) {
			addMonth = addMonth - 1;
		}

		// 対象日付取得
		Date date = DateUtil.addDay(DateUtil.addMonth(simStartdate, addMonth), -1);
		return DateUtil.toYMDString(date);
	}

	/**
	 * 決算段階区分の取得.<br>
	 * 決算コントロールマスタより
	 * 
	 * @param companyCode 会社コード
	 * @return 決算段階区分
	 */
	public static int getAccountStageDivision(String companyCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 決算コントロールマスタより決算伝票入力区分取得
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);

		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		// 決算段階区分
		return glCtlMst.getKSD_KBN();
	}

	/**
	 * 先行伝票入力可能日付のチェック<br>
	 * Company.FiscalPeriodを利用
	 * 
	 * @param date チェック対象日付(yyyy/mm/dd)
	 * @param closeDate 締め年月日(末日)(yyyy/mm/dd)
	 * @return 先行伝票入力可能/不可能 true/false
	 * @throws ParseException
	 */
	@Deprecated
	public static boolean isSlipDateGoAhead(String date, String closeDate) throws ParseException {
		// チェック対象日付が締め年月日(末日)より後ならば、先行伝票入力可能日付ではない
		return !(DateUtil.toYMDDate(date).after(DateUtil.addYear(DateUtil.toYMDDate(closeDate), 1)));
	}

	/**
	 * 伝票番号取得 自動採番マスタより最終伝票番号を取得する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param depCode 入力部門
	 * @param systemDivision システム区分
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @param increase 増分(1または2)
	 * @return 伝票番号
	 */
	public static String getSlipNo(String companyCode, String userCode, String depCode, String systemDivision,
		String slipDate, int increase) {

		return getSlipNo(companyCode, userCode, depCode, systemDivision, slipDate, "", increase);

	}

	/**
	 * 伝票番号取得 自動採番マスタより最終伝票番号を取得する(伝票種別追加)
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param depCode 入力部門
	 * @param systemDivision システム区分
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @param slipType 伝票種別
	 * @param increase 増分(1または2)
	 * @return 伝票番号
	 */
	public static String getSlipNo(String companyCode, String userCode, String depCode, String systemDivision,
		String slipDate, String slipType, int increase) {

		String slipNo = "";

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 会社マスタより取得(自動採番部桁数、自動設定項目1～3)
		CMP_MSTDao cmpdao = (CMP_MSTDao) container.getComponent(CMP_MSTDao.class);
		CMP_MST cmpMst = cmpdao.getCMP_MST_ByKaicode(companyCode);

		if (cmpMst == null) {
			throw new TEnvironmentException("W00084", "C00910", companyCode);
		}

		int keta = cmpMst.getAUTO_NO_KETA();
		String prifix = "";

		String jid1 = cmpMst.getJID_1();
		String jid2 = cmpMst.getJID_2();
		String jid3 = cmpMst.getJID_3();

		// 自動採番コントロールより取得(最終番号)
		AutoControl logic = (AutoControl) container.getComponent(AutoControl.class);

		if (systemDivision.equals("SPT")) {
			keta = 7;
		} else if (systemDivision.equals("TIJ")) {
			keta = 7;
		} else if (systemDivision.equals("NYU")) {
			keta = 4;
		} else {

			// プレフィックス
			prifix = logic.getPrefix("", companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());

			prifix += logic.getAutoSetting(jid1, companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());
			prifix += logic.getAutoSetting(jid2, companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());
			prifix += logic.getAutoSetting(jid3, companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());

			// サフィックス
			prifix += logic.getSuffix("", companyCode, userCode, depCode, systemDivision, slipDate, slipType,
				cmpMst.getCMP_KISYU());
		}

		if (Util.isNullOrEmpty(prifix)) {
			prifix = systemDivision;
		}

		int lastno = logic.getAutoNumber(companyCode, userCode, prifix, increase);

		String zero = "";
		for (int i = 0; i < keta; i++) {
			zero = zero + "0";
		}

		slipNo = prifix + StringUtil.rightBX((zero + lastno), keta);

		return slipNo;
	}

	/**
	 * 請求書番号取得 自動採番マスタより最終請求書番号を取得する
	 * 
	 * @param companyCode 会社コード
	 * @param slipDate 伝票日付
	 * @param userCode ユーザーコード
	 * @param depCode 入力部門
	 * @param bean 入金方針マスタ
	 * @param increase 増分(1または2)
	 * @return 請求書番号
	 */
	public static String getARInvoiceNo(String prifixWord, String companyCode, String slipDate, String userCode, String depCode,
		ReceivePolicy bean, int increase) {

		String invNo = "";

		int keta = bean.getInvNoDigit(); // 桁数
		String prifix = "";

		InvoiceNoAdopt jid1 = bean.getInvoiceNoAdopt1();
		InvoiceNoAdopt jid2 = bean.getInvoiceNoAdopt2();
		InvoiceNoAdopt jid3 = bean.getInvoiceNoAdopt3();
		String jid1Name = bean.getInvNo1Name();
		String jid2Name = bean.getInvNo2Name();
		String jid3Name = bean.getInvNo3Name();

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 自動採番コントロールより取得(最終番号)
		ARAutoControlDao logic = (ARAutoControlDao) container.getComponent(ARAutoControlDao.class);
		// プレフィックス
		prifix = logic.getPrefix("", companyCode, userCode, depCode, slipDate);
		prifix += logic.getAutoSetting(jid1, jid1Name, companyCode, userCode, depCode, slipDate);
		prifix += logic.getAutoSetting(jid2, jid2Name, companyCode, userCode, depCode, slipDate);
		prifix += logic.getAutoSetting(jid3, jid3Name, companyCode, userCode, depCode, slipDate);

		// サフィックス
		prifix += logic.getSuffix("", companyCode, userCode, depCode, slipDate);

		if (prifix.length() + keta > 20) {
			return "";
		}

		String prifix2 = prifixWord + prifix;

		int lastno = logic.getAutoNumber(companyCode, userCode, prifix2, increase);

		String zero = "";
		for (int i = 0; i < keta; i++) {
			zero = zero + "0";
		}

		invNo = prifix + StringUtil.rightBX((zero + lastno), keta);

		return invNo;
	}

	/**
	 * トラオペ専用：ID取得 自動採番テーブルより最終IDを取得する
	 * 
	 * @param companyCode 会社コード
	 * @param usrCode
	 * @param prgCode
	 * @param targetDate 基準日
	 * @param prifix プリフィックス
	 * @param digit 数字部分の桁数
	 * @return OP業務ID
	 * @throws TException
	 */
	public static String getAutoId(String companyCode, String usrCode, String prgCode, Date targetDate, String prifix,
		int digit) throws TException {

		String id = "";

		S2Container container = SingletonS2ContainerFactory.getContainer();
		OPAutoControlDao logic = (OPAutoControlDao) container.getComponent(OPAutoControlDao.class);

		int lastno = logic.getAutoId(companyCode, usrCode, prgCode, prifix, 1); // 自動採番値取得

		id = prifix + DateUtil.toY2MPlainString(targetDate); // yyMM

		String zero = "";
		for (int i = 0; i < digit; i++) {
			zero = zero + "0";
		}

		id = id + StringUtil.rightBX((zero + lastno), digit);

		return id;
	}

	/**
	 * 自動採番マスタより最終番号を取得する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param prifix 特定キー
	 * @param increase 増分(1または2)
	 * @return 最終番号
	 */
	public static int getAutoNumber(String companyCode, String userCode, String prifix, int increase) {

		// 自動採番コントロールより取得(最終番号)
		S2Container container = SingletonS2ContainerFactory.getContainer();
		AutoControl logic = (AutoControl) container.getComponent(AutoControl.class);

		return logic.getAutoNumber(companyCode, userCode, prifix, increase);
	}

	/**
	 * 自動採番マスタより最終番号を文字列で取得する.<br>
	 * 指定された桁数に満たない場合は、左側に0を付与する。
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param prifix 特定キー
	 * @param increase 増分(1または2)
	 * @param keta 桁数
	 * @return 最終番号
	 */
	public static String getAutoNumber(String companyCode, String userCode, String prifix, int increase, int keta) {
		int lastno = getAutoNumber(companyCode, userCode, prifix, increase);

		return StringUtil.rightBX(NumberFormatUtil.zeroFill(lastno, keta), keta);
	}

	/**
	 * プログラム排他開始 <br>
	 * バッチコントロールマスタへの登録
	 * 
	 * @param companyCode 会社コード
	 * @param batId バッチID
	 * @param userId ユーザーID
	 * @return 排他成功/失敗 true/false
	 */
	public static boolean isProgramLockOn(String companyCode, String batId, String userId) {
		return isProgramLockOn(companyCode, batId, batId, userId);
	}

	/**
	 * プログラム排他開始 <br>
	 * バッチコントロールマスタへの登録
	 * 
	 * @param companyCode 会社コード
	 * @param batId バッチID
	 * @param prgId プログラムID
	 * @param userId ユーザーID
	 * @return 排他成功/失敗 true/false
	 */
	public static boolean isProgramLockOn(String companyCode, String batId, String prgId, String userId) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		try {
			// バッチコントロール検索
			BAT_CTLDao batdao = (BAT_CTLDao) container.getComponent(BAT_CTLDao.class);

			BAT_CTL batMe = batdao.getBAT_CTLById(companyCode, batId, prgId, userId);

			if (!Util.isNullOrEmpty(batMe)) {
				// 既に排他済みの場合
				return true;
			}

			BAT_CTL bat = batdao.getBatCtlByPK(companyCode, batId);

			if (bat != null) {
				// 該当のものがあったら既に排他されている
				return false;
			}

			// バッチコントロール登録
			BAT_CTL batCTL = new BAT_CTL();
			Date sysDate = Util.getCurrentDate();

			batCTL.setKAI_CODE(companyCode);
			batCTL.setBAT_ID(batId);
			batCTL.setBAT_STR_DATE(sysDate);
			batCTL.setBAT_END_DATE(sysDate);
			batCTL.setINP_DATE(sysDate);
			batCTL.setUPD_DATE(sysDate);
			batCTL.setPRG_ID(prgId);
			batCTL.setUSR_ID(userId);

			batdao.insert(batCTL);

		} catch (Exception e) {
			ServerErrorHandler.handledException(e);
			return false;

		}

		return true;
	}

	/**
	 * プログラム排他解除 <br>
	 * バッチコントロールマスタの登録を解除
	 * 
	 * @param companyCode 会社コード
	 * @param batId バッチコントロールID
	 * @param userId ユーザID
	 * @return 排他解除成功/失敗 true/false
	 */
	public static boolean isProgramLockOff(String companyCode, String batId, String userId) {
		return isProgramLockOff(companyCode, batId, batId, userId);
	}

	/**
	 * プログラム排他解除 <br>
	 * バッチコントロールマスタの登録を解除
	 * 
	 * @param companyCode 会社コード
	 * @param batId バッチコントロールID
	 * @param prgId プログラムID
	 * @param userId ユーザID
	 * @return 排他解除成功/失敗 true/false
	 */
	public static boolean isProgramLockOff(String companyCode, String batId, String prgId, String userId) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		try {
			// バッチコントロール検索
			BAT_CTLDao batdao = (BAT_CTLDao) container.getComponent(BAT_CTLDao.class);
			BAT_CTL batCTL = batdao.getBAT_CTLById(companyCode, batId, prgId, userId);

			// 該当のものがなかったら既に排他解除されている
			if (batCTL == null) {
				return true;
			}

			batdao.deleteById(companyCode, batId, prgId, userId);

			return true;

		} catch (Exception e) {
			ServerErrorHandler.handledException(e);
			return false;
		}
	}

	/**
	 * 出納日算出 締め日、後月、支払日情報を元に出納日(支払日／入金予定日)を算出
	 * 
	 * @param baseDate 基準日(yyyy/mm/dd)
	 * @param closeDay 締め月
	 * @param nextMonth 後月
	 * @param cashDay 支払日
	 * @return 出納日
	 * @throws ParseException
	 */
	public static String getBalanceDate(String baseDate, int closeDay, int nextMonth, int cashDay)
		throws ParseException {

		Date date = DateUtil.toYMDDate(baseDate);

		// 基準日(伝票日付)の日を取得
		int baseday = DateUtil.getDay(date);

		// 29日以降は全て月末扱い(⇒99)に変換
		if (closeDay > 29) {
			closeDay = 99;
		}

		// 比較 締め日を超えた日の場合、翌月が基準
		if (closeDay < baseday) {
			date = DateUtil.addMonth(date, 1);
		}

		// 後月分ずらす
		date = DateUtil.addMonth(date, nextMonth);

		// 日を結合
		// 29日以降は全て月末扱い
		if (cashDay > 28) {
			date = DateUtil.getLastDate(DateUtil.getYear(date), DateUtil.getMonth(date));
		} else {
			date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
		}

		return DateUtil.toYMDString(date);
	}

	/**
	 * 出納日初期値算出 <br>
	 * 取引先情報を元に支払日／入金予定日初期値を取得
	 * 
	 * @param companyCode 会社コード
	 * @param customerCode 取引先コード
	 * @param customerConditionCode 取引先条件コード(支払日モードの場合のみ必須)
	 * @param baseDate 伝票日付(計上日)
	 * @param division 区分(0：支払日、1：入金予定日)
	 * @return 出納日
	 * @throws ParseException
	 */
	public static String getInitialBalanceDate(String companyCode, String customerCode, String customerConditionCode,
		String baseDate, int division) throws ParseException {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		String jcDate;
		String jcMon;
		String jpDate;
		String sihakbn = "s";

		if (division == 0) {
			TRI_SJ_MSTDao trisjdao = (TRI_SJ_MSTDao) container.getComponent(TRI_SJ_MSTDao.class);
			List trisjlist = trisjdao.getTriSjDateRangeInfo(companyCode, customerCode, customerConditionCode,
				DateUtil.toYMDDate(baseDate));

			if (trisjlist.size() == 0) {
				return null;
			}

			jcDate = ((TRI_SJ_MST) trisjlist.get(0)).getSJC_DATE();
			jcMon = ((TRI_SJ_MST) trisjlist.get(0)).getSJR_MON();
			jpDate = ((TRI_SJ_MST) trisjlist.get(0)).getSJP_DATE();
			sihakbn = ((TRI_SJ_MST) trisjlist.get(0)).getSIHA_KBN();
		} else {
			TRI_MSTDao tridao = (TRI_MSTDao) container.getComponent(TRI_MSTDao.class);
			List trilist = tridao.getTriDateRangeInfo(companyCode, customerCode, DateUtil.toYMDDate(baseDate));

			if (trilist.size() == 0) {
				return null;
			}

			jcDate = ((TRI_MST) trilist.get(0)).getNJ_C_DATE();
			jcMon = ((TRI_MST) trilist.get(0)).getNJ_R_MON();
			jpDate = ((TRI_MST) trilist.get(0)).getNJ_P_DATE();

			jcDate = Util.isNullOrEmpty(jcDate) ? "0" : jcDate;
			jcMon = Util.isNullOrEmpty(jcMon) ? "0" : jcMon;
			jpDate = Util.isNullOrEmpty(jpDate) ? "0" : jpDate;

		}

		if ("00".equals(Util.avoidNull(sihakbn))) {
			// 臨時の場合、支払条件に関係無く、カレンダーマスタから
			AP_CAL_MSTDao caldao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
			List callist = caldao.getApCalBankInfo(companyCode, DateUtil.toYMDDate(baseDate), 1, 0);

			if (callist.size() == 0) {
				return null;
			}

			return DateUtil.toYMDString(((AP_CAL_MST) callist.get(0)).getCAL_DATE());

		} else {
			// 支払条件/入金条件を引き渡し
			return getBalanceDate(baseDate, Integer.parseInt(jcDate), Integer.parseInt(jcMon), Integer.parseInt(jpDate));
		}
	}

	/**
	 * 直近未来営業日取得<br>
	 * 対象日から未来の、最も直近の銀行営業日、臨時支払対象日、社員支払対象日を取得する。
	 * 
	 * @param companyCode 会社コード
	 * @param baseDate 基準日
	 * @param division 区分
	 * @return 直近の対象日（カレンダーがない場合、エラーの場合はnull）
	 * @Deprecated Date型引数の方を利用すること.
	 */
	public static Date getNextBusinessDay(String companyCode, String baseDate, int division) {
		try {
			return getNextBusinessDay(companyCode, DateUtil.toYMDDate(baseDate), division);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 直近未来営業日取得<br>
	 * 対象日から未来の、最も直近の銀行営業日、臨時支払対象日、社員支払対象日を取得する。
	 * 
	 * @param companyCode 会社コード
	 * @param baseDate 基準日
	 * @param division 区分
	 * @return 直近の対象日（カレンダーがない場合、エラーの場合はnull）
	 */
	public static Date getNextBusinessDay(String companyCode, Date baseDate, int division) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			AP_CAL_MSTDao dao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
			AP_CAL_MST entity;

			switch (division) {
				case 0:
					// 銀行営業日
					entity = dao.getNearBusinessDayInfo(companyCode, baseDate);
					break;

				case 1:
					// 臨時支払対象日
					entity = dao.getNearTempPayDayInfo(companyCode, baseDate);
					break;

				case 2:
					// 社員支払対象日
					entity = dao.getNearEmpPayDayInfo(companyCode, baseDate);
					break;

				default:
					return null;
			}

			// 対象日がない場合はnullを返す
			if (entity == null) {
				return null;

			} else {
				return entity.getCAL_DATE();
			}

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 直近過去営業日取得<br>
	 * 対象日から過去の、最も直近の銀行営業日、臨時支払対象日、社員支払対象日を取得する。
	 * 
	 * @param companyCode 会社コード
	 * @param baseDate 基準日
	 * @param division 区分
	 * @return 直近の対象日（カレンダーがない場合、エラーの場合はnull）
	 */
	public static Date getPreviousBusinessDay(String companyCode, String baseDate, int division) {
		try {
			return getPreviousBusinessDay(companyCode, DateUtil.toYMDDate(baseDate), division);

		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 直近過去営業日取得<br>
	 * 対象日から過去の、最も直近の銀行営業日、臨時支払対象日、社員支払対象日を取得する。
	 * 
	 * @param companyCode 会社コード
	 * @param baseDate 基準日
	 * @param division 区分
	 * @return 直近の対象日（カレンダーがない場合、エラーの場合はnull）
	 */
	public static Date getPreviousBusinessDay(String companyCode, Date baseDate, int division) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			AP_CAL_MSTDao dao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
			AP_CAL_MST entity;

			switch (division) {
				case 0:
					// 銀行営業日
					entity = dao.getNearPreBusinessDayInfo(companyCode, baseDate);
					break;

				case 1:
					// 臨時支払対象日
					entity = dao.getNearPreTempPayDayInfo(companyCode, baseDate);
					break;

				case 2:
					// 社員支払対象日
					entity = dao.getNearPreEmpPayDayInfo(companyCode, baseDate);
					break;

				default:
					return null;
			}

			// 対象日がない場合はnullを返す
			if (entity == null) {
				return null;

			} else {
				return entity.getCAL_DATE();
			}

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 指定日の銀行カレンダーが登録されているかどうか
	 * 
	 * @param companyCode 会社コード
	 * @param strDate 指定日
	 * @return <li>true : 登録されている <li>false: 登録されていない
	 */
	public static boolean isRegisteredCalender(String companyCode, String strDate) {

		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			AP_CAL_MSTDao dao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);

			Date date = DateUtil.toYMDDate(strDate);

			AP_CAL_MST bean = dao.getAP_CAL_MST(companyCode, date);

			return bean != null;

		} catch (ParseException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 振込日を取得する
	 * 
	 * @param companyCode 会社コード
	 * @param baseDate 支払予定日
	 * @param paymentDivision 臨時:10/定時
	 * @return 振込日
	 */
	public static String getDateTransferToAccount(String companyCode, String baseDate, String paymentDivision) {
		try {
			Date date = getDateTransferToAccount(companyCode, DateUtil.toYMDDate(baseDate), paymentDivision);
			return DateUtil.toYMDString(date);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 振込日を取得する
	 * 
	 * @param companyCode 会社コード
	 * @param baseDate 支払予定日
	 * @param paymentDivision 臨時:10/定時
	 * @return 振込日
	 */
	public static Date getDateTransferToAccount(String companyCode, Date baseDate, String paymentDivision) {
		Date dateTransferToAccount = null;

		// 定時の場合は支払方針マスタを元に振込日を取得する
		if ("10".equals(paymentDivision)) {
			// 支払方針マスタの設定値を取得
			S2Container container = SingletonS2ContainerFactory.getContainer();
			AP_SHH_MSTDao shhDao = (AP_SHH_MSTDao) container.getComponent(AP_SHH_MSTDao.class);
			AP_SHH_MST apShhMstbean = shhDao.getAP_SHH_MST(companyCode);

			if (Util.isNullOrEmpty(apShhMstbean)) {
				// 設定そのものがない場合は振込日 = 支払予定日
				dateTransferToAccount = baseDate;
			} else {
				// 支払可能日の場合は振込日 = 支払予定日
				if (isPayBusinessDate(companyCode, baseDate, 0)) {
					dateTransferToAccount = baseDate;
				} else {
					// 銀行休業日の場合は前日or翌日の指定を取得してセット
					int day = DateUtil.getDay(baseDate);
					switch (day) {
						case 1:
							// 支払予定日の日付が1日、且つ1日の指定が可能な場合
							if (apShhMstbean.getSHH_SIHA_1() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_1() == 0) {
									// 前日
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_1() == 1) {
									// 翌日
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 5:
							// 支払予定日の日付が5日、且つ5日の指定が可能な場合
							if (apShhMstbean.getSHH_SIHA_5() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_5() == 0) {
									// 前日
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_5() == 1) {
									// 翌日
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 10:
							// 支払予定日の日付が10日、且つ10日の指定が可能な場合
							if (apShhMstbean.getSHH_SIHA_10() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_10() == 0) {
									// 前日
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_10() == 1) {
									// 翌日
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 15:
							// 支払予定日の日付が15日、且つ15日の指定が可能な場合
							if (apShhMstbean.getSHH_SIHA_15() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_15() == 0) {
									// 前日
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_15() == 1) {
									// 翌日
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 20:
							// 支払予定日の日付が20日、且つ20日の指定が可能な場合
							if (apShhMstbean.getSHH_SIHA_20() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_20() == 0) {
									// 前日
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_20() == 1) {
									// 翌日
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						case 25:
							// 支払予定日の日付が25日、且つ25日の指定が可能な場合
							if (apShhMstbean.getSHH_SIHA_25() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_25() == 0) {
									// 前日
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_25() == 1) {
									// 翌日
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
							break;
						default:
							// 支払予定日の日付が月末日、且つ月末日の指定が可能な場合
							if (baseDate.equals(DateUtil.getLastDate(baseDate)) && apShhMstbean.getSHH_SIHA_30() == 1) {
								if (apShhMstbean.getSHH_BNK_KBN_30() == 0) {
									// 前日
									dateTransferToAccount = getPreviousBusinessDay(companyCode, baseDate, 0);
								}
								if (apShhMstbean.getSHH_BNK_KBN_30() == 1) {
									// 翌日
									dateTransferToAccount = getNextBusinessDay(companyCode, baseDate, 0);
								}
							}
					}
				}
			}
			// 支払方針に設定できない日付の場合は振込日 = 支払予定日
			if (Util.isNullOrEmpty(dateTransferToAccount)) {
				dateTransferToAccount = baseDate;
			}
			// 臨時の場合は振込日 = 支払予定日
		} else {
			dateTransferToAccount = baseDate;
		}
		return dateTransferToAccount;
	}

	/**
	 * 受付番号取得<br>
	 * 自動採番した受付番号を取得
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @return 現在日付を YYYYMMDDフォーマット + ##(連番)（取得できない場合はnull）
	 */
	public static String getAcceptanceNo(String companyCode, String userCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {

			Date currentDate = Util.getCurrentDate();
			Date currentYMDDate = DateUtil.toYMDDate(currentDate);

			// 現在日付をプレフィックスにした最大番号を取得
			AP_UKE_CTLDao dao = (AP_UKE_CTLDao) container.getComponent(AP_UKE_CTLDao.class);
			AP_UKE_CTL entity = dao.getAP_UKE_CTLByKaicodeutkdate(companyCode, currentYMDDate);

			int lastNo = 1;
			if (entity == null) {
				// 存在しなければ、番号：1でINSERT
				entity = (AP_UKE_CTL) container.getComponent(AP_UKE_CTL.class);
				entity.setKAI_CODE(companyCode);
				entity.setUTK_DATE(currentYMDDate);
				entity.setUTK_LAST_NO(lastNo);
				entity.setINP_DATE(currentDate);
				entity.setUPD_DATE(null);
				entity.setPRG_ID("AP0130");
				entity.setUSR_ID(userCode);

				dao.insert(entity);

			} else {
				// 存在すれば、番号：番号 + 1でUPDATE
				lastNo = entity.getUTK_LAST_NO() + 1;
				entity.setUTK_LAST_NO(lastNo);
				entity.setUPD_DATE(currentDate);
				entity.setUSR_ID(userCode);

				dao.update(entity);
			}

			String lastUkeNo = Integer.toString(lastNo);
			if (lastNo < 10) {
				lastUkeNo = "0" + lastUkeNo;
			}
			return DateUtil.toYMDString(currentDate).replace("/", "").replace("-", "") + lastUkeNo;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <s>和暦西暦変換<br>
	 * 和暦(YY)を西暦(YYYY)に変換<br>
	 * 和暦は平成限定！(2007年4月現時点仕様) <br>
	 * </s><br>
	 * DateUtilを利用すること.
	 * 
	 * @param JapaneseEra 和暦(YY)
	 * @return 西暦（取得できない場合はnull）
	 * @deprecated DateUtilを利用すること.
	 */
	@Deprecated
	public static String convertJapaneseEraToChristianEra(String JapaneseEra) {
		try {
			return Integer.toString(Integer.parseInt(JapaneseEra) + 1988);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <s>和暦西暦変換（元号対応）<br>
	 * 和暦(YY)を西暦(YYYY)に変換<br>
	 * </s><br>
	 * DateUtilを利用すること.
	 * 
	 * @param JapaneseEra 和暦(YY)
	 * @param gengou 元号区分 <li>0:昭和 <li>1:平成
	 * @return 西暦（取得できない場合はnull）
	 * @deprecated DateUtilを利用すること.
	 */
	@Deprecated
	public static String convertJapaneseEraToChristianEra(String JapaneseEra, int gengou) {
		try {
			if (gengou == 0) {
				return Integer.toString(Integer.parseInt(JapaneseEra) + 1925);
			} else {
				return Integer.toString(Integer.parseInt(JapaneseEra) + 1988);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * コード排他開始<br>
	 * コードでの排他開始
	 * 
	 * @param companyCode 会社コード
	 * @param division 処理区分
	 * @param code 排他コード
	 * @param gyoNo 行No
	 * @param userCode ユーザーID
	 * @param prgCode プログラムID
	 * @return 排他ロック成功/失敗 true/false
	 */
	public static boolean isCodeLockOn(String companyCode, String division, String code, String gyoNo, String userCode,
		String prgCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			HAITA_CTL entity = dao.getHAITA_CTLByKaicodeShorikbnTricodeGyono(companyCode, division, code, gyoNo);

			if (entity == null) {
				entity = (HAITA_CTL) container.getComponent(HAITA_CTL.class);
				entity.setKAI_CODE(companyCode);
				entity.setTRI_CODE(code);
				entity.setSHORI_KBN(division);
				entity.setGYO_NO(gyoNo);
				entity.setINP_DATE(Util.getCurrentDate());
				entity.setUSR_ID(userCode);
				entity.setPRG_ID(prgCode);

				dao.insert(entity);
			} else {
				// 既に排他がかかっている状態
				return false;
			}

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * コード排他強制開始<br>
	 * コードでの排他、存在チェックせずに強制的に開始する
	 * 
	 * @param companyCode 会社コード
	 * @param division 処理区分
	 * @param code 排他コード
	 * @param gyoNo 行No
	 * @param userCode ユーザーID
	 * @param prgCode プログラムID
	 * @return 排他ロック成功/失敗 true/false
	 */
	public static boolean isCodeLockOnCompulsion(String companyCode, String division, String code, String gyoNo,
		String userCode, String prgCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);
			HAITA_CTL entity = new HAITA_CTL();

			entity = (HAITA_CTL) container.getComponent(HAITA_CTL.class);
			entity.setKAI_CODE(companyCode);
			entity.setTRI_CODE(code);
			entity.setSHORI_KBN(division);
			entity.setGYO_NO(gyoNo);
			entity.setINP_DATE(Util.getCurrentDate());
			entity.setUSR_ID(userCode);
			entity.setPRG_ID(prgCode);

			dao.insert(entity);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * コード排他解除<br>
	 * ユーザコードでの排他解除
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーID
	 * @return 排他ロック解除成功/失敗 true/false
	 */
	public static boolean isCodeLockOff(String companyCode, String userCode) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			dao.deleteLockUser(companyCode, userCode);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * コード排他解除<br>
	 * ユーザコード、プログラムIDでの排他解除
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーID
	 * @param prgCode プログラムID
	 * @return 排他ロック解除成功/失敗 true/false
	 */
	public static boolean isCodeLockOff(String companyCode, String userCode, String prgCode) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			dao.deleteByUsrPrg(companyCode, userCode, prgCode);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 任意のコード排他解除<br>
	 * コードでの排他解除
	 * 
	 * @param companyCode 会社コード
	 * @param code コード
	 * @param division 区分
	 * @param gyoNo 行番号
	 * @param userCode ユーザーID
	 * @return 排他ロック解除成功/失敗 true/false
	 */
	public static boolean isCodeLockOffOption(String companyCode, String code, String division, String gyoNo,
		String userCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		try {
			HAITA_CTLDao dao = (HAITA_CTLDao) container.getComponent(HAITA_CTLDao.class);

			HAITA_CTL haita = new HAITA_CTL();

			haita.setKAI_CODE(companyCode);
			haita.setTRI_CODE(code);
			haita.setSHORI_KBN(division);
			haita.setGYO_NO(gyoNo);
			haita.setUSR_ID(userCode);

			dao.delete(haita);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 元帳日別残高表示の判定
	 * 
	 * @param companyCode 会社コード
	 * @return true: 表示する、false: 表示しない
	 */
	public static boolean isLedgerEachDayBalanceView(String companyCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 決算コントロールマスタより決算伝票入力区分取得
		GL_CTL_MSTDao glctldao = (GL_CTL_MSTDao) container.getComponent(GL_CTL_MSTDao.class);

		GL_CTL_MST glCtlMst = glctldao.getGL_CTL_MSTByIKaicode(companyCode);

		if (glCtlMst == null) {
			throw new TEnvironmentException("W00084", "C00142", companyCode);
		}

		// 決算段階区分
		return 1 == glCtlMst.getMT_ZAN_HYJ_KBN();
	}

	/**
	 * 計上会社が付替会社か判定
	 * 
	 * @param companyCode 会社コード
	 * @param appropriateCompanyCode 付替会社コード
	 * @return true: 付替会社、false: 付替会社ではない
	 */
	public static boolean isAppropriateCompanyReplace(String companyCode, String appropriateCompanyCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		KTK_MSTDao dao = (KTK_MSTDao) container.getComponent(KTK_MSTDao.class);

		KTK_MST mst = dao.getKTK_MSTByKaicodeKtkkaicode(companyCode, appropriateCompanyCode);

		if (mst == null) {
			return false;
		}

		return true;
	}

	/**
	 * 支払える日か判定(銀行支払日を考慮）
	 * 
	 * @param companyCode 会社コード
	 * @param date 支払日
	 * @param division 区分(0:定時/1:臨時)
	 * @return true: 支払える、false: 支払えない
	 */
	public static boolean isPayBusinessDate(String companyCode, Date date, int division) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		AP_SHH_MSTDao dao = (AP_SHH_MSTDao) container.getComponent(AP_SHH_MSTDao.class);
		AP_SHH_MST mst = dao.getAP_SHH_MST(companyCode);

		// 設定そのものがない場合は制限しない
		if (mst == null) {
			return true;
		}

		AP_CAL_MSTDao calDao = (AP_CAL_MSTDao) container.getComponent(AP_CAL_MSTDao.class);
		AP_CAL_MST calBean = new AP_CAL_MST();

		int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);

		Date nextMonthDate = date;
		int nextmonth = DateUtil.getMonth(DateUtil.addMonth(nextMonthDate, 1));

		// 支払える日付リスト
		List<Date> dayList = new LinkedList<Date>();

		String notExistsMsg = "W01028";
		String ym = "";

		// 定時支払の場合
		if (division == 0) {
			// 1日
			if (mst.getSHH_SIHA_1() == 1) {

				if (mst.getSHH_BNK_KBN_1() == 0) {

					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, nextmonth, 1));
					if (calBean != null) {
						dayList.add(calBean.getCAL_DATE());
					} else {
						ym = DateUtil.toYMString(DateUtil.getDate(year, nextmonth, 1));
						throw new TRuntimeException(notExistsMsg, ym);
					}

					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 1));

				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 1));
				}

				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 1));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// ５日
			if (mst.getSHH_SIHA_5() == 1) {

				if (mst.getSHH_BNK_KBN_5() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 5));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 5));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 5));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// １０日
			if (mst.getSHH_SIHA_10() == 1) {

				if (mst.getSHH_BNK_KBN_10() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 10));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 10));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 10));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// １５日
			if (mst.getSHH_SIHA_15() == 1) {

				if (mst.getSHH_BNK_KBN_15() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 15));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 15));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 15));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// ２０日
			if (mst.getSHH_SIHA_20() == 1) {

				if (mst.getSHH_BNK_KBN_20() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 20));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 20));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 20));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// ２５日
			if (mst.getSHH_SIHA_25() == 1) {

				if (mst.getSHH_BNK_KBN_25() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 25));
				} else {
					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getDate(year, month, 25));
				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(DateUtil.getDate(year, month, 25));
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// ３０日
			if (mst.getSHH_SIHA_30() == 1) {

				if (mst.getSHH_BNK_KBN_30() == 0) {
					calBean = calDao.getNearPreBusinessDayInfo(companyCode, DateUtil.getLastDate(date));
				} else {

					calBean = calDao.getNearBusinessDayInfo(companyCode,
						DateUtil.getLastDate(DateUtil.addMonth(date, -1)));
					if (calBean != null) {
						dayList.add(calBean.getCAL_DATE());
					} else {
						ym = DateUtil.toYMString(DateUtil.addMonth(date, -1));
						throw new TRuntimeException(notExistsMsg, ym);
					}

					calBean = calDao.getNearBusinessDayInfo(companyCode, DateUtil.getLastDate(date));

				}
				if (calBean != null) {
					dayList.add(calBean.getCAL_DATE());
				} else {
					ym = DateUtil.toYMString(date);
					throw new TRuntimeException(notExistsMsg, ym);
				}
			}

			// 支払日チェック
			for (Date payable : dayList) {
				if (date.equals(payable)) {
					return true;
				}
			}

			// 臨時支払の場合
		} else {

			if (date.equals(BizUtil.getNextBusinessDay(companyCode, date, 1))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 支払える日か判定
	 * 
	 * @param companyCode 会社コード
	 * @param date 支払日
	 * @param division 区分(0:定時/1:臨時)
	 * @return true: 支払える、false: 支払えない
	 */
	public static boolean isPayDate(String companyCode, Date date, int division) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		AP_SHH_MSTDao dao = (AP_SHH_MSTDao) container.getComponent(AP_SHH_MSTDao.class);
		AP_SHH_MST mst = dao.getAP_SHH_MST(companyCode);

		// 設定そのものがない場合は制限しない
		if (mst == null) {
			return true;
		}

		// 日を取得
		int day = DateUtil.getDay(date);
		// 月末日を取得
		int lastDay = DateUtil.getDay(DateUtil.getLastDate(date));

		if (division == 0) {
			// 定時支払の場合
			if (mst.getSHH_SIHA_1() == 1) {
				if (day == 1) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_5() == 1) {
				if (day == 5) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_10() == 1) {
				if (day == 10) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_15() == 1) {
				if (day == 15) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_20() == 1) {
				if (day == 20) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_25() == 1) {
				if (day == 25) {
					return true;
				}
			}

			if (mst.getSHH_SIHA_30() == 1) {
				if (day == lastDay) {
					return true;
				}
			}
		} else {

			// 臨時支払の場合
			if (date.equals(BizUtil.getNextBusinessDay(companyCode, date, 1))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 伝票タイトル取得 <br>
	 * 取得できない場合はNULLを返す
	 * 
	 * @param companyCode 会社コード
	 * @param denSyuCode 伝票種別コード
	 * @return 伝票タイトル
	 */
	public static String getSlipTitle(String companyCode, String denSyuCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		DEN_SYU_MSTDao dao = (DEN_SYU_MSTDao) container.getComponent(DEN_SYU_MSTDao.class);

		DEN_SYU_MST mst = dao.getDEN_SYU_MSTByKaicodeDensyucode(companyCode, denSyuCode);

		if (mst == null) {
			return null;
		}

		return mst.getDEN_SYU_NAME_K();
	}

	/**
	 * 相手科目の設定
	 * 
	 * @param cmpCode 会社コード
	 * @param denNo 伝票番号
	 */
	public static void setOtherItem(String cmpCode, String denNo) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		SWK_DTLDao SwkDtlDao = (SWK_DTLDao) container.getComponent(SWK_DTLDao.class);

		// 区分フラグ
		int dcKbn = 0;
		String kmkCode = "";
		String hkmCode = "";
		String ukmCode = "";
		String depCode = "";

		// 借方桁数を取得
		int numDebitCount = SwkDtlDao.getSwkDtlByKeta(cmpCode, denNo, dcKbn);
		// 1:Nの場合
		if (numDebitCount == 1) {

			dcKbn = 0;
			SWK_DTL SwkDtlKariItem = SwkDtlDao.getSwkDtlByKaicodeSwkden(cmpCode, denNo, dcKbn);
			kmkCode = SwkDtlKariItem.getSWK_KMK_CODE();
			hkmCode = SwkDtlKariItem.getSWK_HKM_CODE();
			ukmCode = SwkDtlKariItem.getSWK_UKM_CODE();
			depCode = SwkDtlKariItem.getSWK_DEP_CODE();

			dcKbn = 1;
			// 相手科目を更新する
			SwkDtlDao.updateSwkDtlByCoItem(cmpCode, denNo, dcKbn, kmkCode, hkmCode, ukmCode, depCode);

		}

		dcKbn = 1;
		// 貸方桁数を取得
		int numCreditCount = SwkDtlDao.getSwkDtlByKeta(cmpCode, denNo, dcKbn);
		// N:1の場合
		if (numCreditCount == 1) {

			dcKbn = 1;
			SWK_DTL SwkDtlKariItem = SwkDtlDao.getSwkDtlByKaicodeSwkden(cmpCode, denNo, dcKbn);
			kmkCode = SwkDtlKariItem.getSWK_KMK_CODE();
			hkmCode = SwkDtlKariItem.getSWK_HKM_CODE();
			ukmCode = SwkDtlKariItem.getSWK_UKM_CODE();
			depCode = SwkDtlKariItem.getSWK_DEP_CODE();

			dcKbn = 0;
			// 相手科目を更新する
			SwkDtlDao.updateSwkDtlByCoItem(cmpCode, denNo, dcKbn, kmkCode, hkmCode, ukmCode, depCode);

		}
	}

	/**
	 * 期首日を取得する
	 * 
	 * @param cmpCode
	 * @return 期首日
	 */
	public static Date getInitialDate(String cmpCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		SIM_CTLDao simdao = (SIM_CTLDao) container.getComponent(SIM_CTLDao.class);

		return simdao.getSIM_CTLByIKaicode(cmpCode).getSIM_STR_DATE();

	}

	/**
	 * 自動仕訳科目を取得する.<br>
	 * 補助、内訳が無い場合はNULL.
	 * 
	 * @param kaiCode 会社コード
	 * @param kmkCnt 科目制御区分
	 * @return 科目コード[0:科目コード, 1:補助科目コード, 2:内訳科目コード 3:計上部門コード]
	 */
	public static String[] getAutoSwkItem(String kaiCode, int kmkCnt) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		SWK_KMK_MSTDao dao = (SWK_KMK_MSTDao) container.getComponent(SWK_KMK_MSTDao.class);

		SWK_KMK_MST entity = dao.getSWK_KMK_MSTByKaicodeKmkcnt(kaiCode, kmkCnt);

		String[] kmkArrays = new String[4];

		if (entity != null) {
			kmkArrays[0] = entity.getKMK_CODE();
			kmkArrays[1] = entity.getHKM_CODE();
			kmkArrays[2] = entity.getUKM_CODE();
			kmkArrays[3] = entity.getDEP_CODE();
		}

		return kmkArrays;
	}

	/**
	 * 科目コードに対する多通貨対応有無を判定する.<br>
	 * 複数データ内の科目を確認する場合、毎回判定すると遅くなる為、<br>
	 * 同じ科目コードに対しては二回確認しないように呼び出し側で考慮すること.
	 * 
	 * @param compCode 会社コード
	 * @param itemCode 科目コード
	 * @return true: 多通貨可、false: 多通貨不可
	 */
	public static boolean isMultiCurrency(String compCode, String itemCode) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		KMK_MSTDao dao = (KMK_MSTDao) container.getComponent(KMK_MSTDao.class);
		KMK_MST bean = dao.getKMK_MSTByKaicodeKmkcode(compCode, itemCode);

		if (bean == null) {
			throw new TEnvironmentException("W00084", "C00077", compCode, itemCode);
		}

		return bean.getMCR_FLG() == 1;
	}
}
