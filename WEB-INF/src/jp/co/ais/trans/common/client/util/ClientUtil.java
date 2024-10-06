package jp.co.ais.trans.common.client.util;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * クライアントサイド(画面)より利用される業務ユーティリティ<br>
 * ログインユーザの情報を元に動作する
 */
public class ClientUtil {

	/**
	 * 消費税額を返します。
	 * 
	 * @param amount 本体価額
	 * @param zeiKbn 税区分(0:内税 1:外税)
	 * @param zei 消費税マスタEntity
	 * @param cmpMst 会社コントロールマスタEntity
	 * @param curMst 通貨
	 * @return 消費税額
	 */
	@Deprecated
	public static BigDecimal calculateTaxInside(BigDecimal amount, int zeiKbn, SZEI_MST zei, CMP_MST cmpMst,
		CUR_MST curMst) {

		BigDecimal returnTax = new BigDecimal(0);

		if (zeiKbn == 0) {
			returnTax = amount.multiply(new BigDecimal(zei.getZEI_RATE() / (100 + zei.getZEI_RATE())));
		} else {
			returnTax = amount.multiply(new BigDecimal(zei.getZEI_RATE() / 100));
		}

		if (zei.getUS_KBN() == 1) { // 売上課税（仮受消費税）
			// 仮受消費税端数処理
			switch (cmpMst.getKU_KBN()) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, curMst.getDEC_KETA());
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, curMst.getDEC_KETA());
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, curMst.getDEC_KETA());
					break;

			}

		} else if (zei.getUS_KBN() == 2) {
			// 仮払消費税端数処理
			switch (cmpMst.getKB_KBN()) {
				case 0:
					returnTax = DecimalUtil.truncateNum(returnTax, curMst.getDEC_KETA());
					break;

				case 1:
					returnTax = DecimalUtil.ceilingNum(returnTax, curMst.getDEC_KETA());
					break;

				default:
					returnTax = DecimalUtil.roundNum(returnTax, curMst.getDEC_KETA());
					break;

			}
		}

		return returnTax;
	}

	/**
	 * 年度取得 期首月に応じた対象日付の年度を取得.<br>
	 * ログインユーザ情報に紐づく期首月を元に算出
	 * 
	 * @param day 対象年月日
	 * @return 対象日付の年度
	 */
	@Deprecated
	public static int getFiscalYear(Date day) {

		// 対象年月日の年
		int year = DateUtil.getYear(day);

		// 対象年月日の月
		int month = DateUtil.getMonth(day);

		// 期首月取得
		int initialMonth =  TLoginInfo.getCompany().getFiscalPeriod().getMonthBeginningOfPeriod();

		if (initialMonth > month) {
			year = year - 1;
		}

		return year;
	}

	/**
	 * 月度取得
	 * 
	 * @param targetDate 対象日付
	 * @return 月度
	 */
	@Deprecated
	public static int getFiscalMonth(Date targetDate) {
		// 月度取得
		return Util.getFiscalMonth(getInitialMonth(), DateUtil.getMonth(targetDate));
	}

	/**
	 * 期首月取得<br>
	 * ログインユーザの会社情報に紐づく期首月
	 * 
	 * @return 期首月
	 */
	@Deprecated
	public static int getInitialMonth() {
		return TLoginInfo.getCompany().getFiscalPeriod().getMonthBeginningOfPeriod();
	}

	/**
	 * 基軸通貨小数点桁数取得<br>
	 * ログインユーザの会社情報に紐づく基軸通貨小数点桁数
	 * 
	 * @return 小数点桁数
	 */
	@Deprecated
	public static int getCurrencyDigit() {
		return TClientLoginInfo.getInstance().getCompanyInfo().getBaseCurrencyDigit();
	}

	/**
	 * ログインユーザ言語コードに紐づく単語取得
	 * 
	 * @param wordID 単語ID
	 * @return 単語
	 */
	@Deprecated
	public static String getWord(String wordID) {
		return MessageUtil.getWord(TClientLoginInfo.getInstance().getUserLanguage(), wordID);
	}

	/**
	 * ログインユーザ言語コードに紐づく単語リスト取得
	 * 
	 * @param wordIDs 単語IDリスト
	 * @return 単語リスト
	 */
	@Deprecated
	public static String[] getWordList(String[] wordIDs) {
		return MessageUtil.getWordList(TClientLoginInfo.getInstance().getUserLanguage(), wordIDs);
	}

	/**
	 * ログインユーザ言語コードに紐づくメッセージ取得
	 * 
	 * @param messageID メッセージID
	 * @return メッセージ
	 */
	@Deprecated
	public static String getMessage(String messageID) {
		return MessageUtil.convertMessage(TClientLoginInfo.getInstance().getUserLanguage(), messageID);
	}
}
