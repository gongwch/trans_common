package jp.co.ais.trans.common.client.util;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 共通情報取得コントロール
 */
public class InformationUtil extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** インスタンス.Staticアクセスの為 */
	private static InformationUtil instance = new InformationUtil();

	/** 組織情報 */
	private OrganizationInfo orgInfo = new OrganizationInfo();

	/** 科目情報 */
	private ItemInfo itemInfo = new ItemInfo();

	/** 補助科目情報 */
	private SubItemInfo subItemInfo = new SubItemInfo();

	/** 内訳科目情報 */
	private BreakDownItemInfo downItemInfo = new BreakDownItemInfo();

	/**
	 * 組織コードリスト取得
	 * 
	 * @return 組織コードリスト
	 * @throws TException
	 */
	public static String[] getOrganizationCodeList() throws TException {
		return instance.getOrganizationCodeListNative();
	}

	/**
	 * 組織コードリスト取得 内部
	 * 
	 * @return 組織コードリスト
	 * @throws TException
	 */
	private String[] getOrganizationCodeListNative() throws TException {
		try {
			addSendValues("FLAG", "OrganizationCode");

			// 送信
			if (!request(TARGET_SERVLET)) {
				throw new TException(getErrorMessage());
			}

			// 組織ｺｰﾄﾞを設定する
			return StringUtil.toArrayFromDelimitString(getResult().get("orgCodes"));
		} catch (IOException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 組織情報取得
	 * 
	 * @param kmtCode 科目体系コード
	 * @param orgCode 組織コード
	 * @return 組織情報
	 * @throws TException
	 */
	public static OrganizationInfo getOrganizationInfo(String kmtCode, String orgCode) throws TException {
		return instance.getOrganizationInfoNative(kmtCode, orgCode);
	}

	/**
	 * 組織情報取得
	 * 
	 * @param kmtCode 科目体系コード
	 * @param orgCode 組織コード
	 * @return 組織情報
	 * @throws TException
	 */
	private OrganizationInfo getOrganizationInfoNative(String kmtCode, String orgCode) throws TException {

		try {
			addSendValues("FLAG", "OrganizationInfo"); // 処理区分
			addSendValues("KAI_CODE", getLoginUserCompanyCode()); // 会社コード
			addSendValues("USR_ID", getLoginUserID()); // ユーザコード
			addSendValues("KMT_CODE", kmtCode); // 科目体系コード
			addSendValues("ORGANIZATION_CODE", orgCode); // 組織コード

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				throw new TException(getErrorMessage());
			}

			// 値のセット
			orgInfo.set(getResult());

			return orgInfo;
		} catch (IOException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 科目情報取得
	 * 
	 * @param kmkCode 科目コード
	 * @return 科目情報
	 * @throws TException
	 */
	public static ItemInfo getItemInfo(String kmkCode) throws TException {
		return instance.getItemInfoNative(kmkCode, "", "");
	}

	/**
	 * 補助科目情報取得
	 * 
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @return 補助科目情報
	 * @throws TException
	 */
	public static SubItemInfo getItemInfo(String kmkCode, String hkmCode) throws TException {
		return (SubItemInfo) instance.getItemInfoNative(kmkCode, hkmCode, "");
	}

	/**
	 * 内訳科目情報取得
	 * 
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return 内訳科目情報
	 * @throws TException
	 */
	public static BreakDownItemInfo getItemInfo(String kmkCode, String hkmCode, String ukmCode) throws TException {
		return (BreakDownItemInfo) instance.getItemInfoNative(kmkCode, hkmCode, ukmCode);
	}

	/**
	 * 科目情報取得
	 * 
	 * @param kmkCode 科目コード
	 * @param hkmCode 補助科目コード
	 * @param ukmCode 内訳科目コード
	 * @return 科目情報
	 * @throws TException
	 */
	private ItemInfo getItemInfoNative(String kmkCode, String hkmCode, String ukmCode) throws TException {

		try {
			addSendValues("FLAG", "ItemInfo"); // 処理区分
			addSendValues("KAI_CODE", getLoginUserCompanyCode()); // 会社コード
			addSendValues("KMK_CODE", kmkCode); // 科目コード
			addSendValues("HKM_CODE", hkmCode); // 補助科目コード
			addSendValues("UKM_CODE", ukmCode); // 内訳科目コード

			// 指定された文字がブランクかどうかで科目種類を判断
			ItemInfo item;
			if (Util.isNullOrEmpty(hkmCode)) {
				// 科目種類(科目)
				addSendValues("ITEM_KIND", "Item");
				item = itemInfo;

			} else if (Util.isNullOrEmpty(ukmCode)) {
				// 科目種類(補助科目)
				addSendValues("ITEM_KIND", "SubItem");
				item = subItemInfo;

			} else {
				// 科目種類(内訳科目)
				addSendValues("ITEM_KIND", "BreakDownItem");
				item = downItemInfo;
			}

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				throw new TException(getErrorMessage());
			}

			Map result = getResult();
			if (result.isEmpty()) {
				throw new TException("W00100", kmkCode, hkmCode, ukmCode);
			}

			item.set(result);
			return item;

		} catch (IOException e) {
			throw new TException(e, "E00009");
		}

	}
}
