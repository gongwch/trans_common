package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ログイン情報
 * 
 * @author AIS
 */
public class TLoginInfo {

	/** ログイン会社 */
	protected static Company company = null;

	/** ログインユーザー */
	protected static User user = null;

	/** 自動仕訳マップ */
	protected static Map<Integer, AutoJornalAccount> taxAutoJournalMap = null;

	/**
	 * ログイン会社
	 * 
	 * @return ログイン会社
	 */
	public static Company getCompany() {
		return company;
	}

	/**
	 * ログイン会社
	 * 
	 * @param company ログイン会社
	 */
	public static void setCompany(Company company) {
		TLoginInfo.company = company;
	}

	/**
	 * ログインユーザー
	 * 
	 * @return ログインユーザー
	 */
	public static User getUser() {
		return user;
	}

	/**
	 * ログインユーザー
	 * 
	 * @param user ログインユーザー
	 */
	public static void setUser(User user) {
		TLoginInfo.user = user;
	}

	/**
	 * タイトル色
	 * 
	 * @return タイトル色
	 */
	public static LookAndFeelColor getTitleColor() {
		return LookAndFeelColor.get(user.getLfColorType());
	}

	/**
	 * 自動仕訳マップの取得
	 * 
	 * @return autoJournalMap 自動仕訳マップ
	 */
	public static Map<Integer, AutoJornalAccount> getAutoJournalMap() {
		return taxAutoJournalMap;
	}

	/**
	 * 自動仕訳マップの設定
	 * 
	 * @param map 自動仕訳マップ
	 */
	public static void setAutoJournalMap(Map<Integer, AutoJornalAccount> map) {
		taxAutoJournalMap = map;
	}

	/**
	 * 自動仕訳の取得
	 * 
	 * @param kbn
	 * @return 自動仕訳
	 */
	public static AutoJornalAccount getAutoJornalAccount(int kbn) {
		return taxAutoJournalMap.get(kbn);
	}

	/**
	 * @param itemCode
	 * @param subItemCode
	 * @param detailItemCode
	 * @return true:消費税自動仕訳の科目
	 */
	public static boolean isTaxAutoItem(String itemCode, String subItemCode, String detailItemCode) {
		if (taxAutoJournalMap == null || taxAutoJournalMap.isEmpty()) {
			return false;
		}
		for (AutoJornalAccount a : taxAutoJournalMap.values()) {
			if (Util.equals(itemCode, a.getItemCode()) //
				&& Util.equals(subItemCode, a.getSubItemCode()) //
				&& Util.equals(detailItemCode, a.getDetailItemCode()) //
			) {
				return true;
			}
		}
		return false;
	}
}
