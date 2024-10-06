package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * 税区分
 * 
 * @author AIS
 */
public enum TaxCalcType {

	/** 外税 */
	OUT(0),

	/** 内税 */
	IN(1),

	/** 非課税 */
	NONE(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private TaxCalcType(int value) {
		this.value = value;
	}

	/**
	 * TaxCalcTypeを返す
	 * 
	 * @param type TaxCalcType
	 * @return TaxCalcType
	 */
	public static TaxCalcType get(int type) {
		for (TaxCalcType em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * TaxCalcType名を返す
	 * 
	 * @return TaxCalcType名
	 */
	public String getName() {
		return getTaxCalcTypeName(this);
	}

	/**
	 * TaxCalcType名を返す
	 * 
	 * @param type TaxCalcType
	 * @return TaxCalcType名
	 */
	public static String getTaxCalcTypeName(TaxCalcType type) {
		if (type == null) {
			return null;
		}
		
		switch (type) {
			case OUT: // 外税
				return "C00337";

			case IN: // 内税
				return "C00023";

			case NONE: // 非課税
				return "C01971";

			default:
				return null;
		}
	}

	/**
	 * 内税・外税のenum
	 * 
	 * @param taxCulKbnRole
	 * @return 内税・外税
	 */
	public static TaxCalcType getTaxCulKbn(boolean taxCulKbnRole) {

		if (taxCulKbnRole == false) {
			return OUT;
		} else {
			return IN;
		}
	}

	/**
	 * 税区分のEnum名称取得<br>
	 * コード：名称、コード：名称形式
	 * 
	 * @param lang 言語コード
	 * @return 税区分のEnum名称
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (TaxCalcType type : TaxCalcType.values()) {
			sb.append(type.value);
			sb.append("：");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("、");
		}

		return sb.substring(0, sb.length() - 1);

	}

	/**
	 * 税区分名称からコードを返す<BR>
	 * コードが引き当らない、または名称が{@code null}の場合-1を返す
	 * 
	 * @param name 税区分名称
	 * @param lang 言語コード
	 * @return 税区分コード
	 */
	public static int getCode4Name(String name, String lang) {

		int result = -1;

		if (name == null) {
			return result;
		}

		if (name.equals(MessageUtil.getWord(lang, "C00337"))) {
			result = OUT.value;
		} else if (name.equals(MessageUtil.getWord(lang, "C00023"))) {
			result = IN.value;
		} else if (name.equals(MessageUtil.getWord(lang, "C01971"))) {
			result = NONE.value;
		}

		return result;
	}
}
