package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * OP Item Control Division
 */
public enum OPItemControlDivision implements TEnumRadio {

	/** FRT : FREIGHT **/
	FREIGHT("FRT"),

	/** BEX : BUNKER EXPENSE **/
	BUNKER_EXPENSE("BEX"),

	/** BSK : BUNKER STOCK **/
	BUNKER_STOCK("BSK"),

	/** BSE : BUNKER STOCK ESTIMATE **/
	BUNKER_STOCK_ESTIMATE("BSE"),

	/** BSR : BUNKER STOCK TI-BOR/TO-BOR **/
	BUNKER_STOCK_BOR("BSR"),

	/** BSP : BUNKER SUPPLY **/
	BUNKER_SUPPLY("BSP"),

	/** BSP : BUNKER SUPPLY(傭船者立替) */
	BUNKER_SUPPLY_CH("BSP-CH"),

	/** BSP : BUNKER SUPPLY(船主立替) */
	BUNKER_SUPPLY_OW("BSP-OW"),

	/** BKR : BUNKER **/
	BUNKER("BKR"),

	/** OH1 : SOBC未作成、OH未作成の場合月次処理ITEM用 **/
	OH_1_NO_SOBC("OH1"),

	/** OH2 : SOBC作成済み、OH未作成の場合月次処理ITEM用 **/
	OH_2_DONE_SOBC("OH2"),

	/** ADV : ADVANCE **/
	ADVANCE("ADV"),

	/** PCG : PORT CHARGE **/
	PORT_CHARGE("PCG"),

	/** BRO : BROKERAGE **/
	BROKERAGE("BRO"),

	/** OEX : OTHER EXPENSE **/
	OTHER_EXPENSE("OEX"),

	/** HIR : HIRE **/
	HIRE("HIR"),

	/** OPF : 運航委託料 */
	OPERATION_FEE("OPF");

	/** 値 */
	public String value;

	/**
	 * コンストラクター
	 * 
	 * @param value
	 */
	OPItemControlDivision(String value) {
		this.value = value;
	}

	/**
	 * 名称取得
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 名称を返す
	 * 
	 * @param type
	 * @return 名称
	 */
	public static String getName(OPItemControlDivision type) {
		if (type == null) {
			return "";
		}

		return type.value;
	}

	/**
	 * Enumを返す
	 * 
	 * @param value
	 * @return Enum
	 */
	public static OPItemControlDivision get(String value) {
		for (OPItemControlDivision em : values()) {
			if (em.value.equals(value)) {
				return em;
			}
		}

		return null;
	}

}
