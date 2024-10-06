package jp.co.ais.trans.common.client.util;

/**
 * 補助科目情報
 */
public class SubItemInfo extends ItemInfo {

	/** 補助区分 0:なし 1:あり */
	private static final String SUB_DIVISION = "1";

	/**
	 * コンストラクタ
	 */
	protected SubItemInfo() {
		nameCode = "HKM_NAME";
		shortNameCode = "HKM_NAME_S";
	}

	/**
	 * 内訳科目が存在するかどうか
	 * 
	 * @return 存在する場合true
	 */
	public boolean isExistBreakDownItem() {
		return SUB_DIVISION.equals(data.get("HKM_KBN"));
	}

}
