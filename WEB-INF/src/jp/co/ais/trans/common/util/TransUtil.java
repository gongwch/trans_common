package jp.co.ais.trans.common.util;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.*;

/**
 * Transのユーティリティクラスです。
 * 
 * @author AIS
 */
public class TransUtil {

	/**
	 * 借方
	 */
	public final static int DEBIT = 0;

	/**
	 * 貸方
	 */
	public final static int CREDIT = 1;

	/**
	 * 集計グループ
	 * 
	 * @author AIS
	 */
	public enum SumGroup {
		/** 集計なし */
		None,
		/** 部門 */
		Bmn,
		/** 取引先 */
		Tri,
		/** 取引先(集計) */
		TriSum,
		/** 取引先(連結) */
		TriCon,
		/** 社員 */
		Emp,
		/** 管理1 */
		Knr1,
		/** 管理2 */
		Knr2,
		/** 管理3 */
		Knr3,
		/** 管理4 */
		Knr4,
		/** 管理5 */
		Knr5,
		/** 管理6 */
		Knr6
	}

	/** 科目レベル */
	public enum ItemLevel {
		/** 科目 */
		Item,
		/** 補助科目 */
		SubItem,
		/** 内訳科目 */
		DetailItem
	}

	/**
	 * 基本科目体系コード
	 */
	public final static String DEFAULT_ITEM_ORGANIZATION_CODE = "00";

	/**
	 * 更新区分：登録
	 */
	public final static int UPD_KBN_ENTRY = 1;

	/**
	 * 更新区分：現場承認
	 */
	public final static int UPD_KBN_GENBA_APPROVE = 2;

	/**
	 * 更新区分：承認
	 */
	public final static int UPD_KBN_APPROVE = 3;

	/**
	 * 更新区分：更新
	 */
	public final static int UPD_KBN_UPDATE = 4;

	/**
	 * 更新区分：現場否認
	 */
	public final static int UPD_KBN_GENBA_DENY = 11;

	/**
	 * 更新区分：否認
	 */
	public final static int UPD_KBN_DENY = 12;

	/**
	 * 更新区分：登録
	 */
	public final static String UPD_KBN_ENTRY_CHAR = "C01258";

	/**
	 * 更新区分：現場承認
	 */
	public final static String UPD_KBN_GENBA_APPROVE_CHAR = "C00157";

	/**
	 * 更新区分：承認
	 */
	public final static String UPD_KBN_APPROVE_CHAR = "C01168";

	/**
	 * 更新区分：更新
	 */
	public final static String UPD_KBN_UPDATE_CHAR = "C00169";

	/**
	 * 更新区分：現場否認
	 */
	public final static String UPD_KBN_GENBA_DENY_CHAR = "C01617";

	/**
	 * 更新区分：否認
	 */
	public final static String UPD_KBN_DENY_CHAR = "C00447";

	/**
	 * 更新区分(int)に紐付く文言を返します。
	 * 
	 * @param updKbn 更新区分
	 * @return 更新区分(int)に紐付く文言
	 */
	public final static String getUpdKbnChar(int updKbn) {

		if (UPD_KBN_ENTRY == updKbn) {
			return UPD_KBN_ENTRY_CHAR;
		} else if (UPD_KBN_GENBA_APPROVE == updKbn) {
			return UPD_KBN_GENBA_APPROVE_CHAR;
		} else if (UPD_KBN_APPROVE == updKbn) {
			return UPD_KBN_APPROVE_CHAR;
		} else if (UPD_KBN_UPDATE == updKbn) {
			return UPD_KBN_UPDATE_CHAR;
		} else if (UPD_KBN_GENBA_DENY == updKbn) {
			return UPD_KBN_GENBA_DENY_CHAR;
		} else if (UPD_KBN_DENY == updKbn) {
			return UPD_KBN_DENY_CHAR;
		}
		return null;
	}

	/**
	 * 売上仕入区分：対象外
	 */
	public final static int US_KBN_NONE = 0;

	/**
	 * 売上仕入区分：売上
	 */
	public final static int US_KBN_URI = 1;

	/**
	 * 売上仕入区分：仕入
	 */
	public final static int US_KBN_SIR = 2;

	/**
	 * 消費税の分類
	 * 
	 * @author AIS
	 */
	public enum ZeiKeiKbn {
		/** 課税売上 */
		UriKazei,
		/** 免税売上 */
		UriMenzei,
		/** 非課税売上 */
		UriHikazei,
		/** 課税仕入 */
		SirKazei,
		/** 非課税仕入 */
		SirHikazei
	}

	/**
	 * 消費税分類に紐付くenumを返します。
	 * 
	 * @param zeiKeiKbn 消費税分類
	 * @return 消費税分類に紐付くenum
	 */
	public final static ZeiKeiKbn getZeiKeiKbn(String zeiKeiKbn) {

		if ("1".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.UriKazei;
		} else if ("2".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.UriMenzei;
		} else if ("3".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.UriHikazei;
		} else if ("4".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.SirKazei;
		} else if ("5".equals(zeiKeiKbn)) {
			return ZeiKeiKbn.SirHikazei;
		}

		return null;
	}

	/**
	 * テーブルIDを返す
	 * 
	 * @param model サーバベース
	 * @param tableName
	 * @return テーブルID
	 */
	public static String getTableName(TModel model, String tableName) {
		if (model == null || model.getUser() == null) {
			return tableName;
		}

		String userLang = model.getUser().getLanguage();

		if (!Util.isNullOrEmpty(userLang)) {

			boolean isUseMultiLang = ServerConfig.isFlagOn("trans.use.multi.lang");
			if (!isUseMultiLang) {
				return tableName;
			}

			boolean hasLang = false;
			String[] langArr = ServerConfig.getProperties("trans.use.multi.lang.list");
			for (String lang : langArr) {
				if (Util.avoidNull(lang).equalsIgnoreCase(userLang)) {
					hasLang = true;
					break;
				}
			}

			if (!hasLang) {
				// 言語が対象外
				return tableName;
			}

			boolean hasTable = false;
			String[] tableArr = ServerConfig.getProperties("trans.use.multi.lang.table.list");
			for (String tbl : tableArr) {
				if (Util.avoidNull(tbl).equalsIgnoreCase(tableName)) {
					hasTable = true;
					break;
				}
			}

			if (!hasTable) {
				// テーブルが対象外
				return tableName;
			}

			return (tableName + "_" + userLang).toUpperCase();
		}

		return tableName;
	}

	/**
	 * テーブルIDを返す
	 * 
	 * @param ctrl クライアントベース
	 * @param tableName
	 * @return テーブルID
	 */
	public static String getTableName(TController ctrl, String tableName) {
		if (ctrl == null || ctrl.getUser() == null) {
			return tableName;
		}

		String userLang = ctrl.getUser().getLanguage();

		if (!Util.isNullOrEmpty(userLang)) {

			boolean isUseMultiLang = ClientConfig.isFlagOn("trans.use.multi.lang");
			if (!isUseMultiLang) {
				return tableName;
			}

			boolean hasLang = false;
			String[] langArr = ClientConfig.getProperties("trans.use.multi.lang.list");
			for (String lang : langArr) {
				if (Util.avoidNull(lang).equalsIgnoreCase(userLang)) {
					hasLang = true;
					break;
				}
			}

			if (!hasLang) {
				// 言語が対象外
				return tableName;
			}

			boolean hasTable = false;
			String[] tableArr = ClientConfig.getProperties("trans.use.multi.lang.table.list");
			for (String tbl : tableArr) {
				if (Util.avoidNull(tbl).equalsIgnoreCase(tableName)) {
					hasTable = true;
					break;
				}
			}

			if (!hasTable) {
				// テーブルが対象外
				return tableName;
			}

			return (tableName + "_" + userLang).toUpperCase();
		}

		return tableName;
	}

}
