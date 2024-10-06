package jp.co.ais.trans.common.client.util;

import java.util.*;

/**
 * 科目情報
 */
public class ItemInfo {

	/** 補助区分 0:なし 1:あり */
	private static final String SUB_DIVISION = "1";

	/** GL科目制御区分 00:通常 */
	public static final String GLCTL_NOMAL = "00";

	/** GL科目制御区分 01:前期繰越利益 */
	public static final String GLCTL_FORWARD_EARNINGS = "01";

	/** GL科目制御区分 04:資金科目 */
	public static final String GLCTL_FUND = "04";

	/** GL科目制御区分 05:売上科目 */
	public static final String GLCTL_SALES = "05";

	/** GL科目制御区分 06:為替換算差損益 */
	public static final String GLCTL_EXCHANGE_GAIN_OR_LOSS = "06";

	/** GL科目制御区分 07:仮勘定 */
	public static final String GLCTL_TEMP_ACCOUNT = "07";

	/** GL科目制御区分 08:為替差損 */
	public static final String GLCTL_EXCHANGE_LOSS = "08";

	/** GL科目制御区分 09:為替差益 */
	public static final String GLCTL_EXCHANGE_GAIN = "09";

	/** 集計区分（0:入力科目） */
	public static final String SUMDIV_INPUT = "0";

	/** 集計区分（1:集計科目） */
	public static final String SUMDIV_SUMMARY = "1";

	/** 集計区分（2:見出科目） */
	public static final String SUMDIV_CAPTION = "2";

	/** データ */
	protected Map<String, String> data;

	/** 名称キー */
	protected String nameCode;
	
	/** 名称キー */
	protected String shortNameCode;
	
	/**
	 * コンストラクタ
	 */
	protected ItemInfo() {
		nameCode = "KMK_NAME";
		shortNameCode = "KMK_NAME_S";
	}
	
	/**
	 * データをセット
	 * 
	 * @param data データ
	 */
	protected void set(Map<String, String> data) {
		this.data = data;
	}

	/**
	 * 科目略称取得
	 * 
	 * @return 科目略称
	 */
	public String getName() {
		return data.get(nameCode);
	}

	/**
	 * 科目略称取得
	 * 
	 * @return 科目略称
	 */
	public String getShortName() {
		return data.get(shortNameCode);
	}

	/**
	 * 集計区分
	 * 
	 * @return 集計区分
	 */
	public String getSumKbn() {
		return data.get("SUM_KBN");
	}

	/**
	 * GL科目制御区分
	 * 
	 * @return GL科目制御区分
	 */
	public String getKmKCntGL() {
		return data.get("KMK_CNT_GL");
	}

	/**
	 * 補助科目が存在するかどうか
	 * 
	 * @return 存在する場合true
	 */
	public boolean isExistSubItem() {
		return SUB_DIVISION.equals(data.get("HKM_KBN"));
	}
}
